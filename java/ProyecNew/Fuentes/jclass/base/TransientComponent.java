// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3)
// Source File Name:   TransientComponent.java

package jclass.base;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import jclass.util.JCEnvironment;

// Referenced classes of package jclass.base:
//            BaseComponent, Border

public abstract class TransientComponent extends BaseComponent
{

    public TransientComponent()
    {
        this(null, null);
    }

    public TransientComponent(Applet applet, String s)
    {
        super(applet, s);
        highlight = 2;
        pref_width = -999;
        pref_height = -999;
        traversable = true;
        ignoreNextEvent = false;
    }

    protected void clipGCToAncestors(Graphics g)
    {
        if(g == null)
            return;
        Rectangle rectangle = getBounds();
        int i = rectangle.x;
        int j = rectangle.y;
        for(Object obj = this; ((Component) (obj)).getParent() != null; obj = ((Component) (obj)).getParent())
        {
            Rectangle rectangle1 = ((Component) (obj)).getParent().getBounds();
            rectangle.x += rectangle1.x;
            rectangle.y += rectangle1.y;
            i += rectangle1.x;
            j += rectangle1.y;
            SwingUtilities.computeIntersection(rectangle1.x, rectangle1.y, rectangle1.width, rectangle1.height, rectangle);
        }

        rectangle.x -= i;
        rectangle.y -= j;
        g.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public static final Event convertEvent(Component component, KeyEvent keyevent)
    {
        int i = keyevent.getKeyChar();
        int j = keyevent.getKeyCode();
        for(int k = 0; k < keyCodes.length; k++)
        {
            if(keyCodes[k][0] != j)
                continue;
            i = keyCodes[k][1];
            break;
        }

        if(JCEnvironment.getOS() == 1 && JCEnvironment.getJavaVersion() >= 116 && (i & 0xff00) != 0 && keyevent.getKeyChar() != '\r' && j != 10 && j != 38 && j != 40 && j != 37 && j != 39 && j != 33 && j != 34 && j != 36 && j != 35)
            i -= 65280;
        return new Event(component, keyevent.getWhen(), 0, 0, 0, i, keyevent.getModifiers());
    }

    public void disable()
    {
        if(isEnabled())
        {
            drawHighlight(false);
            super.disable();
            repaint();
        }
    }

    protected void drawBorder(Graphics g)
    {
        if(!intersects(super.paint_rect, super.border))
            return;
        if(getBorder() != null)
            getBorder().paintBorder(this, g, highlight, highlight, getSize().width - 2 * highlight, getSize().height - 2 * highlight);
//        else
//            Border.draw(g, super.border_style, super.border, highlight, highlight, getSize().width - 2 * highlight, getSize().height - 2 * highlight, getBackground(), getForeground());
    }

    protected void drawHighlight(Graphics g, boolean flag)
    {
        if(highlight == 0)
            return;
        if(!intersects(super.paint_rect, highlight))
            return;
        int i = 0;
        int j = 0;
        int k = getSize().width;
        int l = getSize().height;
        Color color = flag ? highlight_color : getParent().getBackground();
        if(color == null)
            color = Color.black;
        g.setColor(color);
        for(int i1 = 0; i1 < highlight;)
        {
            g.drawRect(i, j, k - 1, l - 1);
            i1++;
            i++;
            j++;
            k -= 2;
            l -= 2;
        }

    }

    protected void drawHighlight(boolean flag)
    {
        if(isShowing())
        {
            Graphics g = getGraphics();
            if(g != null)
            {
                clipGCToAncestors(g);
                drawHighlight(g, flag);
                g.dispose();
            }
        }
    }

    protected void drawHighlightAndBorder(Graphics g)
    {
        drawHighlight(g, super.has_focus);
        if(super.border > 0 && super.border_style != 0)
            drawBorder(g);
    }

    public void enable()
    {
        if(!isEnabled())
        {
            super.enable();
            repaint();
        }
    }

    public Rectangle getDrawingArea()
    {
        Rectangle rectangle = new Rectangle();
        getDrawingArea(rectangle);
        return rectangle;
    }

    public void getDrawingArea(Rectangle rectangle)
    {
        int i = highlight + super.border;
        rectangle.setBounds(i + super.insets.left, i + super.insets.top, Math.max(0, getSize().width - (2 * i + super.insets.left + super.insets.right)), Math.max(0, getSize().height - (2 * i + super.insets.top + super.insets.bottom)));
    }

    public int getDrawingAreaHeight()
    {
        int i = 2 * super.border + 2 * highlight + super.insets.top + super.insets.bottom;
        return Math.max(0, getSize().height - i);
    }

    public int getDrawingAreaWidth()
    {
        int i = 2 * super.border + 2 * highlight + super.insets.left + super.insets.right;
        return Math.max(0, getSize().width - i);
    }

    public Color getHighlightColor()
    {
        return highlight_color == null ? Color.black : highlight_color;
    }

    public int getHighlightThickness()
    {
        return highlight;
    }

    public Rectangle getPaintRect()
    {
        return super.paint_rect;
    }

    public int getPreferredHeightInternal()
    {
        return pref_height;
    }

    public int getPreferredWidthInternal()
    {
        return pref_width;
    }

    public boolean gotFocus102(Event event, Object obj)
    {
        if(!isEnabled())
        {
            drawHighlight(false);
            return true;
        }
        super.gotFocus102(event, obj);
        super.has_focus = true;
        if(isShowing())
            drawHighlight(true);
        return true;
    }

    protected boolean isBustedVM()
    {
        return JCEnvironment.getOS() == 1 && JCEnvironment.getJavaVersion() >= 116;
    }

    public boolean isFocusTraversable()
    {
        return traversable && isEnabled() && isShowing();
    }

    public boolean isTraversable()
    {
        return traversable;
    }

    public synchronized void layout()
    {
        super.needs_layout = false;
    }

    public boolean lostFocus102(Event event, Object obj)
    {
        super.lostFocus102(event, obj);
        if(isShowing())
            drawHighlight(false);
        return true;
    }

    public Dimension minimumSize()
    {
        return preferredSize();
    }

    public void paint(Graphics g)
    {
        synchronized(getAWTLock())
        {
            if(g == null || getBackground() == null)
                return;
            draw_gc = g;
            super.paint_rect = g.getClipRect();
            if(super.double_buffer)
            {
                g = getDoubleBufferGraphics();
                if(g == null)
                    g = draw_gc;
                super.dblbuffer_image = getDoubleBufferImage();
                if(super.paint_rect != null)
                    g.setClip(super.paint_rect);
            }
            if(super.paint_rect == null)
            {
                super.paint_rect = new Rectangle(getSize());
                g.setClip(super.paint_rect);
            }
            super.rect.setBounds(0, 0, getSize().width, getSize().height);
            g.setColor(getBackground());
            fillBackground(g);
            drawHighlightAndBorder(g);
            g.setFont(getFont());
            g.setColor(getForeground());
            getDrawingArea(super.rect);
            g.clipRect(super.rect.x, super.rect.y, super.rect.width, super.rect.height);
            java.awt.Image image = super.dblbuffer_image;
            paintComponent(g);
            if(super.double_buffer && super.dblbuffer_image != null)
            {
                draw_gc.drawImage(super.dblbuffer_image, 0, 0, null);
                if(image != super.dblbuffer_image)
                    drawHighlightAndBorder(draw_gc);
            }
            super.dblbuffer_image = image;
            draw_gc = null;
            super.paint_rect = null;
        }
    }

    protected int preferredHeight()
    {
        return -999;
    }

    public Dimension preferredSize()
    {
        int i = pref_width == -999 ? getPeer() == null ? 0 : preferredWidth() : pref_width;
        i = i >= 0 ? i + 2 * super.border + 2 * highlight + super.insets.left + super.insets.right : 50;
        int j = pref_height == -999 ? getPeer() == null ? 0 : preferredHeight() : pref_height;
        j = j >= 0 ? j + 2 * super.border + 2 * highlight + super.insets.top + super.insets.bottom : 50;
        return new Dimension(i, j);
    }

    protected int preferredWidth()
    {
        return -999;
    }

    protected void processFocusEvent(FocusEvent focusevent)
    {
        Event event = new Event(this, 0L, focusevent.getID(), 0, 0, 0, 0);
        switch(event.id)
        {
        case 1005: // Event.LOST_FOCUS
            lostFocus(event, this);
            break;

        case 1004: // Event.GOT_FOCUS
            gotFocus(event, this);
            break;
        }
        super.processFocusEvent(focusevent);
    }

    protected void processKeyEvent(KeyEvent keyevent)
    {
        int i = keyevent.getID();
        int j = keyevent.getKeyCode();
        if(isBustedVM() && ignoreNextEvent)
        {
            ignoreNextEvent = false;
            return;
        }
        char c;
        if(isBustedVM() && !keyevent.isControlDown() && keyevent.getKeyChar() != '\r' && keyevent.getKeyChar() != '\n' && j != 10 && j != 38 && j != 40 && j != 37 && j != 39 && j != 33 && j != 34 && j != 36 && j != 35)
        {
            c = '\u0190';
        } else
        {
            c = '\u0191';
            if(i != 402 && keyevent.isControlDown())
                ignoreNextEvent = true;
        }
        if(JCEnvironment.getOS() == 0 && i == 400 && keyevent.isControlDown() && keyevent.isAltDown())
        {
            i = c;
            keyevent = new KeyEvent(this, i, keyevent.getWhen(), 0, keyevent.getKeyCode(), keyevent.getKeyChar());
        }
        if(i == c)
        {
            if(keyevent.isActionKey())
                i = 403;
            if(j == 16 || j == 17 || j == 18)
                return;
            Event event = convertEvent(this, keyevent);
            event.id = i;
            keyDown(event, event.key);
        }
        super.processKeyEvent(keyevent);
    }

    protected void processMouseEvent(MouseEvent mouseevent)
    {
        Event event = new Event(this, mouseevent.getWhen(), mouseevent.getID(), mouseevent.getX(), mouseevent.getY(), 0, mouseevent.getModifiers());
        event.clickCount = mouseevent.getClickCount();
        switch(event.id)
        {
        case 503: // Event.MOUSE_MOVE
        default:
            break;

        case 501: // Event.MOUSE_DOWN
            mouse_down_event_timestamp = event.when;
            if(event.when - popdown_event_timestamp > 50L || JCEnvironment.getOS() == 1)
                mouseDown(event, event.x, event.y);
            break;

        case 502: // Event.MOUSE_UP
            mouseUp(event, event.x, event.y);
            break;

        case 504: // Event.MOUSE_EVENT
            mouseEnter(event, event.x, event.y);
            break;

        case 505: // Event.MOUSE_EXIT
            mouseExit(event, event.x, event.y);
            break;
        }
        super.processMouseEvent(mouseevent);
    }

    protected void processMouseMotionEvent(MouseEvent mouseevent)
    {
        Event event = new Event(this, mouseevent.getWhen(), mouseevent.getID(), mouseevent.getX(), mouseevent.getY(), 0, mouseevent.getModifiers());
        switch(event.id)
        {
        case 503: // Event.MOUSE_MOVE
            mouseMove(event, event.x, event.y);
            break;

        case 506: // Event.MOUSE_DRAG
            mouseDrag(event, event.x, event.y);
            break;
        }
        super.processMouseMotionEvent(mouseevent);
    }

    public void setBounds(int i, int j, int k, int l)
    {
        synchronized(this)
        {
            boolean flag = size().width != k || size().height != l;
            if(!flag && location().x == i && location().y == j)
                return;
        }
        super.setBounds(i, j, k, l);
        if(JCEnvironment.getJavaVersion() >= 110 || JCEnvironment.isJavaOS())
            layout();
        if(super.do_repaint)
            repaint();
    }

    public void setBounds(Rectangle rectangle)
    {
        setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void setCursor(int i)
    {
        java.awt.Window window = BaseComponent.getWindow(this);
        if(window != null)
        {
            window.setCursor(Cursor.getPredefinedCursor(i));
            getToolkit().sync();
        }
    }

    public void setHighlightColor(Color color)
    {
        synchronized(this)
        {
            highlight_color = color;
        }
        repaint();
    }

    public void setHighlightThickness(int i)
    {
        synchronized(this)
        {
            highlight = i;
        }
        doLayout();
        repaint();
    }

    public void setPreferredSize(int i, int j)
    {
        pref_width = i;
        pref_height = j;
    }

    public void setPreferredSize(Dimension dimension)
    {
        pref_width = dimension.width;
        pref_height = dimension.height;
    }

    public void setTraversable(boolean flag)
    {
        traversable = flag;
    }

    public static long popdown_event_timestamp;
    public static long mouse_down_event_timestamp;
    protected int highlight;
    protected Color highlight_color;
    protected int pref_width;
    protected int pref_height;
    protected boolean traversable;
    protected transient Graphics draw_gc;
    private static final int keyCodes[][] = {
        {
            127, 127
        }, {
            40, 1005
        }, {
            35, 1001
        }, {
            10, 10
        }, {
            36, 1000
        }, {
            37, 1006
        }, {
            34, 1003
        }, {
            33, 1002
        }, {
            39, 1007
        }, {
            38, 1004
        }
    };
    private boolean ignoreNextEvent;
    private static final boolean TRACE = false;

}
