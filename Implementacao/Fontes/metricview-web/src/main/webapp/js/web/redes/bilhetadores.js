var PainelBilhetador = new function (){
	
	var bilhetadores = new Array();
	
	function montarPainel(){
		BilhetadorAjax.buscarTodasAssociacoes(function(bil){
			PainelBilhetador.bilhetadores = bil;
			
			criarTabela();
		});
	};
	
	function obtemNomeBilhetadores(){
		var bilhetadores = PainelBilhetador.bilhetadores;
		var novoBilhetadores = new Array();
		
		for (var i in bilhetadores){
			novoBilhetadores.push(bilhetadores[i].bilhetador);
		}
		
		return arrayElementosUnico(novoBilhetadores);
	}
	
	function criarTabela(){
		var cols = new Array();
		cols.push(Datatable.colClass(mensagens['label_data'], 'data', 'dataTabBil'));
		cols.push(Datatable.col(mensagens['label_bilhetador'], 'bilhetador'));
		cols.push(Datatable.colClass(mensagens['txt_cn'], 'cn', 'cnTabBil'));
		
		Datatable.init('tabelaBilhetadores', PainelBilhetador.bilhetadores, cols, {
			sort: false,
			displayLength: 12,
			"tipoPaginacao": "two_button"
		});
		
		$('#tabelaBilhetadores').closest('.overflow').css('min-height', 190);
	};
	
	function montaColecaoApresentarTabela(){
		var bilhetadoresSelecionados = new Array();
		
		for (var i in PainelBilhetador.bilhetadores){
			var bilhetador = PainelBilhetador.bilhetadores[i];
			
			if (bilhetador.cn == $("#selectBilhetadorCN").val()){
				if ($("#selectBilhetador").val() == mensagens['label_select_todos']){
					bilhetadoresSelecionados.push(bilhetador);
				}else{
					if ($("#selectBilhetador").val() == bilhetador.bilhetador){
						bilhetadoresSelecionados.push(bilhetador);
					}
				}
			}
		}
		
		return bilhetadoresSelecionados;
	};
	
	return {
		bilhetadores : bilhetadores,
		montarPainel : montarPainel,
		montaColecaoApresentarTabela: montaColecaoApresentarTabela
	};
	
} ();

$(function() {
	$("#selectBilhetadorCN").change(function(){
		var cn = $(this).val();
		cn = $.trim(cn);
		if($.trim(cn) == ""){
			$("#tabelaBilhetadores").dataTable().fnFilter("" , 2);
		} else {
			$("#tabelaBilhetadores").dataTable().fnFilter("^"+cn+"$", 2, true, false);
		}
	});

	$("#selectBilhetador").change(function(){
		var bilhetador = $(this).val();
		bilhetador = $.trim(bilhetador); 
		if($.trim(bilhetador) == ""){
			$("#tabelaBilhetadores").dataTable().fnFilter("" , 1);
		} else {
			$("#tabelaBilhetadores").dataTable().fnFilter("^"+bilhetador+"$", 1, true, false);
		}
	});
	
	PainelBilhetador.montarPainel();
	
});


function arrayElementosUnico(a) {
    var temp = {};
    for (var i = 0; i < a.length; i++)
        temp[a[i]] = true;
    var r = [];
    for (var k in temp)
        r.push(k);
    return r;
}