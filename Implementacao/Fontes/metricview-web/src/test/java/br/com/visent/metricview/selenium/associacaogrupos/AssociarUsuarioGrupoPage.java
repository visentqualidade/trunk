package br.com.visent.metricview.selenium.associacaogrupos;

import java.util.List;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.dao.UsuarioDAO;
import br.com.visent.metricview.entidade.Usuario;

public class AssociarUsuarioGrupoPage {
	WebDriver driver;
	WebDriverWait wait;
	
	public AssociarUsuarioGrupoPage(WebDriver _driver){
		this.driver = _driver;
	}
	/**
	 * Associa usuario ao grupo
	 * @param nomeUsuario
	 * 			passa o nome do usuario como parametro
	 * @param nomeGrupo
	 * 			passa o nome do grupo como parametro.
	 */
	public void associar(String nomeUsuario, String nomeGrupo){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectComboGrupos")));
			Select selectGrupo = new Select(driver.findElement(By.id("selectComboGrupos")));
			selectGrupo.selectByVisibleText(nomeGrupo);
			
			Select selectUsuario = new Select(driver.findElement(By.id("selectComboUsuarios")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectComboUsuarios")));
			selectUsuario.selectByVisibleText(nomeUsuario);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='manutencao']//img[@class='imgenviar']")));
			driver.findElement(By.xpath("//div[@class='manutencao']//img[@class='imgenviar']")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("salvarManutencao")));
			driver.findElement(By.id("salvarManutencao")).click();
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel associar o usuaruo!");
		}
	}
	/**
	 * Retira a associacao de grupo.
	 * @param nomeUsuario
	 * 			passa o nome do usuario como parametro.
	 * @param nomeGrupo
	 * 			passaa o nome do grupo como parametro.
	 */
	public void desassociar(String nomeUsuario, String nomeGrupo){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectComboGrupos")));
			Select selectGrupo = new Select(driver.findElement(By.id("selectComboGrupos")));
			selectGrupo.selectByVisibleText(nomeGrupo);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectComboGruposAssociados")));
			Select selectUsuario = new Select(driver.findElement(By.id("selectComboGruposAssociados")));
			selectUsuario.selectByVisibleText(nomeUsuario);
	
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='manutencao']//img[@class='imgretirar']")));
			driver.findElement(By.xpath("//div[@class='manutencao']//img[@class='imgretirar']")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("salvarManutencao")));
			driver.findElement(By.id("salvarManutencao")).click();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel desassociar o Usuario!");
		}
	}
	
	/**
	 * Metodo que faz a exclusao do usuario do grupo.
	 * @param login
	 * 			passa o login do usuario como parametro.
	 */
	@SuppressWarnings("unchecked")
	public void excluirUsuarioGrupo(String login){
		try {
		
			String sql = "SELECT e FROM Usuario e where e.login = '" + login
					+ "'";
			JPAUtil.beginTransaction();
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			List<Usuario> lista = qr.getResultList();

			new UsuarioDAO().removeAssociacaoUsuarios(lista);

			JPAUtil.commitTransaction();
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel excluir o usuario!");
		}
		
	}
}
