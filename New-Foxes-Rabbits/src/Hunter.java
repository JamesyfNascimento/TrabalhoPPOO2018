/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author isabela
 */
public class Hunter {
    
    // Quantidade de caçadores 
    private int quantidadeCacadores;
    // tempo da temporada de caça
    private boolean temporadaCaca;
    // Quantidade de predadores que pode matar por dia
    private int quantidadeCacas;
    // Que animal pode caçar
    private String animal;

    public Hunter(int quantidadeCacadores, boolean temporadaCaca, int quantidadeCacas, String animal) {
        this.quantidadeCacadores = quantidadeCacadores;
        this.temporadaCaca = temporadaCaca;
        this.quantidadeCacas = quantidadeCacas;
        this.animal = animal;
    }
    
    
    
    
}
