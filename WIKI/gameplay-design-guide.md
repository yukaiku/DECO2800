# ***Gameplay Design Guide***
The purpose of this document is to aid and align design choices with regards to gameplay.

## Index
| Page |
| ---- |
| [Overview](#overview) |
| [Clarifications](#clarifications) |

## Overview
The player starts at the main menu, where they pick to start a new game. ***(Can they load an existing game?)*** Once a new game is selected, the player is brought to the Select Polyhedron screen where the player selects from 2 wizards to form their first [Polyhedron](#polyhedron). Initially, the player only has access to the ***2 Wizards and 2? Knights*** Once selected, the player is ported into the [Tutorial](#/tutorial-world) world, where they are taught how to control the player.

Once progressing out of the tutorial world, the player is ported to the first level where their quest to collect all 4 orbs begins. During each level, the player controls the Polyhedron to fight enemies throughout the world until they find the final Dragon boss for that level. Upon defeating the Dragon, an orb will drop that the player can collect. ***(Need to discuss how items + npc's are spawned, and interacted with. Items drop from defeating monsters, randomly spawn, or are bought from NPCs?)***

- Items like the orbs will drop from bosses upon defeating them, when picked up by players, they will gain a new spell. Other buff items(amulets, rings) or weapons/armor that increase player's attack/defense will drop from the smaller enemies orcs, goblins, etc. As for interaction with items and NPCs, we do plan on replacing the mouse clicking interaction with pressing keys instead, so that the combat can flow more smoothly in the case of wanting to purchase health potions in the middle of combat against enemies. Lastly, since no inventory exists currently and is possibly out of scope, all items will be consumed/equipped on purchase. ***- Team NPC & Items***

Collecting the orb grants a new skill for the wizard. Collecting the orb is the end of a level, and the ***player is brought back to the Polyhedron selection screen?*** **Big questions here:** 
1) Can the player now change wizard? Do we change the knight / mech here?
2) Or do we make the player play the same Wizard the whole game, always have the same knight, but allow the player to change the Mech (skill wise, not visually) based on what Orbs they have collected?

Once the player has selected their next Polyhedron, they are ported into the next level. Levels follow a fixed order that gets progressively more difficult as the game progresses. When the player has defeated all 4 bosses, the victory screen is shown.

Level order: Swamp -> Tundra -> Desert -> Volcano

## Clarifications
1) If you acquire Armour in level A, does it persist into level B etc?
2) Do health potions go into an inventory, or are the effects applied immediately upon pickup / purchase?


## Level Transition
The levels are stored in a 
GameManager.java
``` 
private ArrayList<WorldType> worldOrder;
```
The order of the world is swamp - > tundra -> desert -> volcano
As the player progress, I am planning to integrate a few changes as listed in this document:[GAME BALANCE](https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/issues/120). Once the player collects all 4 orbs a victory screen will be displayed.

***~Kai***