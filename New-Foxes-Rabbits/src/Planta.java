import java.util.List;
import java.util.Random;

/**
 * Classe abstrata para criacao de seres vivos que facam parte da vegetacao.
 */
public abstract class Planta implements Personagem {
    // Se esta viva ou nao.
    private boolean vivo;
    // Campo da planta.
    private Campo campo;
    // Localizacao.
    private Localizacao localizacao;
    // Idade da planta.
    private int idade;
    // Nivel da alimentacao
    private int nivelAlimentar;

  
    public static final int NUM_PASSOS_REPRODUZIR = 12;
    // gerador de valores aleatorios
    protected static final Random rand = Randomico.getRandom();

    /**
     * Construtor que recebe os parametros do campo da planta e a localizacao e 
     * cria a planta na localizacao determinada por padrao viva (obvio).
     * @param campo do tipo Campo
     * @param localizacao do tipo Localizacao
     */    
    public Planta(Campo campo, Localizacao localizacao) {
        vivo = true;
        this.campo = campo;
        setLocalizacao(localizacao);
    }
    /**
     * Método para determinar a acao do personagem
     * @param novasPlantas
     */
    @Override
    public abstract void act(List<Personagem> novasPlantas);

    /**
     * Retorna o status de vida da planta.
     * @return true se a planta estiver vivo
     */
    @Override
    public boolean ehVivo() {
        return vivo;
    }

    /**
     * Ao ser ser declarada morta a planta é retirada do campo.
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
    * Aumenta a idade até chegar ao limite
    */
    protected void envenhecer() {
    setIdade(getIdade() + 1);
    if (getIdade() > getIdadeMaxima()) {
        matar();
    }
    }

    /**
    * Para retornar a idade maxima
    * @return 
    */  
    protected abstract int getIdadeMaxima();

    /**
    * Retorna a localizacao da planta.
    * @return localizacao
    */
    protected Localizacao getLocalizacao() {
        return localizacao;
    }

    /**
    * Atribuicao de nova localizacao
    * @param newLocation 
    */
    @Override
    public void setLocalizacao(Localizacao newLocation) {
        if (localizacao != null) {
            campo.limpar(localizacao);
        }
        localizacao = newLocation;
        campo.lugar(this, newLocation);
    }

    /**
    * Retorna o campo
    * @return campo 
    */
    protected Campo getCampo() {
        return campo;
    }

    /**
    * Criar as novas geracoes
    * @return births
    */
    protected int procriar() {
        int births = 0;
        if (podeNascer() && getRandom().nextDouble() <= getprobabilidadeNascer()) {
            births = getRandom().nextInt(getMaxNascimento()) + 1;
        }
        return births;
    }

    /**
    * Se pode nascer, gerar novos espécimes
    * @return 
    */
    protected abstract boolean podeNascer();

    /**
    * Retorna idade
    * @return idade 
    */
    protected int getIdade() {
        return idade;
    }

    /**
    * Define a idade
    * @param age 
    */
    protected void setIdade(int age) {
        this.idade = age;
    }

    /**
    * Retorna nivel de alimentacao
    * @return nivelAlimentar
    */
    protected int getNivelAlimentar() {
        return nivelAlimentar;
    }

    /**
    * Atribui nivel alimentar
    * @param nivelAlimentar 
    */
    protected void setNivelAlimentar(int nivelAlimentar) {
        this.nivelAlimentar = nivelAlimentar;
    }

    /**
    * Retorna valor aleatorio
    * @return rand
    */  
    protected Random getRandom() {
        return rand;
    }

    /**
    * Retornar maximo de nascimentos
    * @return 
    */
    protected abstract int getMaxNascimento();

    /**
    * Retornar probabilidade de nascer
    * @return 
    */
    protected abstract double getprobabilidadeNascer();
}
