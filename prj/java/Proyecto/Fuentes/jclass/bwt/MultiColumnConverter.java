// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultiColumnConverter.java

package jclass.bwt;

import java.awt.Component;
import jclass.base.BaseComponent;
import jclass.util.JCConverter;
import jclass.util.JCStringTokenizer;

// Referenced classes of package jclass.bwt:
//            BWTEnum, JCComponent, JCContainer, JCMultiColumnInterface, 
//            JCMultiColumnWindow, LabelConverter

class MultiColumnConverter
{

    MultiColumnConverter()
    {
    }

    static String getParam(JCMultiColumnInterface jcmulticolumninterface, String s)
    {
        if(jcmulticolumninterface instanceof JCComponent)
        {
            JCComponent jccomponent = (JCComponent)jcmulticolumninterface;
            return JCComponent.conv.getParam(jccomponent.getApplet(), jccomponent, jccomponent.getName(), s);
        }
        if(jcmulticolumninterface instanceof JCContainer)
        {
            JCContainer jccontainer = (JCContainer)jcmulticolumninterface;
            return JCContainer.conv.getParam(jccontainer.applet, jccontainer, jccontainer.getName(), s);
        } else
        {
            return null;
        }
    }

    static void getParams(JCMultiColumnInterface jcmulticolumninterface, JCConverter jcconverter)
    {
        String s = getParam(jcmulticolumninterface, "numColumns");
        if(s != null && s.equalsIgnoreCase("VARIABLE"))
            jcmulticolumninterface.setNumColumns(-998);
        else
        if(s != null)
            jcmulticolumninterface.setNumColumns(jcconverter.toInt(s, jcmulticolumninterface.getNumColumns()));
        s = getParam(jcmulticolumninterface, "columnResizePolicy");
        if(s != null)
            jcmulticolumninterface.setColumnResizePolicy(jcconverter.toEnum(s, "ColumnResizePolicy", column_resize_policy_strings, column_resize_policy_values, 0));
        s = getParam(jcmulticolumninterface, "columnLabels");
        if(s != null)
            jcmulticolumninterface.setColumnLabels(jcconverter.toVector((Component)jcmulticolumninterface, s, ',', true));
        s = getParam(jcmulticolumninterface, "columnButtons");
        if(s != null)
            jcmulticolumninterface.setColumnButtons(jcconverter.toVector((Component)jcmulticolumninterface, s, ',', true));
        int ai[] = jcconverter.toIntList(getParam(jcmulticolumninterface, "columnWidths"), ',', null);
        if(ai != null)
        {
            for(int i = 0; i < ai.length; i++)
                jcmulticolumninterface.setColumnWidth(i, ai[i]);

        }
        if(jcmulticolumninterface instanceof JCMultiColumnWindow)
        {
            JCMultiColumnWindow jcmulticolumnwindow = (JCMultiColumnWindow)jcmulticolumninterface;
            jcmulticolumnwindow.setColumnLabelSort(jcconverter.toBoolean(getParam(jcmulticolumninterface, "ColumnLabelSort"), jcmulticolumnwindow.getColumnLabelSort()));
        }
        ai = toAlignmentList(jcconverter, getParam(jcmulticolumninterface, "ColumnAlignments"), jcmulticolumninterface.getColumnAlignments());
        jcmulticolumninterface.setColumnAlignments(ai);
        ai = jcconverter.toIntList(getParam(jcmulticolumninterface, "columnLeftMargins"), ',', null);
        if(ai != null)
        {
            for(int j = 0; j < ai.length; j++)
                jcmulticolumninterface.setColumnLeftMargin(j, ai[j]);

        }
        ai = jcconverter.toIntList(getParam(jcmulticolumninterface, "columnRightMargins"), ',', null);
        if(ai != null)
        {
            for(int k = 0; k < ai.length; k++)
                jcmulticolumninterface.setColumnRightMargin(k, ai[k]);

        }
    }

    static int[] toAlignmentList(JCConverter jcconverter, String s, int ai[])
    {
        if(s == null)
            return null;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        int ai1[] = new int[jcstringtokenizer.countTokens(',')];
        for(int i = 0; jcstringtokenizer.hasMoreTokens(); i++)
        {
            String s1 = jcstringtokenizer.nextToken(',').trim();
            int j = ai == null || i >= ai.length ? 0 : ai[i];
            ai1[i] = LabelConverter.toAlignment(jcconverter, s1, j);
        }

        return ai1;
    }

    static final int column_resize_policy_values[] = {
        0, 1, 2
    };
    static final String column_resize_policy_strings[] = {
        "RESIZE_SINGLE", "RESIZE_NONE", "RESIZE_COLLAPSE"
    };

}
