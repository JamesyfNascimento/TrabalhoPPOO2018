import java.util.List;
import java.util.Iterator;

/**
 * Uma classe que representa as características compartilhadas dos caçadores.
 * Um modelo simples de um caçador.
 * Caçadores se movem e disparam.
*/
public class Cacador implements Personagem
{
    // O campo do cacador.
    private final Campo campo;
    // A posicao do caçador no campo.
    private Localizacao localizacao;
    // Se o cacador esta vivo ou nao.
    private boolean ehVivo;
    
    /**
      * Construtor que recebe os parametros do campo do cacador e a sua 
      * localizacao dentro do campo.
      * @param campo O campo atualmente ocupado.
      * @param novaLocalizacao A localizacao dentro do campo.
     */
    public Cacador(Campo campo, Localizacao novaLocalizacao)
    {
        this.campo = campo;
        setLocalizacao(novaLocalizacao);
        ehVivo = true;
    }
   
    @Override
    public void act(List<Personagem> novosCacadores)
    {
    	// Mova-se em direcao a uma fonte de comida, se encontrada.
        Localizacao novaLocalizacao = encontrarAlvo();
        if(novaLocalizacao == null) { 
            // Nenhum alimento encontrado - tente mover para um local livre.
            novaLocalizacao = getCampo().LocalizacaoAdjLivre(getLocalizacao());
        }
        // Veja se foi possivel se mover.
        if(novaLocalizacao != null) {
            setLocalizacao(novaLocalizacao);
        }else{
            ehVivo = false;
        }
    }
    
    /**
      * Procurar por um animal adjacente ao local atual.
      * Apenas o primeiro animal vivo e o alvo.
      * @return onde Localizacao do animal encontrado, ou nulo.
     */
    private Localizacao encontrarAlvo()
    {
        Campo campo = getCampo();
        List<Localizacao> adjacente = campo.localizacoesAdjacentes(getLocalizacao());
        Iterator<Localizacao> it = adjacente.iterator();
        while(it.hasNext()) {
            Localizacao onde = it.next();
            Object animal = campo.getPersonagem(onde);
            if(animal instanceof Coelho) 
            {
                Coelho coelho = (Coelho) animal;
                if(coelho.ehVivo()) 
                { 
                    coelho.matar();
                    return onde;
                }
            }
            else if (animal instanceof Raposa)
            {
                Raposa raposa = (Raposa) animal;
                if(raposa.ehVivo()) 
                { 
                    raposa.matar();
                    return onde;
                }
            	
            }
            else if (animal instanceof Urso)
            {
                Urso urso = (Urso) animal;
                if(urso.ehVivo()) 
                { 
                    urso.matar();
                    return onde;
                }
            }
        }
        return null;
    }
    
    /**
      * Retorna a localização do caçador.
      * @return localizacao A localizacao do cacador.
     */
    public Localizacao getLocalizacao()
    {
        return localizacao;
    }

    /**
      *Coloque o caçador no novo local no campo dado.
      * @param novaLocalizacao A nova localização do caçador.
     */
    @Override
    public void setLocalizacao(Localizacao novaLocalizacao)
    {
        if(localizacao != null) {
            campo.limpar(localizacao);
        }
        localizacao = novaLocalizacao;
        campo.lugar(this, novaLocalizacao);
    }
    
    /**
      * Retorna o campo do caçador.
      * @return campo O campo do caçador.
     */
    public Campo getCampo()
    {
        return campo;
    }   
    
    /**
     * Retorna o status de vida do personagem.
     * @return true se o personagem estiver vivo
     */
    @Override
    public boolean ehVivo() {
        return ehVivo;
    }
}
