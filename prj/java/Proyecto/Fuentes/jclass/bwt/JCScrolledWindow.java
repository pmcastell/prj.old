// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCScrolledWindow.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventObject;
import javax.swing.border.Border;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;

// Referenced classes of package jclass.bwt:
//            JCContainer, BWTEnum, JCAdjustmentListener, JCComponent, 
//            JCScrollableInterface, JCScrollbar, ScrolledWindowConverter, Viewport, 
//            JCAdjustmentEvent

public class JCScrolledWindow extends JCContainer
    implements KeyListener, JCAdjustmentListener
{

    public JCScrolledWindow()
    {
        this(null, null);
    }

    public JCScrolledWindow(Applet applet, String s)
    {
        super(applet, s);
        sb_display = 0;
        sb_offset = 0;
        vert_sb = new JCScrollbar(1);
        horiz_sb = new JCScrollbar(0);
        keystroke = 0;
        smartscroll = true;
        rect = new Rectangle();
        if(s == null)
            setName("scrolledwindow" + nameCounter++);
        setLayout(null);
        viewport = new Viewport();
        if(getClass().getName().equals("jclass.bwt.JCScrolledWindow"))
            getParameters(applet);
        addInternal(vert_sb);
        vert_sb.addAdjustmentListener(this);
        addInternal(horiz_sb);
        horiz_sb.addAdjustmentListener(this);
    }

    public Component add(Component component)
    {
        if(viewport instanceof Viewport)
            ((Viewport)viewport).add(component);
        return component;
    }

    public Component add(Component component, int i)
    {
        if(viewport instanceof Viewport)
            ((Viewport)viewport).add(component, i);
        return component;
    }

    public void add(Component component, Object obj)
    {
        if(viewport instanceof Viewport)
            ((Viewport)viewport).add(component, obj);
    }

    public void add(Component component, Object obj, int i)
    {
        if(viewport instanceof Viewport)
            ((Viewport)viewport).add(component, obj, i);
    }

    public Component add(String s, Component component)
    {
        if(viewport instanceof Viewport)
            ((Viewport)viewport).add(s, component);
        return component;
    }

    protected void addImpl(Component component, Object obj, int i)
    {
        if(viewport instanceof Viewport)
        {
            ((Viewport)viewport).add(component);
            if(!(component instanceof Container))
                component.addKeyListener(this);
        }
    }

    protected void addInternal(Component component)
    {
        if(component.getParent() != this)
            super.addImpl(component, null, -1);
    }

    public void addNotify()
    {
        addInternal(viewport);
        super.addNotify();
    }

    public void adjustmentValueChanged(JCAdjustmentEvent jcadjustmentevent)
    {
        if(jcadjustmentevent.getSource() == vert_sb)
            scrollVertical(jcadjustmentevent, vert_sb.getValue());
        else
        if(jcadjustmentevent.getSource() == horiz_sb)
            scrollHorizontal(jcadjustmentevent, horiz_sb.getValue());
    }

    public JCScrollbar getHorizScrollbar()
    {
        return horiz_sb;
    }

    protected void getParameters()
    {
        super.getParameters();
        ScrolledWindowConverter.getParams(this);
    }

    public int getScrollbarDisplay()
    {
        return sb_display;
    }

    public int getScrollbarOffset()
    {
        return sb_offset;
    }

    public boolean getSmartScroll()
    {
        return smartscroll;
    }

    public JCScrollbar getVertScrollbar()
    {
        return vert_sb;
    }

    public Component getViewport()
    {
        return viewport;
    }

    protected int getViewportHeight()
    {
        return JCComponent.getPreferredSize(viewport).height;
    }

    protected int getViewportWidth()
    {
        return JCComponent.getPreferredSize(viewport).width;
    }

    protected int headerHeight()
    {
        return 0;
    }

    public void keyPressed(KeyEvent keyevent)
    {
        switch(keyevent.getKeyCode())
        {
        case 33: // '!'
            keystroke = 1002;
            scrollVertical(vert_sb.getValue() - vert_sb.getBlockIncrement());
            break;

        case 34: // '"'
            keystroke = 1003;
            scrollVertical(vert_sb.getValue() + vert_sb.getBlockIncrement());
            break;

        case 36: // '$'
            keystroke = 1000;
            scrollVertical(0);
            break;

        case 35: // '#'
            keystroke = 1001;
            scrollVertical(vert_sb.getMaximum());
            break;

        case 37: // '%'
            keystroke = 1006;
            scrollHorizontal(horiz_sb.getValue() - horiz_sb.getUnitIncrement());
            break;

        case 39: // '\''
            keystroke = 1007;
            scrollHorizontal(horiz_sb.getValue() + horiz_sb.getUnitIncrement());
            break;
        }
        keystroke = 0;
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public void layout()
    {
        if(getPeer() == null)
            return;
        int l1 = 0;
        int i2 = 0;
        boolean flag2 = false;
        boolean flag3 = false;
        int l2 = 0;
        int i3 = 0;
        int j3 = 0;
        int k3 = 0;
        int l3 = headerHeight();
        j3 = getViewportWidth();
        k3 = getViewportHeight();
        int i1;
        int j1;
        int k1;
        boolean flag;
        boolean flag1;
        synchronized(this)
        {
            int j2 = size().width - JCComponent.getInsets(this).left - JCComponent.getInsets(this).right;
            int k2 = size().height - JCComponent.getInsets(this).top - JCComponent.getInsets(this).bottom;
            l2 = k2 - l3;
            i3 = j2;
            if(sb_display == 2)
                flag1 = flag = false;
            else
            if(sb_display == 1)
                flag1 = flag = true;
            else
            if(l2 >= k3 && i3 >= j3 && sb_display == 0)
            {
                i3 = j2;
                l2 = k2;
                flag1 = flag = false;
            } else
            {
                flag1 = sb_display != 4;
                flag = sb_display != 3;
                boolean flag4 = true;
                boolean flag6 = true;
                if(k3 <= l2 && sb_display != 3)
                    flag4 = false;
                if(j3 <= i3 && sb_display != 4)
                    flag6 = false;
                if(flag6)
                    l2 -= 16 + sb_offset;
                if(flag4)
                    i3 -= 16 + sb_offset;
                if(k3 <= l2 && sb_display != 3)
                {
                    i3 = j2;
                    flag1 = false;
                } else
                {
                    i3 = j2 - (16 + sb_offset);
                }
                if(j3 <= i3 && sb_display != 4)
                {
                    l2 = k2;
                    flag = false;
                } else
                {
                    l2 = k2 - (16 + sb_offset);
                }
            }
            l2 = flag ? k2 - (16 + sb_offset) : k2;
            i3 = flag1 ? j2 - (16 + sb_offset) : j2;
            if(flag)
                if(!flag1)
                    l1 = j2;
                else
                    l1 = i3;
            if(flag1)
                if(!flag)
                    i2 = k2;
                else
                    i2 = l2;
            j1 = JCComponent.getInsets(this).left;
            k1 = JCComponent.getInsets(this).top;
            int j = JCComponent.getInsets(this).left;
            int k = flag ? size().height - JCComponent.getInsets(this).bottom - 16 : size().height;
            int l = flag1 ? size().width - JCComponent.getInsets(this).right - 16 : j2;
            i1 = JCComponent.getInsets(this).top;
        }
        int i4 = reshapeHeader(j1, k1, i3);
        int k4 = 0;
        boolean flag5 = false;
        int j5 = 0;
        int i;
        synchronized(this)
        {
            k1 += i4;
            l2 -= i4;
            i1 += i4;
            i2 -= i4;
            k4 = vert_sb.getValue();
            int l4 = k4;
            k4 = Math.max(0, Math.min(k4, k3 - l2));
            if(k4 != l4)
                scrollVertical(null, k4);
            j5 = k3;
            i = l2;
            if(viewport instanceof JCComponent)
            {
                JCComponent jccomponent = (JCComponent)viewport;
                int j4 = 2 * jccomponent.getBorderThickness() + jccomponent.getInsets().top + jccomponent.getInsets().bottom;
                i -= j4;
                j5 -= j4;
            }
        }
        setVertScrollbarValues(k4, i, 0, j5);
        synchronized(this)
        {
            int i5 = k4 = horiz_sb.getValue();
            k4 = Math.max(0, Math.min(k4, j3 - i3));
            if(k4 != i5)
                scrollHorizontal(null, k4);
            j5 = j3;
            i = i3;
            if(viewport instanceof JCComponent)
            {
                JCComponent jccomponent1 = (JCComponent)viewport;
                int k5 = 2 * jccomponent1.getBorderThickness() + jccomponent1.getInsets().left + jccomponent1.getInsets().right;
                i -= k5;
                j5 -= k5;
            }
        }
        setHorizScrollbarValues(k4, i, 0, j5);
        reshapeViewport(j1, k1, i3, l2);
        Point point = viewport.location();
        Dimension dimension = viewport.size();
        if(flag1)
            JCComponent.setBounds(vert_sb, point.x + dimension.width + sb_offset, point.y, 16, i2);
        vert_sb.show(flag1);
        if(flag)
            JCComponent.setBounds(horiz_sb, point.x, point.y + dimension.height + sb_offset, l1, 16);
        horiz_sb.show(flag);
    }

    protected int preferredHeight()
    {
        int i = viewport == null ? 0 : JCComponent.getPreferredSize(viewport).height;
        return i <= 0 ? 100 : i + sb_size();
    }

    protected int preferredWidth()
    {
        int i = viewport == null ? 0 : JCComponent.getPreferredSize(viewport).width;
        return i <= 0 ? 100 : i + sb_size();
    }

    protected int reshapeHeader(int i, int j, int k)
    {
        return 0;
    }

    protected void reshapeViewport(int i, int j, int k, int l)
    {
        JCComponent.setBounds(viewport, i, j, k, l);
    }

    int sb_size()
    {
        return sb_display != 1 ? 0 : 16 + sb_offset;
    }

    public void scrollHorizontal(int i)
    {
        horiz_sb.setValue(i);
        scrollHorizontal(null, horiz_sb.getValue());
    }

    protected void scrollHorizontal(JCAdjustmentEvent jcadjustmentevent, int i)
    {
        if(viewport instanceof Viewport)
            ((Viewport)viewport).scrollHorizontal(i);
        else
        if(viewport instanceof JCScrollableInterface)
            scrollHorizontal((JCScrollableInterface)viewport, jcadjustmentevent, i);
    }

    protected void scrollHorizontal(JCScrollableInterface jcscrollableinterface, JCAdjustmentEvent jcadjustmentevent, int i)
    {
        int j = i - jcscrollableinterface.getHorizOrigin();
        int k = Math.abs(j);
        Component component = (Component)jcscrollableinterface;
        jcscrollableinterface.setHorizOrigin(i);
        if(component instanceof JCComponent)
            ((JCComponent)component).getDrawingArea(rect);
        else
            rect.resize(component.size().width, component.size().height);
        if(k >= rect.width)
        {
            component.repaint();
            return;
        }
        int l = rect.width - k;
        JCComponent jccomponent = (component instanceof JCComponent) ? (JCComponent)component : null;
        Graphics g = component.getGraphics();
        if(jccomponent != null)
        {
            if(smartscroll)
            {
                jccomponent.copyArea(g, rect.x + Math.max(j, 0), rect.y, l, rect.height, -j, 0);
                jccomponent.paintImmediately(rect.x + (j <= 0 ? 0 : l), rect.y, k, rect.height);
            } else
            {
                jccomponent.paintImmediately(rect.x, rect.y, rect.width, rect.height);
            }
        } else
        if(smartscroll)
        {
            g.copyArea(rect.x + Math.max(j, 0), rect.y, l, rect.height, -j, 0);
            component.repaint(rect.x + (j <= 0 ? 0 : l), rect.y, k, rect.height);
        } else
        {
            component.repaint(rect.x, rect.y, rect.width, rect.height);
        }
        g.dispose();
    }

    public void scrollVertical(int i)
    {
        vert_sb.setValue(i);
        scrollVertical(null, vert_sb.getValue());
    }

    protected void scrollVertical(JCAdjustmentEvent jcadjustmentevent, int i)
    {
        if(viewport instanceof Viewport)
            ((Viewport)viewport).scrollVertical(i);
        else
        if(viewport instanceof JCScrollableInterface)
            scrollVertical((JCScrollableInterface)viewport, jcadjustmentevent, i);
    }

    protected void scrollVertical(JCScrollableInterface jcscrollableinterface, JCAdjustmentEvent jcadjustmentevent, int i)
    {
        int j = i - jcscrollableinterface.getVertOrigin();
        int k = Math.abs(j);
        Component component = (Component)jcscrollableinterface;
        jcscrollableinterface.setVertOrigin(i);
        if(component instanceof JCComponent)
            ((JCComponent)component).getDrawingArea(rect);
        else
            rect.resize(component.size().width, component.size().height);
        if(k >= rect.height)
        {
            component.repaint();
            return;
        }
        int l = rect.height - k;
        JCComponent jccomponent = (component instanceof JCComponent) ? (JCComponent)component : null;
        Graphics g = component.getGraphics();
        if(jccomponent != null)
        {
            if(smartscroll)
            {
                jccomponent.copyArea(g, rect.x, rect.y + Math.max(j, 0), rect.width, l, 0, -j);
                jccomponent.paintImmediately(rect.x, rect.y + (j <= 0 ? 0 : l), rect.width, k);
            } else
            {
                jccomponent.paintImmediately(rect.x, rect.y, rect.width, rect.height);
            }
        } else
        if(smartscroll)
        {
            g.copyArea(rect.x, rect.y + Math.max(j, 0), rect.width, l, 0, -j);
            component.repaint(rect.x, rect.y + (j <= 0 ? 0 : l), rect.width, k);
        } else
        {
            component.repaint(rect.x, rect.y, rect.width, rect.height);
        }
        g.dispose();
    }

    public void setBorder(Border border)
    {
        super.swing_border = border;
        setChildrenBorder(border);
    }

    protected void setHorizScrollbarValues()
    {
    }

    protected void setHorizScrollbarValues(int i, int j, int k, int l)
    {
        j = Math.min(l - k, j);
        horiz_sb.setValues(i, j, k, l);
        horiz_sb.setBlockIncrement(j);
        horiz_sb.setUnitIncrement(10);
        setHorizScrollbarValues();
    }

    public final void setLayout(LayoutManager layoutmanager)
    {
    }

    public void setScrollPosition(int i, int j)
    {
        horiz_sb.setValue(i);
        vert_sb.setValue(j);
        if(viewport instanceof Viewport)
        {
            ((Viewport)viewport).scrollHorizontal(i);
            ((Viewport)viewport).scrollVertical(j);
        } else
        if(viewport instanceof JCScrollableInterface)
            setScrollPosition((JCScrollableInterface)viewport, horiz_sb.getValue(), vert_sb.getValue());
    }

    protected void setScrollPosition(JCScrollableInterface jcscrollableinterface, int i, int j)
    {
        int k = i - jcscrollableinterface.getHorizOrigin();
        int l = Math.abs(k);
        int i1 = j - jcscrollableinterface.getVertOrigin();
        int j1 = Math.abs(i1);
        jcscrollableinterface.setHorizOrigin(i);
        jcscrollableinterface.setVertOrigin(j);
        Component component = (Component)jcscrollableinterface;
        if(component instanceof JCComponent)
            ((JCComponent)component).getDrawingArea(rect);
        else
            rect.resize(component.size().width, component.size().height);
        if(l >= rect.width || j1 > rect.height)
        {
            component.repaint();
            return;
        }
        int k1 = rect.width - l;
        int l1 = rect.height - j1;
        JCComponent jccomponent = (component instanceof JCComponent) ? (JCComponent)component : null;
        Graphics g = component.getGraphics();
        if(jccomponent != null)
        {
            jccomponent.copyArea(g, rect.x + Math.max(k, 0), rect.y + Math.max(i1, 0), k1, l1, -k, -i1);
            jccomponent.paintImmediately(k < 0 || i1 < 0 ? rect.x : rect.x + k1, rect.y, i1 >= 0 ? l : rect.width, i1 >= 0 ? l1 : j1);
            jccomponent.paintImmediately(k < 0 || i1 >= 0 ? rect.x : rect.x + k1, i1 >= 0 ? rect.y + l1 : rect.y + j1, i1 >= 0 ? rect.width : l, i1 >= 0 ? j1 : l1);
        } else
        {
            g.copyArea(rect.x + Math.max(k, 0), rect.y + Math.max(i1, 0), k1, l1, -k, -i1);
            component.repaint(k < 0 || i1 < 0 ? rect.x : rect.x + k1, rect.y, i1 >= 0 ? l : rect.width, i1 >= 0 ? l1 : j1);
            component.repaint(k < 0 || i1 >= 0 ? rect.x : rect.x + k1, i1 >= 0 ? rect.y + l1 : rect.y + j1, i1 >= 0 ? rect.width : l, i1 >= 0 ? j1 : l1);
        }
        g.dispose();
    }

    public void setScrollbarDisplay(int i)
    {
        ScrolledWindowConverter.checkDisplay(i);
        sb_display = i;
        layout();
    }

    public void setScrollbarOffset(int i)
    {
        sb_offset = i;
        layout();
    }

    public void setSmartScroll(boolean flag)
    {
        smartscroll = flag;
    }

    protected void setVertScrollbarValues()
    {
    }

    protected void setVertScrollbarValues(int i, int j, int k, int l)
    {
        j = Math.min(l - k, j);
        vert_sb.setValues(i, j, k, l);
        vert_sb.setBlockIncrement(j);
        vert_sb.setUnitIncrement(10);
        setVertScrollbarValues();
    }

    public static final int DISPLAY_AS_NEEDED = 0;
    public static final int DISPLAY_ALWAYS = 1;
    public static final int DISPLAY_NONE = 2;
    public static final int DISPLAY_VERTICAL_ONLY = 3;
    public static final int DISPLAY_HORIZONTAL_ONLY = 4;
    int sb_display;
    int sb_offset;
    private JCScrollbar vert_sb;
    private JCScrollbar horiz_sb;
    int keystroke;
    private static final String base = "scrolledwindow";
    private static int nameCounter = 0;
    protected Component viewport;
    protected boolean smartscroll;
    Rectangle rect;

}
