package br.com.visent.metricview.selenium.permissoes;

import java.util.List;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.entidade.Ferramenta;
import br.com.visent.metricview.entidade.Grupo;
import br.com.visent.metricview.entidade.Permissao;
import br.com.visent.metricview.entidade.Usuario;


public class PermissaoUsuarioPage {
	WebDriver driver;
	WebDriverWait wait;
	
	public PermissaoUsuarioPage(WebDriver _driver){
		this.driver = _driver;
	}
	
	/**
	 * Metodo que clica no menu de permissoes.
	 * @param menu
	 * 		passa o nome de menu como parametro.
	 */
	public void clicarMenuPermissao(String menu){
		try {
			for(WebElement e : driver.findElement(By.id("ulMenuSistema")).findElements(By.tagName("li"))){
				if(e.getText().equals(menu)){
					if(e.getAttribute("class").equals("ativo")){
						break;
					}
					else{
						e.click();
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("Nao foi possivel Clicar na permissao do usuario!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo seleciona a permissao do usuario e salva
	 * @param tipoGrupoUsuario
	 * 			passa como parametro o tipo se é usuario ou grupo
	 * @param permissao
	 * 			passa como parametro a permissao se é administrador ou usuario
	 * @param usuarioGrupo
	 * 			passa como parametro o nome do usuario ou grupo
	 * @param menu
	 * 			passa o menu se é Dashboard(1), Easyview(2), Matriz de Interesse(3)
	 */
	public void selecionaPermissaoUsuario(String tipoGrupoUsuario, String permissao, String usuarioGrupo, String menu){
		try {
			wait = new WebDriverWait(driver, 10);
			
			clicarMenuPermissao(menu);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectTipo")));
			Select selectTipo = new Select(driver.findElement(By.id("selectTipo")));
			selectTipo.selectByVisibleText(tipoGrupoUsuario);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("comboSelectPermissao")));
			Select selectPermissoes = new Select(driver.findElement(By.id("comboSelectPermissao")));
			selectPermissoes.selectByVisibleText(permissao);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectPermissaoUsuario")));
			Select selectUsuario = new Select(driver.findElement(By.id("selectPermissaoUsuario")));
			selectUsuario.selectByVisibleText(usuarioGrupo);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
			driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")).click();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel selecionar permissao de Usuario!");
			e.printStackTrace();
		}
	}
	/**
	 * Seleciona a permissao de grupo.
	 * @param nome
	 * 			passa o nome do usuario como parametro.
	 * @param login
	 * 			passa o login do usuario como parametro.
	 * @param grupo
	 * 			passa o grupo como parametro.
	 * @param tipoPermissao
	 * 			passa o tipo de permissao como parametro.
	 * @param idPermissao
	 * 			passa o id da permissao.
	 */
	public void selecionaPermissaoGrupo(String nome, String login, String grupo, String tipoPermissao, String menu){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectComboGrupos")));
			Select comboGrupos = new Select(driver.findElement(By.id("selectComboGrupos"))); // seleciona o grupo
			comboGrupos.selectByVisibleText(grupo);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectComboUsuarios")));
			Select comboUsuarios = new Select(driver.findElement(By.id("selectComboUsuarios"))); //seleciona o usuarui
			comboUsuarios.selectByVisibleText(nome + " " + "(" + login + ")");
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='manutencao']//img[@class='imgenviar']")));
			driver.findElement(By.xpath("//div[@class='manutencao']//img[@class='imgenviar']")).click(); //associa usuario ao grupo
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("salvarManutencao")));
			driver.findElement(By.id("salvarManutencao")).click(); // salva associacao
			
			clicarMenuPermissao(menu);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectTipo")));
			Select tipoGrupo = new Select(driver.findElement(By.id("selectTipo")));  //em permissoes seleciona o grupo
			tipoGrupo.selectByVisibleText("Grupos");
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("comboSelectPermissao")));		
			Select selectPermissao = new Select(driver.findElement(By.id("comboSelectPermissao")));
			selectPermissao.selectByVisibleText(tipoPermissao);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectPermissaoUsuario")));
			Select permissaoUsuario = new Select(driver.findElement(By.id("selectPermissaoUsuario"))); // seleciona qual o grupo
			permissaoUsuario.selectByVisibleText(grupo);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")));
			driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgenviar']")).click(); //associar permissao
			
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("salvarPermissoes")));
			driver.findElement(By.id("salvarPermissoes")).click(); //salvar permissoes
		} catch (Exception e) {
			Assert.fail("Nao foi possivel selecionar permissao de grupo!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Valida se a permissao foi inclusa no banco
	 * @param login
	 * 			Passa como parametro o login do usuario com a permissao
	 * @param modulo
	 * 			Passa como parametro o modulo Dashboard(1), Easyview(2), Matriz de Interesse(3)
	 */
	@SuppressWarnings({"unchecked" })
	public void validaPermissaoBancoUsuario(String login, String modulo){
		try {
			Thread.sleep(1000);
			
			String idUsuario = null;
			String sqlIdUsuario = "select u from Usuario u where login = '" + login + "'";
			
			Query qrUsuario = JPAUtil.getEntityManager().createQuery(sqlIdUsuario);
			List<Usuario> listaUsuario = qrUsuario.getResultList();
			
			for(Usuario u : listaUsuario){
				idUsuario = String.valueOf(u.getIdUsuario());
			}
			
			String idFerramenta = null;
			String sqlFerramenta = "select f from Ferramenta f where nome = '" + modulo + "'";
			Query qrFerramenta = JPAUtil.getEntityManager().createQuery(sqlFerramenta);
			List<Ferramenta> listaFerramenta = qrFerramenta.getResultList();
			
			for(Ferramenta f : listaFerramenta){
				idFerramenta = String.valueOf(f.getId());
			}
			
			String sqlPermissao = "select p from Permissao p where id_ferramenta = '" + idFerramenta + "' and id_usuario = '" + idUsuario + "'";
			boolean achou = false;
			
			Query qrPermissao = JPAUtil.getEntityManager().createQuery(sqlPermissao);
			List<Permissao> listaPermissao = qrPermissao.getResultList();
			
			for(Permissao p : listaPermissao){
				String idPermissaoUsuario = String.valueOf(p.getUsuario().getIdUsuario());
				
				if(idUsuario.equals(idPermissaoUsuario)){
					achou = true;
					break;
				}
			}
			
			if(!achou){
				Assert.fail("Permissao não foi inclusa no banco!");
			}
		} catch (Exception e) {
			Assert.fail("Nao foi possivel validar permissao no banco!");
			e.printStackTrace();
		}
	}
	/**
	 * Valida se o banco esta associando permissao.
	 * @param grupo
	 * 			passa o grupo como parametro.
	 * @param modulo
	 * 			passa qual modulo esta sendo validado.
	 */
	@SuppressWarnings({ "unchecked"})
	public void validaPermissaoBancoGrupo(String grupo, String modulo){
		try {
			Thread.sleep(1000);
			String idGrupo = null;
			String sqlGrupo = "select a from Grupo a where a.nome = '" + grupo + "'";
			
			Query qr = JPAUtil.getEntityManager().createQuery(sqlGrupo);
			List<Grupo> lista = qr.getResultList();
			
			for(Grupo e : lista){
				idGrupo = String.valueOf(e.getIdGrupo());
			}
			
			String idFerramenta = null;
			String sqlFerramenta = "select a from Ferramenta  a where a.nome = '" + modulo + "'";
			
			Query qrFerramenta = JPAUtil.getEntityManager().createQuery(sqlFerramenta);
			List<Ferramenta> listaFerramentas = qrFerramenta.getResultList();
			
			for(Ferramenta f: listaFerramentas){
				idFerramenta = String.valueOf(f.getId());
			}
			
			String sqlPermissao = "select a from Permissao a where id_ferramenta = '" + idFerramenta + "' and id_grupo = '" + idGrupo + "'";
			String idPermissaoGrupo = null;
			boolean achou = false;
			Query qrPermissaoGrupo = JPAUtil.getEntityManager().createQuery(sqlPermissao);
			List<Permissao> listaPermissoes = qrPermissaoGrupo.getResultList();
			
			for(Permissao p: listaPermissoes){
				idPermissaoGrupo = String.valueOf(p.getGrupo().getIdGrupo());
				
				if(idGrupo.equals(idPermissaoGrupo)){
					achou = true;
				}
			}
			
			if(!achou){
				Assert.fail("Permissao não foi inclusa no banco!");
			}
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possviel validar permissao de grupo no banco!");
			e.printStackTrace();
		}
	}
	/**
	 * Verifica se a permissão esta contida na pagina inicial.
	 * @param login
	 * 			passa o login como parametro.
	 * @param modulo
	 * 			passa qual modulo o usurio deveria mostrar.
	 * @param permissao
	 * 			passa qual a permissao o usuário deve ter.
	 */
	public void validaPermissaoPaginaInicial(String login, String modulo, String permissao){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'" + modulo + "')]")));

			Assert.assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'" + modulo + "')]")).isEnabled());
			Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'" + permissao + "')]")).isEnabled());
		} catch (Exception e) {
			Assert.fail("Nao foi possivel validar permissao na pagina inicial!");
			e.printStackTrace();
		}
	}
	/**
	 * Valida se o acesso a permissao está na página inicial.
	 * @param login
	 * @param permissao
	 */
	public void validaIndividual(String login, String permissao, String nomePermissao){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("accordionPermissoes")));		
			
			clicarVisualizarPermissao(nomePermissao);
				
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Permissão Individual: "+ permissao +"')]")));
			
			String menssagem = driver.findElement(By.xpath("//p[contains(text(), 'Permissão Individual: "+ permissao +"')]")).getText();
			Assert.assertEquals(menssagem, "Permissão Individual: " + permissao);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel validar permissao individual!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que clica no menu de permisaao.
	 * @param nomePermissao
	 * 		passa o nome da permissao como parametro.
	 */
	private void clicarVisualizarPermissao(String nomePermissao) {
		try {
			for(WebElement e : driver.findElement(By.id("accordionPermissoes")).findElements(By.tagName("h3"))){
				if(e.getText().equals(nomePermissao)){
					e.click();
					System.out.println(e.getText());
				}
			}
		} catch (Exception e) {
			Assert.fail("Nao clicou na tala vizualizar permisao!");
			e.printStackTrace();
		}
	}

	/**
	 * Valida se o acesso ao grupo está na pagina inicial.
	 * @param login
	 * 			passa o login do usuario como parametro.
	 * @param nomeGrupo
	 * 			passa o nome do grupo como parametro.
	 * @param permissao
	 * 			passa qual a permissao do usuario como parametro.
	 */
	public void validaIndividualGrupo(String login, String nomeGrupo, String permissao, String nomePermissao){
		try {
			wait = new WebDriverWait(driver, 10);
			clicarVisualizarPermissao(nomePermissao);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), '" + nomeGrupo + ": " + permissao + "')]")));
			
			String menssagem = driver.findElement(By.xpath("//p[contains(text(), '" + nomeGrupo + ": " + permissao + "')]")).getText();
			
			driver.findElements(By.xpath("//button[@id='Fechar']")).get(1).click(); // fechar modal permissoes
			driver.findElements(By.xpath("//button[@id='Fechar']")).get(0).click(); //fechar modal de visualizar usuarios.
			
			Assert.assertEquals(menssagem, nomeGrupo + ": " + permissao);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel validar permissao de grupo!");
			e.printStackTrace();
		}
	}
	/**
	 * Retira a permissao de grupo 
	 * @param nomeGrupo
	 * 			passa o nome do grupo como parametro.
	 * @param selecionar
	 * 			seleciona se a permissao é grupos ou usuarios.
	 * @param permissao
	 * 			seleciona a permissao administrador ou usuario.
	 */
	public void retirarAssociacaoGrupo(String nomeGrupo, String selecionar, String permissao){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectTipo")));
			
			Select tipoGrupo = new Select(driver.findElement(By.id("selectTipo")));  //em permissoes seleciona o grupo
			tipoGrupo.selectByVisibleText(selecionar);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("comboSelectPermissao")));
			
			Select selectPermissao = new Select(driver.findElement(By.id("comboSelectPermissao")));
			selectPermissao.selectByVisibleText(permissao);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectPermissao")));
			
			Select permissaoUsuario = new Select(driver.findElement(By.id("selectPermissao"))); // seleciona qual o grupo
			permissaoUsuario.selectByVisibleText(nomeGrupo);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='permissoes']//img[@class='imgretirar']")));
			
			driver.findElement(By.xpath("//div[@class='permissoes']//img[@class='imgretirar']")).click(); //associar permissao
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("salvarPermissoes")));
			
			driver.findElement(By.id("salvarPermissoes")).click(); //salvar permissoes
		} catch (Exception e) {
			Assert.fail("Nao foi possivel retirar permisao!");
			e.printStackTrace();
		}
	}
}
