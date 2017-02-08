package br.com.visent.metricview.entidade;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "CN")
public class CodigoNacional implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CN")
	private Long id;
	
	@Column(name = "NOME")
	private Long codigo;

	@ManyToMany(mappedBy = "codigoNacional")
	private Set<Regional> regionais;

	@OneToMany(mappedBy = "codigoNacional")
	private Set<BilhetadorCN> relBilhetadores;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_uf", referencedColumnName = "id_uf")
	private UnidadeFederativa uf;

	@Override
	public String toString() {
		return "CodigoNacional [id=" + id + ", codigo=" + codigo + ", relBilhetadores=" + relBilhetadores + "]";
	}

	public Long getCodigo() {
		return codigo;
	}

	public Long getId() {
		return id;
	}

	public Set<Regional> getRegionais() {
		return regionais;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setRegionais(Set<Regional> regionais) {
		this.regionais = regionais;
	}
	
	public Set<BilhetadorCN> getRelBilhetadores() {
		return relBilhetadores;
	}
	
	public void setRelBilhetadores(Set<BilhetadorCN> relBilhetadores) {
		this.relBilhetadores = relBilhetadores;
	}
	
	public UnidadeFederativa getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativa uf) {
		this.uf = uf;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		CodigoNacional other = (CodigoNacional) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
