import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class FieldDrawer extends JPanel implements MouseMotionListener, MouseListener {
    PointCell[][] field;
    ArrayList<Ship> ships;
    int mouseX;
    int mouseY;
    boolean shipPlacement = true;
    FieldDrawer(PointCell[][] field, ArrayList<Ship> ships)
    {
        addMouseMotionListener(this);
        addMouseListener(this);
        this.field = field;
        this.ships = ships;
        setBackground(Color.PINK);
    }
    private void setCellSelected(MouseEvent e) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (((e.getX() >= i * PointCell.cellSizeX) & (e.getX() <= i * PointCell.cellSizeX + PointCell.cellSizeX)) &
                        ((e.getY() >= j * PointCell.cellSizeY) & (e.getY() <= j * PointCell.cellSizeY + PointCell.cellSizeY))) {
                    if (field[j][i].cellState == PointCell.state.water) {
                        field[j][i].setSelected(true);
                        field[j][i].cellState = PointCell.state.waterSelected;
                    }
                }
                else if (field[j][i].cellState == PointCell.state.waterSelected)
                {
                    field[j][i].setSelected(false);
                    field[j][i].cellState = PointCell.state.water;
                }
            }

        /*
        int x = e.getX() / PointCell.cellSizeX;
        int y = e.getY() / PointCell.cellSizeY;
        if ((x < 10) & (y < 10)) {
            if (field[y][x].cellState == PointCell.state.water) {
                field[y][x].setSelected(true);
                field[y][x].cellState = PointCell.state.waterSelected;
            } else if (field[y][x].cellState == PointCell.state.waterSelected) {
                field[y][x].setSelected(false);
                field[y][x].cellState = PointCell.state.water;
            }
        }

         */
    }
    private void setShipSelected(MouseEvent e)
    {
        for (Ship item:
             ships) {
            if ((item.isHorizontal) && (((e.getX() / PointCell.cellSizeX)  == item.getPosX()) && ((e.getY() / PointCell.cellSizeY)  == item.getPosY())))
            {
                for (int i = 0; i < item.length; i++)
                    field[item.getPosY()][item.getPosX()+i].cellState = PointCell.state.shipSelected;
            }
            else if ((!item.isHorizontal) && (((e.getX() / PointCell.cellSizeX)  == item.getPosX()) && ((e.getY() / PointCell.cellSizeY)  == item.getPosY())))
            {
                for (int i = 0; i < item.length; i++)
                    field[item.getPosY()+i][item.getPosX()].cellState = PointCell.state.shipSelected;
            }
            else {
                for (int i = 0; i < item.length; i++)
                    field[item.getPosY()][item.getPosX() + i].cellState = PointCell.state.ship;
            }
        }
    }
    private void setMiss()
    {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
            {
                if (field[j][i].getSelected())
                    field[j][i].cellState = PointCell.state.miss;
            }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                switch (field[j][i].cellState) {
                    case hit -> {
                        g2.setColor(Color.LIGHT_GRAY);
                        g2.fillRect(PointCell.cellSizeX * i, PointCell.cellSizeY * j, PointCell.cellSizeX, PointCell.cellSizeY);
                        g2.setColor(Color.RED);
                        g2.drawLine(0, 0, PointCell.cellSizeX, PointCell.cellSizeY);
                        g2.drawLine(PointCell.cellSizeX, 0, 0, PointCell.cellSizeY);
                    }
                    case miss -> {
                        g2.setColor(new Color(80, 50, 255));
                        g2.fillRect(PointCell.cellSizeX * i, PointCell.cellSizeY * j, PointCell.cellSizeX, PointCell.cellSizeY);
                        g2.setColor(Color.RED);
                        g2.fillOval(PointCell.cellSizeX * i + PointCell.cellSizeX / 3, PointCell.cellSizeY * j + PointCell.cellSizeY / 3, PointCell.cellSizeX / 2, PointCell.cellSizeY / 2);
                    }
                    case ship -> {
                        g2.setColor(Color.GRAY);
                        g2.fillRect(PointCell.cellSizeX * i, PointCell.cellSizeY * j, PointCell.cellSizeX, PointCell.cellSizeY);
                    }
                    case shipSelected -> {
                        g2.setColor(Color.LIGHT_GRAY);
                        g2.fillRect(PointCell.cellSizeX * i, PointCell.cellSizeY * j, PointCell.cellSizeX, PointCell.cellSizeY);
                    }
                    case water -> {
                        g2.setColor(new Color(80, 50, 255));
                        g2.fillRect(PointCell.cellSizeX * i, PointCell.cellSizeY * j, PointCell.cellSizeX, PointCell.cellSizeY);
                    }
                    case waterSelected -> {
                        g2.setColor(new Color(100, 100, 255));
                        g2.fillRect(PointCell.cellSizeX * i, PointCell.cellSizeY * j, PointCell.cellSizeX, PointCell.cellSizeY);
                    }
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
        if (!shipPlacement)
            setCellSelected(e);
        else
        {
            setShipSelected(e);
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!shipPlacement)
            setMiss();
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
