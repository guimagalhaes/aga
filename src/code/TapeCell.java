/**
 * Classe com a estrutura de cada celula da fita de um automato.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 1.0
 * @see Automaton
 */

public class TapeCell {
	/**
	 * Atributo SYMBOL do XML.
	 */
	private String sb;
	/**
	 * Atributo TIME do XML.
	 */
	private int tm;
	/**
	 * Atributo FN do XML.
	 */
	private String fn;
	
	/**
	 * Constroi um novo objeto TapeCell. Este construtor
	 * e o default para esta classe.
	 *
	 * @see Aga
	 * @see FnTransform
	 */
	public TapeCell() {	
	} 
	 
	/**
	 * Constroi um novo objeto TapeCell. Este construtor
	 * � o default para esta classe.
	 *
	 * @param sb Simbolo de entrada dessa celula.
	 * @param tm Tempo que o Simbolo de saida gerado por esta 
	 * celula ficara sendo exibida. Sera multiplicado pelo valor 
	 * do base.
	 * @param fn Funcao transformacao para aplicar ao Simbolo de 
	 * saida gerado por esta celula.
	 *
	 * @see Aga
	 * @see FnTransform
	 */
	public TapeCell(String sb, String tm, String fn) {
		this.sb = sb;
		this.tm = Integer.valueOf(tm).intValue();
		this.fn = fn;
	}
	
	/**
	 * Retorna o Simbolo de entrada desta celula. Atributo
	 * SYMBOL do elemento CEL do XML.
	 * @return Simbolo de entrada desta celula.
	 */
	public String getSb() {
		return this.sb;
	}
	
	/**
	 * Retorna o tempo de perman�ncia da imagem gerada por esta celula
	 * relativo ao base. Atributo TIME do elemento CEL do XML.
	 * @return Tempo de perman�ncia da imagem gerada por esta celula 
	 * relativo ao base.
	 * @see Aga#getFrameRate()
	 */
	public int getTm() {
		return this.tm;
	}
	
	/**
	 * Configura o tempo de perman�ncia da imagem gerada por esta celula
	 * relativo ao base. Atributo TIME do elemento CEL do XML.
	 * @param i Tempo de perman�ncia da imagem gerada por esta celula
	 * relativo ao base.
	 * @see Aga#getFrameRate()
	 */
	public void setTm(int i) {
		this.tm = i;
	}
	
	/**
	 * Retorna a Funcao transformacao sobre a imagem gerada por 
	 * esta celula. Atributo FN do elemento CEL do XML.
	 * @return Funcao transformacao sobre a imagem gerada por 
	 * esta celula.
	 * @see FnTransform
	 */
	public String getFn() {
		return this.fn;
	}
}
