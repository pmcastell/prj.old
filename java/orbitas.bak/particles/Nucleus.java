// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Nucleus.java

package particles;

import java.applet.Applet;
import java.awt.*;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

// Referenced classes of package particles:
//            Particle, Orbital

public class Nucleus extends Particle
{

    public Nucleus(Image image, int i, int j, int k)
    {
        super(image, i, j, 0.0D, 0.0D);
        updateRect = new Rectangle(0, 0, 1000, 1000);
        maxDistance = 10000;
        gravity = k;
    }

    public void readOrbitals(Applet applet, String s, Image image)
    {
        countOrbitals(applet, s);
        System.err.println(image);
        orbitals = new Orbital[orbitalCount];
        for(int i = 0; i < orbitalCount; i++)
        {
            String s1 = applet.getParameter(s + i);
            StringTokenizer stringtokenizer = new StringTokenizer(s1);
            try
            {
                int j = Integer.parseInt(stringtokenizer.nextToken());
                int k = Integer.parseInt(stringtokenizer.nextToken());
                int l = Integer.parseInt(stringtokenizer.nextToken());
                int i1 = Integer.parseInt(stringtokenizer.nextToken());
                int j1 = Integer.parseInt(stringtokenizer.nextToken());
                orbitals[i] = new Orbital(image, j, k, this);
                orbitals[i].setRotations(l, i1, j1);
            }
            catch(NoSuchElementException _ex)
            {
                System.err.println("Bad orbital" + i + " declaration in HTML.");
            }
        }

    }

    public void countOrbitals(Applet applet, String s)
    {
        orbitalCount = 0;
        for(int i = 0; applet.getParameter(s + i++) != null;)
            orbitalCount++;

    }

    public void setMinMax(int i, int j)
    {
        minDistance = i;
        maxDistance = j;
    }

    public int maxDistance()
    {
        return maxDistance;
    }

    public int minDistance()
    {
        return minDistance;
    }

    public Rectangle getUpdateRect()
    {
        return updateRect;
    }

    public void step()
    {
        super.step();
        if(orbitalCount > 0)
        {
            updateRect = orbitals[0].getUpdateRect();
            for(int i = 0; i < orbitalCount; i++)
            {
                updateRect = updateRect.union(orbitals[i].getUpdateRect());
                orbitals[i].step();
                updateRect = updateRect.union(orbitals[i].getUpdateRect());
                if(!orbitals[i].alive())
                    deleteOrbital(orbitals[i]);
            }

            zSort();
        }
    }

    public void addOrbital(Orbital orbital)
    {
        Orbital aorbital[] = new Orbital[orbitalCount + 1];
        for(int i = 0; i < orbitalCount; i++)
            aorbital[i] = orbitals[i];

        orbitals = aorbital;
        orbitals[orbitalCount++] = orbital;
    }

    public void deleteOrbital(Orbital orbital)
    {
        for(int i = 0; i < orbitalCount; i++)
            if(orbitals[i] == orbital)
            {
                for(int j = i; j < orbitalCount - 1; j++)
                    orbitals[j] = orbitals[j + 1];

                i = orbitalCount;
            }

        orbitalCount--;
    }

    public void zSort()
    {
        for(int i = orbitalCount; i > 0; i--)
        {
            for(int j = 1; j < i; j++)
                if(orbitals[j].depth() < orbitals[j - 1].depth())
                {
                    Orbital orbital = orbitals[j];
                    orbitals[j] = orbitals[j - 1];
                    orbitals[j - 1] = orbital;
                }

        }

    }

    public void paintAll(Graphics g)
    {
        paintShadows(g);
        paintBackgroundOrbitals(g);
        paint(g);
        paintForegroundOrbitals(g);
    }

    public void paintShadows(Graphics g)
    {
        for(int i = 0; i < orbitalCount; i++)
            orbitals[i].drawShadow(g);

        drawShadow(g);
    }

    public void paintForegroundOrbitals(Graphics g)
    {
        for(int i = 0; i < orbitalCount; i++)
            if(orbitals[i].depth() >= 0)
                orbitals[i].paint(g);

    }

    public void paintBackgroundOrbitals(Graphics g)
    {
        for(int i = 0; i < orbitalCount; i++)
            if(orbitals[i].depth() < 0)
                orbitals[i].paint(g);

    }

    public double gravity()
    {
        return (double)gravity;
    }

    public int count()
    {
        return orbitalCount;
    }

    public Orbital[] orbitals()
    {
        return orbitals;
    }

    public void deleteAllOrbitals()
    {
        orbitalCount = 0;
        orbitals = null;
    }

    public Orbital orbitals[];
    public int orbitalCount;
    int gravity;
    private Rectangle updateRect;
    private int minDistance;
    private int maxDistance;
}
