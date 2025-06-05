package model;

public class AvaliacaoModel {
	
	private Long idAvaliacao;
	private Long idGestorFk;
	private String justificativa;
	private Long idSolicitacaoFk;
	private String dataAvaliacao;
	private java.sql.Date dataAvaliacaoSql;
	private String status;
	
	
	public Long getIdAvaliacao() {
		return idAvaliacao;
	}
	public void setIdAvaliacao(Long idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}
	public Long getIdGestorFk() {
		return idGestorFk;
	}
	public void setIdGestorFk(Long idGestorFk) {
		this.idGestorFk = idGestorFk;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public Long getIdSolicitacaoFk() {
		return idSolicitacaoFk;
	}
	public void setIdSolicitacaoFk(Long idSolicitacaoFk) {
		this.idSolicitacaoFk = idSolicitacaoFk;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDataAvaliacao() {
		return dataAvaliacao;
	}
	public void setDataAvaliacao(String dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
		try {
			// Aceita formatos yyyy-MM-dd ou dd/MM/yyyy
			if (dataAvaliacao != null && dataAvaliacao.contains("/")) {
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
				java.util.Date utilDate = sdf.parse(dataAvaliacao);
				this.dataAvaliacaoSql = new java.sql.Date(utilDate.getTime());
			} else if (dataAvaliacao != null && dataAvaliacao.contains("-")) {
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				java.util.Date utilDate = sdf.parse(dataAvaliacao);
				this.dataAvaliacaoSql = new java.sql.Date(utilDate.getTime());
			} else {
				this.dataAvaliacaoSql = null;
			}
		} catch (Exception e) {
			this.dataAvaliacaoSql = null;
		}
	}
	public java.sql.Date getDataAvaliacaoSql() {
		return dataAvaliacaoSql;
	}

}
