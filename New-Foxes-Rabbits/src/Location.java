/**
 *
 * @author isabela
 */
public class Location {
    // Linhas e colunas.
    private int row;
    private int col;

    /**
     * Representa linhas e colunas
     * @param row linha.
     * @param col coluna.
     */
    public Location(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Implement content equality.
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return row == other.getRow() && col == other.getCol();
        }
        else {
            return false;
        }
    }
    
    /**
     * Retona as "coordenadas" na forma linha, coluna (string)
     * @return Uma string representação uma localização.
     */
    public String toString()
    {
        return row + "," + col;
    }
    
    /**
     * 
     * Use the top 16 bits for the row value and the bottom for
     * the column. Except for very big grids, this should give a
     * unique hash code for each (row, col) pair.
     * @return A hashcode for the location.
     */
    public int hashCode()
    {
        return (row << 16) + col;
    }
    
    /**
     * @return a linha.
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * @return a coluna.
     */
    public int getCol()
    {
        return col;
    }
    
}
