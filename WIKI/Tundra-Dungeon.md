# Introduction

To obtain a new skill and get out of the room, the player needs to look for a hidden message inside the room. The hidden message is a meaningful 5-letter English word. First, the player needs to look for the letters. Each letter is hidden inside some tile. To look for the letter, the player needs to go around the dungeon. If the player arrives at a tile that contains a letter, there will be a dialog box notifying the player. After gathering all 5 letters, the player can come up with a word and type that into the encryption machine. If the word is correct, then the exit portal will come up and the player is rewarded with a new skill.

# Implementation details

## The `TundraDungeon` class

*located at `/worlds/dungeons/TundraDungeon.java`*

This class inherits the `AbstractWorld` class. The current default size is 10-by-10. A bigger world size will take the player longer to gather all the letters (on average), which 

## The Dialog classes

*located at `/worlds/dungeons/tundra`*

These classes inherit the `com.badlogic.gdx.scenes.scene2d.ui.Dialog` class of libgdx.

### `TundraDungeonDialog.java`

This class directly inherits the `Dialog` class of libgdx.

One important pattern that is utilized by some of these dialogs is that they will appear when the player navigates to a certain tile. To make this happen, the `onTick` method of the `TundraDungeon` class always checks for the position of the player entity against all the special tiles. Once the player has navigated to a special tile, a dialog will be created and shown. To prevent a dialog from being created multiple times by successive calls of the `onTick` method, some dialog classes offer the `isLocked`, `lock`, and `release` methods to handle the situation.

Notable methods:

- `public TundraDungeonDialog show(int sizeX, int sizeY, int posX, int posY)`: show a dialog with width of `sizeX`, height of `sizeY` at position `(posX, posY)`

### `TundraDungeonAnnouncementDialog.java`

*This class directly inherits the `TundraDungeonDialog` class.*

The purpose of this class is to announce the player if their answer is correct or not.

### `TundraDungeonHintDialog.java`

*This class directly inherits the `TundraDungeonDialog` class.*

A hint dialog comes up when the player navigates to a tile that hides a letter. It tells the player what the hidden letter is.

### `TundraDungeonInstructionDialog.java`

*This class directly inherits the `TundraDungeonDialog` class.*

The purpose of this class is to create a sequence of instructions by showing a sequence of dialogs. At each step of the instruction, the player can:

- use the `Back` button to go to the previous instruction.
- use the `Next` button to go to the next instruction.
- use the `Skip` button to stop the instructions.

The sequence of instruction dialogs will come up when the player enters the dungeon. After the player has closed down the instruction dialogs, they can reopen these dialogs by navigating to the help tile.

### `TundraDungeonTextDialog.java`

*This class directly inherits the `TundraDungeonDialog` class.*

The purpose of this class is to create a text dialog that the player can type their answer in.

# Further Improvements

One improvement that can be made is coming up with a better way to place the dialog. At the moment, there is no concrete way of placing the dialog at the center of the scene, which is the desired behavior.

In addition, some user testing will be required to come up with other improvements in the future.

# Changelog

*Last update: 12:06 Oct 11, 2020*

*Documentation by @nathan-nguyen*