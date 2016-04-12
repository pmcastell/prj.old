// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCSeparator.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import jclass.base.*;
import jclass.util.JCConverter;
import jclass.util.JCUtilConverter;

// Referenced classes of package jclass.bwt:
//            JCComponent, BWTEnum

public class JCSeparator extends JCComponent
{

    public JCSeparator()
    {
        this(0);
    }

    public JCSeparator(int i)
    {
        this(i, null, null);
    }

    public JCSeparator(int i, Applet applet, String s)
    {
        super(applet, s);
        dir = 0;
        if(s == null)
            setName("separator" + nameCounter++);
        super.border_style = 3;
        if(getClass().getName().equals("jclass.bwt.JCSeparator"))
            getParameters(applet);
        dir = i;
        super.traversable = false;
        super.highlight = 0;
    }

    public int getOrientation()
    {
        return dir;
    }

    protected void getParameters()
    {
        super.getParameters();
        dir = JCComponent.conv.toEnum(getParam("Orientation"), "orientation", orient_strings, orient_values, dir);
    }

    public void paint(Graphics g)
    {
        java.awt.Rectangle rectangle = getDrawingArea();
        int i = size().width;
        int j = size().height;
        int k = dir != 0 ? (i - super.border) / 2 : 0;
        int l = dir != 0 ? 0 : (j - super.border) / 2;
        int i1 = dir != 0 ? 2 * super.border : i - getInsets().left - getInsets().right;
        int j1 = dir != 0 ? j - getInsets().top - getInsets().bottom : 2 * super.border;
        Border.draw(g, super.border_style, super.border, k + getInsets().left, l + getInsets().top, i1, j1, getBackground(), getForeground());
    }

    protected int preferredHeight()
    {
        return dir != 0 ? 100 : 0;
    }

    protected int preferredWidth()
    {
        return dir != 1 ? 100 : 0;
    }

    public void setOrientation(int i)
    {
        JCUtilConverter.checkEnum(i, "orientation", orient_values);
        dir = i;
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    int dir;
    private static final String base = "separator";
    private static int nameCounter = 0;
    static final String orient_strings[] = {
        "horizontal", "vertical"
    };
    static final int orient_values[] = {
        0, 1
    };

}
