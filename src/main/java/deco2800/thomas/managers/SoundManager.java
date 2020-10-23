package deco2800.thomas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages game sounds, music and ambience.
 */
public class SoundManager extends AbstractManager {
	// Logger
	private final Logger logger;
	// Maps of sound effects
	private final Map<String, String> sounds;
	private final Map<String, Sound> soundEffects;
	// Looping sound resource references
	private Sound ambience = null;
	private long ambienceId;
	private Sound music = null;
	private long musicId;
	private Sound bossMusic = null;
	private long bossMusicId;
	// Global audio volume
	private float volume = 1.0f;
	private boolean soundLoaded = false;

	/**
	 * Create an instance of the SoundManager.
	 */
	public SoundManager() {
		logger = LoggerFactory.getLogger(SoundManager.class);
		sounds = new ConcurrentHashMap<>();
		soundEffects = new ConcurrentHashMap<>();
		soundEffects.put("explosion", Gdx.audio.newSound(
				Gdx.files.internal("resources/sounds/sfx/explosion_1.wav")));
	}

	/**
	 * Load sound on game start.
	 */
	public void loadSound() {
		try {
			// Long sound effects, or music that are not preloaded:
			sounds.put("swampAmbience", "resources/sounds/ambience/swamp_ambience.ogg");
			sounds.put("desertAmbience", "resources/sounds/ambience/desert_ambience.ogg");
			sounds.put("tundraAmbience", "resources/sounds/ambience/tundra_ambience.ogg");
			sounds.put("volcanoAmbience", "resources/sounds/ambience/volcano_ambience.ogg");
			sounds.put("menuMusic", "resources/sounds/music/menu_music.ogg");
			sounds.put("menuAmbience", "resources/sounds/ambience/menu_ambience.ogg");

			// boss musics need to be preloaded to avoid lag mid game.
			bossMusic = Gdx.audio.newSound(Gdx.files.internal("resources/sounds/music/boss_1.mp3"));

			// Sound effects that are preloaded (short duration)
			soundEffects.put("fireball", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/sfx/fireball_5.wav")));
			soundEffects.put("woodHit", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/sfx/mech_hit_3.wav")));
			soundEffects.put("fireHit", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/sfx/fireball_hit_3.wav")));
			soundEffects.put("windAttack", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/sfx/wind_1.wav")));
			soundEffects.put("button1", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/sfx/button_1.wav")));
			soundEffects.put("button2", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/sfx/button_2.wav")));
			soundEffects.put("dragon1", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/sfx/dragon_fire.mp3")));

		} catch (Exception e) {
			logger.error(Arrays.toString(e.getStackTrace()));
		} finally {
			soundLoaded = true;
		}
	}

	/**
	 * Sets the master volume of game audio.
	 * @param volume Volume, where 0f = off, and 1.0f = 100%
	 */
	public void setVolume(float volume) {
		this.volume = volume;
		if (ambience != null) {
			ambience.setVolume(ambienceId, volume);
		}
		if (music != null) {
			music.setVolume(musicId, volume);
		}
	}

	/**
	 * Returns the current master volume of game audio.
	 * @return Volume from 0f -> 1.0f
	 */
	public float getVolume() {
		return this.volume;
	}

	/**
	 * Stops any currently playing ambience track.
	 */
	public void stopAmbience() {
		stopSoundResource(ambience);
		ambience = null;
	}

	/**
	 * Sets, and starts playing a new ambience track.
	 * @param soundName Name of ambience track.
	 */
	public void playAmbience(String soundName) {
		if (!soundLoaded) loadSound();
		try {
			stopSoundResource(ambience);
			ambience = Gdx.audio.newSound(Gdx.files.internal(sounds.get(soundName)));
			ambienceId = ambience.loop(volume);
		} catch (Exception e) {
			logger.error(Arrays.toString(e.getStackTrace()));
		}
	}

	/**
	 * Stops any currently playing music.
	 */
	public void stopMusic() {
		stopSoundResource(music);
		music = null;
	}

	/**
	 * Sets, and starts playing a new music track.
	 * @param soundName Name of music track.
	 */
	public void playMusic(String soundName) {
		if (!soundLoaded) loadSound();
		try {
			stopSoundResource(music);
			music = Gdx.audio.newSound(Gdx.files.internal(sounds.get(soundName)));
			musicId = music.loop(volume);
		} catch (Exception e) {
			logger.error(Arrays.toString(e.getStackTrace()));
		}
	}

	/**
	 * Pause (not compose) the ambience and play boss music
	 * @param volume The volume of the music
	 */
	public void playBossMusic(float volume) {
		if (ambience != null) {
			ambience.stop();
		}
		bossMusicId = bossMusic.loop(volume);
	}

	/**
	 * Stop (not compose) the boss music and resume the ambience.
	 */
	public void stopBossMusic() {
		bossMusic.stop();
		if (ambience != null) {
			ambience.play();
		}
	}

	/**
	 * Avoid interrupting ambience and master volume.
	 * @param volume The volume of the music
	 */
	public void setBossMusicVolume(float volume) {
		bossMusic.setVolume(bossMusicId, volume);
	}

	/**
	 * Stops the given sound resource, and disposes of it.
	 * @param sound Sound resource to dispose..
	 */
	private void stopSoundResource(Sound sound) {
		if (sound != null) {
			sound.stop();
			sound.dispose();
		}
	}

	/**
	 * Plays a sound effect that has been preloaded.
	 * @param soundName ID of sound to play
	 */
	public void playSound(String soundName) {
		playSound(soundName, this.volume);
	}

	public void playSound(String soundName, float volume) {
		if (!soundLoaded) loadSound();
		try {
			Sound sound = soundEffects.get(soundName);
			sound.play(volume);
		} catch (Exception e) {
			logger.error(Arrays.toString(e.getStackTrace()));
		}
	}
}
