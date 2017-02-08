//Variavel para criar id's diferentes para cada regional
qtdRegionaisAbertas = 0;
//Variavel para criar id's diferentes para cada area
qtdAreaAbertas = 0;
$(function() {
	$('.filtro').Watermark('Filtrar');
	Funcoes.bindMenuUser();
	Funcoes.bindLogout();
});

var Funcoes = function() {
	
	function apresentarMensagemConfirmInserirEditarUsuarioGrupo(){
		return $("#cancelarManutencao").is(":visible") || $("#salvarManutencao").is(":visible") || $("#cancelarPermissoes").is(":visible") || $("#salvarPermissoes").is(":visible");
	}

	/**
	 * Funcao para apresentar uma mensagem Parametros: mensagem -> Mensagem para
	 * ser apresentada
	 */
	function exibirMensagem(mensagem, life) {
		$.jGrowl(mensagem, {life : life });
	}

	function bindMenuUser() {
		$('.menuUser').click(function() {
			$('.subMenuUser').css('left', $('#imgArrow').offset().left);
			$('.subMenuUser').css('top', $('#imgArrow').offset().top + $('#imgArrow').outerHeight());
			if (!$('.subMenuUser').is(':visible')) {
				$('.subMenuUser').slideDown('fast', function() {
					$('#imgArrow').attr('src',contexto+ '/images/arrow-up.png');
				});
			} else {
				$('.subMenuUser').slideUp('fast',function() {
					$('#imgArrow').attr('src', contexto + '/images/arrow.png');
				});
			}
		});
		$('#content').click(function() {
			if ($('.subMenuUser').is(':visible')) {
				$('.subMenuUser').slideUp('fast', function() {
					$('#imgArrow').attr('src', contexto + '/images/arrow.png');
				});
			}
		});
	}

	function bindLogout() {
		$('#logout').click(function() {
			LoginAjax.logout(function() {
				window.location.reload(true);
			});
		});
	}

	/**
	 * Recebe as trs que serao filtradas e o valor digitado vindo do keyup
	 * 
	 * @param trs
	 * @param valor
	 */
	function filtrar(trs, valor) {

		if (valor != '') {
			valor = valor.toLowerCase();
			trs.each(function() {
				var tr = $(this);
				var tds = tr.find('td');
				for ( var i = 0; i < tds.length; i++) {
					var td = tds[i];
					if (td.innerHTML.toLowerCase().indexOf(valor) != -1) {
						tr.show();
						break;
					} else {
						tr.hide();
					}
				}
			});
		} else {
			trs.show();
		}
		
		var qtd = $(trs[0]).find('th').length;
		var trsVisiveis = trs.closest('table').find('tr:visible').not('.cabecalho');
		if (trsVisiveis.length == 0) {
			trs.closest('table').find('.nenhumRegistro').remove();
			trs.closest('table').append('<tr class="nenhumRegistro"><td colspan="'+qtd+'">Nenhum registro encontrado.</td></tr>');
		} else {
			trs.closest('table').find('.nenhumRegistro').remove();
		}

	}

	/**
	 * Recebe o selector do filtro e o selector do select para filtrar
	 * 
	 * @param filtro
	 * @param select
	 * @param acao - Este parametro irá realizar:
	 * 				 busca - Remove os anteriores e sobrescreve tudo.
	 * 				 assoc - Remove o elemento que foi associado.
	 * 				 desas - Insere o elemento que foi removido.
	 */
	function bindFiltrarOption(filtro, select, acao, elemento) {

		$(filtro).unbind('keyup');
		
		switch(acao){
			case 'busca':
				$(select).removeData('valores');
				$(select).data('valores', $(select + ' option'));
				
				break;
			case 'assoc':
				
				var opcoes = $(select).data('valores');
				var difference = new Array();
				var opcoesSelecionadas = elemento;
				$(select).removeData('valores');


				jQuery.grep(opcoes, function(el) {

				    if (jQuery.inArray(el, opcoesSelecionadas) == -1) difference.push(el);


				});
				
				$(select).append(difference);
				$(select).data('valores',$(select + ' option:not(:selected)'));
				
				break;
			case 'desas':
				
				var options = $(select).data('valores');
				$(select).removeData('valores');
				var optionsFiltrados = new Array();
				
				options.each(function() {
					var option = $(this);
					optionsFiltrados.push('<option>'+option.text()+'</option>');
				});
				
				optionsFiltrados.push('<option>'+elemento+'</option>');
				
				$(select + ' option').remove();				
				$(select).append(optionsFiltrados);
				$(select).data('valores',$(select + ' option'));
				
				break;
		}

		$(filtro).keyup(function() {
			var valor = $(filtro).val().toLowerCase();
			if (valor != '') {
				var options = $(select).data('valores');
				var optionsFiltrados = new Array();
				options.each(function() {
					var option = $(this);
					if (option.text().toLowerCase().indexOf(valor) != -1) {
						optionsFiltrados.push(option);
					}
				});
				$(select + ' option').remove();
				$(select).append(optionsFiltrados);
			} else {
				$(select + ' option').remove();
				$(select).append($(select).data('valores'));
			}
		});
	}

	/**
	 * Funcao para criar os eventos na visualizacao dos dados
	 * OBS.: Essa funcao atende as seguintes requisitos (Quaisquer alteracoes de funcionalidade documenta-las;
	 * 		1.	Funcao de edicao dos dados
	 * 		2. 	Funcao de remocao do dados
	 * 		3. 	Funcao de resetar senha do usuario
	 * 		4. 	Funcao de Kikar o usuario logado
	 * 		5.  Exibir as permissoes do usuario
	 * @param selector selector feito do jquery que deve referenciar a(s) td(s) * ".editar"
	 * @param obj objeto para ser populado quando for editar os ados
	 * @param funcaoValidacao Funcao responsavel por validar os dandos antes de salvar as alteracoes
	 * @param idTabela id da tabela de visulizacao
	 * 
	 */
	function bindVisualizar(selector, obj, funcaoValidacao, idTabela) {
		selector.unbind("click");
		selector.click(function() {
			eventoClickHabilitarEdicao($(this) , obj , funcaoValidacao);
			eventoClickConfirmaEdicao($(this) , obj , funcaoValidacao , idTabela);
		});
		$('#'+idTabela +" .resetarSenha").unbind('click');
		$('#'+idTabela +" .resetarSenha").click(function() {
			eventoClickResetarSenha($(this));
		});
		$('#'+idTabela +" .excluir").unbind('click');
		$('#'+idTabela +" .excluir").click(function() {
			eventoClickExcluir(idTabela , $(this));
		});
		$('#'+idTabela +" .desconectar").unbind('click');
		$('#'+idTabela +" .desconectar").click(function() {
			eventoClickDesconectar($(this) , idTabela );
		});
		$('#'+idTabela +" .exibirPermissoes").unbind('click');
		$('#'+idTabela +" .exibirPermissoes").click(function() {
			eventoClickExibirPermissoes($(this));
		});
	}
	
	/**
	 * Funcao para mostrar as informacoes das permissoes dos usuarios
	 * @param campoTd Td que foi clicada
	 */
	function eventoClickExibirPermissoes(campoTd) {
		PermissaoUsuario.apresentarPermissoesUsuario(campoTd.closest("tr").attr("id"));
		PermissaoUsuario.showVisualizacaoUsuario();
		Funcoes.iniciarModalVisualizacaoPermissao();
		Accordion.criar('accordionPermissoes', {});
		$('#dlgExibirPermissoes').dialog('open');
	}
	
	/**
	 *  Funcao para apresentar os dados das permissoes de usuario e grupo para cada sistema
	 */
	function iniciarModalVisualizacaoPermissao(){
		var botoes = new Array();
		botoes.push(new BotaoModal(mensagens['btn_fechar'], function() { Modal.fecharSemDestroy('dlgExibirPermissoes');}));
		Modal.iniciarSemAbrir("dlgExibirPermissoes" , {
			width:538, 
			height:360, 
			autoOpen: false,
			botoes: botoes,  
			title: mensagens['title_permissoes_modal'],
			close:function(){
				limparDadosModalVisualizarPermissao();
			}
		});
	}

	/**
	 * Funcao para limpar os dados da dialog de permissao
	 */
	function limparDadosModalVisualizarPermissao(){
		Accordion.destroy("accordionPermissoes");
		Modal.fecharSemDestroy('dlgExibirPermissoes');
		$('#accordionPermissoes').empty();
	}

	
	/**
	 * Funcao para criar o evento de desconexao
	 * @param campoTd Td que foi clicada
	 * @param idTabela id da tabela de visulizacao
	 */
	function eventoClickDesconectar (campoTd , idTabela){
		var ajax = campoTd.attr("ajax");
		var metodo = campoTd.attr("metodo");
		var id = campoTd.closest('tr').attr("id");
		if(confirm(mensagens['alerta_desconectar_usuario'])){
			window[ajax][metodo](id , function(){
				if($("#checkSomenteConectado").prop("checked")){
					$('#'+idTabela).find('[id="'+id+'"]').remove();
				} else {
					if(campoTd.hasClass("desconectar")){
						campoTd.removeClass("desconectar");
						campoTd.addClass("desconectarSemIcone");
					}
				}
				Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
			});
		}	
	}
	
	/**
	 * Funcao para criar o evento de excluir
	 * @param campoTd Td que foi clicada
	 * @param idTabela id da tabela de visulizacao
	 */
	function eventoClickExcluir (idTabela , campoTd){
		var ajax = campoTd.attr("ajax");
		var metodo = campoTd.attr("metodo");
		var id = campoTd.closest('tr').attr("id");
		if(confirm(mensagens['alerta_remover_entidade'])){
			window[ajax][metodo](id , function(){
				atualizarListas();
				$('#'+idTabela).find('[id="'+id+'"]').remove();
				Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
			});
		}
	}
	
	/**
	 * Funcao para criar o evento de resetar a senha do usuario
	 * @param campoTd Td que foi clicada
	 */
	function eventoClickResetarSenha (campoTd){
		var ajax = campoTd.attr("ajax");
		var metodo = campoTd.attr("metodo");
		var id = campoTd.closest('tr').attr("id");
		if(confirm(mensagens['alerta_resetar_senha'])){
			window[ajax][metodo](id , function(){
				Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
			});
		}
	}
	
	/**
	 * Funcao para criar o evento de habilitar a edicao dos campos
	 * OBS.: Segue explicacao abaixo para utilizacao do componente
	 * 	Parametros para criar o select
	 * 		 @param classeAjax Classe para Ajax para buscar os dados, caso nao seja passado nada, ira utilizar a classe default de combos
	 * 		 @param metodo Metodo da classe para buscar os dados
	 * 		 @param param Caso seja necessario passar algum parametro para o metodo 
	 * 		 @param value Valor que sera utilizado na cambo
	 * 		 @param description Descricao que ira aparecer para o usuario
	 * 		 @param default Descricao do texto caso nao esteja nada preenchido
	 * 	Parametros para criar o autocomplete
	 * 		 @param classeAjax Classe para Ajax para buscar os dados, caso nao seja passado nada, ira utilizar a classe default de combos
	 * 		 @param metodo Metodo da classe para buscar os dados
	 * 		 @param param Caso seja necessario passar algum parametro para o metodo 
	 * 		 @param value Valor que sera utilizado na cambo
	 * @param campoTd Td que foi clicada
	 * @param obj objeto para ser populado
	 * @param funcaoValidacao funcao que sera responsavel por fazer a validacao do dados informados
	 */
	function eventoClickHabilitarEdicao (campoTd , obj , funcaoValidacao){
		campoTd.closest('tr').find('td').each(function() {
			if (!$(this).hasClass('editar') && !$(this).hasClass('excluir') && !$(this).hasClass('naoeditavel') ) {
				var valor = $(this).text();
				
				// Criando os inputs, checkboxs, selects
				if ($(this).hasClass('checkbox')) {
					$(this).html('<input type="checkbox" ' + (valor == 'S' ? 'checked="checked"': '') + '/>');
				} else if($(this).hasClass("select")){
					criarComponenteSelectVisualizacao($(this) , valor);
				} else if($(this).hasClass("autocomplete")){
					criarComponenteAutoCompleteVisualizacao($(this) , valor);
				} else{
					$(this).html('<input type="text" value="' + valor + '" style="width:' + ($(this).width() * 0.9)+ 'px"/>');
				}
				
				// Criando a configuracao dos inputs, checkboxs
				var inputCampo = $(this).find("input");
				if ($(this).hasClass('obrigatorio')) {
					inputCampo.addClass('obrigatorio');
				}
				if($(this).attr("maxlength") != undefined){
					var max = $(this).attr("maxlength");
					inputCampo.attr("maxlength" , max);
				}
				if ($(this).attr("tipo") != undefined) {
					if($(this).attr("tipo") == "semespecial"){
						inputCampo.attr("tipo","semespecial");
					}
					if ($(this).attr("tipo") == "telefone") {
						Mascara.telefone(inputCampo);
					}
					if ($(this).attr("tipo") == "email") {
						inputCampo.attr("tipo","email");
					}
				}
			}
		});
	}
	
	/**
	 * Funcao para criar um autocomplete no campo
	 * @param campoTd Coluna da tabela em que ira aparecer o autcomplete
	 * @param valor valor para vir pre-selecionado
	 */
	function criarComponenteAutoCompleteVisualizacao (campoTd , valor){
		campoTd.html('<input type="text" value="' + valor + '" id="autocomplete_visu_'+qtdAreaAbertas+++'" style="width:' + (campoTd.width() * 0.9)+ 'px"/>');
		var metodo = campoTd.attr("metodo");
		var param;
		if(campoTd.attr("param") == undefined){
			param = "";
		} else {
			param = campoTd.attr("param");
		}
		var value = campoTd.attr("value");
		var inputCampo = campoTd.find("input");
		if(campoTd.attr("classeAjax") != undefined){
			var ajax = campoTd.attr("classeAjax");
			AutoComplete.criarDifAjax(inputCampo.attr("id") , ajax , metodo , param , value , [valor]);
		} else {
			AutoComplete.criar(inputCampo.attr("id") , metodo , param , value , [valor]);
		}
	}
	
	
	/**
	 * Funcao para criar a combo que sera utilizada nos registros
	 * @param campoTd Coluna da tabela em que ira aparecer o select
	 * @param valor valor para vir pre-selecionado
	 */
	function criarComponenteSelectVisualizacao (campoTd , valor){
		campoTd.html('<select style="width:' + (campoTd.width() * 0.9)+ 'px" id="select_combo_visu'+qtdRegionaisAbertas+++'"></select>');
		var metodo = campoTd.attr("metodo");
		var param;
		if(campoTd.attr("param") == undefined){
			param = "";
		} else {
			param = campoTd.attr("param");
		}
		var value = campoTd.attr("value");
		var description = campoTd.attr("description");
		var valorPadrao;
		if(campoTd.attr("default") == undefined){
			valorPadrao = null;
		} else {
			valorPadrao = campoTd.attr("default");
		}
		var selectCampo = campoTd.find("select");
		if(campoTd.attr("classeAjax") != undefined){
			var ajax = campoTd.attr("classeAjax");
			ComboHelper.selectDifAjax(selectCampo.attr("id") , ajax , metodo , param , value , description ,[valor] , valorPadrao);
		} else {
			ComboHelper.select(selectCampo.attr("id") , metodo , param , value , description ,[valor] , valorPadrao);
		}
		
		// Criando a configuracao do select
		var inputCampo = campoTd.find("select");
		if (campoTd.hasClass('obrigatorio')) {
			inputCampo.addClass('obrigatorio');
		}
	}
	
	
	/**
	 * Funcao para confirmar a edicao dos dados
	 * @param campoTd Td que foi clicada
	 * @param obj objeto para ser populado
	 * @param funcaoValidacao Funcao responsavel por validar os dandos antes de salvar as alteracoes
	 * @param idTabela id da tabela de visulizacao
	 */
	function eventoClickConfirmaEdicao (campoTd , obj , funcaoValidacao , idTabela){
		campoTd.css('background', 'url(' + contexto + '/images/check.png) no-repeat center');
		campoTd.attr('title', mensagens['title_salvar']);
		campoTd.unbind('click');
		campoTd.click(function() {
			var idTr = campoTd.closest("tr").attr("id");
			var selectorTr = $("#"+idTabela).find("[id='"+idTr+"']").find("input.obrigatorio:visible");
			if (Validacao.validarFormularioVisualizacao(selectorTr)) {
				montarObjetoAntesSalvar($(this).closest('tr'), obj);
				if (funcaoValidacao(obj)) {
					$(this).closest('tr').find('td').each(function() {
						if (!$(this).hasClass('editar') && !$(this).hasClass('excluir')) {
							if ($(this).hasClass('checkbox')) {
								var valor = $(this).find('input').is(':checked') ? 'S': 'N';
								$(this).html(valor);
							} else if($(this).hasClass('select')){
								var valor = $(this).find('select').select2("val");
								$(this).html(valor);
							} else {
								var valor = $(this).find('input').val();
								$(this).html(valor);
							}
						}
					});
					$(this).css('background', 'url('+ contexto+ '/images/edita.png) no-repeat center');
					$(this).attr('title', mensagens['title_editar']);
					bindVisualizar($(this), obj , funcaoValidacao , idTabela);
				}
			}
		});
	}
	
	/**
	 * Funcao para montar o objeto que sera utilizado na funcao de verificacao/validacao dos dados ao ser alterada uma entidade
	 * @param campoTd Td que foi clicada
	 * @param obj objeto para ser populado
	 */
	function montarObjetoAntesSalvar(campoTr, obj) {
		obj['id'] = parseInt(campoTr.attr("id"));
		campoTr.find('td').each(function() {
			if (!$(this).hasClass('editar') && !$(this).hasClass('excluir') && !$(this).hasClass('resetarSenha')
					&& !$(this).hasClass('desconectar') && !$(this).hasClass('exibirPermissoes')) {
				
				if ($(this).hasClass('checkbox')) {
					obj[$(this).attr('atributo')] = $(this).find('input').is(':checked');
				} else if ($(this).hasClass('naoeditavel')) {
					obj[$(this).attr('atributo')] = $.trim($(this).html());
				} else if ($(this).hasClass('select')) {
					obj[$(this).attr('atributo')] = $.trim($(this).find("select").select2("val"));
				} else {
					obj[$(this).attr('atributo')] = $.trim($(this).find('input').val());
				}
			}
		});
	}

	function hideNull(valor) {
		return valor == null ? '' : valor;
	}
	
	/**
	 * Exibe / esconde uma tela de loading no container
	 * @param selector - o selector do container onde sera exibido o loading
	 * @param bool - booleano true para exibir, false para esconder
	 */
	function loading(selector, bool) {
		if (bool) {
			var container = $(selector);
			container.append('<div class="loading"></div>');
			$(selector+' > .loading').html('<img src="'+contexto+'/images/loading.gif"/>')
			.css('left', container.offset().left)
			.css('top', container.offset().top)
			.height(container.height())
			.width(container.width());
			$(selector+' > .loading img')
			.css('margin-left', (container.width()*47.5/100))
			.css('margin-top', (container.height()/2));
		} else {
			setTimeout(function() {
				$(selector+' > .loading').remove();
			}, 1000);
		}
	}
	
	function redirecionarAplicacao (usuario){
		if(usuario != null){
			ConstantesMetricViewAjax.buscarParametro(mensagens['const_nome_sistema'] , function(nomeSystem){
				if(nomeSystem == usuario.login){
					window.location.href = contexto + "/system/";
				} else {
					window.location.href = contexto + "/app/painel";
				}
			});
			
		}
	}

	return {
		bindMenuUser : bindMenuUser,
		bindLogout : bindLogout,
		filtrar : filtrar,
		bindFiltrarOption : bindFiltrarOption,
		bindVisualizar : bindVisualizar,
		exibirMensagem : exibirMensagem,
		hideNull : hideNull,
		loading: loading,
		redirecionarAplicacao: redirecionarAplicacao,
		apresentarMensagemConfirmInserirEditarUsuarioGrupo: apresentarMensagemConfirmInserirEditarUsuarioGrupo,
		iniciarModalVisualizacaoPermissao: iniciarModalVisualizacaoPermissao
	};

}();