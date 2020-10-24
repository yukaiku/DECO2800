package deco2800.thomas.entities.agent;

import deco2800.thomas.managers.GameManager;

public class LoadedPeon extends Peon {

    private static int wallet = 0;

    public LoadedPeon(){
        this.wallet = 0;
    }

    public LoadedPeon(float row, float col, float speed, int health) {
        // Initialise abstract entity
        super(row, col, speed, health);
    }

    public static int getWallet() {
        return wallet;
    }
    
    public static void setWallet(int money){
        wallet = money; 
    }

    public static int checkBalance() {
        PlayerPeon player = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
        if (!player.isDead()) {
            return player.getWallet();
        }
        return 0;
    }

    public static void debit(int amount) {
        PlayerPeon player = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();
        if (!player.isDead() && amount > 0) {
            player.takeMoney(amount);
        }
    }

    public static void credit(int amount) {
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

    public static void takeMoney(int amount) {
        wallet -= amount;
    }

    public static void addMoney(int amount) {
        wallet += amount;
    }
}
