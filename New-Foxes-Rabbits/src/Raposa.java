import java.util.List;
import java.util.Iterator;


public class Raposa extends Animal
{
    // Characteristics shared by all foxes (class variables).
    
    // The age at which a fox can start to breed.
    private static int IDADE_REPRODUCAO = 15;
    // The age to which a fox can live.
    private static int IDADE_MAXIMA = 150;
    // The likelihood of a fox breeding.
    private static double PROBABILIDADE_REPRODUCAO = 0.08;
    // The maximum number of births.
    private static int MAX_NASCIMENTO = 4;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int NUM_PASSOS_REPRODUZIR = 9;
    
    
    
    public Raposa(boolean idadeAleatoria, Campo campo, Localizacao localizacao)
    {
        super(campo, localizacao);
        if(idadeAleatoria) {
            setIdade(getRandom().nextInt(IDADE_MAXIMA));
            setNivelComida(getRandom().nextInt(NUM_PASSOS_REPRODUZIR));
        }
        else {
            setIdade(0);
            setNivelComida(NUM_PASSOS_REPRODUZIR);
        }
    }
    
   
    public void act(List<Personagem> novasRaposas)
    {
        envelhecer();
        aumentaFome();
        if(ehVivo()) {
            darHaLuz(novasRaposas);            
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
    
    
    protected int idadeMaxima()
    {
    	return IDADE_MAXIMA;
    }
    
    
    private void aumentaFome()
    {
        setNivelComida(getNivelComida()  - 1);
        if(getNivelComida() <= 0) {
            matar();
        }
    }
    
    
    private Localizacao procuraComida()
    {
        Campo campo = getCampo();
        List<Localizacao> adjacente = campo.localizacoesAdjacentes(getLocalizacao());
        Iterator<Localizacao> it = adjacente.iterator();
        while(it.hasNext()) {
            Localizacao onde = it.next();
            Object animal = campo.getPersonagem(onde);
            if(animal instanceof Coelho) {
                Coelho coelho = (Coelho) animal;
                if(coelho.ehVivo()) { 
                    coelho.matar();
                    setNivelComida(NUM_PASSOS_REPRODUZIR);
                    return onde;
                }
            }
        }
        return null;
    }
    
   
    private void darHaLuz(List<Personagem> novasRaposas)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Campo campo = getCampo();
        List<Localizacao> livre = campo.getLocalizacoesAdjLivres(getLocalizacao());
        int nascimentos = procriar();
        for(int b = 0; b < nascimentos && livre.size() > 0; b++) {
            Localizacao loc = livre.remove(0);
            Raposa filhote = new Raposa(false, campo, loc);
            novasRaposas.add(filhote);
        }
    }

    
    protected boolean podeProcriar()
    {
        return getIdade() >= getIdadeReproducao();
    }
    
   
    protected int getIdadeReproducao()
    {
    	return IDADE_REPRODUCAO;
    }
    
    
    protected int getNumMaxNascimento()
    {
    	return MAX_NASCIMENTO;
    }
    
    
    protected double getProbabilidadeProcriar()
    {
    	return PROBABILIDADE_REPRODUCAO;
    }
    
    
    public static void setIdadeReproducao(int novaIdade)
    {
    	IDADE_REPRODUCAO = novaIdade;
    }
    
    
    public static void setIdadeMaxima(int novaIdade)
    {
    	IDADE_MAXIMA = novaIdade;
    }
    
    
    public static void setProbabilidadeReproducao(double prob)
    {
    	PROBABILIDADE_REPRODUCAO = prob;
    }
    
    
    public static void setMaxNascimento(int maxNascimento)
    {
    	MAX_NASCIMENTO = maxNascimento;
    }
}
