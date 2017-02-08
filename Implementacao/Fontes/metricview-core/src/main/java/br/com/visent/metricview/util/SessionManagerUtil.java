package br.com.visent.metricview.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.visent.componente.dwr.util.SessaoUtil;
import br.com.visent.metricview.entidade.Funcionalidade;
import br.com.visent.metricview.entidade.Grupo;
import br.com.visent.metricview.entidade.Permissao;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.entidade.enuns.PermissaoCodigoEnum;
import br.com.visent.metricview.to.SessionManagerTO;

public class SessionManagerUtil implements HttpSessionListener{
	
	private static final SessionManagerUtil sessionManagerUtil = new SessionManagerUtil();
	public Map<Usuario, SessionManagerTO> mapaUsuarios;
	
	public SessionManagerUtil() {}
	
	/**
	 * Metodo para percorrer o mapa de usuarios logados para encontrar o httprequest do usuario quando ele loga na aplicacao
	 * @param usuarioPesquisa para ser feito a pesquisa no mapa
	 * @return SessionManagerTO do referente usuario
	 */
	public SessionManagerTO buscarSessionManagerDTOUsuario(Usuario usuarioPesquisa){
		SessionManagerTO httpRequest = new SessionManagerTO();
		Set<Map.Entry<Usuario, SessionManagerTO>> entries = mapaUsuarios.entrySet();  
        for(Map.Entry<Usuario, SessionManagerTO> entry: entries){
        	if(entry.getKey().equals(usuarioPesquisa)){
        		httpRequest = (SessionManagerTO)entry.getValue();
        		break;
        	}
        }
        return httpRequest;
	}
	
	/**
	 * Metodo para buscar a sessao do usuario
	 * @param usuarioPesquisa Usuario que esta logado na aplicacao
	 * @return Sessao do usuario
	 */
	public HttpSession buscarSessaoUsuario(Usuario usuarioPesquisa){
		SessionManagerTO sessionManagerTO = buscarSessionManagerDTOUsuario(usuarioPesquisa);
		HttpSession session = null;
		if(sessionManagerTO != null){
			session = sessionManagerTO.getHttpSession();
		}
		return session;
	}
	
	/**
	 * Metodo para verificar se a sessao do browser ja esta sendo utilizada por algum usuario
	 * @return HttpSession que esta ativa
	 */
	public HttpSession isSessaoAtivaBrowser (){
		HttpSession httpSession = null;
		for (Usuario usuario : buscarTodosUsuariosLogados()) {
			HttpSession session = buscarSessaoUsuario(usuario);
			if(session != null){
				if(session.getId().equals(SessaoUtil.getId())){
					httpSession = session;
					break;
				}
			}
		}
		return httpSession;
	}
	
	/**
	 * Metodo para se a pessoa ja esta logada na aplicacao
	 * @param usuario Entidade usuario para verificar se ela ja esta logada na aplicacao
	 * @return Se o usuario ja esta logado ou nao na aplicacao
	 */
	public Boolean isUsuarioLogado(Usuario usuario){
		return buscarTodosUsuariosLogados().contains(usuario);
	}
	
	/**
	 * Metodo para buscar todos os usuarios logados da aplicacao
	 * @return Todos os usuarios que estao devidamente autenticados dentro da aplicacao
	 */
	public List<Usuario> buscarTodosUsuariosLogados(){
		List<Usuario> usuariosLogados = new ArrayList<>();
		Set<Map.Entry<Usuario, SessionManagerTO>> entries = mapaUsuarios.entrySet();  
        for(Map.Entry<Usuario, SessionManagerTO> entry: entries){
        	usuariosLogados.add(entry.getKey());
        }
        return usuariosLogados;
	}
	
	/**
	 * Metodo inserir dentro do mapa, um novo usuario que acaba de logar dentro do sistema
	 * @param usuario Entidade contendo o usuario que acaba de logar
	 * @param req 
	 */
	public void registrarLogin (Usuario usuario){
		criarLogin(usuario);
	}
	
	/**
	 * Metodo para configurar o usuario junto com a sessionManager
	 * @param usuario Usuario que sera colocado na sessao
	 */
	private void criarLogin(Usuario usuario) {
		//cria o controle de usuario autenticados no sistema
		SessionManagerTO dto = new SessionManagerTO();
		dto.setHttpSession(SessaoUtil.getSessao());
		mapaUsuarios.put(usuario, dto);
	}
	
	/**
	 * Metodo para atualizar o mapa de usuario logados do sistema
	 * @param usuario Usuario para ser atualizado na lista de usuarios
	 * @param sessionManagerDTO DTO contendo as novas informacoes sobre o usuario
	 */
	public void atualizarMapaUsuarios (Usuario usuario , SessionManagerTO sessionManagerDTO){
		mapaUsuarios.remove(usuario);
		sessionManagerDTO.getHttpSession().setAttribute(SessaoUtil.USUARIO, usuario);
		mapaUsuarios.put(usuario, sessionManagerDTO);
	}

	/**
	 * Metodo para remover do mapa um usuario, quando o usuario deslogar da aplicacao
	 * @param usuario Entidade contendo o usuario que ira deslogar
	 * @param idSession Id da sessao do usuario
	 */
	public void registrarLogout (Usuario usuario){
		HttpSession httpsession = buscarSessaoUsuario(usuario);
		if(httpsession != null){
			httpsession.removeAttribute(SessaoUtil.USUARIO);
			mapaUsuarios.remove(usuario);
			httpsession.invalidate();
		}
	}
	
	/**
	 * Metodo para retornar a quantidade de usuarios logados dentro do sistema
	 * @return Quantidade de usuarios no sistema
	 */
	public Integer getNumeroUsuarioSistema(){
		return mapaUsuarios.size();
	}
	
	/**
	 * Metodo para buscar a quantidade de usuarios administradores
	 * @return Quantidade de administradores autenticados
	 */
	public Integer getNumeroUsuarioSistemaAdmin(){
		Integer qtdUsuario = 0;
		for (Usuario usuario : buscarTodosUsuariosLogados()) {
			HttpSession session = buscarSessaoUsuario(usuario);
			if(session != null){
				Usuario usuarioSessao = (Usuario) session.getAttribute(SessaoUtil.USUARIO);
				if(usuarioSessao.getAdmin() != null){
					if(usuarioSessao.getAdmin()){
						qtdUsuario++;
					}
				}
			}
		}
		return qtdUsuario;
	}
	
	/**
	 * Metodo para buscar a quantidade de usuarios comuns
	 * @return Quantidade de usuarios comuns autenticados
	 */
	public Integer getNumeroUsuarioSistemaComum(){
		Integer qtdUsuario = 0;
		for (Usuario usuario : buscarTodosUsuariosLogados()) {
			HttpSession session = buscarSessaoUsuario(usuario);
			if(session != null){
				Usuario usuarioSessao = (Usuario) session.getAttribute(SessaoUtil.USUARIO);
				if(usuarioSessao.getAdmin() != null){
					if(!usuarioSessao.getAdmin()){
						qtdUsuario++;
					}
				}
			}
		}
		return qtdUsuario;
	}
	
	/**
	 * Metodo responsavel por iniciar o mapa para se controlar os usuarios dentro do sistema
	 * OBS.: Metodo iniciado (initApp) ao se subir o servidor
	 */
	public void init (){
		mapaUsuarios = new HashMap<>();
	}
	
	/**
	 * Metodo para retornar as funcionalidades do usuario
	 * @param usuario {@link Usuario} para verificar as permissoes de acesso
	 * @param tipoFuncionalidade Tipo de menu para se obter as funcionalidades
	 * @param nomeSistema Nome do sistema em que o usuario ira ter as funcionalidades
	 * @return Lista com as funcionalidades do sistema
	 */
	public List<Funcionalidade> buscarFuncionalidadeUsuarioSessao(Usuario usuario , Long tipoFuncionalidade , String nomeSistema){
		Boolean isUsuarioAdm = Boolean.FALSE;
		Permissao permissaoUsuario = null;
		
		for (Permissao permissao : usuario.getPermissoes()) {
			if(permissao.getFerramenta().getNome().equalsIgnoreCase(nomeSistema)){
				if(permissao.getCodigo().getDescricao().equals(PermissaoCodigoEnum.ADMINISTRADOR.getDescricao())){
					permissaoUsuario = permissao;
					isUsuarioAdm = Boolean.TRUE;
					break;
				}
			}
		}
		if(!isUsuarioAdm){
			for (Grupo grupo : usuario.getGrupos()) {
				for (Permissao permissao : grupo.getPermissoes()) {
					if(permissao.getFerramenta().getNome().equalsIgnoreCase(nomeSistema)){
						if(permissao.getCodigo().getDescricao().equals(PermissaoCodigoEnum.ADMINISTRADOR.getDescricao())){
							permissaoUsuario = permissao;
							isUsuarioAdm = Boolean.TRUE;
							break;
						}
					}
				}
				if(isUsuarioAdm){
					break;
				}
			}
		}
		List<Funcionalidade> funcionalidadesRetorno = new ArrayList<Funcionalidade>();
		if(permissaoUsuario != null){
			for (Funcionalidade funcionalidade : permissaoUsuario.getCodigo().getFuncionalidades()) {
				if(funcionalidade.getFerramenta().getNome().equalsIgnoreCase(nomeSistema)){
					if(tipoFuncionalidade == funcionalidade.getTipoMenu()){
						if(funcionalidade.getExibir()){
							funcionalidadesRetorno.add(funcionalidade);
						}
					}
				}
			}
		}
		return funcionalidadesRetorno;
	}
	
	/**
	 * Metodo inserir dentro do mapa, um novo usuario que acaba de logar dentro do sistema
	 * @param usuario Entidade contendo o usuario que acaba de logar
	 */
	public void registrarLogin (Usuario usuario , HttpServletRequest req){
		criarLogin(usuario , req);
	}
	
	/**
	 * Metodo para configurar o usuario junto com a sessionManager
	 * @param usuario Usuario que sera colocado na sessao
	 */
	private void criarLogin(Usuario usuario , HttpServletRequest req) {
		//cria o controle de usuario autenticados no sistema
		SessionManagerTO dto = new SessionManagerTO();
		dto.setHttpSession(req.getSession());
		mapaUsuarios.put(usuario, dto);
	}
	

	@Override
	public void sessionCreated(HttpSessionEvent se) {}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		Usuario usuario = (Usuario)session.getAttribute(SessaoUtil.USUARIO);
		SessionManagerUtil.getInstance().registrarLogout(usuario);
	}

	public static synchronized SessionManagerUtil getInstance() {
		return sessionManagerUtil;
	}

}
