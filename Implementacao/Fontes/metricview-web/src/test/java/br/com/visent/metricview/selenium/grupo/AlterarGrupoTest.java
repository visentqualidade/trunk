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
public class AlterarGrupoTest extends BaseTestCase {

	AlterarGrupoPage alterarGrupoPage;
	IncluirGrupoPage incluirGrupoPage;

	// dados
	String nomeGrupo;
	String descricao;
	String novoGrupo;
	String novaDescricao;
	String caracteresp;

	public AlterarGrupoTest(String _nomeGrupo, String _descricao,
			String _novoGrupo, String _novaDescricao, String _caracteresp) {
		this.nomeGrupo = _nomeGrupo;
		this.descricao = _descricao;
		this.novoGrupo = _novoGrupo;
		this.novaDescricao = _novaDescricao;
		this.caracteresp = _caracteresp;
	}

	@Before
	public void setUp() {
		alterarGrupoPage = new AlterarGrupoPage(driver);
		incluirGrupoPage = new IncluirGrupoPage(driver);
		incluirGrupoPage.excluirGrupoPeloBanco(nomeGrupo);
		incluirGrupoPage.excluirGrupoPeloBanco(novoGrupo);

	}

	/**
	 * Esse cenario eu faço a alteração do nome de um Grupo.
	 */
	@Test
	public void alterarNomeGrupo() {

		incluirGrupoPage.incluirGrupoBuilder(nomeGrupo, descricao);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		String idGrupo = alterarGrupoPage.buscarId(nomeGrupo);

		alterarGrupoPage.clicarBotaoAlterarGrupo(nomeGrupo, idGrupo);

		alterarGrupoPage.alterarGrupo(nomeGrupo, novoGrupo, idGrupo, descricao,
				novaDescricao);

		String mensagem = new IncluirUsuarioPage(driver)
				.verificaMensagemDeSucesso();
		Assert.assertEquals(mensagem, "Operação Realizada com Sucesso !");

		if (!alterarGrupoPage.verificarBanco(novoGrupo, novaDescricao)) {
			Assert.fail("Grupo: " + novoGrupo + " não cadastrado na base!");
		}
	}

	/**
	 * Cenario que valida cadastro do grupo com mesmo nome.
	 */
	@Test
	public void naoDevePermitirCadastrarGrupoComMesmoNome() {

		incluirGrupoPage.incluirGrupoBuilder(nomeGrupo, descricao);

		incluirGrupoPage.incluirGrupoBuilder(novoGrupo, descricao);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		String idGrupo = alterarGrupoPage.buscarId(nomeGrupo);

		alterarGrupoPage.clicarBotaoAlterarGrupo(nomeGrupo, idGrupo);

		alterarGrupoPage.alterarGrupo(nomeGrupo, novoGrupo, idGrupo, descricao,
				novaDescricao);

		String mensagem = new IncluirUsuarioPage(driver)
				.verificaMensagemDeSucesso();

		if (alterarGrupoPage.verificaQuantidadeDeGruposCadastradosNoBanco(novoGrupo) > 1) {
			Assert.fail("Sistema permitiu cadastrar dois grupos com nomes iguais!");
		}

		Assert.assertEquals(mensagem, "Grupo já cadastrado !");

	}

	/**
	 * Cenario pra obrigatoriedade do campo Descricao
	 */
	@Test
	public void obrigatoriedadeCampoDescricao() {

		incluirGrupoPage.incluirGrupoBuilder(nomeGrupo, descricao);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		String idGrupo = alterarGrupoPage.buscarId(nomeGrupo);

		alterarGrupoPage.clicarBotaoAlterarGrupo(nomeGrupo, idGrupo);

		alterarGrupoPage.alterarGrupo(nomeGrupo, novoGrupo, idGrupo, descricao, " ");

		String mensagem = alterarGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals(mensagem, "* O campo em destaque é de preenchimento obrigatório.");

		if (alterarGrupoPage.verificarBanco(novoGrupo, " ")) {
			Assert.fail("Grupo: " + novoGrupo + " não cadastrado na base!");
		}
	}

	/**
	 * Cenario que valida a obrigatoriedade do campo nome.
	 */
	@Test
	public void obrigatoriedadeCampoNome() {

		incluirGrupoPage.incluirGrupoBuilder(nomeGrupo, descricao);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		String idGrupo = alterarGrupoPage.buscarId(nomeGrupo);

		alterarGrupoPage.clicarBotaoAlterarGrupo(nomeGrupo, idGrupo);

		alterarGrupoPage.alterarGrupo(nomeGrupo, "", idGrupo, descricao, novaDescricao);

		String mensagem = alterarGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals(mensagem, "* O campo em destaque é de preenchimento obrigatório.");

		if (alterarGrupoPage.verificarBanco("", novaDescricao)) {
			Assert.fail("Grupo: " + nomeGrupo + " não cadastrado na base!");
		}
	}

	/**
	 * Cenario que valida caracteres especiais para o campo nome.
	 */
	@Test
	public void naoDevePermitirAlterarNomeComCaracterEspecial() {

		incluirGrupoPage.incluirGrupoBuilder(nomeGrupo, descricao);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		String idGrupo = alterarGrupoPage.buscarId(nomeGrupo);

		alterarGrupoPage.clicarBotaoAlterarGrupo(nomeGrupo, idGrupo);

		alterarGrupoPage.alterarGrupo(nomeGrupo, caracteresp, idGrupo, descricao,
				novaDescricao);

		String mensagem = alterarGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals(mensagem, "* Não é permitido caracter especial.");

		if (alterarGrupoPage.verificarBanco(caracteresp, novaDescricao)) {
			Assert.fail("Grupo: " + novoGrupo + " não cadastrado na base!");
		}
	}

	/**
	 * Cenario que valida caracteres especiais para o campo descricao.
	 */
	@Test
	public void naoDevePermitirAlterarDescricaoComCaracterEspecial() {

		incluirGrupoPage.incluirGrupoBuilder(nomeGrupo, descricao);

		loginPage.acessarPage();

		util.clicaNoMenuAdministracao();

		String idGrupo = alterarGrupoPage.buscarId(nomeGrupo);

		alterarGrupoPage.clicarBotaoAlterarGrupo(nomeGrupo, idGrupo);

		alterarGrupoPage.alterarGrupo(nomeGrupo, novoGrupo, idGrupo, descricao,
				caracteresp);

		String mensagem = alterarGrupoPage.verificaMensagemDeObrigatoriedade();
		Assert.assertEquals(mensagem, "* Não é permitido caracter especial.");

		if (alterarGrupoPage.verificarBanco(novoGrupo, caracteresp)) {
			Assert.fail("Grupo: " + novoGrupo + " não cadastrado na base!");
		}
	}

	@Parameters
	public static List<Object[]> datadriven() {
		return Arrays.asList(new Object[][] { { "GrupoTesteAlterar",
				"Grupo Criado para ser Alterado", "norteAlterado1",
				"descricaoAlterado1", "asd!!@@#asd" }, });
	}

	@After
	public void after() {
		incluirGrupoPage.excluirGrupoPeloBanco(nomeGrupo);
		incluirGrupoPage.excluirGrupoPeloBanco(novoGrupo);
	}
}
