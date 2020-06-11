package client;

public class Board {
    public Cell[][] board; //to chyba powinno byc public #nope w Javie trzeba starac sie, zeby wszystko bylo hermetyczne, a wiec konieczne sa funkcje do obslugie tego i zamiana na private
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
