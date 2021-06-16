
The dragon is the strongest enemy in the game as it as a boss, with the highest health and attack power of all enemies in the game. As dragons implement the passive interface: they implement passive behaviours; they will only target and attack the player once the player has damaged the dragon.

After the dragon has set a target, the dragon will intermittently spawn a ranged BreathAttack targeted at the player's location and attempt to summon a Goblin minion if the special enemy cap has not been reached. Additionally, each dragon implements a unique elemental attack; each dragon will attempt to perform its ElementalAttack once it has locked onto its target. 

Each enemy dragon will have its textures set to left and right facing only (similarly to other enemies, the Dragon will face the direction that it is moving in. This was discussed with the combat team and other designers in the studio to simplify implementation. When moving in an upwards or downwards direction, the texture of the dragon will face either left or right depending on either its current direction or its previous direction if it is not moving either left or right.

Once killed, the dragon will drop an orb that will transport the player to the next environment after the player walks over the orb being walked over. 

# **Dragon status design progress**

#### **28/08/2020 - Version 1.0 of initial designs.**      
There are total of 4 designs draft of the dragon.The 4 designs are as follow:    

Picture 1 is an icon designed with a simple dragon head. The designer believes that there should be an icon of a dragon as a reminder near the appearance of a dragon.

![1](uploads/17f236f0bc026bad4df1ba9ada59151d/1.png)      

Picture 2 is a draft of the dragon attack when I was designing the left side.As the big boss in the game, I added fire-breathing attacks to the basic physical attacks.

![2](uploads/7c561554dd8a52169237f0c118c2aa82/2.png)

Figure 3 is the draft of the dragon on the right.I think this version is perfect for games.Since the game is set in the Middle Ages, the design should have a more Medieval design. I found inspiration in the Assassin's Creed series of games, sketching out sketches of dragons. In the later stage, I will add colors according to the styles of different zones.

![3](uploads/40b46e21694a7bc7c381903d0c4f24db/3.png)

Figure 4 is a rough draft of the dragon I designed on the front. At this stage, I only drew the overall outline, hoping to make the dragon look more powerful through the positive design.

![4](uploads/54c75312373d0c49a27392afb5730af4/4.png)

#### **3/9/2020 - Version 1.1 of second draft designs.** 
Desert dragon design.
   
![微信图片_20200906134821](uploads/a01868ec4a1b8c4e9229641fa9b481bb/微信图片_20200906134821.jpg)  

Volcano dragon design.

![微信图片_202009061348211](uploads/843cda32f8368d2020cfe93995b7d9fd/微信图片_202009061348211.jpg)

Swamp dragon design.

![微信图片_202009061348212](uploads/e01d9d288ab11cca8aa4a001c21b8505/微信图片_202009061348212.jpg)

Tundra dragon design.
![微信图片_202009061348213](uploads/65c9a46915fbb482e772e9cbb618ba19/微信图片_202009061348213.jpg)

#### User Testing Sprint One
User testing was done to find out which of the 6 designs provides best player interaction and experience. The user test was done by online face-to-face interviews with 10 interviewees. The game concept was first introduced to them to provide a rough idea of how the game works. After that during the interviews, I asked them 6 questions:

1. Show the background of the game(4 zone image)
2. Show the 4 design of dragon.
3. Let people to give some suggestion.
4. Show different zone of goblin and orc.
5. Get some suggestion.

The user testing is conducted via google form https://docs.google.com/forms/d/1y7U3PtSoSqzlyEzsAzxkhXev3Ug4nEE9Q8fQ8yul8Tw/edit

User feedback showed that while users generally found the designs of each dragon to be appropriate for the environment, some feedback was provided that the size of the in-game rendering was insufficient, and that it was difficult to identify the colouration of some dragons. 
 
In future sprints, possible improvements to the design of the dragons include increasing the render size of the dragons in the game, to make them look more dangerous and threatening. Additionally, feedback was also provided that some of the dragons colours were too muted (especially the Swamp and Desert dragons) so future improvements to be made would include using brighter and more visible colouration on the dragon designs. 

Additionally, feedback was provided that the background textures of the dragons obscured the background of the environment, which broke the immersion of some users. In future sprints it is recommended to either make the background textures transparent or make the backgrounds more obviously related to enemy visual design (e.g snow surrounding the tundra dragon). 

### Public Methods
```java
- public Dragon(int health, float speed, int orbNumber)
```
   - Dragons should be initialised using this constructor: where the orbNumber represents the orb that the dragon is to drop. Texture management is handled in dragon subclasses without needing to pass any textures. 

```java
- public void summonGoblin()
```
    - Attempts to summon a new Goblin (with the same environmental texture as the Dragon) from the EnemyManager when called if there are currently less than 10 goblins spawned. 
```java
- public int applyDamage(int damage, DamageType damageType)
```
 - Applies the provided damage of type damageType to the dragon. Causes the dragon to look for a target, and calls the dragon's death() method if the damage would reduce its health to 0 or under.

```java
- public void hitByTarget() 
```
   - If the dragon does not currently have a target, when this method is called the dragon will set its new target to the player entity.
```java
- public void elementalAttack()
```
  - When called the dragon attempts to use its elemental attack on its current target. 

```java
- public void breathAttack()
``` 
 - When called the dragon attempts to use its breath attack on its current target. 

```java
- public void death() 
```
  - When called removes the Dragon from the game. Additionally spawns an Orb object at the location where the dragon dies, that will take the player to a new environment when the player walks over it.

```java
- public TextureRegion getFrame(float)
``` 
 - When called returns the current TextureRegion of the dragon.

### Prototype animations 

For implementation in the later progression of the game, sprite sheets were created to show the prototype animations for the enemy dragon. The attack task animations are shown as follows: 

![dragon_desert_sprite_sheet_](uploads/ba5446072e12d3281f61495cae5b6df8/dragon_desert_sprite_sheet_.png)
Sprite sheet animations for the desert dragon.

![dragon_volcano_sprite_sheet](uploads/2a7a8798ef59a3b3655b93a6f946e248/dragon_volcano_sprite_sheet.png)

Sprite sheet animations for the volcano dragon.

![dragon_tundra_sprite_sheet](uploads/4eaa6c2ef059ffb44e389559b707da38/dragon_tundra_sprite_sheet.png)
Sprite sheet animations for the tundra dragon.

![dragon_swamp_sprite_sheet](uploads/aa2478f05dc1a04daa0116190f34dc6c/dragon_swamp_sprite_sheet.png)
Sprite sheet animations for the swamp dragon.

### User Testing Sprint Two

User testing was conducted to identify if the attack tasks and prototype animations matched the user's expectations. This included combat attack tasks, as well as subtle inclusions that is believed to enhance the player's experience as well as their interactions in the game. The user test was done through online-face-to-face interviews with 10 interviewees. Firstly, a document was brought up which introduced the different prototype animations for the game, as well as its intended purpose and functionality in the game. Throughout this process, interviewees were asked the following questions about the proposed animations for each enemy type: 

1. For each animation task, users were asked for additions/changes they believed would improve the animation. 

2 Users were asked if they believed the animations were accurate and fluid. Clips such as the below clip were shown to interviewees. 
(*show clip of intended animations for orcs, goblins and dragons*): example below
![086aed33672f0b7c0235c78aac430000](uploads/b966e8f10af4e0b973b09b6fe614acd1/086aed33672f0b7c0235c78aac430000.mp4)
3. Users were asked whether they believed the prototype animations matched the overall game style, and more specifically which aspects they believed fit the style and which did not. 

The feedback provided on the dragon animations was fairly similar for all dragon types.

Preliminary user testing suggested that users found the animation of each dragon moving its tail to be dissonant with the actual behaviour of the dragon (firing its BreathWeapons and ElementalAttacks). This suggests that users did not have a strong association between the tail animation and the behaviour of each dragon, 

Future designs should consider animating parts of the dragon more associated with its attacks, such as potentially the head or wings (more user testing may be required to determine the appropriate parts users associated with its attacks).

Additionally, some users had difficult determining the changes at each stage of the animation. For example, one user stated that "The second picture feels that nothing has changed "; users generally had difficulty determining the effect of the animation. 

This suggests that users found the fidelity of the animations to be too low, or it may suggest that each frame is not visually distinct enough from the previous frame. This indicates that in future animations, more frames or frames that feature larger, more exaggerated movements could be considered to improve the animation graphics. Future user testing should be performed to determine what types of exaggeration should be used to make the animations more visually obvious.

### User Testing Sprint Four (carried on from Sprint Three)

Although the animations for dragons were not implemented in sprint three, the accumulation of results from both sprint three and four identify and address the following issues related to the dragons: 
1. Testing of the design of the dragons and their cohesion with the rest of the game.
2. Testing of the frames for the dragons to determine if designs are balanced with the actual behaviour of the dragons.
3. Testing of the fluidity of animations.

For each design of the dragon frames in each environment, users were required to answer if:
1. The current implementation of the dragons would create unity in the game as a whole (after showing users the game environment). If not, why and how?
2. The behavior of the dragons, i.e the attack and walking tasks, fit in with the overall game style (anything dissonant or awkward?).
3. The animation frames for the dragons are somewhat well-exaggerated and distinct (are the movements obvious? Could more frames help with this?).

As the game progresses towards its final refinement stages, more context can be provided in testing which allows for better constructive feedback.

User testing was similar to previous user tests, as such that users were shown the frames and sprites for the animations, as well as a short clip of either an in-game example prototype, or a sequence of frames that constitute to animations in a video format. This helps the user to better visualize the intended effects. Prior user tests identified walking and attacking animations to be of much greater importance as compared to tail movement in the dragons. Thus, it was a priority to find ways to implement those animations before anything else. In addition to attacking tasks, such as the dragon breathing fireballs etc, walking behavior was also implemented and tested to see if they matched the user's expectations and overall game style. Additionally, new designs were also proposed for the swamp and desert dragons, which were also included in the tests. The user test was done through online-face-to-face interviews of around 10 interviewees. The form showed the different proposed animation frames and new designs of the dragons, where users can see the full implementations.

Prevalent patterns and findings from user testings showed that designs still definitely need more frames in order to eliminate any signs of choppiness. If given more time, more frames for the designs are a must to create smoother in-game animations. Users found the current behaviour of the dragons to be acceptable and generally not too dissonant with actual behaviour; however, a future improvement as aforementioned, is to include more sprite designs to constitute to animations that move with attitude and purpose. Correspondingly, this means more fluid animations. However, users generally found that while the frames were somewhat choppy, the overall animations were of a much higher quality compared to the previous dragon sprites that were trialled during Sprint 2. This was most likely due to the greater variety of movement and more exaggerated movements. 

As prior testings determined that the swamp and desert dragons did not fit in with the rest of the game and design-wise were incompatible to the ones created for the volcano and tundra environment, to match the quality of the other designs which had been approved to be of acceptable quality for inclusion in the game, desert and swamp dragons were redesigned to better represent the final quality of the game. Alongside this, new animation frames were proposed based on previous and current user testing feedback. The updated designs are shown below.


#### Attacking sprites 

![dragon_tundra_attacking_sprites](uploads/b8f8bf8636b69dfe1b60035a5bb3aa58/dragon_tundra_attacking_sprites.png)
Tundra dragon attacking sprites 

![dragon_volcano_fireball_sprites](uploads/9cd1fadad23fbc11c465bd3e3387529a/dragon_volcano_fireball_sprites.png)
Volcano dragon attacking sprites

![desert_dragon_attack_sprites](uploads/6b60b833e20673398ca03d06384e44b4/desert_dragon_attack_sprites.png)
Desert dragon attacking sprites

![dragon_swamp_attacking_sprites](uploads/b270e3ea6402916c097816dadf28c478/dragon_swamp_attacking_sprites.png)
Swamp dragon attacking sprites


#### Walking Sprites

![dragon_volcano_walking_sprites](uploads/48d7c6ae7d360237a120f95de0881013/dragon_volcano_walking_sprites.png)
Volcano dragon walking sprites

![dragon_tundra_walking_sprites](uploads/6d2aa3981445a5848f22b77d8f5eef71/dragon_tundra_walking_sprites.png)
Tundra dragon walking sprites

![desert_dragon_tail_sprites](uploads/bad3c15688f9454da38041a1fb1d02f6/desert_dragon_tail_sprites.png)
Desert dragon tail movement sprites

![dragon_swamp_walking_sprites](uploads/8a9cea8d9de366f7701ad7ae221c4a0d/dragon_swamp_walking_sprites.png)
Swamp dragon walking sprites

#### Redesigned Swamp and Desert dragons

![dragon_desert](uploads/272198e4e8a0c97afeef07a7bbdb6dba/dragon_desert.png)

Newly designed desert dragon for the desert environment

![dragon_swamp](uploads/2219108743dacc003c3d3833046d1677/dragon_swamp.png)

Newly designed swamp dragon for the swamp environment


#### Example Gameplay Demonstrating New Animations

https://drive.google.com/file/d/1gGsHoS8-0Q7_w-oLzHMmB8GwQMoOHhbq/view?usp=sharing









