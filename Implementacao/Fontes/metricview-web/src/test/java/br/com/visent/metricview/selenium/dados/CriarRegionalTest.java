package br.com.visent.metricview.selenium.dados;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Conexao;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;
@Ignore
public class CriarRegionalTest {
	WebDriver driver;
	VisentTestCase test;
	Connection 	   con;
	Properties 	   pFile;
	String			url;
	HSSFWorkbook wb;
	HSSFSheet sheet;
	String a = null;
	HSSFCell cell = null;
	HSSFRow row;
	String usuario = null;
	
	@Before
	public void setUp(){
		test   = new VisentTestCase();
		driver = test.chamarBrowser(Browser.CHROME);
		con    = new Conexao().getConnection();
		pFile   = test.carregaProperties(new File("src/test/selenium/br/com/visent/metricview/selenium/resources/caminhos.properties"));
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
	}
	
	@Test
	public void criarRegionais() throws InterruptedException{
		fazerLogin("henrique", "henrique");		
		cadastrarRegional();
	}
	
	public void fazerLogin(String login, String senha) throws InterruptedException{		
		if(pFile.getProperty(Constantes.URL) != null) {url = pFile.getProperty(Constantes.URL);}
		boolean condic = false;
		driver.get(url);
		driver.findElement(By.id("inputUsuario")).sendKeys(login);
		driver.findElement(By.id("inputSenha")).sendKeys(senha);
		driver.findElement(By.xpath("//input[@value='Login']")).click();
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
			System.out.println("Sistema não apresentaou a mensagem de acesso simultaneo!");
			//Assert.fail("Sistema não apresentaou a mensagem de acesso simultaneo!");
		}	

	}
	
	public void cadastrarRegional() throws InterruptedException{
		try {
			wb = new HSSFWorkbook(new FileInputStream("C:/Users/admin/workspace/metricview/metricview-web/src/test/selenium/massaDados/Carga.xls"));
			sheet = wb.getSheetAt(1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Thread.sleep(500);	
		driver.findElement(By.id("visualizacao-admin")).click();
		Thread.sleep(500);
		driver.findElement(By.id("pageAdmRedes")).click();		
		Thread.sleep(500);		
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);		
			Thread.sleep(500);	
			driver.findElement(By.id("btnAddRegional")).click();
			Thread.sleep(500);	
			driver.findElement(By.id("nomeRegional")).sendKeys(row.getCell(0).getStringCellValue());
			Thread.sleep(500);	
			driver.findElement(By.id("descricaoRegional")).sendKeys(row.getCell(1).getStringCellValue());
			Thread.sleep(500);	
			Select cnSelect = new Select(driver.findElement(By.id("opcaoCN")));
			cnSelect.selectByVisibleText("12");			
			Thread.sleep(500);	
			driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click();
		}		
	}
	
	@After
	public void tearDown(){
		driver.close();
		driver.quit();
	}
}
