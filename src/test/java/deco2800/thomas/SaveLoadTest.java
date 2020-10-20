package deco2800.thomas;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.EntityCompare;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.managers.OnScreenMessageManager;
import deco2800.thomas.managers.*;
import deco2800.thomas.worlds.TestWorld;
import deco2800.thomas.worlds.Tile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, DatabaseManager.class, PlayerPeon.class})
@PowerMockIgnore({"jdk.internal.reflect.*"})
public class SaveLoadTest extends BaseGDXTest {
    private TestWorld w = null;
   
    @Mock
    private GameManager mockGM;
    
    
    @Before
    public void Setup() {
        w = new TestWorld();
        
        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);
        
        
        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(w);
        
        // Mocked input manager
        InputManager Im = new InputManager();
        
        OnScreenMessageManager mockOSMM = mock(OnScreenMessageManager.class);
        when(mockGM.getManager(OnScreenMessageManager.class)).thenReturn(mockOSMM);
        
        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(Im);

        // Mocked texture manager
        TextureManager mockTM = new TextureManager();
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(mockTM);
    }

    // TODO: Split this test up into multiple unit tests that test individual component functionality
    // TODO: instead of just testing the entire DatabaseManager in a single test.
    // TODO: Whenever this test fails it's impossible to know what went wrong.
    // TODO: If this test is intentionally testing system functionality, then
    // TODO: it still needs to be obvious where the test went wrong so it can be debugged
    // TODO: rather than it just saying "Test failed, good luck!"
    @Test
    public void SetMapTest() {
        CopyOnWriteArrayList<Tile> saveTileMap = new CopyOnWriteArrayList<>();
        Map<Integer, AbstractEntity> newEntities = new ConcurrentHashMap<>();
        float col_one = 1.0f;
        float row_one = 1.0f;
        float col_two = 4.0f;
        float row_two = 5.0f;
        saveTileMap.add(new Tile("grass_1_0", col_one, row_one));
        saveTileMap.add(new Tile("grass_1_0", col_two, row_two));
        w.setTiles(saveTileMap);

        newEntities.put(0, new PlayerPeon(1, 1, 1,10));

        List<AbstractEntity> testEntities = new ArrayList<>(w.getEntities());
//        deco2800.thomas.managers.DatabaseManager.saveWorld(w);


        TestWorld q = new TestWorld();
//        deco2800.thomas.managers.DatabaseManager.loadWorld(q);

        List<AbstractEntity> worldEntities = new ArrayList<>(q.getEntities());

        Collections.sort(testEntities, new EntityCompare());
        Collections.sort(worldEntities, new EntityCompare());


        for (int i = 0; i < saveTileMap.size(); i++) {
//            assertEquals(saveTileMap.get(i).getTextureName(), w.getTiles().get(i).getTextureName());
//
//            assertEquals(saveTileMap.get(i).getTileID(), w.getTiles().get(i).getTileID());
//            assertEquals(saveTileMap.get(i).getRow(), w.getTiles().get(i).getRow(), 0.001f);
//            assertEquals(saveTileMap.get(i).getCol(), w.getTiles().get(i).getCol(), 0.001f);
        }

        for (int i = 0; i < testEntities.size(); i++) {
            // Entities that aren't saved won't be in the list and therefore will cause an index range exception.
            if (testEntities.get(i).isSave()) {
//                assertEquals(testEntities.get(i).getEntityID(), worldEntities.get(i).getEntityID(), 1f);
//                assertEquals(testEntities.get(i).getTexture(), worldEntities.get(i).getTexture());
//                assertEquals(testEntities.get(i).getPosition(), worldEntities.get(i).getPosition());
            }
        }
   }
}
