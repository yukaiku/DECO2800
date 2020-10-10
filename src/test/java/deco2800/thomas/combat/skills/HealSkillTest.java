package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.tasks.combat.HealTask;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HealSkillTest extends BaseGDXTest {
    /**
     * Tests that the heal skill returns valid parameters.
     */
    @Test
    public void testValidConstructor() {
        PlayerPeon playerPeon = mock(PlayerPeon.class);
        HealSkill healSkill = new HealSkill(playerPeon);

        assertNotNull(healSkill.getTexture());
    }

    /**
     * Tests that an exception is thrown when passing null in the constructor.
     */
    @Test(expected = NullPointerException.class)
    public void testInvalidConstructor() {
        new HealSkill(null);
    }

    /**
     * Test get a corresponding task
     */
    @Test
    public void testGetTask() {
        PlayerPeon playerPeon = mock(PlayerPeon.class);
        when(playerPeon.getMaxHealth()).thenReturn(100);
        HealSkill healSkill = new HealSkill(playerPeon);

        assertTrue(healSkill.getTask(0, 0) instanceof HealTask);
    }
}
