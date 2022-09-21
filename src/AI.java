import java.util.Random;

public class AI {
    static PointCell[][] field;

/*    AI(PointCell[][] field) {
        this.field = field;
    }*/

    public void shoot() {
        Random random = new Random();
        int xCoord = random.nextInt(9);
        int yCoord = random.nextInt(9);
        while ((field[yCoord][xCoord].cellState == PointCell.state.miss) || (field[yCoord][xCoord].cellState == PointCell.state.hit)) {
            xCoord = random.nextInt(9);
            yCoord = random.nextInt(9);
        }
        if (GameLogic.shoot(field, xCoord, yCoord))
            shoot();
    }
}

