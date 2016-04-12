// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCSpinBoxEvent.java

package jclass.bwt;

import java.awt.event.ActionEvent;

// Referenced classes of package jclass.bwt:
//            JCAWTEvent

public class JCSpinBoxEvent extends JCAWTEvent
{

    public JCSpinBoxEvent(Object obj, int i, Object obj1)
    {
        super(obj, 1001);
        doit = true;
        position = i;
        value = obj1;
    }

    public boolean getAllowChange()
    {
        return doit;
    }

    public int getPosition()
    {
        return position;
    }

    public Object getValue()
    {
        return value;
    }

    public void setAllowChange(boolean flag)
    {
        doit = flag;
    }

    public void setValue(Object obj)
    {
        value = obj;
    }

    int position;
    Object value;
    boolean doit;
}
