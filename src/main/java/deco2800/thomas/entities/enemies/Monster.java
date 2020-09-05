package deco2800.thomas.entities.enemies;

import deco2800.thomas.tasks.MovementTask;

public abstract class Monster extends EnemyPeon {

    public boolean wild;

    public Monster(String name, String texture, int height, float speed, int health, boolean wild) {
        super(name, texture, height, speed, health);
        this.wild = wild;
    }
}
