package br.com.visent.metricview.entidade;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.visent.corporativo.annotation.PermitidoAlterar;
import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "grupo")
@SequenceGenerator(name = "seq_grupo", sequenceName = "seq_grupo")
public class Grupo implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_grupo", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_grupo")
	@PermitidoAlterar(alterar = false)
	private Long idGrupo;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@ManyToMany(mappedBy = "grupos")
	private Set<Usuario> usuarios;

	@OneToMany(mappedBy = "grupo",cascade = CascadeType.REMOVE, orphanRemoval=true)
	private Set<Permissao> permissoes;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
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
		Grupo other = (Grupo) obj;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Grupo [id=" + idGrupo + ", nome=" + nome + ", descricao=" + descricao + ", permissoes=" + permissoes + "]";
	}

	public Long getIdGrupo() {
		return idGrupo;
	}
	
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	public Long getId() {
		return idGrupo;
	}

	public void setId(Long id) {
		this.idGrupo = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Set<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
