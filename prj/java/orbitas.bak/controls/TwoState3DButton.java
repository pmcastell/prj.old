// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TwoState3DButton.java

package controls;

import java.awt.*;

// Referenced classes of package controls:
//            ThreeDButton

public class TwoState3DButton extends ThreeDButton
{

    public TwoState3DButton(String aString, String string2)
    {
        super(aString);
        altLabel = string2;
    }

    public TwoState3DButton(Image image1, Image image2)
    {
        super(image1);
        altImage = image2;
    }

    public TwoState3DButton(String s1, Image i1, String s2, Image i2)
    {
        super(s1, i1);
        altLabel = s2;
        altImage = i2;
    }

    public void setValue(boolean val)
    {
        value = val;
        repaint();
    }

    public boolean getValue()
    {
        return value;
    }

    public void paint(Graphics g)
    {
        if(!standalone)
            paintBase(g);
        paintContent(g);
    }

    protected void paintContent(Graphics g)
    {
        Dimension d = size();
        int h = d.height - 1;
        int w = d.width - 1;
        int stringWidth = 0;
        int stringHeight = 0;
        if(super.label != null)
        {
            FontMetrics fm = getFontMetrics(getFont());
            stringWidth = fm.stringWidth(super.label);
            stringHeight = fm.getHeight();
        }
        int offset = standalone ? 0 : ((int) (super.state ? 1 : 0));
        g.setColor(super.textColor);
        g.drawRect(0, 0, w, h);
        if(super.label != null && super.image == null)
            g.drawString(currentLabel(), (d.width / 2 - stringWidth / 2) + offset, d.height / 2 + stringHeight / 4 + offset);
        else
        if(super.label != null && super.image != null)
        {
            int total = currentImage().getHeight(null) + stringHeight + 0;
            g.drawString(currentLabel(), (d.width / 2 - stringWidth / 2) + offset, d.height / 2 + total / 2);
            g.drawImage(currentImage(), (d.width / 2 - currentImage().getWidth(null) / 2) + offset, (d.height / 2 - total / 2) + offset, null);
        } else
        if(super.label == null && super.image != null)
            g.drawImage(currentImage(), (d.width / 2 - currentImage().getWidth(null) / 2) + offset, (d.height / 2 - currentImage().getHeight(null) / 2) + offset, null);
    }

    public Dimension minimumSize()
    {
        if(standalone)
            return preferredSize();
        else
            return super.minimumSize();
    }

    private Image currentImage()
    {
        if(value)
            return altImage;
        else
            return super.image;
    }

    public Dimension preferredSize()
    {
        if(standalone)
            return new Dimension(currentImage().getWidth(null), currentImage().getHeight(null));
        else
            return super.preferredSize();
    }

    public boolean handleEvent(Event e)
    {
        switch(e.id)
        {
        case 505: // Event.MOUSE_EXIT
            if(super.hasControl)
            {
                super.state = false;
                repaint();
            }
            return true;

        case 504: // Event.MOUSE_EVENT
            if(super.hasControl)
            {
                super.state = true;
                repaint();
            }
            return true;

        case 501: // Event.MOUSE_DOWN
            super.hasControl = true;
            super.state = true;
            value = !value;
            repaint();
            return true;

        case 502: // Event.MOUSE_UP
            super.hasControl = false;
            Dimension d = size();
            if(!value || !sticky)
                super.state = false;
            paint(getGraphics());
            if(e.x >= 0 && e.x <= d.width && e.y >= 0 && e.y <= d.height)
                postEvent(new Event(this, 1001, super.label));
            return true;

        case 503: // Event.MOUSE_MOVE
        default:
            return false;
        }
    }

    private String currentLabel()
    {
        if(value)
            return altLabel;
        else
            return super.label;
    }

    private Image altImage;
    private String altLabel;
    private boolean sticky;
    private boolean value;
    public boolean standalone;
}
