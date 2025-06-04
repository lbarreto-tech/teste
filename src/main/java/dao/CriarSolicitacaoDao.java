package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import connection.SingleConnection;

public class CriarSolicitacaoDao {
	
	private Connection connection;
	
	public CriarSolicitacaoDao() {
		connection = SingleConnection.getConnection();
	}
	
	
	public void cadastrarSolicitacao(String email, String senha, String dataReserva, String horarioInicio, String horarioFim, String nomeEspaco) throws ParseException, SQLException {
		
		Long idEspaco = getIdEspacoPorNome(nomeEspaco);
		Long idUsuario = getIdUsuarioPorEmailSenha(email, senha);

        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataReserva, formatoEntrada);
        DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataSolicitacao = data.format(formatoSaida);
        
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = dataAtual.format(formatter);
		
		String sql = "insert into solicitacao (idUsuarioFk, idEspacoFk, status, dataSolicitacao, dataReserva, horarioInicio, horarioFim) values (?, ?, DEFAULT, ?, ?, ?, ?)";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setLong(1,idUsuario);
			insert.setLong(2,idEspaco);
			insert.setString(3,dataFormatada);
			insert.setString(4,dataSolicitacao);
			insert.setString(5,horarioInicio);
			insert.setString(6,horarioFim);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private Long getIdEspacoPorNome(String nomeEspaco) throws SQLException {
		String sql = "select idEspaco from espaco where nome = " + nomeEspaco;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		statement.setString(1, nomeEspaco);
		
		return resultado.getLong("idEspaco");
	}
	
	public Long getIdUsuarioPorEmailSenha(String email, String senha) throws SQLException {
		 String sql = "select u.idUsuario from usuario u join email e on u.idUsuario = e.idUsuarioFk where e.enderecoEmail = " + email + " and e.senha = " + senha;
		 
			 PreparedStatement statement = connection.prepareStatement(sql);
			 ResultSet resultado = statement.executeQuery();
			 statement.setString(1, email);
			 statement.setString(2, senha); 
		
		 return resultado.getLong("idUsuario");
	}
	
	public void cadastraAuditoria(String email, String senha) throws SQLException, ParseException {
		
		Long idUsuario = getIdUsuarioPorEmailSenha(email, senha);
		
		LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = dataAtual.format(formatter);
		
		String sql = "insert into auditoria (idUsuarioFk,dataAcao,acao) values (?,?,?)";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setLong(1,idUsuario);
			insert.setString(2, dataFormatada);
			insert.setString(3,"Solicitação");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
