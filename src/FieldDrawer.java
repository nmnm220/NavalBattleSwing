import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class FieldDrawer extends JPanel implements MouseMotionListener, MouseListener {
    PointCell[][] field;
    int mouseX = 0;
    int mouseY = 0;
    FieldDrawer(PointCell[][] field)
    {
        addMouseMotionListener(this);
        addMouseListener(this);
        this.field = field;
        setBackground(Color.PINK);
    }
    private PointCell onCell (MouseEvent e)
    {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (((e.getX() >= i * PointCell.cellSizeX) & (e.getX() <= i * PointCell.cellSizeX + PointCell.cellSizeX)) &
                        ((e.getY() >= j * PointCell.cellSizeY) & (e.getY() <= j * PointCell.cellSizeY + PointCell.cellSizeY)))
                    return field[j][i];
            }
        return null;
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
                        g2.setColor(new Color(80, 50, 255));
                        g2.fillRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
                        g2.setColor(Color.RED);
                        g2.fillOval(PointCell.cellSizeX*i + PointCell.cellSizeX/3, PointCell.cellSizeY*j+ PointCell.cellSizeY/3, PointCell.cellSizeX / 2, PointCell.cellSizeY / 2);
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
                        g2.setColor(new Color(80, 50, 255));
                        g2.fillRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
                        break;
                    case waterSelected:
                        g2.setColor(new Color(100, 100, 255));
                        g2.fillRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
                        break;
                }
                g2.setColor(Color.BLACK);
                g2.drawRect(PointCell.cellSizeX*i, PointCell.cellSizeY*j, PointCell.cellSizeX, PointCell.cellSizeY);
                g2.drawString(String.valueOf(mouseX) + " " + String.valueOf(mouseY),100, 100);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        PointCell currentCell = onCell(e);
        if ((currentCell != null) && ((currentCell.cellState == PointCell.state.water)))
            currentCell.cellState = PointCell.state.waterSelected;
        else if ((onCell(e) != null) && (currentCell.cellState == PointCell.state.waterSelected))
            currentCell.cellState = PointCell.state.water;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PointCell currentCell = onCell(e);
        if ((currentCell != null) && (currentCell.cellState == PointCell.state.waterSelected))
            currentCell.cellState = PointCell.state.miss;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
