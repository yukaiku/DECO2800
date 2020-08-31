package deco2800.thomas.entities;

import deco2800.thomas.entities.Agent.HasHealth;
import deco2800.thomas.entities.Agent.Peon;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.tasks.MovementTask;

/**
 An abstract class inheriting from Peon that will define the
 behaviour of all enemies. All enemies must have some form of health.
 **/
public abstract class EnemyPeon extends Peon implements HasHealth {

    private PlayerPeon target;

    public EnemyPeon(float row, float col, float speed) {
        super(row, col, speed);
        this.setObjectName("EnemyPeon");
        this.setTexture("spacman_blue");
        this.setHeight(1);
        this.target = null;
    }

    public PlayerPeon getTarget() {
        return target;
    }

    public void setTarget(PlayerPeon target) {
        this.target = target;
    }

    public int getHealth() {
        return 0;
    }

    public void setHealth(int newHealth) {
    }
}
