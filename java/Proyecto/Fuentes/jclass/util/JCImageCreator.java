// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCImageCreator.java

package jclass.util;

import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.io.PrintStream;

// Referenced classes of package jclass.util:
//            JCUtilConverter

public class JCImageCreator
{

    public JCImageCreator(Component component)
    {
        colorMap = new int[256];
        colorMap[32] = 0;
        comp = component;
    }

    public JCImageCreator(Component component, int i, int j)
    {
        this(component);
        setSize(i, j);
    }

    public synchronized void clear()
    {
        if(width != 0 && height != 0)
            pixels = new int[width * height];
        curRow = 0;
    }

    public synchronized Image create()
    {
        if(pixels == null)
        {
            return null;
        } else
        {
            MemoryImageSource memoryimagesource = new MemoryImageSource(width, height, pixels, 0, width);
            Image image = comp.createImage(memoryimagesource);
            return JCUtilConverter.waitForImage(comp, image) ? image : null;
        }
    }

    public synchronized Image create(String as[])
    {
        clear();
        setPixels(as);
        return create();
    }

    public static Image getImage(byte abyte0[], Component component)
    {
        if(abyte0 == null)
            return null;
        Image image = Toolkit.getDefaultToolkit().createImage(abyte0);
        MediaTracker mediatracker = new MediaTracker(component);
        mediatracker.addImage(image, 0);
        try
        {
            mediatracker.waitForID(0);
        }
        catch(InterruptedException _ex)
        {
            System.out.println("Cannot load image");
        }
        return image;
    }

    public synchronized void setColor(char c, int i)
    {
        if(c < '\u0100')
            colorMap[c] = i;
    }

    public synchronized void setColor(char c, Color color)
    {
        setColor(c, color.getRGB());
    }

    public synchronized void setPixels(int i, String s)
    {
        if(s.length() != width)
            return;
        int j = 0;
        int k = i * width;
        for(; j < width; j++)
            pixels[j + k] = colorMap[s.charAt(j)];

    }

    public synchronized void setPixels(String s)
    {
        setPixels(curRow++, s);
    }

    public synchronized void setPixels(String as[])
    {
        for(int i = 0; i < as.length && i < height; i++)
            setPixels(curRow++, as[i]);

    }

    public synchronized void setSize(int i, int j)
    {
        if(i == width && j == height)
            return;
        width = i;
        height = j;
        if(i == 0 && j == 0)
            pixels = null;
        else
            pixels = new int[i * j];
    }

    protected int curRow;
    protected int width;
    protected int height;
    protected int colorMap[];
    protected int pixels[];
    protected Component comp;
}
