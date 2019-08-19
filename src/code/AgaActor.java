import java.util.Vector;
import java.awt.Image;
import java.util.Hashtable;

/**
 * Esta classe possui a estrutura do elemento ACTOR do XML
 * que gera a animacao. Extende a classe Mealy.
 * 
 * @author  Guilherme de C. Magalh�es
 * @version 2.0
 * @see Mealy
 */

public class AgaActor extends Mealy implements Cloneable {
		/**
	 	 * Atributo do elemento AGA do XML que gera a animacao.
     	 * Identificador do ator.
     	 */
		private String id;
		/**
	 	 * Atributo do elemento AGA do XML que gera a animacao.
     	 * Numero de estados do ator.
     	 */
		private String sts;
		/**
	 	 * Atributo do elemento AGA do XML que gera a animacao.
     	 * Numero de simbolos de entrada diferentes.
     	 */
		private String sbs;
		/**
	 	 * Atributo do elemento AGA do XML que gera a animacao.
     	 * Tipo do ator. GRAPHICS se o ator exibir imagens ou SOUNDS 
     	 * se tocar sons.
     	 */
		private String type;
		
		/**
	 	 * Funcao Transicao do automatos do ator.
     	 * 
     	 * @see Transf
     	 */
		public Vector transf;
				
		/**
	 	 * Informacoes sobre o ator associado a este automatos.
     	 * 
     	 * @see Actor
     	 */
		public Actor actor;
		
		/**
	 	 * Funcao descricao de estados do automatos.
     	 * 
     	 * @see Desc
     	 */
		public Vector desc;
		
		/**
	 	 * Buffer com as imagens usadas na animacao.
	 	 * Os elementos do Hashtable sao (String, Image).
     	 */
		public Hashtable images;
		
		/**
	 	 * Buffer com o sosns usadas na animacao.
	 	 * Os elementos do Hashtable sao (String, AudioClip).
     	 */
		public Hashtable sounds;
		
		/**
	 	 * Constroi um novo objeto AgaActor. Este construtor
	 	 * e o default para esta classe. Operacoes aqui tambem
	 	 * devem ser colocadas no metodo clone.
	 	 *	 	 
	 	 * @see Actor
	 	 * @see #clone()
	 	 */
		public AgaActor(){
			transf = new Vector();
			desc = new Vector();
			actor = new Actor();
			images = new Hashtable();
			sounds = new Hashtable();
			
			initAutomaton();
		}
				
		/**
     	 * Inicializa o estado do automatos. Chamado no inicio de
     	 * cada loop da animacao. As transformacoes sobre as imagens
     	 * sao reiniciadas.
     	 * @see Mealy#initAutomaton()
     	 * @see FnTransform
     	 */    
		public void initAutomaton(){			
			super.initAutomaton();
			
			//inicializa estado atual com o estado inicial
			if (transf.size() > 0){
				Transf tf = (Transf) transf.elementAt(0);
				setPresentState(tf.getSt());				
			}
			
			//inicializa posicao relativa do ator
			actor.setRelativeX(0);
			actor.setRelativeY(0);
			
			//inicializa a visibilidade do ator
			actor.setActorVisible(true);
			
			//inicializa escala para 100%
			actor.setScale(100);
			
			//inicializa rotacao para 0 graus
			actor.setRotate(0);
			
			//inicializa o flip horizontal e vertical para false
			actor.setFlipH(false);
			actor.setFlipV(false);
			
			//inicializa matriz de transformacao
			actor.setMatrix(false);
		}
		
		/**
    	 * metodo utilizado em cada Transicao do automatos.
    	 * Dado o estado corrente do automatos e o simbolo de 
    	 * entrada lido, e retornado a proxima imagem do ator, 
    	 * associado a este automatos, na animacao.
     	 *
     	 * @param     state Estado atual do automatos na animacao.
     	 * @param     sb simbolo lido da fita.
     	 * @return    Estrutura que representa a proxima imagem
     	 * deste ator na animacao.
     	 * @see       Trans
     	 */    
		public Trans transOf(String state, String sb){
			Transf tf;
			Trans trans;
			
			for(int i=0;i<transf.size();i++){
				tf = (Transf) transf.elementAt(i);
				if(tf.getSt().compareTo(state) == 0){
					for(int j=0;j<tf.trans.size();j++){
						trans = (Trans) tf.trans.elementAt(j);
						if(trans.getSb().compareTo(sb) == 0){
							return trans;
						}
					}
				}
			}
			return null;
		}
		
		/**
    	 * Efetua uma Transicao no automatos. Dado um estado e o 
    	 * simbolo lido da fita, e configurado o novo estado e a 
    	 * nova imagem do ator a ser exibida.
     	 *
     	 * @param     state Estado atual do automatos na animacao.
     	 * @param     sb simbolo lido da fita.
     	 * @see       #transOf(String, String)
     	 * @see       #outputOf(String)
     	 */    
		public void transition(String state, String sb){
    		Trans trans;
    		Output output;
    		Vector outputs=new Vector();
    		Vector sbs;
			int i;
			    	
    		super.transition(state, sb);
    		
    		trans = transOf(state,sb);
    	
    		//testa se existe uma Transicao, se for estado final nao ter�
    		//mais transicoes, e o ator permanece inalterado
    		//se for null tambem pode indicar uma indefini��o
    		//para o simbolo lido no corrente estado
    		//neste caso ele continua exibindo a imagem anterior
    		if (trans != null){
    			//atualizar estado atual
    			setPresentState(trans.getSt());
    		
    			//coletar palavra de saida
    			sbs=FnTransform.getArgs(trans.getOut());
    			
    			//atualizar imagem atual
    			for (i=0;i<sbs.size();i++) {
    				output=(Output) outputOf((String) sbs.elementAt(i)); 	
    				outputs.add (output);
    			
    			}
    			actor.setPresentOutput(outputs);
    			
    		}
    	}
				
		/**
    	 * metodo utilizado em cada Transicao no automatos.
    	 * Dado o identificador do simbolo de saida, e retornado
    	 * a estrutura com suas Informacoes.
     	 *     	 
     	 * @param     id Identificador do simbolo de saida.
     	 * @return    Estrutura que contem Informacoes sobre o 
     	 * simbolo de saida e que deve ser a nova imagem do ator
     	 * a ser exibida.
     	 * @see       Output
     	 */    
		public Output outputOf(String id){
			Output out;
			
			for(int i=0;i<output.size();i++){
				out = (Output) output.elementAt(i);
				if(out.getId().compareTo(id) == 0)			
					return out;
			}
			return null;
		}
		
		/**
		 * Copia todas as celulas da fita recebida para a fita 
		 * deste ator.
		 */
		public void copyCells(Vector tp){
			TapeCell newcell,cellp;
			for(int i=0;i<tp.size();i++){
				cellp = (TapeCell) tp.elementAt(i);
				newcell = new TapeCell(cellp.getSb(),
									   Integer.toString(cellp.getTm()),
									   cellp.getFn());
				this.tape.add(newcell);
			}
		}
		
		/**
	 	 * Retorna o identificador deste ator.
	 	 * @return Identificador deste ator.	 	 
	 	 */
		public String getId(){
			return this.id;
		}
		
		/**
	 	 * Configura o identificador deste ator.
	 	 * @param s Identificador deste ator.
	 	 */
		public void setId(String s){
			this.id = s;
		}
		
		/**
	 	 * Retorna o Numero de estados deste automatos.
	 	 * @return Numero de estados deste automatos.	 	 
	 	 */
		public String getSts(){
			return this.sts;
		}
		
		/**
	 	 * Configura o Numero de estados deste automatos.
	 	 * @param s Numero de estados deste automatos.
	 	 */
		public void setSts(String s){
			this.sts = s;
		}
		
		/**
	 	 * Retorna o Numero de simbolos de entrada diferentes 
	 	 * necessarios para este automatos.
	 	 * @return Numero de simbolos de entrada diferentes 
	 	 * necessarios para este automatos.
	 	 */
		public String getSbs(){
			return this.sbs;
		}
		
		/**
	 	 * Configura o Numero de simbolos de entrada diferentes 
	 	 * necessarios para este automatos.
	 	 * @param s Numero de simbolos de entrada diferentes 
	 	 * necessarios para este automatos.
	 	 */
		public void setSbs(String s){
			this.sbs = s;
		}
		
		/**
	 	 * Retorna o tipo do ator. GRAPHICS se o ator exibir 
	 	 * imagens ou SOUNDS se tocar sons.
	 	 * @return Tipo do ator. 
	 	 */
		public String getType(){
			return this.type;
		}
		
		/**
	 	 * Configura o tipo do ator. GRAPHICS se o ator exibir 
	 	 * imagens ou SOUNDS se tocar sons.
	 	 * @param s Tipo do ator.
	 	 */
		public void setType(String s){
			this.type = s;
		}
	 	 
	 	/**
	 	 * Retorna a descricao do estado atual.
	 	 *
	 	 * @return descricao do estado atual.
	 	 */
		public String getCurrentDescription (){
			int i;
			String state = "";
			
			state = getPresentState();
			for (i=0;i<desc.size();i++){
				if (state.compareTo(((Desc)desc.elementAt(i)).getSt())==0) {
					return (((Desc)desc.elementAt(i)).getDesc());					
				}
			}
			
			return ("");
		}	 	 
	 	 
	 	/**
	 	 * Permite criar um clone desta classe.
	 	 * Todos os objetos membros desta classe devem ser clonados.
	 	 */
		public Object clone(){
      		AgaActor copy = null;
      		
      		try {
      			copy = (AgaActor) super.clone();
      			
      			//clona objetos manualmente
      			copy.tape = (Vector) this.tape.clone();
      			copy.desc = (Vector) this.desc.clone();
      			copy.images = (Hashtable) this.images.clone();
      			copy.output = (Vector) this.output.clone();
      			copy.transf = (Vector) this.transf.clone();
      			copy.actor = (Actor) this.actor.clone();
   			}
   			catch (CloneNotSupportedException e) {}
      		
      		return copy;
   		}
}
