package deco2800.thomas.entities;

import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.observers.KeyUpObserver;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;
import com.badlogic.gdx.Input;

public class PlayerPeon extends Peon implements TouchDownObserver, KeyDownObserver, KeyUpObserver {

    // The health of the player
    private HealthTracker health;

    public PlayerPeon(float row, float col, float speed, int health) {
        super(row, col, speed);
        this.setObjectName("playerPeon");
        this.health = new HealthTracker(health);
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyUpListener(this);
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

        System.out.printf("PlayerBottom: %f\n",this.getBounds().getBottom());
        System.out.printf("PlayerLeft: %f\n", this.getBounds().getLeft());
        System.out.printf("PlayerRight: %f\n", this.getBounds().getRight());
        System.out.printf("PlayerTop: %f\n", this.getBounds().getTop());
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
