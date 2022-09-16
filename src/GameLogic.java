import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameLogic {
    GameLogic()
    {

    }
    static Point setPosition(Point point, Ship ship) {
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

    static void setCellSelected(MouseEvent e, PointCell[][] field) {
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

    static void setShipSelected(MouseEvent e, ArrayList<Ship> ships, PointCell[][] field) {
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

    static void turnShip(ArrayList<Ship> ships, PointCell[][] field) {
        for (Ship ship : ships) {
            if (ship.getSelected() & (ship.getPosition().x + ship.getLength() <= 10) & (ship.getPosition().y + ship.getLength() <= 10)) {
                ship.turn();
            }
        }
        MainWindow.updateShipPos(ships, field);
        //repaint();
        if (checkCollision(ships, field))
            turnShip(ships, field);
    }

    static void moveShip(MouseEvent e, ArrayList<Ship> ships, PointCell[][] field, Point prevPos) {
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
        //repaint();
    }

    static boolean checkCollision(ArrayList<Ship> ships, PointCell[][] field) {
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

    static void setMiss(PointCell[][] field) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (field[j][i].getSelected())
                    field[j][i].cellState = PointCell.state.miss;
            }
    }
}
