package deco2800.thomas.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HealthTrackerTest {

    @Test
    public void getMaxHealthValue() {
        HealthTracker health = new HealthTracker(100);
        int value = health.getMaxHealthValue();
        Assert.assertEquals(value,100);
    }

    @Test
    public void setMaxHealthValue() {
        HealthTracker health = new HealthTracker(100);
        health.setMaxHealthValue(50);
        int value = health.getMaxHealthValue();
        Assert.assertEquals(value,50);
    }
    @Test
    public void setMaxHealthValueWithNegative() {
        HealthTracker health = new HealthTracker(100);
        health.setMaxHealthValue(-10);
        int value = health.getMaxHealthValue();
        Assert.assertEquals(value,0);
    }
    @Test
    public void getCurrentHealthValue() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(100);
        health.reduceHealth(20);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,80);
    }

    @Test
    public void setCurrentHealthValue() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(30);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,30);
    }

    @Test
    public void setCurrentHealthValueByNegative() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(-30);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,0);
    }

    @Test
    public void setCurrentHealthValueByValueLargerThanMax() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(130);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,100);
    }

    @Test
    public void reduceHealth() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(100);
        health.reduceHealth(20);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,80);
    }

    @Test
    public void reduceHealthByValueMoreThanHealth() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(100);
        health.reduceHealth(120);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,0);
    }

    @Test
    public void regenerateHealth() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(50);
        health.regenerateHealth(20);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,70);
    }

    @Test
    public void regenerateHealthByValueMoreThanHealth() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(50);
        health.regenerateHealth(90);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,100);
    }

    @Test
    public void isDead() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(0);
        boolean value = health.isDead();
        Assert.assertEquals(value,true);
    }

    @Test
    public void isDeadFalse() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(20);
        boolean value = health.isDead();
        Assert.assertEquals(value,false);
    }
}
