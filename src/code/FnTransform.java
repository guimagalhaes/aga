import java.util.Vector;

/**
 * Esta classe possui os metodos necessarios para efetuar 
 * transformacoes nas imagens.
 * 
 * @author  Guilherme de C. Magalhaes
 * @version 2.0
 * @see Actor
 */

public class FnTransform{
	/**
	 * tipo de transformacao
	 */
	static final int initpos 		= 0;
	static final int translate	 	= 1;
	static final int visible	 	= 2;
	static final int rotate 		= 3;
	static final int scale 			= 4;
	static final int order			= 5;
	static final int flip			= 6;
	static final int matrix			= 7;
	
	/**
	 * Numero de tranforma��es
	 */
	static final int N_ACTIONS = 7;
	
	/**
	 * Enumracao das transformacoes. Devem estar na mesma ordem em que
	 * aparecem na lista acima. Apenas a transformacao initpos nao 
	 * aparece nesta enumaracao.
	 */
	static final String actions[] = {"TRANSLATE","VISIBLE","ROTATE",
									 "SCALE","ORDER","FLIP","MATRIX"};
	
	
	/**
     * Retorna o numero de argumentos na string recebida como
     * parametro.
     *
     * @param     fn  String que possui os parametros.     
     * @return    Numero de argumentos na string fn.
     */
	public static int getNumArgs(String fn){
		int i = 0, count = 0;
		
		fn.trim();
		
		while(i>=0){			
			i = fn.indexOf(" ");
			if(i >= 0) count++;
			fn = fn.substring(i+1);
		}
			
		count++;
		
		return count;
	}
	
	
	/**
     * Usado quando cada palavra de uma string representa um 
     * parametro. Retorna o desejado parametro da lista de parametros
     * recebida na string.
     *
     * @param     fn  String que possui os parametros.
     * @param     argn  Numero do parametro na string fn desejado. 
     * O primeiro parametro e o de Numero 0.
     * @return    parametro desejado da string fn.
     */
	public static String getArg(String fn,int argn){
		int i = 0, count = 0;
		
		fn.trim();
		
		for(int j=0;j<argn;j++){			
			i = fn.indexOf(" ");
			if(i < 0) count++;
			fn = fn.substring(i+1);
		}
		
		if (count > 0) return "";
		
		i = fn.indexOf(" ");
		if (i >= 0)
			fn = fn.substring(0,i);
		
		return fn;
	}
	
	/**
     * Retorna um Vector com todos os argumentos da lista (strings) de 
     * argumentos recebidos como parametro. Esse parametro e um string 
     * com os argumentos separados por ';'. Cada um desses argumentos 
     * e uma lista de parametros para aplicar uma certa transformacao nas 
     * imagens do ator.
     *
     * @param ls String que possui a lista de argumentos
     * @return Vector com todos os argumentos. O vector sera vazio caso 
     * a lista de argumentos recebida seja tambem vazia.
     */
	public static Vector getArgs(String ls){
		Vector v = new Vector();
		String s;
		
		ls.trim();
		
		int i; 
		do{			
			i = ls.indexOf(';');
			if (i >= 0){
				s = ls.substring(0,i);
			}
			else
				s = ls;
			v.add(s.trim());
			
			ls = ls.substring(i+1);
		}
		while(i >= 0);
		
		return v;
	}
	
	/**
     * Retorna o primeiro parametro da string fn. Este parametro
     * representa o tipo de transformacao.
     *
     * @param     fn  String que possui os parametros da 
     * transformacao.
     * @return    Primeiro parametro da string fn.
     */
	public int decode(String fn){
		String action = getArg(fn,0);
		
		for(int i=0;i<N_ACTIONS;i++){
			if(action.compareTo(actions[i]) == 0){
				if(i == 0){
					String param = getArg(fn,3);
					if (param.compareTo("N") == 0)
						return initpos;
					else
						return translate;
				}
				else
					return (i+1);
			}
		}
		 
		return -1;
	}
	
	/**
     * Aplica a transformacao de posicao inicial, inicializando a
     * posicao do ator.
     *
     * @param     fn  String que possui os parametros da 
     * transformacao.
     * @param     actor Objeto AgaActor do respectivo ator a 
     * aplicar a transformacao.
     * @see Actor#getRelativeX()
     * @see Actor#getRelativeY()
     */
	public void initPosTrans(String fn, AgaActor actor){
		int x = Integer.valueOf(getArg(fn,1)).intValue();
		int y = Integer.valueOf(getArg(fn,2)).intValue();
		
		actor.actor.setRelativeX(x);
		actor.actor.setRelativeY(y);
	}
	
	/**
     * Aplica a transformacao de translacao.
     *
     * @param     fn  String que possui os parametros da 
     * transformacao.
     * @param     actor Objeto AgaActor do respectivo ator a 
     * aplicar a transformacao.
     * @see Actor#getRelativeX()
     * @see Actor#getRelativeY()
     */
	public void translateTrans(String fn, AgaActor actor){
		int x = Integer.valueOf(getArg(fn,1)).intValue();
		int y = Integer.valueOf(getArg(fn,2)).intValue();
		
		int x2 = actor.actor.getRelativeX();
		int y2 = actor.actor.getRelativeY();
		
		actor.actor.setRelativeX(x+x2);
		actor.actor.setRelativeY(y+y2);
	}
	
	/**
     * Aplica a transformacao de visibilidade.
     *
     * @param     fn  String que possui os parametros da 
     * transformacao.
     * @param     actor Objeto AgaActor do respectivo ator a 
     * aplicar a transformacao.
     * @see Actor#setActorVisible(boolean b)
     * @see Actor#isActorVisible()
     */
	public void visibleTrans(String fn, AgaActor actor){		
		String s = getArg(fn,1);
		
		//verifica se o ator deve ficar invis�vel
		if (s.compareTo("N") == 0)
			actor.actor.setActorVisible(false);
		else
			actor.actor.setActorVisible(true);
	}
	
	/**
     * Aplica a transformacao de rotacao.
     *
     * @param     fn  String que possui os parametros da 
     * transformacao.
     * @param     actor Objeto AgaActor do respectivo ator a 
     * aplicar a transformacao.
     * @see Actor#setRotate(int i)
     * @see Actor#getRotate()
     */
	public void rotateTrans(String fn, AgaActor actor){
		int degree = Integer.valueOf(getArg(fn,1)).intValue();		
		
		if (getNumArgs(fn) >= 4){
			int x = Integer.valueOf(getArg(fn,2)).intValue();
			int y = Integer.valueOf(getArg(fn,3)).intValue();					
			actor.actor.setRelativeX(x - ((Output) actor.actor.getPresentOutput().elementAt(0)).getX());
			actor.actor.setRelativeY(y - ((Output) actor.actor.getPresentOutput().elementAt(0)).getY());
			//actor.actor.setRelativeX(x);
			//actor.actor.setRelativeY(y);			
			if(getArg(fn,4).compareTo("N") == 0)
				actor.actor.setRotate(degree);
			else
				actor.actor.setRotate(actor.actor.getRotate() + degree);
		}
		else{
			if(getArg(fn,2).compareTo("N") == 0)
				actor.actor.setRotate(degree);
			else
				actor.actor.setRotate(actor.actor.getRotate() + degree);
		}
	}
	
	/**
     * Aplica a transformacao de escala.
     *
     * @param     fn  String que possui os parametros da 
     * transformacao.
     * @param     actor Objetivo AgaActor do respectivo ator a 
     * aplicar a transformacao.
     * @see Actor#setScale(int i)
     * @see Actor#getScale()
     */
	public void scaleTrans(String fn, AgaActor actor){
		int p = Integer.valueOf(getArg(fn,1)).intValue();
		if(getArg(fn,2).compareTo("N") == 0)
			actor.actor.setScale(p);
		else
			actor.actor.setScale(actor.actor.getScale() + p);
	}
	
	/**
     * Aplica a transformacao de troca da ordem em que cada 
     * ator e desenhado na animacao.
     *
     * @param     fn  String que possui os parametros da 
     * transformacao.
     * @param     actor Objetivo AgaActor do respectivo ator a 
     * aplicar a transformacao.
     * @see Instance#getOrder()
     * @see Actor#setFlipV(boolean)
     */
	public void orderTrans(String fn, AgaActor actor,Instance instance,Vector instances){
		Instance inst = null;		
		int old_order = instance.getOrder();
		int order = Integer.valueOf(getArg(fn,1)).intValue();
		
		int count = 1;
		boolean done = false;
		if(order > old_order){
			while(!done){
				for(int i=0;i<instances.size();i++){
					inst = (Instance) instances.elementAt(i);
					if (inst.actor.getType().compareTo("SOUNDS") == 0)
						continue;
					if(inst.getOrder() == (old_order + count)){
						inst.setOrder(inst.getOrder() - 1);
						count++;
					}
					if (count > (order - old_order)){
						done = true;
						break;
					}
				}
			}
		}
		else{
			if(order < old_order){				
				while(!done){
					for(int i=0;i<instances.size();i++){	
						inst = (Instance) instances.elementAt(i);
						if (inst.actor.getType().compareTo("SOUNDS") == 0)
							continue;
						if(inst.getOrder() == (old_order - count)){
							inst.setOrder(inst.getOrder() + 1);
							count++;
						}
						if(count > (old_order - order)){
							done = true;
							break;
						}
					}
				}
			}
		}
		
		instance.setOrder(order);
	}
	
	/**
     * Aplica a transformacao de rebatimento da imagem.
     *
     * @param     fn  String que possui os parametros da 
     * transformacao.
     * @param     actor Objeto AgaActor do respectivo ator a 
     * aplicar a transformacao.
     * @see Actor#setFlipH(boolean)
     * @see Actor#setFlipV(boolean)
     */
	public void flipTrans(String fn, AgaActor actor){
		String e = getArg(fn,1);
		
		//flip composto anula a transformacao
		if(actor.actor.getFlipH()){
			if(e.compareTo("H") == 0){
				actor.actor.setFlipH(false);
				return;
			}
		}
		if(actor.actor.getFlipV()){
			if(e.compareTo("V") == 0){
				actor.actor.setFlipV(false);
				return;
			}
		}
				
		if(e.compareTo("V") == 0)
			actor.actor.setFlipV(true);
		else
			actor.actor.setFlipH(true);
	}
	
	/**
     * Aplica uma matriz de transformacao.
     *
     * @param     fn  String que possui os parametros da 
     * transformacao.
     * @param     actor Objetivo AgaActor do respectivo ator a 
     * aplicar a transformacao.
     * @see Actor#getMatrixParam(int i)
     * @see Actor#setMatrixParams(String s1,String s2,String s3,
     *			String s4,String s5,String s6)
     * @see Actor#setMatrix(boolean b)
     * @see Actor#getMatrix()
     */
	public void matrixTrans(String fn, AgaActor actor){
		String s1 = getArg(fn,1);
		String s2 = getArg(fn,2);
		String s3 = getArg(fn,3);
		String s4 = getArg(fn,4);
		String s5 = getArg(fn,5);
		String s6 = getArg(fn,6);
		
		actor.actor.setMatrix(true);
		actor.actor.setMatrixParams(s1,s2,s3,s4,s5,s6);
		
	}
}
