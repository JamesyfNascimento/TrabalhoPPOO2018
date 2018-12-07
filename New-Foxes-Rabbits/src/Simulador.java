import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * Classe para definir as configuracoes do simulador.
 */
public class Simulador
{
    // Variaveis que definem as configuracoes do simulador
    // Valor default para o tamanho do grid
    private static final int LARGURA_PADRAO = 100;
    private static final int ALTURA_PADRAO = 60;
    // Probabilidades de reproducao dos personagens que fazem parte da simulacao 
    private static double PROBABILIDADE_REPRODUCAO_URSO = 0.01;
    private static double PROBABILIDADE_REPRODUCAO_RAPOSA = 0.02;
    private static double PROBABILIDADE_REPRODUCAO_COELHO = 0.08;    
    private static double PROBABILIDADE_REPRODUCAO_CACADOR = 0.002;
    private static double PROBABILIDADE_REPRODUCAO_GRAMA = 1;
    
    // Lista de agentes no campo
    private List<Personagem> personagens;
    private Campo campo;
    // Passo autual da simulacao
    private int passo = 0;
    // Visualizacao grafica do simulador
    private SimuladorTela tela;

    
    /**
     * Criacao do simulador padrao
     */
    public Simulador()
    {
        this(ALTURA_PADRAO, LARGURA_PADRAO);
    }
    
    /**
     * Criacao do simulador com dimensoes definidas através dos parametros
     * @param altura
     * @param largura 
     */
    public Simulador(int altura, int largura)
    {
        if(largura <= 0 || altura <= 0) {
            System.out.println("As dimensoes devem ser maiores que zero.");
            System.out.println("Usando Valores Padrao");
            altura = ALTURA_PADRAO;
            largura = LARGURA_PADRAO;
        }
        
        personagens = new ArrayList<Personagem>();
        campo = new Campo(altura, largura);

        // Cria a visualizacao de cada personagem atribuindo uma cor diferente.
        tela = new SimuladorTela(altura, largura);
        tela.setColor(Coelho.class, Color.ORANGE);
        tela.setColor(Raposa.class, Color.BLUE);
        tela.setColor(Urso.class, Color.MAGENTA);
        tela.setColor(Cacador.class, Color.BLACK);
        tela.setColor(Grama.class, Color.GREEN);
        resetar();
    }
    
    /**
     * Um passo de execucao
     */
    public void umPasso()
    {
        passo++;

        List<Personagem> novosPersonagens = new ArrayList<Personagem>();
        
        for(Iterator<Personagem> it = personagens.iterator(); it.hasNext();) {
            Personagem personagem = it.next();
            personagem.act(novosPersonagens);
            if (personagem instanceof Animal)		//Verifica se o personagem e um animal
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
				    
        // adiciona personagens recém nascidos
        personagens.addAll(novosPersonagens);

        tela.mostrarStatus(passo, campo);
    }
    
    /**
     * Volta ao passo 0
     */
    public void resetar()
    {
        tela.getThreadRunner().stop();			
        passo = 0;
        personagens.clear();
        populacao();
        
        // Mostra o estado inicial na visualizacao.
        tela.mostrarStatus(passo, campo);
    }
    
    
    /**
     * Retorna a tela do simulador
     * @return tela
     */
    public SimuladorTela getSimuladorTela()
    {
    	return tela;
    }
    
    /**
     * Retorna o campo
     * @return campo
     */
    public Campo getCampo()
    {
    	return campo;
    }
    
    /**
     * Retorna o passo atual
     * @return passo
     */
    public int getPasso()
    {
    	return passo;
    }
    
    
   /**
    * Cria populacao
    */
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
                
            }
        }
    }
    
    /**
     * Atribui nova probabilidade de reproducao
     * @param probReproducaoUrso 
     */
    public static void setProbReproducaoUrso(double probReproducaoUrso)
    {
    	if (probReproducaoUrso >= 0)
    		Simulador.PROBABILIDADE_REPRODUCAO_URSO = probReproducaoUrso;
    }
    
    /**
     * Atribui nova probabilidade de reproducao
     * @param probReproducaoRaposa 
     */
    public static void setProbReproducaoRaposa(double probReproducaoRaposa)
    {
    	if (probReproducaoRaposa >= 0)
    		Simulador.PROBABILIDADE_REPRODUCAO_RAPOSA = probReproducaoRaposa;
    }
    
    /**
     * Atribui nova probabilidade de reproducao
     * @param probReproducaoRaposa 
     */
    public static void setProbReproducaoCoelho(double probReproducaoRaposa)
    {
    	if (probReproducaoRaposa >= 0)
    		Simulador.PROBABILIDADE_REPRODUCAO_COELHO = probReproducaoRaposa;
    }
    
    /**
     * Atribui nova probabilidade de reproducao
     * @param probReproducaoCacador 
     */
    public static void setProbReproducaoCacador(double probReproducaoCacador)
    {
    	if (probReproducaoCacador >= 0)
    		Simulador.PROBABILIDADE_REPRODUCAO_CACADOR = probReproducaoCacador;
    }
    
    /**
     * Atribui nova probabilidade de reproducao
     * @param probReproducaoGrama 
     */
    public static void setProbReproducaoGrama(double probReproducaoGrama)
    {
    	if (probReproducaoGrama >= 0)
    		Simulador.PROBABILIDADE_REPRODUCAO_GRAMA = probReproducaoGrama;
    }    
}
