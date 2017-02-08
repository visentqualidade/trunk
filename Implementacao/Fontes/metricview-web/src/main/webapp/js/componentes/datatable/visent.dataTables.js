var Datatable = function() {
	
	/**
	 * Cria uma datatable.
	 * 
	 * Parametros:
	 * - table    : id da table
	 * - dados  : colecao de objetos para popular a tabela
	 * - colunas: as colunas da tabela (utilizar DataTable.col)
	 * - opcoes : 
	 * 			 -> 'paginacao'   : ativar paginacao (default true)
	 * 			 -> 'sort'        : ativar ordenacao (default true)
	 */
	function init(table, dados, colunas, opcoes) {
		
		// Configuracoes default
		var defaults = {
				'paginacao': true,
				'formatoTabela': '<"overflow"t>pi',
				'sort': true,
				'displayLength': 10,
				'tipoPaginacao': 'full_numbers'
		};

		var configs = $.extend({}, defaults, opcoes);
		// Cria a datatable
		var oTable = $('#'+table).dataTable({
			"sDom": configs.formatoTabela,
			"bSort": configs.sort,
			"sPaginationType": configs.tipoPaginacao,
			"iDisplayLength": configs.displayLength,
			"aaData": dados,
			"aoColumns": colunas,
			"bPaginate": true,
			"bDestroy": true,
			"bRetrieve":true
		});
		
		return oTable;
	}
	
	function col(titulo, campo) {
		return { 
			"sTitle": titulo, 
			"mDataProp": campo
		};
	}
	
	function colFixed(titulo, campo, largura) {
		return { 
			"sTitle": titulo, 
			"sWidth": largura+'px',
			"mDataProp": campo
		};
	}
	
	function colClass(titulo, campo, clazz) {
		return { 
			"sTitle": titulo, 
			"sClass": clazz,
			"mDataProp": campo
		};
	}
	
	function colEditar(campo , funcao) {
		return { 
			"sTitle": '', 
			"bSortable": false,
			"sWidth": '15px',
			"sClass": 'editar',
			"mDataProp": null,
			"fnRender": function(obj) {
				var sReturn = obj.aData[campo];
	            sReturn = '<a href="#" onclick="javascript:'+funcao+'('+sReturn+')"><div>&nbsp;</div></a>';
	            return sReturn;
	        }
		};
	}
	
	function colExcluir(funcao, param , funcaoRefresh) {
		return {
	        "sTitle": "",
	        "bSortable": false,
	        "sClass": "excluir",
	        "mDataProp": null,
	        "fnRender": function(obj) {
	            var sReturn = obj.aData[param];
	            sReturn = '<a href="#" onclick="javascript:if(confirm(mensagens[\'alerta_remover_entidade\']))'
	            	+funcao+'('+sReturn+', function(){'
	            	+ 'Funcoes.exibirMensagem(mensagens[\'mensagem_sucesso_operacao_sucesso\']);'
					+ funcaoRefresh+'();'
	            	+ '})" class="colExcluir"><div>&nbsp;</div></a>';
	            
	            return sReturn;
	        } 
	    };
	}
	
	// Funcoes publicas
	return {
		init: init,
		col: col,
		colFixed: colFixed,
		colClass: colClass,
		colEditar: colEditar,
		colExcluir: colExcluir
	};

}();