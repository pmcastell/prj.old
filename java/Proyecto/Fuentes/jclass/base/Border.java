// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Border.java

package jclass.base;

import java.awt.Color;
import java.awt.Graphics;

public class Border
{

    public Border()
    {
    }

    public static Color brighter(Color color)
    {
        if(color.equals(Color.white))
            return WHITE_BRIGHTER;
        if(color.equals(Color.black))
        {
            return BLACK_BRIGHTER;
        } else
        {
            int i = color.getRed();
            i += (int)((double)(255 - i) * 0.5D);
            int j = color.getBlue();
            j += (int)((double)(255 - j) * 0.5D);
            int k = color.getGreen();
            k += (int)((double)(255 - k) * 0.5D);
            return new Color(Math.min(i, 255), Math.min(k, 255), Math.min(j, 255));
        }
    }

    public static Color darker(Color color)
    {
        if(color.equals(Color.white))
            return WHITE_DARKER;
        if(color.equals(Color.black))
            return BLACK_DARKER;
        else
            return color.darker();
    }

    public static void draw(Graphics g, int i, int j, int k, int l, int i1, int j1, Color color, 
            Color color1)
    {
        draw(g, i, j, k, l, i1, j1, brighter(color), darker(color), color1);
    }

    public static void draw(Graphics g, int i, int j, int k, int l, int i1, int j1, Color color, 
            Color color1, Color color2)
    {
        Color color3 = g.getColor();
        switch(i)
        {
        default:
            break;

        case 5: // '\005'
            drawNormal(g, j, k, l, i1, j1, color2, color2);
            break;

        case 3: // '\003'
            drawNormal(g, j, k, l, i1, j1, color1, color);
            break;

        case 4: // '\004'
            drawNormal(g, j, k, l, i1, j1, color, color1);
            break;

        case 8: // '\b'
            if(j == 2)
            {
                drawNormal(g, 1, k + 1, l + 1, i1 - 2, j1 - 2, Color.black, color);
                drawNormal(g, 1, k, l, i1, j1, color1, brighter(color));
            } else
            {
                drawNormal(g, j, k, l, i1, j1, color1, color);
            }
            break;

        case 9: // '\t'
            if(j == 2)
            {
                drawNormal(g, 1, k, l, i1, j1, brighter(color), color1);
                drawNormal(g, 1, k + 1, l + 1, i1 - 2, j1 - 2, color, Color.black);
            } else
            {
                drawNormal(g, j, k, l, i1, j1, color, color1);
            }
            break;

        case 1: // '\001'
            int k1 = j % 2;
            int i2 = k1 != 0 ? j / 2 + 1 : j / 2;
            int k2 = j - i2;
            drawNormal(g, i2, k, l, i1, j1, color1, color);
            drawNormal(g, k2, k + i2, l + i2, i1 - j - k1, j1 - j - k1, color, color1);
            break;

        case 2: // '\002'
            int l1 = j % 2;
            int j2 = l1 != 0 ? j / 2 + 1 : j / 2;
            int l2 = j - j2;
            drawNormal(g, j2, k, l, i1, j1, color, color1);
            drawNormal(g, l2, k + j2, l + j2, i1 - j - l1, j1 - j - l1, color1, color);
            break;

        case 7: // '\007'
            drawNormal(g, j, k, l, i1, j1, color, color1);
            drawNormal(g, j - 1, k, l, i1, j1, color2, color2);
            break;

        case 6: // '\006'
            drawNormal(g, j, k, l, i1, j1, color1, color);
            drawNormal(g, j - 1, k, l, i1, j1, color2, color2);
            break;
        }
        g.setColor(color3);
    }

    public static void drawBottomLines(Graphics g, int i, int j, int k, int l, int i1, Color color)
    {
        g.setColor(color);
        for(int j1 = 1; j1 <= i; j1++)
        {
            g.drawLine((j + j1) - 1, (k + i1) - j1, (j + l) - j1, (k + i1) - j1);
            g.drawLine((j + l) - j1, (k + j1) - 1, (j + l) - j1, (k + i1) - j1);
        }

    }

    public static void drawNormal(Graphics g, int i, int j, int k, int l, int i1, Color color, Color color1)
    {
        drawTopLines(g, i, j, k, l, i1, color);
        drawBottomLines(g, i, j, k, l, i1, color1);
    }

    public static void drawTopLines(Graphics g, int i, int j, int k, int l, int i1, Color color)
    {
        g.setColor(color);
        for(int j1 = 0; j1 < i; j1++)
        {
            g.drawLine(j + j1, k + j1, (j + l) - (j1 + 1), k + j1);
            g.drawLine(j + j1, k + j1 + 1, j + j1, (k + i1) - (j1 + 1));
        }

    }

    public static final int NONE = 0;
    public static final int ETCHED_IN = 1;
    public static final int ETCHED_OUT = 2;
    public static final int IN = 3;
    public static final int OUT = 4;
    public static final int PLAIN = 5;
    public static final int FRAME_IN = 6;
    public static final int FRAME_OUT = 7;
    public static final int CONTROL_IN = 8;
    public static final int CONTROL_OUT = 9;
    public static final int NUM_BORDER_STYLES = 10;
    public static final String border_strings[] = {
        "None", "Etched_In", "Etched_Out", "In", "Out", "Plain", "Frame_In", "Frame_Out", "Control_In", "Control_Out"
    };
    public static final int border_values[] = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    };
    private static final Color WHITE_BRIGHTER = new Color(200, 200, 200);
    private static final Color WHITE_DARKER = new Color(140, 140, 140);
    private static final Color BLACK_BRIGHTER = new Color(125, 125, 125);
    private static final Color BLACK_DARKER = new Color(75, 75, 75);
    private static final double FACTOR = 0.5D;

}
