# worlds/TutorialWorld.java
* This world inherits from `AbstractWorld`.
* The size of the world is set up by default. Since this is just for helping the player get used to the game controls, the size of the world doesn't have to be too large.
  * `TUTORIAL_WORLD_WIDTH`: The default width of the world is 10
  * `TUTORIAL_WORLD_HEIGHT`: The default height of the world is 6
* There is a portal that allows the user to enter the game after training. The position of the portal is fixed in the middle of the bottom edge of the map.
* `generateTiles()`: This method generates the tiles of the map and also add agent entities to the world. Agents present in this tutorial include the main character, tutorial NPC, and the enemies.
* `generateEntities()`: This method adds static entities to the world. The purpose of these static entities is for decoration.
* **Update 25/10/2020:**
  * `generateItemEntities()`: This method adds random items in the tutorial world in order to guide players to pick up items.
  * The balance of the player is set to 1000 so that they can pick up items.
# renderers/components/GuidelineComponent.java
* This class implements `Renderer`.
* It renders the guideline to the game screen.
* The guideline is rendered fixed in the middle of the screen.
# GameScreen.java:
* Added guideline in the `render()` method.
* Added an event listener to the key `F9`. If the player is in the tutorial, they can press `F9` to toggle the guideline on and off.
# managers/GameManager.java
* Added a variable named `inTutorial` to check whether the play is in the tutorial world or not.
* This is to prevent players from being able to turn on the guideline during the main game.
# Barrel.java, Stash.java, Target.java, and Portal.java inside the entities folder
* Decorative entities for the tutorial.
### Programmer: @quan281999
### Designer: @jisungkim.k 
### Documentation by @quan281999