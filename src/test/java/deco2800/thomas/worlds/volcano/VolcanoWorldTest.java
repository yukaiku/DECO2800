package deco2800.thomas.worlds.volcano;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.*;
import deco2800.thomas.entities.environment.volcano.*;
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

    /**
     * Tests that all fenceE-W parts are correctly added to the new entity
     * when createGraveYard() is called.
     */
    @Test
    public void createGraveYardFenceEW() {
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

    /**
     * Tests that all fenceN-S parts are correctly added to the new entity
     * when createGraveYard() is called.
     */
    @Test
    public void createGraveYardFenceNS() {
        ArrayList<SquareVector> fenceLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        float row = 0;
        float col = 0;

        VolcanoGraveYard graveYard = spyWorld.createGraveYard(col, row);

        // these are the positions selected in the method
        fenceLocations.add(new SquareVector(0, -6));
        fenceLocations.add(new SquareVector(0, -5));
        fenceLocations.add(new SquareVector(0,  -2));
        fenceLocations.add(new SquareVector(0, -1));
        fenceLocations.add(new SquareVector(13, -6));
        fenceLocations.add(new SquareVector(13, -5));
        fenceLocations.add(new SquareVector(13,  -2));
        fenceLocations.add(new SquareVector(13, -1));

        for (SquareVector vec : graveYard.children.keySet()) {
            if (graveYard.children.get(vec).equals("fenceN-S")) {
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

    /**
     * Tests that all tree parts are correctly added to the new entity
     * when createGraveYard() is called.
     */
    @Test
    public void createGraveYardTrees() {
        ArrayList<SquareVector> treeLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        float row = 0;
        float col = 0;

        VolcanoGraveYard graveYard = spyWorld.createGraveYard(col, row);

        // these are the positions selected in the method
        treeLocations.add(new SquareVector(1, -1));
        treeLocations.add(new SquareVector(12, -1));
        treeLocations.add(new SquareVector(1,  -6));
        treeLocations.add(new SquareVector(12, -6));

        for (SquareVector vec : graveYard.children.keySet()) {
            if (graveYard.children.get(vec).equals("tree")) {
                size++;
                if (!treeLocations.contains(vec)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(treeLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all bones parts are correctly added to the new entity
     * when createGraveYard() is called.
     */
    @Test
    public void createGraveYardBones() {
        ArrayList<SquareVector> bonesLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        float row = 0;
        float col = 0;

        VolcanoGraveYard graveYard = spyWorld.createGraveYard(col, row);

        // these are the positions selected in the method
        bonesLocations.add(new SquareVector(1, -2));
        bonesLocations.add(new SquareVector(2, -1));
        bonesLocations.add(new SquareVector(11,  -1));
        bonesLocations.add(new SquareVector(12, -2));
        bonesLocations.add(new SquareVector(1, -5));
        bonesLocations.add(new SquareVector(2, -6));
        bonesLocations.add(new SquareVector(11,  -6));
        bonesLocations.add(new SquareVector(12, -5));

        for (SquareVector vec : graveYard.children.keySet()) {
            if (graveYard.children.get(vec).equals("Bones")) {
                size++;
                if (!bonesLocations.contains(vec)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(bonesLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all ruins parts are correctly added to the new entity
     * when createGraveYard() is called.
     */
    @Test
    public void createGraveYardRuins() {
        ArrayList<SquareVector> ruinsLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        float row = 0;
        float col = 0;

        VolcanoGraveYard graveYard = spyWorld.createGraveYard(col, row);

        // these are the positions selected in the method
        ruinsLocations.add(new SquareVector(5, -2));
        ruinsLocations.add(new SquareVector(8, -2));
        ruinsLocations.add(new SquareVector(5,  -5));
        ruinsLocations.add(new SquareVector(8, -5));

        for (SquareVector vec : graveYard.children.keySet()) {
            if (graveYard.children.get(vec).equals("Ruins_4")) {
                size++;
                if (!ruinsLocations.contains(vec)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(ruinsLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all ruins_2 parts are correctly added to the new entity
     * when createRuins() is called.
     */
    @Test
    public void createRuins2() {
        ArrayList<SquareVector> ruinsLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        float row = 0;
        float col = 0;

        VolcanoRuins ruins = spyWorld.createRuins(col, row);

        // these are the positions selected in the method
        for (int i = 1; i < 16; i++) {
            ruinsLocations.add(new SquareVector(0, -i));
        }
        ruinsLocations.add(new SquareVector(2, 0));
        ruinsLocations.add(new SquareVector(3, 0));
        ruinsLocations.add(new SquareVector(4, 0));
        ruinsLocations.add(new SquareVector(5, 0));
        ruinsLocations.add(new SquareVector(6, 0));
        ruinsLocations.add(new SquareVector(1, -15));
        ruinsLocations.add(new SquareVector(2, -15));
        ruinsLocations.add(new SquareVector(3, -15));
        ruinsLocations.add(new SquareVector(4, -15));

        for (SquareVector vec : ruins.children.keySet()) {
            if (ruins.children.get(vec).equals("Ruins_2")) {
                size++;
                if (!ruinsLocations.contains(vec)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(ruinsLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all ruins_5 parts are correctly added to the new entity
     * when createRuins() is called.
     */
    @Test
    public void createRuins5() {
        ArrayList<SquareVector> ruinsLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        float row = 0;
        float col = 0;

        VolcanoRuins ruins = spyWorld.createRuins(col, row);

        // these are the positions selected in the method
        ruinsLocations.add(new SquareVector(7, -1));
        ruinsLocations.add(new SquareVector(7, -2));
        ruinsLocations.add(new SquareVector(7, -5));
        ruinsLocations.add(new SquareVector(7, -6));
        ruinsLocations.add(new SquareVector(7, -10));
        ruinsLocations.add(new SquareVector(7, -11));
        ruinsLocations.add(new SquareVector(7, -12));
        ruinsLocations.add(new SquareVector(7, -15));
        ruinsLocations.add(new SquareVector(3, -3));
        ruinsLocations.add(new SquareVector(4, -4));
        ruinsLocations.add(new SquareVector(4, -11));
        ruinsLocations.add(new SquareVector(3, -12));

        for (SquareVector vec : ruins.children.keySet()) {
            if (ruins.children.get(vec).equals("Ruins_5")) {
                size++;
                if (!ruinsLocations.contains(vec)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(ruinsLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all ruins_4 parts are correctly added to the new entity
     * when createRuins() is called.
     */
    @Test
    public void createRuins4() {
        ArrayList<SquareVector> ruinsLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        float row = 0;
        float col = 0;

        VolcanoRuins ruins = spyWorld.createRuins(col, row);

        // these are the positions selected in the method
        ruinsLocations.add(new SquareVector(9, -5));
        ruinsLocations.add(new SquareVector(9, -6));
        ruinsLocations.add(new SquareVector(10, -6));
        ruinsLocations.add(new SquareVector(9, -9));
        ruinsLocations.add(new SquareVector(9, -10));
        ruinsLocations.add(new SquareVector(10, -9));

        for (SquareVector vec : ruins.children.keySet()) {
            if (ruins.children.get(vec).equals("Ruins_4")) {
                size++;
                if (!ruinsLocations.contains(vec)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(ruinsLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that all bones parts are correctly added to the new entity
     * when createRuins() is called.
     */
    @Test
    public void createRuinsBones() {
        ArrayList<SquareVector> bonesLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        float row = 0;
        float col = 0;

        VolcanoRuins ruins = spyWorld.createRuins(col, row);

        // these are the positions selected in the method
        bonesLocations.add(new SquareVector(4, -3));
        bonesLocations.add(new SquareVector(3, -4));
        bonesLocations.add(new SquareVector(3, -11));
        bonesLocations.add(new SquareVector(4, -12));

        for (SquareVector vec : ruins.children.keySet()) {
            if (ruins.children.get(vec).equals("Bones")) {
                size++;
                if (!bonesLocations.contains(vec)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(bonesLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that the dragon skull is correctly created when
     * createDragonSkull() is called.
     */
    @Test
    public void createDragonSkullTest() {
        ArrayList<SquareVector> skullLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        float row = 0;
        float col = 0;

        VolcanoDragonSkull skull = spyWorld.createDragonSkull(col, row);

        // this is the position selected in the method
        skullLocations.add(new SquareVector(0, 0));

        for (SquareVector vec : skull.children.keySet()) {
            if (skull.children.get(vec).equals("DragonSkull")) {
                size++;
                if (!skullLocations.contains(vec)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(skullLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Tests that 20 entities are added when addRandoms() is called.
     */
    @Test
    public void addRandomsTest() {
        for (AbstractEntity entity : spyWorld.getEntities()) {
            spyWorld.removeEntity(entity);
        }

        spyWorld.addRandoms();

        Assert.assertEquals(20, spyWorld.getEntities().size());
    }

    /**
     * Tests that 23 total entities are added when createStaticEntities() is called.
     */
    @Test
    public void createStaticEntitiesTest() {
        for (AbstractEntity entity : spyWorld.getEntities()) {
            spyWorld.removeEntity(entity);
        }

        spyWorld.createStaticEntities();

        Assert.assertEquals(27, spyWorld.getEntities().size());
    }
}
