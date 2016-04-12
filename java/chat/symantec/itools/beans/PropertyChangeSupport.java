// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertyChangeSupport.java

package symantec.itools.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class PropertyChangeSupport extends java.beans.PropertyChangeSupport
    implements Serializable
{

    public PropertyChangeSupport(Object obj)
    {
        super(obj);
        propertyChangeSupportSerializedDataVersion = 1;
        source = obj;
    }

    public synchronized void addPropertyChangeListener(String s, PropertyChangeListener propertychangelistener)
    {
        if(listenerTable == null)
            listenerTable = new Hashtable();
        Vector vector;
        if(listenerTable.containsKey(s))
            vector = (Vector)listenerTable.get(s);
        else
            vector = new Vector();
        vector.addElement(propertychangelistener);
        listenerTable.put(s, vector);
    }

    public void firePropertyChange(String s, Object obj, Object obj1)
    {
        if(obj != null && obj.equals(obj1))
            return;
        super.firePropertyChange(s, obj, obj1);
        Hashtable hashtable = null;
        synchronized(this)
        {
            if(listenerTable == null || !listenerTable.containsKey(s))
                return;
            hashtable = (Hashtable)listenerTable.clone();
        }
        Vector vector = (Vector)hashtable.get(s);
        PropertyChangeEvent propertychangeevent = new PropertyChangeEvent(source, s, obj, obj1);
        for(int i = 0; i < vector.size(); i++)
        {
            PropertyChangeListener propertychangelistener = (PropertyChangeListener)vector.elementAt(i);
            propertychangelistener.propertyChange(propertychangeevent);
        }

    }

    public synchronized void removePropertyChangeListener(String s, PropertyChangeListener propertychangelistener)
    {
        if(listenerTable == null || !listenerTable.containsKey(s))
        {
            return;
        } else
        {
            Vector vector = (Vector)listenerTable.get(s);
            vector.removeElement(propertychangelistener);
            return;
        }
    }

    protected Hashtable listenerTable;
    private Object source;
    private int propertyChangeSupportSerializedDataVersion;
}
