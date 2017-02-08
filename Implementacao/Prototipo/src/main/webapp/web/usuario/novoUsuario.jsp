<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="estiloForm" id="dlgNovoUsuario" style="display: none">
	<form id="formNovoUsuario">
		<div class="inlinebox formUsuario">
			<span class="textoErro"></span>
			<label> ${msgs.txt_login}<span class="campoObrigatorio">*</span> </label> 
			<input id="loginUsuario" type="text" class="obrigatorio" maxlength="25" minlength="6" tipo="semespecial"/>
			<label> ${msgs.txt_nome}<span class="campoObrigatorio">*</span> </label> 
			<input id="nomeUsuario" type="text" class="obrigatorio" maxlength="50" tipo="semespecial"/>
			<label> ${msgs.txt_email}<span class="campoObrigatorio">*</span> </label> 
			<input id="emailUsuario" type="text" class="obrigatorio" tipo="email" maxlength="60"/>
			<label> ${msgs.txt_telefone} </label> 
			<input id="telefoneUsuario" type="text" maxlength="25" />
		</div>
		<div class="inlinebox formUsuario" style="margin-right: 0px;">
			<label> ${msgs.txt_area} </label> 
			<input id="areaUsuario" type="text" maxlength="50" tipo="semespecial"/> 
			<label> ${msgs.txt_regional} </label> 
			<select id="regionalUsuario" style="width: 278px;">
			<option>Regional 1</option>
			<option>Regional 2</option>
			<option>Regional 3</option>
			</select>
			<label> ${msgs.txt_responsavel}	 </label> <input id="responsavelUsuario" type="text" maxlength="50" tipo="semespecial"/> 
		</div>
	</form>
</div>