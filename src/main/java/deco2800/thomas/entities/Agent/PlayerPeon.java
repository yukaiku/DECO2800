package deco2800.thomas.entities.Agent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.combat.Skill;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.combat.skills.FireballSkill;
import deco2800.thomas.combat.skills.MeleeSkill;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.observers.KeyUpObserver;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerPeon extends Peon implements Animatable, TouchDownObserver, KeyDownObserver, KeyUpObserver {
    // Animation Testing
    public enum State {
        STANDING, WALKING, MELEE_ATTACK, RANGE_ATTACK
    }
    public State currentState;
    public State previousState;
    private MovementTask.Direction facingDirection;
    private final Animation<TextureRegion> playerStand;
    private final Animation<TextureRegion> playerMeleeAttack;
    private final Animation<TextureRegion> playerRangeAttack;
    private float stateTimer;
    private int duration = 0;

    public static int DEFAULT_HEALTH = 50;

//    //Orbs tracker
//    private static int orbCount = 0;
//    private static List<Orb> orbs = new ArrayList<Orb>();

    // The health of the player
    private HealthTracker health;
    private static Map<String, String> dialogues = new HashMap<>();

    // Wizard skills
    private List<Skill> wizardSkills;
    private int activeWizardSkill;

    // Mech skill
    private Skill mechSkill;

    public PlayerPeon(float row, float col, float speed) {
        this(row, col, speed, DEFAULT_HEALTH);
    }

    public PlayerPeon(float row, float col, float speed, int health) {
        // Initialise abstract entity
        super(row, col, speed, health);
        this.setObjectName("playerPeon");
        this.setTexture("player_right");
        this.setColRenderLength(1.4f);
        this.setRowRenderLength(1.8f);
        this.setFaction(EntityFaction.Ally);

        // Subscribe listeners
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyUpListener(this);

        // Initialise skills
        wizardSkills = new ArrayList<>();
        wizardSkills.add(new FireballSkill(this));
        activeWizardSkill = 0;

        mechSkill = new MeleeSkill(this);

        // Animation Testing
        facingDirection = MovementTask.Direction.RIGHT;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        playerMeleeAttack = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("player_melee"));
        playerRangeAttack = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("player_range"));
        playerStand = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("player_stand"));
    }

    /**
     * Returns a dialogue string depending on the target string
     *
     * @param target The target string identifier
     */
    public static String getDialogue(String target) {
        dialogues.put("welcome", "Welcome to Decodia the world has been devastated " +
                "with the re-emergence of the five pythagoras orbs. In order to save this world, " +
                "you will need to collect all the orbs and restore balance to the world.");
        dialogues.put("WASD", "To move your character press W for up, S for down, A for left, D for right, " +
                "please move to the " +
                "checkpoint marked with a flag to proceed.");
        dialogues.put("attack", "An enemy is in front of you, get closer and click M1 to kill the monster");
        dialogues.put("orb", "There is an orb in front of you, pick it up by interacting with it.");
        dialogues.put("congrats", "Congratulations on completing the tutorial, would you like to move to the next stage or redo " +
                "the tutorial?");
        dialogues.put("fire", "I am a Pyromancer,, pick me and I'll burn all that stands before you to ashes.");
        dialogues.put("water", "I am a Hydromancer , pick me and I'll drown all our enemies.");
        dialogues.put("air", "I am a Anemancer, pick me and I'll unleash a hurricane on our foes.");
        dialogues.put("earth", "I am a Geomancer, pick me and I'll crush all monsters with mother earth.");
        dialogues.put("shield", "I am the shield knight, nothing shall get past my shield.");
        dialogues.put("sword", "I am the sword knight, i will make quick work of your enemies.");
        dialogues.put("swamp", "Welcome adventure to Swamp Zone , to complete this stage, " +
                "you will have to locate the orb of muck. The monsters here are " +
                "vulnerable to air");
        dialogues.put("volcano", "Welcome adventure to Volcano Zone , to complete this stage, " +
                "you will have to locate the orb of lava. The monsters here are " +
                "vulnerable to earth");
        dialogues.put("tundra", "Welcome adventure to Tundra Zone , to complete this stage, " +
                "you will have to locate the orb of ice. The monsters here are " +
                "vulnerable to fire");
        dialogues.put("desert", "Welcome adventure to Desert Zone , to complete this stage, " +
                "you will have to locate the orb of sand. The monsters here are " +
                "vulnerable to water");
        dialogues.put("next", "Congratulations for collecting the orb and completing the quest, you will now proceed on to " +
                "the next stage.");
        dialogues.put("roar", "Roar!!!");
        dialogues.put("grr", "GRRRRR");
        dialogues.put("died", "Too bad, you died, would you like to restart from your previous checkpoint or start anew?");
        dialogues.put("finish", "Congratulations hero, you have collected all the orbs and restored peace to the world.");
        return dialogues.get(target);
    }

//    /**
//     * Quest Tracker function that tracks the orbs the user currently has
//     *
//     * @return orbCount the number of orbs the user currently has
//     */
//    public static List<Orb> questTracker() {
//        return orbs;
//    }
//
//    /**
//     * Resets the number of orb user has
//     * Notes:
//     * To be used on when a new game is run or upon death
//     */
//    public static void resetQuest() {
//        orbs.clear();
//        orbCount = 0;
//    }
//
//    /**
//     * Increase the number of orbs the user has
//     * Notes:
//     * To be used on when player picks up an orb
//     */
//    public static <Orb> void increaseOrbs(deco2800.thomas.entities.Orb orb) {
//        if (orbCount < 5) {
//            orbCount += 1;
//            orbs.add(orb);
//        }
//    }
//
//    /**
//     * Decrease the number of orbs the user has
//     * Notes:
//     * To be used on when player picks up an orb
//     */
//    public static void decreaseOrbs() {
//        if (orbCount > 1) {
//            orbCount -= 1;
//            orbs.remove(orbCount);
//        }
//    }

    /**
     * Updates the player peon's over time methods, such as tasks and cooldowns.
     *
     * @param i current game tick.
     */
    @Override
    public void onTick(long i) {
        // Check death condition
        if (isDead()) {
            death();
        }

        // Update movement task
        if (getMovementTask() != null && getMovementTask().isAlive()) {
            getMovementTask().onTick(i);

            if (getMovementTask().isComplete()) {
                setMovementTask(null);
            }
        }

        if (--duration < 0) {
            duration = 0;
            currentState = State.STANDING;
        }

        // Update combat task
        if (getCombatTask() != null) {
            getCombatTask().onTick(i);
            if (getCombatTask() instanceof MeleeAttackTask) {
                currentState = State.MELEE_ATTACK;
            } else {
                currentState = State.RANGE_ATTACK;
            }
            // Due to the combat task is currently executed instantly, will set a cool down here
            duration = 9;

            if (getCombatTask().isComplete()) {
                setCombatTask(null);
            }
        }

        // Update skills
        for (Skill s : wizardSkills) {
            if (s != null) {
                s.onTick(i);
            }
        }
        if (mechSkill != null) {
            mechSkill.onTick(i);
        }
    }

    /**
     * Get the texture region of current animation keyframe (this will be called in Renderer3D.java).
     * @param delta the interval of the ticks
     * @return the current texture region in animation
     */
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        // Get the animation frame based on the current state
        switch (currentState) {
            case RANGE_ATTACK:
                region = playerRangeAttack.getKeyFrame(stateTimer);
                break;
            case MELEE_ATTACK:
                region = playerMeleeAttack.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = playerStand.getKeyFrame(stateTimer);
        }
        // Render the correct direction of the texture
        if ((getMovingDirection() == MovementTask.Direction.LEFT || facingDirection == MovementTask.Direction.LEFT) && !region.isFlipX()) {
            region.flip(true, false);
            facingDirection = MovementTask.Direction.LEFT;
        } else if ((getMovingDirection() == MovementTask.Direction.RIGHT || facingDirection == MovementTask.Direction.RIGHT) && region.isFlipX()) {
            region.flip(true, false);
            facingDirection = MovementTask.Direction.RIGHT;
        }
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    /**
     * Handles the mouse down event. This triggers combat skills.
     *
     * @param screenX the x position the mouse was pressed at
     * @param screenY the y position the mouse was pressed at
     * @param pointer
     * @param button  the button which was pressed
     */
    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        if (button == Input.Buttons.LEFT) {
            try {
                //Set combat task to fireball task
                Skill wizardSkill = wizardSkills.get(activeWizardSkill);
                if (wizardSkill.getCooldown() <= 0) {
                    this.setCombatTask(wizardSkill.getNewSkillTask(clickedPosition[0], clickedPosition[1]));
                }
            } catch (SkillOnCooldownException e) {
                // Won't occur because I'm handling it.
                e.printStackTrace();
            }
        } else if (button == Input.Buttons.RIGHT) {
            try {
                if (mechSkill.getCooldown() <= 0) {
                    this.setCombatTask(mechSkill.getNewSkillTask(clickedPosition[0], clickedPosition[1]));
                }
            } catch (SkillOnCooldownException e) {
                // Won't occur because I'm handling it
                e.printStackTrace();
            }
        }

        // set the facing direction when attacking
        if (clickedPosition[0] < this.getCol()) {
            facingDirection = MovementTask.Direction.LEFT;
        } else {
            facingDirection = MovementTask.Direction.RIGHT;
        }
    }

    /**
     * Handles the key down event.
     *
     * @param keycode the key being pressed
     */
    @Override
    public void notifyKeyDown(int keycode) {
        if (keycode == Input.Keys.W || keycode == Input.Keys.S ||
                keycode == Input.Keys.A || keycode == Input.Keys.D) {
            this.startMovementTask(keycode);
        } else if (keycode == Input.Keys.NUM_1 || keycode == Input.Keys.NUM_2 ||
                keycode == Input.Keys.NUM_3 || keycode == Input.Keys.NUM_4) {
            this.swapSkill(keycode);
        }
    }

    /**
     * Handles the key up event.
     *
     * @param keycode the key being released
     */
    @Override
    public void notifyKeyUp(int keycode) {
        if (keycode == Input.Keys.W || keycode == Input.Keys.S ||
                keycode == Input.Keys.A || keycode == Input.Keys.D) {
            this.stopMovementTask(keycode);
        }
    }

    /**
     * Classify the keycode and set the corresponding moving direction
     * for our entity. After that add a new movement task for our entity.
     * The status of the entity's task should be checked in order to
     * prevent the case our player are moving in the middle of a tile and
     * change the direction.
     *
     * @param keycode the input keycode from the key board to classify
     */
    private void startMovementTask(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                this.setMovingDirection(MovementTask.Direction.UP);
                break;
            case Input.Keys.S:
                this.setMovingDirection(MovementTask.Direction.DOWN);
                break;
            case Input.Keys.A:
                this.setMovingDirection(MovementTask.Direction.LEFT);
                break;
            case Input.Keys.D:
                this.setMovingDirection(MovementTask.Direction.RIGHT);
                break;
            default:
                break;
        }
        if (this.getMovementTask() == null || this.getMovementTask().isComplete()) {
            this.setMovementTask(new MovementTask(this, new SquareVector(this.getCol(), this.getRow())));
        }
    }

    /**
     * Set the variable movingDirection of the entity back to NONE to
     * notify the MovementTask stop the entity. The method also check
     * the keycode we released with the current movingDirection in case
     * the player release the previous key after he push a new key.
     *
     * @param keycode the keycode to classify
     */
    private void stopMovementTask(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                if (this.getMovingDirection() == MovementTask.Direction.UP) {
                    break;
                }
                return;
            case Input.Keys.S:
                if (this.getMovingDirection() == MovementTask.Direction.DOWN) {
                    break;
                }
                return;
            case Input.Keys.A:
                if (this.getMovingDirection() == MovementTask.Direction.LEFT) {
                    break;
                }
                return;
            case Input.Keys.D:
                if (this.getMovingDirection() == MovementTask.Direction.RIGHT) {
                    break;
                }
                return;
            default:
                return;
        }
        this.setMovingDirection(MovementTask.Direction.NONE);
    }

    /**
     * Check if there is a skill mapped with the keycode. If
     * there is a key mapped then swap the active skill into
     * that one.
     *
     * @param keycode the keycode of the skill we would like to swap
     */
    private void swapSkill(int keycode) {
        int targetSkill = keycode - Input.Keys.NUM_1;
        if (targetSkill < this.getWizardSkills().size()) {
            this.activeWizardSkill = targetSkill;
        }
    }

    public List<Skill> getWizardSkills() {
        return this.wizardSkills;
    }

    public int getActiveWizardSkill() {
        return this.activeWizardSkill;
    }

    /**
     * Triggered when health goes below zero. Removes entity
     * from world.
     */
    @Override
    public void death() {
        GameManager.get().getWorld().removeEntity(this);
        GameManager.get().getWorld().disposeEntity(this.getEntityID());
        QuestTracker.resetQuest();
    }

    /**
     * Clears up listeners when removed from game world.
     */
    @Override
    public void dispose() {
        GameManager.getManagerFromInstance(InputManager.class).removeTouchDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).removeKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).removeKeyUpListener(this);
    }
}
