package br.com.visent.metricview.selenium.usuario;

import java.util.List;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.entidade.Usuario;

public class ExcluirUsuarioPage {
	WebDriver driver;
	WebDriverWait wait;

	public ExcluirUsuarioPage(WebDriver _driver) {
		this.driver = _driver;
	}

	/**
	 * Faz a exclusao do usuario.
	 * 
	 * @param login
	 *            recebe o login como parametro
	 */
	public void excluir(String login) {
		clicarBotaoTabela(buscarId(login), "excluir");
	}

	/**
	 * Faz um select no banco e retorna o id do usuário
	 * 
	 * @param login
	 *            passa o login como parametro.
	 * @return ID
	 */
	@SuppressWarnings("unchecked")
	public String buscarId(String login) {

		String id = "";
		try {
			String sql = "SELECT e FROM Usuario e where e.login = '" + login
					+ "'";
			JPAUtil.beginTransaction();
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			List<Usuario> lista = qr.getResultList();

			if (lista.size() > 1) {
				Assert.fail("Sistema não deve permitir incluir mais de 2 usuario com o mesmo login.");
			}

			for (Usuario e : lista) {
				id = String.valueOf(e.getId());
			}

			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel buscar por ID.");
			e.printStackTrace();
		}

		return id;
	}

	/**
	 * Verifica o alerta apresentado.
	 */
	public void verificaAlert() {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert msgExclusao = driver.switchTo().alert();
			String msg = msgExclusao.getText();
			msgExclusao.accept();
			Assert.assertEquals(msg, "Tem certeza que deseja excluir ?");
			Thread.sleep(1000);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel apresentar o alert!");
			e.printStackTrace();
		}
	}

	/**
	 * Clica no botao cancelar do Alert.
	 */
	public void cancelaAlert() {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert msgExclusao = driver.switchTo().alert();
			msgExclusao.dismiss();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao cancelar!");
			e.printStackTrace();
		}
	}

	/**
	 * Percorre a lista e faz a ação com o atributo passado como parametro.
	 * 
	 * @param login
	 *            passa o login como parametro.
	 * @param botao
	 *            passa o botao a ser clicado como parametro.
	 */
	public void clicarBotaoTabela(String idUsuario, String botao) {
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("dlgVisualizarUsuarios")));

		WebElement table = driver.findElement(By.id("dlgVisualizarUsuarios"));
		boolean achou = false;
		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		// And iterate over them, getting the cells
		for (WebElement row : allRows) {
			if (achou) {
				break;
			}
			if (row.getAttribute("id").equals(idUsuario)) {
				achou = true;
				List<WebElement> cells = row.findElements(By.tagName("td"));
				for (WebElement cell : cells) {
					if (cell.getAttribute("class").equals(botao)) {
						cell.click();
						break;
					}
				}
			}
		}
	}

}
