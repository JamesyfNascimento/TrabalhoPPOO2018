
import java.util.Iterator;
import java.util.List;



public class Coelho extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static int IDADE_REPRODUCAO = 5;
    // The age to which a rabbit can live.
    private static int IDADE_MAXIMA = 40;
    // The likelihood of a rabbit breeding.
    private static double PROBABILIDADE_REPRODUCAO = 0.12;
    // The maximum number of births.
    private static int MAX_NASCIMENTO = 4;
    // food value from grass
    private static final int VALOR_ALIMENTO_GRAMA = 6;
    
    
    public Coelho(boolean idadeAleatoria, Campo campo, Localizacao localizacao)
    {
        super(campo, localizacao);
        setIdade(0);
        if(idadeAleatoria) {
        	setIdade(getRandom().nextInt(IDADE_MAXIMA));
        }
    }
      
    
    @Override
    public void act(List<Personagem> novosCoelhos)
    {
        envelhecer();
        if(ehVivo()) {
            darHaLuz(novosCoelhos);            
            // Move towards a source of food if found.
            Localizacao novaLocalizacao = procuraComida();
            if(novaLocalizacao == null) { 
                // No food found - try to move to a free location.
                novaLocalizacao = getCampo().LocalizacaoAdjLivre(getLocalizacao());
            }
            // See if it was possible to move.
            if(novaLocalizacao != null) {
                setLocalizacao(novaLocalizacao);
            }
            else {
                // Overcrowding.
                matar();
            }
        }
    }

    
    private void darHaLuz(List<Personagem> novosCoelhos)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Campo campo = getCampo();
        List<Localizacao> livre = campo.getLocalizacoesAdjLivres(getLocalizacao());
        int births = procriar();
        for(int b = 0; b < births && livre.size() > 0; b++) {
            Localizacao loc = livre.remove(0);
            Coelho young = new Coelho(false, campo, loc);
            novosCoelhos.add(young);					
        }
    }

   
    private Localizacao procuraComida()
    {
        Campo campo = getCampo();
        List<Localizacao> adjacente = campo.localizacoesAdjacentes(getLocalizacao());
        Iterator<Localizacao> it = adjacente.iterator();
        while(it.hasNext()) {
            Localizacao where = it.next();
            Object personagem = campo.getPersonagem(where);
            if(personagem instanceof Grama) {
                Grama grama = (Grama) personagem;
                if(grama.ehVivo()) { 
                    grama.matar();
                    setNivelComida(VALOR_ALIMENTO_GRAMA);
                    return where;
                }
            }
        }
        return null;
    }
    
    
    @Override
    protected boolean podeProcriar()
    {
        return getIdade() >= getIdadeReproducao();
    }
    
    /**
     * Getter om BREEDING_AGE op te halen
     * @return 
     */
    protected int getIdadeReproducao()
    {
    	return IDADE_REPRODUCAO;
    }
    
    
    @Override
    protected int idadeMaxima()
    {
    	return IDADE_MAXIMA;
    }
    
    
    @Override
    protected double getProbabilidadeProcriar()
    {
    	return PROBABILIDADE_REPRODUCAO;
    }
    
    
    @Override
    protected int getNumMaxNascimento()
    {
    	return MAX_NASCIMENTO;
    }
    

    
    public static void setIdadeReproducao(int novaIdadeReproducao)
    {
    	IDADE_REPRODUCAO = novaIdadeReproducao;
    }
    
    /**
     *
     * @param novaIdade
     */
    public static void setIdadeMaxima(int novaIdade)
    {
    	IDADE_MAXIMA = novaIdade;
    }
    
    /**
     *
     * @param novaProbReproucao
     */
    public static void serProbReproducao(double novaProbReproucao)
    {
    	PROBABILIDADE_REPRODUCAO = novaProbReproucao;
    }
    
    
    public static void setMaxNascimento(int novaMaxNascimento)
    {
    	MAX_NASCIMENTO = novaMaxNascimento;
    }
}
