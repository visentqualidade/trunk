$(function() {
	$('.textoErro').hide();
	$('#btnVisualizarGrupos').click(function() {
		if(Funcoes.apresentarMensagemConfirmInserirEditarUsuarioGrupo()){
			if(confirm(mensagens['alert_configuracao_permissao_associacao'])){
				iniciarTelaVisualizarGrupo();
			}
		} else {
			iniciarTelaVisualizarGrupo();
		}
	});
});

function iniciarTelaVisualizarGrupo(){
	$('#filtroVisualizacaoGrupos').val('');
	
	$('#tabelaGrupos').empty();
	$('#tabelaGrupos').append('<tr class="cabecalho" ">'
			+ '<th>'+mensagens['txt_col_grupo']+'<span class="campoObrigatorio">*</span></th>'
			+ '<th>'+mensagens['txt_col_descricao']+'<span class="campoObrigatorio">*</span> </th>'
			+ '<th></th>'
			+ '<th></th>'
		+ '</tr>');
	for (var i=0; i < Painel.grupos.length; i++) {
		var grupo = Painel.grupos[i];
		$('#tabelaGrupos').append('<tr id="'+grupo.idGrupo+'">'
				+ '<td 		atributo="nome" 	 maxlength="50" 	tipo="semespecial"			class="oGrupo obrigatorio" 		>'+Funcoes.hideNull(grupo.nome)+		'</td>' 
				+ '<td 		atributo="descricao" maxlength="100" 	tipo="semespecial"			class="descricao obrigatorio"	>'+Funcoes.hideNull(grupo.descricao)+	'</td>'
				+ '<td 																			class="editar" 					title="'+mensagens['title_editar']+'">				 </td>'
				+ '<td 		ajax="GrupoAjax"	metodo="removerGrupo"							class="excluir" 				title="'+mensagens['title_excluir']+'">				 </td>'
			+ '</tr>');
	}
	
	Funcoes.bindVisualizar($('#tabelaGrupos .editar'), new Grupo(), atualizarDadosGrupo, 'tabelaGrupos');
	
	var botoes = new Array();
	
	botoes.push(new BotaoModal(mensagens['btn_fechar'] , function() { atualizarListas(); limparErrosGrupo(); Modal.fechar("dlgVisualizarGrupos");}));
	
	Modal.iniciar("dlgVisualizarGrupos" , {
		width:615 , height:400 , botoes:botoes, 
		title: mensagens['title_visualizar_grupo'],
		close:function(){$(".textoErro").hide();}
	});

	$('#filtroVisualizacaoGrupos').keyup(function() {
		Funcoes.filtrar($('#tabelaGrupos tr'), $(this).val());
	});
}


function limparErrosGrupo() {
	$(".textoErro").html("");
	$(".textoErro").hide();
}


function atualizarDadosGrupo (grupo){
	var isPreenchimentoValido = false;
	grupo.idGrupo = grupo.id;
	GrupoAjax.atualizarEntidade(grupo , {callback:function(){
		isPreenchimentoValido = true;
		Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
	},async:false});
	atualizarListas(); 
	
	return isPreenchimentoValido;
}