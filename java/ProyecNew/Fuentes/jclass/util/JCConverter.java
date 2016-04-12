// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCConverter.java

package jclass.util;

import java.applet.Applet;
import java.awt.*;
import java.io.Serializable;
import java.util.Date;

// Referenced classes of package jclass.util:
//            ConvertUtil, JCUtilConverter, JCVector

public class JCConverter
    implements Serializable
{

    public JCConverter()
    {
    }

    public String getParam(Applet applet, Component component, String s, String s1)
    {
        return JCUtilConverter.getParam(applet, component, s, s1);
    }

    public boolean toBoolean(String s, boolean flag)
    {
        return JCUtilConverter.toBoolean(s, flag);
    }

    public Color toColor(String s)
    {
        return JCUtilConverter.toColor(s);
    }

    public Color toColor(String s, Color color)
    {
        return JCUtilConverter.toColor(s, color);
    }

    public Color[] toColorList(String s, Color acolor[])
    {
        return JCUtilConverter.toColorList(s, acolor);
    }

    public Date toDate(String s, Date date)
    {
        return JCUtilConverter.toDate(s, date);
    }

    public Dimension toDimension(String s, Dimension dimension)
    {
        return JCUtilConverter.toDimension(s, dimension);
    }

    public double toDouble(String s, double d)
    {
        return JCUtilConverter.toDouble(s, d);
    }

    public int toEnum(String s, String s1, String as[], int ai[], int i)
    {
        return JCUtilConverter.toEnum(s, s1, as, ai, i);
    }

    public long toEnum(String s, String s1, String as[], long al[], long l)
    {
        return JCUtilConverter.toEnum(s, s1, as, al, l);
    }

    public int[] toEnumList(String s, String s1, String as[], int ai[], int ai1[])
    {
        return JCUtilConverter.toEnumList(s, s1, as, ai, ai1);
    }

    public Font toFont(String s)
    {
        return JCUtilConverter.toFont(s);
    }

    public Font toFont(String s, Font font)
    {
        return JCUtilConverter.toFont(s, font);
    }

    public Image toImage(Component component, String s)
    {
        return JCUtilConverter.toImage(component, s);
    }

    public Image toImage(Component component, String s, Image image)
    {
        return JCUtilConverter.toImage(component, s, image);
    }

    public Image[] toImageList(Component component, String s, Image aimage[])
    {
        return JCUtilConverter.toImageList(component, s, aimage);
    }

    public Insets toInsets(String s, Insets insets)
    {
        return JCUtilConverter.toInsets(s, insets);
    }

    public int toInt(String s, int i)
    {
        return JCUtilConverter.toInt(s, i);
    }

    public int[] toIntList(String s, char c, int ai[])
    {
        return JCUtilConverter.toIntList(s, c, ai);
    }

    public Object toJCString(Component component, String s, Object obj)
    {
        Object obj1 = ConvertUtil.toCellValue(component, s, true);
        return obj1 == null ? obj : obj1;
    }

    public Point toPoint(String s, Point point)
    {
        return JCUtilConverter.toPoint(s, point);
    }

    public static String[] toStringList(String s)
    {
        return JCUtilConverter.toStringList(s);
    }

    public JCVector toVector(Component component, String s, char c, boolean flag)
    {
        return JCUtilConverter.toVector(component, s, c, flag);
    }

    public JCVector toVector(Component component, String s, char c, boolean flag, JCVector jcvector)
    {
        return JCUtilConverter.toVector(component, s, c, flag, jcvector);
    }
}
