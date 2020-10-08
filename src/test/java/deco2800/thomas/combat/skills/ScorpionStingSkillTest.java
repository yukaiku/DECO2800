package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.ScorpionStingAttackTask;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests the ScorpionSting skill.
 */
public class ScorpionStingSkillTest extends BaseGDXTest {
    /**
     * Tests that the fireball skill returns valid parameters.
     */
    @Test
    public void testValidConstructor() {
        Peon mockedEntity = mock(Peon.class);
        ScorpionStingSkill testSkill = new ScorpionStingSkill(mockedEntity);

        assertEquals(0, testSkill.getCooldownRemaining());
        assertEquals(50, testSkill.getCooldownMax());
        assertNotNull(testSkill.getTexture());
    }

    /**
     * Tests that an exception is thrown when passing null in the constructor.
     */
    @Test (expected = NullPointerException.class)
    public void testInvalidConstructor() {
        new ScorpionStingSkill(null);
    }

    /**
     * Tests that a ScorpionStingAttackTask is returned by the ScorpionStingSkill.
     */
    @Test
    public void testGetTask() throws SkillOnCooldownException {
        PlayerPeon mockedPlayer = mock(PlayerPeon.class);
        ScorpionStingSkill testSkill = new ScorpionStingSkill(mockedPlayer);

        AbstractTask task = testSkill.getNewSkillTask(0, 0);
        assertTrue(task instanceof ScorpionStingAttackTask);
    }
}
