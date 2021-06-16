Previously, the player used the mouse click to make the movement, however, we changed it into WASD in order to let the mouse click for attacking.

# PlayerMovement

![Class_Diagram1](uploads/98890034f9a12216c1b120a322d28306/Class_Diagram1.png)

## How it works ?

- We have 5 type of **Direction**: UP, DOWN, LEFT, RIGHT and NONE for not moving.
- The movement of the entity is rendered through the `onTick()` in the **MovementTask** continuously.
- Each AgentEntity has a **movingDirection** for continuous movement. The `onTick()` require that variable in order to know whether it should keep rendering the entity movement or not. If we are not using this variable, the player have to click the button multiple times to let the player move continuously.
- We have 2 types of movement
  - The `normalMovement()` is for the standard player and it works as we discussed.
  - The `autoMovement()` is for the entities who want to make a path finding process. It will calculate and find the path to let the entity get to the destination.

# Documentation by @jayhuynh239
