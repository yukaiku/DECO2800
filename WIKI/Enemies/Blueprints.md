## Table of Contents

[[_TOC_]]

# Introduction
Blueprints are EnemyPeon objects that serve for storing attributes only; They do not render in the game. Instead, they define what kind of enemies will spawn during the game. All monsters, minions and even bosses will have their own blueprints, and each blueprint will have a unique id (e.g. "swampOrc") to identify them. When spawning enemies, the enemy manager will then look up the blueprints using the id and spawn new enemies according to the blueprints.

### Why use blueprints?

Since the introduction of difficulty curves, enemies are having more and more variations with different spawning rates, sight/melee ranges, damages, health, speed, etc. The lecture's approach of using enemy factories with pre-defined attributes are certainly not capable in this situation, as a tiny variation of the enemies would require a new if-else statement, and may become messy and redundant in the future. Therefore, blueprints are preferred in this case as they can change these attributes on-the-run.

# EnemyIndex

The EnemyIndex class serves as a look-up table of initial configurations of enemies. It will list and manage available enemies all in one place, and provide functionalities for returning new instances. After that, you don't need to worry about the params before initialising special enemies or creating enemy managers; you only need to know the index and pass it to the manager.

### Advantages
* Adjust the configurations of the enemies easily, intuitive for difficulty curves
* Avoid unnecessary initialisations of blueprint objects which will never be rendered
* Avoid redundant codes when having the same enemies in different worlds/dungeons
* Use existing configurations when spawning enemies instead of filling all params, also useful for testing

# Blueprints Usage

## Create blueprints

All initial blueprints are centrally managed in EnemyIndex.java for convenience. Inside of `getEnemy()`, create a new case. For example, I want to add a variation of the orc named "tundraOrc" that uses tundra skin, has a health of 100, with a speed of 0.5, damage of 5, sight range of 8, melee range of 2 and a spawn rate of 0.01:
```java
case "tundraOrc": return new Orc(Variation.TUNDRA, 100, 0.05f, 5, 8, 2, 0.01f);
```

## Add new blueprints to the game

Before you can add the blueprints to the game, you should be familiarised with [Enemy Manager](https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/enemy-manager). It will store a separate collection for the blueprints using `Map<String, EnemyPeon> enemyConfigs`, not to be confused with the lists of enemies alive `List<EnemyPeon> wildEnemiesAlive` and `List<EnemyPeon> specialEnemiesAlive`, where they store the real rendered entities instead.

When you have the newly-created blueprints, you can give them to the manager for spawning in the maps. There are two ways of adding blueprints: one is to add when initialising the manager during the map switching, and the other is to add when the world has been loaded.

### Add when initialising EnemyManager

Simply pass the ids to the manager. You don't need to create new instances anymore, the enemy manager will create the blueprints for you.

```java
EnemyManager enemyManager = new EnemyManager(this, "tundraDragon", 7, "tundraOrc", ...);
GameManager.get().addManager(enemyManager);

// optional
enemyManager.spawnBoss(0, 0);
```

### Add during game

The enemy manager also provides functionality to add blueprints mid-game. It will automatically distinguish which blueprints are wild enemies and which are not. Note: Do not add bosses here.

```java
EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
enemyManager.addEnemyConfigs("tundraOrc", ...);
```

## Get blueprints from the game

This is where blueprints become so much useful. You can get the blueprints the spawning mechanism is currently performing on and change them directly, and the enemy behaviours can be changed immediately. This is very useful for difficulty curves. (see "Adjust difficulties" wiki)

```java
EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
EnemyPeon orcBlueprint = enemyManager.getEnemyConfig("tundraOrc");
```

## Remove blueprints from the game

You can also remove the blueprints you don't want them to spawn any more:

```java
EnemyManager enemyManager = GameManager.getManagerFromInstance(EnemyManager.class);
enemyManager.removeEnemyConfigs("tundraOrc", ...);
```

# Appendix

- **Last edited**: 2 Oct (Sprint 3)
- **Author**: Yiyun Zhang (@fzmi)