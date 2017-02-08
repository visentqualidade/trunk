package br.com.visent.metricview.exception;

import br.com.visent.corporativo.exception.CorporativoException;

public class MetricViewException extends CorporativoException {

	private static final long serialVersionUID = 1L;

	public MetricViewException(String descricaoErro) {
		super(descricaoErro);
	}

	public MetricViewException(String descricaoErro, Exception e) {
		super(descricaoErro, e);
	}

}
