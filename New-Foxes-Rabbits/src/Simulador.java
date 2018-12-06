import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;


public class Simulador
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int LARGURA_PADRAO = 100;
    // The default depth of the grid.
    private static final int ALTURA_PADRAO = 60;
    // The probability that a bear will be created in any given grid position.    
    private static double PROBABILIDADE_REPRODUCAO_URSO = 0.01;
    // The probability that a fox will be created in any given grid position.
    private static double PROBABILIDADE_REPRODUCAO_RAPOSA = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static double PROBABILIDADE_REPRODUCAO_COELHO = 0.08;    
    // The probability that a hunter will be created in any given grid position.
    private static double PROBABILIDADE_REPRODUCAO_CACADOR = 0.002;
    // The probability that grass will be created in any given grid position.
    private static double PROBABILIDADE_REPRODUCAO_GRAMA = 1;
    
    // List of Actors in the field.
    private List<Personagem> personagens;
    // The current state of the field.
    private Campo campo;
    // The current step of the simulation.
    private int passo = 0;
    // A graphical view of the simulation.
    private SimuladorTela tela;

    
    
    public Simulador()
    {
        this(ALTURA_PADRAO, LARGURA_PADRAO);
    }
    
    
    public Simulador(int altura, int largura)
    {
        if(largura <= 0 || altura <= 0) {
            System.out.println("As dimensões devem ser maiores que zero.");
            System.out.println("Usando Valores Padrão");
            altura = ALTURA_PADRAO;
            largura = LARGURA_PADRAO;
        }
        
        personagens = new ArrayList<Personagem>();
        campo = new Campo(altura, largura);

        // Create a view of the state of each location in the field.
        tela = new SimuladorTela(altura, largura);
        tela.setColor(Coelho.class, Color.ORANGE);
        tela.setColor(Raposa.class, Color.BLUE);
        tela.setColor(Urso.class, Color.MAGENTA);
        tela.setColor(Cacador.class, Color.BLACK);
        tela.setColor(Grama.class, Color.GREEN);
        // Setup a valid starting point.
        resetar();
    }
    
    
    public void umPasso()
    {
        passo++;

        // Provide space for newborn actors.
        List<Personagem> novosPersonagens = new ArrayList<Personagem>();
        
        // Let all actors act.
        for(Iterator<Personagem> it = personagens.iterator(); it.hasNext();) {
            Personagem personagem = it.next();
            personagem.act(novosPersonagens);
            if (personagem instanceof Animal)		//	check if actor is an animal
            {
                    Animal animal = (Animal) personagem;
                    if(! animal.ehVivo()) {
                    it.remove();
                }
            }
            else if (personagem instanceof Planta)
            {
                    Planta planta = (Planta) personagem;
                    if(! planta.ehVivo()) {
                    it.remove();
                }
            }
        }
				    
        // Add the newly born foxes and rabbits to the main lists.
        personagens.addAll(novosPersonagens);

        tela.mostrarStatus(passo, campo);
    }
    
    
    public void resetar()
    {
        tela.getThreadRunner().stop();			
        passo = 0;
        personagens.clear();
        populacao();
        
        // Show the starting state in the view.
        tela.mostrarStatus(passo, campo);
    }
    
    
    public SimuladorTela getSimuladorTela()
    {
    	return tela;
    }
    
    
    public Campo getCampo()
    {
    	return campo;
    }
    
    
    public int getPasso()
    {
    	return passo;
    }
    
    
   
    private void populacao()
    {
        Random rand = Randomico.getRandom();
        campo.limpar();
        for(int row = 0; row < campo.getAltura(); row++) {
            for(int col = 0; col < campo.getLargura(); col++) {
                if(rand.nextDouble() <= PROBABILIDADE_REPRODUCAO_RAPOSA) {
                    Localizacao location = new Localizacao(row, col);
                    Raposa fox = new Raposa(true, campo, location);
                    personagens.add(fox);
                }
                else if(rand.nextDouble() <= PROBABILIDADE_REPRODUCAO_COELHO) {
                    Localizacao location = new Localizacao(row, col);
                    Coelho rabbit = new Coelho(true, campo, location);
                    personagens.add(rabbit);
                }
                else if(rand.nextDouble() <= PROBABILIDADE_REPRODUCAO_URSO) {
                    Localizacao location = new Localizacao(row, col);
                    Urso bear = new Urso(true, campo, location);
                    personagens.add(bear);
                }
                else if(rand.nextDouble() <= PROBABILIDADE_REPRODUCAO_CACADOR) {
                    Localizacao location = new Localizacao(row, col);
                    Cacador hunter = new Cacador(campo, location);
                    personagens.add(hunter);
                }
                else if(rand.nextDouble() <= PROBABILIDADE_REPRODUCAO_GRAMA) {
                    Localizacao location = new Localizacao(row, col);
                    Grama grass = new Grama(true, campo, location);
                    personagens.add(grass);
                }
                // else leave the location empty.
            }
        }
    }
    
    
    public static void setProbReproducaoUrso(double probReproducaoUrso)
    {
    	if (probReproducaoUrso >= 0)
    		Simulador.PROBABILIDADE_REPRODUCAO_URSO = probReproducaoUrso;
    }
    
    
    public static void setProbReproducaoRaposa(double probReproducaoRaposa)
    {
    	if (probReproducaoRaposa >= 0)
    		Simulador.PROBABILIDADE_REPRODUCAO_RAPOSA = probReproducaoRaposa;
    }
    
    
    public static void setProbReproducaoCoelho(double probReproducaoRaposa)
    {
    	if (probReproducaoRaposa >= 0)
    		Simulador.PROBABILIDADE_REPRODUCAO_COELHO = probReproducaoRaposa;
    }
    
    
    public static void setProbReproducaoCacador(double probReproducaoCacador)
    {
    	if (probReproducaoCacador >= 0)
    		Simulador.PROBABILIDADE_REPRODUCAO_CACADOR = probReproducaoCacador;
    }
    
    
    public static void setProbReproducaoGrama(double probReproducaoGrama)
    {
    	if (probReproducaoGrama >= 0)
    		Simulador.PROBABILIDADE_REPRODUCAO_GRAMA = probReproducaoGrama;
    }    
}