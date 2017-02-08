package br.com.visent.metricview.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.visent.corporativo.dao.GenericDAO;
import br.com.visent.metricview.entidade.LogEntidade;
import br.com.visent.metricview.to.LogsTO;

public class LogDao extends GenericDAO{
	
	/**
	 * Metodo para buscar as logs baseadas nos filtros selecionados pelo usuario
	 * @param filtro Entidade contendo as informacoes preenchidas pelo usuario
	 * @return Lista Contendo os dados para serem apresentados
	 */
	@SuppressWarnings("unchecked")
	public List<LogEntidade> buscarPorFiltroTO(LogsTO filtro) {
		List<LogEntidade> lista = new ArrayList<>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT entidade FROM LogEntidade entidade WHERE 1=1");
		if(filtro.getDataInicio() != null && filtro.getDataFim() == null){
			sql.append(" AND TRUNC(entidade.dataHora) = TO_DATE('"+dataParaOracle(filtro.getDataInicio())+"' , 'DD/MM/YYYY')");
		} else if(filtro.getDataInicio() == null && filtro.getDataFim() != null) {
			sql.append(" AND TRUNC(entidade.dataHora) = TO_DATE('"+dataParaOracle(filtro.getDataFim())+"' , 'DD/MM/YYYY')");
		} else if(filtro.getDataInicio() != null && filtro.getDataFim() != null) {
			sql.append(" AND TRUNC(entidade.dataHora) BETWEEN ");
			sql.append(" TO_DATE('"+dataParaOracle(filtro.getDataInicio())+"' , 'DD/MM/YYYY') AND");
			sql.append(" TO_DATE('"+dataParaOracle(filtro.getDataFim())+"' , 'DD/MM/YYYY')");
		}
		sql.append(" AND entidade.tipo = 'INFO'");
		sql.append(" ORDER BY entidade.dataHora DESC");
		lista = getEntityManager().createQuery(sql.toString()).getResultList();
		return lista;
	}
	
	/**
	 * Metodo para buscar os usuarios que estao nas logs
	 * @return Lista contendo os usuarios das logs
	 */
	@SuppressWarnings("unchecked")
	public List<String> buscarUsuariosLog(){
		List<String> lista = null;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DISTINCT entidade.nomeUsuario FROM LogEntidade entidade");
		lista = getEntityManager().createQuery(sql.toString()).getResultList();
		return lista;
	}

}
