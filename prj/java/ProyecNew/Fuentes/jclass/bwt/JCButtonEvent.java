// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCButtonEvent.java

package jclass.bwt;

import java.awt.Event;

// Referenced classes of package jclass.bwt:
//            JCAWTEvent

public class JCButtonEvent extends JCAWTEvent
{

    public JCButtonEvent(Event event1)
    {
        super(event1.target, event1.id);
        event = event1;
    }

    public Event getSourceEvent()
    {
        return event;
    }

    Event event;
}
