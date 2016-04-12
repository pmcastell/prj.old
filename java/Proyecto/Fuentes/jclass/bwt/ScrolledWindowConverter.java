// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScrolledWindowConverter.java

package jclass.bwt;

import jclass.util.JCConverter;
import jclass.util.JCUtilConverter;

// Referenced classes of package jclass.bwt:
//            BWTEnum, JCContainer, JCScrolledWindow

class ScrolledWindowConverter
{

    ScrolledWindowConverter()
    {
    }

    static void checkDisplay(int i)
    {
        JCUtilConverter.checkEnum(i, "scrollbar display", display_values);
    }

    static void getParams(JCScrolledWindow jcscrolledwindow)
    {
        JCConverter jcconverter = JCContainer.conv;
        jcscrolledwindow.setScrollbarDisplay(jcconverter.toEnum(jcscrolledwindow.getParam("ScrollbarDisplay"), "ScrollbarDisplay", display_strings, display_values, jcscrolledwindow.getScrollbarDisplay()));
        jcscrolledwindow.setScrollbarOffset(jcconverter.toInt(jcscrolledwindow.getParam("ScrollbarOffset"), jcscrolledwindow.getScrollbarOffset()));
    }

    static final String display_strings[] = {
        "DISPLAY_ALWAYS", "DISPLAY_AS_NEEDED", "DISPLAY_VERTICAL_ONLY", "DISPLAY_HORIZONTAL_ONLY", "DISPLAY_NONE"
    };
    static final int display_values[] = {
        1, 0, 3, 4, 2
    };

}
