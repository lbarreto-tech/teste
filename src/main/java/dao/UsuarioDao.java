package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.SingleConnection;
import model.UsuarioModel;

public class UsuarioDao {
	private Connection connection;

	public UsuarioDao() {
		connection = SingleConnection.getConnection();
	}

	public void insert(UsuarioModel usuario) {
		String sql = "INSERT INTO usuario(idCargoFK, nomeUsuario) VALUES (?, ?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, usuario.getIdCargoFK());
			ps.setString(2, usuario.getNomeUsuario());
			ps.executeUpdate();
			connection.commit();
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

	public ArrayList<UsuarioModel> listarTodos() {
		ArrayList<UsuarioModel> lista = new ArrayList<UsuarioModel>();
		String sql = "SELECT * FROM Usuario";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UsuarioModel usuario = new UsuarioModel();
				usuario.setIdUsuario(rs.getLong("idUsuario"));
				usuario.setIdCargoFK(rs.getLong("idCargoFK"));
				usuario.setNomeUsuario(rs.getString("nomeUsuario"));
				lista.add(usuario);
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
	return lista;

	}

	public UsuarioModel BuscarPorId(Long idUsuario) {
		String sql = "SELECT * FROM USUARIO WHERE idUsuario = ?";
		UsuarioModel usuario = new UsuarioModel();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				usuario.setIdUsuario(rs.getLong("idUsuario"));
				usuario.setIdCargoFK(rs.getLong("idCargoFK"));
				usuario.setNomeUsuario(rs.getString("NomeUsuario"));
			}else {
				return null;
			}
			
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
		return usuario;
}

		public void delete(Long idUsuario) {
			String sql = "DELETE FROM usuario WHERE idUsuario = ?";
			try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, idUsuario);
			ps.executeUpdate();
			}catch(Exception e ) {
				try {
					connection.rollback();
					System.out.println("Alterações revertidas no banco");
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				System.out.println("As informações não foram salvas no banco");
				e.printStackTrace();
		}
}}
