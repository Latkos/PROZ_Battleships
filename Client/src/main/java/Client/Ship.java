package Client;

/**
 * The type Ship.
 */
public class Ship {

    private boolean isPlaced = false;
    /**
     * The Beginning cell of the ship.
     */
    public Cell beginning;
    private int length;
    private boolean isVertical;
    /**
     * The Cells left.
     */
    int cellsLeft;

    /**
     * Gets length.
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets cells left.
     *
     * @return the cells left
     */
    public int getCellsLeft() {
        return cellsLeft;
    }

    /**
     * Decrement cells left.
     */
    public void decrementCellsLeft() {
        cellsLeft--;
    }

    /**
     * Get isVertical boolean.
     *
     * @return the boolean
     */
    public boolean isVertical() {
        return isVertical;
    }

    /**
     * Sets placed boolean.
     *
     * @param placed the placed
     */
    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    /**
     * Get isPlaced boolean.
     *
     * @return the boolean
     */
    public boolean isPlaced()
    {
        return isPlaced;
    }

    /**
     * Set vertical - true if the ship is located vertically.
     *
     * @param vertical the vertical
     */
    public void setVertical(boolean vertical){
        if (!isPlaced)
            this.isVertical=vertical;
    }

    /**
     * Sets beginning cell.
     *
     * @param beginning the beginning
     */
    public void setBeginning(Cell beginning)
    {
        this.beginning = beginning;
    }

    /**
     * Gets beginning cell.
     *
     * @return the beginning
     */
    public Cell getBeginning()
    {
        return beginning;
    }

    /**
     * Instantiates a new Ship.
     *
     * @param length the length
     */
    public Ship(int length) {
        this.length=length;
        this.isVertical=true;
        cellsLeft=length;
    }

}
