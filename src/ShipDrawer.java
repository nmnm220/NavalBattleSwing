import javax.swing.*;
import java.awt.*;

public class ShipDrawer extends JPanel {
    int shipNum;

    ShipDrawer(Ship ship, int shipNum) {
        this.ship = ship;
        this.shipNum = shipNum;
    }

    Ship ship;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        if (ship != null) {
            for (int i = 0; i < ship.getLength(); i++) {
                g2.setColor(Color.GRAY);
                g2.fillRect(PointCell.cellSizeX * i, PointCell.cellSizeY, PointCell.cellSizeX, PointCell.cellSizeY);
                g2.setColor(Color.BLACK);
                g2.drawRect(PointCell.cellSizeX * i, PointCell.cellSizeY, PointCell.cellSizeX, PointCell.cellSizeY);
            }
            g2.drawString("Ship: " + shipNum, PointCell.cellSizeX, PointCell.cellSizeY * 3);
        } else
            g2.drawString("All ships are placed", PointCell.cellSizeX, PointCell.cellSizeY * 3);
    }

    public void drawShip(Ship ship, int shipNum) {
        this.ship = ship;
        this.shipNum = shipNum;
        repaint();
    }

}
