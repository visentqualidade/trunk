package br.com.visent.metricview.service;

import java.util.Date;
import java.util.List;

import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.entidade.Entidade;
import br.com.visent.corporativo.exception.BancoException;
import br.com.visent.corporativo.exception.CorporativoServiceException;
import br.com.visent.corporativo.service.GenericService;
import br.com.visent.metricview.constantes.ConstantesMetricView;
import br.com.visent.metricview.dao.UsuarioDAO;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.entidade.UsuarioExcluido;
import br.com.visent.metricview.util.ConstantesUtil;

public class UsuarioService extends GenericService{
	
	private UsuarioExcluidoService usuarioExcluidoService = new UsuarioExcluidoService();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private AreaUsuarioService areaUsuarioService = new AreaUsuarioService();
	
	@Override
	public void doPreInserir(Entidade entidade) throws BancoException , CorporativoServiceException {
		Usuario usuario = (Usuario)entidade;
		usuario.setLogin(usuario.getLogin().toLowerCase());
		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setLogin(usuario.getLogin());
		if(!buscarPorFiltro(usuarioFiltro).isEmpty()){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_LOGIN_JA_CADASTRADO));
		}
		usuarioFiltro.setLogin(null);
		usuarioFiltro.setEmail(usuario.getEmail());
		if(!buscarPorFiltro(usuarioFiltro).isEmpty()){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_EMAIL_JA_CADASTRADO));
		}
		if(excedeuQtdUsuarioAdm((Usuario)entidade)){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ALERTA_QTD_USUARIO_ADM_CADASTRADOS_ATINGIDA , ConstantesMetricView.QTD_USUARIO_CONECTADO_ADMIN));
		}
		areaUsuarioService.verificarInsericaoNovaArea(usuario.getArea());
		if(ConstantesMetricView.NOME_USUARIO_SYSTEM.equals(usuario.getLogin())){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_LOGIN_JA_CADASTRADO));
		}
	}
	
	@Override
	public void doPreAtualizar(Entidade entidade) throws CorporativoServiceException {
		Usuario usuario = (Usuario)entidade;
		if(!isEmailValido(usuario)){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_EMAIL_JA_CADASTRADO));
		}
		if(excedeuQtdUsuarioAdm((Usuario)entidade)){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_ALERTA_QTD_USUARIO_ADM_CADASTRADOS_ATINGIDA , ConstantesMetricView.QTD_USUARIO_CONECTADO_ADMIN));
		}
		areaUsuarioService.verificarInsericaoNovaArea(usuario.getArea());
		if(ConstantesMetricView.NOME_USUARIO_SYSTEM.equals(usuario.getLogin())){
			throw new CorporativoServiceException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_LOGIN_JA_CADASTRADO));
		}
	}

	/**
	 * Metodo para verificar se o email ja foi cadastrado
	 * @param usuario Usuario que esta sendo atualizado
	 * @return Se o email ja esta cadastrado ou nao
	 */
	public Boolean isEmailValido(Usuario usuario) {
		Boolean isEmailValido = Boolean.TRUE;
		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setEmail(usuario.getEmail());
		
		List<Usuario> listaUsuarios = buscarPorFiltro(usuarioFiltro);
		for (Usuario userLista : listaUsuarios) {
			if(!userLista.getLogin().equals(usuario.getLogin())){
				return Boolean.FALSE;
			}
		}
		return isEmailValido;
	}

	/**
	 * Metodo para verificar se a quantidade de usuarios administradores cadastrados foi atingida
	 * @param entidade Usuario que sera cadastrado
	 * @return se ja quantidade de administrados ja foi atingida
	 */
	public Boolean excedeuQtdUsuarioAdm(Usuario usuario) {
		Boolean excedeu = Boolean.FALSE;
		Integer qtdAdmin = 0;
		if(usuario.getAdmin()){
			Usuario usuarioFiltro = new Usuario();
			usuarioFiltro.setAdmin(Boolean.TRUE);
			for (Usuario userLista : buscarPorFiltro(usuarioFiltro)) {
				if(!userLista.getLogin().equals(usuario.getLogin())){
					qtdAdmin++;
				}
			}
		}
		excedeu = qtdAdmin >= ConstantesMetricView.QTD_USUARIO_CONECTADO_ADMIN;
		return excedeu;
	}

	/**
	 * Metodo para remover o usuario e criar um registro na tabela de usuario_excluido
	 * @param usuario usuario para ser removido
	 * Caso nao consiga excluir o usuario do banco
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 */
	public void removerUsuario(Usuario usuario) throws SecurityException, InstantiationException, IllegalAccessException, NoSuchFieldException {
		remover(Usuario.class, usuario.getId());
		UsuarioExcluido usuarioExcluido = new UsuarioExcluido();
		usuarioExcluido.setAdmin(usuario.getAdmin());
		usuarioExcluido.setArea(usuario.getArea());
		usuarioExcluido.setDataExclusao(new Date());
		usuarioExcluido.setEmail(usuario.getEmail());
		usuarioExcluido.setLogin(usuario.getLogin());
		usuarioExcluido.setNome(usuario.getNome());
		usuarioExcluido.setPrimeiroAcesso(usuario.getPrimeiroAcesso());
		usuarioExcluido.setRegional(usuario.getRegional());
		usuarioExcluido.setResponsavel(usuario.getResponsavel());
		usuarioExcluido.setSenha(usuario.getSenha());
		usuarioExcluido.setTelefone(usuario.getTelefone());
		usuarioExcluidoService.inserir(usuarioExcluido);
	}
	
	/**
	 * Metodo para buscar as permissoes, grupo e permissoes dos grupos de um usuario utilizando somente uma consulta
	 * @param usuario Usuario para ser pesquisado
	 * @return Objeto Usuario com suas dependencias (Permissoes, Grupos e Permissoes do Grupo) totalmente populadas
	 */
	public Usuario buscarUsuariosFetch(Usuario usuario) {
		return usuarioDAO.buscarUsuariosFetch(usuario);
	}

	/**
	 * Metodo para buscar os dados dos usuarios para serem apresentados na tela inicial.
	 * @return Todas as permissoes do usuario, todos os grupos do usuario e todas as permissoes dos grupos do usuario
	 */
	public List<Usuario> buscarTodosUsuariosFetch() {
		return usuarioDAO.buscarTodosUsuariosFetch();
	}

}
