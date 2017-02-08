package br.com.visent.metricview.selenium.alterardados;

import java.util.List;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.selenium.util.Constantes;

public class AlterarDadosPage {
	WebDriver driver;
	WebDriverWait wait;
	
	public AlterarDadosPage(WebDriver _driver){
		this.driver = _driver;
	}
	
	public void alterarDados(String nomeDados, String emailDados, String telefoneDados, String areaDados, 
							 String regionalDados, String responsavelDados, String senhaAntiga, String senhaNova, String confirmaSenha){
		try {
			
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='ui-dialog-title']")));
			String titulo =  driver.findElement(By.xpath("//span[@class='ui-dialog-title']")).getText();//Verifica titulo da  tela
			Assert.assertEquals("Alterar dados", titulo); //Verifica titulo da  tela
			
			driver.findElement(By.id("nomeDados")).clear();
			driver.findElement(By.id("nomeDados")).sendKeys(nomeDados);
			
			driver.findElement(By.id("emailDados")).clear();
			driver.findElement(By.id("emailDados")).sendKeys(emailDados);
			
			driver.findElement(By.id("telefoneDados")).click();
			driver.findElement(By.id("telefoneDados")).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
			Thread.sleep(500);
			driver.findElement(By.id("telefoneDados")).sendKeys(telefoneDados);
			
			driver.findElement(By.id("areaDados")).clear();
			driver.findElement(By.id("areaDados")).sendKeys(areaDados);
			driver.findElement(By.id("areaDados")).sendKeys(Keys.TAB);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='s2id_regionalDados']//a[@href='javascript:void(0)']")));
			
			WebElement select = driver.findElement(By.xpath("//div[@id='s2id_regionalDados']//a[@href='javascript:void(0)']"));
			select.click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + regionalDados + "')]")));
			
			driver.findElement(By.xpath("//div[contains(text(),'" + regionalDados + "')]")).click();
			
			driver.findElement(By.id("responsavelDados")).clear();
			driver.findElement(By.id("responsavelDados")).sendKeys(responsavelDados);
			
			driver.findElement(By.id("senhaAntigaDados")).sendKeys(senhaAntiga); 		    //senha antiga
			driver.findElement(By.id("senhaNovaDados"  )).sendKeys(senhaNova);   		   	//senha nova
			driver.findElement(By.id("senhaNovaConfirmDados")).sendKeys(confirmaSenha);	    //confirma senha nova
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel alterar os dados!");
		}
		
	}
	
	/**
	 * Metodo que consulta se o email ja esta ocmo vazio no banco de dados.
	 * @param email
	 * 			passa o email do usuario como parametro.
	 */
	@SuppressWarnings("unchecked")
	public void deveConsultarEmailNoBanco(String email){
		try {
			String sql = "SELECT e FROM Usuario e where e.login = '" + Constantes.USUARIO + "'";
			
			JPAUtil.beginTransaction();
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			List<Usuario> listaUsuario = qr.getResultList();
			
			
			for(Usuario u : listaUsuario) {
				
				if (Constantes.USUARIO.equals(u.getLogin())) {
					if(u.getEmail().isEmpty()){
						Assert.fail("Email esta com valor Nulo");
					}
				}
			}
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel Validar o email!");
		}
	}
	
	/**
	 * Valida se as informacoes alterados do usuario estao no banco.
	 * @param login
	 * 			passa o login como parametro.
	 * @param nome
	 * 			passa o nome como parametro.
	 * @param email
	 * 			passa o email como parametro.
	 * @param telefone
	 * 			passa o telefone como parametro.
	 * @param area
	 * 			passa a area como parametro.
	 * @param regional
	 * 			passa a regional como parametro.
	 * @param responsavel
	 * 			passa o responsavel como parametro.
	 */
	@SuppressWarnings("unchecked")
	public void deveValidarAlteracaoDeDadosNoBanco(String login, String nome, String email, String telefone, String area, 
												   String regional, String responsavel){
		
		String sql = "SELECT e FROM Usuario e where e.login = '" + Constantes.USUARIO + "'";
		
		JPAUtil.beginTransaction();
		Query qr = JPAUtil.getEntityManager().createQuery(sql);
		List<Usuario> listaUsuario = qr.getResultList();
		
		for(Usuario u : listaUsuario) {
			if (Constantes.USUARIO.equals(u.getLogin())) {
				Assert.assertEquals(u.getLogin(), login);
				Assert.assertEquals(u.getNome(), nome);
				Assert.assertEquals(u.getEmail(), email);
				Assert.assertEquals(u.getTelefone(), telefone);
				Assert.assertEquals(u.getArea(), area);
				Assert.assertEquals(u.getRegional(), regional);
				Assert.assertEquals(u.getResponsavel(), responsavel);
			}
		}
		
		JPAUtil.closeEntityManager();
	}
}
