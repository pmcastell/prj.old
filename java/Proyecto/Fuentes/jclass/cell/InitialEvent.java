// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InitialEvent.java

package jclass.cell;

import java.awt.Event;
import java.io.Serializable;

public interface InitialEvent
    extends Serializable
{

    public abstract int getEventType();

    public abstract int getKey();

    public abstract int getModifiers();

    public abstract int getX();

    public abstract int getY();

    public static final int MOUSE = 1;
    public static final int KEY = 2;
    public static final int ALT = 8;
    public static final int CTRL = 2;
    public static final int META = 4;
    public static final int SHIFT = 1;
}
