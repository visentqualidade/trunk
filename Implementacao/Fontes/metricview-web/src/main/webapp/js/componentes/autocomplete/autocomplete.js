var AutoComplete = function(){
	
	/**
	 * Funcao para criar uma combo com valores para serem apresentados para o usuario
	 * Parametros : idCampo -> Id do campo
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
	function criarDifAjax (idCampo , ajax, funcao , param , value , selected){
		if(param != ''){
			window[ajax][funcao](param , function(result){
				processarDadosAutoComplete(idCampo , selected , value , result);
			});
		} else{
			window[ajax][funcao](function(result){
				processarDadosAutoComplete(idCampo , selected , value , result);
			});
		}
	}
	
	/**
	 * Funcao para criar uma combo com valores para serem apresentados para o usuario
	 * Parametros : idCampo -> Id do campo
	 * 				funcao -> Funcao para que sejaidCampol pesquisar os dados
	 * 				param -> parametros para serem utilizados na chamada dos metodos
	 * 				value -> Valor do value do select
	 * 				description -> descricao do select que sera apresentado para o usuario
	 * 				selected -> valor da opcao que ja devera vir preenchida
	 * 				valorDefault -> Caso true, ira mostrar a opcao ---- | Caso false ira mostrar a primeira opcao da lista
	 * 				
	 * 		Exemplo de uso.:
	 * 	ComboHelper.select('idDoCampoSelect' , 'metodoDoComboHelperAjax' , 'parametrosMetodoAjax' , 'valueDoSelect' , 'descricaoDoSelect',''valorPreSelecionados');
	 */
	function criar (idCampo , funcao , param , value , selected){
		if(param != ''){
			ComboHelperAjax[funcao](param , {callback:function(result){
				processarDadosAutoComplete(idCampo , selected , value , result);
			},async:false});
		} else{
			ComboHelperAjax[funcao]({callback: function(result){
				processarDadosAutoComplete(idCampo , selected , value , result);
			},async: false});
		}
	}
	
	/**
	 * 
	 * Funcao para criar os valroes no select
	 * Parametros : idCampo -> Id do campo
	 * 				arraySelected -> Array contendo os valores que devem vir ja preenchidos
	 * 				value -> Valor do value do select
	 * 				description -> descricao do select que sera apresentado para o usuario
	 * 				result -> Array de dados para serem acrescentados
	 * 				valorDefault -> Valor dafault do combo
	 * */
	
	function processarDadosAutoComplete (idCampo , selected , value , result){
		removerOpcoesAutoComplete(idCampo);
		var dados = new Array();
		for(var i in result){
			var opcao = result[i][value];
			dados.push(""+opcao+"");
		}
		criarComponenteAutoComplete(idCampo , dados);
		$("#"+idCampo).val(selected);
	}
	 
	//Funcao para criar o componente select2
	function criarComponenteAutoComplete (idCampo , dados){
		$('#'+idCampo).autocomplete({heightStyle: "content" , source : dados});
	}
	
	//Funcao para remover os valores que tenham no auto complete
	function removerOpcoesAutoComplete (idCampo){
		$('#'+idCampo).val("");
	}
	
	function destroy (idCampo){
		$("#"+idCampo).accordion("destroy");
	}
	
	return {
		criar: criar,
		destroy: destroy,
		criarDifAjax: criarDifAjax
	};
}();