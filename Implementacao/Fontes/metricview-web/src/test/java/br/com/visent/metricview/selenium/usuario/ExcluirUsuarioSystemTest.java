package br.com.visent.metricview.selenium.usuario;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import br.com.visent.metricview.selenium.core.BaseTestCase;
import br.com.visent.metricview.selenium.util.Constantes;

@RunWith(Parameterized.class)
public class ExcluirUsuarioSystemTest extends BaseTestCase{
	private static IncluirUsuarioPage incluirUsuarioPage;
	private static AlterarUsuarioPage alterarUsuarioPage;
	private static ExcluirUsuarioPage excluirUsuarioPage;
	
	//dados
	String loginOrig;
	String nomeOrig;
	String emailOrig;
	String telefoneOrig;
	String areaOrig;
	String regionalOrig;
	String responsavelOrig;

	
	public ExcluirUsuarioSystemTest(String _loginOrig, String _nomeOrig, String _emailOrig,
							   String _telefoneOrig, String _areaOrig, String _regionalOrig, String _responsavelOrig){

		this.loginOrig       = _loginOrig;
		this.nomeOrig        = _nomeOrig;
		this.emailOrig       = _emailOrig;
		this.telefoneOrig    = _telefoneOrig;
		this.areaOrig        = _areaOrig;
		this.regionalOrig    = _regionalOrig;
		this.responsavelOrig = _responsavelOrig;
	}
	
	@BeforeClass
	public static void setUp(){
		loginPage.acessarPage();
		loginPage.logOut();
		loginPage.login(Constantes.SYSTEM, Constantes.SENHASYSTEM);
		incluirUsuarioPage = new IncluirUsuarioPage(driver);
		alterarUsuarioPage = new AlterarUsuarioPage(driver);
		excluirUsuarioPage = new ExcluirUsuarioPage(driver);
	}
	/**
	 * Faz a exclusao do usuario e verifica no banco se o mesmo foi excluso.
	 * RESUTLADO ESPERADO: Sistema deve excluir o usuario.
	 */
	@Test
	public void excluirUsuario(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginOrig);
		
		loginPage.acessarPage();
		
		incluirUsuarioPage.inserirUsuarioBuilder(loginOrig, nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig);
		
		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();
		
		excluirUsuarioPage.excluir(loginOrig);
		excluirUsuarioPage.verificaAlert();
		
		if(incluirUsuarioPage.consultaRegistroNoBanco("login", loginOrig)){
			Assert.fail("Sistema nao permitiu excluir o usuario");
		}
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginOrig);
	}
	/**
	 * Cancela a exclusao do usuario e verifica se o mesmo não foi excluso do banco.
	 * RESULTADO ESPERADO: sistema não deve excluir o usuario do banco.
	 */
	@Test
	public void cancelarExclusao(){
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginOrig);
		
		loginPage.acessarPage();
		
		incluirUsuarioPage.inserirUsuarioBuilder(loginOrig, nomeOrig, emailOrig, telefoneOrig, areaOrig, regionalOrig, responsavelOrig);
		
		alterarUsuarioPage.clicarNoBotaoVisualizarUsuarios();
		
		excluirUsuarioPage.excluir(loginOrig);
		excluirUsuarioPage.cancelaAlert();
		
		if(!incluirUsuarioPage.consultaRegistroNoBanco("login", loginOrig)){
			Assert.fail("Sistema excluiu o usuario indevidamente!");
		}
		
		incluirUsuarioPage.excluirUsuarioPeloBanco(loginOrig);
	}
	@Parameters
	public static List<Object[]>datadriven(){
		return Arrays.asList(new Object[][]{
			{"michael", "Michael Jackson", "michael@visent.com.br", "123456789", "Area 99", "Claro DF", "Familia" },
		});
	}
	
	@AfterClass
	public static void tearDown(){
	}
}
