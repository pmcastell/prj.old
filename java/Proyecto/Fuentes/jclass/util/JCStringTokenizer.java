// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCStringTokenizer.java

package jclass.util;


// Referenced classes of package jclass.util:
//            JCUtilConverter

public class JCStringTokenizer
{

    public JCStringTokenizer(String s)
    {
        count = false;
        strip_esc = true;
        escape_char = '\\';
        if(s != null)
        {
            string = s.trim();
            length = string.length();
        }
    }

    public int countTokens(char c)
    {
        int j = index;
        count = true;
        int i;
        for(i = 0; index < length; i++)
            nextToken(c);

        index = j;
        count = false;
        return i;
    }

    public char getEscapeChar()
    {
        return escape_char;
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
        int i;
        for(i = index; i < length && JCUtilConverter.isSpace(string.charAt(i)); i++);
        index = i;
        if(i >= length)
            return null;
        if(!count)
            token = new char[length + 1];
        i = index;
        int j = 0;
        for(; i < length; i++)
        {
            if(JCUtilConverter.isSpace(string.charAt(i)))
                break;
            if(!count)
                token[j++] = string.charAt(i);
        }

        index = i + 1;
        return count ? null : (new String(token)).trim();
    }

    public String nextToken(char c)
    {
        if(string == null || index >= length)
            return null;
        if(!count)
            token = new char[length + 1];
        int i = index;
        int j;
        for(j = 0; i < length; j++)
        {
            if(escape_char != 0 && i + 1 < length && string.charAt(i) == escape_char)
            {
                if(!strip_esc)
                {
                    if(token != null)
                    {
                        token[j++] = string.charAt(i);
                        token[j] = string.charAt(++i);
                    }
                } else
                {
                    i++;
                    if(!count)
                        if(string.charAt(i) == 'n')
                            token[j] = '\n';
                        else
                            token[j] = string.charAt(i);
                }
            } else
            {
                if(string.charAt(i) == c)
                    break;
                if(!count)
                    token[j] = string.charAt(i);
            }
            i++;
        }

        index = i + 1;
        return count || j <= 0 ? null : new String(token, 0, j);
    }

    public static String[] parse(String s, char c)
    {
        return parse(s, c, '\\');
    }

    public static String[] parse(String s, char c, char c1)
    {
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        jcstringtokenizer.escape_char = c1;
        String as[] = new String[jcstringtokenizer.countTokens(c)];
        for(int i = 0; i < as.length; i++)
        {
            as[i] = jcstringtokenizer.nextToken(c);
            if(as[i] == null)
                as[i] = "";
        }

        return as;
    }

    public void setEscapeChar(char c)
    {
        escape_char = c;
    }

    private int index;
    private String string;
    private int length;
    private boolean count;
    public boolean strip_esc;
    char escape_char;
    private char token[];
}
