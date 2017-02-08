$(document).ready(function() {
		
		//aplicando select2 nos campos
		$("#selectBilhetador").select2();
		$("#selectBilhetadorCN").select2();
		$("#selectUsuarios").select2();
		$("#selectSolucao").select2();
		$("#opcaoCN").select2();
		
		//configurando dialogs
		$("#dlgRegional").dialog({ //regional
			autoOpen: false,
			title: "Cadastrar Regional",
			resizable: false,
			draggable: false,
			modal: true,
			width: 340,
			buttons: {
				Salvar: function() {
					$(this).dialog("close");
				},
				"Salvar e Fechar": function() {
					$(this).dialog("close");
				},
				Fechar: function() {
					$(this).dialog("close");
				}
			}
		});
		$("#dlgVisualizarUsuarios").dialog({ //visualizar usuario
			title: "visualizar Usuários",
			autoOpen: false,
			width: 987,
			modal: true,
			resizable: false,
			draggable: false,
			buttons: {
				Fechar: function() {
					$(this).dialog("close");
				}
			}
		});
		$("#dlgNovoUsuario").dialog({ //cadastrar usuario
			title: "Cadastrar Usuário",
			autoOpen: false,
			modal: true,
			resizable: false,
			draggable: false,
			width: 700,
			buttons: {
				Salvar: function() {
					$(this).dialog("close");
				},
				"Salvar e Fechar": function() {
					$(this).dialog("close");
				},
				Cancelar: function() {
					$(this).dialog("close");
				}
			}
			
		});
		$("#dlgVisualizarGrupos").dialog({ //visualizar grupos
			title: "Visualizar Grupos",
			autoOpen: false,
			width: 615,
			modal: true,
			resizable: false,
			draggable: false,
			buttons: {
				Fechar: function() {
					$(this).dialog("close");
				}
			}
		});
		$("#dlgNovoGrupo").dialog({ //cadastrar grupos
			title: "Cadastrar Grupo",
			autoOpen: false,
			width: 340,
			modal: true,
			resizable: false,
			draggable: false,
			buttons: {
				Salvar: function() {
					$(this).dialog("close");
				},
				"Salvar e Fechar": function() {
					$(this).dialog("close");
				},
				"Cancelar": function() {
					$(this).dialog("close");
				}
			}
		});
		$("#dlgExibirPermissoes").dialog({
			title: "Permissões",
			modal: true,
			resizable: false,
			draggable: false,
			autoOpen: false,
			width: 539,
			height: 354,
			buttons: {
				Fechar: function() {
					$(this).dialog("close");
				}
			}
		});
		
		//configurando datatable
		$("#tabelaLogs").dataTable({ //tabela de logs
			bLengthChange: false,
			bFilter: false,
			bJQueryUI: false
		});
		$("#tabelaBilhetadores").dataTable({ //tabela de bilhetadores
			bLengthChange: false,
			bFilter: false,
			bJQueryUI: false
		});
		$("#tabelaRegionais").dataTable({ //tabela de regionais
			bLengthChange: false,
			bFilter: false,
			bJQueryUI: false,
			aoColumns: [
			            null,
			            null,
			            null,
			            {"bSortable": false},
			            {"bSortable": false}
			]
		});
		$("#tabelaGrupos").dataTable({
			bLengthChange: false,
			bFilter: false,
			bPaginate: false,
			bSort: false,
			bInfo: false
		});
		$("#tabelaUsuarios").dataTable({
			bLengthChange: false,
			bFilter: false,
			bPaginate: false,
			bSort: false,
			bInfo: false
		});
		
		//configurando calendario
		$.datepicker.setDefaults($.datepicker.regional['pt-BR']);
		$("#inputDataFinal").datepicker({
			 changeMonth: true,
			 changeYear: true
		});
		$("#inputDataInicial").datepicker({
			 changeMonth: true,
			 changeYear: true
		});
		
		//funcoes diversas
		$(".excluirRegio").click(function() { //confirmacao de exclusao de regional
			confirm("Tem certeza que deseja excluir?");
		});
		$("#tabelaGrupos>tbody>tr>td.excluir").click(function() { //confirmacao de exclusao de grupo
			confirm("Tem certeza de que deseja excluir?")
		});
		$("#tabelaUsuarios>tbody>tr>td.excluir").click(function() { //confirmacao de exclusao de grupo
			confirm("Tem certeza de que deseja excluir?")
		});
		$("#tabelaUsuarios>tbody>tr>td.desconectar").click(function() { //confirmacao para desconectar o usuario
			confirm("Tem certeza de que deseja desconectar o usuário?");
		});
		$("#tabelaUsuarios>tbody>tr>td.resetarSenha").click(function() { //confirmacao para resetar a senha do usuario
			confirm("Tem certeza de que deseja resetar a senha?");
		});
		$("#tabelaGrupos>tbody>tr>td.editar").click(function() { //funcao para editar grupo no grid de visualizacao
			if (!$(this).attr("style")) {
				$(this).closest("tr").find("td").each(function() {
					if ($(this).attr("class") == " ") {
						$(this).html("<input type='text' value='"+$(this).text()+"' size='"+$(this).text().length+"'>")
					}
				});
				$(this).attr("style","background: url(/metricview/images/check.png) no-repeat scroll center center transparent;");
			} else {
				$(this).closest("tr").find("td").each(function() {
					if ($(this).attr("class") == " ") {
						console.log($(this).find("input"));
						$(this).html("<td class=' '>"+$(this).find("input").attr("value")+"</td>");
					}
				});
				$(this).attr("style","");
			}
		});
		$("#tabelaUsuarios>tbody>tr>td.editar").click(function() { //funcao para editar usuario no grid de visualizacao
			if (!$(this).attr("style")) {
				$(this).closest("tr").find("td").each(function() {
					if ($(this).attr("class") == " ") {
						$(this).html("<input type='text' value='"+$(this).text()+"' size='"+$(this).text().length+"'>")
					}
				});
				$(this).attr("style","background: url(/metricview/images/check.png) no-repeat scroll center center transparent;");
			} else {
				$(this).closest("tr").find("td").each(function() {
					if ($(this).attr("class") == " ") {
						console.log($(this).find("input"));
						$(this).html("<td class=' '>"+$(this).find("input").attr("value")+"</td>");
					}
				});
				$(this).attr("style","");
			}
		});
		$(".editarRegio").click(function() { //editar regional
			$("#nomeRegional").empty();
			$("#descricaoRegional").empty();
			$("#nomeRegional").attr("value","Nome");
			$("#descricaoRegional").attr("value","Descrição");
			$("#dlgRegional").dialog("open");
		});
		$("#menuUser").click(function() { //efeito do menu usuario
			$("#menuUsuario").toggle("slide",{direction:"up"},150,function() {
				if ($("#imgArrow").attr("src") == "/metricview/images/arrow-up.png") {
					$("#imgArrow").attr("src","/metricview/images/arrow.png");
				} else {
					$("#imgArrow").attr("src","/metricview/images/arrow-up.png");
				}
			});
		});
		
		//configurando paginas principais
		$("#pageAdministracao").hide(); //esconde pagina de administracao
		$("#pageLogs").hide(); //esconde pagina de log
		$("#pageRedes").hide(); //esconde pagina de redes
		
		//efeitos
		$("#accordionPermissoes").accordion({
			heightStyle: "content"
		});
		$("#visualizacao-painel").addClass("visualizacaoAtiva"); //efeito menu de paginas
		$("#pageAdmRedes").click(function() { //efeito de ransicao pagina de redes
			$("#pageAdmRedes").addClass("pagAtiva");
			$("#pageAdmUsuarios").removeClass("pagAtiva");
			$("#pageUsuarios").hide();
			$("#pageRedes").show("slide",{direction: "down"});
		});
		$("#pageAdmUsuarios").click(function() { //efeito de transicao pagina de usuarios
			$("#pageAdmUsuarios").addClass("pagAtiva");
			$("#pageAdmRedes").removeClass("pagAtiva");
			$("#pageUsuarios").show("slide",{direction: "up"});
			$("#pageRedes").hide();
		});
		$("#visualizacao-painel").click(function() { //efeito pagina de ferramentas
			$("[id^='visualizacao']").removeClass();
			$(this).addClass("visualizacaoAtiva");
			$("#pageAdministracao").hide();
			$("#pageLogs").hide();
			$("#pageFerramentas").show("slide");
		});
		$("#visualizacao-admin").click(function() { //visualizacao pagina de administracao
			var lista = $("[id^='visualizacao']");
			$("#pageFerramentas").hide();
			$("#pageLogs").hide();
			$.each(lista, function(key, value) {
				if (value.getAttribute("class") == "visualizacaoAtiva") {
					if (key == 0) {
						$("#pageAdministracao").show("slide",{direction: "rigth"});
					} else {
						$("#pageAdministracao").show("slide",{direction: "left"});
					}
				}
			});
			$("[id^='visualizacao']").removeClass();
			$(this).addClass("visualizacaoAtiva");
		});
		$("#visualizacao-logs").click(function() { //visualizacao pagina de logs
			$("[id^='visualizacao']").removeClass();
			$(this).addClass("visualizacaoAtiva");
			$("#pageFerramentas").hide();
			$("#pageAdministracao").hide();
			$("#pageLogs").show("slide", {direction: "right"});
		});
		$("#ulMenuSistema>li").click(function() { //efeito de ativacao do menu permicoes
			$("#ulMenuSistema>li").removeClass();
			$(this).addClass("ativo");
		});
		
		//ativacao dos dialogs
		$("#btnAddRegional").click(function() { //dialog regional
			$("#nomeRegional").attr("value","");
			$("#descricaoRegional").attr("value","");
			$("#dlgRegional").dialog("open");
		});
		$("#btnVisualizarUsuarios").click(function() { //visualizar usuario
			$("#dlgVisualizarUsuarios").dialog("open");
		});
		$("#btnAddUsuario").click(function() { //cadastrar usuario
			$("#dlgNovoUsuario").dialog("open");
		});
		$("#btnVisualizarGrupos").click(function() { //visualizar grupos
			$("#dlgVisualizarGrupos").dialog("open");
		});
		$("#btnAddGrupo").click(function() { //cadastrar grupos
			$("#dlgNovoGrupo").dialog("open");
		});
		$("#tabelaUsuarios>tbody>tr>td.exibirPermissoes").click(function() {
			$("#dlgExibirPermissoes").dialog("open");
		});
		
		//operacoes tela adiministrador
		$.each(db.semGrupo, function(key, value) { //adiciona todos os usuarios sem grupos
			$("#selectComboUsuarios").append("<option id='"+value.id+"'>"+value.nome+"</option>")
		});
		$("#enviarUsu").click(function() { //funcao para adicionar usuario a um grupo
			var grupo = $("#selectComboGrupos option:selected").attr("id");
			var usuario = $("#selectComboUsuarios option:selected");
			if (usuario.length == 1) {
				if (grupo == "gr1") {
					db.grupo1.push({"nome": usuario.attr("value"),"id": usuario.attr("id")});
					$("#selectComboGruposAssociados").empty();
					$.each(db.grupo1, function(key, grupo) {
						$("#selectComboGruposAssociados").append("<option id='"+grupo.id+"'>"+grupo.nome+"</option>");
						$.each(db.semGrupo, function(pos, grupo1) {
							if (grupo1.nome === grupo.nome) {
								db.semGrupo.splice(pos,1);
								usuario.remove();
							}
						});
					});
					$("#selectComboUsuarios option:selected").remove();
				} else {
					db.grupo2.push({"nome": usuario.attr("value"),"id": usuario.attr("id")});
					$("#selectComboGruposAssociados").empty();
					$.each(db.grupo2, function(key, grupo) {
						$("#selectComboGruposAssociados").append("<option id='"+grupo.id+"'>"+grupo.nome+"</option>");
						$.each(db.semGrupo, function(pos, grupo1) {
							if (grupo1.nome === grupo.nome) {
								db.semGrupo.splice(pos,1);
								usuario.remove();
							}
						});
					});
				}
			} else if(usuario.length > 1) {
				if (grupo == "gr1") {
					$("#selectComboGruposAssociados").empty();
					var index2 = 0;
					for (var index1 = 0; index1 < usuario.length; index1++) {
						db.grupo1.push({"nome":usuario[index1].value,"id":usuario[index1].attributes[0].value})
						while (index2 < db.semGrupo.length) {
							if (usuario[index1].value == db.semGrupo[index2].nome) {
								db.semGrupo.splice(index2,1);
							}
							index2++;
						}
						index2 = 0;
					}
					$.each(db.grupo1, function(key, usuarioAdd) {
						$("#selectComboGruposAssociados").append("<option id='"+usuarioAdd.id+"'>"+usuarioAdd.nome+"</option>");
					});
					$("#selectComboUsuarios option:selected").remove();
				} else {
					$("#selectComboGruposAssociados").empty();
					var index2 = 0;
					for (var index1 = 0; index1 < usuario.length; index1++) {
						db.grupo2.push({"nome":usuario[index1].value,"id":usuario[index1].attributes[0].value})
						while (index2 < db.semGrupo.length) {
							if (usuario[index1].value == db.semGrupo[index2].nome) {
								db.semGrupo.splice(index2,1);
							}
							index2++;
						}
						index2 = 0;
					}
					$.each(db.grupo2, function(key, usuarioAdd) {
						$("#selectComboGruposAssociados").append("<option id='"+usuarioAdd.id+"'>"+usuarioAdd.nome+"</option>");
					});
					$("#selectComboUsuarios option:selected").remove();
					
				}
			}
		});
		$("#retirarUsu").click(function() { //transfere usuario para os semGrupos
			var usuarios = $("#selectComboGruposAssociados option:selected");
			var grupo = $("#selectComboGrupos option:selected").attr("id");
			if (usuarios.length >= 1) {
				$("#selectComboGruposAssociados option:selected").remove();
				$("#selectComboUsuarios").empty();
				if (grupo == "gr1") {
					var index2 = 0;
					for (var index1 = 0; index1 < usuarios.length; index1++) {
						db.semGrupo.push({"nome":usuarios[index1].value, "id":usuarios[index1].attributes[0].value});
						while (index2 < db.grupo1.length) {
							if (usuarios[index1].value == db.grupo1[index2].nome) {
								db.grupo1.splice(index2,1);
							}
							index2++;
						}
						index2 = 0;
					}
					$.each(db.semGrupo, function(pos, value) {
						$("#selectComboUsuarios").append("<option id='"+value.id+"'>"+value.nome+"</option>");
					});
				} else {
					var index2 = 0;
					for (var index1 = 0; index1 < usuarios.length; index1++) {
						db.semGrupo.push({"nome":usuarios[index1].value, "id":usuarios[index1].attributes[0].value});
						while (index2 < db.grupo2.length) {
							if (usuarios[index1].value == db.grupo2[index2].nome) {
								db.grupo2.splice(index2,1);
							}
							index2++;
						}
						index2 = 0;
					}
					$.each(db.semGrupo, function(pos, value) {
						$("#selectComboUsuarios").append("<option id='"+value.id+"'>"+value.nome+"</option>");
					});
				}
			}
		});
		$("#selectComboGrupos").change(function() { //imprimir usuarios dos grupos
			var grupo = $("#selectComboGrupos option:selected").attr("value");
			if (grupo === "Grupo 1") {
				$("#selectComboGruposAssociados").empty();
				$.each(db.grupo1, function(key, grupo) {
					$("#selectComboGruposAssociados").append("<option id='"+grupo.id+"'>"+grupo.nome+"</option>");
				});
			} else {
				$("#selectComboGruposAssociados").empty();
				$.each(db.grupo2, function(key, grupo) {
					$("#selectComboGruposAssociados").append("<option id='"+grupo.id+"'>"+grupo.nome+"</option>");
				});
			}
		});
		$("#selectTipo").change(function() {
			var tipo = $("#selectTipo option:selected").attr("value");
			if (tipo === "Grupos") {
				$("#selectPermissaoUsuario").empty();
				$.each(db.grupos, function(key, grupo) {
					$("#selectPermissaoUsuario").append("<option id='"+grupo.id+"'>"+grupo.nome+"</option>");
				});
			} else if (tipo === "Usuários"){
				$("#selectPermissaoUsuario").empty();
				$.each(db.semGrupo, function(key,usuario) {
					$("#selectPermissaoUsuario").append("<option id='"+usuario.id+"'>"+usuario.nome+"</option>");
				});
				$.each(db.grupo1, function(key,usuario) {
					$("#selectPermissaoUsuario").append("<option id='"+usuario.id+"'>"+usuario.nome+"</option>");
				});
				$.each(db.grupo2, function(key,usuario) {
					$("#selectPermissaoUsuario").append("<option id='"+usuario.id+"'>"+usuario.nome+"</option>");
				});
			} else {
				$("#selectPermissaoUsuario").empty();
			}
		});
		$("#selectComboUsuarios").dblclick(function() { //adicionar usuarios com duplo click
			var grupo = $("#selectComboGrupos option:selected").attr("id");
			var usuario = $("#selectComboUsuarios option:selected");
			if (usuario.length == 1) {
				if (grupo == "gr1") {
					db.grupo1.push({"nome": usuario.attr("value"),"id": usuario.attr("id")});
					$("#selectComboGruposAssociados").empty();
					$.each(db.grupo1, function(key, grupo) {
						$("#selectComboGruposAssociados").append("<option id='"+grupo.id+"'>"+grupo.nome+"</option>");
						$.each(db.semGrupo, function(pos, grupo1) {
							if (grupo1.nome === grupo.nome) {
								db.semGrupo.splice(pos,1);
								usuario.remove();
							}
						});
					});
					$("#selectComboUsuarios option:selected").remove();
				} else {
					db.grupo2.push({"nome": usuario.attr("value"),"id": usuario.attr("id")});
					$("#selectComboGruposAssociados").empty();
					$.each(db.grupo2, function(key, grupo) {
						$("#selectComboGruposAssociados").append("<option id='"+grupo.id+"'>"+grupo.nome+"</option>");
						$.each(db.semGrupo, function(pos, grupo1) {
							if (grupo1.nome === grupo.nome) {
								db.semGrupo.splice(pos,1);
								usuario.remove();
							}
						});
					});
				}
			}
		});
		$("#selectComboGruposAssociados").dblclick(function() { //remover usuario com duplo click
			var usuario = $("#selectComboGruposAssociados option:selected");
			var grupo = $("#selectComboGrupos option:selected").attr("id");
			if (usuario.length == 1) {
				if (grupo == "gr1") {
					$("#selectComboUsuarios").empty();
					$.each(db.grupo1, function(pos, usuarioG) {
						if (usuarioG.nome == usuario.attr("value")) {
							db.grupo1.splice(pos,1);
							db.semGrupo.push({"nome":usuario.attr("value"),"id":usuario.attr("id")});
							$.each(db.semGrupo, function(pos, usuarioS) {
								$("#selectComboUsuarios").append("<option id="+usuarioS.id+">"+usuarioS.nome+"</option>")
							});
							$("#selectComboGruposAssociados option:selected").remove();
						}
					});
				} else {
					$("#selectComboUsuarios").empty();
					$.each(db.grupo2, function(pos, usuarioG) {
						if (usuarioG.nome == usuario.attr("value")) {
							db.grupo2.splice(pos,1);
							db.semGrupo.push({"nome":usuario.attr("value"),"id":usuario.attr("id")});
							$.each(db.semGrupo, function(pos, usuarioS) {
								$("#selectComboUsuarios").append("<option id="+usuarioS.id+">"+usuarioS.nome+"</option>")
							});
							$("#selectComboGruposAssociados option:selected").remove();
						}
					});
				}
			}
		});
	});