package br.com.visent.metricview.selenium.usuario;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;

import br.com.visent.metricview.selenium.core.BaseTestCase;
import br.com.visent.metricview.selenium.util.Constantes;

@RunWith(Parameterized.class)
public class IncluirUsuarioSystemTest extends BaseTestCase{
	
	private static IncluirUsuarioPage incluirUsuarioPage;
	
	//mensagens
	
	//dados
	String 	  	   login;
	String    	   nome;
	String    	   area;
	String    	   email;
	String    	   telefone;
	String    	   regional;
	String    	   responsavel;
	String		   loginExistente;
	String		   emailExistente;
	String 		   loginEmailMascara;
	String 		   emailMascara;
	String 		   quantidadeMaximaLogin;
	String 		   quantidadeMaximaEmail;
	String 		   quantidadeMaximaNome;
	
	public IncluirUsuarioSystemTest(String _login, String _nome, 
							   String _email, String _telefone, String _area, String _regional, 
							   String _responsavel, String _loginExistente, String _emailExistente,
							   String _loginEmailMascara, String _emailMascara, String _quantidadeMaximaLogin,
							   String _quantidadeMaximaEmail, String _quantidadeMaximaNome){
		
		this.login       	       = _login;
		this.nome        	       = _nome;
		this.area        	   	   = _area;
		this.email       	   	   = _email;
		this.telefone    	   	   = _telefone;
		this.regional 	 	   	   = _regional;
		this.responsavel 	   	   = _responsavel;
		this.loginExistente    	   = _loginExistente;
		this.emailExistente    	   = _emailExistente;
		this.loginEmailMascara 	   = _loginEmailMascara;
		this.emailMascara  	   	   = _emailMascara;
		this.quantidadeMaximaLogin = _quantidadeMaximaLogin;
		this.quantidadeMaximaEmail = _quantidadeMaximaEmail;
		this.quantidadeMaximaNome  = _quantidadeMaximaNome;
	}

	@BeforeClass
	public static void setUp(){
		loginPage.logOut();
		loginPage.login(Constantes.SYSTEM, Constantes.SENHASYSTEM);
		incluirUsuarioPage = new IncluirUsuarioPage(driver);
	}
	/**
	 * Cenario o usuario preenche o formulario com o campo nome com caractere especial e salva e fechar.
	 * RESULTADO ESPERADO: sistema deve exibir a mensagem "* Não é permitido caracter especial."
	 */
	@Test
	public void caractereEspecialNomeSalvar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("logincaractere");
		
		loginPage.acessarPage();
		
		incluirUsuarioPage.inserirUsuario("logincaractere", "Lea'teste'dro", emailMascara, telefone, area, regional, responsavel); //faz a inclusao com o mesmo login para a validação
		
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade(); // captura a mensagem de obrigatoriedade
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("logincaractere");
	}

	/**
	 * Cenario o usuario preenche o formulario com o campo nome com caractere especial e salva e fechar.
	 * RESULTADO ESPERADO: sistema deve exibir a mensagem "* Não é permitido caracter especial."
	 */
	@Test
	public void caractereEspecialNomeSalvarFechar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("logincaractere");
		
		loginPage.acessarPage();
		
		incluirUsuarioPage.inserirUsuario("logincaractere", "Lea'teste'dro", emailMascara, telefone, area, regional, responsavel); //faz a inclusao com o mesmo login para a validação
		
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade(); // captura a mensagem de obrigatoriedade
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("logincaractere");
	}

	/**
	 * Cenario o usuario preenche o formulario com o campo login com caractere especial e salva e fechar.
	 * RESULTADO ESPERADO: sistema deve exibir a mensagem "* Não é permitido caracter especial."
	 */
	@Test
	public void caractereEspecialLoginSalvarFechar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("teste@%$#@TESTE");
		
		loginPage.acessarPage();

		incluirUsuarioPage.inserirUsuario("teste@%$#@TESTE", "Leandro", emailMascara, telefone, area, regional, responsavel); //faz a inclusao com o mesmo login para a validação
		
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade(); // captura a mensagem de obrigatoriedade
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("teste@%$#@TESTE");
	}
	/**
	 * Cenario o usuario preenche o formulario com o campo login com caractere especial e salva.
	 * RESULTADO ESPERADO: sistema deve exibir a mensagem "* Não é permitido caracter especial."
	 */
	@Test
	public void caractereEspecialLoginSalvar(){
		

		incluirUsuarioPage.excluirUsuarioPeloBanco("teste@%$#@TESTE");
		
		loginPage.acessarPage();
		
		incluirUsuarioPage.inserirUsuario("teste@%$#@TESTE", "Leandro", emailMascara, telefone, area, regional, responsavel); //faz a inclusao com o mesmo login para a validação
		
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade(); // captura a mensagem de obrigatoriedade
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("teste@%$#@TESTE");
	}
	/**
	 * Esse cenario o usuario preenche um email sem mascara e acionia o comando salvar e fechar.
	 * RESULTADO ESPERADO: Sistema nao deve permitir cadastrar usuario sem mascara no email.
	 */
	@Test
	public void emailSemMascaraSalvarFechar(){
		

		incluirUsuarioPage.excluirUsuarioPeloBanco(loginEmailMascara);
		
		loginPage.acessarPage();

		incluirUsuarioPage.inserirUsuario(loginEmailMascara, "Leandro", emailMascara, telefone, area, regional, responsavel); //faz a inclusao com o mesmo login para a validação

		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade(); // captura a mensagem de obrigatoriedade
		
		if(incluirUsuarioPage.consultaRegistroNoBanco("login", loginEmailMascara)){
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: "+ loginEmailMascara);
		}
		Assert.assertEquals("* O campo em destaque foi preenchido de forma inválida.", mensagem);

		incluirUsuarioPage.excluirUsuarioPeloBanco(loginEmailMascara);
	}
	/**
	 * Esse cenario o usuario preenche um email sem mascara e acionia o comando salvar.
	 * RESULTADO ESPERADO: Sistema nao deve permitir cadastrar usuario sem mascara no email.
	 */
	@Test
	public void emailSemMascara(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("Syndra");
		
		loginPage.acessarPage();

		String novoLogin = "Syndra";
		incluirUsuarioPage.inserirUsuario(novoLogin, "Syndra", "Syndravisent.com.br", telefone, area, regional, responsavel); //faz a inclusao com o mesmo login para a validação
		
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade(); // captura a mensagem de obrigatoriedade

		if(incluirUsuarioPage.consultaRegistroNoBanco("login", novoLogin)){
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: "+ novoLogin);
		}	
		Assert.assertEquals("* O campo em destaque foi preenchido de forma inválida.", mensagem);

		incluirUsuarioPage.excluirUsuarioPeloBanco("Syndra");
	}
	/**
	 * Esse cenario o usuario fecha a modal.
	 * RESULTADO ESPERADO: Sistema deve retornar para a tela anterior.
	 */
//	@Test
	public void fecharModal(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("ben10");
		
		loginPage.acessarPage();
		
		String novoLogin = "ben10";
		incluirUsuarioPage.inserirUsuario(novoLogin, "Ben10", "ben10@visent.com.br", telefone, area, regional, responsavel); //incluir um registro original
		
		driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-closethick']")).click();    //fechar
		
		String titulotela = driver.findElement(By.xpath("//h1[contains(text(),'Manutenção')]")).getText();
		
		if(incluirUsuarioPage.consultaRegistroNoBanco("login", novoLogin)){
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: "+ novoLogin);
		}
		Assert.assertEquals(titulotela, "Manutenção");

		incluirUsuarioPage.excluirUsuarioPeloBanco("ben10");
	}
	/**
	 * Esse cenario o usuário preenche os dados e aciona o botao cancelar.
	 * RESUTLADO ESPERADO: Sistema deve voltar para a tela anterior
	 */
	@Test
	public void canelarModal(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("hannibal");
		
		loginPage.acessarPage();
		
		String novoLogin = "hannibal";
		incluirUsuarioPage.inserirUsuario(novoLogin, "Hannibal Lecter", "hannibal@visent.com.br", telefone, area, regional, responsavel); //incluir um registro original
		
		incluirUsuarioPage.clicaNoBotaoCancelar();
		
		String titulotela = driver.findElement(By.xpath("//h1[contains(text(),'Manutenção')]")).getText();
		
		if(incluirUsuarioPage.consultaRegistroNoBanco("login", novoLogin)){
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: "+ novoLogin);
		}	
		Assert.assertEquals(titulotela, "Manutenção");
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("hannibal");
	}
	/**
	 * Esse cenario o usuário informa no cadastro um email ja existente e aciona o comando Salvar e Fechar.
	 * RESULTADO ESPERADO: Sistema não deve permitir cadastrar 2 email iguais.
	 */
	@Test
	public void emailExistenteSalvarFechar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("obiwan");
		
		loginPage.acessarPage();
		
		String novoEmail = "Obiwan@visent.com.br";
		incluirUsuarioPage.inserirUsuarioBuilder("Obiwan", "Leonardo", novoEmail, telefone, area, regional, responsavel); //incluir um registro original
	
		incluirUsuarioPage.inserirUsuario("leandro", "Leandro", novoEmail, telefone, area, regional, responsavel); //faz a inclusao com o mesmo login para a validação
		
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso(); //captura mensagem de sucesso.
		
		if(incluirUsuarioPage.consultaQuantidadeDeRegistroNoBanco("email", novoEmail)>1){
			Assert.fail("Sistema permitiu incluir email ja cadastrado!"+ novoEmail);
		}
		Assert.assertEquals("E-mail já cadastrado !", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("obiwan");
	}
	/**
	 * Esse cenario o usuário informa no cadastro um email ja existente e aciona o comando Salvar.
	 * RESULTADO ESPERADO: Sistema não deve permitir cadastrar 2 email iguais.
	 */
	@Test
	public void emailExistente(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("hashirama");
		
		loginPage.acessarPage();
		
		String novoEmail = "hashirama@visent.com.br";
		incluirUsuarioPage.inserirUsuarioBuilder("hashirama", "Hashirama Hokage", novoEmail, telefone, area, regional, responsavel); //incluir um registro original
	
		incluirUsuarioPage.inserirUsuario("nagato", "Nagato", novoEmail, telefone, area, regional, responsavel); //faz a inclusao com o mesmo login para a validação
		
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); //captura a mensagem de sucesso
		Assert.assertEquals("E-mail já cadastrado !", mensagem);

		if(incluirUsuarioPage.consultaQuantidadeDeRegistroNoBanco("email", novoEmail)>1){
			Assert.fail("Sistema incluiu emial ja cadastrado!: " + novoEmail);
		}	
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("hashirama");
	}

	/**
	 * Cenario o usuario preenche o minimo de caractere dos campos a aciona o botao salvar e fechar
	 * RESULTADO ESPERADO: Sistema deve apresentar a mensagem "* Informe mais de 6 caractere(s)." e nao permitir salvar no banco
	 */
	@Test
	public void minimoCaractereSalvarFechar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("teste");
		
		loginPage.acessarPage();
		String novoLogin = "TESTE";
		incluirUsuarioPage.inserirUsuario(novoLogin, nome, email, telefone, area, regional, responsavel);
		
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade();// captura a mensagem de obrigatoriedade

		if(incluirUsuarioPage.consultaRegistroNoBanco("login", novoLogin)){
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: "+ novoLogin);
		}	
		Assert.assertEquals("* Informe mais de 6 caractere(s).", mensagem);

		incluirUsuarioPage.excluirUsuarioPeloBanco("teste");
	}
	
	/**
	 * Cenario o usuario preenche o minimo de caractere dos campos a aciona o botao salvar e fechar
	 * RESULTADO ESPERADO: Sistema deve apresentar a mensagem "* Informe mais de 6 caractere(s)." e nao permitir salvar no banco
	 */
	@Test
	public void minimoCaractere(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("teste");
		
		loginPage.acessarPage();

		String novoLogin = "TESTE";
		incluirUsuarioPage.inserirUsuario("TESTE", nome, email, telefone, area, regional, responsavel);
		
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade(); // captura a mensagem de obrigatoriedade

		if(incluirUsuarioPage.consultaRegistroNoBanco("login", novoLogin)){
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: "+ novoLogin);
		}	
		Assert.assertEquals("* Informe mais de 6 caractere(s).", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("teste");
	}
	
	/**
	 * Esse cenario usuário preenche o formulario e deixa o campo email em branco com o botao Salvar e Fechar.
	 * RESULTADO ESPERADO: sistema deve exibir mensagem de obrigatoriedade e nao deixar o registro ser incluso no banco.
	 */
	@Test
	public void obrigatoriedadeEmailSalvarFechar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		
		loginPage.acessarPage();
		
		incluirUsuarioPage.inserirUsuario(login, nome, "", telefone, area, regional, responsavel);
		
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade();

		if(incluirUsuarioPage.consultaRegistroNoBanco("login", login)){
			Assert.fail("Sistema incluiu no banco indevidamente o email!");
		}
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
	}
	/**
	 * Esse cenario usuário preenche o formulario e deixa o campo nome em branco com o botao Salvar e Fechar.
	 * RESULTADO ESPERADO: sistema deve exibir mensagem de obrigatoriedade e nao deixar o registro ser incluso no banco.
	 */
	@Test
	public void obrigatoriedadeNomeSalvarFechar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		
		loginPage.acessarPage();
		 
		incluirUsuarioPage.inserirUsuario(login, "", email, telefone, area, regional, responsavel);
		
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade();

		if(incluirUsuarioPage.consultaRegistroNoBanco("login", login)){
			Assert.fail("Sistema incluiu no banco indevidamente o Nome! ");
		}
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
	}
	/**
	 * Esse cenario usuário preenche o formulario e deixa o campo login em branco com o botao Salvar e Fechar.
	 * RESULTADO ESPERADO: sistema deve exibir mensagem de obrigatoriedade e nao deixar o registro ser incluso no banco.
	 */
	@Test
	public void obrigatoriedadeLoginSalvarFechar(){
		
		loginPage.acessarPage();
 
		incluirUsuarioPage.inserirUsuario("", nome, email, telefone, area, regional, responsavel);
		
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade();

		if(incluirUsuarioPage.consultaRegistroNoBanco("login", "")){
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: "+ "");
		}
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}
	/**
	 * Esse cenario usuário preenche o formulario e deixa o campo email em branco.
	 * RESULTADO ESPERADO: sistema deve exibir mensagem de obrigatoriedade e nao deixar o registro ser incluso no banco.
	 */
	@Test
	public void obrigatoriedadeEmail(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		
		loginPage.acessarPage();
		
		incluirUsuarioPage.inserirUsuario(login, nome, "", telefone, area, regional, responsavel);
		
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade();

		if(incluirUsuarioPage.consultaRegistroNoBanco("login", login)){
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: " + login);
		}
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
	}
	/**
	 * Esse cenario usuário preenche o formulario e deixa o campo nome em branco.
	 * RESULTADO ESPERADO: sistema deve exibir mensagem de obrigatoriedade e nao deixar o registro ser incluso no banco.
	 */
	@Test
	public void obrigatoriedadeNome(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		
		loginPage.acessarPage();
		 
		incluirUsuarioPage.inserirUsuario(login, "", email, telefone, area, regional, responsavel);
		
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade();

		if(incluirUsuarioPage.consultaRegistroNoBanco("login", login)){
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: "+ login);
		}
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
	}
	/**
	 * Esse cenario usuário preenche o formulario e deixa o campo login em branco.
	 * RESULTADO ESPERADO: sistema deve exibir mensagem de obrigatoriedade e nao deixar o registro ser incluso no banco.
	 */
	@Test
	public void obrigatoriedadeLogin(){
		
		loginPage.acessarPage();
 
		incluirUsuarioPage.inserirUsuario("", nome, email, telefone, area, regional, responsavel);
		
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemObrigatoriedade();

		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}
	/**
	 * Esse cenario o usuário preenche o campo login com um ja existente e aciona o botao Salvar e fechar
	 * RESULTADO ESPERADO: Sistema nao deve permitir incluir 2 usuarios com mesmo login.
	 */
	@Test
	public void loginExistenteSalvarFechar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("charles");
		
		loginPage.acessarPage();
 
		String novoLogin = "charles";
		incluirUsuarioPage.inserirUsuarioBuilder(novoLogin, "Charles Xavier", "xarier@visent.com.br", telefone, area, regional, responsavel); //incluir um registro original
	
		incluirUsuarioPage.inserirUsuario(novoLogin, "Charles Xavier", "xarier@visent.com.br", telefone, area, regional, responsavel); //faz a inclusao com o mesmo login para a validação
		
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso();

		if(incluirUsuarioPage.consultaQuantidadeDeRegistroNoBanco("login",novoLogin)>1){
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: "+ novoLogin);
		}	
		Assert.assertEquals("Login já cadastrado !", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("charles");
	}
	/**
	 * Esse cenario o usuário preenche o campo login com um ja existente e aciona o botao Salvar
	 * RESULTADO ESPERADO: Sistema nao deve permitir incluir 2 usuarios com mesmo login.
	 */
	@Test
	public void loginExistente(){
		incluirUsuarioPage.excluirUsuarioPeloBanco("desmond");
		
		loginPage.acessarPage();
		 
		String novoLogin = "desmond";
		incluirUsuarioPage.inserirUsuarioBuilder(novoLogin, "Desmond Miles", "desmond@visent.com.br", telefone, area, regional, responsavel); //incluir um registro original
	
		incluirUsuarioPage.inserirUsuario(novoLogin, "Desmond Miles", "desmond@visent.com.br", telefone, area, regional, responsavel); //faz a inclusao com o mesmo login para a validação
		
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso();

		if(incluirUsuarioPage.consultaQuantidadeDeRegistroNoBanco("login", novoLogin)>1){
			Assert.fail("Sistema incluiu login ja cadastrado no banco: "+ novoLogin);
		}
		Assert.assertEquals("Login já cadastrado !", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("desmond");
	}

	/**
	 *  Esse cenario o ator faz o cadastro de um usuario e verifica se o mesmo está no banco de dados.
	 *  RESULTADO ESPERADO: sistema deve cadastrar o usuário.
	 */
	@Test
	public void incluirUsuarioSalvarFechar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("renato");
		
		loginPage.acessarPage();
		
		String loginSalva  = "renato";
		String nomeSalvar  = "Renato Careca";
		String emailSalvar = "renato@visent.com.br";
		incluirUsuarioPage.inserirUsuario(loginSalva, nomeSalvar, emailSalvar, telefone, area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso();
		
		if(!incluirUsuarioPage.validanoBanco(loginSalva, nomeSalvar, emailSalvar, telefone, area, regional, responsavel)){
			Assert.fail("Sistema não incluiu o registro:" + loginSalva + " no banco." );
		}
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("renato");
	}
	
	/**
	 * Esse cenario o ator faz o cadastro de um usuario e verifica se o mesmo está no banco de dados.
	 * RESULTADO ESPERADO: sistema deve cadastrar o usuário.
	 */
	@Test
	public void incluirUsuarioSalvar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		
		loginPage.acessarPage();
 
		incluirUsuarioPage.inserirUsuario(login, nome, email, telefone, area, regional, responsavel);
		
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso();
		
		if(!incluirUsuarioPage.validanoBanco(login, nome, email, telefone, area, regional, responsavel)){
			Assert.fail("Sistema não incluiu o registro:" + login + " no banco." );
		}
		
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
	}
	/**
	 * Usuario tenta cadastrar mais de 2 administradores e aciona o botao salvar.
	 * RESULTADO ESPERADO: Sistema não deve permitir cadastrar mais de 2 administradores
	 */
	@Test
	public void incluirUsuarioAdmSalvar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(quantidadeMaximaLogin);
		
		loginPage.acessarPage();
		 
		incluirUsuarioPage.inserirUsuario(quantidadeMaximaLogin, quantidadeMaximaNome, quantidadeMaximaEmail, telefone, area, regional, responsavel);
		 
		driver.findElement(By.id("adminUsuario")).click();
		
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso();
		
		if(!incluirUsuarioPage.validanoBanco(quantidadeMaximaLogin, quantidadeMaximaNome, quantidadeMaximaEmail, telefone, area, regional, responsavel)){
			Assert.fail("Sistema incluiu o registro indevidamente: " + quantidadeMaximaEmail + " no banco." );
		}
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(quantidadeMaximaLogin);
	}
	/**
	 * Usuario tenta cadastrar mais de 2 administradores e aciona o botao salvar e fechar.
	 * RESULTADO ESPERADO: Sistema não deve permitir cadastrar mais de 2 administradores
	 */
	@Test
	public void incluirUsuarioAdmSalvarFechar(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		
		loginPage.acessarPage();
		
		incluirUsuarioPage.inserirUsuario(login, nome, email, telefone, area, regional, responsavel);
		
		driver.findElement(By.id("adminUsuario")).click();
		
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso();
		
		if(!incluirUsuarioPage.validanoBanco(login, nome, email, telefone, area, regional, responsavel)){
			Assert.fail("Sistema não incluiu o registro:" + login + " no banco." );
		}
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
	}
	@Parameters
	public static List<Object[]>datadriven(){
		return Arrays.asList(new Object[][]{
			{"rafaelsilva4", "Rafael Nascimento4", "testeMetric1@visent.com.br4", "879285539", "TESTE AREA", "Claro DF", "TESTE RESPONSAVEL", "rafael", "rafaelsilva@visent.com.br", "carlos", "leovisent.com.br", "maxmilianus", "maximus@visent.com.br", "max"},
		});
	}
	
	@AfterClass
	public static void tearDown(){

	}
}
