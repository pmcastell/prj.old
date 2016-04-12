// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KeyModifier.java

package jclass.cell;

import java.io.Serializable;

public class KeyModifier
    implements Serializable
{

    public KeyModifier(int i)
    {
        key = i;
        modifier = 16;
        canInitializeEdit = false;
    }

    public KeyModifier(int i, int j)
    {
        key = i;
        modifier = j;
        canInitializeEdit = false;
    }

    public KeyModifier(int i, int j, boolean flag)
    {
        key = i;
        modifier = j;
        canInitializeEdit = flag;
    }

    public boolean match(int i, int j)
    {
        if(key != i)
            return false;
        if(modifier == 16)
            return true;
        return modifier == j;
    }

    public int key;
    public int modifier;
    public boolean canInitializeEdit;
    public static final int ALL = 16;
    public static final int NONE = 0;
}
