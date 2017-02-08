var ComboHelper = function(){
	
	/**
	 * Funcao para criar uma combo com valores para serem apresentados para o usuario
	 * Parametros : idSelect -> Id do campo
	 * 				ajax -> Ajax para ser pesquisado
	 * 				funcao -> Funcao para que seja possivel pesquisar os dados
	 * 				param -> parametros para serem utilizados na chamada dos metodos
	 * 				value -> Valor do value do selects
	 * 				description -> descricao do select que sera apresentado para o usuario
	 * 				selected -> valor da opcao que ja devera vir preenchida
	 * 				valorDefault -> Caso true, ira mostrar a opcao ---- | Caso false ira mostrar a primeira opcao da lista
	 * 				
	 * 		Exemplo de uso.:
	 * 	ComboHelper.select('idDoCampoSelect' , 'ClasseAjax' , 'metodoDaClasseAjax' , 'parametrosMetodoAjax' , 'valueDoSelect' , 'descricaoDoSelect',''valorPreSelecionados');
	 */
	function selectDifAjax (idSelect , ajax, funcao , param , value , description , selected , valorDefault){
		var arraySelected = criarArraySelected(selected,value);
		if(param != ''){
			window[ajax][funcao](param , function(result){
				processarDados(idSelect , arraySelected , value , description , result , valorDefault , true);
			});
		} else{
			window[ajax][funcao](function(result){
				processarDados(idSelect , arraySelected , value , description , result , valorDefault , true);
			});
		}
	}
	
	/**
	 * Funcao para criar uma combo com valores para serem apresentados para o usuario
	 * Parametros : idSelect -> Id do campo
	 * 				funcao -> Funcao para que seja possivel pesquisar os dados
	 * 				param -> parametros para serem utilizados na chamada dos metodos
	 * 				value -> Valor do value do select
	 * 				description -> descricao do select que sera apresentado para o usuario
	 * 				selected -> valor da opcao que ja devera vir preenchida
	 * 				valorDefault -> Caso true, ira mostrar a opcao ---- | Caso false ira mostrar a primeira opcao da lista
	 * 				
	 * 		Exemplo de uso.:
	 * 	ComboHelper.select('idDoCampoSelect' , 'metodoDoComboHelperAjax' , 'parametrosMetodoAjax' , 'valueDoSelect' , 'descricaoDoSelect',''valorPreSelecionados');
	 */
	function select (idSelect , funcao , param , value , description , selected , valorDefault){
		var arraySelected = criarArraySelected(selected,value);
		if(param != ''){
			ComboHelperAjax[funcao](param , {callback:function(result){
				processarDados(idSelect , arraySelected , value , description , result , valorDefault , true);
			},async:false});
		} else{
			ComboHelperAjax[funcao]({callback: function(result){
				processarDados(idSelect , arraySelected , value , description , result , valorDefault , true);
			},async: false});
		}
	}
	
	/**
	 * Funcao para criar uma combo com valores para serem apresentados para o usuario sem criar o componente
	 * Parametros : idSelect -> Id do campo
	 * 				funcao -> Funcao para que seja possivel pesquisar os dados
	 * 				param -> parametros para serem utilizados na chamada dos metodos
	 * 				value -> Valor do value do select
	 * 				description -> descricao do select que sera apresentado para o usuario
	 * 				selected -> valor da opcao que ja devera vir preenchida
	 * 				valorDefault -> Caso true, ira mostrar a opcao ---- | Caso false ira mostrar a primeira opcao da lista
	 * 				
	 * 		Exemplo de uso.:
	 * 	ComboHelper.select('idDoCampoSelect' , 'metodoDoComboHelperAjax' , 'parametrosMetodoAjax' , 'valueDoSelect' , 'descricaoDoSelect',''valorPreSelecionados');
	 */
	function selectSemSelect2 (idSelect , funcao , param , value , description , selected , valorDefault){
		var arraySelected = criarArraySelected(selected,value);
		if(param != ''){
			ComboHelperAjax[funcao](param , {callback:function(result){
				processarDados(idSelect , arraySelected , value , description , result , valorDefault , false);
			},async:false});
		} else{
			ComboHelperAjax[funcao]({callback: function(result){
				processarDados(idSelect , arraySelected , value , description , result , valorDefault  , false);
			},async: false});
		}
	}
	
	/**
	 * 
	 * Funcao para criar os valroes no select
	 * Parametros : idSelect -> Id do campo
	 * 				arraySelected -> Array contendo os valores que devem vir ja preenchidos
	 * 				value -> Valor do value do select
	 * 				description -> descricao do select que sera apresentado para o usuario
	 * 				result -> Array de dados para serem acrescentados
	 * 				valorDefault -> Valor dafault do combo
	 * */
	
	function processarDados (idSelect , arraySelected , value , description , result , valorDefault , criarSelect2){
		removerOpcoes(idSelect);
		var concat = new String(); 
		if(valorDefault != null){
			concat += "<option value=' '>"+valorDefault+"</option>";
		}
		if(value == "" && description == ""){
			for (var count = 0 ; count < result.length; count++) {
				concat += "<option value='"+result[count]+"'>"+result[count]+"</option>";
			}
			$("#"+idSelect).append(concat);
			for(var j=0;j<arraySelected.length;j++){
				$('#'+idSelect).find("option[value="+arraySelected[j]+"]").attr('selected',true);
			}
		} else {
			for (var count = 0 ; count < result.length; count++) {
				concat += "<option value='"+result[count][value]+"'>"+result[count][description]+"</option>";
			}
			$("#"+idSelect).append(concat);
			for(var j=0;j<arraySelected.length;j++){
				$('#'+idSelect).find("option[value="+arraySelected[j][value]+"]").attr('selected',true);
			}
		}
		if(criarSelect2){
			criarComponenteSelect2(idSelect);
		}
	}
	 
	/**
	 * Funcao para criar o array de valores para serem pre-exibidos
	 * Parametros: selected -> valor(es) da opcao que ja devera vir preenchida
	 */
	function criarArraySelected(selected,value){
		var arraySelected = new Array();
		if(!(selected instanceof Array)){
			var obj = new Object();
			obj[value] = "'"+selected+"'";
			arraySelected.push(obj);
		} else {
			for(var i in selected){
				var obj = new Object();
				obj[value] = "'"+selected[i]+"'";
				arraySelected.push(obj);
			}
		}
		return arraySelected;
	}
	
	//Funcao para criar o componente select2
	function criarComponenteSelect2 (idSelect){
		$('#'+idSelect).select2({ width: 'element' , allowClear: true});
	}
	
	//Funcao para remover os valores que tenham na combo
	function removerOpcoes (idSelect){
		$('#'+idSelect).find("option").remove();
		$('#'+idSelect).select2('destroy');
	}
	
	return {
		select: select,
		selectSemSelect2: selectSemSelect2,
		selectDifAjax: selectDifAjax,
		processarDados: processarDados,
		criarComponenteSelect2: criarComponenteSelect2
	};
	
}();