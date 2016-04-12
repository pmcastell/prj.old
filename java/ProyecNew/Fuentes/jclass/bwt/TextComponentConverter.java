// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextComponentConverter.java

package jclass.bwt;

import jclass.util.JCConverter;
import jclass.util.JCUtilConverter;

// Referenced classes of package jclass.bwt:
//            BWTEnum, JCComponent, JCTextComponent

class TextComponentConverter
{

    TextComponentConverter()
    {
    }

    static void checkAlignment(int i)
    {
        JCUtilConverter.checkEnum(i, "alignment", alignment_values);
    }

    static void checkStringCase(int i)
    {
        JCUtilConverter.checkEnum(i, "StringCase", case_values);
    }

    static void getParams(JCTextComponent jctextcomponent)
    {
        JCConverter jcconverter = JCComponent.conv;
        jctextcomponent.alignment = jcconverter.toEnum(jctextcomponent.getParam("alignment"), "alignment", alignment_strings, alignment_values, jctextcomponent.alignment);
        jctextcomponent.string_case = jcconverter.toEnum(jctextcomponent.getParam("StringCase"), "StringCase", case_strings, case_values, jctextcomponent.string_case);
        jctextcomponent.columns = jcconverter.toInt(jctextcomponent.getParam("Columns"), jctextcomponent.columns);
        String s = jctextcomponent.getParam("Text");
        if(s != null)
            jctextcomponent.text = s.toCharArray();
        jctextcomponent.max_length = jcconverter.toInt(jctextcomponent.getParam("MaximumLength"), jctextcomponent.max_length);
        jctextcomponent.cursor_pos = jcconverter.toInt(jctextcomponent.getParam("CursorPosition"), jctextcomponent.cursor_pos);
        jctextcomponent.display_cursor = jcconverter.toBoolean(jctextcomponent.getParam("ShowCursorPosition"), jctextcomponent.display_cursor);
        jctextcomponent.overstrike = jcconverter.toBoolean(jctextcomponent.getParam("Overstrike"), jctextcomponent.overstrike);
        jctextcomponent.editable = jcconverter.toBoolean(jctextcomponent.getParam("Editable"), jctextcomponent.editable);
        jctextcomponent.setSelectedBackground(jcconverter.toColor(jctextcomponent.getParam("SelectedBackground"), jctextcomponent.getSelectedBackground()));
        jctextcomponent.setSelectedForeground(jcconverter.toColor(jctextcomponent.getParam("SelectedForeground"), jctextcomponent.getSelectedBackground()));
    }

    static final String alignment_strings[] = {
        "LEFT", "CENTER", "RIGHT"
    };
    static final int alignment_values[] = {
        0, 1, 2
    };
    static final int case_values[] = {
        0, 1, 2
    };
    static final String case_strings[] = {
        "CASE_AS_IS", "CASE_LOWER", "CASE_UPPER"
    };

}
