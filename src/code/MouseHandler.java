import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe que extende MouseAdapter e implementa metodos que tratam
 * eventos do mouse. 
 *
 * @author Guilherme de C. Magalh�es
 * @version 2.0
 *
 */

public class MouseHandler extends MouseAdapter {
	/**
	 * Aponta para o objeto Animator.
     */
	private Animator animator;
	
	/**
	 * Constroi um novo objeto MouseHandling. Este construtor
	 * � o default para esta classe.
	 */
	public MouseHandler(Animator a){
		animator = a;
	}	
		 
	/**
	 * Chamado quando houver o evento clique do mouse sobre a applet.
	 * Reinicia a execu��o da animacao.
	 * @see Animator#mouseReleaseWait()
	 */
	public void mousePressed(MouseEvent e) {
		if(animator.timer != null)
			animator.mouseReleaseWait();
	}
}
