import java.awt.*;

public class Ship {
    int length;
    private int hitPoints;
    boolean isHorizontal;
    boolean isSelected;
    private boolean isPlaced = false;
    private boolean alive = true;
    private Point position;
    private Point initPosition;

    Ship(int length, boolean isHorizontal, Point position) {
        this.length = length;
        this.isHorizontal = isHorizontal;
        this.position = position;
        initPosition = new Point(position);
        hitPoints = length;
    }

    public void turn() {
        if (length > 1)
            isHorizontal = !isHorizontal;
    }

    public void hit() {
        if (hitPoints > 0)
            hitPoints--;
        if (hitPoints <= 0)
            alive = false;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point newPoint) {
        position = newPoint;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getLength() {
        return length;
    }

    public Point getInitPosition() {
        return initPosition;
    }

    public void setInitPosition(Point position) {
        initPosition = position;
    }

    public void setPlaced() {
        isPlaced = true;
    }

    public void reset() {
        isPlaced = false;
    }

    public boolean getPlaced() {
        return isPlaced;
    }

    public boolean isAlive() {
        return alive;
    }
}
