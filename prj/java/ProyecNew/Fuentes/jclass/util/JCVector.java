// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3)
// Source File Name:   JCVector.java

package jclass.util;

import java.util.Vector;

// Referenced classes of package jclass.util:
//            JCHeapSort, JCSortAlgorithm, JCSortable

public class JCVector extends Vector
{

    public JCVector()
    {
        super(1, 10);
        sort_algorithm = null;
    }

    public JCVector(int i)
    {
        super(i, 10);
        sort_algorithm = null;
    }

    public JCVector(int i, int j)
    {
        super(i, j);
        sort_algorithm = null;
    }

    public JCVector(Vector vector)
    {
        sort_algorithm = null;
        if(vector == null)
            return;
        for(int i = 0; i < vector.size(); i++)
        {
            Object obj = vector.elementAt(i);
            if(obj != null && (obj instanceof Vector) && !obj.getClass().getName().equals("jclass.util.JCString"))
            {
                JCVector jcvector = new JCVector((Vector)obj);
                addElement(jcvector);
            } else
            {
                addElement(vector.elementAt(i));
            }
        }

    }

    public JCVector(Object aobj[])
    {
        sort_algorithm = null;
        copyFrom(aobj);
    }

    public final void addUnique(Object obj)
    {
        if(!contains(obj))
            addElement(obj);
    }

    public final Object at(int i)
    {
        return elementAt(i);
    }

    public final synchronized void copyFrom(Object aobj[])
    {
        if(aobj == null)
        {
            removeAllElements();
        } else
        {
            ensureCapacity(aobj.length);
            super.elementCount = aobj.length;
            System.arraycopy(((Object) (aobj)), 0, ((Object) (super.elementData)), 0, aobj.length);
        }
    }

    private boolean equals(JCVector jcvector)
    {
        if(jcvector == null || super.elementCount != jcvector.size())
            return false;
        for(int i = 0; i < super.elementCount; i++)
            if(super.elementData[i] == null)
            {
                if(((Vector) (jcvector)).elementAt(i) != null)
                    return false;
            } else
            if(!super.elementData[i].equals(((Vector) (jcvector)).elementAt(i)))
                return false;

        return true;
    }

    public final synchronized Object[] getArrayCopy()
    {
        Object aobj[] = new Object[super.elementCount];
        System.arraycopy(((Object) (super.elementData)), 0, ((Object) (aobj)), 0, super.elementCount);
        return aobj;
    }

    public final synchronized Object getFirst()
    {
        return super.elementCount <= 0 ? null : super.elementData[0];
    }

    public final synchronized Object getLast()
    {
        return super.elementCount <= 0 ? null : super.elementData[super.elementCount - 1];
    }

    public JCSortAlgorithm getSortAlgorithm()
    {
        return sort_algorithm;
    }

    public final synchronized void removeElementsAt(int i, int j)
    {
        for(int k = i; k < i + j && i < super.elementCount; k++)
            removeElementAt(i);

    }

    public void setCapacityIncrement(int i)
    {
        super.capacityIncrement = i;
    }

    public synchronized void setElementAt(int i, Object obj)
    {
        if(obj != null)
        {
            if(super.elementCount < i + 1)
                setSize(i + 1);
            super.elementData[i] = obj;
        } else
        if(i < super.elementCount)
            super.elementData[i] = null;
    }

    public final synchronized void setMinSize(int i)
    {
        if(i > super.elementCount)
        {
            ensureCapacity(i);
            super.elementCount = i;
        }
    }

    public void setSortAlgorithm(JCSortAlgorithm jcsortalgorithm)
    {
        sort_algorithm = jcsortalgorithm;
    }

    public synchronized void sort(int i, int j, JCSortable jcsortable)
    {
        sort(i, j, jcsortable, null);
    }

    public synchronized void sort(int i, int j, JCSortable jcsortable, int ai[])
    {
        if(sort_algorithm == null)
            sort_algorithm = new JCHeapSort();
        sort_algorithm.sort(super.elementData, i, j, super.elementCount, jcsortable, ai);
    }

    public synchronized void sort(JCSortable jcsortable)
    {
        sort(0, size() - 1, jcsortable);
    }

    public final synchronized void strip()
    {
        int i;
        for(i = super.elementCount - 1; i >= 0; i--)
            if(super.elementData[i] != null)
                break;

        super.elementCount = i + 1;
        trimToSize();
    }

    JCSortAlgorithm sort_algorithm;
}
