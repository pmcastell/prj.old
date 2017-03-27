// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCListenerList.java

package jclass.util;

import java.io.Serializable;
import java.util.Enumeration;

// Referenced classes of package jclass.util:
//            JCListenerListEnumeration

public class JCListenerList
    implements Serializable
{

    private JCListenerList(JCListenerList jclistenerlist, Object obj)
    {
        next = null;
        listener = null;
        next = jclistenerlist;
        listener = obj;
    }

    public static JCListenerList add(JCListenerList jclistenerlist, Object obj)
    {
        return new JCListenerList(jclistenerlist, obj);
    }

    public static Enumeration elements(JCListenerList jclistenerlist)
    {
        return new JCListenerListEnumeration(jclistenerlist);
    }

    public static JCListenerList remove(JCListenerList jclistenerlist, Object obj)
    {
        JCListenerList jclistenerlist1 = null;
        if(jclistenerlist == null)
            return null;
        for(; jclistenerlist != null; jclistenerlist = jclistenerlist.next)
            if(jclistenerlist.listener != obj)
                jclistenerlist1 = add(jclistenerlist1, jclistenerlist.listener);

        return jclistenerlist1;
    }

    JCListenerList next;
    Object listener;
}
