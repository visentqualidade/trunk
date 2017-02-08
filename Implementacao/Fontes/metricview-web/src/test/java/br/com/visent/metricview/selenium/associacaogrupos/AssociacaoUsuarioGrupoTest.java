package br.com.visent.metricview.selenium.associacaogrupos;

import java.util.Arrays;
import java.util.List;

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
import br.com.visent.metricview.selenium.grupo.IncluirGrupoPage;
import br.com.visent.metricview.selenium.usuario.IncluirUsuarioPage;

@RunWith(Parameterized.class)
public class AssociacaoUsuarioGrupoTest extends BaseTestCase{
	WebDriverWait wait;
	AssociarUsuarioGrupoPage associarUsuarioPage;
	IncluirGrupoPage incluirGrupoPage;
	IncluirUsuarioPage incluirUsuarioPage;

	// dados
	String nomeGrupo;
	String nomeUsuario;
	String nomeLogin;

	public AssociacaoUsuarioGrupoTest(String _nomeGrupo, String _nomeUsuario, String _nomeLogin) {
		this.nomeGrupo = _nomeGrupo;
		this.nomeUsuario = _nomeUsuario;
		this.nomeLogin = _nomeLogin;
	}

	@Before
	public void setUp() {
		wait = new WebDriverWait(driver, 10);
		associarUsuarioPage = new AssociarUsuarioGrupoPage(driver);
		incluirGrupoPage = new IncluirGrupoPage(driver);
		incluirUsuarioPage = new IncluirUsuarioPage(driver);
		incluirUsuarioPage.excluirUsuarioPeloBanco(nomeLogin);
		incluirGrupoPage.excluirGrupoPeloBanco(nomeGrupo);
	}

	/**
	 * Associa um Usuario ao respectivo grupo escolhido , para iniciar o teste o
	 * usuario e o grupo ja devem estar cadastrados no banco
	 */
	@Test
	public void associarDesassociarUsuarioAoGrupo(){
		
		incluirGrupoPage.incluirGrupoBuilder(nomeGrupo, "Feito pra testar associacao");

		incluirUsuarioPage.inserirUsuarioBuilder(nomeLogin, nomeUsuario, "tabosa@visent.com",
				"12304560", "061", "Claro DF", "Rafael");
		
		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		associarUsuarioPage.associar(nomeUsuario + " (" + nomeLogin + ")", nomeGrupo);
		
		incluirUsuarioPage.validanoBanco(nomeLogin, nomeUsuario, "tabosa@visent.com",
				"12304560", "061", "Claro DF", "Rafael");
		
		incluirGrupoPage.verificaSeGrupoExisteNoBanco(nomeGrupo);
		
		associarUsuarioPage.desassociar(nomeUsuario + " (" + nomeLogin + ")", nomeGrupo);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jGrowl-message")));
		String mensagem = driver.findElement(By.className("jGrowl-message")).getText(); // captura a mensagem de sucesso
		
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
	}

	/**
	 * Testa se ao excluir um grupo a associação existente e desfeita no banco
	 * de dados , para iniciar o teste o usuario e o grupo ja devem estar
	 * cadastrados no banco
	 * @throws InterruptedException 
	 */
	@Test
	public void desassociarUsuarioAoGrupoExcluindoGrupo(){
		
		incluirGrupoPage.incluirGrupoBuilder(nomeGrupo, "Feito pra testar associacao");

		incluirUsuarioPage.inserirUsuarioBuilder(nomeLogin, nomeUsuario, "tabosa@visent.com",
				"12304560", "061", "Claro DF", "Rafael");
		
		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		associarUsuarioPage.associar(nomeUsuario + " (" + nomeLogin + ")", nomeGrupo);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading")));
		associarUsuarioPage.desassociar(nomeUsuario + " (" + nomeLogin + ")", nomeGrupo);
		
		incluirGrupoPage.verificaSeGrupoExisteNoBanco(nomeGrupo);
	}

	@Parameters
	public static List<Object[]> datadriven() {
		return Arrays.asList(new Object[][] { {"GrupoTesteAsociacaoteste", "Luiz1", "luiztabosa1" }, });
	}

	@After
	public void after() {
		incluirUsuarioPage.excluirUsuarioPeloBanco(nomeLogin);
		incluirGrupoPage.excluirGrupoPeloBanco(nomeGrupo);

	}
}
