package br.com.visent.metricview.selenium.logs;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Conexao;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;
@Ignore
@RunWith(Parameterized.class)
public class LogLoginLogoffTest {
	WebDriver driver;
	VisentTestCase test;
	Connection 	   con;
	Properties 	   pFile;
	String			url;
	VisentTestCase testVis;
	
	String nomeGrupo;
	String descGrupo;
	String dataLogin;
	String dataLogoff;
	LogsPage log;
	
	public LogLoginLogoffTest(String _nomeGrupo, String _descGrupo){
		this.nomeGrupo = _nomeGrupo;
		this.descGrupo = _descGrupo;		
	}
	
	@Before
	public void setUp(){
		test   = new VisentTestCase();
		driver = test.chamarBrowser(Browser.CHROME);
		con    = new Conexao().getConnection();
		pFile   = test.carregaProperties(new File("src/test/selenium/br/com/visent/metricview/selenium/resources/caminhos.properties"));
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
	}
	
	@Test
	public void logLogin() throws InterruptedException{
		log = new LogsPage();
		Date data = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");		
		dataLogin = format.format(data);
		log.fazerLogin("henrique", "henrique", driver, pFile, url);	
		Thread.sleep(500);
		driver.findElement(By.xpath("//img[@id='imgArrow']")).click();
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//li[@id='logout']")).click();
		dataLogoff = format.format(data);
		Thread.sleep(5000);
		driver.findElement(By.id("inputUsuario")).sendKeys("henrique");
		driver.findElement(By.id("inputSenha")).sendKeys("henrique");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Thread.sleep(7000);
		driver.get(url);
		Thread.sleep(2000);
		driver.findElement(By.id("visualizacao-admin")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("visualizacao-logs")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//th[contains(text(),'Data/Hora')]")).click();	
		Thread.sleep(500);			
		if(!log.validaLogLoginLogoff(dataLogin, dataLogoff, "Henrique Garcia", "Portal", driver)){
			Assert.fail("Não gerou log");
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
			{"pom2121", "pom2"},
		});
	}
}
