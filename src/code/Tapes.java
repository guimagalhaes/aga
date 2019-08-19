import java.util.Vector;

/**
 * Classe com uma fita do XML e seu identificador.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 1.0
 * @see Aga
 * @see TapeCell
 */

public class Tapes {
	/**
	 * Identificador da fita de uma instancia de ator.
     */
	private String id;
	
	/**
	 * Vector da fita que ser� usada em uma instancia de ator.
	 *
	 * @see TapeCell
	 */
	public Vector tape;
	
	/**
	 * Constroi um novo objeto Tapes. Este construtor
	 * � o default para esta classe.
	 */
	public Tapes() {
		tape = new Vector();		
	}
	
	/**
	 * Retorna o identificador desta fita.
	 * @return Identificador desta fita.
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Configura o identificador desta fita.
	 * @param s Identificador desta fita.
	 */
	public void setId(String s) {
		this.id = s;
	}
}
