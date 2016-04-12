// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VisualRouteFrame.java

import java.awt.*;

public class VisualRouteFrame extends Frame
{

    public VisualRouteFrame(VisualRoutePanel visualroutepanel, String s, VisualRouteApplet visualrouteapplet)
    {
        super(s);
        iVScroll = new Scrollbar(1, 0, 5, 0, 50);
        iHScroll = new Scrollbar(0, 0, 5, 0, 50);
        m_p = new VisualRoutePanel(visualrouteapplet, visualroutepanel);
        add("East", iVScroll);
        add("South", iHScroll);
        add("Center", m_p);
        move(10, 10);
        resize(750, 550);
        show();
    }

    public boolean handleEvent(Event event)
    {
        if(event.target == iVScroll)
            m_p.setScrollPos(iHScroll.getValue(), ((Integer)event.arg).intValue());
        else
        if(event.target == iHScroll)
            m_p.setScrollPos(((Integer)event.arg).intValue(), iVScroll.getValue());
        else
        if(event.id == 201)
        {
            dispose();
            m_p.setHost(null);
            return true;
        }
        return super.handleEvent(event);
    }

    private Scrollbar iVScroll;
    private Scrollbar iHScroll;
    private VisualRoutePanel m_p;
}
