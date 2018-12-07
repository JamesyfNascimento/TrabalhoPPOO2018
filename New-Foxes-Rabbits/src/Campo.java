import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Classe da criacao do campo onde ficam as diversas especies
 */

public class Campo {
    // gerador aleatória para gerar posicoes.
    private final Random rand = Randomico.getRandom();
    // A profundidade o a largura do campo.
    private final int altura;
    private final int largura;
    // Armazenamento para os animais.
    private final Object[][] campo;

    /**
     * Construtor para criacao do campo
     * @param altura dimensao altura
     * @param largura dimensao largura
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
     * Limpar uma determinada localizacao
     * @param location 
     */
    public void limpar(Localizacao location) {
        campo[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Retorna a localizacao no campo 
     * @param animal - Objeto da classe animal
     * @param row - linha no campo
     * @param col - coluna no campo
     */
    public void lugar(Object animal, int row, int col) {
        lugar(animal, new Localizacao(row, col));
    }
    /**
     * Atraves de uma localizacao encontra a posicao linha/coluna no campo
     * e adiciona um animal
     * @param animal
     * @param localizacao 
     */
    public void lugar(Object animal, Localizacao localizacao) {
        campo[localizacao.getRow()][localizacao.getCol()] = animal;
    }

    /**
     * Retorna um personagem (exemplo cacador), dada sua localizacao
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
     * Retona as localizacoes adjacentes
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
        List<Localizacao> livre = new LinkedList<>();
        List<Localizacao> adjacente = localizacoesAdjacentes(localizacao);
        adjacente.stream().filter((proxima) -> (Campo.this.getPersonagem(proxima) == null)).forEachOrdered((proxima) -> {
            livre.add(proxima);
        });
        return livre;
    }

    /**
     * Retorna se uma localizacao passada por parametro e livre ou nao
     * @param localizacao
     * @return 
     */
    public Localizacao LocalizacaoAdjLivre(Localizacao localizacao) {
        // As localizacoes livres disponíveis.
        List<Localizacao> livre = getLocalizacoesAdjLivres(localizacao);
        if (livre.size() > 0) {
            return livre.get(0);
        } else {
            return null;
        }
    }

    /**
     * Encontra localizacoes adjacentes
     * @param localizacao
     * @return 
     */
    public List<Localizacao> localizacoesAdjacentes(Localizacao localizacao) {
        assert localizacao != null : "Localizacao vazia passada para adjacentsLocations";
        // A lista de localizacoes a ser retornada.
        List<Localizacao> localizacoes = new LinkedList<>();
        if (localizacao != null) {
            int row = localizacao.getRow();
            int col = localizacao.getCol();
            for (int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if (nextRow >= 0 && nextRow < altura) {
                    for (int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclui localizacoes invalidas.
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
