package deco2800.thomas.entities.items;

import deco2800.thomas.managers.*;
import deco2800.thomas.worlds.TestWorld;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, DatabaseManager.class, PlayerPeon.class, Item.class})
@PowerMockIgnore({"jdk.internal.reflect.*"})
public class ItemTest extends BaseGDXTest{

    @Mock
    private GameManager mockGM;
    private TestWorld world;
    public PlayerPeon player;

    @Before
    public void setUp() throws Exception {
        player = new PlayerPeon(0f, 0f, 0.05f);

        world = new TestWorld();
        world.setPlayerEntity(player);

        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(world);

        // Mocked input manager
        InputManager Im = new InputManager();

        OnScreenMessageManager mockOSMM = mock(OnScreenMessageManager.class);
        when(mockGM.getManager(OnScreenMessageManager.class)).thenReturn(mockOSMM);

        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(Im);

        // Mocked texture manager
        TextureManager mockTM = new TextureManager();
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(mockTM);
    }

    @Test
    public void getItemName() {
        Item testPotion = new Item("health_potion",50);
        Assert.assertEquals("health_potion",testPotion.getItemName());
    }

    @Test
    public void getCurrencyValue() {
        Item testPotion = new Item("health_potion",50);
        Assert.assertEquals(50,testPotion.getCurrencyValue());
    }

    @Test
    public void randomItemPositionGenerator() {
        List<Integer> numListX = new ArrayList<>();
        List<Integer> numListY = new ArrayList<>();
        int height = 25;
        int width = 50;
        int randomX = Item.randomItemPositionGenerator(height);
        int randomY = Item.randomItemPositionGenerator(width);
        for (int i = -height ; i < height; i++){
            numListX.add(i);
        }
        for (int i = -width; i < width; i++){
            numListY.add(i);
        }
        Assert.assertEquals(true,numListX.contains(randomX));
        Assert.assertEquals(true,numListY.contains(randomY));
    }


    @Test
    public void chargePlayer() {
        Item testPotion = new Item("health_potion",50);
        player.addMoney(100);
        testPotion.chargePlayer();
        Assert.assertEquals(50,player.getWallet(),0.5);
    }
}