package client;

public class Cell {

    private EnumCellStates state;
    private int x;
    private int y;
    private Ship alignedShip;

    public Ship getAlignedShip() {
        return alignedShip;
    }

    public void setAlignedShip(Ship alignedShip) {
        this.alignedShip = alignedShip;
    }

    public EnumCellStates getState() {
        return state;
    }

    public void setState(EnumCellStates state) {
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        state = EnumCellStates.BLANK;
    }

}
