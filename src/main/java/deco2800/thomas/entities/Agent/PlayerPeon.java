package deco2800.thomas.entities.Agent;

import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.observers.KeyUpObserver;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;
import com.badlogic.gdx.Input;

public class PlayerPeon extends Peon implements TouchDownObserver, KeyDownObserver, KeyUpObserver {

    private static int orbCount = 0;

    public PlayerPeon(float row, float col, float speed) {
        super(row, col, speed);
        this.setObjectName("playerPeon");
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyUpListener(this);
    }


    /**
     *
     * Quest Tracker function that tracks the orbs the user currently has
     *
     * @return orbCount the number of orbs the user currently has
     */
    public static int questTracker(){
        return orbCount;
    }

    /**
     *
     * Resets the number of orb user has
     * Notes:
     * To be used on when a new game is run or upon death
     *
     */
    public static void resetQuest(){
        orbCount = 0;
    }

    /**
     *
     * Increase the number of orbs the user has
     * Notes:
     * To be used on when player picks up an orb
     *
     */
    public static void increaseOrbs(){
        if(orbCount < 5){
            orbCount += 1;
        }
    }

    /**
     *
     * Decrease the number of orbs the user has
     * Notes:
     * To be used on when player picks up an orb
     *
     */
    public static void decreaseOrbs(){
        if(orbCount > 1){
            orbCount -= 1;
        }
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
