# Description

The animation is achieved by combining multiple frames and showing one frame at a time. Each frame is a `TextureRegion` with a fixed width and height. When rendering on each tick, the renderer will get the current frame of the animation instead of getting the entity's standard texture.

# Adding animation frames

The animation frames are conveniently managed by TextureManager, along with other static textures. There are two ways of storing the frames, one is to make every frame a single file; or to save space, put all the animation frames into a single sprite file. The TextureManager supports both ways of importing frames.

Note: The initial facing direction should be RIGHT. The flipped version is not needed.

### Import from Individual Files

To import from a collection of files, use 

```java
addAnimationFramesCollection(String id, String... files)
```
For example:
```java
addAnimationFramesCollection("playerFireball", "resources/combat/right_skill1_fire1.png",
        "resources/combat/right_skill1_fire2.png", "resources/combat/right_skill1_fire3.png",
        "resources/combat/right_skill1_fire4.png", "resources/combat/right_skill1_fire5.png");
```

### Import from a Sprite Sheet

To import from a sprite sheet, use

```java
addAnimationFramesSprite(String id, String file, int numOfFrames, int frameWidth, 
        int frameHeight, boolean horizontal)
```
For example:
```java
addAnimationFramesSprite("goblinDesertAttack", "resources/enemies/goblin_desert_sprite_sheet.png",
        3, 350, 486, true);
```

# Interface

To create an animation, you need to implement the `Animatable` interface.

### Animation object

You can specify the interval between frames.
```java
Animation<TextureRegion> playerRangeAttack = new Animation<>(0.1f,
        GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("playerRange"));
```

### Calculate the current frame

```java
return playerRangeAttack.getKeyFrame(frameNo);
```

# Implementing animation
Add the desired animation frames using the previous methods.

Implement the follow private variables:'

```java
// The current state of the entity
public State currentState;
// The previous state of the entity
public State previousState;

// One Animation<TextureRegion> per desired state
private final Animation<TextureRegion> Idle;
private final Animation<TextureRegion> Attacking;

// The timer on the animation
private float stateTimer;
// The duration of the anination
private int duration = 0;
// The current direction faced by the entity
private MovementTask.Direction facingDirection;
```

Initialise each variable in the constructor:
```java
        this.stateTimer = 0;
        // Set the default state and direciton
        currentState = State.IDLE;
        previousState = State.IDLE;
        facingDirection = MovementTask.Direction.RIGHT;

        // The key will depend on the key that was previously chosen
        this.Idle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("idlekey"));
        this.Attacking = new Animation<> (0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("attackKey"));
```
Implement getFrame:

```java
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        // One switch for each possible state
        switch (currentState) {
            case ATTACKING:
                region = Attacking.getKeyFrame(stateTimer);
                break;
            case IDLE:
            default:
                stateTimer = 0;
                region = Idle.getKeyFrame(stateTimer);
        }

        // Set the direction of the texture based on the current direction
        if ((getMovingDirection() == MovementTask.Direction.LEFT ||
                facingDirection == MovementTask.Direction.LEFT) && !region.isFlipX()) {
            region.flip(true, false);
            facingDirection = MovementTask.Direction.LEFT;
        } else if ((getMovingDirection() == MovementTask.Direction.RIGHT ||
                facingDirection == MovementTask.Direction.RIGHT) && region.isFlipX()) {
            region.flip(true, false);
            facingDirection = MovementTask.Direction.RIGHT;
        }
        
        // Set the stateTimer and previous state
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }
```

Implement the following in onTick:
```java
//Sets the state back to the default once the animation has concluded
       if (--duration < 0) {
            duration = 0;
            currentState = State.IDLE;
        }
```

Change the state and duration in the appropriate location in the code, and change the direction in the appropriate location: 
Example (could be called in onTick or similar method)

```java
            if (getTarget().getCol() < this.getCol()) {
                  facingDirection = MovementTask.Direction.LEFT;
            } else if (getTarget().getCol() > this.getCol()) {
                    facingDirection = MovementTask.Direction.RIGHT;
            }

            currentState = State.ATTACKING;
            duration = 12;
            setCombatTask(new MeleeAttackTask(this, origin, 2, 2, 10));
```