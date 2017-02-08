package br.com.visent.metricview.to;

import java.util.Date;

public class LogsTO {

	private Date dataInicio;
	private Date dataFim;

	@Override
	public String toString() {
		return "LogsTO [dataInicio=" + dataInicio + ", dataFim=" + dataFim + "]";
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
