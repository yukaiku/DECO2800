package deco2800.thomas.tasks.combat;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.attacks.HealEffect;
import deco2800.thomas.entities.attacks.WaterShield;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.AbstractTask;

public class HealTask extends AbstractTask {
    /* Lifetime of the effect */
    private final int restoreHealth;
    private boolean complete = false;

    /**
     * Creates an instance of the WaterShieldTask which spawns the WaterShield entity
     * for the parent entity.
     *
     * @param entity   Parent entity of shield
     * @param restoreHealth The life time of the effect
     */
    public HealTask(AbstractEntity entity, int restoreHealth) {
        super(entity);
        this.restoreHealth = restoreHealth;
    }

    /**
     * Spawns a watershield into the game world.
     */
    private void spawn() {
        HealEffect waterShield = new HealEffect((Peon) entity, this.restoreHealth);
        GameManager.get().getWorld().addEntity(waterShield);
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
     * On tick is called one time to spawn the WaterShield.
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