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
        GameLogic.moveShip(e, ships, field, prevPos);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (shipPlacement)
            GameLogic.setShipSelected(e, ships, field);
        else {
            GameLogic.setCellSelected(e, field);
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 3) {
            GameLogic.turnShip(ships, field);
            MainWindow.updateShipPos(ships, field);
            debug = e.getButton();
        }
        if (!shipPlacement)
            GameLogic.setMiss(field);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (GameLogic.checkCollision(ships, field)) {
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
