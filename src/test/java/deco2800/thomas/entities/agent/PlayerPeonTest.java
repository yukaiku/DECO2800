package deco2800.thomas.entities.agent;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.KnightSkills;
import deco2800.thomas.combat.Wizard;
import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.combat.skills.AbstractSkill;
import deco2800.thomas.combat.skills.FireBombSkill;
import deco2800.thomas.combat.skills.FireballSkill;
import deco2800.thomas.combat.skills.IceballSkill;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.managers.PlayerManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.tasks.movement.MovementTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class PlayerPeonTest extends BaseGDXTest {
    @Test
    public void testPlayerSet() {
        PlayerPeon p = new PlayerPeon(0, 0, 1, 10);
        assertThat("", p.getTexture(), is(equalTo("player_right")));
    }

    @Test
    public void testConstructor() {
        PlayerPeon p = new PlayerPeon(1, 1, 1, 10);
        assertThat("", p.getCol(), is(equalTo(1f)));
        assertThat("", p.getRow(), is(equalTo(1f)));
        assertThat("", p.getSpeed(), is(equalTo(1f)));
        assertThat("", p.getTexture(), is(equalTo("player_right")));
    }

    /**
     * Test player press WSAD keys to move
     */
    @Test
    public void testPlayerPressKeyToMove() {
        PlayerPeon playerPressW = new PlayerPeon(10f, 10f, 0.15f, 10);
        PlayerPeon playerPressA = new PlayerPeon(10f, 10f, 0.15f, 10);
        PlayerPeon playerPressS = new PlayerPeon(10f, 10f, 0.15f, 10);
        PlayerPeon playerPressD = new PlayerPeon(10f, 10f, 0.15f, 10);

        playerPressW.notifyKeyDown(Input.Keys.W);
        playerPressA.notifyKeyDown(Input.Keys.A);
        playerPressS.notifyKeyDown(Input.Keys.S);
        playerPressD.notifyKeyDown(Input.Keys.D);

        assertTrue(playerPressW.isDirectionKeyActive(MovementTask.Direction.UP));
        assertTrue(playerPressS.isDirectionKeyActive(MovementTask.Direction.DOWN));
        assertTrue(playerPressA.isDirectionKeyActive(MovementTask.Direction.LEFT));
        assertTrue(playerPressD.isDirectionKeyActive(MovementTask.Direction.RIGHT));

        assertEquals(MovementTask.Direction.UP, playerPressW.getMovementStack().peek());
        assertEquals(MovementTask.Direction.DOWN, playerPressS.getMovementStack().peek());
        assertEquals(MovementTask.Direction.LEFT, playerPressA.getMovementStack().peek());
        assertEquals(MovementTask.Direction.RIGHT, playerPressD.getMovementStack().peek());

        assertNotNull(playerPressW.getMovementTask());
        assertNotNull(playerPressS.getMovementTask());
        assertNotNull(playerPressA.getMovementTask());
        assertNotNull(playerPressD.getMovementTask());

        assertTrue(playerPressW.getMovementTask() instanceof MovementTask);
        assertTrue(playerPressS.getMovementTask() instanceof MovementTask);
        assertTrue(playerPressA.getMovementTask() instanceof MovementTask);
        assertTrue(playerPressD.getMovementTask() instanceof MovementTask);

        /* When the player release the key*/
        playerPressW.notifyKeyUp(Input.Keys.W);
        playerPressA.notifyKeyUp(Input.Keys.A);
        playerPressS.notifyKeyUp(Input.Keys.S);
        playerPressD.notifyKeyUp(Input.Keys.D);

        assertFalse(playerPressW.isDirectionKeyActive(MovementTask.Direction.UP));
        assertFalse(playerPressA.isDirectionKeyActive(MovementTask.Direction.DOWN));
        assertFalse(playerPressS.isDirectionKeyActive(MovementTask.Direction.LEFT));
        assertFalse(playerPressD.isDirectionKeyActive(MovementTask.Direction.RIGHT));
    }

    /**
     * Test player press key not in WASD keys
     */
    @Test
    public void testPlayerPressKeyNotBelongToWASD() {
        PlayerPeon playerPeon = new PlayerPeon(10f, 10f, 0.15f, 10);
        playerPeon.notifyKeyDown(Input.Keys.J);

        assertEquals(MovementTask.Direction.NONE, playerPeon.getMovingDirection());
        assertNull(playerPeon.getMovementTask());
    }

    /**
     * Test player press serial keys in WASD keys
     */
    @Test
    public void testPlayerPressSerialKeys() {
        PlayerPeon playerPeon = new PlayerPeon(10f, 10f, 0.15f, 10);

        playerPeon.notifyKeyDown(Input.Keys.W);
        playerPeon.notifyKeyDown(Input.Keys.D);
        playerPeon.notifyKeyUp(Input.Keys.W);
        playerPeon.notifyKeyDown(Input.Keys.S);
        playerPeon.notifyKeyUp(Input.Keys.D);

        assertEquals(MovementTask.Direction.DOWN, playerPeon.getMovementStack().pop());
        assertEquals(MovementTask.Direction.RIGHT, playerPeon.getMovementStack().pop());
        assertEquals(MovementTask.Direction.UP, playerPeon.getMovementStack().pop());

        assertNotNull(playerPeon.getMovementTask());

        assertFalse(playerPeon.isDirectionKeyActive(MovementTask.Direction.UP));
        assertFalse(playerPeon.isDirectionKeyActive(MovementTask.Direction.RIGHT));
        assertTrue(playerPeon.isDirectionKeyActive(MovementTask.Direction.DOWN));

        playerPeon.notifyKeyUp(Input.Keys.S);
        assertFalse(playerPeon.isDirectionKeyActive(MovementTask.Direction.DOWN));
    }

    /**
     * Test when added 2 new skills into skill list and press button num2. The
     * active skill should be the new one with index 1
     */
    @Test
    public void testPlayerSwapToAValidIndexSkill() {
        PlayerPeon playerPeon = new PlayerPeon(10f, 10f, 0.15f, 10);
        playerPeon.getWizardSkills().add(new FireballSkill(playerPeon));
        playerPeon.getWizardSkills().add(new FireballSkill(playerPeon));
        playerPeon.notifyKeyDown(Input.Keys.NUM_2);

        assertEquals(1, playerPeon.getActiveWizardSkill());
    }

    /**
     * Test swap to an invalid skill index. In this case, we swap to the third skill while
     * we only have 1 skill int the skill list.
     */
    @Test
    public void testPlayerSwapToAnInvalidIndexSkill() {
        PlayerPeon playerPeon = new PlayerPeon(10f, 10f, 0.15f, 10);
        playerPeon.notifyKeyDown(Input.Keys.NUM_6);

        assertEquals(0, playerPeon.getActiveWizardSkill());
    }

    /**
     * Test getting dialogue through PlayerPeon class works
     */
    @Test
    public void testGetDialogue() {
        assertEquals(PlayerPeon.getDialogue("roar"), "Roar!!!");
    }

    /**
     * Tests that PlayerPeon updatePlayerSkills() creates a list of WizardSkills
     * that match correctly to the list supplied from PlayerManager.
     */
    @Test
    public void testUpdatePlayerSkills() {
        // Mock PlayerManager to return 2 wizard skills and a knight skill
        PlayerManager playerManager = mock(PlayerManager.class);
        PowerMockito.mockStatic(GameManager.class);
        when(GameManager.getManagerFromInstance(PlayerManager.class)).thenReturn(playerManager);
        when(playerManager.getCurrentWizardSkills()).thenReturn(new CopyOnWriteArrayList<WizardSkills>() {{
            add(WizardSkills.FIREBALL);
            add(WizardSkills.ICEBALL);
        }});
        when(playerManager.getCurrentKnightSkill()).thenReturn(KnightSkills.FIREBOMB);

        // Mock texture manager to prevent null reference
        TextureManager textureManager = mock(TextureManager.class);
        Texture mockedTexture = mock(Texture.class);
        when(mockedTexture.getWidth()).thenReturn(1);
        when(mockedTexture.getHeight()).thenReturn(1);
        Array<TextureRegion> playerStand = new Array<>();
        playerStand.add(new TextureRegion(new Texture("resources/combat/move_right.png"), 262, 256));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(playerStand);
        when(textureManager.getTexture(anyString())).thenReturn(mockedTexture);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);

        // Mock input manager to prevent null reference
        InputManager inputManager = mock(InputManager.class);
        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(inputManager);

        // Create PlayerPeon and update skills
        PlayerPeon peon = new PlayerPeon(10f, 10f, 0.15f, 10);
        peon.updatePlayerSkills();
        List<AbstractSkill> wizardSkills = peon.getWizardSkills();
        AbstractSkill knightSkill = peon.getMechSkill();

        // Check that all skills correctly match their respective abstract skill class
        assertEquals(2, wizardSkills.size());
        assertTrue(wizardSkills.get(0) instanceof FireballSkill);
        assertTrue(wizardSkills.get(1) instanceof IceballSkill);
        assertTrue(knightSkill instanceof FireBombSkill);
    }
}
