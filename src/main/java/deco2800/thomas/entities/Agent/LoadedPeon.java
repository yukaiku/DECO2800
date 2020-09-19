package deco2800.thomas.entities.Agent;

public class LoadedPeon extends Peon {

    private float wallet = 0f;

    public LoadedPeon(float row, float col, float speed, int health) {
        // Initialise abstract entity
        super(row, col, speed, health);
    }

    public float getWallet() {
        return wallet;
    }

    public void debit(float amount) {
        if (!this.isDead()) {
            wallet -= amount;
        }
    }

    public void credit(float amount) {
        if (!this.isDead()) {
            wallet += amount;
        }
    }
}
