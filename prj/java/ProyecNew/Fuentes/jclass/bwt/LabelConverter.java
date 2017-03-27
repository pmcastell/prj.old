// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LabelConverter.java

package jclass.bwt;

import jclass.base.BaseComponent;
import jclass.util.JCConverter;
import jclass.util.JCUtilConverter;

// Referenced classes of package jclass.bwt:
//            BWTEnum, JCComponent, JCLabel

class LabelConverter
{

    LabelConverter()
    {
    }

    static void checkAlignment(int i)
    {
        JCUtilConverter.checkEnum(i, "alignment", alignment_values);
    }

    static void getParams(JCLabel jclabel)
    {
        JCConverter jcconverter = JCComponent.conv;
        jclabel.label = jcconverter.toJCString(jclabel, jclabel.getParam("label"), jclabel.label);
        jclabel.alignment = toAlignment(jcconverter, jclabel.getParam("alignment"), jclabel.alignment);
        jclabel.setBorderStyle(jcconverter.toEnum(jclabel.getParam("shadowType"), "shadowType", shadowtype_strings, shadowtype_values, jclabel.getBorderStyle()));
    }

    static int toAlignment(JCConverter jcconverter, String s, int i)
    {
        return jcconverter.toEnum(s, "alignment", alignment_strings, alignment_values, i);
    }

    static final int alignment_values[] = {
        0, 0, 1, 1, 2, 2, 3, 4, 5, 6, 
        7, 8
    };
    static final String alignment_strings[] = {
        "TOPLEFT", "LEFT", "TOPCENTER", "CENTER", "TOPRIGHT", "RIGHT", "MIDDLELEFT", "MIDDLECENTER", "MIDDLERIGHT", "BOTTOMLEFT", 
        "BOTTOMCENTER", "BOTTOMRIGHT"
    };
    static final String shadowtype_strings[] = {
        "SHADOW_NONE", "SHADOW_ETCHED_IN", "SHADOW_ETCHED_OUT", "SHADOW_IN", "SHADOW_OUT", "SHADOW_PLAIN", "SHADOW_FRAME_IN", "SHADOW_FRAME_OUT", "CONTROL_SHADOW_IN", "CONTROL_SHADOW_OUT"
    };
    static final int shadowtype_values[] = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    };

}
