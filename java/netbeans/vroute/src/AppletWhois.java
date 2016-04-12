// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppletWhois.java

import java.awt.Component;
import java.awt.Frame;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

public class AppletWhois
    implements Runnable
{

    private AppletWhois(VisualRouteApplet visualrouteapplet, String s, int i)
    {
        bFinished = false;
        m_address = s;
        m_type = i;
        m_applet = visualrouteapplet;
        m_thread = new Thread(this, "whois: " + s);
        m_thread.start();
    }

    public static synchronized void whois(VisualRouteApplet visualrouteapplet, String s, int i)
    {
        new AppletWhois(visualrouteapplet, s, i);
    }

    private boolean processRequest()
    {
        InputStream inputstream = null;
        try
        {
            boolean flag2;
            try
            {
                long al[] = {
                    0x55555555L, 0x5a5a5a71L, 0x1234567dL
                };
                String s = m_applet.getLang();
                URL url = new URL(m_applet.getCodeBase(), (m_type != NETWORK ? "d" : "n") + "whois?go=" + m_address + (s == null ? "" : "&lang=" + s));
                inputstream = url.openConnection().getInputStream();
                String s1 = null;
                m_response = new Vector();
                while((s1 = VisualRouteApplet.readLine(inputstream, al)) != null) 
                    m_response.addElement(s1);
                boolean flag = m_response.elementAt(0) != null && ((String)m_response.elementAt(0)).startsWith("title");
                if(flag)
                {
                    m_title = ((String)m_response.elementAt(0)).substring(5);
                    m_response.removeElementAt(0);
                } else
                {
                    m_title = (m_type != 0 ? "Domain whois" : "Network whois") + " " + m_address;
                }
                boolean flag1 = true;
                return flag1;
            }
            catch(Exception _ex)
            {
                flag2 = false;
            }
            return flag2;
        }
        finally
        {
            try
            {
                inputstream.close();
            }
            catch(Exception _ex) { }
        }
    }

    public void run()
    {
        SnapFrame snapframe = new SnapFrame("Whois : " + m_address, "Contacting server...", 12);
        snapframe.resize(400, 260);
        if(processRequest())
        {
            snapframe.setTitle(m_title);
            snapframe.setText(m_response);
        }
    }

    public static int NETWORK = 0;
    public static int DOMAIN = 1;
    private Thread m_thread;
    private String m_address;
    private int m_type;
    private VisualRouteApplet m_applet;
    private boolean bFinished;
    private Vector m_response;
    private String m_title;

}
