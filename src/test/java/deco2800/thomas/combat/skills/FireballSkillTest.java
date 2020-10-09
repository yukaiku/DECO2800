package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.FireballAttackTask;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests the fireball skill.
 */
public class FireballSkillTest extends BaseGDXTest {
    /**
     * Tests that the fireball skill returns valid parameters.
     */
    @Test
    public void testValidConstructor() {
        PlayerPeon mockedPlayer = mock(PlayerPeon.class);
        FireballSkill testSkill = new FireballSkill(mockedPlayer);

        assertEquals(0, testSkill.getCooldownRemaining());
        assertEquals(20, testSkill.getCooldownMax());
        assertNotNull(testSkill.getTexture());
    }

    /**
     * Tests that an exception is thrown when passing null in the constructor.
     */
    @Test (expected = NullPointerException.class)
    public void testInvalidConstructor() {
        new FireballSkill(null);
    }

    /**
     * Tests that a FireballAttackTask is returned by the FireballSkill.
     */
    @Test
    public void testGetTask() throws SkillOnCooldownException {
        PlayerPeon mockedPlayer = mock(PlayerPeon.class);
        FireballSkill testSkill = new FireballSkill(mockedPlayer);

        AbstractTask task = testSkill.getNewSkillTask(0, 0);
        assertTrue(task instanceof FireballAttackTask);
    }
}
