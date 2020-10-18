package deco2800.thomas.managers;
import deco2800.thomas.BaseGDXTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NetworkManagerTest extends BaseGDXTest {
    private NetworkManager networkManager;
    /***
     * Sets up the entities and info needed for testing the manager
     */
    @Before
    public void setUp(){
        networkManager = new NetworkManager();
    }

    @Test
    public void testClassVariables(){
        //testing class variables
        assertEquals(0,networkManager.getMessagesReceived());
        assertEquals(0,networkManager.getMessagesSent());
        assertEquals(false,networkManager.isHost());
        assertEquals(0,networkManager.getMessagesSent());
        assertEquals(-1,networkManager.getID());
        assertEquals("",networkManager.getUsername());
    }

    @Test
    public void testMessage(){
        assertEquals(0,networkManager.getMessagesReceived());
        networkManager.incrementMessagesReceived();
        assertEquals(1,networkManager.getMessagesReceived());

    }


    @After
    public void tearDown() {
        networkManager = null;
    }
}
