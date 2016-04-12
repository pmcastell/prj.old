// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCAdjustable.java

package jclass.bwt;


// Referenced classes of package jclass.bwt:
//            JCAdjustmentListener

public interface JCAdjustable
{

    public abstract void addAdjustmentListener(JCAdjustmentListener jcadjustmentlistener);

    public abstract int getBlockIncrement();

    public abstract int getMaximum();

    public abstract int getMinimum();

    public abstract int getOrientation();

    public abstract int getUnitIncrement();

    public abstract int getValue();

    public abstract int getVisibleAmount();

    public abstract void removeAdjustmentListener(JCAdjustmentListener jcadjustmentlistener);

    public abstract void setBlockIncrement(int i);

    public abstract void setMaximum(int i);

    public abstract void setMinimum(int i);

    public abstract void setUnitIncrement(int i);

    public abstract void setValue(int i);

    public abstract void setVisibleAmount(int i);

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
}
