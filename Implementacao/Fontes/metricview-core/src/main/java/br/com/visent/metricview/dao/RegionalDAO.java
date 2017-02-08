package br.com.visent.metricview.dao;

import java.util.List;

import br.com.visent.corporativo.dao.GenericDAO;
import br.com.visent.metricview.entidade.Regional;
import br.com.visent.metricview.entidade.UnidadeFederativa;

public class RegionalDAO extends GenericDAO {

	
	/**
	 * Busca por regionais com o mesmo nome, caso o id da regional já esta preenchido verifico se existe regional com o mesmo nome só que com o id diferente deste.
	 * @param regional
	 * @return
	 */
	public boolean buscaRegionalMesmoNome(Regional regional){
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT entidade FROM Regional entidade WHERE upper(entidade.nome) = '"+regional.getNome().toUpperCase()+"' "+(regional.getId() != null ? " AND entidade.id != "+regional.getId() : ""));
		
		if (getEntityManager().createQuery(sql.toString()).getResultList().size() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo para buscar as ufs de uma regional
	 * @param regional String contendo a regional
	 * @return Lista contendo todas as ufs da regional
	 */
	@SuppressWarnings("unchecked")
	public List<UnidadeFederativa> buscarUfs(String regional){
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT uf FROM Regional entidade ");
		sql.append(" INNER JOIN entidade.codigoNacional codNac ");
		sql.append(" INNER JOIN codNac.uf uf ");
		sql.append(" WHERE UPPER(entidade.nome) = UPPER('"+regional+"')");
		
		return getEntityManager().createQuery(sql.toString()).getResultList();
	}
	
	/**
	 * Metodo para consultar as regionais fixas
	 * @return Lista de Regionais
	 */
	@SuppressWarnings("unchecked")
	public List<Regional> consultarRegionaisFixas() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT entidade FROM Regional entidade ");
		sql.append(" WHERE entidade.indicadorSmp = true ");
		
		return getEntityManager().createQuery(sql.toString()).getResultList();
	}
	
}
