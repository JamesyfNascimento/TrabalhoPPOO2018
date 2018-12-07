import java.util.List;

/**
 * Interface para criar novos agentes que nao sao animais no campo no campo
 * @author Anderson, Isabela, James
 */
public interface Personagem 
{
        /**
         * Acoes do personagem
         * @param newActors 
         */
	void act(List<Personagem> newActors);
        
        /**
         * Status de sua vida
         * @return true or false
         */
	boolean ehVivo();
   
        /**
         * Atribuir localizacao
         * @param newLocation 
         */
	void setLocalizacao(Localizacao newLocation);
  	
}
