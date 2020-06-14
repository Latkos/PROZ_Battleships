package Client;

import java.util.ArrayList;

/**
 * The type Player board.
 */
public class PlayerBoard extends Board {
    /**
     * The Ship list.
     */
    ArrayList<Ship> shipList;

    /**
     * Get board cell.
     *
     * @param x the x
     * @param y the y
     * @return the cell
     */
    public Cell getBoardCell(int x, int y){
        return board[x][y];
    }

    /**
     * Get boolean - whether all ships are sunk.
     *
     * @return the boolean
     */
    public boolean allShipsAreSunk() {
        for (int a = 0; a < 4; a++) {
            if (shipCounter[a] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Hit - method which checks whether the ship on the cell(x,y) was hit or not.
     *
     * @param x the x
     * @param y the y
     * @return the int -> 0 - the ship wasn't hit | 1 - the ship was hit | 2 - the ship was hit and sank
     */
    public int hit(int x, int y) {
        if (board[x][y].getState() != EnumCellStates.SHIP) {
            board[x][y].setState(EnumCellStates.MISSED);
            return 0; //not hit
        }
        board[x][y].setState(EnumCellStates.SHOT);
        Ship ship = board[x][y].getAlignedShip();
        ship.decrementCellsLeft();
        if (ship.getCellsLeft() == 0) {
            shipCounter[ship.getLength() - 1]--;
            sinkAllShipCells(ship);
            return 2; //sank
        }
        return 1; //hit but not sank
    }

    private Ship getShipToPlace(int length) {
        for (Ship ship : shipList) {
            if (!ship.isPlaced() && ship.getLength() == length) {
                return ship;
            }
        }
        return null;
    }

    /**
     * Place ship - return the result of the operation.
     *
     * @param length     the length
     * @param x          the x
     * @param y          the y
     * @param isVertical the is vertical
     * @return the boolean
     */
    public boolean placeShip(int length, int x, int y, boolean isVertical) {
        Ship ship = getShipToPlace(length);

        if (ship == null) //przypadek, gdyby funkcja nie zwrocila zadnego statku
            return false;

        if (!changeCellStatesToShip(ship, x, y, isVertical)) return false;
        updateProhibitedFields();
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
        int length = ship.getLength();
        ship.setVertical(isVertical);
        if (isVertical) {
            for (int a = y; a < y + length; a++)
                if (a > 9 || board[x][a].getState() != EnumCellStates.BLANK)
                    return false;

            for (int a = y; a < y + length; a++) {
                board[x][a].setState(EnumCellStates.SHIP);
                board[x][a].setAlignedShip(ship);
            }
        } else {
            for (int a = x; a < x + length; a++) {
                if (a > 9 || board[a][y].getState() != EnumCellStates.BLANK)
                    return false;
            }

            for (int a = x; a < x + length; a++) {
                board[a][y].setState(EnumCellStates.SHIP);
                board[a][y].setAlignedShip(ship);
            }
        }
        ship.setBeginning(board[x][y]);
        return true;
    }

    /**
     * Change all prohibited cells to blank.
     */
    public void changeAllProhibitedCellsToBlank() {
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                if (board[a][b].getState() == EnumCellStates.PROHIBITED) {
                    board[a][b].setState(EnumCellStates.BLANK);
                }
            }
        }
    }

    /**
     * Update prohibited fields.
     */
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

    /**
     * Gets amount ships of type.
     *
     * @param i -> 0 for single-masted ships | 1 for two-masted ships | 2 for three-masted ships | 3 for four-masted ships
     * @return the amount ships of type
     */
/*
    i = 0 -> single-masted
    i = 1 -> two-masted
    i = 2 -> three-masted
    i = 3 -> four-masted
 */
    public int getAmountShipsOfType(int i) {
        return shipCounter[i];
    }

    /**
     * Gets cell state.
     *
     * @param x the x
     * @param y the y
     * @return the cell state
     */
    public EnumCellStates getCellState(int x, int y) {
        return board[x][y].getState();
    }

    /**
     * Gets ship information - the method returns basic parameters of ship in a string.
     *
     * @param x the x
     * @param y the y
     * @return the ship information
     */
    public String getShipInformation(int x, int y) {
        Ship ship = board[x][y].getAlignedShip();
        x = ship.getBeginning().getX();
        y = ship.getBeginning().getY();
        int isV = ship.isVertical() ? 1 : 0;
        int length = ship.getLength();
        int msgCode = 10000;
        msgCode += x * 1000 + y * 100 + isV * 10 + length;
        return Integer.toString(msgCode);
    }

    /**
     * Instantiates a new Player board.
     */
    public PlayerBoard() {
        super();
        shipList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            shipList.add(new Ship(1));
            if (i < 3)
                shipList.add(new Ship(2));
            if (i < 2)
                shipList.add(new Ship(3));
            if (i == 0)
                shipList.add(new Ship(4));
        }
    }
}
