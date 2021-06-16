# Description

The current enemies have been categorised into three types: monsters, minions and bosses. 
Minions will be spawned using methods from other enemies, and will generally have lower health than other types of enemies.
Monsters will be wild enemies: they will be randomly spawned in the environment using an EnemyManager.
Bosses will be singletons: each environment will contain only one boss. Each boss will drop an Orb when it is killed. Bosses will typically have higher health and be more difficult to defeat. 

Different environments will have different variations of enemies. In the scope of Sprint One, enemy variation has been limited to different textures for enemies based on the environment.  In each location, a boss will be spawned. In order to obtain the orb of each boss and move to the next environment, the boss must be defeated.

Additionally, in each environment wild monsters will be spawned, that will attempt to attack the player if they approach. 

# Usages
- [Blueprints](/enemies/blueprints)
- [Spawn, Manage & Remove Enemies](/enemies/enemy-manager)
- [Adjust difficulties](/enemies/adjust-difficulties)

# Classes & Interfaces

* [API References](/enemies/api-references)
- **Monsters:** The standard type of aggressive enemies that are randomly spawned in the environment. Monsters will attempt to chase and attack players if they reach a certain radius but will give up if the player escapes.
   - [Dummy](/enemies/monsters/dummy) (passive, 1 variation)
   - [Orc](/enemies/monsters/orc) (aggressive, 4 variations)
* **Minions:** Another type of aggressive enemies that will pursue the player. They normally come in groups and would have faster movement, and are spawned by other enemy types. They will typically have lower attack damage as well as health.
   - [Goblin](/enemies/minions/goblin) (aggressive, 4 variations)
- **Bosses:** The boss enemy to defeat to get the orbs. They will typically have a fairly large amount of health with more complex combat mechanisms. However, they may have limited movement and could be passive (won't attack until attacked). They may also summon minions.
   - [Dragon](/enemies/bosses/dragon) (aggressive/passive, 4 variations)

* Enemies can behave [aggressive](/enemies/aggressive-enemies) or [passive](/enemies/passive-enemies) or both.

# Play Testing
A round of play testing was undertaken at the start of the sprint in order to determine relatively small, “juicy” changes that could be made to build upon the core functionality created during Sprints 1-3 in order to improve the experience of players. Multiple users were asked to play the game without prompting, and provide their thoughts on the gameplay experience. Some useful feedback that was provided included:

- The minimap was generally well received, with users liking the minimap's use to track enemies and bosses on the map. One user noted that the minimap blocked the top-left corner when the player approached it

- Most users felt that the dragon graphics did not fit with the rest of the designs, especially citing the desert and swamp dragons as being of low quality. Additionally, the dragons lacked animations compared to the orc and goblins, which users also felt to be visually problematic. 

- Some users brought up difficulty curve issues: the bosses were generally found to be of a high difficulty, especially the desert and volcano zones. 

- Users brought up the possibility of adding sound effects; for many users, they were not used to playing games lacking any audio. 

- Load times were a concern; without feedback, some users thought that the game was not functioning correctly. 

- Lack of health bars was concerning to users; many found it fairly hard to play the game without being able to track their own or boss health.

# Appendix

* **Last edited**: 25 Oct (Sprint 4)
* **Authors**: Yiyun Zhang (@fzmi), Wilson Yu (@wyu17)