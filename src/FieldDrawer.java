import javax.swing.*;
import java.awt.*;

public class FieldDrawer extends JPanel {
    PointCell[][] field;
    FieldDrawer(PointCell[][] field)
    {
        this.field = field;
        setBackground(Color.PINK);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                switch (field[j][i].cellState) {
                    case hit:
                        g2.setColor(Color.LIGHT_GRAY);
                        g2.fillRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
                        g2.setColor(Color.RED);
                        g2.drawLine(0, 0, PointCell.cellSizeX, PointCell.cellSizeY);
                        g2.drawLine(PointCell.cellSizeX, 0, 0, PointCell.cellSizeY);
                        break;
                    case miss:
                        g2.setColor(Color.BLUE);
                        g2.fillRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
                        g2.setColor(Color.RED);
                        g2.fillOval(PointCell.cellSizeX / 3, PointCell.cellSizeY / 3, PointCell.cellSizeX / 2, PointCell.cellSizeY / 2);
                        break;
                    case ship:
                        g2.setColor(Color.GRAY);
                        g2.fillRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
                        break;
                    case shipSelected:
                        g2.setColor(Color.LIGHT_GRAY);
                        g2.fillRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
                        break;
                    case water:
                        g2.setColor(new Color(50, 50, 255));
                        g2.fillRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
                        break;
                    case waterSelected:
                        g2.setColor(new Color(100, 100, 255));
                        g2.fillRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
                        break;
                }
                g2.setColor(Color.BLACK);
                g2.drawRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
            }
        }
    }
}
