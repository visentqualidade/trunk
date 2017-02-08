/**
 * Componente para encapsular a criacao do calendario em js
 * 
 */
var Calendario = function (){
	
	var opcoes = {
		closeText: 'Fechar',
		prevText: 'Anterior',
		nextText: 'Próxima',
		currentText: 'Hoje',
		monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],	
		monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
		dayNames: ['Domingo','Segunda-feira','Terça-feira','Quarta-feira','Quinta-feira','Sexta-feira','Sábado'],
		dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],
		dayNamesMin: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],
		weekHeader: 'Sm',
		dateFormat: 'dd/mm/yy',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: false,
		defaultDate: '',
		yearSuffix: '',
		numberOfMonths: 1,
		gotoCurrent: true,
		disabled: false,
		changeMonth: true,
		changeYear: true
	};
	
	function criar (idCampo , configuracoes){
		var config = $.extend(opcoes , configuracoes);
		$('#'+idCampo).datepicker({closeText: config.closeText,
			prevText: config.prevText,
			nextText: config.nextText,
			currentText: config.currentText,
			monthNames: config.monthNames,
			monthNamesShort: config.monthNamesShort,
			dayNames: config.dayNames,
			dayNamesShort: config.dayNamesShort,
			dayNamesMin: config.dayNamesMin,
			weekHeader: config.weekHeader,
			dateFormat: config.dateFormat,
			firstDay: config.firstDay,
			isRTL: config.isRTL,
			showMonthAfterYear: config.showMonthAfterYear,
			defaultDate: config.defaultDate,
			yearSuffix: config.yearSuffix, 
			numberOfMonths: config.numberOfMonths,
			gotoCurrent: config.gotoCurrent,
			disabled: config.disabled,
			changeMonth: config.changeMonth,
			changeYear: config.changeYear
		});
	}
	
	function formatarData (data , formato){
		var formatoData = 'dd/mm/yy';
		if(formato != "" || formato != null){
			formatoData = formato;
		}
		return $.datepicker.formatDate(formatoData , data);
	}
	
	function valorEmDate(valorData){
		var dia = valorData.split("/")[0];
		var mes = valorData.split("/")[1];
		var ano = valorData.split("/")[2];
		return new Date(ano , (mes-1) , dia);
	}
	
	return {
		criar: criar,
		formatarData: formatarData,
		valorEmDate: valorEmDate
	};
	
}();