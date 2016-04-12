// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConvertUtil.java

package jclass.util;

import java.awt.Component;

// Referenced classes of package jclass.util:
//            JCCSVTokenizer, JCString, JCStringTokenizer, JCUtilConverter, 
//            JCVector

public class ConvertUtil
{

    public ConvertUtil()
    {
    }

    private static Object getJCString(Component component, String s)
    {
        return JCString.parse(component, s);
    }

    public static Object toCellValue(Component component, String s, boolean flag)
    {
        Object obj = null;
        int i;
        if(s != null && flag && (i = s.indexOf('[')) != -1 && (i == 0 || i > 0 && s.charAt(i - 1) != '\\'))
            obj = getJCString(component, s);
        else
        if(flag)
            obj = JCUtilConverter.removeEscape(s);
        else
            obj = s;
        return obj;
    }

    public static JCVector toVector(Component component, String s, char c, boolean flag)
    {
        if(s == null)
            return null;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        JCVector jcvector = new JCVector(jcstringtokenizer.countTokens(c));
        jcstringtokenizer.strip_esc = false;
        for(int i = 0; jcstringtokenizer.hasMoreTokens(); i++)
        {
            String s1 = jcstringtokenizer.nextToken(c);
            jcvector.setElementAt(i, toCellValue(component, s1, flag));
        }

        return jcvector;
    }

    public static JCVector toVectorFromCSV(Component component, String s)
    {
        if(s == null)
            return null;
        JCCSVTokenizer jccsvtokenizer = new JCCSVTokenizer(s);
        JCVector jcvector = new JCVector(jccsvtokenizer.countTokens());
        for(int i = 0; jccsvtokenizer.hasMoreTokens(); i++)
            jcvector.setElementAt(i, jccsvtokenizer.nextToken());

        return jcvector;
    }
}
