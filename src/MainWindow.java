import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class MainWindow extends JFrame {
    //Ship[] playerShips = new Ship[10];
    ArrayList<Ship> playerShips = new ArrayList<>();
    ArrayList<Ship> playerShipsPool = new ArrayList<>();
    PointCell[][] playerField = new PointCell[10][10];
    FieldDrawer playerFieldDrawer;
    Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();

    MainWindow() {
        setTitle("Naval Battle");
        setSize(screenResolution.width / 2, screenResolution.height / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        getContentPane().setBackground(new Color(80, 80, 150));
        Dimension frameSize = this.getSize();

        initField(playerShipsPool);
        playerFieldDrawer = new FieldDrawer(playerField, playerShips, 0, 0);
        playerFieldDrawer.setSize(frameSize.height / 2, frameSize.height / 2);
        add(playerFieldDrawer);

        ShipSelector shipSelector = new ShipSelector(playerShipsPool, playerShips, playerField, playerFieldDrawer);
        shipSelector.setSize(frameSize.width / 5, frameSize.height / 2);
        shipSelector.initUI();
        shipSelector.setLocation(PointCell.cellSizeX * 11, 0);
        add(shipSelector);
    }

    private void initField(ArrayList<Ship> ships) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                playerField[i][j] = new PointCell(PointCell.state.water);
            }
        }
        fillPool(ships);
    }

    private void fillPool(ArrayList<Ship> ships) {
        ships.add(new Ship(4, true, new Point(0, 0)));
        ships.add(new Ship(3, true, new Point(0, 2)));
        ships.add(new Ship(3, true, new Point(0, 4)));
        ships.add(new Ship(2, true, new Point(0, 6)));
        ships.add(new Ship(2, true, new Point(0, 8)));
        ships.add(new Ship(2, true, new Point(6, 0)));
        ships.add(new Ship(1, true, new Point(6, 2)));
        ships.add(new Ship(1, true, new Point(6, 4)));
        ships.add(new Ship(1, true, new Point(6, 6)));
        ships.add(new Ship(1, true, new Point(6, 8)));
        //updateShipsPos(ships, playerField);
    }

    static void newShip(Ship ship, ArrayList<Ship> ships, PointCell[][] field) {
        Random random = new Random();
        int rndX = random.nextInt(9);
        int rndY = random.nextInt(9);
        boolean turn = random.nextBoolean();
        ship = new Ship(ship.getLength(), turn, new Point(rndX, rndY));
        ships.add(ship);
        ship.setPlaced();
        for (int i = 0; i < 1000; i++) {
            if (GameLogic.checkCollision(ships, field)) {
                rndX = random.nextInt(9);
                rndY = random.nextInt(9);
                turn = random.nextBoolean();
                if (turn)
                    ship.turn();
                ship.setPosition(new Point(rndX, rndY));
                updateShipsPos(ships, field);
            }
        }
    }

    public static void updateShipsPos(ArrayList<Ship> ships, PointCell[][] field) {
        fillWater(field);
        for (Ship ship : ships)
            if (ship.getPlaced())
                for (int j = 0; j < ship.getLength(); j++) {
                    if ((ship.isHorizontal) & (ship.getPosition().x + j) < 10)
                        field[ship.getPosition().y][ship.getPosition().x + j].setCellState(PointCell.state.ship);
                    else if ((!ship.isHorizontal) & (ship.getPosition().y + j) < 10)
                        field[ship.getPosition().y + j][ship.getPosition().x].setCellState(PointCell.state.ship);
                }
    }

    public static void fillWater(PointCell[][] field) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                field[j][i].setCellState(PointCell.state.water);
    }
}
