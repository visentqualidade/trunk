package br.com.visent.metricview.ajax;

import java.util.List;

import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.metricview.entidade.Permissao;
import br.com.visent.metricview.util.ConstantesUtil;

public class PermissaoAjax extends MetricViewAjax<Permissao> {
	
	public void salvarPermissoes(List<Permissao> permissoes) {
		for (Permissao permissao : permissoes) {
			inserirEntidade(permissao);
		}
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_ALTEROU_DADOS_PERMISSAO), Log.INFO);
	}

}
