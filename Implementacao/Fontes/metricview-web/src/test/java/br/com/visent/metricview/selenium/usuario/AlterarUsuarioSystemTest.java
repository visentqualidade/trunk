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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.metricview.selenium.core.BaseTestCase;
import br.com.visent.metricview.selenium.login.LoginPage;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;

@RunWith(Parameterized.class)
public class AlterarUsuarioSystemTest extends BaseTestCase {
	private static VisentTestCase util2;
	private static IncluirUsuarioPage incluirUsuarioPage;
	private static AlterarUsuarioPage alterarUsuarioPage;
	private static WebDriver driverDesc;
	private static WebDriverWait wait;

	// dados
	String loginOrig;
	String nomeOrig;
	String emailOrig;
	String telefoneOrig;
	String areaOrig;
	String regionalOrig;
	String responsavelOrig;
	String nomeEdit;
	String emailEdit;
	String telefoneEdit;
	String areaEdit;
	String regionalEdit;
	String responsavelEdit;

	public AlterarUsuarioSystemTest(String _loginOrig, String _nomeOrig,
			String _emailOrig, String _telefoneOrig, String _areaOrig,
			String _regionalOrig, String _responsavelOrig, String _nomeEdit,
			String _emailEdit, String _telefoneEdit, String _areaEdit,
			String _regionalEdit, String _responsavelEdit) {

		this.loginOrig = _loginOrig;
		this.nomeOrig = _nomeOrig;
		this.emailOrig = _emailOrig;
		this.telefoneOrig = _telefoneOrig;
		this.areaOrig = _areaOrig;
		this.regionalOrig = _regionalOrig;
		this.responsavelOrig = _responsavelOrig;
		// dados da edição
		this.nomeEdit = _nomeEdit;
		this.emailEdit = _emailEdit;
		this.telefoneEdit = _telefoneEdit;
		this.areaEdit = _areaEdit;
		this.regionalEdit = _regionalEdit;
		this.responsavelEdit = _responsavelEdit;
	}

	@BeforeClass
	public static void setUp() {
		loginPage.acessarPage();
		loginPage.logOut();
		loginPage.login(Constantes.SYSTEM, Constantes.SENHASYSTEM);
		incluirUsuarioPage = new IncluirUsuarioPage(driver);
		alterarUsuarioPage = new AlterarUsuarioPage(driver);
		wait = new WebDriverWait(driver, 10);
	}

	/**
	 * Esse cenario faz a alteração do usuario e verifica no banco. RESULTADO
	 * ESPERADO: valida no banco se as informações foram alteradas.
	 */
	@Test
	public void alterarUsuario() {
		// Se o usuario ja existir excluir.
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginOrig);

		loginPage.acessarPage();

		incluirUsuarioPage.inserirUsuarioBuilder(loginOrig, nomeOrig,
				emailOrig, telefoneOrig, areaOrig, regionalOrig,
				responsavelOrig);

		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginOrig), "editar");

		alterarUsuarioPage.alterar(nomeOrig, emailOrig, telefoneOrig, areaOrig,
				regionalOrig, responsavelOrig, nomeEdit, emailEdit,
				telefoneEdit, areaEdit, regionalEdit, responsavelEdit);

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginOrig), "editar");
		
		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso();

		incluirUsuarioPage.validanoBanco(loginOrig, nomeEdit, emailEdit,
				telefoneEdit, areaEdit, regionalEdit, responsavelEdit);

		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginOrig);
	}

	/**
	 * Usuario nao preenche o campo nome e salva. RESULTADO ESPERADO: Sistema
	 * deve exibir a mensagem
	 * " O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void obrigatoriedadeNome() {

		incluirUsuarioPage.excluirUsuarioPeloBanco("kuroko");

		loginPage.acessarPage();

		String loginObrg = "kuroko";
		String nomeObrg = "Kuroko Tetsuya";
		String emailObrg = "tetsuya@visent.com.br";

		incluirUsuarioPage.inserirUsuarioBuilder(loginObrg, nomeObrg,
				emailObrg, telefoneOrig, areaOrig, regionalOrig,
				responsavelOrig);

		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginObrg), "editar");

		alterarUsuarioPage.alterar(nomeObrg, emailObrg, telefoneOrig, areaOrig,
				regionalOrig, responsavelOrig, "", emailEdit, telefoneEdit,
				areaEdit, regionalEdit, responsavelEdit);

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginObrg), "editar");

		String mensagem = alterarUsuarioPage
				.deveVerificarMensagemDeObrigatoriedade();

		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("kuroko");
	}

	/**
	 * Usuario nao preenche o campo email e salva. RESULTADO ESPERADO: Sistema
	 * deve exibir a mensagem
	 * " O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void obrigatoriedadeEmail() {

		incluirUsuarioPage.excluirUsuarioPeloBanco("akashi");

		loginPage.acessarPage();

		String emailNovo = "akashi@visent.com.br";
		String loginNovo = "akashi";
		String nomeNovo = "Akashi Seijuro";
		incluirUsuarioPage.inserirUsuarioBuilder(loginNovo, nomeNovo,
				emailNovo, telefoneOrig, areaOrig, regionalOrig,
				responsavelOrig);

		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginNovo), "editar");

		alterarUsuarioPage.alterar(nomeNovo, emailNovo, telefoneOrig, areaOrig,
				regionalOrig, responsavelOrig, nomeEdit, "", telefoneEdit,
				areaEdit, regionalEdit, responsavelEdit);

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginNovo), "editar");

		String mensagem = alterarUsuarioPage
				.deveVerificarMensagemDeObrigatoriedade();

		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("akashi");
	}

	/**
	 * Deixa o campo email sem mascara e tenta salvar o usuario. RESULTADO
	 * ESPERADO: Sistema deve exibir a mensagem
	 * "* O campo em destaque foi preenchido de forma inválida."
	 */
	@Test
	public void mascaraEmail() {

		incluirUsuarioPage.excluirUsuarioPeloBanco("kagami");

		loginPage.acessarPage();

		String loginNovo = "kagami";
		String nomeNovo = "Taiga Kagami";
		String emailNovo = "kagami@visent.com.br";

		incluirUsuarioPage.inserirUsuarioBuilder(loginNovo, nomeNovo,
				emailNovo, telefoneOrig, areaOrig, regionalOrig,
				responsavelOrig);

		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginNovo), "editar");

		alterarUsuarioPage.alterar(nomeNovo, emailNovo, telefoneOrig, areaOrig,
				regionalOrig, responsavelOrig, nomeEdit, "asdasdvisent.com",
				telefoneEdit, areaEdit, regionalEdit, responsavelEdit);

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginNovo), "editar");

		String mensagem = alterarUsuarioPage
				.deveVerificarMensagemDeObrigatoriedade();

		Assert.assertEquals("* O campo em destaque foi preenchido de forma inválida.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("kagami");
	}

	/**
	 * Usuario informa o campo nome com caractere especiais. RESULTADO ESPERADO:
	 * Sistema deve exibir a mensagem "Não é permitido caracter especial."
	 */
	@Test
	public void caracteresEspeciaisNome() {

		incluirUsuarioPage.excluirUsuarioPeloBanco("kiyoshi");

		loginPage.acessarPage();

		String loginNovo = "kiyoshi";
		String nomeNovo = "Teppei Kiyoshi";
		String emailNovo = "kiyoshi@visent.com.br";
		incluirUsuarioPage.inserirUsuarioBuilder(loginNovo, nomeNovo,
				emailNovo, telefoneOrig, areaOrig, regionalOrig,
				responsavelOrig);

		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginNovo), "editar");

		alterarUsuarioPage.alterar(nomeNovo, emailNovo, telefoneOrig, areaOrig,
				regionalOrig, responsavelOrig, "nome'asda'sdasd@#$%#@<>",
				emailEdit, telefoneEdit, areaEdit, regionalEdit,
				responsavelEdit);

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginNovo), "editar");

		String mensagem = alterarUsuarioPage
				.deveVerificarMensagemDeObrigatoriedade();

		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
		
		incluirUsuarioPage.excluirUsuarioPeloBanco("kiyoshi");
	}

	/**
	 * Desconectar um usuario e verifica se o mesmo foi para a tela de login.
	 * RESULTADO ESPERADO: Sistema deve desconectar o usuario logado.
	 */
	@Test
	public void desconectarUsuario() {

		incluirUsuarioPage.excluirUsuarioPeloBanco("midorima");

		try {
			loginPage.acessarPage();

			String loginNovo = "midorima";
			String nomeNovo = "Shintaro Midorima";
			String emailNovo = "midorima@visent.com.br";
			incluirUsuarioPage.inserirUsuario(loginNovo, nomeNovo, emailNovo,
					telefoneOrig, areaOrig, regionalOrig, responsavelOrig);

			incluirUsuarioPage.clicaNoBotaoSalvareFechar();

			util2 = new VisentTestCase();
			driverDesc = util2.chamarBrowser(Browser.CHROME);

			new LoginPage(driverDesc).acessarPage();

			util2.login(loginNovo, loginNovo);

			alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

			alterarUsuarioPage.clicarBotaoTabela(
					alterarUsuarioPage.buscarId(loginNovo),
					"desconectar naoeditavel");
			util.verificaAlert("Tem certeza de que deseja desconectar o usuário !");

			// captura a mensagem de sucesso
			String mensagem = driver
					.findElement(By.className("jGrowl-message")).getText(); 

			new WebDriverWait(driverDesc, 10).until(ExpectedConditions
					.visibilityOfElementLocated(By
							.xpath("//label[contains(text(),'Usuário:')]")));
			String usuario = driverDesc.findElement(
					By.xpath("//label[contains(text(),'Usuário:')]")).getText();
			String senha = driverDesc.findElement(
					By.xpath("//label[contains(text(),'Senha:')]")).getText();

			driverDesc.close();
			driverDesc.quit();

			Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
			Assert.assertEquals(usuario, "Usuário:");
			Assert.assertEquals(senha, "Senha:");

		} catch (Exception e) {
			driverDesc.close();
			driverDesc.quit();
		}
		incluirUsuarioPage.excluirUsuarioPeloBanco("midorima");
	}

	/**
	 * Cenario usuario clica no checkbox Mostrar Somente Usuários conectados"
	 * RESULTADO ESPERADO: sistema deve mostrar somente os usuarios logados.
	 */
	@Test
	public void mostrarUsuariosLogados() {

		loginPage.acessarPage();

		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		driver.findElement(By.id("checkSomenteConectado")).click();

		boolean logado = alterarUsuarioPage
				.visualizaUsuarioConectado(Constantes.SYSTEM);

		if (logado) {

			Assert.fail("Sisteman não esta mostrando o usuário logado ! ");
		}
	}

	/**
	 * Cenario fecha a janela e verifica se o mesmo foi para a tela anterior.
	 */
	// @Test
	public void fecharJanela() {

		loginPage.acessarPage();

		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		String titulo = driver.findElement(
				By.xpath("//span[contains(text(),'Visualizar Usuários')]"))
				.getText(); // Verifica titulo da tela

		driver.findElement(
				By.xpath("//span[@class='ui-icon ui-icon-closethick']"))
				.click(); // fechar

		String titulotela = driver.findElement(
				By.xpath("//h1[contains(text(),'Manutenção')]")).getText();
		Assert.assertEquals(titulo, "Visualizar Usuários"); // Verifica titulo
															// da tela
		Assert.assertEquals(titulotela, "Manutenção");
	}

	/**
	 * Cenario abre a janela visualizar usuario e depois fecha com o botao
	 * Fechar.
	 */
	@Test
	public void cancelarModal() {

		loginPage.acessarPage();

		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		String titulo = driver.findElement(
				By.xpath("//span[contains(text(),'Visualizar Usuários')]"))
				.getText(); // Verifica titulo da tela

		driver.findElement(By.id("Fechar")).click(); // salvar

		String titulotela = driver.findElement(
				By.xpath("//h1[contains(text(),'Manutenção')]")).getText();
		Assert.assertEquals(titulotela, "Manutenção");
		Assert.assertEquals(titulo, "Visualizar Usuários"); // Verifica titulo
															// da tela
	}

	/**
	 * Reseta a senha do usuario e verifica se o mesmo foi para a tela de
	 * alterar dados. RESULTADO ESPERADO: Sistema deve resetar a senha do
	 * usuario.
	 */
	@Test
	public void resetarSenha() {

		loginPage.acessarPage();

		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(Constantes.USUARIO),"resetarSenha naoeditavel"); // reseta a senha do usuario
		util.verificaAlert("Tem certeza de que deseja resetar a senha !"); // valida a mensagem

		driver.findElement(By.id("Fechar")).click(); // fecha a janela

		loginPage.logOut();

		util.login(Constantes.USUARIO, Constantes.SENHA);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//span[contains(text(),'Alterar dados')]")));

		String titulo = driver.findElement(
				By.xpath("//span[contains(text(),'Alterar dados')]")).getText(); // Verifica
																					// titulo
																					// da
																					// tela

		driver.findElement(By.id("senhaAntigaDados")).sendKeys(
				Constantes.USUARIO);
		driver.findElement(By.id("senhaNovaDados"))
				.sendKeys(Constantes.USUARIO);
		driver.findElement(By.id("senhaNovaConfirmDados")).sendKeys(
				Constantes.USUARIO);

		driver.findElement(By.id("Salvar")).click();

		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso();

		loginPage.logOut();

		loginPage.login(Constantes.SYSTEM, Constantes.SENHASYSTEM);

		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
		Assert.assertEquals("Alterar dados", titulo);
	}

	@Parameters
	public static List<Object[]> datadriven() {
		return Arrays.asList(new Object[][] { { "jordan", "Hal Jordan",
				"jordan@visent.com.br", "335698745", "Area 69", "Claro DF",
				"Alessandra", "Lanterna Vermelho", "vermelho@visent.com.br",
				"569875312", "Area 75", "Claro PE", "Erick" }, });
	}

	@AfterClass
	public static void tearDown() {

	}
}
