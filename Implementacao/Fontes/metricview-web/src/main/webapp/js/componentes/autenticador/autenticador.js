/**
 *	Classe para verificar se o usuario esta conectado nas aplicacoes. Ela sera utilizada somente nas aplicacoes filhas do metricview
 */
(function(){
'use strict'
	
	window.Visent  = {
			
			AutenticadorFilter : {
				/*Variavel para controlar de quanto em quanto tempo a aplicacao ira ficar 'pingando' no servidor*/
				tempoPolling : 5000, 
				
				/**
				 * Funcao para verificar se o usuario pode acessa a aplicacao
				 */
				verificaUsuarioConectado : function (){
					setInterval(function() {
						VerificaUsuarioLogadoAjax.isUsuarioConectado(function(isConectado){
							if(!isConectado){
								Modal.iniciar("loadingDesconectado" , {
										width:800, height:100, botoes:{}, 
										title: mensagens['title_aviso'], 
										closeOnEscape: false,
										open: function(event, ui) {
											$(this).closest('.ui-dialog').find(".ui-dialog-titlebar-close").hide();
									}
								});
							}
						});
					} , Visent.AutenticadorFilter.tempoPolling);
				}
			},
			
	};
	
})();
