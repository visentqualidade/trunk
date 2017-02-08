package br.com.visent.metricview.selenium.regional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.entidade.CodigoNacional;
import br.com.visent.metricview.entidade.Regional;
import br.com.visent.metricview.service.RegionalService;


public class IncluirRegionalPage {
	WebDriver driver;
	WebDriverWait wait;
	
	public IncluirRegionalPage(WebDriver _driver){
		this.driver = _driver;
	}
	
	/**
	 * Metodo que faz a inclusao do usuario aciona o comando salvar e fechar
	 * @param nomeRegional
	 * 			passa como parametro o nome do usuario
	 * @param descricao
	 * 			passa como parametro a descrição do usuario
	 * @param cns
	 * 			passa como parametro o numero da cn
	 */
	public void incluirRegional(String nomeRegional, String descricao, String cns){
		try {
			
			wait = new WebDriverWait(driver, 10);
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return driver.findElement(
							By.id("btnAddRegional")).isDisplayed();
				}
			});
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddRegional")));
			driver.findElement(By.id("btnAddRegional")).click();
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			String titulo =  driver.findElement(By.className("ui-dialog-title")).getText();   //Verifica titulo da  tela
			Assert.assertEquals(titulo, "Cadastrar Regional"); //Verifica titulo da  tela
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			driver.findElement(By.id("nomeRegional")).sendKeys(nomeRegional);
			
			driver.findElement(By.id("descricaoRegional")).sendKeys(descricao);
			
			Select select = new Select(driver.findElement(By.id("opcaoCN")));
			select.selectByVisibleText(cns);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel incluir regional.");
		}
	}
	
	/**
	 * Metodo que clica no botao Cancelar.
	 */
	public void clicarNoBotaoCancelar(){
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Cancelar")));
		driver.findElement(By.id("Cancelar")).click(); // Cancelar
	}
	
	/**
	 * Metodo que faz o clique no botao Salvar.
	 */
	public void clicarNoBotaoSalvar(){
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='Salvar']//span")));
		driver.findElement(By.xpath("//button[@id='Salvar']//span")).click(); // salvar
	}
	
	/**
	 * Metodo que clica no botao Salvar e Fechar.
	 */
	public void clicarBotaoSalvarFechar(){
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Salvar e Fechar")));
		driver.findElement(By.id("Salvar e Fechar")).click(); // salvar
	}
	
	/**
	 * Metodo que faz a inclusao da regional
	 * @param nomeRegional
	 * 			passa o nome da regional como parametro.
	 * @param descricao
	 * 			passa a descricao da regional como parametro.
	 */
	
	public void incluirRegionalBuilder(String nomeRegional, String descricao, String cn){
		Regional regional = null;
		try {
			Set<CodigoNacional> codigoNacionals = new HashSet<CodigoNacional>();
			CodigoNacional codig = new CodigoNacionallDataBuilder().comId(Long.valueOf("0")).comCodigo(Long.valueOf(cn)).constroi();
			codigoNacionals.add(codig);
			
			regional = new RegionalDataBuilder().comNome(nomeRegional).comDescricao(descricao).comCodigoNacional(codigoNacionals).constroi();
			
			new RegionalService().inserir(regional);
			JPAUtil.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel incluir regional!");
		}
	}
	
	/**
	 * Cenario que testa a obrigatoriedade do campo CNs.
	 * @param nomeRegional
	 * 			passa o nome da regional como parametro.
	 * @param descricao
	 * 			passa a descricao como parametro.
	 */
	public void obrigatoriedadeCns(String nomeRegional, String descricao){
		try {
			
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {//Espera a pagina abrir
				public Boolean apply(WebDriver d) {
					return driver.findElement(By.id("visualizacao-admin")).isDisplayed();											// verificacao do nome precisou ser parametrizada para nao dar erro
				}
			});
			wait = new WebDriverWait(driver,10);		
			driver.findElement(By.id("visualizacao-admin")).click(); //clica menu Administracao
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.id("pageAdmRedes")).click(); //clica menu Redes
			
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddRegional")));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.id("btnAddRegional")).click();
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			String titulo =  driver.findElement(By.className("ui-dialog-title")).getText();   //Verifica titulo da  tela
			Assert.assertEquals(titulo, "Cadastrar Regional"); //Verifica titulo da  tela
			
			driver.findElement(By.id("nomeRegional")).sendKeys(nomeRegional);
			
			driver.findElement(By.id("descricaoRegional")).sendKeys(descricao);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel buscar a obrigatoriedade da CN!");
		}
	}
	/**
	 * Metodo que espera janela de regional aparecer.
	 */
	public void esperaTituloRegional(){
		wait = new WebDriverWait(driver, 10);
		org.openqa.selenium.WebElement e = driver.findElement(By.id("windowRegionais")).findElements(By.tagName("h1")).get(0);
		wait.until(ExpectedConditions.visibilityOf(e));
	}
	
	/**
	 * Clica no menu redes.
	 */
	public void clicarMenuRedes(){
		try {
			
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {//Espera a pagina abrir
				public Boolean apply(WebDriver d) {
					return driver.findElement(By.id("visualizacao-admin")).isDisplayed();											// verificacao do nome precisou ser parametrizada para nao dar erro
				}
			});
			Thread.sleep(3000);
			
			driver.findElement(By.id("visualizacao-admin")).click(); //clica menu Administracao
			Thread.sleep(3000);
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {//Espera a pagina abrir
				public Boolean apply(WebDriver d) {
					return driver.findElement(By.id("pageAdmRedes")).isDisplayed();											// verificacao do nome precisou ser parametrizada para nao dar erro
				}
			});
			driver.findElement(By.id("pageAdmRedes")).click(); //clica menu Redes
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel clicar no menu REDES!");
		}
	}		
			
	/**
	 * Metodo que consulta registro na tabela de regionais.
	 * @param coluna passa a coluna como parametro.
	 * 
	 * @return retorna uma lista com as regionais encontradas.
	 */
	@SuppressWarnings("unchecked")
	public List<Regional> consultaRegistroRegionalNoBanco(String nomeRegional) {
		List<Regional> lista = new ArrayList<Regional>();
		try {
			String sql = "SELECT r FROM Regional r where r.nome =" + "'" + nomeRegional + "'";
			
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			 lista = qr.getResultList();

			JPAUtil.closeEntityManager();
			
		} catch (Exception e) {
			Assert.fail("Nao foi possivel consultar os dados no banco!");
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
	public List<BigDecimal> consultaNoBancoTabelaRelacionalIdCns(String idReg) {		
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
	public List<BigDecimal> consultaNoBancoTabelaCN(String idCns) {		
		List<BigDecimal> lista = new ArrayList<BigDecimal>();
		try {
			String sql = "select NOME from CN where ID_CN = '" + idCns + "'";
			
			Query qryReg = JPAUtil.getEntityManager().createNativeQuery(sql);
			lista = qryReg.getResultList();

			JPAUtil.closeEntityManager();
			
		} catch (Exception e) {
			Assert.fail("Nao foi possivel consultar os dados CN.");
			e.printStackTrace();
		}		
		return lista;
	}
	
	/**
	 * Metodo que verifica se a regional existe no banco.
	 * @param nomeReg
	 * 			passa o login do usuario como parametro.
	 * @return
	 * 			returna true se o usuario existe.
	 */
	@SuppressWarnings("unchecked")
	public boolean verificaSeRegionalExisteNoBanco(String nomeReg){
		boolean isRegCadastrada = false;
		try {
			String sql = "SELECT r FROM Regional r where r.nome = '" + nomeReg + "'";
			
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			List<String> lista = qr.getResultList();
			
			if(!lista.isEmpty()){
				isRegCadastrada = true;
				return isRegCadastrada;
			}
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel Consultar o usuario no banco!");
			e.printStackTrace();
		}
		return isRegCadastrada;
	}
	
	/**
	 * Metodo faz a exclusao da ragional no banco.
	 * 
	 * @param con
	 *            passa como parametro a conexao com o banco.
	 * @param nomeReg
	 *            passa como parametro o login que desena excluir.
	 */
	@SuppressWarnings("unchecked")
	public void excluirRegionalPeloBanco(String nomeReg) {
		
		if (verificaSeRegionalExisteNoBanco(nomeReg)) {
			try {
				String sql = "SELECT r FROM Regional r where r.nome = '" + nomeReg + "'";
				JPAUtil.beginTransaction();
				Query qr = JPAUtil.getEntityManager().createQuery(sql);
				List<Regional> lista = qr.getResultList();
				
				for(Regional e : lista){
					JPAUtil.getEntityManager().remove(e);;
				}
				JPAUtil.commitTransaction();
				JPAUtil.closeEntityManager();
			} catch (Exception e) {
				Assert.fail("Nao foi possivel excluir o usuario!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metodo que verifica no banco a inclusao da regional.
	 * @param nomeRegional
	 * 			passa o nome da regional como parametro.
	 * @param descricao
	 * 			passa a descricao da regional como parametro.
	 * @param cns
	 * 			passa a cn que a regional deve validar.
	 */
	public void validaInclusaoRegionalNoBanco(String nomeRegional, String descricao, String cns){
		Long idReg = 1L;
		List<Regional> regionais = consultaRegistroRegionalNoBanco(nomeRegional);
		for (Regional regional : regionais) {
			idReg = regional.getId();
			String nome = regional.getNome();
			String descricaoReg = regional.getDescricao();
			if (nome.equals(nomeRegional)) {
				Assert.assertEquals(nome, nomeRegional);
				Assert.assertEquals(descricaoReg, descricao);
			}
		}

		BigDecimal idCns = null;

		List<BigDecimal> listaIdCn = consultaNoBancoTabelaRelacionalIdCns(idReg.toString());
		for (BigDecimal codigo : listaIdCn) {
			idCns = codigo;
		}
		List<BigDecimal> listaCn = consultaNoBancoTabelaCN(String.valueOf(idCns));
		BigDecimal nomeCN = null;
		boolean verificaCns = false;
		if (!listaCn.isEmpty()) {
			for (BigDecimal codigo : listaCn) {
				nomeCN = codigo;
				if (nomeCN.equals(BigDecimal.valueOf(Long.parseLong(cns)))) {
					verificaCns = true;
					Assert.assertEquals(nomeCN,
							BigDecimal.valueOf(Long.parseLong(cns)));
				}
			}
		}
		if (!verificaCns) {
			Assert.assertEquals(nomeCN, BigDecimal.valueOf(Long.parseLong(cns)));
		}
	}
}
