// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCVersion.java

package jclass.bwt;

import java.io.PrintStream;

public final class JCVersion
{

    public JCVersion()
    {
    }

    public static final String getVersionNumber()
    {
        if(versionNumber == null)
            versionNumber = "361J";
        return versionNumber;
    }

    public static final String getVersionString()
    {
        if(versionString == null)
            versionString = "JClass BWT 3.6.1J for JDK 1.2 Swing";
        return versionString;
    }

    public static final void main(String args[])
    {
        System.out.println(getVersionString());
    }

    public static final int major = 3;
    public static final int minor = 6;
    public static final int release = 1;
    public static final String platform = "JDK 1.2 Swing";
    public static final String platform_id = "J";
    private static String versionString = null;
    private static String versionNumber = null;

}
