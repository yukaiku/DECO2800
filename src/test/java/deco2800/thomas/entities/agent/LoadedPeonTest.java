package deco2800.thomas.entities.agent;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.managers.*;
import deco2800.thomas.worlds.TestWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, DatabaseManager.class, PlayerPeon.class})
@PowerMockIgnore({"jdk.internal.reflect.*"})
public class LoadedPeonTest extends BaseGDXTest {

    @Mock
    private GameManager mockGM;
    private TestWorld world;
    public PlayerPeon player;

    @Before
    public void setUp() throws Exception {
        player = new PlayerPeon(0f, 0f, 0.05f);
        player.setWallet(0);

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
    public void getWallet() {
        PlayerPeon ply = new PlayerPeon(0f, 0f, 0.03f);

        float balance = ply.getWallet();
        assertEquals((int)balance, 0);

        ply.addMoney(1);
        balance = ply.getWallet();
        assertEquals((int)balance, 1);
    }

    @Test
    public void checkBalance() {
        float balance = PlayerPeon.checkBalance();
        assertEquals((int)balance, 0);

        PlayerPeon.credit(1);
        balance = PlayerPeon.checkBalance();
        assertEquals((int)balance, 1);
    }

    // Has to be > 0
    @Test
    public void debit() {
        PlayerPeon.debit(1000);
        assertEquals(PlayerPeon.checkBalance(), -1000);

        PlayerPeon.debit(-1000);
        assertEquals(PlayerPeon.checkBalance(), -1000);
    }

    @Test
    public void credit() {
        PlayerPeon.credit(1000);
        assertEquals(PlayerPeon.checkBalance(), 1000);

        PlayerPeon.credit(-1000);
        assertEquals(PlayerPeon.checkBalance(), 1000);
    }

    @Test
    public void takeMoney() {
        PlayerPeon ply = new PlayerPeon(0f, 0f, 0.03f);

        ply.takeMoney(1);
        float balance = ply.getWallet();
        assertEquals((int)balance, -1);
    }

    @Test
    public void addMoney() {
        PlayerPeon ply = new PlayerPeon(0f, 0f, 0.03f);

        ply.addMoney(1);
        float balance = ply.getWallet();
        assertEquals((int)balance, 1);
    }
}
