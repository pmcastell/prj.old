// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CellEditorSupport.java

package jclass.cell;

import java.io.Serializable;
import java.util.Enumeration;
import jclass.util.JCListenerList;

// Referenced classes of package jclass.cell:
//            CellEditorEventSource, CellEditorListener, CellEditorEvent

public class CellEditorSupport
    implements CellEditorEventSource, Serializable
{

    public CellEditorSupport()
    {
    }

    public void addCellEditorListener(CellEditorListener celleditorlistener)
    {
        cellEditorListeners = JCListenerList.add(cellEditorListeners, celleditorlistener);
    }

    public void fireCancelEditing(CellEditorEvent celleditorevent)
    {
        if(celleditorevent == null)
            return;
        CellEditorListener celleditorlistener;
        for(Enumeration enumeration = JCListenerList.elements(cellEditorListeners); enumeration.hasMoreElements(); celleditorlistener.editingCanceled(celleditorevent))
            celleditorlistener = (CellEditorListener)enumeration.nextElement();

    }

    public void fireStopEditing(CellEditorEvent celleditorevent)
    {
        if(celleditorevent == null)
            return;
        CellEditorListener celleditorlistener;
        for(Enumeration enumeration = JCListenerList.elements(cellEditorListeners); enumeration.hasMoreElements(); celleditorlistener.editingStopped(celleditorevent))
            celleditorlistener = (CellEditorListener)enumeration.nextElement();

    }

    public void removeCellEditorListener(CellEditorListener celleditorlistener)
    {
        cellEditorListeners = JCListenerList.remove(cellEditorListeners, celleditorlistener);
    }

    protected JCListenerList cellEditorListeners;
}
