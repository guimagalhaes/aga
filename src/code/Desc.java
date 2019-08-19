/**
 * Classe com a estrutura do elemento DESCF do XML que gera a animacao.
 * Usado para descrever cada estado de um automato, afim de documenta-los
 * possibilitando a busca de determinado estado na animacao.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 2.0
 * @see AgaActor
 */

public class Desc{
		/**
	 	 * Atributo STATE do XML.
	 	 */
		private String st;
		/**
	 	 * Valor do elemento DESCRIPTION do XML.
	 	 */
		private String desc;
		
		/**
	 	 * Constroi um novo objeto Desc. Este construtor
	 	 * e o default para esta classe.
	 	 *	 	 
	 	 * @param st Estado.
	 	 * @param desc descricao deste estado.
	 	 */
		public Desc(String st, String desc) {
			this.st = st;
			this.desc = desc;
		}
		
		/**
		 * Retorna o estado desta descricao.
		 * @return Estado desta descricao.
		 */
		public String getSt() {
			return this.st;
		}
		
		/**
		 * Retorna a descricao do estado.
		 * @return descricao do estado.
		 */
		public String getDesc() {
			return this.desc;
		}
}
