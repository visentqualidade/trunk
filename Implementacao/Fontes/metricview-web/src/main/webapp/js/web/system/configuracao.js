$(function() {
	
	bindTrocarVisualizacaoSelecionaSistema();
});

function bindTrocarVisualizacaoSelecionaSistema () {
	$('#selecionaPageSystem li').click(function() {
		if ($(this).hasClass('pagAtiva')) return;
		var opcao = $('.pagAtiva');
		var idOpcao = $(opcao).attr('id').split('li')[1];
		$('#selecionaPageSystem li').removeClass('pagAtiva');
		
		$(this).addClass('pagAtiva');
		var visualizacao = $(this).attr('id').split('li')[1].toLowerCase();
		
		if (visualizacao == 'metricview') {
			$('#page'+idOpcao).hide("slide", { direction: "up" }, 250, function() {
				$("#pageMetricView").show("slide", { direction: "down" }, 250);
			});
		} else if(visualizacao == 'easyview') {
			$('#page'+idOpcao).hide("slide", { direction: "down" }, 250, function() {
				$("#pageEasyView").show("slide", { direction: "up" }, 250);
			});
		} else if(visualizacao == 'controlview') {
			$('#page'+idOpcao).hide("slide", { direction: "down" }, 250, function() {
				$("#pageControlView").show("slide", { direction: "up" }, 250);
			});
		} else if(visualizacao == 'mapview') {
			$('#page'+idOpcao).hide("slide", { direction: "down" }, 250, function() {
				$("#pageMapView").show("slide", { direction: "up" }, 250);
			});
		} else if(visualizacao == 'itxview') {
			$('#page'+idOpcao).hide("slide", { direction: "down" }, 250, function() {
				$("#pageItxView").show("slide", { direction: "up" }, 250);
			});
		} 
	});
}