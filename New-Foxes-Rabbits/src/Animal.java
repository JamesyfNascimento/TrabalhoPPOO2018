
import java.util.List;
import java.util.Random;

/**
 *
 * @author Anderson, Isabela, James
 */
public abstract class Animal {
    
    private boolean alive;
    // Localização do animal
    private Location location;
    // The animal's field.
    private Field field;
    // An animal's food level, which is increased by eating.
    private int foodLevel;
    // Idade do animal
    private int age;
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();
    
    abstract public void act(List<Animal> newAnimals);
    

    public Animal(Field field, Location location) {
       
        this.alive = true;
        this.field = field;
        setLocation(location);
    }
    
    	protected int getAge() 
	{
		return age;
	}
	
    /**
     * set an animals current age
     * @param age int set the current age
     */
	protected void setAge(int age) 
	{
		this.age = age;
	}

    public boolean isAlive() {
        return alive;
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    public void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    public Field getField()
    {
        return field;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
     
    public Location getLocation()
    {
        return location;
    }
    
 
}
