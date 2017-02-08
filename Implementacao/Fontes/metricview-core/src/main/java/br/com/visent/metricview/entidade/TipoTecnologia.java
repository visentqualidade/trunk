package br.com.visent.metricview.entidade;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "tipo_tecnologia")
@SequenceGenerator(name = "seq_tipo_tecnologia", sequenceName = "seq_tipo_tecnologia")
public class TipoTecnologia implements Entidade{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_tipo_tecnologia", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_tipo_tecnologia")
	private Long id;
	
	@Column(name="descricao")
	private String descricao;

	@OneToMany(mappedBy="tipoTecnologia")
	private Set<Tecnologia> tecnologias;
	
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

	@Override
	public String toString() {
		return "TipoTecnologia [id=" + id + ", descricao=" + descricao + "]";
	}

	public Set<Tecnologia> getTecnologias() {
		return tecnologias;
	}
	
	public void setTecnologias(Set<Tecnologia> tecnologias) {
		this.tecnologias = tecnologias;
	}
	
}
