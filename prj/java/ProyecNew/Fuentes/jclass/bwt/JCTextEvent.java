// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCTextEvent.java

package jclass.bwt;

import java.awt.event.TextEvent;

// Referenced classes of package jclass.bwt:
//            JCTextComponent

public class JCTextEvent extends TextEvent
{

    public JCTextEvent(JCTextComponent jctextcomponent, int i, int j, String s)
    {
        super(jctextcomponent, 900);
        doit = true;
        start = i;
        end = j;
        text = s;
    }

    public boolean getAllowChange()
    {
        return doit;
    }

    public int getEndPosition()
    {
        return end;
    }

    public int getStartPosition()
    {
        return start;
    }

    public String getText()
    {
        return text;
    }

    public boolean isDeletion()
    {
        return is_deletion;
    }

    public String paramString()
    {
        return "text=" + text + " start=" + start + " end=" + end;
    }

    public void setAllowChange(boolean flag)
    {
        doit = flag;
    }

    public void setEndPosition(int i)
    {
        end = i;
    }

    public void setStartPosition(int i)
    {
        start = i;
    }

    public void setText(String s)
    {
        text = s;
    }

    int start;
    int end;
    String text;
    boolean doit;
    boolean is_deletion;
}
