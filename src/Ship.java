import java.awt.*;
import java.util.ArrayList;

public class Ship {

    private boolean isPlaced = false;
    public Cell beginning; //mozliwe ze potem zmienie to public, zobaczymy
    public Cell end;
    public int getLength() {
        return length;
    }

    private int length;
    private boolean isVertical;

    public int getCellsLeft() {
        return cellsLeft;
    }

    public int decrementCellsLeft(){
        cellsLeft--;
        return cellsLeft;
    }

    int cellsLeft;

    public boolean isVertical() {
        return isVertical;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public void setVerticalityAndAdjustEnd(boolean vertical) {
        isVertical = vertical;
        if (isVertical) {
            end.setX(beginning.getX() + length - 1);
            end.setY(beginning.getY());
        } else {
            end.setX(beginning.getX());
            end.setY(beginning.getY() + length - 1);
        }
    }

    public Ship(Cell beginning, int length) {
        this.beginning = beginning;
        this.length = length;
        setVerticalityAndAdjustEnd(true);
        cellsLeft=length;
    }
}
