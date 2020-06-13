package client;

public class EnemyBoard extends Board {

    public void setBoardCellState(int x, int y, EnumCellStates state) {
        board[x][y].setState(state);
    }

    public EnumCellStates getCellState(int x, int y)
    {
        return board[x][y].getState();
    }

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

    public int getAmountShipsOfType(int i)
    {
        return shipCounter[i];
    }
    public EnemyBoard()
    {
        super();
        for(int i = 0; i < 4; i++)
            shipCounter[i] = 4 - i;
    }
}
