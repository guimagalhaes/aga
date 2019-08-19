import java.util.Vector;

/**
 * Esta e a classe abstrata que implementa um Automaton. Todos as 
 * classes que implementam algum tipo de automato sao derivados 
 * desta classe.
 * 
 * @author  Guilherme de C. Magalh�es
 * @version 1.0
 */

abstract public class Automaton {
		/**
	 	 * Estado corrente do automato na animacao.
     	 */
		private String presentstate;
		
		/**
	 	 * Fita de entrada do automato.
     	 * 
     	 * @see TapeCell
     	 */
		public Vector tape;
		
		/**
	 	 * Pr�xima celula da fita de entrada a ser lida.
	 	 *
	 	 * @see Tapes
     	 */
		private int presentcell;
		
		/**
	 	 * Constroi um novo objeto Automaton. Este construtor
	 	 * e o default para esta classe. Nao e chamado diretamente, pois
	 	 * objetos desta classe nao podem sem criados.
	 	 */
		public Automaton(){
			tape = new Vector();
		}
				
		/**
     	 * Chamado pelas subclasses no inicio da execucao.
     	 */    
		public void initAutomaton(){
			//inicializa celula atual
			setPresentCell(0);
		}
		
		/**
		 * Metodo abstrato que implementa uma transi��o do automato.
		 */
		abstract public void transition(String state, String sb);
		
		/**
	 	 * Retorna o indice da proxima celula da fita a ser lida.
	 	 * @return indice da proxima celula da fita a ser lida.	 	 
	 	 */
		public int getPresentCell(){
			return this.presentcell;
		}
		
		/**
	 	 * Configura o indice da proxima celula da fita a ser lida.
	 	 * @param i indice da proxima celula da fita a ser lida.	 	 
	 	 */
		public void setPresentCell(int i){
			this.presentcell = i;
		}
		
		/**
	 	 * Retorna o estado atual do automato.
	 	 * @return Estado atual do automato.	 	 
	 	 */
		public String getPresentState(){
			return this.presentstate;
		}
		
		/**
	 	 * Configura o estado atual do automato.
	 	 * @param s Estado atual do automato.
	 	 */
		public void setPresentState(String s){
			this.presentstate = s;
		}
}
