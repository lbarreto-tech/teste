package connection;

public class TesteConexao {
	public static void main(String[] args) {
		
		String url = "jdbc:postgresql://tramway.proxy.rlwy.net:17470/";
		String user = "postgres";
		String password = "CqidVmeJLgvgaswEktzasVbqaQHmxmUc";
		
		SingleConnection connection = new SingleConnection(url, user, password);
		
	}
}
