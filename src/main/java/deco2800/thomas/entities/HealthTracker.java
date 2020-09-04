package deco2800.thomas.entities;

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
    private int maxHealthValue;
    /** Represents the current health value of a character  **/
    private int currentHealthValue;
    public HealthTracker(int maxHealthValue)  {
        if (maxHealthValue > 0) {
            this.maxHealthValue = maxHealthValue;
            this.currentHealthValue = this.maxHealthValue;
        }
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
        if (healthValue > 0) {
            this.maxHealthValue = healthValue;
        }
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
    public void setCurrentHealthValue(int healthValue)  {
        if (healthValue >=0 && healthValue <= maxHealthValue) {
            this.currentHealthValue = healthValue;
        }
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
        if (damage >= 0) {
            this.currentHealthValue -= damage;
        }
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
        if (regen >= 0) {
            this.currentHealthValue += regen;
        }
    }
}

