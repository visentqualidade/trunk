/** 
 *  Objeto para encapsular a criacao do painel e todos os componentes que irao
 *  ficar nele.
 */
var Painel = new function() {
	
	var nomeGrupo 			= mensagens['txt_grupos'];
	var nomeUsuario 		= mensagens['txt_usuarios']; 
	var nomeSelecione       = mensagens['txt_selecione'];

	var usuarios 			= null;
	var grupos 				= null;
	
	return {
		usuarios: usuarios,
		grupos: grupos,
		nomeSelecione: nomeSelecione,
		nomeGrupo: nomeGrupo,
		nomeUsuario: nomeUsuario
	};
	
}();

$(function() {
	actionDoubleClick();
	atualizarListas();
	bindTrocarTipoPermissao();
	bindTrocarVisualizacao();
	bindAjuda();
	bindSalvarAlteracoes();
	hideButtonsManutencao();
	hideButtonsPermissao();
	trocarGrupo();
	trocaPermissaoSistema();
});

function mostrarFerramentasDisponiveis(){
		
		PermissaoUsuario.apresentarPermissoesUsuario(mensagens['constante_id_usuario_logado']);
		if ($('.trocaVisualizacao li.visualizacaoAtiva').html() == 'Painel') {
			PermissaoUsuario.apresentarFerramentas();
		}
		
}

function hideButtonsManutencao(){
	$("#salvarManutencao"  ).hide("slow");
	$("#cancelarManutencao").hide("slow");
}

function hideButtonsPermissao(){
	$("#cancelarPermissoes").hide("slow");
	$("#salvarPermissoes"  ).hide("slow");
}

function salvarAssociacaoGrupos(){
	UsuarioAjax.associarGrupo(Painel.usuarios, function(){
		Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
		Funcoes.loading('.manutencao', false);
	});
}

function bindListBoxes(selector) {
	$(selector + ' .imgenviar').unbind('click');
	$(selector + ' .imgenviar').click(function() {
		if (selector == '.manutencao'){
			
			Manutencao.associarGrupo(selector);
			$("#filtroListaUsuarios").val("");
			$("#filtroListaUsuarios").Watermark(mensagens['txt_filtrar']);
		}else{
			AtribuicaoPermissao.associarPermissoes(selector);
		}
	});
	
	$(selector + ' .imgretirar').unbind('click');
	$(selector + ' .imgretirar').click(function() {
		if (selector == '.manutencao'){
			Manutencao.desassociarGrupo(selector);
			$("#filtroListaUsuarios").val("");
			$("#filtroListaUsuarios").Watermark(mensagens['txt_filtrar']);
		}else{
			if (selector == '.permissoes'){
				AtribuicaoPermissao.desassociarPermissao(selector);
			}
		}
	});
}

/**
 * Função de acionamento do evento doubleclick
 */
function actionDoubleClick(){
	$("#selectComboUsuarios").unbind("click");
	$("#selectComboUsuarios").dblclick(function (){
		Manutencao.associarGrupo(Manutencao.selector);
	});
	
	$("#selectPermissaoUsuario").unbind("click");
	$("#selectPermissaoUsuario").dblclick(function (){
		AtribuicaoPermissao.associarPermissoes(AtribuicaoPermissao.selector);
	});
	
	$("#selectComboGruposAssociados").unbind("click");
	$("#selectComboGruposAssociados").dblclick(function (){
		Manutencao.desassociarGrupo(Manutencao.selector);
	});
	
	$("#selectPermissao").unbind("click");
	$("#selectPermissao").dblclick(function (){
		AtribuicaoPermissao.desassociarPermissao(AtribuicaoPermissao.selector);
	});
	
	$("#filtroListaUsuarios").val("");
	$("#filtroListaUsuarios").Watermark(mensagens['txt_filtrar']);
}

function atualizarListas() {
	$("#selectComboGrupos option").remove();
	$("#selectComboUsuarios option").remove();
	$("#selectTipo").val(Painel.nomeSelecione);
	$(".usuariosGrupo").empty();
	$("#filtroListaUsuarios").empty();
	$('.listaUser').empty();
	$('.userPermitido').empty();
	$("#cancelarPermissoes").hide("slow");
	$("#salvarPermissoes"  ).hide("slow");
	$("#cancelarManutencao").hide("slow");
	$("#salvarManutencao"  ).hide("slow");
	
	listarGrupos();
	bindShowPermissao();
}

function bindShowPermissao(){
	
	
	if ($('.selectUser').val() == Painel.nomeSelecione){
		$('.listaUser').empty();
		$('.userPermitido').empty();
	}
	if ($('.selectUser').val() == Painel.nomeGrupo){
		
		$('.listaUser').empty();
		$('.userPermitido').empty();
		AtribuicaoPermissao.buscarPermissao();
		AtribuicaoPermissao.permissoesFerramenta(AtribuicaoPermissao.CONS_GRUPOS);
		
	}
	if ($('.selectUser').val() == Painel.nomeUsuario){
		
		$('.listaUser').empty();
		$('.userPermitido').empty();
		AtribuicaoPermissao.buscarPermissao();
		AtribuicaoPermissao.permissoesFerramenta(AtribuicaoPermissao.CONS_USUARIOS);
		
	}

}

function trocaPermissaoSistema(){
	$('li','ul#ulMenuSistema').live('click', function(){
		bindShowPermissao();
	});
}

function listarUsuariosSemAtualizarTela() {
	UsuarioAjax.buscarTodos(function(usuarios) {
		Painel.usuarios = usuarios;
		mostrarFerramentasDisponiveis();
	});
}

function listarUsuarios() {
	UsuarioAjax.buscarTodos(function(usuarios) {
		var contem = false;
		Painel.usuarios = usuarios;
		
		$('.usuariosGrupos').delay(5000);
		
		for (var i=0; i < usuarios.length; i++) {
			contem = false;
			if (usuarios[i].grupos != null){
				for (var j=0;j<usuarios[i].grupos.length;j++){
					
					if (usuarios[i].grupos[j].nome == $("#selectComboGrupos").val()){
						$('.usuariosGrupo').append(_criarOptionUsuario(usuarios[i].nomeFormatado));
						contem = true;
					}
					
				}
				if (!contem){
					$('.listaUsuarios').append(_criarOptionUsuario(usuarios[i].nomeFormatado));
				}
			}else{
				$('.listaUsuarios').append(_criarOptionUsuario(usuarios[i].nomeFormatado));
			}
		}
		bindListBoxes('.manutencao');
		Funcoes.bindFiltrarOption('#filtroListaUsuarios', '#selectComboUsuarios', 'busca',null);
		mostrarFerramentasDisponiveis();
	});
}

function listarGruposSemAtualizarTela(){
	GrupoAjax.buscarTodos(function(grupos) {
		Painel.grupos = grupos;
	});
}

function listarGrupos() {
	GrupoAjax.buscarTodos(function(grupos) {
		Painel.grupos = grupos;
		
		for (var i=0; i < grupos.length; i++) {
			$('.selectGrupo').append(_criarOption(grupos[i].nome));
		}
		bindListBoxes('.permissoes');
		listarUsuarios();
	});
}

function trocarGrupo(){
	$("#selectComboGrupos").change(function(){
		var contem = false;
		
		$('#filtroListaUsuarios').val("Filtrar");
		$('.listaUsuarios').empty();
		$('.usuariosGrupo').empty();
		
		var usuarios = Painel.usuarios;
		for (var i=0; i < usuarios.length; i++) {
				
			if (usuarios[i].grupos != null){
				
				contem = false;
				for (var k=0; k < usuarios[i].grupos.length;k++){
					if (usuarios[i].grupos[k].nome == $("#selectComboGrupos").val()){
						$('.usuariosGrupo').append(_criarOptionUsuario(usuarios[i].nomeFormatado));
						contem = true;
					}	
				}
				
				if (!contem){
					$('.listaUsuarios').append(_criarOptionUsuario(usuarios[i].nomeFormatado));
				}
			}else{
				$('.listaUsuarios').append(_criarOptionUsuario(usuarios[i].nomeFormatado));
			}
		}
		
		Funcoes.bindFiltrarOption('#filtroListaUsuarios', '#selectComboUsuarios', 'busca', null);
	});
}

function _criarOption(nome) {
	return '<option>'+nome+'</option>';
}

function _criarOptionUsuario(nome) {
	var nomeSplit = nome.split("(");
	return '<option >'+nomeSplit[0]+' ('+nomeSplit[1]+'</option>';
}

function bindTrocarTipoPermissao() {
	$('.selectUser').change(function() {
		$('.listaUser').empty();
		$('userPermitido').empty();
		bindShowPermissao();
		bindListBoxes('.permissoes');
	});
	
	$('.selectPermissao').change(function() {
		$('.listaUser').empty();
		$('userPermitido').empty();
		bindShowPermissao();
		bindListBoxes('.permissoes');
	});
}

function bindTrocarVisualizacao() {
	
	// menu superior
	$('.trocaVisualizacao li').click(function() {
		
		if ($(this).attr('id').split('-')[1] == 'ajuda') return;
		if ($(this).hasClass('visualizacaoAtiva')) return;
		
		$('.trocaVisualizacao li').removeClass('visualizacaoAtiva');
		$(this).addClass('visualizacaoAtiva');
		
		var visualizacao = $(this).attr('id').split('-')[1];
		
		if (visualizacao == 'admin') {
			$("#visualizacao-painel").unbind("click");
			$("#visualizacao-logs").unbind("click");
			if ($("#pageFerramentas").is(':visible')) {
				$("#pageFerramentas").hide("slide", { direction: "left" }, 250, function() {
					$("#pageAdministracao").show("slide", { direction: "right" }, 250);
					bindTrocarVisualizacao();
				});
			} else {
				$("#pageLogs").hide("slide", { direction: "right" }, 250, function() {
					$("#pageAdministracao").show("slide", { direction: "left" }, 250);
					bindTrocarVisualizacao();
				});
			}
		} else if (visualizacao == 'painel') {
			$("#visualizacao-admin").unbind("click");
			$("#visualizacao-logs").unbind("click");
			var id = '#pageLogs';
			if ($('#pageAdministracao').is(':visible')) {
				id = '#pageAdministracao';
				PermissaoUsuario.apresentarFerramentas();
			}
			$(id).hide("slide", { direction: "right" }, 250, function() {
				$("#pageFerramentas").show("slide", { direction: "left" }, 250);
				bindTrocarVisualizacao();
			});
		} else {
			$("#visualizacao-painel").unbind("click");
			$("#visualizacao-admin").unbind("click");
			var id = '#pageFerramentas';
			if ($('#pageAdministracao').is(':visible')) {
				id = '#pageAdministracao';
			}
			$(id).hide("slide", { direction: "left" }, 250, function() {
				$("#pageLogs").show("slide", { direction: "right" }, 250, function() {
					$('#tabelaLogs').dataTable().fnAdjustColumnSizing();
					bindTrocarVisualizacao();
				});
			});
		}
	});
	
	// menu administracao
	$('#selecionaPage li').click(function() {
		if ($(this).hasClass('pagAtiva')) return;
		
		$('#selecionaPage li').removeClass('pagAtiva');
		
		$(this).addClass('pagAtiva');
		var visualizacao = $(this).attr('id');
		
		if (visualizacao == 'pageAdmRedes') {
			$("#pageAdmUsuarios").unbind("click");
			$("#pageUsuarios").hide("slide", { direction: "up" }, 250, function() {
				$("#pageRedes").show("slide", { direction: "down" }, 250);
				bindTrocarVisualizacao();
			});
		} else {
			$("#pageAdmRedes").unbind("click");
			$("#pageRedes").hide("slide", { direction: "down" }, 250, function() {
				$("#pageUsuarios").show("slide", { direction: "up" }, 250);
				bindTrocarVisualizacao();
			});
		}
	});
	
}

function bindAjuda() {
	
	if (!$('#ajuda').length) return;
	
	$('#ajuda').click(function() {
		Modal.iniciar("dlgAjuda" , {
			width:680 , height:400 , 
			title: mensagens['title_ajuda'],
			close: function() {
				document.getElementById("videometric").pause(); 
				document.getElementById("videometric").currentTime = 0;
			}
		});
	});
	
	$('#avisoAjuda').css('left', $('#ajuda').offset().left);
	$('#avisoAjuda').css('top', $('#ajuda').offset().top + $('#ajuda').outerHeight() + 5);
	$('#avisoAjuda').show();
	$('#avisoAjuda').animate({opacity:0}, 400, 'linear', function() {
		  $(this).animate({opacity:1}, 400, function() {
			  $('#avisoAjuda').fadeOut(8000);
		  });
	});
}

function bindSalvarAlteracoes() {
	
	$('#salvarManutencao').click(function() {
		Manutencao.salvarAssociacaoGrupo();
		hideButtonsManutencao();
	});
	
	$('#cancelarPermissoes').click(function() {
		$('.listaUser').empty();
		$('.userPermitido').empty();
		$("#selectTipo").val(Painel.nomeSelecione);
		hideButtonsPermissao();
		
	});
	
	$("#salvarPermissoes").click(function(){
		salvarPermissoes();
		hideButtonsPermissao();
	});
	
	$('#cancelarManutencao').click(function() {
		hideButtonsManutencao();
		atualizarListas();
	});
}