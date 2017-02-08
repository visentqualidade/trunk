package br.com.visent.metricview.selenium.login;

import java.io.File;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	WebDriver driver;
	WebDriverWait wait;
	
	public LoginPage(WebDriver _driver){
		this.driver = _driver;
	}
	
	/**
	 * Metodo que faz o login no sistema.
	 * @param usuario
	 * 			passa como parametro o nome do usuário.
	 * @param senha
	 * 			passa como paramentro a senha do usuário.
	 */
	public void login(String usuario, String senha){
		try {
			
			wait = new WebDriverWait(driver, 10);
			
			acessarPage();	
			
			//Trata um alert JS
			((JavascriptExecutor)driver).executeScript("window.alert = function(msg){};");
			
			//Clica no botão OK de um confirm JS
			((JavascriptExecutor)driver).executeScript("window.confirm = function(msg){return true;};");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsuario")));
			driver.findElement(By.id("inputUsuario")).sendKeys(usuario);
			driver.findElement(By.id("inputSenha"))  .sendKeys(senha);
			
			org.apache.commons.io.FileUtils.copyFile(((org.openqa.selenium.TakesScreenshot)driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE), new File("C:/teste1.png"), true);
			
			driver.findElement(By.xpath("//input[@value='Login']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@id='imgArrow']")));
		} catch (Exception e) {
			Assert.fail("Nao foi possivel logar!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Acessar a pagina.
	 */
	public void acessarPage(){
		try {
			driver.navigate().to("http://passat:8080/metricview");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Faz o logout do sistema.
	 */
	public void logOut(){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@id='imgArrow']")));
			Thread.sleep(2000);
			driver.findElement(By.xpath("//img[@id='imgArrow']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//li[@id='logout']")).click();
			Thread.sleep(2000);
		} catch (Exception e) {
			Assert.fail("Não foi possivel achar o botao de logout!");
		}

	}
	
	/**
	 * Verifica se a pagina exibe o alerta de usuario logado e aciona o comando OK.
	 */
	public void verificaAlertaUsuarioLogado(){
		boolean condic = false;
		try {
			 condic = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {//Espera o alerta aparecer na tela.
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
}
