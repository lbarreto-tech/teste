package model;

public class AuditoriaModel {
	
	private Long idAuditoria;
	private Long idUsuarioFk;
	private String dataAcao;
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
	public String getDataAcao() {
		return dataAcao;
	}
	public void setDataAcao(String dataAcao) {
		this.dataAcao = dataAcao;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	
}
