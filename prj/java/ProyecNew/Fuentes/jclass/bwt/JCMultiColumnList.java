// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCMultiColumnList.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;
import jclass.util.JCSortInterface;
import jclass.util.JCVector;

// Referenced classes of package jclass.bwt:
//            JCMultiColumnWindow, BWTEnum, JCComponent, JCContainer, 
//            JCListComponent, JCListInterface, JCMultiColumnListComponent, JCScrolledWindow, 
//            JCActionListener, JCItemListener

public class JCMultiColumnList extends JCMultiColumnWindow
    implements JCListInterface
{

    public JCMultiColumnList()
    {
        this(null, null, null);
    }

    public JCMultiColumnList(int i, boolean flag)
    {
        this(null, null, null);
        list.setVisibleRows(i);
        list.setAllowMultipleSelections(flag);
    }

    public JCMultiColumnList(JCVector jcvector)
    {
        this(jcvector, null, null);
    }

    public JCMultiColumnList(JCVector jcvector, Applet applet, String s)
    {
        super(applet, s);
        if(s == null)
            setName("multicolumnlist" + nameCounter++);
        setList(new JCMultiColumnListComponent(jcvector, applet, getName()));
        if(getClass().getName().equals("jclass.bwt.JCMultiColumnList"))
            getParameters(applet);
    }

    public void addActionListener(JCActionListener jcactionlistener)
    {
        list.addActionListener(jcactionlistener);
    }

    public void addFocusListener(FocusListener focuslistener)
    {
        if(list != null)
            list.addFocusListener(focuslistener);
    }

    public void addItem(Applet applet, String s, char c)
    {
        list.addItem(applet, s, c);
    }

    public void addItem(Object obj)
    {
        list.addItem(obj);
    }

    public void addItem(Object obj, int i)
    {
        list.addItem(obj, i);
    }

    public void addItem(String s, char c)
    {
        list.addItem(s, c);
    }

    public void addItemListener(JCItemListener jcitemlistener)
    {
        list.addItemListener(jcitemlistener);
    }

    public void addKeyListener(KeyListener keylistener)
    {
        if(list != null)
            list.addKeyListener(keylistener);
    }

    public boolean allowsMultipleSelections()
    {
        return list.allowsMultipleSelections();
    }

    public void clear()
    {
        list.clear();
    }

    public int countItems()
    {
        return list.countItems();
    }

    public void deleteItem(int i)
    {
        list.deleteItem(i);
    }

    public void deleteItems(int i, int j)
    {
        list.deleteItems(i, j);
    }

    public void deselect(int i)
    {
        list.deselect(i);
    }

    public boolean getAllowMultipleSelections()
    {
        return list.allowsMultipleSelections();
    }

    public boolean getAutoSelect()
    {
        return list.getAutoSelect();
    }

    public boolean getBatched()
    {
        return list.getBatched();
    }

    public Object getItem(int i)
    {
        return list.getItem(i);
    }

    public JCVector getItems()
    {
        return list.getItems();
    }

    public synchronized String[] getItemsStrings()
    {
        return list.getItemsStrings();
    }

    public JCMultiColumnListComponent getList()
    {
        return list;
    }

    public int getRowHeight()
    {
        return list.getRowHeight();
    }

    public int getRows()
    {
        return list.getRows();
    }

    public Color getSelectedBackground()
    {
        return list.getSelectedBackground();
    }

    public Color getSelectedForeground()
    {
        return list.getSelectedForeground();
    }

    public int getSelectedIndex()
    {
        return list.getSelectedIndex();
    }

    public int[] getSelectedIndexes()
    {
        return list.getSelectedIndexes();
    }

    public Object getSelectedItem()
    {
        return list.getSelectedItem();
    }

    public Object[] getSelectedObjects()
    {
        return list.getSelectedObjects();
    }

    public int getSpacing()
    {
        return list.getSpacing();
    }

    public int getTopRow()
    {
        return list.getTopRow();
    }

    public Object[] getUserDataList()
    {
        return ((JCListComponent) (list)).userdata_list;
    }

    protected int getViewportHeight()
    {
        int i = list.countItems() - 1;
        if(i < 0)
            return super.getViewportHeight();
        else
            return list.getRowPosition(i) + list.getRowHeight(i) + 2 * (list.getBorderThickness() + list.getHighlightThickness()) + list.getInsets().top + list.getInsets().bottom;
    }

    public int getVisibleIndex()
    {
        return list.getVisibleIndex();
    }

    public boolean isSelected(int i)
    {
        return list.isSelected(i);
    }

    public void makeVisible(int i)
    {
        list.makeVisible(i);
    }

    public void paintRow(int i)
    {
        list.paintRow(i);
    }

    protected int preferredWidth()
    {
        if(list == null || list.countItems() == 0)
            return super.header == null ? 100 : JCComponent.getPreferredSize(super.header).width;
        int i = JCComponent.getPreferredSize(list).width;
        if(((JCListComponent) (list)).visible_rows == 0 || ((JCListComponent) (list)).visible_rows >= list.countItems())
            i += sb_size();
        else
            i += 16 + super.sb_offset;
        return Math.max(headerWidth(), i);
    }

    public void removeActionListener(JCActionListener jcactionlistener)
    {
        list.removeActionListener(jcactionlistener);
    }

    public void removeFocusListener(FocusListener focuslistener)
    {
        if(list != null)
            list.removeFocusListener(focuslistener);
    }

    public void removeItemListener(JCItemListener jcitemlistener)
    {
        list.removeItemListener(jcitemlistener);
    }

    public void removeKeyListener(KeyListener keylistener)
    {
        if(list != null)
            list.removeKeyListener(keylistener);
    }

    public void replaceItem(Object obj, int i)
    {
        list.replaceItem(obj, i);
    }

    public void select(int i)
    {
        list.select(i);
    }

    public void setAllowMultipleSelections(boolean flag)
    {
        list.setAllowMultipleSelections(flag);
    }

    public void setAutoSelect(boolean flag)
    {
        list.setAutoSelect(flag);
    }

    public void setBatched(boolean flag)
    {
        list.setBatched(flag);
    }

    public void setItems(JCVector jcvector)
    {
        list.setItems(jcvector);
    }

    public void setItems(String as[])
    {
        list.setItems(as);
    }

    public void setItemsStrings(String as[])
    {
        list.setItems(as);
    }

    public void setList(JCMultiColumnListComponent jcmulticolumnlistcomponent)
    {
        list = jcmulticolumnlistcomponent;
        jcmulticolumnlistcomponent.scrolled_window = this;
        setViewport(jcmulticolumnlistcomponent);
        if(getPeer() != null)
            validate();
        jcmulticolumnlistcomponent.addKeyListener(this);
    }

    public void setRowHeight(int i)
    {
        list.setRowHeight(i);
    }

    public void setSelectedBackground(Color color)
    {
        list.setSelectedBackground(color);
    }

    public void setSelectedForeground(Color color)
    {
        list.setSelectedForeground(color);
    }

    public void setSpacing(int i)
    {
        list.setSpacing(i);
    }

    public void setTopRow(int i)
    {
        list.setTopRow(i);
    }

    public void setUserDataList(Object aobj[])
    {
        list.setUserDataList(aobj);
    }

    public void setVisibleRows(int i)
    {
        list.setVisibleRows(i);
    }

    public void sortByColumn(int i, JCSortInterface jcsortinterface)
    {
        list.sortByColumn(i, jcsortinterface);
    }

    public void sortByColumn(int i, JCSortInterface jcsortinterface, int j)
    {
        list.sortByColumn(i, jcsortinterface, j);
    }

    JCMultiColumnListComponent list;
    private static final String base = "multicolumnlist";
    private static int nameCounter = 0;

}
