package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.Peon;
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
}
