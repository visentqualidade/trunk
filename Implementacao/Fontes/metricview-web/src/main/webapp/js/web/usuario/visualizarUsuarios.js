$(function() {
	$('.textoErro').hide();
	$('#btnVisualizarUsuarios').unbind("click");
	$('#btnVisualizarUsuarios').click(function() {
		if(Funcoes.apresentarMensagemConfirmInserirEditarUsuarioGrupo()){
			if(confirm(mensagens['alert_configuracao_permissao_associacao'])){
				montarTabelaDadosUsuario(false);
				eventoSomenteLogados();
			}
		} else {
			montarTabelaDadosUsuario(false);
			eventoSomenteLogados();
		}
	});
});

function montarTabelaDadosUsuario (somenteConectado){
	UsuarioAjax.buscarUsuarioComUsuariosLogados(function(usuariosTO){
		$('#filtroVisualizacaoUsuarios').val('');
		
		$('#tabelaUsuarios').empty();
		$('#tabelaUsuarios').append('<tr class="cabecalho">'
				+ '<th>'+mensagens['txt_col_login']+'</th>'
				+ '<th>'+mensagens['txt_col_nome']+'<span class="campoObrigatorio">*</span></th>'
				+ '<th>'+mensagens['txt_col_email']+'<span class="campoObrigatorio">*</span></th>'
				+ '<th>'+mensagens['txt_col_telefone']+'</th>'
				+ '<th>'+mensagens['txt_col_area']+'</th>'
				+ '<th>'+mensagens['txt_col_regional']+'</th>'
				+ '<th>'+mensagens['txt_col_responsavel']+'</th>'
				+ '<th></th>'
				+ '<th></th>'
				+ '<th></th>'
				+ '<th></th>'
				+ '<th></th>'
				+ '</tr>');
		for (var i=0; i < usuariosTO.length; i++) {
			var to = usuariosTO[i];
			var concat = '<tr id="'+to.usuario.idUsuario+'">';
			if(somenteConectado){
				if(to.isConectado){
					concat += montarColunasTabelaUsuarios(to);
				}
			} else {
				concat += montarColunasTabelaUsuarios(to);
			}
			$('#tabelaUsuarios').append(concat);
		}
		
		Funcoes.bindVisualizar($('#tabelaUsuarios .editar') , new Usuario(), atualizarDadosUsuario , 'tabelaUsuarios');
		
		var botoes = new Array();
		botoes.push(new BotaoModal(mensagens['btn_fechar'] , function() {$(".textoErro").hide();Modal.fechar('dlgVisualizarUsuarios');}));
		Modal.iniciar("dlgVisualizarUsuarios" , {
			width:987 , height:400 , botoes:botoes,  
			title: mensagens['title_visualizar_usuario'],
			close:function(){limparDadosVisualizarUsuario(); atualizarListas();}
		});
		$('#filtroVisualizacaoUsuarios').keyup(function() {
			Funcoes.filtrar($('#tabelaUsuarios tr'), $(this).val());
		});
	});
}

function limparDadosVisualizarUsuario() {
	$("#checkSomenteConectado").prop("checked" , false);
	$(".textoErro").hide();
}


function montarColunasTabelaUsuarios(to){
	var concat = '';
	concat += '<td 					  atributo="login" 	       				  class="login naoeditavel obrigatorio">'+Funcoes.hideNull(to.usuario.login)+'</td>'; 
	concat += '<td tipo="semespecial" atributo="nome" 	       maxlength="50" class="nome obrigatorio">'+Funcoes.hideNull(to.usuario.nome)+'</td>';
	concat += '<td tipo="email"	      atributo="email"         maxlength="60" class="email obrigatorio">'+Funcoes.hideNull(to.usuario.email)+'</td>';
	concat += '<td tipo="telefone"    atributo="telefone"      maxlength="25" class="telefone">'+Funcoes.hideNull(to.usuario.telefone)+'</td>';
	concat += '<td tipo="semespecial" atributo="area"		   maxlength="50" class="area autocomplete" metodo="buscarAreasUsuario" value="descricao">'+Funcoes.hideNull(to.usuario.area)+'</td>';
	concat += '<td 					  atributo="regional"	   maxlength="50" class="regional select" metodo="buscarRegionaisUsuario" value="descricao" description="descricao">'+Funcoes.hideNull(to.usuario.regional)+'</td>';
	concat += '<td tipo="semespecial" atributo="responsavel"   maxlength="50" class="responsavel">'+Funcoes.hideNull(to.usuario.responsavel)+'</td>';
	if(to.isConectado){
		concat += '<td ajax="UsuarioAjax" metodo="desconectarUsuario" class="desconectar naoeditavel" title="'+mensagens['title_desconectar_usuario']+'"></td>';
	} else{
		concat += '<td ajax="UsuarioAjax" metodo="desconectarUsuario" class="desconectarSemIcone naoeditavel"></td>';
	}
	concat += '<td class="editar" title="'+mensagens['title_editar']+'"></td>';
	concat += '<td ajax="UsuarioAjax" metodo="resetarSenha" class="resetarSenha naoeditavel" title="'+mensagens['title_resetar_senha']+'"></td>';
	concat += '<td ajax="UsuarioAjax" metodo="removerUsuario" class="excluir" title="'+mensagens['title_excluir']+'"></td>';
	concat += '<td class="naoeditavel exibirPermissoes" title="Permissões"></td>';
	concat += '</tr>';
	return concat;
}

function eventoSomenteLogados (){
	$("#checkSomenteConectado").click(function(){
		montarTabelaDadosUsuario($("#checkSomenteConectado").prop("checked"));
	});
}

function atualizarDadosUsuario (usuario){
	var isPreenchimentoValido = false;
	usuario.idUsuario = usuario.id;
	UsuarioAjax.atualizarEntidadeSemAdmin(usuario , {callback:function(){
		isPreenchimentoValido = true;
		Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
	},async:false});
	return isPreenchimentoValido;
}