// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCScrollbar.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.border.Border;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;
import jclass.util.*;

// Referenced classes of package jclass.bwt:
//            JCContainer, BWTEnum, BWTUtil, JCActionListener, 
//            JCAdjustable, JCAdjustmentEvent, JCAdjustmentListener, JCArrowButton, 
//            JCButton, JCComponent, JCScrolledWindow, ScrollbarConverter, 
//            ScrollbarThumb, JCActionEvent

public class JCScrollbar extends JCContainer
    implements JCAdjustable, JCActionListener
{

    public JCScrollbar()
    {
        this(1);
    }

    public JCScrollbar(int i)
    {
        this(i, null, null);
    }

    public JCScrollbar(int i, int j, int k, int l, int i1)
    {
        this(i);
        setValues(j, k, l, i1);
    }

    public JCScrollbar(int i, Applet applet, String s)
    {
        super(applet, s);
        scrolled_window = null;
        max = 100;
        dir = -999;
        adjustmentListeners = new JCVector(0);
        line_incr = 10;
        page_incr = 10;
        filter_time = 0L;
        last_time = 0L;
        trough_size = 0;
        slider_size = 0;
        dragStart = -999;
        dragValueStart = -999;
        new_bg = true;
        if(s == null)
            setName("scrollbar" + nameCounter++);
        setLayout(null);
        setOrientation(i);
        add(thumb = new ScrollbarThumb(this));
        super.double_buffer = true;
        enableEvents(32L);
        if(getClass().getName().equals("jclass.bwt.JCScrollbar"))
            getParameters(applet);
    }

    public void actionPerformed(JCActionEvent jcactionevent)
    {
        if(jcactionevent.getSource() == decr_arrow)
            setValue(value - line_incr, 601, true);
        else
        if(jcactionevent.getSource() == incr_arrow)
            setValue(value + line_incr, 602, true);
    }

    public void addAdjustmentListener(JCAdjustmentListener jcadjustmentlistener)
    {
        adjustmentListeners.addUnique(jcadjustmentlistener);
    }

    public boolean atEnd()
    {
        return value == 0 || value >= max - visible;
    }

    int check(int i)
    {
        return Math.min(Math.max(min, i), max - visible);
    }

    private Image createDisabledImage()
    {
        if(!new_bg && disabled_image != null)
            return disabled_image;
        if(creator == null)
            creator = new JCImageCreator(this);
        Color color = getBackground();
        Color color1 = getForeground();
        creator.setColor('b', color.equals(Color.white) ? Color.lightGray : color);
        creator.setColor('w', Color.white);
        new_bg = false;
        creator.setSize(disabled_pixels[0].length(), disabled_pixels.length);
        return creator.create(disabled_pixels);
    }

    int cvtThumbSizeToPixels(int i)
    {
        int j = 0;
        if(max > min)
        {
            j = (trough_size * i) / (max - min);
            if(j < 0)
                j = 0;
        }
        j = Math.min(trough_size, Math.max(j, 4));
        if(min >= max)
            j = trough_size;
        return j;
    }

    boolean dragging()
    {
        return dragStart != -999;
    }

    public int getBlockIncrement()
    {
        return page_incr;
    }

    public long getFilterTime()
    {
        return filter_time;
    }

    public int getMaximum()
    {
        return max;
    }

    public int getMinimum()
    {
        return min;
    }

    public int getOrientation()
    {
        return dir;
    }

    String getParam(String s)
    {
        return JCContainer.conv.getParam(super.applet, this, getName(), s);
    }

    protected void getParameters()
    {
        super.getParameters();
        ScrollbarConverter.getParams(this);
    }

    public void getParameters(Applet applet)
    {
        getParameters();
    }

    public int getUnitIncrement()
    {
        return line_incr;
    }

    public int getValue()
    {
        return value;
    }

    /**
     * @deprecated Method getVisible is deprecated
     */

    public int getVisible()
    {
        return visible;
    }

    public int getVisibleAmount()
    {
        return visible;
    }

    public boolean isFocusTraversable()
    {
        return false;
    }

    public boolean keyDown(Event event, int i)
    {
        if(i == 1002)
        {
            setValue(value - page_incr, 603, true);
            return true;
        }
        if(i == 1003)
        {
            setValue(value + page_incr, 604, true);
            return true;
        }
        if(i == 1000)
        {
            setValue(min, 605, true);
            return true;
        }
        if(i == 1001)
        {
            setValue(max - slider_size, 605, true);
            return true;
        }
        if(i == 1004 || i == 1006)
        {
            setValue(value - line_incr, 601, true);
            return true;
        }
        if(i == 1005 || i == 1007)
        {
            setValue(value + line_incr, 602, true);
            return true;
        } else
        {
            return super.keyDown(event, i);
        }
    }

    public void layout()
    {
        if(getPeer() == null)
            return;
        int i = 0;
        synchronized(this)
        {
            i = Math.max(minor, 16);
            if(i * 2 > major)
                i = major / 2;
            trough_size = Math.max(6, major) - 2 * i;
            slider_size = cvtThumbSizeToPixels(visible);
        }
        if(dir == 0)
            thumb.resize(slider_size, minor);
        else
            thumb.resize(minor, slider_size);
        if(dir == 0)
        {
            JCComponent.setBounds(decr_arrow, 0, 0, i, minor);
            JCComponent.setBounds(incr_arrow, major - i, 0, i, minor);
        } else
        {
            JCComponent.setBounds(decr_arrow, 0, 0, minor, i);
            JCComponent.setBounds(incr_arrow, 0, major - i, minor, i);
        }
        moveThumb();
        thumb.show(slider_size != trough_size);
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        if(event.target != this)
            return true;
        if(event.when - last_time < 20L)
            return true;
        last_time = event.when;
        int k = dir != 0 ? j : i;
        int l = dir != 0 ? thumb.location().y : thumb.location().x;
        if(k >= l && k <= l + slider_size)
        {
            dragStart = k;
            dragValueStart = value;
            return true;
        }
        if(event.controlDown())
        {
            if(k < l)
                setValue(min, 605, true);
            else
                setValue(max - visible, 605, true);
            return true;
        }
        if(k < l)
            setValue(value - page_incr, 603, true);
        else
            setValue(value + page_incr, 604, true);
        return true;
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
        if(!dragging())
            return true;
        int k = dir != 0 ? event.y : event.x;
        boolean flag = event.when - last_time > filter_time;
        if(flag)
            last_time = event.when;
        setValue(dragValueStart + toValue(k - dragStart), flag);
        last_time = event.when;
        return true;
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if(dragging())
        {
            int k = value;
            value = -999;
            setValue(k, 605, true);
        }
        dragStart = dragValueStart = -999;
        if(scrolled_window != null && JCEnvironment.getJavaVersion() < 110)
            scrolled_window.getViewport().requestFocus();
        return true;
    }

    private void moveThumb()
    {
        int j = 0;
        int k = 0;
        synchronized(this)
        {
            if(getPeer() == null)
                return;
            int i = Math.max(0, Math.min(toPixels(value - min), trough_size - slider_size));
            if(dir == 0)
                j = i + decr_arrow.size().width;
            else
                k = i + decr_arrow.size().height;
        }
        thumb.move(j, k);
    }

    public void paintInterior(Graphics g)
    {
        if(!decr_arrow.getBackground().equals(getBackground()))
        {
            decr_arrow.setBackground(getBackground());
            incr_arrow.setBackground(getBackground());
        }
        if(isEnabled())
        {
            g.setColor(BWTUtil.brighter(getBackground()));
            g.fillRect(0, 0, size().width, size().height);
        } else
        {
            if(disabled_image == null)
                disabled_image = createDisabledImage();
            BWTUtil.wallPaper(this, g, disabled_image);
        }
        if(super.swing_border == null)
        {
            g.setColor(Color.black);
            g.drawRect(0, 0, size().width - 1, size().height - 1);
        }
        paintThumb(g);
    }

    protected void paintThumb(Graphics g)
    {
        if(!thumb.isVisible())
            return;
        Point point = BWTUtil.translateToParent(this, thumb, 0, 0);
        Dimension dimension = thumb.size();
        if(!getPaintRect().intersects(new Rectangle(point, dimension)))
        {
            return;
        } else
        {
            thumb.setDoubleBuffer(false);
            Point point1 = thumb.location();
            g.setColor(getBackground());
            Graphics g1 = g.create(point1.x, point1.y, dimension.width, dimension.height);
            thumb.paint(g1);
            g1.dispose();
            thumb.setDoubleBuffer(true);
            return;
        }
    }

    protected int preferredHeight()
    {
        return getOrientation() != 0 ? 100 : 16;
    }

    protected int preferredWidth()
    {
        return getOrientation() != 1 ? 100 : 16;
    }

    public void removeAdjustmentListener(JCAdjustmentListener jcadjustmentlistener)
    {
        adjustmentListeners.removeElement(jcadjustmentlistener);
    }

    public synchronized void setBackground(Color color)
    {
        new_bg = getPeer() == null || color == null || !color.equals(getBackground());
        thumb.setBackground(color);
        super.setBackground(color);
    }

    public void setBlockIncrement(int i)
    {
        page_incr = i;
    }

    public void setBorder(Border border)
    {
        super.swing_border = border;
        setChildrenBorder(border);
    }

    public void setBounds(int i, int j, int k, int l)
    {
        synchronized(this)
        {
            major = dir != 0 ? l : k;
            minor = dir != 0 ? k : l;
        }
        super.setBounds(i, j, k, l);
    }

    public void setFilterTime(long l)
    {
        filter_time = l;
    }

    public synchronized void setForeground(Color color)
    {
        incr_arrow.setForeground(color);
        decr_arrow.setForeground(color);
        super.setForeground(color);
    }

    public final void setLayout(LayoutManager layoutmanager)
    {
    }

    public void setMaximum(int i)
    {
        setValues(value, visible, min, i);
    }

    public void setMinimum(int i)
    {
        setValues(value, visible, i, max);
    }

    public synchronized void setOrientation(int i)
    {
        ScrollbarConverter.checkOrientation(i);
        if(dir == i)
        {
            return;
        } else
        {
            dir = i;
            byte byte0 = ((byte)(i != 0 ? 10 : 0));
            add(decr_arrow = new JCArrowButton(byte0, super.applet, getName()));
            decr_arrow.addActionListener(this);
            byte0 = ((byte)(i != 0 ? 9 : 2));
            add(incr_arrow = new JCArrowButton(byte0, super.applet, getName()));
            incr_arrow.addActionListener(this);
            incr_arrow.setBorderThickness(2);
            decr_arrow.setBorderThickness(2);
            incr_arrow.border_color = decr_arrow.border_color = Color.black;
            incr_arrow.initial_delay = decr_arrow.initial_delay = 250;
            return;
        }
    }

    public void setUnitIncrement(int i)
    {
        line_incr = i;
    }

    public void setValue(int i)
    {
        synchronized(this)
        {
            i = check(i);
            if(i != value)
                value = i;
        }
        moveThumb();
    }

    void setValue(int i, int j, boolean flag)
    {
        switch(j)
        {
        case 601: 
            j = 2;
            break;

        case 602: 
            j = 1;
            break;

        case 603: 
            j = 3;
            break;

        case 604: 
            j = 4;
            break;

        case 605: 
            j = 5;
            break;
        }
        JCVector jcvector = null;
        synchronized(this)
        {
            i = check(i);
            int l = value;
            if(i == l)
                return;
            value = i;
            jcvector = (JCVector)adjustmentListeners.clone();
        }
        moveThumb();
        if(!flag || jcvector.size() == 0)
            return;
        JCAdjustmentEvent jcadjustmentevent = new JCAdjustmentEvent(this, 601, j, i);
        for(int k = 0; k < jcvector.size(); k++)
            ((JCAdjustmentListener)jcvector.elementAt(k)).adjustmentValueChanged(jcadjustmentevent);

    }

    public void setValue(int i, boolean flag)
    {
        setValue(i, 605, flag);
    }

    public void setValues(int i, int j, int k, int l)
    {
        boolean flag = false;
        synchronized(this)
        {
            if(l < k)
                l = k;
            j = Math.min(l - k, j);
            i = Math.min(Math.max(k, i), l - j);
            if(value != i || visible != j || min != k || max != l)
            {
                min = k;
                max = l;
                value = i;
                page_incr = visible = j;
                flag = true;
            }
        }
        if(flag)
            layout();
    }

    public void setVisibleAmount(int i)
    {
        setValues(value, i, min, max);
    }

    private int toPixels(int i)
    {
        if(max == min)
            return 0;
        if(i >= max - min - visible)
        {
            return trough_size;
        } else
        {
            int j = (trough_size * i) / (max - min);
            return j <= 0 ? 0 : j;
        }
    }

    private int toValue(int i)
    {
        if(trough_size == 0)
            return 0;
        else
            return (i * (max - min)) / trough_size;
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    protected transient JCScrolledWindow scrolled_window;
    protected JCArrowButton incr_arrow;
    protected JCArrowButton decr_arrow;
    protected ScrollbarThumb thumb;
    static final int MIN_SLIDERSIZE = 6;
    private static final String base = "scrollbar";
    private static int nameCounter = 0;
    int value;
    int min;
    int max;
    int dir;
    protected JCVector adjustmentListeners;
    int line_incr;
    int page_incr;
    long filter_time;
    long last_time;
    int visible;
    int major;
    int minor;
    int trough_size;
    int slider_size;
    transient Image disabled_image;
    private int dragStart;
    private int dragValueStart;
    private transient JCImageCreator creator;
    private transient boolean new_bg;
    private final String disabled_pixels[] = {
        "wbwbwbwbwbwbwbwb", "bwbwbwbwbwbwbwbw", "wbwbwbwbwbwbwbwb", "bwbwbwbwbwbwbwbw", "wbwbwbwbwbwbwbwb", "bwbwbwbwbwbwbwbw", "wbwbwbwbwbwbwbwb", "bwbwbwbwbwbwbwbw", "wbwbwbwbwbwbwbwb", "bwbwbwbwbwbwbwbw"
    };

}
