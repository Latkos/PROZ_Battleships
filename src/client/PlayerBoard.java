package client;

import java.util.ArrayList;

public class PlayerBoard extends Board {
    ArrayList<Ship> shipList;

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

    private Ship getShipToPlace(int length)
    {
        for (Ship ship : shipList) {
            if (!ship.isPlaced() && ship.getLength() == length) {
                return ship;
            }
        }
        return null;
    }

    public boolean placeShip(int length, int x, int y, boolean isVertical) { //x -> column, y -> row
        Ship ship = getShipToPlace(length);

        if(ship == null) //przypadek, gdyby funkcja nie zwrocila zadnego statku
            return false;

        if (!changeCellStatesToShip(ship, x, y, isVertical)) return false;
        updateProhibitedFields();
        for(int a = 0; a < 10; a++)
        {
            for(int b = 0; b < 10; b++)
                System.out.print(board[a][b].getState() + " ");
            System.out.println();
        }
        ship.setPlaced(true);
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

    private boolean changeCellStatesToShip(Ship ship, int x, int y, boolean isVertical) {
        //int startX = ship.beginning.getX();
        //int startY = ship.beginning.getY();
        int length = ship.getLength();
        ship.setVertical(isVertical);
        if (isVertical) {
            System.out.println("cosik");
            for (int a = y; a < y + length; a++)
                if (a > 9 || board[x][a].getState() != EnumCellStates.BLANK)
                    return false;

            for (int a = y; a < y + length; a++) {
                //System.out.println(a);
                board[x][a].setState(EnumCellStates.SHIP);
                board[x][a].setAlignedShip(ship);
            }
        }
        else
        {
            for (int a = x; a < x + length; a++)
            {
                if (a > 9 || board[a][y].getState() != EnumCellStates.BLANK)
                    return false;
            }

            for (int a = x; a < x+length; a++) {
                board[a][y].setState(EnumCellStates.SHIP);
                board[a][y].setAlignedShip(ship);
            }
        }
        ship.setBeginning(board[x][y]);

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

    /*
    i = 0 -> single-masted
    i = 1 -> two-masted
    i = 2 -> three-masted
    i = 3 -> four-masted
 */
    public int getAmountShipsOfType(int i)
    {
        return shipCounter[i];
    }

    public EnumCellStates getCellState(int x, int y)
    {
        return board[x][y].getState();
    }
    public PlayerBoard()
    {
        super();
        shipList = new ArrayList<>();
        for(int i = 0; i < 4; i++)
        {
            shipList.add(new Ship(1));
            if(i < 3)
                shipList.add(new Ship(2));
            if(i < 2)
                shipList.add(new Ship(3));
            if(i == 0)
                shipList.add(new Ship(4));
        }
    }
}
