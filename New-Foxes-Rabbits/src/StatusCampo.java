import java.awt.Color;
import java.util.HashMap;


public class StatusCampo
{
    static int rabbitCount;
    static int foxCount;
    // Counters for each type of entity (fox, rabbit, etc.) in the simulation.
    private HashMap<Class, Contador> counters;
    // Whether the counters are currently up to date.
    private boolean countsValid;

    public StatusCampo()
    {
        // Set up a collection for counters for each type of animal that
        // we might find
        counters = new HashMap<Class, Contador>();
        countsValid = true;
    }

    
    public String getDetalhePopulacao(Campo field)
    {
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
    
    
    public void resetar()
    {
        countsValid = false;
        for(Class key : counters.keySet()) {
            Contador count = counters.get(key);
            count.reset();
        }
    }

   
    public void incrementaContador(Class animalClass)
    {
        Contador count = counters.get(animalClass);
        if(count == null) {
            // We do not have a counter for this species yet.
            // Create one.
            count = new Contador(animalClass.getName());
            counters.put(animalClass, count);
        }
        count.increment();
    }

    
    public void countFinished()
    {
        countsValid = true;
    }

   
    public boolean isViable(Campo field)
    {
        // How many counts are non-zero.
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
    
    public HashMap<Class, Contador> getPopulacao() {
        return counters;
    }
    
    
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
    
    public boolean getCountsValid() {
        return countsValid;
    }
}
