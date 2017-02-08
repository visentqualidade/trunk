package br.com.visent.metricview.selenium.usuario;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.metricview.selenium.core.BaseTestCase;

@RunWith(Parameterized.class)
public class ExcluirUsuarioTest extends BaseTestCase{
	IncluirUsuarioPage incluirUsuarioPage;
	AlterarUsuarioPage alterarUsuarioPage;
	ExcluirUsuarioPage excluirUsuarioPage;
	WebDriverWait wait;
	
	//dados
	String loginOrig;
	String nomeOrig;
	String emailOrig;
	String telefoneOrig;
	String areaOrig;
	String regionalOrig;
	String responsavelOrig;

	
	public ExcluirUsuarioTest(String _loginOrig, String _nomeOrig, String _emailOrig,
							   String _telefoneOrig, String _areaOrig, String _regionalOrig, String _responsavelOrig){

		this.loginOrig       = _loginOrig;
		this.nomeOrig        = _nomeOrig;
		this.emailOrig       = _emailOrig;
		this.telefoneOrig    = _telefoneOrig;
		this.areaOrig        = _areaOrig;
		this.regionalOrig    = _regionalOrig;
		this.responsavelOrig = _responsavelOrig;
	}
	
	@Before
	public void setUp(){
		incluirUsuarioPage = new IncluirUsuarioPage(driver);
		alterarUsuarioPage = new AlterarUsuarioPage(driver);
		excluirUsuarioPage = new ExcluirUsuarioPage(driver);
		wait = new WebDriverWait(driver, 10);
	}
	/**
	 * Faz a exclusao do usuario e verifica no banco se o mesmo foi excluso.
	 * RESUTLADO ESPERADO: Sistema deve excluir o usuario.
	 */
	@Test
	public void excluirUsuario(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuarioBuilder(loginOrig, nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig);
		
		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		excluirUsuarioPage.excluir(loginOrig);
		
		excluirUsuarioPage.verificaAlert();
		
		if(incluirUsuarioPage.consultaRegistroNoBanco("login", loginOrig)){
			Assert.fail("Sistema nao excluiu o usuario!");
		}
	}
	/**
	 * Cancela a exclusao do usuario e verifica se o mesmo não foi excluso do banco.
	 * RESULTADO ESPERADO: sistema não deve excluir o usuario do banco.
	 */
	@Test
	public void cancelarExclusao(){
		
		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();
		
		incluirUsuarioPage.inserirUsuarioBuilder(loginOrig, nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig);
		
		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();

		excluirUsuarioPage.excluir(loginOrig);

		excluirUsuarioPage.cancelaAlert();
		
		if(!incluirUsuarioPage.consultaRegistroNoBanco("login", loginOrig)){
			Assert.fail("Sistema excluiu o usuario!");
		}
	}

	@Parameters
	public static List<Object[]>datadriven(){
		return Arrays.asList(new Object[][]{
			{"michael", "Michael Jackson", "michael@visent.com.br", "123456789", "Area 99", "Claro DF", "Familia" },
		});
	}
	
	@After
	public void tearDown(){
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginOrig);
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginOrig);
	}
}
