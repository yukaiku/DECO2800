package deco2800.thomas.managers;

import deco2800.thomas.combat.Knight;
import deco2800.thomas.combat.PlayerSkills;
import deco2800.thomas.combat.Wizard;
import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the PlayerManager class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class PlayerManagerTest {
    @Before
    public void setup() {
        // Mock PlayerPeon, AbstractWorld, and GameManager for these tests
        PlayerPeon mockedPlayer = mock(PlayerPeon.class);

        AbstractWorld mockedWorld = mock(AbstractWorld.class);
        when(mockedWorld.getPlayerEntity()).thenReturn(mockedPlayer);

        GameManager mockedGameManager = mock(GameManager.class);
        when(mockedGameManager.getWorld()).thenReturn(mockedWorld);

        PowerMockito.mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(mockedGameManager);
    }

    /**
     * Tests that reset player clears all wizard skills.
     */
    @Test
    public void testResetPlayer() {
        PlayerManager testManager = new PlayerManager();
        testManager.grantWizardSkill(WizardSkills.FIREBALL);
        testManager.resetPlayer();
        assertEquals(0, testManager.getCurrentWizardSkills().size());
    }

    /**
     * Tests that setting the wizard grants the correct skill for all wizards.
     */
    @Test
    public void testSetWizard() {
        PlayerManager testManager = new PlayerManager();
        for (Wizard wizard : Wizard.values()) {
            testManager.resetPlayer();
            testManager.setWizard(wizard);

            List<WizardSkills> wizardSkills = testManager.getCurrentWizardSkills();
            assertEquals(1, wizardSkills.size());
            assertEquals(PlayerSkills.getDefaultWizardSkill(wizard), wizardSkills.get(0));
        }
    }

    /**
     * Tests that setting the knight grants the correct skill for all knights.
     */
    @Test
    public void testSetKnight() {
        PlayerManager testManager = new PlayerManager();
        for (Knight knight : Knight.values()) {
            testManager.resetPlayer();
            testManager.setKnight(knight);

            assertEquals(PlayerSkills.getDefaultKnightSkill(knight), testManager.getCurrentKnightSkill());
        }
    }

    /**
     * Tests that granting a new wizard skill grants 1 more wizard skill, until MAX_WIZARD_SKILLS is reached.
     */
    @Test
    public void testGrantWizardSkill() {
        PlayerManager testManager = new PlayerManager();
        testManager.resetPlayer();
        for (int i = 0; i < PlayerManager.MAX_WIZARD_SKILLS; i++) {
            testManager.grantWizardSkill(WizardSkills.FIREBALL);
            assertEquals(i + 1, testManager.getCurrentWizardSkills().size());
        }
    }

    /**
     * Tests that getCurrentWizardSkills returns a listing of correct skills.
     */
    @Test
    public void testGetCurrentWizardSkills() {
        PlayerManager testManager = new PlayerManager();
        testManager.resetPlayer();
        testManager.grantWizardSkill(WizardSkills.FIREBALL);
        testManager.grantWizardSkill(WizardSkills.ICEBALL);
        testManager.grantWizardSkill(WizardSkills.STING);

        List<WizardSkills> skillsInList = testManager.getCurrentWizardSkills();
        assertEquals(3, skillsInList.size());
        assertEquals(WizardSkills.FIREBALL, skillsInList.get(0));
        assertEquals(WizardSkills.ICEBALL, skillsInList.get(1));
        assertEquals(WizardSkills.STING, skillsInList.get(2));
    }
}
