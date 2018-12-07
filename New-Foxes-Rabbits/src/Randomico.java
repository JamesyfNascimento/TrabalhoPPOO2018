import java.util.Random;

/**
 * Classe para gerar os valores aleatorios usados
 * @author isabela
 */
public class Randomico {
    // Um valor semente padrao.
    private static final int SEED = 1111;
    // Objeto Random compartilhado.
    private static final Random rand = new Random(SEED);
    // Determinda se um gerador aleatorio compartilhado deve ser gerado
    private static final boolean useShared = true;

   
    public Randomico() {
    }

    /**
     * Criar o gerador aleatorio
     * @return new Random()
     */
    public static Random getRandom() {
        if (useShared) {
            return rand;
        } else {
            return new Random();
        }
    }

   /**
    * Reseta o gerador aleatorio com a semente padrao
    */
    public static void reset() {
        if (useShared) {
            rand.setSeed(SEED);
        }
    }
}
