package servlets;

import dao.CargoDao;
import dao.UsuarioDao;
import dao.CriarSolicitacaoDao;
import dao.EspacoDao;
import dao.SolicitacaoDao;
import model.UsuarioModel;
import model.EspacoModel;
import model.SolicitacaoModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("password");

        CriarSolicitacaoDao criarSolicitacaoDao = new CriarSolicitacaoDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        CargoDao cargoDao = new CargoDao();
        SolicitacaoDao solicitacaoDao = new SolicitacaoDao();
        EspacoDao espacoDao = new EspacoDao();

        try {
            Long idUser = criarSolicitacaoDao.getIdUsuarioPorEmailSenha(email, senha);
            UsuarioModel usuario = usuarioDao.BuscarPorId(idUser);
            String cargo = cargoDao.verificaCargo(email, senha);

            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("email", email);
            session.setAttribute("senha", senha);
            session.setAttribute("cargo", cargo);

            if ("gestor".equalsIgnoreCase(cargo)) {
                List<SolicitacaoModel> lista = solicitacaoDao.listarTodos();
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("telaPrincipal.jsp").forward(request, response);

            } else if ("solicitante".equalsIgnoreCase(cargo)) {
                List<SolicitacaoModel> lista = solicitacaoDao.listarSolicitacoesPorUsuario(email, senha);
                List<EspacoModel> espacos = espacoDao.listar();
                request.setAttribute("lista", lista);
                request.setAttribute("espacos", espacos);
                request.getRequestDispatcher("telaSolicitante.jsp").forward(request, response);
            } else {
                request.setAttribute("erro", "Cargo desconhecido");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("erro", "Email ou senha inválidos!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
