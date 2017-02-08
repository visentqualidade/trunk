<script type="text/javascript" src='${pageContext.request.contextPath}/dwr/interface/ExcecaoCGIAjax.js'></script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/redes/redes.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/redes/regionais.js'> </script>
<script type="text/javascript" src='${pageContext.request.contextPath}/js/web/redes/bilhetadores.js'> </script>

<div id="windowRegionais" class="windowAdmRedes inlinebox">
	<div class="TitAdministracao">
		<h1>${msgs.label_regionais}</h1>
	</div>
	<div class="regionais">	
		<div id="cadastRegio" class="inlinebox">
			<img id="btnAddRegional" class="inlinebox btnUser" src="${pageContext.request.contextPath}/images/addNew.png" title="Adicionar nova regional" />
			<input id="filtroListaRegio" type="text" class="filtro filtroReg" />
			<div id="listaRegio" style="overflow: auto">
				<table id="tabelaRegionais" style="width: 100%">
				</table>
			</div>
		</div>
	</div>
</div> 	
<div id="windowBilhetadores" class="windowAdmRedes inlinebox">
	<div class="TitAdministracao">
		<h1>${msgs.label_bilhetadores}</h1>
	</div>
	<div class="bilhetadores">
		<ul id="ulMenuSistemaRede">
			<li class="ativo" id="bilhetador">${msgs.label_centrais}</li>
			<li id="execao">${msgs.label_excecao_cgi}</li>
		</ul>
		<div id="divBilhetadores">
			<div id="filtroBilhet">
				<div class="inlinebox">
					<label>${msgs.label_bilhetador}: </label>
					<select id="selectBilhetador" style="width: 100px"></select>
				</div>
					
				<div class="inlinebox">
					<label>${msgs.txt_cn}: </label>
					<select id="selectBilhetadorCNBilhetador" style="width: 80px"></select>
				</div>
			</div>	
			
			<div id="listaBilhet">
				<table id="tabelaBilhetadores" style="width: 100%;"></table>
			</div>
		</div>
		<div id="divExecaoCGI" style="display: none;">
			<div id="filtroBilhet">
				<div class="inlinebox">
					<label>${msgs.txt_cn}: </label>
					<select id="selectBilhetadorCNExcecao" style="width: 80px"></select>
				</div>
				<div class="inlinebox" style="margin-left: 60%; cursor: pointer ;" title="${msgs.title_exportar_excel}">
					<img id="imgExportaXls" src="${pageContext.request.contextPath}/images/xls.png">
				</div>
			</div>	
			
			<div id="listaBilhet">
				<table id="tabelaExcecao" style="width: 100%;"></table>
			</div>
		</div>
	</div>
</div> 

<div class="estiloForm" id="dlgRegional" style="display: none">
	<form id="formRegional">
		<span class="textoErro"></span>
		<label> ${msgs.txt_regional}<span class="campoObrigatorio">*</span> </label>  <input id="nomeRegional" type="text" class="obrigatorio" maxlength="25" tipo="semespecial"/>
		<label> ${msgs.txt_descricao}<span class="campoObrigatorio">*</span></label>  <input id="descricaoRegional" type="text" class="obrigatorio" maxlength="100" tipo="semespecial"/>
		<label> ${msgs.txt_cns}<span class="campoObrigatorio">*</span> </label> 
		<select id="opcaoCN" style="width: 280px" class="obrigatorio" multiple="multiple"></select> 
	</form>
</div>