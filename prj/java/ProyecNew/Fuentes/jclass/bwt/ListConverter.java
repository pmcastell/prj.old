// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ListConverter.java

package jclass.bwt;

import java.util.Vector;
import jclass.util.JCConverter;

// Referenced classes of package jclass.bwt:
//            BWTEnum, BWTUtil, JCComponent, JCListComponent

class ListConverter
{

    ListConverter()
    {
    }

    static void getParams(JCListComponent jclistcomponent)
    {
        JCConverter jcconverter = JCComponent.conv;
        String s = jclistcomponent.getParam("Items");
        jclass.util.JCVector jcvector = jcconverter.toVector(jclistcomponent, s, ',', true, jclistcomponent.getItems());
        if(s != null && jcvector != null && BWTUtil.instanceOf(jclistcomponent, "JCMultiColumnListComponent"))
        {
            for(int i = 0; i < jcvector.size(); i++)
            {
                Object obj = jcvector.elementAt(i);
                if(obj instanceof String)
                {
                    jclass.util.JCVector jcvector1 = jcconverter.toVector(jclistcomponent, (String)obj, '|', true, null);
                    if(jcvector1 != null)
                        jcvector.setElementAt(jcvector1, i);
                }
            }

        }
        jclistcomponent.setItems(jcvector);
        jclistcomponent.setSelectedBackground(jcconverter.toColor(jclistcomponent.getParam("SelectedBackground"), jclistcomponent.getSelectedBackground()));
        jclistcomponent.setSelectedForeground(jcconverter.toColor(jclistcomponent.getParam("SelectedForeground"), jclistcomponent.getSelectedBackground()));
        jclistcomponent.setAllowMultipleSelections(jcconverter.toBoolean(jclistcomponent.getParam("AllowMultipleSelections"), jclistcomponent.allowsMultipleSelections()));
        jclistcomponent.setAutoSelect(jcconverter.toBoolean(jclistcomponent.getParam("AutoSelect"), jclistcomponent.auto_select));
        jclistcomponent.setVisibleRows(jcconverter.toInt(jclistcomponent.getParam("VisibleRows"), jclistcomponent.getVisibleRows()));
        s = jclistcomponent.getParam("RowHeight");
        if(s != null)
            if(s.trim().equalsIgnoreCase("font_height"))
                jclistcomponent.setRowHeight(-997);
            else
                jclistcomponent.setRowHeight(jcconverter.toInt(s, jclistcomponent.getRowHeight()));
        jclistcomponent.setSpacing(jcconverter.toInt(jclistcomponent.getParam("Spacing"), jclistcomponent.getSpacing()));
    }
}
