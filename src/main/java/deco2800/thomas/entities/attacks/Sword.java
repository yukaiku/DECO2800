package deco2800.thomas.entities.attacks;

import deco2800.thomas.Tickable;
import deco2800.thomas.entities.RenderConstants;

public class Sword extends MeleeEntity implements Tickable {

    public Sword() {
        super();
        this.setTexture("projectile");
        this.setObjectName("combatSword");
        this.setHeight(1);
    }

    public Sword (float row, float col, int damage, int range) {
        super(row, col, RenderConstants.PROJECTILE_RENDER, damage, range);
        this.setObjectName("combatSword");
        this.setTexture("projectile");
    }

}
