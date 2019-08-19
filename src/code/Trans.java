/**
 * Classe com a estrutura do elemento TO do XML que gera 
 * a animacao.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 2.0
 * @see AgaActor
 * @see Transf
 */

public class Trans{
		/**
		 * Atributo SYMBOL do XML.
		 */
		private String sb;
		/**
		 * Atributo STATE do XML.
		 */
		private String st;
		/**
		 * Atributo OUTPUT do XML.
		 */
		private String out;
		
		/**
	 	 * Constroi um novo objeto Trans. Este construtor
	 	 * e o default para esta classe.
	 	 *
	 	 * @param sb Simbolo de entrada.
	 	 * @param st Estado alvo dessa entrada.
	 	 * @param out Simbolo de saida.
	 	 */
		public Trans(String sb, String st, String out) {
			this.sb = sb;
			this.st = st;
			this.out = out;
		}
		
		/**
		 * Retorna o Simbolo de entrada.
		 * @return Simbolo de entrada.
		 */
		public String getSb() {
			return this.sb;
		}
		
		/**
		 * Retorna o estado alvo.
		 * @return Estado alvo.
		 */
		public String getSt() {
			return this.st;
		}
		
		/**
		 * Retorna o Simbolo de saida.
		 * @return Simbolo de saida.
		 */
		public String getOut() {
			return this.out;
		}
}
