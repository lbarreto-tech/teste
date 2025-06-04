package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.SolicitacaoModel;

public class SolicitacaoDao {
	private Connection connection;

	public SolicitacaoDao() {
		connection = SingleConnection.getConnection();
	}

	public void insert(SolicitacaoModel solicitacao) {
		String sql = "INSERT INTO solicitacao(idUsuarioFK, idEspacoFK, status, dataSolicitacao, dataReserva, horarioInicio, horarioFim) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, solicitacao.getIdUsuarioFk());
			ps.setLong(2, solicitacao.getIdEspacoFK());
			ps.setString(3, solicitacao.getStatus());
			ps.setString(4, solicitacao.getDataSolicitacao());
			ps.setString(5, solicitacao.getDataReserva());
			ps.setString(6, solicitacao.getHorarioInicio());
			ps.setString(7, solicitacao.getHorarioFim());
			ps.executeUpdate();
			connection.commit();
			System.out.println("As informações foram salvas");

		} catch (Exception e) {
			try {
				connection.rollback();
				System.out.println("Alterações revertidas no banco");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("As informações não foram salvas no banco");
			e.printStackTrace();
		}
	}

	public ArrayList<SolicitacaoModel> listarTodos() {
		ArrayList<SolicitacaoModel> list = new ArrayList<SolicitacaoModel>();
		String sql = "SELECT * FROM solicitacao";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SolicitacaoModel solicitacao = new SolicitacaoModel();
				solicitacao.setIdSolicitacao(rs.getLong("idSolicitacao"));
				solicitacao.setIdUsuarioFk(rs.getLong("idUsuarioFK"));
				solicitacao.setIdEspacoFK(rs.getLong("idEspacoFK"));
				solicitacao.setStatus(rs.getString("status"));
				solicitacao.setDataSolicitacao(rs.getString("dataSolicitacao"));
				solicitacao.setDataReserva(rs.getString("dataReserva"));
				solicitacao.setHorarioInicio(rs.getString("horarioInicio"));
				solicitacao.setHorarioFim(rs.getString("horarioFim"));
				list.add(solicitacao);
			}
		} catch (Exception e) {
			try {
				connection.rollback();
				System.out.println("Alterações revertidas no banco");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			System.out.println("As informações não foram salvas no banco");
			e.printStackTrace();
		}
		return list;

	}

	public SolicitacaoModel buscarPorId(Long idSolicitacao) {
		SolicitacaoModel solicitacao = new SolicitacaoModel();
		String sql = "SELECT * FROM solicitacao WHERE idSolicitacao = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, idSolicitacao);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				solicitacao.setIdUsuarioFk(rs.getLong("idUsuarioFK"));
				solicitacao.setIdEspacoFK(rs.getLong("idEspacoFK"));
				solicitacao.setStatus(rs.getString("status"));
				solicitacao.setDataSolicitacao(rs.getString("dataSolicitacao"));
				solicitacao.setDataReserva(rs.getString("dataReserva"));
				solicitacao.setHorarioInicio(rs.getString("horarioInicio"));
				solicitacao.setHorarioFim(rs.getString("horarioFim"));
			}
		} catch (Exception e) {
			try {
				connection.rollback();
				System.out.println("Alterações revertidas no banco");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			System.out.println("As informações não foram salvas no banco");
			e.printStackTrace();
		}

		return solicitacao;
	}

	public void delete(Long idSolicitacao) {
		String sql = "DELETE FROM solicitacao WHERE idSolicitacao = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, idSolicitacao);
			ps.execute();
			connection.commit();

			System.out.println("Usuario com o id" + idSolicitacao + "Deletado com sucesso");

		} catch (Exception e) {
			try {
				connection.rollback();
				System.out.println("Alterações revertidas no banco");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			System.out.println("As informações não foram salvas no banco");
			e.printStackTrace();
		}
	}
	
	public List<SolicitacaoModel> listarSolicitacoesPorUsuario(String email, String senha) throws SQLException{
		CriarSolicitacaoDao dao = new CriarSolicitacaoDao();
		Long idUsuario = dao.getIdUsuarioPorEmailSenha(email, senha);
		
		 String sql = "select idSolicitacao, idUsuarioFk, idEspacoFk, status, dataSolicitacao, dataReserva, horarioInicio, horarioFim from solicitacao where idUsuarioFk = " + idUsuario + " ORDER BY dataReserva DESC";
		 
		 List<SolicitacaoModel> solicitacoes = new ArrayList<>();
		 
		 try {
			 PreparedStatement statement = connection.prepareStatement(sql);
			 ResultSet resultado = statement.executeQuery();
			 
			 while (resultado.next()) {
	                SolicitacaoModel s = new SolicitacaoModel();
	                s.setIdSolicitacao(resultado.getLong("idSolicitacao"));
	                s.setIdUsuarioFk(resultado.getLong("idUsuarioFk"));
	                s.setIdEspacoFK(resultado.getLong("idEspacoFk"));
	                s.setStatus(resultado.getString("status"));
	                s.setDataSolicitacao(resultado.getString("dataSolicitacao"));
	                s.setDataReserva(resultado.getString("dataReserva"));
	                s.setHorarioInicio(resultado.getString("horarioInicio"));
	                s.setHorarioFim(resultado.getString("horarioFim"));
	                solicitacoes.add(s);
	            }
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return solicitacoes;
	
	}

	public void aceitarSolicitacao(Long idSolicitacao) {
		String sql = "UPDATE sc.status FROM solicitacao sc SET sc.status = ? WHERE sc.idSolicitacao = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, "APROVADA"); 
			ps.setLong(2, idSolicitacao);
			ps.executeUpdate();
			connection.commit();
		}catch(Exception e) {
			try {
				connection.rollback();
				System.out.println("Alterações revertidas no banco");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			System.out.println("As informações não foram salvas no banco");
			e.printStackTrace();
		}
	}
}
