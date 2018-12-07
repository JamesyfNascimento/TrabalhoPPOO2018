import java.awt.Color;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Classe que mostra as informacẽos do esta acontecendo no simulador
 * @author Anderson, Isabela, James
 */
public class HistoricoTela extends JPanel
{
	// Area de texto que mostra informacoes 
	private JTextArea textArea;
	private int step = -1;

	private HashMap<Color, Contador> stats;
	/**
         * A Classe Historico tela esta configurada para mostrar informacoes 
         * sobre o numero de seres vivos no ambiente.
         * @param height
         * @param width 
         */
	public HistoricoTela(int height, int width)
	{
		textArea = new JTextArea(height, width);
	}
	
	/**
         * Recebe as informacoes
         * @param stats 
         */
	public void stats(HashMap<Color, Contador> stats)
	{
		this.stats = stats;
	}
	
	/**
         * A cada passo/iteracao as informacoes sobre os numeros de seres no 
         * campo e atualizado e mostrado na area de texto
         */
	public void historico()
	{
            try {
                StringBuilder buffer = new StringBuilder();
                String nStep = new String();

                nStep =("Passo: " + step + "  ");
                stats.keySet().stream().map((color) -> stats.get(color)).map((Contador info) -> {
                    buffer.append(info.getName());	//mostra informacao
                    return info;
                }).map((info) -> {
                    buffer.append(": ");
                    buffer.append(info.getCount());
                    return info;
                }).forEachOrdered((_item) -> {
                    buffer.append(' ');
                });
                
                if (!(nStep.equals("Step: " + -1 + "  "))){
                    textArea.append(nStep + buffer.toString() + "\r\n");                    

                }
                step++;
            } catch (Exception e) {
            }
        }	
	/**
         * Configuracao dos passos/iteracoes
         * @param step 
         */
	public void setStep(int step)
	{
		this.step = step;
	}
	/**
         * Configura a area de texto
         * @param textArea 
         */
	public void setTextArea(JTextArea textArea)
	{
		this.textArea = textArea;
	}
	
	/**
         * Retorna a area de texto
         * @return textArea
         */
	public JTextArea getTextArea()
	{
		return textArea;
	}
}
