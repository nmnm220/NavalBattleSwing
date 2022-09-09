import javax.swing.*;
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
    private boolean isSelected = false;
    public static int cellSizeX = 25;
    public static int cellSizeY = 25;
    PointCell(state cellState)
    {
        this.cellState = cellState;
    }
    public void setCellState(state state)
    {
        this.cellState = state;
    }
    public boolean getSelected() { return isSelected; }
    public void setSelected(boolean isSelected) { this.isSelected = isSelected;}
}
