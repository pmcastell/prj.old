// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CellInfo.java

package jclass.cell;

import java.awt.*;

public interface CellInfo
{

    public abstract Color getBackground();

    public abstract Insets getBorderInsets();

    public abstract int getBorderStyle();

    public abstract int getClipHints();

    public abstract Class getDataType();

    public abstract Rectangle getDrawingArea();

    public abstract Font getFont();

    public abstract FontMetrics getFontMetrics();

    public abstract Color getForeground();

    public abstract int getHorizontalAlignment();

    public abstract Insets getMarginInsets();

    public abstract boolean getSelectAll();

    public abstract Color getSelectedBackground();

    public abstract Color getSelectedForeground();

    public abstract int getVerticalAlignment();

    public abstract boolean isEditable();

    public abstract boolean isEnabled();

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 0;
    public static final int BOTTOM = 2;
    public static final int SHOW_NONE = 0;
    public static final int SHOW_HORIZONTAL = 1;
    public static final int SHOW_VERTICAL = 2;
    public static final int SHOW_ALL = 3;
}
