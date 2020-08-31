package deco2800.thomas.entities.enemies;

public abstract class Monster extends EnemyPeon {
    public Monster(String name, String texture, int height, float speed, int health) {
        super(name, texture, height, speed, health);
    }
}
