import java.awt.Color;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Classe que mostra as informaçẽos do está acontecendo no simulador
 * @author Anderson, Isabela, James
 */
public class HistoricoTela extends JPanel
{
	// Area de texto que mostra informações 
	private JTextArea textArea;
	private int step = -1;

	private HashMap<Color, Contador> stats;
	/**
         * A Classe Historico tela está configurada para mostrar informações 
         * sobre o número de seres vivos no ambiente.
         * @param height
         * @param width 
         */
	public HistoricoTela(int height, int width)
	{
		textArea = new JTextArea(height, width);
	}
	
	/**
         * Recebe as informações
         * @param stats 
         */
	public void stats(HashMap<Color, Contador> stats)
	{
		this.stats = stats;
	}
	
	/**
         * A cada passo/iteração as informações sobre os números de seres no 
         * campo é atualizado e mostrado na área de texto
         */
	public void historico()
	{
            try {
                StringBuffer buffer = new StringBuffer();
                String nStep = new String();

                nStep =("Passo: " + step + "  ");
                for(Color color : stats.keySet()){
                    Contador info = stats.get(color);
                    buffer.append(info.getName());	//	show info
                    buffer.append(": ");
                    buffer.append(info.getCount());
                    buffer.append(' ');
                }
                
                if (!(nStep.equals("Step: " + -1 + "  "))){
                    textArea.append(nStep + buffer.toString() + "\r\n");                    

                }
                step++;
            } catch (Exception e) {
            }
        }	
	/**
         * Configuração dos passos/iterações
         * @param step 
         */
	public void setStep(int step)
	{
		this.step = step;
	}
	/**
         * Configura a área de texto
         * @param textArea 
         */
	public void setTextArea(JTextArea textArea)
	{
		this.textArea = textArea;
	}
	
	/**
         * Retorna a área de texto
         * @return textArea
         */
	public JTextArea getTextArea()
	{
		return textArea;
	}
}
