import javax.swing.*;
import java.awt.*;

public class Ship {
    int length;
    PointCell[] shipCells;
    boolean isHorizontal;
    boolean isSelected;
    private Point position;
    Ship (int length, boolean isHorizontal, Point position)
    {
        shipCells = new PointCell[length];
        for (int i = 0; i < length; i++)
        {
            shipCells[i] = new PointCell(PointCell.state.ship);
        }
        this.length = length;
        this.isHorizontal = isHorizontal;
        this.position = position;
    }
    public void turn()
    {
        isHorizontal = !isHorizontal;
    }
    public Point getPosition()
    {
        return position;
    }
    public void setPosition(Point newPoint)
    {
        position = newPoint;
    }
    public boolean getSelected()
    {
        return isSelected;
    }
    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }
    public int getLength()
    {
        return length;
    }
}
