// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCButtonListener.java

package jclass.bwt;


// Referenced classes of package jclass.bwt:
//            JCEventListener, JCButtonEvent

public interface JCButtonListener
    extends JCEventListener
{

    public abstract void buttonArmBegin(JCButtonEvent jcbuttonevent);

    public abstract void buttonArmEnd(JCButtonEvent jcbuttonevent);

    public abstract void buttonDisarmBegin(JCButtonEvent jcbuttonevent);

    public abstract void buttonDisarmEnd(JCButtonEvent jcbuttonevent);
}
