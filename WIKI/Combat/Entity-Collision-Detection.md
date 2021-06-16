# *Entity Collision Detection*
## High Level Process Description
Entity collision is implemented through Axis Aligned Bounding Boxes (AABB). To implement this, we have the BoundingBox class. Note, LibGDX does come with a BoundingBox class, however that class is implemented in 3 dimensions, using Vector3s. Since our game engine is in 2d, and uses SquareVectors, a new class was implemented for this purpose.

The AbstractEntity class initialises their BoundingBox, so that the "origin" of the box references the position of the AbstractEntity, and default dimensions of 0 x 0. Whenever the AbstractEntity method setTexture is called, the bounding box dimensions are updated to match the texture width and height, scaled appropriately using WorldUtil.SCALE_X and WorldUtil.SCALE_Y.

To check for a collision within a given bounds, all the entities bounds in the world are polled against the testing bounds for overlap. If there is an overlap, they are added to the collision list.

## Important Notes!
* The current implementation grabs dimensions of the bounding box from the texture size of the entity. This is done in the _setTexture(string)_ method of AbstractEntity. If this method is not invoked to change the texture, then the BoundingBox will not update. _(By default, it has dimensions of 0x0)._
* Since the dimensions of the BoundingBox come from the texture dimensions, if the texture has excessive "whitespace" surrounding the sprite, the HitBox will appear to be larger than the sprite in game.

_Example of Excessive Whitespace. The red box is the BoundingBox, but the yellow box is what the player sees_
![ExcessiveWhiteSpaceExample](uploads/ea2ba5a23f5d12d954d896f4fb1229f3/ExcessiveWhiteSpaceExample.png)

## How to use:
Access the current game world through the GameManager, and then poll for entities within a bounds.

_e.g., Getting all entities that collide with entityA:_

    AbstractWorld world = GameManager.get().getWorld();

    List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(entityA.getBounds());

## Implemented Class Structure for Collision Detection
### Current Implementation
Access to game entities through GameManager and AbstractWorld:
![AbstractWorldEntities](uploads/be5e5e3f53c376f1e247e21355194361/AbstractWorldEntities.png)

BoundingBox dependencies:
![BoundingBox](uploads/d5fe3e04310274f21313c64eb025b563/BoundingBox.png)

_Note: Not representing full AbstractEntity, or SquareVector classes. Only showing relevant changes to AbstractEntity, and SquareVector still uses the base implementation._

## General remarks and comments
This system utilises one of the fastest collision detection algorithms (AABB), which is extremely well suited to a grid based game. However, it does come with some restrictions:
* All collision "colliders" must be rectangular
* Collision boxes cannot rotate
* BoundingBox dimensions are matched to Sprite dimensions

## Potential Improvements
### Functionality improvements
* Implement circular bounds (Would Circle -> Circle detection, and Circle -> Rectangle detection)
* Implement rotatable bounds (For example, using the Axis of Separation algorithm)
* Separate BoundingBox dimensions from Sprite dimensions
* Abstract Colliders, so that you may use different colliding shapes, or even multiple colliders for the same entity

### Optimisations
* Update the data structure in AbstractWorld such that the search for colliding entities is in sub linear time.
* Reduce the entities polled in AbstractWorld by separating collidable entities, and non-collidable entities into separate lists.
* Reduce memory use by only initialising BoundingBoxes for entites that are collidable.