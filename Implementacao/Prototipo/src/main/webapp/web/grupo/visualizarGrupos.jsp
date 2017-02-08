<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div id="dlgVisualizarGrupos" style="display: none">
	<span class="textoErro"></span>
	<div style="margin-top: 10px">
		<input id="filtroVisualizacaoGrupos" type="text" class="filtro" placeholder="Filtrar"/>
		<div class="listaVisualizar">
			<table id="tabelaGrupos" style="width: 100%">
				<thead>
					<th>Grupo*</th>
					<th>Descrição*</th>
					<th></th>
					<th></th>
				</thead>
				<tbody>
					<tr>
						<td>Grupo 1</td>
						<td>Descrição Descrição Descrição</td>
						<td class="editar"></td>
						<td class="excluir"></td>
					</tr>
					<tr>
						<td>Grupo 2</td>
						<td>Descrição Descrição Descrição</td>
						<td class="editar"></td>
						<td class="excluir"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>