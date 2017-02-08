package br.com.visent.metricview.ajax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import br.com.visent.componente.dwr.reverse.Reverse;
import br.com.visent.componente.dwr.util.CriptografiaUtil;
import br.com.visent.componente.dwr.util.SessaoUtil;
import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.metricview.autenticador.ControleUsuarios;
import br.com.visent.metricview.constantes.ConstantesMetricView;
import br.com.visent.metricview.entidade.Grupo;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.exception.MetricViewException;
import br.com.visent.metricview.service.UsuarioService;
import br.com.visent.metricview.util.ConstantesUtil;
import br.com.visent.metricview.util.SessionManagerUtil;

@SuppressWarnings("rawtypes")
public class LoginAjax extends MetricViewAjax{
	
	private static final String USUARIO_JA_LOGADO = "USUARIO_JA_LOGADO";
	private UsuarioService usuarioService = new UsuarioService();
	
	/**
	 * Metodo para realizar o login do usuario na aplicacao
	 * @param usuario Usuario preenchido na tela de login
	 * @param usuarioJaConectado Boolean para verificar se a requisicao veio do primeiro acesso ou ja o usuario ja confirmou a desconeccao
	 * @return se o usuario esta logado ou nao
	 * @throws MetricViewException Caso nao existe usuario cadastrado
	 */
	public String autenticarUsuario(Usuario usuario , Boolean usuarioJaConectado) throws MetricViewException{
		String isUsuarioLiberado = null;
		if(ConstantesMetricView.NOME_USUARIO_SYSTEM.equals(usuario.getLogin())){
			isUsuarioLiberado = isUsuarioSystem(usuario , usuarioJaConectado);
		} else {
			isUsuarioLiberado = criarAutenticacao(usuario, usuarioJaConectado);
		}
		return isUsuarioLiberado;
	}
	
	/**
	 * Metodo para criar a autenticacao caso o usuario nao seja o sistema
	 * @param usuario Usuario preenchido na tela de login
	 * @param usuarioJaConectado Boolean para verificar se a requisicao veio do primeiro acesso ou ja o usuario ja confirmou a desconeccao
	 * @return se o usuario esta logado ou nao
	 * @throws MetricViewException Caso nao existe usuario cadastrado
	 */
	public String criarAutenticacao(Usuario usuario , Boolean usuarioJaConectado) throws MetricViewException{
		String isUsuarioLiberado = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuario.setSenha(CriptografiaUtil.getInstance().criptografar(usuario.getSenha()));
		usuarios = usuarioService.buscarPorFiltro(usuario);

		if (usuarios.isEmpty()) {
			throw new MetricViewException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ALERTA_USUARIO_NAO_CADASTRADO));
		} else {
			if((isUsuarioLiberado = isUsuarioJaLogado(usuario , usuarioJaConectado)) == null){
				Usuario usuarioLista = usuarios.get(0);
				isQtdUsuarioPermitida(usuarioLista);
				if((isUsuarioLiberado = isUsuarioMesmaSessaoBrowser()) == null){
					if(!usuarioJaConectado){
						//Caso o usuario ja esteja conectado ira desloga-lo, da aplicacao
						Reverse.enviarMensagem(ConstantesUtil.FUNCAO_JS_DESCONECTA_USUARIO, null, SessionManagerUtil.getInstance().buscarSessaoUsuario(usuarioLista).getId());
						SessionManagerUtil.getInstance().registrarLogout(usuarioLista);
					}
					usuarioLista = usuarioService.buscarUsuariosFetch(usuarioLista);
					ControleUsuarios.addUsuarioDeletandoRegistro(usuarioLista);
					SessionManagerUtil.getInstance().registrarLogin(usuario);
					gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_LOGIN, usuarioLista.getNome()), Log.INFO);
				}
			}
		}
		return isUsuarioLiberado;
	}
	
	/**
	 * Metodo para verificar se o usuario ja esta utilizando a sessao do browser
	 * @return null caso nenhum usuario esteja utilizando a sessao do browser, USUARIO_JA_LOGADO caso o usuario ja esteja utilizando a sessao do browser
	 */
	private String isUsuarioMesmaSessaoBrowser() {
		String isMesmaSessao = null;
		HttpSession sessaoUsuario = SessionManagerUtil.getInstance().isSessaoAtivaBrowser();
		if(sessaoUsuario != null){
			isMesmaSessao = USUARIO_JA_LOGADO;
		}
		return isMesmaSessao;
	}

	/**
	 * Metodo para verificar se o usuario ja esta logado na aplicacao
	 * @param usuario Usuario preenchido na tela de login
	 * @param usuarioJaConectado Boolean para verificar se a requisicao veio do primeiro acesso ou ja o usuario ja confirmou a desconeccao
	 * @return null caso o usuario que esteja se conectando ja esteja conectado ou USUARIO_JA_LOGADO caso o usuario que esteja se conectando ja esta logado
	 */
	private String isUsuarioJaLogado(Usuario usuario, Boolean usuarioJaConectado) {
		String usuarioLogado = null;
		if(isUsuarioLogado(usuario)) {
			if(usuarioJaConectado) {
				usuarioLogado = USUARIO_JA_LOGADO;
			}
		}
		return usuarioLogado;
	}

	/**
	 * Metodo para verificar se a quantidade de usuario ja foi ultrapassada 
	 * @param usuario Usuario que esta tentando se autenticar
	 * @throws MetricViewException Caso ja tenha alcancado o numero maximo de usuarios logados
	 */
	private void isQtdUsuarioPermitida(Usuario usuario) throws MetricViewException{
		if(usuario.getAdmin()){
			if(SessionManagerUtil.getInstance().getNumeroUsuarioSistemaAdmin() == ConstantesMetricView.QTD_USUARIO_CONECTADO_ADMIN){
				if(!SessionManagerUtil.getInstance().isUsuarioLogado(usuario)){
					throw new MetricViewException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ALERTA_QTD_USUARIO_SIMULTANEOS_ATINGIDA , ConstantesMetricView.QTD_USUARIO_CONECTADO_ADMIN));
				}
			}
		} else {
			if(SessionManagerUtil.getInstance().getNumeroUsuarioSistemaComum() == ConstantesMetricView.QTD_USUARIO_CONECTADO_COMUM){
				if(!SessionManagerUtil.getInstance().isUsuarioLogado(usuario)){
					throw new MetricViewException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ALERTA_QTD_USUARIO_SIMULTANEOS_ATINGIDA , ConstantesMetricView.QTD_USUARIO_CONECTADO_COMUM));
				}
			}
		}
	}

	/**
	 * Metodo para verificar se o usuario ja esta conectado no sistema
	 * @param usuario Usuario para seer verificado
	 * @return se o usuario ja esta conectado ou nao no sistema
	 */
	private Boolean isUsuarioLogado(Usuario usuario) {
		return SessionManagerUtil.getInstance().buscarSessaoUsuario(usuario) != null;
	}

	/**
	 * Metodo para verificar se o usuario logado é o usuario system ou nao
	 * @param usuario Usuario preenchido na tela de login
	 * @param usuarioJaConectado Boolean para verificar se a requisicao veio do primeiro acesso ou ja o usuario ja confirmou a desconeccao
	 * @return Caso seja o usuario system, ira autentica-lo e retornar true, caso nao seja ira retornar
	 * @throws caso nao seja o usuario system
	 */ 
	private String isUsuarioSystem(Usuario usuario, Boolean usuarioJaConectado) throws MetricViewException{
		String isUsuarioSystem = null;
		if (ConstantesMetricView.SENHA_USUARIO_SYSTEM.equals(CriptografiaUtil.getInstance().criptografar(usuario.getSenha()))) {
			usuario.setNome(usuario.getLogin());
			if(SessionManagerUtil.getInstance().isUsuarioLogado(usuario)){
				if(!usuarioJaConectado){
					//Caso o usuario ja estaja conectado ira desloga-lo, da aplicacao
					Reverse.enviarMensagem(ConstantesUtil.FUNCAO_JS_DESCONECTA_USUARIO, null, SessionManagerUtil.getInstance().buscarSessaoUsuario(usuario).getId());
					SessionManagerUtil.getInstance().registrarLogout(usuario);
					Set<Grupo> lista = new HashSet<Grupo>();
					Grupo grupo = new Grupo();
					grupo.setNome("Grupo Sistema");
					lista.add(grupo);
					usuario.setGrupos(lista);
					ControleUsuarios.addUsuarioDeletandoRegistro(usuario);
					SessionManagerUtil.getInstance().registrarLogin(usuario);
					gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_LOGIN, usuario.getLogin()), Log.INFO);
				} else {
					isUsuarioSystem = USUARIO_JA_LOGADO;
				}
			} else {
				Set<Grupo> lista = new HashSet<Grupo>();
				Grupo grupo = new Grupo();
				grupo.setNome("Grupo Sistema");
				lista.add(grupo);
				usuario.setGrupos(lista);
				ControleUsuarios.addUsuarioDeletandoRegistro(usuario);
				SessionManagerUtil.getInstance().registrarLogin(usuario);
				gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_LOGIN, usuario.getLogin()), Log.INFO);
			}
		} else {
			throw new MetricViewException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ALERTA_USUARIO_NAO_CADASTRADO));
		}
		return isUsuarioSystem;
	}
	
	/**
	 * Metodo para efetuar o logout do usuario que esta na sessao
	 */
	public void logout() {
		Usuario usuario = (Usuario)SessaoUtil.getPojo(SessaoUtil.USUARIO);
		gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_LOGOUT, usuario.getLogin()), Log.INFO);
		if(!usuario.getLogin().equals(ConstantesMetricView.NOME_USUARIO_SYSTEM)){
			HttpSession session = SessionManagerUtil.getInstance().buscarSessaoUsuario(usuario);
			if(session != null){
				Reverse.enviarMensagem(ConstantesUtil.FUNCAO_JS_DESCONECTA_USUARIO, null, session.getId());
			}
		}
		ControleUsuarios.logout();
		SessionManagerUtil.getInstance().registrarLogout(usuario);
	}
	
	/**
	 * Metodo para buscar o usuario da sessao
	 * @return Usuario que esta na sessao
	 */
	public Usuario buscarUsuarioSessao(){
		return (Usuario)SessaoUtil.getPojo(SessaoUtil.USUARIO);
	}
	
}
