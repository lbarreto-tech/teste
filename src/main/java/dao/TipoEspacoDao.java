package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.SingleConnection;
import model.TipoEspacoModel;

public class TipoEspacoDao {
	private Connection connection;

	public TipoEspacoDao() {
		connection = SingleConnection.getConnection();
	}

	public void inserir(TipoEspacoModel tipoEspaco) {
		String sql = "INSERT INTO tipoEspaco(tipoEspaco) VALUES (?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, tipoEspaco.getTipoEspaco());
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

	public ArrayList<TipoEspacoModel> listarTodos() {
		ArrayList<TipoEspacoModel> lista = new ArrayList<TipoEspacoModel>();
		String sql = "SELECT * FROM tipoEspaco";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TipoEspacoModel tipoEspaco = new TipoEspacoModel();
				tipoEspaco.setIdTipoEspaco(rs.getLong("idTipoEspaco"));
				tipoEspaco.setTipoEspaco(rs.getString("tipoEspaco"));
				lista.add(tipoEspaco);
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

	public TipoEspacoModel buscaPorId(Long idTipoEspaco) {
		String sql = "SELECT FROM tipoEspaco WHERE idTipoEspaco = ?";
		TipoEspacoModel tipoEspaco = new TipoEspacoModel();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, idTipoEspaco);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				tipoEspaco.setIdTipoEspaco(rs.getLong("idTipoEspaco"));
				tipoEspaco.setTipoEspaco(rs.getString("tipoEspaco"));
			} else {
				return null;
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
		return tipoEspaco;
	}

	public void deletar(Long idTipoEspaco) {
		String sql = "DELETE FROM tipoEspaco WHERE idTipoEspaco = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, idTipoEspaco);
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

}
