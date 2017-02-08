var Mascara = function (){
	
	function telefone(campo){
		campo.mask("99999999?9",{placeholder:" "});
	}
	
	function data(campo){
		campo.mask("99/99/9999",{placeholder:" "});
	}
	
	function removerMascaraCampo(campo){
		campo.unmask();
	}
	
	function removerMascaraValor(valor){
		valor = valor.replace("." , "");
		valor = valor.replace("," , "");
		valor = valor.replace("-" , "");
		valor = valor.replace("/" , "");
		valor = valor.replace("(" , "");
		valor = valor.replace(")" , "");
		valor = valor.replace(" " , "");
		return valor;
	}
	
	return {
		telefone: telefone,
		removerMascaraCampo: removerMascaraCampo,
		removerMascaraValor: removerMascaraValor,
		data: data 
	};
	
}();