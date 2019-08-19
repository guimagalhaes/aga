import java.util.List;
import java.util.Iterator;
import java.util.Vector;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import java.net.URL;

/**
 * Uma classe para ler o XML que gera a animacao e carregar
 * as informacoes.
 * 
 * @author  Guilherme de C. Magalh�es
 * @version 2.0
 * @see Aga
 */

public class Parser {
	/**
	 * informacoes do elemento AGA do XML. Cont�m os atores e fitas
	 * usadas para criar as instancias e tamb�m as pr�prias instancias.
	 * @see Aga
	 */
	public Aga aga;

	/**
	 * Metodo auxiliar para tratar o elemento GCL do XML que
	 * gera a animacao.
	 */
	private void gclRec(Element gcl,Tapes tps) {
		int it = Integer.valueOf(gcl.getAttributeValue("ITERATION")).intValue();
		
		List cels = gcl.getChildren();
		
		for (int l=0;l<it;l++)
		{
			Iterator i = cels.iterator();
			while(i.hasNext()){
				Element cel = (Element) i.next();
				if (cel.toString().compareTo("[Element: <CEL/>]") == 0)
					//FN pode ser null
					tps.tape.add(new TapeCell(cel.getAttributeValue("SYMBOL"),
									  		  cel.getAttributeValue("TIME"),
									  		  cel.getAttributeValue("FN")));
				else				
					gclRec(cel,tps);
			}
		}

		return;
	}

	/**
	 * Constroi um novo objeto Parser. Este construtor
	 * e o default para esta classe. Ele le o XML que gera
	 * a animacao e o carrega em memoria.
	 *
	 * @param url Endere�o internet do XML a ser lido. Este endereco
	 * deve estar na mesma m�quina onde estao o arquivo aga.jar.
	 */
	public Parser(String url) {
		Iterator j,k;
		
		AgaActor actor = null;
		Transf tf = null;
		Tapes tps = null;
		Instance instance = null;

		try {			
			//carrega o documento sem validacao
			SAXBuilder builder = new SAXBuilder(false);
			Document doc = builder.build(new URL(url));

			//le elemento AGA
			Element root = doc.getRootElement();
			
			aga = new Aga();
			
			//le atributos do AGA 
			//REPEAT pode ser null
			aga.setVersion(root.getAttributeValue("VERSION"));
			aga.setWidth(root.getAttributeValue("WIDTH"));
			aga.setHeight(root.getAttributeValue("HEIGHT"));
			aga.setBackground(root.getAttributeValue("BACKGROUND"));
			aga.setFrameRate(Integer.valueOf(root.getAttributeValue("FRAMERATE")).intValue());
			aga.setRepeat(root.getAttributeValue("REPEAT"));

			//le elemento HEAD			
			Element head = root.getChild("HEAD");
			aga.setTitle(head.getChild("TITLE").getTextTrim());
			aga.setAuthor(head.getChild("AUTHOR").getTextTrim());
			aga.setSubject(head.getChild("SUBJECT").getTextTrim());
			
			//le elemento ACTOR
			List actors = root.getChildren("ACTOR");
      
			Iterator i = actors.iterator();
			while (i.hasNext()) {
				actor = new AgaActor();
				
				Element act = (Element) i.next();
				
				//le atributos do ACTOR
				actor.setId(act.getAttributeValue("ID"));
				actor.setSts(act.getAttributeValue("STATES"));
				actor.setSbs(act.getAttributeValue("SYMBOLS"));
				actor.setType(act.getAttributeValue("TYPE"));
				
				//le elemento OUTPUT
				//pode nao existir
				try{
					List outputs = act.getChildren("OUTPUT");
					j = outputs.iterator();
					while(j.hasNext()){
						Element output = (Element) j.next();
						//se nao existir o atributo X, o ator � do
						//tipo SOUNDS e nao deve ser inicializado os
						//atributos X e Y
						if (output.getAttribute("X") == null)
							actor.output.add(new Output(
								output.getAttributeValue("ID"),
								output.getAttributeValue("SOURCE")));
						else
							actor.output.add(new Output(
								output.getAttributeValue("ID"),
								output.getAttributeValue("SOURCE"),
								output.getAttributeValue("X"),
								output.getAttributeValue("Y")));
					}
				}
				catch(Exception e){ }
				
				//le elemento DESCF
				//pode nao existir
				try{
					Element descf = act.getChild("DESCF");
					List descs = descf.getChildren("DESCRIPTION");
					j = descs.iterator();
					while(j.hasNext()){
						Element desc = (Element) j.next();
						actor.desc.add(new Desc(
							desc.getAttributeValue("STATE"),
							desc.getTextTrim()));
					}
				}
				catch(Exception e){ }
				
				//le elemento TRANSF
				Element transf = act.getChild("TRANSF");
				List froms = transf.getChildren();
				j = froms.iterator();
				while(j.hasNext()){
					Element from = (Element) j.next();
					actor.transf.add(new Transf(
						from.getAttributeValue("STATE")));
					//le elemento TO
					List tos = from.getChildren("TO");
					k = tos.iterator();
					while(k.hasNext()){
						Element to = (Element) k.next();
						//OUTPUT pode ser null
						tf = (Transf) actor.transf.lastElement();
						tf.trans.add(new Trans(
									to.getAttributeValue("SYMBOL"),
									to.getAttributeValue("STATE"),
									to.getAttributeValue("OUTPUT")));
					}
				}
					
				//adiciona este ator ao vector do aga
				aga.actors.add(actor);
			}
			
			//le elemento TAPE
			List tapes = root.getChildren("TAPE");
			j = tapes.iterator();
			while(j.hasNext()){
				Element tape = (Element) j.next();
				tps = new Tapes();
				tps.setId(tape.getAttributeValue("ID"));
				List cels = tape.getChildren();
				k = cels.iterator();
				while(k.hasNext()){
					Element cel = (Element) k.next();
					//le elemento CEL
					if (cel.toString().compareTo("[Element: <CEL/>]") == 0)
						//FN pode ser null
						tps.tape.add(new TapeCell(
							cel.getAttributeValue("SYMBOL"),
							cel.getAttributeValue("TIME"),
							cel.getAttributeValue("FN")));
					//le elemento GCL
					else
						gclRec(cel,tps);
				}
				aga.tapes.add(tps);
			}
			
			//le elemento INSTANCE
			List instances = root.getChildren("INSTANCE");
			j = instances.iterator();
			while(j.hasNext()){
				Element inst = (Element) j.next();
				instance = new Instance(inst.getAttributeValue("ID"),
										inst.getAttributeValue("ACTOR"),
										inst.getAttributeValue("ORDER"));
				List uses = inst.getChildren("USE");
				k = uses.iterator();
				while(k.hasNext()){
					Element use = (Element) k.next();
					instance.tapes.add(new UseTape(use.getAttributeValue("TAPE")));
				}
				aga.instances.add(instance);
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
