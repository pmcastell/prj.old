// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCItemListener.java

package jclass.bwt;


// Referenced classes of package jclass.bwt:
//            JCEventListener, JCItemEvent

public interface JCItemListener
    extends JCEventListener
{

    public abstract void itemStateChanged(JCItemEvent jcitemevent);
}
