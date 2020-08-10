package deco2800.thomas.entities;

import java.util.Comparator;


public class EntityCompare implements Comparator<AbstractEntity> {
    @Override
    public int compare(AbstractEntity e1, AbstractEntity e2) {
        // write comparison logic here like below , it's just a sample
        return e1.getEntityID() - e2.getEntityID();
    }
}
