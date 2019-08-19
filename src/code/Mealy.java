import java.util.Vector;

/**
 * Implementa a estrutura basica de uma Maquina de Mealy. Extende
 * a classe Automaton.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 1.0
 * @see Automaton
 */

public class Mealy extends Automaton {
		/**
	 	 * Atributo do elemento AGA do XML que gera a animacao.
     	 * Contem os simbolos de saida do automato. 
     	 *
     	 * @see Output
     	 */
		public Vector output;
		
		/**
	 	 * Constroi um novo objeto Mealy. Este construtor
	 	 * e o default para esta classe.
	 	 */ 
		public Mealy(){
			output = new Vector();
		}
				
		/**
     	 * Inicializa o estado da Maquina de Mealy. Chamado no inicio de
     	 * cada loop da animacao.
     	 * @see Automaton#initAutomaton()
     	 */    
		public void initAutomaton(){
			super.initAutomaton();
		}
		
		/**
		 * Metodo abstrato que implementa uma transicao da Maquina de 
		 * Mealy.
		 * @see Automaton#transition(String state, String sb)
		 */
		public void transition(String state, String sb){
		}
}
