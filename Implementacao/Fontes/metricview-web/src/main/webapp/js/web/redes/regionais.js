var PainelRegional = function () {

        var idRegional = null;
       
        function editarRegional(idCampo) {
            for (var i in Redes.regionais) {
			var regional = Redes.regionais[i];
			if (regional.id == idCampo) {
				populaCamposRegional(regional);
			}
		}
		_dialogEditarRegional();
	}

	function _dialogEditarRegional() {
		var botoes = new Array();
		botoes.push(new BotaoModal(mensagens['btn_atualizar'], function() {
			_atualizarEFechar();
		}));
		botoes.push(new BotaoModal(mensagens['btn_cancelar'], function() {
			limparDadosNovaRegional();
			Modal.fechar("dlgRegional");
		}));

		Modal.iniciar("dlgRegional", {
			width: 340,
			height: 300,
			botoes: botoes,
			title: mensagens['title_editar_regional'],
			close: function() {
				limparDadosNovaRegional();
			}
		});
	}

	function populaCamposRegional(regional) {
		$("#nomeRegional").val(regional.nome);
		$("#descricaoRegional").val(regional.descricao);

		var cns = new Array();
		cns = regional.cns.split(", ");
		$("#opcaoCN").select2("val", cns);

		idRegional = regional.id;
	}
	
	function limparDadosNovaRegional() {
		$("#formRegional input").each(function() {
			$(this).removeClass("ui-state-error");
			$(this).val("");
		});
		$("#formRegional .select2-container").each(function() {
			$(this).removeClass("ui-state-error");
			$(this).select2("val", "");
		});
		$(".textoErro").html("");
		$(".textoErro").hide();
	}

	function _salvarRegional() {
		if (Validacao.validarFormulario('formRegional')) {
			RegionalAjax.inserir(criarObjetoNovaRegional(), function() {
				Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
				limparDadosNovaRegional();
				alterarDadosTabelaRegional();
				Redes.montar;
			});
		}
	}

	function _salvarRegionalEFechar() {
		if (Validacao.validarFormulario('formRegional')) {
			RegionalAjax.inserir(criarObjetoNovaRegional(), function() {
				Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
				limparDadosNovaRegional();
				alterarDadosTabelaRegional();
				Modal.fechar("dlgRegional");
			});
		}
	}

	function _atualizarEFechar() {
		if (Validacao.validarFormulario('formRegional')) {
			RegionalAjax.atualizarEntidade(criarObjetoNovaRegional(), function() {
				Funcoes.exibirMensagem(mensagens['mensagem_sucesso_operacao_sucesso']);
				limparDadosNovaRegional();
				alterarDadosTabelaRegional();
				Modal.fechar("dlgRegional");
			});
		}
	}

	function criarObjetoNovaRegional() {
		var regional = new Regional();

		regional.id = idRegional;
		regional.nome = $.trim($("#nomeRegional").val());
		regional.descricao = $.trim($("#descricaoRegional").val());
		regional.nomeRelatorio = " ";

		regional.codigoNacional = new Array();

		var cnsSelecionados = $("#opcaoCN").select2("val");

		for (var i = 0; i < cnsSelecionados.length; i++) {
			var codigoNacional = new CodigoNacional();
			codigoNacional.codigo = cnsSelecionados[i];
			regional.codigoNacional.push(codigoNacional);
		}

		idRegional = null;

		return regional;
	}

	function montarTabelaInicialRegional() {
		RegionalAjax.buscarTodosComUF(function(regs) {
			Redes.regionais = regs;
			var cols = new Array();
			cols.push(Datatable.colClass(mensagens.txt_col_nome, 'nome', 'nomeReg'));
			cols.push(Datatable.colClass(mensagens.txt_col_descricao, 'descricao', 'descReg'));
			cols.push(Datatable.colClass(mensagens.txt_ufs, 'ufs', 0));
			cols.push(Datatable.colClass(mensagens.txt_cns, 'cns', 0));
			cols.push(Datatable.colEditar('id', 'PainelRegional.editarRegional'));
			cols.push(Datatable.colExcluir('RegionalAjax.removerEntidade', 'id', 'PainelRegional.alterarDadosTabelaRegional'));
			
			Datatable.init('tabelaRegionais', regs, cols, {
				sort: true,
				"tipoPaginacao": "two_button"
			});
			$('#tabelaRegionais').closest('.overflow').css('min-height', 165);

			$('#filtroListaRegio').keyup(function() {
				$("#tabelaRegionais").dataTable().fnFilter($(this).val());
			});
		});

	}

	function alterarDadosTabelaRegional() {
		RegionalAjax.buscarTodosComUF(function(regionais) {
			Redes.regionais = regionais;
			$('#tabelaRegionais').dataTable().fnClearTable();
			$('#tabelaRegionais').dataTable().fnAddData(regionais);
		});

	}

	/**
	 * Funcao para criar a tela para novo cadastramento das regionais
	 */
	function modalInserirNovaRegional() {
		$('#btnAddRegional').click(function() {
			var botoes = new Array();
			botoes.push(new BotaoModal(mensagens['btn_salvar'], function() {
				_salvarRegional();
			}));
			botoes.push(new BotaoModal(mensagens['btn_salvar_e_fechar'], function() {
				_salvarRegionalEFechar();
			}));
			botoes.push(new BotaoModal(mensagens['btn_cancelar'], function() {
				limparDadosNovaRegional();
				Modal.fechar("dlgRegional");
				alterarDadosTabelaRegional();
			}));

			Modal.iniciar("dlgRegional", {
				width: 340,
				height: 300,
				botoes: botoes,
				title: mensagens['title_cadastrar_regional'],
				close: function() {
					limparDadosNovaRegional();
				}
			});
		});

		$('#dlgRegional select').select2();
	}

	return {
		editarRegional: editarRegional,
		montarTabelaInicialRegional: montarTabelaInicialRegional,
		modalInserirNovaRegional: modalInserirNovaRegional,
		alterarDadosTabelaRegional: alterarDadosTabelaRegional,
	};
}();