// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCFile.java

package jclass.util;

import java.applet.Applet;
import java.awt.Component;
import java.io.*;
import java.net.URL;
import java.util.Vector;

// Referenced classes of package jclass.util:
//            ConvertUtil, JCUtilConverter, JCVector

public class JCFile
{

    public JCFile()
    {
    }

    public static URL createURL(Applet applet, String s)
    {
        if(s.indexOf(":") == -1)
            try
            {
                return new URL(applet.getDocumentBase(), s);
            }
            catch(Exception _ex)
            {
                s = "file:" + System.getProperty("user.dir") + "/" + s;
            }
        try
        {
            return new URL(s);
        }
        catch(Exception _ex)
        {
            return null;
        }
    }

    public static String read(Applet applet, String s)
    {
        String s1 = "";
        try
        {
            Object obj = null;
            try
            {
                obj = new FileInputStream(s);
            }
            catch(Exception _ex)
            {
                obj = null;
            }
            if(obj == null)
                obj = createURL(applet, s).openStream();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new BufferedInputStream(((java.io.InputStream) (obj)))));
            for(String s2 = bufferedreader.readLine(); s2 != null; s2 = bufferedreader.readLine())
                s1 = s1 + s2;

            return s1;
        }
        catch(Exception _ex)
        {
            System.out.println("Error opening file '" + s + "'");
        }
        return null;
    }

    public static JCVector read(Component component, String s, char c, boolean flag)
    {
        JCVector jcvector = new JCVector();
        Vector vector = readLines((component instanceof Applet) ? (Applet)component : null, s);
        if(vector == null)
            return jcvector;
        int i = 0;
        for(int j = 0; j < vector.size(); j++)
            if(vector.elementAt(j) != null)
            {
                String s1 = (String)vector.elementAt(j);
                JCVector jcvector1 = JCUtilConverter.toVector(component, s1, c, flag);
                jcvector.setElementAt(i++, jcvector1);
            }

        return jcvector;
    }

    public static JCVector readCSV(Component component, String s)
    {
        JCVector jcvector = new JCVector();
        Vector vector = readLines((component instanceof Applet) ? (Applet)component : null, s);
        int i = 0;
        for(int j = 0; j < vector.size(); j++)
            if(vector.elementAt(j) != null)
            {
                String s1 = (String)vector.elementAt(j);
                JCVector jcvector1 = ConvertUtil.toVectorFromCSV(component, s1);
                jcvector.setElementAt(i++, jcvector1);
            }

        return jcvector;
    }

    public static Vector readLines(Applet applet, String s)
    {
        return readLines(applet, s, 0, 0x7fffffff);
    }

    public static Vector readLines(Applet applet, String s, int i, int j)
    {
        try
        {
            java.io.InputStream inputstream = createURL(applet, s).openStream();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new BufferedInputStream(inputstream)));
            Vector vector = new Vector();
            String s1;
            for(int k = 0; (s1 = bufferedreader.readLine()) != null; k++)
            {
                if(k > j)
                    break;
                if(k >= i)
                    vector.addElement(s1);
            }

            return vector;
        }
        catch(Throwable _ex)
        {
            System.out.println("Error opening file '" + s + "'");
        }
        return null;
    }
}
