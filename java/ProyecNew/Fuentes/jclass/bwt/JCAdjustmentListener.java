// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCAdjustmentListener.java

package jclass.bwt;


// Referenced classes of package jclass.bwt:
//            JCEventListener, JCAdjustmentEvent

public interface JCAdjustmentListener
    extends JCEventListener
{

    public abstract void adjustmentValueChanged(JCAdjustmentEvent jcadjustmentevent);
}
