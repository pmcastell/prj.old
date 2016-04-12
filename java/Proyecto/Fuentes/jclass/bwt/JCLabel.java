// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCLabel.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.net.URL;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;
import jclass.util.*;

// Referenced classes of package jclass.bwt:
//            JCComponent, BWTEnum, BWTUtil, LabelConverter

public class JCLabel extends JCComponent
{

    public JCLabel()
    {
        this(null, ((Applet) (null)), ((String) (null)));
    }

    public JCLabel(Object obj)
    {
        this(obj, ((Applet) (null)), ((String) (null)));
    }

    public JCLabel(Object obj, Applet applet, String s)
    {
        super(applet, s);
        alignment = 4;
        label_rect = new Rectangle();
        if(s == null)
            setName("label" + nameCounter++);
        super.highlight = super.border = 0;
        super.traversable = false;
        super.insets = new Insets(2, 5, 2, 5);
        if(getClass().getName().equals("jclass.bwt.JCLabel"))
            getParameters(applet);
        if(obj != null)
            setLabel(obj);
    }

    public JCLabel(String s, Image image, int i)
    {
        this(new JCString(s, image, i), ((Applet) (null)), ((String) (null)));
    }

    public JCLabel(String s, String s1, Applet applet, int i)
    {
        this(null, ((Applet) (null)), ((String) (null)));
        setLabel(new JCString(s, JCComponent.conv.toImage(applet, s1), i));
    }

    public void addNotify()
    {
        super.addNotify();
        setLabelSize(label);
        if(super.needs_layout)
            layout();
    }

    protected synchronized void drawValue(Graphics g, Object obj)
    {
        if(!isEnabled())
        {
            Color color = g.getColor();
            g.translate(1, 1);
            g.setColor(Color.white);
            BWTUtil.draw(this, g, obj, alignment, label_rect);
            g.translate(-1, -1);
            g.setColor(getBackground().darker());
            BWTUtil.draw(this, g, obj, alignment, label_rect);
            g.setColor(color);
            return;
        } else
        {
            BWTUtil.draw(this, g, obj, alignment, label_rect);
            return;
        }
    }

    public int getAlignment()
    {
        return alignment;
    }

    public Object getLabel()
    {
        return label;
    }

    public int getLabelAlignment()
    {
        return getAlignment();
    }

    public Rectangle getLabelBounds()
    {
        return label_rect;
    }

    public URL getLabelImage()
    {
        return label_url;
    }

    protected void getParameters()
    {
        super.getParameters();
        LabelConverter.getParams(this);
    }

    public int getShadowType()
    {
        return super.border_style;
    }

    public String getText()
    {
        return label == null ? null : label.toString();
    }

    public String[] getTextList()
    {
        if(label == null)
            return null;
        String s = label.toString();
        JCStringTokenizer jcstringtokenizer = new JCStringTokenizer(s);
        String as[] = new String[jcstringtokenizer.countTokens('\n')];
        for(int i = 0; jcstringtokenizer.hasMoreTokens(); i++)
            as[i] = jcstringtokenizer.nextToken('\n').trim();

        return as;
    }

    public void layout()
    {
        if(getPeer() == null)
            return;
        int i = 0;
        int j = 0;
        synchronized(this)
        {
            getDrawingArea(label_rect);
            if(label_rect.width <= 0 || label_rect.height <= 0)
            {
                super.needs_layout = true;
                return;
            }
            super.needs_layout = false;
            if(BWTUtil.isRight(alignment))
                label_rect.x += label_rect.width - label_width;
            else
            if(BWTUtil.isCenter(alignment))
                label_rect.x += (label_rect.width - label_width) / 2;
            if(BWTUtil.isMiddle(alignment))
                label_rect.y += (label_rect.height - label_height) / 2;
            else
            if(BWTUtil.isBottom(alignment))
                label_rect.y += label_rect.height - label_height;
            j = label_width;
            i = label_height;
        }
        label_rect.resize(j, i);
    }

    protected void layout(Object obj)
    {
        setLabelSize(obj);
        layout();
    }

    public boolean mouseMove(Event event, int i, int j)
    {
        if(super.applet_context != null && BWTUtil.is_jcstring(label))
        {
            url = JCString.getURL(super.applet, label, i, j);
            if(url != null)
            {
                setCursor(12);
                return true;
            }
        }
        url = null;
        setCursor(0);
        return false;
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if(url != null)
            JCString.showURL(url, super.applet_context, super.applet);
        return false;
    }

    protected void paintComponent(Graphics g)
    {
        if(super.needs_layout)
            layout();
        drawValue(g, label);
    }

    protected int preferredHeight()
    {
        return Math.max(20, label_height);
    }

    protected int preferredWidth()
    {
        return Math.max(20, label_width);
    }

    public void setAlignment(int i)
    {
        LabelConverter.checkAlignment(i);
        if(i != alignment)
        {
            alignment = i;
            layout();
            repaint();
        }
    }

    public void setFont(Font font)
    {
        boolean flag = getFont() != null && !getFont().equals(font);
        super.setFont(font);
        if(flag)
        {
            setLabelSize(label);
            layout();
            repaint();
        }
    }

    void setFontInternal(Font font)
    {
        super.setFont(font);
        setLabelSize(label);
        layout();
    }

    public void setLabel(Object obj)
    {
        label = obj;
        setLabelSize(label);
        layout();
        repaint();
    }

    public void setLabelAlignment(int i)
    {
        setAlignment(i);
    }

    public void setLabelImage(URL url1)
    {
        label_url = url1;
        setLabel(getToolkit().getImage(url1));
    }

    protected void setLabelSize(Object obj)
    {
        setLabelSize(obj, getFont());
    }

    protected void setLabelSize(Object obj, Font font)
    {
        if(getPeer() == null)
            return;
        label_width = BWTUtil.getWidth(obj, this, font);
        label_height = BWTUtil.getHeight(obj, this, font);
        if(super.applet_context != null && BWTUtil.is_jcstring(obj))
            enableEvents(32L);
    }

    public void setShadowType(int i)
    {
        setBorderStyle(i);
    }

    public void setText(String s)
    {
        setLabel(s);
    }

    public void setTextList(String as[])
    {
        if(as == null)
        {
            setLabel(null);
            return;
        }
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < as.length; i++)
        {
            stringbuffer.append(as[i]);
            if(i < as.length - 1)
                stringbuffer.append('\n');
        }

        setLabel(new String(stringbuffer));
    }

    public static final int TOPLEFT = 0;
    public static final int LEFT = 0;
    public static final int TOPCENTER = 1;
    public static final int CENTER = 1;
    public static final int TOPRIGHT = 2;
    public static final int RIGHT = 2;
    public static final int MIDDLELEFT = 3;
    public static final int MIDDLECENTER = 4;
    public static final int MIDDLE = 4;
    public static final int MIDDLERIGHT = 5;
    public static final int BOTTOMLEFT = 6;
    public static final int BOTTOMCENTER = 7;
    public static final int BOTTOMRIGHT = 8;
    public static final int SHADOW_NONE = 0;
    public static final int SHADOW_ETCHED_IN = 1;
    public static final int SHADOW_ETCHED_OUT = 2;
    public static final int SHADOW_IN = 3;
    public static final int SHADOW_OUT = 4;
    public static final int SHADOW_PLAIN = 5;
    public static final int SHADOW_FRAME_IN = 6;
    public static final int SHADOW_FRAME_OUT = 7;
    int alignment;
    Object label;
    protected int label_width;
    protected int label_height;
    protected Rectangle label_rect;
    protected transient String url;
    transient URL label_url;
    private static final String base = "label";
    private static int nameCounter = 0;

}
