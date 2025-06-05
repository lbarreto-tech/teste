package servlets;

import dao.SolicitacaoDao;
import model.SolicitacaoModel;
import model.UsuarioModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/GestorServlet")
public class GestorServlet extends HttpServlet {
    private SolicitacaoDao solicitacaoDao;

    @Override
    public void init() {
        solicitacaoDao = new SolicitacaoDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario");


        if (usuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<SolicitacaoModel> lista = solicitacaoDao.listarTodos();
        request.setAttribute("lista", lista);
        request.getRequestDispatcher("principal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        Long idSolicitacao = Long.parseLong(request.getParameter("idSolicitacao"));

        if (acao != null && acao.equals("aceitar")) {
            solicitacaoDao.aceitarSolicitacao(idSolicitacao);
        } else if (acao != null && acao.equals("recusar")) {
            solicitacaoDao.rejeitarSolicitacao(idSolicitacao);
        }

        response.sendRedirect("GestorServlet");
    }
}
