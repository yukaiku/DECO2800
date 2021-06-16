## In GameManager.java

### **Data Type and Attributes**

```java
private static enum WorldType {
  SWAMP_WORLD,
  TUNDRA_WORLD,
  VOLCANO_WORLD,
  DESERT_WORLD
}
private int currentWorld = 0;
private ArrayList<WorldType> worldOrder;

```
- The enum is used to store unique identifier for each of the four Zones.
- `currentWorld` acts as the counter to keep track of which is the current world as well as which is the next world to be set.
- `worldOrder` stores all of the four values in the declared enum.

### **Initialization**

```java
private GameManager() {
  worldOrder = new ArrayList<>(EnumSet.allOf(WorldType.class));
  Collections.shuffle(worldOrder);
}
```
- `worldOrder` is initialized in the constructor of `GameManager`.
- All values from the enum `WorldType` are placed into `worldOrder` and then `worldOrder` is shuffled, which means everytime the game is restarted, there will be a new world order for the player to go through.

### **World Changing Method**

```java
public void setNextWorld() {
  // removes the previous enemy manager
  managers.removeIf(manager -> manager instanceof EnemyManager);
  switch (worldOrder.get(currentWorld)) {
    case TUNDRA_WORLD:
      this.setWorld(new TundraWorld());
      break;
    case SWAMP_WORLD:
      this.setWorld(new SwampWorld());
      break;
    case DESERT_WORLD:
      this.setWorld(new DesertWorld());
      break;
    case VOLCANO_WORLD:
      this.setWorld(new VolcanoWorld());
      break;
  }
  currentWorld = (currentWorld + 1) % worldOrder.size();
}
```
- Get the unique identifier of the next world to be set with `currentWorld` (`currentWorld` is initialized to 0) and depending on the retrieved value, set the according World. 
- Increment the value of `currentWorld` to point to the next world in `worldOrder`

## In AbstractWorld.java

### **Attributes**

```java
protected AgentEntity playerEntity;
private Orb orbEntity;
```
- The Player Entity and Orb Entity have their own `playerEntity` and `orbEntity` attributes to avoid traversing the `entities` list everytime we need to access them.

### **Check if Orb obtained Method**

```java
protected void checkObtainedOrb() {
  if (orbEntity != null) {
    if (playerEntity.getPosition().equals(orbEntity.getPosition())) {
      this.removeEntity(playerEntity);
      GameManager.get().setNextWorld();
    }
  }
}

//...

public void onTick(long i) {
  if (!GameManager.get().inTutorial) {
    this.checkObtainedOrb();
  }

  //...
}
```
- If the position of the Player Entity and that of the Orb Entity is the same, call the `setNextWorld` method in `GameManager` to teleport the Player to the next Zone.
- If the Player is not inside the Tutorial World, the method is called constantly to check if the Orb is obtained by the Player
