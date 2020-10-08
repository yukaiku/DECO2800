package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.attacks.HealEffect;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;

public class HealTask extends AbstractTask {
    /* The amount of restoreHealth */
    private final int restoreHealth;
    private boolean complete = false;

    /**
     * Creates an instance of the HealTask which spawns the HealEffect entity
     * for the parent entity.
     *
     * @param entity   Parent entity of shield
     * @param restoreHealth The amount of restoreHealth
     */
    public HealTask(AbstractEntity entity, int restoreHealth) {
        super(entity);
        this.restoreHealth = restoreHealth;
    }

    /**
     * Spawns a HealEffect into the game world.
     */
    private void spawn() {
        HealEffect healEffect = new HealEffect((Peon) entity, this.restoreHealth);
        GameManager.get().getWorld().addEntity(healEffect);
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    /**
     * On tick is called one time to spawn the HealEffect.
     *
     * @param tick Current game tick
     */
    @Override
    public void onTick(long tick) {
        // Spawn a heal effect entity
        spawn();

        // Task complete
        this.complete = true;
    }
}