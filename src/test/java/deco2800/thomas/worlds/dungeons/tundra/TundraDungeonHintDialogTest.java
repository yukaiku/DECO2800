package deco2800.thomas.worlds.dungeons.tundra;

import deco2800.thomas.BaseGDXTest;
import org.junit.Assert;
import org.junit.Test;

public class TundraDungeonHintDialogTest extends BaseGDXTest {
	@Test
	public void testLock() {
		TundraDungeonHintDialog.lock();
		Assert.assertTrue(TundraDungeonHintDialog.isLocked());
		TundraDungeonHintDialog.release();
		Assert.assertFalse(TundraDungeonHintDialog.isLocked());
	}
}
