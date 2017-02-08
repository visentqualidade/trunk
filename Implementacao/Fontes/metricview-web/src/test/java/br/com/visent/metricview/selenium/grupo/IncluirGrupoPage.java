package br.com.visent.metricview.selenium.grupo;

import java.util.List;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.entidade.Grupo;
import br.com.visent.metricview.service.GrupoService;


public class IncluirGrupoPage {
	
	WebDriver driver;
	WebDriverWait wait;
	
	public IncluirGrupoPage(WebDriver _driver){
		this.driver = _driver;
	}
	
	/**
	 * Verifica mensagem de obrigatoriedade.
	 * @return
	 * 		retorna a string com a mensagem.
	 */
	public String verificaMensagemDeObrigatoriedade(){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dlgNovoGrupo']//span[@class='textoErro']")));
		} catch (Exception e) {
			Assert.fail("Nao foi possivel capturar mensagem de obrigatoriedade.");
			e.printStackTrace();
		}
		
		return	driver.findElement(By.xpath("//div[@id='dlgNovoGrupo']//span[@class='textoErro']")).getText();
	}
	
	/**
	 * Clica no botao salvar grupo.
	 */
	public void clicarBotaoSalvarGrupo(){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Salvar")));
			
			driver.findElement(By.id("Salvar")).click();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao Salvar.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Clicar no botao salver e fechar grupo.
	 */
	public void clicarBotaoSalvarEFecharGrupo(){
		try {
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Salvar")));
			driver.findElement(By.id("Salvar e Fechar")).click();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao salvar e fechar.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Exclui o grupo pelo banco.
	 * @param nome
	 * 			passa o nome do grupo como parametro.
	 */
	@SuppressWarnings("unchecked")
	public void excluirGrupoPeloBanco(String nome) {
		
		if (verificaSeGrupoExisteNoBanco(nome)) {
			try {
				String sql = "SELECT e FROM Grupo e where e.nome = '" + nome + "'";
				JPAUtil.beginTransaction();
				Query qr = JPAUtil.getEntityManager().createQuery(sql);
				List<String> lista = qr.getResultList();
				
				for(Object e : lista){
					JPAUtil.getEntityManager().remove(e);
				}
				JPAUtil.commitTransaction();
				JPAUtil.closeEntityManager();
			} catch (Exception e) {
				Assert.fail("Nao foi possivel excluir o usuario!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metodo verifica se o grupo existe no banco
	 * @param nome
	 * 			passsa o nome do grupo como parametr.
	 * @return
	 * 			retorna true se achou e false se nao achou.
	 */
	@SuppressWarnings("unchecked")
	public boolean verificaSeGrupoExisteNoBanco(String nome){
		boolean isLogin = false;
		try {
			String sql = "SELECT e FROM Grupo e where e.nome = '" + nome + "'";
			
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			List<String> lista = qr.getResultList();
			
			if(!lista.isEmpty()){
				isLogin = true;
				return isLogin;
			}
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel Consultar o usuario no banco!");
			e.printStackTrace();
		}
		return isLogin;
	}
	
	/**
	 * Metodo que faz a inclusao do grupo pela service.
	 * @param nomeGrupo
	 * 			passa o nome do grupo como parametro.
	 * @param descricao
	 * 			passa a descricao do grupo como parametro.
	 */
	public void incluirGrupoBuilder(String nomeGrupo, String descricao){
		Grupo grupo = null;
		try {
			grupo = new GrupoDataBuilder().comNome(nomeGrupo).comDescricao(descricao).constroi();
			
			new GrupoService().inserir(grupo);
			JPAUtil.commitTransaction();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel incluir Grupo!");
			e.printStackTrace();
		}
		
	}
	/**
	 * Faz a inclusao de grupo.
	 * @param nomeGrupo
	 * 			passa o nome do grupo como parametro.
	 * @param descricao
	 * 			passa a descricao do  grupo como parametro.
	 */
	public void incluirGrupo(String nomeGrupo, String descricao){
		try {
			wait = new WebDriverWait(driver, 20);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAddGrupo")));
			
			driver.findElement(By.id("btnAddGrupo")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-dialog-title")));
			
			String titulo = driver.findElement(By.className("ui-dialog-title")).getText();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nomeGrupo")));
			
			driver.findElement(By.id("nomeGrupo")).sendKeys(nomeGrupo);
			driver.findElement(By.id("descricaoGrupo")).sendKeys(descricao);
			
			Assert.assertEquals(titulo, "Cadastrar Grupo");
		} catch (Exception e) {
				Assert.fail("Nao foi possivel incluir o grupo!");
				e.printStackTrace();
		}
	}
}
