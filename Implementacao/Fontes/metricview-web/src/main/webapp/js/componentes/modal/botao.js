/**
 * Objeto que ira representar o botao da modal
 * Parametros: descricao -> Nome do botao que sera apresentado para o usuario
 * 			   funcao -> Funcao que sera executada quando o botao for clicado
 * 
 *   Exemplo de uso.:
 *   		var botao = new BotaoModal ('Teste' , mostrarAlerta);
 */
var BotaoModal = function (descricao , funcao){
	this.descricao = descricao;
	this.funcao = funcao;
};