<%@ include file="/web/topo.jsp" %>

<!-- import dos DWR -->
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/LoginAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/UsuarioAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/GrupoAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/FerramentaAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/BilhetadorAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/RegionalAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/PermissaoAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/LogAjax.js'></script>
<!-- /import dos DWR -->

<!-- import dos JS -->
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/accordion/accordion.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/autocomplete/autocomplete.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/painel/permissao.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/painel/painel.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/painel/manutencao.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/usuario/permissaoUsuario.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/usuario/visualizarUsuarios.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/log/logs.js'> </script>
<!-- /import dos JS -->

<c:set var="classNoAdm" value="noAdm${usuario.permissoes.size()}"/>

<div id="content">
	<div id="logo"><img src="${pageContext.request.contextPath}/images/MetricView.png"></div>
	<div class="nomenclatura"> PORTAL DE TRÁFEGO E QUALIDADE</div>
	<div id="pageFerramentas" class="inlinebox"></div>
	
	<c:if test="${usuario.admin}">
		<div id="pageAdministracao" class="inlinebox" style="display: none">
			<div id="selecionaPage"> 
				<ul>
					<li id="pageAdmUsuarios" class="pagAtiva">${msgs.txt_usuarios}</li>
					<li id="pageAdmRedes">${msgs.txt_redes}</li>
				</ul>
			</div>
			
			<div id="pageUsuarios">
				<div class="windowAdm inlinebox">
					<%@ include file="/web/manutencao.jsp" %>
				</div>
				
				<div class="windowAdm inlinebox">
					<div class="TitAdministracao">
						<h1>${msgs.txt_permissoes}</h1>
					</div>
					<ul class="confirmAuter" style="margin-top: -19px;">
						<li id="cancelarPermissoes">${msgs.txt_cancelar}</li>
						<li id="salvarPermissoes">${msgs.txt_salvar}</li>
					</ul>
					<div class="permissoes">
						<ul id="ulMenuSistema"></ul>
						<div id="selecao" class="inlinebox">
							<select id="selectTipo" class="selectUser">
								<option value="${msgs.txt_selecione}">${msgs.txt_selecione}</option>
								<option value="${msgs.txt_grupos}">${msgs.txt_grupos}</option>
								<option value="${msgs.txt_usuarios}">${msgs.txt_usuarios}</option>
							</select>
							<br>
							<select class="listaUser listboxFrom" multiple="multiple" id="selectPermissaoUsuario"></select>
						</div>
						<div class="inlinebox">
							<img class="imgenviar" src="${pageContext.request.contextPath}/images/enviar.png" title="Enviar"/>
							<br>
							<img class="imgretirar" src="${pageContext.request.contextPath}/images/retirar.png" title="Retirar"/>
						</div>
						<div id="permissao" class="inlinebox">
							<select class="selectPermissao" id="comboSelectPermissao"></select>
							<br>
							<select class="userPermitido listboxTo" multiple="multiple" id="selectPermissao"></select>
						</div>
					</div>
				</div> 
			</div>
			
			<div id="pageRedes" style="display: none;">
				<%@ include file="/web/redes.jsp" %>
			</div>
			
		</div>
		
		<div id="pageLogs" class="inlinebox" style="display: none">
			<%@ include file="/web/logs.jsp" %>
		</div>
	</c:if>
</div>

<div id="avisoAjuda" style="display: none;">
	${msgs.label_duvida_recurso_disponivel_video}
</div>
<div id="dlgAjuda" style="display: none">
	<span class="ui-helper-hidden-accessible"><input type="text"/></span>
	<a class="helpPdf" href="${pageContext.request.contextPath}/help/metricview.pdf" target="_blank">
		<img src="${pageContext.request.contextPath}/images/pdf.png"/>
		${msgs.txt_ver_pdf}
	</a>
	<video class="videos" id="videometric" width="650" controls="controls">
	 	<source src="${pageContext.request.contextPath}/help/metricview.mp4" type="video/mp4">
		${msgs.alert_video_nao_roda_html }
	</video>
</div>

<%@ include file="/web/alterarDados.jsp" %>
<%@ include file="/web/grupo/novoGrupo.jsp" %>
<%@ include file="/web/grupo/visualizarGrupos.jsp" %>
<%@ include file="/web/usuario/novoUsuario.jsp" %>
<%@ include file="/web/usuario/visualizarUsuarios.jsp" %>
<%@ include file="/web/rodape.jsp" %>