var Validacao = function() {
	
	/**
	 * Funcao para validar os dados obrigatorios do formulario na visualizar dos dados do usuario e grupo
	 * Essa funcao valida.:
	 * 	1.	Input's obrigatorios, que estejam visiveis ao usuario
	 *  2.	Input's que tenham uma quantidade maxima de caracteres
	 *  3. 	Input's que tenham uma quantidade minima de caracteres
	 *  6.	Valida input's do tipo email
	 *  7.	Valida inputs' do tipo data
	 * Parametros: selector contendo os inputs que deverao ser verificados
	 * */
	function validarFormularioVisualizacao (selector){
		var valido = true;
		selector.each(function(){
			valido = valido && validarCampo($(this));
			if($(this).attr("tipo") == "email"){
				valido = valido && validarCampoEmail($(this));
			}
			if($(this).attr("tipo") == "data"){
				valido = valido && validarCampoData($(this));
			}
			if($(this).attr("tipo") == "semespecial"){
				valido = valido && validarCampoCaracEspecial($(this));
			}
			if($(this).attr("maxlength") != undefined){
				valido = valido && validarQtdMaxCaracCampo($(this));
			}
			if($(this).attr("minlength") != undefined){
				valido = valido && validarQtdMinCaracCampo($(this));
			}
		});
		
		return valido;
	}
	
	
	/**
	 * Funcao para validar os dados obrigatorios do formulario
	 * Essa funcao valida.:
	 * 	1.	Input's obrigatorios, que estejam visiveis ao usuario
	 *  2.	Input's que tenham uma quantidade maxima de caracteres
	 *  3. 	Input's que tenham uma quantidade minima de caracteres
	 *  4. 	Select's obrigatorios, que estejam visiveis ao usuario
	 *  5. 	TextArea's obrigatorios, que estejam visiveis ao usuario
	 *  6.	Valida input's do tipo email
	 *  7.	Valida inputs' do tipo data
	 * Parametros: idForm -> id do formulario para ser buscados os campos obrigatorios
	 * */
	function validarFormulario (idForm){
		var valido = true;
		$('#'+idForm + ' input.obrigatorio:visible').each(function(){
			valido = valido && validarCampo($(this));
			if($(this).attr("tipo") == "email"){
				valido = valido && validarCampoEmail($(this));
			}
			if($(this).attr("tipo") == "data"){
				valido = valido && validarCampoData($(this));
			}
			if($(this).attr("tipo") == "semespecial"){
				valido = valido && validarCampoCaracEspecial($(this));
			}
			if($(this).attr("maxlength") != undefined){
				valido = valido && validarQtdMaxCaracCampo($(this));
			}
			if($(this).attr("minlength") != undefined){
				valido = valido && validarQtdMinCaracCampo($(this));
			}
		});
		
		if(valido){
			$('#'+idForm + ' .select2-container:visible').each(function(){
				if($('#'+$(this).attr('id').split('_')[1]).hasClass('obrigatorio')){
					valido = valido && validarCampoSelect($(this));
				}
			});
		}

		if(valido){
			$('#'+idForm + ' textarea.obrigatorio:visible').each(function(){
				valido = validarCampo($(this));
				if($(this).attr("tipo") == "semespecial"){
					valido = valido && validarCampoCaracEspecial($(this));
				}
			});
		}
		
		return valido;
	}
	
	/**
	 * Funcao para verificar se o campo contem caracteres especiais ou nao 
	 * Parametros: campo -> Campo para ser verificado
	 */
	function validarCampoCaracEspecial(campo) {
		   var regex=/[*|\":<>[\]{}`\\()'";@&$]/;
		   if(campo.val().match(regex)) {
		        addErroCampo(campo , mensagens['alerta_preenchimento_invalido_carac_especial']);
				return false;
		   }
		   else {
			   removeErroCampo(campo);
			   return true;
		   }
		} 
	
	
	/**
	 * Funcao para verificar se a quantidade maxima de caracter foi ultrapassada ou nao
	 * Parametros: campo -> Campo para ser verificado
	 */
	function validarQtdMaxCaracCampo (campo){
		var qtdMax = campo.attr("maxlength");
		if(campo.val().length > qtdMax){
			addErroCampo(campo , formatarMensagemApresentacao('alerta_qtd_max_carac' , [qtdMax]));
			return false;
		} else {
			removeErroCampo(campo);
			return true;
		}
		
	}
	
	/**
	 * Funcao para verificar se a quantidade minima de caracter foi ultrapassada ou nao
	 * Parametros: campo -> Campo para ser verificado
	 */
	function validarQtdMinCaracCampo (campo) {
		var qtdMin = campo.attr("minlength");
		if(campo.val().length < qtdMin){
			addErroCampo(campo , formatarMensagemApresentacao('alerta_qtd_min_carac' , [qtdMin]));
			return false;
		} else {
			removeErroCampo(campo);
			return true;
		}
	}
	
	/**
	 * Funcao para verificar se o campo email esta corretamente preenchido 
	 * Parametros: campo -> campo para ser verificado
	 */
	function validarCampoEmail (campo){
		var emailValido = /^.+@.+\..{2,}$/;
        if(!emailValido.test(campo.val())) {
        	addErroCampo(campo , mensagens['alerta_preenchimento_invalido']);
			return false;
        } else {
			removeErroCampo(campo);
			return true;
		}
	}
	
	/**
	 * Funcao para verificar se um campo esta preenchido com um valor ou nao
	 * Parametros: campo -> campo para ser verificado
	 * */
	function validarCampo(campo){
		if(campo.val() == null || $.trim(campo.val()) == ""){
			addErroCampo(campo , mensagens['alerta_preenchimento_obrigatorio']);
			return false;
		} else {
			removeErroCampo(campo);
			return true;
		}
	}
	
	function addErroCampo(campo , mensagem){
		campo.addClass("ui-state-error");
		$('.textoErro').text(mensagem);
		$('.textoErro').show();
		campo.focus();
	}
	
	function removeErroCampo(campo){
		campo.removeClass("ui-state-error");
		$('.textoErro').hide();
	}
	
	function validarCampoSelect(campo){
		if(campo.select2("val") == null || campo.select2("val") == "" || campo.select2("val") == " "){
			addErroCampo(campo , mensagens['alerta_preenchimento_obrigatorio']);
			return false;
		} else {
			removeErroCampo(campo);
			return true;
		}
	}
	
	/**
	 * Funcao para verificar se a data informada e valida ou nao
	 * Parametros: campo -> campo para ser verificado
	 * */
	function validarCampoData(campo) {
		var date=campo.val();
		var ardt=new Array;
		var ExpReg=new RegExp("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/[12][0-9]{3}");
		ardt=date.split("/");
		erro=false;
		if ( date.search(ExpReg)==-1){
			erro = true;
			}
		else if (((ardt[1]==4)||(ardt[1]==6)||(ardt[1]==9)||(ardt[1]==11))&&(ardt[0]>30))
			erro = true;
		else if ( ardt[1]==2) {
			if ((ardt[0]>28)&&((ardt[2]%4)!=0))
				erro = true;
			if ((ardt[0]>29)&&((ardt[2]%4)==0))
				erro = true;
		}
		if (erro) {
			addErroCampo(campo , mensagens['alerta_preenchimento_obrigatorio']);
			return false;
		} else {
			removeErroCampo(campo);
        	return true;
		}
	}

	return {
		validarFormulario: validarFormulario,
		validarFormularioVisualizacao: validarFormularioVisualizacao,
		addErroCampo: addErroCampo, 
		removeErroCampo: removeErroCampo,
		validarCampoData: validarCampoData
	};

}();