import java.awt.*;

public class PointCell {

    public enum state
    {
        water,
        ship,
        miss,
        hit,
        shipSelected,
        waterSelected
    }
    state cellState;
    Point coordinate;
    private boolean isSelected = false;
    public static int cellSizeX = 30;
    public static int cellSizeY = 30;
    PointCell(state cellState, Point coordinate)
    {
        this.coordinate = coordinate;
        this.cellState = cellState;
    }
    public void setCellState(state state)
    {
        this.cellState = state;
    }
    public boolean getSelected() { return isSelected; }
    public void setSelected(boolean isSelected) { this.isSelected = isSelected;}
}
