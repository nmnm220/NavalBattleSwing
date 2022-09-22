import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*
Game logic
 */
public class GameLogic {
    private static MainWindow refMainWindow;
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
        for (int i = 0; i < MainWindow.FIELD_SIZE; i++)
            for (int j = 0; j < MainWindow.FIELD_SIZE; j++) {
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

    static PointCell currentCell(MouseEvent e, PointCell[][] field) {
        for (int i = 0; i < MainWindow.FIELD_SIZE; i++)
            for (int j = 0; j < MainWindow.FIELD_SIZE; j++) {
                if (((e.getX() >= i * PointCell.cellSizeX) & (e.getX() <= i * PointCell.cellSizeX + PointCell.cellSizeX)) &
                        ((e.getY() >= j * PointCell.cellSizeY) & (e.getY() <= j * PointCell.cellSizeY + PointCell.cellSizeY))) {
                    return field[j][i];
                }
            }
        return null;
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
            if (ship.getSelected() & (ship.getPosition().x + ship.getLength() <= MainWindow.FIELD_SIZE) & (ship.getPosition().y + ship.getLength() <= MainWindow.FIELD_SIZE)) {
                ship.turn();
            }
        }
        MainWindow.updateShipsPos(ships, field);
        if (checkCollision(ships, field))
            turnShip(ships, field);
    }

    static void moveShip(MouseEvent e, ArrayList<Ship> ships, PointCell[][] field) {
        for (Ship ship : ships) {
            if (ship.getSelected()) {
                Point prevPos = ship.getPosition();
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
                //prevPos = curPos;
            }
        }
        MainWindow.updateShipsPos(ships, field);
    }

    /*static boolean checkCollision(ArrayList<Ship> ships, PointCell[][] field) {
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
    static boolean checkCollision(ArrayList<Ship> ships, PointCell[][] field) { //returns true if any ship on the field is too close to another else returns false
        int allShpLength = 0;
        int curShpLength = 0;
        for (Ship ship : ships) {
            int pX = ship.getPosition().x;
            int pY = ship.getPosition().y;
            int length = ship.getLength();
            int ctr = 0;
            allShpLength += length;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= length; j++) {
                    if (ship.isHorizontal) {
                        if (!((pX + j >= MainWindow.FIELD_SIZE) || (pX + j < 0) || (pY + i >= MainWindow.FIELD_SIZE) || (pY + i < 0)))
                            if (field[pY + i][pX + j].cellState == PointCell.state.ship) {
                                ctr++;
                            }
                    } else {
                        if (!((pX + i >= MainWindow.FIELD_SIZE) || (pX + i < 0) || (pY + j >= MainWindow.FIELD_SIZE) || (pY + j < 0)))
                            if (field[pY + j][pX + i].cellState == PointCell.state.ship) {
                                ctr++;
                            }
                    }
                }
                if (ctr > length)
                    return true;
            }
        }
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field.length; j++)
                if (field[j][i].cellState == PointCell.state.ship)
                    curShpLength++;
        if (curShpLength < allShpLength)
            return true;
        return false;

    }

    static boolean shoot(MouseEvent e, ArrayList<Ship> ships, PointCell[][] field, PointCell[][] hiddenField) { //sets selected cell state to "miss/hit"
        PointCell curPoint = currentCell(e, hiddenField);
        if (curPoint.cellState == PointCell.state.water) {
            field[curPoint.coordinate.y][curPoint.coordinate.x].cellState = PointCell.state.miss;
            return false;
        } else {
            field[curPoint.coordinate.y][curPoint.coordinate.x].cellState = PointCell.state.hit;
            if (currentShip(ships, curPoint.coordinate.x, curPoint.coordinate.y) != null) {
                Ship cShip = currentShip(ships, curPoint.coordinate.x, curPoint.coordinate.y);
                cShip.hit();
                if (!cShip.isAlive()) {
                    shipDraw(cShip, field);
                    checkWin(ships);
                }
            }
            return true;
        }

    }

    static boolean shoot(PointCell[][] field, ArrayList<Ship> ships, int x, int y) {
        if (field[y][x].cellState == PointCell.state.ship) {
            field[y][x].cellState = PointCell.state.hit;
            Ship cShip = currentShip(ships, x, y);
            if (cShip != null) {
                cShip.hit();
                if (!cShip.isAlive()) {
                    shipDraw(cShip, field);
                    checkLoose(ships);
                }
            }
            return true;
        } else {
            field[y][x].cellState = PointCell.state.miss;
            return false;
        }
    }

    static Ship currentShip(ArrayList<Ship> ships, int x, int y) {
        for (Ship ship : ships)
            if (ship.isHorizontal) {
                for (int i = 0; i < ship.getLength(); i++)
                    if (ship.getPosition().x + i == x & ship.getPosition().y == y)
                        return ship;
            } else
                for (int i = 0; i < ship.getLength(); i++)
                    if (ship.getPosition().x == x & ship.getPosition().y + i == y)
                        return ship;
        return null;
    }

    static void shipDraw(Ship ship, PointCell[][] field) {
        int pX = ship.getPosition().x;
        int pY = ship.getPosition().y;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= ship.getLength(); j++) {
                if (ship.isHorizontal) {
                    if (!((pX + j >= MainWindow.FIELD_SIZE) || (pX + j < 0) || (pY + i >= MainWindow.FIELD_SIZE) || (pY + i < 0)))
                        if (field[pY + i][pX + j].cellState == PointCell.state.water) {
                            field[pY + i][pX + j].cellState = PointCell.state.miss;
                        }
                } else {
                    if (!((pX + i >= MainWindow.FIELD_SIZE) || (pX + i < 0) || (pY + j >= MainWindow.FIELD_SIZE) || (pY + j < 0)))
                        if (field[pY + j][pX + i].cellState == PointCell.state.water) {
                            field[pY + j][pX + i].cellState = PointCell.state.miss;
                        }
                }
            }
        }

    }

    static void checkLoose(ArrayList<Ship> ships) {
        int ctr = 0;
        for (Ship ship : ships)
            if (ship.isAlive())
                ctr++;
        if (ctr == 0) {
            MainWindow.win = false;
            gameOver();
        }
    }

    static void checkWin(ArrayList<Ship> ships) {
        int ctr = 0;
        for (Ship ship : ships)
            if (ship.isAlive())
                ctr++;
        if (ctr == 0) {
            MainWindow.win = true;
            gameOver();
        }
    }

    public static void setRefMainWindow(MainWindow refMainWindow) {
        GameLogic.refMainWindow = refMainWindow;
    }

    static void gameOver()
    {
        refMainWindow.gameOver();
    }
}
