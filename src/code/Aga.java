import java.util.Vector;
import java.awt.Color;

/**
 * Esta classe possui a estrutura que representa o elemento AGA
 * no XML que gera a animacao.
 * Esta estrutura contem o campo actors que e um vetor
 * que contem todos os automatos da animacao, tapes, que contem
 * todas as fitas e instances que possui as instancias de automatos
 * (actors) com suas respectivas fitas (tapes).
 * Um automato e sua fita voo determinar a acao do seu respectivo ator
 * na animacao.
 * 
 * @author  Guilherme de C. Magalh�es
 * @version 2.0
 * @see Instance
 * @see AgaActor
 * @see Tapes
 */

public class Aga {	
	/**
	 * Atributo do elemento AGA do XML que gera a animacao.
     * Indica a versao da especificacao do AGA.
     */
	private String version;
	
	/** 
     * Atributo do elemento AGA do XML que gera a animacao.
 	 * Indica a largura da applet.
 	 */
 	private String width; 	
 	/** 
	 * Atributo do elemento AGA do XML que gera a animacao.
 	 * Indica a altura da applet.
 	 */
	private String height;
	
	/**
	 * Atributo do elemento AGA do XML que gera a animacao.
     * Representa a cor do fundo da applet.
     * 
     * @see #setBackgroud()
     * @see #getBackgroud()
     */
	private Color background;
	
	/**
	 * Atributo do elemento AGA do XML que gera a animacao.
     * numero de quadros por segundo.     
     */
	private int framerate;
	
	/**
     * Atributo do elemento AGA do XML que gera a animacao.
     * numero de vezes que a animacao sera executada.
     */
	private String repeat;	
	
	/**
     * Atributo do elemento HEAD do XML que gera a animacao.
     */
	private String title;
	private String author;
	private String subject;	
	
	/**
     * Vector com as instancias de atores.
     *
     * @see Instance
     */
	public Vector instances;
	
	/**
     * Vector com os automatos de cada ator.
     * 
     * @see AgaActor
     */
	public Vector actors;
	
	/**
     * Vector com as fitas.
     *
     * @see Tapes
     */
	public Vector tapes;
	
	/**
     * Tempo em que acabara uma iteracao da animacao.
     *
     * @see #getAnimationEndTime()
     * @see #setAnimationEndTime()
     */
	private int animationEndTime;
	
	/**
	 * constroi um novo objeto AGA. Este construtor
	 * e o default para esta classe.
	 */	 
	public Aga() {
		instances = new Vector();
		actors = new Vector();
		tapes = new Vector();
		
		background = Color.white;
		animationEndTime = 0;
	}
	
	/**
	 * Retorna a versao da especificacao do sistema.
	 * @return versao do sistema.
	 */
	public String getVersion(){
		return this.version;
	}
	
	/**
	 * Configura a versao da especificacao do sistema.	 
	 * @param s versao do sistema.
	 */
	public void setVersion(String s){
		this.version = s;
	}
	
	/**
	 * Retorna a largura da applet.
	 * @return Largura da applet.
	 */
	public String getWidth(){
		return this.width;
	}
	
	/**
	 * Configura a largura da applet.
	 * @param s Largura da applet.
	 */
	public void setWidth(String s){
		this.width = s;
	}
	
	/**
	 * Retorna a altura da applet.
	 * @return Altura da applet.
	 */
	public String getHeight(){
		return this.height;
	}
	
	/**
	 * Configura a altura da applet.
	 * @param s Altura da applet
	 */
	public void setHeight(String s){
		this.height = s;
	}
		
	/**
     * Retorna a cor usada no fundo da applet.
     *
     * @return Cor usada no fundo da applet.
     */
	public Color getBackground(){
		return this.background;
	}
	
	/**
     * Configura a cor usada no fundo da applet.
     *
     * @param s String com os tr�s parametros RGB em decimal 8 bits 
     * para configurar a cor de fundo da applet.
     * 
     */
	public void setBackground(String s){
		int r = Integer.valueOf(FnTransform.getArg(s,0)).intValue();
		int g = Integer.valueOf(FnTransform.getArg(s,1)).intValue();
		int b = Integer.valueOf(FnTransform.getArg(s,2)).intValue();
		
		this.background = new Color(r,g,b);	
	}
	
	/**
	 * Retorna o tempo base de um frame da animacao.
	 * @return Tempo base de um frame da animacao.
	 */
	public int getFrameRate(){
		return this.framerate;
	}
	
	/**
	 * Configura o tempo base de um frame da animacao.
	 * @param i Tempo base de um frame da animacao.
	 */
	public void setFrameRate(int i){
		this.framerate = i;
	}
	
	/**
	 * Retorna o numero de vezes que um loop da animacao 
	 * sera repetida.
	 * @return numero de vezes que um loop da animacao 
	 * sera repetida.
	 */
	public String getRepeat(){
		return this.repeat;
	}
	
	
	/**
	 * Configura o numero de vezes que um loop da animacao 
	 * sera repetida.
	 * @param s numero de vezes que um loop da animacao sera 
	 * repetida.
	 */
	public void setRepeat(String s){
		this.repeat = s;
	}
	
	/**
	 * Retorna o titulo da animacao.
	 * @return titulo da animacao.
	 */
	public String getTitle(){
		return this.title;
	}
	
	/**
	 * Configura o titulo da animacao.
	 * @param s titulo da animacao.
	 */
	public void setTitle(String s){
		this.title = s;
	}
	
	/**
	 * Retorna o autor da animacao.
	 * @return Autor da animacao.
	 */
	public String getAuthor(){
		return this.author;
	}
	
	/**
	 * Configura o Autor da animacao.
	 * @param s Autor da animacao.
	 */
	public void setAuthor(String s){
		this.author = s;
	}
	
	/**
	 * Retorna a descricao da animacao.
	 * @return descricao da animacao.
	 */
	public String getSubject(){
		return this.subject;
	}
	
	/**
	 * Configura a descricao da animacao.
	 * @param s descricao da animacao.
	 */
	public void setSubject(String s){
		this.subject = s;
	}	
		
	/**
     * Retorna o tempo de cada loop da animacao em milissegundos.
     *
     * @return Tempo de cada loop da animacao em milissegundos.
     * 
     */
	public int getAnimationEndTime(){
		return this.animationEndTime;
	}
	
	/**
     * Configura o tempo de duracao de cada loop da animacao.
     *
     * @param i Tempo em milissegundos.
     * 
     */
	public void setAnimationEndTime(int i){
		this.animationEndTime = i;
	}	
}
