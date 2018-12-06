import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a Bear. Bear age, move, eat fox and rabbits, and die.
 */
public class Urso extends Animal {
  // Characteristics shared by all bears (class variables).

  // The age at which a bear can start to breed.
  private static int IDADE_REPRODUCAO = 30;
  // The age to which a bear can live.
  private static int IDADE_MAXIMA = 190;
  // The likelihood of a bear breeding.
  private static double PROBABILIDADE_REPRODUCAO = 0.02;
  // The maximum number of births.
  private static int MAX_NASCIMENTO = 1;
  // The food value of a single rabbit and a single fox. In effect, this is the
  // number of steps a bear can go before it has to eat again.
  private static final int NUM_PASSOS_REPRODUZIR = 9;
  private static final int VALOR_ALIMENTO_RAPOSA = 18;

  
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

  
  @Override
  public void act(List<Personagem> novoUrsos) {
    envelhecer();
    aumentarFome();
    if (ehVivo()) {
      darHaLuz(novoUrsos);
      // Move towards a source of food if found.
      Localizacao novaLocalizacao = procuraComida();
      if (novaLocalizacao == null) {
        // No food found - try to move to a free location.
        novaLocalizacao = getCampo().LocalizacaoAdjLivre(getLocalizacao());
      }
      // See if it was possible to move.
      if (novaLocalizacao != null) {
        setLocalizacao(novaLocalizacao);
      } else {
        // Overcrowding.
        matar();
      }
    }
  }

 
  @Override
  protected int idadeMaxima() {
    return IDADE_MAXIMA;
  }

   
  private void aumentarFome() {
    setNivelComida(getNivelComida() - 1);
    if (getNivelComida() <= 0) {
      matar();
    }
  }

 
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

  
  private void darHaLuz(List<Personagem> novosUrsos) {
    // New bears are born into adjacent locations.
    // Get a list of adjacent free locations.
    Campo campo = getCampo();
    List<Localizacao> livre = campo.getLocalizacoesAdjLivres(getLocalizacao());
    int births = procriar();
    for (int b = 0; b < births && livre.size() > 0; b++) {
      Localizacao loc = livre.remove(0);
      Urso filhote = new Urso(false, campo, loc);
      novosUrsos.add(filhote);
    }
  }

  
  @Override
  protected boolean podeProcriar() {
    return getIdade() >= getIdadeReproduzir();
  }

  protected int getIdadeReproduzir() {
    return IDADE_REPRODUCAO;
  }

  
  @Override
  protected int getNumMaxNascimento() {
    return MAX_NASCIMENTO;
  }

 
  @Override
  protected double getProbabilidadeProcriar() {
    return PROBABILIDADE_REPRODUCAO;
  }

 
  public static void setIdadeReproducao(int novaIdadeReproducao) {
    IDADE_REPRODUCAO = novaIdadeReproducao;
  }

  
  public static void setIdadeMaxima(int novaIdadeMaxima) {
    IDADE_MAXIMA = novaIdadeMaxima;
  }

  
  public static void setProbReproducao(double novaProbReproducao) {
    PROBABILIDADE_REPRODUCAO = novaProbReproducao;
  }

 
  public static void setMaxNascimento(int maxNascimento) {
    MAX_NASCIMENTO = maxNascimento;
  }
}
