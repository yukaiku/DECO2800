package deco2800.thomas.entities.enemies;


import deco2800.thomas.entities.AgentEntity;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MovementTask;

/**
 * A class that defines an implementation of an orc.
 * Orcs are wild enemies and they can be automatically spawned inside EnemyManager.
 */
public class Orc extends Monster implements AggressiveEnemy {

    private int tick;

    // the radius for detecting the target
    private final int awareRadius;

    public Orc(int height, float speed, int health) {
        super("Orc", "spacman_blue", height, speed, health, true);
        this.tick = 60;
        this.awareRadius = 8;
    }

    /**
     * Initialise an orc with custom textures (for different variations)
     */
    public Orc(int height, float speed, int health, String texture) {
        this(height, speed, health);
        this.setTexture(texture);
    }

    /**
     * Detects the target with the given aware radius.
     */
    public void detectTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && Math.sqrt(Math.pow(Math.round(super.getCol()) - Math.round(player.getCol()), 2) +
                Math.pow(Math.round(super.getRow()) - Math.round(player.getRow()), 2)) < awareRadius) {
            super.setTarget((PlayerPeon) player);
            setTask(new MovementTask(this, super.getTarget().getPosition()));
        }
    }

    @Override
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).removeWildEnemy(this);
    }

    @Override
    public void onTick(long i) {
        // update target following path every 60 ticks
        if (++tick > 60) {
            if (super.getTarget() != null) {
                setTask(new MovementTask(this, super.getTarget().getPosition()));
            }
            tick = 0;
        }
        // detect targets for every tick
        if (super.getTarget() == null) {
            detectTarget();
        }
        // execute tasks
        if (getTask() != null && getTask().isAlive()) {
            getTask().onTick(i);
            if (getTask().isComplete()) {
                setTask(null);
            }
        }
    }

    @Override
    public Orc deepCopy() {
        return new Orc(super.getHeight(), super.getSpeed(), super.getMaxHealth(), super.getTexture());
    }
}
