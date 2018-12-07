import java.awt.Color;

/**
 * Classe que Forneçe um contador para um participante na simulação.
 * Isso inclui uma sequência de identificação e uma contagem de quantos
 * participantes desse tipo existem atualmente na simulação.
 */
public class Contador {
    // Um para nome para o tipo de partipante 
    private String name;
    // Contador do tipo especificado
    private int count;

    /**
     * Construtor padrao que recebe uma string contendo o nome do "tipo"
     * de simulação.
     * @param name Um nome, por ex. "Raposa".
     */
    public Contador(String name) {
        this.name = name;
        count = 0;
    }
    
    /**
     * Retorna o nome especificado
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Retorna o contador
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * Incrementa o contador
     */
    public void increment() {
        count++;
    }
    
    /**
     * Zera o contador
     */
    public void reset() {
        count = 0;
    }
}
