/**
 * Classe principal - main
 * @author Anderson, Isabela, James
 */
public class Principal {

    private static Simulador simulador;

    /**
     * Classe main apenas inicializa o simulador
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {


        setSimulador(new Simulador()); 
        
    }

    /**
     * Retorna o simulador
     * @return simulador
     */
    public static Simulador getSimulador() {
        return simulador;
    }

    /**
     * Modifica a configuracao do simulador
     * @param simulador 
     */
    public static void setSimulador(Simulador simulador) {
        Principal.simulador = simulador;
    }

}
