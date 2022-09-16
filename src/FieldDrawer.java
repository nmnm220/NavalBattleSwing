import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class FieldDrawer extends JPanel implements MouseMotionListener, MouseListener {
    private PointCell[][] field;
    private ArrayList<Ship> ships;
    private Point prevPos;
    private int drawingOffsetX = 0;
    private int drawingOffsetY = 0;
    int debug = 0;
    boolean shipPlacement = true;

    FieldDrawer(PointCell[][] field, ArrayList<Ship> ships, int offsetX, int offsetY) {
        addMouseMotionListener(this);
        addMouseListener(this);
        drawingOffsetY = offsetX;
        drawingOffsetY = offsetY;
        this.field = field;
        this.ships = ships;
        setBackground(Color.PINK);
    }

    private Point setPosition(Point point, Ship ship) {
        if (ship.isHorizontal) {
            if (point.x + ship.getLength() > 10)
                point.x = 10 - ship.getLength();
            if (point.y >= 10)
                point.y = 9;
            if (point.x <= 0)
                point.x = 0;
            if (point.y <= 0)
                point.y = 0;
        } else {
            if (point.y + ship.getLength() > 10)
                point.y = 10 - ship.getLength();
            if (point.x >= 10)
                point.x = 9;
            if (point.y <= 0)
                point.y = 0;
            if (point.x <= 0)
                point.x = 0;
        }

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
                } else if (field[j][i].cellState == PointCell.state.waterSelected) {
                    field[j][i].setSelected(false);
                    field[j][i].cellState = PointCell.state.water;
                }
            }
    }

    private void setShipSelected(MouseEvent e) {
        for (Ship ship :
                ships) {
            if (((((e.getX() / PointCell.cellSizeX) >= ship.getPosition().x)
                    && (e.getX() / PointCell.cellSizeX) < ship.getPosition().x + ship.getLength()))
                    && ((e.getY() / PointCell.cellSizeY) == ship.getPosition().y)) {
                if (ship.isHorizontal) {
                    for (int i = 0; i < ship.length; i++) {
                        field[ship.getPosition().y][ship.getPosition().x + i].cellState = PointCell.state.shipSelected;
                        ship.setSelected(true);
                    }
                } else {
                    for (int i = 0; i < ship.length; i++) {
                        field[ship.getPosition().y + i][ship.getPosition().x].cellState = PointCell.state.shipSelected;
                        ship.setSelected(true);
                    }
                }
            } else {
                for (int i = 0; i < ship.length; i++) {
                    if (ship.isHorizontal) {
                        field[ship.getPosition().y][ship.getPosition().x + i].cellState = PointCell.state.ship;
                        ship.setSelected(false);
                    } else {
                        field[ship.getPosition().y + i][ship.getPosition().x].cellState = PointCell.state.ship;
                        ship.setSelected(false);
                    }
                }
            }
        }
    }

    private void turnShip() {
        for (Ship ship : ships) {
            if (ship.getSelected() & (ship.getPosition().x + ship.getLength() <= 10) & (ship.getPosition().y + ship.getLength() <= 10)) {
                ship.turn();
            }
        }
        MainWindow.updateShipPos(ships, field);
        repaint();
        if (checkCollision())
            turnShip();
    }

    private void moveShip(MouseEvent e) {
        for (Ship ship : ships) {
            if (ship.getSelected()) {
                prevPos = ship.getPosition();
                Point curPos = new Point(e.getX() / PointCell.cellSizeX, e.getY() / PointCell.cellSizeY);
                curPos = setPosition(curPos, ship);
                if (ship.isHorizontal) {
                    for (int j = 0; j < ship.getLength(); j++)
                        field[ship.getPosition().y][ship.getPosition().x + j].setCellState(PointCell.state.water);
                } else {
                    for (int j = 0; j < ship.getLength(); j++)
                        field[ship.getPosition().y + j][ship.getPosition().x].setCellState(PointCell.state.water);
                }
                ship.getPosition().translate(curPos.x - prevPos.x, curPos.y - prevPos.y);
                prevPos = curPos;
            }
        }
        MainWindow.updateShipPos(ships, field);
        repaint();
    }

    private boolean checkCollision() {
        for (Ship ship : ships) {
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j < ship.length + 1; j++) {
                    if (ship.isHorizontal) {
                        if ((i == 0) & ((j == 0) | (j == ship.length)))
                            break;
                        {
                            if ((ship.getPosition().y + i >= 0) && (ship.getPosition().y + i < 10) &&
                                    (ship.getPosition().x + j >= 0) && (ship.getPosition().x + j < 10))
                                if (field[ship.getPosition().y + i][ship.getPosition().x + j].cellState == PointCell.state.ship) {
                                    return true;
                                }
                        }
                    }
                }
        }
        return false;
    }

    private void setMiss() {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (field[j][i].getSelected())
                    field[j][i].cellState = PointCell.state.miss;
            }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
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
                g2.drawRect(PointCell.cellSizeX * i, PointCell.cellSizeY * j, PointCell.cellSizeX, PointCell.cellSizeY);
                //g2.drawString(String.valueOf(mouseX) + " " + String.valueOf(mouseY),100, 100);
                g2.drawString(String.valueOf(debug), 100, 100);
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
        else {
            setCellSelected(e);
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 3) {
            turnShip();
            MainWindow.updateShipPos(ships, field);
            debug = e.getButton();
        }
        if (!shipPlacement)
            setMiss();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (checkCollision()) {
            for (Ship ship : ships) ship.setPosition(new Point(ship.getInitPosition()));
            MainWindow.updateShipPos(ships, field);
            repaint();
        } else {
            for (Ship ship : ships) ship.setInitPosition(new Point(ship.getPosition()));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
