package deco2800.thomas.worlds.dungeons.tundra;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.worlds.dungeons.TundraDungeon;
import org.junit.Assert;
import org.junit.Test;

public class TundraDungeonTextDialogTest extends BaseGDXTest {
	@Test
	public void testLock() {
		TundraDungeonTextDialog.lock();
		Assert.assertTrue(TundraDungeonTextDialog.isLocked());
		TundraDungeonTextDialog.release();
		Assert.assertFalse(TundraDungeonTextDialog.isLocked());
	}
}
