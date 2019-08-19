import java.util.Vector;

/**
 * Classe com a estrutura do elemento TRANSF do XML que gera 
 * a animacao.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 2.0
 * @see AgaActor
 */
 
public class Transf{
		/**
		 * Estado deste Transf. Atributo state do elemento FROM do
		 * XML..
		 */
		private String st;
		/**
		 * Vector com os possiveis proximos estados do estado deste
		 * Transf. Elemento TO do XML.
		 * @see Trans
		 */
		public Vector trans;
		
		/**
	 	 * Constroi um novo objeto Ftrans. Este construtor
	 	 * e o default para esta classe.
	 	 *
	 	 * @param st Estado correspondente a este elemento FTRANS.
	 	 */
		public Transf(String st) {
			this.st = st;
			trans = new Vector();
		}
		
		/**
		 * Retorna o estado deste Ftrans.
		 * @return Estado deste Ftrans.
		 */
		public String getSt() {
			return this.st;
		}
}
