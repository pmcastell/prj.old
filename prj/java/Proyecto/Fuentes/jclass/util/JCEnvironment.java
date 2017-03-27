// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCEnvironment.java

package jclass.util;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Component;

public class JCEnvironment
{

    public JCEnvironment()
    {
    }

    public static Applet getApplet(Component component)
    {
        if(component == null)
            return null;
        if(component instanceof Applet)
            return (Applet)component;
        java.awt.Container container = component.getParent();
        java.awt.Container container1;
        for(container1 = component.getParent(); container1 != null && !(container1 instanceof Applet);)
        {
            container1 = container;
            container = container != null ? container.getParent() : null;
        }

        return (Applet)container1;
    }

    public static AppletContext getAppletContext(Applet applet)
    {
        if(applet != null)
            try
            {
                return applet.getAppletContext();
            }
            catch(Exception _ex) { }
        return null;
    }

    public static synchronized int getBrowser(Component component)
    {
        if(component.getPeer() == null)
            return -999;
        if(!inBrowser(component))
            return 1;
        String s = System.getProperty("java.vendor");
        if(s.indexOf("Sun") != -1)
            return 2;
        if(s.indexOf("Netscape") != -1)
            return 3;
        if(s.indexOf("Microsoft") != -1)
            return 4;
        if(s.indexOf("IBM") != -1)
            return 5;
        return s.indexOf("Symantec") == -1 ? 1 : 6;
    }

    public static int getJavaVersion()
    {
        if(java_version != -999)
            return java_version;
        String s = getVersionString();
        String s1 = "";
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(Character.isDigit(c))
                s1 = s1 + c;
        }

        try
        {
            java_version = Integer.parseInt(s1);
            if(java_version > 0 && java_version < 100)
                java_version *= 10;
        }
        catch(NumberFormatException _ex)
        {
            java_version = -999;
        }
        return java_version;
    }

    public static int getOS()
    {
        if(os == -999)
        {
            String s = System.getProperty("os.name");
            String s1 = s.toLowerCase();
            if(!s1.startsWith("win") && !s1.startsWith("mac") && !s1.startsWith("apple"))
                os = 1;
            else
                os = 0;
            return os;
        } else
        {
            return os;
        }
    }

    public static String getVendorString()
    {
        if(vendorString == null)
            try
            {
                vendorString = System.getProperty("java.vendor");
            }
            catch(Exception _ex)
            {
                vendorString = null;
            }
        return vendorString;
    }

    public static String getVersionString()
    {
        if(versionString == null)
            try
            {
                versionString = System.getProperty("java.version");
                if(versionString.indexOf("HP-UX") > -1)
                    versionString = "1.1.6";
            }
            catch(Exception _ex)
            {
                versionString = null;
            }
        return versionString;
    }

    public static boolean inBrowser(Component component)
    {
        return getAppletContext(getApplet(component)) != null;
    }

    public static boolean isBrowserAppletViewer(Component component)
    {
        return getBrowser(component) == 2;
    }

    public static boolean isBrowserExplorer(Component component)
    {
        return getBrowser(component) == 4;
    }

    public static boolean isBrowserNetscape(Component component)
    {
        return getBrowser(component) == 3;
    }

    public static boolean isBrowserVisualAge(Component component)
    {
        return getBrowser(component) == 5;
    }

    public static boolean isBrowserVisualCafe(Component component)
    {
        return getBrowser(component) == 6;
    }

    public static boolean isJBuilder()
    {
        String s = getVersionString();
        return s.indexOf("Borland") != -1;
    }

    public static boolean isJavaOS()
    {
        return JAVA_OS;
    }

    public static boolean isNetBeansDesignTime()
    {
        String s = System.getProperty("netbeans.design.time");
        return s != null;
    }

    public static boolean isVisualAge()
    {
        String s = getVendorString();
        return s.indexOf("IBM") != -1;
    }

    public static boolean isVisualCafe()
    {
        String s = getVendorString();
        return s.indexOf("Symantec") != -1;
    }

    public static final int NOVALUE = -999;
    public static int os = -999;
    public static int java_version = -999;
    public static final int OS_OTHER = 0;
    public static final int OS_UNIX = 1;
    public static final int BROWSER_UNKNOWN = -999;
    public static final int BROWSER_OTHER = 1;
    public static final int BROWSER_INTERPRETER = 1;
    public static final int BROWSER_APPLETVIEWER = 2;
    public static final int BROWSER_NETSCAPE = 3;
    public static final int BROWSER_EXPLORER = 4;
    public static final int BROWSER_VISUALAGE = 5;
    public static final int BROWSER_VISUALCAFE = 6;
    public static String versionString = null;
    public static String vendorString = null;
    public static final boolean JAVA_OS = System.getProperty("os.name").indexOf("JavaOS") > -1;

}
