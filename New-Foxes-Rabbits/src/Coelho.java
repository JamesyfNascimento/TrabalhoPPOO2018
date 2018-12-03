
import java.util.List;

/**
 *
 * @author Anderson, Isabela, James
 */
public class Coelho extends Animal{
        // Características compartilhadas por todos animais(static fields).
    // Idade que um animal pode começar a procriar.
    private static int BREEDING_AGE;
    // A idade limite de idade de um animal.
    private static int MAX_AGE;
    // A probabilidade do animal de reproduzir
    private static double BREEDING_PROBABILITY;
    // Número máximo de nascimentos.
    private static int MAX_LITTER_SIZE;
    // Idade dos animais
    private int age; 

    public Coelho(Field field, Location location) {
        super(field, location);
        BREEDING_AGE = 10;
        MAX_AGE = 35;
        BREEDING_PROBABILITY = 0.15;
        MAX_LITTER_SIZE = 7;
        age = rand.nextInt(MAX_AGE);
    }
    
    public Coelho(Field field, Location location, int age, int breedingAge, int maxAge, 
        double breedingProbability, int maxLitter) {
        super(field, location);
        BREEDING_AGE = breedingAge;
        MAX_AGE = maxAge;
        BREEDING_PROBABILITY = breedingProbability;
        MAX_LITTER_SIZE = maxLitter;
        
        
    }

    @Override
     public void act(List<Animal> newRabbits)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
     
     private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to add newly born rabbits to.
     */
    private void giveBirth(List<Animal> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Coelho young = new Coelho(field, loc);
            newRabbits.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
}
