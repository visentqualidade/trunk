/**
 * Objeto para encapsular a criacao do componente accordion.
 */

var Accordion = function(){
	
	var opcoes = {
		heightStyle: "content"	
	};

	function criar (idCampo , configuracoes){
		var config = $.extend(opcoes , configuracoes);
		$("#"+idCampo).accordion({
			heightStyle: config.heightStyle
		});
	}
	
	function destroy (idCampo){
		$("#"+idCampo).accordion("destroy");
	}
	
	return {
		criar: criar,
		destroy: destroy
	};
	
}();