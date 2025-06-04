package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.EmailModel;

public class EmailDao {
	
	private Connection connection;
	
	public EmailDao() {
		connection = SingleConnection.getConnection();
	}
	
	
	public void salvar(EmailModel model) {
		String sql = "insert into email(enderecoEmail,idUsuarioFk,senha) values(?,?,?)";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, model.getEnderecoEmail());
			insert.setLong(2, model.getIdUsuarioFk());
			insert.setString(3, model.getSenha());
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
	
	public List<EmailModel> listarTodos(){
		List<EmailModel> list = new ArrayList<>();
		
		String sql = "select * from email";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				EmailModel model = new EmailModel();
				model.setIdEmail(resultado.getLong("idEmail"));
				model.setEnderecoEmail(resultado.getString("enderecoEmail"));
				model.setIdUsuarioFk(resultado.getLong("idUsuarioFk"));
				model.setSenha(resultado.getString("senha"));
				
				list.add(model);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public EmailModel listarPorId(Long id){
		EmailModel model = new EmailModel();
		
		String sql = "select * from email where id = " + id;
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				model.setIdEmail(resultado.getLong("idEmail"));
				model.setEnderecoEmail(resultado.getString("enderecoEmail"));
				model.setIdUsuarioFk(resultado.getLong("idUsuarioFk"));
				model.setSenha(resultado.getString("senha"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
	public void deletar(Long id) {
		String sql = "delete from email where id = " + id;
		
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
