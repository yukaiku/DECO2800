package deco2800.thomas.entities.Agent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.HealthTracker;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.observers.KeyUpObserver;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.combat.FireballAttackTask;
import deco2800.thomas.tasks.combat.MeleeAttackTask;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.SquareVector;
import com.badlogic.gdx.Input;
import org.objenesis.instantiator.basic.ClassDefinitionUtils;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.HashMap;
import java.util.Map;

public class PlayerPeon extends Peon implements TouchDownObserver, KeyDownObserver, KeyUpObserver {
    public static int DEFAULT_HEALTH = 50;
    private static int orbCount = 0;

    // The health of the player
    private HealthTracker health;
    private static Map<String, String> dialogues = new HashMap<>();


    public PlayerPeon(float row, float col, float speed) {
        this(row, col, speed, DEFAULT_HEALTH);
    }

    public PlayerPeon(float row, float col, float speed, int health) {
        super(row, col, speed, health);
        this.setObjectName("playerPeon");
        this.setTexture("player_right");
        this.setColRenderLength(1.4f);
        this.setRowRenderLength(1.8f);
        this.setFaction(EntityFaction.Ally);
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyUpListener(this);
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

    /**
     * Quest Tracker function that tracks the orbs the user currently has
     *
     * @return orbCount the number of orbs the user currently has
     */
    public static int questTracker() {
        return orbCount;
    }

    /**
     * Resets the number of orb user has
     * Notes:
     * To be used on when a new game is run or upon death
     */
    public static void resetQuest() {
        orbCount = 0;
    }

    /**
     * Increase the number of orbs the user has
     * Notes:
     * To be used on when player picks up an orb
     */
    public static void increaseOrbs() {
        if (orbCount < 5) {
            orbCount += 1;
        }
    }

    /**
     * Decrease the number of orbs the user has
     * Notes:
     * To be used on when player picks up an orb
     */
    public static void decreaseOrbs() {
        if (orbCount > 1) {
            orbCount -= 1;
        }
    }

    @Override
    public void onTick(long i) {
        if (isDead()) {
            death();
        }
        if (getMovementTask() != null && getMovementTask().isAlive()) {
            getMovementTask().onTick(i);

            if (getMovementTask().isComplete()) {
                setMovementTask(null);
            }
        }

        if (getCombatTask() != null) {
            getCombatTask().onTick(i);

            if (getCombatTask().isComplete()) {
                setCombatTask(null);
            }
        }
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);


        if (button == Input.Buttons.LEFT) {
            //Set combat task to fireball task
            AbstractWorld world = GameManager.get().getWorld();
            this.setCombatTask(new FireballAttackTask(world.getPlayerEntity(), clickedPosition[0], clickedPosition[1],
                    10, 0.5f, 60));
        } else if (button == Input.Buttons.RIGHT) {
            // Set combat task to melee task
            // this.setCombatTask(new meleeTask);
            SquareVector origin;
            double angle = Math.toDegrees(Math.atan2(clickedPosition[0] - this.getCol(), clickedPosition[1] - this.getRow()));
            System.out.println(angle);
            if (angle > -45 && angle < 45) {
                // Spawn above player
                System.out.println("Above");
                origin = new SquareVector(this.getCol(), this.getRow() + 1);
                this.setCombatTask(new MeleeAttackTask(this, origin, 2, 2, 30));

            } else if (angle >= -135 && angle <= -45) {
                // Spawn to left of player
                System.out.println("Left");
                origin = new SquareVector(this.getCol() - 1, this.getRow());
                this.setCombatTask(new MeleeAttackTask(this, origin, 2, 2, 30));

            } else if (angle < -135 || angle > 135) {
                // Spawn below player
                System.out.println("Below");
                origin = new SquareVector(this.getCol(), this.getRow() - 1);
                this.setCombatTask(new MeleeAttackTask(this, origin, 2, 2, 30));

            } else if (angle >= 45 && angle <= 135) {
                // Spawn right of player
                System.out.println("Right");
                origin = new SquareVector(this.getCol() + 1, this.getRow());
                this.setCombatTask(new MeleeAttackTask(this, origin, 2, 2, 30));

            }
        }
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
                this.setTexture("player_left");
                this.setMovingDirection(MovementTask.Direction.LEFT);
                break;
            case Input.Keys.D:
                this.setTexture("player_right");
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

    @Override
    public void death() {
        GameManager.get().getWorld().removeEntity(this);
        GameManager.get().getWorld().disposeEntity(this.getEntityID());
        GameManager.gameOver();
    }

    @Override
    public void dispose() {
        GameManager.getManagerFromInstance(InputManager.class).removeTouchDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).removeKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).removeKeyUpListener(this);
    }
}
