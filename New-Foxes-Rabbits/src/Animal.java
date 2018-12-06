import java.util.List;
import java.util.Random;

/**
 * Classe abstrata com as caracteristicas comuns a todos os animais
 * @author Anderson, Isabela, James
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
    // Um gerador de numeros aleatorios compartilhados para controlar a reprodução.
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

    Falta
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

    Falta
    protected abstract int idadeMaxima();

    /**
     * Retorna a localizacao do animal.
     * @return true se o animal estiver vivo
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
     * Retorna o campo onde esta o animal.
     * @return o campo
     */
    protected Campo getCampo() {
        return campo;
    }

    Falta
    protected int procriar() {
        int nascimentos = 0;
        if (podeProcriar() && getRandom().nextDouble() <= getProbabilidadeProcriar()) {
            nascimentos = getRandom().nextInt(getNumMaxNascimento()) + 1;
        }
        return nascimentos;
    }

    Falta
    protected abstract boolean podeProcriar();

    /**
     * Retorna a idade do animal.
     * @return a idade
     */
    protected int getIdade() {
        return idade;
    }

    /**
     * Configura a idade atual do animal
     * @param age idade do tipo int do animal
     */
    protected void setIdade(int age) {
        this.idade = age;
    }

    /**
     * Retorna o nivel alimentar do animal
     * @return o nivel alimentar
     */
    protected int getNivelComida() {
        return nivelComida;
    }

    /**
     * Configura o nivel alimentar do animal
     * @paran o nivel alimentar
     */
    protected void setNivelComida(int nivelComida) {
        this.nivelComida = nivelComida;
    }

    /**
     * Retorna um numero aleatorio que ira controlar a reproducao.
     * @return um numero aleatorio
     */
    protected Random getRandom() {
        return rand;
    }

    Falta
    protected abstract int getNumMaxNascimento();

    Falta
    protected abstract double getProbabilidadeProcriar();
}