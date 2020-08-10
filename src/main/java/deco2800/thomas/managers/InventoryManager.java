package deco2800.thomas.managers;

public class InventoryManager extends TickableManager {

    //TODO:  There is probably some better structure you can make for this....
    private int stone = 0;

    @Override
    public void onTick(long i) {

    }

    public int getStone() {
        return stone;
    }

    public void addStone(int stone) {
        this.stone += stone;
    }
}
