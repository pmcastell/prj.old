// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCListEvent.java

package jclass.bwt;

import java.awt.Event;
import java.awt.event.ItemEvent;

// Referenced classes of package jclass.bwt:
//            JCItemEvent, JCListInterface

public class JCListEvent extends JCItemEvent
{

    public JCListEvent(JCListInterface jclistinterface, Event event1, int i, int j, int k)
    {
        super(jclistinterface, 701, jclistinterface.getItem(i), k);
        doit = true;
        event = event1;
        row = i;
        type = j;
    }

    public boolean getAllowSelection()
    {
        return doit;
    }

    public int getRow()
    {
        return row;
    }

    public Event getSourceEvent()
    {
        return event;
    }

    public int getType()
    {
        return type;
    }

    public void setAllowSelection(boolean flag)
    {
        doit = flag;
    }

    public static final int INITIAL = 0;
    public static final int MODIFICATION = 1;
    public static final int ADDITION = 2;
    Event event;
    int row;
    int type;
    boolean doit;
}
