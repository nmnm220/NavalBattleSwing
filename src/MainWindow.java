import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MainWindow extends JFrame {
    //Ship[] playerShips = new Ship[10];
    ArrayList<Ship> playerShips = new ArrayList<Ship>();
    PointCell[][] playerField = new PointCell[10][10];
    FieldDrawer playerFieldDrawer;
    Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();

    MainWindow() {
        setTitle("Морской бой");
        setSize(screenResolution.width / 2, screenResolution.height / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        getContentPane().setBackground(new Color(80, 80, 150));
        Dimension frameSize = this.getSize();

        initField(playerShips);
        playerFieldDrawer = new FieldDrawer(playerField, playerShips);
        playerFieldDrawer.setSize(frameSize.height / 2, frameSize.height / 2);
        add(playerFieldDrawer);
    }

    private void initField(ArrayList<Ship> ships) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                playerField[i][j] = new PointCell(PointCell.state.water);
            }
        }
        placeShips(ships);
    }

    private void placeShips(ArrayList<Ship> ships) {
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
        updateShipPos(ships, playerField);
    }
    public static void updateShipPos(ArrayList<Ship> ships, PointCell[][] field) {
        for (Ship item :
                ships) {
            for (int i = 0; i < item.getLength(); i++) {
                if (item.isHorizontal)
                    field[item.getPosition().y][item.getPosition().x + i].setCellState(PointCell.state.ship);
                else
                    field[item.getPosition().y + i][item.getPosition().x].setCellState(PointCell.state.ship);
            }
        }
    }
}
