package br.com.visent.metricview.selenium.logs;

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
import br.com.visent.metricview.selenium.grupo.ExcluirGrupoPage;
import br.com.visent.metricview.selenium.grupo.IncluirGrupoPage;
import br.com.visent.metricview.selenium.logs.LogsPage;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Conexao;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;
@Ignore
@RunWith(Parameterized.class)
public class LogExcluirGrupoTest {
	WebDriver 			driver;
	VisentTestCase 		test;
	Connection 	   		con;
	Properties 	   		pFile;
	String				url;
	VisentTestCase 		testVis;
	IncluirGrupoPage 			incluirGrupo;
	ExcluirGrupoPage				excluirGrupo;
	LogsPage 				log;	
	String 				login;
	String 				senha;
	String 				nomeGrupo;
	String 				descGrupo;
	String 				dataExclusao;

	public LogExcluirGrupoTest(String _login,String _senha,String _nomeGrupo,String _descGrupo){
		this.login = _login;
		this.senha = _senha;
		this.nomeGrupo = _nomeGrupo;
		this.descGrupo = _descGrupo;
	}
	
	@Before
	public void setUp(){
		test   = new VisentTestCase();
		driver = test.chamarBrowser(Browser.CHROME);
		incluirGrupo = new IncluirGrupoPage(driver);
		excluirGrupo = new ExcluirGrupoPage(driver);
		log = new LogsPage();
		con    = new Conexao().getConnection();
		pFile   = test.carregaProperties(new File("src/test/selenium/br/com/visent/metricview/selenium/resources/caminhos.properties"));
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
	}
	
	@Test
	public void logAdicionarGrupo() throws InterruptedException{
		log.fazerLogin(login, senha, driver, pFile, url);
		Thread.sleep(500);
		driver.findElement(By.id("visualizacao-admin")).click();
		Thread.sleep(500);
		incluirGrupo.incluirGrupo(nomeGrupo, descGrupo);
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click();
		Thread.sleep(500);
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}
		Thread.sleep(500);		
		excluirGrupo.excluirGrupo(nomeGrupo, descGrupo);
		Thread.sleep(500);		
		driver.switchTo().alert().accept();
		Date data = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");		
		dataExclusao = format.format(data);
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'close')]")).click();
		Thread.sleep(2000);	
		driver.findElement(By.id("btnAddGrupo")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("nomeGrupo")).sendKeys(nomeGrupo);
		driver.findElement(By.id("descricaoGrupo")).sendKeys(descGrupo);
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click();
		log.iniciarGeracaoLog(driver,url);
		Thread.sleep(500);
		if(!log.validaLogsGrupo(dataExclusao, "Henrique Garcia", nomeGrupo , "Portal", "Excluir", driver)){
			Assert.fail("Não gerou log");
		}
		Thread.sleep(500);
		driver.findElement(By.id("visualizacao-admin")).click();
		Thread.sleep(500);		
		excluirGrupo.excluirGrupo(nomeGrupo, descGrupo);
		Thread.sleep(500);		
		driver.switchTo().alert().accept();
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
			 /* Senha */		"henrique",
			 /* nomeGrupo */	"GrupoAutomacao",
			 /* descGrupo */	"Descricao do grupo"},	
		});
	}
}
