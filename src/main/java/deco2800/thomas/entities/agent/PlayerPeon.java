package deco2800.thomas.entities.agent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.thomas.combat.KnightSkills;
import deco2800.thomas.combat.PlayerSkills;
import deco2800.thomas.combat.SkillOnCooldownException;
import deco2800.thomas.combat.WizardSkills;
import deco2800.thomas.combat.skills.*;
import deco2800.thomas.combat.skills.IceBreathSkill;
import deco2800.thomas.entities.Animatable;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.managers.*;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.observers.KeyUpObserver;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.movement.MovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

import java.util.*;

public class PlayerPeon extends LoadedPeon implements Animatable, TouchDownObserver, KeyDownObserver, KeyUpObserver {
    // Animation Testing
    public enum State {
        IDLE, WALK, ATTACK_MELEE, ATTACK_RANGE, INCAPACITATED
    }

    private State currentState;
    private State previousState;
    private MovementTask.Direction facingDirection;
    private final Animation<TextureRegion> playerIdle;
    private final Animation<TextureRegion> playerMeleeAttack;
    private final Animation<TextureRegion> playerRangeAttack;
    private final Animation<TextureRegion> playerSpin;
    private final Animation<TextureRegion> playerWalk;
    private float stateTimer;
    private int duration = 0;

    public static final int DEFAULT_HEALTH = 100;
    private static int buffDamageTotal;
    private static boolean isCoolDownBuffActive = false;
    private static int buffArmourTotal;

    // Player dialogue
    private static final Map<String, String> dialogues = new HashMap<>();

    // Wizard skills
    private List<AbstractSkill> wizardSkills;
    private int activeWizardSkill;

    // Mech skill
    private AbstractSkill mechSkill;

    // Movement helper
    private Stack<MovementTask.Direction> movementStack = new Stack<>();
    private boolean[] activeDirectionKeys = {true, false, false, false, false};

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
        this.setFaction(EntityFaction.ALLY);
        this.addDamage(buffDamageTotal);
        this.addArmour(buffArmourTotal);
        // Subscribe listeners
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyUpListener(this);

        // Initialise skills
        wizardSkills = new ArrayList<>();
        getPlayerSkills();
        activeWizardSkill = 0;

        // Animation Testing
        facingDirection = MovementTask.Direction.RIGHT;
        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        playerMeleeAttack = new Animation<>(0.08f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("playerMelee"));
        playerRangeAttack = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("playerRange"));
        playerSpin = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("playerSpin"));
        playerIdle = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("playerIdle"));
        playerWalk = new Animation<>(0.1f,
                GameManager.getManagerFromInstance(TextureManager.class).getAnimationFrames("playerWalk"));

        if (isCoolDownBuffActive){
            FireballSkill.setMaxCooldown(10);
            FireBombSkill.setMaxCoolDown(80);
            HealSkill.setMaxCoolDown(100);
            IceballSkill.setMaxCooldown(25);
            IceBreathSkill.setMaxCooldown(10);
            SandTornadoSkill.setMaxCoolDown(15);
            ScorpionStingSkill.setMaxCoolDown(25);
            WaterShieldSkill.setMaxCoolDown(100);
        } else {resetAllSkillCoolDownToOriginal();}
    }

    /**
     * Returns a dialogue string depending on the target string
     *
     * @param target The target string identifier
     */
    public static String getDialogue(String target) {
        dialogues.put("plot", "Greetings my students, you both must save the world of Decodia by defeating the elder dragons" +
                "\nand collecting the orbs they have stolen. These orbs contain the great power of the elements " +
                "\nand without them the world is out of balance. You will have to use your combined powers of " +
                "\nsteel and magic to prevail against the looming terrors.");

        dialogues.put("WASD", "To move your character press W for up, S for down, A for left, D for right, " +
                "\nplease move to the checkpoint marked with a flag to proceed.");

        dialogues.put("attack", "An enemy is in front of you, get closer and click M1 to kill the monster");

        dialogues.put("orb", "There is an orb in front of you, pick it up by interacting with it.");

        dialogues.put("congrats", "Congratulations on completing the tutorial, " +
                "\nwould you like to move to the next stage or redo " +
                "the tutorial?");

        dialogues.put("welcome", "Welcome to Decodia the world has been devastated " +
                "\nwith the re-emergence of the five pythagoras orbs. In order to save this world, " +
                "\nyou will need to collect all the orbs and restore balance to the world.");

        dialogues.put("fire", "I am a Pyromancer,, pick me and I'll burn all that stands before you to ashes.");

        dialogues.put("water", "I am a Hydromancer , pick me and I'll drown all our enemies.");

        dialogues.put("air", "I am a Anemancer, pick me and I'll unleash a hurricane on our foes.");

        dialogues.put("earth", "I am a Geomancer, pick me and I'll crush all monsters with mother earth.");

        dialogues.put("shield", "I am the shield knight, nothing shall get past my shield.");

        dialogues.put("sword", "I am the sword knight, i will make quick work of your enemies.");

        dialogues.put("swamp", "Welcome adventure to Swamp Zone , to complete this stage, " +
                "\nyou will have to locate the orb of muck. The monsters here are vulnerable to air");

        dialogues.put("npc_swamp_dungeon_blue", "Step in the top gold tile to get the treasure ");

        dialogues.put("npc_swamp_dungeon_green", "Yellow always speaks the truth ");

        dialogues.put("npc_swamp_dungeon_orange", "White is the only truth-speaker ");

        dialogues.put("npc_swamp_dungeon_red", "Step in the bottom gold tile to get the treasure ");

        dialogues.put("npc_swamp_dungeon_white", "I totally agree with Blue ");

        dialogues.put("npc_swamp_dungeon_yellow", "Step in the middle gold tile to get the treasure ");

        dialogues.put("volcano", "Welcome adventure to Volcano Zone , to complete this stage, " +
                "\nyou will have to locate the orb of lava. The monsters here are vulnerable to earth");

        dialogues.put("npc_lava_maze", "Welcome (N)ew comer to the legendary lava maze! Those" +
                " who know their directions will know the (W)ay home. \n Those" +
                " who enjoy a risk will find a reward in one corner of this" +
                " deadly maze. \n \n Good luck Adve(n)tur(e)r & don't forget" +
                " to avoid the lava!");

        dialogues.put("tundra", "Welcome adventure to Tundra Zone , to complete this stage, " +
                "\nyou will have to locate the orb of ice. The monsters here are vulnerable to fire");

        dialogues.put("desert", "Welcome adventure to Desert Zone , to complete this stage, " +
                "\nyou will have to locate the orb of sand. The monsters here are vulnerable to water");

        dialogues.put("swampy", "You have slain Siendiadut the swamp dragon and gained the element of " +
                "\nearth, use it to crush your foes.");

        dialogues.put("volcy", "You have slain Chusulth the volcano dragon and gained the element of " +
                "\nfire, use it to burn your foes.");

        dialogues.put("tundy", "You have slain Diokiedes the tundra dragon and gained the element of water, " +
                "\nuse it to drown your foes.");

        dialogues.put("desy", "You have slain Doavnaen the desert dragon and gained the element of air, " +
                "\nuse it to blow down your foes.");

        dialogues.put("roar", "Roar!!!");

        dialogues.put("grr", "GRRRRR");

        dialogues.put("orc", "WAAAAR");

        dialogues.put("gob", "Shinies");

        dialogues.put("died", "Too bad, you died, would you like to restart from " +
                "\nyour previous checkpoint or start anew?");

        dialogues.put("finish", "Congratulations hero, you have collected all the " +
                "\norbs and restored peace to the world.");

        return dialogues.get(target);
    }

    /**
     * Updates the player peon's over time methods, such as tasks and cooldowns.
     *
     * @param i current game tick.
     */
    @Override
    public void onTick(long i) {
        if (combatTask != null && --duration > 0) {
            currentState = State.ATTACK_MELEE;
            duration = 12;
        } else if (getMovementTask() != null) {
            currentState = State.WALK;
        } else {
            currentState = State.IDLE;
        }

        // Update skills
        for (AbstractSkill s : wizardSkills) {
            if (s != null) {
                s.onTick(i);
            }
        }
        if (mechSkill != null) {
            mechSkill.onTick(i);
        }

        if (this.getCurrentHealth() == 0){
            death();
        }
        // Update tasks and effects
        super.onTick(i);
    }

    /**
     * Get the texture region of current animation keyframe (this will be called in Renderer3D.java).
     *
     * @param delta the interval of the ticks
     * @return the current texture region in animation
     */
    public TextureRegion getFrame(float delta) {
        TextureRegion region;
        // Get the animation frame based on the current state
        switch (currentState) {
            case ATTACK_RANGE:
                region = playerRangeAttack.getKeyFrame(stateTimer);
                break;
            case ATTACK_MELEE:
                region = playerMeleeAttack.getKeyFrame(stateTimer);
                break;
            case INCAPACITATED:
                region = playerSpin.getKeyFrame(stateTimer);
                break;
            case WALK:
                if (stateTimer >= playerWalk.getAnimationDuration()) {
                    stateTimer = 0;
                }
                region = playerWalk.getKeyFrame(stateTimer);
                break;
            case IDLE:
            default:
                region = playerIdle.getKeyFrame(stateTimer);
                break;
        }
        // Render the correct direction of the texture
        if ((getMovingDirection() == MovementTask.Direction.LEFT ||
                facingDirection == MovementTask.Direction.LEFT) && !region.isFlipX()) {
            region.flip(true, false);
            facingDirection = MovementTask.Direction.LEFT;
        } else if ((getMovingDirection() == MovementTask.Direction.RIGHT ||
                facingDirection == MovementTask.Direction.RIGHT) && region.isFlipX()) {
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
     * @param pointer not used
     * @param button  the button which was pressed
     */
    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        if (button == Input.Buttons.LEFT) {
            try {
                // Set combat task
                if (clickedPosition[0] != getCol() || clickedPosition[1] != getRow()) {
                    AbstractSkill wizardSkill = wizardSkills.get(activeWizardSkill);
                    if (wizardSkill.getCooldownRemaining() <= 0) {
                        this.setCombatTask(wizardSkill.getNewSkillTask(clickedPosition[0], clickedPosition[1]));
                    }
                }
            } catch (SkillOnCooldownException e) {
                // Won't occur because I'm handling it.
                e.printStackTrace();
            }
        } else if (button == Input.Buttons.RIGHT) {
            try {
                if (mechSkill.getCooldownRemaining() <= 0) {
                    this.setCombatTask(mechSkill.getNewSkillTask(clickedPosition[0], clickedPosition[1]));
                }
            } catch (SkillOnCooldownException e) {
                // Won't occur because I'm handling it
                e.printStackTrace();
            }
        }

        // set the facing direction when attacking
        if (getMovementTask() == null) {
            if (clickedPosition[0] < this.getCol()) {
                facingDirection = MovementTask.Direction.LEFT;
            } else {
                facingDirection = MovementTask.Direction.RIGHT;
            }
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
                keycode == Input.Keys.NUM_3 || keycode == Input.Keys.NUM_4 ||
                keycode == Input.Keys.NUM_5) {
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
     * Update the list of player skills usable to the player.
     */
    public void updatePlayerSkills() {
        wizardSkills.clear();
        getPlayerSkills();
    }

    /**
     * Gets player skills from the PlayerManager.
     */
    private void getPlayerSkills() {
        // Get player skills
        PlayerManager playerManager = GameManager.getManagerFromInstance(PlayerManager.class);
        if (playerManager != null) {
            List<WizardSkills> wizardSkillList = playerManager.getCurrentWizardSkills();
            KnightSkills knightSkill = playerManager.getCurrentKnightSkill();
            for (WizardSkills skill : wizardSkillList) {
                wizardSkills.add(PlayerSkills.getNewWizardSkill(this, skill));
            }
            mechSkill = PlayerSkills.getNewKnightSkill(this, knightSkill);
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
                this.movementStack.push(MovementTask.Direction.UP);
                this.activeDirectionKeys[1] = true;
                break;
            case Input.Keys.S:
                this.movementStack.push(MovementTask.Direction.DOWN);
                this.activeDirectionKeys[2] = true;
                break;
            case Input.Keys.A:
                this.movementStack.push(MovementTask.Direction.LEFT);
                this.activeDirectionKeys[3] = true;
                break;
            case Input.Keys.D:
                this.movementStack.push(MovementTask.Direction.RIGHT);
                this.activeDirectionKeys[4] = true;
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
                this.activeDirectionKeys[1] = false;
                break;
            case Input.Keys.S:
                this.activeDirectionKeys[2] = false;
                break;
            case Input.Keys.A:
                this.activeDirectionKeys[3] = false;
                break;
            case Input.Keys.D:
                this.activeDirectionKeys[4] = false;
                break;
            default:
                break;
        }
    }

    public boolean isDirectionKeyActive(MovementTask.Direction direction) {
        switch (direction) {
            case NONE:
                return activeDirectionKeys[0];
            case UP:
                return activeDirectionKeys[1];
            case DOWN:
                return activeDirectionKeys[2];
            case LEFT:
                return activeDirectionKeys[3];
            case RIGHT:
                return activeDirectionKeys[4];
            default:
                return false;
        }
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

    public static void addBuffDamageTotal(int damage){ buffDamageTotal += damage;}

    public static void setBuffDamageTotal(int damage){buffDamageTotal = damage;}

    public static void addBuffArmourTotal(int armour){ buffArmourTotal += armour;}

    public static void setBuffArmourTotal(int armour){buffArmourTotal = armour;}

    public static void setCooldownBuff(boolean val){ isCoolDownBuffActive = val; }

    public static boolean isCoolDownBuffActive(){ return isCoolDownBuffActive; }

    public List<AbstractSkill> getWizardSkills() {
        return this.wizardSkills;
    }

    public int getActiveWizardSkill() {
        return this.activeWizardSkill;
    }

    public Stack<MovementTask.Direction> getMovementStack() {
        return this.movementStack;
    }

    public AbstractSkill getMechSkill() {
        return this.mechSkill;
    }

    /**
     * Triggered when health goes below zero. Removes entity
     * from world.
     */
    @Override
    public void death() {
        resetAllSkillCoolDownToOriginal();
        setBuffDamageTotal(0);
        setBuffArmourTotal(0);
        setCooldownBuff(false);
        super.death();
        GameManager.getManagerFromInstance(SoundManager.class).stopBossMusic();
        GameManager.get().getWorld().removeEntity(this);
        GameManager.get().getWorld().disposeEntity(this.getEntityID());
        GameManager.gameOver();
    }

    /**
     * Resets all player skills' cooldown to their original values
     */
    public void resetAllSkillCoolDownToOriginal(){
        FireballSkill.setMaxCooldown(20);
        FireBombSkill.setMaxCoolDown(160);
        HealSkill.setMaxCoolDown(200);
        IceballSkill.setMaxCooldown(50);
        IceBreathSkill.setMaxCooldown(20);
        SandTornadoSkill.setMaxCoolDown(30);
        ScorpionStingSkill.setMaxCoolDown(50);
        WaterShieldSkill.setMaxCoolDown(200);
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

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
