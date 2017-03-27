// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCTextCursorEvent.java

package jclass.bwt;


// Referenced classes of package jclass.bwt:
//            JCAWTEvent, JCTextComponent

public class JCTextCursorEvent extends JCAWTEvent
{

    public JCTextCursorEvent(JCTextComponent jctextcomponent, int i, int j)
    {
        super(jctextcomponent, 0);
        doit = true;
        current_pos = i;
        new_pos = j;
    }

    public boolean getAllowMovement()
    {
        return doit;
    }

    public int getCurrentPosition()
    {
        return current_pos;
    }

    public int getNewPosition()
    {
        return new_pos;
    }

    public String paramString()
    {
        return "current=" + current_pos + " new=" + new_pos;
    }

    public void setAllowMovement(boolean flag)
    {
        doit = flag;
    }

    public void setNewPosition(int i)
    {
        new_pos = i;
    }

    int current_pos;
    int new_pos;
    public boolean doit;
}
