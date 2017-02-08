var Redes = new function() {
	
	var regionais 			= new Array(); 
	var codigoNacional   	= null;

	return {
		regionais: regionais,
		codigoNacional: codigoNacional
	};
	
}();

$(function() {
	PainelRegional.montarTabelaInicialRegional();
	PainelRegional.modalInserirNovaRegional();
	ComboHelper.select('opcaoCN' , 'buscarCodigoNacionais' , '' , 'codigo' , 'codigo' ,[], null);
	ComboHelper.select('selectBilhetadorCNBilhetador' , 'buscarCodigoNacionais' , '' , 'codigo' , 'codigo' ,[], "Todos");
	ComboHelper.select('selectBilhetadorCNExcecao' , 'buscarCodigoNacionais' , '' , 'codigo' , 'codigo' ,[], "Todos");
	ComboHelper.select('selectBilhetador' , 'buscarBilhetadores' , '' , 'nome' , 'nome' ,[], "Todos");
	_bindClickMenuSistemaRedes();
	montarTabelaExcecaoCGI();
});

function montarTabelaExcecaoCGI () {
	ExcecaoCGIAjax.consultarTodos(function(excecoes) {
		var cols = new Array();
		cols.push(Datatable.col(mensagens['txt_cns'], 'cn'));
		cols.push(Datatable.col(mensagens['txt_col_central'], 'bilhetador'));
		cols.push(Datatable.col(mensagens['txt_col_setor_nao_identificado'], 'cgi'));
		
		Datatable.init('tabelaExcecao', excecoes, cols, { 
			sort: true,
			"tipoPaginacao": "two_button"
		});
		$('#tabelaExcecao').closest('.overflow').css('min-height', 165);
		
		$("#selectBilhetadorCNExcecao").unbind('change');
		$("#selectBilhetadorCNExcecao").change(function(){
			var cn = $(this).val();
			cn = $.trim(cn);
			if($.trim(cn) == ""){
				$("#tabelaExcecao").dataTable().fnFilter("" , 0);
			} else {
				$("#tabelaExcecao").dataTable().fnFilter("^"+cn+"$", 0, true, false);
			}
		});
		_bindClickExportarExcecaoCGI(excecoes);
	});
}

function _bindClickExportarExcecaoCGI (excecoes) {
	$('#imgExportaXls').unbind('click');
	$('#imgExportaXls').click(function(){
		Funcoes.loading('.bilhetadores', true);
		ExcecaoCGIAjax.exportarRelatorio(excecoes , function(arquivo){
			dwr.engine.openInDownload(arquivo);
			Funcoes.loading('.bilhetadores', false);
		});
	});
}

function _bindClickMenuSistemaRedes (){
	$('#ulMenuSistemaRede li').click(function() {
		$('#ulMenuSistemaRede li').removeClass('ativo');
		$(this).addClass('ativo');
		if($(this).attr('id') == 'bilhetador') {
			$('#divBilhetadores').show();
			$('#divExecaoCGI').hide();
		} else {
			$('#divBilhetadores').hide();
			$('#divExecaoCGI').show();
		}
	});
}