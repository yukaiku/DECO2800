package deco2800.thomas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.ApplyDamageOnCollisionTask;
import deco2800.thomas.tasks.DirectProjectileMovementTask;
import deco2800.thomas.tasks.RangedAttackTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;

public class CombatManager extends AbstractManager implements TouchDownObserver, KeyDownObserver {
    public CombatManager() {
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
    }

    public void removeEntity(CombatEntity entity) {
        GameManager.get().getWorld().removeEntity(entity);
        GameManager.get().getWorld().disposeEntity(entity.getEntityID());
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {

            float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
            float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
            SquareVector destination = new SquareVector(clickedPosition[0], clickedPosition[1]);

            // Spawn a fireball
            AbstractWorld world = GameManager.get().getWorld();
            Fireball.spawn(world.getPlayerEntity().getCol(), world.getPlayerEntity().getRow(),
                    clickedPosition[0], clickedPosition[1], 10, 0.5f, 60,
                    GameManager.get().getWorld().getPlayerEntity().getFaction());
        }
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
