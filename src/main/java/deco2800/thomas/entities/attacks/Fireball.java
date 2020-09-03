package deco2800.thomas.entities.attacks;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.CombatManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.tasks.RangedAttackTask;

public class Fireball extends RangedEntity implements Tickable{
    protected float speed;
    private transient RangedAttackTask task;

    public Fireball() {
        super();
        this.setTexture("projectile");
        this.setObjectName("combatFireball");
        this.setHeight(1);
        this.speed = 0.05f;
    }

    public Fireball (float row, float col, int damage, float speed, int range) {
        super(row, col, RenderConstants.PROJECTILE_RENDER, damage, speed, range);
        this.setObjectName("combatFireball");
        this.setTexture("projectile");
    }
}
