package br.com.visent.metricview.selenium.regional;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.metricview.selenium.core.BaseTestCase;

@RunWith(Parameterized.class)
public class ExcluirRegionalTest extends BaseTestCase{
	WebDriverWait   wait;
	IncluirRegionalPage incluirRegionalPage;
	ExcluirRegionalPage excluirRegionalPage;
	//dados
	String nomeRegional;
	String descricao;
	String cns;
	
	public ExcluirRegionalTest(String _nomeRegional, String _descricao, String _cns){
		this.nomeRegional = _nomeRegional;
		this.descricao 	  = _descricao;
		this.cns 		  = _cns;
	}
	@Before
	public void setUp(){
		incluirRegionalPage = new IncluirRegionalPage(driver);
		excluirRegionalPage = new ExcluirRegionalPage(driver);
		wait    = new WebDriverWait(driver, 10);
	}
	/**
	 * Cenario inclui um registro e depois faz a exclusao validando a mensagem de erro.
	 * RESULTADO ESPERADO: Sistema deve excluir regional do banco;
	 */
	@Test
	public void excluirRegional(){		
	
		incluirRegionalPage.incluirRegionalBuilder(nomeRegional, descricao, cns);  // inclui a regional primeiro.

		loginPage.acessarPage();
		
		incluirRegionalPage.clicarMenuRedes();
	
		excluirRegionalPage.excluirRegional(nomeRegional);					// faz a exclusao da regiional
		
		Alert  msgExclusao = driver.switchTo().alert();
		String msg 		   = msgExclusao.getText();
		msgExclusao.accept();
		Assert.assertEquals(msg, "Tem certeza que deseja excluir ?");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); //captura a mensagem de sucesso
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
		
		List<String> lista = excluirRegionalPage.consultaNoBancoTabelaRegional(nomeRegional);
		for (String str : lista) {
			String nome = str;
			if(nome.equals(nomeRegional)){
				Assert.fail("Sistema não excluiu a Regional"+ nome);
			}else{
				Assert.assertTrue("Operação Realizada com Sucesso !", true);
			}
		}		
	}
	/**
	 * Cenario solicita a exclusao e depois cancela o alert.
	 * RESULTADO ESPERADO: sistema não deve excluir o registro do banco.
	 */
	@Test
	public void cancelarExcluir(){

		incluirRegionalPage.incluirRegionalBuilder(nomeRegional, descricao, cns);  // inclui a regional primeiro.

		loginPage.acessarPage();
		
		incluirRegionalPage.clicarMenuRedes();
		
		excluirRegionalPage.excluirRegional(nomeRegional);					// faz a exclusao da regiional

		Alert  msgExclusao = driver.switchTo().alert();
		String msg 		   = msgExclusao.getText();
		
		msgExclusao.dismiss();
		Assert.assertEquals(msg, "Tem certeza que deseja excluir ?");
		
		List<String> lista = excluirRegionalPage.consultaNoBancoTabelaRegional(nomeRegional);
		for (String str : lista) {
			String nome = str;
			if(nome.equals(nomeRegional)){
				Assert.assertEquals(nome, nomeRegional);
			}
		}

	}
	
	@Parameters
	public static List<Object[]>datadriven(){
		return Arrays.asList(new Object[][]{
			{"leste", "descricao11", "13"},
		});
	}
	
	@After
	public void tearDown(){
			incluirRegionalPage.excluirRegionalPeloBanco(nomeRegional);
	}
}
