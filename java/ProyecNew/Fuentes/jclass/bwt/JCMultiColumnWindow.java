// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCMultiColumnWindow.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.beans.Beans;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.JComponent;
import jclass.base.TransientComponent;
import jclass.util.*;

// Referenced classes of package jclass.bwt:
//            JCScrolledWindow, BWTEnum, HeaderButton, JCActionListener, 
//            JCComponent, JCContainer, JCHeader, JCLabel, 
//            JCMultiColumnInterface, LabelConverter, Viewport, JCActionEvent, 
//            JCMultiColumnData, JCAdjustmentEvent

public abstract class JCMultiColumnWindow extends JCScrolledWindow
    implements JCMultiColumnInterface, JCActionListener
{

    public JCMultiColumnWindow()
    {
        super(null, null);
        column_label_sort = true;
    }

    public JCMultiColumnWindow(Applet applet, String s)
    {
        super(applet, s);
        column_label_sort = true;
    }

    public void actionPerformed(JCActionEvent jcactionevent)
    {
        if(header == null || !getColumnLabelSort())
            return;
        int i = 0;
        Component component = (Component)jcactionevent.getSource();
        int j = header.getComponent(component);
        if(j < 0)
            return;
        if(component instanceof HeaderButton)
            i = ((HeaderButton)component).direction;
        sortByColumn(j, sort_method, i);
        if(component instanceof HeaderButton)
            ((HeaderButton)component).direction = i != 0 ? 0 : 1;
    }

    public void addNotify()
    {
        super.addNotify();
        if(headerArea != null)
        {
            addInternal(headerArea);
            header.setMultiColumnComponent((JCMultiColumnInterface)super.viewport);
        }
    }

    public int calcWidth(int i)
    {
        return comp.calcWidth(i);
    }

    public int getColumnAlignment(int i)
    {
        return comp.getColumnAlignment(i);
    }

    public int[] getColumnAlignments()
    {
        return comp.getColumnAlignments();
    }

    public int getColumnDisplayWidth(int i)
    {
        return comp.getColumnDisplayWidth(i);
    }

    public int[] getColumnDisplayWidths()
    {
        return comp.getColumnDisplayWidths();
    }

    public boolean getColumnLabelSort()
    {
        return column_label_sort;
    }

    public JCSortInterface getColumnLabelSortMethod()
    {
        return sort_method;
    }

    public String[] getColumnLabels()
    {
        String as[] = null;
        if(header == null)
        {
            if(Beans.isDesignTime())
                as = new String[0];
            return as;
        }
        JCComponent ajccomponent[] = header.getLabels();
        as = new String[ajccomponent.length];
        for(int i = 0; i < ajccomponent.length; i++)
            if(ajccomponent[i] instanceof JCLabel)
            {
                Object obj = ((JCLabel)ajccomponent[i]).getLabel();
                as[i] = obj == null ? "" : obj.toString();
            } else
            {
                as[i] = ajccomponent[i].getName();
            }

        return as;
    }

    public int getColumnLeftMargin(int i)
    {
        return comp.getColumnLeftMargin(i);
    }

    public int getColumnPosition(int i)
    {
        return comp.getColumnPosition(i);
    }

    public int getColumnResizePolicy()
    {
        return comp.getColumnResizePolicy();
    }

    public int getColumnRightMargin(int i)
    {
        return comp.getColumnRightMargin(i);
    }

    public int getColumnWidth(int i)
    {
        return comp.getColumnWidth(i);
    }

    public int[] getColumnWidths()
    {
        return comp.getColumnWidths();
    }

    public JCHeader getHeader()
    {
        if(header == null)
            setHeader(new JCHeader(null, super.applet, getName()));
        return header;
    }

    public JCMultiColumnData getMultiColumnData()
    {
        return comp.getMultiColumnData();
    }

    public int getNumColumns()
    {
        return comp.getNumColumns();
    }

    protected int getViewportWidth()
    {
        int i = super.getViewportWidth();
        if(headerArea != null)
            i = Math.max(i, JCComponent.getPreferredSize(headerArea).width);
        return i;
    }

    protected int headerHeight()
    {
        return headerArea != null ? JCComponent.getPreferredSize(headerArea).height : 0;
    }

    protected int headerWidth()
    {
        return headerArea != null ? JCComponent.getPreferredSize(headerArea).width : 0;
    }

    protected int preferredHeight()
    {
        int i = super.preferredHeight();
        if(headerArea != null)
            i += JCComponent.getPreferredSize(headerArea).height;
        return i;
    }

    protected int reshapeHeader(int i, int j, int k)
    {
        int l = headerHeight();
        if(headerArea != null)
            JCComponent.setBounds(headerArea, i, j, k, l);
        return l;
    }

    protected void reshapeViewport(int i, int j, int k, int l)
    {
        if(headerArea != null)
            j -= ((JCComponent)comp).getHighlightThickness();
        super.reshapeViewport(i, j, k, l);
    }

    protected void scrollHorizontal(JCAdjustmentEvent jcadjustmentevent, int i)
    {
        if(headerArea != null)
            headerArea.scrollHorizontal(i);
        super.scrollHorizontal(jcadjustmentevent, i);
    }

    public void setColumnAlignment(int i, int j)
    {
        LabelConverter.checkAlignment(j);
        comp.setColumnAlignment(i, j);
    }

    public void setColumnAlignments(int ai[])
    {
        comp.setColumnAlignments(ai);
    }

    public void setColumnButtons(JCVector jcvector)
    {
        if(header == null)
        {
            if(jcvector == null || jcvector.size() == 0)
                return;
            setHeader(new JCHeader(null, super.applet, getName()));
        }
        if(jcvector == null || jcvector.size() == 0)
            setHeader(null);
        else
            header.setButtons(jcvector);
    }

    public void setColumnButtons(String as[])
    {
        setColumnButtons(new JCVector(as));
    }

    public void setColumnButtonsStrings(String as[])
    {
        setColumnButtons(new JCVector(as));
    }

    public void setColumnDisplayWidth(int i, int j)
    {
        if(header != null)
            header.setColumnDisplayWidth(i, j);
        comp.setColumnDisplayWidth(i, j);
    }

    public void setColumnDisplayWidths(int ai[])
    {
        if(header != null)
            header.setColumnDisplayWidths(ai);
        comp.setColumnDisplayWidths(ai);
    }

    public void setColumnLabelAlignment(int i, int j)
    {
        LabelConverter.checkAlignment(j);
        if(header != null)
            header.setColumnAlignment(i, j);
    }

    public void setColumnLabelSort(boolean flag)
    {
        column_label_sort = flag;
    }

    public void setColumnLabelSortMethod(JCSortInterface jcsortinterface)
    {
        sort_method = jcsortinterface;
    }

    public void setColumnLabels(JCVector jcvector)
    {
        if(header == null)
        {
            if(jcvector == null || jcvector.size() == 0)
                return;
            setHeader(new JCHeader(null, super.applet, getName()));
        }
        if(jcvector == null || jcvector.size() == 0)
            setHeader(null);
        else
            header.setLabels(jcvector);
    }

    public void setColumnLabels(String as[])
    {
        setColumnLabels(new JCVector(as));
    }

    public void setColumnLabelsStrings(String as[])
    {
        setColumnLabels(new JCVector(as));
    }

    public void setColumnLeftMargin(int i, int j)
    {
        comp.setColumnLeftMargin(i, j);
        if(header != null)
            header.setColumnLeftMargin(i, j);
        layout();
    }

    public void setColumnResizePolicy(int i)
    {
        if(header != null)
            header.setColumnResizePolicy(i);
        comp.setColumnResizePolicy(i);
    }

    public void setColumnRightMargin(int i, int j)
    {
        comp.setColumnRightMargin(i, j);
        if(header != null)
            header.setColumnRightMargin(i, j);
        layout();
    }

    public void setColumnWidth(int i, int j)
    {
        if(j < 0 && j != -998)
            throw new IllegalArgumentException("invalid column width: " + j);
        if(header != null)
            header.setColumnWidth(i, j);
        comp.setColumnWidth(i, j);
    }

    public void setColumnWidths(int ai[])
    {
        if(header != null)
            header.setColumnWidths(ai);
        comp.setColumnWidths(ai);
    }

    public void setFont(Font font)
    {
        Font font1 = getFont();
        if(font1 != null && font1.equals(font))
            return;
        super.setFont(font);
        if(getPeer() == null)
            return;
        ((Component)comp).setFont(font);
        if(header != null)
            header.setFont(font);
        layout();
    }

    public void setHeader(JCHeader jcheader)
    {
        header = jcheader;
        if(jcheader == null)
        {
            if(headerArea != null)
                remove(headerArea);
            headerArea = null;
            return;
        }
        jcheader.setColumnResizePolicy(getColumnResizePolicy());
        if(headerArea == null)
            headerArea = new Viewport();
        headerArea.add(jcheader);
        jcheader.setMultiColumnComponent((JCMultiColumnInterface)super.viewport);
        jcheader.addActionListener(this);
        if(getPeer() != null)
            addInternal(headerArea);
    }

    public void setNumColumns(int i)
    {
        if(i < 0 && i != -998)
            throw new IllegalArgumentException("invalid NumColumns: " + i);
        comp.setNumColumns(i);
        if(header != null)
            header.setNumColumns(i);
        layout();
    }

    protected void setViewport(JCMultiColumnInterface jcmulticolumninterface)
    {
        comp = jcmulticolumninterface;
        super.viewport = (Component)jcmulticolumninterface;
    }

    public void sortByColumn(int i, JCSortInterface jcsortinterface)
    {
    }

    public void sortByColumn(int i, JCSortInterface jcsortinterface, int j)
    {
    }

    public static final int ASCENDING = 0;
    public static final int DESCENDING = 1;
    JCSortInterface sort_method;
    boolean column_label_sort;
    protected Viewport headerArea;
    protected JCHeader header;
    protected JCMultiColumnInterface comp;
}
