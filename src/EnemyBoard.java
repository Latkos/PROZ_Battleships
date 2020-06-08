public class EnemyBoard extends Board {
    public void setBoardCellState(int x, int y, EnumCellStates state) {
        board[x][y].setState(state);
    }


}
