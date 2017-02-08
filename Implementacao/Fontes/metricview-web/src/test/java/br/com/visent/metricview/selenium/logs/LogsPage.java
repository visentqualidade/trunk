package br.com.visent.metricview.selenium.logs;

import java.util.List;
import java.util.Properties;

import org.junit.Ignore;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;



import br.com.visent.metricview.selenium.util.Constantes;

@Ignore
public class LogsPage {
	

	public void fazerLogin(String login, String senha,final WebDriver driver,Properties pFile,String url) throws InterruptedException{		
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
	
	public void iniciarGeracaoLog(WebDriver driver,String url) throws InterruptedException{
		Thread.sleep(7000);
		driver.get(url);
		Thread.sleep(2000);
		driver.findElement(By.id("visualizacao-admin")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("visualizacao-logs")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//th[contains(text(),'Data/Hora')]")).click();	
		Thread.sleep(500);
	}
	
	public boolean validaLogLoginLogoff(String dataLogin,String dataLogoff, String nomeUsuario, String solucao,WebDriver driver){						
		WebElement table = driver.findElement(By.id("listaLogs"));
		String msgLogin = "Foi autenticado no sistema.";
		String msgLogoff = "Foi desconectado do sistema.";
		String mensagemLogin = dataLogin+ " " + nomeUsuario + " " + msgLogin + " " + solucao;
		String mensagemLogoff = dataLogoff+ " " + nomeUsuario + " " + msgLogoff + " " + solucao;			
		List<WebElement> allRows = table.findElements(By.tagName("tr")); 				
		System.out.println(mensagemLogin);
		System.out.println(mensagemLogoff);
		System.out.println("===========================");
		System.out.println(allRows.get(2).getText());
		System.out.println(allRows.get(1).getText());	
			if(allRows.get(1).getText().equals(mensagemLogoff) && allRows.get(2).getText().equals(mensagemLogin)){
				return true;
			}else{
				return false;
			}
	}
	
	public boolean validaLogsGrupo(String data, String usuario,String grupo, String solucao,String acao, WebDriver driver){
		String mensagem = null;
		if(acao.equals("Adicionar")){
			mensagem = "Criou o grupo " + grupo + ".";			
		}		
		if(acao.equals("Excluir")){
			mensagem = "Removeu o grupo "+ grupo + ".";
		}
		if(acao.equals("Alterar")){
			mensagem = "Alterou o grupo "+ grupo + ".";
		}
		String mensagemTotal = data + " " + usuario + " " + mensagem + " " + solucao;
		WebElement table = driver.findElement(By.id("listaLogs"));
		List<WebElement> allRows = table.findElements(By.tagName("tr")); 
		System.out.println(mensagemTotal);
		System.out.println(allRows.get(1).getText());
		if(allRows.get(1).getText().equals(mensagemTotal)){
			return true;
		}else{
			return false;
		}
	}
	public boolean validaLogsRegional(String data, String usuario, String regional, String solucao, String acao, WebDriver driver){
		String mensagem = null;
		if(acao.equals("Adicionar")){
			mensagem = "Criou a regional " + regional + ".";			
		}	
		if(acao.equals("Excluir")){
			mensagem = "Excluiu a regional " + regional + ".";			
		}
		if(acao.equals("Alterar")){
			mensagem = "Alterou a regional " + regional + ".";			
		}
		String mensagemTotal = data + " " + usuario + " " + mensagem + " " + solucao;
		WebElement table = driver.findElement(By.id("listaLogs"));
		List<WebElement> allRows = table.findElements(By.tagName("tr")); 
		System.out.println(mensagemTotal);
		System.out.println(allRows.get(1).getText());
		if(allRows.get(1).getText().equals(mensagemTotal)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean validaLogsUsuario(String data, String usuario, String nome, String solucao, String acao, WebDriver driver){
		String mensagem = null;
		if(acao.equals("Adicionar")){
			mensagem = "Cadastrou o usuário " + nome + ".";
		}
		if(acao.equals("Excluir")){
			mensagem = "Removeu o usuário " + nome + ".";
		}
		if(acao.equals("Alterar")){
			mensagem = "Alterou o usuário " + nome + ".";
		}
		String mensagemTotal = data + " " + usuario + " " + mensagem + " " + solucao;
		WebElement table = driver.findElement(By.id("listaLogs"));
		List<WebElement> allRows = table.findElements(By.tagName("tr")); 
		System.out.println(mensagemTotal);
		System.out.println(allRows.get(1).getText());
		if(allRows.get(1).getText().equals(mensagemTotal)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean validaAssociacao(String data,String usuario,String solucao,String acao,WebDriver driver){
		String mensagem = null;
		String mensagemTotal = null;
		if(acao.equals("Associacao")){
			mensagem = "Alterou configurações de grupo.";			
		}
		if(acao.equals("Permissao")){
			mensagem = "Foram alteradas as permissões de acesso aos subsistemas do MetricView.";
		}
		mensagemTotal = data + " " + usuario + " " + mensagem + " " + solucao;				
		WebElement table = driver.findElement(By.id("listaLogs"));
		List<WebElement> allRows = table.findElements(By.tagName("tr")); 
		System.out.println(mensagemTotal);
		System.out.println(allRows.get(1).getText());
		if(allRows.get(1).getText().equals(mensagemTotal)){
			return true;
		}else{
			return false;
		}
	}
}


















