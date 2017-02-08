package br.com.visent.metricview.filter;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.visent.metricview.autenticador.ConstantesAutenticador;
import br.com.visent.metricview.autenticador.ControleUsuarios;
import br.com.visent.metricview.autenticador.RegistroLogin;
import br.com.visent.metricview.entidade.Ferramenta;
import br.com.visent.metricview.entidade.Grupo;
import br.com.visent.metricview.entidade.Permissao;

/**
 * Filtro para verificar se o usuario esta logado no MetricView
 */
public class AutenticadorFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		String moduloAutenticador = session.getServletContext().getInitParameter("modulo_autenticador");
		
		if (Boolean.valueOf(moduloAutenticador)) {
			// se aqui estiver nulo, eh o primeiro acesso e sera verificada a existencia e permissao do usuario
			if (session.getAttribute(ConstantesAutenticador.LOGADO_METRIC_VIEW) == null) {
				if (isUsuarioValido(req)) {
					session.setAttribute(ConstantesAutenticador.LOGADO_METRIC_VIEW, true);
					session.setAttribute(ConstantesAutenticador.USUARIO, req.getParameter(ConstantesAutenticador.USUARIO));
					session.setAttribute(ConstantesAutenticador.TOKEN, req.getParameter(ConstantesAutenticador.TOKEN));
					resp.sendRedirect(req.getRequestURL().toString());
				} else {
					redirectError(resp);
				}
			} else {
				// se nao esta nulo, ainda eh preciso verificar se o login nao esta expirado
				if (isLoginExpirado(session)) {
					session.removeAttribute(ConstantesAutenticador.LOGADO_METRIC_VIEW);
					if(session.getAttribute(ConstantesAutenticador.USUARIO) == null && session.getAttribute(ConstantesAutenticador.TOKEN) == null) {
						redirectError(resp);
					} else {
						session.setAttribute(ConstantesAutenticador.LOGADO_METRIC_VIEW, true);
						session.setAttribute(ConstantesAutenticador.USUARIO, req.getParameter(ConstantesAutenticador.USUARIO));
						session.setAttribute(ConstantesAutenticador.TOKEN, req.getParameter(ConstantesAutenticador.TOKEN));
						chain.doFilter(request, response);
					}
				} else {
					chain.doFilter(request, response);
				}
			}
		} else {
			chain.doFilter(request, response);
		}
		
	}

	private void redirectError(HttpServletResponse resp) throws IOException {
		String enderecoCentral = ResourceBundle.getBundle("metricview").getString("endereco_central");
		resp.sendRedirect(enderecoCentral+"?error=1");
	}

	/**
	 * Verifica se o login esta expirado (caso outra pessoa logue no MetricView) com o mesmo usuario.
	 * Ele eh expirado se nao existir o registro de login ou o token no registro de login eh diferente do setado na sessao.
	 * @param session
	 * @return
	 */
	private boolean isLoginExpirado(HttpSession session) {
		Object usr = session.getAttribute(ConstantesAutenticador.USUARIO);
		if (usr != null) {
			RegistroLogin registro = ControleUsuarios.getRegistro(usr.toString());
			if (registro != null) {
				Object token = session.getAttribute(ConstantesAutenticador.TOKEN);
				return !registro.getToken().equals(token.toString());
			}
		}
		return true;
	}

	/**
	 * Verifica se o usuario eh valido.
	 * Ele eh valido se existir o registro de login do sistema e se possui permissao para a ferramenta.
	 * @param req
	 * @return
	 */
	private boolean isUsuarioValido(HttpServletRequest req) {
		String paramUsuario = req.getParameter(ConstantesAutenticador.USUARIO);
		String paramToken = req.getParameter(ConstantesAutenticador.TOKEN);
		if (paramUsuario == null || paramToken == null) return false;
		
		String context = req.getContextPath();
		RegistroLogin registroLogin = ControleUsuarios.getRegistro(paramUsuario);
		if (registroLogin == null) return false;
		
		Boolean hasPermissao = Boolean.FALSE;
		if (registroLogin != null) {
			if (registroLogin.getToken().equals(paramToken)) {
				for (Permissao permissao : registroLogin.getUsuario().getPermissoes()) {
					Ferramenta ferramenta = permissao.getFerramenta();
					if (ferramenta.getUrl().contains(context)) {
						hasPermissao = Boolean.TRUE;
					}
				}
				if(!hasPermissao){
					for (Grupo grupo : registroLogin.getUsuario().getGrupos()) {
						for (Permissao permissao : grupo.getPermissoes()) {
							Ferramenta ferramenta = permissao.getFerramenta();
							if (ferramenta.getUrl().contains(context)) {
								hasPermissao = Boolean.TRUE;
							}
						}
					}
					
				}
			}
		}
		return hasPermissao;
	}

	public void destroy() {}
}
