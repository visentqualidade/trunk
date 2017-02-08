package br.com.visent.metricview.selenium.logs;

import java.io.File;
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
import org.openqa.selenium.support.ui.Select;
import br.com.visent.metricview.selenium.logs.LogsPage;
import br.com.visent.metricview.selenium.usuario.IncluirUsuarioPage;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;
@Ignore
@RunWith(Parameterized.class)
public class LogPermissaoTest {
	WebDriver 		driver;
	VisentTestCase  test;
	Properties 	    pFile;
	String			url;
	LogsPage 			log;
	String 			dataPermissao;
	IncluirUsuarioPage incluirUsuario;
	//Variaveis usuario
	String login;
	String nome;
	String email;
	String telefone;
	String area;
	String regional;
	String responsavel;
	
	
	public LogPermissaoTest(String _login,String _nome,String _email,String _telefone,String _area,String _regional,String _responsavel){
		this.login = _login;
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
		log = new LogsPage();
		pFile  = test.carregaProperties(new File("src/test/selenium/br/com/visent/metricview/selenium/resources/caminhos.properties"));
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
	}
	
	@Test
	public void logPermissao() throws InterruptedException{
		log.fazerLogin("henrique", "henrique", driver, pFile, url);
		Thread.sleep(500);
		driver.findElement(By.id("visualizacao-admin")).click();
		Thread.sleep(2000);	
		Thread.sleep(2000);	
		incluirUsuario.inserirUsuario(login, nome, email, telefone, area, regional, responsavel);
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click();
		Thread.sleep(500);
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}
		Thread.sleep(5000);
		Select grupoSelect = new Select(driver.findElement(By.id("selectTipo")));
		grupoSelect.selectByVisibleText("Usuários");			
		Select usuarioSelect = new Select(driver.findElement(By.id("selectPermissaoUsuario")));
		usuarioSelect.selectByVisibleText(nome+" (" + login.toLowerCase() + ")");	
		driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")).click();
		Thread.sleep(500);		
		driver.findElement(By.id("salvarPermissoes")).click();
		Thread.sleep(6000);		
		Date data = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");		
		dataPermissao = format.format(data);
		Thread.sleep(500);		
		driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgretirar']")).click();
		Thread.sleep(500);		
		driver.findElement(By.id("salvarPermissoes")).click();
		log.iniciarGeracaoLog(driver,url);
		Thread.sleep(500);
		if(!log.validaAssociacao(dataPermissao,"Henrique Garcia","Portal","Permissao", driver)){		
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
			{"usuarioaPermissao" ,"usuario", "usuarioaPermissao@usuarioaPermissao.com","879234539","areateste","Claro DF","teste"},
		});
	}
}
