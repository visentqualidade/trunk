/**
 * Objeto que ira encapsular o componente modal do jquery ui
 * 
 * Exemplo de Uso.:
 * 		var botoes = new Array(); // Olhar arquivo botao.js
 *		botoes.push(new BotaoModal('Salvar' , validarDadosDialogSalvar));
 *		botoes.push(new BotaoModal('Cancelar' , function() {$(this).dialog("close")}));
 *		Modal.iniciar('divTipoUsuario' , {width:510 , height:280 , show:'explode' , hide : 'explode' , botoes:botoes});
 */
var Modal = function (){
	
	/**
	 * Guarda as informacoes 'defaults' na criacao do plugin
	 */
	var opcoes ={
		title: '',
		autoOpen: false,
		height: 500,
		width: 500,
		modal: true,
		resizable: false,
		closeOnEscape: true,
		draggable: false,
		open: {},
		close: {},
		beforeClose: {},
		botoes: {}
	};
	
	/**
	 * Funcao para criar a modal
	 * Parametro: idCampo -> Id do campo que sera apresentado como modal
	 * 			  configuracoes -> Todas as configuracoes que existem no componente da modal do jquery ui
	 */
	function iniciar (idCampo , configuracoes){
		var config = $.extend(opcoes , configuracoes);
		var finalButtons = configurarBotoes(config);
		$("#"+idCampo).dialog({
			title: config.title,
			autoOpen: config.autoOpen , 
			height: config.height , 
			width: config.width , 
			modal: config.modal , 
			resizable: config.resizable , 
			show: config.show , 
			hide: config.hide , 
			closeOnEscape: config.closeOnEscape,
			draggable: config.draggable,
			buttons: finalButtons,
			close: config.close,
			beforeClose: config.beforeClose,
			open: config.open
		});
		$("#"+idCampo).dialog("open");
	}
	
	/**
	 * Funcao para criar a modal
	 * Parametro: idCampo -> Id do campo que sera apresentado como modal
	 * 			  configuracoes -> Todas as configuracoes que existem no componente da modal do jquery ui
	 */
	function iniciarSemAbrir (idCampo , configuracoes){
		var config = $.extend(opcoes , configuracoes);
		var finalButtons = configurarBotoes(config);
		$("#"+idCampo).dialog({
			title: config.title,
			autoOpen: config.autoOpen , 
			height: config.height , 
			width: config.width , 
			modal: config.modal , 
			resizable: config.resizable , 
			show: config.show , 
			hide: config.hide , 
			closeOnEscape: config.closeOnEscape,
			draggable: config.draggable,
			buttons: finalButtons,
			close: config.close,
			beforeClose: config.beforeClose,
			open: config.open
		});
	}
	
	/**
	 * Funcao para fechar a modal 
	 */
	function fechar (idCampo){
		$('#'+idCampo).dialog("close");
		$('#'+idCampo).dialog("destroy");
	}
	
	/**
	 * Funcao para fechar a modal 
	 */
	function fecharSemDestroy (idCampo){
		$('#'+idCampo).dialog("close");
	}
	
	
	//Funcao para criar os botoes que irao aparecer na modal
	function configurarBotoes(config){
		var finalButtons = new Array();
		for (var i in config.botoes) {
			var button = new Object();
			button['text'] = config.botoes[i].descricao;
			button['click'] = config.botoes[i].funcao;
			button['id'] = config.botoes[i].descricao;
			finalButtons.push(button);
		}
		return finalButtons;
	}
	
	return { 
		iniciar: iniciar,
		iniciarSemAbrir: iniciarSemAbrir,
		fechar: fechar,
		fecharSemDestroy: fecharSemDestroy
	};
	
}();