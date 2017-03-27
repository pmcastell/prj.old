// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCMultiColumnInterface.java

package jclass.bwt;

import jclass.util.JCVector;

// Referenced classes of package jclass.bwt:
//            JCMultiColumnData

public interface JCMultiColumnInterface
{

    public abstract int calcWidth(int i);

    public abstract int getColumnAlignment(int i);

    public abstract int[] getColumnAlignments();

    public abstract int getColumnDisplayWidth(int i);

    public abstract int[] getColumnDisplayWidths();

    public abstract int getColumnLeftMargin(int i);

    public abstract int getColumnPosition(int i);

    public abstract int getColumnResizePolicy();

    public abstract int getColumnRightMargin(int i);

    public abstract int getColumnWidth(int i);

    public abstract int[] getColumnWidths();

    public abstract JCMultiColumnData getMultiColumnData();

    public abstract int getNumColumns();

    public abstract void setColumnAlignment(int i, int j);

    public abstract void setColumnAlignments(int ai[]);

    public abstract void setColumnButtons(JCVector jcvector);

    public abstract void setColumnDisplayWidth(int i, int j);

    public abstract void setColumnDisplayWidths(int ai[]);

    public abstract void setColumnLabels(JCVector jcvector);

    public abstract void setColumnLeftMargin(int i, int j);

    public abstract void setColumnResizePolicy(int i);

    public abstract void setColumnRightMargin(int i, int j);

    public abstract void setColumnWidth(int i, int j);

    public abstract void setColumnWidths(int ai[]);

    public abstract void setNumColumns(int i);
}
