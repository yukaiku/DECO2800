package deco2800.thomas.combat;

import deco2800.thomas.combat.skills.*;
import deco2800.thomas.combat.skills.IceBreathSkill;
import deco2800.thomas.entities.agent.Peon;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for mapping Player skill names to Skill Classes.
 */
public final class PlayerSkills {
    /* Constants for skill specific parameters */
    private static final float ICEBALL_SLOW_MODIFIER = 0.5f;
    private static final int ICEBALL_SLOW_DURATION = 2;

    /* Map of default skills for Wizards */
    private static final Map<Wizard, WizardSkills> DEFAULT_WIZARD_SKILLS = new HashMap<>();

    /* Map of default skills for Knights */
    private static final Map<Knight, KnightSkills> DEFAULT_KNIGHT_SKILLS = new HashMap<>();

    /* Map default skills */
    static {
        // Map default Wizard skills
        DEFAULT_WIZARD_SKILLS.put(Wizard.FIRE, WizardSkills.FIREBALL);
        DEFAULT_WIZARD_SKILLS.put(Wizard.WATER, WizardSkills.ICEBALL);

        // Map default Knight skills
        DEFAULT_KNIGHT_SKILLS.put(Knight.FIRE, KnightSkills.FIREBOMB);
        DEFAULT_KNIGHT_SKILLS.put(Knight.WATER, KnightSkills.WATERSHIELD);
    }

    /**
     * Private constructor for utility class.
     */
    private PlayerSkills() {
        // Not called
    }

    /**
     * Returns the default WizardSkill for a given wizard.
     * @param wizard Wizard type to find default skill of
     * @return Default WizardSkill
     */
    public static WizardSkills getDefaultWizardSkill(Wizard wizard) {
        return DEFAULT_WIZARD_SKILLS.get(wizard);
    }

    /**
     * Returns the default KnightSkill for a given knight.
     * @param knight Knight type to find default skill of.
     * @return Default KnightSkill.
     */
    public static KnightSkills getDefaultKnightSkill(Knight knight) {
        return DEFAULT_KNIGHT_SKILLS.get(knight);
    }

    /**
     * Gets a new AbstractSkill for the player from a given WizardSkill.
     * @param parent PlayerPeon.
     * @param skill WizardSkill to get skill class from.
     * @return AbstractSkill for this skill.
     * @throws IllegalArgumentException If there exists no mapping from a WizardSkill to an AbstractSkill.
     */
    public static AbstractSkill getNewWizardSkill(Peon parent, WizardSkills skill) {
        switch (skill) {
            case FIREBALL:
                return new FireballSkill(parent);
            case STING:
                return new ScorpionStingSkill(parent);
            case ICEBALL:
                return new IceballSkill(parent, ICEBALL_SLOW_MODIFIER, ICEBALL_SLOW_DURATION);
            case HEAL:
                return new HealSkill(parent);
            case SANDTORNADO:
                return new SandTornadoSkill(parent);
            case ICEBREATH:
                return new IceBreathSkill(parent, ICEBALL_SLOW_MODIFIER, ICEBALL_SLOW_DURATION);
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Gets a new AbstractSkill for the player from a given KnightSkill.
     * @param parent PlayerPeon.
     * @param skill KnightSkill to get skill class from.
     * @return AbstractSkill for this skill.
     * @throws IllegalArgumentException if there exists no mapping from a KnightSkill to an AbstractSkill.
     */
    public static AbstractSkill getNewKnightSkill(Peon parent, KnightSkills skill) {
        switch(skill) {
            case FIREBOMB:
                return new FireBombSkill(parent);
            case WATERSHIELD:
                return new WaterShieldSkill(parent);
            default:
                throw new IllegalArgumentException();
        }
    }
}
