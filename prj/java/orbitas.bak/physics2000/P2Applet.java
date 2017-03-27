// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   P2Applet.java

package physics2000;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.PrintStream;
import java.net.URL;
import java.util.StringTokenizer;

public abstract class P2Applet extends Applet
    implements Runnable
{

    public void init()
    {
        System.err.println("This is a Physics 2000 Applet.\n(c) University of Colorado 1997");
        makeOffscreen();
        waitSignLocation = this;
        String s = getParameter("waitsign");
        if(s != null && s.equals("off"))
            displayWaitSignP = false;
    }

    public abstract void loadImages();

    public abstract void step();

    public Image getImage(URL url, String s)
    {
        return getImage(url, s, true);
    }

    public Image getImageOld(URL url, String s)
    {
        return super.getImage(url, s);
    }

    public Image getImage(URL url, String s, boolean flag)
    {
        Image image = super.getImage(url, s);
        MediaTracker mediatracker = new MediaTracker(this);
        mediatracker.addImage(image, 0);
        try
        {
            mediatracker.waitForAll();
        }
        catch(InterruptedException _ex)
        {
            System.err.println("Interrupted while loading images.");
        }
        if(flag)
        {
            int i = image.getHeight(this);
            int j = image.getWidth(this);
            int ai[] = new int[i * j];
            PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, j, i, ai, 0, j);
            try
            {
                pixelgrabber.grabPixels();
            }
            catch(InterruptedException _ex)
            {
                System.err.println("Interrupted");
            }
            return createImage(new MemoryImageSource(j, i, ai, 0, j));
        } else
        {
            return image;
        }
    }

    public static boolean netscapeFour()
    {
        String s = System.getProperty("os.name").substring(0, 3);
        String s1 = System.getProperty("java.vendor").substring(0, 8);
        String s2 = System.getProperty("java.version");
        double d = Double.valueOf(s2.substring(0, 3)).doubleValue();
        return s.equalsIgnoreCase("Mac") && s1.equalsIgnoreCase("Netscape") && d >= 1.1000000000000001D;
    }

    private void loadTheImages()
    {
        showStatus("Loading images...");
        loadImages();
        showStatus("Images loaded.");
    }

    protected String getParameter(String s, String s1)
    {
        String s2 = getParameter(s);
        if(s2 == null)
            return s1;
        else
            return s2;
    }

    private void makeOffscreen()
    {
        Dimension dimension = size();
        offscreen = createImage(dimension.width, dimension.height);
    }

    public void paint(Graphics g)
    {
        if(!loaded)
        {
            if(displayWaitSignP)
                displayWaitSign();
            if(!loading)
            {
                loading = true;
                loadTheImages();
                loaded = true;
                repaint();
            }
        } else
        if(offscreen != null)
            g.drawImage(offscreen, 0, 0, this);
    }

    public Point pointFromParam(String s)
    {
        String s1 = getParameter(s, "0 0");
        StringTokenizer stringtokenizer = new StringTokenizer(s1);
        return new Point(Integer.parseInt(stringtokenizer.nextToken()), Integer.parseInt(stringtokenizer.nextToken()));
    }

    public void start()
    {
        if(threaded && theThread == null)
        {
            theThread = new Thread(this);
            theThread.start();
        }
    }

    public void run()
    {
        do
        {
            if(loaded)
                step();
            try
            {
                Thread.sleep(SLEEP_TIME);
            }
            catch(Exception exception)
            {
                System.err.println(exception);
            }
        } while(true);
    }

    public void stop()
    {
        if(theThread != null && theThread.isAlive())
        {
            theThread.stop();
            theThread = null;
        }
    }

    private void displayWaitSign()
    {
        String s = "Please wait a moment...";
        byte byte0 = 4;
        byte byte1 = 12;
        int i = 0;
        int j = 1;
        byte byte2 = 2;
        Dimension dimension = waitSignLocation.size();
        int k = dimension.height;
        Color acolor[] = new Color[3];
        acolor[i] = new Color(74, 49, 206);
        acolor[j] = new Color(255, 222, 0);
        acolor[byte2] = new Color(235, 95, 76);
        Polygon apolygon[] = new Polygon[4];
        int ai[][] = {
            {
                0, byte1, byte1, byte0, byte0, 0
            }, {
                0, 0, byte0, byte0, k - byte0, k
            }, {
                byte1, dimension.width, dimension.width - byte0, byte1
            }, {
                0, 0, byte0, byte0
            }, {
                dimension.width, dimension.width, byte1, byte1, dimension.width - byte0, dimension.width - byte0
            }, {
                0, k, k, k - byte0, k - byte0, byte0
            }, {
                byte0, byte1, byte1, 0
            }, {
                k - byte0, k - byte0, k, k
            }
        };
        apolygon[0] = new Polygon(ai[0], ai[1], 6);
        apolygon[1] = new Polygon(ai[2], ai[3], 4);
        apolygon[2] = new Polygon(ai[4], ai[5], 6);
        apolygon[3] = new Polygon(ai[6], ai[7], 4);
        Graphics g = waitSignLocation.getGraphics();
        g.setColor(acolor[i]);
        g.fillRect(0, 0, dimension.width, k);
        g.setColor(acolor[byte2]);
        g.fillRect(0, 0, byte1, k);
        g.setColor(acolor[byte2].brighter());
        g.fillPolygon(apolygon[0]);
        g.setColor(acolor[byte2].darker());
        g.fillPolygon(apolygon[3]);
        g.setColor(acolor[i].brighter());
        g.fillPolygon(apolygon[1]);
        g.setColor(acolor[i].darker());
        g.fillPolygon(apolygon[2]);
        g.setColor(acolor[j]);
        g.setFont(fontForString(s, dimension.width - 2 * byte0, 24));
        g.drawString(s, byte1 + 5, k - byte0 - 5);
        g.setFont(fontForString("Physics 2000", dimension.width - 2 * byte0, 36));
        FontMetrics fontmetrics = getFontMetrics(g.getFont());
        int l = fontmetrics.getHeight();
        g.setColor(Color.white);
        g.drawString("Physics 2000", byte1 + 5, byte1 + 5 + l);
        g.setFont(new Font("Times", 0, 10));
        g.drawString("(c) 1997 Physics 2000.  All rights reserved.", byte1 + 5, byte1 + 5 + l + 12);
    }

    private Font fontForString(String s, int i, int j)
    {
        int k = 6;
        Font font1 = new Font("helvetica", 1, k);
        FontMetrics fontmetrics;
        Font font;
        do
        {
            font = font1;
            k += 2;
            font1 = new Font("helvetica", 1, k);
            fontmetrics = getFontMetrics(font1);
        } while(fontmetrics.stringWidth(s) <= (i * 2) / 3 && k <= j);
        return font;
    }

    public void externalCommand(String s)
    {
        System.err.println("External command: " + s);
    }

    public P2Applet()
    {
        displayWaitSignP = true;
        threaded = true;
        SLEEP_TIME = 25;
    }

    protected Image offscreen;
    private boolean loaded;
    private boolean loading;
    protected boolean displayWaitSignP;
    protected boolean threaded;
    protected Component waitSignLocation;
    protected Thread theThread;
    protected int SLEEP_TIME;
}
