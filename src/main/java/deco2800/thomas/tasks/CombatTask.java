package deco2800.thomas.tasks;

import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.entities.CombatEntity;
import deco2800.thomas.entities.StaticEntity;
import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

public class CombatTask extends AbstractTask{

    private CombatEntity entity;
    private SquareVector position;
    private AbstractWorld world;
    private boolean taskAlive = true;
    private boolean taskComplete = false;

    public CombatTask(CombatEntity entity) {
        super(entity);
        this.entity = entity;
        this.position = position;
        world = GameManager.get().getWorld();
    }

    @Override
    public boolean isComplete() {
        return taskComplete;
    }

    @Override
    public boolean isAlive() {
        return taskAlive;
    }

    @Override
    public void onTick(long tick) {
        List<AbstractEntity> collidingEntities = world.getEntitiesInBounds(entity.getBounds());
        /*
        System.out.printf("PlayerPeon Width: %d\n", GameManager.getManagerFromInstance(TextureManager.class).getTexture("spacman_ded").getWidth());
        System.out.printf("PlayerPeon Height: %d\n", GameManager.getManagerFromInstance(TextureManager.class).getTexture("spacman_ded").getHeight());
        System.out.printf("Bottom: %f\n",entity.getBounds().getBottom());
        System.out.printf("Left: %f\n", entity.getBounds().getLeft());
        System.out.printf("Right: %f\n", entity.getBounds().getRight());
        System.out.printf("Top: %f\n", entity.getBounds().getTop());
        System.out.printf("Width: %d\n", GameManager.getManagerFromInstance(TextureManager.class).getTexture("projectile").getWidth());
        System.out.printf("Height: %d\n", GameManager.getManagerFromInstance(TextureManager.class).getTexture("projectile").getHeight());
         */

        if (!collidingEntities.isEmpty()) {
            for (AbstractEntity e : collidingEntities) {
                //System.out.println(e.getObjectName());
                if (e.getObjectName() == "Orc") {
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
}
