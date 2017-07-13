package mobile;

/**
 * Created by bobvv on 7/10/17.
 */
public enum Direction
{
    SOUTH_EAST("SOUTH_EAST", 1, -1,7),
    SOUTH_WEST("SOUTH_WEST", -1, -1,1),
    NORTH_EAST("NORTH_EAST", 1, 1,5),
    NORTH_WEST("NORTH_WEST", -1, 1,3),
    NORTH("NORTH", 0, 1,4),
    WEST("WEST", -1, 0,2),
    EAST("EAST", 1, 0,6),
    SOUTH("SOUTH", 0, -1,0),
    ZERO("ZERO",0,0,8);

    private String direction;
    private int xspeed;
    private int yspeed;
    private int num;

    public int getNum() {
        return num;
    }

    Direction(String direction, int x, int y,int num) {

        this.direction = direction;
        this.xspeed = x;
        this.yspeed = y;
        this.num = num;
    }

    public int getXspeed()
    {
        return xspeed;
    }

    public int getYspeed()
    {
        return yspeed;
    }
}
