package deco2800.thomas.entities.enemies.monsters;

import deco2800.thomas.combat.DamageType;
import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.entities.environment.ExitPortal;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.PlayerManager;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

public class ImmuneOrc extends Orc {
    public ImmuneOrc() {
        super(EnemyIndex.Variation.DESERT, 100, 0.2f, 10, 100, 2, 0);
        this.setObjectName("ImmuneOrc");
    }

    public ImmuneOrc(EnemyIndex.Variation variation, int health, float speed, int damage, int sightRange, int meleeRange,
                     float spawnRate) {
        super(variation, health, speed, damage, sightRange, meleeRange, spawnRate);
        this.setObjectName("ImmuneOrc");
    }

    @Override
    public int applyDamage(int damage, DamageType damageType) {
        if (damageType != DamageType.NOT_IMMUNE) {
            damage = 0;
        }

        return super.applyDamage(damage, damageType);
    }


    @Override
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).
                removeWildEnemy(this);
        GameManager.getManagerFromInstance(PlayerManager.class).grantWizardSkill(WizardSkills.SANDTORNADO);

        AbstractWorld world = GameManager.get().getWorld();
        Tile exitTile = world.getTile(-2, 8f);
        world.addEntity(new ExitPortal(exitTile, false, "portal", "ExitPortal"));
    }

    @Override
    public Orc deepCopy() {
        return new ImmuneOrc(EnemyIndex.Variation.DESERT, getMaxHealth(), getSpeed(), getDamage(), 100, 2, 0);
    }

}
