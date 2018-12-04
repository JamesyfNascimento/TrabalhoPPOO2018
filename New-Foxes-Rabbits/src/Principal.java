
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
    
    public static void main(String[] args) {
       Simulator simulator = new Simulator(150, 150);
       //simulator.runLongSimulation();
       simulator.simulate(90);
       simulator.simulateOneStep();
    }
    
} 

