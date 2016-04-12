// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Particle.java

package particles;

import java.awt.*;

public class Particle
{

    public Particle()
    {
        vx = vy = lx = ly = 0.0D;
    }

    public static void setShadowImage(Image image1)
    {
        shadowImage = image1;
    }

    public Particle(Image image1, int i, int j, double d, double d1)
    {
        image = image1;
        lx = i;
        ly = j;
        vx = d;
        vy = d1;
        size = new Dimension(image1.getWidth(null), image1.getHeight(null));
    }

    public void step()
    {
        lx += vx;
        ly += vy;
    }

    public void paint(Graphics g)
    {
        if(image != null)
            g.drawImage(image, left(), top(), null);
    }

    void drawShadow(Graphics g)
    {
        if(shadowImage != null)
            g.drawImage(shadowImage, left() + shadowOffset.x, top() + shadowOffset.y, null);
    }

    public int centerx()
    {
        return (int)lx;
    }

    public int centery()
    {
        return (int)ly;
    }

    public int left()
    {
        int i = (int)lx;
        return i - size.width / 2;
    }

    public int top()
    {
        int i = (int)ly;
        return i - size.height / 2;
    }

    public int width()
    {
        return size.width;
    }

    public int height()
    {
        return size.height;
    }

    public int right()
    {
        return left() + width();
    }

    public int bottom()
    {
        return top() + height();
    }

    public Rectangle getRect()
    {
        return new Rectangle(left(), top(), width(), height());
    }

    public double vx()
    {
        return vx;
    }

    public double vy()
    {
        return vy;
    }

    public void setRotations(int i, int j, int k)
    {
        if(i != 0)
            rotated |= 1;
        if(j != 0)
            rotated |= 2;
        if(k != 0)
            rotated |= 4;
        double d = ((double)i * 3.1415926535897931D) / 180D;
        double d1 = ((double)j * 3.1415926535897931D) / 180D;
        double d2 = ((double)k * 3.1415926535897931D) / 180D;
        rotations = new double[2][3];
        rotations[0][0] = Math.sin(d);
        rotations[0][1] = Math.sin(d1);
        rotations[0][2] = Math.sin(d2);
        rotations[1][0] = Math.cos(d);
        rotations[1][1] = Math.cos(d1);
        rotations[1][2] = Math.cos(d2);
    }

    public void slow(double d)
    {
        vx *= d;
        vy *= d;
    }

    public void accelerate(double d, double d1)
    {
        vx += d;
        vy += d1;
    }

    public void setSpeed(double d, double d1)
    {
        vx = d;
        vy = d1;
    }

    public void moveTo(double d, double d1)
    {
        lx = d;
        ly = d1;
    }

    public boolean insideComponent(Component component)
    {
        Rectangle rectangle = getRect();
        Dimension dimension = component.size();
        return rectangle.x + rectangle.width >= 0 && rectangle.y + rectangle.height >= 0 && rectangle.x <= dimension.width && rectangle.y <= dimension.height;
    }

    public Image getImage()
    {
        return image;
    }

    Image image;
    Dimension size;
    double vx;
    double vy;
    double lx;
    double ly;
    static final byte SIN = 0;
    static final byte COS = 1;
    static final byte X = 0;
    static final byte Y = 1;
    static final byte Z = 2;
    protected byte rotated;
    protected double rotations[][];
    private static Image shadowImage = null;
    static Point shadowOffset = new Point(10, 10);

}
