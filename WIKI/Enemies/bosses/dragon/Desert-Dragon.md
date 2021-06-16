The Desert Dragon can be found in the Desert World and has two attack types; DesertFireball and SandTornado that overrides the default breathAttack and ElementalAttack respectively. It is currently named "Doavnaen" as per integration with the storyline.

![dragon_desert](uploads/58570d94441c53c042ae6db42c0fd3b2/dragon_desert.png)

## Desert Fireball
```java
- public void breathAttack()
```
The Desert Fireball acts similarly to a regular Fireball and uses both the DirectProjectileMovementTask and ApplyDamageOnCollisionTask. Once the task is complete it also sets the tile that it impacts to a Quicksand tile type which will apply a QuicksandBurnStatus and SpeedStatus effect to entities that walk over it. 

## Sand Tornado
```java
- public void elementalAttack()
```
The Sand Tornado is designed to be an attack that is slow moving and if collides with the player will pick them up, spin them around and apply damage. Currently the animations for spinning a player have not been implemented and the attack only applies damage. (further documentation in [Combat Attack Tasks](/combat/Combat Attack Tasks))

[Sand Tornado Animation](uploads/d43d613da782d0565b048fd5f6e3ffeb/tornado.gif)

## Sprites
![dragon_desert_sprite_sheet_](uploads/ba5446072e12d3281f61495cae5b6df8/dragon_desert_sprite_sheet_.png)

Sprite sheet animations for the desert dragon

#### Updated desert dragon 
Updated desert dragon to better reflect the final quality of in-game designs.
![dragon_desert](uploads/3cce2c0b666143cb8ca2b5d3c00ff11b/dragon_desert.png)

#### Tail movement sprites
Due to the design implementations, walking animations for the desert dragon were not included and thus substituted for tail movement as an in-game visual enhancement.
![desert_dragon_tail_sprites](uploads/fef8147bf8fbf7f07aa870ac3a023606/desert_dragon_tail_sprites.png)

#### Attacking sprites
![desert_dragon_attack_sprites](uploads/8dae0099bbe8e1f36d893a96120c7535/desert_dragon_attack_sprites.png)