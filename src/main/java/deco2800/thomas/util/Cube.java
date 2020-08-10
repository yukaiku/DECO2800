package deco2800.thomas.util;

public class Cube {
    public float x = 0;
    public float y = 0;
    public float z = 0;

    public Cube(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static float cubeDistance(Cube a, Cube b) {
        return distance(a.x, a.y, a.z, b.x, b.y, b.z);
    }
    
    public static float cubeDistanceToZero(Cube a) {
        return distance(a.x, a.y, a.z, 0f, 0f, 0f);
    }
    
    private static float distance(float ax, float ay, float az, float bx, float by, float bz) {
        return (Math.abs(ax - bx) + Math.abs(ay - by) + Math.abs(az - bz)) / 2.0f;
    }

    static public Cube oddqToCube(float q, float r) {
        float x = q;
        float y = r - (q - Math.abs(q % 2))/2f;
        float z = -x-y;
        return new Cube(x, y, z);
    }
    
    public String toString() {
    	return "[" + x + " " + y + " " + z + "]";
    }
}
