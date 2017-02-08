<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilo.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.10.1.custom.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datatable.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/select2/select2.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jgrowl/jquery.jgrowl.css" />
		<script type="text/javascript" src='${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js'> </script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/jgrowl/jquery.jgrowl.js'> </script>
		<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico"/>
		<script>
			function logar() {
				if (($("#inputUsuario").val() == "") || ($("#inputSenha").val() == "") || ($("#inputUsuario").val() != "admin") || ($("#inputSenha").val() != "admin")) {
					$.jGrowl("Usuário ou senha inválidos");
				} else {
					window.location.href="${pageContext.request.contextPath}/app/painel";
				}
			}
		</script>
		<style type="text/css">
			footer {
				position: relative !important;
			}
		</style>
		<title>MetricView | Visent</title>
	</head>
	<body onload="document.getElementById('inputUsuario').focus()">
	<%@ include file="idiomas.jsp" %>
	<div id="contentIndex">
		<fieldset>
			<legend> <img src="images/MetricView.png" width="120"/> </legend>
			<div id="login">
				<form action="javascript:void(0)" onsubmit="return logar()" id="formIndex">
					<label><fmt:message key="txt_usuario"/>:</label> <input id="inputUsuario" type="text" class="obrigatorio"/>
					<label><fmt:message key="txt_senha"/>:</label> <input id="inputSenha" type="password" class="obrigatorio"/>
					<input type="submit" value="Login" />
				</form>
				<span class="textoErro"></span>
			</div>
		</fieldset>
		<%@ include file="/web/rodape.jsp" %>
	</div>
