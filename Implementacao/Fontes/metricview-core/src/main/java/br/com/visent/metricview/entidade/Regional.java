package br.com.visent.metricview.entidade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "REGIONAL")
@SequenceGenerator(name = "seq_regional", sequenceName = "seq_regional")
public class Regional implements Entidade {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_regional", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_regional")
	@PermitidoAlterar(alterar=false)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "nome_relatorio")
	private String nomeRelatorio;
	
	@Type(type="true_false")
	@Column(name="PERMITE_ALTERACAO")
	private Boolean permiteAlteracao;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rel_regional_cod_nacional", 
			joinColumns = @JoinColumn(name = "id_regional", referencedColumnName = "id_regional"), 
			inverseJoinColumns = @JoinColumn(name = "id_cn", referencedColumnName = "id_cn"))
	private Set<CodigoNacional> codigoNacional;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="regional")
	private Set<Geografia> geografias;
	
	@Column(name="INDICADOR_SMP")
	@Type(type="true_false")
	private Boolean indicadorSmp;
	
	public String getUfsFormatados() {
		String ufFormatado = new String();
		if(getCodigoNacional() != null){
			Set<String> ufs = new HashSet<>();
			for (CodigoNacional cn : getCodigoNacional()) {
				ufs.add(cn.getUf().getSigla());
			}
			int i = 0;
			for (String uf : ufs) {
				if(i+1 == ufs.size()) {
					ufFormatado += uf;
				} else {
					ufFormatado += uf + ", ";
				}
				i++;
			}
		}
		return ufFormatado;
	}
	
	public String getCnsFormatados(){
		String cnRetorno = new String();
		if(getCodigoNacional() != null){
			List<CodigoNacional> lista = new ArrayList<>(getCodigoNacional());
			for (int i = 0; i < lista.size(); i++) {
				if(i+1 == lista.size()){
					cnRetorno += lista.get(i).getCodigo();
				} else {
					cnRetorno += lista.get(i).getCodigo() + ", ";
				}
			}
		}
		return cnRetorno;
	}
	
	@Override
	public String toString() {
		return "Regional [id=" + id + ", nome=" + nome + ", descricao="
				+ descricao + ", nomeRelatorio=" + nomeRelatorio
				+ ", permiteAlteracao=" + permiteAlteracao
				+ ", codigoNacional=" + codigoNacional + ", geografias="
				+ geografias + ", indicadorSmp=" + indicadorSmp + "]";
	}

	public Set<CodigoNacional> getCodigoNacional() {
		return codigoNacional;
	}

	public String getDescricao() {
		return descricao;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setCodigoNacional(Set<CodigoNacional> codigoNacional) {
		this.codigoNacional = codigoNacional;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNomeRelatorio() {
		return nomeRelatorio;
	}
	
	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public Boolean getPermiteAlteracao() {
		return permiteAlteracao;
	}

	public void setPermiteAlteracao(Boolean permiteAlteracao) {
		this.permiteAlteracao = permiteAlteracao;
	}

	public Set<Geografia> getGeografias() {
		return geografias;
	}

	public void setGeografias(Set<Geografia> geografias) {
		this.geografias = geografias;
	}

	public Boolean getIndicadorSmp() {
		return indicadorSmp;
	}

	public void setIndicadorSmp(Boolean indicadorSmp) {
		this.indicadorSmp = indicadorSmp;
	}

}
