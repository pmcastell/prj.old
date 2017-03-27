// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BolaGrafica.java

package nat.irc;

import java.awt.Image;

// Referenced classes of package nat.irc:
//            Bola

class BolaGrafica extends Bola
{

    public BolaGrafica(Image image, boolean flag)
    {
        super(flag);
        setSize(img.getWidth(null) + 10, img.getHeight(null) + 10);
        img = image;
    }

    public Image getImagen()
    {
        return img;
    }

    static final int incrementoEntreLineas = 10;
    Image img;

}
