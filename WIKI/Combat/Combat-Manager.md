## Overview
[Combat Entities](/combat/combat-entities) and their respective [Combat Tasks](/combat/combat-tasks) are created in the Combat Manager. The Combat Manager has a touchdown listener that listens for a left mouse click and then spawns the required Combat Entities. It also currently has an experimental arrow key listener implemented to test if that is possibly an easier control layout.
## Class Diagram
![CombatManagerClassDiagram](uploads/1bd3ad9ea496a83dc847c5989a5cdbf6/CombatManagerClassDiagram.PNG)
## Improvements
- Implement an hotbar/inventory system so that a player can have multiple combat entities at once. This would also allow for the Combat Manager to be more generic and generate a combat entity based on the class of the entity that is selected in the hotbar.