package br.com.visent.metricview.selenium.regional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.entidade.CodigoNacional;
import br.com.visent.metricview.entidade.Regional;

public class AlterarRegionalPage {
	WebDriver driver;
	WebDriverWait wait;
	
	public AlterarRegionalPage(WebDriver _driver){
		this.driver = _driver;
	}

	/**
	 * Metodo que clica no botao Atualizar Regional
	 */
	public void clicarNoBotaoAtualizarRegional(){
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='Atualizar']//span")));
			driver.findElement(By.xpath("//button[@id='Atualizar']//span")).click(); //salvar
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao Atualizar Regional");
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que preenche o filtro de regional.
	 * @param nomeRegional
	 * 			passa o nome da regional que deseja filtrar.
	 */
	public void preencheFiltroRegional(String nomeRegional){
		try {
			
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filtroListaRegio")));
			driver.findElement(By.id("filtroListaRegio")).sendKeys(nomeRegional);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel filtrar Regional");
			e.printStackTrace();
		}
	}
	
	/**
	 * Faz a alteracao da regional.
	 * @param regOrig
	 * 			passa a regional original como parametro.
	 * @param nomeRegional
	 * 			passa o nome da regional como parametro.
	 * @param descricao
	 * 			passa a descricao da regional como parametro.
	 * @param cns
	 * 			passa os cns como parametro.
	 */
	public void alterarRegional(String regOrig,String nomeRegional, String descricao, String cns){
		try {
			Thread.sleep(500);
			clicarBotaoTabelaRegional(regOrig, "editar");
			
			Thread.sleep(500);
			String titulo =  driver.findElement(By.className("ui-dialog-title")).getText();   //Verifica titulo da  tela
			Assert.assertEquals(titulo, "Alterar Regional"); //Verifica titulo da  tela
			
			driver.findElement(By.id("nomeRegional")).clear();
			driver.findElement(By.id("nomeRegional")).sendKeys(nomeRegional);
			
			driver.findElement(By.id("descricaoRegional")).clear();
			driver.findElement(By.id("descricaoRegional")).sendKeys(descricao);
			
			Thread.sleep(500);
			
			driver.findElement(By.xpath("//a[@class='select2-search-choice-close']")).click();
			
			Thread.sleep(500);
			
			Select select =	new	Select(driver.findElement(By.id("opcaoCN")));
			select.selectByVisibleText(cns);
			
		} catch (Exception e) {
			Assert.fail("Nao foi possivel alterar a regional!");
			e.printStackTrace();
		}
	}
	/**
	 * Clica no botao redes.
	 */
	public void clicarRedes(){
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("visualizacao-admin")));
			
			driver.findElement(By.id("visualizacao-admin")).click(); //clica menu Administracao
			Thread.sleep(5000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pageAdmRedes")));
			driver.findElement(By.id("pageAdmRedes")).click(); //clica menu Redes
			Thread.sleep(1000);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no menu Redes!");
			e.printStackTrace();
		}
	}
	
	/**
	 * clica no botao da tabela regionais.
	 * @param nomeReg
	 * 			passa o nome da regional como parametro.
	 * @param botao
	 * 			passa o botao a ser clicado como parametro.
	 */
	public void clicarBotaoTabelaRegional(String nomeReg, String botao){
		try {
			Thread.sleep(1000);
			WebElement table = driver.findElement(By.id("listaRegio"));
			
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
						bateu = cell.getText().equals(nomeReg);
					}
					if(bateu && cell.getAttribute("class").equals(botao)){
						cell.click();
						break;
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao para editar Regional.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Faz a obrigatoriedade do campo cn.
	 * @param nomeRegional
	 * 			passa o nome da regional como parametro.
	 */
	public void obrigatoriedadeCns(String nomeRegional){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filtroListaRegio")));
			
			driver.findElement(By.id("filtroListaRegio")).sendKeys(nomeRegional);
			
			clicarBotaoTabelaRegional(nomeRegional, "editar");
			
			Thread.sleep(500);
			
			driver.findElement(By.xpath("//a[@class='select2-search-choice-close']")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-dialog-title")));
			String titulo =  driver.findElement(By.className("ui-dialog-title")).getText();   //Verifica titulo da  tela
			Assert.assertEquals(titulo, "Alterar Regional"); //Verifica titulo da tela
			
		} catch (Exception e) {
			Assert.fail("Nao foi possivel preencher o campo CN!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Clica no botao alterar e valida o titulo da tela.
	 * @param nomeRegional
	 * 			passa o nome da regional como parametro.
	 */
	public void clicarBotaoAlterar(String nomeRegional){
		try {
			clicarBotaoTabelaRegional(nomeRegional, "editar");
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-dialog-title")));
			String titulo =  driver.findElement(By.className("ui-dialog-title")).getText();   //Verifica titulo da  tela
			Assert.assertEquals(titulo, "Alterar Regional"); //Verifica titulo da  tela
			
			driver .findElement(By.xpath("//span[contains(text(),'Atualizar')]")).click(); //salvar
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao alterar!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que consulta registro na tabela Regional.
	 * @param coluna o id do registro como parametro.
	 * 
	 * @return retorna uma lista com as regionais encontradas.
	 */
	@SuppressWarnings("unchecked")
	public List<Regional> consultaNoBancoTabelaRegional(String nomeRegional) {		
		List<Regional> lista = new ArrayList<Regional>();
		try {
			String sql = "SELECT r FROM Regional r where r.nome =" + "'" + nomeRegional + "'";
			
			Query qryReg = JPAUtil.getEntityManager().createQuery(sql);
			lista = qryReg.getResultList();

			JPAUtil.closeEntityManager();
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel consultar os dados da tabela REGIONAL.");
		}		
		return lista;
	}
	
	/**
	 * Metodo que consulta registro na tabela de Regional.
	 * @param coluna o id do registro como parametro.
	 * 
	 * @return retorna uma lista com as regionais encontradas.
	 */
	@SuppressWarnings("unchecked")
	public List<BigDecimal> consultaNoBancoTabelaRelacionalIdReg(String idReg) {		
		List<BigDecimal> lista = new ArrayList<BigDecimal>();
		try {
			String sql = "select ID_REGIONAL from REL_REGIONAL_COD_NACIONAL where ID_REGIONAL = '" + idReg + "'";
			
			Query qryReg = JPAUtil.getEntityManager().createNativeQuery(sql);
			lista = qryReg.getResultList();

			JPAUtil.closeEntityManager();
			
		} catch (Exception e) {
			Assert.fail("Nao foi possivel consultar os dados REL_REGIONAL_COD_NACIONAL.");
			e.printStackTrace();
		}		
		return lista;
	}
	
	/**
	 * Metodo que consulta registro na tabela Relacional.
	 * @param coluna o id do registro como parametro.
	 * 
	 * @return retorna uma lista com as regionais encontradas.
	 */
	@SuppressWarnings("unchecked")
	public List<BigDecimal> consultaNoBancoTabelaRelacionalIdCN(String idReg) {		
		List<BigDecimal> lista = new ArrayList<BigDecimal>();
		try {
			String sql = "select ID_CN from REL_REGIONAL_COD_NACIONAL where ID_REGIONAL = '" + idReg + "'";
			
			Query qryReg = JPAUtil.getEntityManager().createNativeQuery(sql);
			lista = qryReg.getResultList();

			JPAUtil.closeEntityManager();
			
		} catch (Exception e) {
			Assert.fail("Nao foi possivel consultar os dados REL_REGIONAL_COD_NACIONAL.");
			e.printStackTrace();
		}		
		return lista;
	}
	
	/**
	 * Metodo que consulta registro na tabela de CN.
	 * @param coluna o id do registro como parametro.
	 * 
	 * @return retorna uma lista com as regionais encontradas.
	 */
	@SuppressWarnings("unchecked")
	public List<CodigoNacional> consultaNoBancoTabelaCN(String idCns) {		
		List<CodigoNacional> lista = new ArrayList<CodigoNacional>();
		try {
			String sql = "select NOME from CN where ID_CN = '" + idCns + "'";
			
			Query qryReg = JPAUtil.getEntityManager().createNativeQuery(sql);
			lista = qryReg.getResultList();

			JPAUtil.closeEntityManager();
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel consultar os dados CN.");
		}		
		return lista;
	}
	/**
	 * Metodo que valida se os dados alterados estao no banco.
	 * @param nomeRegional
	 * 			passa como parametro o nome da regional.
	 * @param descricao
	 * 			passa como parametro a descricao da regional.
	 * @param cns
	 * 			passa como parametro o cn.
	 */
	public void deveValidarAlteracaoBanco(String nomeRegional, String descricao, String cns){
		List<Regional> lista = consultaNoBancoTabelaRegional(nomeRegional);
		Long idReg = 1L;
		for (Regional regional : lista) {
			idReg = regional.getId();
			String nome = regional.getNome();
			String desc = regional.getDescricao();
			if(nome.equals(nomeRegional)){
				Assert.assertEquals(nome, nomeRegional);
				Assert.assertEquals(desc, descricao);
			}
		}
		
		List<BigDecimal> listaIdReg = consultaNoBancoTabelaRelacionalIdReg(String.valueOf(idReg));
		BigDecimal idRegRelacional = null;
		for (BigDecimal numeroIdReg : listaIdReg) {
			idRegRelacional = numeroIdReg;
			if(idReg.equals(String.valueOf(idRegRelacional))){
				
			}
		}
		
		List<BigDecimal> listaRelacionalIdCN = consultaNoBancoTabelaRelacionalIdCN(String.valueOf(idReg));
		BigDecimal idCns = null;
		for (BigDecimal numeroIdCn : listaRelacionalIdCN) {
			idCns = numeroIdCn;
		}
		
		List<CodigoNacional> listaIdCn = consultaNoBancoTabelaCN(String.valueOf(idCns));
		Object codigo = null;
		boolean verificaCns = false;
		for (Object codigoLista : listaIdCn.toArray()) {
			codigo = codigoLista;
			if(String.valueOf(codigo).equals(cns)){
				verificaCns = true;
				Assert.assertEquals(String.valueOf(codigo), cns);
			}
		}
		if(!verificaCns){
			Assert.assertEquals(String.valueOf(codigo), cns);
		}
	}
}
