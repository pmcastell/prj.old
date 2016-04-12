// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocaleInfo.java

package jclass.bwt.resources;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class LocaleInfo extends ListResourceBundle
    implements Serializable
{

    public LocaleInfo()
    {
    }

    public Object[][] getContents()
    {
        return contents;
    }

    public static final String CUT = "Cut";
    public static final String COPY = "Copy";
    public static final String PASTE = "Paste";
    public static final String DELETE = "Delete";
    public static final String SELECTALL = "SelectAll";
    public static final String CUTKEY = "CutKey";
    public static final String COPYKEY = "CopyKey";
    public static final String PASTEKEY = "PasteKey";
    public static final String HOMEKEY = "HomeKey";
    public static final String ENDKEY = "EndKey";
    public static final String OVRSTKKEY = "OverStrikeKey";
    public static final String DELETEKEY = "DeleteKey";
    public static final String BSKEY = "BackSpaceKey";
    static final Object contents[][] = {
        {
            "Cut", "Cut"
        }, {
            "Copy", "Copy"
        }, {
            "Paste", "Paste"
        }, {
            "Delete", "Delete"
        }, {
            "SelectAll", "SelectAll"
        }, {
            "CutKey", new Integer(24)
        }, {
            "CopyKey", new Integer(3)
        }, {
            "PasteKey", new Integer(22)
        }, {
            "HomeKey", new Integer(-1)
        }, {
            "EndKey", new Integer(-1)
        }, {
            "OverStrikeKey", new Integer(-1)
        }, {
            "DeleteKey", new Integer(-1)
        }, {
            "BackSpaceKey", new Integer(-1)
        }
    };

}
