package Client;

/**
 * The type Board.
 * It stores state of the whole group of cell on it
 */
public class Board {
    /**
     * The Board - array of Cell.
     */
    protected Cell[][] board;
    /**
     * The Ships counter.
     * Indexes and what they store:
     *  0 - the amount of single-masted ships
     *  1 - the amount of two-masted ships
     *  2 - the amount of three-masted ships
     *  3 - the amount of four-masted ships
     */
    protected int[] shipCounter;

    /**
     * Instantiates a new Board.
     */
    public Board() {
        board = new Cell[10][10];
        assignCoordinatesToCells();
        shipCounter = new int[4];
    }

    private void assignCoordinatesToCells() {
        for (int a = 0; a < 10; a++)
            for (int b = 0; b < 10; b++)
                board[a][b] = new Cell(a, b);
    }

}
