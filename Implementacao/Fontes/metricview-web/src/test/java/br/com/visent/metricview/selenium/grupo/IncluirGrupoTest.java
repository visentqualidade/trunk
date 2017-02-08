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
import br.com.visent.metricview.selenium.usuario.IncluirUsuarioPage;

@RunWith(Parameterized.class)
public class IncluirGrupoTest extends BaseTestCase {
	IncluirUsuarioPage incluirUsuarioPage;
	IncluirGrupoPage incluirGrupoPage;

	// dados
	String nomeGrupo;
	String descricao;
	String caracteresp;

	public IncluirGrupoTest(String _nomeGrupo, String _descricao,
			String _caracteresp) {
		this.nomeGrupo = _nomeGrupo;
		this.descricao = _descricao;
		this.caracteresp = _caracteresp;
	}

	@Before
	public void setUp() {
		incluirGrupoPage = new IncluirGrupoPage(driver);
		incluirUsuarioPage = new IncluirUsuarioPage(driver);
		incluirGrupoPage.excluirGrupoPeloBanco(nomeGrupo);
	}

	/**
	 * Esse cenario eu faço o cadastro de um novo grupo com o botão Salvar e
	 * confiro se no banco o mesmo foi cadastrado.
	 */
	@Test
	public void incluirGrupoSalvar() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupo(nomeGrupo, descricao);

		incluirGrupoPage.clicarBotaoSalvarGrupo();

		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso();

		if (!new AlterarGrupoPage(driver).verificarBanco(nomeGrupo, descricao)) {
			Assert.fail("Grupo: " + nomeGrupo + " não foi cadastrado na base!");
		}

		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
	}

	/**
	 * Esse cenario eu faço o cadastro de um novo grupo com o botão Salvar e
	 * Fechar, depois confiro se no banco o mesmo foi cadastrado.
	 */
	@Test
	public void incluirGrupoSalvarEFechar() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupo(nomeGrupo, descricao);

		incluirGrupoPage.clicarBotaoSalvarEFecharGrupo();

		// captura a mensagem de sucesso
		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso(); 

		if (!new AlterarGrupoPage(driver).verificarBanco(nomeGrupo, descricao)) {
			Assert.fail("Grupo: " + nomeGrupo + "não foi cadastrado na base!");
		}
		Assert.assertEquals("Operação Realizada com Sucesso !", mensagem);
	}

	/**
	 * Esse cenario eu tento cadastrar um novo grupo sem informar o nome do
	 * grupo utilizando o botão Salvar. RESULTADO ESPERADO: Sistema deve validar
	 * com a mensagem "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void incluirGrupoSalvarBrancoNome() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupo("", descricao);

		incluirGrupoPage.clicarBotaoSalvarGrupo();

		String mensagem = incluirGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Esse cenario eu tento cadastrar um novo grupo sem informar o nome do
	 * grupo utilizando o botão Salvar e Fechar. RESULTADO ESPERADO: Sistema
	 * deve validar com a mensagem
	 * "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void incluirGrupoSalvarEFecharBrancoNome() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupo(" ", descricao);

		incluirGrupoPage.clicarBotaoSalvarEFecharGrupo();

		String mensagem = incluirGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Esse cenario eu tento cadastrar um novo grupo sem informar a descrição do
	 * grupo utilizando o botão Salvar. RESULTADO ESPERADO: Sistema deve validar
	 * com a mensagem "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void incluirGrupoSalvarBrancoDescricao() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupo(nomeGrupo, "");

		incluirGrupoPage.clicarBotaoSalvarGrupo();

		String mensagem = incluirGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Esse cenario eu tento cadastrar um novo grupo sem informar a descrição do
	 * grupo utilizando o botão Salvar e Fechar. RESULTADO ESPERADO: Sistema
	 * deve validar com a mensagem
	 * "* O campo em destaque é de preenchimento obrigatório."
	 */
	@Test
	public void incluirGrupoSalvarEFecharBrancoDescricao() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupo(nomeGrupo, "");

		incluirGrupoPage.clicarBotaoSalvarEFecharGrupo();

		String mensagem = incluirGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals("* O campo em destaque é de preenchimento obrigatório.", mensagem);
	}

	/**
	 * Esse cenario eu tento cadastrar um novo grupo com caracter especial no
	 * nome do grupo utilizando o botão Salvar. RESULTADO ESPERADO: Sistema deve
	 * validar com a mensagem "* Não é permitido caracter especial."
	 */
	@Test
	public void incluirGrupoSalvarCaracterEspecialNome() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupo(caracteresp, descricao);

		incluirGrupoPage.clicarBotaoSalvarGrupo();

		String mensagem = incluirGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}

	/**
	 * Esse cenario eu tento cadastrar um novo grupo com caracter especial no
	 * nome do grupo utilizando o botão Salvar e Fechar. RESULTADO ESPERADO:
	 * Sistema deve validar com a mensagem
	 * "* Não é permitido caracter especial."
	 */
	@Test
	public void incluirGrupoSalvarEFecharCaracterEspecialNome() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupo(caracteresp, descricao);

		incluirGrupoPage.clicarBotaoSalvarEFecharGrupo();

		String mensagem = incluirGrupoPage.verificaMensagemDeObrigatoriedade();

		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}

	/**
	 * Esse cenario eu tento cadastrar um novo grupo com caracter especial na
	 * descrição do grupo utilizando o botão Salvar. RESULTADO ESPERADO: Sistema
	 * deve validar com a mensagem "* Não é permitido caracter especial."
	 */
	@Test
	public void incluirGrupoSalvarCaracterEspecialDescricao() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupo(nomeGrupo, "sadasdasd%$%¨¨¨%A$S%$ASD");

		incluirGrupoPage.clicarBotaoSalvarEFecharGrupo();

		String mensagem = incluirGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}

	/**
	 * Esse cenario eu tento cadastrar um novo grupo com caracter especial na
	 * descrição do grupo utilizando o botão Salvar e Fechar. RESULTADO
	 * ESPERADO: Sistema deve validar com a mensagem
	 * "* Não é permitido caracter especial."
	 */
	@Test
	public void incluirGrupoSalvarEFecharCaracterEspecialDescricao() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupo(nomeGrupo, "sdasdasd!@#!@#SASDsd");

		incluirGrupoPage.clicarBotaoSalvarEFecharGrupo();

		String mensagem = incluirGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals("* Não é permitido caracter especial.", mensagem);
	}

	/**
	 * Esse cenario eu tento cadastrar um novo grupo com caracter especial na
	 * descrição do grupo utilizando o botão Salvar e Fechar. RESULTADO
	 * ESPERADO: Sistema deve validar com a mensagem
	 * "* Não é permitido caracter especial."
	 */
	@Test
	public void incluirGrupoDuplicado() {

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		incluirGrupoPage.incluirGrupoBuilder(nomeGrupo, descricao);

		incluirGrupoPage.incluirGrupo(nomeGrupo, descricao);

		incluirGrupoPage.clicarBotaoSalvarEFecharGrupo();

		String mensagem = incluirUsuarioPage.verificaMensagemDeSucesso();
		Assert.assertEquals("Grupo já cadastrado !", mensagem);

	}

	@Parameters
	public static List<Object[]> datadriven() {
		return Arrays
				.asList(new Object[][] { { "norte", "descricao", "asd#$%@" }, });
	}

	@After
	public void after() {
		incluirGrupoPage.excluirGrupoPeloBanco(nomeGrupo);
		
	}

}
