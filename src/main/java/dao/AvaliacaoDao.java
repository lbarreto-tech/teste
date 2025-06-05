package dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.AvaliacaoModel;

public class AvaliacaoDao {
	
	private Connection connection;
	
	public AvaliacaoDao() {
		connection = SingleConnection.getConnection();
	}
	
	
	public void salvar(AvaliacaoModel model) {
		String sql = "insert into avaliacao(idGestorFk,justificativa,idSolicitacaoFk,dataAvaliacao,status) values(?,?,?,?,?)";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setLong(1, model.getIdGestorFk());
			insert.setString(2, model.getJustificativa());
			insert.setLong(3, model.getIdSolicitacaoFk());
			if (model.getDataAvaliacaoSql() != null) {
				insert.setDate(4, model.getDataAvaliacaoSql());
			} else {
				insert.setString(4, model.getDataAvaliacao());
			}
			insert.setString(5, model.getStatus());
			insert.executeUpdate();
			connection.commit();
			
			System.out.println("Salvou as informações no banco");
		} catch (Exception e) {
			try {
				System.out.println("Alterações revertidas no banco");
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Não salvou as informações no banco");
			e.printStackTrace();
		}
	}
	
	public List<AvaliacaoModel> listarTodos(){
		List<AvaliacaoModel> list = new ArrayList<>();
		
		String sql = "select * from avaliacao";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				AvaliacaoModel model = new AvaliacaoModel();
				model.setIdAvaliacao(resultado.getLong("idAvaliacao"));
				model.setIdGestorFk(resultado.getLong("idGestorFk"));
				model.setJustificativa(resultado.getString("justificativa"));
				model.setIdSolicitacaoFk(resultado.getLong("idSolicitacaoFk"));
				model.setDataAvaliacao(resultado.getString("dataAvaliacao"));
				model.setStatus(resultado.getString("status"));

				list.add(model);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public AvaliacaoModel listarPorId(Long id) {
		AvaliacaoModel model = new AvaliacaoModel();
		
		String sql = "select * from avaliacao where id = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				model.setIdAvaliacao(resultado.getLong("idAvaliacao"));
				model.setIdGestorFk(resultado.getLong("idGestorFk"));
				model.setJustificativa(resultado.getString("justificativa"));
				model.setIdSolicitacaoFk(resultado.getLong("idSolicitacaoFk"));
				model.setDataAvaliacao(resultado.getString("dataAvaliacao"));
				model.setStatus(resultado.getString("status"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
	public void deletar(Long id) {
		String sql = "delete from avaliacao where id = " + id;
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			connection.commit();
			
			System.out.println("Deletado com sucesso");
		} catch (Exception e) {
			try {
				System.out.println("Alterações revertidas no banco");
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Não foi possivel deletar");
			e.printStackTrace();
		}
	}

	
}
