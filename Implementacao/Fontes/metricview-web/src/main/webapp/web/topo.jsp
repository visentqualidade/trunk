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

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilo.css" media="screen" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.10.1.custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datatable.css" media="screen" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/select2/select2.css" />

<!-- DWR -->
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/engine.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/util.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/ReverseAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/ComboHelperAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/ConstantesMetricViewAjax.js'></script>
<!-- /DWR -->

<!-- JQUERY -->
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery/jquery-ui-1.9.2.custom.min.js'> </script>
<!-- /JQUERY -->

<!-- jgrowl -->
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/jgrowl/jquery.jgrowl.js'> </script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jgrowl/jquery.jgrowl.css" />
<!-- /jgrowl -->

<!-- modal -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/componentes/modal/botao.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/componentes/modal/modal.js"></script>
<!-- /modal -->

<!-- validacao dos formularios -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/componentes/validacao/validacao.js"></script>
<!-- /validacao dos formularios -->

<!-- calendario -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/componentes/calendario/calendario.js"></script>
<!-- /calendario -->

<script type="text/javascript" src="${pageContext.request.contextPath}/js/componentes/datatable/jquery.dataTables.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/componentes/datatable/visent.dataTables.js"></script>
<!-- componente de mascara -->
<script type="text/javascript" src='${pageContext.request.contextPath}/js/maskedinput/jquery.maskedinput.min.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/maskedinput/mascara.js'> </script>
<!-- /componente de mascara -->

<!-- componente select2 -->
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/select2/select2.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/select2/comboHelper.js'> </script>
<!-- /componente select2 -->

<script type="text/javascript" src='${pageContext.request.contextPath}/js/watermark/jquery.watermark.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/funcoes.js'> </script>

<c:set var="req" value="${pageContext.request}" />
<c:set var="urlContextoCompleto" value="${fn:replace(req.requestURL, req.requestURI, '')}" />

<script type="text/javascript">
	
	var contexto = '${pageContext.request.contextPath}';
	var urlContextoCompleto = '${urlContextoCompleto}';
	
	$.fn.dataTableExt.sErrMode = 'throw';
	ReverseAjax.configurarAjaxReverso();
	dwr.engine.setActiveReverseAjax(true);
	dwr.engine.setErrorHandler(errorHandler);
	function errorHandler(message, ex) {
		if(ex instanceof CorporativoException){
			Funcoes.exibirMensagem(ex.descricaoErro);
		} else if(ex.name == 'dwr.engine.http.0' && ex.message == ''){
			Visent.AutenticadorFilter.abrirModalDesconectado(mensagens['mensagem_erro_servidor_fora_do_ar']);
		} else {
			Funcoes.exibirMensagem(mensagens['mensagem_erro_ocorreu_erro_inesperado']);
		}
		console.log(message + " - Detalhe: " + dwr.util.toDescriptiveString(ex, 2));
	}
	
	//Funcao para desconectar o usuario da aplicacao
	function desconectaUsuario (){
		window.location.reload(true);
	}
	

</script>
</head>
<body>

<c:if test="${usuario != null}">
	<div id="barraTopo">
		<div class="conteudoBarra">
			<div class="infoUser">
				<p class="usuario inlinebox"><b>${msgs.txt_usuario}:</b> ${usuario.nome} </p>
				<ul class="menuUser inlinebox">
					<li>
						<img id="imgArrow" src="${pageContext.request.contextPath}/images/arrow.png"/>
					</li>
				</ul>
				<ul class="subMenuUser">
					<li class="liSubMenu" id="alterarDados">${msgs.label_alterarDados}</li>
					<li class="liSubMenu" id="logout">${msgs.label_logout}</li>
				</ul>
			</div>
<!-- 			<div id="visualizacao-ajuda"> -->
<!-- 				<a href="javascript:void(0)" id="ajuda">  -->
<%-- 					<img title="${msgs.title_ajuda}" src="${pageContext.request.contextPath}/images/ajuda.png">  --%>
<!-- 				</a> -->
<!-- 			</div> -->
			<div class="trocaVisualizacao">
				<ul>
					<li id="visualizacao-painel" class="visualizacaoAtiva">${msgs.txt_painel}</li>
					<c:if test="${usuario.admin}">
						<li id="visualizacao-admin">${msgs.txt_administracao}</li>
						<li id="visualizacao-logs">${msgs.txt_logs}</li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</c:if>
