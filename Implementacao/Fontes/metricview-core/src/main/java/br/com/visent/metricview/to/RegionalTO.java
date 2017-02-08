package br.com.visent.metricview.to;


public class RegionalTO {

	private Long id;
	private String nome;
	private String descricao;
	private String cns;
	private String ufs;
	
	@Override
	public String toString() {
		return "RegionalTO [id=" + id + ", nome=" + nome + ", descricao="
				+ descricao + ", cns=" + cns + ", ufs=" + ufs + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCns() {
		return cns;
	}

	public void setCns(String cns) {
		this.cns = cns;
	}

	public String getUfs() {
		return ufs;
	}

	public void setUfs(String ufs) {
		this.ufs = ufs;
	}

}
