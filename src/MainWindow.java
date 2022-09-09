import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MainWindow extends JFrame {
    //Ship[] playerShips = new Ship[10];
    ArrayList<Ship> playerShips = new ArrayList<Ship>();
    PointCell[][] pointCell = new PointCell[10][10];
    JPanel playerPanel;
    Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
    MainWindow()
    {
        setTitle("Морской бой");
        setSize(screenResolution.width/2, screenResolution.height/2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        getContentPane().setBackground(new Color(80, 80, 150));
        Dimension frameSize = this.getSize();

        playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(10, 10, 0, 0));
        playerPanel.setBackground(Color.PINK);
        playerPanel.setSize(frameSize.height/2, frameSize.height/2);
        initField(playerShips, playerPanel);
    }
    private void initField(ArrayList<Ship> ships, JPanel panel)
    {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                pointCell[i][j] = new PointCell(PointCell.state.water);
                panel.add(pointCell[i][j]);
            }
        }
        placeShips(ships);
        this.add(panel);
    }
    private void placeShips(ArrayList<Ship> ships)
    {
        ships.add(new Ship(4, true, new Point(0,0)));
        ships.add(new Ship(3, true, new Point(0,2)));
        ships.add(new Ship(3, true, new Point(0,4)));
        ships.add(new Ship(2, true, new Point(0,6)));
        ships.add(new Ship(2, true, new Point(0,8)));
        ships.add(new Ship(2, true, new Point(6,0)));
        ships.add(new Ship(1, true, new Point(6,2)));
        ships.add(new Ship(1, true, new Point(6,4)));
        ships.add(new Ship(1, true, new Point(6,6)));
        ships.add(new Ship(1, true, new Point(6,8)));
        playerPanel.add(ships.get(0));
        playerPanel.add(ships.get(1));
        /*for (Ship item:
                ships) {
            for(int i = 0; i < item.getLength(); i++) {
                if (item.isHorizontal)
                    pointCell[item.getPosY()][item.getPosX() + i].setCellState(PointCell.state.ship);
                else
                    pointCell[item.getPosY() + i][item.getPosX()].setCellState(PointCell.state.ship);
            }
         */
        }
    }
