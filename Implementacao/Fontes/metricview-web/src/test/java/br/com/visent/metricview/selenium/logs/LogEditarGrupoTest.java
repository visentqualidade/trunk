package br.com.visent.metricview.selenium.logs;

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


import br.com.visent.metricview.selenium.grupo.AlterarGrupoPage;
import br.com.visent.metricview.selenium.grupo.ExcluirGrupoPage;
import br.com.visent.metricview.selenium.grupo.IncluirGrupoPage;
import br.com.visent.metricview.selenium.logs.LogsPage;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Conexao;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;
@Ignore
@RunWith(Parameterized.class)
public class LogEditarGrupoTest {
	WebDriver driver;
	VisentTestCase test;
	Connection 	   con;
	Properties 	   pFile;
	String			url;
	VisentTestCase testVis;
	LogsPage log;
	AlterarGrupoPage alterarGrupo;
	IncluirGrupoPage incluirGrupo;
	ExcluirGrupoPage				excluirGrupo;
	String dataAlteracao;
	
	
	String login;
	String senha;
	String nomeGrupo;
	String descGrupo;
	
	public LogEditarGrupoTest(String _login, String _senha,String _nomeGrupo,String _descGrupo){
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
		alterarGrupo = new AlterarGrupoPage(driver);
		excluirGrupo = new ExcluirGrupoPage(driver);
		log = new LogsPage();		
		con    = new Conexao().getConnection();
		pFile   = test.carregaProperties(new File("src/test/selenium/br/com/visent/metricview/selenium/resources/caminhos.properties"));
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
	}
	
	@Test
	public void logEditarGrupo() throws InterruptedException, SQLException {
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
		Thread.sleep(6000);
		driver.findElement(By.id("visualizacao-painel")).click();
		Thread.sleep(500);
		String idGrupo = alterarGrupo.buscarId(nomeGrupo);
		Thread.sleep(500);
		alterarGrupo.clicarBotaoAlterarGrupo(nomeGrupo, idGrupo);
		Thread.sleep(500);
		Thread.sleep(500);
		alterarGrupo.alterarGrupo(nomeGrupo, nomeGrupo+"Novo", idGrupo, descGrupo, descGrupo+"Nova");
		Thread.sleep(500);
		mensagem = null;
		mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}
		Date data = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		dataAlteracao = format.format(data);
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'close')]")).click();
		Thread.sleep(500);		
		excluirGrupo.excluirGrupo(nomeGrupo+"Novo", descGrupo);
		Thread.sleep(2000);		
		driver.switchTo().alert().accept();
		log.iniciarGeracaoLog(driver,url);
		if(!log.validaLogsGrupo(dataAlteracao, "Henrique Garcia",nomeGrupo+"Novo" , "Portal", "Alterar", driver)){
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
			{/* Login */		"henrique", 
			 /* Senha */		"henrique",
			 /* nomeGrupo */	"GrupoEditarAutomacao",
			 /* descGrupo */	"Descricao do grupo",			 
			},	
		});
	}
}













