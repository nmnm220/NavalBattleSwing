import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PointCell extends JLabel implements MouseListener {

    public enum state
    {
        water,
        ship,
        miss,
        hit,
        shipSelected,
        waterSelected
    }
    Color cellColor = new Color(0,0,0);
    state cellState;
    int cellSizeX = 25;
    int cellSizeY = 25;
    PointCell(state cellState)
    {
        this.cellState = cellState;
        addMouseListener(this);
        setSize(cellSizeX, cellSizeY);
        setVerticalAlignment(JLabel.CENTER);
        setHorizontalAlignment(JLabel.CENTER);
    }
    public void setCellState(state state)
    {
        this.cellState = state;
    }
    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        switch (cellState)
        {
            case hit:
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(0,0,cellSizeX,cellSizeY);
                g2.setColor(Color.RED);
                g2.drawLine(0, 0, cellSizeX, cellSizeY);
                g2.drawLine(cellSizeX, 0, 0, cellSizeY);
                break;
            case miss:
                g2.setColor(Color.BLUE);
                g2.fillRect(0,0,cellSizeX,cellSizeY);
                g2.setColor(Color.RED);
                g2.fillOval(cellSizeX/3,cellSizeY/3,cellSizeX/2, cellSizeY/2);
                break;
            case ship:
                g2.setColor(Color.GRAY);
                g2.fillRect(0,0,cellSizeX,cellSizeY);
                break;
            case shipSelected:
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(0,0,cellSizeX,cellSizeY);
                break;
            case water:
                g2.setColor(new Color(50,50,255));
                g2.fillRect(0,0,cellSizeX,cellSizeY);
                break;
            case waterSelected:
                g2.setColor(new Color(100,100,255));
                g2.fillRect(0,0,cellSizeX,cellSizeY);
                break;
        }
        g2.setColor(cellColor);
        g2.drawRect(0, 0, cellSizeX, cellSizeY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if ((cellState == state.ship) || (cellState == state.shipSelected)) {
            cellState = state.hit;
            repaint();
        }
        if ((cellState == state.water) || (cellState == state.waterSelected))
        {
            cellState = state.miss;
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (cellState == state.water)
        {
            cellState = state.waterSelected;
            repaint();
        }
        if (cellState == state.ship)
        {
            cellState = state.shipSelected;
            repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (cellState == state.waterSelected)
        {
            cellState = state.water;
            repaint();
        }
        if (cellState == state.shipSelected)
        {
            cellState = state.ship;
            repaint();
        }
    }
}
