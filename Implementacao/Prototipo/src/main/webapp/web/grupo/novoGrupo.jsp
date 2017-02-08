<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="estiloForm" id="dlgNovoGrupo" style="display: none">
	<form id="formNovoGrupo">
		<span class="textoErro"></span>
		<label> 
			${msgs.txt_grupo}<span class="campoObrigatorio">*</span>
		</label> 
		<input id="nomeGrupo" class="obrigatorio " type="text" maxlength="50" tipo="semespecial" />
		<label> 
			${msgs.txt_descricao_grupo}<span class="campoObrigatorio">*</span> 
		</label> 
		<textarea id="descricaoGrupo" class="descricao obrigatorio" rows="10" cols="255" maxlength="100" tipo="semespecial"></textarea>
	</form>
</div>