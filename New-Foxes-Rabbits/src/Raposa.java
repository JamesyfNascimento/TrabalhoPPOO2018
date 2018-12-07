import java.util.List;
import java.util.Iterator;


/**
 * Classe com as caracerísticas e ações das raposas
 * @author Anderson, Isabela, James
 */
public class Raposa extends Animal {
     // Características comuns às raposas.
    // A idade que uma raposa pode começar a procriar.
    private static int IDADE_REPRODUCAO = 15;
    // Idade máxima que uma raposa pode viver.
    private static int IDADE_MAXIMA = 150;
    // Probabilidade da raposa procriar
    private static double PROBABILIDADE_REPRODUCAO = 0.08;
     // Máximo de filhores
    private static int MAX_NASCIMENTO = 4;
    // Número de passo até poder comer de novo
    private static final int NIVEL_COMIDA = 9;

    /**
     * Criação de uma raposa com idade aleatória ou recém-nascida
     * @param idadeAleatoria
     * @param campo
     * @param localizacao 
     */
    public Raposa(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        super(campo, localizacao);
        if (idadeAleatoria) {
            setIdade(getRandom().nextInt(IDADE_MAXIMA));
            setNivelComida(getRandom().nextInt(NIVEL_COMIDA));
        } else {
            setIdade(0);
            setNivelComida(NIVEL_COMIDA);
        }
    }
    /**
     * Ações de uma raposa (comer, se mover)
     * @param novasRaposas 
     */
    @Override
    public void act(List<Personagem> novasRaposas) {
        envelhecer();
        aumentaFome();
        if (ehVivo()) {
            darHaLuz(novasRaposas);
            // Se move para procurar comida
            Localizacao novaLocalizacao = procuraComida();
            if (novaLocalizacao == null) {
                // Se não encontrar comida, tenta achar uma nova localização 
                // vazia
                novaLocalizacao = getCampo().LocalizacaoAdjLivre(getLocalizacao());
            }
            // See if it was possible to move.
            if (novaLocalizacao != null) {
                setLocalizacao(novaLocalizacao);
            } else {
                // Superpopulação.
                matar();
            }
        }
    }

    /**
     * Retorna a idade máxima
     * @return IDADE_MAXIMA
     */
    @Override
    protected int idadeMaxima() {
        return IDADE_MAXIMA;
    }

    /**
     * Aumentar fome diminuindo o nível de comida
     */
    private void aumentaFome() {
        setNivelComida(getNivelComida() - 1);
        if (getNivelComida() <= 0) {
            matar();
        }
    }

    /**
     * Procurar comida
     * @return null, ou local onde há comida
     */
    private Localizacao procuraComida() {
        Campo campo = getCampo();
        List<Localizacao> adjacente = campo.localizacoesAdjacentes(getLocalizacao());
        Iterator<Localizacao> it = adjacente.iterator();
        while (it.hasNext()) {
            Localizacao onde = it.next();
            Object animal = campo.getPersonagem(onde);
            if (animal instanceof Coelho) {
                Coelho coelho = (Coelho) animal;
                if (coelho.ehVivo()) {
                    coelho.matar();
                    setNivelComida(NIVEL_COMIDA);
                    return onde;
                }
            }
        }
        return null;
    }

    /**
     * Reprodução das raposas, raposas recém-nascidas recebem idade igual a zero
     * e são colocados dentro do campo em localições adjacentes de onde nasceram
     * @param novosCoelhos 
     */
    private void darHaLuz(List<Personagem> novasRaposas) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Campo campo = getCampo();
        List<Localizacao> livre = campo.getLocalizacoesAdjLivres(getLocalizacao());
        int nascimentos = procriar();
        for (int b = 0; b < nascimentos && livre.size() > 0; b++) {
            Localizacao loc = livre.remove(0);
            Raposa filhote = new Raposa(false, campo, loc);
            novasRaposas.add(filhote);
        }
    }

    /**
     * Determina se a raposa pode procriar ou não
     * @return 
     */
    @Override
    protected boolean podeProcriar() {
        return getIdade() >= getIdadeReproducao();
    }

    /**
     * Retorna se a raposa está em idade de procriação
     * @return IDADE_REPRODUÇÃO
     */
    protected int getIdadeReproducao() {
        return IDADE_REPRODUCAO;
    }

    /**
     * Retorna o maior número de nascimentos possível
     * @return MAX_NASCIMENTO
     */
    @Override
    protected int getNumMaxNascimento() {
        return MAX_NASCIMENTO;
    }

        /**
     * Retorna a probabiliade de procriar.
     * @return PROBABILIDADE_REPRODUCAO
     */
    @Override
    protected double getProbabilidadeProcriar() {
        return PROBABILIDADE_REPRODUCAO;
    }

    /**
     * Configura a idade de reprodução de um coelho
     * @param novaIdadeReproducao 
     */
    public static void setIdadeReproducao(int novaIdade) {
        IDADE_REPRODUCAO = novaIdade;
    }

    /**
     * Configura a idade máxima de um coelho
     * @param novaIdade
     */
    public static void setIdadeMaxima(int novaIdade) {
        IDADE_MAXIMA = novaIdade;
    }

    /**
     * Atriubir nova probalidade de reprodução
     * @param prob 
     */
    public static void setProbabilidadeReproducao(double prob) {
        PROBABILIDADE_REPRODUCAO = prob;
    }

    /**
    * Configura o número máximo de nascimentosd
    * @param novaMaxNascimento
    */
    public static void setMaxNascimento(int maxNascimento) {
        MAX_NASCIMENTO = maxNascimento;
    }
}
