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

	public void cadastrarSolicitacao(String email, String senha, String dataReserva, String horarioInicio,
			String horarioFim, String nomeEspaco) throws ParseException, SQLException {

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
			insert.setLong(1, idUsuario);
			insert.setLong(2, idEspaco);
			insert.setString(3, dataFormatada);
			insert.setString(4, dataSolicitacao);
			insert.setString(5, horarioInicio);
			insert.setString(6, horarioFim);
			insert.executeUpdate(); 
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}

	}

	private Long getIdEspacoPorNome(String nomeEspaco) throws SQLException {
		String sql = "select idEspaco from espaco where nome = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, nomeEspaco);
		ResultSet resultado = statement.executeQuery();
		if (resultado.next()) {
			return resultado.getLong("idEspaco");
		} else {
			throw new SQLException("Espaço não encontrado: " + nomeEspaco);
		}
	}

	public Long getIdUsuarioPorEmailSenha(String email, String senha) throws SQLException {
		String sql = "select u.idUsuario from usuario u join email e on u.idUsuario = e.idUsuarioFk where e.enderecoEmail = ? and e.senha = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, senha);
		ResultSet resultado = statement.executeQuery();
		if (resultado.next()) {
			return resultado.getLong("idUsuario");
		} else {
			throw new SQLException("Usuário não encontrado para o email e senha informados.");
		}
	}

	public void cadastraAuditoria(String email, String senha) throws SQLException, ParseException {

		Long idUsuario = getIdUsuarioPorEmailSenha(email, senha);

		LocalDate dataAtual = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dataFormatada = dataAtual.format(formatter);

		String sql = "insert into auditoria (idUsuarioFk,dataAcao,acao) values (?,?,?)";

		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setLong(1, idUsuario);
			insert.setString(2, dataFormatada);
			insert.setString(3, "Solicitação");
			insert.executeUpdate(); 
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

	public void cadastrarSolicitacaoPorId(String email, String senha, String dataReserva, String horarioInicio,
			String horarioFim, Long idEspaco) throws ParseException, SQLException {
		Long idUsuario = getIdUsuarioPorEmailSenha(email, senha);
		// Converte a string para java.sql.Date
		java.sql.Date dataReservaSql = null;
		try {
			java.util.Date utilDate = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(dataReserva);
			dataReservaSql = new java.sql.Date(utilDate.getTime());
		} catch (ParseException e) {
			throw new SQLException("Data em formato inválido. Use dd/MM/yyyy");
		}
		// Converte horários para java.sql.Time
		java.sql.Time horarioInicioSql = null;
		java.sql.Time horarioFimSql = null;
		try {
			java.util.Date utilInicio = new java.text.SimpleDateFormat("HH:mm").parse(horarioInicio);
			java.util.Date utilFim = new java.text.SimpleDateFormat("HH:mm").parse(horarioFim);
			horarioInicioSql = new java.sql.Time(utilInicio.getTime());
			horarioFimSql = new java.sql.Time(utilFim.getTime());
		} catch (ParseException e) {
			throw new SQLException("Horário em formato inválido. Use HH:mm");
		}
		String sql = "insert into solicitacao (idUsuarioFk, idEspacoFk, status, dataSolicitacao, dataReserva, horarioInicio, horarioFim) values (?, ?, ?, CURRENT_DATE, ?, ?, ?)";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setLong(1, idUsuario);
			insert.setLong(2, idEspaco);
			insert.setString(3, "PENDENTE"); 
			insert.setDate(4, dataReservaSql);
			insert.setTime(5, horarioInicioSql);
			insert.setTime(6, horarioFimSql);
			insert.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

}
