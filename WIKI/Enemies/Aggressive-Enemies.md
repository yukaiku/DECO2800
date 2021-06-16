# **Description**

Aggressive enemies will actively move towards the player's position and attack the player. There may be criteria before an aggressive enemy locks onto the player and pursues the player. For example, Orcs will only lock onto the player once the player enters their detection range.

The radius at which aggressive enemies will begin to pursue the player will be dependent on the type of enemy. 

Aggressive enemies will typically be reasonably easy to defeat in order to prevent frustration when players are pursued by overly difficult to defeat enemies. 

# **Interface Methods**
```java
- void detectTarget();
```
   - Defines a method called by each AggressiveEnemy to attempt to detect the player. 

# **Types of Aggressive Enemies**
- [Orc](/enemies/monsters/orc)
- [Goblin](/enemies/minions/goblin)