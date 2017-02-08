package br.com.visent.metricview.entidade;

import java.util.List;

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
@Table(name = "UF")
@SequenceGenerator(name = "seq_unid_federativa", sequenceName = "seq_unid_federativa")
public class UnidadeFederativa implements Entidade{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "seq_unid_federativa", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_uf")
	private Long id;
	
	@Column(name = "nome_extenso")
	private String nome;
	
	@Column(name = "nome")
	private String sigla;
	
	@OneToMany(mappedBy="uf")
	private List<CodigoNacional> codigoNacionais;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_geografia",referencedColumnName="id_geografia")
	private Geografia geografia;
	
	@Override
	public String toString() {
		return "UnidadeFederativa [id=" + id + ", nome=" + nome + ", sigla=" + sigla + ", codigoNacionais=" + codigoNacionais + "]";
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
	
	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public List<CodigoNacional> getCodigoNacionais() {
		return codigoNacionais;
	}

	public void setCodigoNacionais(List<CodigoNacional> codigoNacionais) {
		this.codigoNacionais = codigoNacionais;
	}

	public Geografia getGeografia() {
		return geografia;
	}

	public void setGeografia(Geografia geografia) {
		this.geografia = geografia;
	}
	
}
