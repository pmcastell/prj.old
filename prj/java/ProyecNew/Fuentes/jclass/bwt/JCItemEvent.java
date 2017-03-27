// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCItemEvent.java

package jclass.bwt;

import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.util.EventObject;

// Referenced classes of package jclass.bwt:
//            ItemSelectableObject

public class JCItemEvent extends ItemEvent
{

    public JCItemEvent(Object obj, int i, Object obj1, int j)
    {
        super(selectable_item, i, obj1, j);
        super.source = obj;
    }

    public Object getItem()
    {
        return super.getItem();
    }

    public int getStateChange()
    {
        return super.getStateChange();
    }

    private static ItemSelectable selectable_item = new ItemSelectableObject();

}
