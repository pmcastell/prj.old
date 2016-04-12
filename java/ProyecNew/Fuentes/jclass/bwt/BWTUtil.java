// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BWTUtil.java

package jclass.bwt;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.util.Vector;
import jclass.base.BaseComponent;
import jclass.base.Border;
import jclass.util.JCString;
import jclass.util.JCUtilConverter;

// Referenced classes of package jclass.bwt:
//            BWTEnum, JCMultiColumnInterface

public class BWTUtil
{

    public BWTUtil()
    {
    }

    public static Color brighter(Color color)
    {
        return Border.brighter(color);
    }

    public static final int[] copyList(int ai[], int i, int j)
    {
        if(ai != null && i <= ai.length)
            return ai;
        int ai1[] = ai;
        int k = ai1 == null ? 0 : ai1.length;
        ai = new int[i];
        if(ai1 != null)
            System.arraycopy(ai1, 0, ai, 0, ai1.length);
        for(int l = k; l < ai.length; l++)
            ai[l] = j;

        return ai;
    }

    public static final Object[] copyList(Object aobj[], int i, Object obj)
    {
        if(aobj != null && i <= aobj.length)
            return aobj;
        Object aobj1[] = aobj;
        int j = aobj1 == null ? 0 : aobj1.length;
        aobj = new Object[i];
        if(aobj1 != null)
            System.arraycopy(((Object) (aobj1)), 0, ((Object) (aobj)), 0, aobj1.length);
        for(int k = j; k < aobj.length; k++)
            aobj[k] = obj;

        return aobj;
    }

    public static final boolean[] copyList(boolean aflag[], int i, boolean flag)
    {
        if(aflag != null && i <= aflag.length)
            return aflag;
        boolean aflag1[] = aflag;
        int j = aflag1 == null ? 0 : aflag1.length;
        aflag = new boolean[i];
        if(aflag1 != null)
            System.arraycopy(aflag1, 0, aflag, 0, aflag1.length);
        for(int k = j; k < aflag.length; k++)
            aflag[k] = flag;

        return aflag;
    }

    public static int countChar(String s, char c, int i, int j)
    {
        if(s == null || i >= s.length() || i >= j)
            return 0;
        if(i < 0)
            i = 0;
        int k = 0;
        for(i = s.indexOf(c, i); i < j; i = s.indexOf(c, i))
        {
            if(i == -1)
                break;
            i++;
            k++;
        }

        return k;
    }

    public static int countChar(char ac[], int i, char c, int j, int k)
    {
        if(ac == null || j >= i || j >= k)
            return 0;
        k = Math.min(k, i);
        if(j < 0)
            j = 0;
        int l = 0;
        for(j = indexOf(ac, i, c, j); j < k; j = indexOf(ac, i, c, j))
        {
            if(j == -1)
                break;
            j++;
            l++;
        }

        return l;
    }

    static synchronized Image createImage(Component component, int i, int j)
    {
        return BaseComponent.createImage(component, i, j);
    }

    static synchronized Image createImage(Component component, Image image, int i, int j)
    {
        return BaseComponent.createImage(component, image, i, j);
    }

    public static Color darker(Color color)
    {
        return Border.darker(color);
    }

    public static synchronized void draw(Component component, Graphics g, Object obj, int i, Rectangle rectangle)
    {
        if(obj == null)
            return;
        int j = toHorizAlignment(i);
        if(is_jcstring(obj))
        {
            ((JCString)obj).draw(component, g, rectangle, j);
            return;
        }
        if(obj instanceof Image)
        {
            g.drawImage((Image)obj, rectangle.x, rectangle.y, null);
            return;
        }
        String s = obj.toString();
        if(s == null || s.length() == 0)
            return;
        FontMetrics fontmetrics = g.getFontMetrics();
        Font font = g.getFont();
        int k = fontmetrics.getHeight();
        int l = 0;
        int i1 = k - fontmetrics.getAscent();
        int j1 = (rectangle.y + k) - i1;
        int k1 = isTop(i) ? 0 : getHeight(s, component, font);
        if(isBottom(i))
            j1 += rectangle.height - k1;
        else
        if(isMiddle(i))
            j1 += (rectangle.height - k1) / 2;
        if(s.indexOf('\n') != -1)
        {
            int l1 = 0;
            int j2 = rectangle.x;
            int i2;
            while((i2 = s.indexOf('\n', l1)) != -1) 
            {
                String s1 = s.substring(l1, i2);
                if(j == 1)
                    l = (rectangle.width - stringWidth(fontmetrics, font, s1)) / 2;
                else
                if(j == 2)
                    l = rectangle.width - stringWidth(fontmetrics, font, s1);
                g.drawString(s1, rectangle.x + l, j1);
                l1 = i2 + 1;
                j1 += k;
            }
            String s2 = s.substring(l1, s.length());
            if(j == 1)
                l = (rectangle.width - stringWidth(fontmetrics, font, s2)) / 2;
            else
            if(j == 2)
                l = rectangle.width - stringWidth(fontmetrics, font, s2);
            g.drawString(s2, rectangle.x + l, j1);
        } else
        {
            if(j == 1)
                l = (rectangle.width - stringWidth(fontmetrics, font, s)) / 2;
            else
            if(j == 2)
                l = rectangle.width - stringWidth(fontmetrics, font, s);
            g.drawString(s, rectangle.x + l, j1);
        }
    }

    public static void drawDashedLine(Graphics g, int i, int j, int k, int l)
    {
        int i1 = Math.min(i, k);
        int j1 = Math.min(j, l);
        int k1 = Math.max(i, k);
        int l1 = Math.max(j, l);
        if(k == i)
        {
            for(int i2 = j1; i2 < l1; i2 = Math.min(i2 + 3, l1))
                g.drawLine(i, i2, i, i2 + 1);

        } else
        {
            for(int j2 = i1; j2 < k1; j2 = Math.min(j2 + 3, k1))
                g.drawLine(j2, j, j2 + 1, j);

        }
    }

    public static void drawDashedRect(Graphics g, int i, int j, int k, int l)
    {
        drawDashedLine(g, i, j, i + k, j);
        drawDashedLine(g, i + k, j, i + k, j + l);
        drawDashedLine(g, i, j, i, j + l);
        drawDashedLine(g, i, j + l, i + k, j + l);
    }

    static void fill3DEdgeRect(Graphics g, int i, int j, int k, int l, boolean flag)
    {
        Color color = g.getColor();
        fill3DRect(g, i + 1, j + 1, k - 2, l - 2, flag);
        g.setColor(Color.black);
        g.drawRect(i, j, k - 1, l - 1);
        g.setColor(color);
    }

    static void fill3DRect(Graphics g, int i, int j, int k, int l, boolean flag)
    {
        Color color = g.getColor();
        Color color1 = brighter(color);
        Color color2 = darker(color);
        if(!flag)
            g.setColor(color2);
        g.fillRect(i + 1, j + 1, k - 2, l - 2);
        g.setColor(flag ? color1 : color2);
        g.drawLine(i, j, i, (j + l) - 1);
        g.drawLine(i + 1, j, (i + k) - 2, j);
        g.setColor(flag ? color2 : color1);
        g.drawLine(i + 1, (j + l) - 1, (i + k) - 1, (j + l) - 1);
        g.drawLine((i + k) - 1, j, (i + k) - 1, (j + l) - 2);
        g.setColor(color);
    }

    public static Applet getApplet(Component component)
    {
        return BaseComponent.getApplet(component);
    }

    public static AppletContext getAppletContext(Applet applet)
    {
        return BaseComponent.getAppletContext(applet);
    }

    public static Frame getFrame(Component component)
    {
        return BaseComponent.getFrame(component);
    }

    public static int getHeight(Object obj, Component component)
    {
        return getHeight(obj, component, component.getFont());
    }

    public static int getHeight(Object obj, Component component, Font font)
    {
        if(obj == null)
            return 0;
        if(obj instanceof Image)
            return ((Image)obj).getHeight(null);
        if(is_jcstring(obj))
            return ((JCString)obj).getHeight(component, font);
        if(obj instanceof Vector)
        {
            Vector vector = (Vector)obj;
            int i = 0;
            for(int j = 0; j < vector.size(); j++)
                i = Math.max(i, getHeight(vector.elementAt(j), component, font));

            return i;
        } else
        {
            return component.getToolkit().getFontMetrics(font).getHeight() * getNumLines(obj.toString());
        }
    }

    public static int getMouseButton(Event event)
    {
        if(event.metaDown())
            return 3;
        return (event.modifiers & 8) == 0 ? 1 : 2;
    }

    public static int getNumLines(Object obj)
    {
        return getNumLines(toString(obj));
    }

    private static int getNumLines(String s)
    {
        if(s == null || s.length() == 0)
            return 0;
        int i = 1;
        for(int j = 0; j < s.length(); j++)
            if(s.charAt(j) == '\n')
                i++;

        return i;
    }

    public static Point getVisibleScreenLoc(Component component, int i, int j, int k, int l)
    {
        return BaseComponent.getVisibleScreenLoc(component, i, j, k, l);
    }

    public static int getWidth(Object obj, Component component)
    {
        return getWidth(obj, component, component.getFont());
    }

    public static int getWidth(Object obj, Component component, Font font)
    {
        if(obj == null)
            return 0;
        if(obj instanceof Image)
            return ((Image)obj).getWidth(null);
        if(is_jcstring(obj))
            return ((JCString)obj).getWidth(component, font);
        if((obj instanceof Vector) && (component instanceof JCMultiColumnInterface))
            return getWidth((Vector)obj, (JCMultiColumnInterface)component);
        if(obj instanceof Vector)
        {
            int i = 0;
            for(int j = 0; j < ((Vector)obj).size(); j++)
                i += getWidth(((Vector)obj).elementAt(j), component);

            return i;
        }
        String s = obj.toString();
        if(s == null || s.length() == 0)
            return 0;
        FontMetrics fontmetrics = component.getToolkit().getFontMetrics(font);
        if(s.indexOf('\n') != -1)
        {
            int k = 0;
            int i1 = 0;
            int l;
            while((l = s.indexOf('\n', k)) != -1) 
            {
                i1 = Math.max(i1, stringWidth(fontmetrics, font, s.substring(k, l)));
                if(font.isItalic())
                    i1 += 5;
                k = l + 1;
            }
            return Math.max(i1, stringWidth(fontmetrics, font, s.substring(k, s.length())));
        } else
        {
            return stringWidth(fontmetrics, font, s);
        }
    }

    public static int getWidth(Vector vector, JCMultiColumnInterface jcmulticolumninterface)
    {
        if(vector == null)
            return 0;
        int i = Math.min(jcmulticolumninterface.getNumColumns(), vector.size());
        if(i == 0)
            return 0;
        int j = 0;
        for(int k = 0; k < i; k++)
            j += jcmulticolumninterface.getColumnWidth(k);

        return j;
    }

    public static Window getWindow(Component component)
    {
        return BaseComponent.getWindow(component);
    }

    public static boolean inBrowser(Component component)
    {
        return getAppletContext(getApplet(component)) != null;
    }

    static final int indexOf(char ac[], int i, int j, int k)
    {
        for(int l = k; l < i; l++)
            if(ac[l] == j)
                return l;

        return -1;
    }

    public static boolean instanceOf(Object obj, String s)
    {
        if(obj == null)
            return false;
        Class class1 = obj.getClass();
        boolean flag;
        while(!(flag = class1.getName().indexOf(s) != -1)) 
            if((class1 = class1.getSuperclass()) == null)
                break;
        return flag;
    }

    public static final boolean intersects(Component component, int i, int j, int k, int l)
    {
        return (component.size().width <= i || component.size().height <= j || i + k < 0 || j + l < 0) ^ true;
    }

    static boolean isBottom(int i)
    {
        return i == 6 || i == 7 || i == 8;
    }

    static boolean isCenter(int i)
    {
        return i == 1 || i == 4 || i == 7;
    }

    static boolean isMiddle(int i)
    {
        return i == 3 || i == 4 || i == 5;
    }

    public static boolean isParent(Component component, Component component1)
    {
        if(component1 == component)
            return true;
        if(component == null || component1 == null || !(component instanceof Container))
            return false;
        for(Container container = component1.getParent(); container != null; container = container.getParent())
            if(container == component)
                return true;

        return false;
    }

    static boolean isRight(int i)
    {
        return i == 2 || i == 5 || i == 8;
    }

    static boolean isTop(int i)
    {
        return i == 0 || i == 1 || i == 2;
    }

    static boolean is_jcstring(Object obj)
    {
        return instanceOf(obj, "JCString");
    }

    public static void setCursor(Component component, int i)
    {
        Window window = getWindow(component);
        if((window instanceof Dialog) && ((Dialog)window).isModal())
            return;
        if(window != null)
        {
            window.setCursor(Cursor.getPredefinedCursor(i));
            component.getToolkit().sync();
        }
    }

    static boolean startsWith(Object obj, char c)
    {
        String s = toString(obj);
        return s == null || s.length() <= 0 ? false : Character.toUpperCase(s.charAt(0)) == c;
    }

    static int stringWidth(FontMetrics fontmetrics, Font font, String s)
    {
        return fontmetrics.stringWidth(s) + (font.isItalic() ? font.getSize() / 3 + 1 : 0);
    }

    public static int toHorizAlignment(int i)
    {
        return isCenter(i) ? 1 : isRight(i) ? 2 : 0;
    }

    static String toString(Object obj)
    {
        if(obj == null)
            return null;
        if(obj instanceof Image)
            return null;
        if(is_jcstring(obj))
            return ((JCString)obj).getString();
        if(obj instanceof Vector)
        {
            for(int i = 0; i < ((Vector)obj).size(); i++)
            {
                String s;
                if((s = toString(((Vector)obj).elementAt(i))) != null)
                    return s;
            }

            return null;
        } else
        {
            return obj.toString();
        }
    }

    public static void trace()
    {
        try
        {
            throw new ArrayIndexOutOfBoundsException("");
        }
        catch(Exception exception)
        {
            exception.printStackTrace(System.out);
        }
    }

    public static Point translateFromParent(Container container, Component component, int i, int j)
    {
        return BaseComponent.translateFromParent(container, component, i, j);
    }

    public static Point translateToParent(Container container, Component component, int i, int j)
    {
        return BaseComponent.translateToParent(container, component, i, j);
    }

    public static void wallPaper(Component component, Graphics g, Image image)
    {
        Dimension dimension = component.size();
        if(!JCUtilConverter.waitForImage(component, image))
            return;
        int i = image.getWidth(component);
        int j = image.getHeight(component);
        for(int k = 0; k < dimension.width; k += i)
        {
            for(int l = 0; l < dimension.height; l += j)
                g.drawImage(image, k, l, component);

        }

    }

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
}
