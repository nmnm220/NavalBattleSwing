import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


public class MainWindow extends JFrame implements ActionListener {
    //Ship[] playerShips = new Ship[10];
    static boolean shipPlacement = true;
    static FieldDrawer refFieldDrawer;
    ArrayList<Ship> playerShips = new ArrayList<>();
    ArrayList<Ship> enemyShips = new ArrayList<>();
    ArrayList<Ship> playerShipsPool = new ArrayList<>();
    ArrayList<Ship> enemyShipsPool = new ArrayList<>();
    PointCell[][] playerField = new PointCell[10][10];
    PointCell[][] enemyField = new PointCell[10][10];
    PointCell[][] hiddenField = new PointCell[10][10];
    FieldDrawer playerFieldDrawer;
    FieldDrawer enemyFieldDrawer;
    ShipSelector shipSelector;
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

        fillWater(hiddenField);

        initPool(playerShipsPool, playerField);
        playerFieldDrawer = new FieldDrawer(playerField, playerShips);
        add(playerFieldDrawer);

        shipSelector = new ShipSelector(playerShipsPool, playerShips, playerField, playerFieldDrawer);
        shipSelector.setSize(frameSize.width / 5, playerFieldDrawer.getHeight());
        shipSelector.initUI();
        shipSelector.setLocation(PointCell.cellSizeX * 10, 0);
        add(shipSelector);
        for (Component component : shipSelector.getComponents())
            if ((component instanceof JButton) && ((JButton) component).getText().equals("Start game")) {
                ((JButton) component).addActionListener(this);
            }
    }

    public void startGame() {
        remove(shipSelector);
        initPool(enemyShipsPool, enemyField);
        randomPlacement(enemyShipsPool, enemyShips, enemyField);

        enemyFieldDrawer = new FieldDrawer(hiddenField, enemyField, enemyShips);
        enemyFieldDrawer.setShipPlacement(false);
        enemyFieldDrawer.setLocation(PointCell.cellSizeX * 12, 0);

        add(enemyFieldDrawer);
        playerFieldDrawer.setShipPlacement(false);
        playerFieldDrawer.repaint();
        enemyFieldDrawer.repaint();
        AI.field = playerField;
        refFieldDrawer = playerFieldDrawer;
    }

    private void initPool(ArrayList<Ship> ships, PointCell[][] field) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = new PointCell(PointCell.state.water, new Point(i, j));
            }
        }
        fillShips(ships);
    }

    private void fillShips(ArrayList<Ship> ships) {
        Point p = new Point();
        ships.add(new Ship(4, true, p));
        ships.add(new Ship(3, true, p));
        ships.add(new Ship(3, true, p));
        ships.add(new Ship(2, true, p));
        ships.add(new Ship(2, true, p));
        ships.add(new Ship(2, true, p));
        ships.add(new Ship(1, true, p));
        ships.add(new Ship(1, true, p));
        ships.add(new Ship(1, true, p));
        ships.add(new Ship(1, true, p));
    }

    static void randomPlacement(ArrayList<Ship> poolShips, ArrayList<Ship> ships, PointCell[][] field) {
        for (Ship poolShip : poolShips) {
            newRandomShipPosition(poolShip, ships, field);
        }
        //newRandomShipPosition();
    }

    static void newRandomShipPosition(Ship poolShip, ArrayList<Ship> ships, PointCell[][] field) {
        Random random = new Random();
        int rndX = random.nextInt(9);
        int rndY = random.nextInt(9);
        boolean turn = random.nextBoolean();
        poolShip = new Ship(poolShip.getLength(), turn, new Point(rndX, rndY));
        ships.add(poolShip);
        poolShip.setPlaced();
        for (int i = 0; i < 1000; i++) {
            if (GameLogic.checkCollision(ships, field)) {
                rndX = random.nextInt(9);
                rndY = random.nextInt(9);
                turn = random.nextBoolean();
                if (turn)
                    poolShip.turn();
                poolShip.setPosition(new Point(rndX, rndY));
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

    public static void removeShips(ArrayList<Ship> ships) {
        ships.removeAll(ships);
    }

    public static void fillWater(PointCell[][] field) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                field[j][i] = new PointCell(PointCell.state.water, new Point(i, j));
                //field[j][i].setCellState(PointCell.state.water);
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start"))
            startGame();
    }
}
