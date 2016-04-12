// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCContainer.java

package jclass.bwt;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.border.Border;
import jclass.base.BaseComponent;
import jclass.util.*;

// Referenced classes of package jclass.bwt:
//            BWTEnum, BWTUtil, ContainerConverter, JCButton, 
//            JCComponent, JCActionEvent, JCActionListener

public class JCContainer extends JComponent
{
    protected class ContainerFocusListener
        implements FocusListener
    {

        public void focusGained(FocusEvent focusevent)
        {
            has_focus = true;
        }

        public void focusLost(FocusEvent focusevent)
        {
            has_focus = false;
        }

        protected ContainerFocusListener()
        {
        }
    }

    class ActionButtonListener
        implements FocusListener, KeyListener, ContainerListener
    {

        public void componentAdded(ContainerEvent containerevent)
        {
            installListenerOnComponent(containerevent.getChild());
        }

        public void componentRemoved(ContainerEvent containerevent)
        {
        }

        public void focusGained(FocusEvent focusevent)
        {
            Component component = (Component)focusevent.getSource();
            if(component instanceof JCButton)
            {
                if(component != current_action_button)
                {
                    if(current_action_button != null)
                        current_action_button.setIsActionButton(false);
                    current_action_button = (JCButton)component;
                    current_action_button.setIsActionButton(true);
                }
            } else
            if(current_action_button != action_button)
            {
                if(current_action_button != null)
                    current_action_button.setIsActionButton(false);
                current_action_button = action_button;
                if(current_action_button != null)
                    current_action_button.setIsActionButton(true);
            }
        }

        public void focusLost(FocusEvent focusevent)
        {
        }

        private void invokeActionListeners(KeyEvent keyevent)
        {
            if(current_action_button == null)
                return;
            JCActionEvent jcactionevent = new JCActionEvent(current_action_button, keyevent.getID(), current_action_button.getActionCommand(), keyevent.getModifiers());
            for(int i = 0; i < current_action_button.actionListeners.size(); i++)
                ((JCActionListener)current_action_button.actionListeners.elementAt(i)).actionPerformed(jcactionevent);

        }

        public void keyPressed(KeyEvent keyevent)
        {
            Component component = (Component)keyevent.getSource();
            if(component instanceof JCButton)
                return;
            if(keyevent.getKeyCode() == 10)
                invokeActionListeners(keyevent);
        }

        public void keyReleased(KeyEvent keyevent)
        {
        }

        public void keyTyped(KeyEvent keyevent)
        {
        }

        ActionButtonListener()
        {
        }
    }


    public JCContainer()
    {
        this(null, null);
    }

    public JCContainer(Applet applet1, String s)
    {
        action_button = null;
        current_action_button = null;
        swing_border = null;
        pref_width = -999;
        pref_height = -999;
        in_pref_size = false;
        double_buffer = false;
        action_button_listener = null;
        listeners_installed = false;
        applet = applet1;
        setName(s);
        addFocusListener(new ContainerFocusListener());
        setLayout(new FlowLayout());
        if(getClass().getName().equals("jclass.bwt.JCContainer"))
            getParameters(applet1);
    }

    public Component add(Component component)
    {
        return component.getParent() == this ? component : super.add(component);
    }

    protected void addImpl(Component component, Object obj, int i)
    {
        if(component.getParent() != this)
            super.addImpl(component, obj, i);
        if(swing_border != null && (component instanceof JComponent))
            ((JComponent)component).setBorder(swing_border);
    }

    public void addNotify()
    {
        if(getPeer() == null)
            super.addNotify();
        if(applet == null)
            applet = BWTUtil.getApplet(this);
        enableEvents(16L);
        if(!isEnabled())
        {
            Component acomponent[] = getComponents();
            for(int i = 0; i < acomponent.length; i++)
                acomponent[i].disable();

        }
    }

    public void disable()
    {
        if(!isEnabled())
            return;
        super.disable();
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
            acomponent[i].disable();

    }

    public void enable()
    {
        if(isEnabled())
            return;
        super.enable();
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
            acomponent[i].enable();

    }

    protected void fillBackground(Graphics g)
    {
        g.fillRect(0, 0, size().width, size().height);
    }

    public String getAbout()
    {
        return "JClass BWT by KL Group (www.klg.com)";
    }

    public JCButton getActionButton()
    {
        return action_button;
    }

    public AppletContext getAppletContext()
    {
        return BWTUtil.getAppletContext(applet);
    }

    public Component getComponent(int i)
    {
        Component acomponent[] = getComponents();
        return i >= acomponent.length ? null : acomponent[i];
    }

    public int getComponent(Component component)
    {
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
            if(acomponent[i] == component)
                return i;

        return -999;
    }

    public static JCConverter getConverter()
    {
        return conv;
    }

    protected synchronized Graphics getDoubleBufferGraphics()
    {
        dblbuffer_image = BWTUtil.createImage(this, size().width, size().height);
        return dblbuffer_image == null ? null : dblbuffer_image.getGraphics();
    }

    public Insets getInsets()
    {
        return insets == null ? super.getInsets() : insets;
    }

    public Rectangle getPaintRect()
    {
        return paint_rect;
    }

    String getParam(String s)
    {
        return conv.getParam(applet, this, getName(), s);
    }

    protected void getParameters()
    {
        ContainerConverter.getParams(this);
    }

    public void getParameters(Applet applet1)
    {
        getParameters(applet1, null);
    }

    public void getParameters(Applet applet1, String s)
    {
        applet = applet1;
        if(s == null)
            s = getParam("paramFile");
        if(s != null)
            JCUtilConverter.setParamSource(this, JCFile.read(applet1, s));
        if(s != null || getAppletContext() != null)
        {
            getParameters();
            if(getPeer() != null)
            {
                addNotify();
                repaint();
            }
        }
    }

    public Dimension getPreferredSize()
    {
        if(in_pref_size)
        {
            return super.getPreferredSize();
        } else
        {
            in_pref_size = true;
            int i = pref_width == -999 ? preferredWidth() : pref_width;
            i = i >= 0 ? i + JCComponent.getInsets(this).left + JCComponent.getInsets(this).right : Math.max(super.getPreferredSize().width, 20);
            int j = pref_height == -999 ? preferredHeight() : pref_height;
            j = j >= 0 ? j + JCComponent.getInsets(this).top + JCComponent.getInsets(this).bottom : Math.max(super.getPreferredSize().height, 20);
            in_pref_size = false;
            return new Dimension(i, j);
        }
    }

    public Object getUserData()
    {
        return userdata;
    }

    public String getVersion()
    {
        return version;
    }

    public boolean hasFocus()
    {
        return has_focus;
    }

    private void installListener(Container container)
    {
        Component acomponent[] = container.getComponents();
        for(int i = 0; i < acomponent.length; i++)
        {
            Component component = acomponent[i];
            installListenerOnComponent(component);
        }

    }

    private void installListenerOnComponent(Component component)
    {
        if(component instanceof Container)
        {
            ((Container)component).addContainerListener(action_button_listener);
            installListener((Container)component);
        }
        component.addFocusListener(action_button_listener);
        if(!(component instanceof JCButton))
            component.addKeyListener(action_button_listener);
    }

    private void installListeners()
    {
        if(action_button_listener == null)
            action_button_listener = new ActionButtonListener();
        installListener(this);
    }

    public boolean isShowing()
    {
        return JCEnvironment.isJavaOS() ? true : super.isShowing();
    }

    public void paint(Graphics g)
    {
        if(g == null || getBackground() == null || getParent() == null)
            return;
        Graphics g1 = g;
        Rectangle rectangle = g.getClipRect();
        if(rectangle == null)
            rectangle = new Rectangle(size());
        if(rectangle.width == 0 || rectangle.height == 0)
            return;
        paint_rect = rectangle;
        if(double_buffer)
        {
            Graphics g2 = getDoubleBufferGraphics();
            if(g2 == null)
            {
                double_buffer = false;
            } else
            {
                g = g2;
                if(rectangle != null)
                    g.clipRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            }
        }
        g.setColor(getBackground());
        fillBackground(g);
        if(!g.getColor().equals(getBackground()))
            g.setColor(getBackground());
        paintInterior(g);
        if(double_buffer)
        {
            g.dispose();
            g1.drawImage(dblbuffer_image, 0, 0, null);
        }
        if(g1 != null)
            super.paint(g1);
    }

    public void paintInterior(Graphics g)
    {
    }

    protected int preferredHeight()
    {
        return -1;
    }

    protected int preferredWidth()
    {
        return -1;
    }

    public void printAll(Graphics g)
    {
        boolean flag = double_buffer;
        double_buffer = false;
        super.printAll(g);
        double_buffer = flag;
    }

    protected void processMouseEvent(MouseEvent mouseevent)
    {
        Event event = new Event(this, mouseevent.getWhen(), mouseevent.getID(), mouseevent.getX(), mouseevent.getY(), 0, mouseevent.getModifiers());
        event.clickCount = mouseevent.getClickCount();
        switch(event.id)
        {
        case 501: // Event.MOUSE_DOWN
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

    public void repaint(int i, int j, int k, int l)
    {
        Graphics g = null;
        synchronized(this)
        {
            if(getPeer() == null || !isShowing() || k <= 0 || l <= 0)
                return;
            int i1 = size().width;
            int j1 = size().height;
            if(i1 <= i || j1 <= j || i + k < 0 || j + l < 0)
                return;
            if(i + k > i1)
                k = i1 - i;
            if(j + l > j1)
                l = j1 - j;
            g = getGraphics();
            g.clipRect(i, j, k, l);
        }
        paint(g);
        g.dispose();
    }

    public void repaint(Rectangle rectangle)
    {
        repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void requestFocus()
    {
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
        {
            Component component = acomponent[i];
            if(component.isFocusTraversable())
                component.requestFocus();
        }

    }

    public void setAbout(String s)
    {
    }

    public void setActionButton(JCButton jcbutton)
    {
        action_button = jcbutton;
        if(!listeners_installed)
            installListeners();
        if(current_action_button != action_button)
        {
            if(current_action_button != null)
                current_action_button.setIsActionButton(false);
            current_action_button = action_button;
            if(current_action_button != null)
                current_action_button.setIsActionButton(true);
        }
    }

    public void setBackground(Color color)
    {
        super.setBackground(color);
        repaint();
    }

    public void setBounds(int i, int j, int k, int l)
    {
        synchronized(this)
        {
            if(i == location().x && j == location().y && k == size().width && l == size().height)
                return;
            if(k < 0 || l < 0)
                return;
        }
        super.setBounds(i, j, k, l);
        invalidate();
        validate();
    }

    protected void setChildrenBorder(Border border)
    {
        for(int i = 0; i < getComponentCount(); i++)
        {
            Component component = getComponent(i);
            if(component instanceof JComponent)
                ((JComponent)component).setBorder(border);
        }

    }

    public static void setConverter(JCConverter jcconverter)
    {
        conv = jcconverter;
    }

    public void setCursor(int i)
    {
        BWTUtil.setCursor(this, i);
    }

    public void setForeground(Color color)
    {
        super.setForeground(color);
        repaint();
    }

    public void setInsets(Insets insets1)
    {
        insets = insets1;
    }

    public void setPreferredSize(int i, int j)
    {
        pref_width = i;
        pref_height = j;
    }

    public void setUserData(Object obj)
    {
        userdata = obj;
    }

    public void setVersion(String s)
    {
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
        BaseComponent.useSystemColors(flag);
    }

    public static final String about = "JClass BWT by KL Group (www.klg.com)";
    public static final String version;
    protected transient Applet applet;
    protected transient Image dblbuffer_image;
    protected JCButton action_button;
    protected JCButton current_action_button;
    protected Border swing_border;
    static JCConverter conv = new JCConverter();
    int pref_width;
    int pref_height;
    Object userdata;
    Insets insets;
    transient boolean in_pref_size;
    boolean double_buffer;
    transient Rectangle paint_rect;
    protected boolean has_focus;
    private ActionButtonListener action_button_listener;
    private boolean listeners_installed;

    static 
    {
        version = JCComponent.version;
    }

}
