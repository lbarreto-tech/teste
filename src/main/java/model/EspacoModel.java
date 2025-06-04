package model;

public class EspacoModel {
	private Long idEspaco;
	private String nome;
	private String numero;
	private Long idTipoEspacoFK;
	private Double metragem;

    public Long getIdEspaco() {
		return idEspaco;
	}

	public void setIdEspaco(Long idEspaco) {
		this.idEspaco = idEspaco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Long getIdTipoEspacoFK() {
		return idTipoEspacoFK;
	}

	public void setIdTipoEspacoFK(Long idTipoEspacoFK) {
		this.idTipoEspacoFK = idTipoEspacoFK;
	}

	public Double getMetragem() {
		return metragem;
	}

	public void setMetragem(Double metragem) {
		this.metragem = metragem;
	}


}
