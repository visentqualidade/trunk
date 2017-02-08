<div id="pageAdministracao" class="inlinebox" style="display: inline-block;">
	<div id="selecionaPage">
		<ul>
			<li id="pageAdmUsuarios" class="pagAtiva">${msgs.txt_usuarios}</li>
			<li id="pageAdmRedes">${msgs.txt_redes}</li>
		</ul>
	</div>

	<div id="pageUsuarios">
		<div class="windowAdm inlinebox">
			<div class="TitAdministracao">
				<h1>${msgs.txt_manutencao}</h1>
			</div>
			<ul class="confirmAuter">
				<li id="cancelarManutencao" style="display: none;">${msgs.btn_cancelar}</li>
				<li id="salvarManutencao" style="display: none;">${msgs.btn_salvar}</li>
			</ul>
			<div class="manutencao">
				<div id="usuarios" class="inlinebox">
					<img class="inlinebox" src="/metricview/images/usuario.png">
					<h2 class="inlinebox">${msgs.txt_usuarios}</h2>
					<img id="btnAddUsuario" class="inlinebox btnUser" src="/metricview/images/addNew.png" title="Adicionar novo usuário">
					<img id="btnVisualizarUsuarios" class="inlinebox btnUser" src="/metricview/images/verLista.png" title="Visualizar Usuários">
					<input style="color: rgb(170, 170, 170);" id="filtroListaUsuarios" class="filtro" type="text" placeholder="Filtrar">
					<select class="listaUsuarios listboxFrom" multiple="multiple" id="selectComboUsuarios">
					</select>
				</div>
				<div class="inlinebox">
					<img class="imgenviar" id="enviarUsu" src="/metricview/images/enviar.png" title="Enviar"> 
					<br> 
					<img class="imgretirar" id="retirarUsu" src="/metricview/images/retirar.png" title="Retirar">
				</div>
				<div id="grupos" class="inlinebox">
					<img class="inlinebox" src="/metricview/images/grupo.png">
					<h2 class="inlinebox">${msgs.txt_grupos}</h2>
					<img id="btnAddGrupo" class="inlinebox btnUser"	src="/metricview/images/addNew.png" title="Adicionar novo grupo">
					<img id="btnVisualizarGrupos" class="inlinebox verGrupo btnUser" src="/metricview/images/verLista.png" title="Visualizar Grupos">
					<select id="selectComboGrupos" class="selectGrupo">
						<option id="gr1">Grupo 1</option>
						<option id="gr2">Grupo 2</option>
					</select>
					<select class="usuariosGrupo listboxTo" multiple="multiple" id="selectComboGruposAssociados">
					</select>
				</div>
			</div>

		</div>

		<div class="windowAdm inlinebox">
			<div class="TitAdministracao">
				<h1>${msgs.txt_permissoes}</h1>
			</div>
			<ul class="confirmAuter" style="margin-top: -19px;">
				<li style="display: none;" id="cancelarPermissoes">${msgs.btn_cancelar}</li>
				<li style="display: none;" id="salvarPermissoes">${msgs.btn_salvar}</li>
			</ul>
			<div class="permissoes">
				<ul id="ulMenuSistema">
					<li class="ativo" id="1">ControlView</li>
					<li id="2">EasyView</li>
					<li id="4">MapView</li>
					<li id="5">ItxView</li>
					<li id="3">Vips</li>
				</ul>
				<div id="selecao" class="inlinebox">
					<select id="selectTipo" class="selectUser">
						<option value="Selecione">Selecione</option>
						<option value="Grupos">${msgs.txt_grupos}</option>
						<option value="Usuários">${msgs.txt_usuarios}</option>
					</select> <br> 
					<select class="listaUser listboxFrom" multiple="multiple" id="selectPermissaoUsuario"></select>
				</div>
				<div class="inlinebox">
					<img class="imgenviar" src="/metricview/images/enviar.png" title="Enviar"><br> 
					<img class="imgretirar" src="/metricview/images/retirar.png" title="Retirar">
				</div>
				<div id="permissao" class="inlinebox">
					<select class="selectPermissao" id="comboSelectPermissao"><option
							value="1">${msgs.txt_administrador}</option>
						<option value="2">${msgs.txt_usuario}</option></select> <br> <select
						class="userPermitido listboxTo" multiple="multiple"
						id="selectPermissao"></select>
				</div>
			</div>
		</div>
	</div>

	<div id="pageRedes" style="display: none;">
		<div id="windowRegionais" class="windowAdmRedes inlinebox">
			<div class="TitAdministracao">
				<h1>${msgs.label_regionais}</h1>
			</div>
			<div class="regionais">
				<div id="cadastRegio" class="inlinebox">
					<img id="btnAddRegional" class="inlinebox btnUser" src="/metricview/images/addNew.png" title="Adicionar nova regional"> 
					<input style="color: rgb(170, 170, 170);" id="filtroListaRegio" class="filtro filtroReg" type="text" placeholder="Filtrar">
					<div id="listaRegio" style="overflow: auto">
						<table id="tabelaRegionais" width="100%">
							<thead>
								<tr>
									<th>${msgs.txt_col_nome}</th>
									<th>${msgs.txt_col_descricao}</th>
									<th>${msgs.txt_cns}</th>
									<th class="editar"></th>
									<th class="excluir"></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${msgs.txt_col_nome}</td>
									<td>${msgs.txt_col_descricao}</td>
									<td>${msgs.txt_cn}</td>
									<td class="editar editarRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
									<td class="excluir excluirRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
								</tr>
								<tr>
									<td>${msgs.txt_col_nome}</td>
									<td>${msgs.txt_col_descricao}</td>
									<td>${msgs.txt_cn}</td>
									<td class="editar editarRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
									<td class="excluir excluirRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
								</tr>
								<tr>
									<td>${msgs.txt_col_nome}</td>
									<td>${msgs.txt_col_descricao}</td>
									<td>${msgs.txt_cn}</td>
									<td class="editar editarRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
									<td class="excluir excluirRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
								</tr>
								<tr>
									<td>${msgs.txt_col_nome}</td>
									<td>${msgs.txt_col_descricao}</td>
									<td>${msgs.txt_cn}</td>
									<td class="editar editarRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
									<td class="excluir excluirRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
								</tr>
								<tr>
									<td>${msgs.txt_col_nome}</td>
									<td>${msgs.txt_col_descricao}</td>
									<td>${msgs.txt_cn}</td>
									<td class="editar editarRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
									<td class="excluir excluirRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
								</tr>
								<tr>
									<td>${msgs.txt_col_nome}</td>
									<td>${msgs.txt_col_descricao}</td>
									<td>${msgs.txt_cn}</td>
									<td class="editar editarRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
									<td class="excluir excluirRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
								</tr>
								<tr>
									<td>${msgs.txt_col_nome}</td>
									<td>${msgs.txt_col_descricao}</td>
									<td>${msgs.txt_cn}</td>
									<td class="editar editarRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
									<td class="excluir excluirRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
								</tr>
								<tr>
									<td>${msgs.txt_col_nome}</td>
									<td>${msgs.txt_col_descricao}</td>
									<td>${msgs.txt_cn}</td>
									<td class="editar editarRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
									<td class="excluir excluirRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
								</tr>
								<tr>
									<td>${msgs.txt_col_nome}</td>
									<td>${msgs.txt_col_descricao}</td>
									<td>${msgs.txt_cn}</td>
									<td class="editar editarRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
									<td class="excluir excluirRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
								</tr>
								<tr>
									<td>${msgs.txt_col_nome}</td>
									<td>${msgs.txt_col_descricao}</td>
									<td>${msgs.txt_cn}</td>
									<td class="editar editarRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
									<td class="excluir excluirRegio">
										<a href="#">
											<div></div>
										</a>
									</td>
								</tr>
							</tbody>
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
				<div id="filtroBilhet">
					<div class="inlinebox">
						<label>${msgs.label_bilhetador}: </label>
						<select id="selectBilhetador" style="width: 100px;">
							<option value=" ">Todos</option>
							<option>${msgs.label_bilhetador}</option>
							<option>${msgs.label_bilhetador}</option>
							<option>${msgs.label_bilhetador}</option>
							<option>${msgs.label_bilhetador}</option>
							<option>${msgs.label_bilhetador}</option>
						</select>
					</div>
					<div class="inlinebox">
						<label>${msgs.txt_cn}: </label> 
						<select id="selectBilhetadorCN" style="width: 80px;">
							<option value=" ">${msgs.label_select_todos}</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="24">24</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="31">31</option>
							<option value="32">32</option>
							<option value="33">33</option>
							<option value="34">34</option>
							<option value="35">35</option>
							<option value="37">37</option>
							<option value="38">38</option>
							<option value="41">41</option>
							<option value="42">42</option>
							<option value="43">43</option>
							<option value="44">44</option>
							<option value="45">45</option>
							<option value="46">46</option>
							<option value="47">47</option>
							<option value="48">48</option>
							<option value="49">49</option>
							<option value="51">51</option>
							<option value="53">53</option>
							<option value="54">54</option>
							<option value="55">55</option>
							<option value="61">61</option>
							<option value="62">62</option>
							<option value="63">63</option>
							<option value="64">64</option>
							<option value="65">65</option>
							<option value="66">66</option>
							<option value="67">67</option>
							<option value="68">68</option>
							<option value="69">69</option>
							<option value="71">71</option>
							<option value="73">73</option>
							<option value="74">74</option>
							<option value="75">75</option>
							<option value="77">77</option>
							<option value="79">79</option>
							<option value="81">81</option>
							<option value="82">82</option>
							<option value="83">83</option>
							<option value="84">84</option>
							<option value="85">85</option>
							<option value="86">86</option>
							<option value="87">87</option>
							<option value="88">88</option>
							<option value="89">89</option>
							<option value="91">91</option>
							<option value="92">92</option>
							<option value="93">93</option>
							<option value="94">94</option>
							<option value="95">95</option>
							<option value="96">96</option>
							<option value="97">97</option>
							<option value="98">98</option>
							<option value="99">99</option>
						</select>
					</div>
				</div>

				<div id="listaBilhet">
					<table id="tabelaBilhetadores" width="100%">
						<thead>
							<tr>
								<th>${msgs.txt_data}</th>
								<th>${msgs.label_bilhetador}</th>
								<th>${msgs.txt_cn}</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>dd/mm/aaaa</td>
								<td>${msgs.label_bilhetador}</td>
								<td>${msgs.txt_cn}</td>
							</tr>
							<tr>
								<td>dd/mm/aaaa</td>
								<td>${msgs.label_bilhetador}</td>
								<td>${msgs.txt_cn}</td>
							</tr>
							<tr>
								<td>dd/mm/aaaa</td>
								<td>${msgs.label_bilhetador}</td>
								<td>${msgs.txt_cn}</td>
							</tr>
							<tr>
								<td>dd/mm/aaaa</td>
								<td>${msgs.label_bilhetador}</td>
								<td>${msgs.txt_cn}</td>
							</tr>
							<tr>
								<td>dd/mm/aaaa</td>
								<td>${msgs.label_bilhetador}</td>
								<td>${msgs.txt_cn}</td>
							</tr>
							<tr>
								<td>dd/mm/aaaa</td>
								<td>${msgs.label_bilhetador}</td>
								<td>${msgs.txt_cn}</td>
							</tr>
							<tr>
								<td>dd/mm/aaaa</td>
								<td>${msgs.label_bilhetador}</td>
								<td>${msgs.txt_cn}</td>
							</tr>
							<tr>
								<td>dd/mm/aaaa</td>
								<td>${msgs.label_bilhetador}</td>
								<td>${msgs.txt_cn}</td>
							</tr>
							<tr>
								<td>dd/mm/aaaa</td>
								<td>${msgs.label_bilhetador}</td>
								<td>${msgs.txt_cn}</td>
							</tr>
							<tr>
								<td>dd/mm/aaaa</td>
								<td>${msgs.label_bilhetador}</td>
								<td>${msgs.txt_cn}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<!-- Dialog de cadastro de regional -->
		<div class="estiloForm" id="dlgRegional" style="display: none">
			<form id="formRegional">
				<label> ${msgs.txt_regional}<span class="campoObrigatorio">*</span></label>
				<input id="nomeRegional" maxlength="25" type="text"> 
				<label> ${msgs.txt_descricao} <span class="campoObrigatorio">*</span></label> 
				<input id="descricaoRegional" maxlength="100" type="text">
				<label>${msgs.txt_cns}<span class="campoObrigatorio">*</span>
				</label> <select tabindex="-1" id="opcaoCN" style="width: 280px;"
					class="obrigatorio select2-offscreen" multiple="multiple">
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
					<option value="16">16</option>
					<option value="17">17</option>
					<option value="18">18</option>
					<option value="19">19</option>
					<option value="21">21</option>
					<option value="22">22</option>
					<option value="24">24</option>
					<option value="27">27</option>
					<option value="28">28</option>
					<option value="31">31</option>
					<option value="32">32</option>
					<option value="33">33</option>
					<option value="34">34</option>
					<option value="35">35</option>
					<option value="37">37</option>
					<option value="38">38</option>
					<option value="41">41</option>
					<option value="42">42</option>
					<option value="43">43</option>
					<option value="44">44</option>
					<option value="45">45</option>
					<option value="46">46</option>
					<option value="47">47</option>
					<option value="48">48</option>
					<option value="49">49</option>
					<option value="51">51</option>
					<option value="53">53</option>
					<option value="54">54</option>
					<option value="55">55</option>
					<option value="61">61</option>
					<option value="62">62</option>
					<option value="63">63</option>
					<option value="64">64</option>
					<option value="65">65</option>
					<option value="66">66</option>
					<option value="67">67</option>
					<option value="68">68</option>
					<option value="69">69</option>
					<option value="71">71</option>
					<option value="73">73</option>
					<option value="74">74</option>
					<option value="75">75</option>
					<option value="77">77</option>
					<option value="79">79</option>
					<option value="81">81</option>
					<option value="82">82</option>
					<option value="83">83</option>
					<option value="84">84</option>
					<option value="85">85</option>
					<option value="86">86</option>
					<option value="87">87</option>
					<option value="88">88</option>
					<option value="89">89</option>
					<option value="91">91</option>
					<option value="92">92</option>
					<option value="93">93</option>
					<option value="94">94</option>
					<option value="95">95</option>
					<option value="96">96</option>
					<option value="97">97</option>
					<option value="98">98</option>
					<option value="99">99</option></select>
			</form>
		</div>
	</div>
</div>
<%@ include file="/web/grupo/novoGrupo.jsp"%>
<%@ include file="/web/grupo/visualizarGrupos.jsp"%>
<%@ include file="/web/usuario/novoUsuario.jsp" %>
<%@ include file="/web/usuario/visualizarUsuarios.jsp" %>