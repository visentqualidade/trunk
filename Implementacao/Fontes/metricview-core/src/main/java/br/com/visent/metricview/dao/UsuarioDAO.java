package br.com.visent.metricview.dao;

import java.util.List;

import org.hibernate.Hibernate;

import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.dao.GenericNativeQueryDAO;
import br.com.visent.metricview.entidade.Grupo;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.util.ConstantesUtil;

public class UsuarioDAO extends GenericNativeQueryDAO {

	public void removerUsuarioGrupo(Long codigo) {
		Grupo grupoBanco = buscarPorID(Grupo.class, codigo);
		for (Usuario userGrupo : grupoBanco.getUsuarios()){
			Hibernate.initialize(userGrupo.getGrupos());
			Grupo grupoRemover = new Grupo();
			
			grupoRemover.setId(codigo);
			
			userGrupo.getGrupos().remove(grupoRemover);
			
			atualizar(userGrupo);
		}
	}
	
	/**
	 * Metodo para buscar as permissoes, grupo e permissoes dos grupos de um usuario utilizando somente uma consulta
	 * @param usuario Usuario para ser pesquisado
	 * @return Objeto Usuario com suas dependencias (Permissoes, Grupos e Permissoes do Grupo) totalmente populadas
	 */
	public Usuario buscarUsuariosFetch(Usuario usuario) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT(entidade) FROM Usuario entidade ");
		sql.append("LEFT JOIN FETCH entidade.grupos grupo ");
		sql.append("LEFT JOIN FETCH entidade.permissoes permissaoUser ");
		sql.append("LEFT JOIN permissaoUser.ferramenta ferramentaUser ");
		sql.append("LEFT JOIN permissaoUser.codigo tipoAcessoUser ");
		sql.append("LEFT JOIN FETCH grupo.permissoes permissaoGru ");
		sql.append("WHERE entidade.id = "+usuario.getId());
		return (Usuario) getEntityManager().createQuery(sql.toString()).getSingleResult();
	}

	/**
	 * Metodo para buscar as permissoes, grupo e permissoes dos grupos de cada usuario utilizando somente uma consulta
	 * @return Objeto Usuario com suas dependencias (Permissoes, Grupos e Permissoes do Grupo) totalmente populadas
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> buscarTodosUsuariosFetch() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT(entidade) FROM Usuario entidade ");
		sql.append("LEFT JOIN FETCH entidade.grupos grupo ");
		sql.append("LEFT JOIN FETCH entidade.permissoes permissaoUser ");
		sql.append("LEFT JOIN FETCH grupo.permissoes permissaoGru ");
		sql.append("LEFT JOIN permissaoGru.codigo tipoAceGrupo ");
		sql.append("LEFT JOIN permissaoUser.codigo tipoAceUser ");
		return getEntityManager().createQuery(sql.toString()).getResultList();
	}

	
	/**
	 * Metodo para remover associações de usuários a grupos diferentes de um conjunto de usuários.
	 */
	@SuppressWarnings("unchecked")
	public void removeAssociacaoUsuarios(List<Usuario> usuarios){
		
		if (usuarios.isEmpty()){
			return;
		}
		
		StringBuilder queryAssociacoes = new StringBuilder();

		queryAssociacoes.append("select ug.id_usuario|| ' - '|| ug.id_grupo		")
						.append("  from usuario_grupo ug 						")
						.append("minus 											")
						.append("select ug.id_usuario|| ' - '|| ug.id_grupo		")
						.append("  from usuario_grupo ug 						")
						.append(" where id_usuario ||' - '|| id_grupo in (		");
			
		for (Usuario user : usuarios){
			for (Grupo grupo : user.getGrupos()){
				queryAssociacoes.append("'"+user.getId()+"'||' - '||'"+grupo.getId()+"' ,");
			}
		}
			
		if (queryAssociacoes.charAt(queryAssociacoes.length()-1) == ','){
			queryAssociacoes.replace(queryAssociacoes.length()-1, queryAssociacoes.length(), ")"); 
		}else{
			queryAssociacoes.append("' ') ");
		}
		
		List<String> associacoes = getEntityManager().createNativeQuery(queryAssociacoes.toString()).getResultList();
		
		if (!associacoes.isEmpty()){
			
			StringBuilder removerAssociacoes = new StringBuilder();
			
			removerAssociacoes	.append("DELETE FROM USUARIO_GRUPO ")
							  	.append(" WHERE id_usuario||' - '||id_grupo in ( ");
			
			for (String associacao : associacoes) {
				removerAssociacoes.append("'"+associacao+"',");
			}
			
			if (removerAssociacoes.charAt(removerAssociacoes.length()-1) == ','){
				removerAssociacoes.replace(removerAssociacoes.length()-1, removerAssociacoes.length(), ")"); 
			}else{
				removerAssociacoes.append("' ') ");
			}
			
			executeInsertUpdateQuery(removerAssociacoes.toString(), PropertiesUtil.getMessage(ConstantesUtil.ASSOCIAR_USUARIO));
		}
		
	}

	/**
	 * Método responsável pela associação de usuários à partir de um conjunto.
	 * @param users
	 */
	public void associarUsuarios(List<Usuario> usuarios) {
		
		if (usuarios.isEmpty()){
			return;
		}
		
		StringBuilder queryAssociacoes = new StringBuilder();
		queryAssociacoes.append("( ");
		for (Usuario user : usuarios){
			for (Grupo grupo : user.getGrupos()){
				queryAssociacoes.append(" select "+user.getId()+", "+grupo.getId()+" from dual union all");
			}
		}
			
		if (queryAssociacoes.charAt(queryAssociacoes.length()-1) == 'l'){
			queryAssociacoes.replace(queryAssociacoes.length()-10, queryAssociacoes.length(), ")"); 
		}else{
			queryAssociacoes.append("select id_usuario, id_grupo from usuario_grupo) ");
		}
		
		queryAssociacoes.append("minus 											")
						.append("select ug.id_usuario, ug.id_grupo				")
						.append("  from usuario_grupo ug 						");
		
		StringBuilder queryInsert = new StringBuilder();
		
		queryInsert.append("INSERT INTO USUARIO_GRUPO ( ")
				   .append(queryAssociacoes)
				   .append(")");
		executeInsertUpdateQuery(queryInsert.toString(), PropertiesUtil.getMessage(ConstantesUtil.ASSOCIAR_USUARIO));
		
	}
}