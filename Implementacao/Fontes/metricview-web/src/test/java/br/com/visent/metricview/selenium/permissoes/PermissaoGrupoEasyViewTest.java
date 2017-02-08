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
public class PermissaoGrupoEasyViewTest extends BaseTestCase {
	IncluirGrupoPage	 incluirGrupoPage;
	IncluirUsuarioPage 	 incluirUsuarioPage;
	AlterarUsuarioPage   alterarUsuarioPage;
	PermissaoUsuarioPage permissaoUsuarioPage;
	WebDriverWait 		 wait;
	
	//Dados
	String 	login;
	String  nome;
	String  email;
	String  area;
	String  telefone;
	String  regional;
	String  responsavel;
	String  modulo;
	String  loginAdm;
	String  nomeAdm;
	String	emailAdm;
	String 	grupo;
	String 	descricaoGrupo;
	String grupoAdm;
	
	public PermissaoGrupoEasyViewTest(String _login, String _nome, String _email, 
								 String _area, String _telefone, String _regional, String _responsavel, String _modulo, 
								 String _loginAdm, String _nomeAdm, String _emailAdm, String _grupo, String _descricaoGrupo,
								 String _grupoAdm){

		this.login		 	= _login;
		this.nome        	= _nome;
		this.email       	= _email;
		this.area        	= _area;
		this.telefone    	= _telefone;
		this.regional    	= _regional;
		this.responsavel 	= _responsavel;
		this.modulo      	= _modulo;
		this.loginAdm    	= _loginAdm;
		this.nomeAdm	 	= _nomeAdm;
		this.emailAdm    	= _emailAdm;
		this.grupo		 	= _grupo;
		this.descricaoGrupo = _descricaoGrupo;
		this.grupoAdm		= _grupoAdm;
	}
	
	@Before
	public void setUp(){
		incluirUsuarioPage   	 = new IncluirUsuarioPage(driver);
		incluirGrupoPage = new IncluirGrupoPage(driver);
		alterarUsuarioPage      = new AlterarUsuarioPage(driver);
		permissaoUsuarioPage 	 = new PermissaoUsuarioPage(driver);
		wait 		 = new WebDriverWait(driver, 10);
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		incluirGrupoPage.excluirGrupoPeloBanco(grupo);
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginAdm);
		incluirGrupoPage.excluirGrupoPeloBanco(grupoAdm);
	}
	/**
	 * Associa uma permissao de um grupo como usuario comum
	 */
	@Test
	public void AssociarPermissaoGrupoEasyView(){
		
		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		permissaoUsuarioPage.selecionaPermissaoGrupo(nome, login, grupo, "Usuário", "EasyView"); // cria a permissao de grupo

		permissaoUsuarioPage.validaPermissaoBancoGrupo(grupo, modulo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnVisualizarUsuarios")));
		driver.findElement(By.id("btnVisualizarUsuarios")).click();		
		
		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(login), "naoeditavel exibirPermissoes");
		
		permissaoUsuarioPage.validaIndividualGrupo(login, grupo, "Usuário", "Indicadores Regulatórios.");

		loginPage.logOut();
		
		loginPage.login(login, login);
		
		permissaoUsuarioPage.validaPermissaoPaginaInicial(login, modulo, "Usuário");
		
		loginPage.logOut();
		
		loginPage.login(Constantes.USUARIO, Constantes.SENHA);

		util.clicaNoMenuAdministracao();
		
		permissaoUsuarioPage.clicarMenuPermissao("EasyView");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectTipo")));
		Select tipoGrupo = new Select(driver.findElement(By.id("selectTipo")));  //em permissoes seleciona o grupo
		tipoGrupo.selectByVisibleText("Grupos");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("comboSelectPermissao")));
		Select selectPermissao = new Select(driver.findElement(By.id("comboSelectPermissao")));
		selectPermissao.selectByVisibleText("Usuário");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectPermissao")));
		Select permissaoUsuario = new Select(driver.findElement(By.id("selectPermissao"))); // seleciona qual o grupo
		permissaoUsuario.selectByVisibleText(grupo);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgretirar']")));
		driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgretirar']")).click(); //associar permissao

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("salvarPermissoes")));
		driver.findElement(By.id("salvarPermissoes")).click(); //salvar permissoes
		
		loginPage.logOut();
		
		loginPage.login(login, login);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); //captura a mensagem de sucesso
		Assert.assertEquals("Não existem permissões de acesso a(os) subsistema(s) do Portal MetricView. Contate o Administrador!", mensagem);
		
		loginPage.logOut();
		
		loginPage.login(Constantes.USUARIO, Constantes.SENHA);
	}

	/**
	 * Associa permssao de grupo administrador.
	 */
	@Test
	public void AssociarPermissaoGrupoEasyViewAdm(){
		
		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		permissaoUsuarioPage.selecionaPermissaoGrupo(nomeAdm, loginAdm, grupoAdm, "Administrador", "EasyView"); // cria a permissao de grupo
		
		permissaoUsuarioPage.validaPermissaoBancoGrupo(grupoAdm, modulo);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnVisualizarUsuarios")));
		driver.findElement(By.id("btnVisualizarUsuarios")).click();		
		
		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginAdm), "naoeditavel exibirPermissoes");
		
		permissaoUsuarioPage.validaIndividualGrupo(loginAdm, grupoAdm, "Administrador", "Indicadores Regulatórios.");
		
		loginPage.logOut();
		
		loginPage.login(loginAdm, loginAdm);
		
		permissaoUsuarioPage.validaPermissaoPaginaInicial(loginAdm, modulo, "Administrador");

		loginPage.logOut();
		
		loginPage.login(Constantes.USUARIO, Constantes.SENHA);

		util.clicaNoMenuAdministracao();
	
		permissaoUsuarioPage.clicarMenuPermissao("EasyView");
		
		Select tipoGrupo = new Select(driver.findElement(By.id("selectTipo")));  //em permissoes seleciona o grupo
		tipoGrupo.selectByVisibleText("Grupos");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("comboSelectPermissao")));
		Select selectPermissao = new Select(driver.findElement(By.id("comboSelectPermissao")));
		selectPermissao.selectByVisibleText("Administrador");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectPermissao")));
		Select permissaoUsuario = new Select(driver.findElement(By.id("selectPermissao"))); // seleciona qual o grupo
		permissaoUsuario.selectByVisibleText(grupoAdm);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgretirar']")));
		driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgretirar']")).click(); //associar permissao

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("salvarPermissoes")));
		driver.findElement(By.id("salvarPermissoes")).click(); //salvar permissoes
		
		loginPage.logOut();
		
		loginPage.login(loginAdm, loginAdm);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); //captura a mensagem de sucesso
		Assert.assertEquals("Não existem permissões de acesso a(os) subsistema(s) do Portal MetricView. Contate o Administrador!", mensagem);

		loginPage.logOut();
		
		loginPage.login(Constantes.USUARIO, Constantes.SENHA);
	}

	/**
	 * Apresenta mensagem de configuracao perdida para o botao adicionar grupo.
	 */
	@Test
	public void configuracaoPerdidaIncluirGrupoAceitar(){
		
		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		permissaoUsuarioPage.clicarMenuPermissao("EasyView");
		
		Select tipoPermissao = new Select(driver.findElement(By.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");
		
		Select selecionaGrupo = new Select(driver.findElement(By.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")).click(); //associar permissao
		
		driver.findElement(By.id("btnAddGrupo")).click();
		
		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.accept();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-dialog-title")));		
		String tituloTela = driver.findElement(By.className("ui-dialog-title")).getText();  //captura o titulo da tela
		Assert.assertEquals("Cadastrar Grupo", tituloTela);
	}
	/**
	 * Apresenta alert de configuracao perdida para o botao adicionar grupo e depois clica em cancelar.
	 * @throws InterruptedException
	 */
	@Test
	public void configuracaoPerdidaIncluirGrupoCancelar() throws InterruptedException{
		
		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		permissaoUsuarioPage.clicarMenuPermissao("EasyView");
		
		Select tipoPermissao = new Select(driver.findElement(By.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");
		
		Select selecionaGrupo = new Select(driver.findElement(By.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")).click(); //associar permissao
		
		driver.findElement(By.id("btnAddGrupo")).click();
		
		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.dismiss();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Manutenção')]")));
		String tituloTela = driver.findElement(By.xpath("//h1[contains(text(),'Manutenção')]")).getText();  //captura o titulo da tela
		Assert.assertEquals("Manutenção", tituloTela);
	}
	/**
	 * Apresenta alert de configuracao perdida para o botao visualizar grupo.
	 */
	@Test
	public void configuracaoPerdidaVisualizarGrupoAceitar(){
		
		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		permissaoUsuarioPage.clicarMenuPermissao("EasyView");
		
		Select tipoPermissao = new Select(driver.findElement(By.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");
		
		Select selecionaGrupo = new Select(driver.findElement(By.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")).click(); //associar permissao
		
		driver.findElement(By.id("btnVisualizarGrupos")).click();
		
		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.accept();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-dialog-title")));
		String tituloTela = driver.findElement(By.className("ui-dialog-title")).getText();  //captura o titulo da tela
		Assert.assertEquals("Visualizar Grupos", tituloTela);
	}
	/**
	 * Apresenta mensagem  de configuracao perdida para o botao visualizar grupo e depois clica em cancelar.
	 */
	@Test
	public void configuracaoPerdidaVisualizarGrupoCancelar(){
		
		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		permissaoUsuarioPage.clicarMenuPermissao("EasyView");
		
		Select tipoPermissao = new Select(driver.findElement(By.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");
		
		Select selecionaGrupo = new Select(driver.findElement(By.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")).click(); //associar permissao
		
		driver.findElement(By.id("btnVisualizarGrupos")).click();
		
		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.dismiss();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Manutenção')]")));
		String tituloTela = driver.findElement(By.xpath("//h1[contains(text(),'Manutenção')]")).getText();  //captura o titulo da tela
		Assert.assertEquals("Manutenção", tituloTela);
	}
	/**
	 * Apresenta alert de configuracao perdida para o botao adicionar usuario.
	 */
	@Test
	public void configuracaoPerdidaIncluirUsuarioAceitar(){
		
		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		permissaoUsuarioPage.clicarMenuPermissao("EasyView");		
		Select tipoPermissao = new Select(driver.findElement(By.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");
		
		Select selecionaGrupo = new Select(driver.findElement(By.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")).click(); //associar permissao
		
		driver.findElement(By.id("btnAddUsuario")).click();
		
		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.accept();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-dialog-title")));
		String tituloTela = driver.findElement(By.className("ui-dialog-title")).getText();  //captura o titulo da tela
		Assert.assertEquals("Cadastrar Usuário", tituloTela);
	}
	/**
	 * Apresenta Alert de configuracao perdida para o botao adicionar usuario e depois clica em cancelar.
	 */
	@Test
	public void configuracaoPerdidaIncluirUsuarioCancelar(){
		
		incluirUsuarioPage.inserirUsuarioBuilder(login, nome, email, telefone, area, regional, responsavel);

		incluirUsuarioPage.inserirUsuarioBuilder(loginAdm, nomeAdm, emailAdm, telefone, area, regional, responsavel);

		incluirGrupoPage.incluirGrupoBuilder(grupo, descricaoGrupo);

		incluirGrupoPage.incluirGrupoBuilder(grupoAdm, descricaoGrupo);

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		permissaoUsuarioPage.clicarMenuPermissao("EasyView");
		
		Select tipoPermissao = new Select(driver.findElement(By.id("selectTipo")));
		tipoPermissao.selectByVisibleText("Grupos");
		
		Select selecionaGrupo = new Select(driver.findElement(By.id("selectPermissaoUsuario")));
		selecionaGrupo.selectByVisibleText(grupo);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
		driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")).click(); //associar permissao
		
		driver.findElement(By.id("btnVisualizarUsuarios")).click();
		
		Alert msg = driver.switchTo().alert();
		Assert.assertEquals("As configurações de Associação e Permissão serão perdidas, deseja continuar ?", msg.getText());
		msg.accept();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Manutenção')]")));
		String tituloTela = driver.findElement(By.xpath("//h1[contains(text(),'Manutenção')]")).getText();  //captura o titulo da tela
		Assert.assertEquals("Manutenção", tituloTela);
	}
	@Parameters
	public static List<Object[]>datadriven(){
		return Arrays.asList(new Object[][]{
			{"grievous", "Griveous", "grievous@visent.com.br2", "TESTE AREA", "879285539", "Claro DF", "TESTE RESPONSAVEL", "EasyView", "condedooku", "Dooku", "dooku@visent.com.br", "GRUPO CLARO", "Grupo equipe claro", "JEDI"},
		});
	}
	
	@After
	public void tearDown(){
		incluirUsuarioPage.excluirUsuarioPeloBanco(login);
		incluirGrupoPage.excluirGrupoPeloBanco(grupo);
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginAdm);
		incluirGrupoPage.excluirGrupoPeloBanco(grupoAdm);
	}
}
