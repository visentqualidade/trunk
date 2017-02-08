package br.com.visent.metricview.autenticador;

import java.io.Serializable;
import java.util.Date;

import br.com.visent.metricview.entidade.Usuario;

public class RegistroLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private String token;
	private String scriptSession;
	
	public RegistroLogin(Usuario usuario , String idSessao) {
		setUsuario(usuario);
		setToken(criarToken(usuario));
		setScriptSession(idSessao);
	}
	
	private String criarToken(Usuario usuario) {
		return usuario.getNome().hashCode()+""+(new Date().getTime());
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getScriptSession() {
		return scriptSession;
	}

	public void setScriptSession(String scriptSession) {
		this.scriptSession = scriptSession;
	}
	
}
