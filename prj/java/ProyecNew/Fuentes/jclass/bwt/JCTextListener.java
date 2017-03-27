// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCTextListener.java

package jclass.bwt;


// Referenced classes of package jclass.bwt:
//            JCEventListener, JCTextEvent

public interface JCTextListener
    extends JCEventListener
{

    public abstract void textValueChangeBegin(JCTextEvent jctextevent);

    public abstract void textValueChangeEnd(JCTextEvent jctextevent);
}
