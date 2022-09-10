import javax.swing.*;
import java.awt.*;

public class Ship {
    int length;
    PointCell[] shipCells;
    boolean isHorizontal;
    Point position;
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
    public int getPosX()
    {
        return position.x;
    }
    public int getPosY()
    {
        return position.y;
    }
    public Point setPosition(Point newPoint)
    {
        position = newPoint;
        return position;
    }
    public int getLength()
    {
        return length;
    }
}
