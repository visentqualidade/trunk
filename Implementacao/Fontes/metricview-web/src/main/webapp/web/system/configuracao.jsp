<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/system/configuracao.js'> </script>

<div id="selecionaPageSystem"> 
	<ul>
		<li id="liMetricView" class="pagAtiva">MetricView</li>
		<li id="liEasyView">EasyView</li>
		<li id="liControlView">ControlView</li>
		<li id="liMapView">MapView</li>
		<li id="liItxView">ItxView</li>
	</ul>
	
	<div id="pageMetricView" >
		<%@ include file="/web/system/sistemas/metricview/metricview.jsp" %>
	</div>
	
	<div id="pageEasyView" style="display: none">
		<%@ include file="/web/system/sistemas/easyview/easyview.jsp" %>
	</div>
	
	<div id="pageControlView" style="display: none">
		<%@ include file="/web/system/sistemas/controlview/controlview.jsp" %>
	</div>
	
	<div id="pageMapView" style="display: none">
		<%@ include file="/web/system/sistemas/mapview/mapview.jsp" %>
	</div>
	
	<div id="pageItxView" style="display: none">
		<%@ include file="/web/system/sistemas/itxview/itxview.jsp" %>
	</div>
</div>