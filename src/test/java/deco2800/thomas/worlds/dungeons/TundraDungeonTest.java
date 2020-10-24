package deco2800.thomas.worlds.dungeons;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.*;

import java.io.File;
import java.util.ArrayList;

import static org.mockito.Mockito.spy;

public class TundraDungeonTest extends BaseGDXTest {
	/**
	 * Instance being tested.
	 */
	private static AbstractWorld spyTundraDungeon;

	/**
	 * Set up - run before each test is executed
	 */
	@BeforeClass
	public static void setUpClass() {
		spyTundraDungeon = new TundraDungeon();
		spy(spyTundraDungeon);
	}

	/**
	 * Tear down - run after each test is executed
	 */
	@AfterClass
	public static void tearDownClass() {
		spyTundraDungeon = null;
	}

	@Test
	public void testSize() {
		Assert.assertEquals(TundraDungeon.DEFAULT_WIDTH, spyTundraDungeon.getWidth());
		Assert.assertEquals(TundraDungeon.DEFAULT_HEIGHT, spyTundraDungeon.getHeight());
	}

	@Test
	public void testGetType() {
		Assert.assertEquals("TundraDungeon", spyTundraDungeon.getType());
	}

	@Test
	public void testWordFileExists() {
		File file = new File(((TundraDungeon) spyTundraDungeon).getWordFilePath());
		Assert.assertTrue(file.exists() && !file.isDirectory());
	}

	@Test
	public void testWordsAreFiveLetters() {
		ArrayList<String> words = ((TundraDungeon) spyTundraDungeon).readWords();
		for (String word : words) {
			Assert.assertEquals(5, word.length());
		}
	}
}
