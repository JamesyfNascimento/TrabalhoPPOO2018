import java.awt.Color;
import java.util.HashMap;

/**
 * Esta classe coleta e fornece alguns dados estatisticos sobre o estado de um
 * campo. E flexivel: criara e mantera um contador para qualquer classe de
 * objeto encontrada no campo.
 */
public class StatusCampo {
    static int rabbitCount;
    static int foxCount;
    // Contadores para cada tipo de entidade (raposa, coelho, etc.) na simulacao.
    private HashMap<Class, Contador> counters;
    // Se os contadores estao atualizados no momento.
    private boolean countsValid;

    /**
     * Construtor para um objeto StatusCampo.
     */
    public StatusCampo() {
        // Configure uma coleção de contadores para cada tipo de animal que
        // possamos encontrar.
        counters = new HashMap<Class, Contador>();
        countsValid = true;
    }

    /**
     * Obtenhem detalhes do que está no campo.
     * @param field O campos em si com todos os personagens
     * @return Uma string descrevendo o que está no campo.
     */
    public String getDetalhePopulacao(Campo field) {
        StringBuffer buffer = new StringBuffer();
        if(!countsValid) {
            generateCounts(field);
        }
        for(Class key : counters.keySet()) {
            Contador info = counters.get(key);
            buffer.append(info.getName());
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        return buffer.toString();
    }
    
    /**
     * Invalida o conjunto atual de estatisticas. 
     * Redefinir todas as contagens para zero.
     */
    public void resetar() {
        countsValid = false;
        for(Class key : counters.keySet()) {
            Contador count = counters.get(key);
            count.reset();
        }
    }

   /**
    * Incrementa a contagem para uma classe de animal.
    * @param animalClass A classe de animais para incrementar.
    */
    public void incrementaContador(Class animalClass) {
        Contador count = counters.get(animalClass);
        if(count == null) {
            // Cria um contador para uma especie que ainda nao o possui.
            count = new Contador(animalClass.getName());
            counters.put(animalClass, count);
        }
        count.increment();
    }

    /**
     * Indica que uma contagem de animais foi concluida.
     */
    public void countFinished() {
        countsValid = true;
    }

   /**
    * Determina se a simulacao ainda e viavel. Ou seja, se deve continuar.
    * @param field O campo de simulacao contendo os personagens.
    * @return true Se houver mais de uma esprcie viva.
    */
    public boolean isViable(Campo field) {
        // Quantas contagens sao diferentes de zero.
        int nonZero = 0;
        if(!countsValid) {
            generateCounts(field);
        }
        for(Class key : counters.keySet()) {
            Contador info = counters.get(key);
            if(info.getCount() > 0) {
                nonZero++;
            }
        }
        return nonZero > 1;
    }
    
    /**
     * Contador de cada personagem da simulacao.
     * @return A quantidade dos individos de cada especie.
     */
    public HashMap<Class, Contador> getPopulacao() {
        return counters;
    }
    
    /**
     * Gera contagens do numero de raposas, coelhos, etc.
     * Estes nao sao mantidos atualizados como personagens, sao colocados no
     * campo, mas somente quando um pedido e feito para a informacao.
     * @param field O campo para gerar as estatisticas.
     */
    private void generateCounts(Campo field)
    {
        resetar();
        for(int row = 0; row < field.getAltura(); row++) {
            for(int col = 0; col < field.getLargura(); col++) {
                Object animal = field.getPersonagem(row, col);
                if(animal != null) {
                    incrementaContador(animal.getClass());
                }
            }
        }
        countsValid = true;
    }
    
    /**
     * Valida a contagem de personagens no campo.
     * @return true se a contagem for valida.
     */
    public boolean getCountsValid() {
        return countsValid;
    }
}