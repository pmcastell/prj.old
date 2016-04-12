// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XYConstraints.java

package com.borland.jbcl.layout;

import java.io.Serializable;

public class XYConstraints
    implements Serializable, Cloneable
{

    public String toString()
    {
        return String.valueOf(String.valueOf((new StringBuffer("XYConstraints[")).append(x).append(",").append(y).append(",").append(width).append(",").append(height).append("]")));
    }

    public Object clone()
    {
        return new XYConstraints(x, y, width, height);
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof XYConstraints)
        {
            XYConstraints xyconstraints = (XYConstraints)obj;
            return xyconstraints.x == x && xyconstraints.y == y && xyconstraints.width == width && xyconstraints.height == height;
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return x ^ y * 37 ^ width * 43 ^ height * 47;
    }

    public void setHeight(int i)
    {
        height = i;
    }

    public int getHeight()
    {
        return height;
    }

    public void setWidth(int i)
    {
        width = i;
    }

    public int getWidth()
    {
        return width;
    }

    public void setY(int i)
    {
        y = i;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int i)
    {
        x = i;
    }

    public int getX()
    {
        return x;
    }

    public XYConstraints(int i, int j, int k, int l)
    {
        x = i;
        y = j;
        width = k;
        height = l;
    }

    public XYConstraints()
    {
        this(0, 0, 0, 0);
    }

    int height;
    int width;
    int y;
    int x;
}
