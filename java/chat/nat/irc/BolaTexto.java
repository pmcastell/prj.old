// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BolaTexto.java

package nat.irc;

import java.awt.Color;
import java.awt.FontMetrics;

// Referenced classes of package nat.irc:
//            Bola

public class BolaTexto extends Bola
{

    public BolaTexto(String s, Color color1, boolean flag, FontMetrics fontmetrics)
    {
        super(flag);
        texto = s;
        color = color1;
        setSize(fontmetrics.stringWidth(texto), fontmetrics.getMaxAscent());
    }

    public void setTexto(String s)
    {
        texto = s;
    }

    public void setColor(Color color1)
    {
        color = color1;
    }

    public String getTexto()
    {
        return texto;
    }

    public Color getColor()
    {
        return color;
    }

    private String texto;
    private Color color;
}
