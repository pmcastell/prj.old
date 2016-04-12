// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CajaGraficaScroll.java

package nat.irc;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

// Referenced classes of package nat.irc:
//            CajaGrafica

public class CajaGraficaScroll extends Panel
    implements AdjustmentListener
{

    public void adjustmentValueChanged(AdjustmentEvent adjustmentevent)
    {
        redraw();
    }

    public CajaGraficaScroll()
    {
        cg = new CajaGrafica(this);
        byte byte0 = 10;
        setLayout(new BorderLayout());
        sv = new Scrollbar(1, 0, byte0, 0, cg.alto - getSize().height);
        sv.addAdjustmentListener(this);
        add("East", sv);
        add("Center", cg);
        int i = getSize().width;
        if(i > 20)
            cg.ancho = i - 20;
    }

    public void redraw()
    {
        int i = 0;
        int j = sv.getValue();
        cg.draw(i, j);
    }

    public void setText(String s)
    {
        borrar();
        if(s != "")
            append(s, true);
    }

    public void append(String s, boolean flag)
    {
        append(s, Color.black, flag);
    }

    public void append(String s, Color color, boolean flag)
    {
        cg.append(s, color, flag);
        if(getSize().height != 0 && getSize().width != 0)
        {
            sv.setValue(Math.max(1, (cg.posicionY - getSize().height) + 3 * cg.incrementoEntreLineas));
            redraw();
        }
    }

    public void ponImagen(Image image)
    {
        cg.ponImagen(image);
        sv.setValue(Math.max(0, (cg.posicionY - getSize().height) + cg.incrementoEntreLineas));
        redraw();
    }

    public void borrar()
    {
        cg.borrar();
    }

    public void setBounds(int i, int j, int k, int l)
    {
        super.setBounds(i, j, k, l);
        if(k > 20)
            cg.ancho = k - 20;
        cg.redibuja();
        if(getSize().height != 0)
        {
            sv.setMaximum(cg.alto - getSize().height);
            sv.setValue(Math.max(0, (cg.posicionY - getSize().height) + 3 * cg.incrementoEntreLineas));
        }
        redraw();
    }

    Scrollbar sv;
    CajaGrafica cg;
}
