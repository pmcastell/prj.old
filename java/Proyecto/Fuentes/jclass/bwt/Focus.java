// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Focus.java

package jclass.bwt;

import java.awt.Component;
import java.awt.Container;
import java.util.Vector;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;

// Referenced classes of package jclass.bwt:
//            BWTUtil, JCComponent

class Focus
{

    Focus()
    {
    }

    private static final int countChildren(Component component, Vector vector)
    {
        if(component == null)
            return 0;
        if(!(component instanceof Container))
            if(isTraversable(component))
            {
                if(vector != null)
                    vector.addElement(component);
                return 1;
            } else
            {
                return 0;
            }
        int i = 1;
        Component acomponent[] = ((Container)component).getComponents();
        for(int j = 0; j < acomponent.length; j++)
            i += countChildren(acomponent[j], vector);

        return i;
    }

    static JCComponent findFocus(Container container)
    {
        if(container == null)
            return null;
        Component acomponent[] = container.getComponents();
        for(int i = 0; i < acomponent.length; i++)
        {
            if(acomponent[i] instanceof Container)
                return findFocus((Container)acomponent[i]);
            if((acomponent[i] instanceof JCComponent) && ((JCComponent)acomponent[i]).hasFocus())
                return (JCComponent)acomponent[i];
        }

        return null;
    }

    static Component getFirstChild(Container container)
    {
        if(!container.isVisible())
            return null;
        Component acomponent[] = container.getComponents();
        for(int i = 0; i < acomponent.length; i++)
            if(acomponent[i] instanceof Container)
            {
                Component component = getFirstChild((Container)acomponent[i]);
                if(component != null)
                    return component;
            } else
            if(isTraversable(acomponent[i]))
                return acomponent[i];

        return null;
    }

    static Component getLastChild(Container container)
    {
        Component acomponent[] = container.getComponents();
        int i;
        for(i = acomponent.length - 1; i >= 0; i--)
        {
            if(acomponent[i] instanceof Container)
                return getLastChild((Container)acomponent[i]);
            if(isTraversable(acomponent[i]))
                break;
        }

        return i < 0 ? null : acomponent[i];
    }

    private static Component getNextChild(Container container, Component component)
    {
        Vector vector = new Vector(countChildren(container, null));
        countChildren(container, vector);
        int i = vector.indexOf(component);
        for(int j = i + 1; j < vector.size(); j++)
        {
            Component component1 = (Component)vector.elementAt(j);
            if(component1.getParent() != component.getParent())
                return component1;
        }

        return null;
    }

    private static Component getPreviousChild(Container container, Component component)
    {
        Vector vector = new Vector(countChildren(container, null));
        countChildren(container, vector);
        int i = vector.indexOf(component);
        for(int j = i - 1; j >= 0; j--)
        {
            Component component1 = (Component)vector.elementAt(j);
            if(component1.getParent() != component.getParent())
                return component1;
        }

        return null;
    }

    private static boolean isTraversable(Component component)
    {
        if(component instanceof JCComponent)
            return ((JCComponent)component).isFocusTraversable();
        if(component instanceof Container)
        {
            Component acomponent[] = ((Container)component).getComponents();
            for(int i = 0; i < acomponent.length; i++)
                if(isTraversable(acomponent[i]))
                    return true;

            return false;
        } else
        {
            return component.isShowing() && component.isEnabled();
        }
    }

    static void nextFocus(Component component)
    {
        if(component.getParent() == null)
            return;
        Component acomponent[] = component.getParent().getComponents();
        if(acomponent.length == 1)
            return;
        int k;
        for(k = 0; k < acomponent.length; k++)
            if(acomponent[k] == component)
                break;

        component = null;
        for(int i = k + 1; component == null && i < acomponent.length; i++)
            if(isTraversable(acomponent[i]))
                component = acomponent[i];

        for(int j = 0; component == null && j < k; j++)
            if(isTraversable(acomponent[j]))
                component = acomponent[j];

        if(component != null)
            component.requestFocus();
    }

    static void nextFocus(Container container, Component component)
    {
        java.awt.Frame frame = BWTUtil.getFrame(container);
        Component component1 = getNextChild(frame, component);
        if(component1 == null)
            component1 = getFirstChild(frame);
        if(component1 != null)
            component1.requestFocus();
    }

    static void previousFocus(Component component)
    {
        if(component.getParent() == null)
            return;
        Component acomponent[] = component.getParent().getComponents();
        if(acomponent.length == 1)
            return;
        int k;
        for(k = 0; k < acomponent.length; k++)
            if(acomponent[k] == component)
                break;

        component = null;
        for(int i = k - 1; component == null && i >= 0; i--)
            if(isTraversable(acomponent[i]))
                component = acomponent[i];

        for(int j = acomponent.length - 1; component == null && j != k; j--)
            if(isTraversable(acomponent[j]))
                component = acomponent[j];

        if(component != null)
            component.requestFocus();
    }

    static void previousFocus(Container container, Component component)
    {
        java.awt.Frame frame = BWTUtil.getFrame(container);
        Component component1 = getPreviousChild(frame, component);
        if(component1 == null)
            component1 = getLastChild(frame);
        if(component1 != null)
            component1.requestFocus();
    }
}
