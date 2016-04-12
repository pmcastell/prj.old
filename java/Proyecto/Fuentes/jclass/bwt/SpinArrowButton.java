// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCSpinBox.java

package jclass.bwt;

import java.awt.Event;

// Referenced classes of package jclass.bwt:
//            JCArrowButton, JCButton, JCSpinBox, SpinField

class SpinArrowButton extends JCArrowButton
{

    public SpinArrowButton(int i, JCSpinBox jcspinbox)
    {
        super(i);
        spin = jcspinbox;
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        boolean flag = super.mouseUp(event, i, j);
        SpinArrowButton spinarrowbutton = null;
        spinarrowbutton = equals(spin.decr_arrow) ? spin.incr_arrow : spin.decr_arrow;
        if(spinarrowbutton != null && spinarrowbutton.isArmed())
            spinarrowbutton.mouseUp(event, i, j);
        return flag;
    }

    JCSpinBox spin;
}
