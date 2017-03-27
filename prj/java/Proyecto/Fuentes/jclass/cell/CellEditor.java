// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CellEditor.java

package jclass.cell;

import java.awt.Component;
import java.awt.Dimension;
import java.io.Serializable;

// Referenced classes of package jclass.cell:
//            CellEditorEventSource, CellInfo, KeyModifier, InitialEvent

public interface CellEditor
    extends CellEditorEventSource, Serializable
{

    public abstract void cancelCellEditing();

    public abstract Object getCellEditorValue();

    public abstract Component getComponent();

    public abstract Dimension getPreferredSize(CellInfo cellinfo, Object obj);

    public abstract KeyModifier[] getReservedKeys();

    public abstract void initialize(InitialEvent initialevent, CellInfo cellinfo, Object obj);

    public abstract boolean isModified();

    public abstract boolean stopCellEditing();
}
