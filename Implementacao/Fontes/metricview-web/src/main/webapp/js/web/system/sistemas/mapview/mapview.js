$(function() {
	var QTD_TEMPO_ESPERANDO = 1000;
	
	$('#uploadAparelho').unbind('click');
	$('#uploadAparelho').click(function () {
		processandoDados(true);
		var imageData = dwr.util.getValue('inputFileAparelho');
		AparelhoAjax.uploadArquivoAparelho(imageData , function (mensagem) {
			setInterval(exibirMensagensUpload(mensagem) , QTD_TEMPO_ESPERANDO);
		});
	});
});

function exibirMensagensUpload (mensagem) {
	processandoDados(false);
	if(confirm(mensagem + "\n Tem certeza que deseja continuar ?")) {
		processandoDados(true);
		AparelhoAjax.realizarCargaDados(function(){
			processandoDados(false);
			if(confirm("Importação realizada com sucesso, emitir o relatório de importação?")) {
				processandoDados(true);
				AparelhoAjax.gerarRelatorioUpload(function(arquivo){
					processandoDados(false);
					dwr.engine.openInDownload(arquivo);
				});
			}
		});
	}
}