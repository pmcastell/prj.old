// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCButton.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.io.PrintStream;
import java.util.Vector;
import jclass.base.*;
import jclass.util.*;

// Referenced classes of package jclass.bwt:
//            JCLabel, BWTEnum, BWTUtil, ButtonConverter, 
//            JCActionEvent, JCActionListener, JCButtonEvent, JCButtonListener, 
//            JCComponent

public class JCButton extends JCLabel
{

    public JCButton()
    {
        this(null, ((Applet) (null)), ((String) (null)));
    }

    public JCButton(Object obj)
    {
        this(obj, ((Applet) (null)), ((String) (null)));
    }

    public JCButton(Object obj, Applet applet, String s)
    {
        super(obj, applet, s);
        armed = false;
        inside_button = false;
        is_action_button = false;
        actionListeners = new JCVector(0);
        buttonListeners = new JCVector(0);
        arm_offset = 1;
        if(s == null)
            setName("button" + nameCounter++);
        super.border_style = 9;
        super.traversable = true;
        super.highlight = 1;
        super.border = 2;
        if(obj == null || (obj instanceof String))
            super.insets = new Insets(0, 5, 0, 5);
        else
            super.insets = new Insets(2, 5, 2, 5);
        if(getClass().getName().equals("jclass.bwt.JCButton"))
            getParameters(applet);
        enableEvents(32L);
    }

    public JCButton(String s, Image image, int i)
    {
        this(new JCString(s, image, i), ((Applet) (null)), ((String) (null)));
        setName(s);
    }

    public JCButton(String s, String s1, Applet applet, int i)
    {
        this(null, ((Applet) (null)), ((String) (null)));
        setLabel(new JCString(s, JCComponent.conv.toImage(applet, s1), i));
        super.insets = new Insets(2, 5, 2, 5);
    }

    public void addActionListener(JCActionListener jcactionlistener)
    {
        actionListeners.addUnique(jcactionlistener);
    }

    public void addButtonListener(JCButtonListener jcbuttonlistener)
    {
        buttonListeners.addUnique(jcbuttonlistener);
    }

    public void armAction(Event event)
    {
        old_shadowtype = super.border_style;
        super.border_style = 3;
        armed = true;
        JCButtonEvent jcbuttonevent = buttonListeners.size() <= 0 ? null : new JCButtonEvent(event);
        for(int i = 0; i < buttonListeners.size(); i++)
            ((JCButtonListener)buttonListeners.elementAt(i)).buttonArmBegin(jcbuttonevent);

        Dimension dimension = size();
        paintImmediately(0, 0, dimension.width, dimension.height);
        super.border_style = old_shadowtype;
        for(int j = 0; j < buttonListeners.size(); j++)
            ((JCButtonListener)buttonListeners.elementAt(j)).buttonArmEnd(jcbuttonevent);

    }

    public void clickAction(Event event)
    {
        String s = getActionCommand();
        if(event == null)
            event = new Event(this, 0, s);
        JCActionEvent jcactionevent = new JCActionEvent(this, event.id, s, event.modifiers);
        for(int i = 0; i < actionListeners.size(); i++)
            ((JCActionListener)actionListeners.elementAt(i)).actionPerformed(jcactionevent);

    }

    public void disarmAction(Event event)
    {
        JCButtonEvent jcbuttonevent = buttonListeners.size() <= 0 ? null : new JCButtonEvent(event);
        for(int i = 0; i < buttonListeners.size(); i++)
            ((JCButtonListener)buttonListeners.elementAt(i)).buttonDisarmBegin(jcbuttonevent);

        if(armed && arm_label != null)
            layout(super.label);
        armed = false;
        super.border_style = old_shadowtype;
        Dimension dimension = size();
        paintImmediately(0, 0, dimension.width, dimension.height);
        for(int j = 0; j < buttonListeners.size(); j++)
            ((JCButtonListener)buttonListeners.elementAt(j)).buttonDisarmEnd(jcbuttonevent);

    }

    protected void drawHighlight(Graphics g, boolean flag)
    {
        super.drawHighlight(g, flag);
        byte byte0 = 4;
        byte byte1 = 4;
        int k = size().width;
        int i1 = size().height;
        Color color = flag ? super.highlight_color : getBackground();
        if(color == null)
            color = getForeground();
        g.setColor(color);
        if(flag)
            BWTUtil.drawDashedRect(g, byte0, byte1, k - 2 * byte0 - 1, i1 - 2 * byte1 - 1);
        else
            g.drawRect(byte0, byte1, k - 2 * byte0 - 1, i1 - 2 * byte1 - 1);
        if(is_action_button && super.highlight > 0)
        {
            int i = 0;
            int j = 0;
            int l = size().width;
            int j1 = size().height;
            g.setColor(getHighlightColor() == null ? Color.black : getHighlightColor());
            for(int k1 = 0; k1 < super.highlight;)
            {
                g.drawRect(i, j, l - 1, j1 - 1);
                k1++;
                i++;
                j++;
                l -= 2;
                j1 -= 2;
            }

        }
    }

    protected void drawValue(Graphics g, Object obj)
    {
        if(armed && arm_label != null)
        {
            layout(arm_label);
            g.translate(arm_offset, arm_offset);
            super.drawValue(g, arm_label);
        } else
        {
            if(armed)
                g.translate(arm_offset, arm_offset);
            super.drawValue(g, obj);
        }
        if(armed)
            g.translate(-arm_offset, -arm_offset);
    }

    public String getActionCommand()
    {
        return actionCommand != null ? actionCommand : String.valueOf(String.valueOf(super.label));
    }

    public Object getArmLabel()
    {
        return arm_label;
    }

    public boolean getIsActionButton()
    {
        return is_action_button;
    }

    protected void getParameters()
    {
        super.getParameters();
        ButtonConverter.getParams(this);
    }

    public boolean isArmed()
    {
        return armed;
    }

    public boolean keyDown(Event event, int i)
    {
        super.keyDown(event, i);
        if(event.key == 10 || (char)event.key == ' ')
        {
            armAction(event);
            getToolkit().sync();
            try
            {
                Thread.sleep(50L);
            }
            catch(Exception _ex) { }
            disarmAction(event);
            clickAction(event);
            getToolkit().sync();
            return true;
        } else
        {
            return false;
        }
    }

    public synchronized void layout()
    {
        super.layout();
        if(getPeer() == null)
            return;
        if(BWTUtil.isRight(super.alignment))
            super.label_rect.x -= arm_offset;
        else
        if(BWTUtil.isCenter(super.alignment))
            super.label_rect.x -= arm_offset / 2;
        if(BWTUtil.isMiddle(super.alignment))
            super.label_rect.y -= arm_offset / 2;
        else
        if(BWTUtil.isBottom(super.alignment))
            super.label_rect.y -= arm_offset;
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        if(BWTUtil.getMouseButton(event) != 1)
            return false;
        if(armed)
            return true;
        if(!inside(i, j))
            return false;
        if(!isEnabled())
        {
            return false;
        } else
        {
            super.mouseDown(event, i, j);
            armAction(event);
            return true;
        }
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
        if(inside(i, j) && !inside_button)
            mouseEnter(event, i, j);
        else
        if(!inside(i, j) && inside_button)
            mouseExit(event, i, j);
        return true;
    }

    public boolean mouseEnter(Event event, int i, int j)
    {
        inside_button = true;
        if(!isEnabled())
            return false;
        if(!armed)
        {
            return false;
        } else
        {
            armAction(event);
            return true;
        }
    }

    public boolean mouseExit(Event event, int i, int j)
    {
        inside_button = false;
        if(!armed)
        {
            return false;
        } else
        {
            disarmAction(event);
            armed = true;
            return true;
        }
    }

    public boolean mouseMove(Event event, int i, int j)
    {
        return true;
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if(BWTUtil.getMouseButton(event) != 1)
            return false;
        if(inside(i, j) && armed)
        {
            disarmAction(event);
            clickAction(event);
        } else
        if(armed)
        {
            armed = false;
            repaint();
        }
        return true;
    }

    protected int preferredHeight()
    {
        int i = Math.max(super.label_height + arm_offset, BWTUtil.getHeight(arm_label, this) + arm_offset);
        return Math.max(20, i);
    }

    protected int preferredWidth()
    {
        int i = Math.max(super.label_width + arm_offset, BWTUtil.getWidth(arm_label, this) + arm_offset);
        return Math.max(20, i);
    }

    public void removeActionListener(JCActionListener jcactionlistener)
    {
        actionListeners.removeElement(jcactionlistener);
    }

    public void removeButtonListener(JCButtonListener jcbuttonlistener)
    {
        buttonListeners.removeElement(jcbuttonlistener);
    }

    public void setActionCommand(String s)
    {
        actionCommand = s;
    }

    public void setArmLabel(Object obj)
    {
        arm_label = obj;
        if(armed)
        {
            layout();
            repaint();
        }
    }

    public void setIsActionButton(boolean flag)
    {
        if(flag != is_action_button)
        {
            is_action_button = flag;
            repaint();
        }
    }

    Object arm_label;
    boolean armed;
    protected int old_shadowtype;
    String actionCommand;
    boolean inside_button;
    boolean is_action_button;
    private static final boolean TRACE = false;
    protected JCVector actionListeners;
    protected JCVector buttonListeners;
    private static final String base = "button";
    private static int nameCounter = 0;
    protected int arm_offset;

}
