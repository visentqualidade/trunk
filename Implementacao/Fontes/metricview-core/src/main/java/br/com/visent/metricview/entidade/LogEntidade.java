package br.com.visent.metricview.entidade;

import java.text.SimpleDateFormat;
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

import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.DataUtil;
import br.com.visent.corporativo.entidade.Entidade;

@Entity
@Table(name = "log")
@SequenceGenerator(name = "seq_log", sequenceName = "seq_log")
public class LogEntidade implements Entidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_log", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_log")
	private Long id;

	@Column(name = "mensagem")
	private String mensagem;

	@Column(name = "tipo")
	private String tipo;

	@Column(name="usuario")
	private String nomeUsuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_hora")
	private Date dataHora;
	
	@Column(name="projeto_solucao")
	private String projetoSolucao;
	
	public String getDataHoraFormatada(){
		String dataHoraFormatada = null;
        if(getDataHora() != null){
			dataHoraFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(getDataHora());
		}
		return dataHoraFormatada;
	}

	@Override
	public String toString() {
		return "LogEntidade [id=" + id + ", mensagem=" + mensagem + ", tipo=" + tipo + ", nomeUsuario=" + nomeUsuario + ", dataHora="
				+ dataHora + ", projetoSolucao=" + projetoSolucao + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String toStringArquivo() {
		return this.mensagem+";"+this.tipo+";"+this.nomeUsuario+";"+DataUtil.formataData(this.dataHora, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"))+";"+this.projetoSolucao;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getProjetoSolucao() {
		return projetoSolucao;
	}

	public void setProjetoSolucao(String projetoSolucao) {
		this.projetoSolucao = projetoSolucao;
	}

}
