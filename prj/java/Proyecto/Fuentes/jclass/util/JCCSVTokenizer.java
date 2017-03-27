// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCCSVTokenizer.java

package jclass.util;


public class JCCSVTokenizer
{

    public JCCSVTokenizer(String s)
    {
        count = false;
        string = s.trim();
        length = string.length();
    }

    public int countTokens()
    {
        int j = index;
        count = true;
        int i;
        for(i = 0; index < length; i++)
            nextToken();

        index = j;
        count = false;
        return i;
    }

    public int getPosition()
    {
        return index;
    }

    public boolean hasMoreTokens()
    {
        return index < length;
    }

    public String nextToken()
    {
        if(string == null || index >= length)
            return null;
        if(!count)
            token = new char[length + 1];
        boolean flag = false;
        int i = index;
        int j = 0;
        for(; i < length; i++)
        {
            if(i + 1 < length && string.charAt(i) == '"' && string.charAt(i + 1) == '"')
            {
                i++;
                if(!count)
                    token[j++] = '"';
                continue;
            }
            if(i == index && string.charAt(i) == '"')
            {
                flag = true;
                continue;
            }
            if(flag && string.charAt(i) == '"')
            {
                flag = false;
                continue;
            }
            if(!flag && string.charAt(i) == ',')
                break;
            if(!count)
                token[j++] = string.charAt(i);
        }

        index = i + 1;
        return count || j <= 0 ? null : new String(token, 0, j);
    }

    private int index;
    private String string;
    private int length;
    private boolean count;
    private char token[];
}
