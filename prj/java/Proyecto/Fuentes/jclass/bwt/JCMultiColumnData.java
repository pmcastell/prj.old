// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCMultiColumnData.java

package jclass.bwt;

import java.awt.*;
import java.io.PrintStream;
import java.util.Vector;
import jclass.base.TransientComponent;

// Referenced classes of package jclass.bwt:
//            BWTEnum, BWTUtil, JCComponent, JCMultiColumnInterface, 
//            JCSerializable

public class JCMultiColumnData
    implements JCSerializable
{

    public JCMultiColumnData()
    {
        column_display_widths = new int[0];
        column_resize_policy = 0;
        column_widths = new int[0];
        column_widths_ext = new int[0];
        num_columns = 1;
        alignments = new int[0];
        left_margins = new int[0];
        right_margins = new int[0];
        rect = new Rectangle();
    }

    public JCMultiColumnData(JCMultiColumnInterface jcmulticolumninterface)
    {
        column_display_widths = new int[0];
        column_resize_policy = 0;
        column_widths = new int[0];
        column_widths_ext = new int[0];
        num_columns = 1;
        alignments = new int[0];
        left_margins = new int[0];
        right_margins = new int[0];
        rect = new Rectangle();
        multi = jcmulticolumninterface;
        if(jcmulticolumninterface instanceof JCComponent)
            comp = (JCComponent)jcmulticolumninterface;
    }

    protected void adjustDrawingRect(int i, Rectangle rectangle, Rectangle rectangle1)
    {
        int j = multi.getColumnLeftMargin(i);
        rectangle1.x = multi.getColumnPosition(i) + j;
        rectangle1.width -= j + multi.getColumnRightMargin(i);
    }

    public void calcColumnWidths()
    {
        if(((Component)multi).getPeer() == null)
            return;
        column_widths_ext = BWTUtil.copyList(column_widths_ext, num_columns, -998);
        column_widths = BWTUtil.copyList(column_widths, num_columns, -998);
        System.arraycopy(column_widths_ext, 0, column_widths, 0, num_columns);
        for(int i = 0; i < num_columns; i++)
            if(column_widths_ext[i] == -998)
                column_widths[i] = multi.calcWidth(i);

    }

    public synchronized void draw(Graphics g, Object obj, Rectangle rectangle)
    {
        if(rectangle == null)
        {
            if(comp != null)
                rect = comp.getDrawingArea();
            else
            if(multi instanceof Component)
                rect = ((Component)multi).bounds();
            else
                return;
        } else
        {
            JCComponent.setBounds(rect, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        Color color = null;
        if(comp != null && !comp.isEnabled())
        {
            color = g.getColor();
            g.setColor(Color.lightGray.darker().darker());
        }
        Vector vector = null;
        boolean flag;
        if(!(obj instanceof Vector) || BWTUtil.is_jcstring(obj))
        {
            if(comp == null)
                return;
            flag = true;
        } else
        {
            vector = (Vector)obj;
            flag = false;
        }
        Rectangle rectangle1 = null;
        if(comp instanceof JCComponent)
            rectangle1 = comp.getPaintRect();
        if(rectangle1 == null)
            rectangle1 = g.getClipRect();
        for(int i = 0; flag && i < 1 || vector != null && i < vector.size(); i++)
        {
            if(i >= num_columns)
                break;
            rect.width = multi.getColumnDisplayWidth(i);
            if(rect.width < 0)
                break;
            adjustDrawingRect(i, rectangle, rect);
            if(rectangle1.intersects(rect))
            {
                Graphics g1 = g.create();
                g1.clipRect(rect.x, rect.y, rect.width, rect.height);
                BWTUtil.draw((Component)multi, g1, flag ? obj : ((Vector)obj).elementAt(i), multi.getColumnAlignment(i), rect);
                g1.dispose();
            }
        }

        if(!comp.isEnabled() && color != null)
            g.setColor(color);
    }

    public int getColumnAlignment(int i)
    {
        return i >= alignments.length ? 3 : alignments[i];
    }

    public int[] getColumnAlignments()
    {
        alignments = BWTUtil.copyList(alignments, num_columns, 3);
        return alignments;
    }

    public int getColumnDisplayWidth(int i)
    {
        int j = i < 0 || i >= column_widths.length ? -999 : getColumnWidth(i);
        if(i >= 0 && i < column_display_widths.length && column_display_widths[i] != -999)
            j = column_display_widths[i];
        return j;
    }

    public int[] getColumnDisplayWidths()
    {
        return column_display_widths;
    }

    public int getColumnLeftMargin(int i)
    {
        return i >= left_margins.length ? 5 : left_margins[i];
    }

    public int getColumnPosition(int i)
    {
        int j = 0;
        for(int k = 0; k < getNumColumns() && k < i; k++)
            if(getColumnDisplayWidth(k) != -999)
                j += getColumnDisplayWidth(k);

        return j;
    }

    public int getColumnResizePolicy()
    {
        return column_resize_policy;
    }

    public int getColumnRightMargin(int i)
    {
        return i >= right_margins.length ? 5 : right_margins[i];
    }

    public int getColumnWidth(int i)
    {
        return i < 0 || i >= column_widths.length ? 0 : column_widths[i];
    }

    public int[] getColumnWidths()
    {
        return column_widths_ext;
    }

    public int getNumColumns()
    {
        return num_columns;
    }

    public int preferredWidth()
    {
        int i = getColumnPosition(getNumColumns());
        return i;
    }

    public void setColumnAlignment(int i, int j)
    {
        alignments = BWTUtil.copyList(alignments, i + 1, 3);
        alignments[i] = j;
    }

    public void setColumnAlignments(int ai[])
    {
        alignments = ai == null ? new int[0] : ai;
    }

    public void setColumnDisplayWidth(int i, int j)
    {
        column_display_widths = BWTUtil.copyList(column_display_widths, i + 1, -999);
        column_display_widths[i] = j == -999 ? getColumnWidth(i) : j;
    }

    public void setColumnDisplayWidths(int ai[])
    {
        column_display_widths = ai == null ? new int[0] : ai;
    }

    public void setColumnLeftMargin(int i, int j)
    {
        left_margins = BWTUtil.copyList(left_margins, i + 1, 5);
        left_margins[i] = j;
    }

    public void setColumnResizePolicy(int i)
    {
        column_resize_policy = i;
    }

    public void setColumnRightMargin(int i, int j)
    {
        right_margins = BWTUtil.copyList(right_margins, i + 1, 5);
        right_margins[i] = j;
    }

    public void setColumnWidth(int i, int j)
    {
        column_widths = BWTUtil.copyList(column_widths, i + 1, -998);
        column_widths_ext = BWTUtil.copyList(column_widths_ext, i + 1, -998);
        column_widths_ext[i] = column_widths[i] = j;
        if(j == -998)
            column_widths[i] = multi.calcWidth(i);
    }

    void setColumnWidthInternal(int i, int j)
    {
        column_widths = BWTUtil.copyList(column_widths, i + 1, -998);
        column_widths_ext = BWTUtil.copyList(column_widths_ext, i + 1, -998);
        column_widths[i] = j;
        if(j == -998)
            column_widths[i] = multi.calcWidth(i);
    }

    public void setColumnWidths(int ai[])
    {
        column_widths_ext = ai == null ? new int[0] : ai;
        calcColumnWidths();
    }

    public void setNumColumns(int i)
    {
        num_columns = i;
    }

    private static final boolean TRACE = false;
    protected JCMultiColumnInterface multi;
    protected JCComponent comp;
    public int column_display_widths[];
    public int column_resize_policy;
    public int column_widths[];
    public int column_widths_ext[];
    public int num_columns;
    public int alignments[];
    public int left_margins[];
    public int right_margins[];
    Rectangle rect;
}
