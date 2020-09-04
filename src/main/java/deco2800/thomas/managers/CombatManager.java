package deco2800.thomas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.RangedAttackTask;
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
        if (button == Input.Buttons.LEFT) {

            float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
            float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
            SquareVector destination = new SquareVector(clickedPosition[0], clickedPosition[1]);

            Fireball fireball = new Fireball(world.getPlayerEntity().getCol(), world.getPlayerEntity().getRow(), 10, 0.15f, 5);
            fireball.setTask(new RangedAttackTask(fireball, destination));

            world.addEntity(fireball);
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
        switch (keycode) {
            case Input.Keys.UP:
                Fireball fireballUp = new Fireball(world.getPlayerEntity().getCol(), world.getPlayerEntity().getRow() + 1, 50, 0.4f, 5);

                SquareVector destinationUp = new SquareVector(world.getPlayerEntity().getCol(), world.getPlayerEntity().getRow() + fireballUp.getRange());
                fireballUp.setTask(new RangedAttackTask(fireballUp, destinationUp));
                world.addEntity(fireballUp);
                break;
            case Input.Keys.DOWN:
                Fireball fireballDown = new Fireball(world.getPlayerEntity().getCol(), world.getPlayerEntity().getRow() - 1, 50, 0.4f, 5);
                SquareVector destinationDown = new SquareVector(world.getPlayerEntity().getCol(), world.getPlayerEntity().getRow() - fireballDown.getRange());
                fireballDown.setTask(new RangedAttackTask(fireballDown, destinationDown));
                world.addEntity(fireballDown);
                break;
            case Input.Keys.LEFT:
                Fireball fireballLeft = new Fireball(world.getPlayerEntity().getCol() - 1, world.getPlayerEntity().getRow(), 50, 0.4f, 5);
                SquareVector destinationLeft = new SquareVector(world.getPlayerEntity().getCol() - fireballLeft.getRange(), world.getPlayerEntity().getRow());
                fireballLeft.setTask(new RangedAttackTask(fireballLeft,destinationLeft));
                world.addEntity(fireballLeft);
                return;
            case Input.Keys.RIGHT:
                Fireball fireballRight = new Fireball(world.getPlayerEntity().getCol() + 1, world.getPlayerEntity().getRow(), 50, 0.4f, 5);
                SquareVector destinationRight = new SquareVector(world.getPlayerEntity().getCol() + fireballRight.getRange(), world.getPlayerEntity().getRow());
                fireballRight.setTask(new RangedAttackTask(fireballRight,destinationRight));
                world.addEntity(fireballRight);
                return;
            default:
                return;
        }
    }
}