# Description
Link to file: [managers/DifficultyManager.java](https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/blob/master/src/main/java/deco2800/thomas/managers/DifficultyManager.java)

This is the wiki containing all the information on what and how the difficultyManger may be used.
Currently, the values that are manipulated based on the stage you are at is as follows:
- Player Hp
- Wild spawn HP
- Wild spawn cap


# Usages
The difficulty manager is initialized each time a new world is generated. 

- SwampWorld.java

An example of where it can be used can be seen in the swamp world.
It intialises the manager and creates one if it does not exist and passes the playerEntity along with world type to the difficultyManager. 
```java
//Updates difficulty manager
        DifficultyManager difficultyManager = GameManager.getManagerFromInstance(DifficultyManager.class);
        difficultyManager.setPlayerEntity((PlayerPeon) this.getPlayerEntity());
        difficultyManager.setDifficultyLevel(getType());
```

If you wish to further customize this it can be done with their specific function as so:
- For wild spawn max health
```java
difficultyManager.setWildSpawnMaxHealth(value);
```

# API
### Public API Methods
```java
public int getDifficultyLevel()
```
Returns the difficulty level it is currently set as.
The difficulty level is the number of orbs plus 1.

```java
public void setPlayerEntity(PlayerPeon playerEntity)
```
Updates the player entity in difficulty manager when a new world is created. 

```java
public void setWildSpawnMaxHealth(int health)
```
Takes in health value to be set and
updates the wild spawn's maxed health.

```java
public int getWildSpawnMaxHealth()
```
Returns the maxed health of the wild spawns

```java
public void setWorldType(String type)
```
Takes in the type of world for example "Tundra" and saves it into 
difficulty manager to be used for wild spawn configs.

```java
public String getWorldType()
```
Returns the type of the world for example "tundra"

```java
public void setDifficultyLevel(String type)
```
Takes in the type of world for example "Tundra" and 
adjusts the difficulty accordingly.


# Appendix

* **Last edited**: 4 Oct (Sprint 3)
* **Author**: Yu Kai Ku (@yukaiku)