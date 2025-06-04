package model;

public class UsuarioModel {
	private Long idUsuario;
	private Long idCargoFK;
	private String nomeUsuario;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdCargoFK() {
		return idCargoFK;
	}

	public void setIdCargoFK(Long idCargoFK) {
		this.idCargoFK = idCargoFK;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

}
