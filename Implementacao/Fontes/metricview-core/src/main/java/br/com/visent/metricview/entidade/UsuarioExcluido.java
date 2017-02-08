package br.com.visent.metricview.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name="usuario_excluido")
@SequenceGenerator(name = "seq_usuario_excluido", sequenceName = "seq_usuario_excluido")
public class UsuarioExcluido implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_usuario_excluido", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_usuario")
	private Long id;

	@Column(name = "login")
	private String login;

	@Column(name = "nome")
	private String nome;

	@Column(name = "senha")
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
	
	@Column(name = "admin")
	@Type(type = "true_false")
	private Boolean admin;
	
	@Column(name = "primeiro_acesso")
	@Type(type = "true_false")
	private Boolean primeiroAcesso;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_exclusao")
	private Date dataExclusao;
	
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
		UsuarioExcluido other = (UsuarioExcluido) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", nome=" + nome + ", senha=" + senha + ", email=" + email + ", telefone="
				+ telefone + ", area=" + area + ", regional=" + regional + ", responsavel=" + responsavel + ", admin=" + admin + ", primeiroAcesso=" + primeiroAcesso + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(Boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

}
