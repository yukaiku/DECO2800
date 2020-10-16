package deco2800.thomas.util;

import deco2800.thomas.BaseGDXTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CubeTest extends BaseGDXTest {
    Cube cube;
    @Before
    public void setUp(){
        cube = new Cube(0,0,0);
        assertEquals(0,cube.getX(),0D);
        assertEquals(0,cube.getY(),0D);
        assertEquals(0,cube.getZ(),0D);
    }

    @Test
    public void testCube(){
        cube.setX(1);
        cube.setY(1);
        cube.setZ(1);
        assertEquals(1,cube.getX(),0D);
        assertEquals(1,cube.getY(),0D);
        assertEquals(1,cube.getZ(),0D);
        Cube cube2 = cube.oddqToCube(2,2);
        assertEquals(2,cube2.getX(),0D);
        assertEquals(1,cube2.getY(),0D);
        assertEquals(-3,cube2.getZ(),0D);



    }

    @After
    public void tearDown(){
        cube = null;
    }
}
