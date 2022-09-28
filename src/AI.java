import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.abs;


public class AI {
    Random random = new Random();
    static PointCell[][] field;
    static ArrayList<Ship> ships;

    Point hitPoint = new Point();
    private static int direction = -1;
    private int x = random.nextInt(MainWindow.FIELD_SIZE);
    private int y = random.nextInt(MainWindow.FIELD_SIZE);
    //private final Point lastHit = new Point(-1, -1);
    //private boolean random = true;
    public static boolean shipHit = false;
    private static int shotNum = 0;
    private static boolean oneDeck = false;
    private static boolean newShot = true;
    private static shotState state = shotState.firstHit;

    private enum shotState {
        destroyed,
        firstHit,
        hit,
        miss
    }
    public void init()
    {
    }

    public void shoot() {
        /*if (!shipHit)
            rndCoord();*/
        //boolean isHit = GameLogic.shoot(field, ships, x, y);
        if ((GameLogic.shoot(field, ships, x, y))) {
            //hitPoints.clear();
            setHitPoints();
            shoot();
        } else {
            //hitPoints.clear();
            setHitPoints();
        }
    }

    static void shipDestroyed(Ship ship) {
        //hitPoints.clear();
    }

    private void setHitPoints() {
        ArrayList<Point> hitPoints = new ArrayList<>();
        for (int i = 0; i < MainWindow.FIELD_SIZE; i++)
            for (int j = 0; j < MainWindow.FIELD_SIZE; j++)
                if (field[j][i].cellState == PointCell.state.hit)
                    hitPoints.add(new Point(j, i));
        if (!hitPoints.isEmpty())
            for (Point point : hitPoints) {
                for (int i = -1; i <= 1; i++) {
                    if (point.y + i > 0 & point.y + i < 10)
                        if ((field[point.y + i][point.x].cellState == PointCell.state.water) || (field[point.y + i][point.x].cellState == PointCell.state.ship)) {
                            x = point.x;
                            y = point.y + i;
                            //field[y][x].cellState = PointCell.state.shipSelected;
                            return;
                        }
                    if (point.x + i > 0 & point.x + i < 10)
                        if ((field[point.y][point.x + i].cellState == PointCell.state.water) || (field[point.y][point.x + i].cellState == PointCell.state.ship)) {
                            x = point.x + i;
                            y = point.y;
                            //field[y][x].cellState = PointCell.state.shipSelected;
                            return;
                        }
                }
            }
        rndCoord();
    }

    public void rndCoord() {

        x = random.nextInt(MainWindow.FIELD_SIZE);
        y = random.nextInt(MainWindow.FIELD_SIZE);
        for (int i = 0; i < 1000; i++) {
            if ((field[y][x].cellState == PointCell.state.miss) || (field[y][x].cellState == PointCell.state.hit)) {
                x = random.nextInt(MainWindow.FIELD_SIZE);
                y = random.nextInt(MainWindow.FIELD_SIZE);
            } else break;
        }
    }

    private boolean checkOutOfBounds(int x, int y) {
        return x < 0 || y < 0 || x > 9 || y > 9;
    }

    private void saveDirection() {
        switch (direction) {
            case 0 -> x++;
            case 1 -> x--;
            case 2 -> y++;
            case 3 -> y--;
            default -> direction = 0;
        }
    }
}

