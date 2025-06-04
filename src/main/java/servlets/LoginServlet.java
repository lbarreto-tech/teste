package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CargoDao;
import dao.CriarSolicitacaoDao;
import dao.SolicitacaoDao;
import model.SolicitacaoModel;


@WebServlet(urlPatterns = {"/LoginServlet", "/SolicitanteServlet"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	String email = "www";
	String password = "www";
	
    public LoginServlet() {
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 SolicitacaoDao solicitacao = new SolicitacaoDao();
         List<SolicitacaoModel> solicitacoes;
		try {
			solicitacoes = solicitacao.listarSolicitacoesPorUsuario(email, email);
			if(solicitacoes.isEmpty()) {
		         request.setAttribute("Erro", "Nenhuma solicitação cadastrada" );
		    }
			request.setAttribute("lista", solicitacoes);
	        request.getRequestDispatcher("telaSolicitante.jsp").forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
         
         
	}

	
	protected void doPostLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getServletPath();
		
		if(acao.equals("/LoginServlet")) {
			 email = request.getParameter("email");
			 password = request.getParameter("password");
			
			CargoDao dao = new CargoDao();
			String cargo = dao.verificaCargo(email, password);
			
				if(cargo.equals("GESTOR")) {
					RequestDispatcher redirecionar = request.getRequestDispatcher("telaPrincipal.jsp");
					redirecionar.forward(request, response);
				}else if(cargo.equals("SOLICITANTE")) {
					//Redireciona para a tela de usuario
				}else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
					redirecionar.forward(request, response);
				}
		}
	}
	
	protected void doPostSolicitante(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException {
		
		String acao = request.getServletPath();
		
		if(acao.equals("/SolicitanteServlet")) {
			
			String dataReserva = request.getParameter("data");
			String horarioInicio = request.getParameter("horarioInicio");
			String horarioFim = request.getParameter("horarioFim");
			String nomeEspaco = request.getParameter("sala");
			
			CriarSolicitacaoDao dao = new CriarSolicitacaoDao();
			dao.cadastrarSolicitacao(email, password, dataReserva, horarioInicio, horarioFim, nomeEspaco);
			dao.cadastraAuditoria(email, password);
		}
	}

	
}
