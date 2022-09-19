import java.awt.*;

public class Ship {
    int length;
    PointCell[] shipCells;
    boolean isHorizontal;
    boolean isSelected;
    private boolean isPlaced = false;
    private Point position;
    private Point initPosition;
    Ship (int length, boolean isHorizontal, Point position)
    {
        this.length = length;
        this.isHorizontal = isHorizontal;
        this.position = position;
        initPosition = new Point(position);
    }
    public void turn()
    {
        if (length > 1)
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
    public Point getInitPosition()
    {
        return initPosition;
    }
    public void setInitPosition(Point position)
    {
        initPosition = position;
    }
    public void setPlaced() {
        isPlaced = true;
    }
    public void reset(){isPlaced = false;}
    public boolean getPlaced()
    {
        return isPlaced;
    }
}
