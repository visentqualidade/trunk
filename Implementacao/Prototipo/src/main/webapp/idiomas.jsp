<style>
	#i18n-component {
		position: fixed;
	    right: 0;
	    top: 0;
	    z-index: 99999;
	}
	#i18n-component li {
		float: left;
	}
</style>

<div id="i18n-component">
<ul>
	<li><a href="${pageContext.request.contextPath}/i18n.jsp?idioma=pt_BR"><img src="${pageContext.request.contextPath}/images/flag_br.png"/></a></li>
	<li><a href="${pageContext.request.contextPath}/i18n.jsp?idioma=en_US"><img src="${pageContext.request.contextPath}/images/flag_us.png"/></a></li>
</ul>
</div>