package br.com.visent.metricview.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.visent.corporativo.dao.GenericDAO;
import br.com.visent.corporativo.entidade.Entidade;
import br.com.visent.metricview.entidade.Tecnologia;

public class TecnologiaDAO  extends GenericDAO{

	/** Método cujo objetivo é resolver a diferença de dois conjuntos, sendo eles:
	 * 		1- Conjunto de tecnologias enviados para o método
	 * 		2- Conjunto de tecnologias presentes no banco de dados
	 * 	Resultado: Conjunto 1 - Conjunto 2
	 * 
	 * @param tecnologias - Coleção de tecnologias que será comparada com o que já existe na base.
	 * 
	 * @return Collection<Bilhetador> - Coleção de tecnologias que são diferentes dos que já existem no banco de dados. 
	 */
	@SuppressWarnings("unchecked")
	public Collection<Tecnologia> buscarConjuntoPorDiferenca(Collection<Entidade> tecnologias) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * ");
		sql.append("  FROM ( ");
		
		if (!tecnologias.isEmpty()){
			
			for (int i=0; i < tecnologias.size() ; i++) {
				
				Tecnologia tec = (Tecnologia) ((ArrayList<Entidade>)tecnologias).get(i);
				
				sql.append("select '"+tec.getNome().trim().toUpperCase()+"' from dual ");
				
				if (i != tecnologias.size()-1){
					sql.append(" UNION ");
				}
				
			}
		}else{
			sql.append(" SELECT upper(nome) from Tecnologia ");
		}
		
		sql.append(" ) ");
		sql.append(" minus ");
		sql.append(" SELECT upper(nome) FROM Tecnologia ");
		
		List<Object> tecnologiasNovas = getEntityManager().createNativeQuery(sql.toString()).getResultList();
		
		Collection<Tecnologia> resultNovasTecnologias = new ArrayList<Tecnologia>();
		
		for (Object objectTecnologia : tecnologiasNovas) {
			Object obj = objectTecnologia;
			
			Tecnologia tecnologia = new Tecnologia();
			
			tecnologia.setNome(obj.toString());
			
			for (int i=0;i< tecnologias.size(); i++){
				Tecnologia bil = (Tecnologia) ((List<Entidade>)tecnologias).get(i);
				
				if (bil.getNome().toUpperCase().equals(tecnologia.getNome().toUpperCase())){
					tecnologia.setTipoTecnologia(bil.getTipoTecnologia());
				}
			}
			
			resultNovasTecnologias.add(tecnologia);
		}
		
		return resultNovasTecnologias;
	}
	
}
