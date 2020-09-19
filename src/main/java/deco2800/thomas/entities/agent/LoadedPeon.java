package deco2800.thomas.entities.agent;

import deco2800.thomas.managers.GameManager;

public class LoadedPeon extends Peon {

    private float wallet = 0f;

    public LoadedPeon(float row, float col, float speed, int health) {
        // Initialise abstract entity
        super(row, col, speed, health);
    }

    public float getWallet() {
        return wallet;
    }

    public static void debit(float amount) {
        PlayerPeon player = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
        if (!player.isDead()) {
            player.takeMoney(amount);
        }
    }

    public static void credit(float amount) {
        PlayerPeon player = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
        if (!player.isDead()) {
            player.addMoney(amount);
        }
    }

    public void takeMoney(float amount) {
        wallet -= amount;
    }

    public void addMoney(float amount) {
        wallet += amount;
    }
}