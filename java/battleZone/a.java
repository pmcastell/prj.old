// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JAX

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

public abstract class a extends Applet
    implements Runnable, KeyListener, MouseListener, MouseMotionListener, WindowListener, FocusListener
{

    public abstract void aq(int i1, int j1, int k1);

    public abstract int ap(int i1, int j1);

    public abstract int ao(int i1, int j1, int k1);

    public abstract void an();

    public abstract void am(int i1);

    public abstract void al(int i1);

    public abstract void ak(int i1);

    public abstract void aj(int i1);

    public abstract void ai(int i1, int j1);

    public abstract void ah();

    public final void ag()
    {
        if(av == 0)
        {
            an();
            h();
            if(p)
            {
                q = true;
                c.bu(o);
                q = false;
                p = false;
                az = System.currentTimeMillis();
            }
        } else
        {
            if(w)
            {
                a();
                w = false;
            }
            az = System.currentTimeMillis();
        }
    }

    public final void af(String s1)
    {
        ar = s1;
        if(aq != null)
            as = aq.stringWidth(s1);
    }

    public final void ae(Graphics g1)
    {
        if(g1 != null && ar != null)
        {
            Rectangle rectangle = getBounds();
            int i1 = rectangle.width - as >> 1;
            int j1 = (int)((float)rectangle.height * at);
            g1.setColor(au);
            g1.drawString(ar, i1, j1);
        }
    }

    private final void ad()
    {
        an = getCodeBase();
        if(ai != null && ah != null && aj != null)
        {
            am = an.getHost();
            ak = an.getPort();
            al = "Host: " + am;
            if(ak == -1)
                ak = 80;
            else
                al += ":" + ak;
            ap = true;
            af = "POST " + ai + " HTTP/1.1" + "\r\n" + "Content-type: application/x-www-form-urlencoded" + "\r\n" + "Content-length: ";
            ae = "\r\n" + al + "\r\n" + "User-agent: ";
            ad = "\r\nConnection: close\r\n\r\n";
            ac = "userid=" + ah + "&gamename=" + aj + "&score=";
        }
    }

    private final boolean ac()
    {
        if(ap)
            try
            {
                ab = new Socket(am, ak);
                aa = new PrintWriter(ab.getOutputStream(), false);
                z = new BufferedReader(new InputStreamReader(ab.getInputStream()));
                ao = true;
            }
            catch(Exception exception)
            {
                ab();
            }
        return ao;
    }

    private final void ab()
    {
        try
        {
            if(z != null)
                z.close();
            if(aa != null)
                aa.close();
            if(ab != null)
                ab.close();
        }
        catch(Exception exception) { }
        ab = null;
        aa = null;
        z = null;
        ao = false;
    }

    public final boolean aa(long l1, long l2)
    {
        if(l1 > ag && ac())
        {
            String s1 = Long.toString(l1, 35);
            String s2 = l1 + "&scoredatetime=" + l2;
            int i1 = ac.length() + s2.length();
            String s3 = null;
            aa.print(af + i1);
            aa.print(ae + s1);
            aa.print(ad);
            aa.print(ac + s2);
            aa.flush();
            i1 = -1;
            try
            {
                for(String s4 = z.readLine().toLowerCase(); s4.length() != 0; s4 = z.readLine().toLowerCase())
                    if(s4.startsWith("content-length: "))
                        i1 = Integer.parseInt(s4.substring("content-length: ".length()));

                if(i1 != 0)
                {
                    char ac1[] = new char[i1];
                    z.read(ac1, 0, i1);
                    s3 = (new String(ac1)).trim();
                } else
                if(i1 == -1)
                {
                    StringBuffer stringbuffer = new StringBuffer(50);
                    for(int j1 = z.read(); j1 != -1; j1 = z.read())
                        stringbuffer.append((char)j1);

                    s3 = stringbuffer.toString().trim();
                }
                if(s3 != null)
                {
                    if(s3.startsWith("ok: high score"))
                        return true;
                    if(s3.startsWith("ok: not high score"))
                        return false;
                }
            }
            catch(Exception exception) { }
        }
        return false;
    }

    public final String getAppletInfo()
    {
        return a5;
    }

    public final void start()
    {
        a6 = new Thread(this);
        a6.start();
        requestFocus();
    }

    public final void stop()
    {
        a6 = null;
        t();
    }

    public final void run()
    {
        az = System.currentTimeMillis();
        while(a6 != null) 
            ag();
    }

    public final void update(Graphics g1)
    {
    }

    public final void paint(Graphics g1)
    {
        if(q)
            c.bv();
        else
        if(av != 0)
            a();
    }

    public final void init()
    {
        a _tmp = this;
        boolean _tmp1 = true;
        n();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addFocusListener(this);
        setBackground(Color.black);
        ad();
        c();
        aq = getFontMetrics(getFont());
        af("click here");
        c.bw(this);
    }

    public final void keyTyped(KeyEvent keyevent)
    {
    }

    public final void keyPressed(KeyEvent keyevent)
    {
        int i1 = keyevent.getKeyCode();
        switch(i1)
        {
        case 122: // 'z'
            x();
            break;

        case 123: // '{'
            ah();
            break;

        default:
            am(i1);
            break;
        }
    }

    public final void keyReleased(KeyEvent keyevent)
    {
        int i1 = keyevent.getKeyCode();
        switch(i1)
        {
        default:
            al(i1);
            break;
        }
    }

    public final void mouseEntered(MouseEvent mouseevent)
    {
    }

    public final void mouseExited(MouseEvent mouseevent)
    {
    }

    public final void mouseClicked(MouseEvent mouseevent)
    {
    }

    public final void mouseDragged(MouseEvent mouseevent)
    {
        ai(mouseevent.getX(), mouseevent.getY());
    }

    public final void mouseMoved(MouseEvent mouseevent)
    {
        ai(mouseevent.getX(), mouseevent.getY());
    }

    public final void mouseReleased(MouseEvent mouseevent)
    {
        aj(mouseevent.getModifiers());
    }

    public final void mousePressed(MouseEvent mouseevent)
    {
        if(!q)
            ak(mouseevent.getModifiers());
        else
            c.bt();
    }

    public final void windowActivated(WindowEvent windowevent)
    {
    }

    public final void windowDeactivated(WindowEvent windowevent)
    {
    }

    public final void windowOpened(WindowEvent windowevent)
    {
    }

    public final void windowClosed(WindowEvent windowevent)
    {
    }

    public final void windowIconified(WindowEvent windowevent)
    {
    }

    public final void windowDeiconified(WindowEvent windowevent)
    {
    }

    public final void windowClosing(WindowEvent windowevent)
    {
        stop();
        System.exit(0);
    }

    public final void focusGained(FocusEvent focusevent)
    {
        s();
        ar = null;
        av &= -3;
    }

    public final void focusLost(FocusEvent focusevent)
    {
        t();
        af("click here");
        av |= 2;
        w = true;
    }

    public final void z(String as1[])
    {
        String s1 = "-";
        ar = null;
        for(int i1 = 0; i1 < as1.length; i1++)
            if(as1[i1].startsWith(s1))
            {
                String s2 = as1[i1].substring(1);
                String s3;
                if(i1 + 1 >= as1.length || as1[i1 + 1].startsWith(s1))
                    s3 = null;
                else
                    s3 = as1[++i1];
                m(s2, s3);
            }

        requestFocus();
        b();
        a6 = Thread.currentThread();
        aq = getFontMetrics(getFont());
        run();
    }

    public final void y(String as1[])
    {
        Class class1 = getClass();
        int i1 = as1.length;
        t = new AudioClip[i1];
        s = new boolean[i1];
        if(class1 != null)
            try
            {
                for(int j1 = 0; j1 < i1; j1++)
                    t[j1] = getAudioClip(class1.getResource("sound/" + as1[j1]));

                v = true;
                u = true;
            }
            catch(Exception exception)
            {
                try
                {
                    for(int k1 = 0; k1 < i1; k1++)
                        t[k1] = getAudioClip(an, "sound/" + as1[k1]);

                    for(int l1 = 0; l1 < i1; l1++)
                        t[l1].stop();

                    v = true;
                    u = true;
                }
                catch(Exception exception1) { }
            }
    }

    private final void x()
    {
        if(u)
        {
            if(v)
                t();
            else
                s();
            v = !v;
        }
    }

    public final void w(int i1)
    {
        if(v)
            t[i1].play();
    }

    public final void v(int i1)
    {
        if(s[i1])
        {
            if(v)
                t[i1].stop();
            s[i1] = false;
        }
    }

    public final void u(int i1)
    {
        if(!s[i1])
        {
            if(v)
                t[i1].loop();
            s[i1] = true;
        }
    }

    private final void t()
    {
        if(u)
        {
            for(int i1 = 0; i1 < t.length; i1++)
                t[i1].stop();

        }
    }

    private final void s()
    {
        if(u)
        {
            for(int i1 = 0; i1 < t.length; i1++)
                if(s[i1])
                    t[i1].loop();

        }
    }

    public final int r()
    {
        return r++;
    }

    public final byte[] q(String s1, int i1)
    {
        return q(s1, i1, i1);
    }

    public final byte[] q(String s1, int i1, int j1)
    {
        byte abyte0[] = null;
        try
        {
            InputStream inputstream = getClass().getResourceAsStream(s1);
            abyte0 = new byte[j1 + 1];
            int l1;
            for(int k1 = 0; k1 < i1; k1 += l1)
            {
                l1 = inputstream.read(abyte0, k1, i1 - k1);
                if(l1 == -1)
                    break;
            }

            inputstream.close();
        }
        catch(Exception exception) { }
        return abyte0;
    }

    public final void p(int i1)
    {
        p = true;
        o = i1;
    }

    public static final void o(String s1, int i1)
    {
        a4.put(s1, new Integer(i1));
    }

    public final void n()
    {
        for(Enumeration enumeration = a4.keys(); enumeration.hasMoreElements();)
        {
            String s1 = (String)enumeration.nextElement();
            try
            {
                String s2 = getParameter(s1);
                if(s2 != null)
                    m(s1, s2);
            }
            catch(Exception exception) { }
        }

    }

    private final void m(String s1, String s2)
    {
        Integer integer = (Integer)a4.get(s1);
        try
        {
            if(integer != null)
            {
                int i1 = integer.intValue();
                switch(i1)
                {
                case 0: // '\0'
                    j(s2);
                    break;

                case 1: // '\001'
                    n = (new Double(s2)).doubleValue();
                    break;

                case 3: // '\003'
                    ah = s2;
                    break;

                case 4: // '\004'
                    ai = s2;
                    break;

                case 5: // '\005'
                    ag = Long.parseLong(s2);
                    break;

                case 6: // '\006'
                    aj = s2;
                    break;

                default:
                    d(i1, s2);
                    break;

                case 2: // '\002'
                    break;
                }
            }
        }
        catch(Exception exception) { }
    }

    public final void l(byte abyte0[], int i1, int j1, int k1)
    {
        j = abyte0;
        l = ~j1;
        k = i1;
        int _tmp = k1;
        i = this;
        if(j != null)
            j[k] = (byte)(j[k] & l | k1);
    }

    public final void k(String s1, int i1)
    {
        if(s1 != null)
            m.put(s1, new Integer(i1));
    }

    public final void j(String s1)
    {
        Integer integer = (Integer)m.get(s1);
        int i1;
        if(integer != null)
            i1 = integer.intValue();
        else
            i1 = i;
        if(j != null)
            j[k] = (byte)(j[k] & l | i1);
        a _tmp = this;
        int _tmp1 = i1;
    }

    public final boolean i()
    {
        if(a_ > a0)
            a2 = 0.9F * a2 + (a_ - a0) * 0.1F;
        else
            a2 = 0.9F * a2 + 0.1F;
        if(a2 > a3)
            a2 = a3;
        a1--;
        if(a1 <= 0.0F)
        {
            a1 += a2;
            return false;
        } else
        {
            return true;
        }
    }

    private final void h()
    {
        long l1 = System.currentTimeMillis();
        long l2 = l1 - az;
        if(l2 < (long)ax)
        {
            try
            {
                Thread.sleep((long)ax - l2);
            }
            catch(Exception exception) { }
            l1 = System.currentTimeMillis();
            l2 = l1 - az;
        } else
        if(ax < 0)
        {
            ay = 0.0F;
            ax = 0;
        }
        a_ = 0.9F * a_ + 0.1F * (float)l2;
        ay += 0.1F * (a0 - a_);
        ax = (int)ay;
        az = l1;
        aw++;
    }

    private final void g()
    {
        a _tmp = this;
        boolean _tmp1 = false;
        av = 0;
        au = Color.white;
        at = 0.9F;
        as = 100;
        ap = false;
        ao = false;
        a _tmp2 = this;
        boolean _tmp3 = false;
        w = false;
        v = false;
        u = false;
        t = new AudioClip[32];
        s = new boolean[32];
        r = 0;
        q = false;
        p = false;
        o = 0;
        a _tmp4 = this;
        boolean _tmp5 = false;
        a _tmp6 = this;
        boolean _tmp7 = false;
        n = 2D;
        m = new Hashtable();
        a _tmp8 = this;
        Toolkit.getDefaultToolkit();
    }

    public a()
    {
        g();
    }

    public abstract void f();

    public abstract void e();

    public final void d(int i1, String s1)
    {
    }

    public final void c()
    {
        f();
        e();
    }

    public final void b()
    {
        a _tmp = this;
        boolean _tmp1 = true;
        setBackground(Color.black);
        b = new Frame(a5);
        b.setVisible(true);
        b.addWindowListener(this);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        b.add(this, "Center");
        Insets insets = b.getInsets();
        f();
        e();
        b.setSize(insets.right + insets.left + e, insets.top + insets.bottom + f);
        b.setResizable(false);
    }

    public final void a()
    {
        Graphics g1 = getGraphics();
        if(g1 != null && d != null)
        {
            ae(c);
            g1.drawImage(d, 0, 0, this);
        }
    }

    public a(int i1)
    {
        this();
    }

    public static Thread a6;
    public static String a5 = "NONE";
    public static Hashtable a4 = new Hashtable();
    public static float a3 = 10F;
    public static float a2 = 1.0F;
    public static float a1 = 1.0F;
    public static float a0;
    public static float a_;
    public static long az;
    public static float ay;
    public static int ax;
    public static int aw = 0;
    public int av;
    public Color au;
    public float at;
    public int as;
    public String ar;
    public FontMetrics aq;
    public boolean ap;
    public boolean ao;
    public URL an;
    public String am;
    public String al;
    public int ak;
    public String aj;
    public String ai;
    public String ah;
    public long ag;
    public String af;
    public String ae;
    public String ad;
    public String ac;
    public Socket ab;
    public PrintWriter aa;
    public BufferedReader z;
    public boolean w;
    public boolean v;
    public boolean u;
    public AudioClip t[];
    public boolean s[];
    public int r;
    public boolean q;
    public boolean p;
    public int o;
    public double n;
    public Hashtable m;
    public int l;
    public int k;
    public byte j[];
    public int i;
    public int h;
    public int g;
    public int f;
    public int e;
    public Image d;
    public Graphics c;
    public Frame b;

    static 
    {
        char _tmp = '\377';
        char _tmp1 = '\377';
        byte _tmp2 = 16;
        byte _tmp3 = 8;
        boolean _tmp4 = false;
        boolean _tmp5 = false;
        boolean _tmp6 = false;
        int[] _tmp7 = new int[256];
        int[] _tmp8 = new int[256];
        int[] _tmp9 = new int[256];
        double _tmp10 = 1.0D;
        double _tmp11 = 1.0D;
        double _tmp12 = 1.0D;
        double _tmp13 = 0.0D;
        double _tmp14 = 0.0D;
        double _tmp15 = 0.0D;
        a0 = 16.666F;
        a_ = a0;
        ay = 8F;
        ax = (int)ay;
        String _tmp16 = "";
        String _tmp17 = "";
        boolean _tmp18 = false;
        boolean _tmp19 = false;
        boolean _tmp20 = false;
        String s1 = System.getProperty("java.version");
        byte byte0 = 2;
        byte byte1 = 2;
        try
        {
            int k1 = s1.indexOf(".");
            int i1 = Integer.parseInt(s1.substring(0, k1));
            s1 = s1.substring(k1 + 1);
            k1 = s1.indexOf(".");
            int j1 = Integer.parseInt(s1.substring(0, k1));
            s1 = s1.substring(k1 + 1);
        }
        catch(Exception exception) { }
        o("language", 0);
        o("scale", 1);
        o("nosound", 2);
        o("userid", 3);
        o("requestURI", 4);
        o("minHighScore", 5);
        o("gamename", 6);
    }
}
