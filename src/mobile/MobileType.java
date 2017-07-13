package mobile;


/**
 * Created by bobvv on 7/10/17.
 */
public enum MobileType
{
    SOLDIER("SOLDIER",5, 0.2, 2),
    Normal("NORMAL",5, 0.2, 2);

    private String humanName;
    private int speed;
    private double size;
    private double height;

    MobileType(String humanName, int speed, double size, double height)
    {
        this.speed = speed;
        this.humanName = humanName;
        this.size = size;
        this.height = height;
    }

    public double getSize()
    {
        return size;
    }

    public double getHeight()
    {
        return height;
    }

    public int getSpeed()
    {
        return speed;
    }
}
