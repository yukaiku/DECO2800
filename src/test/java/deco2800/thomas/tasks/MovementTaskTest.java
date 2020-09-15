package deco2800.thomas.tasks;

import com.badlogic.gdx.Game;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test for MovementTask class
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({WorldUtil.class, GameManager.class})
public class MovementTaskTest {
    private MovementTask spyMovementTask;
    private PlayerPeon playerPeon;
    private SquareVector destination;

    /**
     * Setup the static method and variables to use in each test
     */
    @Before
    public void setUp() {
        playerPeon = mock(PlayerPeon.class);
        SquareVector originPosition = mock(SquareVector.class);

        // Set up a mock GameManager so that Tiles can be returned
        GameManager gameManager = mock(GameManager.class);
        AbstractWorld world = mock(AbstractWorld.class);
        PowerMockito.mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        when(gameManager.getWorld()).thenReturn(world);
        when(world.getTile(anyInt())).thenReturn(null);

        when(playerPeon.getPosition()).thenReturn(originPosition);
        when(originPosition.isCloseEnoughToBeTheSame(any(SquareVector.class))).thenReturn(true);

        doNothing().when(playerPeon).moveTowards(any(SquareVector.class));

        destination = new SquareVector(10f, 10f);
        MovementTask movementTask = new MovementTask(playerPeon, destination);
        spyMovementTask = spy(movementTask);

        PowerMockito.mockStatic(WorldUtil.class);
    }

    /**
     * Test the player makes a normal move UP to a walkable place
     */
    @Test
    public void testNormalMovementMoveUpToAWalkablePlace() {
        when(WorldUtil.isWalkable(anyFloat(), anyFloat())).thenReturn(true);
        when(playerPeon.getMovingDirection()).thenReturn(MovementTask.Direction.UP);

        spyMovementTask.onTick(anyLong());

        assertEquals(11f, destination.getRow(), 0.1f);
        verify(playerPeon, atLeastOnce()).moveTowards(any(SquareVector.class));
    }

    /**
     * Test the player makes a normal move DOWN to a walkable place
     */
    @Test
    public void testNormalMovementMoveDownToAWalkablePlace() {
        when(WorldUtil.isWalkable(anyFloat(), anyFloat())).thenReturn(true);
        when(playerPeon.getMovingDirection()).thenReturn(MovementTask.Direction.DOWN);

        spyMovementTask.onTick(anyLong());

        assertEquals(9f, destination.getRow(), 0.1f);
        verify(playerPeon, atLeastOnce()).moveTowards(any(SquareVector.class));
    }

    /**
     * Test the player makes a normal move LEFT to a walkable place
     */
    @Test
    public void testNormalMovementMoveLeftToAWalkablePlace() {
        when(WorldUtil.isWalkable(anyFloat(), anyFloat())).thenReturn(true);
        when(playerPeon.getMovingDirection()).thenReturn(MovementTask.Direction.LEFT);

        spyMovementTask.onTick(anyLong());

        assertEquals(9f, destination.getCol(), 0.1f);
        verify(playerPeon, atLeastOnce()).moveTowards(any(SquareVector.class));
    }

    /**
     * Test the player makes a normal move RIGHT to a walkable place
     */
    @Test
    public void testNormalMovementMoveRightToAWalkablePlace() {
        when(WorldUtil.isWalkable(anyFloat(), anyFloat())).thenReturn(true);
        when(playerPeon.getMovingDirection()).thenReturn(MovementTask.Direction.RIGHT);

        spyMovementTask.onTick(anyLong());

        assertEquals(11f, destination.getCol(), 0.1f);
        verify(playerPeon, atLeastOnce()).moveTowards(any(SquareVector.class));
    }

    /**
     * Test the player makes a normal move to a none walkable place
     */
    @Test
    public void testNormalMovementMoveToANoneWalkablePlace() {
        when(WorldUtil.isWalkable(anyFloat(), anyFloat())).thenReturn(false);
        when(playerPeon.getMovingDirection()).thenReturn(MovementTask.Direction.UP);

        spyMovementTask.onTick(anyLong());

        assertEquals(10f, destination.getRow(), 0.1f);
        assertTrue(spyMovementTask.isComplete());
        verify(playerPeon, never()).moveTowards(any(SquareVector.class));
    }

    /**
     * Test the player does not make a move
     */
    @Test
    public void testPlayerNotMove() {
        when(WorldUtil.isWalkable(anyFloat(), anyFloat())).thenReturn(true);
        when(playerPeon.getMovingDirection()).thenReturn(MovementTask.Direction.NONE);

        spyMovementTask.onTick(anyLong());

        assertEquals(10f, destination.getRow(), 0.1f);
        assertTrue(spyMovementTask.isComplete());
        verify(playerPeon, atLeastOnce()).moveTowards(any(SquareVector.class));
    }
}
