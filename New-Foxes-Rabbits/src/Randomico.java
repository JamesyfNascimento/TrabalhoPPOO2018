import java.util.Random;


public class Randomico {
    // The default seed for control of randomization.
    private static final int SEED = 1111;
    // A shared Random object, if required.
    private static final Random rand = new Random(SEED);
    // Determine whether a shared random generator is to be provided.
    private static final boolean useShared = true;

   
    public Randomico() {
    }

    
    public static Random getRandom() {
        if (useShared) {
            return rand;
        } else {
            return new Random();
        }
    }

   
    public static void reset() {
        if (useShared) {
            rand.setSeed(SEED);
        }
    }
}
