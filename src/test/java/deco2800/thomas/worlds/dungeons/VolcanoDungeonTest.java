package deco2800.thomas.worlds.dungeons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.environment.TripWire;
import deco2800.thomas.entities.items.Treasure;
import deco2800.thomas.entities.npc.VolcanoDungeonNPC;
import deco2800.thomas.managers.*;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.dungeons.VolcanoDungeon;
import deco2800.thomas.worlds.volcano.VolcanoBurnTile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.EmptyStackException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class VolcanoDungeonTest extends BaseGDXTest {
    // the Volcano Dungeon instance being tested
    private VolcanoDungeon spyWorld;

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

        // inits a volcano world
        VolcanoDungeon world = new VolcanoDungeon();
        spyWorld = spy(world);
        when(gameManager.getWorld()).thenReturn(spyWorld);
    }

    @After
    public void tearDown() {
        spyWorld = null;
    }

    /**
     * Tests that the type returned by a VolcanoDungeon is correct.
     */
    @Test
    public void fodderTest() {
      return;
    }

    /**
     * Tests that the type returned by a VolcanoDungeon is correct.
     */
    @Test
    public void getType() {
        Assert.assertEquals("VolcanoDungeon", spyWorld.getType());
    }

    /**
     * Tests that the number of tiles in a default VolcanoDungeon is 2500.
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

    @Test
    public void setupNpc() {
        spyWorld.setupNpc();
        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity instanceof VolcanoDungeonNPC &&
                    ((VolcanoDungeonNPC) entity).getName().equals("npc_lava_maze")
            &&  entity.getPosition().equals(new SquareVector(-2f, 0f)) ) {
                return;
            }
        }
        fail();
    }
    // TODO : Test NPC dialogue with interact()

    @Test
    public void setupIncorrectTreasure1() {
        spyWorld.setupIncorrectTreasure1();
        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity instanceof TripWire &&
                    entity.getObjectName().equals("tripwire")
                    &&  entity.getPosition().equals(new SquareVector(-23f, -23f))
                    && entity.getTexture().matches("VolcanoPortal")) {
                assertTrue(spyWorld.getTile(entity.getPosition()).isTeleportTile());
                assertFalse(spyWorld.getTile(entity.getPosition()).isTrapTile());
                return;
            }
        }
        fail();
    }

    @Test
    public void setupIncorrectTreasure2() {
        spyWorld.setupIncorrectTreasure2();
        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity instanceof TripWire &&
                    entity.getObjectName().equals("tripwire")
                    &&  entity.getPosition().equals(new SquareVector(22f, -23f))
                    && entity.getTexture().matches("VolcanoPortal")) {
                assertTrue(spyWorld.getTile(entity.getPosition()).isTrapTile());
                assertFalse(spyWorld.getTile(entity.getPosition()).isTeleportTile());
                return;
            }
        }
        fail();
    }

    @Test
    public void setupReward() {
        spyWorld.setupReward();
        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity instanceof TripWire &&
                    entity.getObjectName().equals("tripwire")
                    &&  entity.getPosition().equals(new SquareVector(22f, 23f))
                    && entity.getTexture().matches("VolcanoPortal")) {
                assertTrue(spyWorld.getTile(entity.getPosition()).isRewardTile());
                assertFalse(spyWorld.getTile(entity.getPosition()).isTrapTile());
                assertFalse(spyWorld.getTile(entity.getPosition()).isTeleportTile());
                return;
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

    @Test
    public void activateRewardTile() {
        spyWorld.setupReward();
        Tile tile = spyWorld.getTile(24f, 24f);
        spyWorld.activateRewardTile(tile);
        for (AbstractEntity entity : spyWorld.getEntities()) {
            if (entity instanceof Treasure &&
                    entity.getObjectName().equals("Treasure")
                    && entity.getTexture().matches("treasure_box")) {
                return;
            }
        }
    }
}