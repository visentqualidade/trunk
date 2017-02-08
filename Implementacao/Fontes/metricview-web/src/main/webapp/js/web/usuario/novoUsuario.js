$(function() {
	$('#btnAddUsuario').click(function() {
		if(Funcoes.apresentarMensagemConfirmInserirEditarUsuarioGrupo()){
			if(confirm(mensagens['alert_configuracao_permissao_associacao'])){
				iniciarTelaNovoUsuario();
			}
		} else {
			iniciarTelaNovoUsuario();
		}
	});
});

function iniciarTelaNovoUsuario(){
	Mascara.telefone($("#telefoneUsuario"));
	AutoComplete.criar("areaUsuario" , "buscarAreasUsuario" , "" , "descricao" , []);
	ComboHelper.select('regionalUsuario' , 'buscarRegionaisUsuario' , '' , 'descricao' , 'descricao' ,[], null);
	var botoes = new Array();
	botoes.push(new BotaoModal(mensagens['btn_salvar'] , function() {validarDadosSalvarNovoUsuario();}));
	botoes.push(new BotaoModal(mensagens['btn_salvar_e_fechar'] , function() {validarDadosSalvarFecharNovoUsuario();}));
	botoes.push(new BotaoModal(mensagens['btn_cancelar'] , function() {limparDadosNovoUsuario();Modal.fechar("dlgNovoUsuario");}));
	Modal.iniciar("dlgNovoUsuario" , {
		width:700 , height:350 , botoes:botoes , 
		title: mensagens['title_cadastra_usuario'],
		close:function(){limparDadosNovoUsuario();}
	});
}

function validarDadosSalvarFecharNovoUsuario (){
	if(Validacao.validarFormulario('formNovoUsuario')) {
		UsuarioAjax.inserirEntidade(criarObjetoNovoUsuario() , function(){
			Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
			atualizarListas();
			limparDadosNovoUsuario();
			Modal.fechar("dlgNovoUsuario");
		});
	}
}

function validarDadosSalvarNovoUsuario() {
	if(Validacao.validarFormulario('formNovoUsuario')) {
		UsuarioAjax.inserirEntidade(criarObjetoNovoUsuario() , function(){
			Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
			limparDadosNovoUsuario();
			atualizarListas();
		});
	}
}

function criarObjetoNovoUsuario (){
	var usuario = new Usuario();
	usuario.nome = $.trim($("#nomeUsuario").val());
	usuario.login = $.trim($("#loginUsuario").val());
	usuario.email = $.trim($("#emailUsuario").val());
	usuario.area = $.trim($("#areaUsuario").val());
	usuario.regional = $.trim($("#regionalUsuario").select2("val"));
	usuario.responsavel = $.trim($("#responsavelUsuario").val());
	usuario.telefone = $.trim($("#telefoneUsuario").val());
	if($("#adminUsuario").is(":visible")){
		usuario.admin = $("#adminUsuario").prop("checked"); 
	}
	return usuario;
}

function limparDadosNovoUsuario() {
	$("#formNovoUsuario input").each(function(){
		$(this).removeClass("ui-state-error");
		$(this).val("");
	});
	$(".textoErro").html("");
	$(".textoErro").hide();
	if($("#adminUsuario").is(":visible")){
		$("#adminUsuario").prop("checked" , false); 
	}
}
