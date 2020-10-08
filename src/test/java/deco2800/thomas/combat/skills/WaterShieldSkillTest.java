package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.tasks.combat.WaterShieldTask;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class WaterShieldSkillTest extends BaseGDXTest {
    /**
     * Tests that the water shield skill returns valid parameters.
     */
    @Test
    public void testValidConstructor() {
        PlayerPeon playerPeon = mock(PlayerPeon.class);
        WaterShieldSkill waterShieldSkill = new WaterShieldSkill(playerPeon);

        assertNotNull(waterShieldSkill.getTexture());
    }

    /**
     * Tests that an exception is thrown when passing null in the constructor.
     */
    @Test(expected = NullPointerException.class)
    public void testInvalidConstructor() {
        new WaterShieldSkill(null);
    }

    /**
     * Test get a corresponding task
     */
    @Test
    public void testGetTask() {
        PlayerPeon playerPeon = mock(PlayerPeon.class);
        WaterShieldSkill waterShieldSkill = new WaterShieldSkill(playerPeon);

        assertTrue(waterShieldSkill.getTask(0, 0) instanceof WaterShieldTask);
    }
}
