// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCString.java

package jclass.util;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;

// Referenced classes of package jclass.util:
//            JCVector, JCImage, JCStringTokenizer, JCUtilConverter

public class JCString extends JCVector
{

    public JCString()
    {
    }

    public JCString(int i)
    {
        super(i);
    }

    public JCString(String s)
    {
        super(1);
        addString(s);
    }

    public JCString(String s, Image image, int i)
    {
        this(5);
        if(s == null)
        {
            add(image);
            return;
        }
        if(image == null)
        {
            add(s);
            return;
        }
        switch(i)
        {
        case 0: // '\0'
            add(s);
            add(MIDDLE);
            add(HORIZ_SPACE);
            add(image);
            break;

        case 2: // '\002'
            add(s);
            add(NEWLINE);
            add(VERT_SPACE);
            add(new Integer(5));
            add(image);
            break;

        case 3: // '\003'
            add(image);
            add(NEWLINE);
            add(VERT_SPACE);
            add(new Integer(5));
            add(s);
            break;

        case 1: // '\001'
        default:
            add(image);
            add(MIDDLE);
            add(HORIZ_SPACE);
            add(s);
            break;
        }
    }

    public void add(int i)
    {
        addElement(new Integer(i));
    }

    public void add(int i, Object obj)
    {
        add(i);
        add(obj);
    }

    public boolean add(Object obj)
    {
        if(obj == null)
            return false;
        if(obj instanceof String)
            addString((String)obj);
        else
            addElement(obj);
        return true;
    }

    public boolean addImage(Applet applet, String s)
    {
        return addImage(applet, applet.getDocumentBase(), s);
    }

    public boolean addImage(Applet applet, String s, String s1)
    {
        try
        {
            addImage(applet, new URL(s), s1);
        }
        catch(Exception _ex)
        {
            return false;
        }
        return true;
    }

    private boolean addImage(Applet applet, URL url1, String s)
    {
        Image image = null;
        if((image = getCachedImage(url1.toString() + s)) == null)
        {
            MediaTracker mediatracker = new MediaTracker(applet);
            image = applet.getImage(url1, s);
            mediatracker.addImage(image, 0);
            try
            {
                mediatracker.waitForAll();
            }
            catch(InterruptedException _ex)
            {
                return false;
            }
            putCachedImage(url1.toString() + s, image);
        }
        add(image);
        return true;
    }

    private void addString(String s)
    {
        int i = 0;
        for(int j = 0; (j = s.indexOf("\n", i)) != -1;)
        {
            addElement(s.substring(i, j));
            addElement(NEWLINE);
            i = j + 1;
        }

        addElement(s.substring(i));
    }

    public void draw(Component component, Graphics g, Rectangle rectangle, int i)
    {
        int j = 0;
        int l = rectangle.y;
        url = null;
        url_list = url_rect_list = null;
        Font font = g.getFont();
        init();
        int k;
        while((k = getLineSize(component, font, j, size)) != -1) 
        {
            int i1 = getVertSpace(j, k);
            size.height -= i1;
            l += i1;
            draw_rect.setBounds(rectangle.x, l, size.width, size.height);
            if(i == 1)
                draw_rect.x += (rectangle.width - size.width) / 2;
            else
            if(i == 2)
                draw_rect.x += rectangle.width - size.width;
            drawLine(component, g, font, draw_rect, j, k);
            j = k;
            l += size.height;
        }
    }

    void drawLine(Component component, Graphics g, Font font, Rectangle rectangle, int i, int j)
    {
        Integer integer = BOTTOM;
        FontMetrics fontmetrics = g.getFontMetrics();
        int k = rectangle.x;
        int l = rectangle.y;
        for(int i1 = i; i1 < j && i1 < size(); i1++)
        {
            if(super.elementData[i1] instanceof String)
            {
                int j1 = fontmetrics.getHeight();
                int k1 = j1 - fontmetrics.getAscent();
                if(integer.equals(BOTTOM))
                    l = (rectangle.y + rectangle.height) - k1;
                else
                if(integer.equals(MIDDLE))
                    l = (rectangle.y + (rectangle.height - j1) / 2 + j1) - k1;
                else
                if(integer.equals(TOP))
                    l = (rectangle.y + j1) - k1;
                g.drawString((String)super.elementData[i1], k, l);
                if(url != null)
                    startURL(k, (l - j1) + k1);
                int l1 = stringWidth(fontmetrics, g.getFont(), (String)super.elementData[i1]);
                if(underline)
                    g.drawLine(k, l + 1, k + l1, l + 1);
                if(strikethrough)
                    g.drawLine(k, (l - j1 / 2) + 3, k + l1, (l - j1 / 2) + 3);
                k += l1;
                if(url != null)
                    endURL(g, k, l + k1);
                continue;
            }
            if((super.elementData[i1] instanceof Image) || (super.elementData[i1] instanceof JCImage))
            {
                Object obj = super.elementData[i1];
                Image image = (obj instanceof Image) ? (Image)obj : ((JCImage)obj).image;
                if(url != null)
                    startURL(k, rectangle.y);
                g.drawImage(image, k, rectangle.y, null);
                k += image.getWidth(null);
                if(url != null)
                    endURL(g, k, rectangle.y + rectangle.height);
                continue;
            }
            if(super.elementData[i1] instanceof Font)
            {
                g.setFont((Font)super.elementData[i1]);
                fontmetrics = g.getFontMetrics();
                continue;
            }
            if(super.elementData[i1].equals(DEFAULT_FONT))
            {
                g.setFont(font);
                fontmetrics = g.getFontMetrics();
                continue;
            }
            if(super.elementData[i1].equals(DEFAULT_COLOR))
            {
                g.setColor(component.getForeground());
                continue;
            }
            if(super.elementData[i1].equals(HREF))
            {
                underline = true;
                url = (String)super.elementData[++i1];
                continue;
            }
            if(super.elementData[i1].equals(UNDERLINE))
            {
                underline = true;
                continue;
            }
            if(super.elementData[i1].equals(END_UNDERLINE))
            {
                underline = false;
                continue;
            }
            if(super.elementData[i1].equals(END_HREF))
            {
                underline = false;
                url = null;
                continue;
            }
            if(super.elementData[i1].equals(STRIKETHROUGH))
            {
                strikethrough = true;
                continue;
            }
            if(super.elementData[i1].equals(END_STRIKETHROUGH))
            {
                strikethrough = false;
                continue;
            }
            if(super.elementData[i1].equals(TOP))
            {
                integer = TOP;
                continue;
            }
            if(super.elementData[i1].equals(MIDDLE))
            {
                integer = MIDDLE;
                continue;
            }
            if(super.elementData[i1].equals(BOTTOM))
            {
                integer = BOTTOM;
                continue;
            }
            if(super.elementData[i1].equals(HORIZ_SPACE))
            {
                if(i1 < size() - 1 && isValue(super.elementData[i1 + 1]))
                    k += ((Integer)super.elementData[++i1]).intValue();
                else
                    k += 10;
                continue;
            }
            if(super.elementData[i1].equals(RESET))
            {
                if(url == null)
                    underline = false;
                strikethrough = false;
                integer = BOTTOM;
                g.setColor(component.getForeground());
                g.setFont(font);
                fontmetrics = g.getFontMetrics();
                continue;
            }
            if(super.elementData[i1] instanceof Color)
            {
                g.setColor((Color)super.elementData[i1]);
                continue;
            }
            if(super.elementData[i1].equals(NEWLINE))
                break;
        }

    }

    private void endURL(Graphics g, int i, int j)
    {
        url_list.addElement(url);
        url_rect.width = i - url_rect.x;
        url_rect.height = j - url_rect.y;
        url_rect.translate(x_offset, y_offset);
        url_rect_list.addElement(url_rect);
    }

    public static Applet getApplet(Component component)
    {
        if(component == null)
            return null;
        java.awt.Container container = component.getParent();
        java.awt.Container container1;
        for(container1 = component.getParent(); container1 != null && !(container1 instanceof Applet);)
        {
            container1 = container;
            container = container != null ? container.getParent() : null;
        }

        return (Applet)container1;
    }

    private static Image getCachedImage(String s)
    {
        if(!is_img_caching)
            return null;
        else
            return (Image)img_cache.get(s);
    }

    public int getHeight(Component component, Font font)
    {
        return getSize(component, font).height;
    }

    public Image getImage()
    {
        for(int i = 0; i < size(); i++)
        {
            if(super.elementData[i] instanceof Image)
                return (Image)super.elementData[i];
            if(super.elementData[i] instanceof JCImage)
                return ((JCImage)super.elementData[i]).image;
        }

        return null;
    }

    public static boolean getIsImageCaching()
    {
        return is_img_caching;
    }

    public int getLineSize(Component component, Font font, int i, Dimension dimension)
    {
        if(i >= size())
            return -1;
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        Graphics g = component.getGraphics();
        if(g == null)
            return -1;
        int j;
        for(j = i; j >= 0; j--)
        {
            if(super.elementData[j] instanceof Font)
            {
                g.setFont((Font)super.elementData[j]);
                break;
            }
            if(!super.elementData[j].equals(DEFAULT_FONT))
                continue;
            g.setFont(font);
            break;
        }

        if(j < 0 && font != null)
            g.setFont(font);
        FontMetrics fontmetrics = g.getFontMetrics();
        for(j = i; j < size(); j++)
        {
            if(super.elementData[j] instanceof String)
            {
                k1 = Math.max(k1, fontmetrics.getHeight());
                j1 += stringWidth(fontmetrics, g.getFont(), (String)super.elementData[j]);
                continue;
            }
            if(super.elementData[j] instanceof Font)
            {
                g.setFont((Font)super.elementData[j]);
                fontmetrics = g.getFontMetrics();
                continue;
            }
            if(super.elementData[j] instanceof Image)
            {
                Image image = (Image)super.elementData[j];
                k1 = Math.max(k1, image.getHeight(null));
                j1 += image.getWidth(null);
                continue;
            }
            if(super.elementData[j] instanceof JCImage)
            {
                Image image1 = ((JCImage)super.elementData[j]).image;
                k1 = Math.max(k1, image1.getHeight(null));
                j1 += image1.getWidth(null);
                continue;
            }
            if(super.elementData[j].equals(HREF))
            {
                j++;
                continue;
            }
            if(super.elementData[j].equals(DEFAULT_FONT))
            {
                g.setFont(font);
                fontmetrics = g.getFontMetrics();
                continue;
            }
            if(super.elementData[j].equals(HORIZ_SPACE))
            {
                if(j < size() - 1 && isValue(super.elementData[j + 1]))
                    j1 += ((Integer)super.elementData[++j]).intValue();
                else
                    j1 += 10;
                continue;
            }
            if(super.elementData[j].equals(VERT_SPACE))
            {
                if(j < size() - 1 && isValue(super.elementData[j + 1]))
                    k = ((Integer)super.elementData[++j]).intValue();
                else
                    k = 10;
                continue;
            }
            if(!super.elementData[j].equals(NEWLINE))
                continue;
            l += k1;
            i1 = Math.max(i1, j1);
            k1 = j1 = 0;
            break;
        }

        dimension.width = Math.max(i1, j1);
        dimension.height = l + k1 + k;
        return j + 1;
    }

    public Dimension getSize(Component component, Font font)
    {
        Dimension dimension = new Dimension();
        int i = 0;
        int j = 0;
        int k = 0;
        if(font == null)
            font = component.getFont();
        init();
        while((i = getLineSize(component, font, i, dimension)) != -1) 
        {
            j = Math.max(j, dimension.width);
            k += dimension.height;
        }
        dimension.width = j;
        dimension.height = k;
        return dimension;
    }

    public String getString()
    {
        String s = new String();
        for(int i = 0; i < size(); i++)
            if(super.elementData[i].equals(HREF))
                i++;
            else
            if(super.elementData[i] instanceof String)
                s = s.concat((String)super.elementData[i]);
            else
            if(super.elementData[i].equals(NEWLINE))
                s = s.concat("\n");

        return s;
    }

    public String getURL(int i, int j)
    {
        if(url_rect_list != null)
        {
            for(int k = 0; k < url_rect_list.size(); k++)
                if(((Rectangle)url_rect_list.elementAt(k)).contains(i, j))
                    return (String)url_list.elementAt(k);

        }
        return null;
    }

    public static String getURL(Applet applet, Object obj, int i, int j)
    {
        String s = null;
        if(applet != null)
            try
            {
                if(applet.getAppletContext() == null)
                    applet = null;
            }
            catch(Exception _ex)
            {
                applet = null;
            }
        if(obj instanceof JCString)
        {
            s = ((JCString)obj).getURL(i, j);
            if(s != null && applet != null)
            {
                int k = Math.max(s.indexOf("TARGET="), s.indexOf("target="));
                if(k == -1)
                    applet.showStatus(s);
                else
                    applet.showStatus(s.substring(0, k));
            }
            has_url = true;
        }
        if(applet != null && has_url && s == null)
            applet.showStatus(null);
        if(s == null)
            has_url = false;
        return s;
    }

    int getVertSpace(int i, int j)
    {
        for(int k = i; k < j && k < size(); k++)
            if(super.elementData[k].equals(VERT_SPACE))
                if(k < size() - 1 && isValue(super.elementData[k + 1]))
                    return ((Integer)super.elementData[++k]).intValue();
                else
                    return 10;

        return 0;
    }

    public int getWidth(Component component, Font font)
    {
        return getSize(component, font).width;
    }

    private void init()
    {
        underline = strikethrough = false;
    }

    private boolean isValue(Object obj)
    {
        return (obj instanceof Integer) && ((Integer)obj).intValue() >= 0;
    }

    public static JCString parse(Component component, String s)
    {
        if(s == null)
            return null;
        JCString jcstring = new JCString();
        for(JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s); jcstringtokenizer.hasMoreTokens();)
        {
            String s1 = jcstringtokenizer.nextToken('[');
            jcstring.add(s1);
            if(jcstringtokenizer.hasMoreTokens())
            {
                String s2 = jcstringtokenizer.nextToken(']');
                if(s2 != null)
                    s2.trim();
                parseToken(component, jcstring, s2);
            }
        }

        return jcstring;
    }

    private static void parseToken(Component component, JCString jcstring, String s)
    {
        if(s == null)
            return;
        String s1 = null;
        String s2 = s;
        int i = s.indexOf('=');
        if(i != -1)
        {
            s2 = s.substring(0, i);
            s1 = s.substring(i + 1);
        }
        if(s2.equalsIgnoreCase("DEFAULT_FONT"))
            jcstring.add(DEFAULT_FONT);
        else
        if(s2.equalsIgnoreCase("DEFAULT_COLOR"))
            jcstring.add(DEFAULT_COLOR);
        else
        if(s2.equalsIgnoreCase("UNDERLINE") || s2.equalsIgnoreCase("UL"))
            jcstring.add(UNDERLINE);
        else
        if(s2.equalsIgnoreCase("/UNDERLINE") || s2.equalsIgnoreCase("/UL"))
            jcstring.add(END_UNDERLINE);
        else
        if(s2.equalsIgnoreCase("STRIKETHROUGH") || s2.equalsIgnoreCase("ST"))
            jcstring.add(STRIKETHROUGH);
        else
        if(s2.equalsIgnoreCase("/STRIKETHROUGH") || s2.equalsIgnoreCase("/ST"))
            jcstring.add(END_STRIKETHROUGH);
        else
        if(s2.equalsIgnoreCase("HREF"))
        {
            jcstring.add(HREF);
            jcstring.add(s1);
        } else
        if(s2.equalsIgnoreCase("/HREF"))
            jcstring.add(END_HREF);
        else
        if(s2.equalsIgnoreCase("ALIGN"))
        {
            if(s1.equalsIgnoreCase("TOP"))
                jcstring.add(TOP);
            else
            if(s1.equalsIgnoreCase("MIDDLE"))
                jcstring.add(MIDDLE);
            else
            if(s1.equalsIgnoreCase("BOTTOM"))
                jcstring.add(BOTTOM);
        } else
        if(s2.equalsIgnoreCase("HORIZ_SPACE"))
        {
            jcstring.add(HORIZ_SPACE);
            if(s1 != null)
                jcstring.add(Integer.valueOf(s1));
        } else
        if(s2.equalsIgnoreCase("VERT_SPACE"))
        {
            jcstring.add(VERT_SPACE);
            if(s1 != null)
                jcstring.add(Integer.valueOf(s1));
        } else
        if(s2.equalsIgnoreCase("RESET"))
            jcstring.add(RESET);
        else
        if(s2.equalsIgnoreCase("COLOR"))
        {
            if(s1 != null)
                if(s1.equalsIgnoreCase("DEFAULT"))
                    jcstring.add(DEFAULT_COLOR);
                else
                    jcstring.add(JCUtilConverter.toColor(s1));
        } else
        if(s2.equalsIgnoreCase("IMAGE") || s2.equalsIgnoreCase("IMG"))
        {
            byte byte0 = -1;
            Image image = null;
            if(s1 != null)
            {
                try
                {
                    int j = Integer.valueOf(s1).intValue();
                    if(j > 0 && j <= reg_images.size())
                        image = (Image)reg_images.elementAt(j - 1);
                }
                catch(Exception _ex)
                {
                    if(component != null)
                    {
                        String s3 = String.valueOf(getApplet(component)) + s1;
                        if((image = getCachedImage(s3)) == null && (image = JCUtilConverter.toImage(component, s1)) != null)
                            putCachedImage(s3, image);
                    }
                }
                if(image != null)
                    jcstring.add(new JCImage(image, s1));
            }
        } else
        if(s2.equalsIgnoreCase("FONT"))
        {
            if(s1 != null)
                if(s1.equalsIgnoreCase("DEFAULT"))
                    jcstring.add(DEFAULT_FONT);
                else
                    jcstring.add(JCUtilConverter.toFont(s1));
        } else
        if(s2.equalsIgnoreCase("NEWLINE"))
            jcstring.add(NEWLINE);
    }

    private static void putCachedImage(String s, Image image)
    {
        if(!is_img_caching && image != null)
        {
            return;
        } else
        {
            img_cache.put(s, image);
            return;
        }
    }

    public static int registerImage(Image image)
    {
        reg_images.addElement(image);
        return reg_images.size();
    }

    public static void setIsImageCaching(boolean flag)
    {
        is_img_caching = flag;
    }

    public void setURLoffset(int i, int j)
    {
        x_offset = i;
        y_offset = j;
    }

    public static boolean showURL(String s, AppletContext appletcontext, Applet applet)
    {
        if(s == null || appletcontext == null)
            return false;
        int i = s.indexOf("TARGET=");
        String s1 = i != -1 ? s.substring(0, i) : s;
        String s2 = i != -1 ? s.substring(i + 7) : null;
        try
        {
            URL url1 = new URL(applet.getDocumentBase(), s1);
            if(s2 == null)
                appletcontext.showDocument(url1);
            else
                appletcontext.showDocument(url1, s2);
        }
        catch(Exception _ex)
        {
            applet.showStatus("Invalid URL");
            return false;
        }
        return true;
    }

    private void startURL(int i, int j)
    {
        url_rect = new Rectangle();
        url_rect.x = i;
        url_rect.y = j;
        if(url_list == null)
            url_list = new JCVector();
        if(url_rect_list == null)
            url_rect_list = new JCVector();
    }

    static int stringWidth(FontMetrics fontmetrics, Font font, String s)
    {
        return fontmetrics.stringWidth(s) + (font.isItalic() ? font.getSize() / 3 + 1 : 0);
    }

    public String toString2()
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < size(); i++)
        {
            Object obj = elementAt(i);
            String s = "";
            if(obj instanceof String)
                s = obj.toString();
            else
            if(obj == DEFAULT_FONT)
                s = "[DEFAULT_FONT]";
            else
            if(obj == DEFAULT_COLOR)
                s = "[DEFAULT_COLOR]";
            else
            if(obj == UNDERLINE)
                s = "[UL]";
            else
            if(obj == END_UNDERLINE)
                s = "[/UL]";
            else
            if(obj == STRIKETHROUGH)
                s = "[ST]";
            else
            if(obj == END_STRIKETHROUGH)
                s = "[/ST]";
            else
            if(obj == HREF)
                s = "[HREF=" + elementAt(++i) + "]";
            else
            if(obj == END_HREF)
                s = "[/HREF]";
            else
            if(obj == TOP)
                s = "[ALIGN=TOP]";
            else
            if(obj == MIDDLE)
                s = "[ALIGN=MIDDLE]";
            else
            if(obj == BOTTOM)
                s = "[ALIGN=BOTTOM]";
            else
            if(obj == HORIZ_SPACE)
            {
                s = "[HORIZ_SPACE";
                if(i < size() - 1 && isValue(super.elementData[i + 1]))
                    s = s + "=" + super.elementData[++i];
                s = s + "]";
            } else
            if(obj == VERT_SPACE)
            {
                s = "[VERT_SPACE";
                if(i < size() - 1 && isValue(super.elementData[i + 1]))
                    s = s + "=" + super.elementData[++i];
                s = s + "]";
            } else
            if(obj == RESET)
                s = "[RESET]";
            else
            if(obj == DEFAULT_COLOR)
                s = "[COLOR=DEFAULT]";
            else
            if(obj instanceof Color)
                s = "[COLOR=" + JCUtilConverter.fromColor((Color)obj) + "]";
            else
            if(obj instanceof Image)
                s = "";
            else
            if(obj instanceof JCImage)
                s = "[IMG=" + ((JCImage)obj).file + "]";
            else
            if(obj == DEFAULT_FONT)
                s = "[FONT=DEFAULT]";
            else
            if(obj instanceof Font)
                s = "[FONT=" + JCUtilConverter.fromFont((Font)obj) + "]";
            else
            if(obj == NEWLINE)
                s = "[NEWLINE]";
            stringbuffer.append(s);
        }

        return stringbuffer.toString();
    }

    public static final Integer UNDERLINE = new Integer(-2);
    public static final Integer STRIKETHROUGH = new Integer(-3);
    public static final Integer END_UNDERLINE = new Integer(-4);
    public static final Integer END_STRIKETHROUGH = new Integer(-5);
    public static final Integer HORIZ_SPACE = new Integer(-6);
    public static final Integer VERT_SPACE = new Integer(-7);
    public static final Integer NEWLINE = new Integer(-8);
    public static final Integer RESET = new Integer(-9);
    public static final Integer DEFAULT_FONT = new Integer(-10);
    public static final Integer DEFAULT_COLOR = new Integer(-11);
    public static final Integer TOP = new Integer(-17);
    public static final Integer MIDDLE = new Integer(-18);
    public static final Integer BOTTOM = new Integer(-19);
    public static final Integer LEFT = new Integer(-17);
    public static final Integer CENTER = new Integer(-18);
    public static final Integer RIGHT = new Integer(-19);
    public static final Integer HREF = new Integer(-21);
    public static final Integer END_HREF = new Integer(-22);
    public static final int STRING_LEFT = 0;
    public static final int STRING_RIGHT = 1;
    public static final int STRING_TOP = 2;
    public static final int STRING_BOTTOM = 3;
    static final int NOTFOUND = -1;
    static final int DEFAULT_SPACE = 10;
    static boolean underline = false;
    static boolean strikethrough = false;
    static Dimension size = new Dimension();
    static Rectangle draw_rect = new Rectangle();
    private static Vector reg_images = new Vector();
    public static boolean is_img_caching = true;
    private static Hashtable img_cache = new Hashtable();
    Rectangle url_rect;
    String url;
    JCVector url_rect_list;
    JCVector url_list;
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_RIGHT = 2;
    int x_offset;
    int y_offset;
    static boolean has_url = false;

}
