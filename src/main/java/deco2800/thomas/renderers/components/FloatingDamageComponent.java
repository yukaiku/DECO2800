package deco2800.thomas.renderers.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.Tickable;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This component renders floating damage numbers above the heads of
 * entities that have been hit by damage, or received a heal.
 */
public class FloatingDamageComponent extends OverlayComponent implements Tickable {
    /* List of currently active floating damage instances. */
    private List<FloatingDamageText> floatingDamageInstances;
    private List<FloatingDamageText> floatingDamageInstancesToRemove;

    /**
     * Creates an instance of the FloatingDamageComponent.
     */
    public FloatingDamageComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);
        floatingDamageInstances = new CopyOnWriteArrayList<>();
        floatingDamageInstancesToRemove = new CopyOnWriteArrayList<>();
    }

    /**
     * Updates all floating damage text instances currently managed.
     * @param i Ticks since game start.
     */
    public void onTick(long i) {
        // Update all instances in list
        for (FloatingDamageText text : floatingDamageInstances) {
            text.onTick(i);
            if (text.getLife() <= 0) {
                remove(text);
            }
        }

        // Remove any instances queued for removal
        for (FloatingDamageText text : floatingDamageInstancesToRemove) {
            floatingDamageInstances.remove(text);
        }
        floatingDamageInstancesToRemove.clear();
    }

    /**
     * Queues a floating damage text instance to be removed from the list of
     * currently managed instances.
     * @param text FloatingDamageText to remove.
     */
    public void remove(FloatingDamageText text) {
        floatingDamageInstancesToRemove.add(text);
    }

    /**
     * Render this component.
     * @param batch the sprite batch to draw into
     */
    @Override
    public void render(SpriteBatch batch) {

    }
}
