package deco2800.thomas.entities;

/**
 An abstract class inheriting from Peon that will define the
 behaviour of all enemies. All enemies must have some form of health.
 **/
public abstract class EnemyPeon extends Peon implements HasHealth {
    public int getHealth() {
        return 0;
    }

    public void setHealth(int newHealth) {
    }
}
