package deco2800.thomas.worlds.desert;

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

import static org.mockito.Mockito.*;

/**
 * Tests the DesertWorld class and its methods.
 *
 * NOTE: If tests are failing here during future sprints, it is likely
 * that new managers or features have been added and must now be mocked in the
 * setUp() method below.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class DesertWorldTest extends BaseGDXTest {

    // the DesertWorld instance being tested
    private DesertWorld spyWorld;

    /**
     * Sets up all tests. All managers and features not specifically related
     * to the DesertWorld and its entities MUST be mocked here.
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

        // inits a desert world
        DesertWorld world = new DesertWorld();
        spyWorld = spy(world);
    }

    /**
     * Tests that the type returned by a DesertWorld is correct.
     */
    @Test
    public void getType() {
        Assert.assertEquals("Desert", spyWorld.getType());
    }

    /**
     * Tests that the number of tiles in a default DesertWorld is 2500.
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
     * Tests that all neighbouring tiles of cactus plants have the correct type.
     */
    @Test
    public void cactusNeighboursType() {
        spyWorld.onTick(anyLong());
        boolean allTiles = true;

        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getType().equals("Cactus")) {
                for (Tile neighbour : tile.getNeighbours().values()) {
                    if (!neighbour.getType().equals("CactusNeighbour")) {
                        allTiles = false;
                        break;
                    }
                }
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all neighbouring tiles of cactus plants have an associated status effect.
     */
    @Test
    public void cactusNeighboursDamage() {
        spyWorld.onTick(anyLong());
        boolean allTiles = true;

        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getType().equals("Cactus")) {
                for (Tile neighbour : tile.getNeighbours().values()) {
                    if (!neighbour.hasStatusEffect()) {
                        allTiles = false;
                        break;
                    }
                }
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all cactus tiles have a matching static entity.
     */
    @Test
    public void cactusEntity() {
        spyWorld.onTick(anyLong());
        boolean allTiles = true;
        ArrayList<SquareVector> entityPos = new ArrayList<>();
        SquareVector pos;

        // find all positions of cactus entities
        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("DesertCactus")) {
                entityPos.add(entity.getPosition());
            }
        }

        // check that tiles with Cactus type are in the cactus entity list
        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getType().equals("Cactus")) {
                pos = tile.getCoordinates();
                if (!entityPos.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all tiles with the desert_3 texture have an associated
     * cactus or dead tree static entity.
     */
    @Test
    public void cactusOrDeadTree() {
        spyWorld.onTick(anyLong());
        boolean allTiles = true;
        ArrayList<SquareVector> entityPos = new ArrayList<>();
        SquareVector pos;

        // find all positions 
        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("DesertCactus") || entity.getObjectName().equals("DesertDeadTree")) {
                entityPos.add(entity.getPosition());
            }
        }

        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getTextureName().equals("desert_3")) {
                pos = tile.getCoordinates();
                if (!entityPos.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all quicksand tiles have an associated status effect.
     */
    @Test
    public void quicksandDamage() {
        spyWorld.onTick(anyLong());
        boolean allTiles = true;

        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getType().equals("Quicksand") && !tile.hasStatusEffect()) {
                allTiles = false;
                break;
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all quicksand tiles have a matching static entity.
     */
    @Test
    public void quicksandEntity() {
        spyWorld.onTick(anyLong());
        boolean allTiles = true;
        ArrayList<SquareVector> entityPos = new ArrayList<>();
        SquareVector pos;

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("DesertQuicksand")) {
                entityPos.add(entity.getPosition());
            }
        }

        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getType().equals("Quicksand")) {
                pos = tile.getCoordinates();
                if (!entityPos.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all tiles on the map with blue (water) textures are obstructed.
     */
    @Test
    public void waterObstructed() {
        String texName;
        spyWorld.onTick(anyLong());
        boolean allTiles = true;

        for (Tile tile : spyWorld.getTiles()) {
            texName = tile.getTextureName();
            if (texName.equals("oasis_4") || texName.equals("oasis_5")) {
                if (!tile.isObstructed()) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all tiles on the map sand dune textures are obstructed.
     */
    @Test
    public void dunesObstructed() {
        String texName;
        spyWorld.onTick(anyLong());
        boolean allTiles = true;

        for (Tile tile : spyWorld.getTiles()) {
            texName = tile.getTextureName();
            if (texName.equals("desert_5") || texName.equals("desert_6")) {
                if (!tile.isObstructed()) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertTrue(allTiles);
    }


    /**
     * Tests that all tiles with desert_5 or desert_6 textures have an
     * associated sand dune entity.
     */
    @Test
    public void sandDunesPlaced() {
        spyWorld.onTick(anyLong());
        boolean allTiles = true;
        ArrayList<SquareVector> entityPos = new ArrayList<>();
        SquareVector pos;

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("DesertSandDune")) {
                entityPos.add(entity.getPosition());
            }
        }

        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getTextureName().equals("desert_5") || tile.getTextureName().equals("desert_6")) {
                pos = tile.getCoordinates();
                if (!entityPos.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all oasis tree entities are places on tile with
     * textures: oasis_1, oasis_2 or oasis_3.
     */
    @Test
    public void oasisTreesPlaced() {
        spyWorld.onTick(anyLong());
        boolean allTiles = true;
        ArrayList<SquareVector> tilePos = new ArrayList<>();
        SquareVector pos;

        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getTextureName().equals("oasis_1") || tile.getTextureName().equals("oasis_2")
                    || tile.getTextureName().equals("oasis_3")) {

                tilePos.add(tile.getCoordinates());
            }
        }

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("OasisTree")) {
                pos = entity.getPosition();
                if (!tilePos.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }
    }

    /**
     * Tests that the player entity is spawned in the world.
     */
    @Test
    public void playerSpawned() {
        Assert.assertNotNull(spyWorld.getPlayerEntity());
    }
}

