import java.util.Vector;

/**
 * AgaActor structure.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 1.0
 * @see AgaActor#actor
 * @see FnTransform
 */

public class Actor implements Cloneable{
	/**
	 * Imagem atual do ator, aponta para um ou mais simbolos de saida 
	 * do automato correspondente.
	 * 
	 * @see AgaActor#output
	 * @see Output
	 */
	public Vector presentoutput;
	/**
	 * Indica se o ator esta visivel na animacao.
	 *
	 * @see FnTransform#visibleTrans(String, AgaActor)
	 * @see FnTransform#visible
	 */
	private boolean actorvisible;
	/**
	 * Posicao relativa do ator.
	 * Este valor e adicionado ao componente correspondente do elemento 
	 * OUTPUT.
	 *
	 * @see Output
	 */
	private int relativex;
	private int relativey;
	
	/**
	 * Tempo em que acaba a animacao do ator deste automato.
	 * � a soma de todos os tempos de cada celula da fita.
	 *
	 * @see TapeCell
	 */
	private int endtime;
	
	/**
	 * Escala do ator em porcentagem.
	 *
	 * @see FnTransform#scaleTrans(String, AgaActor)
	 * @see FnTransform#scale
	 */
	private int scale;
	
	/**
	 * Rotacao do ator em graus.
	 *
	 * @see FnTransform#rotateTrans(String, AgaActor)
	 * @see FnTransform#rotate
	 */
	private int rotatedegree;
	
	/**
	 * True se deve ser aplicado a transformacao flip na horizontal.
	 *
	 * @see FnTransform#flipTrans(String, AgaActor)
	 * @see FnTransform#flip
	 */
	private boolean flip_h;
	/**
	 * True se deve ser aplicado a transformacao flip na vertical.
	 *
	 * @see FnTransform#flipTrans(String, AgaActor)
	 * @see FnTransform#flip
	 */
	private boolean flip_v;
	
	/**
	 * True se deve ser aplicado uma matriz de tranformacao.
	 *
	 * @see FnTransform#matrixTrans(String, AgaActor)
	 * @see FnTransform#matrix
	 */
	private boolean matrix;
	/**
	 * Vector de strings com os 6 parametros a serem aplicados a 
	 * uma matrix de transformacao.
	 *
	 * @see FnTransform#matrixTrans(String, AgaActor)
	 * @see FnTransform#matrix
	 */
	private Vector matrix_params;
	
	/**
	 * True se o ator e do tipo SOUNDS e tem som para ser
	 * tocado no proximo Action do timer.
	 */
	private boolean hassoundstoplay;
	
	/**
	 * Constroi um novo objeto Actor. Este construtor
	 * e o default para esta classe.	 
	 */
	public Actor(){
		presentoutput = null;		
		actorvisible = true;
		endtime = 0;
		scale = 100;
		rotatedegree = 0;
		flip_h = false;
		flip_v = false;
		matrix = false;
		matrix_params = new Vector();
	}
	
	/**
	 * Retorna as imagens atuais do ator para composicao.
	 * @return Imagem atual do ator.
	 * @see Output
	 */
	public Vector getPresentOutput(){
		return this.presentoutput;
	}
	
	/**
	 * Atualiza as imagens atuais do ator para a composicao.
	 * @param sb Imagens para composicao do ator.	 
	 */
	public void setPresentOutput(Vector sb){
		this.presentoutput = sb;
	}
	
	/**
	 * Retorna true se ator esta visivel na animacao, false caso 
	 * contr�rio.
	 * @return True se ator esta visivel na animacao, 
	 * false caso contr�rio.
	 */
	public boolean isActorVisible(){
		return this.actorvisible;
	}
	
	/**
	 * Configura a visibilidade do ator.
	 * @param b Visibilidade do ator, true para torna-lo 
	 * visivel na animacao, false para torna-lo invisivel.
	 */
	public void setActorVisible(boolean b){
		this.actorvisible = b;
	}
	
	/**
	 * Retorna posicao relativa do ator. No momento de posicionar 
	 * o ator em cada frame da animacao, essa posicao e adicionada
	 * a correspondente componente descrita na estrutura Output, 
	 * instanciada por este simbolo de saida.
	 * @return Componente X da posicao relativa do ator.
	 * @see Output
	 */
	public int getRelativeX() {		
		return this.relativex;
	}
	
	/**
	 * Configura posicao relativa do ator.
	 * @param x Componente X da posicao relativa do ator.
	 * @see #getRelativeX()
	 */
	public void setRelativeX(int x) {
		this.relativex = x;
	}
	
	/**
	 * Retorna posicao relativa do ator. No momento de posicionar 
	 * o ator em cada frame da animacao, essa posicao e adicionada
	 * a correspondente componente descrita na estrutura Output, 
	 * instanciada por este simbolo de saida.
	 * @return Componente Y da posicao relativa do ator.
	 * @see Output
	 */
	public int getRelativeY() {
		return this.relativey;
	}
	
	/**
	 * Configura posicao relativa do ator.
	 * @param y Componente Y da posicao relativa do ator.
	 * @see #getRelativeY()
	 */
	public void setRelativeY(int y) {
		this.relativey = y;
	}
	
	/**
	 * Retorna o tempo de duracao de um loop deste ator 
	 * na animacao.
	 * @return Tempo de duracao de um loop deste ator 
	 * na animacao.
	 * @see Aga#getAnimationEndTime()
	 */
	public int getEndTime(){
		return this.endtime;
	}
	
	/**
	 * Configura o tempo de duracao de um loop deste ator 
	 * na animacao.
	 * @param i Tempo de duracao de um loop deste ator 
	 * na animacao.
	 * @see Aga#getAnimationEndTime()
	 */
	public void setEndTime(int i){
		this.endtime = i;
	}
	
	/**
	 * Retorna a escala a ser aplicada as imagens 
	 * deste ator. Este valor e inicializado com 100%.
	 * @return Escala a ser aplicada as imagens deste ator.
	 * @see FnTransform#scaleTrans(String, AgaActor)	 
	 */
	public int getScale(){
		return this.scale;
	}
	
	/**
	 * Configura a escala a ser aplicada as imagens 
	 * deste ator.
	 * @param i Escala a ser aplicada as imagens deste ator. A 
	 * unidade e porcento (%).
	 * @see FnTransform#scaleTrans(String, AgaActor)	 
	 */
	public void setScale(int i){
		this.scale = i;
	}	
	
	/**
	 * Retorna o valor em graus de rota�ao a ser aplicada as 
	 * imagens deste ator.
	 * @return Valor em graus de rotacao a ser aplicada as 
	 * imagens deste ator.
	 * @see FnTransform#rotateTrans(String, AgaActor)	 
	 */
	public int getRotate(){
		return this.rotatedegree;
	}
	
	/**
	 * Configura o valor em graus de rotacao a ser aplicada as 
	 * imagens deste ator.
	 * @param i Valor em graus de rotacao a ser aplicada as 
	 * imagens deste ator.
	 * @see FnTransform#rotateTrans(String, AgaActor)	 
	 */
	public void setRotate(int i){
		this.rotatedegree = i;
	}	
	
	/**
	 * Retorna true se deve ser aplicado a transformacao flip
	 * na horizontal.
	 * @return True se deve ser aplicado a transformacao flip
	 * na horizontal.
	 * @see FnTransform#flipTrans(String, AgaActor)	 
	 */
	public boolean getFlipH(){
		return this.flip_h;
	}
	
	/**
	 * Retorna true se deve ser aplicado a transformacao flip
	 * na vertical.
	 * @return True se deve ser aplicado a transformacao flip
	 * na vertical.
	 * @see FnTransform#flipTrans(String, AgaActor)	 
	 */
	public boolean getFlipV(){
		return this.flip_v;
	}
		
	/**
	 * Configura o booleano que indica se a transformacao flip 
	 * na horizontal deve ser aplicada.
	 * @param b Valor booleano que indica se a transformacao na 
	 * horizontal deve ser aplicada.
	 * @see FnTransform#flipTrans(String, AgaActor)	 
	 */
	public void setFlipH(boolean b){
		this.flip_h = b;
	}
	
	/**
	 * Configura o booleano que indica se a transformacao flip 
	 * na vertical deve ser aplicada
	 * @param b Valor booleano que indica se a transformacao flip
	 * na vertical deve ser aplicada.
	 * @see FnTransform#flipTrans(String, AgaActor)	 
	 */
	public void setFlipV(boolean b){
		this.flip_v = b;
	}
	
	/**
	 * Retorna true se deve ser aplicado a transformacao matrix.
	 * @return True se deve ser aplicado a transformacao matrix.
	 * @see FnTransform#matrixTrans(String, AgaActor)
	 */
	public boolean getMatrix(){
		return this.matrix;
	}
	
	/**
	 * Configura o booleano que indica se a transformacao matrix deve 
	 * ser aplicada
	 * @param b Valor booleano que indica se a transformacao matrix 
	 * deve ser aplicada.
	 * @see FnTransform#matrixTrans(String, AgaActor)	 
	 */
	public void setMatrix(boolean b){
		this.matrix = b;
	}
   	
   	/**
	 * Retorna os 6 parametros a aplicar na transformacao matrix.
	 * @return Os 6 parametros a aplicar na transformacao matrix.
	 * @see FnTransform#matrixTrans(String, AgaActor)
	 */
	public float getMatrixParam(int i){
		String s = (String) this.matrix_params.elementAt(i);
		
		float f = Float.valueOf(s).floatValue();
		
		return f;
	}
   	
   	/**
	 * Adiciona os 6 parametros da transformacao matrix em um vetor.
	 * @param si Os 6 parametros a serem usados na  transformacao matrix 
	 *
	 * @see FnTransform#matrixTrans(String, AgaActor)	 
	 */
	public void setMatrixParams(String s1,String s2,String s3,String s4,
							   String s5,String s6){
		this.matrix_params.add(new String(s1));
		this.matrix_params.add(new String(s2));
		this.matrix_params.add(new String(s3));
		this.matrix_params.add(new String(s4));
		this.matrix_params.add(new String(s5));
		this.matrix_params.add(new String(s6));
	}
	
	/**
	 * Retorna true se o ator tem som para ser tocado no proximo 
	 * Action do timer.
	 *
	 * @return True se o ator tem som para ser tocado no proximo 
	 * Action do timer.
	 * @see Animator#actionPerformed(ActionEvent e)
	 */
	public boolean hasSoundToPlay(){
		return this.hassoundstoplay;
	}
	
	/**
	 * Configura booleano que indica se o ator tem som 
	 * para ser tocado no proximo Action do timer.
	 *
	 * @param b Booleano para configurar se o ator tem som 
	 * para ser tocado no proximo Action do timer.
	 * @see Animator#actionPerformed(ActionEvent e)
	 */
	public void setHasSoundToPlay(boolean b){
		this.hassoundstoplay = b;
	}
		
	/**
	 * Permite criar um clone desta classe.
	 * Todos os objetos membros desta classe devem ser clonados.
	 */
	public Object clone(){
    	Actor copy = null;
      		
    	try {
    		copy = (Actor) super.clone();
    		
    		if(presentoutput != null)
    			copy.presentoutput = (Vector) this.presentoutput.clone();
    		copy.matrix_params = (Vector) this.matrix_params.clone();    		
   		}
   		catch (CloneNotSupportedException e) {}
      	
      	return copy;      	
   	}
}
