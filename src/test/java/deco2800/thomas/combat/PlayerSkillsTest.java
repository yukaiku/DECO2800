package deco2800.thomas.combat;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests the PlayerSkills class.
 */
public class PlayerSkillsTest extends BaseGDXTest {
    /**
     * Tests that the defaultWizardSkill returns for all wizard types.
     */
    @Test
    public void defaultWizardSkillTest() {
        for (Wizard wizard : Wizard.values()) {
            assertNotNull(PlayerSkills.getDefaultWizardSkill(wizard));
        }
    }

    /**
     * Tests that the defaultKnightSkill returns for all knight types.
     */
    @Test
    public void defaultKnightSkillTest() {
        for (Knight knight : Knight.values()) {
            assertNotNull(PlayerSkills.getDefaultKnightSkill(knight));
        }
    }

    /**
     * Tests that a valid wizard skill returns for all WizardSkills.
     */
    @Test
    public void allWizardSkillsAreValidTest() {
        PlayerPeon mockedPlayer = mock(PlayerPeon.class);
        for (WizardSkills skill : WizardSkills.values()) {
            assertNotNull(PlayerSkills.getNewWizardSkill(mockedPlayer, skill));
        }
    }

    /**
     * Tests that a valid knight skill returns for all KnightSkills.
     */
    @Test
    public void allKnightSkillsAreValidTest() {
        PlayerPeon mockedPlayer = mock(PlayerPeon.class);
        for (KnightSkills skill : KnightSkills.values()) {
            assertNotNull(PlayerSkills.getNewKnightSkill(mockedPlayer, skill));
        }
    }
}
