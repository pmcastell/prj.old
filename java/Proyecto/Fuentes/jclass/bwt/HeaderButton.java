// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCHeader.java

package jclass.bwt;

import java.awt.*;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;
import jclass.util.JCqsort;

// Referenced classes of package jclass.bwt:
//            JCButton, HeaderLabel, JCHeader

class HeaderButton extends JCButton
{

    HeaderButton(JCHeader jcheader, Object obj)
    {
        super(obj, null, null);
        direction = 0;
        header = jcheader;
        super.border = super.highlight = 1;
        super.traversable = false;
        super.insets.left = super.insets.right = 5;
        enableEvents(32L);
    }

    protected void drawHighlight(Graphics g, boolean flag)
    {
        g.setColor(super.highlight_color == null ? Color.black : super.highlight_color);
        g.drawRect(super.highlight - 1, super.highlight - 1, (size().width - 2 * super.highlight) + 1, (size().height - 2 * super.highlight) + 1);
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        return header.mouseDown(event) || super.mouseDown(event, i, j);
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
        return header.mouseDrag(event);
    }

    public boolean mouseExit(Event event, int i, int j)
    {
        header.mouseExit(event);
        return super.mouseExit(event, i, j);
    }

    public boolean mouseMove(Event event, int i, int j)
    {
        return header.mouseMove(event);
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        header.mouseUp(event);
        return super.mouseUp(event, i, j);
    }

    JCHeader header;
    int direction;
}
