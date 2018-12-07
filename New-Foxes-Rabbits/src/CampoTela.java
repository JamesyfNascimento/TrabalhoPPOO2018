import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Fornece uma visao grafica de um campo retangular. Esta e uma classe aninhada
 * (uma classe definida dentro de uma classe) que define um componente
 * personalizado para a interface do usuario. Este componente exibe o campo.
 */
public class CampoTela extends JPanel {
    private final int GRID_FATOR_ESCALA = 6;
    private int largura, altura;
    private int eixoX, eixoY;
    Dimension dimencao;
    private Graphics g;
    private Image imageCampo;

    /**
     * Construtor da tela de campo, onde os personagens são exibidos.
     * @param altura A altura da area de exibicao do componente
     * @param largura A lagura da area de exibicao do componente
     */ 
    public CampoTela(int altura, int largura) {
        this.altura = altura;
        this.largura = largura;
        dimencao = new Dimension(0, 0);
    }

    /**
     * Diz ao gerente da GUI o tamanho que gostaria de ser.
     * @return A dimensao do grid encapsulada em um unico objeto
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(largura * GRID_FATOR_ESCALA, altura * 
                GRID_FATOR_ESCALA);
    }

    /**
     * Prepara para uma nova rodada de pintura. Como o componente pode ser
     * redimensionado, calcula o fator de escala novamente.
     */
    public void prepararPintura() {
        System.out.print("");
        if (!dimencao.equals(getSize())) { // if the size has changed...
            dimencao = getSize();
            imageCampo = createImage(dimencao.width, dimencao.height);
            g = imageCampo.getGraphics();
            eixoX = dimencao.width / largura;
            if (eixoX < 1) {
                eixoX = GRID_FATOR_ESCALA;
            }
            eixoY = dimencao.height / altura;
            if (eixoY < 1) {
                eixoY = GRID_FATOR_ESCALA;
            }
        }
    }

    /**
     * Pinta na localizacao da grade uma determinada cor.
     * @param x O valor referente a linha da grade.
     * @param y O valor referente a coluna da grade
     * @param color A cor a ser marcada na posicao x/y.
     */
    public void marcarCampo(int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x * eixoX, y * eixoY, eixoX - 1, eixoY - 1);
    }

    /**
     * Reexibe o componente de visao de campo. Copia a imagem
     * interna para a tela.
     * @param g Um conponente grafico
     */
    @Override
    public void paintComponent(Graphics g) {
        if (imageCampo != null) {
            Dimension currentSize = getSize();
            if (dimencao.equals(currentSize)) {
                g.drawImage(imageCampo, 0, 0, null);
            } else {
                // Redimensiona a imagem anterior.
                g.drawImage(imageCampo, 0, 0, currentSize.width,
                        currentSize.height, null);
            }
        }
    }
}