package br.com.visent.metricview.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.visent.corporativo.dao.GenericDAO;
import br.com.visent.corporativo.entidade.Entidade;
import br.com.visent.metricview.entidade.Bilhetador;

public class BilhetadorDAO  extends GenericDAO{

	/** Método cujo objetivo é resolver a diferença de dois conjuntos, sendo eles:
	 * 		1- Conjunto de bilhetadores enviados para o método
	 * 		2- Conjunto de bilhetadores presentes no banco de dados
	 * 	Resultado: Conjunto 1 - Conjunto 2
	 * 
	 * @param bilhetadores - Coleção de bilhetadores que será comparada com o que já existe na base.
	 * 
	 * @return Collection<Bilhetador> - Coleção de bilhetadores que são diferentes dos que já existem no banco de dados. 
	 */
	@SuppressWarnings("unchecked")
	public Collection<Bilhetador> buscarConjuntoPorDiferenca(Collection<Entidade> bilhetadores) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * ");
		sql.append("  FROM ( ");
		
		if (!bilhetadores.isEmpty()){
			
			for (int i=0; i < bilhetadores.size() ; i++) {
				
				Bilhetador bil = (Bilhetador) ((ArrayList<Entidade>)bilhetadores).get(i);
				
				sql.append("select '"+bil.getNome().trim().toUpperCase()+"' from dual ");
				
				if (i != bilhetadores.size()-1){
					sql.append(" UNION ");
				}
				
			}
		}else{
			sql.append(" SELECT upper(nome) from Bilhetador ");
		}
		
		sql.append(" ) ");
		sql.append(" minus ");
		sql.append(" SELECT upper(nome) FROM Bilhetador ");
		
		List<Object> bilhetadoresNovos = getEntityManager().createNativeQuery(sql.toString()).getResultList();
		
		Collection<Bilhetador> resultNovosBilhetadores = new ArrayList<Bilhetador>();
		
		for (Object objectBilhetador : bilhetadoresNovos) {
			Object obj = objectBilhetador;
			
			Bilhetador bilhetador = new Bilhetador();
			
			bilhetador.setNome(obj.toString());
			
			for (int i=0;i< bilhetadores.size(); i++){
				Bilhetador bil = (Bilhetador) ((List<Entidade>)bilhetadores).get(i);
				
				if (bil.getNome().toUpperCase().equals(bilhetador.getNome().toUpperCase())){
					bilhetador.setTecnologia(bil.getTecnologia());
				}
			}
			
			resultNovosBilhetadores.add(bilhetador);
		}
		
		
		return resultNovosBilhetadores;
	}
	
	/**
	 * Metodo para buscar os bilhetadores e suas associações com CN's
	 */
	@SuppressWarnings("unchecked")
	public List<Bilhetador> buscarTodosBilhetadoresFetch() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT(entidade) FROM Bilhetador entidade ");
		sql.append("LEFT JOIN FETCH entidade.relBilhetadores bilhetadoresCN ");
		sql.append("LEFT JOIN FETCH bilhetadoresCN.codigoNacional codigos ");
		sql.append("ORDER BY entidade.nome ");
		
		return getEntityManager().createQuery(sql.toString()).getResultList();
	}
	
	/**
	 * Metodo para consultar todos os bilhetadores de GSM - VOZ
	 * @return Lista de bilhetadores
	 */
	@SuppressWarnings("unchecked")
	public List<Bilhetador> consultarBilhetadoresGSM() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT entidade FROM Bilhetador entidade WHERE entidade.tecnologia.tipoTecnologia.descricao = 'VOZ'")
			.append(" ORDER BY entidade.nome ");
		return getEntityManager().createQuery(sql.toString()).getResultList();
	}
	
}
