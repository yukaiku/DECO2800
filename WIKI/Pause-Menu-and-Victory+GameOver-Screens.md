# managers/GameManager.java
* Added new attribute named `state` describes the current state of the game. There are 4 value:
  * _RUN_: The game current normally.
  * _PAUSED_: The game is paused by the player.
  * _VICTORY_: The player has won the game by collecting all the orbs.
  * _GAMEOVER_: The player has lost the game by being killed by the enemies.
# GameScreen.java
* The `render()` method has been edited. It now renders the game based on the state of the game.
  * _RUN_: The world and entities are rendered usually.
  * _PAUSE_: The world and entities are stopped from being rendered. The **Pause Menu** is rendered instead. The game is paused when the player hits `ESC`.
  * _VICTORY_: The world and entities are stopped from being rendered. The **Victory Screen** is rendered instead.
  * _GAMEOVER_: The world and entities are stopped from being rendered. The **Game Over Screen** is rendered instead.
* `renderGame()`: This method is called when the state of the game is _RUN_.
* `renderPauseMenu()`: This method is called when the state of the game is _PAUSED_. It renders the design of the pause modal and two buttons inside the modal.
  * `resumeButton`: When this button is clicked the game will be resumed.
  * `quitButton`: This button redirects the player to the main menu.
* `renderGameResult()`: This method is called when the state of the game is _VICTORY_ or _GAMEOVER_. It renders the design based on the game result (Victory or GameOver) and the `quitButton`.
  * `quitButton`: This button redirects the player to the main menu.
* `dispose()`: This method is called when the player clicks the `quitButton`. It disposes the `stage` of `GameScreen` and removes the observer.
# renderers/PauseModal.java
* This class implements `Renderer`.
* It renders the modal of the **Pause Menu**.
* The pause modal is rendered fixed in the middle of the screen.
# renderers/Result.java
* This class implements `Renderer`.
* It renders the design of the **Victory Screen** if the state is _VICTORY_ and renders the design of the **Game Over Screen** if the state is _GAMEOVER_.
# renderers/BlackBackground.java
* This class implements `Renderer`.
* It renders the dark black background.
* This black background is used in rendering the **Pause Menu** and **Victory Screen**/**Game Over Screen**
### Programmer: @quan281999
### Designer: @jisungkim.k 
### Documentation by @quan281999