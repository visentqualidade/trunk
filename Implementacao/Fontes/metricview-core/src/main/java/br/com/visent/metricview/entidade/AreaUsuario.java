package br.com.visent.metricview.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.visent.corporativo.annotation.PermitidoAlterar;
import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "area_usuario")
@SequenceGenerator(name = "seq_area_usuario", sequenceName = "seq_area_usuario")
public class AreaUsuario implements Entidade {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_area_usuario", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	@PermitidoAlterar(alterar = false)
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@Override
	public String toString() {
		return "Area Usuario [id=" + getId() + ", descricao=" + descricao + "]";
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
