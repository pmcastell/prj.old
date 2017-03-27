// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCMultiColumnListComponent.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.util.Vector;
import javax.swing.border.Border;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;
import jclass.util.*;

// Referenced classes of package jclass.bwt:
//            JCListComponent, BWTEnum, BWTUtil, JCComponent, 
//            JCHeader, JCMultiColumnData, JCMultiColumnInterface, JCMultiColumnWindow, 
//            MultiColumnConverter

public class JCMultiColumnListComponent extends JCListComponent
    implements JCMultiColumnInterface
{

    public JCMultiColumnListComponent()
    {
        this(null, null, null);
    }

    public JCMultiColumnListComponent(int i, boolean flag)
    {
        super(i, flag);
        data = new JCMultiColumnData(this);
        num_columns_set = false;
        largest_size = 0;
    }

    public JCMultiColumnListComponent(JCVector jcvector)
    {
        this(jcvector, null, null);
    }

    public JCMultiColumnListComponent(JCVector jcvector, Applet applet, String s)
    {
        super(jcvector, applet, s);
        data = new JCMultiColumnData(this);
        num_columns_set = false;
        largest_size = 0;
        if(s == null)
            setName("multicolumnlist" + nameCounter++);
        super.border = super.highlight = super.spacing = 1;
        super.insets.left = super.insets.right = 0;
        if(getClass().getName().equals("jclass.bwt.JCMultiColumnListComponent"))
            getParameters(applet);
        if(jcvector != null)
            setItems(jcvector);
    }

    public void addItem(Applet applet, String s, char c)
    {
        addItem(JCComponent.conv.toVector(applet, s, c, true));
    }

    public void addItem(String s, char c)
    {
        addItem(JCComponent.conv.toVector(this, s, c, true));
    }

    public void addNotify()
    {
        super.addNotify();
        if(!num_columns_set)
        {
            int i = 0;
            for(int j = super.items.size(); i < j; i++)
            {
                Object obj = super.items.elementAt(i);
                if((obj instanceof Vector) && !BWTUtil.is_jcstring(obj))
                    largest_size = Math.max(largest_size, ((Vector)obj).size());
            }

            if(getNumColumns() < largest_size)
            {
                data.setNumColumns(largest_size);
                if(getHeader() != null)
                    getHeader().data.setNumColumns(largest_size);
            }
        }
        calcColumnWidths();
        super.pref_width_internal = data.preferredWidth();
    }

    void calcColumnWidths()
    {
        if(super.batched)
            super.needs_recalc = true;
        else
            data.calcColumnWidths();
    }

    protected void calcSize(int i, boolean flag)
    {
        int j;
        synchronized(this)
        {
            super.pref_width_internal = 0;
            calcColumnWidths();
            if(!num_columns_set && i >= 0 && i < super.items.size())
            {
                Object obj = super.items.elementAt(i);
                if((obj instanceof Vector) && !BWTUtil.is_jcstring(obj))
                    largest_size = Math.max(largest_size, ((Vector)obj).size());
            }
            j = largest_size;
        }
        if(getNumColumns() < j)
        {
            data.setNumColumns(j);
            if(getHeader() != null)
                getHeader().setNumColumns(j);
        }
        boolean flag1;
        synchronized(this)
        {
            flag1 = super.batched;
            if(super.batched)
                super.needs_recalc = true;
        }
        if(!flag1)
        {
            super.calcSize(i, flag);
            if(getPeer() != null)
            {
                calcColumnWidths();
                calcSize(true);
                if(getHeader() != null)
                    getHeader().recalc();
            }
        }
    }

    public int calcWidth(int i)
    {
        int j = 0;
        for(int k = 0; k < super.items.size(); k++)
        {
            Object obj = null;
            Object obj1 = super.items.elementAt(k);
            if((obj1 instanceof Vector) && !BWTUtil.is_jcstring(obj))
            {
                if(i < ((Vector)obj1).size())
                    obj = ((Vector)obj1).elementAt(i);
            } else
            {
                obj = obj1;
            }
            j = Math.max(j, BWTUtil.getWidth(obj, this));
        }

        return j + getColumnLeftMargin(i) + getColumnRightMargin(i);
    }

    protected void draw(Graphics g, Object obj, int i, Rectangle rectangle)
    {
        data.draw(g, obj, rectangle);
    }

    protected void drawHighlight(Graphics g, boolean flag)
    {
        super.drawHighlight(g, flag);
        if(flag)
        {
            return;
        } else
        {
            g.setColor(super.highlight_color == null ? Color.black : super.highlight_color);
            g.drawRect(super.highlight - 1, super.highlight - 1, (size().width - 2 * super.highlight) + 1, (size().height - 2 * super.highlight) + 1);
            return;
        }
    }

    public int getColumnAlignment(int i)
    {
        return data.getColumnAlignment(i);
    }

    public int[] getColumnAlignments()
    {
        return data.getColumnAlignments();
    }

    public int getColumnDisplayWidth(int i)
    {
        return data.getColumnDisplayWidth(i);
    }

    public int[] getColumnDisplayWidths()
    {
        return data.getColumnDisplayWidths();
    }

    public int getColumnLeftMargin(int i)
    {
        return data.getColumnLeftMargin(i);
    }

    public int getColumnPosition(int i)
    {
        return (data.getColumnPosition(i) + super.border + 2 * super.highlight + super.insets.left) - super.horiz_origin;
    }

    public int getColumnResizePolicy()
    {
        return data.getColumnResizePolicy();
    }

    public int getColumnRightMargin(int i)
    {
        return data.getColumnRightMargin(i);
    }

    public int getColumnWidth(int i)
    {
        return data.getColumnWidth(i);
    }

    public int[] getColumnWidths()
    {
        return data.getColumnWidths();
    }

    JCHeader getHeader()
    {
        return super.scrolled_window == null || !(super.scrolled_window instanceof JCMultiColumnWindow) ? null : ((JCMultiColumnWindow)super.scrolled_window).getHeader();
    }

    public synchronized String[] getItemsStrings()
    {
        String as[] = new String[super.items.size()];
        for(int i = 0; i < as.length; i++)
            as[i] = JCUtilConverter.toString(super.items.elementAt(i));

        return as;
    }

    public JCMultiColumnData getMultiColumnData()
    {
        return data;
    }

    public int getNumColumns()
    {
        return data.num_columns;
    }

    protected void getParameters()
    {
        super.getParameters();
        MultiColumnConverter.getParams(this, JCComponent.conv);
    }

    JCMultiColumnWindow getScrolledWindow()
    {
        return super.scrolled_window == null || !(super.scrolled_window instanceof JCMultiColumnWindow) ? null : (JCMultiColumnWindow)super.scrolled_window;
    }

    protected int preferredWidth()
    {
        if(super.pref_width_internal == 0)
        {
            calcColumnWidths();
            if(getHeader() != null)
                getHeader().recalc();
        }
        return super.pref_width_internal = data.preferredWidth();
    }

    public void setBatched(boolean flag)
    {
        if(super.batched == flag)
            return;
        super.batched = flag;
        if(getHeader() != null)
            getHeader().batched = flag;
        if(super.batched)
            return;
        if(super.needs_recalc)
        {
            calcColumnWidths();
            calcSize(true);
            if(getHeader() != null)
                getHeader().recalc();
        }
        if(super.needs_recalc || super.needs_repaint)
            repaint();
        super.needs_recalc = super.needs_repaint = false;
    }

    public void setBorder(Border border)
    {
    }

    public void setColumnAlignment(int i, int j)
    {
        data.setColumnAlignment(i, j);
        repaint();
    }

    public void setColumnAlignments(int ai[])
    {
        data.setColumnAlignments(ai);
        repaint();
    }

    public void setColumnButtons(JCVector jcvector)
    {
        if(getScrolledWindow() != null)
            getScrolledWindow().setColumnButtons(jcvector);
    }

    public void setColumnDisplayWidth(int i, int j)
    {
        data.setColumnDisplayWidth(i, j);
        if(getHeader() != null)
            getHeader().setColumnDisplayWidth(i, j);
        updateParent();
        repaint();
    }

    public void setColumnDisplayWidths(int ai[])
    {
        data.setColumnDisplayWidths(ai);
        repaint();
    }

    public void setColumnLabels(JCVector jcvector)
    {
        if(getScrolledWindow() != null)
            getScrolledWindow().setColumnLabels(jcvector);
    }

    public void setColumnLeftMargin(int i, int j)
    {
        data.setColumnLeftMargin(i, j);
        calcColumnWidths();
        repaint();
    }

    public void setColumnResizePolicy(int i)
    {
        data.setColumnResizePolicy(i);
    }

    public void setColumnRightMargin(int i, int j)
    {
        data.setColumnRightMargin(i, j);
        calcColumnWidths();
        repaint();
    }

    public void setColumnWidth(int i, int j)
    {
        data.setColumnWidth(i, j);
        if(getHeader() != null)
            getHeader().setColumnWidth(i, j);
        updateParent();
        repaint();
    }

    public void setColumnWidths(int ai[])
    {
        data.setColumnWidths(ai);
        repaint();
    }

    public void setFont(Font font)
    {
        super.setFont(font);
        if(getPeer() == null)
            return;
        calcColumnWidths();
        calcSize(false);
        if(super.scrolled_window == null)
            repaint();
    }

    public void setItems(String as[])
    {
        JCVector jcvector = new JCVector(as);
        for(int i = 0; i < jcvector.size(); i++)
            jcvector.setElementAt(i, JCComponent.conv.toVector(this, as[i], ',', true));

        setItems(jcvector);
    }

    public void setNumColumns(int i)
    {
        data.setNumColumns(i == -998 ? largest_size : i);
        num_columns_set = i != -998;
        calcColumnWidths();
        repaint();
    }

    public void sortByColumn(int i, JCSortInterface jcsortinterface)
    {
        sortByColumn(i, jcsortinterface, 0);
    }

    public void sortByColumn(int i, JCSortInterface jcsortinterface, int j)
    {
        int ai[] = new int[0];
        int k = 0;
        synchronized(this)
        {
            k = super.items.size();
            int ai1[] = (new JCqsort(super.items, jcsortinterface)).sort(i, j);
            if(ai1 == null)
                return;
            if(super.focus_row != -1)
                super.focus_row = ai1[super.focus_row];
            ai = new int[ai1.length];
            for(int l = 0; l < ai1.length; l++)
                ai[ai1[l]] = l;

            if(super.userdata_list != null)
            {
                updateUserdataList();
                Object aobj[] = new Object[k];
                for(int k1 = 0; k1 < k; k1++)
                    aobj[k1] = super.userdata_list[ai[k1]];

                super.userdata_list = aobj;
            }
            if(super.selected != null && super.selected.length >= k)
            {
                boolean aflag[] = new boolean[k];
                for(int l1 = 0; l1 < k; l1++)
                {
                    aflag[l1] = super.selected[ai[l1]];
                    super.last_selected[l1] = false;
                }

                super.selected = aflag;
            }
        }
        if(super.row_height_ext != -998)
        {
            repaint();
            return;
        }
        synchronized(this)
        {
            if(super.row_heights.length == k)
            {
                int ai2[] = new int[k];
                for(int i1 = 0; i1 < k; i1++)
                    ai2[i1] = super.row_heights[ai[i1]];

                super.row_heights = ai2;
                for(int j1 = 1; j1 < k; j1++)
                    super.row_pos[j1] = super.row_pos[j1 - 1] + super.row_heights[j1 - 1];

            }
        }
        if(super.focus_row != -1)
            makeVisible(super.focus_row);
        repaint();
    }

    protected JCMultiColumnData data;
    boolean num_columns_set;
    int largest_size;
    private static final String base = "multicolumnlist";
    private static int nameCounter = 0;

}
