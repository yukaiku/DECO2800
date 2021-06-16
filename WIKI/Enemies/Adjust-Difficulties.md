## Table of Contents

[[_TOC_]]

# Enemy Attributes

### Common Attributes

| Property | Type | Default | Description |
| ------ | ------ | ------ | --------------------- |
| `variation` | EnemyIndex.Varition | `EnemyIndex.Varition.SWAMP` | The **environment** variation of the enemies, used for names and texture rendering
| `health` | int | *Required* |
| `speed` | float | *Required* |
| `damage` | int | *Required* |
| `sightRange` | int | *Required* |
| `meleeRange` | int | *Required* |
| `spawnRate` | int | *Required* |

### Bosses

| Property | Type | Default | Description |
| ------ | ------ | ------ | --------------------- |
| orbNumber | `int` | *Required* | The unique orb id drops on defeat |


# Adjust Difficulties

The difficulties of enemies can be achieved by adjusting the blueprints inside of the enemy manager, and the manager will respond to the change respectively.

There are 2 ways to achieve that: define new blueprints or modify existing blueprints.

## Define new Blueprints

See [Blueprints > Create Blueprints](https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/blueprints#create-blueprints)

## Modify existing blueprints

To get the existing blueprints, see [Blueprints > Get the blueprints from the game](https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/enemies/blueprints#get-blueprints-from-the-game)

You can then modify the blueprints in the game.

# Appendix
* **Last edited**: 7 Oct (Sprint 3)
* **Author**: Yiyun Zhang (@fzmi)
