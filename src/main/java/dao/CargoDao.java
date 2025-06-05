package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.CargoModel;

public class CargoDao {
	
	private Connection connection;
	
	public CargoDao() {
		connection = SingleConnection.getConnection();
	}
	
	
	public void salvar(CargoModel model) {
		String sql = "insert into cargo(nomeCargo) values(?)";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, model.getNomeCargo());
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
	
	public List<CargoModel> listarTodos(){
		List<CargoModel> list = new ArrayList<>();
		
		String sql = "select * from cargo";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				CargoModel model = new CargoModel();
				model.setIdCargo(resultado.getLong("idCargo"));
				model.setNomeCargo(resultado.getString("nomeCargo"));
				
				list.add(model);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public CargoModel listarPorId(Long id){
		CargoModel model = new CargoModel();
		String sql = "select * from cargo where idCargo = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultado = statement.executeQuery();
			
			if(resultado.next()) {
				model.setIdCargo(resultado.getLong("idCargo"));
				model.setNomeCargo(resultado.getString("nomeCargo"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
	public String verificaCargo(String email, String password) {
		CargoModel model = new CargoModel();
		String retorno = "www";
		
		String sql = "select c.nomeCargo from email e ";
			   sql+= " join usuario u on e.idUsuarioFk = u.idUsuario ";
			   sql+= " join cargo c on u.idCargoFk = c.idCargo ";
			   sql+= "where e.enderecoEmail = ? and e.senha = ?";
			   
			   try {
				   PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, email);
					statement.setString(2, password);
					ResultSet resultado = statement.executeQuery();
					while(resultado.next()) {
						model.setNomeCargo(resultado.getString("nomeCargo"));
						retorno = model.getNomeCargo();
					}
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return retorno;
	} 
	
	public void deletar(Long id) {
		String sql = "delete from cargo where idCargo = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
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
