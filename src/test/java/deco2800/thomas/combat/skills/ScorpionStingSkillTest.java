package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.AbstractTask;
import deco2800.thomas.tasks.combat.ScorpionStingAttackTask;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Tests the ScorpionSting skill.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class ScorpionStingSkillTest extends BaseGDXTest {
    private AbstractEntity mockedEntity;
    private GameManager mockedGameManager;
    private TextureManager textureManager;

    /**
     * Mocks classes for these tests.
     */
    @Before
    public void setup() {
        // Mocks an abstract entity
        mockedEntity = mock(AbstractEntity.class);
        when(mockedEntity.getCol()).thenReturn(0f);
        when(mockedEntity.getRow()).thenReturn(0f);
        when(mockedEntity.getFaction()).thenReturn(EntityFaction.Ally);

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
     * Tests that the fireball skill returns valid parameters.
     */
    @Test
    public void testValidConstructor() {
        ScorpionStingSkill testSkill = new ScorpionStingSkill(mockedEntity);

        assertEquals(0, testSkill.getCooldown());
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
     * Tests that the correct task is returned when getting task.
     */
    @Test
    public void testValidCombatTask() {
        try {
            // Create skill, and create new task
            ScorpionStingSkill testSkill = new ScorpionStingSkill(mockedEntity);
            AbstractTask task = testSkill.getNewSkillTask(10f, 10f);

            assertTrue(task instanceof ScorpionStingAttackTask);
        } catch (SkillOnCooldownException e) {
            fail("Unexpected SkillOnCooldownException.");
        }
    }

    /**
     * Tests that the cooldown is started when getting task, and is of
     * correct values. Also tests that it countdowns.
     */
    @Test
    public void testCooldown() {
        try {
            // Create skill, and create new task
            ScorpionStingSkill testSkill = new ScorpionStingSkill(mockedEntity);
            testSkill.getNewSkillTask(10f, 10f);

            assertEquals(testSkill.getCooldownMax(), testSkill.getCooldown());
            testSkill.onTick(0);
            assertEquals(testSkill.getCooldownMax() - 1, testSkill.getCooldown());
        } catch (SkillOnCooldownException e) {
            fail("Unexpected SkillOnCooldownException.");
        }
    }

    /**
     * Tests that the exception SkillOnCooldownException is thrown when trying to get
     * a new task, while the skill is on cooldown.
     */
    @Test (expected = SkillOnCooldownException.class)
    public void testSkillOnCooldownNewTaskException() throws SkillOnCooldownException {
        // Create skill, and create new task, perform a tick
        ScorpionStingSkill testSkill = new ScorpionStingSkill(mockedEntity);
        testSkill.getNewSkillTask(10f, 10f);
        testSkill.onTick(0);

        // Try create new task, should throw exception
        testSkill.getNewSkillTask(10f, 10f);
    }
}
