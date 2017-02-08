package br.com.visent.metricview.selenium.core;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.visent.metricview.selenium.alterardados.AlterarDadosTest;
import br.com.visent.metricview.selenium.associacaogrupos.AssociacaoUsuarioGrupoTest;
import br.com.visent.metricview.selenium.grupo.AlterarGrupoTest;
import br.com.visent.metricview.selenium.grupo.ExcluirGrupoTest;
import br.com.visent.metricview.selenium.grupo.IncluirGrupoTest;
import br.com.visent.metricview.selenium.login.LoginPage;
import br.com.visent.metricview.selenium.permissoes.PermissaoGrupoDashboardTest;
import br.com.visent.metricview.selenium.permissoes.PermissaoGrupoEasyViewTest;
import br.com.visent.metricview.selenium.permissoes.PermissaoGrupoMatrizTest;
import br.com.visent.metricview.selenium.permissoes.PermissaoUsuarioDashboardTest;
import br.com.visent.metricview.selenium.permissoes.PermissaoUsuarioEasyViewTest;
import br.com.visent.metricview.selenium.permissoes.PermissaoUsuarioMatrizTest;
import br.com.visent.metricview.selenium.regional.AlterarRegionalTest;
import br.com.visent.metricview.selenium.regional.ExcluirRegionalTest;
import br.com.visent.metricview.selenium.regional.InclusaoRegionalTest;
import br.com.visent.metricview.selenium.usuario.AlterarUsuarioSystemTest;
import br.com.visent.metricview.selenium.usuario.AlterarUsuarioTest;
import br.com.visent.metricview.selenium.usuario.ExcluirUsuarioSystemTest;
import br.com.visent.metricview.selenium.usuario.ExcluirUsuarioTest;
import br.com.visent.metricview.selenium.usuario.IncluirUsuarioSystemTest;
import br.com.visent.metricview.selenium.usuario.IncluirUsuarioTest;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;


@RunWith(Suite.class)
@SuiteClasses({ 
	//Usuario
	IncluirUsuarioTest.class,
	AlterarUsuarioTest.class,
	ExcluirUsuarioTest.class,
	
	//Grupo
	IncluirGrupoTest.class,
	AlterarGrupoTest.class,
	ExcluirGrupoTest.class,
	
	//Regional
	InclusaoRegionalTest.class,
	AlterarRegionalTest.class,
	ExcluirRegionalTest.class,
	
	//Permissoes
	PermissaoUsuarioMatrizTest.class,
	PermissaoUsuarioEasyViewTest.class,
	PermissaoUsuarioDashboardTest.class,
	PermissaoGrupoMatrizTest.class,
	PermissaoGrupoDashboardTest.class,
	PermissaoGrupoEasyViewTest.class,
	
	//AlterarDados
	AlterarDadosTest.class,
	
	//AssociarGrupos
	AssociacaoUsuarioGrupoTest.class,

	//Usuario System
	IncluirUsuarioSystemTest.class,
	AlterarUsuarioSystemTest.class,
	ExcluirUsuarioSystemTest.class
})

public class AllTests {
	public static boolean isAllTest = false;
	
	@BeforeClass
	public static void setUpClassAcesso() {
		isAllTest = true;
		BaseTestCase.util = new VisentTestCase();
		
		BaseTestCase.driver = BaseTestCase.util.chamarBrowser(Browser.CHROME);
		
		BaseTestCase.loginPage = new LoginPage(BaseTestCase.driver);
		
		BaseTestCase.loginPage.login(Constantes.USUARIO, Constantes.SENHA);
		
		BaseTestCase.loginPage.acessarPage();
	}

	@AfterClass
	public static void tearDownClassAcesso() {
		BaseTestCase.driver.close();
		BaseTestCase.driver.quit();
	}
}
