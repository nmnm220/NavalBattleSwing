import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShipSelector extends JPanel implements ActionListener {
    private final JButton nextButton = new JButton(">");
    private final JButton prevButton = new JButton("<");
    private final JButton placeShipButton = new JButton("Place Ship");
    private final JButton resetButton = new JButton("Reset");
    private final JButton startGameButton = new JButton("Start game");
    private ShipDrawer shipDrawer;
    ArrayList<Ship> shipsPool;
    ArrayList<Ship> ships;
    PointCell[][] field;
    FieldDrawer fieldDrawer;
    private int shipNum = 0;

    ShipSelector(ArrayList<Ship> shipsPool, ArrayList<Ship> ships, PointCell[][] field, FieldDrawer fieldDrawer) {
        this.shipsPool = shipsPool;
        this.ships = ships;
        this.field = field;
        this.fieldDrawer = fieldDrawer;
    }

    public void initUI() {
        setLayout(null);
        var size = this.getSize();

        nextButton.setSize(size.height / 4, size.height / 7);
        prevButton.setSize(size.height / 4, size.height / 7);
        placeShipButton.setSize(size.width, size.height / 6);
        resetButton.setSize(size.width, size.height / 6);

        nextButton.setLocation(size.width - nextButton.getSize().width, 2 * size.width / 3);
        prevButton.setLocation(0, 2 * size.width / 3);
        placeShipButton.setLocation(size.width / 2 - placeShipButton.getSize().width / 2, 2 * size.height / 3);
        resetButton.setLocation(size.width / 2 - placeShipButton.getSize().width / 2, 4 * size.height / 5);


        add(nextButton);
        add(prevButton);
        add(placeShipButton);
        add(resetButton);

        nextButton.addActionListener(this);
        prevButton.addActionListener(this);
        placeShipButton.addActionListener(this);
        resetButton.addActionListener(this);

        nextButton.setActionCommand("next");
        prevButton.setActionCommand("prev");
        placeShipButton.setActionCommand("place");
        resetButton.setActionCommand("reset");

        shipDrawer = new ShipDrawer(shipsPool.get(0), shipNum);
        shipDrawer.setSize(size.height / 2, size.height / 3);
        shipDrawer.setLocation(size.width / 2 - shipDrawer.getSize().width / 2, size.height / 8);
        add(shipDrawer);

        startGameButton.setSize(size.width, size.height / 6);
        startGameButton.addActionListener(this);
        startGameButton.setLocation(size.width / 2 - startGameButton.getSize().width / 2, 5 * size.height / 6);
        startGameButton.setActionCommand("start");
        add(startGameButton);
    }
    /*public void startGame()
    {
    }*/

    public void nextShip() {
        shipNum++;
        if (shipNum > 9)
            shipNum = 0;
        else if (shipNum < 0)
            shipNum = 9;
        if (shipsPool.get(shipNum).getPlaced())
            nextShip();
        if (!shipsPool.get(shipNum).getPlaced())
            shipDrawer.drawShip(shipsPool.get(shipNum), shipNum);
    }

    public void previousShip() {
        shipNum--;
        if (shipNum > 9)
            shipNum = 0;
        else if (shipNum < 0)
            shipNum = 9;
        if (shipsPool.get(shipNum).getPlaced())
            previousShip();
        if (!shipsPool.get(shipNum).getPlaced())
            shipDrawer.drawShip(shipsPool.get(shipNum), shipNum);
    }

    public void placeShip() {
        shipsPool.get(shipNum).setPlaced();
        MainWindow.newRandomShipPosition(shipsPool.get(shipNum), ships, field);
        MainWindow.updateShipsPos(ships, field);
        fieldDrawer.repaint();
        int ctr = 0;
        for (Ship ship : shipsPool) {
            if (ship.getPlaced())
                ctr++;
            if (shipsPool.size() == ctr) {
                shipDrawer.drawShip(null, 0);
                repaint();
                placeShipButton.setEnabled(false);
                nextButton.setEnabled(false);
                prevButton.setEnabled(false);
                return;
            }
        }
        if (shipsPool.get(shipNum).getPlaced())
            nextShip();
    }

    public void resetShips() {
        for (Ship ship : shipsPool) {
            ship.reset();
        }
        for (Ship ship : ships) {
            ship.reset();
        }
        placeShipButton.setEnabled(true);
        nextButton.setEnabled(true);
        prevButton.setEnabled(true);
        if (shipsPool.get(shipNum).getPlaced())
            nextShip();
        MainWindow.removeShips(ships);
        MainWindow.updateShipsPos(ships, field);
        fieldDrawer.repaint();
        shipDrawer.drawShip(shipsPool.get(shipNum), shipNum);
        shipDrawer.repaint();
    }
    static void startGame()
    {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "next" -> nextShip();
            case "prev" -> previousShip();
            case "place" -> placeShip();
            case "reset" -> resetShips();
            case "start" -> startGame();
        }
    }
}
