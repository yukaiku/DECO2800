package deco2800.thomas.entities;

import com.badlogic.gdx.math.Vector3;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.managers.DatabaseManager;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.managers.OnScreenMessageManager;
import deco2800.thomas.renderers.PotateCamera;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.entities.NPC.NonPlayablePeon;
import deco2800.thomas.util.SquareVector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.powermock.core.classloader.annotations.PowerMockIgnore;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, DatabaseManager.class, PlayerPeon.class})
@PowerMockIgnore({"jdk.internal.reflect.*"})
public class NonPlayablePeonTest extends BaseGDXTest {
	SquareVector position1 = new SquareVector(1,1); 
	SquareVector position2 = new SquareVector(2,3);
	PlayerPeon current_player = new PlayerPeon(5,3,2);
	PotateCamera camera = new PotateCamera(100, 100);

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@Mock
	private GameManager mockGM;

	@Before
	public void Setup() {
		// setup to listen to System.out.print calls.
		System.setOut(new PrintStream(outContent));

		mockGM = mock(GameManager.class);
		mockStatic(GameManager.class);

		when(GameManager.get()).thenReturn(mockGM);
		when(mockGM.getCamera()).thenReturn(camera);

		// Mocked input manager
		InputManager Im = new InputManager();

		OnScreenMessageManager mockOSMM = mock(OnScreenMessageManager.class);
		when(mockGM.getManager(OnScreenMessageManager.class)).thenReturn(mockOSMM);

		when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(Im);
	}

	@After
	public void cleanup() {
		System.setOut(originalOut);
	}

	@Test 
	public void checkPosition(){
		// check that it spawns.
	// check name is correct 
	// check ends up in the same place. 
		NonPlayablePeon basicNPC= new NonPlayablePeon("NPC1",position1);
		assertEquals(basicNPC.position, position1);
	}
	
	@Test 
	public void checkName(){
		NonPlayablePeon basicNpc = new NonPlayablePeon("NPC1", position2);
		assertEquals(basicNpc.getName(), "NPC1");
	}
	
	
	@Test 
	// check that feet actually obstructed 
	public void checkTileObstructed(){
		Tile A = new Tile ("square2"); 
		A.setCol(2);
		A.setRow(3);
		// probably not important 
		SquareVector position2 = new SquareVector(2,3);
		NonPlayablePeon basicNpc = new NonPlayablePeon("NPC1", position2);
		List<NonPlayablePeon> npnSpawns = new ArrayList<>();
		npnSpawns.add(basicNpc);
		npnSpawns.add(new NonPlayablePeon("Fred", current_player.getPosition()));
		current_player.setPosition(2,3,1);
		
		// after world has been added to game manager.
		// shouldn't be allowed to spawn there?
		// testing -> create new game, new game world, ENUM test world, do 
		// that, create player etc, and then check tile. 
	}
	
	@Test 
	public void checkInteraction() {
		float col = position2.getCol();
		float row = position2.getRow();
		float[] coords = WorldUtil.colRowToWorldCords(row, col);
		Logger.getAnonymousLogger().info(String.format("%f %f %f %f", col, row, coords[0], coords[1]));
		Vector3 worldCoords = new Vector3((int)coords[0], (int)coords[1], 0);
		Logger.getAnonymousLogger().info(String.format("Position: (%f, %f)\nCoords: (%f, %f)\nWorld: (%f, %f)",
				col, row, coords[0], coords[1], worldCoords.x, worldCoords.y));
		NonPlayablePeon basicNpc = new NonPlayablePeon("NPC1", position2);

		Vector3 screen = GameManager.get().getCamera().project(worldCoords);
		Logger.getAnonymousLogger().info(String.format("Position: (%f, %f)\nCoords: (%f, %f)\nWorld: (%f, %f)\nCamera: (%f, %f)",
				col, row, coords[0], coords[1], worldCoords.x, worldCoords.y, screen.x, screen.y));
		basicNpc.notifyTouchDown((int)screen.x,(int)screen.y, 0 ,0);
		//assertEquals("Interacting!", outContent.toString());
	}
}
