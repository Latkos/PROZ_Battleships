package client;

public class EnemyBoard extends Board {
    public void setBoardCellState(int x, int y, EnumCellStates state) {
        board[x][y].setState(state);
    }

    public EnumCellStates getCellState(int x, int y)
    {
        return board[x][y].getState();
    }

    public EnemyBoard()
    {
        super();
    }
}
