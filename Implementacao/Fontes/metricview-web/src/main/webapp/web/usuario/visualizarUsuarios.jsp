<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="dlgVisualizarUsuarios" style="display: none">
	<span class="textoErro"></span>
	<div style="margin-top: 10px">
		<input id="filtroVisualizacaoUsuarios" type="text" class="filtro" />
		<div class="usuariosConectados">
			<input type="checkbox" id="checkSomenteConectado"> <label for="checkSomenteConectado">${msgs.txt_somente_usuario_conectado}</label>
		</div>
		<div class="listaVisualizarUsuarios">
			<table id="tabelaUsuarios" style="width: 100%">
			</table>
		</div>
	</div>
</div>

<div id="dlgExibirPermissoes" style="display: none">

	<div id="accordionPermissoes"></div>

</div>