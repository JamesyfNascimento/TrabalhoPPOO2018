
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
public abstract class Predador {
    
    private static Random rand = new Random();
    
     // Idade dos animais
    private int age; 
    // Se o animal está vivo ou não
    private boolean alive;

    public Predador(int age, boolean alive) {
        this.age = age;
        this.alive = alive;
        
    }
    
    

    public Predador() {
        this.age = age;
        this.alive = alive;
    }

    
    
    
    
    
}
