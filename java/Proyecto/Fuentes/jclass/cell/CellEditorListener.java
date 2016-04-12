// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CellEditorListener.java

package jclass.cell;

import java.util.EventListener;

// Referenced classes of package jclass.cell:
//            CellEditorEvent

public interface CellEditorListener
    extends EventListener
{

    public abstract void editingCanceled(CellEditorEvent celleditorevent);

    public abstract void editingStopped(CellEditorEvent celleditorevent);
}
