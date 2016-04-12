// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VetoableChangeSupport.java

package symantec.itools.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class VetoableChangeSupport extends java.beans.VetoableChangeSupport
    implements Serializable
{

    public VetoableChangeSupport(Object obj)
    {
        super(obj);
        vetoableChangeSupportSerializedDataVersion = 1;
        source = obj;
    }

    public synchronized void addVetoableChangeListener(String s, VetoableChangeListener vetoablechangelistener)
    {
        if(listenerTable == null)
            listenerTable = new Hashtable();
        Vector vector;
        if(listenerTable.containsKey(s))
            vector = (Vector)listenerTable.get(s);
        else
            vector = new Vector();
        vector.addElement(vetoablechangelistener);
        listenerTable.put(s, vector);
    }

    public void fireVetoableChange(String s, Object obj, Object obj1)
        throws PropertyVetoException
    {
        if(obj != null && obj.equals(obj1))
            return;
        super.fireVetoableChange(s, obj, obj1);
        Hashtable hashtable = null;
        synchronized(this)
        {
            if(listenerTable == null || !listenerTable.containsKey(s))
                return;
            hashtable = (Hashtable)listenerTable.clone();
        }
        Vector vector = (Vector)hashtable.get(s);
        PropertyChangeEvent propertychangeevent = new PropertyChangeEvent(source, s, obj, obj1);
        try
        {
            for(int i = 0; i < vector.size(); i++)
            {
                VetoableChangeListener vetoablechangelistener = (VetoableChangeListener)vector.elementAt(i);
                vetoablechangelistener.vetoableChange(propertychangeevent);
            }

            return;
        }
        catch(PropertyVetoException propertyvetoexception)
        {
            PropertyChangeEvent propertychangeevent1 = new PropertyChangeEvent(source, s, obj1, obj);
            for(int j = 0; j < vector.size(); j++)
                try
                {
                    VetoableChangeListener vetoablechangelistener1 = (VetoableChangeListener)vector.elementAt(j);
                    vetoablechangelistener1.vetoableChange(propertychangeevent1);
                }
                catch(PropertyVetoException propertyvetoexception1) { }

            throw propertyvetoexception;
        }
    }

    public synchronized void removeVetoableChangeListener(String s, VetoableChangeListener vetoablechangelistener)
    {
        if(listenerTable == null || !listenerTable.containsKey(s))
        {
            return;
        } else
        {
            Vector vector = (Vector)listenerTable.get(s);
            vector.removeElement(vetoablechangelistener);
            return;
        }
    }

    protected Hashtable listenerTable;
    private Object source;
    private int vetoableChangeSupportSerializedDataVersion;
}
