import Client.EnumCellStates;
import Client.PlayerBoard;
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
        testPlayerBoard.placeShip(4,0,0,true);
    }

    @Test
    void should_successfullyAssertAllProhibitedFields_WhenPlacedAShip() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        testPlayerBoard.placeShip(3,1,1,true);
        for (int a = 0; a <= 4; a++) {
            for (int b = 0; b <= 2; b++) {
                if (b == 1 && a >= 1 && a <= 3) {
                } else
                    assertEquals(EnumCellStates.PROHIBITED, testPlayerBoard.getBoardCell(b,a).getState());
            }
        }
    }

    @Test
    void should_ReturnOne_WhenHitAShipButNotSank() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        testPlayerBoard.placeShip(3,0,0,true);
        assertEquals(1, testPlayerBoard.hit(0, 2));
    }

    @Test
    void should_ReturnTwo_WhenSankAShip() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        testPlayerBoard.placeShip(1,0,0,true);
        assertEquals(2, testPlayerBoard.hit(0, 0));
    }

    @Test
    void should_SinkAllAssociatedCells_whenSankAShip() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        testPlayerBoard.placeShip(3,0,0,true);
        testPlayerBoard.hit(0, 0);
        testPlayerBoard.hit(0, 1);
        testPlayerBoard.hit(0, 2);
        assertEquals(EnumCellStates.SANK, testPlayerBoard.getBoardCell(0,0).getState());
        assertEquals(EnumCellStates.SANK, testPlayerBoard.getBoardCell(0,1).getState());
        assertEquals(EnumCellStates.SANK, testPlayerBoard.getBoardCell(0,1).getState());
    }

    @Test
    void should_NotFindAnyProhibitedCells_whenChangeAllProhibitedToBlank() {
        PlayerBoard testPlayerBoard = new PlayerBoard();
        testPlayerBoard.placeShip(1,1,1,true);
        testPlayerBoard.changeAllProhibitedCellsToBlank();
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                if (a != 1 || b != 1)
                    assertEquals(EnumCellStates.BLANK, testPlayerBoard.getBoardCell(a,b).getState());
            }
        }
    }


}
