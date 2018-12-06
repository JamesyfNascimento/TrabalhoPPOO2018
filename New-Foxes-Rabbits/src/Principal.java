
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author isabela
 */
public class Principal {

    private static Simulator simulator;

    /**
     * Main methode
     * 
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // SplashScreen splash = new SplashScreen(3000);
        // Sound startupSound = new Sound("sounds/test.wav");
        // splash.showSplash();

        setSimulator(new Simulator()); 
        
    }

    /**
     * returns the simulator
     * 
     * @return simulator
     */
    public static Simulator getSimulator() {
        return simulator;
    }

    /**
     * sets a new simulator
     * 
     * @param simulator
     */
    public static void setSimulator(Simulator simulator) {
        Principal.simulator = simulator;
    }

}
