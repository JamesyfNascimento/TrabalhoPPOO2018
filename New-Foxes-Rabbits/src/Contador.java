import java.awt.Color;


public class Contador
{
    // Um para nome para o tipo de partipante 
    private String name;
    // Contador do tipo especificado
    private int count;

    /**
     * Construtor padrao que recebe uma string contendo o nome do "tipo" que 
     * será contado
     * @param name 
     */
    public Contador(String name)
    {
        this.name = name;
        count = 0;
    }
    /**
     * Retorna o nome especificado
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Retorna o contador
     * @return count
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Incrementa o contador
     */
    public void increment()
    {
        count++;
    }
    
    /**
     * Zera o contador
     */
    public void reset()
    {
        count = 0;
    }
}
