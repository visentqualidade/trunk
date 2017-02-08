/**
 * 
 */
var Manutencao = function (){
	
	var selector = '.manutencao';
	
	function associarGrupo(){
		
		$("#salvarManutencao"  ).show("slow");
		$("#cancelarManutencao").show("slow");
		Funcoes.bindFiltrarOption('#filtroListaUsuarios', '#selectComboUsuarios', 'assoc',$(selector+' .listboxFrom option:selected'));
		
		$(selector+' .listboxFrom option:selected').each(function() {
			
			var grupos = Painel.grupos;
			
			for (var i in grupos){
				
				if (grupos[i].nome == $('#selectComboGrupos').val()){
					for (j in Painel.usuarios){
						if (Painel.usuarios[j].nomeFormatado == $(this).val() ){
							if (Painel.usuarios[j].grupos == null){
								Painel.usuarios[j].grupos = new Array();
							}
							Painel.usuarios[j].grupos.push(grupos[i]);
						}
					}
				}
			}
			
			$(this).appendTo($(this).closest(selector).find('.listboxTo')); 
		});
		
	}
	
	
	function desassociarGrupo (){
		
		$("#salvarManutencao"  ).show("slow");
		$("#cancelarManutencao").show("slow");

		
		$(selector+' .listboxTo option:selected').each(function() {
		
		
			for (j in Painel.usuarios){
				if (Painel.usuarios[j].nomeFormatado == $(this).val() ){
					
					for (var i=0;i<Painel.usuarios[j].grupos.length ; i++){
						if (Painel.usuarios[j].grupos[i].nome == $('#selectComboGrupos').val()){
							Painel.usuarios[j].grupos.splice(i,1);
						}
					}
				}
			}
			
			$(this).appendTo($(this).closest(selector).find('.listboxFrom'));
			Funcoes.bindFiltrarOption('#filtroListaUsuarios', '#selectComboUsuarios', 'desas', $(this).val());
		});
	}
	
	function salvarAssociacaoGrupo(){
		Funcoes.loading('.manutencao', true);
		salvarAssociacaoGrupos();
		
		hideButtonsManutencao();
	}
	
	return {
		associarGrupo: associarGrupo,
		desassociarGrupo: desassociarGrupo,
		salvarAssociacaoGrupo: salvarAssociacaoGrupo,
		selector: selector
	};
	
}();