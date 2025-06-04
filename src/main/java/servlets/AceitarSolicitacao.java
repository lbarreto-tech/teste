package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SolicitacaoDao;

@WebServlet("/aceitarSolicitacao")
public class AceitarSolicitacao extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			SolicitacaoDao solicitacao = new SolicitacaoDao();
			Long idSolicitacao = Long.parseLong(request.getParameter("idSolicitacao"));
			solicitacao.aceitarSolicitacao(idSolicitacao);	
			response.sendRedirect("telaPrincipal.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
