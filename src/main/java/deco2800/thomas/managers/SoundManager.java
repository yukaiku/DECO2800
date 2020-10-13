package deco2800.thomas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoundManager extends AbstractManager {
	private final Map<String, String> sounds;
	private Sound ambience;

	public SoundManager() {
		sounds = new ConcurrentHashMap<>();
		sounds.put("swampAmbience", "resources/sounds/ambience/swamp_ambience.ogg");
		sounds.put("desertAmbience", "resources/sounds/ambience/desert_ambience.ogg");
		sounds.put("tundraAmbience", "resources/sounds/ambience/tundra_ambience.ogg");
		sounds.put("volcanoAmbience", "resources/sounds/ambience/volcano_ambience.ogg");
	}

	public void stopAmbience() {
		if (ambience != null) {
			ambience.stop();
			ambience.dispose();
			ambience = null;
		}
	}

	public void setAmbience(String soundName) {
		try {
			stopAmbience();
			ambience = Gdx.audio.newSound(Gdx.files.internal(sounds.get(soundName)));
			ambience.loop();
		} catch (Exception e) {
			// Log
		}
	}

	public void playSound(String soundName) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("resources/sounds/" + soundName));
		sound.play(1);
	}
}
