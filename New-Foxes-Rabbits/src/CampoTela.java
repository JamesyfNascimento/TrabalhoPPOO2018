
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


public class CampoTela extends JPanel {
	
	private static final long serialVersionUID = 4654472673504364932L;

	private final int GRID_FATOR_ESCALA = 6;

	private int largura, altura;
	private int eixoX, eixoY;
	Dimension dimencao;
	private Graphics g;
	private Image imageCampo;

	
	
	public CampoTela(int altura, int largura) {
		this.altura = altura;
		this.largura = largura;
		dimencao = new Dimension(0, 0);
	}

	
        @Override
	public Dimension getPreferredSize() {
		return new Dimension(largura * GRID_FATOR_ESCALA,
				altura * GRID_FATOR_ESCALA);
	}

	public void prepararPintura() {
            System.out.println("haha");
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

	
	public void marcarCampo(int x, int y, Color color) {
		g.setColor(color);
		g.fillRect(x * eixoX, y * eixoY, eixoX - 1, eixoY - 1);
	}

	
        @Override
	public void paintComponent(Graphics g) {
		if (imageCampo != null) {
			Dimension currentSize = getSize();
			if (dimencao.equals(currentSize)) {
				g.drawImage(imageCampo, 0, 0, null);
			} else {
				// Rescale the previous image.
				g.drawImage(imageCampo, 0, 0, currentSize.width,
						currentSize.height, null);
			}
		}
	}
}