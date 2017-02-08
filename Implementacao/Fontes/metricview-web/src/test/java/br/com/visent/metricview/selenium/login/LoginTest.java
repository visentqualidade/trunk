package br.com.visent.metricview.selenium.login;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Conexao;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;
@Ignore
@RunWith(Parameterized.class)
public class LoginTest {

	WebDriver 	   driver;
	WebDriver      driver2; //criei 2 drivers para testar login simultaneo.
	WebDriver      driver3; //criei mais uma instancia para testar regra da qtd de usuários logados.
	VisentTestCase test;
	Properties 	   pFile;
	String 		   url;
	Connection 	   con;
	
	String 		   usuario;
	String		   senha;
	String		   nomeUsuario;
	String		   qtdUsuario1;
	String		   qtdUsuario2;
	String 		   qtdAdmin;
	String		   nomeAdmin;
	String		   userComum1;
	String		   userComum2;
	String 		   userComum3;
	
	public LoginTest(String _usuario, String _senha, String _nomeUsuario, String _qtdUsuario1, 
					  String _qtdUsuario2, String _qtdAdmin, String _nomeAdmin, String _userComum1,
					  String _userComum2,  String _userComum3){
		this.usuario 	 = _usuario;
		this.senha   	 = _senha;
		this.nomeUsuario = _nomeUsuario;
		this.qtdUsuario1 = _qtdUsuario1;
		this.qtdUsuario2 = _qtdUsuario2;
		this.qtdAdmin 	 = _qtdAdmin;
		this.nomeAdmin   = _nomeAdmin;
		this.userComum1  = _userComum1;
		this.userComum2  = _userComum2;
		this.userComum3	 = _userComum3;
	}
	
	@Before
	public void setUp(){
		test   = new VisentTestCase();
		con    = new Conexao().getConnection();
		driver = test.chamarBrowser(Browser.CHROME);
		pFile  = test.carregaProperties(new File("src/test/selenium/br/com/visent/metricview/selenium/resources/caminhos.properties"));
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
	}
	/**
	 * Esse cenario eu faço o login com 2 usuário comuns e logo com um terceiro usuário do tipo administrador.
	 * Resultado Esperado: sistema deve permitir o usuário administrador logar na pagina.
	 * @throws InterruptedException
	 */
//	@Test
	public void qtsUsuarioLogadoComumAdmin() throws InterruptedException{
		VisentTestCase test2 = new VisentTestCase();
		VisentTestCase test3 = new VisentTestCase();
		try {
		driver.get(url); //usa o chrome
		test  .login(usuario, senha);
	
		driver2 = test2.chamarBrowser(Browser.IE); //usa o brownser IE
		
		driver2.get(url);
		test2  .login(qtdUsuario1, qtdUsuario1);
		
		driver3 = test3.chamarBrowser(Browser.FIREFOX); //usa o brownser Firefox
		
		driver3.get(url);
		driver3.findElement(By.id("inputUsuario")).sendKeys(usuario);
		driver3.findElement(By.id("inputSenha"))  .sendKeys(senha);
		driver3.findElement(By.xpath("//input[@value='Login']")).click();
		
		driver3.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(1500);
		
			String nomeUser = driver.findElement(By.xpath("//p[contains(text(),'" + nomeAdmin + "')]")).getText();
			Assert.assertEquals(nomeAdmin, nomeUser);

		
		//fazer logout
		test .logOut();
		test2.logOut();
		test3.logOut();
		//fechar o brownser
		driver2.close();
		driver3.close();
		driver2.quit(); //sai do servidor do IE
		} catch (Exception e) {
			driver2.close();
			driver3.close();
			driver2.quit(); //sai do servidor do IE
			Assert.fail("Teste Falhou!");
		}

	}
	/**
	 * Para esse cenário sistema deve limitar o numero de acesso ate 2 usuários.
	 * Passo1 - Loguei com 1 usuário comum.
	 * Passo2 - Loguei em outro brownser com um usuário rafael.
	 * Passo3 - Loguei com o mesmo usuário do passo 1 em outro brownser.
	 *  Resultado Esperado: Sistema deve apresentar a mensagem "Usuário já está logado deseja se conectar mesmo assim ?".
	 * @throws InterruptedException
	 */
	@Test
	public void qtdUsuarioLogadoComNomesIguais() throws InterruptedException{
		VisentTestCase test2 = new VisentTestCase();
		VisentTestCase test3 = new VisentTestCase();
		
		driver.get(url); //usa o chrome
		test  .login(usuario, senha);
		
		driver2 = test2.chamarBrowser(Browser.IE); //usa o brownser IE

		driver2.get(url);
		test2  .login(qtdUsuario1, qtdUsuario1);
		
		driver3 = test3.chamarBrowser(Browser.FIREFOX); //usa o brownser Firefox
		
		driver3.get(url);
		driver3.findElement(By.id("inputUsuario")).sendKeys(usuario);
		driver3.findElement(By.id("inputSenha"))  .sendKeys(senha);
		driver3.findElement(By.xpath("//input[@value='Login']")).click();
		
		Thread.sleep(1000);

		try {
			(new WebDriverWait(driver3, 3)).until(new ExpectedCondition<Boolean>() {//Espera o alerta aparecer na tela.
	           	public Boolean apply(WebDriver d) {
	               	return driver3.switchTo().alert().getText().equals("Usuário já está logado deseja se conectar mesmo assim ?");								
	            }
			});
			
			test3.verificaAlertaUsuarioLogado();
			} catch (Exception e) {
				driver2.close();
				driver3.close();
				driver2.quit();
				Assert.fail("Sistema não apresentou a mensagem 'Usuário já está logado deseja se conectar mesmo assim ?'");
			}
		
		//fazer logout
//		test .logOut();
//		test2.logOut();

		//fechar o brownser
		driver2.close();
		driver3.close();
		driver2.quit(); //sai do servidor do IE
	}
	/**
	 * Para esse cenário sistema deve limitar o numero de acesso ate 2 usuários.
	 * loguei com 3 usuários comuns.
	 * Resultado Esperado: Sistema deve apresentar a mensagem "Quantidade máxima de 2 usuário(s) logados excedida!".
	 * @throws InterruptedException
	 */
	@Test
	public void qtdUsuariosLogadosComuns() throws InterruptedException{
		VisentTestCase test2 = new VisentTestCase();
		VisentTestCase test3 = new VisentTestCase();
		driver2 = test2.chamarBrowser(Browser.FIREFOX); //usa o brownser IE

		driver2.get(url);
		test2  .login(userComum2, userComum2);

		driver3 = test3.chamarBrowser(Browser.IE); //usa o brownser Firefox
		
		driver3.get(url); //usa o chrome
		test3.login(userComum3, userComum3);
		
		driver.get(url);
		driver.findElement(By.id("inputUsuario")).sendKeys(userComum1);
		driver.findElement(By.id("inputSenha"))  .sendKeys(userComum1);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
//		test.verificaAlertaUsuarioLogado();
		Thread.sleep(1000);

		String mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		
		Thread.sleep(1000);
		test2 .logOut();
		Thread.sleep(1000);
		test3 .logOut();

		//fechar o brownser
		driver2.close();
		driver3.close();
		driver2.quit(); //sai do servidor do IE
		driver3.quit();
		
		Assert.assertEquals("Quantidade máxima de 2 usuário(s) logados excedida!", mensagem);
	}
	/**
	 * Esse cenario o usuário loga com o mesmo login em browsers diferentes, e ao apresentar a mensagem o mesmo cancela.
	 * Resultado Esperado: sistema não deve permitir que o usário logue na pagina.
	 * @throws InterruptedException
	 */
	@Test
	public void acessoSimultaneoCancelar() throws InterruptedException{
		driver2 = new FirefoxDriver();//  loga com firefox
		
		driver2.get(url);
		driver2.findElement(By.id("inputUsuario")).sendKeys(usuario);
		driver2.findElement(By.id("inputSenha"))  .sendKeys(senha);
		driver2.findElement(By.xpath("//input[@value='Login']")).click();
		test = new VisentTestCase();
		test.verificaAlertaUsuarioLogado();
		driver.get(url);//loga com chrome
		driver.findElement(By.id("inputUsuario")).sendKeys(usuario);
		driver.findElement(By.id("inputSenha"))  .sendKeys(senha);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		Thread.sleep(1000);
		
		Alert msg = driver.switchTo().alert();
		msg.dismiss(); //Clica no botao cancelar
			
			//Valida se a tela se o sistema apresentou derrubou o outro usuario
			(new WebDriverWait(driver, 3)).until(new ExpectedCondition<Boolean>() {//Espera o alerta aparecer na tela.
	           	public Boolean apply(WebDriver d) {
	               	return driver.findElement(By.xpath("//label[contains(text(),'Usuário:')]")).getText().equals("Usuário:");								
	            }
			});
			
			String usuario = driver.findElement(By.xpath("//label[contains(text(),'Usuário:')]")).getText();
			String senha   = driver.findElement(By.xpath("//label[contains(text(),'Senha:')]"))  .getText();

			Assert.assertEquals(usuario, "Usuário:");
			Assert.assertEquals(senha  , "Senha:");
			
//			VisentTestCase logout = new VisentTestCase(driver2);
			test.logOut();

			//fecha o browser
			driver2.close();

	}
	/**
	 * Esse cenario o usuário faz o login com brownsers diferentes e verifica se a mensagem de usuário logado aparece na tela.
	 * Resultado Esperado: Sistema deve exibir a mensagem: "Usuário já está logado deseja se conectar mesmo assim ?"
	 * @throws InterruptedException 
	 */
	@Test
	public void acessoSimultaneo() throws InterruptedException{
		boolean condic = false;
		driver2 = new FirefoxDriver();//  loga com firefox
		
		driver2.get(url);
		driver2.findElement(By.id("inputUsuario")).sendKeys(usuario);
		driver2.findElement(By.id("inputSenha"))  .sendKeys(senha);
		driver2.findElement(By.xpath("//input[@value='Login']")).click();
		test = new VisentTestCase();
		test.verificaAlertaUsuarioLogado();
		Thread.sleep(500);
		driver.get(url);//loga com chrome
		driver.findElement(By.id("inputUsuario")).sendKeys(usuario);
		driver.findElement(By.id("inputSenha"))  .sendKeys(senha);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		test = new VisentTestCase();
//		test.verificaAlertaUsuarioLogado();
		Thread.sleep(1000);
		try {
			condic = (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {//Espera o alerta aparecer na tela.
	           	public Boolean apply(WebDriver d) {
	               	return driver.switchTo().alert().getText().equals("Usuário já está logado deseja se conectar mesmo assim ?");								
	            }
			});
			} catch (Exception e) {
				
			}
			if(condic){
				Alert msg = driver.switchTo().alert();
				msg.accept(); //Clica no botao OK
			}else{
				Assert.fail("Sistema não apresentaou a mensagem de acesso simultaneo!");
			}

			Thread.sleep(1000);
			String usuario = driver2.findElement(By.xpath("//label[contains(text(),'Usuário:')]")).getText();
			String senha   = driver2.findElement(By.xpath("//label[contains(text(),'Senha:')]"))  .getText();

			Assert.assertEquals(usuario, "Usuário:");
			Assert.assertEquals(senha  , "Senha:");

			//fecha o browser
			
			driver2.close();
			driver2.quit ();
//			test.logOut();
	}
	
	/**
	 * Esse cenario o usuário autentica de depois desconecta do sistema.
	 * Resultado Esperado: Sistema deve inserir no banco as do log informações:
	 * Data_Hora
	 * Usuario
	 * Grupo
	 * Mensagem
	 * Tipo
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void verificarLogDesconexao() throws SQLException, InterruptedException{
		boolean bateu = false;
		driver.get(url);
		test  .login(usuario, senha); //faz o login do usuario
		test  .logOut(); //desconecta do sistema
		Thread.sleep(1000);
//		test  .iniciarJobs(); //inicia o job para carregar o arquivo de logs no banco

		String            dataAtual = test.verificaDataAtual(); // pega a data atual do sistema
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
				   mensagem.equals("Foi desconectado do sistema.")){
					
				   Assert.assertEquals(nome, nomeUsuario);
				   Assert.assertEquals(tipo, "INFO");
				   Assert.assertEquals(mensagem, "Foi desconectado do sistema.");
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
	 * Esse cenario o usuário loga no sistema.
	 * Resultado Esperado: Sistema deve inserir no banco as informações:
	 * Data_Hora
	 * Usuario
	 * Grupo
	 * Mensagem
	 * Tipo
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	@Test
	public void verificarLogAutenticacao() throws SQLException, InterruptedException{
		boolean bateu = false;
	
		driver.get(url);
		test  .login(usuario, senha); //faz o login do usuario
//		test  .iniciarJobs();

		String            dataAtual = test.verificaDataAtual(); // pega a data atual do sistema
		PreparedStatement qr 		= con .prepareStatement("select * from log"); // executa selct
		ResultSet 		  rs 		= qr  .executeQuery(); //executa  o select
		
		
		while (rs.next()) {
			
			Date   			 datalog   = rs.getTimestamp("data_hora");
			String 			 nome      = rs.getString("usuario");
			String 			 mensagem  = rs.getString("mensagem");
			String 			 tipo      = rs.getString("tipo");
			SimpleDateFormat fm 	   = new SimpleDateFormat(" yyyy-MM-dd HH");
			String 			 dataBanco = fm.format(datalog); 
			
				if(dataAtual.equals(dataBanco)                   && 
				   nome     .equals(nomeUsuario)                 && 
				   tipo     .equals("INFO")                      &&
				   mensagem .equals("Foi autenticado no sistema.")){
                   Assert.assertEquals(mensagem, "Foi autenticado no sistema.");
                   Assert.assertEquals(nome, nomeUsuario);
                   Assert.assertEquals(tipo, "INFO");
				   bateu = true;
				   System.out.println("Teste Log Autenticação Validada!");
			       Assert.assertTrue(bateu);
				   break;
				}
			}
 
		if(!bateu){
			Assert.fail("Teste Log Autenticação Falhou!");
		}

		rs .close();
		con.close();
//		test.logOut();
	}
	/**
	 * Esse cenario o usuario faz o logout do sistema.
	 * Resultado esperado: sistema deve apresentar a tela de login.
	 * @throws InterruptedException
	 */
	@Test
	public void logOut() throws InterruptedException{
		driver.get(url);
		test  .login(usuario, senha);
		test  .logOut();

		String usuario = driver.findElement(By.xpath("//label[contains(text(),'Usuário:')]")).getText();
		String senha   = driver.findElement(By.xpath("//label[contains(text(),'Senha:')]")).getText();
	
		Assert.assertEquals(usuario, "Usuário:");
		Assert.assertEquals(senha  , "Senha:");
	}
	
	/**
	 * Esse cenario o usuário loga no sitema e verifica se o mesmo existe na base.
	 * Resultado Esperado: o usuário logado deve esta contido no banco.
	 * @throws SQLException
	 * @throws InterruptedException 
	 */
	@Test
	public void validaLogin() throws SQLException, InterruptedException{

		PreparedStatement qr = con.prepareStatement("select * from usuario");
		ResultSet 		  rs = qr.executeQuery();
		
		driver.get(url);
		test.login(usuario, senha);
		
		while (rs.next()) {
			
			String nome = rs.getString("login");
				if(nome.equals(usuario)){
					Assert.assertTrue(true);
					System.out.println("Teste de Login foi Validado!");
					break;
				}
			}
		rs .close();
		con.close();
//		test.logOut();
	}
	/**
	 * Esse cenario faz o login com o usuário existente e uma senha que nao existe
	 * Resultado Esperado: Sistema deve exibir a mensagem: "Usuário ou senha inválidos." 
	 * @throws InterruptedException 
	 */
	@Test
	public void loginUsarioInvalido() throws InterruptedException{
		
		driver.get(url);
		test.login(usuario, "123");
		
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText();
		
		Assert.assertEquals("Usuário ou senha inválidos.", mensagem);
		
	}
	/**
	 * Esse cenario o usuario não preenche o campo senha e aciona o comando login.
	 * Resultado Esperado: sistema não deve permitir ao usuário logar na pagina,
	 * e deve exibir a mensagem: "* O campo em destaque é de preenchimento obrigatório."
	 * @throws InterruptedException 
	 */
	@Test
	public void obrigatoriedadeSenha() throws InterruptedException{
		
		driver.get(url);
		test.login(usuario, " ");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String mensagem = driver.findElement(By.className("textoErro")).getText();
		
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
		
	}
	
	/**
	 * Esse cenario o usuario não preenche o campo Usuário e aciona o comando login.
	 * Resultado Esperado: sistema não deve permitir ao usuário logar na pagina,
	 * e deve exibir a mensagem: "* O campo em destaque é de preenchimento obrigatório."
	 * @throws InterruptedException 
	 */
	@Test
	public void obrigatoriedadeUsuario() throws InterruptedException{
		
		driver.get(url);
		test.login(" ", senha);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String mensagem = driver.findElement(By.className("textoErro")).getText();
		
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}
	@Parameters
	public static List<Object[]>datadriven(){
		return Arrays.asList(new Object[][]{
			{"admin", "admin", "Adminstrador", "rafael", "henrique", "admin","Administrador", "alessandra", "minions", "rafael"},
//			{"admin", "admin", "Administrador"},
		});
	}
	
	@After
	public void tearDown(){
		driver.close();
		driver.quit();
	}
}
