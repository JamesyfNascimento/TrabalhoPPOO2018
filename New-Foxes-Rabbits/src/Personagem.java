
import java.util.List;


public interface Personagem 
{
	

	void act(List<Personagem> newActors);
        
	boolean ehVivo();
   
	void setLocalizacao(Localizacao newLocation);
  	
}
