import javax.swing.*;

public class ShipSelector extends JPanel {
    JButton nextButton = new JButton(">");
    JButton prevButton = new JButton("<");
    JButton placeShipButton = new JButton("Place Ship");
    JButton resetButton = new JButton("Reset");
    public void initButtons()
    {
        setLayout(null);
        var size = this.getSize();

        nextButton.setSize(size.height/4, size.height/5);
        prevButton.setSize(size.height/4, size.height/5);
        placeShipButton.setSize(size.width, size.height/6);
        resetButton.setSize(size.width, size.height/6);

        nextButton.setLocation(size.width - nextButton.getSize().width, 2*size.width/3);
        prevButton.setLocation(0, 2*size.width/3);
        placeShipButton.setLocation(size.width/2 - placeShipButton.getSize().width/2, 2*size.height/3);
        resetButton.setLocation(size.width/2 - placeShipButton.getSize().width/2, 5*size.height/6);

        add(nextButton);
        add(prevButton);
        add(placeShipButton);
        add(resetButton);
    }
}
