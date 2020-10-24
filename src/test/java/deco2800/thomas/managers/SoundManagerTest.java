package deco2800.thomas.managers;

import com.badlogic.gdx.files.FileHandle;
import deco2800.thomas.BaseGDXTest;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Tests the SoundManager class.
 */
public class SoundManagerTest extends BaseGDXTest {
	// State tracking for missing files during LoadSound.
	public class FileExistResult {
		private boolean allFilesExist = true;
		private String missingFiles = "Missing Files:";

		public void missingFile(String filename) {
			missingFiles += "\n" + filename;
			allFilesExist = false;
		}

		public boolean testPassed() {
			return allFilesExist;
		}

		public String getMissingFiles() {
			return missingFiles;
		}
	}

	/**
	 * Tests that all sounds in sound manager can be loaded.
	 */
	@Test
	public void testSoundsExist() {
		FileExistResult result = new FileExistResult();

		// Setup mocks so that we check that files exist instead of trying to load them.
		Gdx.audio = mock(Audio.class);
		Gdx.files = mock(Files.class);
		FileHandle fh = mock(FileHandle.class);
		Sound sm = mock(Sound.class);
		when(Gdx.files.internal(anyString())).then(invocation -> {
			File f = new File((String)invocation.getArgument(0));
			if (!f.exists()) {
				result.missingFile(invocation.getArgument(0));
			}
			return fh;
		});
		when(Gdx.audio.newSound(any(FileHandle.class))).thenReturn(sm);

		// Load sounds, and check result
		(new SoundManager()).loadSound();
		assertTrue(result.getMissingFiles(), result.testPassed());
	}

	/**
	 * Tests that no exception is thrown when a file doesn't exist.
	 * (Expects no exception)
	 */
	@Test
	public void testMissingSound() {
		// Setup mocks to through FileNotFound exception
		Gdx.audio = mock(Audio.class);
		Gdx.files = mock(Files.class);
		when(Gdx.files.internal(anyString())).then(invocation -> {
			throw new FileNotFoundException();
		});

		// Try load sound manager
		SoundManager s = new SoundManager();
		s.loadSound();
	}

	/**
	 * Tests that no exception is thrown when an ambience track doesn't exist.
	 * (Expects no exception)
	 */
	@Test
	public void testPlayMissingAmbience() {
		// Try play a non existent ambience track
		SoundManager s = new SoundManager();
		s.playAmbience("doesNotExist");
	}

	/**
	 * Tests that no exception is thrown when an music track doesn't exist.
	 * (Expects no exception)
	 */
	@Test
	public void testPlayMissingMusic() {
		// Try play a non existent music track
		SoundManager s = new SoundManager();
		s.playMusic("doesNotExist");
	}

	/**
	 * Tests that no exception is thrown when a sound doesn't exist.
	 * (Expects no exception)
	 */
	@Test
	public void testPlayMissingSound() {
		// Setup mocks to load sfx
		Gdx.audio = mock(Audio.class);
		Gdx.files = mock(Files.class);
		Sound sm = mock(Sound.class);
		when(Gdx.files.internal(anyString())).thenReturn(null);
		when(Gdx.audio.newSound(Gdx.files.internal(anyString()))).thenReturn(sm);

		// Try play a non existent music track
		SoundManager s = new SoundManager();
		s.playSound("doesNotExist");
	}

	/**
	 * Confirms that the SoundManager attempted to play a sound file.
	 * Not a very useful test, but a simple mocking example.
	 */
	@Test
	public void testPlaySound() {
		Gdx.audio = mock(Audio.class);
		Gdx.files = mock(Files.class);
		Sound sm = mock(Sound.class);

		// Need to mock the call to Gdx.files.internal(...) even though its value is not used.
		when(Gdx.files.internal(anyString())).thenReturn(null);
		when(Gdx.audio.newSound(Gdx.files.internal(anyString()))).thenReturn(sm);

		// Load sounds in sound manager
		SoundManager s = new SoundManager();
		s.loadSound();

		/* This sound must exist */
		s.playSound("explosion");
		verify(sm).play(anyFloat());
	}

	/**
	 * Black box test of changing volume.
	 */
	@Test
	public void volumeTest() {
		SoundManager s = new SoundManager();
		s.setVolume(0.5f);
		assertEquals(0.5f, s.getVolume(), 0.0001f);
	}

	/**
	 * Tests that changing the volume will call setVolume on currently playing looping sounds.
	 */
	@Test
	public void volumeOfLoopingSoundsTest() {
		Gdx.audio = mock(Audio.class);
		Gdx.files = mock(Files.class);
		Sound sm = mock(Sound.class);
		when(sm.loop(anyFloat())).thenReturn(1L);
		when(Gdx.files.internal(anyString())).thenReturn(null);
		when(Gdx.audio.newSound(Gdx.files.internal(anyString()))).thenReturn(sm);

		SoundManager s = new SoundManager();
		s.playAmbience("placeholder");
		s.playMusic("placeholder");
		s.setVolume(0.5f);
		verify(sm, times(2)).setVolume(1L, 0.5f);
	}

	/**
	 * Tests that stopping ambience invokes both stop() and dispose().
	 */
	@Test
	public void testStopAmbience() {
		Gdx.audio = mock(Audio.class);
		Gdx.files = mock(Files.class);
		Sound sm = mock(Sound.class);
		when(Gdx.files.internal(anyString())).thenReturn(null);
		when(Gdx.audio.newSound(Gdx.files.internal(anyString()))).thenReturn(sm);

		SoundManager s = new SoundManager();
		s.playAmbience("placeholder");
		s.stopAmbience();
		verify(sm).stop();
		verify(sm).dispose();
	}

	/**
	 * Tests that stopping music invokes both stop() and dispose().
	 */
	@Test
	public void testStopMusic() {
		Gdx.audio = mock(Audio.class);
		Gdx.files = mock(Files.class);
		Sound sm = mock(Sound.class);
		when(Gdx.files.internal(anyString())).thenReturn(null);
		when(Gdx.audio.newSound(Gdx.files.internal(anyString()))).thenReturn(sm);

		SoundManager s = new SoundManager();
		s.playMusic("placeholder");
		s.stopMusic();
		verify(sm).stop();
		verify(sm).dispose();
	}
}
