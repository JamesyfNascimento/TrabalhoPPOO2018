import java.util.List;

public class Grama extends Planta {
  // Caracteristicas da grama.

  // Idade de reproducao.
  private static int IDADE_REPRODUCAO = 3;
  // Tempo de vida da grama.
  private static int IDADE_MAXIMA = 12;
  // Probabilidade de nascer grama.
  private static double PROBABILIDADE_NASCIMENTOS = 0.5;
  private static int MAX_NASCIMENTOS = 6;

  /**
   * Ao ser criada a grama assim como todos os outros seres vivos do ambiente
   * criado pode ter idade aleatoria, como tambem ser nova
   * @param idadeAleatoria booleando
   * @param campo tipo da Classe Campo
   * @param localizacao tipo da Classe Localizacao
   */
  public Grama(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
    super(campo, localizacao);
    setIdade(0);
    if (idadeAleatoria) {
      setIdade(getRandom().nextInt(IDADE_MAXIMA));
    }
  }
 
  /**
   * Acoes possiveis da grama
   * @param novasGramas 
   */  
  @Override
  public void act(List<Personagem> novasGramas) {
    envenhecer();
    if (ehVivo()) {

      plantar(novasGramas);
      // Try to move into a free location.
      Localizacao novaLocalizacao = getCampo().LocalizacaoAdjLivre(getLocalizacao());
      if (novaLocalizacao != null) {
        setLocalizacao(novaLocalizacao);
      } else {
        // Superpopulacao.
        matar();
      }
    }
  }

  /**
   * Criar grama nova
   * @param novasGramas 
   */
  private void plantar(List<Personagem> novasGramas) {
    // Nova grama nasce na localizações adjacentes.
    // Recebe uma lista de localizações adjacentes livres.
    Campo campo = getCampo();
    List<Localizacao> livre = campo.getLocalizacoesAdjLivres(getLocalizacao());
    int nascimentos = procriar();
    for (int b = 0; b < nascimentos && livre.size() > 0; b++) {
      Localizacao loc = livre.remove(0);
      Grama novaGrama = new Grama(false, campo, loc);
      novasGramas.add(novaGrama);
    }
  }

  /**
   * Se pode nascer gerar novos especimes
   * @return 
   */
  @Override
  protected boolean podeNascer() {
    return getIdade() >= getIdadeNascer();
  }
 
  /**
   * Atribuir idade de repoduzir
   * @param idadeParaNascer 
   */ 
  public static void setIdadeNascer(int idadeParaNascer) {
    if (idadeParaNascer >= 0)
      Grama.IDADE_REPRODUCAO = idadeParaNascer;
  }

  /**
   * Configuracao da idade maxima que a grama pode atingir
   * @param idadeMaxima 
   */
  public static void setIdadeMaxima(int idadeMaxima) {
    if (idadeMaxima >= 1)
      Grama.IDADE_MAXIMA = idadeMaxima;
  }

  /**
   * Configuracao da probabilidade de reproduzir
   * @param probabilidadeNascer 
   */
  public static void setProbabilidadeNascer(double probabilidadeNascer) {
    if (probabilidadeNascer >= 0)
      Grama.PROBABILIDADE_NASCIMENTOS = probabilidadeNascer;
  }

  /**
   * Número maximo de reproducoes
   * @param maxNascimentos 
   */
  public static void setMaxNascimento(int maxNascimentos) {
    if (maxNascimentos >= 1)
      Grama.MAX_NASCIMENTOS = maxNascimentos;
  }

 /**
  * Valores default atribuidos à grama 
  */
  public static void setPadrao() {
    IDADE_REPRODUCAO = 1;
    IDADE_MAXIMA = 100;
    PROBABILIDADE_NASCIMENTOS = 0.02;
    MAX_NASCIMENTOS = 100;
  }

  /**
   * Retorna a idade de reproducao
   * @return 
   */
  protected int getIdadeNascer() {
    return IDADE_REPRODUCAO;
  }

  /**
   * Retorna a idade maxima que pode chegar a grama
   * @return IDADE_MAXIMA
   */
  @Override
  protected int getIdadeMaxima() {
    return IDADE_MAXIMA;
  }

  /**
   * retorna o maximo de reproducoes
   * @return MAX_NASCIMENTOS
   */
  @Override
  protected int getMaxNascimento() {
    return MAX_NASCIMENTOS;
  }

  /**
   * Retorna o valor da probabilidade de nascimentos
   * @return PROBABILIDADE_NASCIMENTOS
   */
  @Override
  protected double getprobabilidadeNascer() {
    return PROBABILIDADE_NASCIMENTOS;
  }
}
