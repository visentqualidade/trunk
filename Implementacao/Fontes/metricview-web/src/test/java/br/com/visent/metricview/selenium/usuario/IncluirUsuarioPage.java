package br.com.visent.metricview.selenium.usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.componente.dwr.util.CriptografiaUtil;
import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.entidade.Usuario;
import br.com.visent.metricview.service.UsuarioService;

public class IncluirUsuarioPage {
	WebDriver driver;
	WebDriverWait wait;

	public IncluirUsuarioPage(WebDriver _driver) {
		this.driver = _driver;
	}

	/**
	 * Faz a inclusao do usuario
	 * 
	 * @param login
	 *            Passa o login do usuario como parametro.
	 * @param nome
	 *            Passa o nome do usuario como parametro.
	 * @param email
	 *            Passa o email do usuario como parametro.
	 * @param telefone
	 *            Passa o telefone do usuario como parametro.
	 * @param area
	 *            Passa a area do usuario como parametro.
	 * @param regional
	 *            Passa a regional do usuario como parametro.
	 * @param responsavel
	 *            Passa o responsavel do usuario como parametro.
	 * @throws InterruptedException
	 */
	public void inserirUsuario(String login, String nome, String email,
			String telefone, String area, String regional, String responsavel) {
		try {
			wait = new WebDriverWait(driver, 10);
			(new WebDriverWait(driver, 10))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return driver.findElement(
									By.xpath("//img[@id='btnAddUsuario']"))
									.isDisplayed();
						}
					});

			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.id("btnAddUsuario")));
			driver.findElement(By.id("btnAddUsuario")).click();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			String titulo = driver.findElement(
					By.xpath("//span[contains(text(),'Cadastrar Usuário')]")).getText();

			Assert.assertEquals(titulo, "Cadastrar Usuário");

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// Preencher os campos

			driver.findElement(By.id("loginUsuario")).sendKeys(login);
			driver.findElement(By.id("nomeUsuario")).sendKeys(nome);
			driver.findElement(By.id("emailUsuario")).sendKeys(email);
			driver.findElement(By.id("telefoneUsuario")).click();
			driver.findElement(By.id("telefoneUsuario")).sendKeys(telefone);
			driver.findElement(By.id("areaUsuario")).sendKeys(area);
			driver.findElement(By.id("areaUsuario")).sendKeys(Keys.TAB);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath("//div[@id='s2id_regionalUsuario']//a[@href='javascript:void(0)']")));

			WebElement select = driver
					.findElement(By
							.xpath("//div[@id='s2id_regionalUsuario']//a[@href='javascript:void(0)']"));
			select.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath("//div[contains(text(),'" + regional + "')]")));

			driver.findElement(
					By.xpath("//div[contains(text(),'" + regional + "')]"))
					.click();

			driver.findElement(By.id("responsavelUsuario")).sendKeys(
					responsavel);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel incluir o usuario!");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que captura mensage de excecao da tela.
	 */
	public String visualizaMensagem() {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath("//form[@id='formNovoUsuario']//span[@class='textoErro']")));
			Thread.sleep(1000);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel visualizar mensagem de Erro!");
			e.printStackTrace();
		}
		return driver
				.findElement(
						By.xpath("//form[@id='formNovoUsuario']//span[@class='textoErro']"))
				.getText();
	}

	/**
	 * Clica no botao Salvar.
	 */
	public void clicaNoBotaoSalvar() {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath("//span[contains(text(),'Salvar')]")));
			driver.findElement(By.xpath("//span[contains(text(),'Salvar')]"))
					.click(); // salvar
			Thread.sleep(500);
		} catch (Exception e) {
			Assert.fail("Não foi possivel clicar no botao Salvar!");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que clica no botao Salvar e Fechar.
	 */
	public void clicaNoBotaoSalvareFechar() {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath("//span[contains(text(),'Salvar e Fechar')]")));
			driver.findElement(
					By.xpath("//span[contains(text(),'Salvar e Fechar')]"))
					.click();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel Clicar no botao SalvarFechar!");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que clica no botao Cancelar da Modal.
	 */
	public void clicaNoBotaoCancelar() {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath("//span[contains(text(),'Cancelar')]")));
			driver.findElement(By.xpath("//span[contains(text(),'Cancelar')]"))
					.click(); // salvar
		} catch (Exception e) {
			Assert.fail("Nao foi possivel Clicar no botao Cancelar!");
			e.printStackTrace();
		}
	}

	/**
	 * Verifica se o usuario foi incluso no banco de dados.
	 * 
	 * @param login
	 *            passa o login do usuario como parametro.
	 * @param nome
	 *            passa o nome do usuario como parametro.
	 * @param email
	 *            passa o email do usuario como parametro.
	 * @param telefone
	 *            passa o telefone do usuario como paremetro.
	 * @param area
	 *            passa a area do usuario como parametro.
	 * @param regional
	 *            passa a regional do usuario como parametro.
	 * @param responsavel
	 *            passa o responsavel seteado no usuario acomo parametro.
	 * 
	 * @return retorna true se achou o registro.
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public boolean validanoBanco(String login, String nome, String email,
			String telefone, String area, String regional, String responsavel) {
		boolean isFind = false;
		try {

			String sql = "SELECT e FROM Usuario e where e.login = '" + login
					+ "'";

			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			List<Usuario> lista = qr.getResultList();

			if (lista.size() > 1) {
				Assert.fail("Sistema não deve permitir incluir mais de 2 usuario com o mesmo login.");
			}

			for (Usuario e : lista) {
				isFind = true;
				Assert.assertEquals(e.getLogin(), login);
				Assert.assertEquals(e.getNome(), nome);
				Assert.assertEquals(e.getEmail(), email);
				Assert.assertEquals(e.getTelefone(), telefone);
				Assert.assertEquals(e.getArea(), area);
				Assert.assertEquals(e.getRegional(), regional);
				Assert.assertEquals(e.getResponsavel(), responsavel);
			}
			JPAUtil.commitTransaction();
			JPAUtil.closeEntityManager();

		} catch (Exception e) {
			Assert.fail("Nao foi possivel encontrar o registro na base!");
			e.printStackTrace();
		}
		return isFind;
	}

	/**
	 * Metodo que consulta registro na tabela de usuarios.
	 * 
	 * @param coluna
	 *            passa a coluna como parametro.
	 * @param campo
	 *            passa o campo que deseja retornar como parametro.
	 * 
	 * @return retorna uma string com os dados que foi encontrado.
	 */
	@SuppressWarnings("unchecked")
	public boolean consultaRegistroNoBanco(String coluna, String campo) {
		boolean isLogin = false;

		try {
			String sql = "SELECT e FROM Usuario e where e." + coluna + "="
					+ "'" + campo + "'";

			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			List<String> lista = qr.getResultList();

			if (!lista.isEmpty()) {
				isLogin = true;
				return isLogin;
			}
			JPAUtil.closeEntityManager();

		} catch (Exception e) {
			Assert.fail("Nao foi possivel consultar os Dados");
		}
		return isLogin;
	}

	/**
	 * Consulta no banco por quantidade e retorna um int.
	 */
	public int consultaQuantidadeDeRegistroNoBanco(String coluna, String campo) {

		int cont = 0;
		try {
			String sql = "SELECT e FROM Usuario e where e." + coluna + "="
					+ "'" + campo + "'";
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			cont = qr.getResultList().size();
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel consultar os Dados");
		}
		return cont;
	}

	/**
	 * Metodo faz a exclusao do usuario no banco.
	 * 
	 * @param con
	 *            passa como parametro a conexao com o banco.
	 * @param login
	 *            passa como parametro o login que desena excluir.
	 */
	@SuppressWarnings("unchecked")
	public void excluirUsuarioPeloBanco(String login) {
		
		if(verificaSeUsuarioExisteNoBanco(login)){
			try {
				String sql = "SELECT e FROM Usuario e where e.login = '" + login
						+ "'";
				JPAUtil.beginTransaction();
				Query qr = JPAUtil.getEntityManager().createQuery(sql);
				List<Usuario> lista = qr.getResultList();
				
				for (Usuario e : lista) {
					JPAUtil.getEntityManager().remove(e);
				}
				JPAUtil.commitTransaction();
				JPAUtil.closeEntityManager();
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Nao foi possivel excluir o usuario!");
			}
		}
	}

	/**
	 * Metodo que verifica se o usuario existe no banco.
	 * 
	 * @param con
	 *            passa a conexao como parametro.
	 * @param login
	 *            passa o login do usuario como parametro.
	 * @return returna true se o usuario existe.
	 */
	@SuppressWarnings("unchecked")
	public boolean verificaSeUsuarioExisteNoBanco(String login) {
		boolean isLogin = false;
		try {
			String sql = "SELECT e FROM Usuario e where e.login = '" + login
					+ "'";

			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			List<String> lista = qr.getResultList();

			if (!lista.isEmpty()) {
				isLogin = true;
				return isLogin;
			}
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel Consultar o usuario no banco!");
		}
		return isLogin;
	}

	/**
	 * Verifica na tela a mensagem de obrigatoriedade.
	 * 
	 * @return retorna a string com a mensagem.
	 */
	public String verificaMensagemObrigatoriedade() {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath("//form[@id='formNovoUsuario']//span[@class='textoErro']")));

		} catch (Exception e) {
			Assert.fail("Nao foi possivel capturar mensagem de obrigatoriedade!");
			e.printStackTrace();
		}
		return driver
				.findElement(
						By.xpath("//form[@id='formNovoUsuario']//span[@class='textoErro']"))
				.getText();// captura a mensagem de obrigatoriedade
	}

	/**
	 * Metodo que captura mensagem de sucesso.
	 * 
	 * @return deve retornar uma string com a mensagem.
	 */
	public String verificaMensagemDeSucesso() {
		try {
			wait = new WebDriverWait(driver, 10);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.className("jGrowl-message")));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Nao foi possivel exibir a mensagem de sucesso!");
		}

		// captura a mensagem de sucesso
		return driver.findElement(By.className("jGrowl-message")).getText(); 
	}

	/**
	 * Metodo que faz a inclusao do usuario atravez de uma builder.
	 * 
	 * @param login
	 *            passa o login como parametro.
	 * @param nome
	 *            passa o nome como parametro.
	 * @param email
	 *            passa o email como parametro.
	 * @param telefone
	 *            passa o telefone como parametro.
	 * @param area
	 *            passa a area como parametro.
	 * @param regional
	 *            passa a regional como parametro.
	 * @param responsavel
	 *            passa o responsavel como parametro.
	 */
	public void inserirUsuarioBuilder(String login, String nome, String email,
			String telefone, String area, String regional, String responsavel) {

		Usuario usuario = null;
		try {
			usuario = new UsuarioDataBuilder()
					.comLogin(login)
					.comSenha(CriptografiaUtil.getInstance().criptografar(login.toLowerCase()))
					.comNome(nome).comEmail(email).comArea(area)
					.comTelefone(telefone).comRegional(regional)
					.comResponsavel(responsavel).comPrimeiroAcesso(false)
					.comAdmin(false).constroi();

			new UsuarioService().inserir(usuario);
			JPAUtil.commitTransaction();
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel inserir o objeto Usuario!");
			e.printStackTrace();
		}
	}
}
