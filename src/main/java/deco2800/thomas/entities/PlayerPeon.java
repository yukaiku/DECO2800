package deco2800.thomas.entities;

import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.observers.KeyUpObserver;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;
import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

public class PlayerPeon extends Peon implements TouchDownObserver, KeyDownObserver, KeyUpObserver {

    // The health of the player
    private HealthTracker health;
    private Map<String, String> dialogues = new HashMap<>();

    public PlayerPeon(float row, float col, float speed, int health) {
        super(row, col, speed);
        this.setObjectName("playerPeon");
        this.health = new HealthTracker(health);
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyUpListener(this);
    }

    /**
     * Returns a dialogue string depending on the target string
     * @param target The target string identifier
     */
    public String getDialogue(String target) {
        dialogues.put("welcome","Welcome to (Game Name) the world has been devastated " +
                "with the re-emergence of the five pythagoras orbs. In order to save this world, " +
                "you will need to collect all the orbs and restore balance to the world.");
        dialogues.put("WASD", "To move your character press W for up, S for down, A for left, D for right, " +
                "please move to the " +
                "checkpoint marked with a flag to proceed.");
        dialogues.put("attack", "An enemy is in front of you, get closer and click (attack key) to kill the monster");
        dialogues.put("orb", "There is an orb in front of you, pick it up by interacting with it.");
        dialogues.put("congrats", "Congratulations on completing the tutorial, would you like to move to the next stage or redo " +
                "the tutorial?");
        dialogues.put("fire", "I am a Pyromancer,, pick me and I'll burn all that stands before you to ashes.");
        dialogues.put("water", "I am a Hydromancer , pick me and I'll drown all our enemies.");
        dialogues.put("air", "I am a Anemancer, pick me and I'll unleash a hurricane on our foes.");
        dialogues.put("earth", "I am a Geomancer, pick me and I'll crush all monsters with mother earth.");
        dialogues.put("shield", "I am the shield knight, nothing shall get past my shield.");
        dialogues.put("sword", "I am the sword knight, i will make quick work of your enemies.");
        dialogues.put("zone", "Welcome adventure to (zone name) , to complete this stage, " +
                "you will have to locate the orb of (depend on zone). The monsters here are " +
                "vulnerable to (element) and have high (attack/defense) " +
                "but low (attack/defense). Choose your character wisely.");
        dialogues.put("next", "Congratulations for collecting the orb and completing the quest, you will now proceed on to " +
                "the next stage.");
        dialogues.put("roar", "Roar!!!");
        dialogues.put("grr", "GRRRRR");
        dialogues.put("died", "Too bad, you died, would you like to restart from your previous checkpoint or start anew?");
        dialogues.put("finish", "Congratulations hero, you have collected all the orbs and restored peace to the world.");
        return dialogues.get(target);
    }

    /**
     * Returns the maximum health of the player.
     */
    public int getMaxHealth() {
        return health.getMaxHealthValue();
    }

    /**
     * Sets the maximum health of the player.
     * @param newMaxHealth the new maximum health of the player.
     */
    public void setMaxHealth(int newMaxHealth) {
        this.health.setMaxHealthValue(newMaxHealth);
    }

    /**
     * Returns the current health of the player.
     */
    public int getCurrentHealth() {
        return this.health.getCurrentHealthValue();
    }

    /**
     * Sets the current health of this player to be a new value.
     * @param newHealth The new current health of this player.
     */
    public void setCurrentHealthValue(int newHealth) {
        this.health.setCurrentHealthValue(newHealth);
    }

    /**
     * Reduces the health of the player by the given amount.
     * @param damage The amount of damage to be taken by the player.
     */
    public void reduceHealth (int damage) {
        this.health.reduceHealth(damage);
    }

    /**
     * Increases the health of the player by the given amount.
     * @param regen The amount of health the player is to be healed by.
     */
    public void regenerateHealth (int regen) {
        this.health.regenerateHealth(regen);
    }

    public boolean isDead () {
        return (this.getCurrentHealth() <= 0);
    }

    @Override
    public void onTick(long i) {
        if (getTask() != null && getTask().isAlive()) {
            getTask().onTick(i);

            if (getTask().isComplete()) {
                setTask(null);
            }
        }
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
    }

    @Override
    public void notifyKeyDown(int keycode) {
        if (keycode == Input.Keys.W || keycode == Input.Keys.S ||
                keycode == Input.Keys.A || keycode == Input.Keys.D) {
            this.startMovementTask(keycode);
        }
    }

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
        if (this.getTask() == null || this.getTask().isComplete()) {
            this.setTask(new MovementTask(this, new SquareVector(this.getCol(), this.getRow())));
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
}
