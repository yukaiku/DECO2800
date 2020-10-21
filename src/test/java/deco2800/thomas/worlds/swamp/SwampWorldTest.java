package deco2800.thomas.worlds.swamp;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests the SwampWorld class and its methods.
 *
 * NOTE: If tests are failing here during future sprints, it is likely
 * that new managers or features have been added and must now be mocked in the
 * setUp() method below.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class SwampWorldTest extends BaseGDXTest {

    // the SwampWorld instance being tested
    private SwampWorld spyWorld;

    /**
     * Sets up all tests. All managers and features not specifically related
     * to the SwampWorld and its entities MUST be mocked here.
     */
    @Before
    public void setUp() throws Exception {
        // set up mocks of all managers used during a world's creation
        PowerMockito.mockStatic(GameManager.class);
        GameManager gameManager = mock(GameManager.class);
        EnemyManager enemyManager = mock(EnemyManager.class);
        DifficultyManager difficultyManager = mock(DifficultyManager.class);
        InputManager inputManager = mock(InputManager.class);
        OnScreenMessageManager onScreenMessageManager = mock(OnScreenMessageManager.class);
        TextureManager textureManager = mock(TextureManager.class);
        SoundManager soundManager = mock(SoundManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        when(gameManager.getManager(OnScreenMessageManager.class)).thenReturn(onScreenMessageManager);
        when(gameManager.getManager(InputManager.class)).thenReturn(inputManager);
        when(gameManager.getManager(EnemyManager.class)).thenReturn(enemyManager);
        when(gameManager.getManager(DifficultyManager.class)).thenReturn(difficultyManager);
        when(gameManager.getManager(TextureManager.class)).thenReturn(textureManager);
        when(gameManager.getManager(SoundManager.class)).thenReturn(soundManager);
        when(GameManager.getManagerFromInstance(OnScreenMessageManager.class)).thenReturn(onScreenMessageManager);
        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(inputManager);
        when(GameManager.getManagerFromInstance(EnemyManager.class)).thenReturn(enemyManager);
        when(GameManager.getManagerFromInstance(DifficultyManager.class)).thenReturn(difficultyManager);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);
        when(GameManager.getManagerFromInstance(SoundManager.class)).thenReturn(soundManager);

        // sets up some functions for a mock Texture and its manager
        Texture texture = mock(Texture.class);
        when(textureManager.getTexture(anyString())).thenReturn(texture);
        Array<TextureRegion> playerStand = new Array<>();
        playerStand.add(new TextureRegion(new Texture("resources/combat/move_right.png"), 262, 256));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(playerStand);
        when(texture.getWidth()).thenReturn(1);
        when(texture.getHeight()).thenReturn(1);

        // inits a swamp world
        SwampWorld world = new SwampWorld();
        spyWorld = spy(world);
    }

    /**
     * Tests that the type returned by a SwampWorld is correct.
     */
    @Test
    public void getType() {
        Assert.assertEquals("Swamp", spyWorld.getType());
    }

    /**
     * Tests that the number of tiles in a default SwampWorld is 2500.
     */
    @Test
    public void getTilesLength() {
        Assert.assertEquals(2500, spyWorld.getTiles().size());
    }

    /**
     * Tests that no tiles in the world are null.
     */
    @Test
    public void tilesNotNull() {
        boolean allTiles = true;
        for (Tile tile : spyWorld.getTiles()) {
            if (tile == null) {
                allTiles = false;
                break;
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all tile IDs in the world match their index within the tile array.
     */
    @Test
    public void tileIDsMatch() {
        boolean allTiles = true;
        int i = 0;
        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getTileID() != i++) {
                allTiles = false;
                break;
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that the player entity is spawned in the world.
     */
    @Test
    public void playerSpawned() {
        Assert.assertNotNull(spyWorld.getPlayerEntity());
    }

    /**
     * Tests that the createPond() method correctly spawns all SwampPond entities.
     */
    @Test
    public void createPondTest() {
        SquareVector pos;
        ArrayList<SquareVector> pondLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        // these are the positions selected in the method
        pondLocations.add(new SquareVector(20, -19));
        pondLocations.add(new SquareVector(21, -19));
        pondLocations.add(new SquareVector(16, -24));
        pondLocations.add(new SquareVector(17, -25));
        pondLocations.add(new SquareVector(16, -25));
        pondLocations.add(new SquareVector(19, -23));
        pondLocations.add(new SquareVector(19, -24));
        pondLocations.add(new SquareVector(21, 14));

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("SwampPond")) {
                size++;
                pos = entity.getPosition();
                if (!pondLocations.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(pondLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that the generateDeadTree() method correctly spawns all SwampDeadTree entities.
     */
    @Test
    public void generateDeadTreeTest() {
        SquareVector pos;
        ArrayList<SquareVector> treeLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        // these are the positions selected in the method
        treeLocations.add(new SquareVector(-5, -24));
        treeLocations.add(new SquareVector(-6, -23));
        treeLocations.add(new SquareVector(-4, -22));
        treeLocations.add(new SquareVector(-3, -24));
        treeLocations.add(new SquareVector(-2, -22));
        treeLocations.add(new SquareVector(-1, -24));
        treeLocations.add(new SquareVector(-1, -22));
        treeLocations.add(new SquareVector(20, 15));
        treeLocations.add(new SquareVector(22, 15));
        treeLocations.add(new SquareVector(22, 14));
        treeLocations.add(new SquareVector(-1, -22));
        treeLocations.add(new SquareVector(13, -6));
        treeLocations.add(new SquareVector(14, -7));
        treeLocations.add(new SquareVector(15, -5));
        treeLocations.add(new SquareVector(15, -8));
        treeLocations.add(new SquareVector(16, -6));
        treeLocations.add(new SquareVector(12, -6));
        treeLocations.add(new SquareVector(12, -9));
        treeLocations.add(new SquareVector(13, -10));
        treeLocations.add(new SquareVector(14, -9));
        treeLocations.add(new SquareVector(17, -24));
        treeLocations.add(new SquareVector(16, -23));
        treeLocations.add(new SquareVector(18, -22));
        treeLocations.add(new SquareVector(15, -24));
        treeLocations.add(new SquareVector(18, -24));

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("SwampDeadTree")) {
                size++;
                pos = entity.getPosition();
                if (!treeLocations.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(treeLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that the createTreeStub() method correctly spawns all SwampTreeStub entities.
     */
    @Test
    public void createTreeStubTest() {
        SquareVector pos;
        ArrayList<SquareVector> treeLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        // these are the positions selected in the method
        treeLocations.add(new SquareVector(-5, -22));
        treeLocations.add(new SquareVector(0, -23));
        treeLocations.add(new SquareVector(1, -24));
        treeLocations.add(new SquareVector(21, 13));
        treeLocations.add(new SquareVector(24, 15));
        treeLocations.add(new SquareVector(21, 16));
        treeLocations.add(new SquareVector(14, -5));
        treeLocations.add(new SquareVector(12, -7));
        treeLocations.add(new SquareVector(16, -7));
        treeLocations.add(new SquareVector(13, -8));
        treeLocations.add(new SquareVector(17, -5));
        treeLocations.add(new SquareVector(17, -8));
        treeLocations.add(new SquareVector(13, -4));

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("SwampTreeStub")) {
                size++;
                pos = entity.getPosition();
                if (!treeLocations.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(treeLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that the createTreeLog() method correctly spawns all SwampTreeLog entities.
     */
    @Test
    public void createTreeLogTest() {
        SquareVector pos;
        ArrayList<SquareVector> treeLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        // these are the positions selected in the method
        treeLocations.add(new SquareVector(19, -19));
        treeLocations.add(new SquareVector(20, -18));
        treeLocations.add(new SquareVector(20, 13));
        treeLocations.add(new SquareVector(19, 14));
        treeLocations.add(new SquareVector(12, -4));
        treeLocations.add(new SquareVector(11, -5));
        treeLocations.add(new SquareVector(11, -6));

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("SwampTreeLog")) {
                size++;
                pos = entity.getPosition();
                if (!treeLocations.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(treeLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that the createFallenTree() method correctly spawns all SwampFallenTree entities.
     */
    @Test
    public void createFallenTreeTest() {
        SquareVector pos;
        ArrayList<SquareVector> treeLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        // these are the positions selected in the method
        treeLocations.add(new SquareVector(22, -19));
        treeLocations.add(new SquareVector(22, -20));
        treeLocations.add(new SquareVector(-4, -23));
        treeLocations.add(new SquareVector(-2, -23));
        treeLocations.add(new SquareVector(24, 14));
        treeLocations.add(new SquareVector(23, 13));

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("SwampFallenTree")) {
                size++;
                pos = entity.getPosition();
                if (!treeLocations.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(treeLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

}
