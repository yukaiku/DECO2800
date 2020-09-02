package deco2800.thomas.entities.enemies;


import deco2800.thomas.entities.AgentEntity;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.EnemyUtil;

/**
 * A class that defines an implementation of an orc.
 *
 * Orcs are wild enemies. They can be automatically spawned using EnemyManager.
 */
public class Orc extends Monster implements AggressiveEnemy {

    private int tickFollowing = 60;
    private int tickDetecting = 15;
    private int awarenessRange = 8;

    String textureFacingUp = "spacman_blue";
    String textureFacingRight = "spacman_green";
    String textureFacingDown = "spacman_red";
    String textureFacingLeft = "spacman_yellow";


    public Orc(int height, float speed, int health) {
        super("Orc", "spacman_blue", height, speed, health, true);
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
        if (player != null && EnemyUtil.playerInRadius(this, player,
                awarenessRange)) {
            super.setTarget((PlayerPeon) player);
            setTask(new MovementTask(this,
                    super.getTarget().getPosition()));
        }
    }

    /**
     * Stops targeting the player once they leave the awareness
     */
    public void discardTarget() {
        AgentEntity player = GameManager.get().getWorld().getPlayerEntity();
        if (player != null && !EnemyUtil.playerInRadius(this, player,
                awarenessRange)) {
            super.setTarget(null);
            setTask(null);
        }
    }

    @Override
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).
                removeWildEnemy(this);
    }

    @Override
    public void onTick(long i) {
        // update target following path every 1 second (60 ticks)
        if (++tickFollowing > 60) {
            if (super.getTarget() != null) {
                setTask(new MovementTask(this, super.getTarget().
                        getPosition()));
            }
            tickFollowing = 0;
        }
        // checks player position every 0.25 second (15 ticks)
        if (++tickDetecting > 15) {
            if (super.getTarget() == null) {
                detectTarget();
            } else {
                discardTarget();
            }
            tickDetecting = 0;
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
        return new Orc(super.getHeight(), super.getSpeed(),
                super.getMaxHealth(), super.getTexture());
    }
}
