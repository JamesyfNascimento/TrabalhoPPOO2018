import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.border.EmptyBorder;
import java.util.HashMap;
import javax.swing.text.DefaultCaret;



public class SimuladorTela extends JFrame {
    // Colors used for empty locations.
    private static final Color COR_VAZIA = Color.white;

    // Color used for objects that have no defined color.
    private static final Color COR_DESCONHECIDA = Color.gray;

    private final String PASSO = "Passo: ";
    private final String POPULACAO = "Populacao: ";
    private JLabel passoLabel, populacao;
    
    
    private CampoTela campoTela;
    private HistoricoTela historicoTela;


    // A map for storing colors for participants in the simulation
    private Map<Class, Color> cores;
    // A statistics object computing and storing simulation information
    private StatusCampo statusCampo;

    /*
     * Mecanismo auxiliar para a GridBagLayout. Restrições adicionados aos
     * componetes antes de serem adicionados ao layout. (posicionamento, largura,
     * alinhamento)
     */
    
    
    private ThreadRunner threadRunner;

    
   
    
    public SimuladorTela(int altura, int largura) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        threadRunner = new ThreadRunner();

        statusCampo = new StatusCampo();
        cores = new LinkedHashMap<Class, Color>();

        
        setTitle("Trabalho Final Disciplina PPOO");
        passoLabel = new JLabel(PASSO, JLabel.CENTER);
        populacao = new JLabel(POPULACAO, JLabel.CENTER);
//        contents = getContentPane();
//        setLocation(100, 50);

        campoTela = new CampoTela(altura, largura);
               
        montarTelaHistorico(altura, largura);

        frame();

    }
    
    public void frame(){
        JPanel frame = new JPanel();
        frame.setLayout(new BorderLayout());
        frame.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(frame);
        
        
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(2, 2));
        painel.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.add(painel, BorderLayout.CENTER);
        
        // maak field panel (bovenste laag) aan, en border van field panel.
        JPanel campo = new JPanel();
        campo.setLayout(new BorderLayout());
        campo.add(passoLabel, BorderLayout.NORTH);
        campo.add(campoTela, BorderLayout.CENTER);
        campo.add(populacao, BorderLayout.SOUTH);
        painel.add(campo); // field panel toevoegen aan view panel.
        
                
        // textArea panel
        JTextArea textArea = new JTextArea(20, 20);
        historicoTela.setTextArea(textArea);
        textArea.setEditable(false);
        
        // scrollPane voor textArea
        JScrollPane scrollPane = new JScrollPane(textArea);		
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);		
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        painel.add(scrollPane);
	
        
        JPanel containerMenuBotao = new JPanel();
        containerMenuBotao.setLayout(new GridLayout(20, 0)); 
        containerMenuBotao.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        menuBotao(containerMenuBotao);
        
        
        frame.add(containerMenuBotao, BorderLayout.WEST); // toolbar panel toevoegen aan de main frame


        pack();
        //setSize(new Dimension(1024, 768));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        setExtendedState(Frame.MAXIMIZED_BOTH);
    }
    
    
    private void menuBotao(JPanel containerMenuBotao){
        // labels en knoppen
        // step 1 knop
        JButton passo1 = new JButton("1 Passo");
        passo1.addActionListener((ActionEvent e) -> {
            threadRunner.startRun(1);
        });
        containerMenuBotao.add(passo1); // toevoegen aan toolbar panel

        // step 100 knop
        JButton passo100 = new JButton("100 Passos");
        passo100.addActionListener((ActionEvent e) -> {
            threadRunner.startRun(100);
        });
        containerMenuBotao.add(passo100);

        // start knop
        JButton rodar = new JButton("Rodar");
        rodar.addActionListener((ActionEvent e) -> {
            threadRunner.startRun(0);
        });
        containerMenuBotao.add(rodar); // toevoegen aan toolbar panel

        // stop knop
        JButton parar = new JButton("Parar");
        parar.addActionListener((ActionEvent e) -> {
            threadRunner.stop();
        });
        containerMenuBotao.add(parar); // toevoegen aan toolbar panel

        // reset knop
        JButton padrao = new JButton("Padrão");
        padrao.addActionListener((ActionEvent e) -> {
            historicoTela.setStep(0);
            Principal.getSimulador().resetar();
        });
        containerMenuBotao.add(padrao); // toevoegen aan toolbar panel
    }
    
    
    
    private void montarTelaHistorico(int height, int width) {
        historicoTela = new HistoricoTela(height, width);
        historicoTela.setSize(height, width);
        historicoTela.stats(getDetalhePopulacao());
        historicoTela.historico();
    }

    
    public void setColor(Class animalClass, Color color) {
        cores.put(animalClass, color);
    }

    
    private Color getColor(Class animalClass) {
        Color col = cores.get(animalClass);
        if (col == null) {
            // no color defined for this class
            return COR_DESCONHECIDA;
        } else {
            return col;
        }
    }

    
    public void mostrarStatus(int step, Campo campo) {
        if (!isVisible()) {
            setVisible(true);
        }

        passoLabel.setText(PASSO + step);
        statusCampo.resetar();

        campoTela.prepararPintura();

        for (int row = 0; row < campo.getAltura(); row++) {
            for (int col = 0; col < campo.getLargura(); col++) {
                Object animal = campo.getPersonagem(row, col);
                if (animal != null) {
                    statusCampo.incrementaContador(animal.getClass());
                    campoTela.marcarCampo(col, row, getColor(animal.getClass()));
                } else {
                    campoTela.marcarCampo(col, row, COR_VAZIA);
                }
            }
        }
        statusCampo.countFinished();

        populacao.setText(POPULACAO + statusCampo.getDetalhePopulacao(campo));
        campoTela.repaint();
        

        historicoTela.stats(getDetalhePopulacao());
        historicoTela.historico();

    }

    
    public boolean isViable(Campo campo) {
        return statusCampo.isViable(campo);
    }
    
    
    public HashMap<Color, Contador> getDetalhePopulacao() {
        HashMap<Class, Contador> classStats = statusCampo.getPopulacao();
        HashMap<Color, Contador> colorStats = new HashMap<Color, Contador>();

        for (Class c : classStats.keySet()) {
                colorStats.put(getColor(c), classStats.get(c));
        }
        return colorStats;
    }
    
    
    public ThreadRunner getThreadRunner() {
            return threadRunner;
    }

}
