// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ButtonConverter.java

package jclass.bwt;

import jclass.util.JCConverter;

// Referenced classes of package jclass.bwt:
//            JCButton, JCComponent

class ButtonConverter
{

    ButtonConverter()
    {
    }

    static void getParams(JCButton jcbutton)
    {
        jcbutton.arm_label = JCComponent.conv.toJCString(jcbutton, jcbutton.getParam("armLabel"), jcbutton.arm_label);
    }
}
