package br.com.visent.metricview.selenium.usuario;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.metricview.selenium.core.BaseTestCase;
import br.com.visent.metricview.selenium.login.LoginPage;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;


@RunWith(Parameterized.class)
public class AlterarUsuarioTest extends BaseTestCase{

	VisentTestCase util2;
	IncluirUsuarioPage incluirUsuarioPage;
	ExcluirUsuarioPage excluirUsuarioPage;
	AlterarUsuarioPage alterarUsuarioPage;
	WebDriver	   driverDesc;
	WebDriverWait  wait;
	
	//dados
	String userSystem;
	String senhaSystem;
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
	
	public AlterarUsuarioTest(String _userSystem, String _senhaSystem, String _loginOrig, String _nomeOrig, 
							   String _emailOrig, String _telefoneOrig, String _areaOrig, String _regionalOrig, 
							   String _responsavelOrig, String _nomeEdit, String _emailEdit, String _telefoneEdit,
							   String _areaEdit, String _regionalEdit, String _responsavelEdit){
		
		this.userSystem		 = _userSystem;
		this.senhaSystem     = _senhaSystem;
		this.loginOrig       = _loginOrig;
		this.nomeOrig  		 = _nomeOrig;
		this.emailOrig 		 = _emailOrig;
		this.telefoneOrig    = _telefoneOrig;
		this.areaOrig  		 = _areaOrig;
		this.regionalOrig 	 = _regionalOrig;
		this.responsavelOrig = _responsavelOrig;
		//dados da edição
		this.nomeEdit 		 = _nomeEdit;
		this.emailEdit 		 = _emailEdit;
		this.telefoneEdit 	 = _telefoneEdit;
		this.areaEdit 		 = _areaEdit;
		this.regionalEdit 	 = _regionalEdit;
		this.responsavelEdit = _responsavelEdit;
	}
	
	@Before
	public void setUp(){
		incluirUsuarioPage 	= new IncluirUsuarioPage(driver);
		alterarUsuarioPage 	= new AlterarUsuarioPage(driver);
		excluirUsuarioPage 	= new ExcluirUsuarioPage(driver);
		wait = new WebDriverWait(driver, 10);
	}

	/**
	 * Esse cenario faz a alteração do usuario e verifica no banco.
	 * RESULTADO ESPERADO: valida no banco se as informações foram alteradas.
	 */
	@Test
	public void alterarUsuario(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuarioBuilder(loginOrig, nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig);
		
		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();
		
		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "editar");
		
		alterarUsuarioPage.alterar(nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig, 
						nomeEdit, emailEdit, telefoneEdit, areaEdit, regionalEdit, responsavelEdit);
		
		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "editar");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); //captura a mensagem de sucesso
		
		incluirUsuarioPage.validanoBanco(loginOrig, nomeEdit, emailEdit, telefoneEdit, areaEdit, regionalEdit, responsavelEdit);
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
	}

	/**
	 * Usuario nao preenche o campo nome e salva.
	 * RESULTADO ESPERADO: Sistema deve exibir a mensagem " O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void obrigatoriedadeNome(){
		
		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuarioBuilder(loginOrig, nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig);
		
		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();
				
		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "editar");
		
		alterarUsuarioPage.alterar(nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig, 
						"", emailEdit, telefoneEdit, areaEdit, regionalEdit, responsavelEdit);

		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "editar");
		
		String mensagem = driver.findElement(By.xpath("//div[@id='dlgVisualizarUsuarios']//span[@class='textoErro']")).getText();
		
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}
	/**
	 * Usuario nao preenche o campo email e salva.
	 * RESULTADO ESPERADO: Sistema deve exibir a mensagem " O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void obrigatoriedadeEmail(){
		
		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuarioBuilder(loginOrig, nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig);
		
		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();
		
		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "editar");
		
		alterarUsuarioPage.alterar(nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig, 
						nomeEdit, "", telefoneEdit, areaEdit, regionalEdit, responsavelEdit);

		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "editar");
		
		String mensagem = driver.findElement(By.xpath("//div[@id='dlgVisualizarUsuarios']//span[@class='textoErro']")).getText();
		
		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "excluir");
		excluirUsuarioPage.verificaAlert();
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}
	/**
	 * Deixa o campo email sem mascara e tenta salvar o usuario.
	 * RESULTADO ESPERADO: Sistema deve exibir a mensagem "* O campo em destaque foi preenchido de forma inválida."
	 */
	@Test
	public void mascaraEmail(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuarioBuilder(loginOrig, nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig);

		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();
		
		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "editar");
		
		alterarUsuarioPage.alterar(nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig, 
						nomeEdit, "asdasdvisent.com", telefoneEdit, areaEdit, regionalEdit, responsavelEdit);

		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "editar");
		
		String mensagem = driver.findElement(By.xpath("//div[@id='dlgVisualizarUsuarios']//span[@class='textoErro']")).getText();
			
		Assert.assertEquals("* O campo em destaque foi preenchido de forma inválida.", mensagem);
	}
	/**
	 * Usuario informa o campo nome com caractere especiais.
	 * RESULTADO ESPERADO: Sistema deve exibir a mensagem "Não é permitido caracter especial."
	 */
	@Test
	public void caracteresEspeciaisNome(){
		
		loginPage.acessarPage();
	
		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuarioBuilder(loginOrig, nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig);
		
		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();
		
		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "editar");
		
		alterarUsuarioPage.alterar(nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig, 
						"nome'asda'sdasd@#$%#@<>", emailEdit, telefoneEdit, areaEdit, regionalEdit, responsavelEdit);

		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "editar");
		
		String mensagem = driver.findElement(By.xpath("//div[@id='dlgVisualizarUsuarios']//span[@class='textoErro']")).getText();
		
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}
	
	/**
	 * Desconectar um usuario e verifica se o mesmo foi para a tela de login.
	 * RESULTADO ESPERADO: Sistema deve desconectar o usuario logado.
	 */
	@Test
	public void desconectarUsuario(){
		
		try {
			
			loginPage.acessarPage();
			
			util.clicaNoMenuAdministracao();
			
			incluirUsuarioPage.inserirUsuario(loginOrig, nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig);
			
			util2       = new VisentTestCase();
			driverDesc  = util2.chamarBrowser(Browser.CHROME);
			
			new LoginPage(driverDesc).acessarPage();
			
			util2.login(loginOrig, loginOrig);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnVisualizarUsuarios")));
			WebElement visualizarUser = driver.findElement(By.id("btnVisualizarUsuarios"));
			
			visualizarUser.click();
			
			alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "desconectar naoeditavel");
			util.verificaAlert("Tem certeza de que deseja desconectar o usuário !");
			
			String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); //captura a mensagem de sucesso
			
			new WebDriverWait(driverDesc, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'Usuário:')]")));
			
			String usuario = driverDesc.findElement(By.xpath("//label[contains(text(),'Usuário:')]")).getText();
			String senha   = driverDesc.findElement(By.xpath("//label[contains(text(),'Senha:')]"))  .getText();
			
			alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(loginOrig), "excluir");
			excluirUsuarioPage.verificaAlert();
			
			driverDesc.close();
			driverDesc.quit();
			
			Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
			Assert.assertEquals(usuario, "Usuário:");
			Assert.assertEquals(senha  , "Senha:");
			
		} catch (Exception e) {
			driverDesc.close();
			driverDesc.quit();
			e.printStackTrace();
		}
	}
	
	/**
	 * Cenario usuario clica no checkbox Mostrar Somente Usuários conectados"
	 * RESULTADO ESPERADO: sistema deve mostrar somente os usuarios logados.
	 */
	@Test
	public void mostrarUsuariosLogados() {
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnVisualizarUsuarios")));
		WebElement visualizarUser = driver.findElement(By.id("btnVisualizarUsuarios"));
		
		visualizarUser.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkSomenteConectado")));
		
		driver.findElement(By.id("checkSomenteConectado")).click();
		
		boolean logado = alterarUsuarioPage.visualizaUsuarioConectado(Constantes.USUARIO);
		
		if(logado){
			
			Assert.fail("Sisteman não esta mostrando o usuário logado ! ");
		}
	}
	
	/**
	 * Cenario fecha a janela e verifica se o mesmo foi para a tela anterior.
	 */
	@Test
	public void fecharJanela(){
		
		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnVisualizarUsuarios")));
		WebElement visualizarUser = driver.findElement(By.id("btnVisualizarUsuarios"));
		visualizarUser.click();
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='ui-dialog-title']")));
		
		String titulo =  driver.findElement(By.xpath("//span[@class='ui-dialog-title']")).getText(); //Verifica titulo da  tela

		driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-closethick']")).click();    //fechar
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String titulotela = driver.findElement(By.xpath("//h1[contains(text(),'Manutenção')]")).getText();
		
		Assert.assertEquals(titulo, "Visualizar Usuários"); //Verifica titulo da  tela
		Assert.assertEquals(titulotela, "Manutenção");
	}
	/**
	 * Cenario abre a janela visualizar usuario e depois fecha com o botao Fechar.
	 */
	@Test
	public void cancelarModal(){

		loginPage.acessarPage();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("visualizacao-admin")));

		WebElement telaAdm = driver.findElement(By.id("visualizacao-admin")); //clica menu Administracao
		
		telaAdm.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnVisualizarUsuarios")));
		
		WebElement visualizarUser = driver.findElement(By.id("btnVisualizarUsuarios"));
		visualizarUser.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='ui-dialog-title']")));
		
		String titulo =  driver.findElement(By.xpath("//span[@class='ui-dialog-title']")).getText(); //Verifica titulo da  tela

		driver.findElement(By.id("Fechar")).click(); //salvar
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String titulotela = driver.findElement(By.xpath("//h1[contains(text(),'Manutenção')]")).getText();
		
		Assert.assertEquals(titulo, "Visualizar Usuários"); //Verifica titulo da  tela
		Assert.assertEquals(titulotela, "Manutenção");
	} 
	/**
	 * Reseta a senha do usuario e verifica se o mesmo foi para a tela de alterar dados.
	 * RESULTADO ESPERADO: Sistema deve resetar a senha do usuario.
	 */
	@Test
	public void resetarSenha(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnVisualizarUsuarios")));
		
		WebElement visualizarUser = driver.findElement(By.id("btnVisualizarUsuarios"));
		visualizarUser.click();
		
		alterarUsuarioPage.clicarBotaoTabela(alterarUsuarioPage.buscarId(Constantes.USUARIO), "resetarSenha naoeditavel");  //reseta a senha do usuario
		
		util.verificaAlert("Tem certeza de que deseja resetar a senha !"); //valida a mensagem
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Fechar")));
		
		driver.findElement(By.id("Fechar")).click(); //fecha a janela
		
		util.logOut();

		util.login(Constantes.USUARIO, Constantes.SENHA);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='ui-dialog-title']")));
		
		String titulo =  driver.findElement(By.xpath("//span[@class='ui-dialog-title']")).getText(); //Verifica titulo da  tela
		
		driver.findElement(By.id("senhaAntigaDados"		)).sendKeys(Constantes.USUARIO);
		driver.findElement(By.id("senhaNovaDados"		)).sendKeys(Constantes.USUARIO);
		driver.findElement(By.id("senhaNovaConfirmDados")).sendKeys(Constantes.USUARIO);
		
		driver.findElement(By.id("Salvar")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); //captura a mensagem de sucesso
		
		Assert.assertEquals("Alterar dados", titulo);
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
	}
	@Parameters
	public static List<Object[]>datadriven(){
		return Arrays.asList(new Object[][]{
			{"system", "visent.1970", "jordan", "Hal Jordan", "jordan@visent.com.br", "335698745", "Area 69", "Claro DF", "Alessandra", "Lanterna Vermelho", "vermelho@visent.com.br", "569875312", "Area 75", "Claro PE", "Erick"},
		});
	}
	
	@After
	public void tearDown(){
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginOrig);
		
	}
}
