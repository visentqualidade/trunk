package br.com.visent.metricview.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "BANDA")
public class Banda implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_banda")
	private Long id;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "TECNOLOGIA")
		private String tecnologia;
	
	@Override
	public String toString( ) {
		return "Banda [id=" + id + ", descricao=" + descricao + ", tecnologia =" + tecnologia + "]";
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
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
		Banda other = (Banda) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equalsIgnoreCase(other.descricao))
			return false;
		return true;
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

	public String getTecnologia() {
		return tecnologia;
	}

	public void setTecnologia(String tecnologia) {
		this.tecnologia = tecnologia;
	}

}
