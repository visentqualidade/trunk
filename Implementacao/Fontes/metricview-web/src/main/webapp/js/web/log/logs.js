var qtdDiasMax = null;
$(function(){
	ConstantesMetricViewAjax.buscarParametro(mensagens['const_qtd_max_dias_log'] , function(result){
		qtdDiasMax = result;
		criarFiltros();
		if (!$('#tabelaLogs').length) return;
		$("#inputDataInicial, #inputDataFinal").val(Calendario.formatarData(new Date() , 'dd/mm/yy'));
		montarTabelaLog();
		
		$("#inputDataInicial, #inputDataFinal").change(function() {
			Funcoes.loading('#listaLogs', true);
			consultarLogs();
			Funcoes.loading('#listaLogs', false);
		});
	});
});

function criarFiltros (){
	ComboHelper.selectDifAjax('selectUsuarios' , 'LogAjax' , 'buscarUsuariosLog' , '' , 'nome' , 'nome' ,[], "Todos");
	ComboHelper.select('selectSolucao' , 'buscarSolucoes' , '' , 'descricaoTela' , 'descricaoTela' ,[], "Todas");
	Calendario.criar('inputDataInicial' , {});
	Calendario.criar('inputDataFinal' , {});
	
	$("#selectUsuarios").change(function(){
		var usuario = $(this).val();
		usuario = $.trim(usuario);
		if($.trim(usuario) == ""){
			$("#tabelaLogs").dataTable().fnFilter("" , 1);
		} else {
			$("#tabelaLogs").dataTable().fnFilter("^"+usuario+"$", 1, true, false);
		}
	});
	
	$("#selectSolucao").change(function(){
		var solucao = $(this).val();
		if($.trim(solucao) == ""){
			$("#tabelaLogs").dataTable().fnFilter("" , 3);
		} else {
			$("#tabelaLogs").dataTable().fnFilter("^"+solucao+"$", 3, true, false);
		}
	});
	
	$("#mensagemLogs").keyup(function(){
		var texto = $(this).val();
		if($.trim(texto) == ""){
			$("#tabelaLogs").dataTable().fnFilter("" , 2);
		} else {
			$("#tabelaLogs").dataTable().fnFilter(texto, 2, true, false);
		}
	});
}

function montarTabelaLog (){
	var logTO = criarLogEntidadeFiltro();
	LogAjax.buscarPorFiltroTO(logTO , function(logs){
		var cols = new Array();
		cols.push(Datatable.colFixed(mensagens['txt_col_data_hora'], 'dataHoraFormatada', 105));
		cols.push(Datatable.colFixed(mensagens['txt_col_usuario'], 'nomeUsuario', 110));
		cols.push(Datatable.col(mensagens['txt_col_mensagem'], 'mensagem'));
		cols.push(Datatable.colFixed(mensagens['txt_col_solucao'], 'projetoSolucao', 120));
		Datatable.init('tabelaLogs', logs, cols, {
			displayLength: 15
		});
		$('#tabelaLogs').closest('.overflow').css('min-height', '200px');
		$('#tabelaLogs').css("width" , "100%");
	});
}

function consultarLogs() {
	var logTO = criarLogEntidadeFiltro();
	LogAjax.buscarPorFiltroTO(logTO , function(logs){
		var qtdDias = Math.round((logTO.dataFim.getTime() - logTO.dataInicio.getTime()) / (1000 * 60 * 60  * 24));
		if(qtdDias > qtdDiasMax){
			Funcoes.exibirMensagem(formatarMensagemApresentacao('alerta_qtd_max_dif_consulta_log' , qtdDiasMax));
		}
		$('#tabelaLogs').dataTable().fnClearTable();
		$('#tabelaLogs').dataTable().fnAddData(logs);
	});
}

function criarLogEntidadeFiltro (){
	var logsTO = new LogsTO();
	logsTO.dataInicio = Calendario.valorEmDate($("#inputDataInicial").val());
	logsTO.dataFim = Calendario.valorEmDate($("#inputDataFinal").val());
	return logsTO;
}