// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ArrowButtonConverter.java

package jclass.bwt;

import jclass.util.JCConverter;
import jclass.util.JCUtilConverter;

// Referenced classes of package jclass.bwt:
//            BWTEnum, JCArrowButton, JCComponent

class ArrowButtonConverter
{

    ArrowButtonConverter()
    {
    }

    static void checkOrientation(int i)
    {
        JCUtilConverter.checkEnum(i, "orientation", orientation_values);
    }

    static void getParams(JCArrowButton jcarrowbutton)
    {
        JCConverter jcconverter = JCComponent.conv;
        jcarrowbutton.orientation = jcconverter.toEnum(jcarrowbutton.getParam("Orientation"), "orientation", orientation_strings, orientation_values, jcarrowbutton.orientation);
    }

    static int orientation_values[] = {
        10, 9, 0, 2
    };
    static String orientation_strings[] = {
        "UP", "DOWN", "LEFT", "RIGHT"
    };

}
