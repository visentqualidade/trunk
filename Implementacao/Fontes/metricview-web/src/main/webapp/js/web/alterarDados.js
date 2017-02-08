$(function() {
	Mascara.telefone($('#telefoneDados'));
	AlterarDadosAjax.buscarUsuarioSessao(function(usuario){
		if(usuario.primeiroAcesso){
			desabilitarFuncaoEsc();
			$("#textoPrimeiroAcesso").show();
			popularDadosInputs(usuario);
			$("#senhaAntigaDados").addClass("obrigatorio");
			$("#senhaNovaConfirmDados").addClass("obrigatorio");
			$("#senhaNovaDados").addClass("obrigatorio");
			var botoes = new Array();
			botoes.push(new BotaoModal(mensagens['btn_salvar'] , function() {validarDadosSalvarPrimeiroAcesso();}));
			Modal.iniciar("dlgAlterarDados" , {
				width:680, height:550, botoes:botoes, 
				title: mensagens['title_alterar_dados'], 
				closeOnEscape: false,
				open : function (event , ui){$('#dlgAlterarDados').dialog({ dialogClass: 'no-close' });}
			});
		} 
	});
	$('#alterarDados').click(function() {
		AlterarDadosAjax.buscarUsuarioSessao(function(usuario){
			var botoes = new Array();
			botoes.push(new BotaoModal(mensagens['btn_salvar'] , function() {validarDadosSalvar();}));
			botoes.push(new BotaoModal(mensagens['btn_fechar'] , function() {limparAlterarDados();Modal.fecharSemDestroy("dlgAlterarDados");}));
			$("#textoPrimeiroAcesso").hide();
			popularDadosInputs(usuario);
			Modal.iniciar("dlgAlterarDados" , {
				width:700, height:550, botoes:botoes, 
				title: mensagens['title_alterar_dados'],
				open : function (event , ui){$('#dlgAlterarDados').dialog({ dialogClass: '' });},
				close: function(ev, ui) { limparAlterarDados(); }
			});
		});
	});
});

//O browser IE9 apaga as informacoes dos inputs quando se aperta 2 vezes a tecla 'esc' 
function desabilitarFuncaoEsc(){
	$(document).keydown(function (e) {
		if(e.which == 27) {
			return false;
		}
	});
}

function popularDadosInputs(usuario) {
	$("#loginDados").val(usuario.login);
	$("#nomeDados").val(usuario.nome);
	$("#emailDados").val(usuario.email);
	$("#telefoneDados").val(usuario.telefone);
	AutoComplete.criar("areaDados" , "buscarAreasUsuario" , "" , "descricao" , [usuario.area]);
	ComboHelper.select('regionalDados' , 'buscarRegionaisUsuario' , '' , 'descricao' , 'descricao' ,[usuario.regional], null);
	$("#responsavelDados").val(usuario.responsavel);
}

function limparAlterarDados (){
	$("#formAlterarDados input").each(function(){
		$(this).removeClass("ui-state-error");
		$(this).val("");
	});
	$(".textoErro").html("");
	$(".textoErro").hide();
}

function validarDadosSalvarPrimeiroAcesso (){
	if(Validacao.validarFormulario('formAlterarDados')){
		var senhaAntiga = $("#senhaAntigaDados").val();
		if(senhaAntiga != ""){
			if($("#senhaNovaConfirmDados").val() == $("#senhaNovaDados").val()){
				AlterarDadosAjax.atualizarDadosPrimeiroAcesso(criarObjetoAlterarDados() , senhaAntiga , function(){
					Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
					limparAlterarDados();
					$("#senhaAntigaDados").removeClass("obrigatorio");
					$("#senhaNovaConfirmDados").removeClass("obrigatorio");
					$("#senhaNovaDados").removeClass("obrigatorio");
					Modal.fecharSemDestroy("dlgAlterarDados");
				});
			} else {
				$("#senhaNovaDados").addClass("ui-state-error");
				$("#senhaNovaConfirmDados").addClass("ui-state-error");
				$(".textoErro").html(mensagens['mensagem_erro_senhas_incorretas']);
				$(".textoErro").show();
			}
		}
	}
}

function validarDadosSalvar (){
	isCampoSenhasPreenchidos();
	if(Validacao.validarFormulario('formAlterarDados')){
		var senhaAntiga = $("#senhaAntigaDados").val();
		if($("#senhaNovaConfirmDados").val() == $("#senhaNovaDados").val()){
			AlterarDadosAjax.atualizarDados(criarObjetoAlterarDados() , senhaAntiga , !isCampoSenhasPreenchidos() , function(){
				Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
				Modal.fecharSemDestroy("dlgAlterarDados");
			});
		} else {
			$("#senhaNovaDados").addClass("ui-state-error");
			$("#senhaNovaConfirmDados").addClass("ui-state-error");
			$(".textoErro").html(mensagens['mensagem_erro_senhas_incorretas']);
			$(".textoErro").show();
		}
	}
}

function isCampoSenhasPreenchidos(){
	var isCorreto = false;
	if(!($.trim($("#senhaAntigaDados").val()) == "" && $.trim($("#senhaNovaConfirmDados").val()) == "" && $.trim($("#senhaNovaDados").val()) == "")){
		$("#senhaAntigaDados").addClass("obrigatorio");
		$("#senhaNovaDados").addClass("obrigatorio");
		$("#senhaNovaConfirmDados").addClass("obrigatorio");
		isCorreto = false;
	} else {
		$("#senhaAntigaDados").removeClass("obrigatorio");
		$("#senhaNovaDados").removeClass("obrigatorio");
		$("#senhaNovaConfirmDados").removeClass("obrigatorio");
		isCorreto = true;
	}
	return isCorreto;
}

function criarObjetoAlterarDados (){
	var usuario = new Usuario();
	usuario.nome = $("#nomeDados").val();
	usuario.login = $("#loginDados").val();
	usuario.email = $("#emailDados").val();
	usuario.senha = $("#senhaNovaConfirmDados").val();
	usuario.telefone = $("#telefoneDados").val(); 
	usuario.area = $("#areaDados").val();
	usuario.regional = $("#regionalDados").select2("val");
	usuario.responsavel = $("#responsavelDados").val();
	return usuario;
}
