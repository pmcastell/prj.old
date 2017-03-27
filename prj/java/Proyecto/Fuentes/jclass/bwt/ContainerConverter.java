// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContainerConverter.java

package jclass.bwt;

import java.awt.Component;
import javax.swing.JComponent;
import jclass.util.JCConverter;

// Referenced classes of package jclass.bwt:
//            JCContainer

class ContainerConverter
{

    ContainerConverter()
    {
    }

    static void getParams(JCContainer jccontainer)
    {
        JCConverter jcconverter = JCContainer.conv;
        jccontainer.setBackground(jcconverter.toColor(jccontainer.getParam("background"), jccontainer.getBackground()));
        jccontainer.setForeground(jcconverter.toColor(jccontainer.getParam("foreground"), jccontainer.getForeground()));
        jccontainer.setFont(jcconverter.toFont(jccontainer.getParam("font"), jccontainer.getFont()));
        jccontainer.insets = jcconverter.toInsets(jccontainer.getParam("insets"), jccontainer.insets);
        jccontainer.pref_width = jcconverter.toInt(jccontainer.getParam("preferredWidth"), jccontainer.pref_width);
        jccontainer.pref_height = jcconverter.toInt(jccontainer.getParam("preferredHeight"), jccontainer.pref_height);
    }
}
