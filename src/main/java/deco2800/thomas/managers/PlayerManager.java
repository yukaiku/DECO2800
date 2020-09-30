package deco2800.thomas.managers;

import java.util.HashMap;
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
    public final static int MAX_WIZARD_SKILLS = 5;

    /**
     * Possible skills the player can acquire as a Wizard.
     */
    public enum WizardSkills {
        FIREBALL,
        ICEBALL,
        STING
    }

    /**
     * Possible wizards the player can choose from. Each grants
     * a unique skill.
     */
    public enum Wizard {
        FIRE,
        WATER
    }

    /**
     * Possible skills the player can acquire as a Knight.
     */
    public enum KnightSkills {
        FIREBOMB
    }

    /**
     * Possible knights the player can choose from. Each grants
     * a unique skill.
     */
    public enum Knight {
        FIRE
    }

    /* Current skills the player has access to. */
    private final List<WizardSkills> currentWizardSkills;
    private KnightSkills currentKnightSkill;

    /* Map default skills of Wizards */
    private final static HashMap<Wizard, WizardSkills> DEFAULT_WIZARD_SKILLS = new HashMap<>() {{
        put(Wizard.FIRE, WizardSkills.FIREBALL);
        put(Wizard.WATER, WizardSkills.ICEBALL);
    }};

    /* Map default skills of Knights */
    private final static HashMap<Knight, KnightSkills> DEFAULT_KNIGHT_SKILLS = new HashMap<>() {{
        put(Knight.FIRE, KnightSkills.FIREBOMB);
    }};

    /**
     * Creates a new instance of the PlayerManager, where the player
     * is not configured.
     */
    public PlayerManager() {
        currentWizardSkills = new CopyOnWriteArrayList<>();
    }

    /**
     * Resets the player to a blank slate with no configuration.
     */
    public void resetPlayer() {
        currentWizardSkills.clear();
    }

    /**
     * Sets the player's wizard, and grants the default skill for that wizard.
     * @param wizard Wizard to set. Cannot be Wizard.NONE.
     */
    public void setWizard(Wizard wizard) {
        currentWizardSkills.add(DEFAULT_WIZARD_SKILLS.get(wizard));
    }

    /**
     * Sets the player's knight, and grants the default skill for that knight.
     * @param knight Knight to set. Cannot be Knight.NONE.
     */
    public void setKnight(Knight knight) {
        currentKnightSkill = DEFAULT_KNIGHT_SKILLS.get(knight);
    }

    /**
     * Grants a new Wizard skill to the player. If the player already has MAX_WIZARD_SKILLS
     * then this method will silently fail.
     * @param skill Skill to grant.
     */
    public void grantWizardSkill(WizardSkills skill) {
        if (currentWizardSkills.size() < MAX_WIZARD_SKILLS) {
            currentWizardSkills.add(skill);
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
