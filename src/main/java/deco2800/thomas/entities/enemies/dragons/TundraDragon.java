package deco2800.thomas.entities.enemies.dragons;

import deco2800.thomas.entities.enemies.Dragon;

public class TundraDragon extends Dragon {
    public TundraDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.identifier = "dragonTundra";
        this.setObjectName("Giaphias");
    }
}
