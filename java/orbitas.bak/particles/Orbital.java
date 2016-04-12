// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Orbital.java

package particles;

import java.awt.*;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

// Referenced classes of package particles:
//            Particle, Nucleus

public class Orbital extends Particle
{

    public Orbital(Image image, int i, int j, double d, double d1, 
            Nucleus nucleus1)
    {
        super(image, i, j, d, d1);
        corrected = false;
        alive = true;
        vectorOn = false;
        steps = 5;
        gravityBound = true;
        nucleus = nucleus1;
    }

    public static void setShadowImage(Image image)
    {
        shadowImage = image;
    }

    public Orbital(Image image, int i, int j, Nucleus nucleus1)
    {
        corrected = false;
        alive = true;
        vectorOn = false;
        steps = 5;
        gravityBound = true;
        nucleus = nucleus1;
        super.image = image;
        super.lx = i;
        super.ly = j;
        super.size = new Dimension(image.getWidth(null), image.getHeight(null));
        makeCircular();
    }

    private void makeCircular()
    {
        double d = distance();
        double d1 = nucleus.gravity() / (1.95D * d * d);
        double d2 = Math.sqrt(d1 * (d - d1));
        super.vx = (d2 * super.ly) / d;
        super.vy = (d2 * -super.lx) / d;
    }

    public static Orbital fromParams(String s, Image image, Nucleus nucleus1)
    {
        Orbital orbital = null;
        StringTokenizer stringtokenizer = new StringTokenizer(s);
        try
        {
            int i = Integer.parseInt(stringtokenizer.nextToken());
            int j = Integer.parseInt(stringtokenizer.nextToken());
            int k = Integer.parseInt(stringtokenizer.nextToken());
            int l = Integer.parseInt(stringtokenizer.nextToken());
            int i1 = Integer.parseInt(stringtokenizer.nextToken());
            orbital = new Orbital(image, i, j, nucleus1);
            orbital.setRotations(k, l, i1);
        }
        catch(NoSuchElementException _ex)
        {
            System.err.println("Bad particle declaration in HTML.");
        }
        return orbital;
    }

    public static void setScaleFactor(int i)
    {
        scaleFactor = i;
    }

    public boolean alive()
    {
        return alive;
    }

    public void toggleVector(boolean flag)
    {
        vectorOn = flag;
    }

    public void setPrecisionLevel(int i)
    {
        steps = i;
    }

    protected void updateVariables()
    {
        x = super.lx;
        y = super.ly;
        z = 0.0D;
    }

    public void step()
    {
        if(nucleus == null)
        {
            super.step();
        } else
        {
            for(int i = 0; i < steps; i++)
            {
                double d = distance();
                if(d <= (double)nucleus.minDistance() || d >= (double)nucleus.maxDistance())
                    alive = false;
                double d1 = gravityBound ? nucleus.gravity() : 0.0D;
                double d2 = d1 / (2D * d * d);
                d2 /= steps;
                double d3 = (super.lx * d2) / d;
                double d4 = (super.ly * d2) / d;
                super.vx -= d3;
                super.vy -= d4;
                super.lx += super.vx / (double)steps;
                super.ly += super.vy / (double)steps;
                innerStep();
            }

        }
        updateVariables();
        if(super.rotated != 0)
            rotate();
    }

    protected void innerStep()
    {
    }

    public void setFree()
    {
        gravityBound = false;
    }

    public void oldstep()
    {
        double d = distance();
        double d1 = nucleus.gravity() / (2D * d * d);
        double d2 = (super.lx * d1) / d;
        double d3 = (super.ly * d1) / d;
        super.vx -= d2;
        super.vy -= d3;
        super.step();
        x = super.lx;
        y = super.ly;
        z = 0.0D;
        if(super.rotated != 0)
            rotate();
    }

    public double distance()
    {
        return Math.sqrt(super.lx * super.lx + super.ly * super.ly);
    }

    private double scale()
    {
        return (double)(depth() + 3 * scaleFactor) / (double)(4 * scaleFactor);
    }

    public int width()
    {
        if(scaleFactor == 1)
            return super.size.width;
        else
            return (int)((double)super.size.width * scale());
    }

    public int height()
    {
        if(scaleFactor == 1)
            return super.size.height;
        else
            return (int)((double)super.size.height * scale());
    }

    public int depth()
    {
        return (int)z;
    }

    private double getForce()
    {
        double d = distance();
        return nucleus.gravity() / (2D * d * d);
    }

    protected double[] forceVector()
    {
        double d = distance();
        double d1 = nucleus.gravity() / (2D * d * d);
        double d2 = (-super.lx * d1) / d;
        double d3 = (-super.ly * d1) / d;
        double ad[] = {
            d2, d3
        };
        return ad;
    }

    public void drawForceArrow(Graphics g)
    {
        double ad[] = forceVector();
        ad[0] *= 25D;
        ad[1] *= 25D;
        drawForceArrow(g, ad);
    }

    public void drawForceArrow(Graphics g, double ad[])
    {
        distance();
        double d = Math.sqrt(ad[0] * ad[0] + ad[1] * ad[1]);
        double d1 = ad[0] / d;
        double d2 = ad[1] / d;
        d += super.image.getWidth(null) >> 1;
        double d3 = d1 * d;
        double d4 = d2 * d;
        g.setColor(Color.gray);
        Point point = new Point((int)((double)centerx() + d3), (int)((double)centery() + d4));
        int i = 0;
        int j = 0;
        if(Math.abs(d3) > Math.abs(d4))
        {
            i = 0;
            j = 1;
        } else
        {
            i = 1;
            j = 0;
        }
        for(int k = -1; k < 2; k++)
            g.drawLine(centerx() + k * i, centery() + k * j, point.x + k * i, point.y + k * j);

        g.setColor(new Color(120, 0, 0));
        d += ARROW_LENGTH;
        d3 = d1 * d;
        d4 = d2 * d;
        point = new Point((int)((double)centerx() + d3), (int)((double)centery() + d4));
        Polygon polygon = new Polygon();
        polygon.addPoint(point.x, point.y);
        double d5 = Math.atan(d4 / d3);
        if(d3 > 0.0D)
            d5 += 3.1415926535897931D;
        double d6 = d5 + 0.52359877559829882D;
        int i1 = (int)(Math.sin(d6) * 8D);
        int l = (int)(Math.cos(d6) * 8D);
        polygon.addPoint(point.x + l, point.y + i1);
        d6 = d5 - 0.52359877559829882D;
        i1 = (int)(Math.sin(d6) * 8D);
        l = (int)(Math.cos(d6) * 8D);
        polygon.addPoint(point.x + l, point.y + i1);
        g.fillPolygon(polygon);
    }

    public void paint(Graphics g)
    {
        if(vectorOn)
            drawForceArrow(g);
        if(scaleFactor == 1)
        {
            g.drawImage(super.image, left(), top(), null);
            return;
        } else
        {
            double d = scale();
            int i = (int)(d * (double)super.size.width);
            int j = (int)(d * (double)super.size.height);
            g.drawImage(super.image, left(), top(), i, j, null);
            return;
        }
    }

    public int centerx()
    {
        if(nucleus == null)
            return super.centerx();
        else
            return (int)Math.round(((Particle) (nucleus)).lx + x);
    }

    public int centery()
    {
        if(nucleus == null)
            return super.centery();
        else
            return (int)Math.round(((Particle) (nucleus)).ly + y);
    }

    public int absolutex()
    {
        return super.centerx();
    }

    public int absolutey()
    {
        return super.centery();
    }

    public int relativey()
    {
        return (int)y;
    }

    public int relativex()
    {
        return (int)x;
    }

    public int left()
    {
        if(nucleus == null)
        {
            return super.left();
        } else
        {
            int i = (int)Math.round(((Particle) (nucleus)).lx + x);
            return i - super.size.width / 2;
        }
    }

    public int top()
    {
        if(nucleus == null)
        {
            return super.top();
        } else
        {
            int i = (int)Math.round(((Particle) (nucleus)).ly + y);
            return i - super.size.height / 2;
        }
    }

    protected void rotate()
    {
        if((super.rotated & 1) == 1)
            rotateX();
        if((super.rotated >> 1 & 1) == 1)
            rotateY();
        if((super.rotated >> 2 & 1) == 1)
            rotateZ();
    }

    private void rotateX()
    {
        double d = y * super.rotations[1][0] - z * super.rotations[0][0];
        z = y * super.rotations[0][0] + z * super.rotations[1][0];
        y = d;
    }

    private void rotateY()
    {
        double d = x * super.rotations[1][1] - z * super.rotations[0][1];
        z = -x * super.rotations[0][1] + z * super.rotations[1][1];
        x = d;
    }

    private void rotateZ()
    {
        double d = x * super.rotations[1][2] - y * super.rotations[0][2];
        y = x * super.rotations[0][2] + y * super.rotations[1][2];
        x = d;
    }

    void drawShadow(Graphics g)
    {
        if(shadowImage != null)
            g.drawImage(shadowImage, left() + Particle.shadowOffset.x, top() + Particle.shadowOffset.y, null);
    }

    public Rectangle getUpdateRect()
    {
        Rectangle rectangle = new Rectangle(left(), top(), width(), height());
        if(shadowImage != null)
        {
            rectangle.width += Particle.shadowOffset.x + 5;
            rectangle.height += Particle.shadowOffset.y + 5;
        }
        if(vectorOn)
        {
            double ad[] = forceVector();
            rectangle.width += (int)Math.abs(ad[0]);
            rectangle.height += (int)Math.abs(ad[1]);
            if(ad[0] < 0.0D)
                rectangle.x += ad[0];
            if(ad[1] < 0.0D)
                rectangle.y += ad[1];
        }
        return rectangle;
    }

    public void changeRadius(int i)
    {
        double d = super.lx;
        double d1 = super.ly;
        double d2 = distance();
        super.lx = (d * (double)i) / d2;
        super.ly = (d1 * (double)i) / d2;
        makeCircular();
    }

    public double speed()
    {
        return Math.sqrt(super.vx * super.vx + super.vy * super.vy);
    }

    public void reverseDirection()
    {
        slow(-1D);
    }

    protected Nucleus nucleus;
    private boolean corrected;
    double x;
    double y;
    double z;
    private static Image shadowImage = null;
    private boolean alive;
    private boolean vectorOn;
    private int steps;
    static int scaleFactor = 1;
    boolean gravityBound;
    private static final double MAGIC_PERCENT = 0.10000000000000001D;
    private static final double ARROW_MULTIPLIER = 25D;
    private static final int ARROW_SIZE = 8;
    private static final double ARROW_ANGLE = 0.52359877559829882D;
    private static final int ARROW_LENGTH = (int)(Math.cos(0.52359877559829882D) * 8D);

}
