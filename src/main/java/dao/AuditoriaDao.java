package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.AuditoriaModel;

public class AuditoriaDao {
	
	private Connection connection;
	
	public AuditoriaDao() {
		connection = SingleConnection.getConnection();
	}
	
	
	public void salvar(AuditoriaModel model) {
		String sql = "insert into auditoria(idUsuarioFk,dataAcao,acao) values(?,?,?)";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setLong(1, model.getIdUsuarioFk());
			insert.setString(2, model.getDataAcao());
			insert.setString(3, model.getAcao());
			insert.executeUpdate();
			connection.commit();
			
			System.out.println("Salvou as informaões no banco");
		} catch (Exception e) {
			try {
				System.out.println("Alterações revertidas no banco");
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Não salvou as informaões no banco");
			e.printStackTrace();
		}
	}
	
	public List<AuditoriaModel> listarTodos(){
		List<AuditoriaModel> list = new ArrayList<>();
		
		String sql = "select * from auditoria";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				AuditoriaModel model = new AuditoriaModel();
				model.setIdAuditoria(resultado.getLong("idAuditoria"));
				model.setIdUsuarioFk(resultado.getLong("idUsuarioFk"));
				model.setDataAcao(resultado.getString("dataAcao"));
				model.setAcao(resultado.getString("acao"));
				
				list.add(model);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public AuditoriaModel listarPorId(Long id){
		AuditoriaModel model = new AuditoriaModel();
		
		String sql = "select * from auditoria where id = " + id;
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				model.setIdAuditoria(resultado.getLong("idAuditoria"));
				model.setIdUsuarioFk(resultado.getLong("idUsuarioFk"));
				model.setDataAcao(resultado.getString("dataAcao"));
				model.setAcao(resultado.getString("acao"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
	public void deletar(Long id) {
		String sql = "delete from auditoria where id = " + id;
		
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
