// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3)
// Source File Name:   BaseComponent.java

package jclass.base;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JComponent;
import javax.swing.border.Border;
import jclass.util.JCEnvironment;

// Referenced classes of package jclass.base:
//            Border

public abstract class BaseComponent extends JComponent
{

    public BaseComponent()
    {
        this(null, null);
    }

    public BaseComponent(Applet applet1, String s)
    {
        in_repaint = false;
        needs_layout = true;
        border = 2;
        border_style = 3;
        clipToScreen = true;
        double_buffer = true;
        has_focus = false;
        insets = new Insets(0, 0, 0, 0);
        do_repaint = true;
        realized = false;
        rect = new Rectangle();
        applet = applet1;
        setName(s);
    }

    public void addNotify()
    {
        if(!realized)
        {
            if(use_system_colors)
            {
                if(getBackground() == null)
                    setBackground(SystemColor.control);
                if(getForeground() == null)
                    setForeground(SystemColor.controlText);
            }
            super.addNotify();
            realized = true;
        }
        if(applet == null)
            applet = getApplet(this);
        applet_context = getAppletContext();
        enableEvents(28L);
    }

    public void copyArea(Graphics g, int i, int j, int k, int l, int i1, int j1)
    {
        g.copyArea(i, j, k, l, i1, j1);
    }

    public Image createImage(int i, int j)
    {
        if(clipToScreen)
        {
            Dimension dimension = getToolkit().getScreenSize();
            i = Math.max(1, Math.min(i, dimension.width));
            j = Math.max(1, Math.min(j, dimension.height));
        } else
        {
            i = Math.max(1, i);
            j = Math.max(1, j);
        }
        Image image = null;
        try
        {
            image = super.createImage(i, j);
        }
        catch(Throwable _ex) { }
        return image;
    }

    public static synchronized Image createImage(Component component, int i, int j)
    {
        for(Enumeration enumeration = images.keys(); enumeration.hasMoreElements();)
        {
            Thread thread = (Thread)enumeration.nextElement();
            if(!thread.isAlive())
                images.remove(thread);
        }

        Thread thread1 = Thread.currentThread();
        Image image = (Image)images.get(thread1);
        Image image1 = createImage(component, image, i, j);
        if(image1 != image)
        {
            images.remove(thread1);
            if(image1 != null)
                images.put(thread1, image1);
        }
        return image1;
    }

    public static synchronized Image createImage(Component component, Image image, int i, int j)
    {
        if(component != null && (component instanceof BaseComponent) && !((BaseComponent)component).clipToScreen)
        {
            i = Math.max(1, i);
            j = Math.max(1, j);
        } else
        {
            Dimension dimension = component.getToolkit().getScreenSize();
            i = Math.max(1, Math.min(i, dimension.width));
            j = Math.max(1, Math.min(j, dimension.height));
        }
        try
        {
            if(image == null || image.getWidth(null) < i || image.getHeight(null) < j)
                image = component.createImage(i, j);
        }
        catch(Throwable _ex) { }
        return image;
    }

    protected void drawBorder(Graphics g)
    {
        if(!intersects(paint_rect, border))
            return;
        if(getBorder() != null)
            getBorder().paintBorder(this, g, 0, 0, getSize().width, getSize().height);
//        else
//            Border.draw(g, border_style, border, 0, 0, getSize().width, getSize().height, getBackground(), getForeground());
    }

    protected void fillBackground(Graphics g)
    {
        g.fillRect(0, 0, getSize().width, getSize().height);
    }

    public Object getAWTLock()
    {
        return getTreeLock();
    }

    public Applet getApplet()
    {
        return applet;
    }

    public static Applet getApplet(Component component)
    {
        if(component == null)
            return null;
        Container container = component.getParent();
        Container container1;
        for(container1 = component.getParent(); container1 != null && !(container1 instanceof Applet);)
        {
            container1 = container;
            container = container != null ? container.getParent() : null;
        }

        return (Applet)container1;
    }

    public AppletContext getAppletContext()
    {
        return getAppletContext(applet);
    }

    public static AppletContext getAppletContext(Applet applet1)
    {
        if(applet1 != null)
            try
            {
                return applet1.getAppletContext();
            }
            catch(Exception _ex) { }
        return null;
    }

    public int getBorderStyle()
    {
        return border_style;
    }

    public int getBorderThickness()
    {
        if(getBorder() != null)
            return getBorder().getBorderInsets(this).top;
        else
            return border;
    }

    public boolean getClipToScreen()
    {
        return clipToScreen;
    }

    public boolean getDoubleBuffer()
    {
        return double_buffer;
    }

    public Graphics getDoubleBufferGraphics()
    {
        synchronized(getAWTLock())
        {
            Image image = dblbuffer_image;
            dblbuffer_image = createImage(this, getSize().width, getSize().height);
            if(dblbuffer_image == null)
                dblbuffer_image_gc = null;
            else
            if(dblbuffer_image != image)
                dblbuffer_image_gc = dblbuffer_image.getGraphics();
            Graphics g = dblbuffer_image_gc;
            return g;
        }
    }

    public Image getDoubleBufferImage()
    {
        return dblbuffer_image;
    }

    public Frame getFrame()
    {
        return getFrame(((Component) (this)));
    }

    public static Frame getFrame(Component component)
    {
        Window window = getWindow(component);
        if((window instanceof Dialog) && (window.getParent() instanceof Window))
            window = (Window)window.getParent();
        return (window instanceof Frame) ? (Frame)window : null;
    }

    public Insets getInsets()
    {
        return insets;
    }

    public int getShadowThickness()
    {
        return getBorderThickness();
    }

    public Object getUserData()
    {
        return userdata;
    }

    public int getUserDataInt()
    {
        return (userdata instanceof Integer) ? ((Integer)userdata).intValue() : 0;
    }

    public static Point getVisibleScreenLoc(Component component, int i, int j, int k, int l)
    {
        Point point = translateToParent(null, component, i, j);
        Dimension dimension = component.getToolkit().getScreenSize();
        point.x = Math.max(0, Math.min(point.x, dimension.width - k));
        point.y = Math.max(0, Math.min(point.y, dimension.height - l));
        return point;
    }

    public static Window getWindow(Component component)
    {
        Object obj = component;
        Component component1;
        for(component1 = component; component1 != null && !(component1 instanceof Window);)
        {
            component1 = ((Component) (obj));
            if(obj == null)
                return null;
            obj = ((Component) (obj)).getParent();
        }

        return (Window)component1;
    }

    public boolean gotFocus102(Event event, Object obj)
    {
        return true;
    }

    public boolean hasFocus()
    {
        return has_focus;
    }

    protected final boolean intersects(Rectangle rectangle, int i)
    {
        if(rectangle == null)
        {
            return true;
        } else
        {
            int j = i;
            int k = i;
            int l = (k + getSize().height) - 2 * i;
            int i1 = (j + getSize().width) - 2 * i;
            return j >= rectangle.x && j <= rectangle.x + rectangle.width || i1 >= rectangle.x && i1 <= rectangle.x + rectangle.width || k >= rectangle.y && k <= rectangle.y + rectangle.height || l >= rectangle.y && l <= rectangle.y + rectangle.height;
        }
    }

    public boolean isRealized()
    {
        return realized;
    }

    public boolean isShowing()
    {
        return JCEnvironment.isJavaOS() ? true : super.isShowing();
    }

    public boolean lostFocus102(Event event, Object obj)
    {
        has_focus = false;
        return true;
    }

    public abstract void paint(Graphics g);

    protected void paintComponent(Graphics g)
    {
    }

    public void paintImmediately(int i, int j, int k, int l)
    {
        super.paintImmediately(i, j, k, l);
    }

    public void printAll(Graphics g)
    {
        boolean flag = double_buffer;
        double_buffer = false;
        super.printAll(g);
        double_buffer = flag;
    }

    protected void processFocusEvent(FocusEvent focusevent)
    {
        Event event = new Event(this, 0L, focusevent.getID(), 0, 0, 0, 0);
        switch(event.id)
        {
        case 1005: // Event.LOST_FOCUS
            lostFocus102(event, this);
            break;

        case 1004: // Event.GOT_FOCUS
            gotFocus102(event, this);
            break;
        }
        super.processFocusEvent(focusevent);
    }

    public void removeNotify()
    {
        super.removeNotify();
        realized = false;
    }

    public void repaint()
    {
        repaint(0, 0, getSize().width, getSize().height);
    }

    public void repaint(int i, int j, int k, int l)
    {
        if(!isRealized() || !isShowing() || k <= 0 || l <= 0 || in_repaint)
            return;
        int i1 = getSize().width;
        int j1 = getSize().height;
        if(i1 <= i || j1 <= j || i + k < 0 || j + l < 0)
            return;
        if(i + k > i1)
            k = i1 - i;
        if(j + l > j1)
            l = j1 - j;
        super.repaint(i, j, k, l);
    }

    public void repaint(Graphics g, int i, int j, int k, int l)
    {
        if(g == null)
            return;
        in_repaint = true;
        g.setClip(i, j, k, l);
        try
        {
            paint(g);
        }
        catch(RuntimeException runtimeexception)
        {
            throw runtimeexception;
        }
        finally
        {
            in_repaint = false;
        }
    }

    public void repaint(Rectangle rectangle)
    {
        repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void setBackground(Color color)
    {
        super.setBackground(color);
        repaint();
    }

    public void setBorderStyle(int i)
    {
        border_style = i;
        doLayout();
        repaint();
    }

    public void setBorderThickness(int i)
    {
        border = i;
        doLayout();
        repaint();
    }

    public void setClipToScreen(boolean flag)
    {
        clipToScreen = flag;
    }

    public void setDoubleBuffer(boolean flag)
    {
        double_buffer = flag;
    }

    public void setForeground(Color color)
    {
        super.setForeground(color);
        repaint();
    }

    public void setInsets(Insets insets1)
    {
        synchronized(this)
        {
            insets = insets1;
        }
        doLayout();
        repaint();
    }

    public void setShadowThickness(int i)
    {
        setBorderThickness(i);
    }

    public void setUserData(Object obj)
    {
        userdata = obj;
    }

    public static Point translateFromParent(Container container, Component component, int i, int j)
    {
        for(; component != null && component != container; component = component.getParent())
        {
            i -= component.getLocation().x;
            j -= component.getLocation().y;
        }

        return new Point(i, j);
    }

    public static Point translateToParent(Container container, Component component, int i, int j)
    {
        for(; component != null && component != container; component = component.getParent())
        {
            i += component.getLocation().x;
            j += component.getLocation().y;
        }

        return new Point(i, j);
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void updateParent()
    {
        if(getParent() != null)
        {
            invalidate();
            getParent().invalidate();
            getParent().validate();
        }
    }

    public static void useSystemColors(boolean flag)
    {
        use_system_colors = flag;
    }

    public void validate()
    {
        if(isValid() || !isRealized())
            return;
        super.validate();
        if(JCEnvironment.getJavaVersion() >= 110 || JCEnvironment.isJavaOS())
            doLayout();
    }

    public static final int NOVALUE = -999;
    public static final char BELL = 7;
    public static final char BS = 8;
    public static final char TAB = 9;
    public static final char ENTER = 10;
    public static final char DELETE = 127;
    public static final char ESC = 27;
    protected transient Applet applet;
    protected transient AppletContext applet_context;
    protected transient boolean in_repaint;
    protected transient boolean needs_layout;
    protected int border;
    protected int border_style;
    protected boolean clipToScreen;
    protected static final Object LOCK = new Object();
    protected boolean double_buffer;
    protected boolean has_focus;
    protected Object userdata;
    protected Insets insets;
    protected boolean do_repaint;
    protected static boolean use_system_colors = false;
    protected transient Rectangle paint_rect;
    protected transient Image dblbuffer_image;
    protected transient Graphics dblbuffer_image_gc;
    protected transient boolean realized;
    protected Rectangle rect;
    protected static Hashtable images = new Hashtable(5);

}
