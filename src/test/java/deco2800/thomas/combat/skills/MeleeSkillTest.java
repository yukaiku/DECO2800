package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.Peon;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests the melee skill.
 */
public class MeleeSkillTest extends BaseGDXTest {
    /**
     * Tests that the melee skill returns valid parameters.
     */
    @Test
    public void testValidConstructor() {
        Peon mockedEntity = mock(Peon.class);
        MeleeSkill testSkill = new MeleeSkill(mockedEntity);

        assertEquals(0, testSkill.getCooldownRemaining());
        assertEquals(10, testSkill.getCooldownMax());
        assertNull(testSkill.getTexture());
    }

    /**
     * Tests that an exception is thrown when passing null in the constructor.
     */
    @Test (expected = NullPointerException.class)
    public void testInvalidConstructor() {
        new MeleeSkill(null);
    }
}
