// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CellEditorEvent.java

package jclass.cell;

import java.awt.AWTEvent;
import java.util.EventObject;

public class CellEditorEvent extends EventObject
{

    public CellEditorEvent(AWTEvent awtevent)
    {
        super(awtevent.getSource());
        event = awtevent;
    }

    public CellEditorEvent(Object obj)
    {
        super(obj);
    }

    public AWTEvent getEvent()
    {
        return event;
    }

    protected AWTEvent event;
}
