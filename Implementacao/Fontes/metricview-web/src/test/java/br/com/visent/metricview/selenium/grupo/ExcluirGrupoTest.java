package br.com.visent.metricview.selenium.grupo;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import br.com.visent.metricview.selenium.core.BaseTestCase;


@RunWith(Parameterized.class)
public class ExcluirGrupoTest extends BaseTestCase{
	ExcluirGrupoPage excluirGrupoPage;
	IncluirGrupoPage incluirGrupoPage;
	AlterarGrupoPage alterarGrupoPage;
	
	//dados
	String login;
	String senha;
	String nomeGrupo;
	String descricao;
	
	public ExcluirGrupoTest(String _nomeGrupo, String _descricao){
		this.nomeGrupo = _nomeGrupo;
		this.descricao = _descricao;
		
	}
	@Before
	public void setUp(){
		excluirGrupoPage = new ExcluirGrupoPage(driver);
		incluirGrupoPage = new IncluirGrupoPage(driver);
		alterarGrupoPage = new AlterarGrupoPage(driver);
		incluirGrupoPage.excluirGrupoPeloBanco(nomeGrupo);
	}
	/**
	 * Esse cenario eu faço remoção de um Grupo na lista.
	 */
	@Test
	public void excluirGrupo(){
		
		incluirGrupoPage.incluirGrupoBuilder(nomeGrupo, descricao);

		loginPage.acessarPage();
		
		util.clicaNoMenuAdministracao();
		
		excluirGrupoPage.excluirGrupo(nomeGrupo, descricao);
		
		util.verificaAlert("Tem certeza que deseja excluir ?");
		
		if(!alterarGrupoPage.verificarBanco(nomeGrupo, descricao))
			Assert.fail("Grupo: " + nomeGrupo + " ainda cadastrado na base!");
	}
	
	@Parameters
	public static List<Object[]>datadriven(){
		return Arrays.asList(new Object[][]{
			{"SUPER", "descricao"},
		});
	}
	@After
	public void after(){
		incluirGrupoPage.excluirGrupoPeloBanco(nomeGrupo);
	}
}
