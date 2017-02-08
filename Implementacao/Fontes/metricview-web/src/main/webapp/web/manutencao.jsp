	<div class="TitAdministracao">
		<h1>${msgs.txt_manutencao}</h1>
	</div>
	<ul class="confirmAuter">
		<li id="cancelarManutencao" style="display: none;">${msgs.btn_cancelar}</li>
		<li id="salvarManutencao" style="display: none;">${msgs.btn_salvar}</li>
	</ul>
	<div class="manutencao">
		<div id="usuarios" class="inlinebox">
			<img class="inlinebox" src="${pageContext.request.contextPath}/images/usuario.png" />
			<h2 class="inlinebox">${msgs.txt_usuarios}</h2>
			<img id="btnAddUsuario" class="inlinebox btnUser" src="${pageContext.request.contextPath}/images/addNew.png" title="${msgs.title_add_novo_usuario}" />
			<img id="btnVisualizarUsuarios" class="inlinebox btnUser" src="${pageContext.request.contextPath}/images/verLista.png" title="${msgs.title_visualizar_usuario}" />
			<input id="filtroListaUsuarios" type="text" class="filtro" />
<!-- 			<div class="listaUsuarios"> -->
<!-- 				<table class="listboxFrom"></table> -->
<!-- 			</div> -->
			<select class="listaUsuarios listboxFrom" multiple="multiple" id="selectComboUsuarios"></select>
		</div>
		<div class="inlinebox">
			<img class="imgenviar" src="${pageContext.request.contextPath}/images/enviar.png" title="${msgs.title_enviar}"/>
			<br>
			<img class="imgretirar" src="${pageContext.request.contextPath}/images/retirar.png" title="${msgs.title_retirar}"/>
		</div>
		<div id="grupos" class="inlinebox">
			<img class="inlinebox" src="${pageContext.request.contextPath}/images/grupo.png" />
			<h2 class="inlinebox">${msgs.txt_grupos}</h2>
			<img id="btnAddGrupo" class="inlinebox btnUser" src="${pageContext.request.contextPath}/images/addNew.png" title="${msgs.title_add_novo_grupo}" />
			<img id="btnVisualizarGrupos" class="inlinebox verGrupo btnUser" src="${pageContext.request.contextPath}/images/verLista.png" title="${msgs.title_visualizar_grupo}" />
			<select id="selectComboGrupos" class="selectGrupo"></select>
			<select class="usuariosGrupo listboxTo" multiple="multiple" id="selectComboGruposAssociados"></select>
		</div>
	</div>
