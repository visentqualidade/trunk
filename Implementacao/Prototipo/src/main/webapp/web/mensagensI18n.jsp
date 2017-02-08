<script>
	var contexto = '${pageContext.request.contextPath}';
	
	var mensagens = new Object();
	
	//#Identificacao das mensagens de erro
	mensagens['alerta_preenchimento_obrigatorio'] = "${msgs.alerta_preenchimento_obrigatorio}";
	mensagens['alerta_preenchimento_invalido_carac_especial'] = "${msgs.alerta_preenchimento_invalido_carac_especial}";
	mensagens['alerta_preenchimento_invalido'] = "${msgs.alerta_preenchimento_invalido}";
	mensagens['alerta_usuario_ja_logado'] = "${msgs.alerta_usuario_ja_logado}";
	mensagens['alerta_resetar_senha'] = "${msgs.alerta_resetar_senha}";
	mensagens['alerta_remover_entidade'] = "${msgs.alerta_remover_entidade}";
	mensagens['alerta_desconectar_usuario'] = "${msgs.alerta_desconectar_usuario}";
	mensagens['alerta_qtd_min_carac'] = "${msgs.alerta_qtd_min_carac}";
	mensagens['alerta_qtd_max_carac'] = "${msgs.alerta_qtd_max_carac}";
	mensagens['alerta_qtd_usuario_adm_cadastrados_atingida'] = "${msgs.alerta_qtd_usuario_adm_cadastrados_atingida}";
	mensagens['alerta_qtd_max_dif_consulta_log'] = "${msgs.alerta_qtd_max_dif_consulta_log}";
	mensagens['alert_configuracao_permissao_associacao'] = "${msgs.alert_configuracao_permissao_associacao}";
	
	
	//#Identificacao dos titles
	mensagens['title_alterar_dados'] = "${msgs.title_alterar_dados}";
	mensagens['title_editar'] = "${msgs.title_editar}";
	mensagens['title_salvar'] = "${msgs.title_salvar}";
	mensagens['title_ajuda'] = "${msgs.title_ajuda}";
	mensagens['title_cadastra_usuario'] = "${msgs.title_cadastra_usuario}";
	mensagens['title_cadastrar_regional'] = "${msgs.title_cadastrar_regional}";
	mensagens['title_editar_regional'] = "${msgs.title_editar_regional}";
	mensagens['title_visualizar_usuario'] = "${msgs.title_visualizar_usuario}";
	mensagens['title_resetar_senha'] = "${msgs.title_resetar_senha}";
	mensagens['title_desconectar_usuario'] = "${msgs.title_desconectar_usuario}";
	mensagens['title_excluir'] = "${msgs.title_excluir}";
	mensagens['title_cadastra_grupo']   = "${msgs.title_cadastra_grupo}";
	mensagens['title_visualizar_grupo'] = "${msgs.title_visualizar_grupo}";
	mensagens['title_permissoes_modal'] = "${msgs.title_permissoes_modal}";
	
	//#Identificacao dos Botoes
	mensagens['btn_salvar'] = "${msgs.btn_salvar}";
	mensagens['btn_fechar'] = "${msgs.btn_fechar}";
	mensagens['btn_cancelar'] = "${msgs.btn_cancelar}";
	mensagens['btn_salvar_e_fechar'] = "${msgs.btn_salvar_e_fechar}";
	mensagens['btn_atualizar'] = "${msgs.btn_atualizar}";
	
	//#Identificacao das colunas das tabelas
	mensagens['txt_col_data_hora'] = "${msgs.txt_col_data_hora}";
	mensagens['txt_col_usuario'] = "${msgs.txt_col_usuario}";
	mensagens['txt_col_mensagem'] = "${msgs.txt_col_mensagem}";
	mensagens['txt_col_solucao'] = "${msgs.txt_col_solucao}";
	mensagens['txt_col_grupo'] = "${msgs.txt_col_grupo}";
	mensagens['txt_col_login'] = "${msgs.txt_col_login}";	
	mensagens['txt_col_nome'] = "${msgs.txt_col_nome}";
	mensagens['txt_col_email'] = "${msgs.txt_col_email}";
	mensagens['txt_col_telefone'] = "${msgs.txt_col_telefone}";
	mensagens['txt_col_area'] = "${msgs.txt_col_area}";
	mensagens['txt_col_regional'] = "${msgs.txt_col_regional}";
	mensagens['txt_col_responsavel'] = "${msgs.txt_col_responsavel}";
	mensagens['txt_col_admin'] = "${msgs.txt_col_admin}";
	mensagens['txt_col_descricao'] = "${msgs.txt_col_descricao}";
	mensagens['txt_cns'] = "${msgs.txt_cns}";
	mensagens['label_bilhetador'] = "${msgs.label_bilhetador}";
	mensagens['txt_cn'] = "${msgs.txt_cn}";
	mensagens['txt_nao_tem_permissao'] = "${msgs.txt_nao_tem_permissao}";
	mensagens['txt_col_permissao'] = "${msgs.txt_col_permissao}";
	mensagens['txt_col_permissao_individual'] = "${msgs.txt_col_permissao_individual}";
	mensagens['txt_col_permissao_grupos'] = "${msgs.txt_col_permissao_grupos}";
	mensagens['label_data'] = "${msgs.txt_data}";

	mensagens['txt_grupos'] = "${msgs.txt_grupos}";
	mensagens['txt_usuarios'] = "${msgs.txt_usuarios}";
	mensagens['txt_selecione'] = "${msgs.txt_selecione}";
	
	//#Identificacao dos textos
	mensagens['txt_administrador'] = "${msgs.txt_administrador}";
	mensagens['label_select_todos'] = "${msgs.label_select_todos}";
	mensagens['txt_system'] = "${msgs.txt_system}";
	
	//#Identificacao das mensagens de erro
	mensagens['mensagem_erro_senhas_incorretas'] = "${msgs.mensagem_erro_senhas_incorretas}";
	mensagens['mensagem_sem_acesso_ao_portal'] = "${msgs.mensagem_sem_acesso_ao_portal}";
	mensagens['mensagem_erro_servidor_fora_do_ar'] = "${msgs.mensagem_erro_servidor_fora_do_ar}";
	mensagens['mensagem_erro_ocorreu_erro_inesperado'] = "${msgs.mensagem_erro_ocorreu_erro_inesperado}";
	mensagens['txt_filtrar'] = "${msgs.txt_filtrar}";
	
	//#Identificao das mensagens de sucesso
	mensagens['mensagem_sucesso_operacao_sucesso'] = "${msgs.mensagem_sucesso_operacao_sucesso}";
	
	//#Identificacao das constantes
	mensagens['const_qtd_max_dias_log'] = "QTD_MAX_DIAS_PESQUISA_FILTRO";
	mensagens['const_nome_sistema'] = "NOME_USUARIO_SYSTEM";
	
	//#Constantes de sessão
	mensagens['constante_id_usuario_logado'] = "${usuario.idUsuario}";
	mensagens['constante_nome_usuario_logado'] = "${usuario.login}";
	mensagens['constante_token_sessao'] = "${token}";
	mensagens['constante_user_e_admin'] = "${usuario.admin}";
	mensagens['constante_user_class_no_admin'] = "${classNoAdm}";
	
    function formatarMensagemApresentacao(key , params){
    	var mensagem = mensagens[key];
    	for(var i = 0 ; i < params.length ; i++){
    		var textoSubs = "{"+i+"}";
    		mensagem = mensagem.replace(textoSubs , params[i]);
    	}
    	return mensagem;
    }

	

</script>