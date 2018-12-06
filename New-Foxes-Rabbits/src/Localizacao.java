/**
 * Classe para determinar localização
 * @author Anderson, Isabela, James
 */
public class Localizacao {
    // Linhas e colunas.
    private final int row;
    private final int col;
   
    /**
     * Criação de uma localização que recebe a linha e coluna
     * @param row
     * @param col 
     */
    public Localizacao(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
   /**
    * Verifica se a localização adicionada é a igual 
    * @param obj
    * @return valor booleano
    */
    public boolean equals(Object obj)
    {
        if(obj instanceof Localizacao) {
            Localizacao other = (Localizacao) obj;
            return row == other.getRow() && col == other.getCol();
        }
        else {
            return false;
        }
    }
    
    /**
     * Formato linha, coluna
     * @return 
     */
    @Override
    public String toString()
    {
        return row + "," + col;
    }
    
    /**
     * Retorna o hashcode
     * @return (row << 16) + col
     */
    @Override
    public int hashCode()
    {
        return (row << 16) + col;
    }
    
    /**
     * Retorna a linha
     * @return row
     */
    public int getRow()
    {
        return row;
    }
   
    /**
     * Retorna a coluna
     * @return col
     */
    public int getCol()
    {
        return col;
    }
    
}
