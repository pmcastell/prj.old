// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Register.java

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.StringTokenizer;

public class Register extends Panel
{

    public static boolean isValidatedURL(String s)
    {
        if(doValidateURL(s))
            return true;
        else
            return doValidateURL(fixURL(s));
    }

    private static GridBagConstraints makeGBC(int i, int j, String s)
    {
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = i;
        gridbagconstraints.gridy = j;
        gridbagconstraints.anchor = 17;
        if(s != null)
        {
            StringTokenizer stringtokenizer = new StringTokenizer(s, TX("%4;6$#,30(*:"), true);
            while(stringtokenizer.hasMoreTokens()) 
            {
                try
                {
                    String s1 = stringtokenizer.nextToken();
                    int k = Integer.parseInt(stringtokenizer.nextToken());
                    if(s1.equals(TX("%")))
                        gridbagconstraints.gridwidth = k;
                    else
                    if(s1.equals(TX("4")))
                        gridbagconstraints.gridheight = k;
                    else
                    if(s1.equals(TX(";")))
                        gridbagconstraints.anchor = aAnchor[k];
                    else
                    if(s1.equals(TX("6")))
                        gridbagconstraints.fill = aFill[k];
                    else
                    if(s1.equals(TX("$")))
                        gridbagconstraints.weightx = k;
                    else
                    if(s1.equals(TX("#")))
                        gridbagconstraints.weighty = k;
                    else
                    if(s1.equals(TX(",")))
                    {
                        gridbagconstraints.ipadx = k;
                        gridbagconstraints.ipady = k;
                    } else
                    if(s1.equals(TX("3")))
                        gridbagconstraints.insets = new Insets(k, k, k, k);
                    else
                    if(s1.equals(TX("0")))
                        gridbagconstraints.insets.left = k;
                    else
                    if(s1.equals(TX("(")))
                        gridbagconstraints.insets.top = k;
                    else
                    if(s1.equals(TX("*")))
                        gridbagconstraints.insets.right = k;
                    else
                    if(s1.equals(TX(":")))
                        gridbagconstraints.insets.bottom = k;
                    else
                        System.out.println("?GBC:" + s1);
                    continue;
                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
                break;
            }
        }
        return gridbagconstraints;
    }

    private void doGo()
    {
        String s = iemail.getText().trim();
        if(!isValidEmailAddress(s))
        {
            iLine1.setForeground(Color.red);
            iLine2.setForeground(Color.red);
            iemail.requestFocus();
            iemail.selectAll();
            return;
        } else
        {
            String s1 = TX("4((,bmm)79'*7n&3)';0%;*7n9-/m9*/m)*75");
            long l = System.currentTimeMillis();
            String s2 = s1 + TX("]&7*_kv7/;30_") + encode(s) + TX("v(_") + l + TX("v'*0_") + encode(base());
            showDocument(s2);
            return;
        }
    }

    private void doFirstTimeInit()
    {
        if(m_bFirstTime)
        {
            m_bFirstTime = false;
            iemail.selectAll();
            iemail.requestFocus();
        }
    }

    public Register(Applet applet, String s)
    {
        iemail = new TextField(25);
        iGo = new Button(TX("U-{"));
        m_bFirstTime = true;
        m_applet = applet;
        m_url = s;
        setBackground(new Color(0xffffd0));
        setForeground(Color.black);
        setLayout(new GridBagLayout());
        setFont(new Font(TX("T70&7(39;"), 0, 12));
        iemail.setFont(new Font(TX("Y-'*37*"), 0, 12));
        MyLabel mylabel = new MyLabel(TX("P-53.|m|K'391|J753)(*;(3-.|o|I(7,|k|-6|k"));
        mylabel.setFont(new Font(TX("T70&7(39;"), 1, 16));
        gbAdd(mylabel, 0, 0, "w2");
        if(s.indexOf("?") > 0 && !isValidatedURL(s))
        {
            MyLabel mylabel1 = new MyLabel(TX("AGJP|&7*3639;(3-.|7**-*|oo|,;*(3;0|GJP]?"));
            mylabel1.setForeground(Color.red);
            gbAdd(mylabel1, 0, 1, "w2");
        }
        String s1 = System.getProperty(TX("2;&;n&7*)3-."));
        if(isUpgradeJavaVersion(s1))
        {
            MyLabel mylabel2 = new MyLabel(TX("R;&;|knhnjq|G,8;(7|J79-//7.878|t6-'.8|") + s1 + TX("s|oo|4((,bmm%%%n2;&;n9-/"));
            mylabel2.setClickable(true);
            mylabel2.setForeground(Color.red);
            gbAdd(mylabel2, 0, 2, "w2");
            iUpgradeJava = mylabel2;
        }
        iLine1 = new MyLabel(TX("W.(7*|#-'*|7/;30|;88*7))|(-|;997))|(43)|)7*&397n||V-*|63*)(|(3/7|')7*)p"));
        iLine2 = new MyLabel(TX(";|LSN|.'/:7*|;.8|;9(3&;(3-.|3.)(*'9(3-.)|%300|:7|7/;3078|(-|#-'b"));
        gbAdd(iLine1, 0, 4, "w2");
        gbAdd(iLine2, 0, 6, "w2b5");
        gbAdd(iemail, 0, 10, "x1f1");
        gbAdd(iGo, 1, 10, "");
        String s2 = getOpt(s, TX("7/;30"));
        iemail.setText(s2 == null ? DEFEMAIL : s2);
    }

    public void paint(Graphics g)
    {
        doFirstTimeInit();
        int i = size().width - 1;
        int j = size().height - 1;
        g.setColor(Color.lightGray);
        g.drawLine(i, 0, i, j);
        g.drawLine(i, j, 0, j);
        g.setColor(Color.gray);
        g.drawLine(0, 0, i, 0);
        g.drawLine(0, 0, 0, j);
    }

    private void gbAdd(Object obj, int i, int j, String s)
    {
        Register register = this;
        GridBagConstraints gridbagconstraints = makeGBC(i, j, s);
        Object obj1 = (obj instanceof Component) ? ((Object) ((Component)obj)) : ((Object) (new MyLabel(obj.toString())));
        if(obj1 instanceof Label)
            ((Label)obj1).setBackground(Color.red);
        register.add(((Component) (obj1)));
        try
        {
            ((GridBagLayout)register.getLayout()).setConstraints(((Component) (obj1)), gridbagconstraints);
            return;
        }
        catch(Exception exception)
        {
            System.out.println("gbAdd setConstraints failed: " + exception);
        }
    }

    private void showDocument(String s)
    {
        try
        {
            m_applet.getAppletContext().showDocument(new URL(s));
        }
        catch(Exception exception)
        {
            System.err.println("" + exception);
        }
        try
        {
            for(int i = 0; i < 10 && m_applet.isActive(); i++)
                Thread.sleep(50L);

            if(m_applet.isActive())
            {
                System.err.println(TX("E[JNSNUb|)4-%X-9'/7.(ts|6;3078p|(*#3.5|R;&;I9*3,(|:;91',"));
                jsEval(m_applet, TX("8-9'/7.(n0-9;(3-.n4*76_u") + s + "'");
                return;
            }
        }
        catch(Exception exception1)
        {
            System.err.println("" + exception1);
        }
    }

    private static String encode(String s)
    {
        int i = s.length();
        StringBuffer stringbuffer = new StringBuffer(2 * i);
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9')
                stringbuffer.append(c);
            else
            if(c == ' ')
            {
                stringbuffer.append('+');
            } else
            {
                stringbuffer.append('%');
                stringbuffer.append(HEX[c / 16 & 0xf]);
                stringbuffer.append(HEX[c & 0xf]);
            }
        }

        return stringbuffer.toString();
    }

    public static boolean isTimeOK(String s)
    {
        try
        {
            long l = System.currentTimeMillis();
            long l1 = Long.parseLong(getOpt(s, "t"));
            return Math.abs(l - l1) < 0x1b7740L;
        }
        catch(Exception _ex)
        {
            return false;
        }
    }

    private String base()
    {
        String s = m_url;
        int i = s.indexOf('?');
        if(i > 0)
            return s.substring(0, i);
        else
            return s;
    }

    private static String TX(String s)
    {
        char ac[] = s.toCharArray();
        for(int i = 0; i < ac.length; i++)
        {
            char c = ac[i];
            if(c >= ' ' && c <= '~')
                ac[i] = (char)(32 + (0x50ed89 - c) % 95);
        }

        return new String(ac);
    }

    private static String decode(String s)
    {
        int i = s.length();
        StringBuffer stringbuffer = new StringBuffer(i);
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(c == '+')
                c = ' ';
            else
            if(c == '%')
            {
                try
                {
                    c = (char)Integer.parseInt(s.substring(j + 1, j + 1 + 2), 16);
                }
                catch(Exception _ex) { }
                j += 2;
            }
            stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

    private static void outputHex(StringBuffer stringbuffer, int i)
    {
        int j = 0;
        do
        {
            stringbuffer.append(HEX[i >>> 28]);
            i <<= 4;
        } while(++j < 8);
    }

    private static long lhex(String s)
    {
        long l = 0L;
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char c = Character.toUpperCase(s.charAt(j));
            l = l * 16L + (long)(c < '0' || c > '9' ? c < 'A' || c > 'F' ? 0 : (c - 65) + 10 : c - 48);
        }

        return l;
    }

    private static Class _mthclass$(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private boolean isUpgradeJavaVersion(String s)
    {
        String as[] = {
            TX("knknj"), TX("knkni"), TX("kni"), TX("knhnl")
        };
        for(int i = 0; i < as.length; i++)
            if(s.startsWith(as[i]))
                return true;

        return false;
    }

    public boolean action(Event event, Object obj)
    {
        if(event.target == iGo || event.target == iemail)
            doGo();
        else
        if(iUpgradeJava != null && event.target == iUpgradeJava)
            showDocument(TX("4((,bmm%%%n2;&;n9-/"));
        return true;
    }

    private static String getOpt(String s, String s1)
    {
        int i = s.indexOf('?');
        if(i > 0)
        {
            String s2 = "&" + s.substring(i + 1);
            String s3 = "&" + s1 + "=";
            int j = s2.indexOf(s3);
            if(j >= 0)
            {
                int k = j + s3.length();
                int l = s2.indexOf("&", k);
                return decode(l < k ? s2.substring(k) : s2.substring(k, l));
            }
        }
        return null;
    }

    private static byte[] stob(String s)
    {
        if(s != null)
        {
            byte abyte0[] = new byte[s.length()];
            s.getBytes(0, abyte0.length, abyte0, 0);
            return abyte0;
        } else
        {
            return null;
        }
    }

    public Insets insets()
    {
        return new Insets(5, 5, 5, 5);
    }

    private static boolean doValidateURL(String s)
    {
        try
        {
            int i = s.indexOf(TX("v&_"));
            if(i > 0)
            {
                long l = 1L | lhex(sha(s.substring(0, i)).substring(0, 16));
                long l1 = lhex(s.substring(i + 3));
                return l * l1 == 0x75bcd15L;
            }
        }
        catch(Exception _ex) { }
        return false;
    }

    private static String fixURL(String s)
    {
        try
        {
            int i = s.indexOf('?');
            if(i > 0)
            {
                String s1 = s.substring(0, i + 1);
                for(StringTokenizer stringtokenizer = new StringTokenizer(s.substring(i + 1), "&", true); stringtokenizer.hasMoreTokens();)
                {
                    String s2 = stringtokenizer.nextToken();
                    int j = s2.indexOf('=');
                    if(j > 0)
                        s1 += s2.substring(0, j + 1) + encode(s2.substring(j + 1));
                    else
                        s1 += s2;
                }

                return s1;
            }
        }
        catch(Exception _ex) { }
        return s;
    }

    private static boolean isValidEmailChars(String s)
    {
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            boolean flag = c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == '_' || c == '-' || c == '.' || c == '@';
            if(!flag)
                return false;
        }

        return true;
    }

    private static void jsEval(Applet applet, String s)
    {
        try
        {
            Class.forName(TX("RI")).getMethod(TX("2)"), new Class[] {
                java.applet.Applet.class, java.lang.String.class
            }).invoke(null, new Object[] {
                applet, s
            });
            return;
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }

    private static boolean isValidEmailAddress(String s)
    {
        if(s.indexOf(DEFEMAIL) >= 0)
            return false;
        if(s.length() > 0 && Character.isDigit(s.charAt(s.length() - 1)))
            return false;
        int i = s.indexOf('@');
        int j = s.indexOf('.', i + 1);
        if(i > 1 && j > i + 1)
            return isValidEmailChars(s);
        else
            return false;
    }

    private static String sha(String s)
    {
        return sha(stob(s));
    }

    private static String sha(byte abyte0[])
    {
        int i = abyte0.length;
        byte abyte1[] = new byte[((i + 1 + 8 + 63) / 64) * 64 - i];
        abyte1[0] = -128;
        long l = (long)i * 8L;
        int j = 0;
        do
            abyte1[abyte1.length - 1 - j] = (byte)(int)(255L & l >> j * 8);
        while(++j < 8);
        j = 0x67452301;
        int k = 0xefcdab89;
        int i1 = 0x98badcfe;
        int j1 = 0x10325476;
        int k1 = 0xc3d2e1f0;
        int ai[] = new int[80];
        int l1 = i + abyte1.length;
        for(int i2 = 0; i2 < l1;)
        {
            int j2 = 0;
            do
            {
                int k2 = 0;
                int j3 = 0;
                do
                {
                    k2 = k2 << 8 | 0xff & (i2 >= i ? abyte1[i2 - i] : abyte0[i2]);
                    i2++;
                } while(++j3 < 4);
                ai[j2] = k2;
            } while(++j2 < 16);
            j2 = 16;
            do
            {
                int l2 = ai[j2 - 3] ^ ai[j2 - 8] ^ ai[j2 - 14] ^ ai[j2 - 16];
                ai[j2] = l2 << 1 | l2 >>> -1;
            } while(++j2 < 80);
            j2 = j;
            int i3 = k;
            int k3 = i1;
            int l3 = j1;
            int i4 = k1;
            int j4 = 0;
            do
            {
                int k4 = (j2 << 5 | j2 >>> -5) + (i3 & k3 | ~i3 & l3) + i4 + ai[j4] + 0x5a827999;
                i4 = l3;
                l3 = k3;
                k3 = i3 << 30 | i3 >>> -30;
                i3 = j2;
                j2 = k4;
            } while(++j4 < 20);
            j4 = 20;
            do
            {
                int l4 = (j2 << 5 | j2 >>> -5) + (i3 ^ k3 ^ l3) + i4 + ai[j4] + 0x6ed9eba1;
                i4 = l3;
                l3 = k3;
                k3 = i3 << 30 | i3 >>> -30;
                i3 = j2;
                j2 = l4;
            } while(++j4 < 40);
            j4 = 40;
            do
            {
                int i5 = (j2 << 5 | j2 >>> -5) + (i3 & k3 | i3 & l3 | k3 & l3) + i4 + ai[j4] + 0x8f1bbcdc;
                i4 = l3;
                l3 = k3;
                k3 = i3 << 30 | i3 >>> -30;
                i3 = j2;
                j2 = i5;
            } while(++j4 < 60);
            j4 = 60;
            do
            {
                int j5 = (j2 << 5 | j2 >>> -5) + (i3 ^ k3 ^ l3) + i4 + ai[j4] + 0xca62c1d6;
                i4 = l3;
                l3 = k3;
                k3 = i3 << 30 | i3 >>> -30;
                i3 = j2;
                j2 = j5;
            } while(++j4 < 80);
            j += j2;
            k += i3;
            i1 += k3;
            j1 += l3;
            k1 += i4;
        }

        StringBuffer stringbuffer = new StringBuffer(40);
        outputHex(stringbuffer, j);
        outputHex(stringbuffer, k);
        outputHex(stringbuffer, i1);
        outputHex(stringbuffer, j1);
        outputHex(stringbuffer, k1);
        return stringbuffer.toString();
    }

    private static char HEX[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F'
    };
    private static final String DEFEMAIL = TX("#-'\\9-/,;.#n9-/");
    private Applet m_applet;
    private String m_url;
    private MyLabel iLine1;
    private MyLabel iLine2;
    private TextField iemail;
    private MyLabel iUpgradeJava;
    private Button iGo;
    private boolean m_bFirstTime;
    private static int aFill[] = {
        0, 2, 3, 1
    };
    private static int aAnchor[] = {
        10, 18, 11, 12, 17, 10, 13, 16, 15, 14
    };

}
