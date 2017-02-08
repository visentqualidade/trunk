package br.com.visent.metricview.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "geografia")
public class Geografia implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_geografia")
	private Long id;

	@Column(name="nome")
	private String nome;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_regional",referencedColumnName="id_regional")
	private Regional regional;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_regiao",referencedColumnName="id_regiao")
	private Regiao regiao;
	
	@Override
	public String toString() {
		return "Geografia [id=" + id + ", nome=" + nome + ", regional=" + regional + "]";
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

	public Regional getRegional() {
		return regional;
	}

	public void setRegional(Regional regional) {
		this.regional = regional;
	}

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

}
