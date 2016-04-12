// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComponentConverter.java

package jclass.bwt;

import java.awt.Component;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;
import jclass.util.JCConverter;

// Referenced classes of package jclass.bwt:
//            JCComponent

class ComponentConverter
{

    ComponentConverter()
    {
    }

    static void getParams(JCComponent jccomponent)
    {
        JCConverter jcconverter = JCComponent.conv;
        jccomponent.setBackground(jcconverter.toColor(jccomponent.getParam("background"), jccomponent.getBackground()));
        jccomponent.setForeground(jcconverter.toColor(jccomponent.getParam("foreground"), jccomponent.getForeground()));
        jccomponent.setFont(jcconverter.toFont(jccomponent.getParam("font"), jccomponent.getFont()));
        jccomponent.setBorderThickness(jcconverter.toInt(jccomponent.getParam("ShadowThickness"), jccomponent.getBorderThickness()));
        jccomponent.setHighlightThickness(jcconverter.toInt(jccomponent.getParam("HighlightThickness"), jccomponent.getHighlightThickness()));
        jccomponent.setHighlightColor(jcconverter.toColor(jccomponent.getParam("HighlightColor"), jccomponent.getHighlightColor()));
        jccomponent.setInsets(jcconverter.toInsets(jccomponent.getParam("insets"), jccomponent.getInsets()));
        jccomponent.setPreferredSize(jcconverter.toInt(jccomponent.getParam("preferredWidth"), jccomponent.getPreferredWidthInternal()), jcconverter.toInt(jccomponent.getParam("preferredHeight"), jccomponent.getPreferredHeightInternal()));
        jccomponent.setTraversable(jcconverter.toBoolean(jccomponent.getParam("traversable"), jccomponent.isTraversable()));
        jccomponent.setDoubleBuffer(jcconverter.toBoolean(jccomponent.getParam("doubleBuffer"), jccomponent.getDoubleBuffer()));
    }
}
