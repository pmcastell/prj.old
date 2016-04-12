// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCComponent.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import javax.swing.JComponent;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;
import jclass.util.*;

// Referenced classes of package jclass.bwt:
//            BWTUtil, ComponentConverter, Focus, JCVersion

public abstract class JCComponent extends TransientComponent
{

    public JCComponent()
    {
        this(null, null);
    }

    public JCComponent(Applet applet, String s)
    {
        super.applet = applet;
        setName(s);
        if(getClass().getName().equals("jclass.bwt.JCComponent"))
            getParameters(applet);
    }

    public void addNotify()
    {
        super.addNotify();
    }

    public String getAbout()
    {
        return "JClass BWT by KL Group (www.klg.com)";
    }

    public static JCConverter getConverter()
    {
        return conv;
    }

    public static Insets getInsets(Container container)
    {
        return container.getInsets();
    }

    public static Dimension getMinimumSize(Component component)
    {
        return component.getMinimumSize();
    }

    String getParam(String s)
    {
        return conv.getParam(super.applet, this, getName(), s);
    }

    protected void getParameters()
    {
        ComponentConverter.getParams(this);
    }

    public void getParameters(Applet applet)
    {
        getParameters(applet, null);
    }

    public void getParameters(Applet applet, String s)
    {
        super.applet = applet;
        if(s == null)
            s = getParam("paramFile");
        if(s != null)
            JCUtilConverter.setParamSource(this, JCFile.read(applet, s));
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

    public static Dimension getPreferredSize(Component component)
    {
        return component.getPreferredSize();
    }

    public int getShadowThickness()
    {
        return getBorderThickness();
    }

    public static Dimension getSize(Container container)
    {
        return container.getSize();
    }

    public String getVersion()
    {
        return version;
    }

    public boolean keyDown(Event event, int i)
    {
        if(event.key == 1004 || event.key == 1006)
            Focus.previousFocus(this);
        else
        if(event.key == 1005 || event.key == 1007)
            Focus.nextFocus(this);
        return false;
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        TransientComponent.mouse_down_event_timestamp = event.when;
        if(event.when - TransientComponent.popdown_event_timestamp < 50L && JCEnvironment.getOS() != 1)
            return true;
        if(BWTUtil.getMouseButton(event) == 1 && isFocusTraversable())
            requestFocus();
        return false;
    }

    public void requestFocus()
    {
        super.requestFocus();
        if(isFocusTraversable())
            handleEvent(new Event(this, 1004, null));
    }

    public void setAbout(String s)
    {
    }

    public void setBackground(Color color)
    {
        super.setBackground(color);
    }

    public static void setBounds(Component component, int i, int j, int k, int l)
    {
        component.setBounds(i, j, k, l);
    }

    public static void setBounds(Rectangle rectangle, int i, int j, int k, int l)
    {
        rectangle.setBounds(i, j, k, l);
    }

    public static void setConverter(JCConverter jcconverter)
    {
        conv = jcconverter;
    }

    public void setCursor(int i)
    {
        BWTUtil.setCursor(this, i);
    }

    public void setDoubleBuffer(boolean flag)
    {
        super.setDoubleBuffer(flag);
    }

    public void setFont(Font font)
    {
        super.setFont(font);
    }

    public void setForeground(Color color)
    {
        super.setForeground(color);
    }

    public void setHighlightColor(Color color)
    {
        super.setHighlightColor(color);
    }

    public void setHighlightThickness(int i)
    {
        super.setHighlightThickness(i);
    }

    public void setInsets(Insets insets)
    {
        super.setInsets(insets);
    }

    public void setVersion(String s)
    {
    }

    public static final String version = JCVersion.getVersionString();
    public static final String about = "JClass BWT by KL Group (www.klg.com)";
    static JCConverter conv = new JCConverter();

}
