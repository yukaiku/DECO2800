package deco2800.thomas.tasks;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Stack;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test for MovementTask class
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({WorldUtil.class, GameManager.class})
public class MovementTaskTest extends BaseGDXTest {
    private MovementTask spyMovementTask;
    private PlayerPeon playerPeon;
    private SquareVector destination;
    private Stack<MovementTask.Direction> movementStack;

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

        movementStack = new Stack<>();

        PowerMockito.mockStatic(WorldUtil.class);
    }

    private void setUpDirection(MovementTask.Direction direction, boolean isWalkable){
        movementStack.add(direction);
        when(WorldUtil.isWalkable(anyFloat(), anyFloat())).thenReturn(isWalkable);
        when(playerPeon.getMovementStack()).thenReturn(movementStack);
        when(playerPeon.getMovingDirection()).thenReturn(direction);
        when(playerPeon.isDirectionKeyActive(direction)).thenReturn(true);
    }

    /**
     * Test the player makes a normal move UP to a walkable place
     */
    @Test
    public void testNormalMovementMoveUpToAWalkablePlace() {
        this.setUpDirection(MovementTask.Direction.UP, true);

        spyMovementTask.onTick(anyLong());

        assertEquals(11f, destination.getRow(), 0.1f);
        verify(playerPeon, atLeastOnce()).moveTowards(any(SquareVector.class));
        verify(playerPeon).setMovingDirection(MovementTask.Direction.UP);
    }

    /**
     * Test the player makes a normal move DOWN to a walkable place
     */
    @Test
    public void testNormalMovementMoveDownToAWalkablePlace() {
        setUpDirection(MovementTask.Direction.DOWN, true);

        spyMovementTask.onTick(anyLong());

        assertEquals(9f, destination.getRow(), 0.1f);
        verify(playerPeon, atLeastOnce()).moveTowards(any(SquareVector.class));
        verify(playerPeon).setMovingDirection(MovementTask.Direction.DOWN);
    }

    /**
     * Test the player makes a normal move LEFT to a walkable place
     */
    @Test
    public void testNormalMovementMoveLeftToAWalkablePlace() {
        setUpDirection(MovementTask.Direction.LEFT, true);

        spyMovementTask.onTick(anyLong());

        assertEquals(9f, destination.getCol(), 0.1f);
        verify(playerPeon, atLeastOnce()).moveTowards(any(SquareVector.class));
        verify(playerPeon).setMovingDirection(MovementTask.Direction.LEFT);
    }

    /**
     * Test the player makes a normal move RIGHT to a walkable place
     */
    @Test
    public void testNormalMovementMoveRightToAWalkablePlace() {
        setUpDirection(MovementTask.Direction.RIGHT, true);

        spyMovementTask.onTick(anyLong());

        assertEquals(11f, destination.getCol(), 0.1f);
        verify(playerPeon, atLeastOnce()).moveTowards(any(SquareVector.class));
        verify(playerPeon).setMovingDirection(MovementTask.Direction.RIGHT);
    }

    /**
     * Test the player makes a normal move to a none walkable place
     */
    @Test
    public void testNormalMovementMoveToANoneWalkablePlace() {
        setUpDirection(MovementTask.Direction.UP, false);

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
        when(playerPeon.getMovementStack()).thenReturn(movementStack);
        when(playerPeon.getMovingDirection()).thenReturn(MovementTask.Direction.NONE);

        spyMovementTask.onTick(anyLong());

        assertEquals(10f, destination.getRow(), 0.1f);
        assertTrue(spyMovementTask.isComplete());
    }

    /**
     * Test the player release all keys
     */
    @Test
    public void testPlayerNotMoveWhenReleaseKeys() {
        movementStack.add(MovementTask.Direction.UP);
        when(WorldUtil.isWalkable(anyFloat(), anyFloat())).thenReturn(true);
        when(playerPeon.getMovementStack()).thenReturn(movementStack);
        when(playerPeon.getMovingDirection()).thenReturn(MovementTask.Direction.NONE);
        when(playerPeon.isDirectionKeyActive(any(MovementTask.Direction.class))).thenReturn(false);

        spyMovementTask.onTick(anyLong());

        assertEquals(10f, destination.getRow(), 0.1f);
    }
}
