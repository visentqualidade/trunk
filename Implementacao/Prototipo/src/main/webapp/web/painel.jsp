<%@ include file="/web/topo.jsp"%>

<script type="text/javaScript" src="${pageContext.request.contextPath}/js/painel.js"></script>

<style type="text/css">
.window {
	cursor: pointer;
}
</style>

<div id="content">
	<div id="logo">
		<img src="${pageContext.request.contextPath}/images/MetricView.png">
	</div>
<!-- 	<div class="nomenclatura">PORTAL DE TRÁFEGO E QUALIDADE</div> -->
	<%@ include file="/web/pageLog.jsp" %>
	<%@ include file="/web/administracao.jsp" %>
	<%@ include file="/web/ferramentas.jsp" %>
	<%@ include file="/web/alterarDados.jsp"%>
</div>

<%@ include file="/web/rodape.jsp"%>