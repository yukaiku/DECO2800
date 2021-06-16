## Table of Contents

[[_TOC_]]

# Introduction

The minimap is a square overlay showing the a small representation of the current world. It also displays the relative location of both the player and enemies. This includes wild enemies such as orcs, as well as special enemies and bosses such as goblins and dragons. The minimap has been positioned in the bottom right corner of the screen. 

Additionally, the zoom functionality was disabled in order to prevent users from being able to view the entire world by zooming out, thus encouraging the use of the minimap. 

## Features

* Different colours for environmental tiles
* Different markers for entities (players and enemies)
* Scale representation of the position of the players and enemies on the environment

# Components

### Player

The player's location will be represented using a miniaturised version of the player's sprite to scale on the minimap.  

### Enemies

The normal enemies will be shown as icons representing the relative location of each enemy in the environment, while the bosses will use miniaturised versions of their in-game textures. 

### Tiles

Each tile in each environment has been rendered to scale on the minimap. 

# Design Process
* Icon of goblins in each environment (swamp desert tundra volcano respectively)
![sketch1602073320510_已去底_](uploads/b79b46dffe29b2d3289008080a843697/sketch1602073320510_已去底_.png)
![sketch1602073509379_已去底_](uploads/2c8a7f10d2d42664bb20e2cac9ecdcc2/sketch1602073509379_已去底_.png)
![sketch1602073618709_已去底_](uploads/0cf25044135846052ddcd0f2c4713a66/sketch1602073618709_已去底_.png)
![sketch1602073768623_已去底_](uploads/4fd5212603347ed44b6b3841a99cb9b9/sketch1602073768623_已去底_.png)

* Icon of orc in each environment (tundra desert swamp volcano respectively)
![sketch1602072623080](uploads/34980948a3fd5e3fd3ef3531d71744ef/sketch1602072623080.png)
![sketch1602072703219_已去底_](uploads/ed8615a939c0630c2ea8e2e1f7a11932/sketch1602072703219_已去底_.png)
![sketch1602072838531_已去底_](uploads/e0897d92e972abf2984f22ecb5093e23/sketch1602072838531_已去底_.png)
![sketch1602073018544_已去底_](uploads/ed6bc824a008d917d8fd85cd06eb2469/sketch1602073018544_已去底_.png)

* Icon of dragons in each environment (swamp, desert, volcano, tundra respectively)
![sketch1602073098528](uploads/4d3a8092b084cf77c607313dcd48cb9f/sketch1602073098528.png)
![sketch1602073122654](uploads/451fb9fad82c2f3ac7317cb712d5127f/sketch1602073122654.png)
![sketch1602073155862](uploads/da3f76bda5103c353cc0c071d0934be8/sketch1602073155862.png)
![sketch1602073296365](uploads/b2bf529d9d5a4ed0c9a67e694a4d672e/sketch1602073296365.png)

## Concept Draft

<img src="uploads/3ae7884d5c425f736642c2619be587d5/IMG_0657.jpg" width=500/>

## Swamp Environment Minimap
![swamp_minimap](uploads/6905d14b9ce355a61118e267e81bd67a/swamp_minimap.png)

## Tundra Environment Minimap
![tundra_minimap](uploads/02e4e1e19ee24e2b1e16ceac6a7553aa/tundra_minimap.png)

## Desert Environment Minimap
![desert_minimap](uploads/04aa05b01869c961d0dd06497287524f/desert_minimap.png)

## Volcano Environment Minimap
![volcano_minimap](uploads/8d21f5feadd431a51c771fa20eb1bf17/volcano_minimap.png)

## User Testing
User testing was done to find out which icon design provided the best player interaction and experience, and which icons most reflected the design of each entity's textures, so that they could be most easily identified as relating to the enemy texture in-game. The user test was done by online face-to-face interviews and google forms. Each user was asked whether they thought the icons accurately reflected for goblins, orcs and dragons.

Feedback for the orc and goblin icons were generally positive: users were able to understand that the icons correlated to the appearance of the orcs and goblins inside the game, and thus understand that the relative position of an orc/goblin icon corresponded to the orc/goblin location in the game. Some feedback that was provided was that each of these icon were rendered with the same eye colour: distinguishing the eye colour could have somewhat improved the visual differences between icons. 

Feedback for the dragon icons was that it was difficult to determine the dragon icons were representative of the bosses in the game: this may have been because the icons were too stylised and not as close to the appearance of the in-game textures when compared to the goblins and orcs. As a result, for this sprint the choice was made to use the textures of the dragons instead to represent the location of bosses on the minimap, as users found that it was easier to determine that the smaller textures were representative of boss locations. In further sprints, user testing to determine what icons more accurately convey to the player that they represent each boss is to be undertaken. 

## Sprint 4 Updates
During sprint 4, minor, quality of life updates were made to the minimap in response to feedback generated from the play test documented in [Enemies](/enemies).

The main point of feedback was that on smaller screens or non-full screen mode, the bottom of the minimap would be cut off from the screen. To fix this issue, the minimap was changed to be rendered on the top-left of the screen instead, which was able to resolve these issues. However, it was found that with the introduction of the camera locked inside world boundaries, the new minimap would block vision of the player if they moved to the upper left. In response, the minimap was changed to be semi-transparent as the player approached the top-left of the screen in order to prevent this obscuration of the player's vision. 

Additionally, the name of the current and the player's current coordinates have also been rendered underneath the minimap. This was because some users found having this information to be beneficial in keeping track of the current location. 

## Swamp Environment Minimap
![swamp_minimap](uploads/6356d5b8ed558450153b36685866de45/image.png))

## Tundra Environment Minimap
![tundra_minimap](uploads/9159f50675b66b484da99c2d0047755c/image.png))

## Desert Environment Minimap
![desert_minimap](uploads/e3d2c24141e84c640f3e7aecf03b3167/image.png)

## Volcano Environment Minimap
![volcano_minimap](uploads/4dcfef7570e9f45e00a69cc76725de04/image.png)

## Transparent Minimap
![transparent_minimap](uploads/0af6ce5434f8f5b8cbcfbaf71e1eec0d/image.png)

## Full Screen 
(to show new minimap position)
![full_screen](uploads/fd817305df3596cfe7abe6381dae03f7/image.png)

# Appendix
* **Last edited**: 25 Oct (Sprint 4)
* **Authors**: Yiyun Zhang (@fzmi), Wilson Yu (@wyu17), Zixin Xia(@BLAKEXIA)
