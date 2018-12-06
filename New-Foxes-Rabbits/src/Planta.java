import java.util.List;
import java.util.Random;

/**
 * Classe abstrata para criação de seres vivos que façam parte da vegetação
 * @author Anderson, Isabela, James
 */
public abstract class Planta implements Personagem {
  // Se está viva ou não.
  private boolean vivo;
  // Campo da planta.
  private Campo campo;
  // Localização.
  private Localizacao localizacao;
  // Idade da planta.
  private int idade;
  // Nível da alimentação
  private int nivelAlimentar;

  
  public static final int NUM_PASSOS_REPRODUZIR = 12;
  // gerador de valores aleatórios
  protected static final Random rand = Randomico.getRandom();

 /**
     * Construtor que recebe os parametros do campo da planta e a localizacao e 
     * cria a planta na localizacao determinada por padrao viva (obvio).
     * @param campo do tipo Campo
     * @param localizacao do tipo Localização
     */    
  public Planta(Campo campo, Localizacao localizacao) {
    vivo = true;
    this.campo = campo;
    setLocalizacao(localizacao);
  }
    /**
     * Método para determinar a ação do personagem
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
   * Para retornar a idade máxima
   * @return 
   */  
  protected abstract int getIdadeMaxima();

    /**
     * Retorna a localizacao da planta.
     * @return localização
     */
  protected Localizacao getLocalizacao() {
    return localizacao;
  }

  /**
   * Atribuição de nova localização
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
   * Criar as novas gerações
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
   * Retorna nível de alimentação
   * @return nivelAlimentar
   */
  protected int getNivelAlimentar() {
    return nivelAlimentar;
  }

  /**
   * Atribui nível alimentar
   * @param nivelAlimentar 
   */
  protected void setNivelAlimentar(int nivelAlimentar) {
    this.nivelAlimentar = nivelAlimentar;
  }

  /**
   * Retorna valor aleatório
   * @return rand
   */  
  protected Random getRandom() {
    return rand;
  }

  /**
   * Retornar máximo de nascimentos
   * @return 
   */
  protected abstract int getMaxNascimento();

  /**
   * Retornar probabilidade de nascer
   * @return 
   */
  protected abstract double getprobabilidadeNascer();
}
