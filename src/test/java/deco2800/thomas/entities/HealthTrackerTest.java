package deco2800.thomas.entities;

import deco2800.thomas.BaseGDXTest;
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
    public void getMaxHealthValue1() {
        HealthTracker health = new HealthTracker(150);
        int value = health.getMaxHealthValue();
        Assert.assertEquals(value,150);
    }

    @Test
    public void getMaxHealthValue2() {
        HealthTracker health = new HealthTracker(200);
        int value = health.getMaxHealthValue();
        Assert.assertNotEquals(value,150);
    }

    @Test
    public void setMaxHealthValue() {
        HealthTracker health = new HealthTracker(100);
        health.setMaxHealthValue(50);
        int value = health.getMaxHealthValue();
        Assert.assertEquals(value,50);
    }

    @Test
    public void setMaxHealthValue1() {
        HealthTracker health = new HealthTracker(100);
        health.setMaxHealthValue(100);
        int value = health.getMaxHealthValue();
        Assert.assertEquals(value,100);
    }

    @Test
    public void setMaxHealthValue2() {
        HealthTracker health = new HealthTracker(100);
        health.setMaxHealthValue(0);
        int value = health.getMaxHealthValue();
        Assert.assertEquals(value,0);
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
    public void getCurrentHealthValue1() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(90);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,90);
    }

    @Test
    public void getCurrentHealthValue2() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(-100);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,0);
    }

    @Test
    public void setCurrentHealthValue() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(30);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,30);
    }

    @Test
    public void setCurrentHealthValue1() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(110);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,100);
    }

    @Test
    public void setCurrentHealthValue2() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(0);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,0);
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
    public void reduceHealth1() {
        HealthTracker health = new HealthTracker(100);
        health.reduceHealth(20);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,80);
    }

    @Test
    public void reduceHealth2() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(110);
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
    public void regenerateHealth1() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(-30);
        health.regenerateHealth(20);
        int value = health.getCurrentHealthValue();
        Assert.assertEquals(value,20);
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
    public void isDead1() {
        HealthTracker health = new HealthTracker(100);
        health.setCurrentHealthValue(-10);
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
