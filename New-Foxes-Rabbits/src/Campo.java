import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Classe da criação do campo onde ficam as diversas espécies
 * @author Anderson, Isabela, James
 */

public class Campo {
    // gerador aleatória para gerar posições.
    private final Random rand = Randomico.getRandom();
    // A profundidade o a largura do campo.
    private int altura, largura;
    // Armazenamento para os animais.
    private Object[][] campo;

    /**
     * Construtor para criação do campo
     * @param altura dimensão altura
     * @param largura dimensão largura
     */
    public Campo(int altura, int largura) {
        this.altura = altura;
        this.largura = largura;
        campo = new Object[altura][largura];
    }

    /**
     * Limpa todo o campo e o deixa vazio
     */
    public void limpar() {
        for (int row = 0; row < altura; row++) {
            for (int col = 0; col < largura; col++) {
                campo[row][col] = null;
            }
        }
    }

    /**
     * Limpar uma determinada localização
     * @param location 
     */
    public void limpar(Localizacao location) {
        campo[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Retorna a localização no campo 
     * @param animal - Objeto da classe animal
     * @param row - linha no campo
     * @param col - coluna no campo
     */
    public void lugar(Object animal, int row, int col) {
        lugar(animal, new Localizacao(row, col));
    }
    /**
     * ????
     * @param animal
     * @param localizacao 
     */
   
    public void lugar(Object animal, Localizacao localizacao) {
        campo[localizacao.getRow()][localizacao.getCol()] = animal;
    }

    /**
     * Retorna um personagem (exemplo caçador), dada sua localização
     * @param localizacao
     * @return 
     */    
    public Object getPersonagem(Localizacao localizacao) {
        return getPersonagem(localizacao.getRow(), localizacao.getCol());
    }

    /**
     * Retorna o personagem dada as coordenadas
     * @param row - linha
     * @param col - coluna
     * @return campo[liha][coluna]
     */   
    public Object getPersonagem(int row, int col) {
        return campo[row][col];
    }

    /**
     * Retona as localizções adjacentes
     * @param localizacao
     * @return adjacente
     */
    public Localizacao radonLocalizacaoAdj(Localizacao localizacao) {
        List<Localizacao> adjacente = localizacoesAdjacentes(localizacao);
        return adjacente.get(0);
    }

    /**
     * Retorna lista com os locais adjacentes vazios
     * @param localizacao
     * @return livre
     */
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

    /**
     * Retorna se uma localização passada por parâmetro é livre ou não
     * @param localizacao
     * @return 
     */
    public Localizacao LocalizacaoAdjLivre(Localizacao localizacao) {
        // As localizações livres disponíveis.
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

            // Embaralha a lista. 
            Collections.shuffle(localizacoes, rand);
        }
        return localizacoes;
    }
    /**
     * Retorna a altura do campo
     * @return altura 
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Retorna a largura
     * @return largura 
     */
    public int getLargura() {
        return largura;
    }
}
