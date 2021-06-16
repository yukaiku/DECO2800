# **Description**

Passive enemies will generally only attack the player if they are attacked first, or if the player approaches within close range of the enemy.

Passive enemies will typically be harder to defeat (e.g bosses and minibosses) and provide some type of reward when defeated (such as orbs to progress the story). 

# **Interface Methods**
```java
- void hitByTarget();
```
   - Defines a method called by each PassiveEnemy when their health is reduced. Usually this will be to target the entity that has reduced their health. 

# **Types of Passive Enemies**
- [Dummy](/enemies/monsters/dummy)
- [Dragon](/enemies/minions/dragon)