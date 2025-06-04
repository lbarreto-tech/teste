package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.SolicitacaoDao;
import model.SolicitacaoModel;

@WebServlet("/gestor")
public class SolicitacoesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            SolicitacaoDao solicitacao = new SolicitacaoDao();
            List<SolicitacaoModel> solicitacoes = solicitacao.listarTodos();
            if(solicitacoes.isEmpty()) {
            request.setAttribute("Erro", "Nenhuma tarefa cadastrada" );
            }
            request.setAttribute("lista", solicitacoes);
            request.getRequestDispatcher("telaPrincipal.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

