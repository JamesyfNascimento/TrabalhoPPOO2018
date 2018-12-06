import java.util.List;
import java.util.Random;

public abstract class Planta implements Personagem {
  // Whether the plant is alive or not.
  private boolean vivo;
  // The plant's field.
  private Campo campo;
  // The plant's position in the field.
  private Localizacao localizacao;
  // An plant's age.
  private int idade;
  // An plant's food level, which is increased by eating.
  private int nivelAlimentar;

  // The food value of a single plant. In effect, this is the
  // number of steps an plant can go before it has to eat again.
  public static final int NUM_PASSOS_REPRODUZIR = 12;
  // A shared random number generator to control breeding.
  protected static final Random rand = Randomico.getRandom();

 
  public Planta(Campo campo, Localizacao localizacao) {
    vivo = true;
    this.campo = campo;
    setLocalizacao(localizacao);
  }

 
  public abstract void act(List<Personagem> novasPlantas);

  
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

  protected void envenhecer() {
    setIdade(getIdade() + 1);
    if (getIdade() > getIdadeMaxima()) {
      matar();
    }
  }

  
  protected abstract int getIdadeMaxima();

  
  protected Localizacao getLocalizacao() {
    return localizacao;
  }

 
  public void setLocalizacao(Localizacao newLocation) {
    if (localizacao != null) {
      campo.limpar(localizacao);
    }
    localizacao = newLocation;
    campo.lugar(this, newLocation);
  }

  
  protected Campo getCampo() {
    return campo;
  }

  
  protected int procriar() {
    int births = 0;
    if (podeNascer() && getRandom().nextDouble() <= getprobabilidadeNascer()) {
      births = getRandom().nextInt(getMaxNascimento()) + 1;
    }
    return births;
  }

  
  protected abstract boolean podeNascer();

  
  protected int getIdade() {
    return idade;
  }

  
  protected void setIdade(int age) {
    this.idade = age;
  }

  
  protected int getNivelAlimentar() {
    return nivelAlimentar;
  }

  
  protected void setNivelAlimentar(int nivelAlimentar) {
    this.nivelAlimentar = nivelAlimentar;
  }

  
  protected Random getRandom() {
    return rand;
  }

  
  protected abstract int getMaxNascimento();

  
  protected abstract double getprobabilidadeNascer();
}