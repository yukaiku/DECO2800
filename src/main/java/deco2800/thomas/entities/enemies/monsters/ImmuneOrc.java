package deco2800.thomas.entities.enemies.monsters;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.entities.environment.ExitPortal;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.PlayerManager;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

/**
 * A new type of Orc which is immune to all types of damage except for
 * Desert Zone environment damage.
 */
public class ImmuneOrc extends Orc {

    // whether this orc has died yet
    private boolean dead = false;

    /**
     * Creates a new ImmuneOrc with its default stats.
     */
    public ImmuneOrc() {
        super(EnemyIndex.Variation.DESERT, 100, 0.2f, 30, 100, 2, 0);
        this.setObjectName("ImmuneOrc");
    }

    /**
     * Creates a new Immune orc with specified stats.
     */
    public ImmuneOrc(EnemyIndex.Variation variation, int health, float speed, int damage, int sightRange, int meleeRange,
                     float spawnRate) {
        super(variation, health, speed, damage, sightRange, meleeRange, spawnRate);
        this.setObjectName("ImmuneOrc");
    }

    /**
     * Applies damage as normal, but only if it was of the special type NOT_IMMUNE.
     *
     * @param damage The amount of damage to be taken by this AgentEntity.
     * @param damageType The type of damage to apply from DamageType enum.
     * @return The damage applied.
     */
    @Override
    public int applyDamage(int damage, DamageType damageType) {
        if (damageType != DamageType.NOT_IMMUNE) {
            damage = 0;
        }

        return super.applyDamage(damage, damageType);
    }

    /**
     * Handles the Orc's death as normal, but also grants the player a new skill
     * and spawns a portal back to the Desert World.
     */
    @Override
    public void death() {
        if (dead) return;
        dead = true;
        super.death();
        GameManager.getManagerFromInstance(PlayerManager.class).grantWizardSkill(WizardSkills.SANDTORNADO);

        AbstractWorld world = GameManager.get().getWorld();
        Tile exitTile = world.getTile(-2, 8f);
        world.addEntity(new ExitPortal(exitTile, false, "portal", "ExitPortal"));
    }

    /**
     * Creates a deep copy of the Immune Orc.
     *
     * @return A deep copy of the Immune Orc.
     */
    @Override
    public Orc deepCopy() {
        return new ImmuneOrc(EnemyIndex.Variation.DESERT, getMaxHealth(), getSpeed(), getDamage(), 100, 2, 0);
    }

}
