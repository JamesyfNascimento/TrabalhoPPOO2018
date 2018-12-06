
import java.util.List;

/**
 * Interface para criar novos agentes que não são animais no campo no campo
 * @author Anderson, Isabela, James
 */
public interface Personagem 
{
        /**
         * Ações do personagem
         * @param newActors 
         */
	void act(List<Personagem> newActors);
        
        /**
         * Status de sua vida
         * @return true or false
         */
	boolean ehVivo();
   
        /**
         * Atribuir localização
         * @param newLocation 
         */
	void setLocalizacao(Localizacao newLocation);
  	
}
