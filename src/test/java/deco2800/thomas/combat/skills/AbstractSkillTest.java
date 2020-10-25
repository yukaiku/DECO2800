package deco2800.thomas.combat.skills;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.tasks.AbstractTask;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests the shared methods of the AbstractSkill class.
 */
public class AbstractSkillTest extends BaseGDXTest {
    /**
     * Implements a base AbstractSkill to test on.
     */
    public class TestSkill extends AbstractSkill {
        private static final int MAX_COOLDOWN = 10;
        private static final String SKILL_ICON = "example";

        // Dummy task to return
        private AbstractTask testTask = null;

        /**
         * Returns (in ticks) how long the full cooldown of this skill is.
         *
         * @return Maximum cooldown of skill.
         */
        @Override
        public int getCooldownMax() {
            return MAX_COOLDOWN;
        }

        @Override
        public void setCooldownMax(int cooldownMax) {

        }


        public void reduceCooldownMax(float percent){
            if (MAX_COOLDOWN > 5){
                Math.round(MAX_COOLDOWN * (1.0f-percent));
            }
        }


        /**
         * Returns a string containing the name of the texture that is used to represent
         * this skill on the skill bar.
         *
         * @return Texture id
         */
        @Override
        public String getTexture() {
            return SKILL_ICON;
        }

        /**
         * Set a dummy task to return for testing purposes in getTask.
         * @param task Dummy task to return.
         */
        public void setDummyTask(AbstractTask task) {
            testTask = task;
        }

        /**
         * Returns a new skill task for this skill.
         *
         * @param targetX X position of mouse in ColRow coordinates.
         * @param targetY Y position of mouse in ColRow coordinates.
         * @return New AbstractTask to execute.
         */
        @Override
        protected AbstractTask getTask(float targetX, float targetY) {
            return testTask;
        }
    }

    /**
     * Tests that the cooldown starts, and is set to CooldownMax.
     */
    @Test
    public void testStartCooldown() {
        TestSkill testSkill = new TestSkill();
        testSkill.startCooldown();

        assertEquals(testSkill.getCooldownMax(), testSkill.getCooldownRemaining());
    }

    /**
     * Tests that the cooldown ticks down during onTick.
     */
    @Test
    public void testCooldownTicks() {
        TestSkill testSkill = new TestSkill();
        testSkill.startCooldown();
        testSkill.onTick(0);

        assertEquals(testSkill.getCooldownMax() - 1, testSkill.getCooldownRemaining());

        for (int i = 0; i < 11; i++) {
            testSkill.onTick(0);
        }

        assertEquals(0, testSkill.getCooldownRemaining());
    }

    /**
     * Tests that a task is returned, and the cooldown invoked when getNewSkillTask is called.
     */
    @Test
    public void testGetNewSkillTask() throws SkillOnCooldownException {
        TestSkill testSkill = new TestSkill();
        AbstractTask mockedTask = mock(AbstractTask.class);
        testSkill.setDummyTask(mockedTask);

        assertEquals(mockedTask, testSkill.getNewSkillTask(0, 0));
        assertEquals(testSkill.getCooldownMax(), testSkill.getCooldownRemaining());
    }

    /**
     * Tests that the SkillOnCooldownException is thrown if trying to use a skill
     * while cooldown > 0.
     */
    @Test(expected = SkillOnCooldownException.class)
    public void testCooldownException() throws SkillOnCooldownException {
        TestSkill testSkill = new TestSkill();
        testSkill.startCooldown();
        testSkill.getNewSkillTask(0, 0);
    }
}
