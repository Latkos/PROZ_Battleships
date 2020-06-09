import java.util.ArrayList;

public class Board {
    public Cell[][] board; //to chyba powinno byc public
    public int[] shipCounter;
    public Board() {
        board = new Cell[10][10];
        assignCoordinatesToCells();
        shipCounter = new int[4];
    }

    private void assignCoordinatesToCells() {
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                board[a][b]=new Cell();
                board[a][b].setX(a);
                board[a][b].setY(b);
            }
        }
    }

}
