// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScrollbarConverter.java

package jclass.bwt;

import jclass.util.JCConverter;
import jclass.util.JCUtilConverter;

// Referenced classes of package jclass.bwt:
//            BWTEnum, JCContainer, JCScrollbar

class ScrollbarConverter
{

    ScrollbarConverter()
    {
    }

    static void checkOrientation(int i)
    {
        JCUtilConverter.checkEnum(i, "orientation", orientation_values);
    }

    static void getParams(JCScrollbar jcscrollbar)
    {
        JCConverter jcconverter = JCContainer.conv;
        jcscrollbar.setMinimum(jcconverter.toInt(jcscrollbar.getParam("Minimum"), jcscrollbar.min));
        jcscrollbar.setMaximum(jcconverter.toInt(jcscrollbar.getParam("Maximum"), jcscrollbar.max));
        jcscrollbar.setVisibleAmount(jcconverter.toInt(jcscrollbar.getParam("VisibleAmount"), jcscrollbar.getVisibleAmount()));
        jcscrollbar.setBlockIncrement(jcconverter.toInt(jcscrollbar.getParam("BlockIncrement"), jcscrollbar.getBlockIncrement()));
        jcscrollbar.setUnitIncrement(jcconverter.toInt(jcscrollbar.getParam("UnitIncrement"), jcscrollbar.getUnitIncrement()));
        jcscrollbar.filter_time = jcconverter.toInt(jcscrollbar.getParam("FilterTime"), (int)jcscrollbar.filter_time);
    }

    static final int orientation_values[] = {
        0, 1
    };
    static final String orientation_strings[] = {
        "HORIZONTAL", "VERTICAL"
    };

}
