package model;

public class SolicitacaoModel {
	private Long idSolicitacao;
	private Long idUsuarioFk;
	private Long idEspacoFK;
	private String status;
	private String dataSolicitacao;
	private String dataReserva;
	private String horarioInicio;
	private String horarioFim;

	public Long getIdSolicitacao() {
		return idSolicitacao;
	}

	public void setIdSolicitacao(Long idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	public Long getIdUsuarioFk() {
		return idUsuarioFk;
	}

	public void setIdUsuarioFk(Long idUsuarioFk) {
		this.idUsuarioFk = idUsuarioFk;
	}

	public Long getIdEspacoFK() {
		return idEspacoFK;
	}

	public void setIdEspacoFK(Long idEspacoFK) {
		this.idEspacoFK = idEspacoFK;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(String dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public String getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(String dataReserva) {
		this.dataReserva = dataReserva;
	}

	public String getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(String horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public String getHorarioFim() {
		return horarioFim;
	}

	public void setHorarioFim(String horarioFim) {
		this.horarioFim = horarioFim;
	}
	
	

}
