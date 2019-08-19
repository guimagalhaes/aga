/**
 * Classe com a estrutura de cada simbolo de saida de uma automato.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 2.0
 * @see AgaActor
 */

public class Output implements Cloneable{
		/**
	 	 * Atributo ID do XML.
	 	 */
		private String id;
		/**
	 	 * Atributo SOURCE do XML.
	 	 */
		private String source;
		/**
	 	 * Atributo X do XML.
	 	 */
		private int x;
		/**
	 	 * Atributo Y do XML.
	 	 */
		private int y;
		
		/**
	 	 * Constroi um novo objeto Output. Este construtor
	 	 * e o default para esta classe.	 	 
	 	 */
		public Output() {
		}
		
		/**
	 	 * Constroi um novo objeto Output. Usado quando o tipo
	 	 * do ator for GRAPHICS.
	 	 *
	 	 * @param id Identificador deste simbolo de saida.
	 	 * @param source Simbolo de saida.
	 	 * @param x Componente X da posicao inicial da imagem.
	 	 * @param y Componente Y da posicao inicial da imagem.
	 	 */
		public Output(String id, String source, String x, String y) {
			this.id = id;
			this.source = source;
			this.x = Integer.valueOf(x).intValue();
			this.y = Integer.valueOf(y).intValue();			
		}
		
		/**
	 	 * Constroi um novo objeto Output. Usado quando o tipo
	 	 * do ator for SOUNDS.
	 	 *
	 	 * @param id Identificador deste simbolo de saida.
	 	 * @param source Simbolo de saida.	 	 
	 	 */
		public Output(String id, String source) {
			this.id = id;
			this.source = source;			
		}
		
		/**
		 * Retorna o identificador do simbolo de saida.
		 * @return Identificador do simbolo de saida.
		 */
		public String getId() {
			return this.id;
		}
		
		/**
		 * Configura o identificador do simbolo de saida.
		 * @param id Identificador do simbolo de saida.
		 */
		public void setId(String id) {
			this.id = id;
		}
		
		/**
		 * Retorna o simbolo de saida.
		 * @return simbolo de saida.
		 */
		public String getSource() {
			return this.source;
		}
		
		/**
		 * Configura o simbolo de saida.
		 * @param source simbolo de saida.
		 */
		public void setSource(String source) {
			this.source = source;
		}
		
		/**
		 * Retorna a componente X da posicao inicial da imagem.
		 * @return Componente X da posicao inicial da imagem.
		 */
		public int getX() {
			return this.x;
		}
		
		/**
		 * Configura a componente X da posicao inicial da imagem.
		 * @param x Componente X da posicao inicial da imagem.
		 */
		public void setX(int x) {
			this.x = x;
		}
		
		/**
		 * Retorna a componente Y da posicao inicial da imagem.
		 * @return Componente Y da posicao inicial da imagem.
		 */
		public int getY() {
			return this.y;
		}
		
		/**
		 * Configura a componente Y da posicao inicial da imagem.
		 * @param y Componente Y da posicao inicial da imagem.
		 */
		public void setY(int y) {
			this.y = y;
		}
		
		/**
	 	 * Permite criar um clone desta classe.
	 	 * Todos os objetos membros desta classe devem ser clonados.
	 	 */
		public Object clone(){
    		Output copy = null;
      		
    		try {
    			copy = (Output) super.clone();
   			}
   			catch (CloneNotSupportedException e) {}
      	
      		return copy;
   		}
}
