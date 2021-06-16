## **Description**
![微信图片_202009020041512](uploads/c80e3e8015869837a2978aa6314dd978/微信图片_202009020041512.jpg)

 This is a rough sketch of a possible enemy goblin design (subject to change).

The Goblin has implemented AggressiveEnemy and is a subclass of Minion.

They have a higher movement speed compared to other enemies, but have lower health and attack damage, and come in groups of up to 10.

 Each enemy goblin will have its textures set to left and right facing only; each Goblin will face the direction it is moving (towards the player). This was discussed with the combat team and other designers in the studio to simplify implementation. When moving in an upwards or downwards direction, the texture will face either left or right depending on the position of the player.

Goblins have implemented attackPlayer(); player is inside of the goblin's attack radius, the goblin will attempt to perform a melee attack on the player.

Goblins are to be summoned by other types of enemies and do not naturally spawn: currently Dragons spawn goblins into the world after being attacked. 

In addition, each Environment will also have a different enemy goblin to match it.

 
![goblinLeft](uploads/505327145d2bbd0c39a4003e209e3c35/goblinLeft.png)
Green goblin to match the swamp environment.

![goblindesert](uploads/326f197f480fe6de9bfd86895d454c27/goblindesert.png)
Yellow goblin to match the desert environment.

![goblintundra](uploads/af9fe696830287139aecde9b08abb6db/goblintundra.png)
Blue goblin to match the tundra environment.

![goblinvolcano](uploads/c99204c4a01c1c234f7e8692131bc9e5/goblinvolcano.png)
Red goblin to match the volcano environment.
### User Testing
- User Testing Feedback: 
    - From preliminary user testing performed, users generally found the design of the goblins to be suitable for the associated environment. However, some users found the body proportions of the goblins to be unrealistic: possible design improvements to be made for goblins could include designing more realistically proportioned goblins. 


### Public Methods
```java
- Goblin(int health, float speed)
- Goblin(Variation variation, int health, float speed)
```
   - Goblins are initialised with a Variation (that can be DESERT, TUNDRA, VOLCANO or SWAMP) and a given health and speed. If no Variation is provided, defaults to SWAMP.

```java
- public void detectTarget()
```
    - Called in the constructor of the Goblin to set its target to the current player.

```java
- public void death() 
```
  - When called removes the Goblin from the game.

```java
- public void onTick(long i)
```
If the goblin has a target, the goblin will update its direction based on the current direction it is facing, will move towards the player and attempt a melee attack. 

```java
- public void attackPlayer()
```
  - When called the goblin attempts to melee attack its current target if the target is in the goblin's attack range. It does this by creating a new MeleeAttackTask. Goblins have a very small attack size (BoundingBox dimensions of 1 x 1) and do the lowest damage out of all currently implemented enemy types. 

```java
public TextureRegion getFrame(float delta)
```
Returns the current TextureRegion of the given goblin. 

```java
- public void deepCopy()
```
   - Returns an Goblin with the same height,  speed, and variation as the goblin this method is called on.


### Prototype animations 

For implementation in the later progression of the game, sprite sheets were created to show the prototype animations for the enemy goblin. The attack task animations are shown as follows: 

![goblin_desert_sprite_sheet](uploads/2294f16de40cd518ab440381f1b5e47f/goblin_desert_sprite_sheet.png)
Sprite sheet animations for the desert goblin.

![goblin_swamp_sprite_sheet](uploads/7166e3dc9560764dd2e42b627822192a/goblin_swamp_sprite_sheet.png)
Sprite sheet animations for the swamp goblin.

![goblin_tundra_sprite_sheet](uploads/e50ade4a56754b01ab626b1273b87460/goblin_tundra_sprite_sheet.png)
Sprite sheet animations for the tundra goblin.

![goblin_volcano_sprite_sheet](uploads/23df94536b59847c0db053bdbb2f4744/goblin_volcano_sprite_sheet.png) 
Sprite sheet animations for the volcano goblin
### Intended animations
![1cf7c0ebe039f7cdba159d9075963105](uploads/49b17b5837dc5724344eb5b0cc087997/1cf7c0ebe039f7cdba159d9075963105.mp4)

### User Testing Sprint Two
User testing was conducted to identify if the attack tasks and prototype animations matched the user's expectations. This included combat attack tasks, as well as subtle inclusions that is believed to enhance the player's experience as well as their interactions in the game. The user test was done through online-face-to-face interviews with 10 interviewees. Firstly, a document was brought up which introduced the different prototype animations for the game, as well as its intended purpose and functionality in the game. Throughout this process, interviewees were asked the following questions about the proposed animations for each enemy type: 

1. For each animation task, users were asked for additions/changes they believed would improve the animation. 

2 Users were asked if they believed the animations were accurate and fluid. Clips such as the below clip were shown to interviewees. 
(*show clip of intended animations for orcs, goblins and dragons*): example below
![1cf7c0ebe039f7cdba159d9075963105](uploads/49b17b5837dc5724344eb5b0cc087997/1cf7c0ebe039f7cdba159d9075963105.mp4)
3. Users were asked whether they believed the prototype animations matched the overall game style, and more specifically which aspects they believed fit the style and which did not. 

User testing was carried out through online interviews and conducted via google form which included snippets of the animations and short clips to allow the user to visualize the proposed implementation. 

The feedback provided on the animations was fairly similar for all goblin types.

Feedback was provided that some users did not feel the animation accurately reflected the attack performed: for example, one user stated that "I feel like it would look nicer if the attack is in the opposite direction, from up to down" while another stated that "It looks like a knife typically humans would use. Maybe design the weapon more like what goblins would use". Similarly, some users stated that the animations did not fit into the overall artstyle of the game. For example, the size of the head and ears was considered to proportionally large compared to other elements in the game. 

This feedback suggests that users found the animation of the goblin's attacks to be dissonant with the actual behaviour of the goblin, and with the general art style of the game. Other feedback provided was that the animation was a little clunky, with only a small number of frames. For example, some feedback provided was that "The arm movement can be more natural, one more animation can be added between" and "Add few more images and make the swings bigger to give a better experience"

Future changes to the animation of the goblins could include further user testing to determine animations that more accurately conform to the animations expected by users (swing from up to down? etc) and fit with the art style (insufficient feedback was provided as to why users felt it did not fit the art style). Additionally, a number of in-between frames could also be implemented to reduce the choppiness of the animation. 

### Sprint Three
For sprint three, the animation frames from sprint two were extended upon. This includes fixing the direction of the attack, where users stated that they believed the attack would look better going from up, swinging down instead of the other way around - better conforming to users' expectation of the attack movements. Furthermore, additional frames were added to reduce the choppiness of the animation, and enhance the overall game experience. Additionally, walking frames were also introduced as an added visual enhancement to the game. The following below are the final outputs of the design:

#### Attacking sprites

![goblin_desert_sprite_sheet_ext](uploads/3f78aa751f3fcd2c543f1c1e2a76589f/goblin_desert_sprite_sheet_ext.png)


![goblin_swamp_sprite_sheet_ext](uploads/4779b731cdbc1a50578077f00a107676/goblin_swamp_sprite_sheet_ext.png)


![goblin_tundra_sprite_sheet_ext](uploads/948d20d0e584ad259e5597527ab36142/goblin_tundra_sprite_sheet_ext.png)


![goblin_volcano_sprite_sheet_ext](uploads/1e6660158eaa8c8b3d7b719a1ff0b539/goblin_volcano_sprite_sheet_ext.png)

#### Walking sprites

![goblin_desert_walking_sprites](uploads/e72e308b7d9d007d06a34d7d934c0a6b/goblin_desert_walking_sprites.png) ![goblin_swamp_walking_sprites](uploads/35e297a7719f95e0ef6860f636d250f0/goblin_swamp_walking_sprites.png) ![goblin_tundra_walking_sprites](uploads/cad04e682ebad1bb58ff8929e7f54e31/goblin_tundra_walking_sprites.png) ![goblin_volcano_walking_sprites](uploads/aac1428d3204813e38f10701dee4eb61/goblin_volcano_walking_sprites.png)

### Swamp Orc and Goblin Animations

https://drive.google.com/file/d/13TSlGmlVsbS3Hzefmvr5N_VswGrzofPA/view?usp=sharing

### Tundra Orc and Goblin Animations

https://drive.google.com/file/d/1jRg4cWOjkmCVw5cNOPzstIMqbDzG76VZ/view?usp=sharing

### Desert Orc and Goblin Animations

https://drive.google.com/file/d/1r51wmz_iv035yGSASQDqQM4NCWRMjZqr/view?usp=sharing

### Volcano Orc and Goblin Animations

https://drive.google.com/file/d/1jJBH9ErGc9xP9zsW77KyLH9EBDu--uGR/view?usp=sharing


### User Testing Sprint 3
For this part of the user testing, users were shown the extended version of the prototype animations, and asked if they believed the new animations more closely resembled a closer representation of the intended animations. With the addition of walking frames, user testing was conducted to see if these designs matched the user's expectation, and where further improvements can be done. The user test was conducted by getting users to respond to the questions contained within a google form. During this process, interviewees were asked the following on the designs above:

1. For each animation task, users were asked what they thought works and what doesn't with what is presented in front of them. (attacking task and walking task).
1. Similar to previous user tests, users were shown a small clip of the movements of the designs and were asked about the fluidity and accuracy of the animations. (Where can improvements be made?)
1. Users were asked whether they believed the prototype animations matched the overall game style, and more specifically which aspects they believed fit the style and which did not.

User testing was carried out through a mixture of online and in-person interviews conducted via google form and/or directly by showing them the intended animation movements. Short clips were included to help them better visualize the final product.

The feedback provided on the animations was fairly similar for all goblin types; and fairly similar to previous user tests.

#### Feedback

Feedback was provided that some users felt that although the attacks are now going from up swinging down, which was an improvement that matched the user's expectations from previous user tests, even more frames could be added to reduce the choppiness of the motions. One user stated that the attacking frames/walking frames could be "more exaggerated" to better reflect that they are walking or attacking. Another user stated more in-between frames would help smoothen out the animations when changing between states. Similar to the Orcs, other users stated the need for more movement, and not just hands/feet when attacking/walking.

This feedback suggests that users found the animation of the goblin's attacks and walking movement still to be quite unclear, with still not enough frames to reduce signs of choppiness or dissonance with goblin behaviour. Other feedback provided was that the goblin proportions could be fixed, so that animation movements look more realistic and convincing.

Future changes to the animation of the goblins would include further user testing to determine animations that more accurately conform to the animations expected by users (more range of movements? Better design proportion?) and the implementation of more extended/exaggerated frames that better represent that action or movement.