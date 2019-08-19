import java.util.Vector;

/**
 * Classe com uma instancia de um ator.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 1.0
 * @see Aga
 */

public class Instance {
	/**
	 * Identificador da Instancia. Atributo ID do XML.
	 */
	private String id;
	/**
	 * Identificador do Ator. Atributo ACTOR do XML.
	 */
	private String actorid;
	/**
	 * Ordem do ator na animacao. Atributo ORDER do XML.
	 */
	private int order;
	/**
	 * Usado para restaurar a ordem inicial do ator ja que
	 * a ordem pode ser alterada pela funcao Order.
	 *
	 * @see FnTransform#orderTrans(String fn, AgaActor actor,Instance 
	 * instance,Vector instances)
	 */
	private int initial_order;
	
	
	/**
	 * Instancia do ator. Possui uma c�oia da definicao de um ator 
	 * da classe Aga.
	 *
	 * @see AgaActor
	 */
	public AgaActor actor;
	
	/**
	 * Vector com identificador de fitas a serem usadas pelo ator.
	 * e uma concatenacao das fitas associadas a ele pelo elemento
	 * USETAPE do XML.
	 *
	 * @see UseTape
	 */
	public Vector tapes;
	
	/**
	 * Constroi um novo objeto Instance. Este construtor
	 * e o default para esta classe.
	 */
	public Instance(String id, String actorid, String order) {		
		this.id = id;
		this.actorid = actorid;
		
		if (order != null){
			this.order = Integer.valueOf(order).intValue();		
			this.initial_order = this.order;
		}
		
		tapes = new Vector();
		actor = null;
	}
	
	/**
	 * Retorna o identificador da instancia do ator.
	 * @return Identificador da instancia do ator.
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Configura o identificador da instancia do ator.
	 * @param s Identificador da instancia do ator.
	 */
	public void setId(String s) {
		this.id = s;
	}
	
	/**
	 * Retorna o identificador do ator.
	 * @return Identificador do ator.
	 */
	public String getActorId() {
		return this.actorid;
	}
	
	/**
	 * Configura o identificador do ator.
	 * @param s Identificador do ator.
	 * @see AgaActor#getId()
	 */
	public void setActorId(String s) {
		this.actorid = s;
	}
	
	/**
	 * Retorna a ordem do ator na animacao.
	 * @return Ordem do ator na animacao.
	 */
	public int getOrder() {
		return this.order;
	}
	
	/**
	 * Configura a ordem do ator na animacao.
	 * @param s Ordem do ator na animacao
	 */
	public void setOrder(int i) {
		this.order = i;
	}
	
	/**
	 * Restaura a ordem inicial do ator. Chamado a cada 
	 * reinicializacao dos automatos.
	 * @see Animator#initAutomatons()
	 */
	protected void recoverOrder() {
		this.order = this.initial_order;
	}
}
