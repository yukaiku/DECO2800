package deco2800.thomas.worlds.tutorial;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.agent.AgentEntity;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.TutorialWorld;
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

public class TutorialWorldTest extends BaseGDXTest {
    // the TutorialWorld instance being tested
    private TutorialWorld tutWorld;

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
        TutorialWorld world = new TutorialWorld();
        tutWorld = spy(world);
    }

    /**
     * Test the size of the world.
     */
    @Test
    public void getTilesLength() {
        Assert.assertEquals(240, tutWorld.getTiles().size());
    }

    /**
     * Test whether the player entity is spawned in the world.
     */
    @Test
    public void playerSpawned() {
        Assert.assertNotNull(tutWorld.getPlayerEntity());
    }

    /**
     * Test the position of the the player entity.
     */
    @Test
    public void playerPosition() {
        AgentEntity player = tutWorld.getPlayerEntity();
        Assert.assertEquals(new SquareVector(-2, -2), player.getPosition());
    }

    /**
     * Test the position of stashes in the tutorial.
     */
    @Test
    public void stashTest() {
        SquareVector pos;
        ArrayList<SquareVector> stashLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        // these are the positions selected in the method
        stashLocations.add(new SquareVector(-6, 5));
        stashLocations.add(new SquareVector(-3, 5));
        stashLocations.add(new SquareVector(0, 5));
        stashLocations.add(new SquareVector(3, 5));
        stashLocations.add(new SquareVector(6, 5));

        for (AbstractEntity entity : tutWorld.getEntities()) {
            if (entity.getObjectName().equals("stash")) {
                size++;
                pos = entity.getPosition();
                if (!stashLocations.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(stashLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Test the position of chests in the tutorial.
     */
    @Test
    public void chestTest() {
        SquareVector pos;
        ArrayList<SquareVector> chestLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        // these are the positions selected in the method
        chestLocations.add(new SquareVector(-10, 0));
        chestLocations.add(new SquareVector(9, 0));

        for (AbstractEntity entity : tutWorld.getEntities()) {
            if (entity.getObjectName().equals("chest")) {
                size++;
                pos = entity.getPosition();
                if (!chestLocations.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(chestLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Test the position of barrels in the tutorial.
     */
    @Test
    public void barrelTest() {
        SquareVector pos;
        ArrayList<SquareVector> barrelLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        // these are the positions selected in the method
        barrelLocations.add(new SquareVector(-10, 4));
        barrelLocations.add(new SquareVector(-10, -5));
        barrelLocations.add(new SquareVector(9, 4));
        barrelLocations.add(new SquareVector(9, -5));

        for (AbstractEntity entity : tutWorld.getEntities()) {
            if (entity.getObjectName().equals("barrel")) {
                size++;
                pos = entity.getPosition();
                if (!barrelLocations.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(barrelLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Test the position of barrels in the tutorial.
     */
    @Test
    public void targetTest() {
        SquareVector pos;
        ArrayList<SquareVector> targetLocations = new ArrayList<>();
        boolean allTiles = true;
        int size = 0;

        // these are the positions selected in the method
        targetLocations.add(new SquareVector(-6, -6));
        targetLocations.add(new SquareVector(-4, -6));
        targetLocations.add(new SquareVector(4, -6));
        targetLocations.add(new SquareVector(6, -6));

        for (AbstractEntity entity : tutWorld.getEntities()) {
            if (entity.getObjectName().equals("target")) {
                size++;
                pos = entity.getPosition();
                if (!targetLocations.contains(pos)) {
                    allTiles = false;
                    break;
                }
            }
        }

        Assert.assertEquals(targetLocations.size(), size);
        Assert.assertTrue(allTiles);
    }

    /**
     * Test the position of the portal in the tutorial.
     */
    @Test
    public void portalTest() {
        AbstractEntity portal = null;
        for (AbstractEntity entity : tutWorld.getEntities()) {
            if (entity.getObjectName().equals("portal")) {
                portal = entity;
            }
        }

        Assert.assertNotEquals(null, portal);
        Assert.assertEquals(new SquareVector(0, -6), portal.getPosition());
    }

    /**
     * Test the position of the notify sign in the tutorial.
     */
    @Test
    public void notifyTest() {
        AbstractEntity notify = null;
        for (AbstractEntity entity : tutWorld.getEntities()) {
            if (entity.getObjectName().equals("leave")) {
                notify = entity;
            }
        }

        Assert.assertNotEquals(null, notify);
        Assert.assertEquals(new SquareVector(0, -4), notify.getPosition());
    }
}
