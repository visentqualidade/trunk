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
public class InclusaoRegionalTest extends BaseTestCase {
	WebDriverWait wait;
	IncluirRegionalPage incluirRegionalPage;

	// dados
	String nomeRegional;
	String descricao;
	String cns;

	public InclusaoRegionalTest(String _nomeRegional, String _descricao,
			String _cns) {
		this.nomeRegional = _nomeRegional;
		this.descricao = _descricao;
		this.cns = _cns;
	}

	@Before
	public void setUp() {
		incluirRegionalPage = new IncluirRegionalPage(driver);
		wait = new WebDriverWait(driver, 120);
	}

	/**
	 * Metodo inclui regional e depois verifica no banco se a mesma foi esta
	 * contida. RESULTADO ESPERADO: Sistema deve incluir no banco os registros
	 * inclusos.
	 */
	@Test
	public void deveIncluirRegional() {

		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.incluirRegional(nomeRegional, descricao, cns);

		incluirRegionalPage.clicarBotaoSalvarFechar();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String mensagem = driver.findElement(By.className("jGrowl-message"))
				.getText(); // captura a mensagem de sucesso
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);

		incluirRegionalPage.validaInclusaoRegionalNoBanco(nomeRegional,
				descricao, cns);
	}

	/**
	 * Metodo que testa obrigatoriedade do campo Cns na inclusao usuario deixao
	 * campo CNs em abranco e aciona o comando salvar. RESULTADO ESPERADO:
	 * Sistema deve exibir a mensagem de obrigatoriedade:
	 * "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void deveApresentarObrigatoriedadeCns() {
		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.obrigatoriedadeCns("REGIONAL", "DESCRICAO");

		incluirRegionalPage.clicarNoBotaoSalvar();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//form[@id='formRegional']//span[@class='textoErro']")));

		String mensagem = driver.findElement(
				By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();

		Assert.assertEquals(
				"* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Metodo que testa obrigatoriedade do campo Cns na inclusao usuario deixao
	 * campo Descricao em abranco e aciona o comando salvar. RESULTADO ESPERADO:
	 * Sistema deve exibir a mensagem de obrigatoriedade:
	 * "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void deveApresentarObrigatoriedadeDescricao() {
		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.incluirRegional("REGIONAL", " ", cns);

		incluirRegionalPage.clicarNoBotaoSalvar();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")));

		String mensagem = driver.findElement(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();

		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Metodo que testa obrigatoriedade do campo Cns na inclusao usuario deixao
	 * campo Nome em abranco e aciona o comando salvar. RESULTADO ESPERADO:
	 * Sistema deve exibir a mensagem de obrigatoriedade:
	 * "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void deveApresentarObrigatoriedadeNomeRegional() {
		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		org.openqa.selenium.WebElement e = driver.findElement(By.id("windowRegionais")).findElements(By.tagName("h1")).get(0);
		
		wait.until(ExpectedConditions.visibilityOf(e));
		
		incluirRegionalPage.incluirRegional(" ", descricao, cns);

		incluirRegionalPage.clicarBotaoSalvarFechar();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")));

		String mensagem = driver.findElement(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();

		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Metodo que testa obrigatoriedade do campo Cns na inclusao usuario deixao
	 * campo CNs em abranco e aciona o comando salvar. RESULTADO ESPERADO:
	 * Sistema deve exibir a mensagem de obrigatoriedade:
	 * "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void deveApresentarObrigatoriedadeCnsSalvarFechar() {
		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.obrigatoriedadeCns("REGIONAL", "DESCRICAO");

		incluirRegionalPage.clicarBotaoSalvarFechar();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formRegional']//span[@class='textoErro']")));

		String mensagem = driver.findElement(
				By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();

		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Metodo que testa obrigatoriedade do campo Cns na inclusao usuario deixao
	 * campo Descricao em abranco e aciona o comando salvar. RESULTADO ESPERADO:
	 * Sistema deve exibir a mensagem de obrigatoriedade:
	 * "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void deveApresentarObrigatoriedadeDescricaoSalvarFechar() {

		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.incluirRegional("REGIONAL", " ", cns);

		incluirRegionalPage.clicarBotaoSalvarFechar();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formRegional']//span[@class='textoErro']")));

		String mensagem = driver.findElement(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();

		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Metodo que testa obrigatoriedade do campo Cns na inclusao usuario deixao
	 * campo Nome em abranco e aciona o comando salvar. RESULTADO ESPERADO:
	 * Sistema deve exibir a mensagem de obrigatoriedade:
	 * "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void deveApresentarObrigatoriedadeNomeRegioalSalvarFechar() {
		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.incluirRegional(" ", descricao, cns);

		incluirRegionalPage.clicarBotaoSalvarFechar();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formRegional']//span[@class='textoErro']")));

		String mensagem = driver.findElement(By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();

		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Cenario usuario preenche o nome com um registro ja cadastrado e salva.
	 * RESULTADO ESPERADO: Sistema deve exibir mensagem informando que ja existe
	 * um registro cadastrado.
	 */
	@Test
	public void deveValidarRegistroCadastrado() {
		incluirRegionalPage
				.incluirRegionalBuilder(nomeRegional, descricao, cns);

		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.incluirRegional(nomeRegional, descricao, cns);

		incluirRegionalPage.clicarBotaoSalvarFechar();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));

		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); // captura a mensagem de sucesso
		Assert.assertEquals("Regional já cadastrada !", mensagem);
	}

	/**
	 * Cenario o usuaario peenche o campo nome com caractere especial e salva.
	 * RESULTADO ESPERADO: Sistema deve validar com a mensagem
	 * "* Não é permitido caracter especial."
	 */
	@Test
	public void deveValidarCaractereEspecialNomeSalvarFechar() {
		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.incluirRegional("Regioal'asd'saad", descricao, cns);

		incluirRegionalPage.clicarBotaoSalvarFechar();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formRegional']//span[@class='textoErro']")));

		String mensagem = driver.findElement(
				By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();

		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);

	}

	/**
	 * Cenario o usuaario peenche o campo nome com caractere especial e salva.
	 * RESULTADO ESPERADO: Sistema deve validar com a mensagem
	 * "* Não é permitido caracter especial."
	 */
	@Test
	public void deveValidarCaractereEspecialNomeSalvar() {
		
		loginPage.acessarPage();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.incluirRegional("Regioal'asd'saad", descricao, cns);

		incluirRegionalPage.clicarNoBotaoSalvar();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formRegional']//span[@class='textoErro']")));

		String mensagem = driver.findElement(
				By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();

		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}

	/**
	 * Cenario o usuaario peenche o campo descricao com caractere especial e
	 * salva. RESULTADO ESPERADO: Sistema deve validar com a mensagem
	 * "* Não é permitido caracter especial."
	 */
	@Test
	public void deveValidarCaractereEspecialDescricaoSalvar() {
		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.incluirRegional(nomeRegional, "desc@'ric'a.o", cns);

		incluirRegionalPage.clicarBotaoSalvarFechar();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formRegional']//span[@class='textoErro']")));

		String mensagem = driver.findElement(
				By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();

		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}

	/**
	 * Cenario o usuaario peenche o campo descricao com caractere especial e
	 * salva. RESULTADO ESPERADO: Sistema deve validar com a mensagem
	 * "* Não é permitido caracter especial."
	 */
	@Test
	public void deveValidarCaractereEspecialDescricaoSalvarFechar() {
		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		incluirRegionalPage.incluirRegional(nomeRegional, "desc@'ric'a.o", cns);

		incluirRegionalPage.clicarBotaoSalvarFechar();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formRegional']//span[@class='textoErro']")));

		String mensagem = driver.findElement(
				By.xpath("//form[@id='formRegional']//span[@class='textoErro']")).getText();

		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}

	/**
	 * Cenario clica no botao cancelar e verifica se o mesmo fechou a modal e
	 * retornou para a tela anterior.
	 */
	@Test
	public void deveCancelarModalRegional() {
		loginPage.acessarPage();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		incluirRegionalPage.clicarMenuRedes();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//img[@id='btnAddRegional']")));
		
		driver.findElement(By.xpath("//img[@id='btnAddRegional']")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String titulo = driver.findElement(
				By.className("ui-dialog-title")).getText(); // Verifica titulo da tela
		
		Assert.assertEquals(titulo, "Cadastrar Regional"); // Verifica titulo da tela

		incluirRegionalPage.clicarNoBotaoCancelar();

		String tituloAdmin = driver.findElement(
				By.xpath("//h1[contains(text(),'Regionais')]")).getText();
		Assert.assertEquals(tituloAdmin, "Regionais");
	}

	@Test
	public void deveFecharJanelaModal() {
		loginPage.acessarPage();

		incluirRegionalPage.clicarMenuRedes();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//img[@id='btnAddRegional']")));
		
		driver.findElement(By.xpath("//img[@id='btnAddRegional']")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Verifica titulo da tela
		String titulo = driver.findElement(By.className("ui-dialog-title")).getText();
		// Verifica titulo da tela
		Assert.assertEquals(titulo, "Cadastrar Regional"); 

		driver.findElement(
				By.xpath("//span[@class='ui-icon ui-icon-closethick']")).click(); // Cancelar

		String tituloAdmin = driver.findElement(
				By.xpath("//h1[contains(text(),'Regionais')]")).getText();
		
		Assert.assertEquals(tituloAdmin, "Regionais");
	}

	@Parameters
	public static List<Object[]> datadriven() {
		return Arrays
				.asList(new Object[][] { { "norte", "descricao11", "13" }, });
	}

	@After
	public void tearDown() {
			incluirRegionalPage.excluirRegionalPeloBanco(nomeRegional);
	}
}
