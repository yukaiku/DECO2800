package deco2800.thomas.worlds.dungeons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.managers.*;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.desert.DesertWorld;
import deco2800.thomas.worlds.dungeons.desert.DesertDungeonDialog;
import deco2800.thomas.worlds.dungeons.desert.DesertDungeonOpeningDialog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests the DesertDungeon class and its methods.
 *
 * NOTE: If tests are failing here during future sprints, it is likely
 * that new managers or features have been added and must now be mocked in the
 * setUp() method below.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DesertDungeon.class, GameManager.class, DesertDungeonDialog.class, DesertDungeonOpeningDialog.class})
public class DesertDungeonTest extends BaseGDXTest {

    // the DesertWorld instance being tested
    private DesertDungeon spyWorld;

    /**
     * Sets up all tests. All managers and features not specifically related
     * to the DesertDungeon and its entities MUST be mocked here.
     */
    @Before
    public void setUp() throws Exception {
        // set up mocks of all managers used during a world's creation
        PowerMockito.mockStatic(GameManager.class);
        PowerMockito.mockStatic(DesertDungeonDialog.class);
        PowerMockito.mockStatic(DesertDungeonOpeningDialog.class);
        GameManager gameManager = mock(GameManager.class);
        EnemyManager enemyManager = mock(EnemyManager.class);
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

        // sets up some functions for a mock Texture and its manager
        Texture texture = mock(Texture.class);
        when(textureManager.getTexture(anyString())).thenReturn(texture);
        Array<TextureRegion> playerStand = new Array<>();
        playerStand.add(new TextureRegion(new Texture("resources/combat/move_right.png"), 262, 256));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(playerStand);
        when(texture.getWidth()).thenReturn(1);
        when(texture.getHeight()).thenReturn(1);

        // sets up mocks for the dialog box
        Stage stage = mock(Stage.class);
        when(gameManager.getStage()).thenReturn(stage);

        PowerMockito.mockStatic(DesertDungeonDialog.class);
        PowerMockito.doNothing().when(DesertDungeonDialog.class);
        DesertDungeonDialog.setup(any(Stage.class));
        DesertDungeonDialog dialog = mock(DesertDungeonDialog.class);

        DesertDungeonOpeningDialog openingDialog = mock(DesertDungeonOpeningDialog.class);
        when(openingDialog.show()).thenReturn(dialog);
        PowerMockito.whenNew(DesertDungeonOpeningDialog.class).withArguments(anyString()).thenReturn(openingDialog);

        // inits a desert dungeon
        DesertDungeon world = new DesertDungeon();
        spyWorld = spy(world);
    }

    /**
     * Tests that the type returned by a DesertWorld is correct.
     */
    @Test
    public void getType() {
        Assert.assertEquals("DesertDungeon", spyWorld.getType());
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
}