package mainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static java.lang.Math.max;
import static java.lang.StrictMath.min;

/**
 * Created by sarb on 7/11/17.
 */
public class MouseAbilities implements MyMouseListener
{
    private int startX,startY;
    private Canvas canvas;

    public MouseAbilities(Canvas canvas)
    {
        this.canvas = canvas;
    }


    @Override
    public void mousePressed(MouseEvent e)
    {
        if (SwingUtilities.isLeftMouseButton(e))
        {
            canvas.checkMobiles(e);
        }
        if (SwingUtilities.isRightMouseButton(e))
            canvas.setTarget(e);
        startX = e.getX();
        startY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        int x1 = min(startX,e.getX());
        int y1 = min(startY,e.getY());
        int x2 = max(startX,e.getX());
        int y2 = max(startY,e.getY());
        Rectangle rectangle = new Rectangle(x1,y1,x2-x1,y2-y1);
        canvas.setDraggedRect(rectangle);
    }

    @Override
    public void MouseReleased(MouseEvent e)
    {
        canvas.checkRect();
        canvas.setDraggedRect(null);
//        canvas.repaintCanvas();
    }
}
