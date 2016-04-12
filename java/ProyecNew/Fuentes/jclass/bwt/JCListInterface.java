// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCListInterface.java

package jclass.bwt;

import java.awt.Color;
import jclass.util.JCVector;

// Referenced classes of package jclass.bwt:
//            JCItemSelectable

public interface JCListInterface
    extends JCItemSelectable
{

    public abstract void addItem(Object obj);

    public abstract void addItem(Object obj, int i);

    public abstract boolean allowsMultipleSelections();

    public abstract void clear();

    public abstract int countItems();

    public abstract void deleteItem(int i);

    public abstract void deleteItems(int i, int j);

    public abstract void deselect(int i);

    public abstract boolean getAutoSelect();

    public abstract Object getItem(int i);

    public abstract JCVector getItems();

    public abstract int getRows();

    public abstract Color getSelectedBackground();

    public abstract Color getSelectedForeground();

    public abstract int getSelectedIndex();

    public abstract int[] getSelectedIndexes();

    public abstract Object getSelectedItem();

    public abstract Object[] getSelectedObjects();

    public abstract int getSpacing();

    public abstract int getTopRow();

    public abstract int getVisibleIndex();

    public abstract boolean isSelected(int i);

    public abstract void makeVisible(int i);

    public abstract void replaceItem(Object obj, int i);

    public abstract void select(int i);

    public abstract void setAllowMultipleSelections(boolean flag);

    public abstract void setAutoSelect(boolean flag);

    public abstract void setItems(JCVector jcvector);

    public abstract void setRowHeight(int i);

    public abstract void setSelectedBackground(Color color);

    public abstract void setSelectedForeground(Color color);

    public abstract void setSpacing(int i);

    public abstract void setTopRow(int i);

    public abstract void setVisibleRows(int i);
}
