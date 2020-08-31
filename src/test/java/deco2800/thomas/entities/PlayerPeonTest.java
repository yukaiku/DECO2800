package deco2800.thomas.entities;

import com.badlogic.gdx.Input;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.tasks.MovementTask;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PlayerPeonTest extends BaseGDXTest {
    @Test
    public void testPlayerSet() {
        PlayerPeon p = new PlayerPeon(0, 0, 1);
        assertThat("", p.getTexture(), is(equalTo("spacman_ded")));
    }

    @Test
    public void testConstructor() {
        PlayerPeon p = new PlayerPeon(1, 1, 1);
        assertThat("", p.getCol(), is(equalTo(1f)));
        assertThat("", p.getRow(), is(equalTo(1f)));
        assertThat("", p.getSpeed(), is(equalTo(1f)));
        assertThat("", p.getTexture(), is(equalTo("spacman_ded")));
    }

    /**
     * Test player press WSAD keys to move
     */
    @Test
    public void testPlayerPressKeyToMove() {
        PlayerPeon playerPressW = new PlayerPeon(10f, 10f, 0.15f);
        PlayerPeon playerPressA = new PlayerPeon(10f, 10f, 0.15f);
        PlayerPeon playerPressS = new PlayerPeon(10f, 10f, 0.15f);
        PlayerPeon playerPressD = new PlayerPeon(10f, 10f, 0.15f);

        playerPressW.notifyKeyDown(Input.Keys.W);
        playerPressA.notifyKeyDown(Input.Keys.A);
        playerPressS.notifyKeyDown(Input.Keys.S);
        playerPressD.notifyKeyDown(Input.Keys.D);

        assertEquals(MovementTask.Direction.UP, playerPressW.getMovingDirection());
        assertEquals(MovementTask.Direction.DOWN, playerPressS.getMovingDirection());
        assertEquals(MovementTask.Direction.LEFT, playerPressA.getMovingDirection());
        assertEquals(MovementTask.Direction.RIGHT, playerPressD.getMovingDirection());

        assertNotNull(playerPressW.getTask());
        assertNotNull(playerPressS.getTask());
        assertNotNull(playerPressA.getTask());
        assertNotNull(playerPressD.getTask());

        assertTrue(playerPressW.getTask() instanceof MovementTask);
        assertTrue(playerPressS.getTask() instanceof MovementTask);
        assertTrue(playerPressA.getTask() instanceof MovementTask);
        assertTrue(playerPressD.getTask() instanceof MovementTask);

        /* When the player release the key*/
        playerPressW.notifyKeyUp(Input.Keys.W);
        playerPressA.notifyKeyUp(Input.Keys.A);
        playerPressS.notifyKeyUp(Input.Keys.S);
        playerPressD.notifyKeyUp(Input.Keys.D);

        assertEquals(MovementTask.Direction.NONE, playerPressW.getMovingDirection());
        assertEquals(MovementTask.Direction.NONE, playerPressS.getMovingDirection());
        assertEquals(MovementTask.Direction.NONE, playerPressA.getMovingDirection());
        assertEquals(MovementTask.Direction.NONE, playerPressD.getMovingDirection());
    }

    /**
     * Test player press key not in WASD keys
     */
    @Test
    public void testPlayerPressKeyNotBelongToWASD() {
        PlayerPeon playerPeon = new PlayerPeon(10f, 10f, 0.15f);
        playerPeon.notifyKeyDown(Input.Keys.J);

        assertEquals(MovementTask.Direction.NONE, playerPeon.getMovingDirection());
        assertNull(playerPeon.getTask());
    }

    /**
     * Test player press serial keys in WASD keys
     */
    @Test
    public void testPlayerPressSerialKeys() {
        PlayerPeon playerPeon = new PlayerPeon(10f, 10f, 0.15f);

        playerPeon.notifyKeyDown(Input.Keys.W);
        playerPeon.notifyKeyDown(Input.Keys.D);
        playerPeon.notifyKeyUp(Input.Keys.W);
        playerPeon.notifyKeyDown(Input.Keys.S);
        playerPeon.notifyKeyUp(Input.Keys.D);

        assertEquals(MovementTask.Direction.DOWN, playerPeon.getMovingDirection());
        assertNotNull(playerPeon.getTask());

        playerPeon.notifyKeyUp(Input.Keys.S);
        assertEquals(MovementTask.Direction.NONE, playerPeon.getMovingDirection());
    }
}
