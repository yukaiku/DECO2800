package deco2800.thomas.entities.attacks;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.CombatEntity;
import deco2800.thomas.entities.RenderConstants;
import deco2800.thomas.managers.CombatManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TaskPool;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;

public class Fireball extends CombatEntity implements Projectile, Tickable {
    protected float speed;
    private MovementTask.Direction movingDirection = MovementTask.Direction.NONE;
    private transient AbstractTask combatTask;
    private transient AbstractTask movementTask;

    public Fireball() {
        super();
        this.setTexture("projectile");
        this.setObjectName("Peon");
        this.setHeight(1);
        this.speed = 0.05f;
    }

    public Fireball (float row, float col, int damage, float speed, SquareVector destination) {
        super(row, col, RenderConstants.PROJECTILE_RENDER, damage, speed);
        this.setObjectName("combatFireball");
        this.setTexture("projectile");
        this.movementTask = new MovementTask(this, destination);
    }

    public void setMovementTask(AbstractTask task) {this.movementTask = task;}

    @Override
    public void onTick(long i) {
        if(combatTask != null) {
            if(combatTask.isComplete()) {
                GameManager.get().getManager(CombatManager.class).removeEntity(this);
            }
            combatTask.onTick(i);
        } else {
            combatTask = GameManager.getManagerFromInstance(TaskPool.class).getCombatTask(this);
        }

        if (movementTask != null) {
            if (movementTask.isComplete()) {
                GameManager.get().getManager(CombatManager.class).removeEntity(this);
            }
            movementTask.onTick(i);
        }
    }


}
