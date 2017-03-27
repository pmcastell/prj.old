// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MyLabel.java

import java.awt.*;

public class MyLabel extends Panel
{

    public void setText(String s)
    {
        m_label = s;
        invalidate();
        repaint();
    }

    public boolean mouseMove(Event event, int i, int j)
    {
        if(m_bClickable)
            setCursor(new Cursor(12));
        return true;
    }

    public void setBackground(Color color)
    {
        super.setBackground(color);
        repaint();
    }

    public MyLabel()
    {
        this("", 0);
    }

    public MyLabel(String s)
    {
        this(s, 0);
    }

    public MyLabel(String s, int i)
    {
        setText(s);
        setAlignment(i);
    }

    public void setFullUnderline(boolean flag)
    {
        m_bFullUnderline = flag;
    }

    public void paint(Graphics g)
    {
        FontMetrics fontmetrics = g.getFontMetrics();
        int i = getAlignment();
        String s = getText();
        int j = fontmetrics.stringWidth(s);
        int k = i != 1 ? i != 2 ? 0 : size().width - j : (size().width - j) / 2;
        int l = fontmetrics.getLeading() + fontmetrics.getAscent();
        g.drawString(s, k, l);
        if(m_bFullUnderline)
            g.drawLine(0, l, size().width, l);
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if(m_bClickable)
            postEvent(new Event(this, 1001, null));
        return true;
    }

    public Dimension minimumSize()
    {
        FontMetrics fontmetrics = getFontMetrics(getFont());
        int i = Math.max(fontmetrics.stringWidth(getText()), fontmetrics.stringWidth(getWidthText()));
        return new Dimension(i, fontmetrics.getHeight());
    }

    public void setWidthText(String s)
    {
        m_widthText = s;
    }

    public String getWidthText()
    {
        String s = m_widthText;
        if(s != null)
            return s;
        else
            return "";
    }

    public Dimension preferredSize()
    {
        return minimumSize();
    }

    public int getAlignment()
    {
        return m_alignment;
    }

    public void setAlignment(int i)
    {
        m_alignment = i;
        repaint();
    }

    public void setForeground(Color color)
    {
        super.setForeground(color);
        repaint();
    }

    public String getText()
    {
        return m_label;
    }

    public void setClickable(boolean flag)
    {
        m_bClickable = flag;
    }

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    private String m_widthText;
    private String m_label;
    private int m_alignment;
    private boolean m_bFullUnderline;
    private boolean m_bClickable;
}
