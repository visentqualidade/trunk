package br.com.visent.metricview.autenticador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;

import br.com.visent.componente.dwr.util.SessaoUtil;
import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.service.LogService;
import br.com.visent.metricview.util.ConstantesUtil;

/**
 * Classe responsavel por armazenar os acessos dos usuarios junto com seus tokens
 */
public class ControleUsuarios {

	private static String path;
	private static LogService logService = new LogService();

	private ControleUsuarios() {}

	private static String getPath() {
		if (path == null) {
			ResourceBundle bundle = ResourceBundle.getBundle("metricview");
			path = bundle.getString("dir_controle_usuarios");
		}
		return path;
	}
	
	public static void init() {
		criarDiretorioArquivo(getPath());
		File dir = new File(getPath());
		File[] files = dir.listFiles();
		for (File file : files) {
			file.delete();
		}
	}
	
	/**
	 * Metodo para verificar se o caminho do arquivo esta criado, senao estiver
	 * o sistema ira criar
	 * 
	 * @param arquivo
	 *            String contendo o caminho para o arquivo a ser gerado
	 */
	private static void criarDiretorioArquivo(String diretorio) {
		File file = new File(diretorio);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**
	 * Retorna o {@link RegistroLogin} associado ao usuario
	 * @param usuario
	 * @return
	 */
	public static RegistroLogin getRegistro(String usuario) {
		RegistroLogin registro = null;
		try {
			FileInputStream fis = new FileInputStream(getPathUsuario(usuario));
			ObjectInputStream ois = new ObjectInputStream(fis);
			registro = (RegistroLogin) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
			logService.gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_NAO_ENCONTROU_REGISTRO_LOGIN , usuario), Log.ERROR, ConstantesUtil.NOME_SISTEMA, "ControleUsuarios");
		}
		return registro;
	}
	
	/**
	 * Remove o usuario da sessao e apaga o registro de login no sistema.
	 */
	public static void logout() {
		Usuario usuario = (Usuario) SessaoUtil.getSessao().getAttribute(SessaoUtil.USUARIO);
		File file = new File(getPathUsuario(usuario));
		if (file.exists()) {
			file.delete();
		}
	}
	
	/**
	 * Remove o usuario da sessao e apaga o registro de login no sistema.
	 */
	public static void logout(Usuario usuario) {
		File file = new File(getPathUsuario(usuario));
		if (file.exists()) {
			file.delete();
		}
	}
	
	/**
	 * Adiciona o usuario na sessao e o respectivo registro de login no sistema, SEM apagar o registro antigo, caso exista.
	 * @param usuario Usuario que para ser adicionado
	 */
	public static void addUsuarioSemDeletarRegistro(Usuario usuario) {
		RegistroLogin registroLogin = new RegistroLogin(usuario , SessaoUtil.getId());
		SessaoUtil.setPojo(SessaoUtil.USUARIO, usuario);
		SessaoUtil.setPojo("token", registroLogin.getToken());
		try {
			FileOutputStream fos = new FileOutputStream(getPathUsuario(usuario));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(registroLogin);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			logService.gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_ERRO_ADD_REGISTRO_LOGIN), Log.ERROR, ConstantesUtil.NOME_SISTEMA, "ControleUsuarios");
		}
	}
	
	/**
	 * Adiciona o usuario na sessao e o respectivo registro de login no sistema, apagando o registro antigo, caso exista.
	 * @param usuario Usuario que para ser adicionado
	 */
	public static void addUsuarioDeletandoRegistro(Usuario usuario) {
		RegistroLogin registroLogin = new RegistroLogin(usuario , SessaoUtil.getId());
		SessaoUtil.setPojo(SessaoUtil.USUARIO, usuario);
		SessaoUtil.setPojo("token", registroLogin.getToken());
		deletarLoginAntigo(usuario);
		try {
			FileOutputStream fos = new FileOutputStream(getPathUsuario(usuario));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(registroLogin);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			logService.gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_ERRO_ADD_REGISTRO_LOGIN), Log.ERROR, ConstantesUtil.NOME_SISTEMA, "ControleUsuarios");
		}
	}
	
	private static void deletarLoginAntigo(Usuario usuario) {
		File file = new File(getPathUsuario(usuario));
		if (file.exists()) {
			file.delete();
		}
	}

	private static String getPathUsuario(Usuario usuario) {
		return getPathUsuario(usuario.getLogin());
	}
	
	private static String getPathUsuario(String usuario) {
		return getPath() + File.separator + usuario.toLowerCase() + ".ser";
	}
	
}
