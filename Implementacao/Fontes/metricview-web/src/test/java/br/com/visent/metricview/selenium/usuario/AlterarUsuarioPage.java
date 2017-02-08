package br.com.visent.metricview.selenium.usuario;

import java.util.List;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.entidade.Usuario;

public class AlterarUsuarioPage {
	WebDriver driver;
	WebDriverWait wait;
	
	public AlterarUsuarioPage(WebDriver _driver){
		this.driver = _driver;
	}
	
	/**
	 * Altera os dados passando como paramentro os nomes original e o editado
	 * @param nomeOrig
	 * @param emailOrig
	 * @param telefoneOrig
	 * @param areaOrig
	 * @param regionalOrig
	 * @param responsavelOrig
	 * @param nomeEdit
	 * @param emailEdit
	 * @param telefoneEdit
	 * @param areaEdit
	 * @param regionalEdit
	 * @param responsavelEdit
	 */
	public void alterar(String nomeOrig, String emailOrig, String telefoneOrig, String areaOrig, String regionalOrig, String responsavelOrig,
						String nomeEdit, String emailEdit, String telefoneEdit, String areaEdit, String regionalEdit, String responsavelEdit){
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Visualizar Usuários')]")));
			driver.findElement(By.xpath("//input[@value='"+ nomeOrig +"']")).clear();
			driver.findElement(By.xpath("//input[@value='"+ nomeOrig +"']")).sendKeys(nomeEdit);
			
			driver.findElement(By.xpath("//input[@value='"+ emailOrig +"']")).clear();
			driver.findElement(By.xpath("//input[@value='"+ emailOrig +"']")).sendKeys(emailEdit);
			
			driver.findElement(By.xpath("//input[@value='"+ telefoneOrig +"']")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='"+ telefoneOrig +"']")));
			driver.findElement(By.xpath("//input[@value='"+ telefoneOrig +"']")).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE); // limpa o campo telefone e preeche o valor
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='"+ telefoneOrig +"']")));
			driver.findElement(By.xpath("//input[@value='"+ telefoneOrig +"']")).sendKeys(telefoneEdit);
			
			driver.findElement(By.xpath("//input[@value='"+ areaOrig +"']")).clear();
			driver.findElement(By.xpath("//input[@value='"+ areaOrig +"']")).sendKeys(areaEdit);
			
			driver.findElement(By.xpath("//div[starts-with(@id, 's2id_select_combo_visu')]//a[@href='javascript:void(0)']")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + regionalEdit + "')]")));
			driver.findElement(By.xpath("//div[contains(text(),'" + regionalEdit + "')]")).click();
			
			driver.findElement(By.xpath("//input[@value='"+ responsavelOrig +"']")).clear();
			driver.findElement(By.xpath("//input[@value='"+ responsavelOrig +"']")).sendKeys(responsavelEdit);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel alterar o usuario!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que clica no botao visualizar usuarios.
	 */
	public void clicarNoBotaoVisualizarUsuarios(){
		try {
			Thread.sleep(1000);
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnVisualizarUsuarios")));
			driver.findElement(By.id("btnVisualizarUsuarios")).click();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao Visualizar Usuarios!");
			e.printStackTrace();
		}
	}
	
	public void clicarNoBotaoSalvareFechar(){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Salvar e Fechar')]")));
			driver.findElement(By.xpath("//span[contains(text(),'Salvar e Fechar')]")).click(); //salvar
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao SalvarEFechar!");
			e.printStackTrace();
		}

	}
	
	/**
	 * Varre a tabela de visualizar usuarios e clica no botao.
	 * @param idUsuario
	 * 			passa o id do usuario como parametro.
	 * @param botao
	 * 			passa o botao a ser clicado como parametro.
	 */
	public void clicarBotaoTabela(String idUsuario, String botao){
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dlgVisualizarUsuarios")));
			
			WebElement table = driver.findElement(By.id("dlgVisualizarUsuarios"));
			boolean achou=false;
			List<WebElement> allRows = table.findElements(By.tagName("tr")); 
			// And iterate over them, getting the cells 
			for (WebElement row : allRows) { 
				if(achou){
					break;
				}
				if(row.getAttribute("id").equals(idUsuario)){
					achou = true;
					List<WebElement> cells = row.findElements(By.tagName("td")); 
					for (WebElement cell : cells) {
						if( cell.getAttribute("class").equals(botao)){
							cell.click();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao Alterar Usuario!");
			e.printStackTrace();
		}
	}
	/**
	 * Clica no checkbox visualizar usuario conectado.
	 * @param login
	 * 			passa o login como parametro.
	 * @return
	 * 			retorna true ou false.
	 */
	public boolean visualizaUsuarioConectado(String login){
		boolean bateu = false;
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dlgVisualizarUsuarios")));
			WebElement table = driver.findElement(By.id("dlgVisualizarUsuarios"));
			
			List<WebElement> allRows = table.findElements(By.tagName("tr")); 
			// And iterate over them, getting the cells 
			for (WebElement row : allRows) { 
				if(bateu){
					break;
				}
				List<WebElement> cells = row.findElements(By.tagName("td")); 
				if(bateu){
					break;
				}
				for (WebElement cell : cells) {
					if(!bateu){
						bateu = cell.getAttribute("class").equals("desconectarSemIcone naoeditavel");
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("Nao foi possivel visualizar o elemento Usuario Conectado!");
			e.printStackTrace();
		}
		return bateu;
	}
	
	/**
	 * Retorna o id do usuario.
	 * @param login
	 * 			passa o login como parametro.
	 * @return
	 * 			retorna o id do usuario.
	 */
	@SuppressWarnings("unchecked")
	public String buscarId(String login) {
		String id = "";
		try {
			String sql = "SELECT e FROM Usuario e where e.login = '" + login + "'";
			JPAUtil.beginTransaction();
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			List<Usuario> lista = qr.getResultList();
			
			if(lista.size()>1){
				Assert.fail("Sistema não deve permitir incluir mais de 2 usuario com o mesmo login.");
			}
			
			for(Usuario e : lista){
				id = String.valueOf(e.getId());
			}
			
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel buscar o id no banco!");
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * Metodo que captura mensagem de obrigadoriedade da tela de alteracao.
	 * @return
	 * 		retorna a string com a mensagem.
	 */
	public String deveVerificarMensagemDeObrigatoriedade(){
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dlgVisualizarUsuarios']//span[@class='textoErro']")));
		} catch (Exception e) {
			Assert.fail("Nao foi possivel Capturar mensagem de obrigatoriedade!");
		}
		return driver.findElement(By.xpath("//div[@id='dlgVisualizarUsuarios']//span[@class='textoErro']")).getText();
	}
}
