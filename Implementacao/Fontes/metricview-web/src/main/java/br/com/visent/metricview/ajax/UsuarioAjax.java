package br.com.visent.metricview.ajax;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.visent.componente.dwr.reverse.Reverse;
import br.com.visent.componente.dwr.util.CriptografiaUtil;
import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.entidade.Entidade;
import br.com.visent.corporativo.exception.CorporativoServiceException;
import br.com.visent.corporativo.service.GenericService;
import br.com.visent.metricview.autenticador.ControleUsuarios;
import br.com.visent.metricview.constantes.ConstantesMetricView;
import br.com.visent.metricview.dao.UsuarioDAO;
import br.com.visent.metricview.decorator.UsuarioDecorator;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.service.AreaUsuarioService;
import br.com.visent.metricview.service.UsuarioService;
import br.com.visent.metricview.util.ConstantesUtil;
import br.com.visent.metricview.util.EmailService;
import br.com.visent.metricview.util.SessionManagerUtil;

public class UsuarioAjax extends MetricViewAjax<Usuario>{
	
	private UsuarioService usuarioService = new UsuarioService();
	private UsuarioDAO usuarioDao = new UsuarioDAO();
	private AreaUsuarioService areaUsuarioService = new AreaUsuarioService();
	
	@Override
	public Entidade inserirEntidade(Entidade entidade) throws CorporativoServiceException {
		Usuario usuario = (Usuario)entidade;
		if(usuario.getAdmin() == null){
			usuario.setAdmin(Boolean.FALSE);
		}
		usuario.setSenha(CriptografiaUtil.getInstance().criptografar(usuario.getLogin().toLowerCase()));
		usuario.setPrimeiroAcesso(Boolean.TRUE);
		usuario = (Usuario) usuarioService.inserir(usuario);
		EmailService.enviarEmail(usuario , EmailService.EMAIL_CADASTRO_USUARIO);
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_USUARIO_INSERIDO_SUCESSO , usuario.getNome()), Log.INFO);
		return usuario;
	}
	
	/**
	 * Metodo para atualizar a entidade sem aplicar nenhuma regra de negocio
	 * @param entidade Entidade para ser atualizada
	 * @throws CorporativoServiceException Caso ocorro algum problema ao atualizar os dados
	 */
	public void atualizarEntidadeSemAdmin(Entidade entidade) throws CorporativoServiceException{
		/*
		 * Usando a classe generica pois nao ha necessidade de aplicar qualquer 
		 * regra de negocio ao atualizar o usuario quando for atualizar
		 */
		GenericService genericService = new GenericService();
		Usuario usuario = (Usuario)entidade;
		Usuario usuarioBanco = (Usuario) buscarPorId(entidade.getId());
		usuario.setSenha(usuarioBanco.getSenha());
		usuario.setPrimeiroAcesso(usuarioBanco.getPrimeiroAcesso());
		usuario.setPermissoes(usuarioBanco.getPermissoes());
		usuario.setGrupos(usuarioBanco.getGrupos());
		if(usuario.getAdmin() == null){
			usuario.setAdmin(usuarioBanco.getAdmin());
		}
		if(!usuarioService.isEmailValido(usuario)){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_EMAIL_JA_CADASTRADO));
		}
		areaUsuarioService.verificarInsericaoNovaArea(usuario.getArea());
		if(ConstantesMetricView.NOME_USUARIO_SYSTEM.equals(usuario.getLogin())){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_LOGIN_JA_CADASTRADO));
		}
		genericService.atualizar(usuario);
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_USUARIO_ATUALIZADO_SUCESSO , usuario.getNome()), Log.INFO);
	}	
	
	@Override
	public void atualizarEntidade(Entidade entidade) throws CorporativoServiceException{
		Usuario usuario = (Usuario)entidade;
		Usuario usuarioBanco = (Usuario) buscarPorId(entidade.getId());
		usuario.setSenha(usuarioBanco.getSenha());
		usuario.setPrimeiroAcesso(usuarioBanco.getPrimeiroAcesso());
		usuario.setPermissoes(usuarioBanco.getPermissoes());
		usuario.setGrupos(usuarioBanco.getGrupos());
		if(usuario.getAdmin() == null){
			usuario.setAdmin(usuarioBanco.getAdmin());
		}
		usuarioService.atualizar(usuario);
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_USUARIO_ATUALIZADO_SUCESSO , usuario.getNome()), Log.INFO);
	}	
	
	/**
	 * Metodo para resetar a senha do usuario
	 * @param idUsuario id do usuario para ser resetado
	 */
	public void resetarSenha (Long idUsuario){
		/*
		 * Usando a classe generica pois nao ha necessidade de aplicar qualquer 
		 * regra de negocio ao atualizar o usuario quando for resetar a senha
		 */
		GenericService genericService = new GenericService();
		Usuario usuarioBanco = (Usuario) buscarPorId(idUsuario);
		usuarioBanco.setPrimeiroAcesso(Boolean.TRUE);
		usuarioBanco.setSenha(CriptografiaUtil.getInstance().criptografar(usuarioBanco.getLogin()));
		genericService.atualizar(usuarioBanco);
		EmailService.enviarEmail(usuarioBanco , EmailService.EMAIL_RESETAR_SENHA);
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_RESETAR_SENHA_USUARIO , usuarioBanco.getNome()), Log.INFO);
	}
	
	/**
	 * Metodo para remover o usuario
	 * @param idUsuario id do usuario para ser removido
	 */
	public void removerUsuario(Long idUsuario) {
		Usuario usuarioBanco = (Usuario) buscarPorId(idUsuario);
		try {
			usuarioService.removerUsuario(usuarioBanco);
			HttpSession httpSession = SessionManagerUtil.getInstance().buscarSessionManagerDTOUsuario(usuarioBanco).getHttpSession();
			if(httpSession != null){
				Reverse.enviarMensagem(ConstantesUtil.FUNCAO_JS_DESCONECTA_USUARIO, null, httpSession.getId());
			}
			SessionManagerUtil.getInstance().registrarLogout(usuarioBanco);
			ControleUsuarios.logout(usuarioBanco);
			gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_USUARIO_REMOVIDO_SUCESSO , usuarioBanco.getNome()), Log.INFO);
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
	
	
	/**
	 * Método responsável por salvar diversos usuários manipulados em tela.
	 * @param usuarios
	 */
	public void associarGrupo(List<Usuario> usuarios){
		List<Usuario> users = usuarios;
		
	 	usuarioDao.removeAssociacaoUsuarios(users);
	 	usuarioDao.associarUsuarios(users);
		
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_ALTERAR_ASSOCIACAO_GRUPO , ""), Log.INFO);
	}
	
	/**
	 * Metodo para desconectar o usuario do sistema
	 * @param idUsuario id do usuario para ser pesquisado
	 */
	public void desconectarUsuario (Long idUsuario){
		Usuario usuario = (Usuario) buscarPorId(idUsuario);
		Reverse.enviarMensagem(ConstantesUtil.FUNCAO_JS_DESCONECTA_USUARIO, null, SessionManagerUtil.getInstance().buscarSessaoUsuario(usuario).getId());
		ControleUsuarios.logout(usuario);
		SessionManagerUtil.getInstance().registrarLogout(usuario);
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_DESCONECTA_USUARIO , usuario.getNome()), Log.INFO);
	}
	
	/**
	 * Metodo para buscar os usuarios para a tela de edicao informando quais estao logados na aplicacao
	 * @return Lista contendo todos os usuarios e os usuarios logados na aplicacao
	 */
	public List<UsuarioDecorator> buscarUsuarioComUsuariosLogados(){
		List<UsuarioDecorator> usuarioTO = new ArrayList<>();
		List<Usuario> listaUsuario = super.buscarTodos();
		for (Usuario usuario : listaUsuario) {
			UsuarioDecorator to = new UsuarioDecorator();
			to.setIsConectado(SessionManagerUtil.getInstance().isUsuarioLogado(usuario));
			to.setUsuario(usuario);
			usuarioTO.add(to);
		}
		return usuarioTO;
	}
	
	@Override
	public List<Usuario> buscarTodos() {
		return usuarioService.buscarTodosUsuariosFetch();
	}
	
	public void salvarPermissao(List<Usuario> users){
		for (Usuario usuario : users){
			Usuario user = (Usuario) buscarPorId(usuario.getId());
			user.setPermissoes(usuario.getPermissoes());
			atualizarEntidade(user); 
		}
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_ALTERAR_PERMISSAO_USUARIO , ""), Log.INFO);		
	}
	
}
