import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HistoricoTela extends JPanel
{
	//	text area
	private JTextArea textArea;
	private int step = -1;
	
	//	hashmap die hoeveelheid per kleur bij houdt
	private HashMap<Color, Contador> stats;
	
	public HistoricoTela(int height, int width)
	{
		textArea = new JTextArea(height, width);
	}
	
	
	public void stats(HashMap<Color, Contador> stats)
	{
		this.stats = stats;
	}
	
	
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
                //	tijdelijk blokkeren
                if (!(nStep.equals("Step: " + -1 + "  "))){
                    textArea.append(nStep + buffer.toString() + "\r\n");
                    escreveEmArquivo(buffer.toString());

                }
                step++;
            } catch (Exception e) {
            }
        }
        
        
        public void escreveEmArquivo(String log) throws IOException{
            FileWriter arquivo = null;
            BufferedWriter bw = null;
            try {
                    Date data = new Date();
                    SimpleDateFormat formatador = new SimpleDateFormat("EEE, MMM d");
                    String dt = formatador.format(data).toString();
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("log/log");
                    buffer.append("-");
                    buffer.append(dt);                   
                    buffer.append(".txt");
                    
                    File file= new File(buffer.toString());
                    
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    
                    arquivo = new FileWriter(file.getAbsoluteFile(), true);
                    
                    bw = new BufferedWriter(arquivo);
                    System.out.println(log);
                    bw.write(log);
                    bw.newLine();
            } catch (IOException e) {
                    e.printStackTrace();
            } catch (Exception e) {
                    e.printStackTrace();
            }
            finally{
                try {

                    if (bw != null)
                            bw.close();

                    if (arquivo != null)
                            arquivo.close();

                } catch (IOException ex) {

                    ex.printStackTrace();

                }


            }
        }
	
	
	public void setStep(int step)
	{
		this.step = step;
	}
	
	public void setTextArea(JTextArea textArea)
	{
		this.textArea = textArea;
	}
	
	
	public JTextArea getTextArea()
	{
		return textArea;
	}
}
