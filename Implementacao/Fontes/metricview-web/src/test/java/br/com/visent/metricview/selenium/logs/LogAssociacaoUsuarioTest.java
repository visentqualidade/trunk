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
import org.openqa.selenium.support.ui.Select;

import br.com.visent.metricview.selenium.grupo.ExcluirGrupoPage;
import br.com.visent.metricview.selenium.grupo.IncluirGrupoPage;
import br.com.visent.metricview.selenium.usuario.ExcluirUsuarioPage;
import br.com.visent.metricview.selenium.usuario.IncluirUsuarioPage;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Conexao;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;


@Ignore
@RunWith(Parameterized.class)
public class LogAssociacaoUsuarioTest {
	WebDriver driver;
	VisentTestCase  test;
	Connection 	    con;
	Properties 	    pFile;
	String			url;
	LogsPage 			log;
	IncluirUsuarioPage  incluirUsuario;
	IncluirGrupoPage			incluirGrupo;
	ExcluirUsuarioPage  excluirUsuario;
	ExcluirGrupoPage			excluirGrupo;
	
	String 			login;
	String 			senha;
	String 			loginusuario;
	String 			nome;	
	String 			email;
	String			telefone;
	String 			area;
	String 			regional;
	String 			responsavel;
	String 			nomeGrupo;
	String 			descGrupo;
	String 			dataAssociacao;
		
	public LogAssociacaoUsuarioTest(String _login,String _senha,String _loginusuario, String _nome, String _email,String _telefone,String _area,String _regional, String _responsavel,String _nomeGrupo,String _descGrupo){
		this.login = _login;
		this.senha = _senha;
		this.loginusuario = _loginusuario;
		this.nome = _nome;
		this.email = _email;
		this.telefone = _telefone;
		this.area = _area;
		this.regional = _regional;
		this.responsavel = _responsavel;
		this.nomeGrupo = _nomeGrupo;
		this.descGrupo = _descGrupo;
	}

	
	@Before
	public void setUp(){
		test   = new VisentTestCase();
		driver = test.chamarBrowser(Browser.CHROME);
		log    = new LogsPage();
		incluirUsuario = new IncluirUsuarioPage(driver);
		incluirGrupo = new IncluirGrupoPage(driver);
		excluirGrupo = new ExcluirGrupoPage(driver);
		excluirUsuario = new ExcluirUsuarioPage(driver);
		con    = new Conexao().getConnection();
		pFile  = test.carregaProperties(new File("src/test/selenium/br/com/visent/metricview/selenium/resources/caminhos.properties"));
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
	}
	
	@Test
	public void logAdicionarGrupo() throws InterruptedException, SQLException{
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
		incluirGrupo.incluirGrupo(nomeGrupo, descGrupo);
		driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click();
		mensagem = null;
		mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}
		Thread.sleep(5000);		
		Select grupoSelect = new Select(driver.findElement(By.id("selectComboGrupos")));
		grupoSelect.selectByVisibleText(nomeGrupo);		
		Select usuarioSelect = new Select(driver.findElement(By.id("selectComboUsuarios")));
		usuarioSelect.selectByVisibleText(nome+" (" + loginusuario + ")");	
		driver.findElement(By.className("imgenviar")).click();
		Thread.sleep(1000);	
		driver.findElement(By.id("salvarManutencao")).click();
		Thread.sleep(500);
		mensagem = null;
		mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}
		Date data = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");		
		dataAssociacao = format.format(data);
		Thread.sleep(6000);	
		driver.findElement(By.className("imgretirar")).click();
		Thread.sleep(1000);	
		driver.findElement(By.id("salvarManutencao")).click();
		mensagem = null;
		mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}		
		log.iniciarGeracaoLog(driver,url);
		if(!log.validaAssociacao(dataAssociacao,"Henrique Garcia","Portal","Associacao", driver)){		
			Assert.fail("Não gerou log");
		}
		Thread.sleep(500);
		driver.findElement(By.id("visualizacao-admin")).click();
		Thread.sleep(500);
		driver.findElement(By.id("btnVisualizarUsuarios")).click();
		Thread.sleep(500);
		excluirUsuario.clicarBotaoTabela(excluirUsuario.buscarId(loginusuario), "excluir");
		Thread.sleep(500);
		driver.switchTo().alert().accept();
		mensagem = null;
		mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		if(!mensagem.equals("Operação Realizada com Sucesso !")){
			Assert.fail(mensagem);
		}	
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[contains(text(),'close')]")).click();
		Thread.sleep(500);		
		excluirGrupo.excluirGrupo(nomeGrupo, descGrupo);
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
			 /* senha */ 		"henrique",
			 /* loginusuario */ "usuarioautomacao" ,
			 /* nome */ 		"UsuarioAutomacao",
			 /* email */ 		"usarioteste@usarioteste.com",
			 /* telefone */ 	"879285539",
			 /* area */ 		"AreaAutomacao",
			 /* regional */ 	"Claro DF",
			 /* responsavel */ 	"Automacao",
			 /* nomeGrupo */ 	"GrupoAutomacao",
			 /* descGrupo */ 	"DescGrupoAuto"},
		});
	}
}
