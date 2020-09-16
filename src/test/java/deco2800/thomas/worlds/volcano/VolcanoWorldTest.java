package deco2800.thomas.worlds.volcano;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.Part;
import deco2800.thomas.entities.VolcanoGraveYard;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.volcano.VolcanoWorld;
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

/**
 * Tests the VolcanoWorld class and its methods.
 *
 * NOTE: If tests are failing here during future sprints, it is likely
 * that new managers or features have been added and must now be mocked in the
 * setUp() method below.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class VolcanoWorldTest extends BaseGDXTest {

    // the VolcanoWorld instance being tested
    private VolcanoWorld spyWorld;

    /**
     * Sets up all tests. All managers and features not specifically related
     * to the VolcanoWorld and its entities MUST be mocked here.
     */
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

        // inits a volcano world
        VolcanoWorld world = new VolcanoWorld();
        spyWorld = spy(world);
        when(gameManager.getWorld()).thenReturn(spyWorld);
    }

    /**
     * Tests that the type returned by a VolcanoWorld is correct.
     */
    @Test
    public void getType() {
        Assert.assertEquals("Volcano", spyWorld.getType());
    }

    /**
     * Tests that the number of tiles in a default VolcanoWorld is 2500.
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
    public void createGraveYardFenceEW() {
        SquareVector pos;
        ArrayList<SquareVector> fenceLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        float row = 0;
        float col = 0;

        VolcanoGraveYard graveYard = spyWorld.createGraveYard(col, row);

        // these are the positions selected in the method
        fenceLocations.add(new SquareVector(1, 0));
        fenceLocations.add(new SquareVector(2, 0));
        fenceLocations.add(new SquareVector(3,  0));
        fenceLocations.add(new SquareVector(9, 0));
        fenceLocations.add(new SquareVector(10, 0));
        fenceLocations.add(new SquareVector(11,  0));
        fenceLocations.add(new SquareVector(12, 0));
        fenceLocations.add(new SquareVector(1, -7));
        fenceLocations.add(new SquareVector(2, -7));
        fenceLocations.add(new SquareVector(3,  -7));
        fenceLocations.add(new SquareVector(9, -7));
        fenceLocations.add(new SquareVector(10, -7));
        fenceLocations.add(new SquareVector(11,  -7));
        fenceLocations.add(new SquareVector(12, -7));

        for (SquareVector vec : graveYard.children.keySet()) {
            if (graveYard.children.get(vec).equals("fenceE-W")) {
                size++;
                if (!fenceLocations.contains(vec)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(fenceLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    @Test
    public void createRuinsTest() {

    }

    @Test
    public void createDragonSkullTest() {

    }


}
