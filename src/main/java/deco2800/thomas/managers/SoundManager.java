package deco2800.thomas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager extends AbstractManager {
	
    public void playSound(String soundName) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + soundName));
        sound.play(1);
    }
}
