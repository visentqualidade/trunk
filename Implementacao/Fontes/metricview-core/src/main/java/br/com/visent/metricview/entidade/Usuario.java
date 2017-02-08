package br.com.visent.metricview.entidade;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import br.com.visent.corporativo.annotation.PermitidoAlterar;
import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario")
public class Usuario implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_usuario", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_usuario")
	@PermitidoAlterar(alterar = false)
	private Long idUsuario;

	@Column(name = "login")
	@PermitidoAlterar(alterar = false)
	private String login;

	@Column(name = "nome")
	private String nome;

	@Column(name = "senha",nullable=false)
	private String senha;

	@Column(name = "email")
	private String email;
	
	@Column(name="telefone")
	private String telefone;
	
	@Column(name="area")
	private String area;
	
	@Column(name="regional")
	private String regional;
	
	@Column(name="responsavel")
	private String responsavel;
	
	@OneToMany(mappedBy = "usuario",orphanRemoval=true)
	private Set<Permissao> permissoes;

	@ManyToMany
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"),
				inverseJoinColumns = @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo"))
	private Set<Grupo> grupos;

	@Column(name = "admin")
	@Type(type = "true_false")
	private Boolean admin;
	
	@Column(name = "primeiro_acesso")
	@Type(type = "true_false")
	private Boolean primeiroAcesso;
	
	public String getNomeFormatado(){
		String nomeFormatado = null;
		if(getNome() != null){
			if(getNome().length() > 16){
				nomeFormatado = getNome().substring(0 , 16) + ". (" + ""+getLogin()+")";
			} else {
				nomeFormatado = getNome() + " (" + ""+getLogin()+")";
			}
		}
		return nomeFormatado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		Usuario other = (Usuario) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + idUsuario + ", login=" + login + ", nome=" + nome + ", senha=" + senha + ", email=" + email + ", telefone="
				+ telefone + ", area=" + area + ", regional=" + regional + ", responsavel=" + responsavel + ", permissoes=" + permissoes
				+", admin=" + admin + ", primeiroAcesso=" + primeiroAcesso + "]";
	}

	public Long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Long getId() {
		return idUsuario;
	}

	public void setId(Long id) {
		this.idUsuario = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRegional() {
		return regional;
	}

	public void setRegional(String regional) {
		this.regional = regional;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupo) {
		this.grupos = grupo;
	}

	public Boolean getPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(Boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}

}