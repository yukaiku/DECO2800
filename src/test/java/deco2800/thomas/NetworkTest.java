package deco2800.thomas;

import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.NetworkManager;
import deco2800.thomas.managers.OnScreenMessageManager;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;


import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, NetworkManager.class})
@PowerMockIgnore({"jdk.internal.reflect.*"})


public class NetworkTest {

//    @Mock
//    private GameManager mockGameManager;
//
//    @Mock
//    private ClientListener mockListenerClient;

    private GameManager mockGM;

    private Server mockedServer;

    private Client mockedClient;

    @InjectMocks
    private NetworkManager serverManager;
    @InjectMocks
    private NetworkManager clientManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        // Mock the GameManager
        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(mockGM);

        // Mock the OnScreenMessageManager
        OnScreenMessageManager mockOSMM = mock(OnScreenMessageManager.class);
        when(mockGM.getManager(OnScreenMessageManager.class)).thenReturn(mockOSMM);

        // Mocking the client and Server class
        mockedClient = PowerMockito.mock(Client.class);
        mockedServer = PowerMockito.mock(Server.class);

        // NetworkManagers for a client and a host
        clientManager = new NetworkManager();
        serverManager = new NetworkManager();
    }

    @After
    public void cleanup() {
        mockedClient.stop();
        mockedClient.close();
        mockedServer.stop();
        mockedServer.close();
    }

    //TODO fix this test
    /*
    @Test (expected = IllegalStateException.class)
    public void clientCannotDeleteUser() {
        clientManager.serverDeleteUser("host");
    }*/


    @Test
    public void testIncrementMessagesReceived() {
        assertEquals(0, serverManager.getMessagesReceived());
        serverManager.incrementMessagesReceived();
        assertEquals(1, serverManager.getMessagesReceived());
    }

 
    @Test
    public void testGetClientUsernameNonExistingKey() {
        HashMap<Integer, String> expectedResult = new HashMap<>();

        serverManager.addClientConnection(0, "Client1");
        serverManager.addClientConnection(1, "Client2");

        expectedResult.put(0, "Client1");
        expectedResult.put(1, "Client2");
        assertNull(serverManager.getClientUsernameFromConnection(3));
    }

    @Test
    public void testGetClientUsernameExistingKey() {
        HashMap<Integer, String> expectedResult = new HashMap<>();

        serverManager.addClientConnection(0, "Client1");
        serverManager.addClientConnection(1, "Client2");

        expectedResult.put(0, "Client1");
        expectedResult.put(1, "Client2");
        assertEquals("Client2", serverManager.getClientUsernameFromConnection(1));
    }

    //////////////////////////////////////////
    //Tests involving client and server here//
    //////////////////////////////////////////
    @Test
    public void testGetUsernameAsHost() {
//        when(mockedUser.getUsername()).thenReturn("Host");
//        mockedUser.getUsername();
//        verify(mockedUser).getUsername();
        serverManager.startHosting("Host");
        assertEquals("Host", serverManager.getUsername());
    }

    @Test
    public void testGetUsernameAsClient() {
//        when(mockedUser.getUsername()).thenReturn("Bob");
//        mockedUser.getUsername();
//        verify(mockedUser).getUsername();
        clientManager.connectToHost("", "Client");
        assertEquals("Client", clientManager.getUsername());
    }

}
