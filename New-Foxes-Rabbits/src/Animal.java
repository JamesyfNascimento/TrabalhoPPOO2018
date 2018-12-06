import java.util.List;
import java.util.Random;


public abstract class Animal implements Personagem {
    // Whether the animal is alive or not.
    private boolean vivo;
    // The animal's field.
    private Campo campo;
    // The animal's position in the field.
    private Localizacao localizacao;
    // An animal's age.
    private int idade;
    // An animal's food level, which is increased by eating.
    private int nivelComida;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomico.getRandom();

    
    public Animal(Campo campo, Localizacao localizacao) {
        vivo = true;
        this.campo = campo;
        setLocalizacao(localizacao);
    }

    
    @Override
    public abstract void act(List<Personagem> novosAnimais);

    
    @Override
    public boolean ehVivo() {
        return vivo;
    }

    
    protected void matar() {
        vivo = false;
        if (localizacao != null) {
            campo.limpar(localizacao);
            localizacao = null;
            campo = null;
        }
    }

    
    protected void envelhecer() {
        setIdade(getIdade() + 1);
        if (getIdade() > idadeMaxima()) {
            matar();
        }
    }

    
    protected abstract int idadeMaxima();

    
    protected Localizacao getLocalizacao() {
        return localizacao;
    }

    
    @Override
    public void setLocalizacao(Localizacao novaLocalizacao) {
        if (localizacao != null) {
            campo.limpar(localizacao);
        }
        localizacao = novaLocalizacao;
        campo.lugar(this, novaLocalizacao);
    }

   
    protected Campo getCampo() {
        return campo;
    }

    
    protected int procriar() {
        int nascimentos = 0;
        if (podeProcriar() && getRandom().nextDouble() <= getProbabilidadeProcriar()) {
            nascimentos = getRandom().nextInt(getNumMaxNascimento()) + 1;
        }
        return nascimentos;
    }

   
    protected abstract boolean podeProcriar();

    
    protected int getIdade() {
        return idade;
    }

    
    protected void setIdade(int age) {
        this.idade = age;
    }

    
    protected int getNivelComida() {
        return nivelComida;
    }

   
    protected void setNivelComida(int nivelComida) {
        this.nivelComida = nivelComida;
    }

   
    protected Random getRandom() {
        return rand;
    }

   
    protected abstract int getNumMaxNascimento();

    
    protected abstract double getProbabilidadeProcriar();
}