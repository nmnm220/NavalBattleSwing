import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;


public class AI {
    static PointCell[][] field;
    static ArrayList<Ship> ships;
    //private ArrayList<Point> hitPoints = new ArrayList<>();
    Point hitPoint = new Point();
    private static int direction = -1;
    private int x = -1;
    private int y = -1;
    private Point lastHit = new Point(-1, -1);
    private boolean random = true;
    private int coord = 0;

    public void shoot() {
        if (random) {
            rndCoord();
        }
        if (GameLogic.shoot(field, ships, x, y)) {
            random = false;
            nextShot();
            shoot();
        } else {
            nextShot();
        }
    }

    public void rndCoord() {
        Random random = new Random();
        x = random.nextInt(MainWindow.FIELD_SIZE);
        y = random.nextInt(MainWindow.FIELD_SIZE);
        for (int i = 0; i < 1000; i++) {
            if ((field[y][x].cellState == PointCell.state.miss) || (field[y][x].cellState == PointCell.state.hit)) {
                x = random.nextInt(MainWindow.FIELD_SIZE);
                y = random.nextInt(MainWindow.FIELD_SIZE);
            } else break;
        }
    }
    public void nextShot() {
        for (int k = -1; k <= 1; k++)
            for (int l = 1; l >= -1; l--) {
                if ((x + l >= 0 & y + k >= 0 & x + l < 10 & y + k < 10 & (abs(k) != abs(l)))) {
                    if ((field[y + k][x + l].cellState == PointCell.state.water) || (field[y + k][x + l].cellState == PointCell.state.ship)) {
                        x = x + l;
                        y = y + k;
                        return;
                    }
                }
            }
        random = true;
    }

/*    private void findHit() {
        ArrayList<Point> hitPoints = new ArrayList<>();
        for (int i = 0; i < MainWindow.FIELD_SIZE; i++)
            for (int j = 0; j < MainWindow.FIELD_SIZE; j++) {
                if (field[j][i].cellState == PointCell.state.hit) {
                    hitPoints.add(new Point(j, i));
                }
            }
        for (Point point : hitPoints)
            for (int k = -1; k <= 1; k++)
                for (int l = 1; l >= -1; l--) {
                    if ((point.x + l) >= 0 & (point.y + k) >= 0 & (point.x + l) < 10 & (point.y + k) < 10 & (abs(k) != abs(l))) {
                        if ((field[point.y + k][point.x + l].cellState == PointCell.state.water) || (field[point.y + k][point.x + l].cellState == PointCell.state.ship)) {
                            x = point.x + l;
                            y = point.y + k;
                            return;
                        }
                    }
                }
    }*/

    private void saveDirection() {
        switch (direction) {
            case 0 -> x++;
            case 1 -> x--;
            case 2 -> y++;
            case 3 -> y--;
            default -> direction = -1;
        }
        if ((x < 0) || (y < 0) || (x > 9) || (y > 9)) {
            x = 0;
            y = 0;
        }
    }
}

