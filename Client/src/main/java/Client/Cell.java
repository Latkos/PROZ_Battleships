package Client;

/**
 * The type Cell.
 */
public class Cell {

    private EnumCellStates state;
    private int x;
    private int y;
    private Ship alignedShip;

    /**
     * Gets aligned to ship.
     *
     * @return the aligned ship
     */
    public Ship getAlignedShip() {
        return alignedShip;
    }

    /**
     * Sets aligned ship.
     *
     * @param alignedShip the aligned ship
     */
    public void setAlignedShip(Ship alignedShip) {
        this.alignedShip = alignedShip;
    }

    /**
     * Gets state of the cell.
     *
     * @return the state
     */
    public EnumCellStates getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(EnumCellStates state) {
        this.state = state;
    }

    /**
     * Gets x - the coordinate on the game board.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y - the coordinate on the game board.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Instantiates a new Cell.
     *
     * @param x the x
     * @param y the y
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        state = EnumCellStates.BLANK;
    }

}
