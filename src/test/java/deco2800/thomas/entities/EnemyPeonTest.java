package deco2800.thomas.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
public class EnemyPeonTest {

    @Test
    public void testEnemyTexture() {
        PlayerPeon player = new PlayerPeon(0,0,1);
        EnemyPeon enemy =  new Orc(1, 1, 100, player);
        assertEquals(enemy.getTexture(), "spacman_blue");
    }

    @Test
    public void testEnemyName() {
        PlayerPeon player = new PlayerPeon(0,0,1);
        EnemyPeon enemy =  new Orc(1, 1, 100, player);

        assertEquals(enemy.getObjectName(), "Orc");
    }

    //Need some formal way of testing that it approaches the player? For now
    // ingame testing has confirmed it
}
