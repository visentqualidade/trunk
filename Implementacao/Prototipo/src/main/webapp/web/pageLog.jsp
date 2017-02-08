<div id="pageLogs" class="inlinebox" style="display: inline-block;">
	<div class="windowAdmLogs">
		<div class="TitAdministracao">
			<h1>Logs</h1>
		</div>
		<div class="logs">
			<div id="filtrosLogs">
				<div class="inlinebox">
					<label for="inputDataInicial">${msgs.txt_data}: </label> 
					<input id="inputDataInicial" readonly="readonly" style="width: 70px; margin-right: 0px" type="text">
				    <label for="inputDataFinal"> ${msgs.txt_data_inicio_a_data_fim}&nbsp;</label>
					<input id="inputDataFinal" readonly="readonly" style="width: 70px" type="text">
				</div>

				<div class="inlinebox">
					<label>${msgs.txt_usuarios}: </label>
					<select id="selectUsuarios" style="width: 150px">
						<option value=" ">${msgs.label_select_todos}</option>
						<option value="u1">Usuário 1</option>
						<option value="u1">Usuário 2</option>
						<option value="u1">Usuário 3</option>
						<option value="u1">Usuário 4</option>
						<option value="u1">Usuário 5</option>
						<option value="u1">Usuário 6</option>
						<option value="u1">Usuário 7</option>
						<option value="u1">Usuário 8</option>
						<option value="u1">Usuário 9</option>
						<option value="u1">Usuário 10</option>
					</select>
				</div>
				<div class="inlinebox">
					<label>${msgs.txt_mensagem}: </label> <input id="mensagemLogs" type="text">
				</div>

				<div class="inlinebox">
					<label>${msgs.txt_solucao}: </label>
					<select id="selectSolucao" style="width: 150px">
						<option value=" ">${msgs.label_select_todas}</option>
						<option value="DashBoard">ControlView</option>
						<option value="EasyView">EasyView</option>
						<option value="ItxView">ItxView</option>
						<option value="Portal">Portal</option>
					</select>
				</div>
			</div>
			<div id="listaLogs">
				<table id="tabelaLogs" width="100%">
					<thead>
						<tr>
							<th>${msgs.txt_col_data_hora}</th>
							<th>${msgs.txt_col_usuario}</th>
							<th>${msgs.txt_col_mensagem}</th>
							<th>${msgs.txt_col_solucao}</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>dd/mm/aaa hh:mm</td>
							<td>Usuário 1</td>
							<td>Mensagem Mensagem Mensagem Mensagem Mensagem</td>
							<td>Solução</td>
						</tr>
						<tr>
							<td>dd/mm/aaa hh:mm</td>
							<td>Usuário 1</td>
							<td>Mensagem Mensagem Mensagem Mensagem Mensagem</td>
							<td>Solução</td>
						</tr>
						<tr>
							<td>dd/mm/aaa hh:mm</td>
							<td>Usuário 1</td>
							<td>Mensagem Mensagem Mensagem Mensagem Mensagem</td>
							<td>Solução</td>
						</tr>
						<tr>
							<td>dd/mm/aaa hh:mm</td>
							<td>Usuário 1</td>
							<td>Mensagem Mensagem Mensagem Mensagem Mensagem</td>
							<td>Solução</td>
						</tr>
						<tr>
							<td>dd/mm/aaa hh:mm</td>
							<td>Usuário 1</td>
							<td>Mensagem Mensagem Mensagem Mensagem Mensagem</td>
							<td>Solução</td>
						</tr>
						<tr>
							<td>dd/mm/aaa hh:mm</td>
							<td>Usuário 1</td>
							<td>Mensagem Mensagem Mensagem Mensagem Mensagem</td>
							<td>Solução</td>
						</tr>
						<tr>
							<td>dd/mm/aaa hh:mm</td>
							<td>Usuário 1</td>
							<td>Mensagem Mensagem Mensagem Mensagem Mensagem</td>
							<td>Solução</td>
						</tr>
						<tr>
							<td>dd/mm/aaa hh:mm</td>
							<td>Usuário 1</td>
							<td>Mensagem Mensagem Mensagem Mensagem Mensagem</td>
							<td>Solução</td>
						</tr>
						<tr>
							<td>dd/mm/aaa hh:mm</td>
							<td>Usuário 1</td>
							<td>Mensagem Mensagem Mensagem Mensagem Mensagem</td>
							<td>Solução</td>
						</tr>
						<tr>
							<td>dd/mm/aaa hh:mm</td>
							<td>Usuário 1</td>
							<td>Mensagem Mensagem Mensagem Mensagem Mensagem</td>
							<td>Solução</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>