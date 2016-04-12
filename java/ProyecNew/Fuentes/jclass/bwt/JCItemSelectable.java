// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCItemSelectable.java

package jclass.bwt;


// Referenced classes of package jclass.bwt:
//            JCItemListener

public interface JCItemSelectable
{

    public abstract void addItemListener(JCItemListener jcitemlistener);

    public abstract Object[] getSelectedObjects();

    public abstract void removeItemListener(JCItemListener jcitemlistener);
}
