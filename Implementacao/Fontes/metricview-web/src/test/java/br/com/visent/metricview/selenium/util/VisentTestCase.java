package br.com.visent.metricview.selenium.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.corporativo.job.ControleJob;
import br.com.visent.metricview.quartz.GravarLogQuartz;
import br.com.visent.metricview.util.ConstantesUtil;


public class VisentTestCase {

	private WebDriver  driver;
	private WebDriverWait wait;
	private Properties pFile;
	
	public VisentTestCase(){
	
	}
	/**
	 * Chama o brownser e seus respectivos servidores passado como parametro.
	 * Ex:  InternetExplorer
	 * 		Chrome
	 * 		Firefox
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public WebDriver chamarBrowser(Browser nomeBrowser) {
		
		if(nomeBrowser.equals(Browser.IE)){
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);      
			File file = new File("src/test/java/br/com/visent/metricview/selenium/drivers/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			driver = new InternetExplorerDriver(capabilities);	
		}
		if(nomeBrowser.equals(Browser.CHROME)){
			System.setProperty("webdriver.chrome.driver", "src/test/java/br/com/visent/metricview/selenium/drivers/chromedriver.exe");

			// Gera download e move o arquivo para a pasta selenium.
			String diretorioDownload = "c:/selenium";
			limpaDiretorio(diretorioDownload);

			String downloadFilepath = diretorioDownload;
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--test-type");
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(cap);
			
		}
		if(nomeBrowser.equals(Browser.FIREFOX)){
			driver = new FirefoxDriver();
		}
		if(nomeBrowser.equals(Browser.GHOST)){
			
			ArrayList argumentosPhantomJS = new ArrayList();  
			argumentosPhantomJS.add("--ignore-ssl-errors=true");  
			argumentosPhantomJS.add("--ssl-protocol=any");  
			argumentosPhantomJS.add("--proxy-type=none");

			DesiredCapabilities dcaps = new DesiredCapabilities();  
			dcaps.setCapability("takesScreenshot", false);
			dcaps.setJavascriptEnabled(true);
			dcaps.setCapability("javascriptCanCloseWindows", true);
			dcaps.setCapability("javascriptCanOpenWindows", true);
			dcaps.setCapability("loadImages", true);
			dcaps.setCapability("localToRemoteUrlAccessEnabled", false);
			dcaps.setCapability("webSecurityEnabled", true);
			dcaps.setCapability("phantomjs.ghostdriver.cli.args", argumentosPhantomJS);
			File executavelPhantomJS = new File("src/test/java/br/com/visent/metricview/selenium/drivers/phantomjs.exe");  
			dcaps.setCapability("phantomjs.binary.path", executavelPhantomJS.getAbsolutePath());

			driver = new PhantomJSDriver(dcaps); 
		}
		return driver;
	}
	
	/**
	 * Metodo que clica no menu Administrador
	 */
	public void clicaNoMenuAdministracao(){
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("visualizacao-admin")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("visualizacao-admin")));
			driver.findElement(By.id("visualizacao-admin")).click(); //clica menu Administracao
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pageUsuarios")));
			Thread.sleep(2000);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no menu Administracao!");
			e.printStackTrace();
		}
	}
	
	public void clicaNoIconeFecharDaModal(){
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='ui-icon ui-icon-closethick']")));
		driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-closethick']")).click(); // fechar
	}
	
	/**
	 * Verifica se existe o diretorio se existir ele limpa se nao ele cria.
	 * 
	 * @param dir
	 *            passa a stringo do diretorio como parametro.
	 */
	public void limpaDiretorio(String dir) {
		File arq = new File(dir);
		if (arq.exists()) {
			if (arq.listFiles().length > 0) {
				for (File f : arq.listFiles()) {
					f.delete();
				}
			}
		} else {
			arq.mkdir();
		}
	}
	
	/**
	 * Metodo que faz o login no sistema.
	 * @param usuario
	 * 			passa como parametro o nome do usuário.
	 * @param senha
	 * 			passa como paramentro a senha do usuário.
	 */
	public void login(String usuario, String senha) {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsuario")));
			driver.findElement(By.id("inputUsuario")).sendKeys(usuario);
			driver.findElement(By.id("inputSenha"))  .sendKeys(senha);
			driver.findElement(By.xpath("//input[@value='Login']")).click();
			Thread.sleep(1000);
			verificaAlertaUsuarioLogado();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel logar!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Verifica se a pagina exibe o alerta de usuario logado e aciona o comando OK.
	 */
	public void verificaAlertaUsuarioLogado(){
		boolean condic = false;
		try {
			 condic = (new WebDriverWait(driver, 3)).until(new ExpectedCondition<Boolean>() {//Espera o alerta aparecer na tela.
	           	public Boolean apply(WebDriver d) {
	               	return driver.switchTo().alert().getText().equals("Usuário já está logado deseja se conectar mesmo assim ?");								
	            }
			});
			} catch (Exception e) {
				
			}
			if(condic){
				Alert al = driver.switchTo().alert();
				al.accept(); //Clica no botao OK
			}
	}
	/**
	 * Faz o logout do sistema.
	 */
	public void logOut() {
		try {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//img[@id='imgArrow']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//li[@id='logout']")).click();
		} catch (Exception e) {
			Assert.fail("Não foi possivel achar o botao de logout!");
		}

	}
	/**
	 * Metodo clica na janela alterar dados
	 */
	public void alterarDados(){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@id='imgArrow']")));
			driver.findElement(By.xpath("//img[@id='imgArrow']")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='alterarDados']")));
			driver.findElement(By.xpath("//li[@id='alterarDados']")).click();
			
		} catch (Exception e) {
			Assert.fail("Nao foi possivel achar o botao alterar dados!");
		}

	}
	/**
	 * Metodo que carrega o properties
	 * @param properties
	 * 			passa o caminho onde se encontra o arquivo de properties
	 * @return
	 * 		retorna file com o arquivo.
	 */
	public Properties carregaProperties(File properties) {
		//Carrega o arquivos path.properties.
		pFile = new Properties();
		try {
			pFile.load(new FileReader(properties));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return pFile;
	}
	/**
	 * Metodo verifica a datahora atual.
	 * @return
	 * string no formato dd-mm-
	 */
	public String verificaDataAtual(){
		
		Date data= new Date();
		SimpleDateFormat fm = new SimpleDateFormat(" yyyy-MM-dd");
		String dataAtual = fm.format(data); 
		
		return dataAtual;
	}
	/**
	 * Esse metodo navega pela tabela de de edicao de usuarios e executa a acao dos botoes editar, excluir,
	 * resetar e desconectar usuario
	 * @param login
	 * 			passa qual o login do usuario como parametro
	 * @param botao
	 * 			passa qual a acao que o usuario ira realizar
	 */
	public void clicarBotaoTabela(String login, String botao){
		WebElement table = driver.findElement(By.className("listaVisualizarUsuarios"));
		
		List<WebElement> allRows = table.findElements(By.tagName("tr")); 
		boolean bateu = false;
		// And iterate over them, getting the cells 
		for (WebElement row : allRows) { 
		    List<WebElement> cells = row.findElements(By.tagName("td")); 
		    if(bateu){
		    	break;
		    }
			for (WebElement cell : cells) {
				if(!bateu){
					bateu = cell.getText().equals(login);
				}
				if(bateu && cell.getAttribute("class").equals(botao)){
					cell.click();
					break;
				}
			}
		}
	}
	/**
	 * Valida se a log foi para o banco.
	 * @param con
	 * 			passa a conexao como parametro.
	 * @param nomeUsuario
	 * 			passa o nome do usuario como parametro.
	 * @param mensagemLog
	 * 			passa a mensagem do log como parametro.
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public void validaLogExecucao(Connection con, String nomeUsuario, String mensagemLog) throws SQLException, InterruptedException{
		boolean bateu = false;
		iniciarJobs();
		String            dataAtual = verificaDataAtual(); // pega a data atual do sistema
		PreparedStatement qr 		= con .prepareStatement("select * from log"); // executa selct
		ResultSet 		  rs 		= qr  .executeQuery(); //executa  o select
		while (rs.next()) {
			
			Date   			 datalog   = rs.getTimestamp("data_hora");
			String 			 nome      = rs.getString("usuario");
			String 			 mensagem  = rs.getString("mensagem");
			String 			 tipo      = rs.getString("tipo");
			SimpleDateFormat fm 	   = new SimpleDateFormat(" yyyy-MM-dd HH");
			String 			 dataBanco = fm.format(datalog); 

				if(dataAtual.equals(dataBanco) 				 && 
				   nome.equals(nomeUsuario)    				 &&
				   mensagem.equals(mensagemLog)){
					
				   Assert.assertEquals(nome, nomeUsuario);
				   Assert.assertEquals(tipo, "INFO");
				   Assert.assertEquals(mensagem, mensagemLog);
				   bateu = true;
				   System.out.println("Teste Log Autenticação Validada!");
			       Assert.assertTrue(bateu);
				   break;
				}
			}
 		if(!bateu){
			Assert.fail("Teste Log Desconectado Falhou!");
		}
		rs .close();
		con.close();
	}
	/**
	 * Metodo inicia os job para gravas as logs.
	 * @throws InterruptedException 
	 */
	public void iniciarJobs() throws InterruptedException {
		ControleJob.init();
		ControleJob.addJob(ConstantesUtil.JOB_GRAVAR_LOGS, GravarLogQuartz.class, 
				new HashMap<String, Object>(), 3);
		ControleJob.start();
//		Thread.sleep(10000);
//		ControleJob.pause();
	}
	/**
	 * Captura o alert da tela e faz a assergiva com a mensagem original.
	 * @param msgOriginal
	 * 			passa como parametro a mensagem original.
	 */
	public void verificaAlert(String msgOriginal){
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			
			Alert  msgExclusao = driver.switchTo().alert();
			String msg 		   = msgExclusao.getText();
			msgExclusao.accept();
			Assert.assertEquals(msg, msgOriginal);
		} catch (Exception e) {
			Assert.fail("Nao fooi possivel verificar o alert!");
			e.printStackTrace();
		}

	}
	
	public void screeshot(String nomeArquivo){
		try {
			FileUtils.copyFile(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE), new File("C:/"+nomeArquivo+".png"), false);
		} catch (Exception e) {
		}
	}
	

//	public boolean aguardaResposta(){
//		final boolean condicao = false;
//		(new WebDriverWait(driver, 3)).until(new ExpectedCondition<Boolean>() {//Espera o alerta aparecer na tela.
//           	public Boolean apply(WebDriver d) {
//           		condicao = true;
//               	return condicao;								
//            }
//		});
//		return condicao;
//	}
}
