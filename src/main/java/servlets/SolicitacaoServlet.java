package servlets;

import dao.AvaliacaoDao;
import dao.AuditoriaDao;
import dao.CriarSolicitacaoDao;
import dao.SolicitacaoDao;
import model.AuditoriaModel;
import model.AvaliacaoModel;
import model.UsuarioModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/SolicitacaoServlet")
public class SolicitacaoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        HttpSession session = request.getSession();
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario");
        String email = (String) session.getAttribute("email");
        String senha = (String) session.getAttribute("senha");
        String cargo = (String) session.getAttribute("cargo");

        SolicitacaoDao solicitacaoDao = new SolicitacaoDao();
        CriarSolicitacaoDao criarSolicitacaoDao = new CriarSolicitacaoDao();
        AuditoriaDao auditoriaDao = new AuditoriaDao();
        AvaliacaoDao avaliacaoDao = new AvaliacaoDao();

        try {
            if ("nova".equals(acao) && "solicitante".equalsIgnoreCase(cargo)) {
                String data = request.getParameter("data");
                String horarioInicio = request.getParameter("horarioInicio");
                String horarioFim = request.getParameter("horarioFim");
                Long idEspaco = Long.parseLong(request.getParameter("sala"));

                criarSolicitacaoDao.cadastrarSolicitacaoPorId(email, senha, data, horarioInicio, horarioFim, idEspaco);

                // Auditoria
                AuditoriaModel auditoria = new AuditoriaModel();
                auditoria.setIdUsuarioFk(usuario.getIdUsuario());
                auditoria.setDataAcao(java.sql.Date.valueOf(LocalDate.now()));
                auditoria.setAcao("Cadastro de solicitação (Espaço ID: " + idEspaco + ")");
                auditoriaDao.salvar(auditoria);

                response.sendRedirect("LoginServlet");

            } else if ("aceitar".equals(acao) && "gestor".equalsIgnoreCase(cargo)) {
                Long idSolicitacao = Long.parseLong(request.getParameter("idSolicitacao"));
                String justificativa = request.getParameter("justificativa");

                solicitacaoDao.aceitarSolicitacao(idSolicitacao);

                AvaliacaoModel avaliacao = new AvaliacaoModel();
                avaliacao.setIdGestorFk(usuario.getIdUsuario());
                avaliacao.setIdSolicitacaoFk(idSolicitacao);
                avaliacao.setJustificativa(justificativa);
                avaliacao.setStatus("APROVADO");
                avaliacao.setDataAvaliacao(LocalDate.now().toString());
                avaliacaoDao.salvar(avaliacao);

                // Auditoria
                AuditoriaModel auditoria = new AuditoriaModel();
                auditoria.setIdUsuarioFk(usuario.getIdUsuario());
                auditoria.setDataAcao(java.sql.Date.valueOf(LocalDate.now()));
                auditoria.setAcao("Avaliação: Aprovação de solicitação (ID: " + idSolicitacao + ")");
                auditoriaDao.salvar(auditoria);

                response.sendRedirect("LoginServlet");

            } else if ("rejeitar".equals(acao) && "gestor".equalsIgnoreCase(cargo)) {
                Long idSolicitacao = Long.parseLong(request.getParameter("idSolicitacao"));
                String justificativa = request.getParameter("justificativa");

                solicitacaoDao.rejeitarSolicitacao(idSolicitacao);

                AvaliacaoModel avaliacao = new AvaliacaoModel();
                avaliacao.setIdGestorFk(usuario.getIdUsuario());
                avaliacao.setIdSolicitacaoFk(idSolicitacao);
                avaliacao.setJustificativa(justificativa);
                avaliacao.setStatus("REJEITADO");
                avaliacao.setDataAvaliacao(LocalDate.now().toString());
                avaliacaoDao.salvar(avaliacao);

                // Auditoria
                AuditoriaModel auditoria = new AuditoriaModel();
                auditoria.setIdUsuarioFk(usuario.getIdUsuario());
                auditoria.setDataAcao(java.sql.Date.valueOf(LocalDate.now()));
                auditoria.setAcao("Avaliação: Rejeição de solicitação (ID: " + idSolicitacao + ")");
                auditoriaDao.salvar(auditoria);

                response.sendRedirect("LoginServlet");
            } else {
                response.sendRedirect("inde.jsp");
            }

        } catch (Exception e) {
            request.setAttribute("erro", "Erro: " + e.getMessage());
            request.getRequestDispatcher("inde.jsp").forward(request, response);
        }
    }
}
