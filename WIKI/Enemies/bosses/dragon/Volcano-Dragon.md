The Volcano Dragon can be found in the Volcano World and has two attack types; VolcanoFireball and FireBombAttack that overrides the default
breathAttack and ElementalAttack respectively. It is currently named "Chusulth" as per integration with the storyline.

![dragon_volcano_right](uploads/3ba7129cb412200040f5cffce738871e/dragon_volcano_right.png)

## VolcanoFireball
```java
- public void breathAttack()
```
The VolcanoFireball acts similar to a regular Fireball and uses both the DirectProjectileMovementTask and ApplyDamageOnCollisionTask, but has a darker colour than a normal fireball. Additionally, the Volcano dragon fires three of these fireballs at once, and each fireball is initialised with higher damage and less speed than a standard fireball. 

## FireBombAttackTask
```java
- public void elementalAttack()
```
The VolcanoDragon's elementalAttack creates a FireBombAttack that creates 5 x 5 grid of Explosions around the dragon that damage the player on contact. (further documentation in [Combat Attack Tasks](/combat/Combat Attack Tasks))

## Sprites
![dragon_volcano_sprite_sheet](uploads/2a7a8798ef59a3b3655b93a6f946e248/dragon_volcano_sprite_sheet.png)

Sprite sheet animations for the volcano dragon.

#### Updated attacking sprites

![dragon_volcano_fireball_sprites](uploads/91b121e3a1c0a93c2b639de7fa3b7373/dragon_volcano_fireball_sprites.png)


#### Updated walking sprites 

![dragon_volcano_walking_sprites](uploads/553a7c5779e8b79fde78134aec4cc65a/dragon_volcano_walking_sprites.png)


