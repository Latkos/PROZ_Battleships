package client;

public class Ship {

    private boolean isPlaced = false;
    public Cell beginning;
    private int length;
    private boolean isVertical;
    int cellsLeft;

    public int getLength() {
        return length;
    }

    public int getCellsLeft() {
        return cellsLeft;
    }

    public void decrementCellsLeft() {
        cellsLeft--;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public boolean isPlaced()
    {
        return isPlaced;
    }

    public void setVertical(boolean vertical){
        if (!isPlaced)
            this.isVertical=vertical;
    }

    public void setBeginning(Cell beginning)
    {
        this.beginning = beginning;
    }

    public Cell getBeginning()
    {
        return beginning;
    }

    public Ship(int length) {
        this.length=length;
        this.isVertical=true;
        cellsLeft=length;
    }

}
