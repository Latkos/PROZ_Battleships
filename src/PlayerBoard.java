import java.util.ArrayList;

public class PlayerBoard extends Board {
    //ArrayList<Ship> shipList = new ArrayList<Ship>();

    public boolean allShipsAreSunk() {
        for (int a = 0; a < 4; a++) {
            if (shipCounter[a] != 0) {
                return false;
            }
        }
        return true;
    }

    public int hit(int x, int y) {
        if (board[x][y].getState() != EnumCellStates.SHIP){
            board[x][y].setState(EnumCellStates.MISSED);
            return 0; //not hit
        }
        board[x][y].setState(EnumCellStates.SHOT);
        Ship ship = board[x][y].getAlignedShip();
        ship.decrementCellsLeft();
        if (ship.getCellsLeft() == 0) { //we just sank a shit
            shipCounter[ship.getLength() - 1]--;
            sinkAllShipCells(ship);
            return 2;
        }
        return 1; //hit but not sank
    }

    public boolean placeShip(Ship ship) {
        if (!changeCellStatesToShip(ship)) return false;
        updateProhibitedFields();
        ship.setPlaced(true);
        //it is probably useless
        //shipList.add(ship);
        shipCounter[ship.getLength() - 1]++;
        return true;
    }

    private void sinkAllShipCells(Ship ship) {
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                if (board[a][b].getAlignedShip() == ship)
                    board[a][b].setState(EnumCellStates.SANK);
            }
        }
    }

    private boolean changeCellStatesToShip(Ship ship) {
        int startX = ship.beginning.getX();
        int startY = ship.beginning.getY();
        int length=ship.getLength();
        if (ship.isVertical()) {
            for (int a = startX; a <startX+length; a++)
                if (board[a][startY].getState() == EnumCellStates.PROHIBITED) return false;
            for (int a = startX; a <startX+length; a++) {
                System.out.println(a);
                board[a][startY].setState(EnumCellStates.SHIP);
                board[a][startY].setAlignedShip(ship);
            }
        } else {
            for (int a = startY; a <startY+length; a++)
                if (board[startX][a].getState() == EnumCellStates.PROHIBITED) return false;
            for (int a = startY; a <startY+length; a++) {
                board[startX][a].setState(EnumCellStates.SHIP);
                board[startX][a].setAlignedShip(ship);
            }
        }
        return true;
    }

    public void changeAllProhibitedCellsToBlank() {
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                if (board[a][b].getState() == EnumCellStates.PROHIBITED) {
                    board[a][b].setState(EnumCellStates.BLANK);
                }
            }
        }
    }

    void updateProhibitedFields() {
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                if (board[a][b].getState() == EnumCellStates.SHIP) {
                    checkAndUpdateSurroundingCells(board[a][b]);
                }
            }
        }
    }

    private void checkAndUpdateSurroundingCells(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        for (int a = x - 1; a <= x + 1; a++) {
            for (int b = y - 1; b <= y + 1; b++) {
                prohibitCell(a, b);
            }
        }
    }

    private void prohibitCell(int x, int y) {
        if (x >= 0 && x <= 9 && y >= 0 && y <= 9)
            if (board[x][y].getState() == EnumCellStates.BLANK) board[x][y].setState(EnumCellStates.PROHIBITED);
    }
}
