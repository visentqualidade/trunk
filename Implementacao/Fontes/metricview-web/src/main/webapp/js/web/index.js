$(function() {
	$("#inputUsuario").focus();
});

function logar() {
	if(Validacao.validarFormulario('formIndex')){
		LoginAjax.autenticarUsuario(criarObjetoUsuarioIndex() , true , function(valido) {
			if(valido == "USUARIO_JA_LOGADO"){
				if(confirm(mensagens['alerta_usuario_ja_logado'])){
					LoginAjax.autenticarUsuario(criarObjetoUsuarioIndex() , false , function(valido) {
						LoginAjax.buscarUsuarioSessao(function(usuario){
							Funcoes.redirecionarAplicacao(usuario);
						});
					});
				} else {
					limparDados();
				}
			} else {
				LoginAjax.buscarUsuarioSessao(function(usuario){
					Funcoes.redirecionarAplicacao(usuario);
				});
			}
		});
	}
	return false;
}

function criarObjetoUsuarioIndex(){
	var usuario = new Usuario();
	usuario.login = $("#inputUsuario").val();
	usuario.senha = $("#inputSenha").val();
	return usuario;
}

function limparDados (){
	$("#inputUsuario").val('');
	$("#inputSenha").val('');
}