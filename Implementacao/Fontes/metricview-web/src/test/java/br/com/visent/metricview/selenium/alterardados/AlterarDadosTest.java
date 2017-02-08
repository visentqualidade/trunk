package br.com.visent.metricview.selenium.alterardados;

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
import br.com.visent.metricview.selenium.util.Constantes;

@RunWith(Parameterized.class)
public class AlterarDadosTest extends BaseTestCase{

	WebDriverWait wait;
	AlterarDadosPage alterarDadosPage;

	String nomeDados;
	String emailDados;
	String telefoneDados;
	String areaDados;
	String regionalDados;
	String responsavelDados;
	String senhaAntiga;
	String senhaNova;
	String confirmaSenha;
	String emailCadastrado;

	public AlterarDadosTest(String _nomeDados,
			String _emailDados, String _telefoneDados, String _areaDados,
			String _regionalDados, String _responsavelDados,
			String _senhaAntiga, String _senhaNova, String _confirmaSenha,
			String _emailCadastrado) {

		this.nomeDados = _nomeDados;
		this.emailDados = _emailDados;
		this.telefoneDados = _telefoneDados;
		this.areaDados = _areaDados;
		this.regionalDados = _regionalDados;
		this.responsavelDados = _responsavelDados;
		this.senhaAntiga = _senhaAntiga;
		this.senhaNova = _senhaNova;
		this.confirmaSenha = _confirmaSenha;
		this.emailCadastrado = _emailCadastrado;
	}

	@Before
	public void setUp() {
		wait = new WebDriverWait(driver, 10);
		alterarDadosPage = new AlterarDadosPage(driver);
	}

	/**
	 * Usuário preenche o confirma senha diferente da senha nova.
	 */
	@Test
	public void senhasNaoConferemConfirmaSenha(){

		loginPage.acessarPage();

		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, emailDados, telefoneDados,
				areaDados, regionalDados, responsavelDados, senhaAntiga,
				senhaNova, "asdasdadasdasd");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//span[@class='ui-button-text']")));

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']")));

		String mensagem = driver
				.findElement(
						By.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']"))
				.getText();// captura a mensagem de obrigatoriedade

		Assert.assertEquals("As senhas não conferem !", mensagem);
	}

	/**
	 * Usuario preenche a senha antiga e a confirmação da senha deforma
	 * diferente.
	 */
	@Test
	public void senhasNaoConferem(){

		loginPage.acessarPage();

		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, emailDados, telefoneDados,
				areaDados, regionalDados, responsavelDados, senhaAntiga,
				"asdasdasd", confirmaSenha);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//span[@class='ui-button-text']")));

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']")));

		String mensagem = driver
				.findElement(
						By.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']"))
				.getText();// captura a mensagem de obrigatoriedade
		Assert.assertEquals("As senhas não conferem !", mensagem);
	}

	/**
	 * Usuario preencheu a senha antiga errada e salva
	 */
	@Test
	public void senhasAntigaIncorreta(){

		loginPage.acessarPage();

		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, emailDados, telefoneDados,
				areaDados, regionalDados, responsavelDados, "asdsdasadsad",
				senhaNova, confirmaSenha);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//span[@class='ui-button-text']")));

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.className("jGrowl-message")));

		String mensagem = driver.findElement(By.className("jGrowl-message"))
				.getText(); // captura a mensagem de sucesso
		Assert.assertEquals("Senha antiga incorreta", mensagem);
	}

	/**
	 * Esse cenario o usuario fecha o modal pelo icone e verifica se o mesmo foi
	 * para a tela enterior.
	 */
	@Test
	public void fecharModal(){

		loginPage.acessarPage();

		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, emailDados, telefoneDados,
				areaDados, regionalDados, responsavelDados, senhaAntiga,
				senhaNova, confirmaSenha);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//span[@class='ui-icon ui-icon-closethick']")));

		driver.findElement(
				By.xpath("//span[@class='ui-icon ui-icon-closethick']"))
				.click(); // submit

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//p[contains(text(),'" + nomeDados + "')]")));

		String nomeUser = driver.findElement(
				By.xpath("//p[contains(text(),'" + nomeDados + "')]"))
				.getText(); // verifica se foi para a tela anterior
		Assert.assertEquals("Usuário: " + nomeDados, nomeUser);
	}

	/**
	 * Testa a obrigatoriedade da senha antiga.
	 */
	@Test
	public void obrigatoriedadeSenhaAntiga(){

		loginPage.acessarPage();

		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, emailDados, telefoneDados,
				areaDados, regionalDados, responsavelDados, "", senhaNova,
				confirmaSenha);

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit

		String mensagem = driver
				.findElement(
						By.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']"))
				.getText();// captura a mensagem de obrigatoriedade
		Assert.assertEquals(
				"* O campo em destaque é de preenchimento obrigatório.",
				mensagem);

	}

	/**
	 * Esse cenario o usuário deixa a confirma senha em branco e preenche as
	 * senhas antiga e a senha nova. RESULTADO ESPERADO: Sistema deve exibir a
	 * mensagem "As senhas não conferem !" e nao permitir a alteração da senha.
	 */
	@Test
	public void obrigatoriedadeConfirmeSenha(){

		loginPage.acessarPage();
		
		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, emailDados, telefoneDados,
				areaDados, regionalDados, responsavelDados, senhaAntiga,
				senhaNova, "");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//span[@class='ui-button-text']")));

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']")));

		String mensagem = driver
				.findElement(
						By.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']"))
				.getText();// captura a mensagem de obrigatoriedade
		Assert.assertEquals(
				"* O campo em destaque é de preenchimento obrigatório.",
				mensagem);
	}

	/**
	 * Esse cenario o usuário deixa a senha nova em branco e preenche as senhas
	 * antiga e a confirmação da senha. RESULTADO ESPERADO: Sistema deve exibir
	 * a mensagem "As senhas não conferem !" e nao permitir a alteração da
	 * senha.
	 */
	@Test
	public void obrigatoriedadeSenhaNova(){

		loginPage.acessarPage();
		
		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, emailDados, telefoneDados,
				areaDados, regionalDados, responsavelDados, senhaAntiga, "",
				confirmaSenha);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//span[@class='ui-button-text']")));

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit

		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']")));
		
		String mensagem = driver
				.findElement(
						By.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']"))
				.getText();// captura a mensagem de obrigatoriedade

		Assert.assertEquals(
				"* O campo em destaque é de preenchimento obrigatório.",
				mensagem);
	}

	/**
	 * Esse cenario o usuário altera senha com um email ja cadastrado. RESULTADO
	 * ESPERADO: Sistema não deve permitir ao usuario alterar seu email por um
	 * email ja cadastrado.
	 */
	@Test
	public void emailCadastrado(){

		loginPage.acessarPage();
		
		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, emailCadastrado, telefoneDados,
				areaDados, regionalDados, responsavelDados, senhaAntiga,
				senhaNova, confirmaSenha);

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		String mensagem = driver.findElement(By.className("jGrowl-message"))
				.getText(); // captura a mensagem de sucesso
		Assert.assertEquals("E-mail já cadastrado !", mensagem);

		alterarDadosPage.deveConsultarEmailNoBanco(emailDados);
		
	}

	/**
	 * Verifica se o sistema esta validado email sem mascara.
	 */
	@Test
	public void emailSemMascara(){

		loginPage.acessarPage();
		
		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, "asdasdadasda.com.br",
				telefoneDados, areaDados, regionalDados, responsavelDados,
				senhaAntiga, senhaNova, confirmaSenha);

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit

		String mensagem = driver
				.findElement(
						By.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']"))
				.getText();// captura a mensagem de obrigatoriedade
		Assert.assertEquals(
				"* O campo em destaque foi preenchido de forma inválida.",
				mensagem);

	}

	/**
	 * Esse cenario esta a obrigatoriedade do campo email. RESULTADO ESPERADO:
	 * sistema deve apresentar o mensagem
	 * "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void obrigatoriedadeEmail(){

		loginPage.acessarPage();
		
		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, "", telefoneDados, areaDados,
				regionalDados, responsavelDados, senhaAntiga, senhaNova,
				confirmaSenha);

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit

		String mensagem = driver
				.findElement(
						By.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']"))
				.getText();// captura a mensagem de obrigatoriedade
		Assert.assertEquals(
				"* O campo em destaque é de preenchimento obrigatório.",
				mensagem);

		alterarDadosPage.deveConsultarEmailNoBanco(emailDados);
	}

	/**
	 * Usuario preenche o campo nome com caracteres especiais. RESULTADO
	 * ESPERADO: Sistema deve exibir a mensagem
	 * "* Não é permitido caracter especial."
	 */
	@Test
	public void caracteresEspeciaisCampoNome(){

		loginPage.acessarPage();
		
		util.alterarDados();

		alterarDadosPage.alterarDados("Teste'sdads'asdad", emailDados,
				telefoneDados, areaDados, regionalDados, responsavelDados,
				senhaAntiga, senhaNova, confirmaSenha);

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit

		String mensagem = driver
				.findElement(
						By.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']"))
				.getText();// captura a mensagem de obrigatoriedade
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}

	/**
	 * Esse cenario testa a mensagem de obrigatoriedade quando o campo Nome
	 * Usuario não é preenchido. RESULTADO ESPERADO: Sistema deve exibir a
	 * mensagem de obrigatoriedade
	 * "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void obrigatoriedadeCampoNome(){

		loginPage.acessarPage();
		
		util.alterarDados();

		alterarDadosPage.alterarDados("", emailDados, telefoneDados, areaDados,
				regionalDados, responsavelDados, senhaAntiga, senhaNova,
				confirmaSenha);

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String mensagem = driver
				.findElement(
						By.xpath("//form[@id='formAlterarDados']//span[@class='textoErro']"))
				.getText();// captura a mensagem de obrigatoriedade
		Assert.assertEquals(
				"* O campo em destaque é de preenchimento obrigatório.",
				mensagem);
	}

	/**
	 * Esse cenario o usuario preenche os dados RESULTADO ESPERADO: Sistema deve
	 * retornar para a tela anterior
	 */
	@Test
	public void alterarDadosCancelar(){

		loginPage.acessarPage();
		
		util.alterarDados();

		String titulo = driver.findElement(
				By.xpath("//span[@class='ui-dialog-title']")).getText();// Verifica
																		// titulo
																		// da
																		// tela
		Assert.assertEquals("Alterar dados", titulo); // Verifica titulo da tela

		driver.findElement(By.xpath("//span[contains(text(),'Fechar')]"))
				.click();

		String nomeUser = driver.findElement(
				By.xpath("//p[contains(text(),'" + nomeDados + "')]"))
				.getText();
		Assert.assertEquals("Usuário: " + nomeDados, nomeUser);
	}

	/**
	 * Esse cenario faz a alteração dos dados do usuário e aciona o comando
	 * salvar. RESULTADO ESPERADO: sistema deve alterar os dados no banco.
	 */
	@Test
	public void alterarDadosUsuario(){

		loginPage.acessarPage();
		
		util.alterarDados();

		alterarDadosPage.alterarDados(nomeDados, emailDados, telefoneDados,
				areaDados, regionalDados, responsavelDados, senhaAntiga,
				senhaNova, confirmaSenha);

		driver.findElement(By.xpath("//span[@class='ui-button-text']")).click(); // submit
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		String mensagem = driver.findElement(By.className("jGrowl-message"))
				.getText(); // captura a mensagem de sucesso
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);

		//validacao banco
		alterarDadosPage.deveValidarAlteracaoDeDadosNoBanco(Constantes.USUARIO, nomeDados, emailDados, telefoneDados, 
				                                        areaDados, regionalDados, responsavelDados);
	}

	@Parameters
	public static List<Object[]> datadriven() {
		return Arrays.asList(new Object[][] { {"rafaelsilva", "rafaelsilva@visent.com.br", "92515815","TESTE AREA", "Claro SP", "TESTE RESPONSAVEL", "rafaelsilva","rafaelsilva", "rafaelsilva", "henrique@visent.com.br" }, });
	}

	@After
	public void tearDown() {
	}
}
