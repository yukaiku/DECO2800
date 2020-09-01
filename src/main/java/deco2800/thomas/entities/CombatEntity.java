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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void onTick(long i) {

    }
}
