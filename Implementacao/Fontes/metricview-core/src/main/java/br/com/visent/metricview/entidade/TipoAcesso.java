package br.com.visent.metricview.entidade;

import java.util.List;

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
@Table(name = "tipo_acesso")
@SequenceGenerator(name="seq_tipo_acesso",sequenceName="seq_tipo_acesso")
public class TipoAcesso implements Entidade{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="seq_tipo_acesso",strategy=GenerationType.SEQUENCE)
	@Column(name = "id_tipo_acesso")
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@OneToMany(mappedBy="tipoAcesso")
	private List<Funcionalidade> funcionalidades;
	
	@OneToMany(mappedBy="codigo")
	private List<Permissao> permissoes;
	
	@Override
	public String toString() {
		return "TipoAcesso [id=" + id + ", descricao=" + descricao + ", funcionalidades=" + funcionalidades + "]";
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

	public List<Funcionalidade> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<Funcionalidade> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
	
	public List<Permissao> getPermissoes() {
		return permissoes;
	}
	
	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

}
