import java.util.List;
import java.util.Random;

/**
 * Classe abstrata com as caracteristicas comuns a todos os animais
 */
public abstract class Animal implements Personagem {
    // Se o animal esta vivo ou nao.
    private boolean vivo;
    // Campo do animal.
    private Campo campo;
    // A posicao do animal no campo.
    private Localizacao localizacao;
    // A idade do animal.
    private int idade;
    // O nivel alimentar de um animal, que e aumentado pela ingestao.
    private int nivelComida;
    // Um gerador de numeros aleatorios compartilhados para controlar a reproducao.
    private static final Random rand = Randomico.getRandom();
	
    /**
     * Construtor que recebe os parametros do campo do animal e a localizacao e 
     * cria o animal na localizacao determinada por padrao vivo (obvio).
     * @param campo do tipo Campo
     * @param localizacao do tipo Localização
     */    
    public Animal(Campo campo, Localizacao localizacao) {
        vivo = true;
        this.campo = campo;
        setLocalizacao(localizacao);
    }

    /**
     * Metodo para determinar a acao do personagem
     */
	@Override
    public abstract void act(List<Personagem> novosAnimais);

    /**
     * Retorna o status de vida do animal.
     * @return true se o animal estiver vivo
     */
    @Override
    public boolean ehVivo() {
        return vivo;
    }

    /**
     * Ao ser ser declarado morto o animal e retirado do campo.
     */
    protected void matar() {
        vivo = false;
        if (localizacao != null) {
            campo.limpar(localizacao);
            localizacao = null;
            campo = null;
        }
    }

    /**
     * Incrementa a idade do animal e caso este atinja a idade maxima e morto.
     */
    protected void envelhecer() {
        setIdade(getIdade() + 1);
        if (getIdade() > idadeMaxima()) {
            matar();
        }
    }

    /**
     * Metodo para definir idade maxima
     * @return idade maxima 
     */
    protected abstract int idadeMaxima();

    /**
     * @return localização do animal.
     */
    protected Localizacao getLocalizacao() {
        return localizacao;
    }

    /**
     * Metodo para configurar a localizacao do animal.
     * @param novaLocalizacao do Tipo Localização 
     */   
    @Override
    public void setLocalizacao(Localizacao novaLocalizacao) {
        if (localizacao != null) {
            campo.limpar(localizacao);
        }
        localizacao = novaLocalizacao;
        campo.lugar(this, novaLocalizacao);
    }

    /**
     * @return o campo onde esta o animal.
     */
    protected Campo getCampo() {
        return campo;
    }
    
    /**
     * Metodo para determinar o nacimentos dos agentes que estão no campo.
     * @return o numero de nascimentos.
     */
    protected int procriar() {
        int nascimentos = 0;
        if (podeProcriar() && getRandom().nextDouble() <= getProbabilidadeProcriar()) {
            nascimentos = getRandom().nextInt(getNumMaxNascimento()) + 1;
        }
        return nascimentos;
    }

    /**
     * Alguns animais podem pocriar outros nao.
     * @return true or false.
    */
    protected abstract boolean podeProcriar();

    /**
     * @return a idade do animal.
     */
    protected int getIdade() {
        return idade;
    }

    /**
     * Configura a idade atual do animal.
     * @param age A idade do animal.
     */
    protected void setIdade(int age) {
        this.idade = age;
    }

    /**
     * @return o nivel alimentardo animal.
     */
    protected int getNivelComida() {
        return nivelComida;
    }

    /**
     * Configura o nivel alimentar do animal.
     * @param nivelComida o nível alimentar.
     */
    protected void setNivelComida(int nivelComida) {
        this.nivelComida = nivelComida;
    }

    /**
     * @return um numero aleatorio que ira controlar a reproducao.
     */
    protected Random getRandom() {
        return rand;
    }

    /**
     * @return o numero maximo de nascimentos.
     */
    protected abstract int getNumMaxNascimento();

    /**
     * @return a probabilidade de procriar.
     */
    protected abstract double getProbabilidadeProcriar();
}
