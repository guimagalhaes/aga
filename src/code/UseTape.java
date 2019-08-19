import java.util.Vector;

/**
 * Classe com um identificador de fita a ser usado por um ator.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 1.0
 * @see Instance
 * @see Tapes
 */

public class UseTape {
	/**
	 * Identificador da fita.
	 * @see Tapes
	 */
	private String tape;
		
	/**
	 * Constroi um novo objeto UseTape. Este construtor
	 * e o default para esta classe.
	 */
	public UseTape(String tape) {
		this.tape = tape;
	}
	
	/**
	 * Retorna o identificador da fita a ser usada pelo ator.
	 * @return Identificador da fita a ser usada pelo ator.
	 */
	public String getTape() {
		return this.tape;
	}
	
	/**
	 * Configura o identificador da fita a ser usada pelo ator.
	 * @param s Identificador da fita a ser usada pelo ator.
	 */
	public void setTape(String s) {
		this.tape = s;
	}
}
