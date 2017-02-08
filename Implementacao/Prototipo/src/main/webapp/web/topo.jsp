<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<title>MetricView | Visent</title>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="x-ua-compatible" content="IE=9"/>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico"/>
<fmt:setBundle basename="/i18n/messages" var="mensagens"></fmt:setBundle>
<c:set var="msgs" value="${mensagens.resourceBundle}"></c:set>

<!-- internacionalizacao -->
<%@include file="/web/mensagensI18n.jsp" %>

<!-- DB -->
<%@include file="/web/db.jsp" %>
<!-- /DB -->

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilo.css" media="screen" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.10.1.custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datatable.css" media="screen" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/select2/select2.css" />

<!-- JQUERY -->
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery/jquery-ui-1.9.2.custom.min.js'> </script>
<!-- /JQUERY -->

<!-- DataTable -->
<script type="text/javaScript" src="${pageContext.request.contextPath}/js/componentes/datatable/jquery.dataTables.js"></script>
<!-- /DataTable -->

<!-- calendario -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/componentes/calendario/calendario.js"></script>
<!-- /calendario -->

<!-- componente select2 -->
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/select2/select2.js'> </script>
<!-- /componente select2 -->

<c:set var="req" value="${pageContext.request}" />
<c:set var="urlContextoCompleto" value="${fn:replace(req.requestURL, req.requestURI, '')}" />
</head>
<body>
	<div id="barraTopo">
		<div class="conteudoBarra">
			<div class="infoUser">
				<p class="usuario inlinebox"><b>${msgs.txt_usuario}:</b>Administrador</p>
				<ul id="menuUser" class="menuUser inlinebox">
					<li>
						<img id="imgArrow" src="${pageContext.request.contextPath}/images/arrow.png"/>
					</li>
				</ul>
				<ul class="subMenuUser" id="menuUsuario" style="margin-left: 140px;">
					<li class="liSubMenu" cursor="pointer" onClick="alterarDados();" id="alterarDados">${msgs.label_alterarDados}</li>
					<li class="liSubMenu" cursor="pointer" onClick="deslogar();" id="logout">${msgs.label_logout}</li>
				</ul>
			</div>
			<div class="trocaVisualizacao">
				<ul>
					<li id="visualizacao-painel">${msgs.txt_painel}</li>
					<li id="visualizacao-admin">${msgs.txt_administracao}</li>
					<li id="visualizacao-logs">${msgs.txt_logs}</li>
				</ul>
			</div>
		</div>
	</div>
<script type="text/javaScript">
	function deslogar() {
		window.location.href="${pageContext.request.contextPath}";
	}
	var txt_alterar_dados = '${msgs.title_alterar_dados}';
	function alterarDados() {
		$("#dlgAlterarDados").dialog({
			width: 700,
			heigth: 550,
			draggable: false,
			modal: true,
			resizable: false,
			title: txt_alterar_dados,
			buttons: {
				Salvar : function() {
					$(this).dialog("close");
				},
				Fechar : function() {
					$(this).dialog("close");
				}
			}
		});
	}
</script>
