// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Viewport.java

package jclass.bwt;

import java.awt.*;
import javax.swing.JComponent;

// Referenced classes of package jclass.bwt:
//            JCComponent

class Viewport extends JComponent
{

    Viewport()
    {
        setLayout(null);
    }

    public Component add(Component component)
    {
        if(child != null)
            remove(child);
        super.add(child = component);
        return component;
    }

    public Component add(Component component, int i)
    {
        if(child != null)
            remove(child);
        super.add(child = component, i);
        return component;
    }

    public void add(Component component, Object obj)
    {
        if(child != null)
            remove(child);
        super.add(child = component, obj);
    }

    public void add(Component component, Object obj, int i)
    {
        if(child != null)
            remove(child);
        super.add(child = component, obj, i);
    }

    public Component add(String s, Component component)
    {
        if(child != null)
            remove(child);
        super.add(s, child = component);
        return component;
    }

    public void addNotify()
    {
        super.addNotify();
        if(child != null)
            child.resize(JCComponent.getPreferredSize(child).width, JCComponent.getPreferredSize(child).height);
    }

    public Dimension getPreferredSize()
    {
        return child == null ? super.getPreferredSize() : JCComponent.getPreferredSize(child);
    }

    public void scrollHorizontal(int i)
    {
        if(child != null)
            child.move(-i, child.location().y);
    }

    public void scrollVertical(int i)
    {
        if(child != null)
            child.move(child.location().x, -i);
    }

    public void setBounds(int i, int j, int k, int l)
    {
        synchronized(this)
        {
            if(i == location().x && j == location().y && k == size().width && l == size().height)
                return;
        }
        super.setBounds(i, j, k, l);
        if(child != null)
            child.resize(Math.max(k, child.size().width), Math.max(l, child.size().height));
    }

    Component child;
    int horiz_origin;
    int vert_origin;
}
