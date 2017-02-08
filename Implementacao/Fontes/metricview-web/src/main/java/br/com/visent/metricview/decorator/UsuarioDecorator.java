package br.com.visent.metricview.decorator;

import br.com.visent.metricview.entidade.Usuario;

/**
 * Classe para representar os usuarios que estao logados e aprensentar na tela de edicao dos dados
 */
public class UsuarioDecorator {

	private Usuario usuario;

	private Boolean isConectado;
	
	@Override
	public String toString() {
		return "UsuarioTO [usuario=" + usuario + ", isConectado=" + isConectado + "]";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getIsConectado() {
		return isConectado;
	}

	public void setIsConectado(Boolean isConectado) {
		this.isConectado = isConectado;
	}

}
