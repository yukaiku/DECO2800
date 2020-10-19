package deco2800.thomas.managers;

import com.badlogic.gdx.files.FileHandle;
import deco2800.thomas.BaseGDXTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
	/**
	 * Tests that all sounds in sound manager can be loaded.
	 */
	@Test
	public void testSoundsExist() {
		// Setup mocks so that we check that files existing instead of trying to load them.
		Gdx.audio = mock(Audio.class);
		Gdx.files = mock(Files.class);
		FileHandle fh = mock(FileHandle.class);
		Sound sm = mock(Sound.class);
		when(Gdx.files.internal(anyString())).then(invocation -> {
			File f = new File((String)invocation.getArgument(0));
			if (f.exists()) {
				return fh;
			} else {
				throw new FileNotFoundException(invocation.getArgument(0));
			}
		});
		when(Gdx.audio.newSound(any(FileHandle.class))).thenReturn(sm);

		new SoundManager();
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

		SoundManager s = new SoundManager();
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
