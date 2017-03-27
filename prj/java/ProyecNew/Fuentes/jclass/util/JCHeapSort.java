// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCHeapSort.java

package jclass.util;

import java.io.PrintStream;

// Referenced classes of package jclass.util:
//            JCSortAlgorithm

public class JCHeapSort extends JCSortAlgorithm
{

    public JCHeapSort()
    {
        heap_size = 0;
        start = 0;
        end = 0;
    }

    private final void build_heap()
    {
        heap_size = (end - start) + 1;
        for(int i = parent(end); i >= 0; i--)
            heapify(i);

    }

    void checkHeap()
    {
        for(int i = start; i <= end; i++)
        {
            int j = i;
            if(super.indexes != null)
                j = super.indexes[i];
            if(do_compare(j, parent(j)) > 0L)
            {
                System.out.println("It is NOT a heap!!!");
                return;
            }
        }

        System.out.println("It is a heap!");
    }

    private final void heapify(int i)
    {
        int j = left(i);
        int k = right(i);
        if(j == -1 || k == -1)
            return;
        int l = i;
        if(j < start + heap_size && do_compare(j, i) > 0L)
            l = j;
        if(k < start + heap_size && do_compare(k, l) > 0L)
            l = k;
        if(l != i)
        {
            swap(l, i);
            heapify(l);
        }
    }

    private final int left(int i)
    {
        if(i < start)
            return -1;
        else
            return start + (i - start << 1);
    }

    private final int parent(int i)
    {
        if(i < start)
            return start;
        else
            return start + (i - start >> 1);
    }

    void printList()
    {
        System.out.print("List:     ");
        for(int i = start; i <= end; i++)
        {
            int j = i;
            if(super.indexes != null)
                j = super.indexes[i];
            System.out.print(super.list[j] + ", ");
        }

        System.out.println();
    }

    private final int right(int i)
    {
        if(i < start)
            return -1;
        else
            return start + (i - start << 1) + 1;
    }

    void sort(int i, int j)
    {
        start = i;
        end = j;
        build_heap();
        for(int k = j; k > i; k--)
        {
            swap(i, k);
            heap_size--;
            heapify(i);
        }

    }

    private static final int INVALID = -1;
    int heap_size;
    int start;
    int end;
}
