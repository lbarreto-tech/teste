package model;

public class EmailModel {
	
	private Long idEmail;
	private String enderecoEmail;
	private Long idUsuarioFk;
	private String senha;
	
	
	public Long getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(Long idEmail) {
		this.idEmail = idEmail;
	}
	public String getEnderecoEmail() {
		return enderecoEmail;
	}
	public void setEnderecoEmail(String enderecoEmail) {
		this.enderecoEmail = enderecoEmail;
	}
	public Long getIdUsuarioFk() {
		return idUsuarioFk;
	}
	public void setIdUsuarioFk(Long idUsuarioFk) {
		this.idUsuarioFk = idUsuarioFk;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
