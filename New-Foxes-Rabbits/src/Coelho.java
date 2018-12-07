import java.util.Iterator;
import java.util.List;

/**
 * Classe para criacao de um simples coelho que pode procriar, comer grama, 
 * servir de presa para outros animais e morrer
 * @author isabela
 */

public class Coelho extends Animal
{
    // Caracteristicas comuns aos coelhos(class variables).
    // A idade que um coelho pode começar a procriar.
    private static int IDADE_REPRODUCAO = 5;
    // A idade máxima de um coelho.
    private static int IDADE_MAXIMA = 40;
    // A probabilidade de reproduzir do coelho.
    private static double PROBABILIDADE_REPRODUCAO = 0.12;
    // O número máximo de filhotes.
    private static int MAX_NASCIMENTO = 4;
    // Valor atribuído à alimentação do coelho - a grama
    private static final int VALOR_ALIMENTO_GRAMA = 6;
    
    /**
     * Construtor de criacao do coelho, que recebe se ele tera idade aleatoria
     * ou será um recem nascido e sua localizacao no campo.
     * @param idadeAleatoria valor booleano
     * @param campo - tipo da Classe Campo
     * @param localizacao - tipo da Classe localização
     */
    public Coelho(boolean idadeAleatoria, Campo campo, Localizacao localizacao)
    {
        super(campo, localizacao);
        setIdade(0);
        if(idadeAleatoria) {
        	setIdade(getRandom().nextInt(IDADE_MAXIMA));
        }
    }
      
    /**
     * Configuração das acoes do coelho, coelhos reproduzem, comem grama ou
     * podem virar presa
     * @param novosCoelhos 
     */
    @Override
    public void act(List<Personagem> novosCoelhos)
    {
        envelhecer();
        if(ehVivo()) {
            darHaLuz(novosCoelhos);            
            // Se move para procurar comida
            Localizacao novaLocalizacao = procuraComida();
            if(novaLocalizacao == null) { 
                // Quando nao encontra comida, procura uma nova localizacao
                novaLocalizacao = getCampo().LocalizacaoAdjLivre(getLocalizacao());
            }
            // Verifica se e possivel se mover.
            if(novaLocalizacao != null) {
                setLocalizacao(novaLocalizacao);
            }
            else {
                // Superpopulacao de coelhos.
                matar();
            }
        }
    }
    /**
     * Reprodução dos coelhos, coelhos recem-nascidos recebem idade igual a zero
     * e são colocados dentro do campo em localições adjacentes de onde nasceram
     * @param novosCoelhos 
     */
    
    private void darHaLuz(List<Personagem> novosCoelhos)
    {
        // Novos coelhos nascem em localizacoes adjacentes.
        Campo campo = getCampo();
        List<Localizacao> livre = campo.getLocalizacoesAdjLivres(getLocalizacao());
        int births = procriar();
        for(int b = 0; b < births && livre.size() > 0; b++) {
            Localizacao loc = livre.remove(0);
            Coelho young = new Coelho(false, campo, loc);
            novosCoelhos.add(young);					
        }
    }
    /**
     * Procura por localizacoes vazias onde ha grama.
     * @return 
     */
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
    
    /**
     * Determina se o coelho pode procriar ou nao
     * @return 
     */
    @Override
    protected boolean podeProcriar()
    {
        return getIdade() >= getIdadeReproducao();
    }
    
    /**
     * Retorna se o coelho está em idade de procriacao
     * @return IDADE_REPRODUCAO
     */
    protected int getIdadeReproducao()
    {
    	return IDADE_REPRODUCAO;
    }
    
    /**
     * Retorna a idade limite do coelho
     * @return IDADE_MAXIMA
     */
    @Override
    protected int idadeMaxima()
    {
    	return IDADE_MAXIMA;
    }
    
    /**
     * Retorna a probabiliade de procriar de um coelho
     * @return PROBABILIDADE_REPRODUCAO
     */
    @Override
    protected double getProbabilidadeProcriar()
    {
    	return PROBABILIDADE_REPRODUCAO;
    }
    
    /**
     * Retorna o maior numero de nascimentos possivel
     * @return MAX_NASCIMENTO
     */
    @Override
    protected int getNumMaxNascimento()
    {
    	return MAX_NASCIMENTO;
    }
    
    /**
     * Configura a idade de reproducao de um coelho
     * @param novaIdadeReproducao 
     */
    
    public static void setIdadeReproducao(int novaIdadeReproducao)
    {
    	IDADE_REPRODUCAO = novaIdadeReproducao;
    }
    
    /**
     * Configura a idade maxima de um coelho
     * @param novaIdade
     */
    public static void setIdadeMaxima(int novaIdade)
    {
    	IDADE_MAXIMA = novaIdade;
    }
    
    /**
     * Configura a probabilidade de reproducao do coelho
     * @param novaProbReproucao
     */
    public static void serProbReproducao(double novaProbReproucao)
    {
    	PROBABILIDADE_REPRODUCAO = novaProbReproucao;
    }
    
    /**
    * Confira o numero maximo de nascimentos
    * @param novaMaxNascimento
    */
    public static void setMaxNascimento(int novaMaxNascimento)
    {
    	MAX_NASCIMENTO = novaMaxNascimento;
    }
}
