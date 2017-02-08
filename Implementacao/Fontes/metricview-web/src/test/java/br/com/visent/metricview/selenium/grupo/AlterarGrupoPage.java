package br.com.visent.metricview.selenium.grupo;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.Query;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.visent.corporativo.util.JPAUtil;
import br.com.visent.metricview.entidade.Grupo;

public class AlterarGrupoPage {
	WebDriver driver;
	WebDriverWait wait;
	
	public AlterarGrupoPage(WebDriver _driver){
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
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dlgVisualizarGrupos']//span[@class='textoErro']")));
		} catch (Exception e) {
			Assert.fail("Nao foi possivel capturar mensagem de obrigatoriedade!");
			e.printStackTrace();
		}
		
		return	driver.findElement(By.xpath("//div[@id='dlgVisualizarGrupos']//span[@class='textoErro']")).getText();
	}
	
	/**
	 * busca pelo nome do grupo e clica no botao editar.
	 * @param nomeGrupo
	 * 			passa o nome do grupo como parametro.
	 * @param idGrupo
	 * 			id do grupo.
	 */
	public void clicarBotaoAlterarGrupo(String nomeGrupo, String idGrupo){
		try {
			
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnVisualizarGrupos")));
			driver.findElement(By.id("btnVisualizarGrupos")).click();
			
			clicarBotaoTabela(nomeGrupo, "editar", idGrupo);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao para alterar.");
			e.printStackTrace();
		}
	}
	/**
	 * Faz a edicao do grupo
	 * @param nome
	 * 			passa o nome do grupo como parametro.
	 * @param novo
	 * 			passa o novo nome do grupo como parametro.
	 * @param idGrupo
	 * 			passa o id do grupo do banco.
	 * @param descricao
	 * 			passa a descricao como parametro.
	 * @param novaDescricao
	 * 			passa a nova descricao como parametro.
	 */
	public void alterarGrupo(String nome, String novo, String idGrupo, String descricao, String novaDescricao){
		try {
			
			wait = new WebDriverWait(driver, 10);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='"+ nome +"']")));
			
			driver.findElement(By.xpath("//input[@value='"+ nome +"']")).clear();
			driver.findElement(By.xpath("//input[@value='"+ nome +"']")).sendKeys(novo);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='"+ descricao +"']")));
			
			driver.findElement(By.xpath("//input[@value='"+ descricao +"']")).clear();
			driver.findElement(By.xpath("//input[@value='"+ descricao +"']")).sendKeys(novaDescricao);
			
			clicarBotaoTabela(nome, "editar", idGrupo);
		} catch (Exception e) {
			Assert.fail("Nao foi possivel alterar o grupo.");
			e.printStackTrace();
		}
	}
	/**
	 * Varre a tela e clica no botao passado como parametro.
	 * @param nomeGrupo
	 * 			passa o nome do grupo como parametro.
	 * @param botao
	 * 			passa o botao que vai clicar.
	 * @param idGrupo
	 * 			passa o id do grupo como para metro.
	 * @throws InterruptedException
	 */
	public void clicarBotaoTabela(String nomeGrupo, String botao, String idGrupo) throws InterruptedException{
		try {
			
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tabelaGrupos")));
			
			WebElement table = driver.findElement(By.id("tabelaGrupos"));
			boolean achou=false;
			List<WebElement> allRows = table.findElements(By.tagName("tr")); 
			// And iterate over them, getting the cells 
			for (WebElement row : allRows) { 
				if(achou){
					break;
				}
				if(row.getAttribute("id").equals(idGrupo)){
					achou = true;
					List<WebElement> cells = row.findElements(By.tagName("td")); 
					for (WebElement cell : cells) {
						if( cell.getAttribute("class").equals(botao)){
							cell.click();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			Assert.fail("Nao foi possivel clicar no botao da tabela.");
			e.printStackTrace();
		}
	}
	/**
	 * Valida no banco se o grupo foi alterado.
	 * @param Grupo
	 * 			passa o nome do grupo como parametro.
	 * @return
	 * 			retorna se foi validado ou nao .
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public boolean verificarBanco(String nomeGrupo, String descricao){
		boolean isGrupo = false;
		try {
			String sql = "SELECT e FROM Grupo e where e.nome = '" + nomeGrupo + "'";
			JPAUtil.beginTransaction();
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			List<Grupo> lista = qr.getResultList();
			
			if(lista.size()>1){
				return isGrupo;
			}
			
			for(Grupo e : lista){
				isGrupo = true;
				Assert.assertEquals(e.getNome(), nomeGrupo);
				Assert.assertEquals(e.getDescricao(), descricao);
			}
			
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel consultar Grupo!");
			e.printStackTrace();
		}
		return isGrupo;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Verifica a quantidade de grupos cadastrados no banco.
	 * @param nome
	 * 			passa o nome do grupo.
	 * @return
	 * 		retorna a quantidade.
	 */
	public int verificaQuantidadeDeGruposCadastradosNoBanco(String nome){
		int qtd = 0;
		try{
			String sql = "SELECT e FROM Grupo e where e.nome = '" + nome + "'";
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			
			List<String> lista = qr.getResultList();
			if (lista.size()>1){
				qtd = lista.size();
				return qtd;
			}
	
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel excluir o usuario!");
		}
			return qtd;
	}
	
	/**
	 * Busca o grupo por id.
	 * @param nomeGrupo
	 * 			passa o nome do grupo como parametro
	 * @return
	 * 			retorna o id que ele achou.
	 */
	@SuppressWarnings("unchecked")
	public String buscarId(String nomeGrupo){
		
		String id = null;
		try{
			Thread.sleep(1000);
			String sql = "SELECT e FROM Grupo e where e.nome = '" + nomeGrupo + "'";
			Query qr = JPAUtil.getEntityManager().createQuery(sql);
			
			List<Grupo> lista = qr.getResultList();
			
			if (lista.size()>1){
				Assert.fail("Existe 2 grupos com o mesmo nome!");
			}
			
			for(Grupo g : lista){
				id = String.valueOf(g.getIdGrupo());
			}
			JPAUtil.closeEntityManager();
		} catch (Exception e) {
			Assert.fail("Nao foi possivel excluir o usuario!");
			e.printStackTrace();
		}
		return id;
	}
}
