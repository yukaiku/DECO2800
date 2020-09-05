package deco2800.thomas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.entities.attacks.Sword;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;

public class CombatManager extends AbstractManager implements TouchDownObserver, KeyDownObserver {
    private AbstractWorld world;

    public CombatManager(AbstractWorld world) {
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
        this.world = world;
    }

    public void removeEntity(CombatEntity entity) {
        world.removeEntity(entity);
        world.disposeEntity(entity.getEntityID());
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
        SquareVector destination = new SquareVector(clickedPosition[0], clickedPosition[1]);

        if (button == Input.Buttons.LEFT) {

            // Spawn a fireball
            Fireball.spawn(world.getPlayerEntity().getCol(), world.getPlayerEntity().getRow(),
                    clickedPosition[0], clickedPosition[1], 10, 0.5f, 60,
                    world.getPlayerEntity().getFaction());
        }
        if (button == Input.Buttons.RIGHT) {
            float direction[] = new float[] {
                    destination.getCol() - world.getPlayerEntity().getCol(),
                    destination.getRow() - world.getPlayerEntity().getRow()
            };

            if (Math.abs(direction[0]) > Math.abs(direction[1])) {
                // Then create melee entity on left or right of player
                if (direction[0] > 0) {
                    // Create melee entity on right of player
                    Sword.spawn(MovementTask.Direction.RIGHT,
                            30, 3, world.getPlayerEntity().getFaction());
                } else if (direction[0] < 0) {
                    // Create melee entity on left of player
                    Sword.spawn(MovementTask.Direction.LEFT,
                            30, 3, world.getPlayerEntity().getFaction());
                }
            } else if (Math.abs(direction[0]) < Math.abs(direction[1])) {
                // Create melee entity above or below player
                if (direction[1] > 0) {
                    // Create melee entity above player
                    Sword.spawn(MovementTask.Direction.UP,
                            30, 3, world.getPlayerEntity().getFaction());
                } else if (direction[1] < 0) {
                    // Create melee entity below player
                    Sword.spawn(MovementTask.Direction.DOWN,  30, 3, world.getPlayerEntity().getFaction());
                }
            }
        }
    }



    private void calculateDestination(AbstractWorld world, float[] clickedPosition) {
        world.getPlayerEntity().getCol();
        world.getPlayerEntity().getRow();
    }

    @Override
    public void notifyKeyDown(int keycode) {
        if (keycode == Input.Keys.UP || keycode == Input.Keys.DOWN ||
                keycode == Input.Keys.LEFT || keycode == Input.Keys.RIGHT) {
            this.generateFireball(keycode);
        }
    }

    private void generateFireball(int keycode) {
    
    }
}
