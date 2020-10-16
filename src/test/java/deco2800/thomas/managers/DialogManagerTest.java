package deco2800.thomas.managers;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.ItemBox;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.items.Item;
import deco2800.thomas.entities.items.Treasure;
import deco2800.thomas.worlds.TestWorld;
import deco2800.thomas.worlds.Tile;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class DialogManagerTest extends BaseGDXTest {

	@Test
	public void checkConstructor() {
		ArrayList<AbstractDialogBox> boxes = new ArrayList<>();
		TestWorld world = new TestWorld();
		Tile tile = new Tile("treasure-box");
		PlayerPeon player = mock(PlayerPeon.class);
		Item item = new Treasure(tile, false, player, "volcano");
		ItemBox tester = new ItemBox(item, "Treasure Box"
				, "0", "some description", "volcano");

		// ready is false since game manager is null. 
		DialogManager dialog = new DialogManager(world, player, boxes);
		Assert.assertFalse(dialog.getReady());
	}
}
