import java.util.List;
import java.util.Iterator;

public class Cacador implements Personagem
{
    // The hunter's field.
    private Campo campo;
    // The hunter's position in the field.
    private Localizacao localizacao;
    
    private boolean ehVivo;
    
    
    public Cacador(Campo campo, Localizacao novaLocalizacao)
    {
        this.campo = campo;
        setLocalizacao(novaLocalizacao);
        ehVivo = true;
    }

   
    public void act(List<Personagem> novosCacadores)
    {
    	// Move towards a source of food if found.
        Localizacao novaLocalizacao = encontrarAlvo();
        if(novaLocalizacao == null) { 
            // No food found - try to move to a free location.
            novaLocalizacao = getCampo().LocalizacaoAdjLivre(getLocalizacao());
        }
        // See if it was possible to move.
        if(novaLocalizacao != null) {
            setLocalizacao(novaLocalizacao);
        }else{
            ehVivo = false;
        }

    }


   
    private Localizacao encontrarAlvo()
    {
        Campo campo = getCampo();
        List<Localizacao> adjacente = campo.localizacoesAdjacentes(getLocalizacao());
        Iterator<Localizacao> it = adjacente.iterator();
        while(it.hasNext()) {
            Localizacao Onde = it.next();
            Object animal = campo.getPersonagem(Onde);
            if(animal instanceof Coelho) 
            {
                Coelho coelho = (Coelho) animal;
                if(coelho.ehVivo()) 
                { 
                    coelho.matar();
                    return Onde;
                }
            }
            else if (animal instanceof Raposa)
            {
                Raposa raposa = (Raposa) animal;
                if(raposa.ehVivo()) 
                { 
                    raposa.matar();
                    return Onde;
                }
            	
            }
            else if (animal instanceof Urso)
            {
                Urso urso = (Urso) animal;
                if(urso.ehVivo()) 
                { 
                    urso.matar();
                    return Onde;
                }
            	
            }
        }
        return null;
    }
    
    
    public Localizacao getLocalizacao()
    {
        return localizacao;
    }
    
    
    public void setLocalizacao(Localizacao novaLocalizacao)
    {
        if(localizacao != null) {
            campo.limpar(localizacao);
        }
        localizacao = novaLocalizacao;
        campo.lugar(this, novaLocalizacao);
    }
    
    
    public Campo getCampo()
    {
        return campo;
    }   

    @Override
    public boolean ehVivo() {
        return ehVivo;
    }
}