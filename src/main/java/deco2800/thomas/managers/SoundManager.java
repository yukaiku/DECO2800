package deco2800.thomas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import org.javatuples.Pair;
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
	private final Map<String, Pair<String, Float>> sounds;
	private final Map<String, Pair<Sound, Float>> soundEffects;
	// Looping sound resource references
	private Sound ambience = null;
	private float ambienceVolume;
	private long ambienceId;
	private Sound music = null;
	private float musicVolume;
	private long musicId;
	private Sound bossMusic;
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
	}

	/**
	 * Load sound on game start.
	 */
	public void loadSound() {
		try {
			// Long sound effects, or music that are not preloaded:
			addNewSong("swampAmbience", "resources/sounds/ambience/swamp_ambience.ogg", 0.8f);
			addNewSong("desertAmbience", "resources/sounds/ambience/desert_ambience.ogg", 0.1f);
			addNewSong("tundraAmbience", "resources/sounds/ambience/tundra_ambience.ogg", 0.8f);
			addNewSong("volcanoAmbience", "resources/sounds/ambience/volcano_ambience.ogg", 0.8f);
			addNewSong("menuAmbience", "resources/sounds/ambience/menu_ambience.ogg", 1);

			// Boss music is the exception to the rule, and is preloaded
			addNewSound("bossMusic", "resources/sounds/music/boss_1.mp3", 0.8f);

			// Sound effects that are preloaded (short duration)
			addNewSound("fireball", "resources/sounds/sfx/fireball_5.wav", 1);
			addNewSound("woodHit", "resources/sounds/sfx/mech_hit_3.wav", 0.8f);
			addNewSound("fireHit", "resources/sounds/sfx/fireball_hit_3.wav", 1);
			addNewSound("windAttack", "resources/sounds/sfx/wind_1.wav", 1);
			addNewSound("explosion", "resources/sounds/sfx/explosion_1.wav", 0.7f);
			addNewSound("button1", "resources/sounds/sfx/button_1.wav", 0.1f);
			addNewSound("button2", "resources/sounds/sfx/button_2.wav", 0.2f);
			addNewSound("gameOver", "resources/sounds/sfx/game_over.ogg", 0.7f);
			addNewSound("victory", "resources/sounds/sfx/victory_sound.ogg", 1.0f);

			// Enemy sounds
			addNewSound("orcGrowl", "resources/sounds/sfx/orc_growl.wav", 0.5f);
			addNewSound("volcanoDragon", "resources/sounds/sfx/dragon_volcano.mp3", 0.8f);
			addNewSound("desertDragon", "resources/sounds/sfx/dragon_desert.mp3", 0.6f);
			addNewSound("tundraDragon", "resources/sounds/sfx/dragon_tundra.wav", 0.5f);
			addNewSound("swampDragon", "resources/sounds/sfx/dragon_swamp.wav", 0.8f);
			addNewSound("dragonGrowl", "resources/sounds/sfx/dragon_growl.wav", 0.8f);
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
			ambience.setVolume(ambienceId, volume * ambienceVolume);
		}
		if (music != null) {
			music.setVolume(musicId, volume * musicVolume);
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
			Pair<String, Float> songPair = sounds.get(soundName);
			ambience = Gdx.audio.newSound(Gdx.files.internal(songPair.getValue0()));
			ambienceVolume = songPair.getValue1();
			ambienceId = ambience.loop(volume * ambienceVolume);
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
			Pair<String, Float> songPair = sounds.get(soundName);
			music = Gdx.audio.newSound(Gdx.files.internal(songPair.getValue0()));
			musicVolume = songPair.getValue1();
			musicId = music.loop(volume * musicVolume);
		} catch (Exception e) {
			logger.error(Arrays.toString(e.getStackTrace()));
		}
	}

	/**
	 * Pause (not compose) the ambience and play boss music
	 * @param sound Id of song resource
	 */
	public void playBossMusic(String sound) {
		if (ambience != null) {
			ambience.pause();
		}
		bossMusic = loopSound(sound);
	}

	/**
	 * Stop (not compose) the boss music and resume the ambience.
	 */
	public void stopBossMusic() {
		if (bossMusic != null) {
			bossMusic.stop();
			bossMusic = null;
		}
		if (ambience != null) {
			ambience.resume();
		}
	}

	/**
	 * Toggles boss music if player enters/leaves dungeons
	 * @param play Play or pause the music
	 */
	public void toggleBossMusic(boolean play) {
		if (bossMusic != null) {
			if (play) {
				if (ambience != null) ambience.stop();
				bossMusic.resume();
			} else {
				if (ambience != null) ambienceId = ambience.loop(volume * ambienceVolume);
				bossMusic.pause();
			}
		}
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
		playSound(soundName, 0);
	}

	/**
	 * Plays a sound effect that has been preloaded.
	 * @param soundName ID of sound to play
	 * @param pan L/R channel, -1 is full left, 1 is full right and 0 is center.
	 */
	public void playSound(String soundName, float pan) {
		if (!soundLoaded) loadSound();
		try {
			Pair<Sound, Float> soundPair = soundEffects.get(soundName);
			soundPair.getValue0().play(volume * soundPair.getValue1(), 1f, pan);
		} catch (Exception e) {
			logger.error(Arrays.toString(e.getStackTrace()));
		}
	}


	/**
	 * Loops a sound that has been preloaded.
	 * @param soundName ID of sound to play
	 * @return Returns LibGDX sound of looping sound
	 */
	public Sound loopSound(String soundName) {
		if (!soundLoaded) loadSound();
		try {
			Pair<Sound, Float> soundPair = soundEffects.get(soundName);
			soundPair.getValue0().loop(volume * soundPair.getValue1());
			return soundPair.getValue0();
		} catch (Exception e) {
			logger.error(Arrays.toString(e.getStackTrace()));
		}
		return null;
	}

	/**
	 * Preloads a sound resource from an internal file.
	 * @param sound Sound ID
	 * @param filename Relative file path and name
	 * @param volume Base volume of sound resource
	 */
	private void addNewSound(String sound, String filename, float volume) {
		try {
			Sound soundResource = Gdx.audio.newSound(Gdx.files.internal(filename));
			soundEffects.put(sound, new Pair<>(soundResource, volume));
		} catch (Exception e) {
			logger.error(Arrays.toString(e.getStackTrace()));
		}
	}

	/**
	 * Adds a song filepath that can be accessed later to play
	 * ambience or music. Does not preload.
	 * @param song Sound ID
	 * @param filename Relative file path and name
	 * @param volume Base volume of song resource
	 */
	private void addNewSong(String song, String filename, float volume) {
		try {
			sounds.put(song, new Pair<>(filename, volume));
		} catch (Exception e) {
			logger.error(Arrays.toString(e.getStackTrace()));
		}
	}
}
