import java.util.List;
import java.util.Iterator;

/**
 * Um modelo simples de um urso que pode se mover, comer ou morrer.
 */
public class Urso extends Animal {
    // Caracteristicas compartilhadas entre os ursos.

    // A idade que um urso pode comecar a procriar.
    private static int IDADE_REPRODUCAO = 30;
    // A idade maxima que pode viver o urso
    private static int IDADE_MAXIMA = 190;
    // Probabiliade de procriar
    private static double PROBABILIDADE_REPRODUCAO = 0.02;
    // Número maximo de filhotes.
    private static int MAX_NASCIMENTO = 1;
    // Valor da alimentacao e quantidade de passos para comer novamente
    private static final int NUM_PASSOS_REPRODUZIR = 9;
    private static final int VALOR_ALIMENTO_RAPOSA = 18;

    /**
     * Criacao de um urso com idade aleatória ou recem-nascido
     *
     * @param idadeAleatoria
     * @param campo
     * @param localizacao
     * 
     */
    public Urso(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        super(campo, localizacao);
        if (idadeAleatoria) {
            setIdade(getRandom().nextInt(IDADE_MAXIMA));
            setNivelComida(getRandom().nextInt(NUM_PASSOS_REPRODUZIR + VALOR_ALIMENTO_RAPOSA));
        } else {
            setIdade(0);
            setNivelComida(NUM_PASSOS_REPRODUZIR + VALOR_ALIMENTO_RAPOSA);
        }
    }

    /**
     * Acoes do urso
     *
     * @param novoUrsos
     */
    @Override
    public void act(List<Personagem> novoUrsos) {
        envelhecer();
        aumentarFome();
        if (ehVivo()) {
            darHaLuz(novoUrsos);
            // Procura comida
            Localizacao novaLocalizacao = procuraComida();
            if (novaLocalizacao == null) {
                // Se nao acha comida se move.
                novaLocalizacao = getCampo().LocalizacaoAdjLivre(getLocalizacao());
            }
            // Verifica se e possível se mover
            if (novaLocalizacao != null) {
                setLocalizacao(novaLocalizacao);
            } else {
                // Superpopulacao.
                matar();
            }
        }
    }

    /**
     * Idade maxima
     *
     * @return IDADE_MAXIMA
     */
    @Override
    protected int idadeMaxima() {
        return IDADE_MAXIMA;
    }

    /**
     * Diminui o nivel de comida e aumenta a fome
     */
    private void aumentarFome() {
        setNivelComida(getNivelComida() - 1);
        if (getNivelComida() <= 0) {
            matar();
        }
    }

    /**
     * Acões necessarias para procurar comida
     *
     * @return
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
                    setNivelComida(NUM_PASSOS_REPRODUZIR);
                    return onde;
                }
            } else if (animal instanceof Raposa) {
                Raposa raposa = (Raposa) animal;
                if (raposa.ehVivo()) {
                    raposa.matar();
                    setNivelComida(VALOR_ALIMENTO_RAPOSA);
                    return onde;
                }

            }
        }
        return null;
    }

    /**
     * Reproduzir
     *
     * @param novosUrsos
     */
    private void darHaLuz(List<Personagem> novosUrsos) {
        // Novos ursos nacem em localizacoes adjacetes.
        Campo campo = getCampo();
        List<Localizacao> livre = campo.getLocalizacoesAdjLivres(getLocalizacao());
        int births = procriar();
        for (int b = 0; b < births && livre.size() > 0; b++) {
            Localizacao loc = livre.remove(0);
            Urso filhote = new Urso(false, campo, loc);
            novosUrsos.add(filhote);
        }
    }

    /**
     * Determina se o urso pode procriar ou nao
     *
     * @return idade se for maoir ou igual a idade de reproducao
     */
    @Override
    protected boolean podeProcriar() {
        return getIdade() >= getIdadeReproduzir();
    }

    /**
     * Retorna se o urso esta em idade de procriacao
     *
     * @return IDADE_REPRODUÇÃO
     */
    protected int getIdadeReproduzir() {
        return IDADE_REPRODUCAO;
    }

    /**
     * Retorna o maior número de nascimentos possível
     *
     * @return MAX_NASCIMENTO
     */
    @Override
    protected int getNumMaxNascimento() {
        return MAX_NASCIMENTO;
    }

    /**
     * Retorna a probabiliade de procriar.
     *
     * @return PROBABILIDADE_REPRODUCAO
     */
    @Override
    protected double getProbabilidadeProcriar() {
        return PROBABILIDADE_REPRODUCAO;
    }

    /**
     * Configura a idade de reproducao de um urso
     *
     * @param novaIdadeReproducao
     */
    public static void setIdadeReproducao(int novaIdadeReproducao) {
        IDADE_REPRODUCAO = novaIdadeReproducao;
    }

    /**
     * Configura a idade maxima de uma raposa
     *
     * @param novaIdadeMaxima
     */
    public static void setIdadeMaxima(int novaIdadeMaxima) {
        IDADE_MAXIMA = novaIdadeMaxima;
    }

    /**
     * Atribuir nova probalidade de reproducao
     *
     * @param novaProbReproducao
     */
    public static void setProbReproducao(double novaProbReproducao) {
        PROBABILIDADE_REPRODUCAO = novaProbReproducao;
    }

    /**
     * Configura o número maximo de nascimentos
     *
     * @param maxNascimento
     */
    public static void setMaxNascimento(int maxNascimento) {
        MAX_NASCIMENTO = maxNascimento;
    }
}
