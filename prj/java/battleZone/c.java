// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JAX

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.net.URL;

public final class c
{

    private static final void bx()
    {
        c c1 = cr;
        if(c1 != null)
        {
            c c2 = c1.cn;
            if(cv.checkID(c1.bs, true))
            {
                if(!cv.isErrorAny())
                {
                    if(cq != null)
                    {
                        c1.cn = cq.cn;
                        c _tmp = c1;
                        c _tmp1 = cq;
                        cq.cn = c1;
                        c _tmp2 = c1.cn;
                        c _tmp3 = c1;
                    } else
                    {
                        c1.cn = c1;
                        c _tmp4 = c1;
                        c _tmp5 = c1;
                    }
                    cq = c1;
                    cp++;
                }
                cr = c2;
                if(cr != null)
                    cv.checkID(cr.bs, true);
                return;
            }
        }
        if(cq != null)
            cq = cq.cn;
    }

    public static final void bw(a a)
    {
        cu = a;
        cv = new MediaTracker(cu);
        ct = cu.getAppletContext();
        if(cr != null)
            cv.checkID(cr.bs, true);
    }

    public static final boolean bv()
    {
        if(cq != null)
        {
            Graphics g = cu.getGraphics();
            Rectangle rectangle = cu.getBounds();
            Image image = cq.ck;
            if(g != null && image != null)
            {
                if(d == null || e != rectangle.width || f != rectangle.height)
                {
                    d = cu.createImage(rectangle.width, rectangle.height);
                    c = d.getGraphics();
                    e = rectangle.width;
                    f = rectangle.height;
                }
                if(c != null)
                {
                    int i = e - image.getWidth(cu) >> 1;
                    int j = f - image.getHeight(cu) >> 1;
                    c.clearRect(0, 0, e, f);
                    c.drawImage(cq.ck, i, j, null);
                    g.drawImage(d, 0, 0, cu);
                    return true;
                }
            }
        }
        return false;
    }

    public static final void bu(int i)
    {
        if(co == i)
        {
            bx();
            if(bv())
            {
                Thread thread = Thread.currentThread();
                try
                {
                    Thread.sleep(cq.cm);
                }
                catch(Exception exception) { }
                long l = System.currentTimeMillis();
                long l1;
                do
                    l1 = System.currentTimeMillis() - l;
                while(l1 < (long)cq.cm);
                cq.cj++;
            }
        }
    }

    public static final void bt()
    {
        if(cq != null && cq.cl != null)
            ct.showDocument(cq.cl, "Ad");
    }

    public static MediaTracker cv;
    public static Applet cu;
    public static AppletContext ct;
    public static Image d;
    public static Graphics c;
    public static c cr;
    public static c cq;
    public static int e;
    public static int f;
    public static int cp = 0;
    public static int co;
    public c cn;
    public int cm;
    public URL cl;
    public int bs;
    public Image ck;
    public int cj;

    static 
    {
        boolean _tmp = false;
    }
}
