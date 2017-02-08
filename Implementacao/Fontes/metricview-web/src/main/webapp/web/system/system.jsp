<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>MetricView | Visent</title>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico"/>
<fmt:setBundle basename="/i18n/messages" var="mensagens"></fmt:setBundle>
<c:set var="msgs" value="${mensagens.resourceBundle}"></c:set>

<!-- internacionalizacao -->
<%@include file="/web/mensagensI18n.jsp" %>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilo.css" media="screen" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.10.1.custom.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/select2/select2.css" />

<!-- DWR -->
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/engine.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/util.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/ConstantesMetricViewAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/ReverseAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/ComboHelperAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/LoginAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/UsuarioAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/GrupoAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/FerramentaAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/autocomplete/autocomplete.js'> </script>
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
<!-- modal -->

<!-- validacao dos formularios -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/componentes/validacao/validacao.js"></script>
<!-- /validacao dos formularios -->

<!-- componente select2 -->
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/select2/select2.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/select2/comboHelper.js'> </script>
<!-- /componente select2 -->

<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/accordion/accordion.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/autocomplete/autocomplete.js'> </script>

<script type="text/javascript" src='${pageContext.request.contextPath}/js/maskedinput/jquery.maskedinput.min.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/maskedinput/mascara.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/watermark/jquery.watermark.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/funcoes.js'> </script>

<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/painel/painel.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/system/system.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/painel/permissao.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/usuario/permissaoUsuario.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/painel/manutencao.js'> </script>


<script type="text/javascript">
	var contexto = '${pageContext.request.contextPath}';
	
	ReverseAjax.configurarAjaxReverso();
	dwr.engine.setActiveReverseAjax(true);
	dwr.engine.setErrorHandler(errorHandler);
	function errorHandler(message, ex) {
		processandoDados(false);
		if(ex instanceof CorporativoException){
			Funcoes.exibirMensagem(ex.descricaoErro);
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
				<li class="liSubMenu" id="logout">${msgs.label_logout}</li>
			</ul>
		</div>
		<div class="trocaVisualizacaoSystem">
			<ul>
				<li id="visualizacao-painel" class="visualizacaoAtiva">Painel</li>
				<li id="visualizacao-configSistema">Config. Sistema</li>
			</ul>
		</div>
	</div>
</div>

<div id="content">
	<div id="logo"><img src="${pageContext.request.contextPath}/images/MetricView.png"></div>
	<div id="pageAdministracao" style="margin-top: 120px">
		<div class="windowAdm" style="margin: 0 auto;">
			<%@ include file="/web/manutencao.jsp" %>
		</div>
	</div>
	
	<div id="pageConfigSystem" class="inlinebox" style="display: none">
		<%@ include file="/web/system/configuracao.jsp" %>
	</div>
</div>



<%@ include file="/web/grupo/novoGrupo.jsp" %>
<%@ include file="/web/grupo/visualizarGrupos.jsp" %>
<%@ include file="/web/usuario/novoUsuario.jsp" %>
<%@ include file="/web/usuario/visualizarUsuarios.jsp" %>
<%@ include file="/web/rodape.jsp" %>

<div id="divProcessandoDados" style="display:none;">
Processando Informações
</div>