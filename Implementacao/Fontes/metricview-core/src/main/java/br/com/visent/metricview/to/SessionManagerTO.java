package br.com.visent.metricview.to;

import javax.servlet.http.HttpSession;

/**
 * Classe responsavel por agregar as informações do usuario que esta sendo
 * autenticado no sistema. As informações serao: sessao (browser) para que possa
 * ser feito o controle de desconecao do mesmo pelo administrador do sistema e a
 * 
 * OBS.: caso essa classe venha a ser alterada, comentar as alteracoes
 */
public class SessionManagerTO {

	private HttpSession httpSession;
	
	public SessionManagerTO(HttpSession httpSession) {
		setHttpSession(httpSession);
	}
	
	public SessionManagerTO() {}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

}
