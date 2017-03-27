// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCTextField.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.JComponent;
import jclass.base.TransientComponent;
import jclass.cell.*;
import jclass.util.JCVector;

// Referenced classes of package jclass.bwt:
//            JCTextComponent, BWTEnum, JCActionEvent, JCActionListener, 
//            JCComponent

public class JCTextField extends JCTextComponent
{

    public JCTextField()
    {
        this("", 20);
    }

    public JCTextField(String s)
    {
        this(s, 20);
    }

    public JCTextField(String s, int i)
    {
        this(s, null, null);
        super.columns = i;
    }

    public JCTextField(String s, Applet applet, String s1)
    {
        super(applet, s1);
        actionListeners = new JCVector(0);
        setInsets(new Insets(2, 2, 2, 2));
        if(s1 == null)
            setName("textfield" + nameCounter++);
        setText(s);
    }

    public void addActionListener(JCActionListener jcactionlistener)
    {
        actionListeners.addUnique(jcactionlistener);
    }

    final int drawLine(Graphics g, String s, int i, int j)
    {
        g.drawString(s, i, j);
        return super.fm.stringWidth(s);
    }

    public boolean echoCharIsSet()
    {
        return echo_char != 0;
    }

    public String getActionCommand()
    {
        return actionCommand != null ? actionCommand : "";
    }

    public char getEchoChar()
    {
        return echo_char;
    }

    public String getEchoCharString()
    {
        if(echo_char == 0)
        {
            return null;
        } else
        {
            char ac[] = {
                echo_char
            };
            return new String(ac);
        }
    }

    public Dimension getMinimumSize(int i)
    {
        return getPeer() == null ? super.getMinimumSize() : new Dimension(i * super.fm.charWidth('N'), preferredHeight());
    }

    char[] getOutputChars()
    {
        if(echo_char == 0)
            return getTextChars();
        char ac[] = new char[super.num_char];
        for(int i = 0; i < super.num_char; i++)
            ac[i] = echo_char;

        return ac;
    }

    protected String getOutputText()
    {
        return new String(getOutputChars(), 0, super.num_char);
    }

    protected void getParameters()
    {
        super.getParameters();
    }

    public Dimension getPreferredSize(CellInfo cellinfo, Object obj)
    {
        FontMetrics fontmetrics = cellinfo.getFontMetrics();
        String s = (String)obj;
        return new Dimension(fontmetrics.stringWidth(s), fontmetrics.getHeight());
    }

    public KeyModifier[] getReservedKeys()
    {
        return key_modifiers;
    }

    public boolean keyDown(Event event, int i)
    {
        if(i == 10)
        {
            postActionEvent(event);
            return true;
        } else
        {
            return super.keyDown(event, i);
        }
    }

    protected void paintComponent(Graphics g)
    {
        paintComponent(g, super.horiz_origin, getOutputText(), getDrawingArea(), super.alignment, super.fm, isEnabled(), getForeground(), getBackground(), super.selected_fg, super.selected_bg, super.select_start, super.select_end);
        blinkCursor(true);
    }

    protected void paintComponent(Graphics g, int i, String s, Rectangle rectangle, int j, FontMetrics fontmetrics, boolean flag, 
            Color color, Color color1, Color color2, Color color3, int k, int l)
    {
        g.translate(-i, 0);
        int i1 = rectangle.x;
        switch(j)
        {
        case 1: // '\001'
            i1 += (rectangle.width - fontmetrics.stringWidth(s)) / 2;
            break;

        case 2: // '\002'
            i1 += rectangle.width - fontmetrics.stringWidth(s);
            break;
        }
        if(k >= s.length())
            k = s.length();
        if(l >= s.length())
            l = s.length();
        int j1 = fontmetrics.getHeight();
        int k1 = j1 - fontmetrics.getAscent();
        int l1 = (rectangle.y + j1) - k1;
        if(!isEnabled())
        {
            Color color4 = g.getColor();
            g.setColor(Color.lightGray.darker().darker());
            g.drawString(s, i1, l1);
            g.setColor(color4);
        } else
        if(k != l)
        {
            if(k > 0)
                i1 += drawLine(g, s.substring(0, k), i1, l1);
            int i2 = positionToX(k);
            int j2 = fontmetrics.charsWidth(getOutputChars(), k, l - k);
            g.setColor(color3 == null ? color : color3);
            g.fillRect(i2, 0, j2, Math.min(size().height, fontmetrics.getHeight() + fontmetrics.getDescent() + fontmetrics.getAscent()));
            g.setColor(color2 == null ? color1 : color2);
            i1 += drawLine(g, s.substring(k, l), i1, l1);
            g.setColor(getForeground());
            drawLine(g, s.substring(l), i1, l1);
        } else
        {
            g.drawString(s, i1, l1);
        }
        g.translate(i, 0);
    }

    public int pointToPosition(int i, int j)
    {
        getDrawingArea(super.rect);
        return pointToPosition(i, j, getOutputChars(), super.num_char, super.fm, super.rect, super.horiz_origin);
    }

    protected int pointToPosition(int i, int j, char ac[], int k, FontMetrics fontmetrics, Rectangle rectangle, int l)
    {
        int ai[] = fontmetrics.getWidths();
        int i1 = size().width;
        switch(super.alignment)
        {
        case 1: // '\001'
            i -= rectangle.x + (rectangle.width - fontmetrics.charsWidth(ac, 0, k)) / 2;
            break;

        case 2: // '\002'
            i -= (rectangle.x + rectangle.width) - fontmetrics.charsWidth(ac, 0, k) - l;
            break;

        default:
            i -= rectangle.x - l;
            break;
        }
        for(int j1 = 0; j1 < k; j1++)
        {
            int k1;
            if(ac[j1] < '\u0100')
                k1 = ai[ac[j1]];
            else
                k1 = fontmetrics.charWidth(ac[j1]);
            if(super.overstrike)
            {
                if(i < k1)
                    return j1;
            } else
            if(i < k1 / 2)
                return j1;
            i -= k1;
        }

        return k;
    }

    public int pointToPosition(int i, int j, char ac[], FontMetrics fontmetrics, Rectangle rectangle, int k)
    {
        int l = ac.length;
        return pointToPosition(i, j, ac, l, fontmetrics, rectangle, k);
    }

    public int positionToX(int i)
    {
        if(getPeer() == null)
            return 0;
        getDrawingArea(super.rect);
        i = Math.max(0, Math.min(i, super.num_char));
        int j = 0;
        char ac[] = getOutputChars();
        switch(super.alignment)
        {
        case 2: // '\002'
            return (super.rect.x + super.rect.width) - super.fm.charsWidth(ac, i, super.num_char - i);

        case 1: // '\001'
            j = (super.rect.width - super.fm.charsWidth(ac, 0, super.num_char)) / 2;
            break;
        }
        return super.rect.x + super.fm.charsWidth(ac, 0, i) + j;
    }

    protected void postActionEvent(Event event)
    {
        String s = getActionCommand();
        if(event == null)
            event = new Event(this, 0, s);
        JCActionEvent jcactionevent = new JCActionEvent(this, event.id, s, event.modifiers);
        for(int i = 0; i < actionListeners.size(); i++)
            ((JCActionListener)actionListeners.elementAt(i)).actionPerformed(jcactionevent);

        super.cell_editor_support.fireStopEditing(new CellEditorEvent(jcactionevent));
    }

    public void removeActionListener(JCActionListener jcactionlistener)
    {
        actionListeners.removeElement(jcactionlistener);
    }

    public void setActionCommand(String s)
    {
        actionCommand = s;
    }

    public void setCursorPosition(InitialEvent initialevent, CellInfo cellinfo, String s)
    {
        char ac[] = new char[s.length()];
        s.getChars(0, s.length(), ac, 0);
        int i = pointToPosition(initialevent.getX(), initialevent.getY(), ac, super.num_char, cellinfo.getFontMetrics(), cellinfo.getDrawingArea(), 0);
        setCursorPosition(i);
    }

    public void setEchoChar(char c)
    {
        echo_char = c;
        repaint();
    }

    public void setEchoCharString(String s)
    {
        echo_char = s == null || s.length() <= 0 ? '\0' : s.charAt(0);
    }

    public void setText(String s)
    {
        if(getText().equals(s))
        {
            return;
        } else
        {
            super.setText(s);
            return;
        }
    }

    public void showPosition(int i)
    {
        if(getPeer() == null)
            return;
        int j = positionToX(i);
        int k = super.horiz_origin;
        getDrawingArea(super.rect);
        if(super.rect.width == 0 || super.rect.height == 0)
            return;
        if(j < super.horiz_origin + super.rect.x)
            super.horiz_origin = j - super.rect.x - 2;
        else
        if(j > super.horiz_origin + super.rect.x + super.rect.width)
            super.horiz_origin = (j - (super.rect.x + super.rect.width)) + 5;
        if(k != super.horiz_origin)
            repaint();
        super.cursor_pos = i;
    }

    char echo_char;
    String actionCommand;
    protected JCVector actionListeners;
    private static final String base = "textfield";
    private static int nameCounter = 0;
    KeyModifier key_modifiers[] = {
        new KeyModifier(39, 16), new KeyModifier(37, 16)
    };

}
