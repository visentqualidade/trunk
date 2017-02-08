package br.com.visent.metricview.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.visent.corporativo.annotation.PermitidoAlterar;
import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "REGIAO")
public class Regiao implements Entidade {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_regiao")
	@PermitidoAlterar(alterar=false)
	private Long id;

	@Column(name = "nome")
	private String nome;

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

	@Override
	public String toString() {
		return "Regiao [id=" + id + ", nome=" + nome + "]";
	}

}
