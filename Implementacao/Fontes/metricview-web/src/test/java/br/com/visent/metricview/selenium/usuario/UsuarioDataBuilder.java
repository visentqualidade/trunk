package br.com.visent.metricview.selenium.usuario;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.visent.metricview.entidade.Usuario;

public class UsuarioDataBuilder {

	private Usuario usuario = null;
	
	static {
		Fixture.of(Usuario.class).addTemplate("valido", new Rule(){
			{
//				add("idUsuario", random(Long.class, range(1L, 2000L)));
				add("login", "userteste");
				add("nome", "teste metricview");
				add("email", "userteste@visent.com.br");
				add("senha", "userteste");
				add("telefone", "879285539");
				add("area", "12");
				add("regional", "Claro DF");
				add("responsavel", "DEV");
			}
		});
	}
	
	public UsuarioDataBuilder() {
		usuario = Fixture.from(Usuario.class).gimme("valido");
	}
	
	public UsuarioDataBuilder comLogin(String login){
		usuario.setLogin(login);
		
		return this;
	}
	public UsuarioDataBuilder comNome(String nome){
		usuario.setNome(nome);
		
		return this;
	}
	public UsuarioDataBuilder comSenha(String senha){
		usuario.setSenha(senha);
		
		return this;
	}
	public UsuarioDataBuilder comEmail(String email){
		usuario.setEmail(email);
		
		return this;
	}
	public UsuarioDataBuilder comArea(String area){
		usuario.setArea(area);
		
		return this;
	}
	public UsuarioDataBuilder comTelefone(String telefone){
		usuario.setTelefone(telefone);
		
		return this;
	}
	public UsuarioDataBuilder comRegional(String regional){
		usuario.setRegional(regional);
		
		return this;
	}
	public UsuarioDataBuilder comResponsavel(String responsavel){
		usuario.setResponsavel(responsavel);
		
		return this;
	}
	public UsuarioDataBuilder comPrimeiroAcesso(boolean primeiroAcesso){
		usuario.setPrimeiroAcesso(primeiroAcesso);
		
		return this;
	}
	
	public UsuarioDataBuilder comAdmin(boolean admin){
		usuario.setAdmin(admin);
		
		return this;
	}
	
	public Usuario constroi(){
		return usuario;
	}
}
