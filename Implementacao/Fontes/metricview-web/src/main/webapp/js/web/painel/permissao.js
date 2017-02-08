/**
 *  Objeto para encapsular a logica da associacao de permissao do grupo ou usuario aos determinados sistemas
 */
var AtribuicaoPermissao = function(){
	
	// CONSTANTES
	var CONS_USUARIOS = "usuarios";
	var CONS_GRUPOS   = "grupos";
	var CONS_ADMINISTRADOR = 1;

	var ferramenta = new Array();
	
	
	var usuarios = null;
	var selector = '.permissoes';
	
	
	
	// funções
	
	/**
	 * Método responsável pela vinculaão de permissão a um usuário
	 * recebe como parâmetro, descrição do usuário movido em tela, a ferramenta selecionada, a permissão concedida.
	 */
	function vincularPermissaoUsuario(descricaoUsuario, ferramenta, permissao){
		
		// busco o grupo que foi manipulado
		var newUsuario = null;
		
		for (var i=0; i < Painel.usuarios.length ; i++){
			if (Painel.usuarios[i].nomeFormatado == descricaoUsuario){
				newUsuario = Painel.usuarios[i];
			}
		}
		
		// realiza a composição do grupo dentro de permissão
		permissao.usuario = newUsuario;
		
		// realiza a composição do grupo dentro de permissão
		if (newUsuario.permissoes.length != null){
			for (var i=0; i < newUsuario.permissoes.length ; i++){
				var permission = newUsuario.permissoes[i];
				
				if (permission.ferramenta.id == permissao.ferramenta.id &&
					permission.codigo.id        == permissao.codigo.id  &&
					permission.usuario.idUsuario    == permissao.usuario.idUsuario){
					permissao.id = permission.id;
				}
			}	
		}
		
		// adiciono a permissão a lista e finalizo.
		PainelPermissao.permissoes.push(permissao);
		
	}
	
	/**
	 * Método responsável pela vinculaão de permissão a um grupo
	 * recebe como parâmetro, descrição do grupo movido em tela, a ferramenta selecionada, a permissão concedida.
	 */
	function vincularPermissaoGrupo(descricaoGrupo, ferramenta, permissao){
		
		// busco o grupo que foi manipulado
		var newGrupo = new Grupo();
		
		for (var i=0; i < Painel.grupos.length ; i++){
			if (Painel.grupos[i].nome == descricaoGrupo){
				newGrupo = Painel.grupos[i];
			}
		}
		
		permissao.grupo = newGrupo;
		
		// realiza a composição do grupo dentro de permissão
		if (newGrupo.permissoes.length != null){
			for (var i=0; i < newGrupo.permissoes.length ; i++){
				var permission = newGrupo.permissoes[i];
				
				if (permission.ferramenta.id == permissao.ferramenta.id &&
					permission.codigo.id     == permissao.codigo.id     &&
					permission.grupo.idGrupo      == permissao.grupo.idGrupo){
					permissao.id = permission.id;
				}
			}	
		}
		
		// adiciono a permissão a lista e finalizo.
		PainelPermissao.permissoes.push(permissao);
		
	}
	
	/**
	 * Método acionado e responsável por acionar os métodos de vinculação de permissão.
	 * recebe como parâmetro, descrição do usuário / grupo movido em tela
	 */
	function associarPermissao(descricaoOptionSelect){
	
		$("#cancelarPermissoes").show("slow");
		$("#salvarPermissoes"  ).show("slow");
		
		var permissao = new Permissao();
		permissao.codigo = new TipoAcesso();
		permissao.codigo.id = $("#comboSelectPermissao").val();
		var ferramenta = new Ferramenta();
		ferramenta.id = $('.permissoes ul li.ativo').attr("id");
		permissao.ferramenta = ferramenta;
		permissao.codigo.id = $('#comboSelectPermissao').val();
		if($("#selectTipo").val() == mensagens['txt_grupos']){
			
			vincularPermissaoGrupo(descricaoOptionSelect, ferramenta, permissao);
			
		} else if($("#selectTipo").val() == mensagens['txt_usuarios']){
			
			vincularPermissaoUsuario(descricaoOptionSelect, ferramenta, permissao);
			
		}
		return permissao;
	}
	
	/**
	 * Método utilizado pela imagem de controle de associação de permissão, monitora o evento de grant de permissão.
	 * 
	 */
	function associarPermissoes(){
		$("#cancelarPermissoes").show("slow");
		$("#salvarPermissoes"  ).show("slow");
		
		$(selector+' .listboxFrom option:selected').each(function() {
			AtribuicaoPermissao.associarPermissao($(this).val()); 
			$(this).appendTo($(this).closest(selector).find('.listboxTo')); 
		});
	}
	
	/**
	 * Método utilizado pela imagem de controle de desassociação de permissão, monitora o evento de grant de permissão.
	 * 
	 */
	function desassociarPermissao(){
		$("#cancelarPermissoes").show("slow");
		$("#salvarPermissoes"  ).show("slow");
		
		$(selector+' .listboxTo option:selected').each(function() {
			removerPermissao($(this).val());
			$(this).appendTo($(this).closest(selector).find('.listboxFrom')); 
		});
	}
	
	
	/**
	 * Método responsável pelo "switch", tomar decisão de qual método será utilizado para 
	 * preencher os atributos que contem as informações da tela.
	 * Parametro: Constante indicando tipo de ação que o sistema deve fazer.
	 */
	function permissoesFerramenta(tipo){
		
		switch (tipo){
		
			case AtribuicaoPermissao.CONS_USUARIOS:
				
				AtribuicaoPermissao.bindPermissaoUsuario();
				
				break;
			case AtribuicaoPermissao.CONS_GRUPOS:
				
				
				AtribuicaoPermissao.bindPermissaoGrupo();
				
				break;
			default:
				break;
		}
	}
	
	/**
	 * Método responsável pela apresentação do usuário na tela, selecionando o "box" certo de acordo com suas permissões
	 */
	function bindPermissaoUsuario(){
		
		var idFerramenta = $('.ativo').attr("id");
		var tipoPermissao = $("#comboSelectPermissao").val();
		
		   
		// corro todos os usuarios para montar a tela
		for (var i=0; i < Painel.usuarios.length ; i ++){
			
			var usuario = Painel.usuarios[i];
			
			var contem = false;
			
			for (var j = 0; j < PainelPermissao.permissoes.length ; j++){
				var permissao = PainelPermissao.permissoes[j];
				
				if (permissao.usuario != null){
					if (permissao.ferramenta.id == idFerramenta &&
						permissao.usuario.idUsuario    == usuario.idUsuario     &&
						permissao.codigo.id     == parseInt(tipoPermissao))	{
						
						$('.userPermitido').append(_criarOption(usuario.nomeFormatado));
						contem = true;
					}
				}
			}
			
			if (!contem){
				$('.listaUser').append(_criarOption(usuario.nomeFormatado));
			}
			
		}

	}
	
	
	/**
	 * Método responsável pela apresentação do grupo na tela, selecionando o "box" certo de acordo com suas permissões
	 */
	function bindPermissaoGrupo(){
		
		var idFerramenta = $('.ativo').attr("id");
		var tipoPermissao = $("#comboSelectPermissao").val();
		
		   
		// corro todos os grupos para montar a tela
		for (var i=0; i < Painel.grupos.length ; i ++){
			
			var grupo = Painel.grupos[i];
			
			var contem = false;
			
			
			for (var j = 0; j < PainelPermissao.permissoes.length ; j++){
				var permissao = PainelPermissao.permissoes[j];
				
				if (permissao.grupo != null){
					if (permissao.ferramenta.id == idFerramenta &&
						permissao.grupo.idGrupo      == grupo.idGrupo     &&
						permissao.codigo.id     == tipoPermissao){
						$('.userPermitido').append(_criarOption(grupo.nome));
						contem = true;
					}
				}
			}
			
			if (!contem){
				$('.listaUser').append(_criarOption(grupo.nome));
			}
			
		}

	}
	
	/**
	 * Método responsável pela remoção de permissão de um grupo.
	 * Parâmetro: nome do grupo que teve a permissão removida, ferramenta a ser removida a permissão.
	 */
	function removerPermissaoGrupo(descricaoGrupo, ferramenta){
		
		var tipoPermissao = $("#comboSelectPermissao").val();
		
		var grupo = null;
		
		for (var i = 0 ; i < Painel.grupos.length ; i++){
			
			if (Painel.grupos[i].nome == descricaoGrupo){
				grupo = Painel.grupos[i];
			}
		}
		
		var permissao = new Permissao();
		permissao.codigo = new TipoAcesso();
		
		permissao.codigo.id  = tipoPermissao;
		permissao.ferramenta = ferramenta;
		permissao.grupo      = grupo;
		
		
		var permissoes = PainelPermissao.permissoes;
		// percorro as permissões de grupo para remover ela
		for (var i=0; i < permissoes.length ; i++){
			
			if (permissoes[i].grupo == null){
				continue;
			}
			
			var permision = permissoes[i];
			
			if (permision.ferramenta.id == permissao.ferramenta.id &&
				permision.codigo.id     == permissao.codigo.id     &&
				permision.grupo.idGrupo      == permissao.grupo.idGrupo){
				
				PainelPermissao.permissoes.splice(i,1);
				
			}
			
			
		}
		
	}
	
	
	/**
	 * Método responsável pela remoção de permissão de um usuário.
	 * Parâmetro: nome do usuário que teve a permissão removida, ferramenta a ser removida a permissão.
	 */
	function removerPermissaoUsuario(descricaoUsuario, ferramenta){
		var tipoPermissao = $("#comboSelectPermissao").val();
		var usuario = null;
		
		for (var i = 0 ; i < Painel.usuarios.length ; i++){
			
			if (Painel.usuarios[i].nomeFormatado == descricaoUsuario){
				usuario = Painel.usuarios[i];
			}
		}
		
		var permissao = new Permissao();
		permissao.codigo = new TipoAcesso();
		
		permissao.codigo.id  = tipoPermissao;
		permissao.ferramenta = ferramenta;
		permissao.usuario    = usuario;
		
		
		var permissoes = PainelPermissao.permissoes;
		// percorro as permissões de grupo para remover ela
		for (var i=0; i < permissoes.length ; i++){
			
			if (permissoes[i].usuario == null){
				continue;
			}
			
			var permision = permissoes[i];
			
			if (permision.ferramenta.id 			== permissao.ferramenta.id &&
				permision.codigo.id        			== permissao.codigo.id     &&
				permision.usuario.idUsuario    			== permissao.usuario.idUsuario){
				
				PainelPermissao.permissoes.splice(i,1);
				
			}
			
			
		}
	}
	
	/**
	 * Método responsável pela busca de permissão entre os atributos que existem já carregados na tela.
	 * Serve de apoio para os métodos de preenchimento de permissões.
	 */
	function buscarPermissao(){
		
		if ((!$('#salvarPermissoes').is(':visible'))){
		
			PainelPermissao.permissoes = new Array();
			
			if (Painel.grupos.length != 0){
				for (var i=0; i < Painel.grupos.length ; i++){
					
					var grupo = Painel.grupos[i];
					
					for (var j =0 ; j< grupo.permissoes.length ; j++){
						
						var permissao = grupo.permissoes[j];
						
						PainelPermissao.permissoes.push(permissao);
						
					}
					
				}
			}

			if (Painel.usuarios.length != 0){
				for (var i=0; i < Painel.usuarios.length ; i++){
					
					var usuario = Painel.usuarios[i];
					
					for (var j =0 ; j< usuario.permissoes.length ; j++){
						
						var permissao = usuario.permissoes[j];
						
						PainelPermissao.permissoes.push(permissao);
						
					}
					
				}
			}
		}
		
	}

	
	return {
		buscarPermissao: buscarPermissao, 
		associarPermissoes: associarPermissoes,
		desassociarPermissao: desassociarPermissao,
		ferramenta: ferramenta,
		usuarios: usuarios,
		permissoesFerramenta: permissoesFerramenta,
		CONS_USUARIOS: CONS_USUARIOS,
		CONS_GRUPOS: CONS_GRUPOS,
		CONS_ADMINISTRADOR: CONS_ADMINISTRADOR,
		bindPermissaoGrupo: bindPermissaoGrupo, 
		bindPermissaoUsuario: bindPermissaoUsuario, 
		associarPermissao: associarPermissao,
		removerPermissaoGrupo: removerPermissaoGrupo, 
		removerPermissaoUsuario: removerPermissaoUsuario,
		selector: selector
	};
}();


var PainelPermissao = function (){
	
	var permissoes = new Array();
	
	return {
		permissoes: permissoes
	};
	
}();


/**
 * Método utilizado para fazer o switch de tipo de remoção de permissão.
 * @param descricaoOptionSelect
 */
function removerPermissao(descricaoOptionSelect){
	
	$("#cancelarPermissoes").show("slow");
	$("#salvarPermissoes"  ).show("slow");
	
	var ferramenta = new Ferramenta();
	ferramenta.id = $('.permissoes ul li.ativo').attr("id");
	
	if($("#selectTipo").val() == mensagens['txt_grupos']){
		
		AtribuicaoPermissao.removerPermissaoGrupo(descricaoOptionSelect, ferramenta);
		
	} else if($("#selectTipo").val() == mensagens['txt_usuarios']){
		
		AtribuicaoPermissao.removerPermissaoUsuario(descricaoOptionSelect, ferramenta);
		
	}
}
 
$(function(){
	ComboHelper.selectSemSelect2('comboSelectPermissao' , 'buscarPermissoes' , '' , 'valor' , 'descricao' ,[], null);
	montarMenuSistemas();
	hideButtonsPermissao();;
});


/**
 * Método utilizado por salvar as permissões manipuladas em tela.
 */
function salvarPermissoes(){
		Funcoes.loading('.permissoes', true);
		
		FerramentaAjax.salvarFerramenta(PainelPermissao.permissoes, function (){
			Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
			listarGruposSemAtualizarTela();
			listarUsuariosSemAtualizarTela();
			Funcoes.loading('.permissoes', false);
		});
		
		
		
}

/**
 * Funcao para montar as opcoes de sistemas
 */
function montarMenuSistemas (){
	
	$('#ulMenuSistema li').remove();
	
	AtribuicaoPermissao.ferramenta.length = 0;
	FerramentaAjax.buscarTodos(function(result){
		var concat = "";
		for(var i in result){
			AtribuicaoPermissao.ferramenta.push(result[i]);
			if(i == 0){
				concat += '<li class="ativo" id="'+result[i].id+'">'+result[i].nome+'</li>';
			} else {
				concat += '<li id="'+result[i].id+'">'+result[i].nome+'</li>';
			}
			
		}
		$("#ulMenuSistema").append(concat);
		
		//Aqui faz a troca entres as abas do sistemas disponivies
		$('.permissoes ul li').click(function() {
			$('.permissoes ul li').removeClass('ativo');
			$(this).addClass('ativo');
		});
		
	});	
}