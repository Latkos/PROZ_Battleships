/*import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlayerBoard {
    @Test
    void should_ReturnTrue_WhenTestedIfAllShipsAreSunkOnEmptyBoard() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        assertEquals(true, testPlayerBoard.allShipsAreSunk());
    }

    @Test
    void should_ReturnZero_WhenHitABlank() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        assertEquals(0, testPlayerBoard.hit(0, 0));
    }

    @Test
    void should_ReturnTrue_WhenShipSuccessfullyPlaced() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Ship testShip = new Ship(testPlayerBoard.board[0][0], 4);
        testPlayerBoard.placeShip(testShip);
    }

    @Test
    void should_successfullyAssertAllProhibitedFields_WhenPlacedAShip() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Ship testShip = new Ship(testPlayerBoard.board[1][1], 3);
        testPlayerBoard.placeShip(testShip);
        for (int a = 0; a <= 4; a++) {
            for (int b = 0; b <= 2; b++) {
                if (b == 1 && a >= 1 && a <= 3) {
                } else
                    assertEquals(EnumCellStates.PROHIBITED, testPlayerBoard.board[a][b].getState());
            }
        }
    }

    @Test
    void should_ReturnOne_WhenHitAShipButNotSank() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Ship testShip = new Ship(testPlayerBoard.board[0][0], 3);
        testPlayerBoard.placeShip(testShip);
        assertEquals(1, testPlayerBoard.hit(2, 0));
    }

    @Test
    void should_ReturnTwo_WhenSankAShip() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Ship testShip = new Ship(testPlayerBoard.board[0][0], 1);
        testPlayerBoard.placeShip(testShip);
        assertEquals(2, testPlayerBoard.hit(0, 0));
    }

    @Test
    void should_SinkAllAssociatedCells_whenSankAShip() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Ship testShip = new Ship(testPlayerBoard.board[0][0], 3);
        testPlayerBoard.placeShip(testShip);
        testPlayerBoard.hit(0, 0);
        testPlayerBoard.hit(1, 0);
        testPlayerBoard.hit(2, 0);
        assertEquals(EnumCellStates.SANK, testPlayerBoard.board[0][0].getState());
        assertEquals(EnumCellStates.SANK, testPlayerBoard.board[1][0].getState());
        assertEquals(EnumCellStates.SANK, testPlayerBoard.board[2][0].getState());
    }

    @Test
    void should_NotFindAnyProhibitedCells_whenChangeAllProhibitedToBlank() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        Ship testShip = new Ship(testPlayerBoard.board[1][1], 1);
        testPlayerBoard.placeShip(testShip);
        testPlayerBoard.changeAllProhibitedCellsToBlank();
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                if (a != 1 || b != 1)
                    assertEquals(EnumCellStates.BLANK, testPlayerBoard.board[a][b].getState());
            }
        }
    }


}
*/