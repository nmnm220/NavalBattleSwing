public class PointCell {

    public enum state
    {
        water,
        ship,
        miss,
        hit,
        shipSelected,
        waterSelected
    }
    state cellState;
    private boolean isSelected = false;
    public static int cellSizeX = 30;
    public static int cellSizeY = 30;
    PointCell(state cellState)
    {
        this.cellState = cellState;
    }
    public void setCellState(state state)
    {
        this.cellState = state;
    }
    public boolean getSelected() { return isSelected; }
    public void setSelected(boolean isSelected) { this.isSelected = isSelected;}
}
