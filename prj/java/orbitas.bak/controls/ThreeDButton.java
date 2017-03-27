// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThreeDButton.java

package controls;

import java.awt.*;

public class ThreeDButton extends Canvas
{

    public ThreeDButton()
    {
        label = null;
        textColor = Color.black;
        BORDER = 3;
        BUFFER = 1;
        state = false;
        hasControl = false;
        sticky = false;
        setFont(new Font("times", 0, 10));
    }

    public void makeSticky()
    {
        sticky = true;
    }

    public void setState(boolean flag)
    {
        state = flag;
        repaint();
    }

    public boolean getState()
    {
        return state;
    }

    public ThreeDButton(Image image1)
    {
        this();
        image = image1;
    }

    public ThreeDButton(String s)
    {
        this();
        label = s;
    }

    public ThreeDButton(String s, Image image1)
    {
        this();
        label = s;
        image = image1;
    }

    public void setMargins(int i, int j)
    {
        BORDER = i;
        BUFFER = j;
    }

    public void setTextColor(Color color)
    {
        textColor = color;
    }

    public void changeText(String s)
    {
        label = s;
        paint(getGraphics());
    }

    public Dimension minimumSize()
    {
        int i = 0;
        int j = 0;
        if(label != null)
        {
            FontMetrics fontmetrics = getFontMetrics(getFont());
            i += fontmetrics.stringWidth(label);
            j += fontmetrics.getHeight() + fontmetrics.getMaxDescent();
        }
        if(image != null)
        {
            i = Math.max(i, image.getWidth(null));
            j += image.getHeight(null);
        }
        if(image != null && label != null)
            j += 0;
        return new Dimension(i + 2 * (BORDER + BUFFER), j + 2 * (BORDER + BUFFER));
    }

    public Dimension preferredSize()
    {
        Dimension dimension = minimumSize();
        return new Dimension((int)((double)dimension.width * 1.5D), dimension.height + 2);
    }

    public String label()
    {
        return label;
    }

    public void paint(Graphics g)
    {
        paintBase(g);
        paintContents(g);
    }

    public void paintBase(Graphics g)
    {
        Dimension dimension = size();
        int i = dimension.height - 1;
        int j = dimension.width - 1;
        int ai[] = {
            0, j, j, j - BORDER, j - BORDER, BORDER
        };
        int ai1[] = {
            0, 0, j, j - BORDER, BORDER, BORDER
        };
        int ai2[] = {
            i, i, 0, BORDER, i - BORDER, i - BORDER
        };
        int ai3[] = {
            i, 0, 0, BORDER, BORDER, i - BORDER
        };
        g.setColor(SURFACE);
        g.fillRect(0, 0, dimension.width, dimension.height);
        g.setColor(state ? LIGHT_EDGE : DARK_EDGE);
        g.fillPolygon(new Polygon(ai, ai2, 6));
        g.setColor(state ? DARK_EDGE : LIGHT_EDGE);
        g.fillPolygon(new Polygon(ai1, ai3, 6));
    }

    protected void paintContents(Graphics g)
    {
        Dimension dimension = size();
        int i = dimension.height - 1;
        int j = dimension.width - 1;
        int k = state ? 1 : 0;
        g.setColor(textColor);
        g.drawRect(0, 0, j, i);
        if(label != null && image == null)
        {
            FontMetrics fontmetrics = getFontMetrics(getFont());
            int l = fontmetrics.stringWidth(label);
            int j1 = fontmetrics.getMaxAscent();
            g.drawString(label, (dimension.width / 2 - l / 2) + k, dimension.height / 2 + j1 / 2 + k);
        } else
        if(label != null && image != null)
        {
            FontMetrics fontmetrics1 = getFontMetrics(getFont());
            int i1 = fontmetrics1.stringWidth(label);
            int k1 = fontmetrics1.getHeight();
            int l1 = image.getHeight(null) + k1;
            g.drawString(label, (dimension.width / 2 - i1 / 2) + k, dimension.height / 2 + l1 / 2);
            g.drawImage(image, (dimension.width / 2 - image.getWidth(null) / 2) + k, (dimension.height / 2 - l1 / 2) + k, null);
        } else
        if(label == null && image != null)
            g.drawImage(image, (dimension.width / 2 - image.getWidth(null) / 2) + k, (dimension.height / 2 - image.getHeight(null) / 2) + k, null);
    }

    public boolean handleEvent(Event event)
    {
        switch(event.id)
        {
        case 505: // Event.MOUSE_EXIT
            if(hasControl && !sticky)
            {
                state = false;
                repaint();
            }
            return true;

        case 504: // Event.MOUSE_EVENT
            if(hasControl && !sticky)
            {
                state = true;
                repaint();
            }
            return true;

        case 501: // Event.MOUSE_DOWN
            hasControl = true;
            state = true;
            repaint();
            return true;

        case 502: // Event.MOUSE_UP
            hasControl = false;
            Dimension dimension = size();
            if(!sticky)
                state = false;
            paint(getGraphics());
            if(event.x >= 0 && event.x <= dimension.width && event.y >= 0 && event.y <= dimension.height)
                postEvent(new Event(this, 1001, label));
            return true;

        case 503: // Event.MOUSE_MOVE
        default:
            return false;
        }
    }

    String label;
    protected Color textColor;
    protected Image image;
    protected int BORDER;
    protected int BUFFER;
    protected static final int GAP = 0;
    protected static final boolean UP = false;
    protected static final boolean DOWN = true;
    protected boolean state;
    protected boolean hasControl;
    protected boolean sticky;
    protected static final int LIGHT_SHADE = 240;
    protected static final int DARK_SHADE = 150;
    protected static final int MED_SHADE = 195;
    protected static final Color LIGHT_EDGE = new Color(240, 240, 240);
    protected static final Color DARK_EDGE = new Color(150, 150, 150);
    protected static final Color SURFACE = new Color(195, 195, 195);

}
