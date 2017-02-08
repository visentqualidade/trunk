<%@page import="br.com.visent.metricview.entidade.Usuario"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="br.com.visent.componente.dwr.util.SessaoUtil"%>
<%@ include file="/web/topo.jsp" %>

<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/LoginAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/index.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/componentes/validacao/validacao.js'> </script>

<script>
	$(function(){
		LoginAjax.buscarUsuarioSessao(function(usuario){
			Funcoes.redirecionarAplicacao(usuario);
		});		
	});
</script>


<div id="contentIndex">

	<fieldset>
		<legend> <img src="images/MetricView.png" width="120"/> </legend>
		<div id="login">
			<form action="javascript:void(0)" onsubmit="return logar()" id="formIndex">
				<label>${msgs.txt_usuario}:</label> <input id="inputUsuario" type="text" class="obrigatorio"/>
				<label>${msgs.txt_senha}:</label> <input id="inputSenha" type="password" class="obrigatorio"/>
				<input type="submit" value="Login" />
			</form>
			<span class="textoErro"></span>
		</div>
	</fieldset>
	
	<%@ include file="/web/rodape.jsp" %>

</div>

