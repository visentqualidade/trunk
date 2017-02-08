package br.com.visent.metricview.ajax;

import java.util.List;

import org.hibernate.Hibernate;

import br.com.visent.componente.dwr.util.CriptografiaUtil;
import br.com.visent.componente.dwr.util.SessaoUtil;
import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.corporativo.ajax.GenericAjax;
import br.com.visent.corporativo.exception.CorporativoServiceException;
import br.com.visent.corporativo.exception.UsuarioSemPermissaoException;
import br.com.visent.corporativo.service.GenericService;
import br.com.visent.metricview.autenticador.ControleUsuarios;
import br.com.visent.metricview.entidade.AreaUsuario;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.exception.MetricViewException;
import br.com.visent.metricview.service.LogService;
import br.com.visent.metricview.to.SessionManagerTO;
import br.com.visent.metricview.util.ConstantesUtil;
import br.com.visent.metricview.util.SessionManagerUtil;

public class AlterarDadosAjax extends GenericAjax<Usuario>{
	
	private GenericService genericService = new GenericService();
	protected LogService logService = new LogService();
	
	/**
	 * Metodo para alterar os dados do usuario logado
	 * @param usuario Usuario com os dados alterados
	 * @param senhaAntiga String contendo a senha antiga do usuario
	 * @param informouSenha Flag para verificar se o usuario informou as senhas ou nao
	 * @throws UsuarioSemPermissaoException Caso o usuario esteja tentando alterar outro usuario que nao seja o seu proprio
	 * @throws MetricViewException Caso a senha antiga nao conferir com a senha cadastrada no banco, ou o email ja esteja cadastrado
	 */
	public void atualizarDados (Usuario usuario , String senhaAntiga , Boolean informouSenha) throws CorporativoServiceException , MetricViewException{
		isPermitidoAlterar(usuario , senhaAntiga , informouSenha);
		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setLogin(usuario.getLogin());
		List<Usuario> usuariosBanco = genericService.buscarPorFiltro(usuarioFiltro);
		if(!usuariosBanco.isEmpty()){
			Usuario usuarioBanco = usuariosBanco.get(0);
			popularDadosUsuarioBanco(usuarioBanco , usuario , informouSenha);
			verificarInsericaoNovaArea(usuarioBanco.getArea());
			genericService.atualizar(usuarioBanco);
			Hibernate.initialize(usuarioBanco.getPermissoes());
			ControleUsuarios.addUsuarioSemDeletarRegistro(usuarioBanco);
			SessionManagerUtil.getInstance().atualizarMapaUsuarios(usuarioBanco, new SessionManagerTO(SessaoUtil.getSessao()));
			logService.gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_ALTEROU_DADOS , usuario.getNome()), Log.INFO);
		}
	}
	
	/**
	 * Metodo para alterar os dados do usuario logado no primeiro acesso
	 * @param usuario Usuario com os dados alterados
	 * @param senhaAntiga String contendo a senha antiga do usuario
	 * @throws UsuarioSemPermissaoException Caso o usuario esteja tentando alterar outro usuario que nao seja o seu proprio
	 * @throws MetricViewException Caso a senha antiga nao conferir com a senha cadastrada no banco, ou o email ja esteja cadastrado
	 */
	public void atualizarDadosPrimeiroAcesso (Usuario usuario , String senhaAntiga) throws UsuarioSemPermissaoException , MetricViewException{
		isPermitidoAlterarPrimeiroAcesso(usuario, senhaAntiga);
		Usuario usuarioFiltro = new Usuario();
		usuarioFiltro.setLogin(usuario.getLogin());
		List<Usuario> usuariosBanco = genericService.buscarPorFiltro(usuarioFiltro);
		if(!usuariosBanco.isEmpty()){
			Usuario usuarioBanco = usuariosBanco.get(0);
			popularDadosUsuarioBanco(usuarioBanco , usuario , Boolean.TRUE);
			verificarInsericaoNovaArea(usuarioBanco.getArea());
			genericService.atualizar(usuarioBanco);
			Hibernate.initialize(usuarioBanco.getPermissoes());
			ControleUsuarios.addUsuarioSemDeletarRegistro(usuarioBanco);
			logService.gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_ALTEROU_DADOS , usuario.getNome()), Log.INFO);
		}
	}
	
	/**
	 * Metodo para verificar se e permitido a alteracao do dados do usuario no primeiro acesso
	 * @param usuario Usuario com os dados alterados
	 * @param senhaAntiga String contendo a senha antiga do usuario
	 * @throws UsuarioSemPermissaoException Caso o usuario esteja tentando alterar outro usuario que nao seja o seu proprio
	 * @throws MetricViewException Caso a senha antiga nao conferir com a senha cadastrada no banco, ou o email ja esteja cadastrado
	 */
	private void isPermitidoAlterarPrimeiroAcesso(Usuario usuario , String senhaAntiga) throws UsuarioSemPermissaoException , MetricViewException{
		Usuario usuarioSessao = (Usuario)SessaoUtil.getPojo(SessaoUtil.USUARIO);
		isPermitidoAlterar(usuario, senhaAntiga, Boolean.TRUE);
		if(!usuario.getEmail().equals(usuarioSessao.getEmail())){
			Usuario usuarioFiltro = new Usuario();
			usuarioFiltro.setEmail(usuario.getEmail());
			if(!genericService.buscarPorFiltro(usuarioFiltro).isEmpty()){
				throw new MetricViewException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_EMAIL_JA_CADASTRADO)); 
			}
		}
	}
	
	/**
	 * Metodo para popular o usuario que sera atualizado no banco com as novas informacoes preenchidas na tela
	 * @param usuarioBanco Usuario pesquisado no banco
	 * @param usuario Usuario contendo as informacoes preenchidas na tela
	 * @param informouSenha 
	 */
	private void popularDadosUsuarioBanco(Usuario usuarioBanco , Usuario usuario, Boolean informouSenha) {
		usuarioBanco.setNome(usuario.getNome());
		usuarioBanco.setEmail(usuario.getEmail());
		if(informouSenha){
			usuarioBanco.setSenha(CriptografiaUtil.getInstance().criptografar(usuario.getSenha()));
		}
		usuarioBanco.setPrimeiroAcesso(Boolean.FALSE);
		usuarioBanco.setArea(usuario.getArea());
		usuarioBanco.setRegional(usuario.getRegional());
		usuarioBanco.setResponsavel(usuario.getResponsavel());
		usuarioBanco.setTelefone(usuario.getTelefone());
	}

	/**
	 * Metodo para verificar se e permitido a alteracao do dados do usuario
	 * @param usuario Usuario com os dados alterados
	 * @param senhaAntiga String contendo a senha antiga do usuario
	 * @param informouSenha Flag para verificar se o usuario informou as senhas ou nao
	 * @throws UsuarioSemPermissaoException Caso o usuario esteja tentando alterar outro usuario que nao seja o seu proprio
	 * @throws MetricViewException Caso a senha antiga nao conferir com a senha cadastrada no banco, ou o email ja esteja cadastrado
	 */
	private void isPermitidoAlterar(Usuario usuario, String senhaAntiga , Boolean informouSenha) throws UsuarioSemPermissaoException , MetricViewException{
		Usuario usuarioSessao = (Usuario)SessaoUtil.getPojo(SessaoUtil.USUARIO);
		if(!usuarioSessao.getLogin().equals(usuario.getLogin())){
			throw new UsuarioSemPermissaoException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_USUARIO_NAO_PODE_SER_ALTERADO));
		}
		if(informouSenha){
			if(!usuarioSessao.getSenha().equals(CriptografiaUtil.getInstance().criptografar(senhaAntiga))){
				throw new MetricViewException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_SENHA_ANTIGA_INCORRETA));	
			}
		}
		if(!isEmailValido(usuario)){
			throw new MetricViewException(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_EMAIL_JA_CADASTRADO));
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
	 * Metodo para verificar se ha necessidade de inserir uma nova area ou nao no banco
	 * @param area String contendo as informacoes sobre a area
	 */
	public void verificarInsericaoNovaArea(String area){
		AreaUsuario areaUsuario = new AreaUsuario();
		areaUsuario.setDescricao(area);
		if(areaUsuario.getDescricao().trim().length() != 0 || areaUsuario.getDescricao() != null){
			if(genericService.buscarPorFiltro(areaUsuario).isEmpty()){
				genericService.inserir(areaUsuario);
			}
		}
	}
	
	/**
	 * Metodo para buscar o usuario da sessao
	 * @return Usuario que esta na sessao
	 */
	public Usuario buscarUsuarioSessao(){
		return (Usuario)SessaoUtil.getPojo(SessaoUtil.USUARIO);
	}

}
