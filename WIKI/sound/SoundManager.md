# ***SoundManager***
The SoundManager provides a facade for playing music and sounds in the game.

## Sound Types
There are 3 types of sounds implemented, which each have different behaviours based on resource usage and performance requirements.

### Ambience / Music
Ambience and music tracks are not preloaded until they are required. As these tracks often have high memory requires, preloading all tracks that are used in the game is an excessive use of memory. Since only 1 of these tracks is played at a time, and they loop for the duration of a level, these are only preloaded when entering the new level.

Internally in the SoundManager, these are referred to as a ***Song*** resource.

### Sound Effect
Sound effects are often short, and played frequently. Therefore these are preloaded into memory at the beginning of the game, and stay in memory until the game is exited.

Internally in the SoundManager, these are referred to as a ***Sound***  resource.

### Boss Music
The Boss music is an exception to general music rule. Since the boss music needs to start immediately when entering a battle, and preloading at this time will cause lag, the boss music is loaded as a SoundEffect. However, unlike regular SoundEffects the Boss Music resource is played as a loop.

## Implementation
The SoundManager does not rely on any other classes except those that are a part of the LibGDX library. The Sounds are stored in a _Pair<Sound or String, Float>_ structure. Songs are stored as a string to the resource file, while Sounds are stored as a Sound resource. The Float represents the _base volume_ of the sound resource, and is used to balance the audio levels between different sound effects and music.

### Adding New Resources
_Adding a new Ambience or Music track:_

    // ... inside the try { } block of loadSound()
    // Where 0.8f is the base volume of the audio track
    addNewSong("nameOfSong", "internal/file/path.ogg", 0.8f);

_Adding a new Sound Effect:_

    // ... inside the try { } block of loadSound()
    // Where 0.8f is the base volume of the audio track
    addNewSound("nameOfSound", "internal/file/path.wav", 0.8f);

### Playing Sounds in Game
_Playing one shot SoundEffect:_

    // ... wherever sound triggers from
    GameManager.getManagerFromInstance(SoundManager.class).playSound("name");

_Playing ambience / music:_

    GameManager.getManagerFromInstance(SoundManager.class).playMusic("name");
    GameManager.getManagerFromInstance(SoundManager.class).stopMusic();
    GameManager.getManagerFromInstance(SoundManager.class).playAmbience("name");
    GameManager.getManagerFromInstance(SoundManager.class).stopAmbience();

_Playing Boss Music:_

    GameManager.getManagerFromInstance(SoundManager.class).playBossMusic("name");
    GameManager.getManagerFromInstance(SoundManager.class).stopBossMusic();

## Master Volume
Part of the requirements for the SoundManager was to allow a master volume control so that the player can adjust the game volume.

### Implementation
All sound resources (whether preloaded or not) have a base volume between 0.0f and 1.0f, where 0 is muted and 1.0f is 100% volume. The SoundManager has an additional private field for volume which also has a ranged between 0.0f and 1.0f, where 0 is muted and 1.0f is 100% volume. The value passed to LibGDX is the product of these two values.

Since looping sounds will continue to play at the volume they were set to when the loop was started, a reference to those loops is stored, and we can then adjust their volume when the SoundManagers.setVolume is called.

### Usage

    // Set master volume to 80%:
    GameManager.getManagerFromInstance(SoundManager.class).setVolume(0.8f);