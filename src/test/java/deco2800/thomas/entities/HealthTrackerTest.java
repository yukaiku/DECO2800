package deco2800.thomas.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HealthTrackerTest {

    @Test
    public void testgetMaxHealthValue() {
        HealthTracker health = new HealthTracker(100);
        int value = health.getMaxHealthValue();
        Assert.assertEquals(value,100);
    }

    @Test
    public void testsetMaxHealthValue() {
        HealthTracker health = new HealthTracker(100);
        health.setMaxHealthValue(50);
        int value = health.getMaxHealthValue();
        Assert.assertEquals(value,50);
    }
    @Test
    public void testsetMaxHealthValueWithNegative() {
        HealthTracker health = new HealthTracker(100);
        health.setMaxHealthValue(-10);
        int value = health.getMaxHealthValue();
        Assert.assertEquals(value,0);
    }
    @Test
    public void getCurrentHealthValue() {
    }

    @Test
    public void setCurrentHealthValue() {
    }

    @Test
    public void reduceHealth() {
    }

    @Test
    public void regenerateHealth() {
    }

    @Test
    public void isDead() {
    }

    @Test
    public void recharge() {
    }
}
