import com.sun.tools.javac.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AI {
    static PointCell[][] field;
    static ArrayList<Ship> ships;
    private static int direction = 0;
    private int x;
    private int y;
    ArrayList<Point> hitPoints = new ArrayList<>();

    static boolean hit;

    public void shoot() {
        x = -1;
        y = -1;
        findHit();
        if (x < 0) {
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
        if (GameLogic.shoot(field, ships, x, y)) {
            MainWindow.refFieldDrawer.repaint();
            shoot();
        }
    }

    private void findHit() {
        for (int i = 0; i < MainWindow.FIELD_SIZE; i++)
            for (int j = 0; j < MainWindow.FIELD_SIZE; j++) {
                if (field[j][i].cellState == PointCell.state.hit) {
                    hitPoints.add(new Point(j, i));
                }
            }
        for (Point point : hitPoints)
            for (int k = -1; k <= 1; k++)
                for (int l = -1; l <= 1; l++) {
                    if ((point.x + l) >= 0 & (point.y + k) >= 0 & (point.x + l) < 10 & (point.y + k) < 10) {
                        if ((field[point.x + l][point.y + k].cellState != PointCell.state.hit && field[point.x + l][point.y + k].cellState != PointCell.state.miss)) {
                            x = point.x + l;
                            y = point.y + k;
                        }
                    }
                }
    }

    /*public void shoot() {
        if (direction == 0) {
            Random random = new Random();
            int xCoord = random.nextInt(MainWindow.FIELD_SIZE);
            int yCoord = random.nextInt(MainWindow.FIELD_SIZE);
            for (int i = 0; i < 1000; i++) {
                if ((field[yCoord][xCoord].cellState == PointCell.state.miss) || (field[yCoord][xCoord].cellState == PointCell.state.hit)) {
                    xCoord = random.nextInt(MainWindow.FIELD_SIZE);
                    yCoord = random.nextInt(MainWindow.FIELD_SIZE);
                } else break;
            }
            if (GameLogic.shoot(field, ships, xCoord, yCoord)) {
                prevX = xCoord;
                prevY = yCoord;
                x = xCoord;
                y = yCoord;
                direction = 1;
                shoot();
            }
        } else {
        }
    }*/

    private void changeDirection() {
        direction++;
        //x = prevX;
        //y = prevY;
        if (direction == 4)
            direction = 0;
    }
}

