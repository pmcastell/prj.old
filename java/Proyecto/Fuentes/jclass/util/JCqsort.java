// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCqsort.java

package jclass.util;

import java.util.Date;
import java.util.Vector;

// Referenced classes of package jclass.util:
//            JCSortInterface, JCString

public class JCqsort
{

    public JCqsort(Vector vector, JCSortInterface jcsortinterface)
    {
        base = vector;
        sort_if = jcsortinterface;
    }

    private void addVect(Vector vector, Vector vector1, Vector vector2)
    {
        vector.removeAllElements();
        for(int i = 0; i < vector1.size(); i++)
            vector.addElement(vector1.elementAt(i));

        for(int j = 0; j < vector2.size(); j++)
            vector.addElement(vector2.elementAt(j));

    }

    public int getDirection()
    {
        return direction;
    }

    public static boolean gt(Object obj, Object obj1, JCSortInterface jcsortinterface)
    {
        if(obj == null && obj1 == null)
            return false;
        if(obj == null && obj1 != null)
            return false;
        if(obj != null && obj1 == null)
            return true;
        if(jcsortinterface != null)
            return jcsortinterface.compare(obj, obj1);
        if((obj instanceof String) && (obj1 instanceof String))
            return ((String)obj).compareTo((String)obj1) > 0;
        if((obj instanceof Number) && (obj1 instanceof Number))
            return ((Number)obj).doubleValue() > ((Number)obj1).doubleValue();
        if((obj instanceof Date) && (obj1 instanceof Date))
            return ((Date)obj).getTime() > ((Date)obj1).getTime();
        if((obj instanceof JCString) && (obj1 instanceof JCString))
            return ((JCString)obj).getString().compareTo(((JCString)obj1).getString()) > 0;
        try
        {
            return obj.toString().compareTo(obj1.toString()) > 0;
        }
        catch(Exception _ex)
        {
            return true;
        }
    }

    public static boolean lt(Object obj, Object obj1, JCSortInterface jcsortinterface)
    {
        if(obj == null && obj1 == null)
            return false;
        if(obj == null && obj1 != null)
            return false;
        if(obj != null && obj1 == null)
            return true;
        if(jcsortinterface != null)
            return jcsortinterface.compare(obj1, obj);
        if((obj instanceof String) && (obj1 instanceof String))
            return ((String)obj).compareTo((String)obj1) < 0;
        if((obj instanceof Number) && (obj1 instanceof Number))
            return ((Number)obj).doubleValue() < ((Number)obj1).doubleValue();
        if((obj instanceof Date) && (obj1 instanceof Date))
            return ((Date)obj).getTime() < ((Date)obj1).getTime();
        if((obj instanceof JCString) && (obj1 instanceof JCString))
            return ((JCString)obj).getString().compareTo(((JCString)obj1).getString()) < 0;
        try
        {
            return obj.toString().compareTo(obj1.toString()) < 0;
        }
        catch(Exception _ex)
        {
            return true;
        }
    }

    private void qs(Vector vector, int i)
    {
        if(vector.size() < 2)
            return;
        Vector vector1 = new Vector();
        Vector vector2 = new Vector();
        Object obj = vector.elementAt(0);
        if(obj instanceof Vector)
        {
            Vector vector3 = (Vector)obj;
            if(i < vector3.size())
                obj = vector3.elementAt(i);
        }
        for(int j = 1; j < vector.size(); j++)
        {
            Object obj1;
            Object obj2 = obj1 = vector.elementAt(j);
            if(obj2 instanceof Vector)
            {
                Vector vector4 = (Vector)obj2;
                obj1 = i >= vector4.size() ? null : vector4.elementAt(i);
            }
            if(direction != 0 ? lt(obj1, obj, sort_if) : gt(obj1, obj, sort_if))
                vector2.addElement(obj2);
            else
                vector1.addElement(obj2);
        }

        qs(vector1, i);
        qs(vector2, i);
        vector1.addElement(vector.elementAt(0));
        addVect(vector, vector1, vector2);
    }

    public int[] sort(int i)
    {
        return sort(i, 0);
    }

    public int[] sort(int i, int j)
    {
        direction = j;
        boolean flag = true;
        for(int k = 0; k < base.size(); k++)
        {
            Object obj = base.elementAt(k);
            if(obj == null || (obj instanceof Vector))
            {
                Vector vector;
                if((vector = (Vector)obj) == null)
                    base.setElementAt(vector = new Vector(), k);
                vector.insertElementAt(new Integer(k), 0);
            } else
            {
                flag = false;
            }
        }

        qs(base, i + 1);
        if(!flag)
            return null;
        int ai[] = new int[base.size()];
        for(int l = 0; l < base.size(); l++)
        {
            Vector vector1 = (Vector)base.elementAt(l);
            ai[((Integer)vector1.elementAt(0)).intValue()] = l;
            vector1.removeElementAt(0);
        }

        return ai;
    }

    private Vector base;
    private int direction;
    private JCSortInterface sort_if;
    public static final int ASCENDING = 0;
    public static final int DESCENDING = 1;
}
