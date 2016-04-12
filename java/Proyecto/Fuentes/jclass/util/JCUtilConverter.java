// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCUtilConverter.java

package jclass.util;

import java.applet.Applet;
import java.awt.*;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.*;
import java.util.*;

// Referenced classes of package jclass.util:
//            ConvertUtil, JCConverter, JCString, JCStringTokenizer, 
//            JCVector

public class JCUtilConverter
{

    public JCUtilConverter()
    {
    }

    public static void checkEnum(int i, String s, int ai[])
    {
        for(int j = 0; j < ai.length; j++)
            if(ai[j] == i)
                return;

        throw new IllegalArgumentException("invalid " + s + ": " + i);
    }

    public static void error(String s)
    {
        System.err.println(s);
    }

    public static void error(String s, String s1)
    {
        error("Error parsing '" + s1 + "' in " + s);
    }

    public static String fromColor(Color color)
    {
        if(color == null)
            return null;
        if(color.equals(Color.black))
            return "black";
        if(color.equals(Color.blue))
            return "blue";
        if(color.equals(Color.cyan))
            return "cyan";
        if(color.equals(Color.darkGray))
            return "darkGray";
        if(color.equals(Color.gray))
            return "gray";
        if(color.equals(Color.green))
            return "green";
        if(color.equals(lightBlue))
            return "lightBlue";
        if(color.equals(Color.lightGray))
            return "lightGray";
        if(color.equals(Color.magenta))
            return "magenta";
        if(color.equals(Color.orange))
            return "orange";
        if(color.equals(Color.pink))
            return "pink";
        if(color.equals(Color.red))
            return "red";
        if(color.equals(Color.white))
            return "white";
        if(color.equals(Color.yellow))
        {
            return "yellow";
        } else
        {
            StringBuffer stringbuffer = new StringBuffer();
            stringbuffer.append(color.getRed());
            stringbuffer.append("-");
            stringbuffer.append(color.getGreen());
            stringbuffer.append("-");
            stringbuffer.append(color.getBlue());
            return stringbuffer.toString();
        }
    }

    public static String fromColorList(Color acolor[])
    {
        if(acolor == null)
            return "";
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < acolor.length; i++)
        {
            if(i > 0)
                stringbuffer.append(",");
            stringbuffer.append(fromColor(acolor[i]));
        }

        return new String(stringbuffer);
    }

    public static String fromEnum(int i, String s, String s1, String as[][], int ai[][], String s2)
    {
        int j;
        for(j = 0; j < as.length; j++)
            if(as[j][0].equalsIgnoreCase(s1))
                break;

        if(j == as.length)
            return null;
        for(int k = 0; k < ai[j].length; k++)
            if(ai[j][k] == i)
                return as[j][k + 1];

        try
        {
            return String.valueOf(i);
        }
        catch(Exception _ex)
        {
            error("Error converting '" + i + "' to " + s);
        }
        return s2;
    }

    public static String fromEnum(int i, String as[], int ai[])
    {
        if(ai == null || as == null)
            return null;
        for(int j = 0; j < ai.length; j++)
            if(i == ai[j] && j < as.length)
                return as[j];

        return null;
    }

    public static String fromEnum(long l, String as[], long al[])
    {
        if(al == null || as == null)
            return null;
        for(int i = 0; i < al.length; i++)
            if(l == al[i] && i < as.length)
                return as[i];

        return null;
    }

    public static String fromFont(Font font)
    {
        if(font == null)
            return null;
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(font.getName());
        stringbuffer.append("-");
        switch(font.getStyle())
        {
        case 2: // '\002'
            stringbuffer.append("ITALIC");
            break;

        case 1: // '\001'
            stringbuffer.append("BOLD");
            break;

        case 3: // '\003'
            stringbuffer.append("BOLDITALIC");
            break;

        case 0: // '\0'
        default:
            stringbuffer.append("PLAIN");
            break;
        }
        stringbuffer.append("-");
        stringbuffer.append(font.getSize());
        return stringbuffer.toString();
    }

    public static String fromInsets(Insets insets)
    {
        return insets.top + "," + insets.left + "," + insets.bottom + "," + insets.right;
    }

    public static String fromNewLine(String s)
    {
        if(s == null || s.indexOf('\n') == -1)
            return s;
        int l = 0;
        for(int i = 0; i < s.length(); i++)
            if(s.charAt(i) == '\n')
                l++;

        char ac[] = new char[s.length() + l];
        int j = 0;
        int k;
        for(k = 0; j < s.length(); k++)
        {
            if(s.charAt(j) == '\n')
            {
                ac[k++] = '\\';
                ac[k] = 'n';
            } else
            {
                ac[k] = s.charAt(j);
            }
            j++;
        }

        return new String(ac, 0, k);
    }

    public static String getParam(Applet applet, Component component, String s)
    {
        String s1 = null;
        if(param_source != null)
        {
            Hashtable hashtable = (Hashtable)param_source.get(component);
            if(hashtable != null)
                s1 = (String)hashtable.get(s.toLowerCase());
        }
        if(s1 == null && applet != null)
            try
            {
                return applet.getParameter(s);
            }
            catch(Exception _ex)
            {
                return null;
            }
        else
            return s1;
    }

    public static String getParam(Applet applet, Component component, String s, String s1)
    {
        if(param_source != null && param_source.containsKey(component) && s1.equalsIgnoreCase("paramFile"))
            return null;
        String s2 = null;
        if(s != null)
            s2 = getParam(applet, component, s + "." + s1);
        if(s2 == null)
            s2 = getParam(applet, component, s1);
        int i;
        if(s2 != null)
            while((i = s2.indexOf('\n')) != -1) 
                s2 = s2.substring(0, i) + s2.substring(i + 1);
        return s2;
    }

    public static boolean isSpace(char c)
    {
        return Character.isWhitespace(c);
    }

    public static void parseError(String s)
    {
        if(reportErrors)
            error("Error parsing '" + s + "'");
    }

    public static String removeEscape(String s)
    {
        if(s == null || s.indexOf('\\') == -1)
            return s;
        int k = s.length();
        char ac[] = new char[k];
        int i = 0;
        int j;
        for(j = 0; i < k; j++)
        {
            char c = s.charAt(i);
            if(i + 1 < k && c == '\\')
            {
                i++;
                if(s.charAt(i) == 'n')
                    ac[j] = '\n';
                else
                    ac[j] = s.charAt(i);
            } else
            {
                ac[j] = c;
            }
            i++;
        }

        return j <= 0 ? null : new String(ac, 0, j);
    }

    public static void setParamSource(Component component, String s)
    {
        if(s == null)
        {
            if(param_source == null)
                return;
            if(param_source.containsKey(component))
                param_source.remove(component);
            return;
        }
        int j = 0;
        int k = 0;
        boolean flag = false;
        for(; j < s.length() && (j = s.indexOf('<', j)) != -1; j++)
        {
            if(s.regionMatches(true, j, "<applet", 0, 7))
                for(k = j + 7; (k = s.indexOf("</", k)) != -1; k++)
                {
                    if(!s.regionMatches(true, k, "</applet>", 0, 9))
                        continue;
                    flag = true;
                    break;
                }

            if(flag)
                break;
        }

        if(flag)
            s = s.substring(j, k);
        if(param_source == null)
            param_source = new Hashtable();
        Hashtable hashtable = new Hashtable();
        param_source.put(component, hashtable);
        int i;
        while((i = s.indexOf('<')) != -1) 
        {
            s = s.substring(i + 1);
            JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
            jcstringtokenizer.strip_esc = false;
            String s1 = trim(jcstringtokenizer.nextToken('>'));
            if(s1 == null || s1.length() == 0)
                return;
            s = trim(s.substring(jcstringtokenizer.getPosition()));
            if(s1.charAt(0) == '!' || s1.length() < 20 || isSpace(s1.charAt(11)) || !s1.regionMatches(true, 0, "param name=", 0, 11))
                continue;
            s1 = s1.substring(11);
            int i1;
            for(i1 = 1; i1 < s1.length(); i1++)
                if(isSpace(s1.charAt(i1)))
                    break;

            int l;
            for(l = i1 + 1; l < s1.length(); l++)
                if(!isSpace(s1.charAt(l)))
                    break;

            if(l == s1.length() || !s1.regionMatches(true, l, "value=", 0, 6))
                continue;
            String s2 = s1.substring(l + 6);
            if(s2.charAt(0) == '"')
            {
                if(s2.length() < 2)
                    continue;
                s2 = s2.substring(1);
                if(s2.charAt(s2.length() - 1) == '"')
                    s2 = s2.substring(0, s2.length() - 1);
            }
            hashtable.put(s1.substring(0, i1).toLowerCase(), s2);
        }
    }

    public static boolean toBoolean(Applet applet, Component component, String s, String s1, boolean flag)
    {
        return conv.toBoolean(getParam(applet, component, s, s1), flag);
    }

    public static boolean toBoolean(String s, boolean flag)
    {
        if(s == null)
            return flag;
        try
        {
            return Boolean.valueOf(s).booleanValue();
        }
        catch(Exception _ex)
        {
            parseError(s);
        }
        return flag;
    }

    public static Color toColor(String s)
    {
        if(s == null)
            return null;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        String s1 = jcstringtokenizer.nextToken('-');
        if(s1 == null)
            return null;
        s1 = s1.trim();
        if(s1.equalsIgnoreCase("black"))
            return Color.black;
        if(s1.equalsIgnoreCase("blue"))
            return Color.blue;
        if(s1.equalsIgnoreCase("cyan"))
            return Color.cyan;
        if(s1.equalsIgnoreCase("darkGray"))
            return Color.darkGray;
        if(s1.equalsIgnoreCase("darkGrey"))
            return Color.darkGray;
        if(s1.equalsIgnoreCase("gray"))
            return Color.gray;
        if(s1.equalsIgnoreCase("grey"))
            return Color.gray;
        if(s1.equalsIgnoreCase("green"))
            return Color.green;
        if(s1.equalsIgnoreCase("lightGray"))
            return Color.lightGray;
        if(s1.equalsIgnoreCase("lightGrey"))
            return Color.lightGray;
        if(s1.equalsIgnoreCase("lightBlue"))
            return lightBlue;
        if(s1.equalsIgnoreCase("magenta"))
            return Color.magenta;
        if(s1.equalsIgnoreCase("orange"))
            return Color.orange;
        if(s1.equalsIgnoreCase("pink"))
            return Color.pink;
        if(s1.equalsIgnoreCase("red"))
            return Color.red;
        if(s1.equalsIgnoreCase("white"))
            return Color.white;
        if(s1.equalsIgnoreCase("yellow"))
            return Color.yellow;
        try
        {
            if(s1.startsWith("#"))
            {
                if(s1.length() >= 13)
                    return new Color(Integer.valueOf(s1.substring(1, 5), 16).intValue() / 256, Integer.valueOf(s1.substring(5, 9), 16).intValue() / 256, Integer.valueOf(s1.substring(9, 13), 16).intValue() / 256);
                if(s1.length() >= 7)
                    return new Color(Integer.valueOf(s1.substring(1, 3), 16).intValue(), Integer.valueOf(s1.substring(3, 5), 16).intValue(), Integer.valueOf(s1.substring(5, 7), 16).intValue());
                else
                    return new Color(Integer.valueOf(s1.substring(1), 16).intValue());
            } else
            {
                int i = Integer.parseInt(s1);
                int j = Integer.parseInt(jcstringtokenizer.nextToken('-'));
                int k = Integer.parseInt(jcstringtokenizer.nextToken('-'));
                return new Color(i, j, k);
            }
        }
        catch(Exception _ex)
        {
            return null;
        }
    }

    public static Color toColor(String s, Color color)
    {
        Color color1 = toColor(s);
        return s == null ? color : color1;
    }

    public static Color[] toColorList(String s)
    {
        if(s == null)
            return null;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        Color acolor[] = new Color[jcstringtokenizer.countTokens(',')];
        for(int i = 0; jcstringtokenizer.hasMoreTokens(); i++)
        {
            String s1 = jcstringtokenizer.nextToken(',').trim();
            if((acolor[i] = conv.toColor(s1)) == null)
                error(s, s1);
        }

        return acolor;
    }

    public static Color[] toColorList(String s, Color acolor[])
    {
        Color acolor1[] = toColorList(s);
        return s == null ? acolor : acolor1;
    }

    public static Date toDate(String s, Date date)
    {
        if(s == null)
            return date;
        try
        {
            try
            {
                SimpleDateFormat simpledateformat = (SimpleDateFormat)DateFormat.getDateTimeInstance(1, 1);
                simpledateformat.setCalendar(Calendar.getInstance());
                simpledateformat.setTimeZone(TimeZone.getDefault());
                return simpledateformat.parse(s);
            }
            catch(ParseException _ex)
            {
                return new Date(s);
            }
        }
        catch(Exception _ex)
        {
            return date;
        }
    }

    public static Dimension toDimension(String s, Dimension dimension)
    {
        int ai[] = toIntList(s, 'x', null);
        if(ai != null && ai.length == 2)
            return new Dimension(ai[0], ai[1]);
        else
            return dimension;
    }

    public static double toDouble(String s, double d)
    {
        if(s == null)
            return d;
        try
        {
            return Double.valueOf(s).doubleValue();
        }
        catch(Exception _ex)
        {
            parseError(s);
        }
        return d;
    }

    public static Double[] toDoubleList(String s, char c)
    {
        if(s == null)
            return null;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        Double adouble[] = new Double[jcstringtokenizer.countTokens(c)];
        for(int i = 0; jcstringtokenizer.hasMoreTokens(); i++)
        {
            String s1 = jcstringtokenizer.nextToken(c).trim();
            adouble[i] = new Double(conv.toDouble(s1, 0.0D));
        }

        return adouble;
    }

    public static Double[] toDoubleList(String s, char c, Double adouble[])
    {
        Double adouble1[] = toDoubleList(s, c);
        return adouble1 == null ? adouble : adouble1;
    }

    public static int toEnum(String s, String s1, String s2, String as[][], int ai[][], int i)
    {
        if(s2 == null)
            s2 = s1;
        int j;
        for(j = 0; j < as.length; j++)
            if(as[j][0].equalsIgnoreCase(s1))
                break;

        if(j == as.length)
        {
            error("Error converting '" + s + "' to " + s2);
            return i;
        }
        s = s.trim();
        for(int k = 1; k < as[j].length; k++)
            if(s.equalsIgnoreCase(as[j][k]))
                return ai[j][k - 1];

        try
        {
            return Integer.parseInt(s);
        }
        catch(Exception _ex)
        {
            error("Error converting '" + s + "' to " + s2);
        }
        return i;
    }

    public static int toEnum(String s, String s1, String as[], int ai[], int i)
    {
        if(s == null)
            return i;
        s = s.trim();
        for(int j = 0; j < as.length; j++)
            if(s.equalsIgnoreCase(as[j]))
                return ai[j];

        try
        {
            return Integer.parseInt(s);
        }
        catch(Exception _ex)
        {
            error("Error converting '" + s + "' to " + s1);
        }
        return i;
    }

    public static long toEnum(String s, String s1, String as[], long al[], long l)
    {
        if(s == null)
            return l;
        s = s.trim();
        for(int i = 0; i < as.length; i++)
            if(s.equalsIgnoreCase(as[i]))
                return al[i];

        try
        {
            return Long.parseLong(s);
        }
        catch(Exception _ex)
        {
            error("Error converting '" + s + "' to " + s1);
        }
        return l;
    }

    public static int toEnum(String s, String as[], int ai[], int i)
    {
        if(s == null)
            return i;
        s = s.trim();
        for(int j = 0; j < as.length; j++)
            if(s.equalsIgnoreCase(as[j]))
                return ai[j];

        return i;
    }

    public static int[] toEnumList(String s, String s1, String as[], int ai[], int ai1[])
    {
        if(s == null)
            return ai1;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        int ai2[] = new int[jcstringtokenizer.countTokens(',')];
        for(int i = 0; jcstringtokenizer.hasMoreTokens(); i++)
        {
            String s2 = jcstringtokenizer.nextToken(',').trim();
            int j = ai1 == null || i >= ai1.length ? 0 : ai1[i];
            ai2[i] = conv.toEnum(s2, s1, as, ai, j);
        }

        return ai2;
    }

    public static Font toFont(String s)
    {
        if(s == null)
            return null;
        byte byte0 = 0;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        if(jcstringtokenizer.countTokens('-') != 3)
            return null;
        String s1 = jcstringtokenizer.nextToken('-');
        if(s1 == null)
            return null;
        s1 = s1.trim();
        String s2 = jcstringtokenizer.nextToken('-');
        if(s2 == null)
        {
            byte0 = 0;
        } else
        {
            s2 = s2.trim();
            if(s2.equalsIgnoreCase("PLAIN"))
                byte0 = 0;
            if(s2.equalsIgnoreCase("ITALIC"))
                byte0 = 2;
            if(s2.equalsIgnoreCase("BOLD"))
                byte0 = 1;
            if(s2.equalsIgnoreCase("BOLDITALIC"))
                byte0 = 3;
        }
        String s3 = jcstringtokenizer.nextToken('-');
        int i = 8;
        if(s3 != null)
            i = Integer.parseInt(s3.trim());
        return new Font(s1, byte0, i);
    }

    public static Font toFont(String s, Font font)
    {
        Font font1 = conv.toFont(s);
        return font1 == null ? font : font1;
    }

    public static Image toImage(Component component, String s)
    {
        if(s == null)
            return null;
        Image image = null;
        boolean flag = false;
        Applet applet = (component instanceof Applet) ? (Applet)component : JCString.getApplet(component);
        if(applet != null)
        {
            URL url = null;
            try
            {
                url = applet.getDocumentBase();
                flag = true;
            }
            catch(Exception _ex) { }
            if(flag)
                image = applet.getImage(url, s);
        }
        if(!flag)
        {
            if(s.indexOf(":") == -1)
            {
                char c = System.getProperty("file.separator").charAt(0);
                String s1;
                try
                {
                    s1 = System.getProperty("user.dir");
                }
                catch(Exception _ex)
                {
                    s1 = "";
                }
                s = "file:" + s1 + c + s;
                s = s.replace('/', c);
                if(c != '\\' && System.getProperty("os.name").indexOf("Windows") != -1)
                    s = s.replace('/', '\\');
            }
            try
            {
                Toolkit toolkit = component == null ? Toolkit.getDefaultToolkit() : component.getToolkit();
                image = toolkit.getImage(new URL(s));
            }
            catch(MalformedURLException _ex)
            {
                error("Error loading image " + s);
            }
        }
        if(component == null || image == null)
            return image;
        if(!waitForImage(component, image))
            image = null;
        if(image == null)
            error("Error loading image " + s);
        return image;
    }

    public static Image toImage(Component component, String s, Image image)
    {
        Image image1 = conv.toImage(component, s);
        return image1 == null ? image : image1;
    }

    public static Image[] toImageList(Component component, String s, Image aimage[])
    {
        if(s == null)
            return aimage;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        Image aimage1[] = new Image[jcstringtokenizer.countTokens(',')];
        for(int i = 0; jcstringtokenizer.hasMoreTokens(); i++)
        {
            String s1 = jcstringtokenizer.nextToken(',').trim();
            Image image = aimage == null || i >= aimage.length ? null : aimage[i];
            aimage1[i] = conv.toImage(component, s1, aimage[i]);
        }

        return aimage1;
    }

    public static Insets toInsets(String s, Insets insets)
    {
        if(s == null)
            return insets;
        int ai[] = toIntList(s, ',', null);
        if(ai != null && ai.length == 4)
            return new Insets(ai[0], ai[1], ai[2], ai[3]);
        else
            return insets;
    }

    public static int toInt(String s, int i)
    {
        if(s == null)
            return i;
        if(s.equalsIgnoreCase("maxint"))
            return 0x7fffffff;
        if(s.equalsIgnoreCase("novalue"))
            return -999;
        if(s.equalsIgnoreCase("variable"))
            return -998;
        if(s.equalsIgnoreCase("default"))
            return -999;
        try
        {
            return Integer.parseInt(s);
        }
        catch(Exception _ex)
        {
            parseError(s);
        }
        return i;
    }

    public static int[] toIntList(String s, char c)
    {
        if(s == null)
            return null;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        int ai[] = new int[jcstringtokenizer.countTokens(c)];
        for(int i = 0; jcstringtokenizer.hasMoreTokens(); i++)
        {
            String s1 = jcstringtokenizer.nextToken(c).trim();
            ai[i] = conv.toInt(s1, 0);
        }

        return ai;
    }

    public static int[] toIntList(String s, char c, int ai[])
    {
        int ai1[] = toIntList(s, c);
        return ai1 == null ? ai : ai1;
    }

    public static Integer[] toIntegerList(String s, char c)
    {
        if(s == null)
            return null;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        Integer ainteger[] = new Integer[jcstringtokenizer.countTokens(c)];
        for(int i = 0; jcstringtokenizer.hasMoreTokens(); i++)
        {
            String s1 = jcstringtokenizer.nextToken(c).trim();
            ainteger[i] = new Integer(conv.toInt(s1, 0));
        }

        return ainteger;
    }

    public static Integer[] toIntegerList(String s, char c, Integer ainteger[])
    {
        Integer ainteger1[] = toIntegerList(s, c);
        return ainteger1 == null ? ainteger : ainteger1;
    }

    public static String toNewLine(String s)
    {
        if(s == null || s.indexOf("\\n") == -1)
            return s;
        char ac[] = new char[s.length()];
        int i = 0;
        for(int j = 0; j < ac.length;)
        {
            if(s.charAt(j) == '\\' && j < ac.length - 1 && s.charAt(j + 1) == 'n')
            {
                ac[i] = '\n';
                j++;
            } else
            {
                ac[i] = s.charAt(j);
            }
            j++;
            i++;
        }

        return new String(ac, 0, i);
    }

    public static Point toPoint(String s, Point point)
    {
        int ai[] = toIntList(s, ',', null);
        if(ai != null && ai.length == 2)
            return new Point(ai[0], ai[1]);
        else
            return point;
    }

    public static String toString(Object obj)
    {
        if(!(obj instanceof Vector))
            return fromNewLine(obj == null ? "" : obj.toString());
        Vector vector = (Vector)obj;
        StringBuffer stringbuffer = new StringBuffer(vector.size());
        int i = 0;
        for(int j = vector.size(); i < j; i++)
        {
            stringbuffer.append(toString(vector.elementAt(i)));
            if(i < j - 1)
                stringbuffer.append(',');
        }

        return new String(stringbuffer);
    }

    public static String[] toStringList(String s)
    {
        return toStringList(s, ',');
    }

    public static String[] toStringList(String s, char c)
    {
        return toStringList(s, c, true);
    }

    public static String[] toStringList(String s, char c, boolean flag)
    {
        if(s == null)
            return null;
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        String as[] = new String[jcstringtokenizer.countTokens(c)];
        for(int i = 0; jcstringtokenizer.hasMoreTokens(); i++)
        {
            String s1 = jcstringtokenizer.nextToken(c);
            if(s1 == null)
                s1 = "";
            String s2 = s1.trim();
            if(s2 == null)
                s2 = "";
            as[i] = flag ? s2 : s1;
        }

        return as;
    }

    public static JCVector toVector(Component component, String s, char c, boolean flag)
    {
        return ConvertUtil.toVector(component, s, c, flag);
    }

    public static JCVector toVector(Component component, String s, char c, boolean flag, JCVector jcvector)
    {
        JCVector jcvector1 = conv.toVector(component, s, c, flag);
        return jcvector1 == null ? jcvector : jcvector1;
    }

    public static JCVector toVectorFromCSV(Component component, String s)
    {
        return ConvertUtil.toVectorFromCSV(component, s);
    }

    public static JCVector toVectorFromCSV(Component component, String s, JCVector jcvector)
    {
        JCVector jcvector1 = toVectorFromCSV(component, s);
        return jcvector1 == null ? jcvector : jcvector1;
    }

    public static String trim(Object obj)
    {
        String s;
        if(obj == null || (s = obj.toString()) == null)
            return null;
        s = s.trim();
        int i = s.indexOf('\0');
        if(i != -1)
            return s.substring(0, i);
        else
            return s;
    }

    public static boolean waitForImage(Component component, Image image)
    {
        MediaTracker mediatracker = new MediaTracker(component);
        try
        {
            mediatracker.addImage(image, 0);
            mediatracker.waitForID(0);
        }
        catch(Exception _ex)
        {
            return false;
        }
        return mediatracker.isErrorAny() ^ true;
    }

    public static JCConverter conv = new JCConverter();
    public static boolean reportErrors = true;
    public static final Color lightBlue = new Color(173, 216, 230);
    public static Hashtable param_source;

}
