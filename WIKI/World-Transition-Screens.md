# managers/GameManager.java
* Added new values to the attribute named `state` describes the current state of the game:
  * _TRANSITION_: The game is in a transitional screen that briefs the player about the next stage.
# GameScreen.java
* The `render()` method has been edited. It now includes a transition state.
  * _TRANSITION_: The player is rendered into a transitional state upon completing the orb quest from the previous stage.
 The game is continued when the player hits the button.
* `renderTransitionScreen()`: This method is called when the state of the game is _TRANSITION_. It will render a transitional screen correlating to the world the player is going to explore next.


# renderes/TransitionScreen.java 
* This class implements `Renderer`.
* It renders the design of the **Transition Screen** if the state is _TRANSITION_ 

# renderers/BlackBackground.java
* This class implements `Renderer`.
* The black background is rendered below the transition screen similar to other screens such as victory and game over. [Victory and Gameover](Pause Menu and Victory+GameOver Screens)
### Programmer: @quan281999
### Designer: @jisungkim.k 
### Documentation by @yukaiku