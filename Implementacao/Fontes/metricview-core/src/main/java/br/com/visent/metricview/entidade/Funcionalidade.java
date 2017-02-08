package br.com.visent.metricview.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import br.com.visent.corporativo.entidade.Entidade;

/**
 *	Classe para descricao as funcionalidades do usuario
 *	Atributos:
 *	<p>
 *			<strong>id</strong> - id do banco para diferenciar os registros
 *	</p>
 *	<p>
 *		A formacao dos menus esta sendo feita da seguinte forma < li class="liCad" id="usuarios">${msgs.label_cadastrarUsuario}< / li>
 *		<p>
 *			<strong>descricao</strong>  - Essa descricao tem que ser igual ao id="" do < li>
 *			<p>
 *				E a descricao para o usuario (${msgs.label_cadastrarUsuario}) sera da seguinte forma ${msgs.label_cadastrar_'descricao'}
 *			</p>	
 *		</p> 
 *	</p>
 *	<p>
 *			<strong>exibir</strong>  - Caso esteja true, o menu ira aparecer para o usario, Caso estaja falso o menu nao ira aparecer
 *	</p>
 *	<p>
 *			<strong>tipoMenu</strong>  - Coluna para diferenciar as funcionalidades do sistema.
 *			<p> 
 *				Valores.:
 *				<p>
 * 					1 - Para o menu superior de admninistracao
 * 				</p>
 * 				<p>
 * 					2 - Para o menu inferior de cada indicador
 * 				</p>
 * 			</p>
 *	</p>
 */
@Entity
@Table(name = "funcionalidade")
@SequenceGenerator(name = "seq_funcionalidade",sequenceName="seq_funcionalidade")
public class Funcionalidade implements Entidade{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="seq_funcionalidade",strategy=GenerationType.SEQUENCE)
	@Column(name = "id_funcionalidade")
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@Type(type="true_false")
	@Column(name = "exibir")
	private Boolean exibir;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_acesso", referencedColumnName = "id_tipo_acesso")
	private TipoAcesso tipoAcesso;
	
	@Column(name="tipo_menu")
	private Long tipoMenu;
	
	@OneToOne
	@JoinColumn(name="id_ferramenta",referencedColumnName="id_ferramenta")
	private Ferramenta ferramenta;
	
	@Override
	public String toString() {
		return "Funcionalidade [id=" + id + ", descricao=" + descricao + ", exibir=" + exibir + ", tipoMenu=" + tipoMenu + ", ferramenta=" + ferramenta + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getExibir() {
		return exibir;
	}

	public void setExibir(Boolean exibir) {
		this.exibir = exibir;
	}

	public TipoAcesso getTipoAcesso() {
		return tipoAcesso;
	}

	public void setTipoAcesso(TipoAcesso tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}

	public Long getTipoMenu() {
		return tipoMenu;
	}

	public void setTipoMenu(Long tipoMenu) {
		this.tipoMenu = tipoMenu;
	}

	public Ferramenta getFerramenta() {
		return ferramenta;
	}

	public void setFerramenta(Ferramenta ferramenta) {
		this.ferramenta = ferramenta;
	}

}
