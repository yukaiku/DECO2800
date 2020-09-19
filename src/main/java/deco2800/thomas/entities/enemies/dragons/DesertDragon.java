package deco2800.thomas.entities.enemies.dragons;

import com.badlogic.gdx.graphics.g2d.Animation;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.attacks.DesertFireball;
import deco2800.thomas.entities.attacks.VolcanoFireball;
import deco2800.thomas.entities.enemies.Dragon;
import deco2800.thomas.entities.enemies.Variation;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class DesertDragon extends Dragon {
    public DesertDragon(int health, float speed, int orbNumber) {
        super(health, speed, orbNumber);
        this.variation = Variation.DESERT;
        this.identifier = "dragonDesert";
        this.setObjectName("Chuzzinoath");
        this.dragonIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames(identifier + "Idle"));
    }

    @Override
    public void summonRangedAttack() {
        DesertFireball.spawn(this.getCol(), this.getRow(), getTarget().getCol(),
                getTarget().getRow(), 10, 0.1f, 60, EntityFaction.Evil);
    }
}
