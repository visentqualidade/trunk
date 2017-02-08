package br.com.visent.metricview.ajax;

import br.com.visent.corporativo.ajax.GenericAjax;
import br.com.visent.corporativo.entidade.Entidade;
import br.com.visent.metricview.service.LogService;

public abstract class MetricViewAjax<E extends Entidade> extends GenericAjax<E>{
	
	protected LogService logService = new LogService();
	
	/**
	 * Metodo para gravar as acoes do usuario no banco
	 * @param msg Mensagem para ser gravada
	 * @param tipo Tipo de erro <strong> Tipos Possiveis INFO, ERROR </strong>
	 */
	protected void gravarLogArquivo (String msg , String tipo){
		logService.gravarLogArquivo(msg, tipo);
	}

}
