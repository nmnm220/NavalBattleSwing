import java.util.ArrayList;
import java.util.Random;

public class AI {
    static PointCell[][] field;
    static ArrayList<Ship> ships;

    public void shoot() {
        Random random = new Random();
        int xCoord = random.nextInt(MainWindow.FIELD_SIZE - 1);
        int yCoord = random.nextInt(MainWindow.FIELD_SIZE - 1);
        while ((field[yCoord][xCoord].cellState == PointCell.state.miss) || (field[yCoord][xCoord].cellState == PointCell.state.hit)) {
            xCoord = random.nextInt(MainWindow.FIELD_SIZE - 1);
            yCoord = random.nextInt(MainWindow.FIELD_SIZE - 1);
        }
        if (GameLogic.shoot(field, ships, xCoord, yCoord))
            shoot();
    }
}

