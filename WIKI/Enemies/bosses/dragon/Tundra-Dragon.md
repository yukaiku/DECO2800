The Tundra Dragon can be found in the Tundra World and has two attack types; Iceball and IceBreath that override the default
breathAttack and ElementalAttack respectively. It is currently named "Diokiedes" as per integration with the storyline.

![dragon_tundra](uploads/05d0d98837098cc6451600b1ad8187a4/dragon_tundra.png)
## Iceball
```java
- public void breathAttack()
```

The Tundra dragon's breathAttack implements the Iceball class.

The breath attack spawns in an Iceball entity that has a SlowOnCollisionTask combat task so that when it collides with an entity of the enemy faction it will apply a SpeedStatus effect that will slow the entity as well as applying damage. (further documentation in [Combat Attack Tasks](/combat/Combat Attack Tasks))


![iceball_small](uploads/8a4dd1b020af23db3800d2f0074e0983/iceball_small.png)

## Ice Breath
```java
- public void elementalAttack()
```
The TundraDragon's elementalAttack implements the IceBreathTask.

The elemental attack spawns Freeze entities that have a SlowOnCollisionTask combat task so that when they collide with an entity of the enemy faction, a SpeedStatus effect is applied that that will slow the entity. The Freeze entities are currently spawned in a cone expanding away from the dragon in the direction of the player. (further documentation in [Combat Attack Tasks](/combat/Combat Attack Tasks))

[Ice Breath Animation](uploads/f50f86f2d1cbc41942338ea934ce4f29/ice-wave.gif)

## Sprites
![dragon_tundra_sprite_sheet](uploads/4eaa6c2ef059ffb44e389559b707da38/dragon_tundra_sprite_sheet.png)
Sprite sheet animations for the tundra dragon.


#### Updated walking sprites 

![dragon_tundra_walking_sprites](uploads/b4532125db42dbb219654c42a62ca420/dragon_tundra_walking_sprites.png)

#### Updated attacking sprites

![dragon_tundra_attacking_sprites](uploads/b7514eedbf53a16c4b27cfa270b94fca/dragon_tundra_attacking_sprites.png)
