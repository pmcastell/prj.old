// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   C:\Vjtrial\projects\JavaClock\JavaClock.java

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.util.Date;

public class JavaClock extends Applet
    implements Runnable
{

    public JavaClock()
    {
        MAXX = 72;
        MAXY = 20;
    }

    public void start()
    {
        if(mainThread != null);
        mainThread = new Thread(this);
        mainThread.start();
    }

    public void stop()
    {
        if(mainThread != null)
        {
            mainThread.stop();
            mainThread = null;
        }
    }

    public boolean mouseDown(Event evt, int x, int y)
    {
        getAppletContext().showStatus("Copyright (C) 1996 MeaSoft Enterprises.");
        return true;
    }

    void pause(int time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch(InterruptedException _ex) { }
    }

    public String getAppletInfo()
    {
        return "Name: JavaClock\r\n" + "Author: Mounir El Abridi\r\n" + "Version: 1\r\n" + "Copyright: 1996 MeaSoft Enterprises Inc.\r\n" + "WWW: www.measoft.com\r\n";
    }

    public boolean mouseUp(Event evt, int x, int y)
    {
        getAppletContext().showStatus("More Applets at http://www.MeaSoft.com/");
        return true;
    }

    public void run()
    {
        do
        {
            bg.setColor(Color.black);
            bg.fillRect(0, 0, MAXX, MAXY);
            Date now = new Date();
            int h1 = now.getHours();
            int h = h1 % 10;
            h1 = (h1 - h) / 10;
            int m1 = now.getMinutes();
            int m = m1 % 10;
            m1 = (m1 - m) / 10;
            int s1 = now.getSeconds();
            int s = s1 % 10;
            s1 = (s1 - s) / 10;
            button(0, 0, MAXX, MAXY, Color.black, Color.white, Color.gray, Color.black);
            bg.drawImage(DIGITS_IMAGE[h1], 1, 3, this);
            bg.drawImage(DIGITS_IMAGE[h], 11, 3, this);
            if(ShowColon)
                bg.drawImage(Colon, 21, 3, this);
            bg.drawImage(DIGITS_IMAGE[m1], 26, 3, this);
            bg.drawImage(DIGITS_IMAGE[m], 36, 3, this);
            if(ShowColon)
                bg.drawImage(Colon, 46, 3, this);
            ShowColon = !ShowColon;
            bg.drawImage(DIGITS_IMAGE[s1], 51, 3, this);
            bg.drawImage(DIGITS_IMAGE[s], 61, 3, this);
            repaint();
            pause(1000);
        } while(true);
    }

    public void button(int l, int t, int w, int h, Color inside, Color top, Color bottom, 
            Color bottom1)
    {
        bg.setColor(inside);
        bg.fillRect(l, t, w, h);
        bg.setColor(top);
        bg.fillRect(l, t, w, 1);
        bg.fillRect(l, t, 1, h);
        bg.setColor(bottom);
        bg.fillRect(l + 1, (t + h) - 2, w - 2, 1);
        bg.fillRect((l + w) - 2, t + 1, 1, h - 2);
        bg.setColor(bottom1);
        bg.fillRect(l, (t + h) - 1, w - 1, 1);
        bg.fillRect((l + w) - 1, t, 1, h);
    }

    public void destroy()
    {
        bg.dispose();
    }

    public void init()
    {
        resize(MAXX, MAXY);
        ShowColon = true;
        buffered_image = createImage(MAXX, MAXY);
        bg = buffered_image.getGraphics();
        DIGITS_IMAGE = new Image[10];
        tracker = new MediaTracker(this);
        Colon = getImage(getCodeBase(), "pictures/" + "colon.gif");
        tracker.addImage(Colon, 0);
        int i = 0;
        do
        {
            DIGITS_IMAGE[i] = getImage(getCodeBase(), "pictures/" + i + ".gif");
            tracker.addImage(DIGITS_IMAGE[i], 0);
        } while(++i < 10);
        try
        {
            tracker.waitForAll();
        }
        catch(InterruptedException _ex) { }
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public boolean handleEvent(Event evt)
    {
        if(evt.id == 201)
        {
            bg.dispose();
            System.exit(0);
        }
        return super.handleEvent(evt);
    }

    public void paint(Graphics g)
    {
        g.drawImage(buffered_image, 0, 0, null);
    }

    Color c;
    Image buffered_image;
    int MAXX;
    int MAXY;
    Image DIGITS_IMAGE[];
    Image Colon;
    MediaTracker tracker;
    private Thread mainThread;
    private Graphics bg;
    private boolean ShowColon;
}
