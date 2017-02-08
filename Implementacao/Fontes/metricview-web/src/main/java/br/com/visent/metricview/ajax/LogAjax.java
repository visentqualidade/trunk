package br.com.visent.metricview.ajax;

import java.util.ArrayList;
import java.util.List;

import br.com.visent.componente.util.DataUtil;
import br.com.visent.metricview.constantes.ConstantesMetricView;
import br.com.visent.metricview.dao.LogDao;
import br.com.visent.metricview.entidade.LogEntidade;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.exception.MetricViewException;
import br.com.visent.metricview.to.LogsTO;

public class LogAjax extends MetricViewAjax<LogEntidade>{
	
	private LogDao logDao = new LogDao();

	/**
	 * Metodo para buscar as logs baseadas nos filtros selecionados pelo usuario
	 * @param filtro Entidade contendo as informacoes preenchidas pelo usuario
	 * @return Lista Contendo os dados para serem apresentados
	 * @throws MetricViewException Caso o periodo da consulta tenha ultrapassado {@link ConstantesMetricView} QTD_MAX_DIAS_PESQUISA_FILTRO
	 */
	public List<LogEntidade> buscarPorFiltroTO(LogsTO filtroTO) throws MetricViewException{
		if(DataUtil.diferencaEntreDatas(filtroTO.getDataInicio(), filtroTO.getDataFim()) > ConstantesMetricView.QTD_MAX_DIAS_PESQUISA_FILTRO){
			filtroTO.setDataInicio(DataUtil.subtrairDiaData(filtroTO.getDataFim() , ConstantesMetricView.QTD_MAX_DIAS_PESQUISA_FILTRO));
		}
		return logDao.buscarPorFiltroTO(filtroTO);
	}
	
	/**
	 * Metodo para buscar os usuarios que estao nas logs
	 * @return Lista contendo os usuarios das logs
	 */
	public List<Usuario> buscarUsuariosLog(){
		List<Usuario> lista = new ArrayList<>();
		for (String usuarioLog : logDao.buscarUsuariosLog()) {
			Usuario usuario = new Usuario();
			usuario.setNome(usuarioLog);
			lista.add(usuario);
		}
		return lista;
	}
}
