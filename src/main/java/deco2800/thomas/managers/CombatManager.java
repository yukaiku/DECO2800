package deco2800.thomas.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import deco2800.thomas.entities.CombatEntity;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.AbstractWorld;

public class CombatManager extends AbstractManager implements TouchDownObserver {
    private AbstractWorld world;

    public CombatManager(AbstractWorld world) {
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
        this.world = world;
    }

    public void removeEntity(CombatEntity entity) {
        world.removeEntity(entity);
        world.deleteEntity(entity.getEntityID());
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
            float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
            SquareVector destination = new SquareVector(clickedPosition[0], clickedPosition[1]);
            Fireball fireball = new Fireball(world.getPlayerEntity().getCol(), world.getPlayerEntity().getRow(), 10, 0.15f, destination);
            //fireball.setCombatTask(new MovementTask(fireball, new SquareVector(clickedPosition[0], clickedPosition[1])));
            world.addEntity(fireball);

        }
    }
}
