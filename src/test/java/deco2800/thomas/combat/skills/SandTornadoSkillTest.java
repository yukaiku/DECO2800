package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class SandTornadoSkillTest extends BaseGDXTest {

    private Peon mockedEntity;
    private GameManager mockedGameManager;
    private TextureManager textureManager;

    @Before
    public void setUp() throws Exception {
        // Mocks an peon entity
        mockedEntity = mock(Peon.class);
        when(mockedEntity.getCol()).thenReturn(0f);
        when(mockedEntity.getRow()).thenReturn(0f);
        when(mockedEntity.getFaction()).thenReturn(EntityFaction.ALLY);

        // Mock the game manager
        mockedGameManager = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(mockedGameManager);

        // Mock texture manager
        textureManager = mock(TextureManager.class);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);

        // Mock an abstract world
        AbstractWorld mockedWorld = mock(AbstractWorld.class);
        when(mockedGameManager.getWorld()).thenReturn(mockedWorld);
    }

    /**
     * Tests that the sand tornado skill returns valid parameters.
     */
    @Test
    public void testValidConstructor() {
        SandTornadoSkill testSkill = new SandTornadoSkill(mockedEntity);

        assertEquals(0, testSkill.getCooldownRemaining());
        assertEquals(30, testSkill.getCooldownMax());
        assertEquals("sandTornadoIcon",testSkill.getTexture());
    }

    /**
     * Tests that an exception is thrown when passing null in the constructor.
     */
    @Test (expected = NullPointerException.class)
    public void testInvalidConstructor() {
        new SandTornadoSkill(null);
    }
}