package deco2800.thomas.managers;

import deco2800.thomas.combat.*;
import deco2800.thomas.entities.agent.PlayerPeon;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The PlayerManager tracks player progression through the game,
 * for example it tracks skills acquired, Polyhedron configuration etc.
 */
public class PlayerManager extends AbstractManager {
    /**
     * Maximum number of skills the wizard can use at a time.
     */
    public static final int MAX_WIZARD_SKILLS = 5;

    /* Current skills the player has access to. */
    private final List<WizardSkills> currentWizardSkills;
    private KnightSkills currentKnightSkill;

    /**
     * Creates a new instance of the PlayerManager, where the player
     * is not configured.
     */
    public PlayerManager() {
        currentWizardSkills = new CopyOnWriteArrayList<>();

        // TEMPORARY Default to fire wizard and fire knight
        setWizard(Wizard.FIRE);
        setKnight(Knight.WATER);
    }

    /**
     * Resets the player to a blank slate with no configuration.
     */
    public void resetPlayer() {
        currentWizardSkills.clear();
    }

    /**
     * Sets the player's wizard, and grants the default skill for that wizard.
     * @param wizard Wizard to set.
     */
    public void setWizard(Wizard wizard) {
        currentWizardSkills.add(PlayerSkills.getDefaultWizardSkill(wizard));
    }

    /**
     * Sets the player's knight, and grants the default skill for that knight.
     * @param knight Knight to set.
     */
    public void setKnight(Knight knight) {
        currentKnightSkill = PlayerSkills.getDefaultKnightSkill(knight);
    }

    /**
     * Grants a new Wizard skill to the player. If the player already has MAX_WIZARD_SKILLS
     * then this method will silently fail.
     * @param skill Skill to grant.
     */
    public void grantWizardSkill(WizardSkills skill) {
        if (currentWizardSkills.size() < MAX_WIZARD_SKILLS) {
            currentWizardSkills.add(skill);
            ((PlayerPeon)GameManager.get().getWorld().getPlayerEntity()).updatePlayerSkills();
        }
    }

    /**
     * Returns a cloned copy of the current wizard skills available to the player.
     * @return List of current wizard skills.
     */
    public List<WizardSkills> getCurrentWizardSkills() {
        return new CopyOnWriteArrayList<>(currentWizardSkills);
    }

    /**
     * Returns the current knight skill available to the player.
     * @return Current knight skill.
     */
    public KnightSkills getCurrentKnightSkill() {
        return currentKnightSkill;
    }
}
