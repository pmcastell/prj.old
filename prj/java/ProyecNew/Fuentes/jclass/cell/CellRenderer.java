// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CellRenderer.java

package jclass.cell;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;

// Referenced classes of package jclass.cell:
//            CellInfo

public interface CellRenderer
    extends Serializable
{

    public abstract void draw(Graphics g, CellInfo cellinfo, Object obj, boolean flag);

    public abstract Dimension getPreferredSize(CellInfo cellinfo, Object obj);
}
