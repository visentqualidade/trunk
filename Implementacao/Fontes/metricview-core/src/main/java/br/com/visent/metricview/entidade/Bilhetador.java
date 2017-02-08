package br.com.visent.metricview.entidade;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "bilhetador")
@SequenceGenerator(name = "seq_bilhetador", sequenceName = "seq_bilhetador")
public class Bilhetador implements Entidade, Comparable<Bilhetador> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_bilhetador", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_bilhetador")
	private Long id_bilhetador;

	@Column(name = "nome")
	private String nome;

	@OneToMany(mappedBy = "bilhetador", cascade=CascadeType.ALL , fetch=FetchType.LAZY)
	private Set<BilhetadorCN> relBilhetadores;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tecnologia", referencedColumnName = "id")
	private Tecnologia tecnologia;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bilhetador other = (Bilhetador) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bilhetador [id_bilhetador=" + id_bilhetador + ", nome=" + nome
				+ ", relBilhetadores=" + relBilhetadores + ", tecnologia="
				+ tecnologia + "]";
	}

	public Bilhetador() {}

	public Bilhetador(String nome, String tecnologia) {
		this.nome = nome;
		this.tecnologia = new Tecnologia();
		this.tecnologia.setNome(tecnologia);
	}
	
	public Bilhetador(String nome) {
		this.nome = nome;
	}
	
	public Long getId() {
		return id_bilhetador;
	}

	public void setId(Long id) {
		this.id_bilhetador = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<BilhetadorCN> getRelBilhetadores() {
		return relBilhetadores;
	}

	public void setRelBilhetadores(Set<BilhetadorCN> relBilhetadores) {
		this.relBilhetadores = relBilhetadores;
	}

	public Tecnologia getTecnologia() {
		return tecnologia;
	}

	public void setTecnologia(Tecnologia tecnologia) {
		this.tecnologia = tecnologia;
	}

	public int compareTo(Bilhetador obj) {
		return getNome().compareTo(obj.getNome());
	};
	
}
