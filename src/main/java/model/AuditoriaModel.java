package model;

public class AuditoriaModel {
	
	private Long idAuditoria;
	private Long idUsuarioFk;
	private java.sql.Date dataAcao;
	private String acao;
	
	
	public Long getIdAuditoria() {
		return idAuditoria;
	}
	public void setIdAuditoria(Long idAuditoria) {
		this.idAuditoria = idAuditoria;
	}
	public Long getIdUsuarioFk() {
		return idUsuarioFk;
	}
	public void setIdUsuarioFk(Long idUsuarioFk) {
		this.idUsuarioFk = idUsuarioFk;
	}
	public java.sql.Date getDataAcao() {
		return dataAcao;
	}
	public void setDataAcao(java.sql.Date dataAcao) {
		this.dataAcao = dataAcao;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	
}
