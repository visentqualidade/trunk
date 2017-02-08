package br.com.visent.metricview.selenium.grupo;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ExcluirGrupoPage {
	WebDriver driver;
	
	public ExcluirGrupoPage(WebDriver _driver){
		this.driver = _driver;
	}
	/**
	 * Faz a exclusao de um grupo.
	 * @param nomeGrupo
	 * 			passa o nome do grupo como parametro.
	 * @param descricao
	 * 			passa a descricao do grupo como parametro.
	 */
	public void excluirGrupo(String nomeGrupo, String descricao){
		try {
			Thread.sleep(500);
			driver.findElement(By.id("btnVisualizarGrupos")).click();
			
			Thread.sleep(500);
			clicarBotaoExcluir(nomeGrupo, "excluir");		
		} catch (Exception e) {
			Assert.fail("Nao foi possivel excluir grupo!");
			e.printStackTrace();
		}
	}
	/**
	 * Clica no botao excluir.
	 * @param nomeGrupo
	 * 			Passa o nome do grupo como parametro.
	 * @param botao
	 * 			passa o botao a ser clicado.
	 */
	public void clicarBotaoExcluir(String nomeGrupo, String botao){
		try {
			
			WebElement table = driver.findElement(By.id("dlgVisualizarGrupos"));
			
			List<WebElement> allRows = table.findElements(By.tagName("tr")); 
			boolean bateu = false;
			// And iterate over them, getting the cells 
			for (WebElement row : allRows) { 
				if(bateu){
					break;
				}
				List<WebElement> cells = row.findElements(By.tagName("td")); 
				for (WebElement cell : cells) {
					if(!bateu){
						bateu = cell.getText().equals(nomeGrupo);
					}
					if(bateu && cell.getAttribute("class").equals(botao)){
						cell.click();
						break;
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("Nao foi possivel achar o botao excluir.");
			e.printStackTrace();
		}
	}
}
