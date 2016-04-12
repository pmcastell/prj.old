// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCListListener.java

package jclass.bwt;


// Referenced classes of package jclass.bwt:
//            JCItemListener, JCListEvent

public interface JCListListener
    extends JCItemListener
{

    public abstract void listItemSelectBegin(JCListEvent jclistevent);

    public abstract void listItemSelectEnd(JCListEvent jclistevent);
}
