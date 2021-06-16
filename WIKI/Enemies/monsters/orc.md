## **Description**

![微信图片_20200902004151](uploads/ec842cf174353f53e5b069d48e069887/微信图片_20200902004151.jpg)

The appearance of the orc textures is as follows. In different environments, the texture of the orc will change based on the environment. 
This inspiration came from the game World of Warcraft.

Orcs are wild enemies, and a subclass of the Monster type.
They also implement the AggressiveEnemy interface, meaning that they will pursue players when the player moves inside each orc's detection radius. They will stop pursuing the player when the player moves outside their discard radius. 
Orcs have implemented attackPlayer(): when the player approaches the orc's attack radius, the orc will attempt to perform a melee attack against the player. 

Similar to other enemies, each enemy orc will have its textures set to left and right facing only. This was discussed with the combat team and other designers in the studio to simplify implementation. When moving in an upwards or downwards direction, the texture will face either left or right depending on the direction the orc is moving or attacking in. When moving left or right, the appropriate texture will be set to face the direction the orc is either moving or attacking.

In addition, each Environment will also have a different enemy orc to match it.


![orcdesert](uploads/3ee5250639d494ab383c6f2638010091/orcdesert.png)
Orc design to match the desert environment.

![orctundra](uploads/1858a725124d7056852d6dcdf092f11a/orctundra.png)
Orc design to match the tundra environment.


![orcvolcano](uploads/58c4786cc467b6ce34bde8c74867d247/orcvolcano.png)
Orc design to match the volcano environment.

![orcswamp](uploads/4e1edb17dbfe250903c26ce46b5a7113/orcswamp.png)
Orc design to match the swamp environment.

### User Testing
- User Testing Feedback: 
    - From preliminary user testing performed, users generally found that the desert and tundra orcs were appropriate for the environment. However, feedback was provided that some users found the swamp and volcano orcs to be not be very colourful and potentially difficult to make out from the background: a possible improvement in future sprints would be to use brighter colours that stand out more from the background of the swamp and volcano environments respectively. 

### Public Methods
```java
- Orc(int health, float speed) 
- Orc(Variation variation, int health, float speed)
```
   - Orcs are initialised with a Variation (that can be DESERT, TUNDRA, VOLCANO or SWAMP) and a given health and speed. If no Variation is provided, defaults to SWAMP.

```java
- public void detectTarget()
```
    - When called the orc attempts to set a new target if the orc currently has no target.

```java
- public void pursueTarget()
```
   - When called the orc nulls the current target if the target moves out of the discard range.

```java
- public void death() 
```
  - When called removes the orc from the game.

```java
- public void attackPlayer()
```
  - When called the orc attempts to melee attack its current target if the target is in the orc's attack range. It does this by creating a new MeleeAttackTask. 

```java
- public void onTick(long i)
```
If the orc has a target, the orc will update its direction based on the current direction it is facing, will move towards the player and attempt a melee attack. 

```java
public TextureRegion getFrame(float delta)
```
Returns the current TextureRegion of the given orc. 

```java
- public void deepCopy()
```
   - Returns an Orc with the same height, speed, and variation as the orc this method is called on.

### Prototype animations

For implementation in the later progression of the game, sprite sheets were created to show the prototype animations for the Orc texture. The attack task animations are shown as follows:

Sprite sheet animations for the desert orc.
![orc_desert_sprite_sheet](uploads/8b54ddeda823a0a179336b7939bb8f51/orc_desert_sprite_sheet.png)

Sprite sheet animations for the swamp orc.
![orc_swamp_sprite_sheet](uploads/dcc680aaf66ec940651edd711eafe1b2/orc_swamp_sprite_sheet.png)

Sprite sheet animations for the tundra orc.
![orc_tundra_sprite_sheet](uploads/13d5b44a95eed89a193e852d67384bd6/orc_tundra_sprite_sheet.png)

Sprite sheet animations for the volcano orc.
![orc_volcano_sprite_sheet](uploads/f1313261608f7ef77a9c156cf1226ac6/orc_volcano_sprite_sheet.png)

![10d5e9464bd5b13977a296c935e4d630](uploads/33558926cc490450c8525d7420ea86fb/10d5e9464bd5b13977a296c935e4d630.mp4)

### User Testing Sprint Two
User testing was conducted to identify if the attack tasks and prototype animations matched the user's expectations. This included combat attack tasks, as well as subtle inclusions that is believed to enhance the player's experience as well as their interactions in the game. The user test was done through online-face-to-face interviews with 10 interviewees. Firstly, a document was brought up which introduced the different prototype animations for the game, as well as its intended purpose and functionality in the game. Throughout this process, interviewees were asked the following questions about the proposed animations for each enemy type: 

1. For each animation task, users were asked for additions/changes they believed would improve the animation. 

2 Users were asked if they believed the animations were accurate and fluid. Clips such as the below clip were shown to interviewees. 
(*show clip of intended animations for orcs, goblins and dragons*): example below
![10d5e9464bd5b13977a296c935e4d630](uploads/33558926cc490450c8525d7420ea86fb/10d5e9464bd5b13977a296c935e4d630.mp4)
3. Users were asked whether they believed the prototype animations matched the overall game style, and more specifically which aspects they believed fit the style and which did not. 

User testing was carried out through online interviews and conducted via google form which included snippets of the animations and short clips to allow the user to visualize the proposed implementation. 

The feedback provided on the animations was fairly similar for all orc types.
Feedback was provided that some users did not feel the range of motion accurately reflected the attack performed: for example, one user stated that "The range of motion could be larger" while another stated that the attack would look better going from up to down instead of down to up.

This feedback suggests that users found the animation of the orc's attacks to be dissonant with the actual behaviour of the orc. Other feedback provided was that the animation was somewhat clunky, with only a small number of frames. 

Future changes to the animation of the orcs could include further user testing to determine animations that more accurately conform to the animations expected by users (swing from up to down instead? etc) and the implementation of a few in-between frames to reduce the choppiness of the animation. 

### Sprint Three Orc Animations
For sprint three, the animation frames from sprint two were extended upon. Due to the limitations of what can be achieved given such a short time frame, the following were taken into consideration when extending from the current enemy sprites. This includes fixing the direction of the attack, where users stated that they believed the attack would look better going from up, swinging down instead of the other way around - resembling users' expectation of the attack movements from other games that they have played. Furthermore, additional frames were added to reduce the choppiness of the animation, and to enhance the overall game experience. Walking frames were also added for better in-game visuals and playability. The following below are the final outputs of the design: 

#### Attacking sprites

![orc_desert_sprite_sheet_ext](uploads/aaa79886f6af52f570102bcb70d375b1/orc_desert_sprite_sheet_ext.png)

![orc_tundra_walking_sprites](uploads/9501e03e269cf41f0b51d0212e97a586/orc_tundra_walking_sprites.png)

![orc_swamp_walking_sprites](uploads/668b2445303e2cd5819d7fb939701c8a/orc_swamp_walking_sprites.png)

![orc_volcano_sprite_sheet_ext](uploads/71f1355d9553480a1dd5bf99c065eb6a/orc_volcano_sprite_sheet_ext.png)

#### Walking sprites

![orc_desert_walking_sprites](uploads/e4b715f8d6745a21caa942645a805049/orc_desert_walking_sprites.png)

![orc_tundra_walking_sprites](uploads/6c4579aa4b663187a8bdafa44a88ade7/orc_tundra_walking_sprites.png)

![orc_swamp_walking_sprites](uploads/668b2445303e2cd5819d7fb939701c8a/orc_swamp_walking_sprites.png)

![orc_volcano_walking_sprites](uploads/4765067bd5519ecc9f6a0ca4a269cd8a/orc_volcano_walking_sprites.png)

### Swamp Orc and Goblin Animations

https://drive.google.com/file/d/13TSlGmlVsbS3Hzefmvr5N_VswGrzofPA/view?usp=sharing

### Tundra Orc and Goblin Animations

https://drive.google.com/file/d/1jRg4cWOjkmCVw5cNOPzstIMqbDzG76VZ/view?usp=sharing

### Desert Orc and Goblin Animations

https://drive.google.com/file/d/1r51wmz_iv035yGSASQDqQM4NCWRMjZqr/view?usp=sharing

### Volcano Orc and Goblin Animations

https://drive.google.com/file/d/1jJBH9ErGc9xP9zsW77KyLH9EBDu--uGR/view?usp=sharing

## User Testing Sprint 3
For this part of the user testing, users were shown the extended version of the prototype animations and asked if they believed this somewhat resembled a closer representation of the intended animations; and what works/doesn't work. With the addition of walking frames, user testing was conducted to see if these designs matched the user's expectatiosn, and where further improvements could be made. The user test was conducted by getting users to respond to the questions contained within a google form. During this process, interviewees were asked the following on the designs above: 

1. For each animation task, users were asked what they thought works and what doesn't with what is presented in front of them. (attacking task and walking task).

2. Similar to previous user tests, users were shown a small clip of the movements of the designs and were asked about the fluidity and accuracy of the animations. (Where can improvements be made?)

3. Users were asked whether they believed the prototype animations matched the overall game style, and more specifically which aspects they believed fit the style and which did not. 

User testing was carried out through a mixture of online and in-person interviews conducted via google form and/or directly by showing them the intended animation movements. Short clips were included to help them better visualize the final product.

The feedback provided on the animations was fairly similar for all orc types; and fairly similar to previous user tests. 

### Feedback

Feedback was provided that some users felt that although the attacks are now going from up swinging down, which is an improvement that matched the user's expectations, the range of motions could still be more dramatic. For example, one user stated that the attacking frames/walking frames could be "more exaggerated" to better reflect that they are indeed walking or attacking, as they believe the movements are still quite vague. Another user stated that it would not only be better to have more frames for the attacking/walking animations, but design it so that not only are the hands/feet moving, but perhaps body posture is shifting, to better represent those actions, and make it more obvious 

This feedback suggests that users found the animation of the orc's attacks and walking movement still to be quite unclear due to the movements not being exaggerated enough to be easily determined 'walking,' or 'attacking'. Other feedback provided was that the animation could still do with more frames, to further reduce any signs of choppiness. 

Future changes to the animation of the orcs would include further user testing to determine animations that more accurately conform to the animations expected by users (maybe more range of movements?) and the implementation of more exaggerated frames that better represent that action or movement.



