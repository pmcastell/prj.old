// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HeaderConverter.java

package jclass.bwt;

import jclass.util.JCConverter;

// Referenced classes of package jclass.bwt:
//            JCContainer, JCHeader, MultiColumnConverter

class HeaderConverter
{

    HeaderConverter()
    {
    }

    static void getParams(JCHeader jcheader)
    {
        JCConverter jcconverter = JCContainer.conv;
        MultiColumnConverter.getParams(jcheader, jcconverter);
        jclass.util.JCVector jcvector = jcconverter.toVector(jcheader, jcheader.getParam("labels"), ',', true, null);
        if(jcvector != null)
            jcheader.setLabels(jcvector);
        jclass.util.JCVector jcvector1 = jcconverter.toVector(jcheader, jcheader.getParam("buttons"), ',', true, null);
        if(jcvector1 != null)
            jcheader.setButtons(jcvector1);
    }
}
