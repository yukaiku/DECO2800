## Table of Contents

[[_TOC_]]

# Enemy Manager

The spawning mechanisms of the enemies are handled by the enemy manager. It needs [blueprints](https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/blueprints) to know what kinds of enemies to spawn.

Enemies are categorised into wild enemies, special enemies and bosses, and the enemy manager will track them separately. Wild enemies are automatically spawned at a random position on a fixed tick (different enemies will have various spawning rates depending on the blueprints); Special enemies and bosses do not randomly spawn and need to be manually placed at the given position.

Different worlds should initialise different enemy managers. If the world does not contain enemies, you don't necessarily need it.

## Spawning Enemies

### Wild enemies
You don't need to worry about wild enemies ad it will spawn automatically. Just give the manager blueprints and it will handle the rest.

### Special enemies

For example, to spawn a goblin with the "desertGoblin" blueprint at 0, 0:

```java
EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
enemyManager.spawnSpecialEnemy("desertGoblin", 0, 0);
```

### Bosses

There can be only one boss in the world so you don't need to specify which blueprint. For example, to summon the boss at 0, 0:

```java
EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
enemyManager.spawnBoss(0, 0);
```

## Manage Enemies

To get the current wild enemies (like orcs) alive:
```java
enemyManager.getWildEnemiesAlive(); // -> List<EnemyPeon>
```

To get the current special enemies (like goblins and dummies) alive:
```java
enemyManager.getSpecialEnemiesAlive(); // -> List<EnemyPeon>
```

For the bosses, the blueprints themselves are the instances. To get the boss instance in the map:
```java
enemyManager.getBoss(); // -> Boss
```

## Remove Enemies

```java
enemyManager.removeWildEnemy(wildEnemy); // Note: EnemyPeon instances, not blueprints
enemyManager.removeSpecialEnemy(specialEnemy) // Note: EnemyPeon instances, not blueprints
enemyManager.removeBoss();
```

## Old approach (Sprint 1 & 2)

The EnemyManager only needs to know what kinds of wild enemies and bosses the maps have.
First, initialise the blueprints of wild enemies and bosses:

```java
// (int height, float speed, int health, [String texture])
Orc swampOrc = new Orc(1, 0.09f, 50, "orc_swamp");
Orc speedyOrc = new Orc(1, 0.2f, 25, "orc_volcano");
Dragon boss = new Dragon(3, 0.03f, 1000, "dragon_swamp");
```

Then, construct a new EnemyManager and register it to the GameManager:

```java
// (AbstractWorld world, int wildEnemyCap, List<EnemyPeon> blueprints, [Boss boss])
EnemyManager enemyManager = new EnemyManager(this, 5, Arrays.asList(swampOrc, speedyOrc), boss);
GameManager.get().addManager(enemyManager);
```

That's it. It will now automatically assemble the enemy peons and control the spawning rates.

To summon the boss at a location, use spawnBoss() method:

```java
enemyManager.spawnBoss(23, -24);
```

In the future sprints, when the enemies variations become much diverse, we will consider using JSONs to import the enemy configurations to speed up the workflow.


## **API Reference**

![EnemyManager](uploads/5270e1c453525cf32c41a5756b34d9fb/EnemyManager.png)

# Appendix
- **Last edited**: 2 Oct (Sprint 3)
- **Author**: Yiyun Zhang (@fzmi)
