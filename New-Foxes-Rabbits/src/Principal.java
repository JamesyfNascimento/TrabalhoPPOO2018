
import java.util.Random;

public class Principal {

    private static Simulador simulador;

    
    public static void main(String[] args) throws Exception {


        setSimulador(new Simulador()); 
        
    }

   
    public static Simulador getSimulador() {
        return simulador;
    }

    
    public static void setSimulador(Simulador simulador) {
        Principal.simulador = simulador;
    }

}
