package br.com.visent.metricview.selenium.permissoes;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.metricview.selenium.core.BaseTestCase;
import br.com.visent.metricview.selenium.usuario.AlterarUsuarioPage;
import br.com.visent.metricview.selenium.usuario.IncluirUsuarioPage;
import br.com.visent.metricview.selenium.util.Constantes;

@RunWith(Parameterized.class)
public class PermissaoUsuarioMatrizTest extends BaseTestCase{

	private static IncluirUsuarioPage incluirUsuarioPage;
	private static AlterarUsuarioPage alterarUsuarioPage;
	private static PermissaoUsuarioPage permisaoUsuarioPage;
	private static WebDriverWait wait;

	// Dados
	String login;
	String nome;
	String email;
	String area;
	String telefone;
	String regional;
	String responsavel;
	String modulo;
	String loginAdm;
	String nomeAdm;
	String emailAdm;
	String grupo;
	String descricaoGrupo;

	public PermissaoUsuarioMatrizTest(String _login, String _nome, String _email, String _area,
			String _telefone, String _regional, String _responsavel,
			String _modulo, String _loginAdm, String _nomeAdm,
			String _emailAdm, String _grupo, String _descricaoGrupo) {

		this.login = _login;
		this.nome = _nome;
		this.email = _email;
		this.area = _area;
		this.telefone = _telefone;
		this.regional = _regional;
		this.responsavel = _responsavel;
		this.modulo = _modulo;
		this.loginAdm = _loginAdm;
		this.nomeAdm = _nomeAdm;
		this.emailAdm = _emailAdm;
		this.grupo = _grupo;
		this.descricaoGrupo = _descricaoGrupo;
	}

	@Before
	public void setUp(){
		incluirUsuarioPage = new IncluirUsuarioPage(driver);
		alterarUsuarioPage = new AlterarUsuarioPage(driver);
		permisaoUsuarioPage = new PermissaoUsuarioPage(driver);
		wait = new WebDriverWait(driver, 10);
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginAdm);
	}

	/**
	 * Cenario testa a permissao de usuario do Easyview e verifica se na tela
	 * inicial o mesmo mostra a permissao. RESULTADO ESPERADO: Sistema deve
	 * incluir a permissao para o usuario selecionado.
	 */
	@Test
	public void permisaoUsuario(){

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);
		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permisaoUsuarioPage.selecionaPermissaoUsuario("Usuários", "Usuário", nome + " "
				+ "(" + login + ")", "Itxview");

		driver.findElement(By.id("salvarPermissoes")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("jGrowl-message")));

		String mensagemPermissao = driver.findElement(
				By.className("jGrowl-message")).getText(); // captura a mensagem
															// de sucesso

		Assert.assertEquals("Operação Realizada com Sucesso !",
				mensagemPermissao);

		permisaoUsuarioPage.validaPermissaoBancoUsuario(login, modulo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("btnVisualizarUsuarios")));

		driver.findElement(By.id("btnVisualizarUsuarios")).click();

		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(login),
				"naoeditavel exibirPermissoes");

		permisaoUsuarioPage.validaIndividual(login, "Usuário", "Matrizes de Interesse de Interconexão.");

		driver.findElements(By.id("Fechar")).get(1).click(); // fecha permissoes

		driver.findElements(By.id("Fechar")).get(0).click(); // fechar
																// visualizar
																// usuarios
		loginPage.logOut();

		loginPage.login(login, login);

		permisaoUsuarioPage.validaPermissaoPaginaInicial(login, modulo, "Usuário");

		loginPage.logOut();

		loginPage.login(Constantes.USUARIO, Constantes.SENHA);

		util.clicaNoMenuAdministracao();

		permisaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select selectTipo = new Select(driver.findElement(By.id("selectTipo")));
		selectTipo.selectByVisibleText("Usuários");

		Select selectPermissoes = new Select(driver.findElement(By
				.id("comboSelectPermissao")));
		selectPermissoes.selectByVisibleText("Usuário");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("selectPermissao")));

		Select selectPermissao = new Select(driver.findElement(By
				.id("selectPermissao")));

		selectPermissao.selectByVisibleText(nome + " " + "(" + login + ")");

		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgretirar']"))
				.click();

		driver.findElement(By.id("salvarPermissoes")).click();

		loginPage.logOut();

		loginPage.login(login, login);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("jGrowl-message")));

		String mensagem = driver.findElement(By.className("jGrowl-message"))
				.getText(); // captura a mensagem de sucesso

		Assert.assertEquals(
				"Não existem permissões de acesso a(os) subsistema(s) do Portal MetricView. Contate o Administrador!",
				mensagem);

		loginPage.logOut();
		
		loginPage.login(Constantes.USUARIO, Constantes.SENHA);
	}

	/**
	 * Cenario faz a inclusao da permisao do tipo Administrador, e verifica se o
	 * sistema incluiu na pagina inicial. RESULTADO ESPERADO: Sistema deve
	 * incluir a apermissao como administradoar.
	 */
	@Test
	public void permissaoAdministrador(){

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);
		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permisaoUsuarioPage.selecionaPermissaoUsuario("Usuários", "Administrador",
				nomeAdm + " " + "(" + loginAdm + ")", "Itxview");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("salvarPermissoes")));

		driver.findElement(By.id("salvarPermissoes")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("jGrowl-message")));

		String mensagemPermissao = driver.findElement(
				By.className("jGrowl-message")).getText(); // captura a mensagem

		Assert.assertEquals("Operação Realizada com Sucesso !",
				mensagemPermissao);

		permisaoUsuarioPage.validaPermissaoBancoUsuario(loginAdm, modulo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("btnVisualizarUsuarios")));

		driver.findElement(By.id("btnVisualizarUsuarios")).click();

		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginAdm),
				"naoeditavel exibirPermissoes");

		permisaoUsuarioPage.validaIndividual(loginAdm, "Administrador", "Matrizes de Interesse de Interconexão.");

		driver.findElements(By.id("Fechar")).get(1).click(); // fechar
																// permissoes
		driver.findElements(By.id("Fechar")).get(0).click(); // fechar
																// visualizar
																// adm
		loginPage.logOut();

		loginPage.login(loginAdm, loginAdm);

		permisaoUsuarioPage.validaPermissaoPaginaInicial(loginAdm, modulo,
				"Administrador");

		loginPage.logOut();

		loginPage.login(Constantes.USUARIO, Constantes.SENHA);

		util.clicaNoMenuAdministracao();

		permisaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select selectTipo = new Select(driver.findElement(By.id("selectTipo")));
		selectTipo.selectByVisibleText("Usuários");

		Select selectPermissoes = new Select(driver.findElement(By
				.id("comboSelectPermissao")));

		selectPermissoes.selectByVisibleText("Administrador");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("selectPermissao")));

		Select selectPermissao = new Select(driver.findElement(By
				.id("selectPermissao")));

		selectPermissao.selectByVisibleText(nomeAdm + " " + "(" + loginAdm
				+ ")");

		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgretirar']"))
				.click();

		driver.findElement(By.id("salvarPermissoes")).click();

		loginPage.logOut();

		loginPage.login(loginAdm, loginAdm);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("jGrowl-message")));

		String mensagem = driver.findElement(By.className("jGrowl-message"))
				.getText(); // captura a mensagem de sucesso

		Assert.assertEquals(
				"Não existem permissões de acesso a(os) subsistema(s) do Portal MetricView. Contate o Administrador!",
				mensagem);

		loginPage.logOut();

		loginPage.login(Constantes.USUARIO, Constantes.SENHA);
	}

	/**
	 * Seleciona um usuario para a permissao e aciona o botao de adicionar grupo
	 * RESULTADO ESPERADO: Sistema deve apresentar a mensagem
	 * "As configurações de Associação e Permissão serão perdidas, deseja Continuar ?"
	 */
	@Test
	public void configuracaoPerdidaIncluirGrupoAceitar(){

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);
		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permisaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select tipoPermissao = new Select(driver.findElement(By
				.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Usuários");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("selectPermissaoUsuario")));

		Select selecionaGrupo = new Select(driver.findElement(By
				.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(nome + " " + "(" + login + ")");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));

		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgenviar']"))
				.click(); // associar permissao

		driver.findElement(By.id("btnAddGrupo")).click();

		Alert msg = driver.switchTo().alert();
		Assert.assertEquals(
				"As configurações de Associação e Permissão serão perdidas, deseja continuar ?",
				msg.getText());
		msg.accept();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("ui-dialog-title")));

		String tituloTela = driver.findElement(By.className("ui-dialog-title"))
				.getText(); // captura o titulo da tela

		Assert.assertEquals("Cadastrar Grupo", tituloTela);
	}

	/**
	 * Seleciona um usuario para a permissao e aciona o botao de visualizar
	 * grupo cancela o alert RESULTADO ESPERADO: Sistema deve apresentar a
	 * mensagem
	 * "As configurações de Associação e Permissão serão perdidas, deseja Continuar ?"
	 */
	@Test
	public void configuracaoPerdidaIncluirGrupoCancelar(){

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);
		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permisaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select tipoPermissao = new Select(driver.findElement(By
				.id("selectTipo")));

		tipoPermissao.selectByVisibleText("Usuários");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("selectPermissaoUsuario")));

		Select selecionaGrupo = new Select(driver.findElement(By
				.id("selectPermissaoUsuario")));

		selecionaGrupo.selectByVisibleText(nome + " " + "(" + login + ")");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));

		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgenviar']"))
				.click(); // associar permissao

		driver.findElement(By.id("btnAddGrupo")).click();

		Alert msg = driver.switchTo().alert();
		Assert.assertEquals(
				"As configurações de Associação e Permissão serão perdidas, deseja continuar ?",
				msg.getText());
		msg.dismiss();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//h1[contains(text(),'Manutenção')]")));

		String tituloTela = driver.findElement(
				By.xpath("//h1[contains(text(),'Manutenção')]")).getText();

		Assert.assertEquals("Manutenção", tituloTela);
	}

	/**
	 * Seleciona um usuario para a permissao e depois que o sistema apresentar
	 * os botoes salvar e cancelar usuario aciona o botao de adicionar usuario
	 * RESULTADO ESPERADO: Sistema deve apresentar a mensagem
	 * "As configurações de Associação e Permissão serão perdidas, deseja Continuar ?"
	 */
	@Test
	public void configuracaoPerdidaIncluirUsuarioAceitar(){

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);
		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permisaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select tipoPermissao = new Select(driver.findElement(By
				.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Usuários");

		Select selecionaGrupo = new Select(driver.findElement(By
				.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(nome + " " + "(" + login + ")");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));

		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgenviar']"))
				.click(); // associar permissao

		driver.findElement(By.id("btnAddUsuario")).click();

		Alert msg = driver.switchTo().alert();
		Assert.assertEquals(
				"As configurações de Associação e Permissão serão perdidas, deseja continuar ?",
				msg.getText());
		msg.accept();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("ui-dialog-title")));

		String tituloTela = driver.findElement(By.className("ui-dialog-title"))
				.getText(); // captura o titulo da tela
		Assert.assertEquals("Cadastrar Usuário", tituloTela);
	}

	/**
	 * Seleciona um usuario para a permissao e depois que o sistema apresentar
	 * os botoes salvar e cancelar usuario aciona o botao de vizualizar usuario
	 * RESULTADO ESPERADO: Sistema deve apresentar a mensagem
	 * "As configurações de Associação e Permissão serão perdidas, deseja Continuar ?"
s	 */
	@Test
	public void configuracaoPerdidaIncluirUsuarioCancelar(){

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);
		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permisaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select tipoPermissao = new Select(driver.findElement(By
				.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Usuários");

		Select selecionaGrupo = new Select(driver.findElement(By
				.id("selectPermissaoUsuario")));

		selecionaGrupo.selectByVisibleText(nome + " " + "(" + login + ")");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));

		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgenviar']"))
				.click(); // associar permissao

		driver.findElement(By.id("btnVisualizarUsuarios")).click();

		Alert msg = driver.switchTo().alert();
		Assert.assertEquals(
				"As configurações de Associação e Permissão serão perdidas, deseja continuar ?",
				msg.getText());
		msg.accept();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//h1[contains(text(),'Manutenção')]")));

		String tituloTela = driver.findElement(
				By.xpath("//h1[contains(text(),'Manutenção')]")).getText();
		Assert.assertEquals("Manutenção", tituloTela);
	}

	@Parameters
	public static List<Object[]> datadriven() {
		return Arrays.asList(new Object[][] { 
				{"rafaela", "Rafaela", "rafaela@visent.com.br2", 
				  "TESTE AREA", "879285539", "Claro DF", "TESTE RESPONSAVEL", 
				  "Itxview","ramones", "Ramones", "ramones@visent.com.br",
				  "TESTE MATRIZINTERESSE", "Grupo equipe claro" }, });
	}

	@After
	public void tearDown() {
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginAdm);
	}
}
