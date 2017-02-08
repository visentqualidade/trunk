package br.com.visent.metricview.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "ferramenta")
public class Ferramenta implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_ferramenta")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "url")
	private String url;

	@Column(name = "imagem")
	private String imagem;
	
	@Override
	public String toString() {
		return "Ferramenta [id=" + id + ", nome=" + nome + ", descricao="
				+ descricao + ", url=" + url + ", imagem=" + imagem + "]";
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

}
