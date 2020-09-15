package deco2800.thomas.worlds.desert;

import com.badlogic.gdx.graphics.Texture;
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
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class DesertWorldTest {

    private DesertWorld spyWorld;

    @Before
    public void setUp() throws Exception {
        // set up mocks of all managers used during a world's creation
        PowerMockito.mockStatic(GameManager.class);
        GameManager gameManager = mock(GameManager.class);
        EnemyManager enemyManager = mock(EnemyManager.class);
        InputManager inputManager = mock(InputManager.class);
        OnScreenMessageManager onScreenMessageManager = mock(OnScreenMessageManager.class);
        TextureManager textureManager = mock(TextureManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        when(gameManager.getManager(OnScreenMessageManager.class)).thenReturn(onScreenMessageManager);
        when(gameManager.getManager(InputManager.class)).thenReturn(inputManager);
        when(gameManager.getManager(EnemyManager.class)).thenReturn(enemyManager);
        when(gameManager.getManager(TextureManager.class)).thenReturn(textureManager);
        when(GameManager.getManagerFromInstance(OnScreenMessageManager.class)).thenReturn(onScreenMessageManager);
        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(inputManager);
        when(GameManager.getManagerFromInstance(EnemyManager.class)).thenReturn(enemyManager);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);

        // sets up some functions for a mock Texture and its manager
        Texture texture = mock(Texture.class);
        when(textureManager.getTexture(anyString())).thenReturn(texture);
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

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("DesertCactus")) {
                entityPos.add(entity.getPosition());
            }
        }

        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getType().equals("Cactus")) {
                SquareVector pos = tile.getCoordinates();
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

        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity.getObjectName().equals("DesertQuicksand")) {
                entityPos.add(entity.getPosition());
            }
        }

        for (Tile tile : spyWorld.getTiles()) {
            if (tile.getType().equals("Quicksand")) {
                SquareVector pos = tile.getCoordinates();
                if (!entityPos.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertTrue(allTiles);
    }

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
}