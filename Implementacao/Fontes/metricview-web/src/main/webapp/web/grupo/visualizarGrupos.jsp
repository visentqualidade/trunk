<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/grupo/visualizarGrupos.js'> </script>

<div id="dlgVisualizarGrupos" style="display: none">
	<span class="textoErro"></span>
	<div style="margin-top: 10px">
		<input id="filtroVisualizacaoGrupos" type="text" class="filtro" />
		<div class="listaVisualizar">
			<table id="tabelaGrupos" style="width: 100%">
			</table>
		</div>
	</div>
</div>