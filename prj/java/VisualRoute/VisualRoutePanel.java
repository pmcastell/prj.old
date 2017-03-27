// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VisualRoutePanel.java

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class VisualRoutePanel extends Panel
    implements Runnable
{

    public void setLogonMessage(String s)
    {
        m_status.setText(s);
    }

    public String getLogonMessage()
    {
        return m_status.getText();
    }

    private double[] map(int i, int j)
    {
        Image image = m_image;
        if(image != null)
        {
            int k = j - rMap.y;
            i -= m_ptMap.x;
            k -= m_ptMap.y;
            k -= 23;
            int l = image.getWidth(null);
            int i1 = image.getHeight(null);
            double d = Math.abs(m_mapCoverage[2] - Math.abs(m_mapCoverage[0]));
            double d1 = Math.abs(m_mapCoverage[1] - m_mapCoverage[3]);
            double d2 = m_mapCoverage[0] - d * ((double)k / (double)i1);
            double d3 = m_mapCoverage[1] + d1 * ((double)i / (double)l);
            double ad[] = {
                d2, d3
            };
            return ad;
        } else
        {
            return null;
        }
    }

    private Point map(double d, double d1)
    {
        Point point = null;
        if(m_image != null)
        {
            int i = m_image.getWidth(null);
            int j = m_image.getHeight(null);
            double d2 = m_mapCoverage[3] - m_mapCoverage[1];
            double d3 = m_mapCoverage[0] - m_mapCoverage[2];
            int k = (int)(((double)i / d2) * 360D);
            int l = (int)(((double)j / d3) * 180D);
            int i1 = (int)(((double)l / 180D) * (90D - m_mapCoverage[0]));
            int j1 = (int)(((double)k / 360D) * (m_mapCoverage[1] + 180D));
            d = 90D - d;
            d1 += 180D;
            if(i > 0 && j > 0)
            {
                point = new Point((int)((d1 * (double)k) / 360D), (int)((d * (double)l) / 180D));
                point.x = (point.x - j1) + 5;
                point.y = (point.y - i1) + 16;
            }
        }
        return point;
    }

    private Font doSetFont(Graphics g, int i, int j)
    {
        Font font = g.getFont();
        g.setFont(new Font("Helvitica", i, (g.getFont().getSize() * j) / 100));
        return font;
    }

    private Panel makeLoginPanel()
    {
        m_status = new Label("Please enter your username and password to access this " + m_applet.appName() + " server");
        m_status.setFont(new Font("Helvetica", 1, 12));
        m_password.setEchoChar('*');
        Panel panel = new Panel();
        GridBagLayout gridbaglayout = new GridBagLayout();
        panel.setLayout(gridbaglayout);
        GridBagConstraints gridbagconstraints = gridbaglayout.getConstraints(this);
        gridbagconstraints.gridy = 1;
        gridbagconstraints.gridx = 1;
        gridbagconstraints.insets = new Insets(20, 10, 20, 0);
        gridbagconstraints.gridwidth = 3;
        gridbagconstraints.fill = 2;
        panel.add(jjj(gridbaglayout, m_status, gridbagconstraints));
        gridbagconstraints.insets = new Insets(0, 10, 0, 0);
        gridbagconstraints.fill = 0;
        gridbagconstraints.gridwidth = 1;
        gridbagconstraints.gridy++;
        panel.add(jjj(gridbaglayout, new Label("Username:"), gridbagconstraints));
        gridbagconstraints.gridy++;
        gridbagconstraints.insets = new Insets(1, 10, 20, 0);
        panel.add(jjj(gridbaglayout, new Label("Password:"), gridbagconstraints));
        gridbagconstraints.gridx++;
        gridbagconstraints.gridy = 2;
        gridbagconstraints.insets = new Insets(0, 5, 0, 0);
        panel.add(jjj(gridbaglayout, m_username, gridbagconstraints));
        gridbagconstraints.gridy++;
        gridbagconstraints.insets = new Insets(1, 5, 20, 0);
        panel.add(jjj(gridbaglayout, m_password, gridbagconstraints));
        gridbagconstraints.gridx++;
        gridbagconstraints.weightx = 1.0D;
        gridbagconstraints.anchor = 17;
        gridbagconstraints.insets = new Insets(1, 10, 20, 0);
        panel.add(jjj(gridbaglayout, m_login, gridbagconstraints));
        return panel;
    }

    private int getColWidth(String s)
    {
        return getColWidth(findCol(s));
    }

    public void doLogin()
    {
        String s = "The " + m_applet.appName() + " server is currently not available. Please try again later.";
        try
        {
            s = readUrl("/logon?u=" + m_username.getText() + "&p=" + m_password.getText());
        }
        catch(Exception _ex) { }
        if("OK".equals(s))
        {
            setLoggedOn(true);
            return;
        } else
        {
            m_status.setText(s);
            return;
        }
    }

    private int getColWidth(int i)
    {
        if(i >= 0 && i < m_colwidths.length)
            return m_colwidths[i];
        else
            return 0;
    }

    private void setColWidth(String s, int i)
    {
        setColWidth(findCol(s), i);
    }

    private void setColWidth(int i, int j)
    {
        m_colwidths[i] = Math.min(400, Math.max(0, j));
    }

    private Component jjj(GridBagLayout gridbaglayout, Component component, GridBagConstraints gridbagconstraints)
    {
        gridbaglayout.setConstraints(component, gridbagconstraints);
        return component;
    }

    private int hitEdge(int i, int j)
    {
        if(rTable.inside(i, j))
        {
            i -= 5;
            for(int k = 0; k < m_colpos.length; k++)
            {
                int l = m_colpos[k];
                if(l > 0 && Math.abs(i - l) <= 2)
                    return k;
            }

        }
        return -1;
    }

    private void doSnap()
    {
        String as[][] = m_snapTable;
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        PrintStream printstream = new PrintStream(bytearrayoutputstream);
        String s = m_snapDate == null ? "" : " on " + m_snapDate.toLocaleString();
        String s1 = "=== " + m_applet.appNameVer() + " report" + s + " ===";
        printstream.println(makeString('=', s1.length()));
        printstream.println(s1);
        printstream.println(makeString('=', s1.length()));
        printstream.println("");
        printstream.println(m_heading);
        printstream.println(m_snapAnalysis);
        printstream.println("");
        int i = m_colreal[graphCol()];
        for(int j = 0; j < as[i].length; j++)
            as[i][j] = makeTextGraph(as[i][j]);

        int k = as.length;
        int ai[] = new int[k];
        for(int l = 0; l < as[0].length; l++)
        {
            for(int j1 = 0; j1 < k; j1++)
            {
                String s3 = as[j1][l];
                if(s3 != null)
                    ai[j1] = Math.max(ai[j1], s3.length());
                else
                    k = Math.min(k, j1);
            }

        }

        for(int i1 = 0; i1 < as[0].length; i1++)
        {
            String s2 = "";
            for(int k1 = 0; k1 < k; k1++)
                s2 = s2 + "| " + padString(as[k1][i1], ai[k1]) + " ";

            s2 = s2 + "|";
            if(i1 < 2)
                printstream.println(makeString('-', s2.length()));
            printstream.println(s2);
            if(i1 == as[0].length - 1)
                printstream.println(makeString('-', s2.length()));
        }

        new SnapFrame(m_applet.appName() + " table", bytearrayoutputstream.toString(), getFont().getSize());
    }

    private int nameCol()
    {
        return findCol(m_applet.getParam("col-name", "Node Name"));
    }

    public boolean isLoggedOn()
    {
        return m_bLoggedOn;
    }

    private void zoomIn(int i, int j)
    {
        if(m_zoomIndex < m_maxZoomLevel)
        {
            double ad[] = map(i, j);
            if(ad != null)
            {
                m_zoomIndex++;
                getMapImage(ad[0], ad[1], false);
            }
        }
    }

    private boolean isWhois(int i, int j)
    {
        Point point = hitTest(i, j);
        boolean flag = "true".equals(m_applet.getParam("whois"));
        return flag && point != null && (point.x == nameCol() || point.x == netCol());
    }

    public void doLogout()
    {
        String s = null;
        try
        {
            s = readUrl("/logoff");
        }
        catch(Exception _ex) { }
        if("OK".equals(s))
            setLoggedOn(false);
    }

    private String getStartButtonLabel()
    {
        return m_applet.getParam("startbtntext", "Start Trace");
    }

    private void setCursor(int i)
    {
        try
        {
            getFrame().setCursor(i);
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    public boolean mouseExit(Event event, int i, int j)
    {
        setCursor(0);
        return true;
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if(!_bDrag)
            if(_down.y > rTable.y && _down.y < rTable.y + rTable.height)
            {
                Point point = hitTest(i, j);
                int k = point == null ? -1 : point.x;
                int l = point == null ? -1 : point.y;
                if(isWhois(i, j))
                {
                    if(l > 0 && k == nameCol())
                    {
                        String s = m_snapTable[m_colreal[k]][l];
                        doDomainWhois(s);
                    } else
                    if(l > 0 && k == netCol())
                    {
                        String s1 = m_snapTable[m_colreal[ipCol()]][l];
                        doNetworkWhois(s1);
                    }
                } else
                {
                    String s2 = getLink(i, j);
                    if(s2 != null)
                        m_applet.showDocument(s2);
                }
            } else
            if(j > rMap.y)
                if(_down.metaDown())
                    zoomOut(i, j);
                else
                    zoomIn(i, j);
        return true;
    }

    private String readUrl(String s)
        throws IOException
    {
        URL url = new URL(m_applet.getCodeBase(), s);
        URLConnection urlconnection = url.openConnection();
        InputStream inputstream = urlconnection.getInputStream();
        String s1 = (new DataInputStream(inputstream)).readLine();
        inputstream.close();
        return s1;
    }

    private Frame getFrame()
    {
        for(Object obj = this; obj != null; obj = ((Component) (obj)).getParent())
            if(obj instanceof Frame)
                return (Frame)obj;

        return null;
    }

    private void putCityName(Graphics g, Vector vector, Point point, String s)
    {
        FontMetrics fontmetrics = g.getFontMetrics();
        int i = fontmetrics.getAscent();
        int j = fontmetrics.stringWidth(s);
        Rectangle rectangle = new Rectangle(point.x - 2, point.y - i / 2, j + 4, i);
        Rectangle rectangle1 = new Rectangle(point.x - j - 2, point.y - i / 2, j + 4, i);
        if(canPut(vector, rectangle))
        {
            g.drawString(s, rectangle.x + 4, rectangle.y + rectangle.height);
            return;
        }
        if(canPut(vector, rectangle1))
            g.drawString(s, rectangle1.x, rectangle1.y + rectangle1.height);
    }

    private Point hitTest(int i, int j)
    {
        if(rTable.inside(i, j))
        {
            int k = -1;
            for(int l = 0; l < m_colpos.length; l++)
            {
                int i1 = m_colpos[l];
                int k1 = m_colwidths[l];
                if(i1 <= 0 || i >= i1 || i <= i1 - k1)
                    continue;
                k = l;
                break;
            }

            byte byte0 = 20;
            int j1 = (j - rTable.y - byte0) / m_snapTableHeight;
            if(k >= 0 && j1 >= 0)
                return new Point(k, j1);
            else
                return null;
        } else
        {
            return null;
        }
    }

    private void getMapImage(double d, double d1, boolean flag)
    {
        String s = "md";
        s += "x" + d1 + "y" + d;
        s += "z" + m_zoomIndex;
        try
        {
            long al[] = {
                0x55555555L, 0x55aa55d3L, 0x1122334dL
            };
            URL url = m_applet.getCodeBase();
            URL url1 = new URL(url, s);
            InputStream inputstream = url1.openConnection().getInputStream();
            String s1 = VisualRouteApplet.readLine(inputstream, al);
            StringTokenizer stringtokenizer = new StringTokenizer(s1, ",");
            for(int i = 0; stringtokenizer.hasMoreTokens() && i < 4; i++)
            {
                String s3 = stringtokenizer.nextToken();
                m_mapCoverage[i] = Double.valueOf(s3).doubleValue();
            }

            String s2 = VisualRouteApplet.readLine(inputstream, al);
            if(s2 != null)
                s2 = s2.replace('\\', '/');
            URL url2 = new URL(url, s2);
            m_image = m_applet.getImage(url2);
            System.out.println("gmi: bFirst=" + flag);
            if(flag)
                System.out.println("gmi2: w=" + size().width + ", iw=" + m_image.getWidth(null));
        }
        catch(Exception exception)
        {
            System.out.println("getMapImage " + exception);
        }
        repaint(rMap, 0L);
    }

    private boolean canDragMap()
    {
        return !m_applet.isSimpleGUI();
    }

    private Panel makeRegisterPanel(String s)
    {
        Panel panel = new Panel();
        panel.setBackground(new Color(230, 230, 230));
        panel.setLayout(new FlowLayout(0));
        Register register = new Register(m_applet, s);
        panel.add(register);
        return panel;
    }

    private void setTableWidth(int i)
    {
        if(m_tw != i)
        {
            m_tw = i;
            repaint();
        }
    }

    private int paintMap(Graphics g, String s)
    {
        if(m_image != null)
        {
            Dimension dimension = size();
            Graphics g1 = g.create(2, 2, dimension.width, dimension.height);
            paintPanel(g1, "Map", 0, 0, Math.max(m_tw, 610), 420);
            g1.clipRect(2, 0, 606, 418);
            g1.translate(m_ptMap.x, m_ptMap.y);
            Image image = m_image;
            g1.drawImage(image, 5, 15, this);
            drawGeoPath(g1, s);
            return 420;
        } else
        {
            return 0;
        }
    }

    public boolean action(Event event, Object obj)
    {
        if(event.target == iHost || event.target == iStartTrace)
        {
            if(iHost.getText().trim().length() == 0)
                iHost.setText(getLocalHostAddress());
            iHost.selectAll();
            setHost(iHost.getText().trim());
        } else
        if(event.target == iSnap)
            doSnap();
        else
        if(event.target == iStop)
            stop();
        else
        if(event.target == iNewWindow)
            doNewWindow();
        else
        if(event.target == iShowDetails)
            repaint();
        else
        if(event.target == m_login)
            doLogin();
        else
        if(event.target == m_logout)
            doLogout();
        return true;
    }

    private String getStringOption(String s, String s1)
    {
        String s2 = ";;?";
        int i = s == null ? 0 : s.indexOf(s2);
        if(i >= 0)
        {
            s = "&" + s.substring(i + s2.length());
            String s3 = "&" + s1 + "=";
            i = s.indexOf(s3);
            if(i >= 0)
            {
                s = s.substring(i + s3.length());
                return s;
            }
        }
        return null;
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        _down = event;
        _col = hitEdge(i, j);
        _orgcolsize = getColWidth(_col);
        _orgmap = m_ptMap;
        _bDrag = false;
        return true;
    }

    public void run()
    {
        m_snapDate = new Date();
        String s = m_applet.isSimpleGUI() ? "VisualRoute Server" : m_applet.getCodeBase().getHost();
        setAnalysis("Contacting '" + s + "'...");
        boolean flag = false;
        try
        {
            String s1 = m_applet.getLang();
            long l = System.currentTimeMillis() / 1000L;
            String s2 = m_applet.getDocumentBase().getHost();
            String s3 = "";
            String s4 = null;
            URL url = new URL(m_applet.getCodeBase(), "/traceauth?t=" + l);
            URLConnection urlconnection = url.openConnection();
            InputStream inputstream = urlconnection.getInputStream();
            (new DataInputStream(inputstream)).readLine();
            inputstream.close();
            String s6 = urlconnection.getHeaderField("Etag");
            s4 = s6 == null || s6.length() != 40 ? null : s6;
            url = new URL(m_applet.getCodeBase(), "/vr?url=" + m_host + (m_prot == null ? "" : "&prot=" + m_prot) + (m_port == -1 ? "" : "&port=" + m_port) + "&t=" + l + "&dh=" + s2 + (s4 == null ? "" : "&tok=" + s4) + (s1 == null ? "" : "&lang=" + s1) + s3);
            setInputStream(url.openStream());
            try
            {
                long al[] = {
                    0x55555555L, 0x7777777dL, 0x3333335fL
                };
                String s5;
                while((s5 = VisualRouteApplet.readLine(m_in, al)) != null) 
                    flag |= processCommand(s5);
            }
            catch(Exception exception1)
            {
                exception1.printStackTrace();
            }
            if(!flag && !m_bStopping)
                setAnalysis(m_analysis + " `c=ff0000`'" + s + "' aborted your connection");
            try
            {
                m_in.close();
            }
            catch(Exception _ex) { }
        }
        catch(IOException _ex)
        {
            setAnalysis("`c=ff0000`Error connecting to '" + s + "'");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        setInputStream(null);
        m_snapDate = new Date();
    }

    private boolean processCommand(String s)
    {
        int i = s.indexOf('=');
        if(i > 0)
        {
            String s1 = s.substring(0, i);
            String s2 = s.substring(i + 1);
            if(s1.equals("heading"))
            {
                m_heading = s2;
                repaint(rHeading, 100L);
            } else
            if(s1.equals("analysis"))
                setAnalysis(s2);
            else
            if(s1.equals("table"))
            {
                m_table = s2;
                repaint(rTable, 100L);
            } else
            if(s1.equals("map"))
            {
                m_map = s2;
                repaint(rMap, 100L);
            } else
            if(s1.equals("eof"))
                return true;
        }
        return false;
    }

    private Panel makeCommandPanel(boolean flag)
    {
        boolean flag1 = !"trace".equals(m_applet.getParam("access"));
        boolean flag2 = m_applet.isSimpleGUI();
        Panel panel = new Panel();
        panel.add(new Label("(Once you have finished using " + m_applet.appName() + ", click here to log out:"));
        panel.add(m_logout);
        panel.add(new Label(")"));
        Panel panel1 = new Panel();
        panel1.setBackground(Color.white);
        panel1.setLayout(new BorderLayout());
        GridBagLayout gridbaglayout = new GridBagLayout();
        GridBagConstraints gridbagconstraints = gridbaglayout.getConstraints(this);
        Panel panel2 = new Panel();
        panel2.setLayout(gridbaglayout);
        if(m_applet.isLogin())
        {
            gridbagconstraints.insets = new Insets(5, 1, 5, 0);
            gridbagconstraints.gridwidth = 99;
            gridbagconstraints.anchor = 17;
            panel2.add(jjj(gridbaglayout, panel, gridbagconstraints));
            gridbagconstraints.gridwidth = 1;
            gridbagconstraints.anchor = 10;
        }
        int i = 0;
        gridbagconstraints.gridy = 2;
        gridbagconstraints.insets = new Insets(3, 1, 3, 0);
        if(flag1)
        {
            gridbagconstraints.gridx = i++;
            panel2.add(jjj(gridbaglayout, new Label("Enter Host/URL:"), gridbagconstraints));
            gridbagconstraints.gridx = i++;
            gridbagconstraints.weightx = 1.0D;
            gridbagconstraints.fill = 2;
            panel2.add(jjj(gridbaglayout, iHost, gridbagconstraints));
            gridbagconstraints.fill = 0;
            gridbagconstraints.weightx = 0.0D;
        }
        iStop.disable();
        gridbagconstraints.gridx = i++;
        iStartTrace.setLabel(getStartButtonLabel());
        panel2.add(jjj(gridbaglayout, iStartTrace, gridbagconstraints));
        gridbagconstraints.gridx = i++;
        panel2.add(jjj(gridbaglayout, iStop, gridbagconstraints));
        gridbagconstraints.insets = new Insets(3, 10, 3, 0);
        if(!flag2)
        {
            iShowDetails.enable(false);
            gridbagconstraints.gridx = i++;
            panel2.add(jjj(gridbaglayout, iShowDetails, gridbagconstraints));
            gridbagconstraints.insets = new Insets(3, 1, 3, 0);
        }
        gridbagconstraints.gridx = i++;
        panel2.add(jjj(gridbaglayout, iSnap, gridbagconstraints));
        gridbagconstraints.insets = new Insets(3, 1, 3, 0);
        if("true".equals(m_applet.getParam("detach")))
        {
            gridbagconstraints.gridx = i++;
            panel2.add(jjj(gridbaglayout, iNewWindow, gridbagconstraints));
        }
        panel1.add("North", panel2);
        return panel1;
    }

    private void paint2(Graphics g)
    {
        g.setFont(new Font("Helvetica", 0, 12));
        Dimension dimension = size();
        Rectangle rectangle = g.getClipRect();
        boolean flag = rectangle.equals(new Rectangle(0, 0, dimension.width, dimension.height));
        int i = 0;
        int j = 0;
        int k = 0;
        boolean flag1 = false;
        doSetFont(g, 0, 100);
        int l = iTopPanel.size().height;
        j += translateY(g, l != 0 ? l : 41);
        g.setColor(Color.gray);
        g.drawLine(0, 0, dimension.width, 0);
        g.setColor(Color.white);
        g.drawLine(0, 1, dimension.width, 1);
        j += translateY(g, 2);
        g.clipRect(0, 0, dimension.width, dimension.height);
        i += translateX(g, -m_nScrollPixX);
        j += translateY(g, -m_nScrollPixY);
        j += translateY(g, 5);
        k = !flag && (flag1 || !rectangle.intersects(rHeading)) ? rHeading.height : paintHeading(g, m_heading);
        Rectangle rectangle1 = new Rectangle(i, j, dimension.width, k);
        flag1 |= !rectangle1.equals(rHeading);
        j += translateY(g, k);
        rHeading = rectangle1;
        j += translateY(g, 2);
        k = !flag && (flag1 || !rectangle.intersects(rAnalysis)) ? rAnalysis.height : paintAnalysis(g, m_analysis);
        rectangle1 = new Rectangle(i, j, dimension.width, k);
        flag1 |= !rectangle1.equals(rAnalysis);
        j += translateY(g, k);
        rAnalysis = rectangle1;
        j += translateY(g, 3);
        k = !flag && (flag1 || !rectangle.intersects(rTable)) ? rTable.height : paintTable(g, m_table, i);
        rectangle1 = new Rectangle(i, j, dimension.width, k);
        flag1 |= !rectangle1.equals(rTable);
        j += translateY(g, k);
        rTable = rectangle1;
        j += translateY(g, 3);
        k = !flag && (flag1 || !rectangle.intersects(rMap)) ? rMap.height : paintMap(g, m_map);
        rectangle1 = new Rectangle(i, j, dimension.width, k);
        flag1 |= !rectangle1.equals(rMap);
        j += translateY(g, k);
        rMap = rectangle1;
        j += translateY(g, -j);
        if(!flag && flag1)
            repaint(0, 0, dimension.width, dimension.height);
    }

    public boolean mouseMove(Event event, int i, int j)
    {
        map(i, j);
        String s = getLink(i, j);
        setCursor(hitEdge(i, j) < 0 ? ((int) (s == null && !isWhois(i, j) ? 0 : 12)) : 11);
        m_applet.showStatus(s == null ? rMap.y <= 0 || j <= rMap.y ? isWhois(i, j) ? "Click on row/column for whois information" : "" : mapHelp() : s);
        return true;
    }

    private int translateY(Graphics g, int i)
    {
        g.translate(0, i);
        return i;
    }

    private void stop()
    {
        if(m_in != null)
        {
            m_bStopping = true;
            try
            {
                m_in.close();
            }
            catch(Exception _ex) { }
            for(long l = System.currentTimeMillis(); m_in != null && System.currentTimeMillis() - l < 2000L;)
                try
                {
                    Thread.currentThread();
                    Thread.sleep(100L);
                }
                catch(Exception _ex) { }

            System.out.println("stop (in=" + m_in + ")");
            setInputStream(null);
            m_analysis += " `c=ff0000`WARNING: This trace was stopped by the user.";
        }
        repaint(100L);
    }

    private int findCol(String s)
    {
        s = s.toLowerCase();
        int i = 0;
        do
        {
            String s1 = m_colnames[i];
            if(s1 == null)
            {
                m_colnames[i] = s;
                m_colwidths[i] = 60;
                return i;
            }
            if(s.equals(s1))
                return i;
        } while(++i < 20);
        return 0;
    }

    private int netCol()
    {
        return findCol(m_applet.getParam("col-network", "Network"));
    }

    private int graphCol()
    {
        return findCol(m_applet.getParam("col-graph", "Graph"));
    }

    private int ipCol()
    {
        return findCol(m_applet.getParam("col-ip", "IP Address"));
    }

    private void setInputStream(InputStream inputstream)
    {
        m_in = inputstream;
        iStop.enable(m_in != null);
    }

    private String padString(String s, int i)
    {
        s = s == null ? "" : s;
        int j = Math.max(0, Math.min(i - s.length(), "                                                                   ".length()));
        return s + "                                                                   ".substring(0, j);
    }

    private String getLocalHostAddress()
    {
        String s = m_applet.getParam("ip");
        if(s == null)
            try
            {
                return InetAddress.getLocalHost().getHostAddress();
            }
            catch(Exception _ex) { }
        return s;
    }

    private String mapHelp()
    {
        String s = "";
        if(m_zoomIndex < m_maxZoomLevel)
            s += (s.length() <= 0 ? "" : ", ") + "ZOOM IN = left mouse click";
        if(m_zoomIndex > 0)
            s += (s.length() <= 0 ? "" : ", ") + "ZOOM OUT = right mouse click";
        if(canDragMap())
            s += (s.length() <= 0 ? "" : ", ") + "MOVE = click and drag";
        return s;
    }

    private void drawString(Graphics g, String s, int i, int j)
    {
        if(s != null)
        {
            String s1 = getStringOption(s, "link");
            int k = s.indexOf(";;?");
            s = k <= 0 ? s : s.substring(0, k);
            int l = 0;
            if(s.length() > 1 && s.charAt(0) == '?')
            {
                s = s.substring(1);
                Color color = g.getColor();
                g.setColor(cNA);
                l = s1 == null ? 0 : g.getFontMetrics().stringWidth(s);
                g.drawString(s, i, j);
                g.setColor(color);
            } else
            {
                l = s1 == null ? 0 : g.getFontMetrics().stringWidth(s);
                g.drawString(s, i, j);
            }
            if(l > 0)
            {
                Image image = getWebLinkImage();
                int i1 = image == null ? 0 : image.getHeight(this);
                if(i1 > 0)
                    g.drawImage(image, i + l, j - i1, this);
            }
        }
    }

    private void setAnalysis(String s)
    {
        if(s.indexOf("You have reached your") >= 0)
        {
            String s1 = m_applet.getParameter("limitreachedurl");
            if(s1 != null)
                try
                {
                    m_applet.getAppletContext().showDocument(new URL(s1), "_blank");
                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
        }
        m_analysis = s;
        repaint(rAnalysis, 0L);
    }

    private void drawGraph(Graphics g, String as[], int i, int j, int k, int l)
    {
        Point point = null;
        int i1 = j - 1;
        int j1 = 0;
        for(int k1 = 0; k1 < as.length; k1++)
        {
            StringTokenizer stringtokenizer = new StringTokenizer(as[k1], ",");
            try
            {
                int i2 = Integer.parseInt(stringtokenizer.nextToken());
                int j2 = Integer.parseInt(stringtokenizer.nextToken());
                int k2 = Integer.parseInt(stringtokenizer.nextToken());
                j1 = Integer.parseInt(stringtokenizer.nextToken());
                if(i2 >= 0)
                {
                    int l2 = (j2 * i1) / j1;
                    int i3 = (k2 * i1) / j1;
                    int j3 = k + l * k1 + l / 2;
                    g.setColor(Color.gray);
                    g.fillRect(l2, j3, (i3 - l2) + 1, 2);
                    g.fillRect(l2 - 1, j3 - 1, 1, 4);
                    g.fillRect(i3 + 1, j3 - 1, 1, 4);
                    int k3 = (i2 * i1) / j1;
                    if(point != null)
                    {
                        g.setColor(Color.blue);
                        g.drawLine(point.x, point.y, k3, j3);
                    }
                    point = new Point(k3, j3);
                }
            }
            catch(Exception _ex) { }
        }

        if(j1 > 0)
        {
            doSetFont(g, 0, 75);
            FontMetrics fontmetrics = g.getFontMetrics();
            int l1 = k + l + fontmetrics.getAscent();
            g.drawString("0", 0, l1);
            String s = "" + j1;
            g.drawString(s, j - fontmetrics.stringWidth(s), l1);
        }
    }

    private int translateX(Graphics g, int i)
    {
        g.translate(i, 0);
        return i;
    }

    public void go(String s)
    {
        if(s != null)
        {
            iHost.setText(s);
            setHost(iHost.getText().trim());
            return;
        } else
        {
            iHost.requestFocus();
            return;
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    private void autoResizeCols(String s)
    {
        int i = 0;
        for(StringTokenizer stringtokenizer = new StringTokenizer(s, "\t"); stringtokenizer.hasMoreTokens();)
            i += getColWidth(stringtokenizer.nextToken());

        StringTokenizer stringtokenizer1 = new StringTokenizer(s, "\t");
        double d = (double)(595 - 5 * stringtokenizer1.countTokens()) / (double)Math.max(100, i);
        String s1;
        for(; stringtokenizer1.hasMoreTokens(); setColWidth(s1, (int)(d * (double)getColWidth(s1))))
            s1 = stringtokenizer1.nextToken();

    }

    private int paintAnalysis(Graphics g, String s)
    {
        byte byte0 = 2;
        g.translate(byte0, byte0);
        int i = m_tw;
        int j = paintAnalysisText(g, s, false, i);
        paintPanel(g, "Analysis", 0, byte0, Math.max(i + 10, 610), j + 20);
        g.translate(5, 15);
        paintAnalysisText(g, s, true, i);
        g.translate(-5, -15);
        g.translate(-byte0, -byte0);
        return j + byte0 * 2 + 20;
    }

    private Image getWebLinkImage()
    {
        if(m_weblink == null)
        {
            m_weblink = m_applet.getImage(m_applet.getCodeBase(), "/weblink.gif");
            m_weblink.getWidth(null);
        }
        return m_weblink;
    }

    private static int mid(int i, int j, float f)
    {
        return (int)(((float)i + (float)j * f) - (float)i * f);
    }

    private void paintPanel(Graphics g, String s, int i, int j, int k, int l)
    {
        gradientFill(g, 1 + i, 1 + j, k - 3, 13, new Color(192, 208, 240), new Color(247, 247, 247));
        g.setColor(new Color(21, 50, 144));
        Font font = g.getFont();
        g.setFont(new Font("Helvetica", 1, 11));
        g.drawString(s, 13 + i, 11 + j);
        gradientFill(g, 1 + i, 14 + j, k - 3, l - 15, new Color(247, 247, 247), new Color(226, 234, 243));
        g.setColor(new Color(133, 150, 202));
        g.drawRoundRect(i, j, k - 1, l - 1, 8, 8);
        g.setFont(font);
    }

    private static void gradientFill(Graphics g, int i, int j, int k, int l, Color color, Color color1)
    {
        for(int i1 = 0; i1 < l; i1++)
        {
            float f = (float)i1 / (float)l;
            g.setColor(new Color(mid(color.getRed(), color1.getRed(), f), mid(color.getGreen(), color1.getGreen(), f), mid(color.getBlue(), color1.getBlue(), f)));
            g.drawLine(i, i1 + j, i + k, i1 + j);
        }

    }

    private void doNewWindow()
    {
        String s = m_applet.getParam("from");
        String s1 = m_applet.appName() + (s == null ? "" : " from " + s);
        new VisualRouteFrame(this, s1, m_applet);
    }

    private void doNeedLogin()
    {
        boolean flag = m_applet.isLogin() && !m_bLoggedOn;
        iCommandPanel.setVisible(!flag);
        iLoginPanel.setVisible(flag);
        validate();
        repaint();
    }

    private void reset(VisualRoutePanel visualroutepanel, boolean flag)
    {
        String s = m_applet.getParam("col-hop", "Hop");
        String s1 = m_applet.getParam("col-err", "%Loss");
        String s2 = m_applet.getParam("col-ip", "IP Address");
        String s3 = m_applet.getParam("col-name", "Node Name");
        String s4 = m_applet.getParam("col-loc", "Location");
        String s5 = m_applet.getParam("col-tz", "Tzone");
        String s6 = m_applet.getParam("col-ms", "ms");
        String s7 = m_applet.getParam("col-graph", "Graph");
        String s8 = m_applet.getParam("col-network", "Network");
        String s9 = s + '\t' + s1 + '\t' + s2 + '\t' + s3 + '\t' + s4 + '\t' + s5 + '\t' + s6 + '\t' + s7 + '\t' + s8;
        String s10 = m_applet.getParam("cols", s9);
        boolean flag1 = visualroutepanel != null;
        boolean flag2 = m_applet.isSimpleGUI();
        boolean flag3 = !"trace".equals(m_applet.getParam("access"));
        String s11 = m_applet.appNameVerBuild();
        String s12 = "Copyright (c) 1996-" + VisualRouteApplet.COPYRIGHTDATE + " Visualware, Inc";
        String s13 = "All rights reserved.";
        String s14 = flag2 ? "Instructions" : m_applet.getParam("m.title", m_applet.appName() + " report");
        String s15 = flag2 ? flag3 ? "Enter the IP address or host name that you would like to trace, then click 'Start trace'. If you do not enter an IP address or host name, the service will use your IP address.\n`c=808080`" + s11 + " " + s12 : "Click the '" + getStartButtonLabel() + "' button above to test connectivity from this server to your computer.\n" + "`c=808080`" + s11 + " " + s12 : s11 + "\n" + s12 + "\n" + s13 + "\nFor the full-featured Personal Edition of VisualRoute, visit http://www.visualware.com";
        m_heading = flag1 ? visualroutepanel.m_heading : s14;
        m_analysis = flag1 ? visualroutepanel.m_analysis : s15;
        m_table = flag1 ? visualroutepanel.m_table : s10;
        m_map = flag1 ? visualroutepanel.m_map : "";
        if(flag1)
        {
            setLoggedOn(visualroutepanel.isLoggedOn());
            setLogonMessage(visualroutepanel.getLogonMessage());
        }
        m_bStopping = false;
        repaint(100L);
        if(flag)
        {
            setColWidth(s, 23);
            setColWidth(s1, 38);
            setColWidth(s2, 80);
            setColWidth(s3, 80);
            setColWidth(s4, 100);
            setColWidth(s5, 34);
            setColWidth(s6, 30);
            setColWidth(s7, 45);
            setColWidth(s8, 120);
            autoResizeCols(s10);
        }
    }

    public VisualRoutePanel(VisualRouteApplet visualrouteapplet, VisualRoutePanel visualroutepanel)
    {
        iHost = new TextField(30);
        iStop = new Button("Stop");
        iStartTrace = new Button("Start Trace");
        iSnap = new Button("Snap...");
        iNewWindow = new Button("*");
        iShowDetails = new Checkbox("Show Details", null, true);
        m_host = "host";
        m_prot = null;
        m_port = -1;
        rHeading = new Rectangle();
        rAnalysis = new Rectangle();
        rTable = new Rectangle();
        rMap = new Rectangle();
        m_colnames = new String[20];
        m_colpos = new int[20];
        m_colwidths = new int[20];
        m_colreal = new int[20];
        m_tw = 595;
        m_zoomIndex = 0;
        m_mapCoverage = new double[4];
        m_ptMap = new Point(0, 0);
        m_snapTable = new String[0][0];
        m_snapAnalysis = "";
        _col = -1;
        m_bLoggedOn = false;
        m_username = new TextField(25);
        m_password = new TextField(25);
        m_login = new Button("Log In");
        m_logout = new Button("Log Out");
        m_applet = visualrouteapplet;
        m_maxZoomLevel = m_applet.getMaxZoomLevel();
        iShowDetails.setState(visualrouteapplet.autoShowDetails());
        String s = m_applet.getParameter("url");
        String s1 = Register.isValidatedURL(s) ? s : m_applet.getDB().toString();
        if(!VisualRouteApplet.ISREGISTER || Register.isValidatedURL(s1) && Register.isTimeOK(s1))
            iCommandPanel = makeCommandPanel(visualroutepanel == null);
        else
            iCommandPanel = makeRegisterPanel(s1);
        iLoginPanel = makeLoginPanel();
        setBackground(Color.white);
        setForeground(Color.black);
        setLayout(new BorderLayout());
        iTopPanel = new Panel();
        GridBagLayout gridbaglayout = new GridBagLayout();
        iTopPanel.setLayout(gridbaglayout);
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.weightx = 1.0D;
        gridbagconstraints.fill = 1;
        iTopPanel.add(jjj(gridbaglayout, iCommandPanel, gridbagconstraints));
        gridbagconstraints.gridy++;
        iTopPanel.add(jjj(gridbaglayout, iLoginPanel, gridbagconstraints));
        doNeedLogin();
        add("North", iTopPanel);
        reset(visualroutepanel, true);
        getMapImage(0.0D, 0.0D, true);
        repaint();
        m_bFirstPaint = true;
    }

    private void repaint(Rectangle rectangle, long l)
    {
        repaint(l, rectangle.x, rectangle.y, Math.max(1, rectangle.width), Math.max(0, rectangle.height) + 1);
    }

    public void paint(Graphics g)
    {
        try
        {
            if(m_bFirstPaint)
            {
                m_image.getWidth(null);
                m_ptMap.x = 3;
                m_bFirstPaint = false;
            }
            Dimension dimension = size();
            Rectangle rectangle = g.getClipRect().intersection(new Rectangle(0, 0, dimension.width, dimension.height));
            Image image = createImage(rectangle.width, rectangle.height);
            Graphics g1 = image.getGraphics();
            g1.translate(-rectangle.x, -rectangle.y);
            g1.clipRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            g1.setColor(getBackground());
            g1.fillRect(0, 0, dimension.width, dimension.height);
            g1.setColor(getForeground());
            paint2(g1);
            g.drawImage(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
            g1.dispose();
            m_nNumSmoothPix += rectangle.width * rectangle.height;
            if(m_nNumSmoothPix > 0x1e8480)
            {
                m_nNumSmoothPix = 0;
                System.gc();
                return;
            }
        }
        catch(Exception exception)
        {
            g.setColor(Color.black);
            g.drawString("Exception: " + exception, 0, 20);
            exception.printStackTrace();
        }
    }

    private int paintTable(Graphics g, String s, int i)
    {
        byte byte0 = 2;
        size();
        FontMetrics fontmetrics = g.getFontMetrics();
        int j = fontmetrics.getHeight();
        StringTokenizer stringtokenizer = new StringTokenizer(s, "\007");
        int k = stringtokenizer.countTokens();
        int l = 20;
        boolean flag = iShowDetails.getState();
        int i1 = flag ? 0 : Math.max(0, k - 4);
        int j1 = k - i1;
        String as[][] = new String[l][j1];
        for(int k1 = 0; k1 < k; k1++)
        {
            String s1 = stringtokenizer.nextToken();
            if(k1 == 0 || k1 > i1)
            {
                int j2 = k1 != 0 ? k1 - i1 : 0;
                StringTokenizer stringtokenizer1 = new StringTokenizer(s1, "\t");
                l = Math.min(l, stringtokenizer1.countTokens());
                for(int i3 = 0; i3 < l; i3++)
                {
                    String s3 = stringtokenizer1.nextToken();
                    as[i3][j2] = s3;
                }

            }
        }

        int l1 = 0;
        for(int i2 = 0; i2 < l; i2++)
            l1 += getColWidth(as[i2][0]) + 5;

        setTableWidth(l1);
        byte byte1 = 4;
        m_rowHeight = j;
        int k2 = Math.max(2, j1) * m_rowHeight + byte1;
        paintPanel(g, "Route Table", byte0, 0, Math.max(l1 + 10, 610), k2 + 20);
        g.translate(5, 15);
        gradientFill(g, 2, 0, l1, j + byte1, new Color(255, 255, 231), new Color(255, 241, 148));
        g.setColor(new Color(173, 199, 239));
        g.drawRect(2, 0, l1, k2);
        g.drawLine(2, j + byte1, l1, j + byte1);
        l1 = 0;
        for(int l2 = 0; l2 < l; l2++)
        {
            l1 += getColWidth(as[l2][0]) + 5;
            g.drawLine(2 + l1, 0, 2 + l1, k2);
            int j3 = findCol(as[l2][0]);
            m_colpos[j3] = i + 2 + l1;
            m_colreal[j3] = l2;
        }

        String s2 = m_applet.getParam("col-graph", "Graph");
        int k3 = 5;
        for(int l3 = 0; l3 < l; l3++)
        {
            int i4 = getColWidth(as[l3][0]);
            if(i4 > 0)
            {
                int j4 = (j + byte1) - fontmetrics.getDescent();
                Graphics g1 = g.create(k3, 0, i4, k2);
                g1.setColor(Color.black);
                if(as[l3][0].equals(s2))
                {
                    drawString(g1, as[l3][0], 0, j4 - byte1 / 2);
                    drawGraph(g1, as[l3], l3, i4, byte1, j);
                } else
                {
                    for(int k4 = 0; k4 < j1; k4++)
                    {
                        String s4 = as[l3][k4];
                        drawString(g1, s4, 0, j4 - (k4 != 0 ? 0 : byte1 / 2));
                        j4 += j;
                    }

                }
                g1.dispose();
            }
            k3 += i4 + 5;
        }

        g.translate(-5, -15);
        m_snapTable = as;
        m_snapTableHeight = j;
        return k2 + 20;
    }

    public void setLoggedOn(boolean flag)
    {
        m_bLoggedOn = flag;
        if(!flag)
            m_status.setText("Please enter your username and password to access this " + m_applet.appName() + " server");
        doNeedLogin();
    }

    public void setHost(String s)
    {
        stop();
        int i = s.indexOf("://");
        m_prot = null;
        m_port = -1;
        if(i > 0)
        {
            m_prot = s.substring(0, i);
            s = s.substring(i + 3);
        }
        int j = s.indexOf(":");
        if(j > 0)
            try
            {
                m_port = Integer.parseInt(s.substring(j + 1));
                s = s.substring(0, j);
            }
            catch(Exception _ex) { }
        m_host = s;
        reset(null, false);
        iShowDetails.enable(m_host != null);
        if(m_host != null)
        {
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    private void zoomOut(int i, int j)
    {
        if(m_zoomIndex > 0)
        {
            double ad[] = map(i, j);
            if(ad != null)
            {
                m_zoomIndex--;
                getMapImage(ad[0], ad[1], false);
            }
        }
    }

    private boolean canPut(Vector vector, Rectangle rectangle)
    {
        int i = vector.size();
        for(int j = 0; j < i; j++)
            if(((Rectangle)vector.elementAt(j)).intersects(rectangle))
                return false;

        vector.addElement(rectangle);
        return true;
    }

    private String makeString(char c, int i)
    {
        char ac[] = new char[i];
        for(int j = 0; j < i; j++)
            ac[j] = c;

        return new String(ac);
    }

    private int paintHeading(Graphics g, String s)
    {
        Font font = doSetFont(g, 0, 150);
        FontMetrics fontmetrics = g.getFontMetrics();
        int i = m_tw / 2;
        int j = fontmetrics.getHeight();
        g.setColor(Color.black);
        g.drawString(s, Math.max(2, i - fontmetrics.stringWidth(s) / 2), (j - fontmetrics.getHeight()) + fontmetrics.getAscent());
        g.setFont(font);
        return j;
    }

    public void setScrollPos(int i, int j)
    {
        int k = m_nScrollPixX;
        int l = m_nScrollPixY;
        m_nScrollPixX = i * 10;
        m_nScrollPixY = j * 20;
        if(k != i || l != j)
            repaint();
    }

    private void doDomainWhois(String s)
    {
        AppletWhois.whois(m_applet, s, AppletWhois.DOMAIN);
    }

    private void doNetworkWhois(String s)
    {
        AppletWhois.whois(m_applet, s, AppletWhois.NETWORK);
    }

    private String makeTextGraph(String s)
    {
        try
        {
            StringTokenizer stringtokenizer = new StringTokenizer(s, ",");
            int i = Integer.parseInt(stringtokenizer.nextToken());
            int j = Integer.parseInt(stringtokenizer.nextToken());
            int k = Integer.parseInt(stringtokenizer.nextToken());
            int l = Integer.parseInt(stringtokenizer.nextToken());
            char ac[] = new char[10];
            int i1 = ac.length - 1;
            for(int j1 = 0; j1 < ac.length; j1++)
                ac[j1] = ' ';

            int k1 = (j * i1) / l;
            int l1 = (k * i1) / l;
            for(int i2 = k1; i2 <= l1; i2++)
                ac[i2] = '-';

            ac[(i * i1) / l] = 'x';
            return new String(ac);
        }
        catch(Exception _ex)
        {
            return s;
        }
    }

    private String getLink(int i, int j)
    {
        try
        {
            Point point = hitTest(i, j);
            if(point.y > 0)
                return getStringOption(m_snapTable[m_colreal[point.x]][point.y], "link");
        }
        catch(Exception _ex) { }
        return null;
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
        _bDrag |= Math.abs(i - _down.x) > 2 || Math.abs(j - _down.y) > 2;
        if(_col >= 0)
        {
            setColWidth(_col, _orgcolsize + (i - _down.x));
            repaint(rTable, 0L);
        } else
        if(_down.y > rMap.y && _bDrag && canDragMap())
        {
            int k = _orgmap.x + (i - _down.x);
            int l = _orgmap.y + (j - _down.y);
            l = rMap.y + l <= rTable.y + rTable.height ? rMap.y - (rTable.y + rTable.height) : l;
            m_ptMap = new Point(k, l);
            repaint(rMap, 0L);
        }
        return true;
    }

    private void drawGeoPath(Graphics g, String s)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "\t");
        Point point = null;
        int i = 0;
        int j = stringtokenizer.countTokens();
        Point apoint[] = new Point[j];
        String as[] = new String[j];
        g.setColor(Color.blue);
        while(stringtokenizer.hasMoreTokens()) 
        {
            StringTokenizer stringtokenizer1 = new StringTokenizer(stringtokenizer.nextToken(), ",");
            try
            {
                boolean flag = Integer.parseInt(stringtokenizer1.nextToken()) > 0;
                double d = Double.valueOf(stringtokenizer1.nextToken()).doubleValue();
                double d1 = Double.valueOf(stringtokenizer1.nextToken()).doubleValue();
                String s2 = stringtokenizer1.nextToken();
                Point point1 = map(d, d1);
                if(point1 != null)
                {
                    apoint[i] = point1;
                    as[i] = s2;
                    i++;
                    g.fillRect(point1.x - 1, point1.y - 1, 3, 3);
                    if(point != null)
                    {
                        if(!flag)
                            g.setColor(cNA);
                        g.drawLine(point.x, point.y, point1.x, point1.y);
                        g.setColor(Color.blue);
                    }
                    point = point1;
                }
            }
            catch(Exception _ex) { }
        }
        if(i > 0)
        {
            g.setColor(Color.black);
            Vector vector = new Vector();
            String s1 = "";
            for(int k = 0; k < i; k++)
            {
                int l = k != 0 ? k != 1 ? k - 1 : i - 1 : 0;
                if(!as[l].equals(s1))
                {
                    putCityName(g, vector, apoint[l], as[l]);
                    s1 = as[l];
                }
            }

        }
    }

    private int paintAnalysisText(Graphics g, String s, boolean flag, int i)
    {
        byte byte0 = 2;
        int j = i;
        int k = 0;
        FontMetrics fontmetrics = g.getFontMetrics();
        int l = fontmetrics.getHeight();
        int i1 = fontmetrics.getDescent();
        String s1 = "";
        StringTokenizer stringtokenizer = new StringTokenizer(s, " `\n", true);
        g.setColor(Color.black);
        while(stringtokenizer.hasMoreTokens()) 
        {
            String s2 = stringtokenizer.nextToken();
            if(s2.startsWith("c="))
            {
                if(flag)
                    try
                    {
                        g.setColor(new Color(Integer.parseInt(s2.substring(2), 16)));
                    }
                    catch(Exception _ex) { }
            } else
            if(!s2.equals("`"))
            {
                int j1 = fontmetrics.stringWidth(s2);
                boolean flag1 = s2.equals("\n");
                boolean flag2 = s2.equals(" ");
                if(flag1 || j > 0 && j + j1 + 2 * byte0 >= i)
                {
                    j = 0;
                    k += l;
                    if(flag)
                        s1 += "\n";
                }
                if(flag)
                    s1 += flag1 ? "" : s2;
                if(!flag2 && !flag1 && flag)
                    g.drawString(s2, byte0 + j, k - i1);
                j += !flag1 && (j != 0 || !flag2) ? j1 : 0;
            }
        }
        k = Math.max(2 * l, k);
        if(flag)
            m_snapAnalysis = s1;
        return k;
    }

    private static final String g_spc = "                                                                   ";
    private static final int MAXCOLS = 20;
    private static final int MINPANELWIDTH = 610;
    private static final Color cY = new Color(255, 255, 204);
    private static final Color cHeader = new Color(153, 204, 204);
    private static final Color cNA = new Color(153, 102, 204);
    private VisualRouteApplet m_applet;
    private TextField iHost;
    private Button iStop;
    private Button iStartTrace;
    private Button iSnap;
    private Button iNewWindow;
    private Checkbox iShowDetails;
    private Panel iLoginPanel;
    private Panel iCommandPanel;
    private Panel iTopPanel;
    private String m_host;
    private String m_prot;
    private int m_port;
    private Image m_weblink;
    private boolean m_bStopping;
    private String m_heading;
    private String m_analysis;
    private String m_table;
    private String m_map;
    private Rectangle rHeading;
    private Rectangle rAnalysis;
    private Rectangle rTable;
    private Rectangle rMap;
    private String m_colnames[];
    private int m_colpos[];
    private int m_colwidths[];
    private int m_colreal[];
    private int m_tw;
    private int m_rowHeight;
    private int m_nNumSmoothPix;
    private int m_maxZoomLevel;
    private int m_zoomIndex;
    private Image m_image;
    private double m_mapCoverage[];
    private Point m_ptMap;
    private Date m_snapDate;
    private String m_snapTable[][];
    private String m_snapAnalysis;
    private int m_snapTableHeight;
    private int m_nScrollPixX;
    private int m_nScrollPixY;
    private InputStream m_in;
    private Event _down;
    private int _col;
    private int _orgcolsize;
    private Point _orgmap;
    private boolean _bDrag;
    private boolean m_bLoggedOn;
    private TextField m_username;
    private TextField m_password;
    private Label m_status;
    private Button m_login;
    private Button m_logout;
    private boolean m_bFirstPaint;

}
