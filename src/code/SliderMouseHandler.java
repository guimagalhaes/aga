import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe que extende MouseAdapter e implementa metodos que tratam
 * eventos do mouse sobre o slider. 
 *
 * @author Guilherme de C. Magalhaes
 * @version 1.0
 */

public class SliderMouseHandler extends MouseAdapter {
	/**
	 * Aponta para o objeto Animator.
     */
	private Animator animator;
	
	/**
	 * Constroi um novo objeto SliderMouseHandling. Este construtor
	 * e o default para esta classe.
	 */
	public SliderMouseHandler(Animator a){
		animator = a;
	}
	
	/**
	 * Chamado quando houver o evento mouse release sobre o slider.	 
	 */
	public void mouseReleased(MouseEvent e) {
		JSlider slider = (JSlider)e.getSource();
		if((!slider.getValueIsAdjusting()) && (animator != null)){
			animator.stopAnimation();
			animator.setBaseTime(slider.getValue() / (1000 / animator.parser.aga.getFrameRate()));
			animator.setSoundDisable(true);
			animator.startAnimation();
		}
	}
}
