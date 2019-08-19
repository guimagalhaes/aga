import java.applet.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.*;
import java.net.URL;

/**
 * Applet que gera e exibe a animacao.
 *  
 * @author  Guilherme de C. Magalh�es
 * @version 2.0
 * @see Parser
 */

public class Animator extends JApplet implements ActionListener {
	/**
	 * Objeto para aplicar o parsing no XML.
	 */
	public Parser parser = null;
	/**
	 * Timer para controlar o tempo de cada frame. A cada intervalo 
	 * de tempo configurado conforme o FrameRate ele dispara o evento que exibe 
	 * o frame.
	 * @see #actionPerformed(ActionEvent)
	 * @see Aga#getFrameRate()
	 */
    public Timer timer = null;
    /**
     * Multiplicado ao aga.getFrameRate() resulta o 
     * tempo atual da animacao.
     * @see Aga#getFrameRate()
     */
    private int basetime = 0;
    /**
     * Numero de vezes que a animacao foi iniciada.
     */
    private int animationInit = 1;
    /**
     * Se true o sistema deve pausar a animacao e 
     * aguardar um click do mouse para continuar
     */
    private boolean wait = false;
    /**
     * Cor usada como fundo da imagem.
     */
    private Color color;
    
    /**
     * Componentes Graficos para a interface
     */
    private JButton button1;//play
	private JButton button2;//pause
	private JButton button3;//stop
	private JPanel panel;//painel principal;
	private JPanel pbotoes;//painel de botoes;
	private JPanel pconsulta;//painel de consulta;
	private JComboBox pinstancias;//lista com as instancias
	private JTextField consulta;//texto de consulta
	private JButton button4;//search
	private JSlider slider;//linha de tempo
    
    /**
     * MediaTracker e usado para aguardar o carregamento das 
     * imagens antes de iniciar a animacao.
     */
    private MediaTracker tracker;
    /**
     * O objeto fn cont�m os Metodos para tratar a funcao 
     * que transforma as imagens.
     * @see FnTransform
     */
    private FnTransform fn = new FnTransform();
    
    /**
     * Objeto necess�rio para desenhar offscreen, 
     * evitando flicker.
     */
    private BufferedImage offScreenImage;
	private Graphics offScreenGraphics;
	
	/**
	 * Objeto usado para carregar um frame na animacao e ser passado
	 * para o layout da applet.
	 */
	private JPanel gv;
	
	/**
	 * Fila de sons a serem tocados no proximo Action do timer. O vector
	 * cont�m elementos da classe AudioClip.
	 */
	private Vector queueSounds;
	
	/**
	 * Lista com todos os sons ja tocados na animacao. O vector
	 * cont�m elementos da classe AudioClip.
	 */
	private Vector soundsPlayed;
	
	/**
	 * True se som estiver desabilitado.
	 */
	private boolean soundDisable = false;
	 
	/**
	 * True se devera ser exibido os bot�es de controle. e inicializado
	 * com false e sera configurado com true se no HTML for indicado
	 * "yes" no parametro useControls.
	 */
	private boolean useControls = false;
    
    //as URLs devem terminar com '/'
    /**
     * Endereco web dos gifs.
     */
	private static final String ImagesPath = "http://localhost/aga/images/";	
	//private static final String ImagesPath = "http://teia.inf.ufrgs.br/aga/images/";
	//private static final String ImagesPath = "http://143.54.10.13/aga/images/";
	//private static final String ImagesPath = "http://143.54.10.23/aga/images/";
	/**
     * Endereco web dos wavs e aus.
     */
	private static final String SoundsPath = "http://localhost/aga/sounds/";	
	//private static final String SoundsPath = "http://teia.inf.ufrgs.br/aga/sounds/";
	//private static final String SoundsPath = "http://143.54.10.13/aga/sounds/";
	//private static final String SoundsPath = "http://143.54.10.23/aga/sounds/";
	/**
	 * Endereco web do xml.
	 */
	private static final String XMLPath = "http://localhost/aga/xml/";
	//private static final String XMLPath = "http://teia.inf.ufrgs.br/aga/xml/";
	//private static final String XMLPath = "http://143.54.10.13/aga/xml/";
	//private static final String XMLPath = "http://143.54.10.23/aga/xml/";

	/**
	 * Configura o valor do contador de tempo da animacao.
	 * @param i Valor do contador de tempo da animacao.
	 */
	public void setBaseTime(int i){
		basetime = i;
	}
	
	/**
	 * Habilita ou desabilita o som na animacao.
	 * @param b True para desabilitar o som, false para habilitar o som.
	 */
	public void setSoundDisable(boolean b){
		soundDisable = b;
	}
	
	/**
	 * Para todos os sons que estiverem tocando na animacao.
	 */
	private void stopSounds() {
		AudioClip sound = null;
		
		for (int i=0;i<soundsPlayed.size();i++){
			sound = (AudioClip) soundsPlayed.elementAt(i);
			if (sound != null)
        		sound.stop();
        }
	}

	/**
	 * Ativa o timer.
	 * @see #timer
	 */
    public synchronized void startAnimation() {
    	//inicia ou reinicia a animacao
    	if (!timer.isRunning()){
        	timer.start();
        }
        if(wait)
        	wait = false;
    }
	
	/**
	 * Desativa o timer. Mantem estados do automato de cada ator.
	 * @see #timer
	 */
    public synchronized void pauseAnimation() {
        //pausa a animacao
        if (timer.isRunning()){
        	timer.stop();
        	stopSounds();
        }
    }
	
	/**
	 * Desativa o timer e reinicia o automato de cada ator.
	 * @see #timer
	 * @see AgaActor#initAutomaton()
	 */
    public synchronized void stopAnimation() {
        //p�ra a animacao e reinicia os atores
        if (timer.isRunning()){
        	timer.stop();        	
        	stopSounds();
    	}
        initAutomatons();
    }
    
    /**
     * Libera a execucao da animacao
     * @see #wait
     */
	public void mouseReleaseWait(){
		startAnimation();
	}
    
    /**
	 * Inicializa todos os automatos desta animacao.
	 * @see AgaActor#initAutomaton()
	 */
    public void initAutomatons(){
    	Instance inst;
    	
    	basetime = 0;
        for(int i=0;i<parser.aga.instances.size();i++){
        	inst = (Instance) parser.aga.instances.elementAt(i);
        	if (inst.actor.getType().compareTo("SOUNDS") != 0)
        		inst.recoverOrder();
        	inst.actor.initAutomaton();
        }
    }
    
    /**
     * Retorna true se ja houve alguma transicao em algum automato.
     * Se retornar true, o buffer usado para gerar cada quadro da
     * animacao e inicializado.
     */
    protected boolean transitionHappen(){
    	Instance inst = null;
    	
    	for(int i=0;i<parser.aga.instances.size();i++){
    		inst = (Instance) parser.aga.instances.elementAt(i);
    		AgaActor agaactor = inst.actor;
        	if (agaactor.getPresentCell() > 0)
        		return true;
        }
        
        return false;
    }
        
    /**
     * Retorna o indice no vetor do ator com ordem recebida como parametro.
     *
     * @return indice no vetor do ator com ordem recebida como parametro.
     * Retorna -1 se nao encontrar instancia com essa ordem.
     *
     * @param     i  Ordem do ator.
     * @see Instance#getOrder()
     * @see Instance#setOrder(int i)
     */
    protected int getNextActor(int order){
    	Instance inst = null;
    	
    	for(int i=0;i<parser.aga.instances.size();i++){
    		inst = (Instance) parser.aga.instances.elementAt(i); 
    		
    		if (inst.actor.getType().compareTo("SOUNDS") == 0)
				continue;   		
        	if (inst.getOrder() == order)
        		return i;
        }
        return -1;
    }

	/**
	 * Evento gerado pelo timer ou pelos botoes de controle. Aqui alem 
	 * de aplicada transicoes para cada automato, afim de configurar a 
	 * proxima imagem de cada ator, tambem e configurada as transformacoes 
	 * nessas imagens.
	 * Se algum bot�o de controle for clicado, aqui sera manipulado o
	 * respectivo evento.
	 *
	 * @see FnTransform
	 */
    public void actionPerformed(ActionEvent e) {
    	AgaActor agaactor = null;
    	TapeCell cell = null;
    	Instance inst = null;
    	String s="";
    	boolean modoConsulta=false;
    	
    	//eventos dos bot�es
    	//PLAY
    	if (e.getSource()==button1){
    		startAnimation();
    		return;
    	}
    	//PAUSE
    	if (e.getSource()==button2){
    		pauseAnimation();
    		//se retirado essa proxima linha, cada vez que for apertado
    		//o botao PAUSE teremos o avanco de um quadro, possibilitando
    		//assim o recurso quadro a quadro.
    		return;
    	}
    	//STOP
    	if (e.getSource()==button3){
    		stopAnimation();
    		return;
    	}
    	//CONSULTA
    	if (e.getSource()==button4){
    		modoConsulta=true; 
    		pauseAnimation();   		    		
    	}
    	
    	//ter� o tempo absoluto atual da animacao
    	int animationTime;
    	//sera true se houve mudanca no frame
    	//se estiver false indica que o frame nao se alterou
    	//e sendo assim o frame nao deve ser redesenhado
    	boolean redraw = false;
        
        //se a animacao esta em estado WAIT, nada a fazer
        if (wait) return;
        
        do{
        	//calcula tempo atual da animacao
        	animationTime = basetime * (1000 / parser.aga.getFrameRate());
        	//incrementa contagem de tempo
        	basetime++;
        	
        	//atualiza a linha de tempo
        	if (slider!=null) {
				slider.setValue(animationTime);			
			}
        	
	        //testa fim da itera��o da animacao
	        if (animationTime <= parser.aga.getAnimationEndTime()){
	        	for(int i=0;i<parser.aga.instances.size();i++){
	        		inst = (Instance) parser.aga.instances.elementAt(i);
	        		agaactor = inst.actor;
	        		//inicializa a celula da fita
	        		cell = null;
	        		
	        		//loop para tratar todos os frames pendentes
	        		//pega celula atual e prepara proxima se estiver 
	        		//no tempo do animationTime
	        		while (agaactor.getPresentCell() < agaactor.tape.size()){
	        			cell = (TapeCell) agaactor.tape.elementAt(agaactor.
	        				getPresentCell());
	        			
	        			if (cell == null) break;
	        			
	        			//aponta para proxima celula
	        			if (cell.getTm() <= animationTime){
	        				agaactor.setPresentCell(agaactor.
	        					getPresentCell() + 1);
	        				
	        				//efetua a transicao deste automato
	        				if ((cell.getSb().compareTo("WAIT") == 0) && (!modoConsulta)){
	        					wait = true;
	        					pauseAnimation();
	        				}
	        				//aplicar a transicao e gerar nova saida
	        				//se o simbolo da fita nao for EMPTY
	        				if(cell.getSb().compareTo("EMPTY") != 0){
	        					agaactor.transition(agaactor.getPresentState(),
	        						cell.getSb());
	        					//um dos automatos pelo menos sofreu uma 
	        					//transicao, entao frame deve ser redesenhado
	        					//e sons tocados 
	        					if ((agaactor.getType().compareTo("SOUNDS") == 0) && (!soundDisable))
	        						agaactor.actor.setHasSoundToPlay(true);
	        					redraw = true;
	        				}
	        				
	        				//tratar FN
	        				//aplica-se FN a imagem que sera apresentada pelo 
	        				//automato, que esta em agaactor.getPresentOutput()
	        				if (cell.getFn() != null){
	        					//cria vector com todas os parametros de 
	        					//todas as transformacoes a serem aplicadas
	        					Vector args = fn.getArgs(cell.getFn());
	        					for (int j=0;j<args.size();j++){
	        					  	int action = fn.decode((String) args.elementAt(j));
	        					  	switch(action){
	        							case FnTransform.initpos:
	        								fn.initPosTrans((String) args.elementAt(j),agaactor);
	        								break;
	        							case FnTransform.translate:
		        							fn.translateTrans((String) args.elementAt(j),agaactor);
		        							break;
	        							case FnTransform.visible:
	          								fn.visibleTrans((String) args.elementAt(j),agaactor);
		        							break;
	        							case FnTransform.rotate:
	          								fn.rotateTrans((String) args.elementAt(j),agaactor);
		        							break;
	        							case FnTransform.scale:
	        								fn.scaleTrans((String) args.elementAt(j),agaactor);
		        							break;
		        						case FnTransform.order:
	        								fn.orderTrans((String) args.elementAt(j),agaactor,inst,parser.aga.instances);
		        							break;
		        						case FnTransform.flip:
	        								fn.flipTrans((String) args.elementAt(j),agaactor);
		        							break;
		        						case FnTransform.matrix:
	        								fn.matrixTrans((String) args.elementAt(j),agaactor);
		        							break;
	        						}
	        					}
	        				}
	        				
	        				//Verifica se ha alguma descricao compativel
	        				if ((modoConsulta) && (i == pinstancias.getSelectedIndex())){
	        					s=inst.actor.getCurrentDescription();
	        					for (int k=0;k<=(s.length()-consulta.getText().length());k++){
	        						if (s.regionMatches(true,k,consulta.getText(),0,consulta.getText().length())){					
	        							modoConsulta=false;
	        							break;
	        						}
	        					}
	        				}
	        			}
	        			else{
	        				//quebra o loop e vai para o proximo ator
	        				break;         				
	        			}        			
	        		}
	        	}
	        }
	        else{
	        	//animacao chegou ao fim desta iteracao
	        	
	        	//incrementa Numero de vezes que animacao foi iniciada
	        	animationInit++;
	        	
	        	if (modoConsulta) {
	        			JOptionPane.showMessageDialog(null,"Estado nao encontrado para este ator.");
	        			modoConsulta=false;
	        			startAnimation();
	        	}
	        	
	        	//limpa lista de sons tocados na animacao
	        	soundsPlayed.clear();
	        	
	        	//verifica se animacao deve ser reiniciada ou terminada
	        	if (parser.aga.getRepeat().compareTo("LOOP") == 0){        		
	        		initAutomatons();
	        	}
	        	else{
	        		int i = Integer.valueOf(parser.aga.getRepeat()).intValue();
	       			if (animationInit > i)
		       			stopAnimation();
		       		else{
		       			initAutomatons();
		       		}
	        	}        	
	    	}
    	} while (modoConsulta);
    	
    	//libera o som, caso tenha sido desabilitado ao ser clicado no slide
    	soundDisable = false;
                
        //chama o Metodo update() se houve mudan�a de frame
        if (redraw)
			repaint();		
    }
	
	/**
	 * Desenha frame na tela.
	 */
	public void paint(Graphics g) {		
		if (useControls)
			panel.repaint();
    	gv.getGraphics().drawImage(offScreenImage,0,0,this);    	
    }
	
	/**
	 * Exibe um frame da animacao. Desencadeado pelo repaint().
	 * Cada frame � desenhado em um buffer offscreen no Metodo paint(), 
	 * e, quando o buffer estiver pronto, � exibido.
	 *
	 * @see #paint(Graphics)
	 */
	public final synchronized void update(Graphics g) {
		AudioClip sound;
		
		//verifica se ja houve uma transicao em algum automato
    	if(transitionHappen()){
	    	//carrega novo frame offscreen
	    	paintFrame(offScreenGraphics);
		    
    		//toca todos os sons na fila
    		for(int i=0;i<queueSounds.size();i++){
    			sound = (AudioClip) queueSounds.elementAt(i);
    			if (sound != null){
    				sound.play();
    				soundsPlayed.add(sound);
    			}
    		}
    		
    		//limpa a fila de sons
    		queueSounds.clear();
		}
		
		paint(g);
	}
	
	/**
	 * Gera o buffer com o frame a ser exibido. Aqui, 
	 * as transformacoes nas imagens sao aplicadas.
	 */
	public void paintFrame(Graphics g) {
		Instance inst = null;
		AgaActor agaactor = null;
		Vector outputs = null;
		Output output = null;
		Image image = null;
		AudioClip sound = null;
		int index;
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform at = new AffineTransform();
		
		//reseta a transformacao
        g2d.setTransform(at);
		//limpa o frame
		g2d.clearRect(0,0,getWidth(),getHeight());
		g2d.fillRect(0,0,getWidth(),getHeight());
		
		//cria fila com os sons que devem ser tocados no proximo frame
		for(int i=0;i<parser.aga.instances.size();i++){
			inst = (Instance) parser.aga.instances.elementAt(i);
			if (inst != null){
        		agaactor = inst.actor;
        		
        		//se nao for do tipo SOUNDS passa para proximo
        		if (agaactor.getType().compareTo("SOUNDS") != 0)
        			continue;
        		
        		outputs = agaactor.actor.getPresentOutput();
        		if (outputs !=null) {
        			output=(Output) outputs.elementAt(0);
        			if (output != null){
        				if (agaactor.actor.hasSoundToPlay()){
        					sound = (AudioClip) agaactor.sounds.get(output.getId());
        					queueSounds.add(sound);
        					//configura como falso para nao ser tocado na 
        					//proxima vez
        					agaactor.actor.setHasSoundToPlay(false);
        				}
					}
				}
			}
		}
		
		//monta frame
		for(int i=0;i<parser.aga.instances.size();i++){
			//recupera proximo ator na ordem
			index = getNextActor(i+1);
			if (index < 0)
				break;
			inst = (Instance) parser.aga.instances.elementAt(index);
			if (inst != null){
        		agaactor = inst.actor;
        		
        		//se nao for do tipo GRAPHICS passa para proximo
        		if (agaactor.getType().compareTo("GRAPHICS") != 0)
        			continue;
        		
        		//se ator nao esta visivel passa para proximo
        		if (agaactor.actor.isActorVisible() == false)
        			continue;
        		
        		outputs = agaactor.actor.getPresentOutput();
        		if (outputs != null){
	        		for (int j=0;j<outputs.size();j++) {
        				output=(Output) outputs.elementAt(j);
        				image = (Image) agaactor.images.get(output.getId());
	        			
	        			if (image != null){
	        				//reseta a transformacao
	        				g2d.setTransform(at);
	        				
	        				//aplica uma matriz de transformacao
	        				if(agaactor.actor.getMatrix())
	        					g2d.setTransform(new AffineTransform(
	        						agaactor.actor.getMatrixParam(0),
	        						agaactor.actor.getMatrixParam(1),
	        						agaactor.actor.getMatrixParam(2),
	        						agaactor.actor.getMatrixParam(3),
	        						agaactor.actor.getMatrixParam(4),
	        						agaactor.actor.getMatrixParam(5)));
	        				
	        				//se escala e diferente de 100% ela deve ser aplicada
	        				if (agaactor.actor.getScale() != 100){
	        					g2d.scale(agaactor.actor.getScale() / 100.0,
	        						agaactor.actor.getScale() / 100.0);
	        				}
	        			 
	        			 	//se rotacao e diferente de 0 grau ela deve ser aplicada
	        				if (agaactor.actor.getRotate() != 0){        					
	        					g2d.rotate(agaactor.actor.getRotate() * Math.PI / 180.0);
	        				}
	        			       				
	        			   	//aplica translacao	a imagem
	        			   	g2d.translate(agaactor.actor.getRelativeX() + output.getX(),
	        					agaactor.actor.getRelativeY() + output.getY());
	        			       				
	        				//flip horizontal
	        				if(agaactor.actor.getFlipH()){
	        					g2d.scale(1.0,-1.0);		
								g2d.translate((double)0.0, -((double)image.getHeight(this)));
	        				}
	        				//flip vertical
	        				if(agaactor.actor.getFlipV()){
	        					g2d.scale(-1.0,1.0);	        						
								g2d.translate(-((double)image.getWidth(this)),(double)0.0);
	        				}
	        			
	        				//desenha a imagem no buffer
	        				g2d.drawImage(image,null,this);
	        			}
        			}
    			}
    		}
		}
	}
	
	/**
	 * Inicializa e dispara a animacao.
	 */
    public void init() {
        AgaActor agaactor = null;
		Output output = null;
		TapeCell cell = null;
		Image image;		
		AudioClip sound;
		
		int delay;
		//nome do arquivo xml
    	String xmldoc;
    	String controls;
		
		xmldoc = getParameter("xml");
		controls = getParameter("useControls");
		
		//le o xml e o carrega na mem�ria
		parser = new Parser(XMLPath + xmldoc);
		
        //configura a cor de fundo
        color = parser.aga.getBackground();
        //setBackground(color);
        
        this.getContentPane().setLayout(new BorderLayout());
							       
        if (controls.compareTo("yes") == 0){     		     	
			useControls = true;
	       	panel = new JPanel();
        	panel.setBackground(Color.lightGray);
	        panel.setLayout(new GridLayout(3,1));
	        this.getContentPane().add(panel,BorderLayout.SOUTH);
	        
	        pbotoes=new JPanel();
	        pbotoes.setLayout(new GridLayout(1,4));
	        panel.add(pbotoes);
	        	                
	       	button1 = new JButton(">");
           	pbotoes.add(button1);
        	
        	button2 = new JButton("||");
        	pbotoes.add(button2);
        	
        	button3 = new JButton("[]");
        	pbotoes.add(button3);
         	
         	button4 = new JButton(">?");
        	pbotoes.add(button4);
        	
        	pconsulta = new JPanel();
        	pconsulta.setLayout(new GridLayout(1,2));
        	panel.add(pconsulta);
        	
	        pinstancias = new JComboBox();
	        pconsulta.add(pinstancias);
         	         	
	        consulta = new JTextField();
	        pconsulta.add(consulta);
	        
	        slider = new JSlider();
	        panel.add(slider);	                   
	        	        
	        //conecta os bot�es ao gerenciador de eventos
	        button1.addActionListener(this);
	        button2.addActionListener(this);
	        button3.addActionListener(this);
	        button4.addActionListener(this);	                	        
    	}
    	
    	gv = new JPanel();
    	this.getContentPane().add(gv,BorderLayout.CENTER);
    	gv.setDoubleBuffered(false);
                
        tracker = new MediaTracker(this);
                
        for(int i=0;i<parser.aga.actors.size();i++){
			agaactor = (AgaActor) parser.aga.actors.elementAt(i);
			//inicializa Hashtable de imagens ou sons do ator
        	for(int j=0;j<agaactor.output.size();j++){
				output = (Output) agaactor.output.elementAt(j);
				try{
					if (agaactor.getType().compareTo("SOUNDS") == 0){						
						sound = getAudioClip(new URL(SoundsPath + output.getSource()));
						agaactor.sounds.put(output.getId(), sound);
					}
					else{
						image = getImage(new URL(ImagesPath + output.getSource()));
						agaactor.images.put(output.getId(), image);
						tracker.addImage(image,0);
					}
				}
				catch(Exception e){}
			}
						
			//inicializa automato do ator
			agaactor.initAutomaton();
		}
		
		try{
        	//aguarda o carregamento das imagens
			tracker.waitForAll();
		}
		catch (InterruptedException ie){}
		
		//cria as instancias
		AgaActor act;
		Instance inst;
		UseTape usetp;
		Tapes tapes;
		for(int i=0;i<parser.aga.instances.size();i++){
			inst = (Instance) parser.aga.instances.elementAt(i);
			//adiciona instancias na barra de rolagem
			if (pinstancias != null) pinstancias.addItem(inst.getId());
			
			//gera o ator dessa instancia
			for(int j=0;j<parser.aga.actors.size();j++){
				act = (AgaActor) parser.aga.actors.elementAt(j);
				if (inst.getActorId().compareTo(act.getId()) == 0){					
					inst.actor = (AgaActor) act.clone();
															
					//gera a fita desse ator
					for(int k=0;k<inst.tapes.size();k++){
						usetp = (UseTape) inst.tapes.elementAt(k);
						for(int l=0;l<parser.aga.tapes.size();l++){
							tapes = (Tapes) parser.aga.tapes.elementAt(l);
							if(usetp.getTape().compareTo(tapes.getId()) == 0){
								inst.actor.copyCells(tapes.tape);
								break;
							}
						}
					}
										
					//ajusta valores da fita
					//tempo inicial � zero
					int k = 0, aux = 0;
					for(int l=0;l<inst.actor.tape.size();l++){
						cell = (TapeCell) inst.actor.tape.elementAt(l);
						aux = cell.getTm();
						cell.setTm(k);
						k += aux;
					}
					//calcula o tempo de fim da animacao
					inst.actor.actor.setEndTime(k);
					if (parser.aga.getAnimationEndTime() < inst.actor.actor.getEndTime())
						parser.aga.setAnimationEndTime(inst.actor.actor.getEndTime());
					
					break;
				}
			}
		}
		
		//deleta atores e fitas do Aga
		parser.aga.actors = null;
		parser.aga.tapes = null;
		
		//registra a classe para esperar eventos do mouse
        MouseHandler mh = new MouseHandler(this);
        addMouseListener(mh);
        		
		//Ajusta linha de tempo
		if (slider!=null) {
			slider.setMaximum(parser.aga.getAnimationEndTime());
			slider.setMinimum(0);
			slider.setValue(0);
			
			SliderMouseHandler scl = new SliderMouseHandler(this);
			slider.addMouseListener(scl);
		}
		
		queueSounds = new Vector();
		soundsPlayed = new Vector();
		
		//configura o tempo de espera para atualizar o frame
		delay = 1000 / parser.aga.getFrameRate();
		
		timer = new Timer(delay, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
        
        //Constroi Buffer de Imagem
		offScreenImage = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_RGB);	        
        offScreenGraphics = offScreenImage.getGraphics();		
        
        if (!useControls) {
   			startAnimation();   	
   		}
   		else{
   			offScreenGraphics.setFont(new Font("SansSerif",Font.BOLD,20));
        	offScreenGraphics.setColor(new Color(255,255,0));
        	offScreenGraphics.drawString("AGA versao 2.1",10,30);
       	 	offScreenGraphics.setColor(new Color(255,255,255));
        	offScreenGraphics.setFont(new Font("SansSerif",Font.PLAIN,14));       
        	offScreenGraphics.drawString("T�TULO: "+ parser.aga.getTitle(),10,70);
        	offScreenGraphics.drawString("AUTOR: "+parser.aga.getAuthor(),10,85);
   		}     
   		
		offScreenGraphics.setColor(color);
    }
}
