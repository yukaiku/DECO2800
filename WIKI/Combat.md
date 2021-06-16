# ***Combat***
This page describes the combat system implemented in this game. Combat combines many aspects of the game:
1) Player controls and UI;
2) Attacks the player can use, where they are targeted, and what they do;
3) Attacks the enemy can use, _(Note that the enemy team is responsible for targeting and decision making)_; and
4) Determining when an attack was successful.

| **Index** |
|-----------|
| [Gameplay](#gameplay) |
| [Design and Testing](#design-and-testing) |
| [Implementation](#implementation) |
| [Improvements](#improvements) |

## Gameplay
The combat gameplay consists of controlling a *Polyhedron*. In the game, a Polyhedron is a combined unit of the Wizard, the Knight and the Mech. The Knight controls the Mech, while the Wizard rides the Mech and can attack with magic freely.

### Wizard
The Wizard can use magic to attack. The Wizard starts with a single skill, and learns a new skill for killing a boss, and for completing certain puzzles in dungeons. The Wizard can access up to 5 skills, (1 base skill and +4 learned skills).

To use the Wizard's skills, left click where you want to attack. This will attack with the currently selected Skill from the Wizard's skill hotbar. You can change skills by pressing the keys 1, 2, 3, 4 and 5 respectively.

### Mech
The Mech is capable of moving, and has a single ***Ultimate*** attack. This attack is very potent, but has a long cooldown. The Mech only has access to a single skill, which is specified when you select your polyhedron at the beginning of the game.

### Knight
The Knight is controlling the Mech. To move the Mech, use the keys W, A, S and D. To use the Mech's ultimate attack, right click. Note, the many of the Mech's attacks are not targeted.

### Combat
Combat consists of both Melee and Ranged attacks. All attacks have a damage type:

| ***Damage Type*** |
| ----------------- |
| Common |
| Fire |
| Ice |
| Earth |
| Air |

All enemies _may_ be vulnerable to a damage type. Damage is calculated by this algorithm:

    damageTaken = damageApplied * ARMOUR_CONSTANT / armourRating;
    if (damageType == vulnerability && vulnerability != DamageType.NONE) {
        damageTaken *= 1.5;
    }

Here, the ARMOUR_CONSTANT is an arbitrary constant. Currently it is set to 1000, therefore all Peon's start with an Armour Rating of 1000. This represents no damage reduction.

## Design and Testing
### Gameplay Prototyping
Link to our prototype: https://drive.google.com/file/d/17jUFl4MTQ-Nn_Rh_k_x70FSLKO6OXWqQ/view?usp=sharing

_Not created with LibGDX, but another tool (GMS2) for rapid prototyping._

### Design Iterations
[Wizard and Attack design](combat/Wizard-design)

[Skill Hot Bar design](combat/Hotbar-design)

### User Testing
User testing has already been utilised to aid our decision making over graphical design. Full details are provided on the respective pages as linked under Design Iterations. However, the gameplay of the project has only just reached a state where gameplay user testing is viable. User testing to validate the user's experience in combat is required. For example, a user testing session where volunteers play through the game for a timed duration, followed by a questionnaire:

    1) Does the movement controls feel natural, and responsive?
    2) Are the combat controls easy to use in combat? Specifically, does the player focus on what is happening in game, and not on which buttons need to be pressed to execute the skills the player wants to?
    3) In terms of potency, are the player combat abilities fairly balanced with enemies? Are the players abilities too strong, or are the enemies abilities too strong?
    4) Would the game benefit from implementing a difficulty curve, where early levels have easier enemies, but later levels have more difficult enemies?

### Early User Testing Results
A small group of volunteers was asked to play test the game, whilst under observation, and then provide feedback in the form of a small questionnaire.

***General Feedback:***
1) At the time of testing, the game was too difficult. _Important note, difficulty has since been lowered, and this will need to be tested again._
2) Player movement was identified as needing improvement by all participants.
3) The controls of the game in terms of player movement, swapping skills, and attacking are good.
4) Better skill differentiation was recommended. Specifically, skill icons at the time were very similar. _(This has now been rectified)._ And the difference between skills was not significant. All testers opted to stick to using the same skill, despite knowing they could change. Some of the suggestions included a "Heavy Ranged attack" and "Bigger skill impacts."

***Observations:***
1) All testers spent time attempting to clear the room of orcs, and found the respawning frustrating.
2) Half of the testers did not realise they needed to kill the dragon to obtain the orb, and spent time exploring the map looking for it.
3) The Swamp map, and Tundra map are very open. As a result, the testers either roamed the map unable to find their goals, or immediately found the dragon and did not further explore the map.
4) All testers used the same strategy of running away from the bosses to prevent incoming damage.

***Conclusions:***
1) Player movement has already been identified as a weak point, and there are already plans for improving this system in place.
2) Alternative skills from the base skill need to have a bigger impact, but may need longer cooldowns to encourage skill swapping during fights.
3) Reconsidering how and when enemies respawn could improve player responses to the game.
4) Boss fights would benefit from taking place inside regions of the map with walls of some form, to prevent the player simply running away. A perfect example of this is the Volcano map, where the boss fight is surrounded by lava.

## Implementation
The implementation of Combat is split into:
1) UI elements;
2) Peons; and
2) Combat system.

### UI Elements
The skill hot bar, and floating damage is implemented through a [OverlayComponent](OverlayRenderer-and-OverlayComponent), while the [Camera](camera) is implemented to follow the PlayerPeon.

### Peons
A Peon is an AgentEntity _(ie, an entity that moves)_ who additionally can be involved in combat. As such, the Peon is the class where you will find character stats such as Health, Armour and other attributes related to combat. [Link to Peon Javadoc to be updated when Jenkins becomes available.](#)

### Combat System
The combat system comprises of several layers of abstraction.

| **Name** | ***Description*** |
| -------- | ----------------- |
| [Skill](combat/combat-skills) | Interface that encapsulates CombatTask, Cooldowns and Skill Icons |
| [CombatAttackTask](combat/combat-attack-tasks) | AbstractTask that executes the attack, including spawning any CombatEntities |
| [CombatEntity](combat/combat-entity) | AbstractEntities used specifically in combat, for example projectiles. |
| [CombatTask](combat/combat-tasks) | AbstractTask that give CombatEntities specific behaviours in combat. |

The design goal of this system is so that when an entity wants to use an attack, it only needs to execute the Task from its Skill. All other combat behaviours are handled by the task system asynchronously from that point.

### Player Skills
The player skills system provides an method for initialising the player with certain skills based on how they set up their polyhedron. A full description for using the system, and for implementation can be found [here](combat/player-skills).

## Improvements
### Features Planned, but not Implemented
_(All planned features are currently implemented)_

### Code Quality Improvements
* Update code for consistent use of Skills for both Player skills and Enemy skills
* Update projectiles to be more generic, reducing code duplication
* Fix damage types of all attacks

### Optimisations
* The biggest optimisations can be seen in the [Entity Collision Detection Page](combat/entity-collision-detection)