<div class="windowAdmLogs">
	<div class="TitAdministracao">
		<h1>${msgs.txt_logs}</h1>
	</div>
	<div class="logs">
		
		<div  id="filtrosLogs">
			<div class="inlinebox">
				<label>${msgs.txt_data}: </label>
				<input type="text" class="datepicker" id="inputDataInicial" readonly="readonly" style="width: 70px; margin-right: 0px"/>
				<label style="margin-right: 0px"> ${msgs.txt_data_inicio_a_data_fim} </label>
				<input type="text" class="datepicker" id="inputDataFinal" readonly="readonly" style="width: 70px"/>
			</div>
			
			<div class="inlinebox">
				<label>${msgs.txt_usuarios}: </label>
				<select id="selectUsuarios" style="width: 150px"></select>
			</div>
			
			<div class="inlinebox">
				<label>${msgs.txt_mensagem}: </label>
				<input type="text" id="mensagemLogs"/>
			</div>
			
			<div class="inlinebox">
				<label>${msgs.txt_solucao}: </label>
				<select id="selectSolucao" style="width: 150px"></select>
			</div>
			
		</div>
		
		<div id="listaLogs">
			<table id="tabelaLogs"></table>
		</div>
		
	</div>
</div>