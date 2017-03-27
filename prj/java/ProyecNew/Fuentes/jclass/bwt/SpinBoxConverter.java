// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpinBoxConverter.java

package jclass.bwt;

import jclass.util.JCConverter;

// Referenced classes of package jclass.bwt:
//            JCContainer, JCSpinBox

class SpinBoxConverter
{

    SpinBoxConverter()
    {
    }

    static void getParams(JCSpinBox jcspinbox)
    {
        JCConverter jcconverter = JCContainer.conv;
        jcspinbox.auto_arrow_disable = jcconverter.toBoolean(jcspinbox.getParam("AutoArrowDisable"), jcspinbox.auto_arrow_disable);
        jcspinbox.min = jcconverter.toInt(jcspinbox.getParam("Minimum"), jcspinbox.min);
        jcspinbox.max = jcconverter.toInt(jcspinbox.getParam("Maximum"), jcspinbox.max);
        jcspinbox.incr = jcconverter.toInt(jcspinbox.getParam("Increment"), jcspinbox.incr);
        jcspinbox.decimals = jcconverter.toInt(jcspinbox.getParam("decimals"), jcspinbox.decimals);
    }
}
