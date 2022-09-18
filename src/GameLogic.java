import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*
Game logic
 */
public class GameLogic {
    static Point setPosition(Point point, Ship ship) { //sets position of all ships
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

    static void setCellSelected(MouseEvent e, PointCell[][] field) { //sets cell selected when selecting single cell
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

    static void setShipSelected(MouseEvent e, ArrayList<Ship> ships, PointCell[][] field) { //sets all cells of the ship selected when mouse is on the ship
        for (Ship ship :
                ships) {
            if (((((e.getX() / PointCell.cellSizeX) >= ship.getPosition().x)
                    && (e.getX() / PointCell.cellSizeX) < ship.getPosition().x + ship.getLength()))
                    && ((e.getY() / PointCell.cellSizeY) == ship.getPosition().y) && ship.getPlaced() && (ship.isHorizontal)) {
                for (int i = 0; i < ship.length; i++) {
                    field[ship.getPosition().y][ship.getPosition().x + i].cellState = PointCell.state.shipSelected;
                    ship.setSelected(true);
                }
            } else if (((((e.getY() / PointCell.cellSizeX) >= ship.getPosition().y)
                    && (e.getY() / PointCell.cellSizeX) < ship.getPosition().y + ship.getLength()))
                    && ((e.getX() / PointCell.cellSizeY) == ship.getPosition().x) && ship.getPlaced() && (!ship.isHorizontal)) {
                for (int i = 0; i < ship.length; i++) {
                    field[ship.getPosition().y + i][ship.getPosition().x].cellState = PointCell.state.shipSelected;
                    ship.setSelected(true);
                }
            } else if (ship.getPlaced()) {
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

    static void turnShip(ArrayList<Ship> ships, PointCell[][] field) { //turns the ships not letting it go out of array index
        for (Ship ship : ships) {
            if (ship.getSelected() & (ship.getPosition().x + ship.getLength() <= 10) & (ship.getPosition().y + ship.getLength() <= 10)) {
                ship.turn();
            }
        }
        MainWindow.updateShipsPos(ships, field);
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
        MainWindow.updateShipsPos(ships, field);
        //repaint();
    }

    /*static boolean checkCollision(ArrayList<Ship> ships, PointCell[][] field) { //returns true if any ship on the field is too close to another else returns false
        for (Ship ship : ships)
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= ship.getLength(); j++) {
                    int k = 0;
                    if (ship.isHorizontal) {
                        if ((ship.getPosition().y + i >= 0) && (ship.getPosition().y + i < 10) &&
                                (ship.getPosition().x + j >= 0) && (ship.getPosition().x + j < 10))
                            if (i == 0) {
                                if (field[ship.getPosition().y + i][ship.getPosition().x + j].cellState == PointCell.state.ship)
                                    k++;
                                if (k > ship.getLength())
                                    return true;
                            }
                        if (field[ship.getPosition().y + i][ship.getPosition().x + j].cellState == PointCell.state.ship) {
                            return true;
                        }
                    } else if ((ship.getPosition().y + j >= 0) && (ship.getPosition().y + j < 10) &&
                            (ship.getPosition().x + i >= 0) && (ship.getPosition().x + i < 10))
                        if (field[ship.getPosition().y + j][ship.getPosition().x + i].cellState == PointCell.state.ship) {
                            return true;
                        }

                }
        return false;
    }*/
    static boolean checkCollision(ArrayList<Ship> ships, PointCell[][] field) {
        for (Ship ship : ships) {
            int pX = ship.getPosition().x;
            int pY = ship.getPosition().y;
            int length = ship.getLength();
            for (int i = -1; i <= 1; i++) {
                int ctr = 0;
                for (int j = -1; j < length; j++) {
                    if ((pX + j >= 10) | (pX + j < 0) | (pY + j >= 10) | (pY + j < 0))
                        break;
                    if (ship.isHorizontal) {
                        if (field[pY + i][pX + j].cellState == PointCell.state.ship)
                            ctr++;
                        if (ctr >= length)
                            return true;
                    }
                }
            }
        }
        return false;

    }

    static void setMiss(PointCell[][] field) { //sets selected cell state to "miss"
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (field[j][i].getSelected())
                    field[j][i].cellState = PointCell.state.miss;
            }
    }

    static int coordSet(int input) {
        if (input >= 10)
            input = 9;
        else if (input < 0)
            input = 0;
        return input;
    }
}
