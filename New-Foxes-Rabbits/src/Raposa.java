
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Anderson, Isabela, James
 */
public class Raposa extends Animal {
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
    // An animal's food level, which is increased by eating.
    private static final int RABBIT_FOOD_VALUE = 7;
    private int foodLevel;

    public Raposa(Field field, Location location) {    
        super(field, location);
        BREEDING_AGE = 10;
        MAX_AGE = 20;
        BREEDING_PROBABILITY = 0.09;
        MAX_LITTER_SIZE = 3;
        age = rand.nextInt(MAX_AGE);
        foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
    }
    
    public Raposa(Field field, Location location, int breedingAge, int maxAge, 
        double breedingProbability, int maxLitter) {
        super(field, location);
        BREEDING_AGE = breedingAge;
        MAX_AGE = maxAge;
        BREEDING_PROBABILITY = breedingProbability;
        MAX_LITTER_SIZE = maxLitter;
        
    }
    
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
     private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
     
        private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
    private Location findFood(Location location)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Coelho) {
                Coelho rabbit = (Coelho) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to add newly born foxes to.
     */
    private void giveBirth(List<Animal> newFoxes)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Raposa young = new Raposa(field, loc);
            newFoxes.add(young);
        }
    }
    
    public void act(List<Animal> newFoxes)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newFoxes);            
            // Move towards a source of food if found.
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
}
    
    
    
    

    
    

