package deco2800.thomas.worlds.dungeons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.npc.SwampDungeonNPC;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.Tile;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests the SwampDungeon class and its methods.
 *
 * NOTE: If tests are failing here during future sprints, it is likely
 * that new managers or features have been added and must now be mocked in the
 * setUp() method below.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class})
public class SwampDungeonTest extends BaseGDXTest {

    // The SwampDungeon instance being tested
    private static SwampDungeon spyWorld;

    // an enemy manager being mocked for some tests
    private static EnemyManager enemyManager;

    // a game manager being mocked for some tests
    private static GameManager gameManager;

    /**
     * Sets up all tests. All managers and features not specifically related
     * to the SwampDungeon and its entities MUST be mocked here.
     */
    @BeforeClass
    public static void setUp() throws Exception {
        // set up mocks of all managers used during a world's creation
        PowerMockito.mockStatic(GameManager.class);
        gameManager = mock(GameManager.class);
        enemyManager = mock(EnemyManager.class);
        DifficultyManager difficultyManager = mock(DifficultyManager.class);
        InputManager inputManager = mock(InputManager.class);
        OnScreenMessageManager onScreenMessageManager = mock(OnScreenMessageManager.class);
        TextureManager textureManager = mock(TextureManager.class);
        when(GameManager.get()).thenReturn(gameManager);
        when(gameManager.getManager(OnScreenMessageManager.class)).thenReturn(onScreenMessageManager);
        when(gameManager.getManager(InputManager.class)).thenReturn(inputManager);
        when(gameManager.getManager(EnemyManager.class)).thenReturn(enemyManager);
        when(gameManager.getManager(DifficultyManager.class)).thenReturn(difficultyManager);
        when(gameManager.getManager(TextureManager.class)).thenReturn(textureManager);
        when(GameManager.getManagerFromInstance(OnScreenMessageManager.class)).thenReturn(onScreenMessageManager);
        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(inputManager);
        when(GameManager.getManagerFromInstance(EnemyManager.class)).thenReturn(enemyManager);
        when(GameManager.getManagerFromInstance(DifficultyManager.class)).thenReturn(difficultyManager);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);
        SoundManager soundManager = mock(SoundManager.class);
        when(gameManager.getManager(SoundManager.class)).thenReturn(soundManager);
        when(GameManager.getManagerFromInstance(SoundManager.class)).thenReturn(soundManager);

        // sets up some functions for a mock Texture and its manager
        Texture texture = mock(Texture.class);
        when(textureManager.getTexture(anyString())).thenReturn(texture);
        Array<TextureRegion> textureRegion = new Array<>();
        textureRegion.add(mock(TextureRegion.class));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(textureRegion);
        when(texture.getWidth()).thenReturn(1);
        when(texture.getHeight()).thenReturn(1);

        SwampDungeon world = new SwampDungeon();
        spyWorld = spy(world);
    }

    @Test
    public void widthTest() {
        Assert.assertEquals(12, spyWorld.getWidth());
    }

    @Test
    public void heightTest() {
        Assert.assertEquals(7, spyWorld.getHeight());
    }

    @Test
    public void tileNumberTest() {
        Assert.assertEquals(336, spyWorld.getTiles().size());
    }

    @Test
    public void tilesNotNullTest() {
        boolean allTiles = true;
        for (Tile tile : spyWorld.getTiles()) {
            if (tile == null) {
                allTiles = false;
                break;
            }
        }

        Assert.assertTrue(allTiles);
    }

    @Test
    public void getTypeTest() {
        Assert.assertEquals("SwampDungeon", spyWorld.getType());
    }

    @Test
    public void saveFileTest() {
        Assert.assertEquals("resources/environment/swamp_dungeon/swamp_dungeon_map.json",
                SwampDungeon.SAVE_LOCATION_AND_FILE_NAME);
    }

    @Test
    public void swampDungeonDialougesTest() {
        Assert.assertEquals(6, spyWorld.getAllSwampDungeonDialogues().size());
    }

    @Test
    public void topTrapTest() {
        boolean isEqual = spyWorld.getTopTrap().equals(new SquareVector(7, 1));
        Assert.assertTrue(isEqual);
    }

    @Test
    public void midTrapTest() {
        boolean isEqual = spyWorld.getMidTrap().equals(new SquareVector(7, -1));
        Assert.assertTrue(isEqual);
    }

    @Test
    public void correctTileTest() {
        boolean isEqual = spyWorld.getCorrectTile().equals(new SquareVector(7, -3));
        Assert.assertTrue(isEqual);
    }

    @Test
    public void setUpPlatformsTest() {
        Tile topTrapTile = spyWorld.getTile(spyWorld.getTopTrap());
        Assert.assertTrue(topTrapTile.isTrapTile());
        Assert.assertFalse(topTrapTile.getTrapActivated());

        Tile midTrapTile = spyWorld.getTile(spyWorld.getMidTrap());
        Assert.assertTrue(midTrapTile.isTrapTile());
        Assert.assertFalse(midTrapTile.getTrapActivated());

        Tile correctTile = spyWorld.getTile(spyWorld.getCorrectTile());
        Assert.assertTrue(correctTile.isRewardTile());
        Assert.assertFalse(correctTile.getRewardActivated());
    }


}












