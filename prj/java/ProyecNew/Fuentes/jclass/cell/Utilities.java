// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Utilities.java

package jclass.cell;

import java.awt.*;
import java.io.Serializable;

// Referenced classes of package jclass.cell:
//            CellInfo, KeyModifier

public class Utilities
    implements Serializable
{

    public Utilities()
    {
    }

    public static KeyModifier[] addKey(KeyModifier akeymodifier[], KeyModifier keymodifier)
    {
        KeyModifier akeymodifier1[] = new KeyModifier[akeymodifier.length + 1];
        System.arraycopy(akeymodifier, 0, akeymodifier1, 0, akeymodifier.length);
        akeymodifier1[akeymodifier.length] = keymodifier;
        return akeymodifier1;
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

    public static void drawBorder(Graphics g, int i, int j, int k, int l, int i1, int j1, Color color, 
            Color color1)
    {
        drawBorder(g, i, j, k, l, i1, j1, brighter(color), darker(color), color1);
    }

    public static void drawBorder(Graphics g, int i, int j, int k, int l, int i1, int j1, Color color, 
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

    public static void drawClipArrows(Graphics g, CellInfo cellinfo, Dimension dimension, int i, boolean flag)
    {
        int j = cellinfo.getClipHints();
        Rectangle rectangle = cellinfo.getDrawingArea();
        Rectangle rectangle1 = g.getClipBounds();
        Rectangle rectangle2 = new Rectangle(rectangle);
        translateToInsideBorders(g, cellinfo, rectangle2);
        g.setClip(rectangle2);
        if((j == 1 || j == 3) && dimension.width > rectangle.width)
        {
            int k;
            switch(cellinfo.getVerticalAlignment())
            {
            case 0: // '\0'
            default:
                k = rectangle2.height - i;
                break;

            case 1: // '\001'
                k = (rectangle2.height + i) / 2;
                break;

            case 2: // '\002'
                k = i * 2;
                break;
            }
            int i1 = cellinfo.getHorizontalAlignment();
            if(i1 == 1)
            {
                drawHorizontalArrow(g, rectangle2, k, 0, i, flag);
                drawHorizontalArrow(g, rectangle2, k, 2, i, flag);
            } else
            {
                drawHorizontalArrow(g, rectangle2, k, i1, i, flag);
            }
        }
        if((j == 2 || j == 3) && dimension.height > rectangle.height)
        {
            int l;
            switch(cellinfo.getHorizontalAlignment())
            {
            case 0: // '\0'
            default:
                l = rectangle2.width - i;
                break;

            case 1: // '\001'
                l = (rectangle2.width + i) / 2;
                break;

            case 2: // '\002'
                l = i * 2;
                break;
            }
            int j1 = cellinfo.getVerticalAlignment();
            if(j1 == 1)
            {
                drawVerticalArrow(g, rectangle2, l, 0, i, flag);
                drawVerticalArrow(g, rectangle2, l, 2, i, flag);
            } else
            {
                drawVerticalArrow(g, rectangle2, l, j1, i, flag);
            }
        }
        restoreFromInsideBorders(g, cellinfo, rectangle2);
        g.setClip(rectangle1);
    }

    static void drawHorizontalArrow(Graphics g, Rectangle rectangle, int i, int j, int k, boolean flag)
    {
        switch(j)
        {
        case 0: // '\0'
            int l = rectangle.width;
            xpoints[0] = l - k;
            ypoints[0] = i - k;
            xpoints[1] = xpoints[0];
            ypoints[1] = ypoints[0] + k;
            xpoints[2] = l;
            ypoints[2] = i - k / 2;
            break;

        case 2: // '\002'
            int i1 = 0;
            xpoints[0] = i1 + k;
            ypoints[0] = i - k;
            xpoints[1] = xpoints[0];
            ypoints[1] = ypoints[0] + k;
            xpoints[2] = i1;
            ypoints[2] = i - k / 2;
            break;

        case 1: // '\001'
        default:
            return;
        }
        drawPolygon(g, flag);
    }

    public static void drawNormal(Graphics g, int i, int j, int k, int l, int i1, Color color, Color color1)
    {
        drawTopLines(g, i, j, k, l, i1, color);
        drawBottomLines(g, i, j, k, l, i1, color1);
    }

    static void drawPolygon(Graphics g, boolean flag)
    {
        if(flag)
        {
            g.drawLine(xpoints[0], ypoints[0], xpoints[1], ypoints[1]);
            g.drawLine(xpoints[1], ypoints[1], xpoints[2], ypoints[2]);
            g.drawLine(xpoints[2], ypoints[2], xpoints[0], ypoints[0]);
        }
        g.fillPolygon(xpoints, ypoints, 3);
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

    static void drawVerticalArrow(Graphics g, Rectangle rectangle, int i, int j, int k, boolean flag)
    {
        switch(j)
        {
        case 0: // '\0'
            int l = rectangle.height;
            xpoints[0] = i - k;
            ypoints[0] = l - k;
            xpoints[1] = xpoints[0] + k;
            ypoints[1] = ypoints[0];
            xpoints[2] = i - k / 2;
            ypoints[2] = l;
            break;

        case 2: // '\002'
            int i1 = 0;
            xpoints[0] = i - k;
            ypoints[0] = i1 + k;
            xpoints[1] = xpoints[0] + k;
            ypoints[1] = ypoints[0];
            xpoints[2] = i - k / 2;
            ypoints[2] = i1;
            break;

        case 1: // '\001'
        default:
            return;
        }
        drawPolygon(g, flag);
    }

    public static Rectangle getWholeCell(CellInfo cellinfo, Rectangle rectangle)
    {
        Insets insets = cellinfo.getMarginInsets();
        Insets insets1 = cellinfo.getBorderInsets();
        return new Rectangle(-(insets.right + insets1.right), -(insets.top + insets1.top), rectangle.width + insets.right + insets.left + insets1.right + insets1.left, rectangle.height + insets.top + insets.bottom + insets1.top + insets1.bottom);
    }

    public static void restoreFromInsideBorders(Graphics g, CellInfo cellinfo, Rectangle rectangle)
    {
        Insets insets = cellinfo.getMarginInsets();
        g.translate(insets.right, insets.top);
        rectangle.setSize(rectangle.width - insets.right - insets.left, rectangle.height - insets.top - insets.bottom);
    }

    public static void restoreFromWholeCell(Graphics g, CellInfo cellinfo, Rectangle rectangle)
    {
        Insets insets = cellinfo.getMarginInsets();
        Insets insets1 = cellinfo.getBorderInsets();
        g.translate(insets.right + insets1.right, insets.top + insets1.top);
        rectangle.setSize(rectangle.width - insets.right - insets.left - insets1.right - insets1.left, rectangle.height - insets.top - insets.bottom - insets1.top - insets1.bottom);
    }

    public static void translateToInsideBorders(Graphics g, CellInfo cellinfo, Rectangle rectangle)
    {
        Insets insets = cellinfo.getMarginInsets();
        g.translate(-insets.right, -insets.top);
        rectangle.setSize(rectangle.width + insets.right + insets.left, rectangle.height + insets.top + insets.bottom);
    }

    public static void translateToWholeCell(Graphics g, CellInfo cellinfo, Rectangle rectangle)
    {
        Rectangle rectangle1 = getWholeCell(cellinfo, rectangle);
        g.translate(rectangle1.x, rectangle1.y);
        rectangle.setSize(rectangle1.width, rectangle1.height);
    }

    private static final Color WHITE_BRIGHTER = new Color(200, 200, 200);
    private static final Color WHITE_DARKER = new Color(140, 140, 140);
    private static final Color BLACK_BRIGHTER = new Color(125, 125, 125);
    private static final Color BLACK_DARKER = new Color(75, 75, 75);
    private static final double FACTOR = 0.5D;
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
    public static final int DEFAULT_CLIP_ARROW_SIZE = 4;
    static int xpoints[] = new int[3];
    static int ypoints[] = new int[3];

}
