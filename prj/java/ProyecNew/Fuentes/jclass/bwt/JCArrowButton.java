// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCArrowButton.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.io.PrintStream;
import javax.swing.JComponent;
import jclass.base.*;
import jclass.util.JCEnvironment;

// Referenced classes of package jclass.bwt:
//            JCButton, ArrowButtonConverter, BWTEnum, BWTUtil, 
//            JCComponent

public class JCArrowButton extends JCButton
    implements Runnable
{

    public JCArrowButton()
    {
        this(9, null, null);
    }

    public JCArrowButton(int i)
    {
        this(i, null, null);
    }

    public JCArrowButton(int i, Applet applet, String s)
    {
        super(null, applet, s);
        orientation = 9;
        initial_delay = 0x7fffffff;
        thread_stop = false;
        thread_counter = 0;
        tn = 0;
        bg_set = false;
        if(s == null)
            setName("arrowbutton" + nameCounter++);
        super.highlight = 0;
        super.border = 1;
        if(getClass().getName().equals("jclass.bwt.JCArrowButton"))
            getParameters(applet);
        setOrientation(i);
        if(JCEnvironment.getJavaVersion() >= 110)
            super.traversable = false;
        super.double_buffer = false;
        super.border_style = 4;
    }

    public void armAction(Event event)
    {
        super.armAction(event);
        synchronized(this)
        {
            if(initial_delay != 0x7fffffff)
            {
                if(thread != null && !thread.equals(Thread.currentThread()))
                {
                    thread_stop = true;
                    try
                    {
                        for(; thread != null && thread.isAlive(); thread.join(1000L));
                    }
                    catch(Exception _ex) { }
                    thread = null;
                }
                if(thread == null)
                {
                    thread = new Thread(this, getName() + "-created-thread" + thread_counter++);
                    if(JCEnvironment.getBrowser(this) != 3)
                        thread.setPriority(1);
                    thread_stop = false;
                    thread.start();
                }
            }
        }
    }

    public synchronized void disable()
    {
        if(isEnabled() && thread != null)
            if(thread.equals(Thread.currentThread()))
            {
                thread_stop = true;
                super.disarmAction(null);
            } else
            {
                disarmAction(null);
            }
        super.disable();
    }

    public void disarmAction(Event event)
    {
        synchronized(this)
        {
            if(thread != null)
            {
                thread_stop = true;
                thread = null;
            }
        }
        super.disarmAction(event);
    }

    protected void drawBorder(Graphics g)
    {
        super.drawBorder(g);
        if(getBorder() == null)
        {
            if(border_color != null)
            {
                g.setColor(border_color);
                g.drawRect(0, 0, size().width - 1, size().height - 1);
            }
            if(right_border_color != null)
                Border.drawBottomLines(g, 1, 0, 0, size().width, size().height, right_border_color);
        }
    }

    protected void drawHighlight(Graphics g, boolean flag)
    {
    }

    public Dimension getArrowSize()
    {
        return arrow_size;
    }

    public void getDrawingArea(Rectangle rectangle)
    {
        int i = super.highlight + super.border;
        JCComponent.setBounds(rectangle, i, i, Math.max(0, size().width - 2 * i), Math.max(0, size().height - 2 * i));
    }

    public int getInitialRepeatDelay()
    {
        return initial_delay;
    }

    public int getOrientation()
    {
        return orientation;
    }

    protected void getParameters()
    {
        super.getParameters();
        ArrowButtonConverter.getParams(this);
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        if(BWTUtil.getMouseButton(event) != 1)
            return false;
        if(super.armed)
            return true;
        if(!inside(i, j))
            return false;
        if(!isEnabled())
        {
            return false;
        } else
        {
            super.mouseDown(event, i, j);
            clickAction(event);
            return true;
        }
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
        return super.mouseDrag(event, i, j);
    }

    public boolean mouseMove(Event event, int i, int j)
    {
        return true;
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if(BWTUtil.getMouseButton(event) != 1)
            return false;
        if(super.armed)
            disarmAction(event);
        return BWTUtil.instanceOf(getParent(), "JCScrollbar") ^ true;
    }

    protected void paintComponent(Graphics g)
    {
        if(!bg_set)
            setBackground(getParent().getBackground());
        int ai[] = new int[3];
        int ai1[] = new int[3];
        Rectangle rectangle = getDrawingArea();
        switch(orientation)
        {
        case 10: // '\n'
            ai[0] = arrow_size.width / 2;
            ai1[0] = 0;
            ai[1] = 0;
            ai1[1] = arrow_size.height - 1;
            ai[2] = arrow_size.width - 1;
            ai1[2] = arrow_size.height - 1;
            break;

        case 9: // '\t'
            ai[0] = arrow_size.width / 2;
            ai1[0] = arrow_size.height - 1;
            ai[1] = 0;
            ai[2] = arrow_size.width - 1;
            ai1[1] = ai1[2] = 0;
            break;

        case 0: // '\0'
            ai1[0] = arrow_size.height / 2;
            ai[0] = 0;
            ai1[1] = 0;
            ai1[2] = arrow_size.height - 1;
            ai[1] = ai[2] = arrow_size.width - 1;
            break;

        case 2: // '\002'
            ai[0] = arrow_size.width - 1;
            ai1[0] = arrow_size.height / 2;
            ai[1] = ai[2] = 0;
            ai1[1] = 0;
            ai1[2] = arrow_size.height - 1;
            break;
        }
        int i = (rectangle.width - arrow_size.width) / 2;
        int j = (rectangle.height - arrow_size.height) / 2;
        for(int k = 0; k < 3; k++)
        {
            ai[k] += rectangle.x + i;
            ai1[k] += rectangle.y + j;
        }

        if(super.armed)
            g.translate(super.arm_offset, super.arm_offset);
        if(!isEnabled())
            g.setColor(Color.lightGray.darker());
        if(orientation == 10 || orientation == 9)
        {
            int l = ai[0];
            int j1 = ai[0];
            int l1 = ai1[0];
            double d = ai1[1] == ai1[0] ? 0.0D : (double)(ai[1] - ai[0]) / (double)(ai1[1] - ai1[0]);
            double d2 = ai1[2] == ai1[0] ? 0.0D : (double)(ai[2] - ai[0]) / (double)(ai1[2] - ai1[0]);
            do
            {
                g.drawLine(l, l1, j1, l1);
                if(ai1[1] <= ai1[0] ? --l1 < ai1[1] : ++l1 > ai1[1])
                    break;
                l = (int)((double)(l1 - ai1[0]) * d + (double)ai[0]);
                j1 = (int)((double)(l1 - ai1[0]) * d2 + (double)ai[0]);
            } while(true);
        } else
        {
            int i1 = ai1[0];
            int k1 = ai1[0];
            int i2 = ai[0];
            double d1 = ai[1] == ai[0] ? 0.0D : (double)(ai1[1] - ai1[0]) / (double)(ai[1] - ai[0]);
            double d3 = ai[2] == ai[0] ? 0.0D : (double)(ai1[2] - ai1[0]) / (double)(ai[2] - ai[0]);
            do
            {
                g.drawLine(i2, i1, i2, k1);
                if(ai[1] <= ai[0] ? --i2 < ai[1] : ++i2 > ai[1])
                    break;
                i1 = (int)((double)(i2 - ai[0]) * d1 + (double)ai1[0]);
                k1 = (int)((double)(i2 - ai[0]) * d3 + (double)ai1[0]);
            } while(true);
        }
        g.translate(-super.arm_offset, -super.arm_offset);
    }

    protected int preferredHeight()
    {
        return arrow_size.height;
    }

    protected int preferredWidth()
    {
        return arrow_size.width;
    }

    public void run()
    {
        int i = initial_delay;
        int j = tn;
        tn++;
        do
        {
            long l = System.currentTimeMillis();
            try
            {
                Thread.sleep(i);
            }
            catch(Exception _ex) { }
            if(thread == null || thread_stop || !Thread.currentThread().isAlive())
                return;
            if(isEnabled() && System.currentTimeMillis() - l >= (long)i)
                clickAction(null);
            if(thread == null || thread_stop)
                return;
            i = 50;
        } while(true);
    }

    public synchronized void setArrowSize(Dimension dimension)
    {
        arrow_size = dimension;
        repaint();
    }

    public synchronized void setBackground(Color color)
    {
        bg_set = true;
        super.setBackground(color);
    }

    public void setInitialRepeatDelay(int i)
    {
        initial_delay = i;
    }

    public synchronized void setOrientation(int i)
    {
        ArrowButtonConverter.checkOrientation(i);
        orientation = i;
        if(i == 10 || i == 9)
        {
            super.insets = new Insets(3, 2, 3, 3);
            arrow_size = new Dimension(7, 4);
        } else
        {
            arrow_size = new Dimension(4, 7);
            super.insets = new Insets(2, 3, 3, 3);
        }
        repaint();
    }

    public static final int UP = 10;
    public static final int DOWN = 9;
    public static final int LEFT = 0;
    public static final int RIGHT = 2;
    int orientation;
    Dimension arrow_size;
    int initial_delay;
    transient Thread thread;
    transient boolean thread_stop;
    transient int thread_counter;
    int tn;
    Color border_color;
    Color right_border_color;
    boolean bg_set;
    private static final String base = "arrowbutton";
    private static int nameCounter = 0;
    private static final boolean TRACE = false;

}
