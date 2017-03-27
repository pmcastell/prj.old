// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCSortable.java

package jclass.util;


public interface JCSortable
{

    public abstract long compare(Object obj, Object obj1);

    public static final long LESS_THAN = -1L;
    public static final long EQUAL = 0L;
    public static final long GREATER_THAN = 1L;
}
