package deco2800.thomas.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.tundra.TundraWorld;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Tests the TundraWorld class and its methods.
 *
 * NOTE: If tests are failing here during future sprints, it is likely
 * that new managers or features have been added and must now be mocked in the
 * setUp() method below.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class TundraWorldTest extends BaseGDXTest {

    // the TundraWorld instance being tested
    private TundraWorld spyWorld;

    /**
     * Sets up all tests. All managers and features not specifically related
     * to the TundraWorld and its entities MUST be mocked here.
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

        // inits a tundra world
        TundraWorld world = new TundraWorld();
        spyWorld = spy(world);
    }

    /**
     * Tests that the type returned by a TundraWorld is correct.
     */
    @Test
    public void getType() {
        Assert.assertEquals("Tundra", spyWorld.getType());
    }

    /**
     * Tests that the number of tiles in a default TundraWorld is 2500.
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
     * Tests that 20 campfires are correctly added during generateStaticEntities().
     */
    @Test public void addCampfireTest() {
        int counter = 0;

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("TundraCampfire")) {
                counter++;
            }
        }

        Assert.assertEquals(20, counter);
    }

    /**
     * Tests that 60 tree logs are correctly added during generateStaticEntities().
     */
    @Test
    public void addTreeLogTest() {
        int counter = 0;

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("TundraTreeLog")) {
                counter++;
            }
        }

        Assert.assertEquals(60, counter);
    }

    /**
     * Tests that 60 rocks are correctly added during generateStaticEntities().
     */
    @Test
    public void addRockTest() {
        int counter = 0;

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("TundraTreeLog")) {
                counter++;
            }
        }

        Assert.assertEquals(60, counter);
    }

    /**
     * Tests that all neighbours of campfire entities are correctly set
     * as tundra fire tiles with an associated status effect.
     */
    @Test
    public void campfireNeighbours() {
        boolean allTiles = true;
        Tile tile;

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("TundraCampfire")) {
                tile = spyWorld.getTile(entity.getPosition());

                for (Tile neighbour : tile.getNeighbours().values()) {
                    if (!neighbour.hasStatusEffect()
                            || !neighbour.getType().equals("TundraFireTile")) {

                        allTiles = false;
                        break;
                    }
                }
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all tiles with textures tundra-tile-1 or 2 are correctly set as
     * tundra ice tiles or fire tiles (if a neighbour is a campfire).  Also checks
     * that these tiles have an associated status effect.
     */
    @Test
    public void iceTiles() {
        boolean allTiles = true;

        for (Tile tile : spyWorld.getTiles()) {

            if (tile.getTextureName().equals("tundra-tile-1")
                    || tile.getTextureName().equals("tundra-tile-2")) {

                if (!tile.hasStatusEffect() || (!tile.getType().equals("TundraIceTile")
                        && !tile.getType().equals("TundraFireTile"))) {

                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertTrue(allTiles);
    }
}