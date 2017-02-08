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
import br.com.visent.metricview.selenium.grupo.IncluirGrupoPage;
import br.com.visent.metricview.selenium.usuario.AlterarUsuarioPage;
import br.com.visent.metricview.selenium.usuario.IncluirUsuarioPage;
import br.com.visent.metricview.selenium.util.Constantes;

@RunWith(Parameterized.class)
public class PermissaoGrupoMatrizTest extends BaseTestCase {

	IncluirGrupoPage incluirGrupoPage;
	IncluirUsuarioPage incluirUsuarioPage;
	AlterarUsuarioPage alterarUsuarioPage;
	PermissaoUsuarioPage permissaoUsuarioPage;
	WebDriverWait wait;

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
	String grupoAdm;

	public PermissaoGrupoMatrizTest(String _login, String _nome, String _email,
			String _area, String _telefone, String _regional,
			String _responsavel, String _modulo, String _loginAdm,
			String _nomeAdm, String _emailAdm, String _grupo,
			String _descricaoGrupo, String _grupoAdm) {

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
		this.grupoAdm = _grupoAdm;
	}

	@Before
	public void setUp() {
		incluirUsuarioPage = new IncluirUsuarioPage(driver);
		incluirGrupoPage = new IncluirGrupoPage(driver);
		alterarUsuarioPage = new AlterarUsuarioPage(driver);
		permissaoUsuarioPage = new PermissaoUsuarioPage(driver);
		wait = new WebDriverWait(driver, 10);
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		incluirGrupoPage.excluirGrupoPeloBanco(grupo);
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginAdm);
		incluirGrupoPage.excluirGrupoPeloBanco(grupoAdm);

	}

	/**
	 * Faz a associacao de grupo e desassociar grupos com usuario comum
	 */
	@Test
	public void AssociarPermissaoGrupo() {

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm,
				telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permissaoUsuarioPage.selecionaPermissaoGrupo(nome, login, grupo,
				"Usuário", "Itxview"); // cria a permissao de grupo

		permissaoUsuarioPage.validaPermissaoBancoGrupo(grupo, modulo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("btnVisualizarUsuarios")));
		driver.findElement(By.id("btnVisualizarUsuarios")).click();

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(login),
				"naoeditavel exibirPermissoes");

		permissaoUsuarioPage.validaIndividualGrupo(login, grupo, "Usuário",
				"Matrizes de Interesse de Interconexão.");

		loginPage.logOut();

		loginPage.login(login, login);

		permissaoUsuarioPage.validaPermissaoPaginaInicial(login, modulo,
				"Usuário");

		loginPage.logOut();

		loginPage.login(Constantes.USUARIO, Constantes.SENHA);

		util.clicaNoMenuAdministracao();

		permissaoUsuarioPage.clicarMenuPermissao("Itxview");

		permissaoUsuarioPage.retirarAssociacaoGrupo(grupo, "Grupos", "Usuário");

		loginPage.logOut();

		loginPage.login(login, login);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("jGrowl-message")));
		String mensagem = driver.findElement(By.className("jGrowl-message"))
				.getText(); // captura a mensagem de sucesso
		Assert.assertEquals("Não existem permissões de acesso a(os) subsistema(s) do Portal MetricView. Contate o Administrador!", mensagem);

		loginPage.logOut();

		loginPage.login(Constantes.USUARIO, Constantes.SENHA);
	}

	/**
	 * Associar e desassociar parmissao de grupo administrador
	 */
	@Test
	public void AssociarPermissaoGrupoAdm() {

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm,
				telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permissaoUsuarioPage.selecionaPermissaoGrupo(nomeAdm, loginAdm,
				grupoAdm, "Administrador", "Itxview"); // cria a permissao de
														// grupo

		permissaoUsuarioPage.validaPermissaoBancoGrupo(grupoAdm, modulo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("btnVisualizarUsuarios")));
		driver.findElement(By.id("btnVisualizarUsuarios")).click();

		alterarUsuarioPage.clicarBotaoTabela(
				alterarUsuarioPage.buscarId(loginAdm),
				"naoeditavel exibirPermissoes");

		permissaoUsuarioPage.validaIndividualGrupo(loginAdm, grupoAdm,
				"Administrador", "Matrizes de Interesse de Interconexão.");

		loginPage.logOut();

		loginPage.login(loginAdm, loginAdm);

		permissaoUsuarioPage.validaPermissaoPaginaInicial(loginAdm, modulo,
				"Administrador");

		loginPage.logOut();

		loginPage.login(Constantes.USUARIO, Constantes.SENHA);

		util.clicaNoMenuAdministracao();

		permissaoUsuarioPage.clicarMenuPermissao("Itxview");

		permissaoUsuarioPage.retirarAssociacaoGrupo(grupoAdm, "Grupos",
				"Administrador");

		loginPage.logOut();

		loginPage.login(loginAdm, loginAdm);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("jGrowl-message")));
		String mensagem = driver.findElement(By.className("jGrowl-message"))
				.getText(); // captura a mensagem de sucesso
		Assert.assertEquals("Não existem permissões de acesso a(os) subsistema(s) do Portal MetricView. Contate o Administrador!", mensagem);

		loginPage.logOut();

		loginPage.login(Constantes.USUARIO, Constantes.SENHA);
	}

	/**
	 * Apresenta alert de configuracao perdida quando clicar em adicionar grupo
	 */
	@Test
	public void configuracaoPerdidaIncluirGrupoAceitar() {

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm,
				telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permissaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select tipoPermissao = new Select(driver.findElement(By
				.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");

		Select selecionaGrupo = new Select(driver.findElement(By
				.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgenviar']"))
				.click(); // associar permissao

		driver.findElement(By.id("btnAddGrupo")).click();

		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.accept();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("ui-dialog-title")));
		String tituloTela = driver.findElement(By.className("ui-dialog-title"))
				.getText(); // captura o titulo da tela
		Assert.assertEquals("Cadastrar Grupo", tituloTela);
	}

	/**
	 * Apresenta alert de configuracao perdida e depois cancela para adicionar
	 * grupo.
	 */
	@Test
	public void configuracaoPerdidaIncluirGrupoCancelar() {

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm,
				telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permissaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select tipoPermissao = new Select(driver.findElement(By
				.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");

		Select selecionaGrupo = new Select(driver.findElement(By
				.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgenviar']"))
				.click(); // associar permissao

		driver.findElement(By.id("btnAddGrupo")).click();

		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.dismiss();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//h1[contains(text(),'Manutenção')]")));
		String tituloTela = driver.findElement(
				By.xpath("//h1[contains(text(),'Manutenção')]")).getText(); // captura
																			// o
																			// titulo
																			// da
																			// tela
		Assert.assertEquals("Manutenção", tituloTela);
	}

	/**
	 * Apresenta alert de configuracao perdida para o botao visualizar grupos.
	 */
	@Test
	public void configuracaoPerdidaVisualizarGrupoAceitar() {

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm,
				telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permissaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select tipoPermissao = new Select(driver.findElement(By
				.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");

		Select selecionaGrupo = new Select(driver.findElement(By
				.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgenviar']"))
				.click(); // associar permissao

		driver.findElement(By.id("btnVisualizarGrupos")).click();

		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.accept();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("ui-dialog-title")));
		String tituloTela = driver.findElement(By.className("ui-dialog-title"))
				.getText(); // captura o titulo da tela
		Assert.assertEquals("Visualizar Grupos", tituloTela);
	}

	/**
	 * Apresenta alert de configuracao perdida para o botao visualizar grupo, e
	 * dpeois cancela.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void configuracaoPerdidaVisualizarGrupoCancelar(){

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm,
				telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permissaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select tipoPermissao = new Select(driver.findElement(By
				.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");

		Select selecionaGrupo = new Select(driver.findElement(By
				.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgenviar']"))
				.click(); // associar permissao

		driver.findElement(By.id("btnVisualizarGrupos")).click();

		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.dismiss();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//h1[contains(text(),'Manutenção')]")));

		// captura o titulo da tela
		String tituloTela = driver.findElement(
				By.xpath("//h1[contains(text(),'Manutenção')]")).getText(); 
		
		Assert.assertEquals("Manutenção", tituloTela);
	}

	/**
	 * Apresenta alert de configuracao perdida para a o botao adicionar usuario.
	 */
	@Test
	public void configuracaoPerdidaIncluirUsuarioAceitar() {

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm,
				telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permissaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select tipoPermissao = new Select(driver.findElement(By
				.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");

		Select selecionaGrupo = new Select(driver.findElement(By
				.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgenviar']"))
				.click(); // associar permissao

		driver.findElement(By.id("btnAddUsuario")).click();

		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.accept();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("ui-dialog-title")));
		String tituloTela = driver.findElement(By.className("ui-dialog-title"))
				.getText(); // captura o titulo da tela
		Assert.assertEquals("Cadastrar Usuário", tituloTela);
	}

	/**
	 * Apresenta alert de configuracao perdida para o botao adicionar usuario e
	 * depois cancela.
	 */
	@Test
	public void configuracaoPerdidaIncluirUsuarioCancelar() {

		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone,
				area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm,
				telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		permissaoUsuarioPage.clicarMenuPermissao("Itxview");

		Select tipoPermissao = new Select(driver.findElement(By
				.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");

		Select selecionaGrupo = new Select(driver.findElement(By
				.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(
				By.xpath("//div[@class='permissoes']//img[@class='imgenviar']"))
				.click(); // associar permissao

		driver.findElement(By.id("btnVisualizarUsuarios")).click();

		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.accept();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//h1[contains(text(),'Manutenção')]")));
		String tituloTela = driver.findElement(
				By.xpath("//h1[contains(text(),'Manutenção')]")).getText(); // captura
																			// o
																			// titulo
																			// da
																			// tela
		Assert.assertEquals("Manutenção", tituloTela);
	}

	@Parameters
	public static List<Object[]> datadriven() {
		return Arrays
				.asList(new Object[][] { { "carlos", "Carlos",
						"carlos@visent.com.br2", "TESTE AREA", "879285539",
						"Claro DF", "TESTE RESPONSAVEL", "Itxview",
						"princesaleia", "Leia", "leia@visent.com.br",
						"GRUPO MATRIZ", "Grupo MATRIZ", "MATRIZ" }, });
	}

	@After
	public void tearDown() {
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		incluirGrupoPage.excluirGrupoPeloBanco(grupo);
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginAdm);
		incluirGrupoPage.excluirGrupoPeloBanco(grupoAdm);
	}
}
