package deco2800.thomas.tasks;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.attacks.RangedEntity;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.managers.AttackPathFindingService;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import deco2800.thomas.worlds.Tile;

import java.util.List;

public class RangedAttackTask extends AbstractTask{

    RangedEntity entity;
    SquareVector destination;
    private AbstractWorld world;

    private boolean computingPath = false;

    private boolean taskAlive = true;
    private boolean taskComplete = false;

    private List<Tile> path;

    public RangedAttackTask(RangedEntity entity, SquareVector destination) {
        super(entity);

        this.entity = entity;
        this.destination = destination;
        this.taskComplete = false;
        world = GameManager.get().getWorld();
    }

    @Override
    public boolean isComplete() { return taskComplete; }

    @Override
    public boolean isAlive() {
        return taskAlive;
    }

    @Override
    public void onTick(long tick) {
        autoMovement();

        List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(entity.getBounds());

        if (!collidingEntities.isEmpty()) {
            for (AbstractEntity e : collidingEntities) {
                if (e instanceof EnemyPeon) {
                    applyDamage(((EnemyPeon) e));
                }
                if (e instanceof StaticEntity) {
                    this.taskComplete = true;
                }
            }
        }
    }

    private void applyDamage (EnemyPeon e) {
        e.reduceHealth(entity.getDamage());
        this.taskComplete = true;
        if (e.isDead()) {
            e.death();
        }
    }

    private void autoMovement() {
        if (path != null) {
            // We have a path.
            if (path.isEmpty()) {
                taskComplete = true;
            } else {
                entity.moveTowards(path.get(0).getCoordinates());
                // This is a bit of a hack.
                if (entity.getPosition().isCloseEnoughToBeTheSame(path.get(0).getCoordinates())) {
                    path.remove(0);
                }
            }
        } else if (computingPath) {
            // Change sprite to show waiting??

        } else {
            // Ask for a path.
            computingPath = true;
            GameManager.get().getManager(AttackPathFindingService.class).addPath(this);
        }
    }

    public void setPath(List<Tile> path) {
        if (path == null) {
            taskAlive = false;
        }
        this.path = path;
        computingPath = false;
    }

    public List<Tile> getPath() {
        return path;
    }
}
