// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Datos.java

package nat.irc;

import java.util.StringTokenizer;
import java.util.Vector;

public class Datos
{

    public Datos()
    {
        targets = new Vector();
        parameters = "";
    }

    public Datos(String as[], String s)
    {
        this();
        for(int i = 0; i < as.length; i++)
            addTarget(as[i]);

        setParameters(s);
    }

    public Datos(String s)
    {
        this();
        int i = s.indexOf(':');
        int j = i + 1;
        String s1 = s.substring(0, i);
        String s2 = s.substring(j, s.length());
        s2.trim();
        setParameters(s2);
        for(StringTokenizer stringtokenizer = new StringTokenizer(s1, " "); stringtokenizer.hasMoreTokens(); addTarget(stringtokenizer.nextToken()));
    }

    public void addTarget(String s)
    {
        targets.addElement(s);
    }

    public boolean containsTarget(String s)
    {
        return targets.contains(s);
    }

    public int getNumberOfTargets()
    {
        return targets.size();
    }

    public String getParameters()
    {
        return parameters;
    }

    public Vector getTargets()
    {
        return (Vector)targets.clone();
    }

    public boolean hasNoParameters()
    {
        return getParameters() == null || getParameters() == "";
    }

    public boolean hasNoTargets()
    {
        return targets.isEmpty();
    }

    public void removeTarget(String s)
    {
        targets.removeElement(s);
    }

    public void setParameters(String s)
    {
        parameters = s;
    }

    public String toString()
    {
        String s = "";
        String s1 = "";
        if(!hasNoTargets())
        {
            StringBuffer stringbuffer = new StringBuffer();
            for(int i = 0; i < targets.size(); i++)
                stringbuffer.append(String.valueOf((String)targets.elementAt(i)) + String.valueOf(' '));

            s = stringbuffer.toString();
        }
        if(!hasNoParameters())
            s1 = String.valueOf(':') + String.valueOf(getParameters());
        return String.valueOf(s) + String.valueOf(s1);
    }

    private Vector targets;
    private String parameters;
}
