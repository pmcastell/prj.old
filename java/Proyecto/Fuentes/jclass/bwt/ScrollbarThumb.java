// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScrollbarThumb.java

package jclass.bwt;

import java.awt.*;
import javax.swing.JComponent;
import jclass.base.*;

// Referenced classes of package jclass.bwt:
//            JCComponent, BWTUtil

class ScrollbarThumb extends JCComponent
{

    ScrollbarThumb(Component component)
    {
        visible = true;
        sb = component;
        super.highlight = 0;
        super.border_style = 4;
    }

    public void addNotify()
    {
    }

    protected void drawBorder(Graphics g)
    {
        super.drawBorder(g);
        if(getBorder() == null)
        {
            g.setColor(Color.black);
            g.drawRect(0, 0, size().width - 1, size().height - 1);
        }
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void repaint(int i, int j, int k, int l)
    {
        if(!isVisible() || k <= 0 || l <= 0)
        {
            return;
        } else
        {
            Point point = BWTUtil.translateToParent((Container)sb, this, i, j);
            sb.repaint(point.x, point.y, k, l);
            return;
        }
    }

    public void setBounds(int i, int j, int k, int l)
    {
        if(k < 0 || l < 0)
            return;
        boolean flag = size().width != k || size().height != l;
        if(!flag && location().x == i && location().y == j)
            return;
        Rectangle rectangle = bounds();
        super.do_repaint = false;
        super.setBounds(i, j, k, l);
        super.do_repaint = true;
        Rectangle rectangle1 = bounds();
        if(!rectangle1.intersects(rectangle))
        {
            sb.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            repaint();
        } else
        {
            Rectangle rectangle2 = rectangle1.union(rectangle);
            sb.repaint(rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
        }
    }

    public void show(boolean flag)
    {
        if(visible != flag)
        {
            visible = flag;
            Rectangle rectangle = bounds();
            sb.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }

    Component sb;
    boolean visible;
}
