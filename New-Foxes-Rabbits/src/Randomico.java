import java.util.Random;

/**
 * Classe para gerar os valores aleatórios usados
 * @author Anderson, Isabela, James
 */
public class Randomico {
    // Um valor semente padrão.
    private static final int SEED = 1111;
    // Objeto Random compartilhado.
    private static final Random rand = new Random(SEED);
    // Determinda se um gerador aleatório compartilhado deve ser gerado
    private static final boolean useShared = true;

   
    public Randomico() {
    }

    /**
     * Criar o gerador aleatório
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
    * Reseta o gerador
    */
    public static void reset() {
        if (useShared) {
            rand.setSeed(SEED);
        }
    }
}
