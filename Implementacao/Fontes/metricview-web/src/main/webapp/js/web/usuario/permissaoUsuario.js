var PermissaoUsuario = function (){
	
	var visualizacao = new Array();
	
	
	/**
	 * Função responsável pela apresentação de permissões do usuário na tela.
	 */
	function apresentarPermissoesUsuario(idUsuario){
		var usuario = null;
		var ferramentas = AtribuicaoPermissao.ferramenta;
		
		// busco o usuário do sistema
		var i=0;
		while (usuario == null){
			if (Painel.usuarios[i].idUsuario == idUsuario){
				usuario = Painel.usuarios[i];
			}
			i++;
		}

		var listaFerramenta = new Array();
		
		// gero os objetos a serem exibidos na tela por ferramenta
		for (var i=0; i < ferramentas.length ; i++){
			var tool = new VisualizacaoPermissaoIndividualTO();
			var ferramenta = ferramentas[i];
			var contemAdmin = false;
			var contemFerramenta = false;
			
			tool.ferramenta = ferramenta;
			
			for (var j in usuario.permissoes){
				var permissao = usuario.permissoes[j];
				
				if (permissao.ferramenta.id == ferramenta.id){
					contemFerramenta = true;
					if (permissao.codigo.id        == AtribuicaoPermissao.CONS_ADMINISTRADOR){
						contemAdmin = true;
					}
				}
			}
			
			if (contemFerramenta){
				if (contemAdmin){
					tool.permissaoIndividual = mensagens['txt_administrador'];
					tool.permissaoPrincipal  = mensagens['txt_administrador'];
				}else{
					tool.permissaoIndividual = mensagens['txt_col_usuario'];
				}
			}else{
				tool.permissaoIndividual = mensagens['txt_nao_tem_permissao'];
			}
			
			var listaGrupos = new Array();
			for (var j in usuario.grupos){
				var grupo = usuario.grupos[j];
				
				var group = new VisualizacaoGrupoTO();
				
				contemFerramenta = false;
				contemAdmin 	 = false;
				for (var k in grupo.permissoes){
					var permissao = grupo.permissoes[k];
					
					if (permissao.ferramenta.id == ferramenta.id){
						contemFerramenta = true;
						if (permissao.codigo.id        == AtribuicaoPermissao.CONS_ADMINISTRADOR){
							contemAdmin = true;
						}
					}
				}
					
				group.nome      = grupo.nome;
				
				if (contemFerramenta){
					if (contemAdmin){
						group.permissao =  mensagens['txt_administrador'] ;
						tool.permissaoPrincipal  = mensagens['txt_administrador'];
					}else{
						group.permissao =  mensagens['txt_col_usuario'];
						if (tool.permissaoPrincipal == null){
							tool.permissaoPrincipal  = mensagens['txt_col_usuario'];
						}
					}
				}else{
					group.permissao = mensagens['txt_nao_tem_permissao'];
				}
				
				listaGrupos.push(group);
			}
			
			tool.grupos = listaGrupos;

			if (tool.permissaoPrincipal == null){
				
				if (tool.permissaoIndividual == null){
					tool.permissaoindividual = mensagens['txt_nao_tem_permissao'];
					tool.permissaoPrincipal = mensagens['txt_nao_tem_permissao'];
				}else{
					tool.permissaoPrincipal = tool.permissaoIndividual;
				}
				
			}
			
			listaFerramenta.push(tool);
			
			PermissaoUsuario.visualizacao = listaFerramenta;
		}
		
	}
	
	/**
	 * Método utilizado para printar na tela as informações de permissões do usuário
	 */
	function showVisualizacaoUsuario(){
		var concatView = '';
		
		for (var i in PermissaoUsuario.visualizacao){
			var tool = PermissaoUsuario.visualizacao[i];
			
			concatView += 	'<h3>'+tool.ferramenta.descricao+'</h3>';
			concatView += 	'<div>';
			concatView += 	'   <div class="permissaoPrincipal">';
			concatView += 	'		<p><b> '+mensagens['txt_col_permissao']+': '+tool.permissaoPrincipal+' </b></p>';
			concatView += 	'   </div>';
			concatView += 	'   <div class="outrasPermissoes">			';
			concatView += 	'  		<p>'+mensagens['txt_col_permissao_individual']+': '+tool.permissaoIndividual+'</p>';
			concatView += 	'  		<p>'+mensagens['txt_col_permissao_grupos']+':</p>';
			concatView += 	'     	<ul>';
			for (var j in tool.grupos){
				concatView += 	'  		<li><p>'+tool.grupos[j].nome+': '+tool.grupos[j].permissao+'</p></li>';
			}
			concatView += 	'  		</ul>';
			concatView += 	'   </div>';
			concatView += 	'</div>';
			
		}
		$('#accordionPermissoes').append(concatView);
		
	}
	
	
	function apresentarFerramentas(){
		
		$('#pageFerramentas').empty();
		
		var concat = '';
		
		for (var i in PermissaoUsuario.visualizacao){
			var tool = PermissaoUsuario.visualizacao[i];
			
			if (tool.permissaoPrincipal == mensagens['txt_nao_tem_permissao']){
				continue;
			}
			
			var urlFerramenta = '';
			
			if(tool.ferramenta.url.lastIndexOf("http", 0) === 0)
			{
				urlFerramenta = tool.ferramenta.url;
			}
			else
			{
				urlFerramenta = urlContextoCompleto + tool.ferramenta.url;
			}
			
			concat += '<a href="'+urlFerramenta+'?mod_aut_usuario='+mensagens['constante_nome_usuario_logado']+'&mod_aut_token='+mensagens['constante_token_sessao']+'" target="_blank">';
			concat += '<div class="window inlinebox '+(!mensagens['constante_user_e_admin'] ? mensagens['constante_user_class_no_admin'] : '')+'">';
			concat += '		<div class="TitFerramenta">';
			concat += '			<h1>'+tool.ferramenta.nome+'</h1>';
			concat += '			<p>'+tool.ferramenta.descricao+'</p>';
			concat += '         <p>'+tool.permissaoPrincipal+'</p>';
			concat += '     </div>';
			concat += '     <img src="'+contexto+'/images/'+tool.ferramenta.imagem+'" title="'+tool.ferramenta.nome+'"/>';
			concat += '</div>';
			concat += '</a>';
		}
		
		if (concat == ''){
			Funcoes.exibirMensagem(mensagens['mensagem_sem_acesso_ao_portal'],10000);
		}
		$('#pageFerramentas').append (concat);
	}
	
	
	return {
		apresentarPermissoesUsuario: apresentarPermissoesUsuario,
		visualizacao: visualizacao,
		showVisualizacaoUsuario: showVisualizacaoUsuario,
		apresentarFerramentas: apresentarFerramentas
	};
}();