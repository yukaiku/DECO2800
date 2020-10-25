package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.IceballAttackTask;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests the fireball skill.
 */
public class IceballSkillTest extends BaseGDXTest {
    /**
     * Tests that the fireball skill returns valid parameters.
     */
    @Test
    public void testValidConstructor() {
        Peon mockedEntity = mock(Peon.class);
        IceballSkill testSkill = new IceballSkill(mockedEntity, 0.4f, 4);

        assertEquals(0, testSkill.getCooldownRemaining());
        assertEquals(50, testSkill.getCooldownMax());
        assertNotNull(testSkill.getTexture());
    }

    /**
     * Tests that an exception is thrown when passing null in the constructor.
     */
    @Test (expected = NullPointerException.class)
    public void testInvalidConstructor() {
        new IceballSkill(null, 0,0);
    }

    /**
     * Tests that a IceballAttackTask is returned by the IceballSkill.
     */
    @Test
    public void testGetTask() throws SkillOnCooldownException {
        PlayerPeon mockedPlayer = mock(PlayerPeon.class);
        IceballSkill testSkill = new IceballSkill(mockedPlayer, 1, 1);

        AbstractTask task = testSkill.getNewSkillTask(0, 0);
        assertTrue(task instanceof IceballAttackTask);
    }
}
