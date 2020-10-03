package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests the fire bomb skill.
 */
public class FireBombSkillTest extends BaseGDXTest {
    /**
     * Tests that the fireball skill returns valid parameters.
     */
    @Test
    public void testValidConstructor() {
        PlayerPeon mockedEntity = mock(PlayerPeon.class);
        FireBombSkill testSkill = new FireBombSkill(mockedEntity);

        assertEquals(0, testSkill.getCooldownRemaining());
        assertEquals(160, testSkill.getCooldownMax());
        assertNotNull(testSkill.getTexture());
    }

    /**
     * Tests that an exception is thrown when passing null in the constructor.
     */
    @Test (expected = NullPointerException.class)
    public void testInvalidConstructor() {
        new FireballSkill(null);
    }
}
