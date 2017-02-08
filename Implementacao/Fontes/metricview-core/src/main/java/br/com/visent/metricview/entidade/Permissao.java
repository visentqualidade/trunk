package br.com.visent.metricview.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "permissao")
@SequenceGenerator(name = "seq_permissao", sequenceName = "seq_permissao")
public class Permissao implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_permissao", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_permissao")
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
	private Usuario usuario;

	@OneToOne
	@JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")
	private Grupo grupo;

	@OneToOne
	@JoinColumn(name = "id_ferramenta", referencedColumnName = "id_ferramenta")
	private Ferramenta ferramenta;
	
	@Override
	public String toString() {
		return "Permissao [id=" + id + ", ferramenta=" + ferramenta + ", codigo=" + codigo + "]";
	}

	@ManyToOne
	@JoinColumn(name = "codigo", referencedColumnName = "id_tipo_acesso")
	private TipoAcesso codigo;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Permissao other = (Permissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ferramenta getFerramenta() {
		return ferramenta;
	}

	public void setFerramenta(Ferramenta ferramenta) {
		this.ferramenta = ferramenta;
	}

	public TipoAcesso getCodigo() {
		return codigo;
	}
	
	public void setCodigo(TipoAcesso codigo) {
		this.codigo = codigo;
	}
	
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuarios) {
		this.usuario = usuarios;
	}

}
