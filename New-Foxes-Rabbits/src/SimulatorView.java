import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.border.EmptyBorder;
import java.util.HashMap;
import javax.swing.text.DefaultCaret;


/**
 * A graphical view of the simulation grid. The view displays a colored
 * rectangle for each location representing its contents. It uses a default
 * background color. Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class SimulatorView extends JFrame {
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    
    
    private FieldView fieldView;
    private HistoryView historyView;


    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;
    private boolean isReset;

    /*
     * Mecanismo auxiliar para a GridBagLayout. Restrições adicionados aos
     * componetes antes de serem adicionados ao layout. (posicionamento, largura,
     * alinhamento)
     */
    private GridBagConstraints gbc;
    private GridBagLayout gbl;
    private JPanel painel;
    
    private ThreadRunner threadRunner;

    
   
    /**
     * Create a view of the given width and height.
     * 
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        threadRunner = new ThreadRunner();

        stats = new FieldStats();
        colors = new LinkedHashMap<Class, Color>();

        
        setTitle("Trabalho Final Disciplina PPOO");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
//        contents = getContentPane();
//        setLocation(100, 50);

        fieldView = new FieldView(height, width);
               
        makeHistoryView(height, width);

        frame();

    }
    
    public void frame(){
        JPanel mainFrame = new JPanel();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(mainFrame);
        
        
        // maak view panel (tweede laag) aan, layout en border van view panel.
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new GridLayout(2, 2));
        viewPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainFrame.add(viewPanel, BorderLayout.CENTER);
        
        // maak field panel (bovenste laag) aan, en border van field panel.
        JPanel field = new JPanel();
        field.setLayout(new BorderLayout());
        field.add(stepLabel, BorderLayout.NORTH);
        field.add(fieldView, BorderLayout.CENTER);
        field.add(population, BorderLayout.SOUTH);
        viewPanel.add(field); // field panel toevoegen aan view panel.
        
                
        // textArea panel
        JTextArea textArea = new JTextArea(20, 20);
        historyView.setTextArea(textArea);
        textArea.setEditable(false);
        
        // scrollPane voor textArea
        JScrollPane scrollPane = new JScrollPane(textArea);		
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);		
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        viewPanel.add(scrollPane);
	
        // maak toolbar panel (tweede laag, linkerkant) aan, layout en border
        // van toolbar panel
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(20, 0)); 
        toolbar.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        // labels en knoppen
        // step 1 knop
        JButton step1 = new JButton("Step 1");
        step1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {						
                        threadRunner.startRun(1);
                }
        });
        toolbar.add(step1); // toevoegen aan toolbar panel

        // step 100 knop
        JButton step100 = new JButton("Step 100");
        step100.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        threadRunner.startRun(100);
                }
        });
        toolbar.add(step100);

        // start knop
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        threadRunner.startRun(0);
                }
        });
        toolbar.add(start); // toevoegen aan toolbar panel

        // stop knop
        JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        threadRunner.stop();
                }
        });
        toolbar.add(stop); // toevoegen aan toolbar panel

        // reset knop
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        historyView.setStep(0);
                        Principal.getSimulator().reset();
                }
        });
        toolbar.add(reset); // toevoegen aan toolbar panel
        mainFrame.add(toolbar, BorderLayout.WEST); // toolbar panel toevoegen aan de main frame


        pack();
        //setSize(new Dimension(1024, 768));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        setExtendedState(Frame.MAXIMIZED_BOTH);
    }
    
    
    
    private void makeHistoryView(int height, int width) {
        historyView = new HistoryView(height, width);
        historyView.setSize(height, width);
        historyView.stats(getPopulationDetails());
        historyView.history(getIsReset());
    }

//    private void adicionarComponente(Component comp, int anchor, int fill, double pesoX, double pesoY, int linha,
//            int coluna, int larg, int alt) {
//        gbc.weightx = pesoX;
//        gbc.weighty = pesoY;
//        gbc.anchor = anchor; // posicionamento do componente na tela (esquerda, direita, centralizado, etc)
//        gbc.fill = fill; // define se o tamanho do componente será expandido ou não
//        gbc.gridx = linha; // coluna do grid onde o componente será inserido
//        gbc.gridy = coluna; // linha do grid onde o componente será inserido
//        gbc.gridwidth = larg; // quantidade de colunas do grid que o componente irá ocupar
//        gbc.gridheight = alt; // quantidade de linhas do grid que o componente irá ocupar
//        gbc.insets = new Insets(1, 1, 1, 1); // espaçamento (em pixels) entre os componentes da tela
//        gbl.setConstraints(comp, gbc); // adiciona o componente "comp" ao layout com as restrições previamente
//                                       // especificadas
//        contents.add(comp);
//    }

    /**
     * Define a color to be used for a given class of animal.
     * 
     * @param animalClass The animal's Class object.
     * @param color       The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color) {
        colors.put(animalClass, color);
    }

    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass) {
        Color col = colors.get(animalClass);
        if (col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        } else {
            return col;
        }
    }

    /**
     * Show the current status of the field.
     * 
     * @param step  Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, Field field) {
        if (!isVisible()) {
            setVisible(true);
        }

        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();

        fieldView.preparePaint();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if (animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                } else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
        

        historyView.stats(getPopulationDetails());
    }

    /**
     * Determine whether the simulation should continue to run.
     * 
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }
    
    
    public HashMap<Color, Counter> getPopulationDetails() {
        HashMap<Class, Counter> classStats = stats.getPopulation();
        HashMap<Color, Counter> colorStats = new HashMap<Color, Counter>();

        for (Class c : classStats.keySet()) {
                colorStats.put(getColor(c), classStats.get(c));
        }
        return colorStats;
    }
    
    public boolean getIsReset() {
        return isReset;
    }
    
    public ThreadRunner getThreadRunner() {
            return threadRunner;
    }

}
