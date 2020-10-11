package deco2800.thomas.entities.enemies.monsters;

import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.enemies.EnemyIndex;
import deco2800.thomas.managers.EnemyManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.PlayerManager;

public class ImmuneOrc extends Orc {
    public ImmuneOrc() {
        super(EnemyIndex.Variation.DESERT, 100, 0.2f, 10, 100, 2, 0);
        this.setObjectName("ImmuneOrc");
    }

    @Override
    public void death() {
        GameManager.getManagerFromInstance(EnemyManager.class).
                removeWildEnemy(this);
        GameManager.getManagerFromInstance(PlayerManager.class).grantWizardSkill(WizardSkills.ICEBALL);
    }

}
