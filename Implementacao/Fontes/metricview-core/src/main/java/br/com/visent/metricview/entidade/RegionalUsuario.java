package br.com.visent.metricview.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name="regional_usuario")
public class RegionalUsuario implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_regional_usuario")
	private Long id;

	@Column(name="descricao")
	private String descricao;
	
	@Override
	public String toString() {
		return "RegionalUsuario [id=" + id + ", descricao=" + descricao + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
