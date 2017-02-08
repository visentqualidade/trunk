<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- <script type="text/javascript" src='${pageContext.request.contextPath}/js/web/alterarDados.js'> </script> --%>

<style>
/* estilo para mostrar e esconder o 'X' da modal */
.no-close .ui-dialog-titlebar-close {display: none }
</style>

<div class="estiloForm" id="dlgAlterarDados" style="display: none">
	<form id="formAlterarDados">
		<span class="textoErro"></span>
		<div class="inlinebox formAlterarDados">
			<label> ${msgs.txt_login}<span class="campoObrigatorio">*</span> </label> <input id="loginDados" type="text" readonly="readonly" class="obrigatorio" maxlength="25" tipo="semespecial" value="admin"/>
			<label> ${msgs.txt_nome}<span class="campoObrigatorio">*</span> </label> <input id="nomeDados" type="text" class="obrigatorio" maxlength="50" tipo="semespecial" value="Administrador"/>
			<label> ${msgs.txt_email}<span class="campoObrigatorio">*</span> </label> <input id="emailDados" type="text" class="obrigatorio" tipo="email" maxlength="60" tipo="semespecial" value="admin@admin.com"/>
			<label> ${msgs.txt_telefone}</label> <input id="telefoneDados" type="text" maxlength="25"/>
		</div>
		<div class="inlinebox formAlterarDados" style="margin-right: 0px;">
			<label> ${msgs.txt_area}: </label> <input id="areaDados" type="text" maxlength="50" tipo="semespecial"/> 
			<label> ${msgs.txt_regional} </label> <select id="regionalDados"><option>Regional 1</option><option>Regional 2</option><option>Regional 3</option></select> 
			<label> ${msgs.txt_responsavel}: </label> <input id="responsavelDados" type="text" maxlength="50" tipo="semespecial"/> 
		</div>
		<hr style="clear: both">
		<label> ${msgs.txt_senha_antiga}: </label> <input id="senhaAntigaDados" type="password" /> 
		<label> ${msgs.txt_senha_nova}: </label> <input id="senhaNovaDados" type="password"/> 
		<label> ${msgs.txt_confirme_senha_nova}: </label> <input id="senhaNovaConfirmDados" type="password"/> 
	</form>
</div>