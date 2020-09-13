package deco2800.thomas.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Animatable {

    /**
     * Get the texture region of current animation keyframe (this will be called in Renderer3D.java).
     * @param delta the interval of the ticks
     * @return the current texture region in animation
     */
    TextureRegion getFrame(float delta);
}
