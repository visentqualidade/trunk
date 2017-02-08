package br.com.visent.metricview.dao;

import br.com.visent.corporativo.dao.GenericDAO;
import br.com.visent.metricview.entidade.Grupo;

public class GrupoDAO extends GenericDAO {

	public boolean buscaGrupoMesmoNome(Grupo grupo){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT entidade FROM Grupo entidade WHERE upper(entidade.nome) = '"+grupo.getNome().toUpperCase()+"'");
		
		if (getEntityManager().createQuery(sql.toString()).getResultList().size() > 0){
			return true;
		}
		return false;
	}
	
}
