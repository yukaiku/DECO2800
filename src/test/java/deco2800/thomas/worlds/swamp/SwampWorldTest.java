package deco2800.thomas.worlds.swamp;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.desert.DesertWorld;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class SwampWorldTest extends BaseGDXTest {

    private SwampWorld spyWorld;

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

        Array<TextureRegion> playerStand = new Array<>();
        playerStand.add(new TextureRegion(new Texture("resources/combat/move_right.png"), 262, 256));

        when(textureManager.getAnimationFrames(anyString())).thenReturn(playerStand);
        when(texture.getWidth()).thenReturn(1);
        when(texture.getHeight()).thenReturn(1);

        // inits a swamp world
        SwampWorld world = new SwampWorld();
        spyWorld = spy(world);
    }

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

    @Test
    public void createPondTest() {
        SquareVector pos;
        ArrayList<SquareVector> pondLocations = new ArrayList<>();
        boolean allTiles = true;


        spyWorld.createPond();

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
                pos = entity.getPosition();
                if (!pondLocations.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertTrue(allTiles);
    }
}
