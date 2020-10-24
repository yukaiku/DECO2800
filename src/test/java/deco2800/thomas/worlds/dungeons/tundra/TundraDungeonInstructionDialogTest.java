package deco2800.thomas.worlds.dungeons.tundra;

import deco2800.thomas.BaseGDXTest;
import org.junit.Assert;
import org.junit.Test;

public class TundraDungeonInstructionDialogTest extends BaseGDXTest {
	@Test
	public void testLock() {
		TundraDungeonInstructionDialog.lock();
		Assert.assertTrue(TundraDungeonInstructionDialog.isLocked());
		TundraDungeonInstructionDialog.release();
		Assert.assertFalse(TundraDungeonInstructionDialog.isLocked());
	}
}
