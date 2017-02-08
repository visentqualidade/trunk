package br.com.visent.metricview.util;

public class ConstantesUtil {

	//Descricao das mensagens do arquivo messages.properties
	public static final String KEY_MESSAGE_ALERTA_USUARIO_NAO_CADASTRADO = "alerta_usuario_nao_cadastrado";
	public static final String KEY_MESSAGE_USUARIO_NAO_PODE_SER_ALTERADO = "mensagem_erro_usuario_nao_pode_ser_alterado";
	public static final String KEY_MESSAGE_SENHA_ANTIGA_INCORRETA = "mensagem_erro_senha_antiga_incorreta";
	public static final String KEY_MESSAGE_LOGIN_JA_CADASTRADO = "mensagem_erro_login_ja_cadastrado";
	public static final String KEY_MESSAGE_GRUPO_JA_CADASTRADO = "mensagem_erro_grupo_ja_cadastrado";
	public static final String KEY_MESSAGE_REGIONAL_JA_CADASTRADO = "mensagem_erro_regional_ja_cadastrado";
	public static final String KEY_MESSAGE_EMAIL_JA_CADASTRADO = "mensagem_erro_email_ja_cadastrado";
	public static final String KEY_MESSAGE_ALERTA_QTD_USUARIO_SIMULTANEOS_ATINGIDA = "alerta_qtd_usuario_simultaneos_atingida";
	public static final String KEY_MESSAGE_ALERTA_QTD_MAX_DIF_CONSULTA_LOG = "alerta_qtd_max_dif_consulta_log";
	public static final String KEY_MESSAGE_ALERTA_QTD_USUARIO_ADM_CADASTRADOS_ATINGIDA = "alerta_qtd_usuario_adm_cadastrados_atingida";
	
	//Descricao das mensagens de logs do arquivo messages.properties
	public static final String KEY_MESSAGE_MENSAGEM_LOG_ERRO_ADD_REGISTRO_LOGIN = "mensagem_log_erro_add_registro_login";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_LOGIN = "mensagem_log_login";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_LOGOUT = "mensagem_log_logout";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_NAO_ENCONTROU_REGISTRO_LOGIN = "mensagem_log_nao_encontrou_registro_login";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_USUARIO_INSERIDO_SUCESSO = "mensagem_log_usuario_inserido_sucesso";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_USUARIO_REMOVIDO_SUCESSO = "mensagem_log_usuario_removido_sucesso";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_GRUPO_REMOVIDO_SUCESSO = "mensagem_log_grupo_removido_sucesso";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_RESETAR_SENHA_USUARIO= "mensagem_log_resetar_senha_usuario";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_DESCONECTA_USUARIO= "mensagem_log_desconecta_usuario";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_USUARIO_ATUALIZADO_SUCESSO = "mensagem_log_usuario_atualizado_sucesso";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_ERRO_ENVIAR_EMAIL = "mensagem_log_erro_enviar_email";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_ALTEROU_DADOS = "mensagem_log_alterou_dados";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_GRUPO_INSERIDO_SUCESSO = "mensagem_log_criar_novo_grupo";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_ALTEROU_PERMISSOES = "mensagem_log_alterou_permissoes";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_REGIONAL_INSERIDO_SUCESSO = "mensagem_log_criar_novo_regional";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_REGIONAL_REMOVIDO_SUCESSO = "mensagem_log_excluir_regional";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_REGIONAL_ALTERADO_SUCESSO = "mensagem_log_alterar_regional";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_GRUPO_ALTERADO_SUCESSO = "mensagem_log_alterar_grupo";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_GRUPO_EXCLUIR_SUCESSO = "mensagem_log_excluir_grupo";
	public static final String KEY_MESSAGE_MENSAGEM_LOG_ALTEROU_DADOS_PERMISSAO = "mensagem_log_alterou_dados_permissao";
	public static final String KEY_MESSAGE_MENSAGEM_ALTERAR_ASSOCIACAO_GRUPO = "mensagem_alterar_associacao_grupo";
	public static final String KEY_MESSAGE_MENSAGEM_ALTERAR_PERMISSAO_USUARIO = "mensagem_alterar_permissoes_usuario";
	public static final String KEY_MESSAGE_MENSAGEM_ALTERAR_PERMISSAO_GRUPO = "mensagem_alterar_permissoes_grupo";
	public static final String TXT_COL_CN = "txt_cns";
	public static final String LBL_EXCECAO_CGI = "label_excecao_cgi";
	public static final String TXT_COL_CENTRAL = "txt_col_central";
	public static final String TXT_COL_CGI = "txt_col_setor_nao_identificado";
	
	//Descricao das configuracoes do arquivo metricview.properties
	public static final String KEY_CONFIG_CAMINHO_ARQUIVO_GRAVACAO_LOGS = "caminho_arquivo_gravacao_logs";
	public static final String KEY_CONFIG_FUNCAO_LOGS_ATIVA = "funcao_de_logs_ativa";

	//Decricao dos jobs do sistema
	// JOB LOG
	public static final String JOB_GRAVAR_LOGS = "job_gravar_logs";
	public static final String TEMPO_JOB_GRAVA_LOG = "tempo_job_grava_log";
	// JOB CADASTRO DINAMICO
	public static final String JOB_CADASTRO_DINAMICO = "job_cadastro_dinamico";
	public static final String TEMPO_JOB_CADASTRO_DINAMICO = "tempo_job_cadastro";
	
	//JOB FILTROS DINAMICOS
	public static final String JOB_FILTRO_DINAMICO = "job_filtro_dinamico";
	public static final String HORA_EXECUCAO_JOB_FILTRO_DINAMICO = "hora_execucao_job_filtro_dinamico";
	public static final String TEMPO_JOB_FILTRO_DINAMICO = "tempo_job_filtro_dinamico";
	public static final String DRIVER_JDBC = "driver_jdbc";
	public static final String URL_EASYVIEW = "url_easyview";
	public static final String USUARIO_EASYVIEW = "usuario_easyview";
	public static final String SENHA_EASYVIEW = "senha_easyview";
	public static final String URL_ITX = "url_itx";
	public static final String USUARIO_ITX = "usuario_itx";
	public static final String SENHA_ITX = "senha_itx";

	//Descricao do cabecalho e das informacoes necessarias da log dos arquivos de logs
	public static final String CABECALHO_LOG = "mensagem;tipo(INFO, ERROR);usuario(nome);data_hora;projeto/solucao\n";
	public static final String NOME_SISTEMA = "Sistema";

	//Nome das funcoes em javascript para que seja possivel ser feito o ajax reverso
	public static final String FUNCAO_JS_DESCONECTA_USUARIO = "desconectaUsuario";
	public static final String FUNCAO_JS_EXIBIR_QTD_LINHA = "exibirQtdLinhasProcessadas";
	public static final int QTD_LINHA_MSG_USUARIO = 10000;
	public static final int QTD_LINHA_MSG_USUARIO_APARELHO = 1000;
	
	//Algumas configuracoes do sistema
	public static final String NOME_ARQUIVO_APARELHOS = "relatorio-carga-aparelhos";
	public static final String DESCRICAO_USUARIO_SEM_GRUPO = "NID";
	
	//Nome para aparecer na filtragem dos logs e quaisquer mais funcionalidades
	public static final String TODOS_USUARIOS = "Todos";
	
	//Descricao das configuracoes de email
	public static final String EMAIL_AUTENTICACAO = "email_autenticacao";
	public static final String EMAIL_LOGIN = "email_login";
	public static final String EMAIL_SENHA = "email_senha";
	public static final String EMAIL_SSL_MAIL = "email_ssl_mail";
	public static final String EMAIL_TSL_MAIL = "email_tsl_mail";
	public static final String EMAIL_FROM = "email_from";
	public static final String EMAIL_HOST_NAME = "email_host_name";
	public static final String EMAIL_ASSUNTO = "email_assunto";
	public static final String EMAIL_PORTA = "email_porta";
	public static final String EMAIL_DESABILITAR_ENVIO_EMAIL = "email_desabilitar_envio_email";
	public static final String EMAIL_MENSAGEM_CADASTRO_USUARIO = "email_mensagem_cadastro_usuario";
	public static final String EMAIL_MENSAGEM_RESETAR_SENHA= "email_mensagem_resetar_senha";
	
	
	// Mensagens de erro
	public static final String KEY_MESSAGE_ERRO_SECURITY 	= "mensagem_erro_violacao_seguranca";
	public static final String KEY_MESSAGE_ERRO_INSTANCIA 	= "mensagem_erro_instancia";
	public static final String KEY_MESSAGE_ERRO_ACESSO 		= "mensagem_erro_sem_acesso";
	public static final String KEY_MESSAGE_ERRO_CAMPO 		= "mensagem_erro_sem_campo";
	
	// Constantes para utilização do Cadastro Dinâmico.
	public static final String KEY_CONFIG_CADASTRO_DINAMICO_ATIVO = "funcao_cadastro_dinamico";
	public static final String KEY_CONFIG_URL_PORTAL_CADASTRO_DINAMICO = "config_url_portal_cadastro_dinamico";
	public static final String CAMPO_BILHETADOR = "em_definicao";
	public static final String KEY_CONFIG_DIRETORIO_ARQUIVOS = "config_url_diretorio_arquivos";
	public static final String ASSOCIAR_USUARIO = "acao_associar_usuario";
	
	// Classificação de tipo tecnologia
	public static final Long   	TIPO_TECNOLOGIA_SMS 	= 4l;
	public static final Long 	TIPO_TECNOLOGIA_VOZ 	= 2l;
	public static final Long 	TIPO_TECNOLOGIA_DADOS 	= 3l;
	public static final Long    TIPO_TECNOLOGIA_INDEF   = 5l;
	public static final String 	TIPO_TEC_SMS = "SMS";
	public static final String 	TIPO_TEC_VOZ_MSC = "MSC";
	public static final String 	TIPO_TEC_VOZ_MSS = "MSS";
	public static final String  TIPO_TEC_DADOS_SGSN = "SGSN";
	public static final String  TIPO_TEC_DADOS_GGSN = "GGSN";
	public static final String  TIPO_TEC_DADOS_SG   = "SG";
	public static final String  TIPO_TEC_DADOS_GG	= "GG";
	public static final String REMOVER_NOME_CHU_BILHETADOR = "_CHU";

}
