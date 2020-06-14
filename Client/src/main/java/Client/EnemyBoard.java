package Client;

/**
 * The type Enemy board.
 */
public class EnemyBoard extends Board {

    /**
     * Sets board cell state.
     *
     * @param x     the x
     * @param y     the y
     * @param state the state
     */
    public void setBoardCellState(int x, int y, EnumCellStates state) {
        board[x][y].setState(state);
    }

    /**
     * Gets cell state.
     *
     * @param x the x
     * @param y the y
     * @return the cell state
     */
    public EnumCellStates getCellState(int x, int y)
    {
        return board[x][y].getState();
    }

    /**
     * Sink ship.
     *
     * @param x          the x
     * @param y          the y
     * @param isVertical the is vertical
     * @param length     the length
     */
    public void sinkShip(int x, int y, boolean isVertical, int length)
    {
        if(isVertical)
        {
            for(int i = y; i < y + length; i++)
                setBoardCellState(x, i, EnumCellStates.SANK);
        }
        else
        {
            for(int i = x; i < x + length; i++)
                setBoardCellState(i, y, EnumCellStates.SANK);
        }
        shipCounter[length - 1]--;
    }

    /**
     * Gets amount ships of type.
     *
     * @param i the
     * @return the amount ships of type
     */
    public int getAmountShipsOfType(int i)
    {
        return shipCounter[i];
    }

    /**
     * Instantiates a new Enemy board.
     */
    public EnemyBoard()
    {
        super();
        for(int i = 0; i < 4; i++)
            shipCounter[i] = 4 - i;
    }
}
