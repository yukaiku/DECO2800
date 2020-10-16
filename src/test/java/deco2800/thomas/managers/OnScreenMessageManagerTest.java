package deco2800.thomas.managers;
import deco2800.thomas.BaseGDXTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class OnScreenMessageManagerTest extends BaseGDXTest {
    private OnScreenMessageManager onScreenMessageManager;
    /***
     * Sets up the entities and info needed for testing the manager
     */
    @Before
    public void setUp(){
        onScreenMessageManager = new OnScreenMessageManager();
    }

    @Test
    public void testClassVariables(){
        //testing class variables
        assertEquals(0,onScreenMessageManager.getMessages().size());
        assertEquals(false,onScreenMessageManager.isTyping());
        assertEquals("Type your message",onScreenMessageManager.getUnsentMessage());
    }

    @Test
    public void testMessage(){
        for (int i=0; i < 21; i++){
            onScreenMessageManager.addMessage(Integer.toString(i));
            assertEquals(Integer.toString(i), onScreenMessageManager.getMessages().get(i));
        }
        assertEquals("20",onScreenMessageManager.getMessages().get(20));
        onScreenMessageManager.addMessage("21");
        assertEquals("1",onScreenMessageManager.getMessages().get(0));
        assertEquals("21",onScreenMessageManager.getMessages().get(20));
    }


    @After
    public void tearDown() {
        onScreenMessageManager = null;
    }
}
