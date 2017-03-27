// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCTextCursorListener.java

package jclass.bwt;


// Referenced classes of package jclass.bwt:
//            JCEventListener, JCTextCursorEvent

public interface JCTextCursorListener
    extends JCEventListener
{

    public abstract void textCursorMoveBegin(JCTextCursorEvent jctextcursorevent);

    public abstract void textCursorMoveEnd(JCTextCursorEvent jctextcursorevent);
}
