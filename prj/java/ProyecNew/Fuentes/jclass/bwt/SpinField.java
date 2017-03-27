// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCSpinBox.java

package jclass.bwt;

import java.awt.*;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;

// Referenced classes of package jclass.bwt:
//            JCTextField, JCSpinBox, JCTextComponent, SpinArrowButton

class SpinField extends JCTextField
{

    SpinField(JCSpinBox jcspinbox)
    {
        spin = jcspinbox;
    }

    protected void drawHighlight(Graphics g, boolean flag)
    {
        if(super.highlight == 0)
            return;
        if(!intersects(super.paint_rect, super.highlight))
            return;
        int i = 0;
        int j = 0;
        int k = size().width;
        int l = size().height;
        Color color = flag ? super.highlight_color : getBackground();
        if(color == null)
            color = Color.black;
        g.setColor(color);
        for(int i1 = 0; i1 < super.highlight;)
        {
            g.drawRect(i, j, k - 1, l - 1);
            i1++;
            i++;
            j++;
            k -= 2;
            l -= 2;
        }

    }

    protected boolean insertChar(Event event, int i)
    {
        if(spin.validateKey((char)i))
            return super.insertChar(event, i);
        else
            return false;
    }

    public boolean keyDown(Event event, int i)
    {
        if(i == 1004 || i == 1005)
        {
            spin.setTextAction(i != 1004 ? -1 : 1);
            return true;
        } else
        {
            return super.keyDown(event, i);
        }
    }

    JCSpinBox spin;
}
