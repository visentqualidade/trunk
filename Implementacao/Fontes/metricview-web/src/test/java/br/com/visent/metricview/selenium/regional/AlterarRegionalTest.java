package br.com.visent.metricview.selenium.regional;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.metricview.selenium.core.BaseTestCase;

@RunWith(Parameterized.class)
public class AlterarRegionalTest extends BaseTestCase{	
	WebDriverWait   wait;
	IncluirRegionalPage incluirRegionalPage;
	AlterarRegionalPage alterarRegioinalPage;

	//dados
	String regionalOriginal;
	String descricaoOriginal;
	String cnsOriginal;
	String nomeRegional;
	String descricao;
	String cns;
	String regObrigatoriedadeCn;
	String regObrigatoriedadeDescricao;
	String caracteresEspecDescricao;
	String regionalCadastrada;		
	
	public AlterarRegionalTest(String _regionalOriginal, 
								String _descricaoOriginal, String _cnsOriginal, String _nomeRegional, 
								String _descricao, String _cns, String _regObrigatoriedadeCn, String _regObrigatoriedadeDescricao,
								String _caracteresEspecDescricao, String _regionalCadastrada){
		this.regionalOriginal  	  		 = _regionalOriginal;
		this.descricaoOriginal 	 		 = _descricaoOriginal;
		this.cnsOriginal       	  		 = _cnsOriginal;
		this.nomeRegional 	   	  		 = _nomeRegional;
		this.descricao 	  	   	  		 = _descricao;
		this.cns 		  	   	  		 = _cns;
		this.regObrigatoriedadeCn 		 = _regObrigatoriedadeCn;
		this.regObrigatoriedadeDescricao = _regObrigatoriedadeDescricao;
		this.caracteresEspecDescricao    = _caracteresEspecDescricao;
		this.regionalCadastrada			 = _regionalCadastrada;
	}
	
	@Before
	public void setUp(){
		wait = new WebDriverWait(driver, 120);
		incluirRegionalPage = new IncluirRegionalPage(driver);
		alterarRegioinalPage = new AlterarRegionalPage(driver);
	}
	/**
	 * Metodo inclui regional e depois verifica no banco se a mesma foi esta contida.
	 * RESULTADO ESPERADO: Sistema deve incluir no banco os registros inclusos.
	 */
	@Test
	public void deveAlterarRegionalESalvar(){
		
		incluirRegionalPage.incluirRegionalBuilder(regionalOriginal, descricaoOriginal, cnsOriginal); //faz a inclusao de um registro

		loginPage.acessarPage();				
		
		alterarRegioinalPage.clicarRedes();
		
		alterarRegioinalPage.preencheFiltroRegional(regionalOriginal);
		
		alterarRegioinalPage.alterarRegional(regionalOriginal, nomeRegional, descricao, cns);			
		
		alterarRegioinalPage.clicarNoBotaoAtualizarRegional();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); //captura a mensagem de sucesso
		
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
		
		alterarRegioinalPage.deveValidarAlteracaoBanco(nomeRegional, descricao, cns);
	}
	/**
	 * Metodo que testa obrigatoriedade do campo Cns na inclusao usuario deixao campo CNs em abranco e aciona o comando salvar.
	 * RESULTADO ESPERADO: Sistema deve exibir a mensagem de obrigatoriedade: "* O campo em destaque é de preenchimento obrigatório."
	 * @throws InterruptedException 
	 */
	@Test
	public void deveApresentarObrigatoriedadeCns() throws InterruptedException {

		incluirRegionalPage.incluirRegionalBuilder(regionalOriginal, descricaoOriginal, cnsOriginal); //faz a inclusao de um registro
	
		loginPage.acessarPage();	
		
		alterarRegioinalPage.clicarRedes();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		alterarRegioinalPage.obrigatoriedadeCns(regionalOriginal);
		
		alterarRegioinalPage.clicarNoBotaoAtualizarRegional();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formRegional")));
		Thread.sleep(500);
		String mensagem = driver.findElement(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}
	/**
	 * Metodo que testa obrigatoriedade do campo Cns na inclusao usuario deixao campo Descricao em abranco e aciona o comando salvar.
	 * RESULTADO ESPERADO: Sistema deve exibir a mensagem de obrigatoriedade: "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void deveApresentarObrigatoriedadeDescricao(){

		incluirRegionalPage.incluirRegionalBuilder(regObrigatoriedadeDescricao, descricaoOriginal, cnsOriginal); //faz a inclusao de um registro

		loginPage.acessarPage();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.clicarRedes();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.preencheFiltroRegional(regObrigatoriedadeDescricao);
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		alterarRegioinalPage.alterarRegional(regObrigatoriedadeDescricao, "REGIONAL", " ", cns);
		
		driver.findElement(By.xpath("//button[@id='Atualizar']//span")).click(); //salvar

		String mensagem = driver.findElement(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Metodo que testa obrigatoriedade do campo Cns na inclusao usuario deixao campo Nome em abranco e aciona o comando salvar.
	 * RESULTADO ESPERADO: Sistema deve exibir a mensagem de obrigatoriedade: "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void deveApresentarObrigatoriedadeNomeRegioal(){
		
		incluirRegionalPage.incluirRegionalBuilder(nomeRegional, descricao, cns); //faz a inclusao de um registro
		
		loginPage.acessarPage();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.clicarRedes();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.preencheFiltroRegional(nomeRegional);
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.alterarRegional(nomeRegional, " ", "descricao", cns);
		
		alterarRegioinalPage.clicarNoBotaoAtualizarRegional();

		String mensagem = driver.findElement(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Cenario usuario preenche o nome com um registro ja cadastrado e salva.
	 * RESULTADO ESPERADO: Sistema deve exibir mensagem informando que ja existe um registro cadastrado.
	 */
	@Test
	public void deveValidarRegistroCadastrado(){
		
		incluirRegionalPage.incluirRegionalBuilder(regionalOriginal, descricaoOriginal, cnsOriginal);
		
		incluirRegionalPage.incluirRegionalBuilder(regionalCadastrada, descricaoOriginal, cnsOriginal);
		
		loginPage.acessarPage();
		
		alterarRegioinalPage.clicarRedes();
		
		alterarRegioinalPage.preencheFiltroRegional(regionalOriginal);		
	
		alterarRegioinalPage.alterarRegional(regionalOriginal, regionalCadastrada, "descricao registro cadastrado", cns);
		
		alterarRegioinalPage.clicarNoBotaoAtualizarRegional();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); //captura a mensagem de sucesso
		
		Assert.assertEquals("Regional já cadastrada !", mensagem);
		
		Assert.assertTrue(true);
	}
	/**
	 * Cenario o usuaario peenche o campo nome com caractere especial e salva.
	 * RESULTADO ESPERADO: Sistema deve validar com a mensagem "* Não é permitido caracter especial."
	 */
	@Test
	public void deveValidarCaractereEspecialNomeSalvar(){		

		incluirRegionalPage.incluirRegionalBuilder(nomeRegional, descricao, cns); //faz a inclusao de um registro
		
		loginPage.acessarPage();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.clicarRedes();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.preencheFiltroRegional(nomeRegional);
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.alterarRegional(nomeRegional, "asd@%$as'd'a", descricao, cns);
		
		alterarRegioinalPage.clicarNoBotaoAtualizarRegional();
		
		String mensagem = driver.findElement(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();
		
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}
	/**
	 * Cenario o usuaario peenche o campo descricao com caractere especial e salva.
	 * RESULTADO ESPERADO: Sistema deve validar com a mensagem "* Não é permitido caracter especial."
	 */
	@Test
	public void deveValidarCaractereEspecialDescricaoSalvar(){
		
		incluirRegionalPage.incluirRegionalBuilder(caracteresEspecDescricao, descricao, cnsOriginal); //faz a inclusao de um registro		

		loginPage.acessarPage();
		
		alterarRegioinalPage.clicarRedes();
	
		alterarRegioinalPage.preencheFiltroRegional(caracteresEspecDescricao);
		
		alterarRegioinalPage.alterarRegional(caracteresEspecDescricao, regionalOriginal, "dasd@#!@@!#asd'a'd", cns);
		
		alterarRegioinalPage.clicarNoBotaoAtualizarRegional();
		
		String mensagem = driver.findElement(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}
	/**
	 * Esse cenario o usuário apresentar a tela de altearação não altera nenhum registro e aciona o botao Atualizar.
	 * RESULTADO ESPERADO: Sistema deve apresentar a tela de sucesso e não registro cadastrado.
	 */
	@Test
	public void deveAcionaroBotaoAltearar(){

		incluirRegionalPage.incluirRegionalBuilder(nomeRegional, descricao, cns); //faz a inclusao de um registro
		
		loginPage.acessarPage();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.clicarRedes();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.preencheFiltroRegional(nomeRegional);
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		alterarRegioinalPage.clicarBotaoAlterar(nomeRegional);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); //captura a mensagem de sucesso
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
	}		
		
	@Parameters
	public static List<Object[]>datadriven(){
		return Arrays.asList(new Object[][]{
			{"CentroOeste", "descricao12", "13", "CentroOeste1", "descricaoAlteracao", "15", "area",	"ObrigatoriedadeDescricao", "caracteresEspealDesc",	"regionalCadastrada"},
		});
	}
	@After
	public void tearDown(){
			incluirRegionalPage.excluirRegionalPeloBanco(regionalCadastrada);
			incluirRegionalPage.excluirRegionalPeloBanco(regionalOriginal);
			incluirRegionalPage.excluirRegionalPeloBanco(nomeRegional);
			incluirRegionalPage.excluirRegionalPeloBanco(regObrigatoriedadeDescricao);
			incluirRegionalPage.excluirRegionalPeloBanco(caracteresEspecDescricao);
	}
}
