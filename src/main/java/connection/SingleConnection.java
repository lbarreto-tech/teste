package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

	private static final String url = "jdbc:postgresql://tramway.proxy.rlwy.net:17470/railway";
	private static final String user = "postgres";
	private static final String password = "CqidVmeJLgvgaswEktzasVbqaQHmxmUc";
	private static Connection connection;

	public SingleConnection(String url, String user, String password) {
		conectar(url, user, password);
	}

	public SingleConnection() {

		conectar();
	}

	static {
		conectar();
	}


	private static void conectar(String url, String user, String password) {
		try {
			if(connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
				System.out.println("Conectou ao banco com sucesso!");
			}
		}catch(Exception e) {
			System.out.println("Erro ao se conectar ao banco!");
			e.printStackTrace();
		}
	}

	private static void conectar() {
		try {
			if(connection == null) {
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
				System.out.println("Conectou ao banco com sucesso!");
			}
		}catch(Exception e) {
			System.out.println("Erro ao se conectar ao banco!");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}

}
