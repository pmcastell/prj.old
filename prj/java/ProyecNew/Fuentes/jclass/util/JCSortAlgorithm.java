// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCSortAlgorithm.java

package jclass.util;


// Referenced classes of package jclass.util:
//            JCSortable

public abstract class JCSortAlgorithm
{

    public JCSortAlgorithm()
    {
    }

    protected long do_compare(int i, int j)
    {
        if(indexes == null)
            return compare.compare(list[i], list[j]);
        else
            return compare.compare(list[indexes[i]], list[indexes[j]]);
    }

    abstract void sort(int i, int j);

    public synchronized void sort(Object aobj[], int i, int j, int k, JCSortable jcsortable, int ai[])
    {
        compare = jcsortable;
        list = aobj;
        indexes = ai;
        if(ai != null)
        {
            for(int l = i; l <= j && l < k; l++)
                ai[l] = l;

        }
        sort(Math.max(0, i), Math.min(j, k - 1));
    }

    protected final void swap(int i, int j)
    {
        if(indexes == null)
        {
            Object obj = list[i];
            list[i] = list[j];
            list[j] = obj;
        } else
        {
            int k = indexes[i];
            indexes[i] = indexes[j];
            indexes[j] = k;
        }
    }

    JCSortable compare;
    int indexes[];
    Object list[];
}
