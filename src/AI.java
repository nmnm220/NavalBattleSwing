import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class AI {
    private final Random random = new Random();
    static PointCell[][] field;
    static ArrayList<Ship> ships;

    Point hitPoint = new Point();
    private static int direction = -1;
    private int x = random.nextInt(MainWindow.FIELD_SIZE);
    private int y = random.nextInt(MainWindow.FIELD_SIZE);


    public void shoot() {

        if ((GameLogic.shoot(field, ships, x, y))) {
            setHitPoints();
            shoot();
        } else {
            setHitPoints();
        }
    }

    private void setHitPoints() {
        ArrayList<Point> hitPoints = new ArrayList<>();
        for (int i = 0; i < MainWindow.FIELD_SIZE; i++)
            for (int j = 0; j < MainWindow.FIELD_SIZE; j++)
                if (field[i][j].cellState == PointCell.state.hit)
                    hitPoints.add(new Point(j, i));

        if (!hitPoints.isEmpty())
            for (Point point : hitPoints) {
                int ctr = 0;
                for (int i = -4; i < 4; i++) {
                    if ((point.x + i >= 0 & point.x + i < 10) && (field[point.y][point.x + i].cellState == PointCell.state.hit)) {
                        ctr++;
                    }
                    if (ctr >= 2 & (point.x + i + 1 > 0 & point.x + i + 1 < 10) && (field[point.y][point.x + i + 1].cellState == PointCell.state.water || field[point.y][point.x + i + 1].cellState == PointCell.state.ship)) {
                        x = point.x + i + 1;
                        y = point.y;
                        return;
                    }
                }
                ctr = 0;
                for (int i = -4; i < 4; i++) {
                    if ((point.y + i >= 0 & point.y + i < 10) && (field[point.y + i][point.x].cellState == PointCell.state.hit)) {
                        ctr++;
                    }
                    if (ctr >= 2 & (point.y + i + 1 > 0 & point.y + i + 1 < 10) && (field[point.y + i + 1][point.x].cellState == PointCell.state.water || field[point.y + i + 1][point.x].cellState == PointCell.state.ship)) {
                        x = point.x;
                        y = point.y + i + 1;
                        return;
                    }
                }


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
}

