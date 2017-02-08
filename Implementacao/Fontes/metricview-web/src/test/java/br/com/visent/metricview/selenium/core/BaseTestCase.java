package br.com.visent.metricview.selenium.core;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.selenium.login.LoginPage;
import br.com.visent.metricview.selenium.util.Browser;
import br.com.visent.metricview.selenium.util.Constantes;
import br.com.visent.metricview.selenium.util.VisentTestCase;

public class BaseTestCase {

	protected static VisentTestCase util;
	protected static LoginPage loginPage;
	protected static WebDriver driver;
	
	@BeforeClass
	public static void setUpClass() {
		JPAUtil.init("metricview");
		if(!AllTests.isAllTest ){
			util = new VisentTestCase();
			driver = util.chamarBrowser(Browser.CHROME);
			loginPage = new LoginPage(driver);
			loginPage.login(Constantes.USUARIO, Constantes.SENHA);
		}
	}
	
	
	@AfterClass
	public static void tearDownClass() {
	}
}
