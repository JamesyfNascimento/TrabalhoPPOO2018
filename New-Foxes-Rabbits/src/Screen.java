
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author anderson
 */
public class Screen extends JFrame{
    // Layout da tela. -> Mecanismo que me permite posicionar os elementos na tela.
    private GridBagLayout gbl;
    /* Mecanismo auxiliar para a GridBagLayout. Restrições adicionados 
        aos componetes antes de serem adicionados ao layout. (posicionamento, largura, alinhamento) */
    private GridBagConstraints gbc;
    
    // Atributos
    private int qdeCoelhos = 0;
    private int qdeRaposas = 0;
    private int qdeCacadores = 0;
    
    // Botões
    private JButton btConfiguracoes;
    private JButton btRun;
    
    // Rotulos
    private JLabel lbMenu;
    private JLabel lbVazia;
    private JLabel lbQdePassos;
    
    // Caixas de texto
    private JTextField tfQdePassos;
    
    // Tabela
    private JTable tbPopulation;
    private DefaultTableModel mdPopulation;
    private final int tamCol = 30;
    
       
    // container
    private JPanel painelSimulacao; // Tela de simulação.
    private JPanel painelMenu; // Menu de opções.
    
    public Screen() {
        super("Calculadora");// Título da tela.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Modo de como a tela será fechada.
        setResizable(true);//setResizable(false);// Evita que a tela possa ser redimensionada pelo usuário
        construirTela(); // Chamada do método de inserção dos componentes da tela.
        pack(); // Método da classe JFrame que redmensiona a tela de acordo com os componentes que estão nela inseridos.
    }
    
    /* Método que irá, de fato, inserir os componentes na tela. */
    public void construirTela() {
        gbl = new GridBagLayout(); // Objeto da classe que definirá o Layout.
        gbc = new GridBagConstraints();
        setLayout(gbl); // Método da classe JFrame que define qual será o layout da janela. Diz ao java como adicionar o objeto na tela.
        lbMenu = new JLabel("MENU DE OPÇÕES");
        lbVazia = new JLabel("");
        lbQdePassos = new JLabel("Quantidade de Passos:");
        tfQdePassos = new JTextField(5); // Recebe como parâmetro a qde de colunas (tamanho) da caixa de texto.
        
        btConfiguracoes = new JButton("Set Config");
        btConfiguracoes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Atributos variaveis
                qdeCoelhos = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de coelhos:"));
                qdeRaposas = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de raposas:"));
                qdeCacadores = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de caçadores:"));
                setDados(qdeCoelhos, qdeRaposas, qdeCacadores);
                btConfiguracoes.setEnabled(false);
            }
        });
        
        btRun = new JButton("Run");
        btRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        mdPopulation = new DefaultTableModel(); // Criando os dados -> Informações que serão apresentadas na tabela.
        mdPopulation.addColumn("Raposas");
        mdPopulation.addColumn("Coelhos");
        mdPopulation.addColumn("Caçadores");
        
        tbPopulation = new JTable(mdPopulation); // Cria a tabela e adiciona nela o modelo de dados.
        
        // Configura o tamanho das colunas da tabela
        tbPopulation.getColumnModel().getColumn(0).setMaxWidth(tamCol); // Dimensiona a largura da coluna em pixels.
        tbPopulation.getColumnModel().getColumn(1).setMaxWidth(tamCol);
        tbPopulation.getColumnModel().getColumn(2).setMaxWidth(tamCol);
        //setDados(qdeCoelhos, qdeRaposas, qdeCacadores);
        
        // Instancia o painel (container) de Simulação e adiciona os componentes a ele
        painelSimulacao = new JPanel(new GridLayout(2, 1, 5, 5)); //2 linha e 1 colunas
        painelSimulacao.add(lbMenu);
        painelSimulacao.add(tbPopulation);
        
        // Instancia o painel (container) de Menu e adiciona os componentes a ele
        painelMenu = new JPanel(new GridLayout(7, 1, 5, 5)); //6 linha e 4 colunas
        painelMenu.add(lbMenu);
        painelMenu.add(lbVazia);
        painelMenu.add(btConfiguracoes);
        painelMenu.add(lbVazia);
        painelMenu.add(lbQdePassos);
        painelMenu.add(tfQdePassos);
        painelMenu.add(btRun);
        
        // Adicionando os componentes recém-criados à tela
        adicionarComponente(painelSimulacao, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0, 1, 1);
        adicionarComponente(painelMenu, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 1, 1, 1);
        adicionarComponente(tbPopulation, GridBagConstraints.CENTER, GridBagConstraints.NONE, 1, 0, 1, 2);
        
    }
    
    /* Método responsável por adicionar os componentes na tela, conforme especifições pré-determinadas. */
    private void adicionarComponente(Component comp, int anchor, int fill, int linha, int coluna, int larg, int alt) {
        gbc.anchor = anchor; // posicionamento do componente na tela (esquerda, direita, centralizado, etc)
        gbc.fill = fill; // define se o tamanho do componente será expandido ou não
        gbc.gridy = linha; // linha do grid onde o componente será inserido
        gbc.gridx = coluna; // coluna do grid onde o componente será inserido
        gbc.gridwidth = larg; // quantidade de colunas do grid que o componente irá ocupar
        gbc.gridheight = alt; // quantidade de linhas do grid que o componente irá ocupar
        gbc.insets = new Insets(3, 3, 3, 3); // espaçamento (em pixels) entre os componentes da tela
        gbl.setConstraints(comp, gbc); // adiciona o componente "comp" ao layout com as restrições previamente especificadas
        add(comp); // efetivamente insere o componente na tela
    }
    
    private void setDados(int r, int f, int h) {
        String[] dados = new String[3]; // O tamenho deve ser a quantidade de colunas da tabela. Cada célula armazenará os dados.
        dados[0] = Integer.toString(r);
        dados[1] = Integer.toString(f);
        dados[3] = Integer.toString(h);
        mdPopulation.addRow(dados); // Adiciona o vetor na tabela
        JOptionPane.showMessageDialog(this, "Configurações definidas com sucesso!", "Sucesso :-)",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        new Screen().setVisible(true); // Instancia a tela e a torna visivel.
    }
}