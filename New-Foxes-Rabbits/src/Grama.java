import java.util.List;

public class Grama extends Planta {
  // Characteristics shared by all grass (class variables).

  // The age at which a grass can start to breed.
  private static int IDADE_REPRODUCAO = 3;
  // The age to which a grass can live.
  private static int IDADE_MAXIMA = 12;
  // The likelihood of a grass breeding.
  private static double PROBABILIDADE_NASCIMENTOS = 0.5;
  // The maximum number of births.
  private static int MAX_NASCIMENTOS = 6;

 
  public Grama(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
    super(campo, localizacao);
    setIdade(0);
    if (idadeAleatoria) {
      setIdade(getRandom().nextInt(IDADE_MAXIMA));
    }
  }

  
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
        // Overcrowding.
        matar();
      }
    }
  }

  
  private void plantar(List<Personagem> novasGramas) {
    // New grass are born into adjacent locations.
    // Get a list of adjacent free locations.
    Campo campo = getCampo();
    List<Localizacao> livre = campo.getLocalizacoesAdjLivres(getLocalizacao());
    int nascimentos = procriar();
    for (int b = 0; b < nascimentos && livre.size() > 0; b++) {
      Localizacao loc = livre.remove(0);
      Grama novaGrama = new Grama(false, campo, loc);
      novasGramas.add(novaGrama);
    }
  }

 
  @Override
  protected boolean podeNascer() {
    return getIdade() >= getIdadeNascer();
  }

  
  public static void setIdadeNascer(int idadeParaNascer) {
    if (idadeParaNascer >= 0)
      Grama.IDADE_REPRODUCAO = idadeParaNascer;
  }

  
  public static void setIdadeMaxima(int idadeMaxima) {
    if (idadeMaxima >= 1)
      Grama.IDADE_MAXIMA = idadeMaxima;
  }

  
  public static void setProbabilidadeNascer(double probabilidadeNascer) {
    if (probabilidadeNascer >= 0)
      Grama.PROBABILIDADE_NASCIMENTOS = probabilidadeNascer;
  }

  
  public static void setMaxNascimento(int maxNascimentos) {
    if (maxNascimentos >= 1)
      Grama.MAX_NASCIMENTOS = maxNascimentos;
  }

 
  public static void setPadrao() {
    IDADE_REPRODUCAO = 1;
    IDADE_MAXIMA = 100;
    PROBABILIDADE_NASCIMENTOS = 0.02;
    MAX_NASCIMENTOS = 100;
  }

  
  protected int getIdadeNascer() {
    return IDADE_REPRODUCAO;
  }

  
  @Override
  protected int getIdadeMaxima() {
    return IDADE_MAXIMA;
  }

  
  @Override
  protected int getMaxNascimento() {
    return MAX_NASCIMENTOS;
  }

  
  @Override
  protected double getprobabilidadeNascer() {
    return PROBABILIDADE_NASCIMENTOS;
  }
}