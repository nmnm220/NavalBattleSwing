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
    Color cellColor = new Color(0,0,0);
    state cellState;
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
}
