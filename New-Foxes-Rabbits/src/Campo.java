import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Campo {
    // A random number generator for providing random locations.
    private final Random rand = Randomico.getRandom();

    // The depth and width of the field.
    private int altura, largura;
    // Storage for the animals.
    private Object[][] campo;

    
    public Campo(int altura, int largura) {
        this.altura = altura;
        this.largura = largura;
        campo = new Object[altura][largura];
    }

    
    public void limpar() {
        for (int row = 0; row < altura; row++) {
            for (int col = 0; col < largura; col++) {
                campo[row][col] = null;
            }
        }
    }

   
    public void limpar(Localizacao location) {
        campo[location.getRow()][location.getCol()] = null;
    }

    
    public void lugar(Object animal, int row, int col) {
        lugar(animal, new Localizacao(row, col));
    }

   
    public void lugar(Object animal, Localizacao localizacao) {
        campo[localizacao.getRow()][localizacao.getCol()] = animal;
    }

    
    public Object getPersonagem(Localizacao localizacao) {
        return getPersonagem(localizacao.getRow(), localizacao.getCol());
    }

   
    public Object getPersonagem(int row, int col) {
        return campo[row][col];
    }

    
    public Localizacao radonLocalizacaoAdj(Localizacao localizacao) {
        List<Localizacao> adjacente = localizacoesAdjacentes(localizacao);
        return adjacente.get(0);
    }

   
    public List<Localizacao> getLocalizacoesAdjLivres(Localizacao localizacao) {
        List<Localizacao> livre = new LinkedList<Localizacao>();
        List<Localizacao> adjacente = localizacoesAdjacentes(localizacao);
        for (Localizacao proxima : adjacente) {
            if (Campo.this.getPersonagem(proxima) == null) {
                livre.add(proxima);
            }
        }
        return livre;
    }

   
    public Localizacao LocalizacaoAdjLivre(Localizacao localizacao) {
        // The available free ones.
        List<Localizacao> livre = getLocalizacoesAdjLivres(localizacao);
        if (livre.size() > 0) {
            return livre.get(0);
        } else {
            return null;
        }
    }

    
    public List<Localizacao> localizacoesAdjacentes(Localizacao localizacao) {
        assert localizacao != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Localizacao> localizacoes = new LinkedList<Localizacao>();
        if (localizacao != null) {
            int row = localizacao.getRow();
            int col = localizacao.getCol();
            for (int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if (nextRow >= 0 && nextRow < altura) {
                    for (int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if (nextCol >= 0 && nextCol < largura && (roffset != 0 || coffset != 0)) {
                            localizacoes.add(new Localizacao(nextRow, nextCol));
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(localizacoes, rand);
        }
        return localizacoes;
    }

   
    public int getAltura() {
        return altura;
    }

    
    public int getLargura() {
        return largura;
    }
}
