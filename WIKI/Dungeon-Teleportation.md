# Wiki last updated 23/10 - End of Sprint 4
# Description

Dungeon Teleportation is made possible via 2 methods in `GameManager` & 1 method in `MovementTask`. In brief, MovementTask checks once a player steps onto a tile containing a portal entity, MovementTask then calls respective `enterDungeon()` or `exitDungeon()`. Next, in `GameManager()`, either `ExitDungeon()`, `setWorld` or nothing is executed depending on the type of portal present.

## In GameManager.java

### **Respective Entering/Exiting Dungeon Attributes**

```java

private AbstractWorld worldOutsideDungeon;

private EnemyManager enemyManagerOutSideWorld;

private String currentDungeon;


```
- The `AbstractWorld` worldOutsideDungeon stores the current world the player is in once a player enters a dungeon - the default is null;
-  The `EnemyManager ` enemyManagerOutSideWorld stores the current world's state in terms of enemies including their respective type, health & position.

### **Entering & ExitingDungeon Methods**

```java
public void enterDungeon(String dungeon){

		if (dungeon.equals("ExitPortal")) {
			this.exitDungeon();
			return;
		}

		this.currentDungeon = dungeon;
		this.worldOutsideDungeon = this.getWorld();
		this.enemyManagerOutSideWorld = this.getManager(EnemyManager.class);
		// removes the previous enemy manager
		managers.removeIf(manager -> manager instanceof EnemyManager);
		switch(dungeon) {
			case "VolcanoDungeonPortal":
				this.setWorld(new VolcanoDungeon());
				break;
			case "TundraDungeonPortal":
				this.setWorld(new TundraDungeon());
				break;
			case "SwampDungeonPortal":
				this.setWorld(new SwampDungeon());
				break;
			case "DesertDungeonPortal":
				this.setWorld(new DesertDungeon());
				break;
		}
```
-  The Enter dungeon handles portal entity collisions & calls portal actions whether its exiting the current dungeon, moving to a new world or doing nothing at all depending on the type of portal string given.

//...

```java

public void exitDungeon() {

		//Remove the tiles parent
		for (Tile tile : worldOutsideDungeon.getTiles()) {
			if (tile.hasParent() && tile.getParent().getObjectName().equals(this.currentDungeon)) {
				tile.setParent(null);
			}
		}
		//Remove the portal from the entities list.
		for (AbstractEntity entity : worldOutsideDungeon.getEntities()) {
			if (entity.getObjectName().equals(this.currentDungeon)) {
				worldOutsideDungeon.removeEntity(entity);
			}
		}
		//Keep the same player
		AgentEntity player = this.getWorld().getPlayerEntity();
		// Dispose Dungeon & reset manager
		this.getWorld().dispose();
		managers.removeIf(manager -> manager instanceof EnemyManager);
		//Add existing world & enemy manager
		this.addManager(enemyManagerOutSideWorld);
		this.setWorld(worldOutsideDungeon);
		((PlayerPeon)this.worldOutsideDungeon.getPlayerEntity()).updatePlayerSkills();
		this.worldOutsideDungeon = null;
	}

```
- The first thing the exitDungeon() executes is the removal of the dungeon portal so that the player does not return to the Original World & instantly teleport back into the dungeon (This was a bug fixed in sprint 3).
- exitDungeon() then gets the original world the player was in outside of the dungeon, removes the dungeon portal, disposes the old world before replacing the concurrent world, enemy manager & peon with the original world, enemy manager & player outside of the dungeon.

## In MovementTask.java

```java
 private void checkForValidPortal(SquareVector position) {
        // get the next tile
        Tile tile = gameManager.getWorld().getTile(position);

        if (tile != null && tile.getParent() instanceof Portal) {
            Portal portal = (Portal) tile.getParent();
            String type = portal.getObjectName();
            gameManager.enterDungeon(type);
        }
    }
```
- This `checkForValidPortal()` is called every time a player moves to a new tile, whilst this could be thought of as inefficient, it is much more efficient than the previous approach of polling the players new tile in the `onTick()` within the respective world class.
- `checkForValidPortal()` first evaluates the tile & whether it has a respective portal entity on it. It then calls enter `GameManager.get().enterDungeon(type)` depending on the type of portal entity should there exist a portal on that tile.


# Documentation by @ArthurM99115 (Arthur Mitchell)