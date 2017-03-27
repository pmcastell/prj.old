// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SnapFrame.java

import java.awt.*;
import java.util.Vector;

public class SnapFrame extends Frame
{

    public SnapFrame(String s, Vector vector, int i)
    {
        this(s, vtos(vector), i);
    }

    public SnapFrame(String s, String s1, int i)
    {
        super(s);
        iTextArea = new TextArea(s1, 30, 80);
        iTextArea.setBackground(Color.white);
        iTextArea.setForeground(Color.black);
        iTextArea.setEditable(false);
        iTextArea.setFont(new Font("Courier", 0, i));
        add("Center", iTextArea);
        pack();
        show();
    }

    private static String vtos(Vector vector)
    {
        String s = "";
        for(int i = 0; i < vector.size(); i++)
            s += vector.elementAt(i) + "\n";

        return s;
    }

    public boolean handleEvent(Event event)
    {
        if(event.id == 201)
        {
            dispose();
            return true;
        } else
        {
            return super.handleEvent(event);
        }
    }

    public void setText(String s)
    {
        iTextArea.setText(s);
        repaint();
    }

    public void setText(Vector vector)
    {
        setText(vtos(vector));
    }

    private TextArea iTextArea;
}
