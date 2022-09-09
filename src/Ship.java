import javax.swing.*;
import java.awt.*;

public class Ship extends JPanel {
    int length;
    PointCell[] shipCells;
    boolean isHorizontal;
    Point position;
    Ship (int length, boolean isHorizontal, Point position)
    {
        shipCells = new PointCell[length];
        setLayout(new GridLayout(1, length, 0, 0));
        setSize(50,50);
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
    public int getLength()
    {
        return length;
    }
}
