package br.com.visent.metricview.ajax;

import java.util.List;

import org.hibernate.Hibernate;

import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.entidade.Entidade;
import br.com.visent.corporativo.exception.BancoException;
import br.com.visent.corporativo.exception.CorporativoServiceException;
import br.com.visent.metricview.dao.GrupoDAO;
import br.com.visent.metricview.dao.UsuarioDAO;
import br.com.visent.metricview.entidade.Grupo;
import br.com.visent.metricview.service.GrupoService;
import br.com.visent.metricview.util.ConstantesUtil;

public class GrupoAjax extends MetricViewAjax<Grupo>{

	private GrupoService grupoService = new GrupoService();
	private GrupoDAO grpDao = new GrupoDAO();
	
	/**
	 * Realiza a inserção do grupo no banco de dados à partir dos dados informados na tela.
	 */
	public Entidade inserirEntidade(Entidade entidade) throws BancoException {
		entidade = grupoService.inserir(entidade);
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_GRUPO_INSERIDO_SUCESSO ,((Grupo) entidade).getNome()), Log.INFO);
		return entidade;
	}
	
	@Override
	public void atualizarEntidade(Entidade entidade) {
		Grupo grupo = (Grupo) entidade;
		Grupo grupoBanco = (Grupo) buscarPorId(grupo.getId());
		if (grupoBanco != null && !grupoBanco.getNome().equalsIgnoreCase(grupo.getNome())){
			if (grpDao.buscaGrupoMesmoNome(grupo)){
				throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_GRUPO_JA_CADASTRADO));
			}
		}
		grupo.setPermissoes(grupoBanco.getPermissoes());
		grupoService.atualizar(grupo);
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_GRUPO_ALTERADO_SUCESSO , grupo.getNome()), Log.INFO);
	}
	
	/**
	 * Metodo para remover o grupo
	 * @param idGrupo id do grupo para ser removido
	 */
	public void removerGrupo(Long idGrupo) {
		Grupo grupoBanco = (Grupo) buscarPorId(idGrupo);
		UsuarioDAO user = new UsuarioDAO();
		
		try {
			user.removerUsuarioGrupo(idGrupo);
			grupoService.remover(Grupo.class, grupoBanco.getId()); // Ao remover o grupo estará removendo automaticamente todas as suas permissões
			gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_GRUPO_REMOVIDO_SUCESSO , grupoBanco.getNome()), Log.INFO);
		} catch (SecurityException e) {
			e.printStackTrace();
			Log.debug(e.getMessage());
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ERRO_SECURITY));
		} catch (InstantiationException e) {
			e.printStackTrace();
			Log.debug(e.getMessage());
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ERRO_INSTANCIA));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Log.debug(e.getMessage());
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ERRO_ACESSO));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			Log.debug(e.getMessage());
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ERRO_CAMPO));
		}
	}
	
	@Override
	public List<Grupo> buscarTodos() {
		List<Grupo> grupos = super.buscarTodos();
		
		for (Grupo grupo : grupos) {
			Hibernate.initialize(grupo.getPermissoes());
		}
		
		return grupos;
	}
	
	public void salvarPermissao(Grupo grupo){
		
		Grupo group = (Grupo) buscarPorId(grupo.getId());
		group.setPermissoes(grupo.getPermissoes());
		atualizarEntidade(group); 
		
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_ALTERAR_PERMISSAO_GRUPO , ""), Log.INFO);		
	}
}
