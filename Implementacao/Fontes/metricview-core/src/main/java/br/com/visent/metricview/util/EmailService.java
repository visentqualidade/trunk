package br.com.visent.metricview.util;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.com.visent.componente.dwr.util.SessaoUtil;
import br.com.visent.componente.log.Log;
import br.com.visent.componente.util.PropertiesUtil;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.service.LogService;


public class EmailService implements Runnable{
	
	public static final String EMAIL_RESETAR_SENHA = "email_resetar_senha";
	public static final String EMAIL_CADASTRO_USUARIO = "email_cadastro_usuario";
	
	private Usuario usuarioParaEmail;
	private String tipoEmail;
	private LogService logService = new LogService();
	private String url;
	
	public EmailService(Usuario usuario , String tipoEmail, String url) {
		setUsuarioParaEmail(usuario);
		setTipoEmail(tipoEmail);
		setUrl(url);
	}
	
	/**
	 * Metodo para enviar o email utilizando uma Thread para nao atrapalhar a Thread principal com possiveis gargalos
	 * @param usuario Usuario que ira receber o email
	 * @param string Tipo de email que se deseja enviar
	 */
	public static void enviarEmail(Usuario usuario , String tipoEmail){
		String url = "http://"+SessaoUtil.getRequest().getServerName()+":"+SessaoUtil.getRequest().getServerPort()+SessaoUtil.getRequest().getContextPath();
		new Thread(new EmailService(usuario , tipoEmail , url)).start();
	}

	/**
	 * Metodo para configurar o email na hora de envia-lo
	 * @param email Email para ser configurado
	 * @throws EmailException caso ocorra algum erro no commons mail
	 */
	private void configure(Email email) throws EmailException {
		if(Boolean.parseBoolean(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_AUTENTICACAO))){
			email.setAuthentication(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_LOGIN), PropertiesUtil.getConfig(ConstantesUtil.EMAIL_SENHA));
			email.setSSLOnConnect(Boolean.parseBoolean(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_SSL_MAIL)));
			email.setStartTLSEnabled(Boolean.parseBoolean(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_TSL_MAIL)));
		}
		email.setFrom(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_FROM));
		email.setHostName(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_HOST_NAME));
		email.setSubject(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_ASSUNTO));
		email.setMsg(criarCorpoMensagem());
		email.setSmtpPort(Integer.parseInt(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_PORTA)));
		
		// Escolhe o charSet UTF-8 devido aos acentos
		email.setCharset("UTF-8");
	}
	
	/**
	 * Metodo para criar a mensagem que sera enviada
	 * @return Mensagem para ser enviada
	 */
	private String criarCorpoMensagem() {
		Usuario usuario = getUsuarioParaEmail();
		StringBuffer msg = new StringBuffer();
		msg.append("<html> <head> </head> <body>");
		if(EmailService.EMAIL_CADASTRO_USUARIO.equals(getTipoEmail())){
			msg.append(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_MENSAGEM_CADASTRO_USUARIO , usuario.getLogin() , usuario.getLogin() , getUrl()));
		} else if(EmailService.EMAIL_RESETAR_SENHA.equals(getTipoEmail())){
			msg.append(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_MENSAGEM_RESETAR_SENHA));
		}
		msg.append("</body></html>");
		return msg.toString();
	}

	/**
	 * Metodo para enviar o email para o usuario
	 * @param email Email devidamente configurado
	 * @throws EmailException caso ocorra algum erro no commons mail
	 */
	private void enviarEmail(Email email) throws EmailException {
		configure(email);
		email.addTo(getUsuarioParaEmail().getEmail());
		email.send();
	}

	@Override
	public void run() {
		if(!Boolean.parseBoolean(PropertiesUtil.getConfig(ConstantesUtil.EMAIL_DESABILITAR_ENVIO_EMAIL))){
			enviarEmailRun();
		}
	}
	
	/**
	 * Metodo para chamar o run da Thread
	 */
	private void enviarEmailRun() {
		try {
			HtmlEmail email = new HtmlEmail();
			enviarEmail(email);
		} catch (EmailException e) {
			e.printStackTrace();
			logService.gravarLogArquivo(PropertiesUtil.getMessage(ConstantesUtil.KEY_MESSAGE_MENSAGEM_LOG_ERRO_ENVIAR_EMAIL, getUsuarioParaEmail().getEmail()), Log.INFO, ConstantesUtil.NOME_SISTEMA, "EmailService");
		}
	}

	public Usuario getUsuarioParaEmail() {
		return usuarioParaEmail;
	}

	public void setUsuarioParaEmail(Usuario usuarioParaEmail) {
		this.usuarioParaEmail = usuarioParaEmail;
	}

	public String getTipoEmail() {
		return tipoEmail;
	}

	public void setTipoEmail(String tipoEmail) {
		this.tipoEmail = tipoEmail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
