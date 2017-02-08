package br.com.visent.metricview.entidade;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.visent.componente.util.DataUtil;
import br.com.visent.corporativo.entidade.Entidade;


@Entity
@Table(name = "rel_bilhetador_cn")
@SequenceGenerator(name = "seq_rel_bilhetador_cn", sequenceName = "seq_rel_bilhetador_cn")
public class BilhetadorCN implements Entidade{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "seq_rel_bilhetador_cn", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "data")
	private Date data;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_bilhetador", referencedColumnName = "id_bilhetador")
	private Bilhetador bilhetador;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_cn", referencedColumnName = "id_cn")
	private CodigoNacional codigoNacional;
	
	public BilhetadorCN(CodigoNacional codigoNacional, String data, Bilhetador bil) {
		setCodigoNacional(codigoNacional);
		setData(DataUtil.parse(data, new SimpleDateFormat("yyyyMMdd hhmm")));
		setBilhetador(bil);
	}
	
	public BilhetadorCN() {}

	@Override
	public String toString() {
		return "BilhetadorCN [id=" + id + ", data=" + data + "]";
	}

	public String getDataFormatada(){
		String dataRetorno = null;
		if(getData() != null){
			dataRetorno = DataUtil.sdf_DDMMYYYY.format(getData());
		}
		return dataRetorno;
	}
	
	public Long getId() {
		return id;
	}
	
	public Bilhetador getBilhetador() {
		return bilhetador;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setBilhetador(Bilhetador bilhetador) {
		this.bilhetador = bilhetador;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public CodigoNacional getCodigoNacional() {
		return codigoNacional;
	}
	
	public void setCodigoNacional(CodigoNacional codigoNacional) {
		this.codigoNacional = codigoNacional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bilhetador == null) ? 0 : bilhetador.hashCode());
		result = prime * result
				+ ((codigoNacional == null) ? 0 : codigoNacional.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		BilhetadorCN other = (BilhetadorCN) obj;
		if (bilhetador == null) {
			if (other.bilhetador != null)
				return false;
		} else if (!bilhetador.equals(other.bilhetador))
			return false;
		if (codigoNacional == null) {
			if (other.codigoNacional != null)
				return false;
		} else if (!codigoNacional.equals(other.codigoNacional))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
	
}
