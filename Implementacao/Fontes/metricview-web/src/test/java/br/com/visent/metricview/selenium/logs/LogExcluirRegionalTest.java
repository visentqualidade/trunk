package br.com.visent.metricview.selenium.logs;

import java.awt.AWTException;
import java.io.File;
import java.sql.Connection;
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
import br.com.visent.metricview.selenium.regional.ExcluirRegionalPage;
import br.com.visent.metricview.selenium.regional.IncluirRegionalPage;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Conexao;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;
@Ignore
@RunWith(Parameterized.class)
public class LogExcluirRegionalTest {
	VisentTestCase 		test;
	WebDriver      		driver;
	Connection 	   		con;
	Properties 	   		pFile;
	String		  		url;
	LogsPage 		   		log;
	IncluirRegionalPage 	IncluirRegional;
	ExcluirRegionalPage 	excluirRegional;
	
	String 				login;
	String 				senha;
	String 				regional;
	String 				descricao;
	String 				cn;
	String 				dataExclusao;
	
	public LogExcluirRegionalTest(String _login,String _senha,String _regional, String _descricao, String _cn){
		this.login = _login;
		this.senha = _senha;
		this.regional = _regional;
		this.descricao = _descricao;
		this.cn = _cn;
	}

	
	@Before
	public void setUp(){
		test   = new VisentTestCase();
		driver = test.chamarBrowser(Browser.CHROME);
		IncluirRegional = new IncluirRegionalPage(driver);
		excluirRegional = new ExcluirRegionalPage(driver);
		log = new LogsPage();
		con    = new Conexao().getConnection();
		pFile   = test.carregaProperties(new File("src/test/selenium/br/com/visent/metricview/selenium/resources/caminhos.properties"));
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
	}
	
	@Test
	public void logExcluirRegional() throws InterruptedException, AWTException{
		log.fazerLogin(login, senha, driver, pFile, url);
		Thread.sleep(1000);			
		IncluirRegional.clicarMenuRedes();
		Thread.sleep(500);	
		IncluirRegional.incluirRegional(regional, descricao, cn);
		driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click();
		Thread.sleep(500);	
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}
		Thread.sleep(500);
		excluirRegional.excluirRegional(regional);
		driver.switchTo().alert().accept();
		Date data = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		String dataExclusao = format.format(data);
		mensagem = null;
		mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}
		Thread.sleep(500);	
		IncluirRegional.incluirRegional(regional, descricao, cn);
		driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click();
		log.iniciarGeracaoLog(driver,url);
		if(!log.validaLogsRegional(dataExclusao, "Henrique Garcia", regional, "Portal", "Excluir", driver)){
			Assert.fail("Erro ao gerar log");
		}
		Thread.sleep(500);	
		IncluirRegional.clicarMenuRedes();
		Thread.sleep(500);	
		excluirRegional.excluirRegional(regional);
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
			{/* login */		"henrique", 
			 /* senha */		"henrique",
			 /* regional */		"RegionalAutomacao", 
			 /* descricao */	"DescRegional",
			 /* CN */			"12"},	
		});
	}
}
