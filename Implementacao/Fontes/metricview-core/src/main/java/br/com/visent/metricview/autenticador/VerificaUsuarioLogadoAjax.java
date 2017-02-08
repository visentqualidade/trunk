package br.com.visent.metricview.autenticador;

import br.com.visent.componente.dwr.util.SessaoUtil;
import br.com.visent.metricview.entidade.Usuario;

/**
 *	Classe para verificar se o usuario esta conectado nas aplicacoes. Ela sera utilizada somente nas aplicacoes filhas do metricview
 */
public class VerificaUsuarioLogadoAjax {
	
	/**
	 * Metodo para verificar se o arquivo de login do usuario existe
	 * @return Se o arquivo existir ira retornar true se nao false
	 */
	public Boolean isUsuarioConectado () {
		Usuario usuario = (Usuario) SessaoUtil.getPojo(SessaoUtil.USUARIO);
		RegistroLogin registro = ControleUsuarios.getRegistro(usuario.getLogin());
		return registro != null;
	}

}
