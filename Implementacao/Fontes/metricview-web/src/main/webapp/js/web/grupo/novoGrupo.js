$(function() {
	$('#btnAddGrupo').click(function() {
		if(Funcoes.apresentarMensagemConfirmInserirEditarUsuarioGrupo()){
			if(confirm(mensagens['alert_configuracao_permissao_associacao'])){
				iniciarTelaNovoGrupo();
			}
		} else {
			iniciarTelaNovoGrupo();
		}
	});
});

function iniciarTelaNovoGrupo(){
	var botoes = new Array();
	botoes.push(new BotaoModal(mensagens['btn_salvar'] , function() {validarDadosSalvarNovoGrupo();}));
	botoes.push(new BotaoModal(mensagens['btn_salvar_e_fechar'] , function() {validarDadosSalvarFecharNovoGrupo();}));
	botoes.push(new BotaoModal(mensagens['btn_cancelar'] , function() {limparDadosNovoGrupo(); Modal.fechar("dlgNovoGrupo");}));
	Modal.iniciar("dlgNovoGrupo" , {
		width:340 , height:375 , botoes:botoes , 
		title: mensagens['title_cadastra_grupo'],
		close:function(){limparDadosNovoGrupo();}
	});
}

function validarDadosSalvarFecharNovoGrupo (){
	if(Validacao.validarFormulario('formNovoGrupo')) {
		GrupoAjax.inserirEntidade(criarObjetoNovoGrupo() , function(){
			Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
			limparDadosNovoGrupo();
			atualizarListas();
			Modal.fechar("dlgNovoGrupo");
		});
	}
}

function validarDadosSalvarNovoGrupo() {
	if(Validacao.validarFormulario('formNovoGrupo')) {
		GrupoAjax.inserirEntidade(criarObjetoNovoGrupo() , function(){
			Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
			limparDadosNovoGrupo();
			atualizarListas();
		});
	}
}


function criarObjetoNovoGrupo (){
	var grupo = new Grupo();
	
	grupo.nome = $.trim($("#nomeGrupo").val());
	grupo.descricao = $.trim($("#descricaoGrupo").val());
	
	return grupo;
}

function limparDadosNovoGrupo() {
	$("#formNovoGrupo input").each(function(){
		$(this).removeClass("ui-state-error");
		$(this).val("");
	});
	$("#formNovoGrupo textArea").each(function(){
		$(this).removeClass("ui-state-error");
		$(this).val("");
	});
	$(".textoErro").html("");
	$(".textoErro").hide();
}