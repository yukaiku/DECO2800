package deco2800.thomas.entities;

public abstract class CombatEntity extends AbstractEntity {
    private boolean projectile;
    private int damage;
    private float speed;

    public CombatEntity() {
        super();
    }

    public CombatEntity(float col, float row, int renderOrder, int damage) {
        super(col, row, renderOrder);
        this.damage = damage;
    }

    public CombatEntity(float col, float row, int renderOrder, int damage, float speed) {
        super(col, row, renderOrder);
        this.damage = damage;
        this.speed = speed;
    }

    public void applyDamange (Peon entity){
        // Used to apply damage to an entity
    }

    @Override
    public void onTick(long i) {
        // Needs to check if colliding with enemy. If it is then deal damage.
    }
}
