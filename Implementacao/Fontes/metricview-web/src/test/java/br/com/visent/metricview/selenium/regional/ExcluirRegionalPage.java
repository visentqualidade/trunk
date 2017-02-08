package br.com.visent.metricview.selenium.regional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.visent.corporativo.util.JPAUtil;



public class ExcluirRegionalPage {

	WebDriver driver;
	
	public ExcluirRegionalPage(WebDriver _driver){
		this.driver = _driver;
	}
	
	/**
	 * faz a exclusao da regional.
	 * @param nomeRegional
	 * 			passa o nome da regional como parametro.
	 */
	public void excluirRegional(String nomeRegional){
		try {
			Thread.sleep(1000);
			driver.findElement(By.id("filtroListaRegio")).sendKeys(nomeRegional);	
			
			clicarBotaoTabela(nomeRegional, "excluir");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel excluir a regional");
		}
		
	}
	/**
	 * Clica no botao excluir
	 * @param nomeReg
	 * 			passa o nome da regional como parametro.
	 * @param botao
	 * 			passa o botao a ser clicado como parametro.
	 */
	public void clicarBotaoTabela(String nomeReg, String botao){
		try {
			WebElement table = driver.findElement(By.id("listaRegio"));
			
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
						bateu = cell.getText().equals(nomeReg);
					}
					if(bateu && cell.getAttribute("class").equals(botao)){
						cell.click();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel Clicar no botao excluir.");
		}
	}
	
	/**
	 * Metodo que consulta registro na tabela de Regional.
	 * @param coluna o id do registro como parametro.
	 * 
	 * @return retorna uma lista com as regionais encontradas.
	 */
	@SuppressWarnings("unchecked")
	public List<String> consultaNoBancoTabelaRegional(String nomeRegional) {
		
		List<String> lista = new ArrayList<String>();
		try {
			String sql = "select NOME from REGIONAL where NOME = '" + nomeRegional + "'";
			
			Query qryReg = JPAUtil.getEntityManager().createNativeQuery(sql);
			lista = qryReg.getResultList();

			JPAUtil.closeEntityManager();
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel consultar os dados da tabela REGIONAL.");
		}		
		return lista;
	}
}
