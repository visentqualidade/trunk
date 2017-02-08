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
import br.com.visent.metricview.selenium.usuario.AlterarUsuarioPage;
import br.com.visent.metricview.selenium.usuario.ExcluirUsuarioPage;
import br.com.visent.metricview.selenium.usuario.IncluirUsuarioPage;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Conexao;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;
@Ignore
@RunWith(Parameterized.class)
public class LogEditarUsuarioTest {
	VisentTestCase test;
	WebDriver      driver;
	Connection 	   con;
	Properties 	   pFile;
	String		   url;
	LogsPage           log;	
	IncluirUsuarioPage incluirUsuario;
	AlterarUsuarioPage alterarUsuario;
	ExcluirUsuarioPage excluirUsuario;
	
	String login;
	String senha;
	String loginUsuario;
	String nome;
	String email;
	String telefone;
	String area;
	String regional;
	String responsavel;
	
	
	public LogEditarUsuarioTest(String _login,String _senha,String _loginUsuario,String _nome,String _email,String _telefone,String _area,String _regional,String _responsavel){
		this.login = _login;
		this.senha = _senha;
		this.loginUsuario = _loginUsuario;
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
		alterarUsuario = new AlterarUsuarioPage(driver);
		excluirUsuario = new ExcluirUsuarioPage(driver);
		log = new LogsPage();
		con    = new Conexao().getConnection();
		pFile   = test.carregaProperties(new File("src/test/selenium/br/com/visent/metricview/selenium/resources/caminhos.properties"));
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
	}
	
	@Test
	public void logEditarUsuario() throws InterruptedException, AWTException, SQLException{
		log.fazerLogin(login, senha, driver, pFile, url);
		Thread.sleep(500);
		driver.findElement(By.id("visualizacao-admin")).click();
		Thread.sleep(2000);	
		incluirUsuario.inserirUsuario(loginUsuario, nome, email, telefone, area, regional, responsavel);
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click();
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}		
		Thread.sleep(1000);		
		driver.findElement(By.id("btnVisualizarUsuarios")).click();
		Thread.sleep(500);	
		alterarUsuario.clicarBotaoTabela(alterarUsuario.buscarId(loginUsuario), "editar");
		Thread.sleep(500);	
		alterarUsuario.alterar(nome, email, telefone, area, regional, responsavel, 
				nome+"Novo", email, telefone, area, regional, responsavel);
		alterarUsuario.clicarBotaoTabela(alterarUsuario.buscarId(loginUsuario), "editar");
		Thread.sleep(500);
		Date data = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");		
		String dataAlteracao = format.format(data);
		mensagem = null;
		mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		Thread.sleep(500);
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}	
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'close')]")).click();
		Thread.sleep(500);
		driver.findElement(By.id("btnVisualizarUsuarios")).click();
		Thread.sleep(500);
		excluirUsuario.excluir(loginUsuario);
		driver.switchTo().alert().accept();
		mensagem = null;
		mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}									
		log.iniciarGeracaoLog(driver,url);
		if(!log.validaLogsUsuario(dataAlteracao, "Henrique Garcia", nome+"Novo", "Portal", "Alterar", driver)){
			Assert.fail();
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
			{/* Login */			"henrique", 
			 /* Senha */			"henrique", 
			 /* loginUsuario */		"usuarioeditareutomacao", 
			 /* nome */				"usuarioEditarAutomacao", 
			 /* email */			"usuario@automacao.com", 
			 /* telefone */			"123456789", 
			 /* area */				"AreaAutomacao", 
			 /* regional */ 		"Claro DF",
			 /* responsavel */ 		"Automacao"
			},
		});
	}
}
