package br.com.visent.metricview.selenium.logs;

import java.awt.AWTException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import br.com.visent.metricview.selenium.logs.LogsPage;
import br.com.visent.metricview.selenium.usuario.ExcluirUsuarioPage;
import br.com.visent.metricview.selenium.usuario.IncluirUsuarioPage;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Conexao;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;
@Ignore
@RunWith(Parameterized.class)
public class LogExcluirUsuarioTest {
	VisentTestCase test;
	WebDriver      driver;
	Connection 	   con;
	Properties 	   pFile;
	String		   url;
	IncluirUsuarioPage incluirUsuario;
	ExcluirUsuarioPage excluirUsuario;
	
	LogsPage log;
	String login;
	String senha;
	String loginusuario;
	String nome;	
	String email;
	String telefone;
	String area;
	String regional;
	String responsavel;
	String dataExclusao;
	
	public LogExcluirUsuarioTest(String _login,String _senha,String _loginusuario, String _nome, String _email,String _telefone,String _area,String _regional, String _responsavel){
		this.login = _login;
		this.senha = _senha;
		this.loginusuario = _loginusuario;
		this.nome = _nome;
		this.email = _email;
		this.telefone = _telefone;
		this.area = _area;
		this.regional = _regional;
		this.responsavel = _responsavel;
	}

	@Before
	public void setUp(){
		test   = new VisentTestCase();
		driver = test.chamarBrowser(Browser.CHROME);
		incluirUsuario = new IncluirUsuarioPage(driver);
		excluirUsuario = new ExcluirUsuarioPage(driver);
		log = new LogsPage();
		con    = new Conexao().getConnection();
		pFile   = test.carregaProperties(new File("src/test/selenium/br/com/visent/metricview/selenium/resources/caminhos.properties"));
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
	}
	
	@Test
	public void logExcluirUsuario() throws InterruptedException, AWTException, SQLException{
		log.fazerLogin(login, senha, driver, pFile, url);
		Thread.sleep(500);
		driver.findElement(By.id("visualizacao-admin")).click();
		Thread.sleep(2000);	
		incluirUsuario.inserirUsuario(loginusuario, nome, email, telefone, area, regional, responsavel);
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click();
		Thread.sleep(500);
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}
		Thread.sleep(500);
		driver.findElement(By.id("btnVisualizarUsuarios")).click();
		Thread.sleep(500);
		excluirUsuario.excluir(loginusuario);
		driver.switchTo().alert().accept();
		Date data = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");		
		dataExclusao = format.format(data);
		mensagem = null;
		mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'close')]")).click();
		Thread.sleep(2000);	
		driver.findElement(By.id("btnAddUsuario")).click();
		driver.findElement(By.id("loginUsuario"		 )).sendKeys(loginusuario);
		driver.findElement(By.id("nomeUsuario"		 )).sendKeys(nome);
		driver.findElement(By.id("emailUsuario"		 )).sendKeys(email);
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click();
		Thread.sleep(500);
		log.iniciarGeracaoLog(driver,url);
		if(!log.validaLogsUsuario(dataExclusao, "Henrique Garcia", nome, "Portal", "Excluir", driver)){
			Assert.fail("Não gerou log");
		}
		driver.findElement(By.id("visualizacao-admin")).click();
		Thread.sleep(500);
		driver.findElement(By.id("btnVisualizarUsuarios")).click();
		Thread.sleep(500);
		excluirUsuario.clicarBotaoTabela(excluirUsuario.buscarId(loginusuario), "excluir");
		Thread.sleep(500);
		driver.switchTo().alert().accept();
		Thread.sleep(500);
		mensagem = null;
		mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}	
	}

	@After
	public void tearDown(){
		driver.close();
		driver.quit();
	}
	
	@Parameters
	public static List<Object[]>datadriven(){
		return Arrays.asList(new Object[][]{
			{/* Login */		"henrique",
			 /* Senha */ 		"henrique",
			 /* Loginusuario */ "usuarioautomacao" ,
			 /* Nome */ 		"UsuarioAutomacao",
			 /* Email */ 		"usarioteste@usarioteste.com",
			 /* Telefone */ 	"879285539",
			 /* Area */ 		"AreaAutomacao",
			 /* Regional */ 	"Claro DF",
			 /* Responsavel */ 	"Automacao"},
		});
	}
}
