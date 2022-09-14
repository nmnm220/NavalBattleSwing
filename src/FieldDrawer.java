import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class FieldDrawer extends JPanel implements MouseMotionListener, MouseListener {
    PointCell[][] field;
    ArrayList<Ship> ships;
    Point prevPos;
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
    private Point setPosition(Point point, Ship ship)
    {
        //int coord;
            if (point.x + ship.getLength() > 10)
                point.x = 10 - ship.getLength();
            if (point.y >= 10)
                point.y = 9;
            if (point.x <= 0)
                point.x = 0;
            if (point.y <= 0)
                point.y = 0;
        return point;
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
            if ((item.isHorizontal) && ((((e.getX() / PointCell.cellSizeX)  >= item.getPosition().x)
                    && (e.getX() / PointCell.cellSizeX)  < item.getPosition().x+ item.getLength()))
                    && ((e.getY() / PointCell.cellSizeY)  == item.getPosition().y))
            {
                for (int i = 0; i < item.length; i++) {
                    field[item.getPosition().y][item.getPosition().x + i].cellState = PointCell.state.shipSelected;
                    item.setSelected(true);
                }
            }
            else if ((!item.isHorizontal) && ((((e.getX() / PointCell.cellSizeY)  >= item.getPosition().y)
                    && (e.getX() / PointCell.cellSizeY)  < item.getPosition().y+ item.getLength()))
                    && ((e.getY() / PointCell.cellSizeX)  == item.getPosition().x))
            {
                for (int i = 0; i < item.length; i++) {
                    field[item.getPosition().y + i][item.getPosition().x].cellState = PointCell.state.shipSelected;
                    item.setSelected(true);
                }
            }
            else {
                for (int i = 0; i < item.length; i++) {
                    field[item.getPosition().y][item.getPosition().x + i].cellState = PointCell.state.ship;
                    item.setSelected(false);
                }
            }
        }
    }
    private void moveShip(MouseEvent e)
    {
        for (int i = 0; i < ships.size(); i++)
        {
            if (ships.get(i).getSelected()) {
                prevPos = ships.get(i).getPosition();
                Point curPos = new Point(e.getX() / PointCell.cellSizeX, e.getY() / PointCell.cellSizeY);
                curPos = setPosition(curPos, ships.get(i));
                if (ships.get(i).isHorizontal)
                {
                    for (int j = 0; j < ships.get(i).getLength(); j++)
                        field[ships.get(i).getPosition().y][ships.get(i).getPosition().x+j].setCellState(PointCell.state.water);
                }
                else
                {
                    for (int j = 0; j < ships.get(i).getLength(); j++)
                        field[ships.get(i).getPosition().y+j][ships.get(i).getPosition().x].setCellState(PointCell.state.water);
                }
                ships.get(i).getPosition().translate(curPos.x - prevPos.x, curPos.y - prevPos.y);
                prevPos = curPos;
            }
            MainWindow.updateShipPos(ships, field);
            repaint();
        }
    }
    private void checkCollision()
    {
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
        moveShip(e);
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        if (shipPlacement)
            setShipSelected(e);

        else
        {
            setCellSelected(e);
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
