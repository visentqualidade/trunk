package br.com.visent.metricview.selenium.usuario;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.metricview.selenium.core.BaseTestCase;

@RunWith(Parameterized.class)
public class IncluirUsuarioTest extends BaseTestCase{
	
	IncluirUsuarioPage incluirUsuarioPage;
	ExcluirUsuarioPage excluirUsuarioPage;
    Properties pFile;
	WebDriverWait wait;

	// mensagem

	String login;
	String nome;
	String area;
	String email;
	String telefone;
	String regional;
	String responsavel;
	String loginExistente;
	String emailExistente;
	String loginEmailMascara;
	String emailMascara;


	public IncluirUsuarioTest(String _login,
			String _nome, String _email, String _telefone, String _area,
			String _regional, String _responsavel, String _loginExistente,
			String _emailExistente, String _loginEmailMascara,
			String _emailMascara) {

		this.login = _login;
		this.nome = _nome;
		this.area = _area;
		this.email = _email;
		this.telefone = _telefone;
		this.regional = _regional;
		this.responsavel = _responsavel;
		this.loginExistente = _loginExistente;
		this.emailExistente = _emailExistente;
		this.loginEmailMascara = _loginEmailMascara;
		this.emailMascara = _emailMascara;
	}

	@Before
	public void setUp() {
		incluirUsuarioPage = new IncluirUsuarioPage(driver);
		excluirUsuarioPage = new ExcluirUsuarioPage(driver);
		wait = new WebDriverWait(driver, 10);
	}

	/**
	 * Cenario o usuario preenche o formulario com o campo nome com caractere
	 * especial e salva e fechar. RESULTADO ESPERADO: sistema deve exibir a
	 * mensagem "* Nao e permitido caracter especial."
	 */
	@Test
	public void caractereEspecialNomeSalvar() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirUsuarioPage.inserirUsuario("logincaractere", "Lea'teste'dro", emailMascara,
				telefone, area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvar();

		Assert.assertEquals("* Não é permitido caracter especial.", incluirUsuarioPage.visualizaMensagem());
	}

	/**
	 * Cenario o usuario preenche o formulario com o campo nome com caractere
	 * especial e salva e fechar. RESULTADO ESPERADO: sistema deve exibir a
	 * mensagem "* Nao e permitido caracter especial."
	 */
	@Test
	public void caractereEspecialNomeSalvarFechar(){

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirUsuarioPage.inserirUsuario("logincaractere", "Lea'teste'dro", emailMascara,
				telefone, area, regional, responsavel); // faz a inclusao com o

		incluirUsuarioPage.clicaNoBotaoSalvareFechar();

		Assert.assertEquals("* Não é permitido caracter especial.", incluirUsuarioPage.visualizaMensagem());
	}

	/**
	 * Cenario o usuario preenche o formulario com o campo login com caractere
	 * especial e salva e fechar. RESULTADO ESPERADO: sistema deve exibir a
	 * mensagem "* Nao e permitido caracter especial."
	 */
	@Test
	public void caractereEspecialLoginSalvarFechar(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirUsuarioPage.inserirUsuario("teste@%$#@TESTE", "Leandro", emailMascara, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvareFechar();

		Assert.assertEquals("* Não é permitido caracter especial.", incluirUsuarioPage.visualizaMensagem());
	}

	/**
	 * Cenario o usuario preenche o formulario com o campo login com caractere
	 * especial e salva. RESULTADO ESPERADO: sistema deve exibir a mensagem
	 * "* Nao e permitido caracter especial."
	 */
	@Test
	public void caractereEspecialLoginSalvar(){

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirUsuarioPage.inserirUsuario("teste@%$#@TESTE", "Leandro", emailMascara, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvar();

		Assert.assertEquals("* Não é permitido caracter especial.", incluirUsuarioPage.visualizaMensagem());
	}

	/**
	 * Esse cenario o usuario preenche um email sem mascara e acionia o comando
	 * salvar e fechar. RESULTADO ESPERADO: Sistema nao deve permitir cadastrar
	 * usuario sem mascara no email.
	 */
	@Test
	public void emailSemMascaraSalvarFechar(){

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirUsuarioPage.inserirUsuario(loginEmailMascara, "Leandro", emailMascara, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvareFechar();

		Assert.assertEquals("* O campo em destaque foi preenchido de forma inválida.", incluirUsuarioPage.visualizaMensagem());

		if (incluirUsuarioPage.consultaRegistroNoBanco("login", loginEmailMascara)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ loginEmailMascara);
		}
	}

	/**
	 * Esse cenario o usuario preenche um email sem mascara e aciona o comando
	 * salvar. RESULTADO ESPERADO: Sistema nao deve permitir cadastrar usuario
	 * sem mascara no email.
	 */
	@Test
	public void emailSemMascara(){
		
		loginPage.acessarPage();
 
		util.clicaNoMenuAdministracao();
		
		String novoLogin = "cassandra";
		incluirUsuarioPage.inserirUsuario(novoLogin, "Cassandra", "cassandravisent.com.br",
				telefone, area, regional, responsavel); 

		incluirUsuarioPage.clicaNoBotaoSalvar();

		Assert.assertEquals("* O campo em destaque foi preenchido de forma inválida.", incluirUsuarioPage.visualizaMensagem());
		
		if (incluirUsuarioPage.consultaRegistroNoBanco("login", novoLogin)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ novoLogin);
		}
	}

	/**
	 * Esse cenario o usuario fecha a modal. RESULTADO ESPERADO: Sistema deve
	 * retornar para a tela anterior.
	 */
	@Test
	public void fecharModal(){

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		String novoLogin = "hannibal";
		incluirUsuarioPage.inserirUsuario(novoLogin, "Hannibal Lecter", "hannibal@visent.com.br",
				telefone, area, regional, responsavel); // incluir um registro
														// original
		util.clicaNoIconeFecharDaModal();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String titulotela = driver.findElement(
				By.xpath("//h1[contains(text(),'Manutenção')]")).getText();
		
		Assert.assertEquals(titulotela, "Manutenção");

		if (incluirUsuarioPage.consultaRegistroNoBanco("login", novoLogin)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ novoLogin);
		}
	}

	/**
	 * Esse cenario o usuario preenche os dados e aciona o botao cancelar.
	 * RESUTLADO ESPERADO: Sistema deve voltar para a tela anterior
	 */
	@Test
	public void canelarModal(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		String novoLogin = "hannibal";
		
		incluirUsuarioPage.inserirUsuario(novoLogin, "Hannibal Lecter", "hannibal@visent.com.br",
				telefone, area, regional, responsavel); // incluir um registro
														// original
		incluirUsuarioPage.clicaNoBotaoCancelar();

		String titulotela = driver.findElement(
				By.xpath("//h1[contains(text(),'Manutenção')]")).getText();

		Assert.assertEquals(titulotela, "Manutenção");
		
		if (incluirUsuarioPage.consultaRegistroNoBanco("login", novoLogin)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuário: "
					+ novoLogin);
		}
	}

	/**
	 * Esse cenario o usuario informa no cadastro um email ja existente e
	 * aciona o comando Salvar e Fechar. RESULTADO ESPERADO: Sistema nao deve
	 * permitir cadastrar 2 email iguais.
	 */
	@Test
	public void emailExistenteSalvarFechar(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		String novoEmail = "leo@visent.com.br";
		
		incluirUsuarioPage.inserirUsuarioBuilder("joaocleber", "Leonardo", novoEmail, telefone, area,
				regional, responsavel); // incluir um registro original

		incluirUsuarioPage.inserirUsuario("leandro", "Leandro", "leo@visent.com.br", telefone,
				area, regional, responsavel); // faz a inclusao com o mesmo
											  // login para a validacao
		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); // captura a mensagem de sucesso

		Assert.assertEquals("E-mail já cadastrado !", mensagem);

		if (incluirUsuarioPage.consultaQuantidadeDeRegistroNoBanco("email", novoEmail) > 1) {
			Assert.fail("Sistema permitiu incluir email ja cadastrado!"
					+ novoEmail);
		}
	}

	/**
	 * Esse cenario o usuario informa no cadastro um email ja existente e
	 * aciona o comando Salvar. RESULTADO ESPERADO: Sistema nÃ£o deve permitir
	 * cadastrar 2 email iguais.
	 */
	@Test
	public void emailExistente(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		String novoEmail = "naruto@visent.com.br";
		incluirUsuarioPage.inserirUsuarioBuilder("naruto", "Uzumaki Naruto", novoEmail, telefone, area,
				regional, responsavel); // incluir um registro original

		incluirUsuarioPage.inserirUsuario("nagato", "Nagato", "naruto@visent.com.br", telefone,
				area, regional, responsavel);
											
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));

		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); // captura a mensagem de sucesso
		Assert.assertEquals("E-mail já cadastrado !", mensagem);

		if (incluirUsuarioPage.consultaQuantidadeDeRegistroNoBanco("email", novoEmail) > 1) {
			Assert.fail("Sistema incluiu emial ja cadastrado!: " + novoEmail);
		}
	}

	/**
	 * Cenario o usuario preenche o minimo de caractere dos campos a aciona o
	 * botao salvar e fechar RESULTADO ESPERADO: Sistema deve apresentar a
	 * mensagem "* Informe mais de 6 caractere(s)." e nao permitir salvar no
	 * banco
	 */
	@Test
	public void minimoCaractereSalvarFechar(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		String novoLogin = "TESTE";
		incluirUsuarioPage.inserirUsuario(novoLogin, nome, email, telefone, area, regional,
				responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		Assert.assertEquals("* Informe mais de 6 caractere(s).", incluirUsuarioPage.visualizaMensagem());

		if (incluirUsuarioPage.consultaRegistroNoBanco("login", novoLogin)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ novoLogin);
		}
	}

	/**
	 * Cenario o usuario preenche o minimo de caractere dos campos a aciona o
	 * botao salvar e fechar RESULTADO ESPERADO: Sistema deve apresentar a
	 * mensagem "* Informe mais de 6 caractere(s)." e nao permitir salvar no
	 * banco
	 */
	@Test
	public void minimoCaractere(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		String novoLogin = "TESTE";
		incluirUsuarioPage.inserirUsuario("TESTE", nome, email, telefone, area, regional,
				responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		Assert.assertEquals("* Informe mais de 6 caractere(s).", incluirUsuarioPage.visualizaMensagem());

		if (incluirUsuarioPage.consultaRegistroNoBanco("login", novoLogin)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ novoLogin);
		}
	}

	/**
	 * Esse cenario usuario preenche o formulario e deixa o campo email em
	 * branco com o botao Salvar e Fechar. RESULTADO ESPERADO: sistema deve
	 * exibir mensagem de obrigatoriedade e nao deixar o registro ser incluso no
	 * banco.
	 */
	@Test
	public void obrigatoriedadeEmailSalvarFechar(){

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuario(login, nome, "", telefone, area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvareFechar();	
		
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", incluirUsuarioPage.visualizaMensagem());

		if (incluirUsuarioPage.consultaRegistroNoBanco("login", login)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ login);
		}
	}

	/**
	 * Esse cenario usuario preenche o formulario e deixa o campo nome em
	 * branco com o botao Salvar e Fechar. RESULTADO ESPERADO: sistema deve
	 * exibir mensagem de obrigatoriedade e nao deixar o registro ser incluso no
	 * banco.
	 */
	@Test
	public void obrigatoriedadeNomeSalvarFechar(){

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuario(login, "", email, telefone, area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", incluirUsuarioPage.visualizaMensagem());

		if (incluirUsuarioPage.consultaRegistroNoBanco("login", login)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ login);
		}
	}

	/**
	 * Esse cenario usuario preenche o formulario e deixa o campo login em
	 * branco com o botao Salvar e Fechar. RESULTADO ESPERADO: sistema deve
	 * exibir mensagem de obrigatoriedade e nao deixar o registro ser incluso no
	 * banco.
	 */
	@Test
	public void obrigatoriedadeLoginSalvarFechar(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuario("", nome, email, telefone, area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", incluirUsuarioPage.visualizaMensagem());

		if (incluirUsuarioPage.consultaRegistroNoBanco("login", "")) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ "");
		}
	}

	/**
	 * Esse cenario usuario preenche o formulario e deixa o campo email em
	 * branco. RESULTADO ESPERADO: sistema deve exibir mensagem de
	 * obrigatoriedade e nao deixar o registro ser incluso no banco. 
	 */
	@Test
	public void obrigatoriedadeEmail(){

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();

		incluirUsuarioPage.inserirUsuario(login, nome, "", telefone, area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvar();

		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", incluirUsuarioPage.visualizaMensagem());

		if (incluirUsuarioPage.consultaRegistroNoBanco("login", login)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ login);
		}
	}

	/**
	 * Esse cenario usuario preenche o formulario e deixa o campo nome em
	 * branco. RESULTADO ESPERADO: sistema deve exibir mensagem de
	 * obrigatoriedade e nao deixar o registro ser incluso no banco.
	 */
	@Test
	public void obrigatoriedadeNome(){

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuario(login, "", email, telefone, area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvar();

		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", incluirUsuarioPage.visualizaMensagem());
		
		if (incluirUsuarioPage.consultaRegistroNoBanco("login", login)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ login);
		}
	}

	/**
	 * Esse cenario usuario preenche o formulario e deixa o campo login em
	 * branco. RESULTADO ESPERADO: sistema deve exibir mensagem de
	 * obrigatoriedade e nao deixar o registro ser incluso no banco.
	 */
	@Test
	public void obrigatoriedadeLogin(){

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuario("", nome, email, telefone, area, regional, responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", incluirUsuarioPage.visualizaMensagem());

		if (incluirUsuarioPage.consultaRegistroNoBanco("nome", nome)) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: " + nome);
		}
	}

	/**
	 * Esse cenario o usuario preenche o campo login com um ja existente e
	 * aciona o botao Salvar e fechar RESULTADO ESPERADO: Sistema nao deve
	 * permitir incluir 2 usuarios com mesmo login. 
	 */
	@Test
	public void loginExistenteSalvarFechar(){

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		String novoLogin = "wolverine";
		incluirUsuarioPage.inserirUsuarioBuilder(novoLogin, "Logan", "logan@visent.com.br", telefone,
				area, regional, responsavel); // incluir um registro original

		incluirUsuarioPage.inserirUsuario("Wolverine", "Logan", "logan@visent.com.br", telefone,
				area, regional, responsavel); 

		incluirUsuarioPage.clicaNoBotaoSalvareFechar();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));

		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); // captura a mensagem de sucesso
		Assert.assertEquals("Login já cadastrado !", mensagem);

		if (incluirUsuarioPage.consultaQuantidadeDeRegistroNoBanco("login", novoLogin)>1) {
			Assert.fail("Sistema incluiu no banco indevidamente o usuario: "
					+ novoLogin);
		}
	}

	/**
	 * Esse cenario o usuario preenche o campo login com um ja existente e
	 * aciona o botao Salvar RESULTADO ESPERADO: Sistema nao deve permitir
	 * incluir 2 usuarios com mesmo login. 
	 */
	@Test
	public void loginExistente(){

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		String novoLogin = "kratos";
											
		incluirUsuarioPage.inserirUsuarioBuilder(novoLogin, "Kratos God", "kratos@visent.com.br", telefone, area, regional, responsavel);
		
		incluirUsuarioPage.inserirUsuario("kratos", "Kratos God", "kratos@visent.com.br",
				telefone, area, regional, responsavel);
														
		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));

		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); // captura a mensagem de sucesso
		Assert.assertEquals("Login já cadastrado !", mensagem);

		if (incluirUsuarioPage.consultaQuantidadeDeRegistroNoBanco("login", novoLogin) > 1) {
			Assert.fail("Sistema incluiu login ja cadastrado no banco: "
					+ novoLogin);
		}
	}

	/**
	 * Esse cenario o ator faz o cadastro de um usuario e verifica se o mesmo
	 * esta no banco de dados. RESULTADO ESPERADO: sistema deve cadastrar o
	 * usuario. 
	 */
	@Test
	public void incluirUsuarioSalvarFechar(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		String loginSalva = "joaosilva";
		String nomeSalvar = "Joao Silva";
		String emailSalvar = "joaosilva@visent.com.br";
		
		incluirUsuarioPage.inserirUsuario(loginSalva, nomeSalvar, emailSalvar, telefone, area,
				regional, responsavel);

		String tituloSaida = driver.findElement(
				By.xpath("//span[@class='ui-dialog-title']")).getText();

		incluirUsuarioPage.clicaNoBotaoSalvar();
		
		String mensagem = driver.findElement(By.className("jGrowl-message"))
				.getText(); // captura a mensagem de sucesso

		if (!incluirUsuarioPage.validanoBanco(loginSalva, nomeSalvar, emailSalvar, telefone,
				area, regional, responsavel)) {
			Assert.fail("Sistema não incluiu o registro:" + loginSalva
					+ " no banco.");
		}
		Assert.assertEquals(tituloSaida, "Cadastrar Usuário"); 
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
	}

	/**
	 * Esse cenario o ator faz o cadastro de um usuario e verifica se o mesmo
	 * esta no banco de dados. RESULTADO ESPERADO: sistema deve cadastrar o
	 * usuario.
	 */
	@Test
	public void incluirUsuarioSalvar(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuario(login, nome, email, telefone, area, regional,
				responsavel);

		incluirUsuarioPage.clicaNoBotaoSalvar();

		String mensagem = driver.findElement(By.className("jGrowl-message"))
				.getText(); // captura a mensagem de sucesso

		String tituloSaida = driver.findElement(
				By.xpath("//span[@class='ui-dialog-title']")).getText();
																		
		if (!incluirUsuarioPage.validanoBanco(login, nome, email, telefone, area, regional,
				responsavel)) {
			Assert.assertEquals(tituloSaida, "Cadastrar Usuário");
			Assert.fail("Sistema nao incluiu o registro:" + login
					+ " no banco.");
		}
		Assert.assertEquals(tituloSaida, "Cadastrar Usuário"); 
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
	}

	@Parameters
	public static List<Object[]> datadriven() {
		return Arrays.asList(new Object[][] { 
				{"rafaelsilva3", "Rafael Nascimento3",
				"testeMetric1@visent.com.br3", "879285539", "TESTE AREA",
				"Claro DF", "TESTE RESPONSAVEL", "rafael",
				"rafaelsilva@visent.com.br", "carlos", "leovisent.com.br" }, });
	}
	
	@After
	public void tearDown(){
			incluirUsuarioPage.excluirUsuarioPeloBanco("logincaractere");
			incluirUsuarioPage.excluirUsuarioPeloBanco("teste@%$#@TESTE");
			incluirUsuarioPage.excluirUsuarioPeloBanco(loginEmailMascara);
			incluirUsuarioPage.excluirUsuarioPeloBanco("cassandra");
			incluirUsuarioPage.excluirUsuarioPeloBanco("cassandra");
			incluirUsuarioPage.excluirUsuarioPeloBanco("hannibal");
			incluirUsuarioPage.excluirUsuarioPeloBanco("joaocleber");
			incluirUsuarioPage.excluirUsuarioPeloBanco("naruto");
			incluirUsuarioPage.excluirUsuarioPeloBanco("TESTE");
			incluirUsuarioPage.excluirUsuarioPeloBanco(login);
			incluirUsuarioPage.excluirUsuarioPeloBanco("wolverine");
			incluirUsuarioPage.excluirUsuarioPeloBanco("kratos");
			incluirUsuarioPage.excluirUsuarioPeloBanco("joaosilva");
	}
}
