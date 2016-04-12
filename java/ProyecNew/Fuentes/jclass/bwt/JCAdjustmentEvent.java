// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCAdjustmentEvent.java

package jclass.bwt;

import java.awt.Adjustable;
import java.awt.Event;
import java.awt.event.AdjustmentEvent;
import java.util.EventObject;

// Referenced classes of package jclass.bwt:
//            AdjustableObject

public class JCAdjustmentEvent extends AdjustmentEvent
{

    public JCAdjustmentEvent(Object obj, int i, int j, int k)
    {
        super(adj, i, j, k);
        super.source = obj;
    }

    public static final int ADJUSTMENT_VALUE_CHANGED = 601;
    public static final int UNIT_INCREMENT = 1;
    public static final int UNIT_DECREMENT = 2;
    public static final int BLOCK_DECREMENT = 3;
    public static final int BLOCK_INCREMENT = 4;
    public static final int TRACK = 5;
    private static Adjustable adj = new AdjustableObject();

}
