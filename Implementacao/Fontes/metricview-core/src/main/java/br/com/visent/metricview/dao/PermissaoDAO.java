package br.com.visent.metricview.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.visent.corporativo.dao.GenericDAO;
import br.com.visent.metricview.entidade.Ferramenta;
import br.com.visent.metricview.entidade.Grupo;
import br.com.visent.metricview.entidade.Permissao;
import br.com.visent.metricview.entidade.TipoAcesso;
import br.com.visent.metricview.entidade.Usuario;

public class PermissaoDAO extends GenericDAO {
	
	/**
	 * Metodo para buscar as permissoes que serao removidas do banco
	 * @param classeEntidade 
	 * @param permissao Permissao contendo os dados para serem pesquisados
	 * @return Todas as permissoes para serem removidas
	 */
	@SuppressWarnings("unchecked")
	public List<Permissao> buscarPermissoesRemoverBanco(List<Permissao> permissoes){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM Permissao ");
		sql.append("minus ");
		sql.append("SELECT * FROM Permissao ");
		if(!permissoes.isEmpty()){
			sql.append(appendIdPermissao(permissoes));
		} else {
			sql.append(" where 1!=1");
		}
		
		// Hibernate nao suporta MINUS.
		List<Object> permissoesObject = getEntityManager().createNativeQuery(sql.toString()).getResultList();
		List<Permissao> permissoesRemovidos = new ArrayList<Permissao>();
		
		for (Object objPermissao : permissoesObject){
			Permissao 	permissao 	= new Permissao	();
			Grupo 		grupo 		= new Grupo 	();
			Ferramenta 	ferramenta 	= new Ferramenta();
			Usuario     usuario     = new Usuario	();
			
			Object[] obj = (Object[])objPermissao;
			permissao.setId			(Long.parseLong(obj[0].toString()));
			
			TipoAcesso tpAcesso = new TipoAcesso();
			tpAcesso.setId(Long.parseLong(obj[1].toString()));
			permissao.setCodigo		(tpAcesso);
			
			ferramenta.setId(Long.parseLong(obj[2].toString()));
			permissao.setFerramenta(ferramenta);
			
			if (obj[3] != null){
				grupo.setId	(Long.parseLong(obj[3].toString()));
				permissao.setGrupo(grupo);
			}else{
				usuario.setId(Long.parseLong(obj[4].toString()));
				permissao.setUsuario(usuario);
			}
			
			permissoesRemovidos.add(permissao);
		}
		
		return permissoesRemovidos;
	}

	/**
	 * Cria uma query SQL a ser executada pelo banco de dados afim de descobrir quais permissões estão diferentes desta
	 * no banco de dados.
	 * @param permissoes lista de permissões que serão utilizadas para gerar a query.
	 * @return
	 */
	private String appendIdPermissao(List<Permissao> permissoes) {
		
		StringBuilder sqlWhere = new StringBuilder();
		
		boolean contem = false;
		
		for (Permissao permissao : permissoes) {
			if (permissao.getId() != null){
				contem = true;
				break;
			}
		}
		
		if (contem){
			sqlWhere.append("where id_permissao IN (");
		}
		
		for(Permissao permissao : permissoes){
			if (permissao.getId() != null){
				sqlWhere.append(permissao.getId()+" ,");
			}
		}
		
		if(sqlWhere.toString().endsWith(",")){
			String sqlAntigo = sqlWhere.toString(); 
			sqlWhere = new StringBuilder();
			sqlWhere.append(sqlAntigo.substring(0, sqlAntigo.toString().length() -1));
		}
		
		if (contem){
			sqlWhere.append(")");
		}
		
		return sqlWhere.toString();
	}

}
