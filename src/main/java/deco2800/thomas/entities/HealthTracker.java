package deco2800.thomas.entities;

import com.google.gson.annotations.Expose;

/**
 * Contains the current and maximum health value for a given character
 * (player, enemy or NPC) as well as methods for tracking and controlling the
 * health value.
 * <p>
 * All characters should be initialised with a HealthTracker object.
 */
public class HealthTracker {
    // Class Fields
    /** Represents the maximum health value of a character **/
    @Expose
    private int maxHealthValue;
    /** Represents the current health value of a character  **/
    @Expose
    private int currentHealthValue;
    /** Represents whether or not the entity has reached 0 health  **/
    @Expose
    private boolean isDead;

    public HealthTracker(int maxHealthValue) {

        this.maxHealthValue = maxHealthValue;
        this.currentHealthValue = this.maxHealthValue;
        this.isDead = false;
    }

    /**
     * Returns the maximumHealthValue of the character with this HealthTracker.
     *
     * @return maximumHealthValue
     */
    public int getMaxHealthValue() {
        return this.maxHealthValue;
    }

    /**
     * Sets the new maximumHealthValue.
     * <P>
     * Can not be set below 0.
     *
     * @param healthValue new value to which maximumHealthValue is set.
     */
    public void setMaxHealthValue(int healthValue) {
        this.maxHealthValue = Math.max(healthValue, 0);
    }

    /**
     * Returns the currentHealthValue of the character with this HealthTracker.
     *
     * @return currentHealthValue
     */
    public int getCurrentHealthValue() {
        return this.currentHealthValue;
    }

    /**
     * Sets the currentHealthValue to any value between 0 and the
     * maximumHealthValue.
     * <p>
     * If trying to set currentHealthValue to bellow 0,
     * it will instead be set to 0. If trying to set currentHealthValue to
     * above the maximumHealthValue, it will instead be set to the
     * maximumHealthValue.
     *
     * @param healthValue new value to which currentHealthValue is set.
     */
    public void setCurrentHealthValue(int healthValue) {
        if(healthValue <= 0) {
            this.currentHealthValue = 0;
        } else
            this.currentHealthValue = Math.min(healthValue, maxHealthValue);
    }

    /**
     * Reduces character's currentHealthValue when damage is done.
     * <p>
     * Should not reduce currentHealthValue bellow 0. Takes appropriate
     * action when health reaches 0.
     *
     * @param damage the integer amount by which currentHealValue is to be
     *               reduced.
     */

    public void reduceHealth(int damage) {

        int newHealth = this.currentHealthValue;
        newHealth -= damage;
        this.currentHealthValue = Math.max(newHealth, 0);
    }

    /**
     * Increases character's currentHealthValue when health is regenerated.
     * <p>
     * Should not increase currentHealthValue above a character's
     * maximumHealthValue.
     *
     * @param regen the integer amount by which currentHealthValue is to be
     *              increased.
     */
    public void regenerateHealth(int regen) {
        int newHealth = this.currentHealthValue;
        newHealth += regen;
        this.currentHealthValue = Math.min(newHealth, maxHealthValue);
    }

    /**
     * Internally checks is currentHealthValue is 0. If it is, isDead boolean
     * is set to true, other wise, set to false.
     * <p>
     * After this check isDead boolean is returned
     *
     * @return isDead
     */

    public boolean isDead () {
        this.isDead = (currentHealthValue == 0);
        return isDead;
    }
}

