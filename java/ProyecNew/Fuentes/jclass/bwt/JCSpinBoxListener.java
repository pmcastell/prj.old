// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCSpinBoxListener.java

package jclass.bwt;


// Referenced classes of package jclass.bwt:
//            JCEventListener, JCSpinBoxEvent

public interface JCSpinBoxListener
    extends JCEventListener
{

    public abstract void spinBoxChangeBegin(JCSpinBoxEvent jcspinboxevent);

    public abstract void spinBoxChangeEnd(JCSpinBoxEvent jcspinboxevent);
}
