package client;

public class Cell {

    private EnumCellStates state = EnumCellStates.BLANK;
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

    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



}
