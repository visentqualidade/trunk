package br.com.visent.metricview.entidade;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "tecnologia")
@SequenceGenerator(name = "seq_tecnologia", sequenceName = "seq_tecnologia")
public class Tecnologia implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_tecnologia", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@ManyToOne
	@JoinColumn(name="id_tipo_tecnologia",referencedColumnName="id_tipo_tecnologia")
	private TipoTecnologia tipoTecnologia;

	@OneToMany(mappedBy = "tecnologia", cascade=CascadeType.ALL)
	private Set<Bilhetador> bilhetadores;
	
	@Override
	public String toString() {
		return "Tecnologia [id=" + id + ", nome=" + nome + ", tipoTecnologia=" + tipoTecnologia + "]";
	}

	public Tecnologia() {}

	public Tecnologia(String nome, String tipoTecnologia) {
		this.nome = nome;
		this.tipoTecnologia = new TipoTecnologia();
		this.tipoTecnologia.setDescricao(tipoTecnologia);
	}

	public Object getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoTecnologia getTipoTecnologia() {
		return tipoTecnologia;
	}

	public void setTipoTecnologia(TipoTecnologia tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}
	
	public Set<Bilhetador> getBilhetadores() {
		return bilhetadores;
	}
	
	public void setBilhetadores(Set<Bilhetador> bilhetadores) {
		this.bilhetadores = bilhetadores;
	}

}
