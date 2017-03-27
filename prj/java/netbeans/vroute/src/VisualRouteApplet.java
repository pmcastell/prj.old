// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VisualRouteApplet.java

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Hashtable;
import netscape.javascript.JSObject;

public class VisualRouteApplet extends Applet
    implements Runnable
{

    public String appNameVerBuild()
    {
        String s = getParam("build");
        return appNameVer() + (s == null ? "" : " (build " + s + ")");
    }

    public String getDB()
    {
        try
        {
            JSObject jsobject = (JSObject)JSObject.getWindow(this).getMember("document");
            String s = ((JSObject)jsobject.getMember("location")).getMember("href").toString();
            return s;
        }
        catch(Throwable _ex)
        {
            return "http://visualroute.visualware.com/";//getDocumentBase().toString();
        }
    }

    private void doInit()
    {
        System.out.println("Applet V2");
        doLoadParams();
        if(m_vok)
            doPreload();
        m_bLoading = false;
        repaint();
        if(m_vok)
            doAddPanel();
    }

    public int getMaxZoomLevel()
    {
        String s = getParam("zoom");
        if(s != null)
            return Integer.parseInt(s);
        else
            return 2;
    }

    public String getLang()
    {
        String s = getArg("lang");
        if(s != null)
            return s;
        else
            return getParameter("lang");
    }

    public void paint(Graphics g)
    {
        Dimension dimension = size();
        g.setFont(new Font("Helvitica", 0, 16));
        FontMetrics fontmetrics = g.getFontMetrics();
        int i = fontmetrics.getHeight();
        if(m_bLoading)
        {
            g.drawString("Loading " + appName() + " Java Applet...", 5, i * 1);
            g.drawString("[Java " + System.getProperty("java.version") + " - " + System.getProperty("java.vendor") + "]", 5, i * 2);
        } else
        /*if(m_params == null)
        {
            g.setColor(new Color(255, 255, 204));
            g.fillRect(0, 0, dimension.width, dimension.height);
            g.setColor(Color.black);
            g.drawString("ERROR: Unable to contact VisualRoute Server:", 5, i * 1);
            if(!isSimpleGUI())
            {
                g.setColor(Color.red);
                g.drawString("" + getCodeBase(), 5, i * 2);
            }
        } else*/
        if(!m_vok)
        {
            g.setColor(new Color(255, 255, 204));
            g.fillRect(0, 0, dimension.width, dimension.height);
            g.setColor(Color.black);
            g.drawString("ERROR: Applet version mismatch (browser/proxy caching problems)", 5, i * 1);
            g.drawString("Press CTRL+Refresh or SHIFT+Reload in your web browser to reload applet", 5, i * 2);
        }
        if(!m_bDidInit)
        {
            m_bDidInit = true;
            (new Thread(this)).start();
        }
    }

    public void showDocument(String s)
    {
        try
        {
            getAppletContext().showDocument(new URL(s), "_blank");
            return;
        }
        catch(Exception exception)
        {
            System.err.println(exception.toString());
        }
    }

    private static int read(InputStream inputstream, long al[])
        throws IOException
    {
        int i = inputstream.read();
        int j = inputstream.read();
        if(i == -1 || j == -1)
        {
            return -1;
        } else
        {
            int k = (char)(j << 8 | i);
            char c = (char)(int)((long)k ^ al[0] >>> 11);
            al[0] = al[0] * al[1] + al[2] + (long)(c << 8) ^ al[0] >>> 19;
            return c;
        }
    }

    public VisualRouteApplet()
    {
        m_bDidInit = false;
        m_bLoading = true;
        m_params = null;
    }

    public String appName()
    {
        return "VisualRoute";
    }

    public String appNameVer()
    {
        String s = getParam("appnamever");
        if(s != null)
            return s;
        else
            return appName() + " (tm)";
    }

    public String getArg(String s)
    {
        String s1 = getDB();
		String s2;
        int i = s1.indexOf('?');
        if(i > 0)
        {
            s2 = "&" + s1.substring(i + 1);
            i = s2.indexOf('#');
            s2 = i <= 0 ? s2 : s2.substring(0, i);
            String s3 = "&" + s + "=";
            i = s2.indexOf(s3);
            if(i >= 0)
            {
                String s4 = s2.substring(i + s3.length());
                i = s4.indexOf('&');
                if(i > 0)
                    return s4.substring(0, i);
                else
                    return s4;
            } else
            {
                return null;
            }
        } else
        {
            s2 = getParameter(s);
            return s2;
        }
    }

    public URL getCodeBase()
    {
        if(g_bTesting)
            try
            {
                return new URL("http", "localhost", 80, "");
            }
            catch(Exception _ex)
            {
                return null;
            }
        else {
            //return super.getCodeBase();
            try {
               return new URL("http","visualroute.visualware.com",80,"");
            } catch (Exception ex) {}
        }
        return null;
           
    }

    public boolean isLogin()
    {
        return "true".equals(getParam("login"));
    }

    public static String readLine(InputStream inputstream, long al[])
        throws IOException
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i;
        while((i = read(inputstream, al)) != -1 && i != 10) 
            stringbuffer.append((char)i);
        if(i == -1 && stringbuffer.length() == 0)
            return null;
        else
            return stringbuffer.toString();
    }

    private void doAddPanel()
    {
        setLayout(new BorderLayout());
        VisualRoutePanel visualroutepanel = new VisualRoutePanel(this, null);
        add("Center", visualroutepanel);
        boolean flag = true;//!"no".equals(getParameter("allowgo"));
        visualroutepanel.go("no");//go(flag ? getArg("go") : null);
        validate();
        //showStatus(getAppletInfo());
    }

    public String getParam(String s)
    {
        return getParam(s, null);
    }

    public String getParam(String s, String s1)
    {
        Object obj = m_params == null ? null : m_params.get(s);
        if(obj != null)
            return obj.toString();
        else
            return s1;
    }
    boolean isStandalone;
    public static void main(String[] args) {
        VisualRouteApplet applet = new VisualRouteApplet();
      applet.isStandalone = true;
      javax.swing.JFrame frame = new javax.swing.JFrame();
      frame.setTitle("Applet Frame");
      frame.getContentPane().add(applet, BorderLayout.CENTER);
      applet.init();
      applet.start();
      frame.setSize(810,695);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosing(java.awt.event.WindowEvent e) {
              System.exit(0);
          }
      });
      frame.setVisible(true);
    }

    public String getAppletInfo()
    {
        return appNameVerBuild() + " Applet Copyright (c) 1996-" + COPYRIGHTDATE + " Visualware, Inc";
    }

    public void run()
    {
        try
        {
            doInit();
            return;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void doPreload()
    {
        for(int i = 0; i < m_aPreload.length; i++)
            try
            {
                Class.forName(m_aPreload[i]);
            }
            catch(Throwable _ex) { }

    }

    public boolean isSimpleGUI()
    {
        return "true".equals(getParam("simple"));
    }

    public boolean autoShowDetails()
    {
        return "true".equals(getParam("details"));
    }

    private void doLoadParams()
    {
        try
        {
            Hashtable hashtable = new Hashtable();
            long al[] = {
                0x55555555L, 0x55555561L, 0x66666689L
            };
            String s = null; //+++ getLang();
            String s1 = "?t=" + System.currentTimeMillis() / 1000L;
            String s2 = "&v=" + m_v.substring(4, 7);
            String s3 = s == null ? "" : "&lang=" + s;
            URL url = new URL(new URL("http", "visualroute.visualware.com", 80, ""), "/params" + s1 + s2 + s3);
            InputStream inputstream = url.openStream();
            String s4;
            while((s4 = readLine(inputstream, al)) != null) 
            {
                int i = s4.indexOf('=');
                if(i > 0)
                {
                    String s5 = s4.substring(0, i);
                    String s6 = s4.substring(i + 1);
                    hashtable.put(s5, s6);
                }
            }
            inputstream.close();
            m_params = hashtable;
        }
        catch(Exception _ex) {
            _ex.printStackTrace();
        }
        m_vok = "true".equals(getParam("vok"));
    }

    private String m_aPreload[] = {
        "VisualRoutePanel", "VisualRouteFrame", "SnapFrame", "AppletWhois"
    };
    private static String m_v = "XXXX008YYYY";
    private boolean m_bDidInit;
    private boolean m_bLoading;
    private boolean m_vok;
    private Hashtable m_params;
    public static String COPYRIGHTDATE = "2006";
    public static boolean ISREGISTER = true;
    private static boolean g_bTesting = false;

}
