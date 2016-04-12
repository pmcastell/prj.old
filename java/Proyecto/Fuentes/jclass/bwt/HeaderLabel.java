// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCHeader.java

package jclass.bwt;

import java.awt.*;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;

// Referenced classes of package jclass.bwt:
//            JCLabel, HeaderButton, JCHeader

class HeaderLabel extends JCLabel
{

    HeaderLabel(JCHeader jcheader, Object obj)
    {
        super(obj, null, null);
        header = jcheader;
        super.insets.left = super.insets.right = 5;
        super.border = super.highlight = 1;
        enableEvents(32L);
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        return header.mouseDown(event);
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
        return header.mouseDrag(event);
    }

    public boolean mouseExit(Event event, int i, int j)
    {
        return header.mouseExit(event);
    }

    public boolean mouseMove(Event event, int i, int j)
    {
        return header.mouseMove(event);
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        return header.mouseUp(event);
    }

    JCHeader header;
}
