package deco2800.thomas.managers;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


public class SoundManagerTest {

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
		when(Gdx.files.internal("resources/sounds/" + "file")).thenReturn(null);
		when(Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + "file"))).thenReturn(sm);
		// The following is not really necessary, given how playSound is implemented,
		// but it may be clearer as to what is happening in the tested code.
		when(sm.play(1)).thenReturn(1L);

		SoundManager s = new SoundManager();
		s.playSound("file");
		verify(sm).play(1);
	}

}
