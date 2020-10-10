package deco2800.thomas.entities.agent;

import deco2800.thomas.managers.GameManager;

public class LoadedPeon extends Peon {

    private float wallet = 0f;

    public LoadedPeon(){
        this.wallet = 0;
    }

    public LoadedPeon(float row, float col, float speed, int health) {
        // Initialise abstract entity
        super(row, col, speed, health);
    }

    public float getWallet() {
        return wallet;
    }

    public static float checkBalance() {
        PlayerPeon player = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
        if (!player.isDead()) {
            return player.getWallet();
        }
        return 0f;
    }

    public static void debit(float amount) {
        PlayerPeon player = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
        if (!player.isDead() && amount > 0) {
            player.takeMoney(amount);
        }
    }

    public static void credit(float amount) {
        PlayerPeon player = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
        if (!player.isDead() && amount > 0) {
            player.addMoney(amount);
        }
    }

    public static void healPlayer(int amount){
        PlayerPeon player = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
        if (!player.isDead() && player.getWallet() > 0) {
            player.regenerateHealth(amount);
        }
    }

    public void takeMoney(float amount) {
        wallet -= amount;
    }

    public void addMoney(float amount) {
        wallet += amount;
    }
}
